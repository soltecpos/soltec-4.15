/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.inventory;

import com.openbravo.basic.BasicException;
import com.openbravo.data.user.BrowsableData;
import com.openbravo.format.Formats;
import javax.swing.table.AbstractTableModel;

public class StockModel
extends AbstractTableModel {
    private BrowsableData m_bd;
    private Formats[] m_formats;
    private boolean[] m_bedit;

    public StockModel(BrowsableData bd, Formats[] f, boolean[] bedit) {
        this.m_bd = bd;
        this.m_formats = f;
        this.m_bedit = bedit;
    }

    @Override
    public int getRowCount() {
        return this.m_bd.getSize();
    }

    @Override
    public int getColumnCount() {
        return this.m_formats.length;
    }

    @Override
    public Object getValueAt(int row, int column) {
        return this.m_formats[column].formatValue(((Object[])this.m_bd.getElementAt(row))[column]);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return this.m_bedit[column];
    }

    @Override
    public void setValueAt(Object aValue, int row, int column) {
        Object[] record = (Object[])this.m_bd.getElementAt(row);
        try {
            record[column] = this.m_formats[column].parseValue((String)aValue);
            this.m_bd.updateRecord(row, record);
        }
        catch (BasicException basicException) {
            // empty catch block
        }
    }
}

