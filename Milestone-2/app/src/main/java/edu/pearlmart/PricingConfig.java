package edu.pearlmart;
import java.io.*;
import java.math.BigDecimal;
import java.nio.file.*;
import java.util.Properties;
public final class PricingConfig {
    private PricingConfig() {}
    public static PricingPolicy load() throws IOException {
        Properties p = new Properties(); Path external = Path.of("pricing.properties");
        try (InputStream input = Files.exists(external) ? Files.newInputStream(external)
                : PricingConfig.class.getResourceAsStream("/pricing.properties")) {
            if (input == null) throw new IOException("Missing pricing.properties.");
            p.load(input);
        }
        String mode=p.getProperty("mode", "");
        if (mode.equals("none")) return new NoAdjustmentPricing();
        if (!mode.equals("percentage")) throw new IllegalArgumentException("Pricing mode must be none or percentage.");
        return new PercentagePricing(new BigDecimal(p.getProperty("discountRate", "invalid")),
                                     new BigDecimal(p.getProperty("taxRate", "invalid")));
    }
}
