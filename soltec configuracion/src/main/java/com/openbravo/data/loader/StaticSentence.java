/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.loader;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.DataResultSet;
import com.openbravo.data.loader.ISQLBuilderStatic;
import com.openbravo.data.loader.JDBCSentence;
import com.openbravo.data.loader.NormalBuilder;
import com.openbravo.data.loader.SentenceUpdateResultSet;
import com.openbravo.data.loader.SerializerRead;
import com.openbravo.data.loader.SerializerWrite;
import com.openbravo.data.loader.Session;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StaticSentence<P, R>
extends JDBCSentence<P, R> {
    private static final Logger logger = Logger.getLogger("com.openbravo.data.loader.StaticSentence");
    private ISQLBuilderStatic m_sentence;
    protected SerializerWrite<P> m_SerWrite = null;
    protected SerializerRead<R> m_SerRead = null;
    private Statement m_Stmt;

    public StaticSentence(Session s, ISQLBuilderStatic sentence, SerializerWrite<P> serwrite, SerializerRead<R> serread) {
        super(s);
        this.m_sentence = sentence;
        this.m_SerWrite = serwrite;
        this.m_SerRead = serread;
        this.m_Stmt = null;
    }

    public StaticSentence(Session s, ISQLBuilderStatic sentence) {
        this(s, sentence, null, null);
    }

    public StaticSentence(Session s, ISQLBuilderStatic sentence, SerializerWrite<P> serwrite) {
        this(s, sentence, serwrite, null);
    }

    public StaticSentence(Session s, String sentence, SerializerWrite<P> serwrite, SerializerRead<R> serread) {
        this(s, new NormalBuilder(sentence), serwrite, serread);
    }

    public StaticSentence(Session s, String sentence, SerializerWrite<P> serwrite) {
        this(s, new NormalBuilder(sentence), serwrite, null);
    }

    public StaticSentence(Session s, String sentence) {
        this(s, new NormalBuilder(sentence), null, null);
    }

    @Override
    public DataResultSet openExec(P params) throws BasicException {
        this.closeExec();
        try {
            this.m_Stmt = this.m_s.getConnection().createStatement();
            String sentence = this.m_sentence.getSQL(this.m_SerWrite, params);
            logger.log(Level.INFO, "Executing static SQL: {0}", sentence);
            if (this.m_Stmt.execute(sentence)) {
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
    public void closeExec() throws BasicException {
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

    @Override
    public DataResultSet moreResults() throws BasicException {
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
}

