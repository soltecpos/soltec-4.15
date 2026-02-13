/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.loader;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.DataParams;
import com.openbravo.data.loader.DataWrite;
import com.openbravo.data.loader.SerializerWrite;

public class SerializerWriteParams
implements SerializerWrite<DataParams> {
    public static final SerializerWriteParams INSTANCE = new SerializerWriteParams();

    @Override
    public void writeValues(DataWrite dp, DataParams obj) throws BasicException {
        obj.setDataWrite(dp);
        obj.writeValues();
        obj.setDataWrite(null);
    }
}

