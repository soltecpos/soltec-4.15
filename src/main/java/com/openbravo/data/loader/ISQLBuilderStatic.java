/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.loader;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.SerializerWrite;

public interface ISQLBuilderStatic {
    public <T> String getSQL(SerializerWrite<T> var1, T var2) throws BasicException;
}

