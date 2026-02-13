/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.reports;

import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.reports.ReportException;
import com.openbravo.pos.reports.ReportFields;
import java.util.HashMap;
import java.util.Map;

public class ReportFieldsArray
implements ReportFields {
    private Map m_keys = new HashMap();

    public ReportFieldsArray(String[] afields) {
        for (int i = 0; i < afields.length; ++i) {
            this.m_keys.put(afields[i], new Integer(i));
        }
    }

    @Override
    public Object getField(Object record, String field) throws ReportException {
        Integer i = (Integer)this.m_keys.get(field);
        if (i == null) {
            throw new ReportException(AppLocal.getIntString("exception.unavailablefield", field));
        }
        Object[] arecord = (Object[])record;
        if (arecord == null || i < 0 || i >= arecord.length) {
            throw new ReportException(AppLocal.getIntString("exception.unavailablefields"));
        }
        return arecord[i];
    }
}

