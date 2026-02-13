/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.loader;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.DataWrite;
import com.openbravo.data.loader.Datas;
import com.openbravo.data.loader.SerializerWrite;

public class SerializerWriteString
implements SerializerWrite<String> {
    public static final SerializerWriteString INSTANCE = new SerializerWriteString();

    private SerializerWriteString() {
    }

    @Override
    public void writeValues(DataWrite dp, String obj) throws BasicException {
        Datas.STRING.setValue(dp, 1, obj);
    }
}

