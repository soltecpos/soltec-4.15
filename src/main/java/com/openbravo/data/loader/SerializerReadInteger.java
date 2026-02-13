/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.loader;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.DataRead;
import com.openbravo.data.loader.Datas;
import com.openbravo.data.loader.SerializerRead;

public class SerializerReadInteger
implements SerializerRead<Integer> {
    public static final SerializerRead<Integer> INSTANCE = new SerializerReadInteger();

    private SerializerReadInteger() {
    }

    @Override
    public Integer readValues(DataRead dr) throws BasicException {
        return (Integer)Datas.INT.getValue(dr, 1);
    }
}

