
# B02 - Level 0 DFD and Data Flow Inventory

## 1. Purpose

This document records the data flows provided for the PearlMart Inventory Management System and supports the preparation and validation of the DFD diagrams.

The data flow inventory is based on the project file `dfd-boundary-flows.csv`.

## 2. External Entities

| Entity | Description |
|---|---|
| Employee | An authorized employee who interacts with the inventory management system. |
| Supplier | An external entity that receives supplier orders and provides goods and invoice details. |

## 3. Data Flow Inventory

The original data flow inventory contains 16 flows.

The numbers 1 through 7 refer to internal DFD processes. They are preserved here exactly as provided in the source file.

| ID | Source | Data Flow | Destination |
|---|---|---|---|
| F01 | Employee | Credentials and password change | 1 |
| F02 | 1 | Access and password result | Employee |
| F03 | Employee | Inventory maintenance request | 2 |
| F04 | 2 | Item details and maintenance result | Employee |
| F05 | Employee | Order and receipt details | 3 |
| F06 | 3 | Order and receipt result | Employee |
| F07 | Employee | Sale details and decision | 4 |
| F08 | 4 | Sale result and receipt | Employee |
| F09 | Employee | Replacement request | 5 |
| F10 | 5 | Replacement result | Employee |
| F11 | Employee | Customer item request | 6 |
| F12 | 6 | Request confirmation and list | Employee |
| F13 | Employee | Report criteria | 7 |
| F14 | 7 | Inventory activity report | Employee |
| F15 | 3 | Supplier order | Supplier |
| F16 | Supplier | Supplier goods and invoice details | 3 |

## 4. DFD Modeling Notes

The source inventory identifies seven numbered internal processes.

For a context-level representation using one system process, the external interactions must be represented at the system boundary without showing internal process numbers.

For a decomposed DFD, the original process numbers and the corresponding flows should be retained.

The numbering and naming of DFD levels must follow the course lab instructions.

## 5. Consistency and Balancing

The diagrams should be checked to ensure that:

- The external entities are represented consistently.
- Data flow directions match the source inventory.
- Data flow names remain meaningful and consistent.
- Supplier-related flows are correctly connected.
- The decomposition preserves the external inputs and outputs.
- No unsupported external entities or flows are introduced.
- The diagrams are consistent with the project scope and assumptions.

## 6. Review Checklist

- [ ] Verify all 16 flows against the source CSV.
- [ ] Confirm the correct DFD level numbering.
- [ ] Review the external entities.
- [ ] Prepare the required DFD diagram using Gane-Sarson notation.
- [ ] Check flow names and directions.
- [ ] Validate consistency and balancing.
- [ ] Review the diagram with another team member.
- [ ] Apply any required corrections.

## 7. Status

In Progress.

The 16 data flows have been documented from the supplied source inventory.

The DFD diagram, level numbering, balancing, and team review still require validation.
