/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.loader;

import com.openbravo.data.loader.SentenceFind;
import com.openbravo.data.loader.SequenceForMySQL;
import com.openbravo.data.loader.Session;
import com.openbravo.data.loader.SessionDB;

public class SessionDBMySQL
implements SessionDB {
    @Override
    public String TRUE() {
        return "TRUE";
    }

    @Override
    public String FALSE() {
        return "FALSE";
    }

    @Override
    public String INTEGER_NULL() {
        return "CAST(NULL AS UNSIGNED INTEGER)";
    }

    @Override
    public String CHAR_NULL() {
        return "CAST(NULL AS CHAR)";
    }

    @Override
    public String getName() {
        return "MySQL";
    }

    @Override
    public SentenceFind getSequenceSentence(Session s, String sequence) {
        return new SequenceForMySQL(s, sequence);
    }

    @Override
    public SentenceFind resetSequenceSentence(Session s, String sequence) {
        return new SequenceForMySQL(s, "UPDATE pickup_number SET ID=1");
    }
}

