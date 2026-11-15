package edu.pearlmart;
import java.util.List;
import static edu.pearlmart.Domain.*;
/** Behavioral Strategy: SalesService delegates calculation through this interface. */
public interface PricingPolicy { Totals calculate(List<SaleLine> lines); }
