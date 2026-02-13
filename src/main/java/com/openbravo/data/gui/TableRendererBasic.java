/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.gui;

import com.openbravo.format.Formats;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class TableRendererBasic
extends DefaultTableCellRenderer {
    private Formats[] m_aFormats;

    public TableRendererBasic(Formats[] aFormats) {
        this.m_aFormats = aFormats;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JLabel aux = (JLabel)super.getTableCellRendererComponent(table, null, isSelected, hasFocus, row, column);
        aux.setText(this.m_aFormats[column].formatValue(value));
        aux.setHorizontalAlignment(this.m_aFormats[column].getAlignment());
        return aux;
    }
}

