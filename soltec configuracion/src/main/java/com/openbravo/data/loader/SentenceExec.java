/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.loader;

import com.openbravo.basic.BasicException;

public interface SentenceExec<T> {
    public int exec() throws BasicException;

    public int exec(T var1) throws BasicException;
}

