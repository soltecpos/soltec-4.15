/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.loader;

import com.openbravo.data.loader.IKeyGetter;
import com.openbravo.data.loader.IKeyed;

public class KeyGetterBuilder
implements IKeyGetter {
    public static final IKeyGetter INSTANCE = new KeyGetterBuilder();

    @Override
    public Object getKey(Object value) {
        return value == null ? null : ((IKeyed)value).getKey();
    }
}

