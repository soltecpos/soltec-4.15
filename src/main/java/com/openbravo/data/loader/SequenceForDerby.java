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

public class SequenceForDerby
extends BaseSentence {
    private BaseSentence<Object, Object> sent1;
    private BaseSentence<Object, Object> sent2;
    private BaseSentence<Object, Integer> sent3;

    public SequenceForDerby(Session s, String sSeqTable) {
        this.sent1 = new StaticSentence<Object, Object>(s, "DELETE FROM  " + sSeqTable);
        this.sent2 = new StaticSentence<Object, Object>(s, "INSERT INTO " + sSeqTable + " VALUES (DEFAULT)");
        this.sent3 = new StaticSentence<Object, Integer>(s, "SELECT IDENTITY_VAL_LOCAL() FROM " + sSeqTable, null, SerializerReadInteger.INSTANCE);
    }

    public DataResultSet openExec(Object params) throws BasicException {
        this.sent1.exec();
        this.sent2.exec();
        return this.sent3.openExec(null);
    }

    @Override
    public DataResultSet moreResults() throws BasicException {
        return this.sent3.moreResults();
    }

    @Override
    public void closeExec() throws BasicException {
        this.sent3.closeExec();
    }
}

