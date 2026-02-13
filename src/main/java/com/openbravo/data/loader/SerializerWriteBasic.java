/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.loader;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.DataWrite;
import com.openbravo.data.loader.Datas;
import com.openbravo.data.loader.SerializerWrite;

public class SerializerWriteBasic
implements SerializerWrite<Object[]> {
    private Datas[] m_classes;

    public SerializerWriteBasic(Datas ... classes) {
        this.m_classes = classes;
    }

    @Override
    public void writeValues(DataWrite dp, Object[] obj) throws BasicException {
        for (int i = 0; i < this.m_classes.length; ++i) {
            this.m_classes[i].setValue(dp, i + 1, obj[i]);
        }
    }
}

