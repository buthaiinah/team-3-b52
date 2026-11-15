# Milestone 1 design alignment

The submitted M1 PDF pages 18–22 is the design reference. The M2 brief pages 1–3 explicitly limits implementation to Add Goods and Process Sale and excludes full login/password management and other workflows. This is a focused refinement, not implementation of all M1 use cases.

| M1 element | Implemented M2 mapping | Evidence |
|---|---|---|
| Item and its private stock/price fields | Item.java; isExpired, hasStock, addStock, removeStock and updatePrice retain M1 responsibilities | InventoryService.addExisting and SalesService.confirm call these operations; T29, T34 |
| Abstract Transaction | Transaction.java retains transactionId, occurredAt, status and abstract getSummary; adds recordedBy association | T26 |
| Sale specializes Transaction | Sale.java extends Transaction; addLine, calculateTotal, complete, cancel and getSummary | T07, T10, T26, T33 |
| StockMovement specializes Transaction | StockMovement.java extends Transaction; quantityChange, reason, sourceRef, item association | T01, T07, T26 |
| Sale owns SaleLine | Private list of immutable SaleLine values; read-only collection, Item association by itemId, price snapshot | T18, T30, T32, T33 |
| PricingPolicy | PricingPolicy.java is the Strategy interface; NoAdjustmentPricing and PercentagePricing implement it | T14, T21, T25 |
| User with Role value | User.java and Role.java; assumed authorized identities, canPerform checks, one recordedBy per transaction/activity | T26–T28, T31 |
| ActivityRecord | ActivityRecord.java stores activityId, timestamp, action, recordRef, details and user | T27–T28; fault tests compare activities too |
| InventoryService | Coordinates Item, User, StockMovement, ActivityRecord and shared store | T01–T06, T27, T31 |
| SalesService | Coordinates Sale, Item, pricing, movements, receipt and activity | T07–T25, T26–T28 |

## Explicit refinements and rationale

1. M1 conceptual service operations are split into UI-sized commands: addGoods becomes addNew/addExisting, search becomes find/list, processSale becomes start/addLine/calculate/confirm, cancelSale becomes cancel, generateReceipt becomes receipt retrieval. User is injected once into a service instead of passed on every call. This does not imply a login session.
2. Sale discount, tax and total are grouped in an immutable Totals value. M1 SaleLine discount/tax fields are not duplicated: all adjustments apply once at sale level, avoiding double application or per-line rounding differences. SaleLine.lineTotal is the pre-adjustment quantity × snapshot price. This monetary refinement must be retained consistently in the native UML.
3. PricingPolicy is an interface with calculate(lines): Totals; rates live in PercentagePricing and external configuration. Default zero adjustments are explicit. NoAdjustmentPricing delegates to the same monetary rules with zero rates.
4. SaleLine and ActivityRecord use Java records; Item and Sale are ordinary encapsulated classes. Item and Sale are copied on storage input, transaction staging and snapshot output. Mutable detached objects cannot change committed state. A candidate State publishes stock, movements, sale, receipt and activities together.
5. SalesReceipt and its Builder are M2 helper additions required for explicit receipt generation. The receipt references the completed sale; repeated confirmation is rejected.
6. Status is a Java enum rather than an unrestricted String. DateTime/Decimal become Instant/BigDecimal. StockMovement is immediately COMPLETED; sale status follows PENDING → COMPLETED or CANCELLED.
7. User retains only identity, username and role in the focused prototype. Password hash, verifyPassword, changePassword, AccessService and the remaining full-system roles are excluded, as permitted by M2. The two fixed workflow identities are a demonstration assumption, not secure authentication.
8. Procurement, suppliers/orders/invoices, goods receipts, replacement, customer requests and administrative reporting remain outside the required implementation. No empty placeholder implementations are presented as working features. Their design remains in the submitted M1 reference.

## Evidence boundaries

34 JUnit tests passed on Linux with Java 21 on 14 November 2026; eight genuine JavaFX scene captures were regenerated from this revision. Earlier Windows screenshots and the 25-test result belong to the earlier revision. Re-run this version on Windows 11 and retain its own logs/screenshots. See evidence/tested_source_sha256.json for the exact tested source bytes.

PlantUML/PNG models are reference diagrams. Native Visual Paradigm .vpp files and matching exports, actual two-Sprint Scrum records, four members' contributions, reviewed PRs and final commit links still require completion. No complete-submission or full-system implementation claim is made.
