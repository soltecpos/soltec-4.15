/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.loader;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.DataRead;
import com.openbravo.data.loader.Datas;
import com.openbravo.data.loader.SerializerRead;

public class SerializerReadBasic
implements SerializerRead<Object[]> {
    private Datas[] m_classes;

    public SerializerReadBasic(Datas[] classes) {
        this.m_classes = classes;
    }

    @Override
    public Object[] readValues(DataRead dr) throws BasicException {
        Object[] m_values = new Object[this.m_classes.length];
        for (int i = 0; i < this.m_classes.length; ++i) {
            m_values[i] = this.m_classes[i].getValue(dr, i + 1);
        }
        return m_values;
    }
}

