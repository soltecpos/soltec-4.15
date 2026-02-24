/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.loader;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.BaseSentence;
import com.openbravo.data.loader.DataResultSet;
import com.openbravo.data.loader.SerializerReadInteger;
import com.openbravo.data.loader.Session;
import com.openbravo.data.loader.StaticSentence;

public class SequenceForMySQL
extends BaseSentence {
    private BaseSentence<Object, Object> sent1;
    private BaseSentence<Object, Integer> sent2;

    public SequenceForMySQL(Session s, String sSeqTable) {
        this.sent1 = new StaticSentence<Object, Object>(s, "UPDATE " + sSeqTable + " SET ID = LAST_INSERT_ID(ID + 1)");
        this.sent2 = new StaticSentence<Object, Integer>(s, "SELECT LAST_INSERT_ID()", null, SerializerReadInteger.INSTANCE);
    }

    public DataResultSet openExec(Object params) throws BasicException {
        this.sent1.exec();
        return this.sent2.openExec(null);
    }

    @Override
    public DataResultSet moreResults() throws BasicException {
        return this.sent2.moreResults();
    }

    @Override
    public void closeExec() throws BasicException {
        this.sent2.closeExec();
    }
}

