/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.user;

import com.openbravo.basic.BasicException;
import com.openbravo.data.user.EditorCreator;

public class EditorCreatorComposed
implements EditorCreator {
    private EditorCreator[] m_editors;

    public EditorCreatorComposed(EditorCreator ... editors) {
        this.m_editors = editors;
    }

    @Override
    public Object createValue() throws BasicException {
        Object[] value = new Object[this.m_editors.length];
        for (int i = 0; i < this.m_editors.length; ++i) {
            value[i] = this.m_editors[i].createValue();
        }
        return value;
    }
}

