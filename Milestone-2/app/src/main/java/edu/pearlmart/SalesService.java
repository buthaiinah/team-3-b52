package edu.pearlmart;
import java.time.*;
import java.util.*;
import static edu.pearlmart.Domain.*;
public final class SalesService {
    private final InMemoryStore store; private final PricingPolicy pricing; private final User actor;
    public SalesService(InMemoryStore store, PricingPolicy pricing) {this(store,pricing,User.SALES);}
    public SalesService(InMemoryStore store,PricingPolicy pricing,User actor){this.store=store;this.pricing=Objects.requireNonNull(pricing);this.actor=Objects.requireNonNull(actor);}
    public Sale start() {
        actor.require("PROCESS_SALE"); Sale sale=new Sale(UUID.randomUUID().toString(),Instant.now(),actor);
        store.transact(s -> replaceSale(s,sale)); return sale;
    }
    public Sale get(String id) {
        Sale sale=store.snapshot().sales().get(id);
        if (sale==null) throw new IllegalArgumentException("Sale not found."); return sale;
    }
    private Sale pending(InMemoryStore.State s,String id) {
        actor.require("PROCESS_SALE"); Sale sale=s.sales().get(id);
        if (sale==null || sale.status()!=Status.PENDING) throw new IllegalStateException("Only a pending sale can be changed.");
        return sale;
    }
    public void addLine(String saleId,String identifier,int q) {
        positive(q); String key=required(identifier,"Item ID or barcode");
        store.transact(s -> {
            Sale sale=pending(s,saleId);
            Item item=s.items().values().stream().filter(i -> i.id().equals(key)||i.barcode().equals(key)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown item."));
            sale.addLine(item,q);
            return replaceSale(s,sale);
        });
    }
    public void removeLine(String saleId,String itemId) {
        store.transact(s -> { Sale sale=pending(s,saleId);
            sale.removeLine(itemId); return replaceSale(s,sale); });
    }
    public Totals calculate(String saleId) {
        Sale sale=pending(store.snapshot(),saleId); sale.calculateTotal(pricing); return sale.totals();
    }
    public SalesReceipt confirm(String saleId) {
        store.transact(s -> {
            Sale sale=pending(s,saleId); sale.calculateTotal(pricing);
            var items=new HashMap<>(s.items()); var movements=new ArrayList<>(s.movements());
            for(SaleLine l:sale.lines()) {
                Item item=items.get(l.itemId());
                if(item==null || !item.hasStock(l.quantity())) throw new IllegalArgumentException("Stock changed; review quantities before confirming.");
                if(item.isExpired(LocalDate.now())) throw new IllegalArgumentException("Item has expired; remove the line.");
                if(!item.removeStock(l.quantity()))throw new IllegalStateException("Stock changed."); items.put(item.id(),item);
                movements.add(new StockMovement(UUID.randomUUID().toString(),item.id(),-l.quantity(),"SALE",saleId,Instant.now(),actor));
            }
            sale.complete(); Sale completed=sale;
            SalesReceipt receipt=new SalesReceipt.Builder().identity(UUID.randomUUID().toString(),Instant.now())
                .fromCompletedSale(completed).build();
            var sales=new HashMap<>(s.sales()); sales.put(saleId,completed);
            var receipts=new HashMap<>(s.receipts()); receipts.put(saleId,receipt);
            var activities=new ArrayList<>(s.activities()); activities.add(ActivityRecord.of("SALE_COMPLETED",saleId,completed.getSummary(),actor));
            return new InMemoryStore.State(items,sales,movements,receipts,activities);
        });
        return receipt(saleId);
    }
    public void cancel(String saleId) {
        store.transact(s -> {Sale sale=pending(s,saleId);sale.cancel("Cancelled by salesperson before confirmation");
            var next=replaceSale(s,sale);var activities=new ArrayList<>(s.activities());activities.add(ActivityRecord.of("SALE_CANCELLED",saleId,sale.getSummary(),actor));
            return new InMemoryStore.State(next.items(),next.sales(),next.movements(),next.receipts(),activities);});
    }
    public SalesReceipt receipt(String saleId) {
        SalesReceipt r=store.snapshot().receipts().get(saleId);
        if(r==null) throw new IllegalArgumentException("No completed receipt for this sale."); return r;
    }
    private InMemoryStore.State replaceSale(InMemoryStore.State s,Sale sale) {
        var sales=new HashMap<>(s.sales()); sales.put(sale.id(),sale);
        return new InMemoryStore.State(s.items(),sales,s.movements(),s.receipts(),s.activities());
    }
}
