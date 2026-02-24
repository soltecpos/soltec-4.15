/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.loader;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.BatchSentence;
import com.openbravo.data.loader.Session;
import java.io.Reader;
import java.io.StringReader;

public class BatchSentenceScript
extends BatchSentence {
    private String m_sScript;

    public BatchSentenceScript(Session s, String script) {
        super(s);
        this.m_sScript = script;
    }

    @Override
    protected Reader getReader() throws BasicException {
        return new StringReader(this.m_sScript);
    }
}

