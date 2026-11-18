package edu.pearlmart;
import static edu.pearlmart.Domain.required;
/** Authorized prototype identity. Password management is explicitly outside M2. */
public record User(String userId, String username, Role role) {
 public User { userId=required(userId,"User ID"); username=required(username,"Username"); java.util.Objects.requireNonNull(role); }
 public boolean canPerform(String action) {
  return role==Role.INVENTORY_MANAGER && action.equals("ADD_GOODS") || role==Role.SALESPERSON && action.equals("PROCESS_SALE");
 }
 static final User INVENTORY = new User("U-INV","Inventory manager",Role.INVENTORY_MANAGER);
 static final User SALES = new User("U-SALES","Salesperson",Role.SALESPERSON);
 public void require(String action) { if(!canPerform(action)) throw new SecurityException("Role cannot perform "+action); }
}
