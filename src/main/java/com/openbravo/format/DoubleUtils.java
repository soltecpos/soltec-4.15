/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.format;

public class DoubleUtils {
    public static double fixDecimals(Number value) {
        return Math.rint(value.doubleValue() * 1000000.0) / 1000000.0;
    }
}

