/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.loader;

import com.openbravo.basic.BasicException;

public interface SentenceEnum {
    public void load() throws BasicException;

    public void load(Object var1) throws BasicException;

    public Object getCurrent() throws BasicException;

    public boolean next() throws BasicException;
}

