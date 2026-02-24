/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.loader;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.DataResultSet;
import com.openbravo.data.loader.DataWrite;
import com.openbravo.data.loader.JDBCSentence;
import com.openbravo.data.loader.LocalRes;
import com.openbravo.data.loader.SerializerRead;
import com.openbravo.data.loader.SerializerWrite;
import com.openbravo.data.loader.Session;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class MetaSentence<R>
extends JDBCSentence<Object, R> {
    private String m_sSentence;
    protected SerializerRead<R> m_SerRead = null;
    protected SerializerWrite<Object> m_SerWrite = null;

    public MetaSentence(Session s, String sSentence, SerializerWrite<Object> serwrite, SerializerRead<R> serread) {
        super(s);
        this.m_sSentence = sSentence;
        this.m_SerWrite = serwrite;
        this.m_SerRead = serread;
    }

    public MetaSentence(Session s, String sSentence, SerializerRead<R> serread) {
        this(s, sSentence, null, serread);
    }

    @Override
    public DataResultSet openExec(Object params) throws BasicException {
        this.closeExec();
        try {
            DatabaseMetaData db = this.m_s.getConnection().getMetaData();
            MetaParameter mp = new MetaParameter();
            if (params != null) {
                this.m_SerWrite.writeValues(mp, params);
            }
            if ("getCatalogs".equals(this.m_sSentence)) {
                return new JDBCSentence.JDBCDataResultSet<R>(db.getCatalogs(), this.m_SerRead);
            }
            if ("getSchemas".equals(this.m_sSentence)) {
                return new JDBCSentence.JDBCDataResultSet<R>(db.getSchemas(), this.m_SerRead);
            }
            if ("getTableTypes".equals(this.m_sSentence)) {
                return new JDBCSentence.JDBCDataResultSet<R>(db.getTableTypes(), this.m_SerRead);
            }
            if ("getTypeInfo".equals(this.m_sSentence)) {
                return new JDBCSentence.JDBCDataResultSet<R>(db.getTypeInfo(), this.m_SerRead);
            }
            if ("getUDTs".equals(this.m_sSentence)) {
                return new JDBCSentence.JDBCDataResultSet<R>(db.getUDTs(mp.getString(0), mp.getString(1), null, null), this.m_SerRead);
            }
            if ("getSuperTypes".equals(this.m_sSentence)) {
                return new JDBCSentence.JDBCDataResultSet<R>(db.getSuperTypes(mp.getString(0), mp.getString(1), mp.getString(2)), this.m_SerRead);
            }
            if ("getAttributes".equals(this.m_sSentence)) {
                return new JDBCSentence.JDBCDataResultSet<R>(db.getAttributes(mp.getString(0), mp.getString(1), null, null), this.m_SerRead);
            }
            if ("getTables".equals(this.m_sSentence)) {
                return new JDBCSentence.JDBCDataResultSet<R>(db.getTables(mp.getString(0), mp.getString(1), null, null), this.m_SerRead);
            }
            if ("getSuperTables".equals(this.m_sSentence)) {
                return new JDBCSentence.JDBCDataResultSet<R>(db.getSuperTables(mp.getString(0), mp.getString(1), mp.getString(2)), this.m_SerRead);
            }
            if ("getTablePrivileges".equals(this.m_sSentence)) {
                return new JDBCSentence.JDBCDataResultSet<R>(db.getTablePrivileges(mp.getString(0), mp.getString(1), mp.getString(2)), this.m_SerRead);
            }
            if ("getBestRowIdentifier".equals(this.m_sSentence)) {
                return new JDBCSentence.JDBCDataResultSet<R>(db.getBestRowIdentifier(mp.getString(0), mp.getString(1), mp.getString(2), 0, true), this.m_SerRead);
            }
            if ("getPrimaryKeys".equals(this.m_sSentence)) {
                return new JDBCSentence.JDBCDataResultSet<R>(db.getPrimaryKeys(mp.getString(0), mp.getString(1), mp.getString(2)), this.m_SerRead);
            }
            if ("getColumnPrivileges".equals(this.m_sSentence)) {
                return new JDBCSentence.JDBCDataResultSet<R>(db.getColumnPrivileges(mp.getString(0), mp.getString(1), mp.getString(2), null), this.m_SerRead);
            }
            if ("getColumns".equals(this.m_sSentence)) {
                return new JDBCSentence.JDBCDataResultSet<R>(db.getColumns(mp.getString(0), mp.getString(1), mp.getString(2), null), this.m_SerRead);
            }
            if ("getVersionColumns".equals(this.m_sSentence)) {
                return new JDBCSentence.JDBCDataResultSet<R>(db.getVersionColumns(mp.getString(0), mp.getString(1), mp.getString(2)), this.m_SerRead);
            }
            if ("getIndexInfo".equals(this.m_sSentence)) {
                return new JDBCSentence.JDBCDataResultSet<R>(db.getIndexInfo(mp.getString(0), mp.getString(1), mp.getString(2), false, false), this.m_SerRead);
            }
            if ("getExportedKeys".equals(this.m_sSentence)) {
                return new JDBCSentence.JDBCDataResultSet<R>(db.getExportedKeys(mp.getString(0), mp.getString(1), mp.getString(2)), this.m_SerRead);
            }
            if ("getImportedKeys".equals(this.m_sSentence)) {
                return new JDBCSentence.JDBCDataResultSet<R>(db.getImportedKeys(mp.getString(0), mp.getString(1), mp.getString(2)), this.m_SerRead);
            }
            if ("getCrossReference".equals(this.m_sSentence)) {
                return new JDBCSentence.JDBCDataResultSet<R>(db.getCrossReference(mp.getString(0), mp.getString(1), mp.getString(2), null, null, null), this.m_SerRead);
            }
            if ("getProcedures".equals(this.m_sSentence)) {
                return new JDBCSentence.JDBCDataResultSet<R>(db.getProcedures(mp.getString(0), mp.getString(1), null), this.m_SerRead);
            }
            if ("getProcedureColumns".equals(this.m_sSentence)) {
                return new JDBCSentence.JDBCDataResultSet<R>(db.getProcedureColumns(mp.getString(0), mp.getString(1), mp.getString(2), null), this.m_SerRead);
            }
            return null;
        }
        catch (SQLException eSQL) {
            throw new BasicException(eSQL);
        }
    }

    @Override
    public void closeExec() throws BasicException {
    }

    @Override
    public DataResultSet moreResults() throws BasicException {
        return null;
    }

    private static class MetaParameter
    implements DataWrite {
        private ArrayList<String> m_aParams = new ArrayList();

        @Override
        public void setDouble(int paramIndex, Double dValue) throws BasicException {
            throw new BasicException(LocalRes.getIntString("exception.noparamtype"));
        }

        @Override
        public void setBoolean(int paramIndex, Boolean bValue) throws BasicException {
            throw new BasicException(LocalRes.getIntString("exception.noparamtype"));
        }

        @Override
        public void setInt(int paramIndex, Integer iValue) throws BasicException {
            throw new BasicException(LocalRes.getIntString("exception.noparamtype"));
        }

        @Override
        public void setString(int paramIndex, String sValue) throws BasicException {
            this.ensurePlace(paramIndex - 1);
            this.m_aParams.set(paramIndex - 1, sValue);
        }

        @Override
        public void setTimestamp(int paramIndex, Date dValue) throws BasicException {
            throw new BasicException(LocalRes.getIntString("exception.noparamtype"));
        }

        @Override
        public void setBytes(int paramIndex, byte[] value) throws BasicException {
            throw new BasicException(LocalRes.getIntString("exception.noparamtype"));
        }

        @Override
        public void setObject(int paramIndex, Object value) throws BasicException {
            this.setString(paramIndex, value == null ? null : value.toString());
        }

        public String getString(int index) {
            return this.m_aParams.get(index);
        }

        private void ensurePlace(int i) {
            this.m_aParams.ensureCapacity(i);
            while (i >= this.m_aParams.size()) {
                this.m_aParams.add(null);
            }
        }
    }
}

