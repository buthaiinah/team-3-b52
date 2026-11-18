package edu.pearlmart;
import java.math.BigDecimal;
import java.util.List;
import static edu.pearlmart.Domain.*;
/** Explicit zero-discount, zero-tax policy; no assumption of legal tax rates. */
public final class NoAdjustmentPricing implements PricingPolicy {
    public Totals calculate(List<SaleLine> lines) {
        return new PercentagePricing(BigDecimal.ZERO, BigDecimal.ZERO).calculate(lines);
    }
}
