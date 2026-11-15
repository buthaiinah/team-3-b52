package edu.pearlmart;

import java.time.Instant;
import java.util.*;
import static edu.pearlmart.Domain.*;

public final class InventoryService {
    private final InMemoryStore store; private final User actor;
    public InventoryService(InMemoryStore store) { this(store,User.INVENTORY); }
    public InventoryService(InMemoryStore store,User actor) { this.store = store; this.actor=Objects.requireNonNull(actor); }
    public List<Item> list() {
        return store.snapshot().items().values().stream().sorted(Comparator.comparing(Item::id)).toList();
    }
    public Item find(String identifier) {
        String key = required(identifier, "Item ID or barcode");
        return store.snapshot().items().values().stream()
            .filter(i -> i.id().equals(key) || i.barcode().equals(key)).findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Item not found: " + key));
    }
    public void addNew(Item item) {
        actor.require("ADD_GOODS"); positive(item.quantity());
        store.transact(s -> {
            // Enforce uniqueness across both lookup namespaces to avoid ambiguity.
            if (s.items().values().stream().anyMatch(i -> i.id().equals(item.id()) ||
                i.barcode().equals(item.barcode()) || i.id().equals(item.barcode()) || i.barcode().equals(item.id())))
                throw new IllegalArgumentException("Item ID or barcode already exists. Use Add to existing.");
            var items = new HashMap<>(s.items()); items.put(item.id(), item);
            return added(s, items, item.id(), item.quantity());
        });
    }
    public void addExisting(String identifier, int quantity) {
        actor.require("ADD_GOODS"); positive(quantity); String id = find(identifier).id();
        store.transact(s -> {
            var item = s.items().get(id); var items = new HashMap<>(s.items());
            item.addStock(quantity); items.put(id,item);
            return added(s, items, id, quantity);
        });
    }
    private InMemoryStore.State added(InMemoryStore.State s, Map<String, Item> items, String id, int q) {
        var movements = new ArrayList<>(s.movements()); String ref = UUID.randomUUID().toString();
        movements.add(new StockMovement(UUID.randomUUID().toString(), id, q, "ADD", ref, Instant.now(),actor));
        var activities=new ArrayList<>(s.activities()); activities.add(ActivityRecord.of("ADD_GOODS",ref,id+" +"+q,actor));
        return new InMemoryStore.State(items, s.sales(), movements, s.receipts(),activities);
    }
}
