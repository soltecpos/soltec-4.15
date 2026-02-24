/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.loader;

import com.openbravo.basic.BasicException;

public interface SentenceFind<T> {
    public T find() throws BasicException;

    public T find(Object var1) throws BasicException;

    public T find(Object ... var1) throws BasicException;
}

