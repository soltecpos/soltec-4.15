/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.loader;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.SentenceExec;

public abstract class SentenceExecAdapter
implements SentenceExec<Object> {
    @Override
    public int exec() throws BasicException {
        return this.exec((Object)null);
    }

    public int exec(Object ... params) throws BasicException {
        return this.exec((Object)params);
    }

    @Override
    public abstract int exec(Object var1) throws BasicException;
}

