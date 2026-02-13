/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.panels;

import com.openbravo.basic.BasicException;
import com.openbravo.data.gui.MessageInf;
import com.openbravo.data.loader.LocalRes;
import com.openbravo.data.user.BrowsableEditableData;
import com.openbravo.data.user.BrowseListener;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;

public class JTableNavigator<T>
extends JPanel
implements BrowseListener,
ListSelectionListener {
    protected BrowsableEditableData<T> m_bd;
    protected JTable m_table;
    private NavigatorTableModel m_model;
    private String[] m_columnNames;
    private int[] m_columnIndices;

    public JTableNavigator(BrowsableEditableData<T> bd, String[] columnNames, int[] columnIndices) {
        this.m_bd = bd;
        this.m_columnNames = columnNames;
        this.m_columnIndices = columnIndices;
        this.m_model = new NavigatorTableModel();
        this.initComponents();
        this.m_table.setModel(this.m_model);
        this.m_table.setSelectionMode(0);
        this.m_table.getSelectionModel().addListSelectionListener(this);
        this.m_table.setRowSorter(new TableRowSorter<NavigatorTableModel>(this.m_model));
        this.m_bd.addBrowseListener(this);
    }

    private void initComponents() {
        this.m_table = new JTable();
        JScrollPane scroll = new JScrollPane(this.m_table);
        scroll.getVerticalScrollBar().setPreferredSize(new Dimension(35, 35));
        scroll.getHorizontalScrollBar().setPreferredSize(new Dimension(35, 35));
        this.setLayout(new BorderLayout());
        this.add((Component)scroll, "Center");
        this.setPreferredSize(new Dimension(500, 2));
    }

    public JTable getTable() {
        return this.m_table;
    }

    @Override
    public void updateIndex(int iIndex, int iCounter) {
        if (iIndex >= 0 && iIndex < iCounter) {
            try {
                int viewIndex = this.m_table.convertRowIndexToView(iIndex);
                this.m_table.setRowSelectionInterval(viewIndex, viewIndex);
                Rectangle rect = this.m_table.getCellRect(viewIndex, 0, true);
                this.m_table.scrollRectToVisible(rect);
            }
            catch (IllegalArgumentException | IndexOutOfBoundsException runtimeException) {}
        } else {
            this.m_table.clearSelection();
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent evt) {
        int viewIndex;
        if (!evt.getValueIsAdjusting() && (viewIndex = this.m_table.getSelectedRow()) >= 0) {
            int modelIndex = this.m_table.convertRowIndexToModel(viewIndex);
            if (!this.m_bd.isAdjusting() && modelIndex != this.m_bd.getIndex()) {
                try {
                    this.m_bd.moveTo(modelIndex);
                }
                catch (BasicException eD) {
                    MessageInf msg = new MessageInf(-67108864, LocalRes.getIntString("message.nomove"), eD);
                    msg.show(this);
                }
            }
        }
    }

    private class NavigatorTableModel
    extends AbstractTableModel {
        public NavigatorTableModel() {
            JTableNavigator.this.m_bd.getListModel().addListDataListener(new ListDataListener(){

                @Override
                public void intervalAdded(ListDataEvent e) {
                    NavigatorTableModel.this.fireTableDataChanged();
                }

                @Override
                public void intervalRemoved(ListDataEvent e) {
                    NavigatorTableModel.this.fireTableDataChanged();
                }

                @Override
                public void contentsChanged(ListDataEvent e) {
                    NavigatorTableModel.this.fireTableDataChanged();
                }
            });
        }

        @Override
        public int getRowCount() {
            return JTableNavigator.this.m_bd.getListModel().getSize();
        }

        @Override
        public int getColumnCount() {
            return JTableNavigator.this.m_columnNames.length;
        }

        @Override
        public String getColumnName(int column) {
            return JTableNavigator.this.m_columnNames[column];
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Object[] data = (Object[])JTableNavigator.this.m_bd.getListModel().getElementAt(rowIndex);
            return data[JTableNavigator.this.m_columnIndices[columnIndex]];
        }
    }
}

