/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.loader;

import com.openbravo.data.loader.SentenceFind;
import com.openbravo.data.loader.Session;

public interface SessionDB {
    public String TRUE();

    public String FALSE();

    public String INTEGER_NULL();

    public String CHAR_NULL();

    public String getName();

    public SentenceFind getSequenceSentence(Session var1, String var2);

    public SentenceFind resetSequenceSentence(Session var1, String var2);
}

