/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.loader;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.DataWrite;
import com.openbravo.data.loader.Datas;
import com.openbravo.data.loader.SerializerWrite;

public class SerializerWriteBasicExt
implements SerializerWrite<Object[]> {
    private Datas[] m_classes;
    private int[] m_index;

    public SerializerWriteBasicExt(Datas[] classes, int[] index) {
        this.m_classes = classes;
        this.m_index = index;
    }

    @Override
    public void writeValues(DataWrite dp, Object[] obj) throws BasicException {
        for (int i = 0; i < this.m_index.length; ++i) {
            this.m_classes[this.m_index[i]].setValue(dp, i + 1, obj[this.m_index[i]]);
        }
    }
}

