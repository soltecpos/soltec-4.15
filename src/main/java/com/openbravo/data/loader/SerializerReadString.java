/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.loader;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.DataRead;
import com.openbravo.data.loader.Datas;
import com.openbravo.data.loader.SerializerRead;

public class SerializerReadString
implements SerializerRead<String> {
    public static final SerializerRead<String> INSTANCE = new SerializerReadString();

    private SerializerReadString() {
    }

    @Override
    public String readValues(DataRead dr) throws BasicException {
        return (String)Datas.STRING.getValue(dr, 1);
    }
}

