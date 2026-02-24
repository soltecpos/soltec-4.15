/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.util;

import com.openbravo.format.Formats;

public class RoundUtils {
    private RoundUtils() {
    }

    public static double round(double dValue) {
        double fractionMultiplier = Math.pow(10.0, Formats.getCurrencyDecimals());
        return Math.rint(dValue * fractionMultiplier) / fractionMultiplier;
    }

    public static int compare(double d1, double d2) {
        return Double.compare(RoundUtils.round(d1), RoundUtils.round(d2));
    }

    public static double getValue(Double value) {
        return value == null ? 0.0 : value;
    }
}

