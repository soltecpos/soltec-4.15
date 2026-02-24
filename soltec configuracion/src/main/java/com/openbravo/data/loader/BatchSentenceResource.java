/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.loader;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.BatchSentence;
import com.openbravo.data.loader.LocalRes;
import com.openbravo.data.loader.Session;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

public class BatchSentenceResource
extends BatchSentence {
    private String m_sResScript;

    public BatchSentenceResource(Session s, String resscript) {
        super(s);
        this.m_sResScript = resscript;
    }

    @Override
    protected Reader getReader() throws BasicException {
        InputStream in = BatchSentenceResource.class.getResourceAsStream(this.m_sResScript);
        if (in == null) {
            throw new BasicException(LocalRes.getIntString("exception.nosentencesfile"));
        }
        try {
            return new InputStreamReader(in, "UTF-8");
        }
        catch (UnsupportedEncodingException ex) {
            throw new BasicException(LocalRes.getIntString("exception.nosentencesfile"), ex);
        }
    }
}

