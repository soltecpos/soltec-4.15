/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.loader;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.DataRead;

public interface SerializerRead<T> {
    public T readValues(DataRead var1) throws BasicException;
}

