/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.user;

import com.openbravo.data.user.DocumentLoader;

public class DocumentLoaderBasic
implements DocumentLoader {
    public static final DocumentLoader INSTANCE = new DocumentLoaderBasic();

    private DocumentLoaderBasic() {
    }

    @Override
    public Object getValue(Object key) {
        return key;
    }

    @Override
    public Object getKey(Object value) {
        return value;
    }
}

