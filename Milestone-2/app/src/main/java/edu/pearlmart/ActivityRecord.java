package edu.pearlmart;
import java.time.Instant;
import java.util.*;
import static edu.pearlmart.Domain.*;
public record ActivityRecord(String activityId,Instant timestamp,String action,String recordRef,String details,User recordedBy) {
 public ActivityRecord {activityId=required(activityId,"Activity ID");Objects.requireNonNull(timestamp);action=required(action,"Action");recordRef=required(recordRef,"Record reference");details=required(details,"Details");Objects.requireNonNull(recordedBy);}
 static ActivityRecord of(String action,String reference,String details,User user){return new ActivityRecord(UUID.randomUUID().toString(),Instant.now(),action,reference,details,user);}
 public String getSummary(){return timestamp+" | "+action+" | "+recordRef+" | "+recordedBy.username()+" | "+details;}
}
