/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.loader;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.DataWrite;
import com.openbravo.data.loader.SerializerWrite;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SerializerWriteComposed
implements SerializerWrite {
    private List<SerializerWrite> serwrites = new ArrayList<SerializerWrite>();

    public void add(SerializerWrite sw) {
        this.serwrites.add(sw);
    }

    public void writeValues(DataWrite dp, Object obj) throws BasicException {
        Object[] a = (Object[])obj;
        DataWriteComposed dpc = new DataWriteComposed(dp);
        int i = 0;
        for (SerializerWrite sw : this.serwrites) {
            dpc.next();
            sw.writeValues(dpc, a[i++]);
        }
    }

    private static class DataWriteComposed
    implements DataWrite {
        private DataWrite dp;
        private int offset = 0;
        private int max = 0;

        public DataWriteComposed(DataWrite dp) {
            this.dp = dp;
        }

        public void next() {
            this.offset = this.max;
        }

        @Override
        public void setInt(int paramIndex, Integer iValue) throws BasicException {
            this.dp.setInt(this.offset + paramIndex, iValue);
            this.max = Math.max(this.max, this.offset + paramIndex);
        }

        @Override
        public void setString(int paramIndex, String sValue) throws BasicException {
            this.dp.setString(this.offset + paramIndex, sValue);
            this.max = Math.max(this.max, this.offset + paramIndex);
        }

        @Override
        public void setDouble(int paramIndex, Double dValue) throws BasicException {
            this.dp.setDouble(this.offset + paramIndex, dValue);
            this.max = Math.max(this.max, this.offset + paramIndex);
        }

        @Override
        public void setBoolean(int paramIndex, Boolean bValue) throws BasicException {
            this.dp.setBoolean(this.offset + paramIndex, bValue);
            this.max = Math.max(this.max, this.offset + paramIndex);
        }

        @Override
        public void setTimestamp(int paramIndex, Date dValue) throws BasicException {
            this.dp.setTimestamp(this.offset + paramIndex, dValue);
            this.max = Math.max(this.max, this.offset + paramIndex);
        }

        @Override
        public void setBytes(int paramIndex, byte[] value) throws BasicException {
            this.dp.setBytes(this.offset + paramIndex, value);
            this.max = Math.max(this.max, this.offset + paramIndex);
        }

        @Override
        public void setObject(int paramIndex, Object value) throws BasicException {
            this.dp.setObject(this.offset + paramIndex, value);
            this.max = Math.max(this.max, this.offset + paramIndex);
        }
    }
}

