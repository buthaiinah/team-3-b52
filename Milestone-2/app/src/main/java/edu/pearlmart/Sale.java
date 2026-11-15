package edu.pearlmart;
import java.time.*;
import java.math.BigDecimal;
import java.util.*;
import static edu.pearlmart.Domain.*;
public final class Sale extends Transaction {
 private List<SaleLine> lines;
 private Totals totals;
 private String cancellationReason="";
 public Sale(String id,Instant at,User user){this(id,at,Status.PENDING,List.of(),null,user,"");}
 private Sale(String id,Instant at,Status status,List<SaleLine> lines,Totals totals,User user,String reason){super(id,at,status,user);this.lines=List.copyOf(lines);this.totals=totals;cancellationReason=reason;}
 private void pending(){if(status()!=Status.PENDING)throw new IllegalStateException("Only a pending sale can be changed.");}
 public void addLine(Item item,int qty){
  pending();positive(qty);if(item.isExpired(LocalDate.now()))throw new IllegalArgumentException("Expired item cannot be sold.");
  var next=new ArrayList<>(lines);var old=next.stream().filter(l->l.itemId().equals(item.id())).findFirst().orElse(null);
  int q=Math.addExact(qty,old==null?0:old.quantity());if(!item.hasStock(q))throw new IllegalArgumentException("Insufficient stock. Available: "+item.quantity());
  next.remove(old);next.add(new SaleLine(item.id(),item.name(),old==null?item.price():old.unitPrice(),q));lines=List.copyOf(next);totals=null;
 }
 public void removeLine(String itemId){pending();lines=lines.stream().filter(l->!l.itemId().equals(itemId)).toList();totals=null;}
 public BigDecimal calculateTotal(PricingPolicy policy){pending();totals=policy.calculate(lines);return totals.payable();}
 public boolean complete(){pending();if(lines.isEmpty()||totals==null)throw new IllegalStateException("Price a nonempty sale before completion.");setStatus(Status.COMPLETED);return true;}
 public boolean cancel(String reason){pending();cancellationReason=required(reason,"Cancellation reason");totals=null;setStatus(Status.CANCELLED);return true;}
 public List<SaleLine> lines(){return lines;} public Totals totals(){return totals;} public String cancellationReason(){return cancellationReason;}
 public Sale copy(){return new Sale(id(),created(),status(),lines,totals,recordedBy(),cancellationReason);}
 @Override public String getSummary(){return id()+" | "+status()+" | lines "+lines.size()+" | "+recordedBy().username()+(cancellationReason.isEmpty()?"":" | "+cancellationReason);}
 private List<Object> value(){return Arrays.asList(id(),created(),status(),lines,totals,recordedBy(),cancellationReason);}
 @Override public boolean equals(Object o){return o instanceof Sale s&&value().equals(s.value());}
 @Override public int hashCode(){return value().hashCode();}
}
