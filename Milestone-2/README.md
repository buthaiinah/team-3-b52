# PearlMart Milestone 2

JavaFX implementation and review package for UC04 Add Goods and UC13 Process Sale, including UC14–UC17 behavior. This is not a completed course submission: native Visual Paradigm files, Windows 11 verification, instructor feedback and real team Scrum evidence remain release gates.

## Start on Windows 11

Install JDK 21 (a JDK, not only a JRE) and Maven 3.9.x. Check `java -version`, `javac -version`, and `mvn -version`. Maven must use JDK 21.

Open PowerShell in `Milestone-2/app`:

```powershell
mvn clean test
mvn javafx:run
```

The first build downloads dependencies from Maven Central. JavaFX is declared in pom.xml; no separate JavaFX SDK setup is required with this Maven workflow. Pinned JavaFX 21.0.2 is a reproducible prototype baseline, not a claim of the latest release.

Data lives in memory and is lost when the application closes. SampleData loads three clearly marked demonstration products each launch. Both workflows use the same store. No login, supplier, replacement or database deployment is required.

## Demonstrate the system

1. Existing item P001 starts at 20. Add 5 and verify 25.
2. Start a sale. Add P001 quantity 3 and P002 quantity 1.
3. Before confirmation, stock remains 25 and 10. Total with the default zero policy is 61.50.
4. Confirm. Receipt is displayed; stock becomes 22 and 9. Confirm is disabled. Service also rejects duplicate confirmation.
5. Start another sale, add P001 quantity 2 and cancel. Confirm the cancellation dialog. Stock remains 22; history records CANCELLED and no completed receipt is issued.
6. Try a zero quantity, unknown item, duplicate barcode and insufficient stock; verify feedback and unchanged data.

## Pricing configuration and patterns

`app/pricing.properties` is read on startup, with a bundled fallback. `mode=none` chooses NoAdjustmentPricing. To demonstrate the PercentagePricing Strategy, use `mode=percentage`, `discountRate=0.10`, `taxRate=0.05`, then restart. These are example store rules, not legal rates. Discount is rounded to cents before tax on the discounted subtotal; HALF_UP rounding. Missing/invalid policy prevents operation rather than inventing amounts.

SalesReceipt.Builder assembles an immutable receipt only from a completed, priced sale. It is invoked inside the candidate transaction before any state is published. Strategy is behavioral; Builder is creational. No Singleton is counted.

## Evidence and scope

See `evidence` for genuine automated Java tests and Linux headless JavaFX screenshots. These do not certify Windows compatibility or human usability. Run the Windows checklist and capture your own results.

`design/*.puml` and PNGs are provisional model references. They are NOT Visual Paradigm files and do not satisfy the native .vpp deliverable. Recreate/refine the models in Visual Paradigm, export the diagrams, update the report and include the original .vpp.

The team must review and understand the code, adapt it to course conventions, and acknowledge assistance according to course rules. Do not copy a declaration claiming no assistance if it is untrue.

## Repository integration

Use the existing assigned repository and Project. Create a review branch, copy this Milestone-2 directory into the repository, commit and open a PR for a real team review. Do not mark all backlog items Done merely because this package exists. Put final deliverables on main after genuine review and verification. See scrum/SPRINT_PLAN.md.

## Technical reference

Official JavaFX Maven setup: https://openjfx.io/openjfx-docs/#maven

## Milestone 1 alignment revision

Read M1_ALIGNMENT.md for class-by-class continuity and every design refinement. Sale and StockMovement extend Transaction. Item owns stock/price rules. ActivityRecord and the two authorized User/Role contexts are implemented. No login is required by the assignment. The revised source passed 34 tests; re-run this revision on Windows even if the earlier package already worked there. Do not merge old and new src trees: replace the project in a separate folder and import its pom.xml.
