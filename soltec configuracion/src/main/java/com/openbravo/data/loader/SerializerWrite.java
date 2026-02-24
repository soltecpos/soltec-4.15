/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.loader;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.DataWrite;

public interface SerializerWrite<T> {
    public void writeValues(DataWrite var1, T var2) throws BasicException;
}

