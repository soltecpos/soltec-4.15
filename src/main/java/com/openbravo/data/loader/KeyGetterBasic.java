/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.loader;

import com.openbravo.data.loader.IKeyGetter;

public class KeyGetterBasic
implements IKeyGetter {
    private int[] m_aElems;

    public KeyGetterBasic(int[] aElems) {
        this.m_aElems = aElems;
    }

    @Override
    public Object getKey(Object value) {
        if (value == null) {
            return null;
        }
        Object[] avalue = (Object[])value;
        Object[] akey = new Object[this.m_aElems.length];
        for (int i = 0; i < this.m_aElems.length; ++i) {
            akey[i] = avalue[this.m_aElems[i]];
        }
        return akey;
    }
}

