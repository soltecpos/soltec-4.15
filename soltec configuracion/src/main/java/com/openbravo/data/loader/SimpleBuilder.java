/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.loader;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.ISQLBuilderStatic;
import com.openbravo.data.loader.SerializerWrite;

public class SimpleBuilder
implements ISQLBuilderStatic {
    private String m_sSentence;

    public SimpleBuilder(String sSentence) {
        this.m_sSentence = sSentence;
    }

    @Override
    public <T> String getSQL(SerializerWrite<T> sw, T params) throws BasicException {
        return this.m_sSentence;
    }
}

