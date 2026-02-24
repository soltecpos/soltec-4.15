/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.loader;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.DataRead;
import com.openbravo.data.loader.SerializableBuilder;
import com.openbravo.data.loader.SerializableRead;
import com.openbravo.data.loader.SerializerRead;

public class SerializerReadBuilder
implements SerializerRead<SerializableRead> {
    private SerializableBuilder m_sb;

    public SerializerReadBuilder(SerializableBuilder sb) {
        this.m_sb = sb;
    }

    @Override
    public SerializableRead readValues(DataRead dr) throws BasicException {
        SerializableRead sr = this.m_sb.createNew();
        sr.readValues(dr);
        return sr;
    }
}

