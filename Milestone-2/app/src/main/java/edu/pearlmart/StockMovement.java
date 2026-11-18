package edu.pearlmart;
import java.time.Instant;
import java.util.*;
import static edu.pearlmart.Domain.*;
public final class StockMovement extends Transaction {
 private final String itemId,reason,sourceRef;
 private final int quantityChange;
 public StockMovement(String id,String itemId,int delta,String reason,String ref,Instant at,User user){super(id,at,Status.COMPLETED,user);this.itemId=required(itemId,"Item ID");if(delta==0)throw new IllegalArgumentException("Movement cannot be zero.");quantityChange=delta;this.reason=required(reason,"Reason");sourceRef=ref;}
 public String itemId(){return itemId;}public int delta(){return quantityChange;}public String reason(){return reason;}public String reference(){return sourceRef;}public Instant at(){return created();}
 @Override public String getSummary(){return created()+" | "+itemId+" | "+quantityChange+" | "+reason+" | "+sourceRef+" | "+recordedBy().username();}
 private List<Object> value(){return Arrays.asList(id(),created(),status(),recordedBy(),itemId,quantityChange,reason,sourceRef);}
 @Override public boolean equals(Object o){return o instanceof StockMovement m&&value().equals(m.value());}
 @Override public int hashCode(){return value().hashCode();}
}
