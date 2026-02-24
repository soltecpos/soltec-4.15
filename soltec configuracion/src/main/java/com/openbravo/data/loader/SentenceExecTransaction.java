/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.loader;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.SentenceExec;
import com.openbravo.data.loader.Session;
import com.openbravo.data.loader.Transaction;

public abstract class SentenceExecTransaction<T>
implements SentenceExec<T> {
    private Session m_s;

    public SentenceExecTransaction(Session s) {
        this.m_s = s;
    }

    @Override
    public final int exec() throws BasicException {
        return this.exec(null);
    }

    @Override
    public final int exec(final T params) throws BasicException {
        Transaction<Integer> t = new Transaction<Integer>(this.m_s){

            @Override
            public Integer transact() throws BasicException {
                return SentenceExecTransaction.this.execInTransaction(params);
            }
        };
        return (Integer)t.execute();
    }

    protected abstract int execInTransaction(T var1) throws BasicException;
}

