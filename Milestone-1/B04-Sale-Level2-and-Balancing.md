
# B04 - Sale Level 2 DFD and Balancing

## 1. Objective
Document the Level 2 Data Flow Diagram for Process 4: Process Sale in the PearlMart Inventory Management System.

## 2. Diagram Notation
The diagram uses Gane-Sarson notation to represent processes, external entities, data stores, and data flows.

## 3. Level 2 Subprocesses
Process 4 is decomposed into five subprocesses:

- 4.1 Capture Sale Items
- 4.2 Validate Item and Quantity
- 4.3 Calculate Sale Amount
- 4.4 Complete or Cancel Sale
- 4.5 Generate Receipt and Update Records

## 4. External Entity
- Salesperson: provides sale details and the decision to complete or cancel the sale, and receives the final sale result and receipt.

## 5. Data Stores
- D2 - Items: provides item records and receives sold stock updates for completed sales.
- D4 - Sales: receives transaction records and final sale record updates.
- D7 - Activity records: receives sale activity and receipt generation records.

## 6. Main Data Flows
1. Salesperson sends sale details to Process 4.1.
2. Process 4.1 passes the item identifier and requested quantity to Process 4.2.
3. Process 4.2 reads item records from D2 and passes valid item and quantity details to Process 4.3.
4. Process 4.3 passes the selling amount, discounts, taxes, and final amount to Process 4.4.
5. Salesperson sends the complete or cancel decision to Process 4.4.
6. Process 4.4 records the transaction in D4 and updates D2 when the sale is completed.
7. Process 4.4 passes the sale outcome and transaction details to Process 4.5.
8. Process 4.5 generates the final receipt, updates D4, and records receipt generation activity in D7.
9. The final sale result and receipt are returned to the Salesperson.

## 7. DFD Balancing
The Level 2 diagram decomposes Process 4 from the Level 1 DFD.

Balancing must be checked by comparing the external inputs and outputs of Process 4 in Level 1 with the corresponding boundary flows in Level 2.

The diagram also requires consistency with the Level 0 DFD.

## 8. Validation Checklist
- [ ] Confirm all five subprocesses match the Visual Paradigm diagram.
- [ ] Verify the directions and names of all data flows.
- [ ] Compare Level 2 boundary flows with Process 4 in Level 1.
- [ ] Verify data store usage and transaction updates.
- [ ] Record and correct any balancing differences.

## 9. Related GitHub Issue
Related to #4.

## 10. Status
Level 2 DFD documentation prepared. Final balancing validation is pending team review.
  
