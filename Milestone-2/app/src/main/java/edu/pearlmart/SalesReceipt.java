package edu.pearlmart;
import java.time.Instant;
import java.util.*;
import static edu.pearlmart.Domain.*;
/** Immutable receipt built in stages only from a completed sale. */
public record SalesReceipt(String receiptId, String saleId, Instant issued, List<SaleLine> lines, Totals totals) {
    public SalesReceipt { lines=List.copyOf(lines); }
    public String text() {
        StringBuilder b=new StringBuilder("PEARLMART | SALES RECEIPT\n");
        b.append("Receipt: ").append(receiptId).append("\nSale: ").append(saleId)
         .append("\nIssued: ").append(issued).append("\n\n");
        for (SaleLine l:lines) b.append(String.format("%s | %s x %d @ %s = %s%n", l.itemId(), l.name(), l.quantity(), l.unitPrice(), l.amount()));
        return b.append("\nSubtotal: ").append(totals.subtotal()).append("\nDiscount: ").append(totals.discount())
            .append("\nTax: ").append(totals.tax()).append("\nTOTAL: ").append(totals.payable()).toString();
    }
    /** Creational Builder: separates receipt assembly from the sale transaction. */
    public static final class Builder {
        private String receiptId; private Instant issued; private Sale sale;
        public Builder identity(String id, Instant at) { receiptId=required(id,"Receipt ID"); issued=Objects.requireNonNull(at); return this; }
        public Builder fromCompletedSale(Sale value) {
            if (value.status()!=Status.COMPLETED || value.lines().isEmpty() || value.totals()==null)
                throw new IllegalArgumentException("A receipt requires a completed priced sale.");
            sale=value; return this;
        }
        public SalesReceipt build() {
            if (receiptId==null || issued==null || sale==null) throw new IllegalStateException("Incomplete receipt builder.");
            return new SalesReceipt(receiptId,sale.id(),issued,sale.lines(),sale.totals());
        }
    }
}
