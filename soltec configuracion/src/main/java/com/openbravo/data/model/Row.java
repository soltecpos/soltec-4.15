/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.model;

import com.openbravo.basic.BasicException;
import com.openbravo.data.gui.ListCellRendererBasic;
import com.openbravo.data.loader.ComparatorCreator;
import com.openbravo.data.loader.DataRead;
import com.openbravo.data.loader.DataWrite;
import com.openbravo.data.loader.Datas;
import com.openbravo.data.loader.IRenderString;
import com.openbravo.data.loader.PreparedSentence;
import com.openbravo.data.loader.SentenceExec;
import com.openbravo.data.loader.SentenceList;
import com.openbravo.data.loader.SerializerRead;
import com.openbravo.data.loader.SerializerWrite;
import com.openbravo.data.loader.Session;
import com.openbravo.data.loader.Vectorer;
import com.openbravo.data.model.Field;
import com.openbravo.data.model.Table;
import com.openbravo.data.user.FilterEditorCreator;
import com.openbravo.data.user.ListProvider;
import com.openbravo.data.user.ListProviderCreator;
import com.openbravo.data.user.SaveProvider;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.swing.ListCellRenderer;

public class Row {
    private Field[] fields;

    public Row(Field ... fields) {
        this.fields = fields;
    }

    public Vectorer getVectorer() {
        return new RowVectorer();
    }

    public IRenderString getRenderString() {
        return new RowRenderString();
    }

    public ListCellRenderer getListCellRenderer() {
        return new ListCellRendererBasic(new RowRenderString());
    }

    public ComparatorCreator getComparatorCreator() {
        return new RowComparatorCreator();
    }

    public SentenceExec<Object[]> getExecSentence(Session s, String sql, final int ... indexes) {
        return new PreparedSentence(s, sql, new SerializerWrite<Object[]>(){

            @Override
            public void writeValues(DataWrite dp, Object[] obj) throws BasicException {
                for (int i = 0; i < indexes.length; ++i) {
                    Row.this.fields[indexes[i]].getData().setValue(dp, i + 1, obj[indexes[i]]);
                }
            }
        });
    }

    public ListProvider getListProvider(Session s, Table t) {
        return new ListProviderCreator<Object[]>(this.getListSentence(s, t));
    }

    public SaveProvider<Object[]> getSaveProvider(Session s, Table t) {
        return new SaveProvider<Object[]>(this.getUpdateSentence(s, t), this.getInsertSentence(s, t), this.getDeleteSentence(s, t));
    }

    public SentenceList<Object[]> getListSentence(Session s, String sql, SerializerWrite sw) {
        return new PreparedSentence(s, sql, sw, new RowSerializerRead());
    }

    public ListProvider getListProvider(Session s, String sql, FilterEditorCreator filter) {
        return new ListProviderCreator<Object[]>(this.getListSentence(s, sql, filter.getSerializerWrite()), filter);
    }

    public SentenceList<Object[]> getListSentence(Session s, Table t) {
        return this.getListSentence(s, t.getListSQL(), null);
    }

    public SentenceExec<Object[]> getInsertSentence(Session s, final Table t) {
        return new PreparedSentence(s, t.getInsertSQL(), new SerializerWrite<Object[]>(){

            @Override
            public void writeValues(DataWrite dp, Object[] obj) throws BasicException {
                for (int i = 0; i < t.getColumns().length; ++i) {
                    Row.this.fields[i].getData().setValue(dp, i + 1, obj[i]);
                }
            }
        });
    }

    public SentenceExec<Object[]> getDeleteSentence(Session s, final Table t) {
        return new PreparedSentence(s, t.getDeleteSQL(), new SerializerWrite<Object[]>(){

            @Override
            public void writeValues(DataWrite dp, Object[] obj) throws BasicException {
                int index = 1;
                for (int i = 0; i < t.getColumns().length; ++i) {
                    if (!t.getColumns()[i].isPK()) continue;
                    Row.this.fields[i].getData().setValue(dp, index++, obj[i]);
                }
            }
        });
    }

