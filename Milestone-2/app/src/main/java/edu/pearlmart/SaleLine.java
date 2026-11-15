package edu.pearlmart;
import java.math.BigDecimal;
import static edu.pearlmart.Domain.*;
/** Owned by a Sale; itemId is the association to shared inventory, price is a snapshot.
 * M1 line-level discount/tax are refined to sale-level adjustments to avoid double rounding. */
public record SaleLine(String itemId,String name,BigDecimal unitPrice,int quantity) {
 public SaleLine {itemId=required(itemId,"Item ID");name=required(name,"Name");positive(quantity);if(unitPrice==null||unitPrice.signum()<0||unitPrice.scale()>2)throw new IllegalArgumentException("Invalid unit price.");unitPrice=unitPrice.setScale(2);}
 public BigDecimal lineTotal(){return unitPrice.multiply(BigDecimal.valueOf(quantity)).setScale(2);}
 public BigDecimal amount(){return lineTotal();}
}
