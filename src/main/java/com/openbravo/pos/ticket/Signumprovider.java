/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.ticket;

import java.util.HashSet;
import java.util.Set;

public class Signumprovider {
    private Set m_positives = new HashSet();
    private Set m_negatives = new HashSet();

    public void addPositive(Object key) {
        this.m_positives.add(key);
    }

    public void addNegative(Object key) {
        this.m_negatives.add(key);
    }

    public Double correctSignum(Object key, Double value) {
        if (this.m_positives.contains(key)) {
            return value < 0.0 ? Double.valueOf(-value.doubleValue()) : value;
        }
        if (this.m_negatives.contains(key)) {
            return value > 0.0 ? Double.valueOf(-value.doubleValue()) : value;
        }
        return value;
    }
}

