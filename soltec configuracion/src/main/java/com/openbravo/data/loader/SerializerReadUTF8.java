/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.loader;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.DataRead;
import com.openbravo.data.loader.Datas;
import com.openbravo.data.loader.SerializerRead;
import java.io.UnsupportedEncodingException;

public class SerializerReadUTF8
implements SerializerRead<String> {
    public static final SerializerRead<String> INSTANCE = new SerializerReadUTF8();

    private SerializerReadUTF8() {
    }

    @Override
    public String readValues(DataRead dr) throws BasicException {
        try {
            return new String((byte[])Datas.BYTES.getValue(dr, 1), "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            return null;
        }
    }
}

