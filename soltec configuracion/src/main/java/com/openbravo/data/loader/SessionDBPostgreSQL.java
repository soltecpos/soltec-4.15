/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.loader;

import com.openbravo.data.loader.SentenceFind;
import com.openbravo.data.loader.SerializerReadInteger;
import com.openbravo.data.loader.Session;
import com.openbravo.data.loader.SessionDB;
import com.openbravo.data.loader.StaticSentence;

public class SessionDBPostgreSQL
implements SessionDB {
    @Override
    public String TRUE() {
        return "True";
    }

    @Override
    public String FALSE() {
        return "False";
    }

    @Override
    public String INTEGER_NULL() {
        return "CAST(NULL AS INTEGER)";
    }

    @Override
    public String CHAR_NULL() {
        return "CAST(NULL AS CHAR)";
    }

    @Override
    public String getName() {
        return "PostgreSQL";
    }

    @Override
    public SentenceFind getSequenceSentence(Session s, String sequence) {
        return new StaticSentence(s, "SELECT NEXTVAL('" + sequence + "')", null, SerializerReadInteger.INSTANCE);
    }

    @Override
    public SentenceFind resetSequenceSentence(Session s, String sequence) {
        return new StaticSentence(s, "ALTER SEQUENCE " + sequence + " RESTART WITH '0'", null, SerializerReadInteger.INSTANCE);
    }
}

