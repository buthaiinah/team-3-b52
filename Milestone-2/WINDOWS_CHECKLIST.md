# Windows 11 verification

Actual tester:
Actual date:
Windows edition/build:
CPU/RAM:
JDK vendor/version:
Maven version:
Git commit tested:

1. Run mvn clean test and retain console output and target/surefire-reports.
2. Run mvn javafx:run. Confirm no startup error and all controls are readable.
3. Execute the six-step README demo and take screenshots with visible before/after stock, status and receipt.
4. Register a new item with all fields; try missing name and duplicate barcode.
5. Cancel a pending sale; also decline the cancellation dialog and verify the draft resumes.
6. Switch to percentage policy, restart and verify discount-before-tax calculation.
7. Verify navigation, inline error messages, keyboard entry and receipt readability.
8. Complete actual results and pass/fail for each test; do not reuse Linux screenshots as Windows evidence.

NFR usability protocol: after a five-minute introduction, an authorized test user performs existing-stock addition, a two-item sale and a pending-sale cancellation without facilitator intervention within three minutes each, with no uncorrected input errors. Record actual times and observations. These are proposed acceptance criteria, not measured results.
