/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.user;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.SentenceExec;
import com.openbravo.data.loader.TableDefinition;

public class SaveProvider<T> {
    protected SentenceExec<T> m_sentupdate;
    protected SentenceExec<T> m_sentinsert;
    protected SentenceExec<T> m_sentdelete;

    public SaveProvider(SentenceExec<T> sentupdate, SentenceExec<T> sentinsert, SentenceExec<T> sentdelete) {
        this.m_sentupdate = sentupdate;
        this.m_sentinsert = sentinsert;
        this.m_sentdelete = sentdelete;
    }

    public SaveProvider(TableDefinition table) {
        this.m_sentupdate = (SentenceExec<T>)table.getUpdateSentence();
        this.m_sentdelete = (SentenceExec<T>)table.getDeleteSentence();
        this.m_sentinsert = (SentenceExec<T>)table.getInsertSentence();
    }

    public SaveProvider(TableDefinition table, int[] fields) {
        this.m_sentupdate = (SentenceExec<T>)table.getUpdateSentence(fields);
        this.m_sentdelete = (SentenceExec<T>)table.getDeleteSentence();
        this.m_sentinsert = (SentenceExec<T>)table.getInsertSentence(fields);
    }

    public boolean canDelete() {
        return this.m_sentdelete != null;
    }

    public int deleteData(Object value) throws BasicException {
        return this.m_sentdelete.exec((T)value);
    }

    public boolean canInsert() {
        return this.m_sentinsert != null;
    }

    public int insertData(Object value) throws BasicException {
        return this.m_sentinsert.exec((T)value);
    }

    public boolean canUpdate() {
        return this.m_sentupdate != null;
    }

    public int updateData(Object value) throws BasicException {
        return this.m_sentupdate.exec((T)value);
    }
}

