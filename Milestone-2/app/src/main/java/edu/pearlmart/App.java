package edu.pearlmart;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.function.Function;
import static edu.pearlmart.Domain.*;

/** JavaFX boundary/controller. Business rules remain in application services. */
public final class App extends Application {
    final InMemoryStore store=new InMemoryStore();
    final InventoryService inventory=new InventoryService(store);
    SalesService sales;
    String currentSale;
    final TabPane tabs=new TabPane();
    final TableView<Item> stock=new TableView<>();
    final TableView<SaleLine> cart=new TableView<>();
    final Label message=new Label("Ready. Sample inventory is loaded."), totals=new Label("Add an item to calculate the amount payable."), saleStatus=new Label("No sale started");
    final TextArea history=new TextArea(), receipt=new TextArea();
    final TextField search=new TextField();
    final Button confirm=new Button("Confirm sale"), cancel=new Button("Cancel pending sale"), addLine=new Button("Add to sale"), removeLine=new Button("Remove selected line");
    public static void main(String[] args) { launch(args); }
    @Override public void start(Stage stage) {
        try { sales=new SalesService(store,PricingConfig.load()); }
        catch(Exception ex) { new Alert(Alert.AlertType.ERROR,"Pricing configuration invalid: "+ex.getMessage()).showAndWait(); javafx.application.Platform.exit(); return; }
        SampleData.load(inventory);
        Label title=new Label("PearlMart"); title.setStyle("-fx-font-size: 30; -fx-font-weight: bold; -fx-text-fill: #153d4a;");
        Label subtitle=new Label("Inventory and sales  |  Milestone 2 prototype | Authorized role per workflow");
        VBox header=new VBox(3,title,subtitle); header.setPadding(new Insets(20));
        tabs.getTabs().addAll(tab("Inventory & Add Goods",inventoryScreen()),tab("Process Sale",saleScreen()),tab("Transaction history",historyScreen()));
        tabs.getSelectionModel().selectedItemProperty().addListener((o,a,b)->refresh());
        message.setWrapText(true); message.setPadding(new Insets(14)); message.setMinHeight(52);
        BorderPane root=new BorderPane(tabs,header,null,message,null);
        root.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 13; -fx-background-color: #f4f7f8;");
        Scene scene=new Scene(root,1120,900);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        stage.setTitle("PearlMart Inventory Management"); stage.setScene(scene); stage.setMinWidth(1000); stage.setMinHeight(720); stage.show(); refresh();
    }
    private Tab tab(String text,javafx.scene.Node node) { Tab t=new Tab(text,node); t.setClosable(false); return t; }
    private <T> void col(TableView<T> table,String title,Function<T,String> get,double width) {
        TableColumn<T,String> c=new TableColumn<>(title);c.setCellValueFactory(v->new SimpleStringProperty(get.apply(v.getValue())));c.setPrefWidth(width);table.getColumns().add(c);
    }
    private TextField field(String id,String prompt) { TextField t=new TextField();t.setId(id);t.setPromptText(prompt);return t; }
    private void act(Runnable action,String success) {
        try { action.run();message.setText(success);message.setStyle("-fx-text-fill:#126044;"); }
        catch(Exception ex) {message.setText("Not completed: "+(ex.getMessage()==null?ex.getClass().getSimpleName():ex.getMessage()));message.setStyle("-fx-text-fill:#ad2635;");}
        refresh();
    }
    private int quantity(TextField field) { try{return Integer.parseInt(field.getText().trim());}catch(NumberFormatException e){throw new IllegalArgumentException("Quantity must be a whole number.");} }
    private VBox inventoryScreen() {
        col(stock,"Item ID",Item::id,85);col(stock,"Barcode",Item::barcode,100);col(stock,"Name",Item::name,260);
        col(stock,"Unit price",i->i.price().toString(),95);col(stock,"In stock",i->""+i.quantity(),85);col(stock,"Origin",Item::origin,100);col(stock,"Expiry",i->i.expiry()==null?"None":i.expiry().toString(),120);
        stock.setPrefHeight(245);search.setPromptText("Filter by ID, barcode or name");search.setId("search");search.textProperty().addListener((o,a,b)->refreshInventory());
        TextField id=field("itemId","Item ID"),barcode=field("barcode","Unique barcode"),name=field("itemName","Name"),price=field("price","e.g. 12.50"),qty=field("newQty","Positive quantity"),manufacturer=field("manufacturer","Manufacturer"),origin=field("origin","Origin"),comments=field("comments","Optional comments");
        DatePicker expiry=new DatePicker();expiry.setPromptText("Optional expiry");expiry.setEditable(false);
        GridPane grid=new GridPane();grid.setHgap(12);grid.setVgap(8);
        grid.addRow(0,new Label("Item ID"),id,new Label("Barcode"),barcode,new Label("Name"),name);
        grid.addRow(1,new Label("Selling price"),price,new Label("Quantity"),qty,new Label("Manufacturer"),manufacturer);
        grid.addRow(2,new Label("Origin"),origin,new Label("Expiry"),expiry,new Label("Comments"),comments);
        Button create=new Button("Register new item");create.setId("createItem");create.setOnAction(e->act(()->inventory.addNew(new Item(id.getText(),barcode.getText(),name.getText(),new BigDecimal(price.getText().trim()),quantity(qty),manufacturer.getText(),origin.getText(),expiry.getValue(),comments.getText())),"New item registered; stock movement recorded."));
        TextField existing=field("existingId","Existing ID or barcode"),extra=field("existingQty","Quantity to add");
        Button add=new Button("Add to existing");add.setId("addExisting");add.setOnAction(e->act(()->inventory.addExisting(existing.getText(),quantity(extra)),"Stock addition saved; quantity and movement updated together."));
        Button lookup=new Button("Find item");lookup.setOnAction(e->act(()->{var item=inventory.find(existing.getText());search.setText(item.id());},"Item found in shared inventory."));
        VBox v=new VBox(10,new Label("Shared inventory"),search,stock,new Label("Register a new product"),grid,create,new Separator(),new Label("Increase existing stock"),new HBox(10,existing,extra,lookup,add));v.setPadding(new Insets(18));return v;
    }
    private VBox saleScreen() {
        TextField item=field("saleItem","Item ID or barcode"),qty=field("saleQty","Quantity");qty.setText("1");
        Button start=new Button("Start new sale");start.setId("startSale");
        start.setOnAction(e->act(()->{if(currentSale!=null&&sales.get(currentSale).status()==Status.PENDING)throw new IllegalStateException("Confirm or cancel the current sale first.");currentSale=sales.start().id();receipt.clear();},"Pending sale started. Stock is unchanged."));
        addLine.setId("addLine");addLine.setOnAction(e->act(()->sales.addLine(currentSale,item.getText(),quantity(qty)),"Item added to the pending sale. Stock is unchanged."));
        removeLine.setOnAction(e->act(()->{var selected=cart.getSelectionModel().getSelectedItem();if(selected==null)throw new IllegalArgumentException("Select a line first.");sales.removeLine(currentSale,selected.itemId());},"Line removed. Stock is unchanged."));
        col(cart,"Item ID",SaleLine::itemId,85);col(cart,"Product",SaleLine::name,300);col(cart,"Quantity",l->""+l.quantity(),90);col(cart,"Unit price",l->l.unitPrice().toString(),100);col(cart,"Line total",l->l.amount().toString(),120);cart.setPrefHeight(185);
        confirm.setId("confirmSale");confirm.setOnAction(e->act(()->receipt.setText(sales.confirm(currentSale).text()),"Sale completed. Receipt generated and stock deducted exactly once."));
        cancel.setId("cancelSale");cancel.setOnAction(e->{
            Alert a=new Alert(Alert.AlertType.CONFIRMATION,"Cancel this pending sale? Stock will remain unchanged.",ButtonType.YES,ButtonType.NO);
            a.setHeaderText("Confirm cancellation");if(a.showAndWait().orElse(ButtonType.NO)==ButtonType.YES)act(()->{sales.cancel(currentSale);receipt.clear();},"Sale cancelled and recorded. Stock is unchanged.");
        });
        receipt.setEditable(false);receipt.setPrefRowCount(9);receipt.setStyle("-fx-font-family: monospace; -fx-font-size: 12;");
        totals.setStyle("-fx-font-size: 17; -fx-font-weight: bold;");
        VBox v=new VBox(10,new HBox(16,start,saleStatus),new HBox(10,item,qty,addLine,removeLine),cart,totals,new HBox(10,confirm,cancel),new Label("Completed sales receipt"),receipt);v.setPadding(new Insets(18));VBox.setVgrow(receipt,Priority.ALWAYS);return v;
    }
    private VBox historyScreen() {
        history.setEditable(false);history.setStyle("-fx-font-family: monospace;");
        VBox box=new VBox(10,new Label("Recorded sales and inventory movements"),new Label("Prototype data lasts for this application session only."),history);box.setPadding(new Insets(18));VBox.setVgrow(history,Priority.ALWAYS);return box;
    }
    void refreshInventory() {
        String q=search.getText().trim().toLowerCase();stock.setItems(FXCollections.observableArrayList(inventory.list().stream().filter(i->(i.id()+" "+i.barcode()+" "+i.name()).toLowerCase().contains(q)).toList()));
    }
    void refresh() {
        refreshInventory();boolean pending=currentSale!=null&&sales.get(currentSale).status()==Status.PENDING;
        addLine.setDisable(!pending);removeLine.setDisable(!pending);cancel.setDisable(!pending);confirm.setDisable(!pending);
        if(currentSale!=null){var sale=sales.get(currentSale);saleStatus.setText("Status: "+sale.status()+" | "+sale.id().substring(0,8));cart.setItems(FXCollections.observableArrayList(sale.lines()));
            if(sale.lines().isEmpty()||sale.status()==Status.CANCELLED){totals.setText("No payable amount");confirm.setDisable(true);}
            else try {var t=pending?sales.calculate(currentSale):sale.totals();totals.setText("Subtotal "+t.subtotal()+"   - Discount "+t.discount()+"   + Tax "+t.tax()+"   = Total "+t.payable());}
            catch(Exception ex){totals.setText("Calculation unavailable: "+ex.getMessage());confirm.setDisable(true);}
        }
        StringBuilder b=new StringBuilder("SALES\n");store.snapshot().sales().values().stream().sorted(java.util.Comparator.comparing(Sale::created)).forEach(s->b.append(s.getSummary()).append('\n'));
        b.append("\nINVENTORY MOVEMENTS\n");for(var m:store.snapshot().movements())b.append(m.at()).append(" | ").append(m.itemId()).append(" | ").append(m.delta()).append(" | ").append(m.reason()).append(" | ").append(m.reference()).append('\n');b.append("\nACTIVITY RECORDS\n");for(var a:store.snapshot().activities())b.append(a.getSummary()).append('\n');history.setText(b.toString());
    }
}
