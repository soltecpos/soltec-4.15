/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.loader;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.DataWrite;
import com.openbravo.data.loader.Datas;
import com.openbravo.data.loader.SerializerWrite;

public class SerializerWriteBasicComposed
implements SerializerWrite<Object[]> {
    private Datas[][] m_classes;

    public SerializerWriteBasicComposed(Datas[] ... classes) {
        this.m_classes = classes;
    }

    @Override
    public void writeValues(DataWrite dp, Object[] obj) throws BasicException {
        int index = 0;
        for (int i = 0; i < this.m_classes.length; ++i) {
            Object[] val = (Object[])obj[i];
            for (int j = 0; j < this.m_classes[i].length; ++j) {
                this.m_classes[i][j].setValue(dp, ++index, val[j]);
            }
        }
    }
}

