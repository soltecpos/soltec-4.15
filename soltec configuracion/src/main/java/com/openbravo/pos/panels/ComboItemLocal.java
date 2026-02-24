/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.panels;

import com.openbravo.data.loader.IKeyed;
import com.openbravo.pos.forms.AppLocal;

public class ComboItemLocal
implements IKeyed {
    protected Integer m_iKey;
    protected String m_sKeyValue;

    public ComboItemLocal(Integer iKey, String sKeyValue) {
        this.m_iKey = iKey;
        this.m_sKeyValue = sKeyValue;
    }

    @Override
    public Object getKey() {
        return this.m_iKey;
    }

    public Object getValue() {
        return this.m_sKeyValue;
    }

    public String toString() {
        return AppLocal.getIntString(this.m_sKeyValue);
    }
}

