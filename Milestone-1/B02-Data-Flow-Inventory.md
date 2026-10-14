
# B02 - Level 0 DFD and Data Flow Inventory

## 1. Purpose

This document identifies the external entities and major data flows of the PearlMart Inventory Management System. It supports the preparation and review of the Level 0 Data Flow Diagram (DFD).

## 2. External Entities

| Entity | Description |
|---|---|
| Employee | An authorized user who manages inventory, sales, and related operations. |
| Supplier | An external party that receives purchase orders and provides delivery information. |
| Customer | A person who purchases products or submits replacement and unavailable-item requests. |

## 3. Main Data Flows

| ID | Source | Destination | Data Flow |
|---|---|---|---|
| F01 | Employee | Inventory Management System | Login details and inventory updates |
| F02 | Inventory Management System | Employee | Authentication results and inventory information |
| F03 | Employee | Inventory Management System | Supplier orders and received quantities |
| F04 | Inventory Management System | Supplier | Purchase order details |
| F05 | Supplier | Inventory Management System | Supplier invoice and delivery information |
| F06 | Employee | Inventory Management System | Sales and cancellation details |
| F07 | Customer | Inventory Management System | Replacement or unavailable-item requests |
| F08 | Inventory Management System | Employee | Transaction confirmations and inventory reports |

## 4. Level 0 DFD Preparation

The Level 0 DFD should:

- Use Gane-Sarson notation.
- Show the main system processes.
- Identify the external entities.
- Show the main data flows with meaningful labels.
- Include relevant data stores where appropriate.
- Remain consistent with the approved project scope and requirements.

## 5. Review Checklist

- [ ] External entities have been reviewed.
- [ ] Major data flows have been identified.
- [ ] Data flow names are clear and meaningful.
- [ ] The Level 0 DFD has been created.
- [ ] The diagram follows Gane-Sarson notation.
- [ ] The diagram is consistent with the project requirements.
- [ ] The diagram has been reviewed by a team member.

## 6. Status

In Progress - The data flow inventory is a working draft. The Level 0 DFD and its consistency with the project requirements still need to be reviewed.
