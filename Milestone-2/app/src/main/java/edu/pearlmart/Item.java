package edu.pearlmart;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import static edu.pearlmart.Domain.*;
/** M1 entity. Changes are made on transaction-local copies; no stock setter. */
public final class Item {
 private final String itemId,barcode,name,manufacturer,origin,comments;
 private BigDecimal sellingPrice;
 private int quantity;
 private final LocalDate expiryDate;
 public Item(String id,String barcode,String name,BigDecimal price,int quantity,String manufacturer,String origin,LocalDate expiry,String comments) {
  itemId=required(id,"Item ID");this.barcode=required(barcode,"Barcode");this.name=required(name,"Name");
  this.manufacturer=required(manufacturer,"Manufacturer");this.origin=required(origin,"Origin");this.comments=comments==null?"":comments.trim();
  expiryDate=expiry;updatePrice(price);if(quantity<0)throw new IllegalArgumentException("Stock cannot be negative.");this.quantity=quantity;
 }
 public boolean isExpired(LocalDate on){return expiryDate!=null&&expiryDate.isBefore(Objects.requireNonNull(on));}
 public boolean hasStock(int qty){positive(qty);return quantity>=qty;}
 public void addStock(int qty){positive(qty);quantity=Math.addExact(quantity,qty);}
 public boolean removeStock(int qty){positive(qty);if(!hasStock(qty))return false;quantity-=qty;return true;}
 public void updatePrice(BigDecimal price){if(price==null||price.signum()<0||price.scale()>2)throw new IllegalArgumentException("Price must be nonnegative with at most two decimal places.");sellingPrice=price.setScale(2);}
 public String id(){return itemId;} public String barcode(){return barcode;} public String name(){return name;}
 public BigDecimal price(){return sellingPrice;} public int quantity(){return quantity;}
 public String manufacturer(){return manufacturer;} public String origin(){return origin;} public LocalDate expiry(){return expiryDate;} public String comments(){return comments;}
 public Item copy(){return new Item(itemId,barcode,name,sellingPrice,quantity,manufacturer,origin,expiryDate,comments);}
 private List<Object> value(){return Arrays.asList(itemId,barcode,name,sellingPrice,quantity,manufacturer,origin,expiryDate,comments);}
 @Override public boolean equals(Object o){return o instanceof Item i&&value().equals(i.value());}
 @Override public int hashCode(){return value().hashCode();}
}
