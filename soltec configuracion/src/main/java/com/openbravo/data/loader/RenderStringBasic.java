/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.loader;

import com.openbravo.data.loader.IRenderString;
import com.openbravo.format.Formats;

public class RenderStringBasic
implements IRenderString {
    private Formats[] m_aFormats;
    private int[] m_aiIndex;

    public RenderStringBasic(Formats[] fmts, int[] aiIndex) {
        this.m_aFormats = fmts;
        this.m_aiIndex = aiIndex;
    }

    @Override
    public String getRenderString(Object value) {
        if (value == null) {
            return null;
        }
        Object[] avalue = (Object[])value;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.m_aiIndex.length; ++i) {
            if (i > 0) {
                sb.append(" - ");
            }
            sb.append(this.m_aFormats[this.m_aiIndex[i]].formatValue(avalue[this.m_aiIndex[i]]));
        }
        return sb.toString();
    }
}

