/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.loader;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.DataRead;
import com.openbravo.data.loader.Datas;
import com.openbravo.data.loader.SerializerRead;

public class SerializerReadBytes
implements SerializerRead<byte[]> {
    public static final SerializerRead<byte[]> INSTANCE = new SerializerReadBytes();

    private SerializerReadBytes() {
    }

    @Override
    public byte[] readValues(DataRead dr) throws BasicException {
        return (byte[])Datas.BYTES.getValue(dr, 1);
    }
}

