# Required native Visual Paradigm work

The PNG and PlantUML reference diagrams are not native .vpp deliverables.

1. Open the team's current Visual Paradigm project and save a separate Milestone-2 project copy.
2. Add two UML sequence diagrams named UC04 Add Goods and UC13 Process Sale. Model the participant instances, operation calls/returns, loop and guarded alt fragments. Keep return messages through the actual calling service when refining the reference views.
3. Include missing-field/duplicate/nonpositive quantity and commit failure paths for Add Goods; unknown item, insufficient quantity, pricing failure, pending cancellation, declined cancellation and finalization failure for Process Sale.
4. Add the focused class model with attributes, operation parameter/return types, relationships and multiplicities. Services, JavaFX boundary, shared State repository, encapsulated domain classes and immutable value records, Strategy and Builder must match the source.
5. Reference PNGs split the model for readability; keep one consistent underlying model. Item, Sale, Transaction, StockMovement, User, Role and ActivityRecord are separate source files. Only Totals/Status are nested in Domain; State and Builder are nested in their corresponding classes.
6. Add the architecture sketch and pattern views; no separate UC17 sequence is required.
7. Check implementation details against the actual code, including validation before transaction callbacks and exception return paths. The supplied sequence diagrams are condensed review references, not a substitute for this consistency check.
8. Export readable final diagrams, replace the provisional report images, save the original .vpp, close and reopen it, and include it in the repository.
9. Have a teammate review the native model in a real PR. Record reviewer, feedback and corrections.
