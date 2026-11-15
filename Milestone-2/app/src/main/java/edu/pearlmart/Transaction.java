package edu.pearlmart;
import java.time.Instant;
import java.util.Objects;
import static edu.pearlmart.Domain.*;
/** M1 abstraction: identity, occurrence time, status and polymorphic summary. */
public abstract class Transaction {
 private final String transactionId;
 private final Instant occurredAt;
 private Status status;
 private final User recordedBy;
 protected Transaction(String id,Instant at,Status status,User user) {
  transactionId=required(id,"Transaction ID"); occurredAt=Objects.requireNonNull(at);
  this.status=Objects.requireNonNull(status); recordedBy=Objects.requireNonNull(user);
 }
 public final String id(){return transactionId;}
 public final Instant created(){return occurredAt;}
 public final Status status(){return status;}
 public final User recordedBy(){return recordedBy;}
 protected final void setStatus(Status value){status=Objects.requireNonNull(value);}
 public abstract String getSummary();
}
