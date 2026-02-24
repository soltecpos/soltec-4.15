/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.panels;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.DataField;
import com.openbravo.data.loader.DataRead;
import com.openbravo.data.loader.Datas;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class SQLTableModel
extends AbstractTableModel {
    private List m_aRows = new ArrayList();
    private DataField[] m_df;
    private Datas[] m_classes;

    public SQLTableModel(DataField[] df) {
        this.m_df = df;
        this.m_classes = new Datas[df.length];
        block8: for (int i = 0; i < df.length; ++i) {
            switch (df[i].Type) {
                case -6: 
                case -5: 
                case 4: 
                case 5: {
                    this.m_classes[i] = Datas.INT;
                    continue block8;
                }
                case -7: 
                case 16: {
                    this.m_classes[i] = Datas.BOOLEAN;
                    continue block8;
                }
                case 2: 
                case 3: 
                case 6: 
                case 7: 
                case 8: {
                    this.m_classes[i] = Datas.DOUBLE;
                    continue block8;
                }
                case -1: 
                case 1: 
                case 12: 
                case 2005: {
                    this.m_classes[i] = Datas.STRING;
                    continue block8;
                }
                case 91: 
                case 92: 
                case 93: {
                    this.m_classes[i] = Datas.TIMESTAMP;
                    continue block8;
                }
                case -4: 
                case -3: 
                case -2: 
                case 2004: {
                    this.m_classes[i] = Datas.BYTES;
                    continue block8;
                }
                default: {
                    this.m_classes[i] = Datas.OBJECT;
                }
            }
        }
    }

    public void addRow(DataRead dr) throws BasicException {
        Object[] m_values = new Object[this.m_classes.length];
        for (int i = 0; i < this.m_classes.length; ++i) {
            m_values[i] = this.m_classes[i].getValue(dr, i + 1);
        }
        this.m_aRows.add(m_values);
    }

    public String getColumnString(int row) {
        Object[] rowvalues = (Object[])this.m_aRows.get(row);
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < rowvalues.length; ++i) {
            if (i > 0) {
                s.append(", ");
            }
            s.append(this.m_classes[i].toString(rowvalues[i]));
        }
        return s.toString();
    }

    public Class getColumnClass(int columnIndex) {
        return this.m_classes[columnIndex].getClassValue();
    }

    @Override
    public String getColumnName(int columnIndex) {
        return this.m_df[columnIndex].Name;
    }

    @Override
    public int getRowCount() {
        return this.m_aRows.size();
    }

    @Override
    public int getColumnCount() {
        return this.m_df.length;
    }

    @Override
    public Object getValueAt(int row, int column) {
        Object[] rowvalues = (Object[])this.m_aRows.get(row);
        return rowvalues[column];
    }
}

