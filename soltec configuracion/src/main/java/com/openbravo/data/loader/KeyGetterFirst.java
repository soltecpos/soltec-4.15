/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.loader;

import com.openbravo.data.loader.IKeyGetter;

public class KeyGetterFirst
implements IKeyGetter {
    private final int[] m_aElems;

    public KeyGetterFirst(int[] aElems) {
        this.m_aElems = aElems;
    }

    @Override
    public Object getKey(Object value) {
        if (value == null) {
            return null;
        }
        Object[] avalue = (Object[])value;
        return avalue[this.m_aElems[0]];
    }
}

