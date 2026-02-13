/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.loader;

import com.openbravo.basic.BasicException;

public interface Vectorer {
    public String[] getHeaders() throws BasicException;

    public String[] getValues(Object var1) throws BasicException;
}

