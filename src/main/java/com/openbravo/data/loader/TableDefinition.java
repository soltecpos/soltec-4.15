/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.loader;

import com.openbravo.data.loader.ComparatorCreator;
import com.openbravo.data.loader.ComparatorCreatorBasic;
import com.openbravo.data.loader.Datas;
import com.openbravo.data.loader.IKeyGetter;
import com.openbravo.data.loader.IRenderString;
import com.openbravo.data.loader.KeyGetterBasic;
import com.openbravo.data.loader.KeyGetterFirst;
import com.openbravo.data.loader.PreparedSentence;
import com.openbravo.data.loader.RenderStringBasic;
import com.openbravo.data.loader.SentenceExec;
import com.openbravo.data.loader.SentenceList;
import com.openbravo.data.loader.SerializerRead;
import com.openbravo.data.loader.SerializerReadBasic;
import com.openbravo.data.loader.SerializerWrite;
import com.openbravo.data.loader.SerializerWriteBasicExt;
import com.openbravo.data.loader.Session;
import com.openbravo.data.loader.Vectorer;
import com.openbravo.data.loader.VectorerBasic;
import com.openbravo.format.Formats;

public class TableDefinition {
    private Session m_s;
    private String tablename;
    private String[] fieldname;
    private String[] fieldtran;
    private Datas[] fielddata;
    private Formats[] fieldformat;
    private int[] idinx;

    public TableDefinition(Session s, String tablename, String[] fieldname, String[] fieldtran, Datas[] fielddata, Formats[] fieldformat, int[] idinx) {
        this.m_s = s;
        this.tablename = tablename;
        this.fieldname = fieldname;
        this.fieldtran = fieldtran;
        this.fielddata = fielddata;
        this.fieldformat = fieldformat;
        this.idinx = idinx;
    }

    public TableDefinition(Session s, String tablename, String[] fieldname, Datas[] fielddata, Formats[] fieldformat, int[] idinx) {
        this(s, tablename, fieldname, fieldname, fielddata, fieldformat, idinx);
    }

    public String getTableName() {
        return this.tablename;
    }

    public String[] getFields() {
        return this.fieldname;
    }

    public Vectorer getVectorerBasic(int[] aiFields) {
        return new VectorerBasic(this.fieldtran, this.fieldformat, aiFields);
    }

    public IRenderString getRenderStringBasic(int[] aiFields) {
        return new RenderStringBasic(this.fieldformat, aiFields);
    }

    public ComparatorCreator getComparatorCreator(int[] aiOrders) {
        return new ComparatorCreatorBasic(this.fieldtran, this.fielddata, aiOrders);
    }

    public IKeyGetter getKeyGetterBasic() {
        if (this.idinx.length == 1) {
            return new KeyGetterFirst(this.idinx);
        }
        return new KeyGetterBasic(this.idinx);
    }

    public SerializerRead<Object[]> getSerializerReadBasic() {
        return new SerializerReadBasic(this.fielddata);
    }

    public SerializerWrite<Object[]> getSerializerInsertBasic(int[] fieldindx) {
        return new SerializerWriteBasicExt(this.fielddata, fieldindx);
    }

    public SerializerWrite<Object[]> getSerializerDeleteBasic() {
        return new SerializerWriteBasicExt(this.fielddata, this.idinx);
    }

    public SerializerWrite<Object[]> getSerializerUpdateBasic(int[] fieldindx) {
        int i;
        int[] aindex = new int[fieldindx.length + this.idinx.length];
        for (i = 0; i < fieldindx.length; ++i) {
            aindex[i] = fieldindx[i];
        }
        for (i = 0; i < this.idinx.length; ++i) {
            aindex[i + fieldindx.length] = this.idinx[i];
        }
        return new SerializerWriteBasicExt(this.fielddata, aindex);
    }

    public SentenceList<Object[]> getListSentence() {
        return this.getListSentence(this.getSerializerReadBasic());
    }

    public SentenceList<Object[]> getListSentence(SerializerRead<Object[]> sr) {
        return new PreparedSentence(this.m_s, this.getListSQL(), null, sr);
    }

    public String getListSQL() {
        StringBuilder sent = new StringBuilder();
        sent.append("select ");
        for (int i = 0; i < this.fieldname.length; ++i) {
            if (i > 0) {
                sent.append(", ");
            }
            sent.append(this.fieldname[i]);
        }
        sent.append(" from ");
        sent.append(this.tablename);
        return sent.toString();
    }

    public SentenceExec<Object[]> getDeleteSentence() {
        return this.getDeleteSentence(this.getSerializerDeleteBasic());
    }

    public SentenceExec<Object[]> getDeleteSentence(SerializerWrite<Object[]> sw) {
        return new PreparedSentence(this.m_s, this.getDeleteSQL(), sw, null);
    }

    public String getDeleteSQL() {
        StringBuilder sent = new StringBuilder();
        sent.append("delete from ");
        sent.append(this.tablename);
        for (int i = 0; i < this.idinx.length; ++i) {
            sent.append(i == 0 ? " where " : " and ");
            sent.append(this.fieldname[this.idinx[i]]);
            sent.append(" = ?");
        }
        return sent.toString();
    }

    public SentenceExec<Object[]> getInsertSentence() {
        return this.getInsertSentence(this.getAllFields());
    }

    public SentenceExec<Object[]> getInsertSentence(int[] fieldindx) {
        return new PreparedSentence(this.m_s, this.getInsertSQL(fieldindx), this.getSerializerInsertBasic(fieldindx), null);
    }

    private String getInsertSQL(int[] fieldindx) {
        StringBuilder sent = new StringBuilder();
        StringBuilder values = new StringBuilder();
        sent.append("insert into ");
        sent.append(this.tablename);
        sent.append(" (");
        for (int i = 0; i < fieldindx.length; ++i) {
            if (i > 0) {
                sent.append(", ");
                values.append(", ");
            }
            sent.append(this.fieldname[fieldindx[i]]);
            values.append("?");
        }
        sent.append(") values (");
        sent.append(values.toString());
        sent.append(")");
        return sent.toString();
    }

    private int[] getAllFields() {
        int[] fieldindx = new int[this.fieldname.length];
        for (int i = 0; i < this.fieldname.length; ++i) {
            fieldindx[i] = i;
        }
        return fieldindx;
    }

    public SentenceExec<Object[]> getUpdateSentence() {
        return this.getUpdateSentence(this.getAllFields());
    }

    public SentenceExec<Object[]> getUpdateSentence(int[] fieldindx) {
        return new PreparedSentence(this.m_s, this.getUpdateSQL(fieldindx), this.getSerializerUpdateBasic(fieldindx), null);
    }

    private String getUpdateSQL(int[] fieldindx) {
        int i;
        StringBuilder sent = new StringBuilder();
        sent.append("update ");
        sent.append(this.tablename);
        sent.append(" set ");
        for (i = 0; i < fieldindx.length; ++i) {
            if (i > 0) {
                sent.append(", ");
            }
            sent.append(this.fieldname[fieldindx[i]]);
            sent.append(" = ?");
        }
        for (i = 0; i < this.idinx.length; ++i) {
            sent.append(i == 0 ? " where " : " and ");
            sent.append(this.fieldname[this.idinx[i]]);
            sent.append(" = ?");
        }
        return sent.toString();
    }
}

