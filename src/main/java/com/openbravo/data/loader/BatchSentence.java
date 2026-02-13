/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.loader;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.BaseSentence;
import com.openbravo.data.loader.DataField;
import com.openbravo.data.loader.DataResultSet;
import com.openbravo.data.loader.DataWrite;
import com.openbravo.data.loader.ImageUtils;
import com.openbravo.data.loader.LocalRes;
import com.openbravo.data.loader.PreparedSentence;
import com.openbravo.data.loader.SerializableWrite;
import com.openbravo.data.loader.SerializerWriteBuilder;
import com.openbravo.data.loader.Session;
import com.openbravo.data.loader.StaticSentence;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class BatchSentence
extends BaseSentence<Object, Object> {
    protected Session m_s;
    protected HashMap<String, String> m_parameters;

    public BatchSentence(Session s) {
        this.m_s = s;
        this.m_parameters = new HashMap();
    }

    public void putParameter(String name, String replacement) {
        this.m_parameters.put(name, replacement);
    }

    protected abstract Reader getReader() throws BasicException;

    @Override
    public final void closeExec() throws BasicException {
    }

    @Override
    public final DataResultSet moreResults() throws BasicException {
        return null;
    }

    @Override
    public DataResultSet openExec(Object params) throws BasicException {
        BufferedReader br = new BufferedReader(this.getReader());
        StringBuffer sSentence = new StringBuffer();
        ArrayList<BasicException> aExceptions = new ArrayList<BasicException>();
        try {
            String sLine;
            while ((sLine = br.readLine()) != null) {
                if ((sLine = sLine.trim()).equals("") || sLine.startsWith("--")) continue;
                if (sLine.endsWith(";")) {
                    sSentence.append(sLine.substring(0, sLine.length() - 1));
                    Pattern pattern = Pattern.compile("\\$(\\w+)\\{([^}]*)\\}");
                    Matcher matcher = pattern.matcher(sSentence.toString());
                    ArrayList<Object> paramlist = new ArrayList<Object>();
                    StringBuffer buf = new StringBuffer();
                    while (matcher.find()) {
                        if ("FILE".equals(matcher.group(1))) {
                            paramlist.add(ImageUtils.getBytesFromResource(matcher.group(2)));
                            matcher.appendReplacement(buf, "?");
                            continue;
                        }
                        String replacement = this.m_parameters.get(matcher.group(1));
                        if (replacement == null) {
                            matcher.appendReplacement(buf, Matcher.quoteReplacement(matcher.group(0)));
                            continue;
                        }
                        paramlist.add(replacement);
                        matcher.appendReplacement(buf, "?");
                    }
                    matcher.appendTail(buf);
                    try {
                        if (paramlist.isEmpty()) {
                            new StaticSentence(this.m_s, buf.toString()).exec();
                        } else {
                            new PreparedSentence(this.m_s, buf.toString(), SerializerWriteBuilder.INSTANCE).exec(new VarParams(paramlist));
                        }
                    }
                    catch (BasicException eD) {
                        aExceptions.add(eD);
                    }
                    sSentence = new StringBuffer();
                    continue;
                }
                sSentence.append(sLine);
            }
            br.close();
        }
        catch (IOException eIO) {
            throw new BasicException(LocalRes.getIntString("exception.noreadfile"), eIO);
        }
        if (sSentence.length() > 0) {
            aExceptions.add(new BasicException(LocalRes.getIntString("exception.nofinishedfile")));
        }
        return new ExceptionsResultSet(aExceptions);
    }

    private static class VarParams
    implements SerializableWrite {
        private List<Object> l;

        public VarParams(List<Object> l) {
            this.l = l;
        }

        @Override
        public void writeValues(DataWrite dp) throws BasicException {
            for (int i = 0; i < this.l.size(); ++i) {
                Object v = this.l.get(i);
                if (v instanceof String) {
                    dp.setString(i + 1, (String)v);
                    continue;
                }
                if (v instanceof byte[]) {
                    dp.setBytes(i + 1, (byte[])this.l.get(i));
                    continue;
                }
                dp.setObject(i + 1, v);
            }
        }
    }

    public class ExceptionsResultSet
    implements DataResultSet {
        List<BasicException> l;
        int m_iIndex;

        public ExceptionsResultSet(List<BasicException> l) {
            this.l = l;
            this.m_iIndex = -1;
        }

        @Override
        public Integer getInt(int columnIndex) throws BasicException {
            throw new BasicException(LocalRes.getIntString("exception.nodataset"));
        }

        @Override
        public String getString(int columnIndex) throws BasicException {
            throw new BasicException(LocalRes.getIntString("exception.nodataset"));
        }

        @Override
        public Double getDouble(int columnIndex) throws BasicException {
            throw new BasicException(LocalRes.getIntString("exception.nodataset"));
        }

        @Override
        public Boolean getBoolean(int columnIndex) throws BasicException {
            throw new BasicException(LocalRes.getIntString("exception.nodataset"));
        }

        @Override
        public Date getTimestamp(int columnIndex) throws BasicException {
            throw new BasicException(LocalRes.getIntString("exception.nodataset"));
        }

        @Override
        public byte[] getBytes(int columnIndex) throws BasicException {
            throw new BasicException(LocalRes.getIntString("exception.nodataset"));
        }

        @Override
        public Object getObject(int columnIndex) throws BasicException {
            throw new BasicException(LocalRes.getIntString("exception.nodataset"));
        }

        @Override
        public DataField[] getDataField() throws BasicException {
            throw new BasicException(LocalRes.getIntString("exception.nodataset"));
        }

        @Override
        public Object getCurrent() throws BasicException {
            if (this.m_iIndex < 0 || this.m_iIndex >= this.l.size()) {
                throw new BasicException(LocalRes.getIntString("exception.outofbounds"));
            }
            return this.l.get(this.m_iIndex);
        }

        @Override
        public boolean next() throws BasicException {
            return ++this.m_iIndex < this.l.size();
        }

        @Override
        public void close() throws BasicException {
        }

        @Override
        public int updateCount() {
            return 0;
        }
    }
}

