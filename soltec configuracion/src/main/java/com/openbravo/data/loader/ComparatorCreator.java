/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.loader;

import java.util.Comparator;

public interface ComparatorCreator {
    public String[] getHeaders();

    public Comparator<?> createComparator(int[] var1);
}

