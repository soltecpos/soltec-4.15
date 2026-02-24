/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.inventory;

import com.openbravo.format.Formats;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.inventory.InventoryLine;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultCellEditor;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

public class JInventoryLines
extends JPanel {
    private InventoryTableModel m_inventorylines;
    private JScrollPane jScrollPane1;
    private JTable m_tableinventory;

    public JInventoryLines() {
        this.initComponents();
        DefaultTableColumnModel columns = new DefaultTableColumnModel();
        TableColumn c = new TableColumn(0, 150, new DataCellRenderer(2), new DefaultCellEditor(new JTextField()));
        c.setHeaderValue(AppLocal.getIntString("label.item"));
        columns.addColumn(c);
        c = new TableColumn(1, 75, new DataCellRenderer(4), new DefaultCellEditor(new JTextField()));
        c.setHeaderValue(AppLocal.getIntString("label.units2"));
        columns.addColumn(c);
        c = new TableColumn(2, 100, new DataCellRenderer(4), new DefaultCellEditor(new JTextField()));
        c.setHeaderValue(AppLocal.getIntString("label.price"));
        columns.addColumn(c);
        c = new TableColumn(3, 100, new DataCellRenderer(4), new DefaultCellEditor(new JTextField()));
        c.setHeaderValue(AppLocal.getIntString("label.prodvaluebuy"));
        columns.addColumn(c);
        this.m_tableinventory.setColumnModel(columns);
        this.m_tableinventory.getTableHeader().setReorderingAllowed(false);
        this.m_tableinventory.setRowHeight(30);
        this.m_tableinventory.getSelectionModel().setSelectionMode(0);
        this.m_tableinventory.setIntercellSpacing(new Dimension(0, 1));
        this.m_inventorylines = new InventoryTableModel();
        this.m_tableinventory.setModel(this.m_inventorylines);
    }

    public void clear() {
        this.m_inventorylines.clear();
    }

    public void addLine(InventoryLine i) {
        this.m_inventorylines.addRow(i);
        this.setSelectedIndex(this.m_inventorylines.getRowCount() - 1);
    }

    public void deleteLine(int index) {
        this.m_inventorylines.removeRow(index);
        if (index >= this.m_inventorylines.getRowCount()) {
            index = this.m_inventorylines.getRowCount() - 1;
        }
        if (index >= 0 && index < this.m_inventorylines.getRowCount()) {
            this.setSelectedIndex(index);
        }
    }

    public void setLine(int index, InventoryLine i) {
        this.m_inventorylines.setRow(index, i);
        this.setSelectedIndex(index);
    }

    public InventoryLine getLine(int index) {
        return this.m_inventorylines.getRow(index);
    }

    public List<InventoryLine> getLines() {
        return this.m_inventorylines.getLines();
    }

    public int getCount() {
        return this.m_inventorylines.getRowCount();
    }

    public int getSelectedRow() {
        return this.m_tableinventory.getSelectedRow();
    }

    public void setSelectedIndex(int i) {
        this.m_tableinventory.getSelectionModel().setSelectionInterval(i, i);
        Rectangle oRect = this.m_tableinventory.getCellRect(i, 0, true);
        this.m_tableinventory.scrollRectToVisible(oRect);
    }

    public void goDown() {
        int i = this.m_tableinventory.getSelectionModel().getMaxSelectionIndex();
        if (i < 0) {
            i = 0;
        } else if (++i >= this.m_inventorylines.getRowCount()) {
            i = this.m_inventorylines.getRowCount() - 1;
        }
        if (i >= 0 && i < this.m_inventorylines.getRowCount()) {
            this.setSelectedIndex(i);
        }
    }

    public void goUp() {
        int i = this.m_tableinventory.getSelectionModel().getMinSelectionIndex();
        if (i < 0) {
            i = this.m_inventorylines.getRowCount() - 1;
        } else if (--i < 0) {
            i = 0;
        }
        if (i >= 0 && i < this.m_inventorylines.getRowCount()) {
            this.setSelectedIndex(i);
        }
    }

    private void initComponents() {
        this.jScrollPane1 = new JScrollPane();
        this.m_tableinventory = new JTable();
        this.setFont(new Font("Arial", 0, 14));
        this.setLayout(new BorderLayout());
        this.jScrollPane1.setFont(new Font("Arial", 0, 12));
        this.jScrollPane1.setPreferredSize(new Dimension(455, 402));
        this.m_tableinventory.setAutoCreateColumnsFromModel(false);
        this.m_tableinventory.setFont(new Font("Arial", 0, 14));
        this.m_tableinventory.setAutoResizeMode(0);
        this.m_tableinventory.setFocusable(false);
        this.m_tableinventory.setRequestFocusEnabled(false);
        this.m_tableinventory.setSelectionBackground(new Color(0, 153, 204));
        this.m_tableinventory.setShowVerticalLines(false);
        this.m_tableinventory.addMouseListener(new MouseAdapter(){

            @Override
            public void mousePressed(MouseEvent evt) {
                JInventoryLines.this.m_tableinventoryMousePressed(evt);
            }
        });
        this.jScrollPane1.setViewportView(this.m_tableinventory);
        this.add((Component)this.jScrollPane1, "Center");
    }

    private void m_tableinventoryMousePressed(MouseEvent evt) {
    }

    private static class DataCellRenderer
    extends DefaultTableCellRenderer {
        private int m_iAlignment;

        public DataCellRenderer(int align) {
            this.m_iAlignment = align;
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel aux = (JLabel)super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            aux.setVerticalAlignment(1);
            aux.setHorizontalAlignment(this.m_iAlignment);
            if (!isSelected) {
                aux.setBackground(UIManager.getDefaults().getColor("TextField.disabledBackground"));
            }
            return aux;
        }
    }

    private static class InventoryTableModel
    extends AbstractTableModel {
        private final ArrayList<InventoryLine> m_rows = new ArrayList();

        private InventoryTableModel() {
        }

        @Override
        public int getRowCount() {
            return this.m_rows.size();
        }

        @Override
        public int getColumnCount() {
            return 4;
        }

        @Override
        public String getColumnName(int column) {
            return "a";
        }

        @Override
        public Object getValueAt(int row, int column) {
            InventoryLine i = this.m_rows.get(row);
            switch (column) {
                case 0: {
                    return "<html>" + i.getProductName() + (i.getProductAttSetInstDesc() == null ? "" : "<br>" + i.getProductAttSetInstDesc());
                }
                case 1: {
                    return "x" + Formats.DOUBLE.formatValue(i.getMultiply());
                }
                case 2: {
                    return Formats.CURRENCY.formatValue(i.getPrice());
                }
                case 3: {
                    return Formats.CURRENCY.formatValue(i.getSubValue());
                }
            }
            return null;
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }

        public void clear() {
            int old = this.getRowCount();
            if (old > 0) {
                this.m_rows.clear();
                this.fireTableRowsDeleted(0, old - 1);
            }
        }

        public List<InventoryLine> getLines() {
            return this.m_rows;
        }

        public InventoryLine getRow(int index) {
            return this.m_rows.get(index);
        }

        public void setRow(int index, InventoryLine oLine) {
            this.m_rows.set(index, oLine);
            this.fireTableRowsUpdated(index, index);
        }

        public void addRow(InventoryLine oLine) {
            this.insertRow(this.m_rows.size(), oLine);
        }

        public void insertRow(int index, InventoryLine oLine) {
            this.m_rows.add(index, oLine);
            this.fireTableRowsInserted(index, index);
        }

        public void removeRow(int row) {
            this.m_rows.remove(row);
            this.fireTableRowsDeleted(row, row);
        }
    }
}

