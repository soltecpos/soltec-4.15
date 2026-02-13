/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.loader;

import com.openbravo.data.loader.SessionDB;
import com.openbravo.data.loader.SessionDBDerby;
import com.openbravo.data.loader.SessionDBGeneric;
import com.openbravo.data.loader.SessionDBHSQLDB;
import com.openbravo.data.loader.SessionDBMySQL;
import com.openbravo.data.loader.SessionDBOracle;
import com.openbravo.data.loader.SessionDBPostgreSQL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Session {
    private final String m_surl;
    private final String m_sappuser;
    private final String m_spassword;
    private Connection m_c;
    private boolean m_bInTransaction;
    public final SessionDB DB;

    public Session(String url, String user, String password) throws SQLException {
        this.m_surl = url;
        this.m_sappuser = user;
        this.m_spassword = password;
        this.m_c = null;
        this.m_bInTransaction = false;
        this.connect();
        this.DB = this.getDiff();
    }

    public void connect() throws SQLException {
        this.close();
        this.m_c = this.m_sappuser == null && this.m_spassword == null ? DriverManager.getConnection(this.m_surl) : DriverManager.getConnection(this.m_surl, this.m_sappuser, this.m_spassword);
        this.m_c.setAutoCommit(true);
        this.m_bInTransaction = false;
    }

    public void close() {
        if (this.m_c != null) {
            try {
                if (this.m_bInTransaction) {
                    this.m_bInTransaction = false;
                    this.m_c.rollback();
                    this.m_c.setAutoCommit(true);
                }
                this.m_c.close();
            }
            catch (SQLException sQLException) {
            }
            finally {
                this.m_c = null;
            }
        }
    }

    public Connection getConnection() throws SQLException {
        if (!this.m_bInTransaction) {
            this.ensureConnection();
        }
        return this.m_c;
    }

    public void begin() throws SQLException {
        if (this.m_bInTransaction) {
            throw new SQLException("Already in transaction");
        }
        this.ensureConnection();
        this.m_c.setAutoCommit(false);
        this.m_bInTransaction = true;
    }

    public void commit() throws SQLException {
        if (!this.m_bInTransaction) {
            throw new SQLException("Transaction not started");
        }
        this.m_bInTransaction = false;
        this.m_c.commit();
        this.m_c.setAutoCommit(true);
    }

    public void rollback() throws SQLException {
        if (!this.m_bInTransaction) {
            throw new SQLException("Transaction not started");
        }
        this.m_bInTransaction = false;
        this.m_c.rollback();
        this.m_c.setAutoCommit(true);
    }

    public boolean isTransaction() {
        return this.m_bInTransaction;
    }

    private void ensureConnection() throws SQLException {
        boolean bclosed;
        try {
            bclosed = this.m_c == null || this.m_c.isClosed();
        }
        catch (SQLException e) {
            bclosed = true;
        }
        if (bclosed) {
            this.connect();
        }
    }

    public String getURL() throws SQLException {
        return this.getConnection().getMetaData().getURL();
    }

    private SessionDB getDiff() throws SQLException {
        String sdbmanager;
        switch (sdbmanager = this.getConnection().getMetaData().getDatabaseProductName()) {
            case "HSQL Database Engine": {
                return new SessionDBHSQLDB();
            }
            case "MySQL": {
                return new SessionDBMySQL();
            }
            case "PostgreSQL": {
                return new SessionDBPostgreSQL();
            }
            case "Oracle": {
                return new SessionDBOracle();
            }
            case "Apache Derby": {
                return new SessionDBDerby();
            }
        }
        return new SessionDBGeneric(sdbmanager);
    }
}

