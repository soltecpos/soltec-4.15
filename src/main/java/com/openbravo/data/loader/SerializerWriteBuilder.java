/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.loader;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.DataWrite;
import com.openbravo.data.loader.SerializableWrite;
import com.openbravo.data.loader.SerializerWrite;

public class SerializerWriteBuilder
implements SerializerWrite<SerializableWrite> {
    public static final SerializerWriteBuilder INSTANCE = new SerializerWriteBuilder();

    private SerializerWriteBuilder() {
    }

    @Override
    public void writeValues(DataWrite dp, SerializableWrite obj) throws BasicException {
        obj.writeValues(dp);
    }
}

