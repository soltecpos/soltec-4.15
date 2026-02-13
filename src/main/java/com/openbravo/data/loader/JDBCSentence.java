/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.loader;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.BaseSentence;
import com.openbravo.data.loader.DataField;
import com.openbravo.data.loader.DataResultSet;
import com.openbravo.data.loader.SerializerRead;
import com.openbravo.data.loader.Session;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

public abstract class JDBCSentence<P, R>
extends BaseSentence<P, R> {
    protected Session m_s;

    public JDBCSentence(Session s) {
        this.m_s = s;
    }

    protected static final class JDBCDataResultSet<R>
    implements DataResultSet {
        private ResultSet m_rs;
        private SerializerRead<R> m_serread;

        public JDBCDataResultSet(ResultSet rs, SerializerRead<R> serread) {
            this.m_rs = rs;
            this.m_serread = serread;
        }

        @Override
        public Integer getInt(int columnIndex) throws BasicException {
            try {
                int iValue = this.m_rs.getInt(columnIndex);
                return this.m_rs.wasNull() ? null : Integer.valueOf(iValue);
            }
            catch (SQLException eSQL) {
                throw new BasicException(eSQL);
            }
        }

        @Override
        public String getString(int columnIndex) throws BasicException {
            try {
                return this.m_rs.getString(columnIndex);
            }
            catch (SQLException eSQL) {
                throw new BasicException(eSQL);
            }
        }

        @Override
        public Double getDouble(int columnIndex) throws BasicException {
            try {
                double dValue = this.m_rs.getDouble(columnIndex);
                return this.m_rs.wasNull() ? null : Double.valueOf(dValue);
            }
            catch (SQLException eSQL) {
                throw new BasicException(eSQL);
            }
        }

        @Override
        public Boolean getBoolean(int columnIndex) throws BasicException {
            try {
                boolean bValue = this.m_rs.getBoolean(columnIndex);
                return this.m_rs.wasNull() ? null : Boolean.valueOf(bValue);
            }
            catch (SQLException eSQL) {
                throw new BasicException(eSQL);
            }
        }

        @Override
        public Date getTimestamp(int columnIndex) throws BasicException {
            try {
                Timestamp ts = this.m_rs.getTimestamp(columnIndex);
                return ts == null ? null : new Date(ts.getTime());
            }
            catch (SQLException eSQL) {
                throw new BasicException(eSQL);
            }
        }

        @Override
        public byte[] getBytes(int columnIndex) throws BasicException {
            try {
                return this.m_rs.getBytes(columnIndex);
            }
            catch (SQLException eSQL) {
                throw new BasicException(eSQL);
            }
        }

        @Override
        public Object getObject(int columnIndex) throws BasicException {
            try {
                return this.m_rs.getObject(columnIndex);
            }
            catch (SQLException eSQL) {
                throw new BasicException(eSQL);
            }
        }

        @Override
        public DataField[] getDataField() throws BasicException {
            try {
                ResultSetMetaData md = this.m_rs.getMetaData();
                DataField[] df = new DataField[md.getColumnCount()];
                for (int i = 0; i < df.length; ++i) {
                    df[i] = new DataField();
                    df[i].Name = md.getColumnName(i + 1);
                    df[i].Size = md.getColumnDisplaySize(i + 1);
                    df[i].Type = md.getColumnType(i + 1);
                }
                return df;
            }
            catch (SQLException eSQL) {
                throw new BasicException(eSQL);
            }
        }

        @Override
        public Object getCurrent() throws BasicException {
            return this.m_serread.readValues(this);
        }

        @Override
        public boolean next() throws BasicException {
            try {
                return this.m_rs.next();
            }
            catch (SQLException eSQL) {
                throw new BasicException(eSQL);
            }
        }

        @Override
        public void close() throws BasicException {
            try {
                this.m_rs.close();
            }
            catch (SQLException eSQL) {
                throw new BasicException(eSQL);
            }
        }

        @Override
        public int updateCount() throws BasicException {
            return -1;
        }
    }
}

