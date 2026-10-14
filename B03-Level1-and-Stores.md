# B03 – Level 1 DFD and Data Stores

## 1. Purpose

This document describes the Level 1 Data Flow Diagram (DFD) for the PearlMart Inventory Management System. It identifies the main processes, external entities, data stores, and data flows shown in the Visual Paradigm model.

## 2. External Entities

- **Employee:** Interacts with the system to manage access, inventory, procurement, sales, replacements, customer requests, and reports.
- **Supplier:** Receives purchase orders and provides goods information.

## 3. Main Processes

| Process | Name | Description |
|---|---|---|
| 1.0 | Manage Access | Handles employee login and authentication. |
| 2.0 | Maintain Inventory | Manages inventory operations and item information. |
| 3.0 | Manage Procurement | Handles supplier orders and procurement records. |
| 4.0 | Process Sale | Processes sales and records transactions. |
| 5.0 | Process Replacement | Handles item replacement requests and records. |
| 6.0 | Manage Customer Requests | Records and manages customer item requests. |
| 7.0 | Generate Reports | Produces reports using inventory and transaction data. |

## 4. Data Stores

| ID | Data Store | Purpose |
|---|---|---|
| D1 | Users | Stores user and authentication information. |
| D2 | Items | Stores inventory item information. |
| D3 | Procurement | Stores procurement records. |
| D4 | Sales | Stores sales records. |
| D5 | Replacements | Stores replacement records. |
| D6 | Customer Requests | Stores customer request records. |
| D7 | Activity Records | Stores inventory activity records. |

## 5. Main Data Flows

The Level 1 DFD shows the following interactions:

- Employee login and account data are handled by Manage Access.
- Authentication data are associated with the Users data store.
- Inventory operations are handled by Maintain Inventory.
- Procurement information is exchanged with the Supplier.
- Procurement records are stored in D3.
- Inventory item information and updates are associated with D2.
- Sales transactions are recorded in D4.
- Replacement transactions are recorded in D5.
- Customer requests are recorded in D6.
- Activity information is stored in D7.
- Generate Reports receives data from the relevant data stores to prepare reports.

## 6. Consistency and Balancing

The Level 1 DFD is a decomposition of the PearlMart system shown in the Level 0 Context Diagram.

The following items require validation:

- External entities are consistent across Level 0 and Level 1.
- External input and output flows are preserved.
- Process numbering follows the required DFD notation.
- Data stores and their connections are correctly represented.
- Data flow names and directions are consistent.

## 7. Review Checklist

- [ ] Verify all seven processes.
- [ ] Verify all seven data stores.
- [ ] Review data flow names and directions.
- [ ] Compare external flows with Level 0.
- [ ] Validate DFD balancing.
- [ ] Review the diagram with another team member.
- [ ] Apply any required corrections.

## 8. Status

**In Progress — documentation prepared from the current Visual Paradigm Level 1 DFD. Final consistency and balancing checks are pending.**
