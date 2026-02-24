/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.loader;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.DataWrite;
import com.openbravo.data.loader.Datas;
import com.openbravo.data.loader.SerializerWrite;

public class SerializerWriteInteger
implements SerializerWrite<Integer> {
    public static final SerializerWrite INSTANCE = new SerializerWriteInteger();

    private SerializerWriteInteger() {
    }

    @Override
    public void writeValues(DataWrite dp, Integer obj) throws BasicException {
        Datas.INT.setValue(dp, 1, obj);
    }
}

