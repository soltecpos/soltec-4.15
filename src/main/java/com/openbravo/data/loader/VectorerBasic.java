/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.loader;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.Vectorer;
import com.openbravo.format.Formats;

public class VectorerBasic
implements Vectorer {
    private int[] m_aiIndex;
    private String[] m_asHeaders;
    private Formats[] m_aFormats;

    public VectorerBasic(String[] asHeaders, Formats[] aFormats, int[] aiIndex) {
        this.m_asHeaders = asHeaders;
        this.m_aFormats = aFormats;
        this.m_aiIndex = aiIndex;
    }

    @Override
    public String[] getHeaders() throws BasicException {
        String[] asvalues = new String[this.m_aiIndex.length];
        for (int i = 0; i < this.m_aiIndex.length; ++i) {
            asvalues[i] = this.m_asHeaders[this.m_aiIndex[i]];
        }
        return asvalues;
    }

    @Override
    public String[] getValues(Object obj) throws BasicException {
        Object[] avalues = (Object[])obj;
        String[] asvalues = new String[this.m_aiIndex.length];
        for (int i = 0; i < this.m_aiIndex.length; ++i) {
            asvalues[i] = this.m_aFormats[this.m_aiIndex[i]].formatValue(avalues[this.m_aiIndex[i]]);
        }
        return asvalues;
    }
}

