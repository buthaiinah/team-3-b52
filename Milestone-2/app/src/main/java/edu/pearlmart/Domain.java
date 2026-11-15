package edu.pearlmart;

import java.math.BigDecimal;
import java.time.*;
import java.util.*;

/** Shared status, monetary totals and input validation helpers. */
public final class Domain {
    private Domain() {}
    public enum Status { PENDING, COMPLETED, CANCELLED }
    public record Totals(BigDecimal subtotal, BigDecimal discount, BigDecimal tax, BigDecimal payable) {}
    public static String required(String v, String label) {
        if (v == null || v.isBlank()) throw new IllegalArgumentException(label + " is required.");
        return v.trim();
    }
    public static void positive(int q) {
        if (q <= 0) throw new IllegalArgumentException("Quantity must be a positive whole number.");
    }
}
