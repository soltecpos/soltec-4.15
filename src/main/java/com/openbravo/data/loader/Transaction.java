/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.loader;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.Session;
import java.sql.SQLException;

public abstract class Transaction<T> {
    private Session s;

    public Transaction(Session s) {
        this.s = s;
    }

    public final T execute() throws BasicException {
        if (this.s.isTransaction()) {
            return this.transact();
        }
        try {
            try {
                this.s.begin();
                T result = this.transact();
                this.s.commit();
                return result;
            }
            catch (BasicException e) {
                this.s.rollback();
                throw e;
            }
        }
        catch (SQLException eSQL) {
            throw new BasicException("Transaction error", eSQL);
        }
    }

    protected abstract T transact() throws BasicException;
}

