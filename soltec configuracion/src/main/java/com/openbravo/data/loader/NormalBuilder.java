/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.loader;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.DataWrite;
import com.openbravo.data.loader.DataWriteUtils;
import com.openbravo.data.loader.ISQLBuilderStatic;
import com.openbravo.data.loader.LocalRes;
import com.openbravo.data.loader.SerializerWrite;
import java.util.ArrayList;
import java.util.Date;

public class NormalBuilder
implements ISQLBuilderStatic {
    private String m_sSentence;

    public NormalBuilder(String sSentence) {
        this.m_sSentence = sSentence;
    }

    @Override
    public <T> String getSQL(SerializerWrite<T> sw, T params) throws BasicException {
        NormalParameter mydw = new NormalParameter(this.m_sSentence);
        if (sw != null) {
            sw.writeValues(mydw, params);
        }
        return mydw.getSentence();
    }

    private static class NormalParameter
    implements DataWrite {
        private String m_sSentence;
        private ArrayList<String> m_aParams;

        public NormalParameter(String sSentence) {
            this.m_sSentence = sSentence;
            this.m_aParams = new ArrayList();
        }

        @Override
        public void setDouble(int paramIndex, Double dValue) throws BasicException {
            this.ensurePlace(paramIndex - 1);
            this.m_aParams.set(paramIndex - 1, DataWriteUtils.getSQLValue(dValue));
        }

        @Override
        public void setBoolean(int paramIndex, Boolean bValue) throws BasicException {
            this.ensurePlace(paramIndex - 1);
            this.m_aParams.set(paramIndex - 1, DataWriteUtils.getSQLValue(bValue));
        }

        @Override
        public void setInt(int paramIndex, Integer iValue) throws BasicException {
            this.ensurePlace(paramIndex - 1);
            this.m_aParams.set(paramIndex - 1, DataWriteUtils.getSQLValue(iValue));
        }

        @Override
        public void setString(int paramIndex, String sValue) throws BasicException {
            this.ensurePlace(paramIndex - 1);
            this.m_aParams.set(paramIndex - 1, DataWriteUtils.getSQLValue(sValue));
        }

        @Override
        public void setTimestamp(int paramIndex, Date dValue) throws BasicException {
            this.ensurePlace(paramIndex - 1);
            this.m_aParams.set(paramIndex - 1, DataWriteUtils.getSQLValue(dValue));
        }

        @Override
        public void setBytes(int paramIndex, byte[] value) throws BasicException {
            throw new BasicException(LocalRes.getIntString("exception.noparamtype"));
        }

        @Override
        public void setObject(int paramIndex, Object value) throws BasicException {
            this.ensurePlace(paramIndex - 1);
            this.m_aParams.set(paramIndex - 1, DataWriteUtils.getSQLValue(value));
        }

        private void ensurePlace(int i) {
            this.m_aParams.ensureCapacity(i);
            while (i >= this.m_aParams.size()) {
                this.m_aParams.add(null);
            }
        }

        public String getSentence() {
            int iPos;
            StringBuilder sNewSentence = new StringBuilder();
            int iCount = 0;
            int iLast = 0;
            while ((iPos = this.m_sSentence.indexOf(63, iLast)) > 0) {
                sNewSentence.append(this.m_sSentence.substring(iLast, iPos));
                if (iCount < this.m_aParams.size() && this.m_aParams.get(iCount) != null) {
                    sNewSentence.append(this.m_aParams.get(iCount));
                } else {
                    sNewSentence.append(DataWriteUtils.getSQLValue((String)null));
                }
                ++iCount;
                iLast = iPos + 1;
            }
            sNewSentence.append(this.m_sSentence.substring(iLast));
            return sNewSentence.toString();
        }
    }
}

