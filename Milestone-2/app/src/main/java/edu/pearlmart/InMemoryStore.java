package edu.pearlmart;

import java.util.*;
import java.util.function.UnaryOperator;
import static edu.pearlmart.Domain.*;

/** Copy-on-write transaction boundary, shared by inventory and sales services. */
public final class InMemoryStore {
    public record State(Map<String, Item> items, Map<String, Sale> sales,
                        List<StockMovement> movements, Map<String, SalesReceipt> receipts, List<ActivityRecord> activities) {
        public State {
            var itemCopies=new HashMap<String,Item>(); items.forEach((k,v)->itemCopies.put(k,v.copy())); items=Map.copyOf(itemCopies);
            var saleCopies=new HashMap<String,Sale>(); sales.forEach((k,v)->saleCopies.put(k,v.copy())); sales=Map.copyOf(saleCopies);
            movements = List.copyOf(movements); receipts = Map.copyOf(receipts); activities=List.copyOf(activities);
        }
    }
    private State state = new State(Map.of(), Map.of(), List.of(), Map.of(), List.of());
    private Runnable beforeCommit = () -> {};
    public synchronized State snapshot() { return new State(state.items(),state.sales(),state.movements(),state.receipts(),state.activities()); }
    public synchronized void transact(UnaryOperator<State> operation) {
        State candidate = Objects.requireNonNull(operation.apply(snapshot()));
        beforeCommit.run(); // Test seam: failure here must publish no changes.
        state = new State(candidate.items(),candidate.sales(),candidate.movements(),candidate.receipts(),candidate.activities());  // Single publication of all stock and transaction records.
    }
    void setBeforeCommit(Runnable guard) { beforeCommit = Objects.requireNonNull(guard); }
}