    public SentenceExec<Object[]> getUpdateSentence(Session s, final Table t) {
        return new PreparedSentence(s, t.getUpdateSQL(), new SerializerWrite<Object[]>(){

            @Override
            public void writeValues(DataWrite dp, Object[] obj) throws BasicException {
                int i;
                int index = 1;
                for (i = 0; i < t.getColumns().length; ++i) {
                    if (t.getColumns()[i].isPK()) continue;
                    Row.this.fields[i].getData().setValue(dp, index++, obj[i]);
                }
                for (i = 0; i < t.getColumns().length; ++i) {
                    if (!t.getColumns()[i].isPK()) continue;
                    Row.this.fields[i].getData().setValue(dp, index++, obj[i]);
                }
            }
        });
    }

    public Datas[] getDatas() {
        Datas[] d = new Datas[this.fields.length];
        for (int i = 0; i < this.fields.length; ++i) {
            d[i] = this.fields[i].getData();
        }
        return d;
    }

    public SerializerRead<Object[]> getSerializerRead() {
        return new RowSerializerRead();
    }

    private class RowVectorer
    implements Vectorer {
        private RowVectorer() {
        }

        @Override
        public String[] getHeaders() throws BasicException {
            ArrayList<String> l = new ArrayList<String>();
            for (Field f : Row.this.fields) {
                if (!f.isSearchable()) continue;
                l.add(f.getLabel());
            }
            return l.toArray(new String[l.size()]);
        }

        @Override
        public String[] getValues(Object obj) throws BasicException {
            Object[] values = (Object[])obj;
            ArrayList<String> l = new ArrayList<String>();
            for (int i = 0; i < Row.this.fields.length; ++i) {
                if (!Row.this.fields[i].isSearchable()) continue;
                l.add(Row.this.fields[i].getFormat().formatValue(values[i]));
            }
            return l.toArray(new String[l.size()]);
        }
    }

    private class RowRenderString
    implements IRenderString {
        private RowRenderString() {
        }

        @Override
        public String getRenderString(Object value) {
            Object[] values = (Object[])value;
            StringBuilder s = new StringBuilder();
            for (int i = 0; i < Row.this.fields.length; ++i) {
                if (!Row.this.fields[i].isTitle()) continue;
                if (s.length() > 0) {
                    s.append(" - ");
                }
                s.append(Row.this.fields[i].getFormat().formatValue(values[i]));
            }
            return s.toString();
        }
    }

    private class RowComparatorCreator
    implements ComparatorCreator {
        private List<Integer> comparablefields = new ArrayList<Integer>();

        public RowComparatorCreator() {
            for (int i = 0; i < Row.this.fields.length; ++i) {
                if (!Row.this.fields[i].isComparable()) continue;
                this.comparablefields.add(i);
            }
        }

        @Override
        public String[] getHeaders() {
            String[] headers = new String[this.comparablefields.size()];
            for (int i = 0; i < this.comparablefields.size(); ++i) {
                headers[i] = Row.this.fields[this.comparablefields.get(i)].getLabel();
            }
            return headers;
        }

        @Override
        public Comparator<?> createComparator(final int[] orderby) {
            return new Comparator<Object>(){

                @Override
                public int compare(Object o1, Object o2) {
                    if (o1 == null) {
                        if (o2 == null) {
                            return 0;
                        }
                        return -1;
                    }
                    if (o2 == null) {
                        return 1;
                    }
                    Object[] ao1 = (Object[])o1;
                    Object[] ao2 = (Object[])o2;
                    for (int i = 0; i < orderby.length; ++i) {
                        int result = Row.this.fields[(Integer)RowComparatorCreator.this.comparablefields.get(orderby[i])].getData().compare(ao1[(Integer)RowComparatorCreator.this.comparablefields.get(orderby[i])], ao2[(Integer)RowComparatorCreator.this.comparablefields.get(orderby[i])]);
                        if (result == 0) continue;
                        return result;
                    }
                    return 0;
                }
            };
        }
    }

    private class RowSerializerRead
    implements SerializerRead<Object[]> {
        private RowSerializerRead() {
        }

        @Override
        public Object[] readValues(DataRead dr) throws BasicException {
            Object[] m_values = new Object[Row.this.fields.length];
            for (int i = 0; i < Row.this.fields.length; ++i) {
                m_values[i] = Row.this.fields[i].getData().getValue(dr, i + 1);
            }
            return m_values;
        }
    }
}

