/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.sf.jasperreports.engine.JRDataSource
 *  net.sf.jasperreports.engine.JRException
 *  net.sf.jasperreports.engine.JRField
 */
package com.openbravo.pos.reports;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.BaseSentence;
import com.openbravo.data.loader.DataResultSet;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.reports.ReportException;
import com.openbravo.pos.reports.ReportFields;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

public class JRDataSourceBasic
implements JRDataSource {
    private BaseSentence<Object, Object> sent;
    private DataResultSet SRS = null;
    private Object current = null;
    private ReportFields m_fields = null;

    public JRDataSourceBasic(BaseSentence<Object, Object> sent, ReportFields fields, Object params) throws BasicException {
        this.sent = sent;
        this.SRS = sent.openExec(params);
        this.m_fields = fields;
    }

    public Object getFieldValue(JRField jrField) throws JRException {
        try {
            return this.m_fields.getField(this.current, jrField.getName());
        }
        catch (ReportException er) {
            throw new JRException((Throwable)er);
        }
    }

    public boolean next() throws JRException {
        if (this.SRS == null) {
            throw new JRException(AppLocal.getIntString("exception.unavailabledataset"));
        }
        try {
            if (this.SRS.next()) {
                this.current = this.SRS.getCurrent();
                return true;
            }
            this.current = null;
            this.SRS = null;
            this.sent.closeExec();
            this.sent = null;
            return false;
        }
        catch (BasicException e) {
            throw new JRException((Throwable)e);
        }
    }
}

