/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.loader;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.DataResultSet;
import com.openbravo.data.loader.DataWrite;
import com.openbravo.data.loader.JDBCSentence;
import com.openbravo.data.loader.SentenceUpdateResultSet;
import com.openbravo.data.loader.SerializerRead;
import com.openbravo.data.loader.SerializerWrite;
import com.openbravo.data.loader.Session;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PreparedSentence<P, R>
extends JDBCSentence<P, R> {
    private static final Logger logger = Logger.getLogger("com.openbravo.data.loader.PreparedSentence");
    private String m_sentence;
    protected SerializerWrite<P> m_SerWrite = null;
    protected SerializerRead<R> m_SerRead = null;
    private PreparedStatement m_Stmt;

    public PreparedSentence(Session s, String sentence, SerializerWrite<P> serwrite, SerializerRead<R> serread) {
        super(s);
        this.m_sentence = sentence;
        this.m_SerWrite = serwrite;
        this.m_SerRead = serread;
        this.m_Stmt = null;
    }

    public PreparedSentence(Session s, String sentence, SerializerWrite<P> serwrite) {
        this(s, sentence, serwrite, null);
    }

    public PreparedSentence(Session s, String sentence) {
        this(s, sentence, null, null);
    }

    @Override
    public DataResultSet openExec(P params) throws BasicException {
        this.closeExec();
        try {
            logger.log(Level.INFO, "Executing prepared SQL: {0}", this.m_sentence);
            this.m_Stmt = this.m_s.getConnection().prepareStatement(this.m_sentence);
            if (this.m_SerWrite != null) {
                this.m_SerWrite.writeValues(new PreparedSentencePars(this.m_Stmt), params);
            }
            if (this.m_Stmt.execute()) {
                return new JDBCSentence.JDBCDataResultSet<R>(this.m_Stmt.getResultSet(), this.m_SerRead);
            }
            int iUC = this.m_Stmt.getUpdateCount();
            if (iUC < 0) {
                return null;
            }
            return new SentenceUpdateResultSet(iUC);
        }
        catch (SQLException eSQL) {
            throw new BasicException(eSQL);
        }
    }

    @Override
    public final DataResultSet moreResults() throws BasicException {
        try {
            if (this.m_Stmt.getMoreResults()) {
                return new JDBCSentence.JDBCDataResultSet<R>(this.m_Stmt.getResultSet(), this.m_SerRead);
            }
            int iUC = this.m_Stmt.getUpdateCount();
            if (iUC < 0) {
                return null;
            }
            return new SentenceUpdateResultSet(iUC);
        }
        catch (SQLException eSQL) {
            throw new BasicException(eSQL);
        }
    }

    @Override
    public final void closeExec() throws BasicException {
        if (this.m_Stmt != null) {
            try {
                this.m_Stmt.close();
            }
            catch (SQLException eSQL) {
                throw new BasicException(eSQL);
            }
            finally {
                this.m_Stmt = null;
            }
        }
    }

    private static final class PreparedSentencePars
    implements DataWrite {
        private PreparedStatement m_ps;

        PreparedSentencePars(PreparedStatement ps) {
            this.m_ps = ps;
        }

        @Override
        public void setInt(int paramIndex, Integer iValue) throws BasicException {
            try {
                this.m_ps.setObject(paramIndex, (Object)iValue, 4);
            }
            catch (SQLException eSQL) {
                throw new BasicException(eSQL);
            }
        }

        @Override
        public void setString(int paramIndex, String sValue) throws BasicException {
            try {
                this.m_ps.setString(paramIndex, sValue);
            }
            catch (SQLException eSQL) {
                throw new BasicException(eSQL);
            }
        }

        @Override
        public void setDouble(int paramIndex, Double dValue) throws BasicException {
            try {
                this.m_ps.setObject(paramIndex, (Object)dValue, 8);
            }
            catch (SQLException eSQL) {
                throw new BasicException(eSQL);
            }
        }

        @Override
        public void setBoolean(int paramIndex, Boolean bValue) throws BasicException {
            try {
                if (bValue == null) {
                    this.m_ps.setObject(paramIndex, null);
                } else {
                    this.m_ps.setBoolean(paramIndex, bValue);
                }
            }
            catch (SQLException eSQL) {
                throw new BasicException(eSQL);
            }
        }

        @Override
        public void setTimestamp(int paramIndex, Date dValue) throws BasicException {
            try {
                this.m_ps.setObject(paramIndex, (Object)(dValue == null ? null : new Timestamp(dValue.getTime())), 93);
            }
            catch (SQLException eSQL) {
                throw new BasicException(eSQL);
            }
        }

        @Override
        public void setBytes(int paramIndex, byte[] value) throws BasicException {
            try {
                this.m_ps.setBytes(paramIndex, value);
            }
            catch (SQLException eSQL) {
                throw new BasicException(eSQL);
            }
        }

        @Override
        public void setObject(int paramIndex, Object value) throws BasicException {
            try {
                this.m_ps.setObject(paramIndex, value);
            }
            catch (SQLException eSQL) {
                throw new BasicException(eSQL);
            }
        }
    }
}

