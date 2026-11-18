package edu.pearlmart;
import java.math.*;
import java.util.List;
import static edu.pearlmart.Domain.*;
public final class PercentagePricing implements PricingPolicy {
    private final BigDecimal discountRate, taxRate;
    public PercentagePricing(BigDecimal discountRate, BigDecimal taxRate) {
        if (discountRate == null || taxRate == null || discountRate.signum() < 0 ||
            discountRate.compareTo(BigDecimal.ONE) > 0 || taxRate.signum() < 0 || taxRate.compareTo(BigDecimal.ONE) > 0)
            throw new IllegalArgumentException("Configured rates must be between 0 and 1.");
        this.discountRate=discountRate; this.taxRate=taxRate;
    }
    public Totals calculate(List<SaleLine> lines) {
        if (lines.isEmpty()) throw new IllegalArgumentException("Add at least one valid item before calculating.");
        BigDecimal subtotal = lines.stream().map(SaleLine::amount).reduce(new BigDecimal("0.00"), BigDecimal::add);
        BigDecimal discount = subtotal.multiply(discountRate).setScale(2, RoundingMode.HALF_UP);
        BigDecimal net = subtotal.subtract(discount);
        BigDecimal tax = net.multiply(taxRate).setScale(2, RoundingMode.HALF_UP);
        return new Totals(subtotal, discount, tax, net.add(tax));
    }
}
