package edu.pearlmart;
import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.image.WritableImage;
import javafx.stage.*;
import java.nio.file.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.util.concurrent.*;

/** Optional GUI smoke/evidence runner. Run separately; not part of mvn test. */
public class GuiEvidence {
 static Stage stage; static App app;
 static void text(String id,String value){((TextField)stage.getScene().lookup("#"+id)).setText(value);}
 static void click(String id){((Button)stage.getScene().lookup("#"+id)).fire();}
 static void shot(String name)throws Exception{
  stage.getScene().getRoot().applyCss();stage.getScene().getRoot().layout();
  WritableImage im=stage.getScene().snapshot(null); BufferedImage out=new BufferedImage((int)im.getWidth(),(int)im.getHeight(),BufferedImage.TYPE_INT_ARGB);
  for(int y=0;y<out.getHeight();y++)for(int x=0;x<out.getWidth();x++)out.setRGB(x,y,im.getPixelReader().getArgb(x,y));
  Path p=Path.of("../evidence/screenshots",name+".png");Files.createDirectories(p.getParent());ImageIO.write(out,"png",p.toFile());
 }
 static void check(boolean value,String message){if(!value)throw new AssertionError(message);}
 public static void main(String[] args)throws Exception{
  CountDownLatch done=new CountDownLatch(1);Throwable[] failure=new Throwable[1];
  Platform.startup(()->{try{
   app=new App();stage=new Stage();app.start(stage);
   text("existingId","P001");text("existingQty","5");click("addExisting");check(app.inventory.find("P001").quantity()==25,"Addition failed");shot("01_add_goods");
   text("existingQty","0");click("addExisting");check(app.message.getText().startsWith("Not completed"),"Expected validation error");shot("02_invalid_quantity");
   app.tabs.getSelectionModel().select(1);stage.getScene().getRoot().applyCss();stage.getScene().getRoot().layout();click("startSale");
   text("saleItem","P001");text("saleQty","3");click("addLine");text("saleItem","P002");text("saleQty","1");click("addLine");
   check(app.inventory.find("P001").quantity()==25,"Draft deducted stock");shot("03_pending_sale");
   click("confirmSale");check(app.inventory.find("P001").quantity()==22,"Sale stock wrong");check(app.receipt.getText().contains("TOTAL: 61.50"),"Receipt wrong");shot("04_completed_receipt");
   click("startSale");text("saleItem","P001");text("saleQty","2");click("addLine");
   Platform.runLater(()->{for(Window window:java.util.List.copyOf(Window.getWindows()))if(window!=stage && window.getScene()!=null){var n=window.getScene().getRoot();if(n instanceof DialogPane pane)((Button)pane.lookupButton(ButtonType.YES)).fire();}});
   click("cancelSale");check(app.sales.get(app.currentSale).status()==Domain.Status.CANCELLED,"Cancellation not recorded");check(app.inventory.find("P001").quantity()==22,"Cancellation changed stock");shot("05_cancelled_sale");
   app.tabs.getSelectionModel().select(2);shot("06_transaction_history");
   check(app.store.snapshot().activities().getLast().action().equals("SALE_CANCELLED"),"Missing cancellation activity");
   app.tabs.getSelectionModel().select(0);shot("07_stock_after_cancellation");
   text("itemId","P004");text("barcode","100004");text("itemName","Demo new product");text("price","5.00");text("newQty","4");text("manufacturer","Demo manufacturer");text("origin","Qatar");click("createItem");
   check(app.inventory.find("P004").quantity()==4,"New registration failed");shot("08_new_item_registration");
   System.out.println("GUI SMOKE PASS: addition, validation, pending sale, completion receipt, cancellation, history. Platform: "+System.getProperty("os.name"));
  }catch(Throwable t){failure[0]=t;t.printStackTrace();}finally{if(stage!=null)stage.close();done.countDown();}});
  if(!done.await(45,TimeUnit.SECONDS))throw new AssertionError("GUI smoke timed out");Platform.exit();if(failure[0]!=null)throw new AssertionError(failure[0]);
 }
}
