/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.loader;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.DataWrite;
import com.openbravo.data.loader.SerializableWrite;
import com.openbravo.data.loader.Vectorer;
import java.util.ArrayList;
import java.util.Date;

public abstract class VectorerBuilder
implements Vectorer {
    @Override
    public abstract String[] getHeaders() throws BasicException;

    @Override
    public String[] getValues(Object obj) throws BasicException {
        SerializableToArray s2a = new SerializableToArray();
        ((SerializableWrite)obj).writeValues(s2a);
        return s2a.getValues();
    }

    private static class SerializableToArray
    implements DataWrite {
        private ArrayList<String> m_aParams = new ArrayList();

        @Override
        public void setDouble(int paramIndex, Double dValue) throws BasicException {
            this.ensurePlace(paramIndex - 1);
            this.m_aParams.set(paramIndex - 1, dValue.toString());
        }

        @Override
        public void setBoolean(int paramIndex, Boolean bValue) throws BasicException {
            this.ensurePlace(paramIndex - 1);
            this.m_aParams.set(paramIndex - 1, bValue.toString());
        }

        @Override
        public void setInt(int paramIndex, Integer iValue) throws BasicException {
            this.ensurePlace(paramIndex - 1);
            this.m_aParams.set(paramIndex - 1, iValue.toString());
        }

        @Override
        public void setString(int paramIndex, String sValue) throws BasicException {
            this.ensurePlace(paramIndex - 1);
            this.m_aParams.set(paramIndex - 1, sValue);
        }

        @Override
        public void setTimestamp(int paramIndex, Date dValue) throws BasicException {
            this.ensurePlace(paramIndex - 1);
            this.m_aParams.set(paramIndex - 1, dValue.toString());
        }

        @Override
        public void setBytes(int paramIndex, byte[] value) throws BasicException {
            this.ensurePlace(paramIndex - 1);
            this.m_aParams.set(paramIndex - 1, value.toString());
        }

        @Override
        public void setObject(int paramIndex, Object value) throws BasicException {
            this.ensurePlace(paramIndex - 1);
            this.m_aParams.set(paramIndex - 1, value.toString());
        }

        private void ensurePlace(int i) {
            this.m_aParams.ensureCapacity(i);
            while (i >= this.m_aParams.size()) {
                this.m_aParams.add(null);
            }
        }

        public String[] getValues() {
            return this.m_aParams.toArray(new String[this.m_aParams.size()]);
        }
    }
}

