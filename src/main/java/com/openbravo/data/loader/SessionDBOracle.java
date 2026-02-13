/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.loader;

import com.openbravo.data.loader.SentenceFind;
import com.openbravo.data.loader.SerializerReadInteger;
import com.openbravo.data.loader.Session;
import com.openbravo.data.loader.SessionDB;
import com.openbravo.data.loader.StaticSentence;

public class SessionDBOracle
implements SessionDB {
    @Override
    public String TRUE() {
        return "1";
    }

    @Override
    public String FALSE() {
        return "0";
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
        return "Oracle";
    }

    @Override
    public SentenceFind<Integer> getSequenceSentence(Session s, String sequence) {
        return new StaticSentence(s, "SELECT " + sequence + ".NEXTVAL FROM DUAL", null, SerializerReadInteger.INSTANCE);
    }

    @Override
    public SentenceFind<Integer> resetSequenceSentence(Session s, String sequence) {
        return new StaticSentence(s, "SELECT " + sequence + ".NEXTVAL FROM DUAL", null, SerializerReadInteger.INSTANCE);
    }
}

