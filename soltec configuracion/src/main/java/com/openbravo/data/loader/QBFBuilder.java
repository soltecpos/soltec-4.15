/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.loader;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.DataWrite;
import com.openbravo.data.loader.DataWriteUtils;
import com.openbravo.data.loader.ISQLBuilderStatic;
import com.openbravo.data.loader.LocalRes;
import com.openbravo.data.loader.QBFCompareEnum;
import com.openbravo.data.loader.SerializerWrite;
import java.util.Date;

public class QBFBuilder
implements ISQLBuilderStatic {
    private final String m_sSentNullFilter;
    private final String m_sSentBeginPart;
    private final String m_sSentEndPart;
    private final String[] m_asFindFields;

    public QBFBuilder(String sSentence, String[] asFindFields) {
        int iPos = sSentence.indexOf("?(QBF_FILTER)");
        if (iPos < 0) {
            this.m_sSentBeginPart = sSentence;
            this.m_sSentEndPart = "";
            this.m_sSentNullFilter = sSentence;
        } else {
            this.m_sSentBeginPart = sSentence.substring(0, iPos);
            this.m_sSentEndPart = sSentence.substring(iPos + 13);
            this.m_sSentNullFilter = this.m_sSentBeginPart + "(1=1)" + this.m_sSentEndPart;
        }
        this.m_asFindFields = asFindFields;
    }

    @Override
    public <T> String getSQL(SerializerWrite<T> sw, T params) throws BasicException {
        QBFParameter mydw = new QBFParameter(this.m_asFindFields);
        if (sw == null || params == null) {
            return this.m_sSentNullFilter;
        }
        sw.writeValues(mydw, params);
        String sFilter = mydw.getFilter();
        if (sFilter.length() == 0) {
            return this.m_sSentNullFilter;
        }
        return this.m_sSentBeginPart + "(" + sFilter + ")" + this.m_sSentEndPart;
    }

    private static class QBFParameter
    implements DataWrite {
        private final String[] m_asFindFields;
        private final QBFCompareEnum[] m_aiCondFields;
        private final String[] m_aParams;

        public QBFParameter(String[] asFindFields) {
            this.m_asFindFields = asFindFields;
            this.m_aiCondFields = new QBFCompareEnum[asFindFields.length];
            this.m_aParams = new String[asFindFields.length];
            for (int i = 0; i < this.m_aParams.length; ++i) {
                this.m_aParams[i] = DataWriteUtils.getSQLValue((String)null);
            }
        }

        @Override
        public void setDouble(int paramIndex, Double dValue) throws BasicException {
            if ((paramIndex - 1) % 2 == 0) {
                throw new BasicException(LocalRes.getIntString("exception.nocompare"));
            }
            this.m_aParams[(paramIndex - 1) / 2] = DataWriteUtils.getSQLValue(dValue);
        }

        @Override
        public void setBoolean(int paramIndex, Boolean bValue) throws BasicException {
            if ((paramIndex - 1) % 2 == 0) {
                throw new BasicException(LocalRes.getIntString("exception.nocompare"));
            }
            this.m_aParams[(paramIndex - 1) / 2] = DataWriteUtils.getSQLValue(bValue);
        }

        @Override
        public void setInt(int paramIndex, Integer iValue) throws BasicException {
            if ((paramIndex - 1) % 2 == 0) {
                throw new BasicException(LocalRes.getIntString("exception.nocompare"));
            }
            this.m_aParams[(paramIndex - 1) / 2] = DataWriteUtils.getSQLValue(iValue);
        }

        @Override
        public void setString(int paramIndex, String sValue) throws BasicException {
            if ((paramIndex - 1) % 2 == 0) {
                throw new BasicException(LocalRes.getIntString("exception.nocompare"));
            }
            this.m_aParams[(paramIndex - 1) / 2] = DataWriteUtils.getSQLValue(sValue);
        }

        @Override
        public void setTimestamp(int paramIndex, Date dValue) throws BasicException {
            if ((paramIndex - 1) % 2 == 0) {
                throw new BasicException(LocalRes.getIntString("exception.nocompare"));
            }
            this.m_aParams[(paramIndex - 1) / 2] = DataWriteUtils.getSQLValue(dValue);
        }

        @Override
        public void setBytes(int paramIndex, byte[] value) throws BasicException {
            if ((paramIndex - 1) % 2 == 0) {
                throw new BasicException(LocalRes.getIntString("exception.nocompare"));
            }
            throw new BasicException("Param type not allowed");
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public void setObject(int paramIndex, Object value) throws BasicException {
            if ((paramIndex - 1) % 2 == 0) {
                if (!(value instanceof QBFCompareEnum)) throw new BasicException(LocalRes.getIntString("exception.nocompare"));
                this.m_aiCondFields[(paramIndex - 1) / 2] = (QBFCompareEnum)value;
                return;
            } else {
                this.m_aParams[(paramIndex - 1) / 2] = DataWriteUtils.getSQLValue(value);
            }
        }

        public String getFilter() {
            StringBuilder sFilter = new StringBuilder();
            for (int i = 0; i < this.m_asFindFields.length; ++i) {
                String sItem = this.m_aiCondFields[i].getExpression(this.m_asFindFields[i], this.m_aParams[i]);
                if (sItem == null) continue;
                if (sFilter.length() > 0) {
                    sFilter.append(" AND ");
                }
                sFilter.append(sItem);
            }
            return sFilter.toString();
        }
    }
}

