# PearlMart Inventory Management System
## B01 – Scope and Assumptions

### 1. Project Overview

PearlMart Inventory Management System is designed to automate inventory-related activities in a medium-sized retail store. It replaces manual inventory procedures with an integrated system that maintains consistent information across the warehouse and billing counters.

### 2. System Scope

**The system includes:**

- User login, password changes, and role-based access for authorized employees.
- Item registration and maintenance, including barcode, name, price, quantity, manufacturer, origin, and applicable expiration date.
- Adding goods to inventory and removing available quantities.
- Searching for items and updating permitted item information.
- Identifying and displaying expired goods.
- Preparing supplier orders and recording supplier invoices and delivered quantities.
- Processing sales, calculating applicable discounts and taxes, generating receipts, and updating inventory.
- Cancelling sales before completion without permanently deducting stock.
- Processing accepted item replacements and adjusting inventory.
- Recording customer requests for unavailable items.
- Maintaining transaction records and generating inventory summary reports.

### 3. System Boundary

The PearlMart Inventory Management System is responsible for managing the store's inventory information, inventory transactions, supplier orders, sales-related stock changes, and reports.

Suppliers are external entities. The system exchanges order and delivery information with them but does not manage their internal operations.

### 4. Assumptions

| ID | Assumption | Justification | Affected Models |
|---|---|---|---|
| A01 | Each item has a unique item identifier and barcode. | Prevents duplicate item identification. | DFD, Use Case, Class Diagram |
| A02 | Authorized employees access only functions permitted by their roles. | Supports the stated employee responsibilities. | Use Case, Class Diagram |
| A03 | Inventory quantities cannot become negative. | Prevents invalid stock records. | DFD, Use Case Specifications, Class Diagram |
| A04 | Supplier deliveries are verified before received quantities are added to inventory. | Ensures recorded quantities reflect accepted deliveries. | DFD, Use Case Specifications |
| A05 | A cancelled sale does not permanently reduce available inventory. | Preserves stock consistency. | DFD, Use Case Specifications |
| A06 | Replacement requests must satisfy PearlMart's replacement policy before stock is adjusted. | The policy is mentioned but its detailed conditions are not specified. | Use Case Specifications, Class Diagram |
| A07 | Applicable tax rates and discount rules are configured according to store policy. | Specific rates and calculation rules are not provided. | Use Case Specifications, Class Diagram |
| A08 | Inventory changes from warehouse and billing counters are recorded consistently in the same system. | The project requires an integrated inventory record. | DFD, Class Diagram |

### 5. Out of Scope

- Designing or managing the internal systems and operations of external suppliers.
- Customer-facing online shopping or e-commerce functionality.
- Employee payroll and human resources management.
- Accounting functions beyond the inventory-related transactions described in the project.

These exclusions define the proposed project boundary and should be confirmed with the lab instructor if additional requirements are introduced.

### 6. Open Questions for the Client

1. What conditions determine whether a customer replacement request is accepted?
2. What discount and tax rules should be applied during billing?
3. Which employee roles may change selling prices or remove inventory?
4. How should the system handle partial supplier deliveries?
5. What information should be included in each inventory summary report?

### 7. Definition of Done

This document is considered complete when its scope and assumptions have been reviewed against the project description, any necessary corrections have been applied, and the updated file has been saved in the repository.
