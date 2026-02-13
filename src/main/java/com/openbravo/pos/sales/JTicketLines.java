/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.sales;

import com.openbravo.data.loader.LocalRes;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.scripting.ScriptEngine;
import com.openbravo.pos.scripting.ScriptException;
import com.openbravo.pos.scripting.ScriptFactory;
import com.openbravo.pos.ticket.TicketLineInfo;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class JTicketLines
extends JPanel {
    private static final Logger logger = Logger.getLogger("com.openbravo.pos.sales.JTicketLines");
    private static SAXParser m_sp = null;
    private final TicketTableModel m_jTableModel;
    private Boolean sendStatus;
    private JScrollPane m_jScrollTableTicket;
    private JTable m_jTicketTable;

    public JTicketLines(String ticketline) {
        this.initComponents();
        ColumnTicket[] acolumns = new ColumnTicket[]{};
        if (ticketline != null) {
            try {
                if (m_sp == null) {
                    SAXParserFactory spf = SAXParserFactory.newInstance();
                    m_sp = spf.newSAXParser();
                }
                ColumnsHandler columnshandler = new ColumnsHandler();
                m_sp.parse(new InputSource(new StringReader(ticketline)), (DefaultHandler)columnshandler);
                acolumns = columnshandler.getColumns();
            }
            catch (ParserConfigurationException ePC) {
                logger.log(Level.WARNING, LocalRes.getIntString("exception.parserconfig"), ePC);
            }
            catch (SAXException eSAX) {
                logger.log(Level.WARNING, LocalRes.getIntString("exception.xmlfile"), eSAX);
            }
            catch (IOException eIO) {
                logger.log(Level.WARNING, LocalRes.getIntString("exception.iofile"), eIO);
            }
        }
        this.m_jTableModel = new TicketTableModel(acolumns);
        this.m_jTicketTable.setModel(this.m_jTableModel);
        TableColumnModel jColumns = this.m_jTicketTable.getColumnModel();
        for (int i = 0; i < acolumns.length; ++i) {
            jColumns.getColumn(i).setPreferredWidth(acolumns[i].width);
            jColumns.getColumn(i).setResizable(false);
        }
        this.m_jScrollTableTicket.getVerticalScrollBar().setPreferredSize(new Dimension(35, 35));
        this.m_jTicketTable.getTableHeader().setReorderingAllowed(false);
        this.m_jTicketTable.setDefaultRenderer(Object.class, new TicketCellRenderer(acolumns));
        this.m_jTicketTable.setRowHeight(50);
        this.m_jTicketTable.getSelectionModel().setSelectionMode(0);
        this.m_jTableModel.clear();
    }

    public void addListSelectionListener(ListSelectionListener l) {
        this.m_jTicketTable.getSelectionModel().addListSelectionListener(l);
    }

    public void removeListSelectionListener(ListSelectionListener l) {
        this.m_jTicketTable.getSelectionModel().removeListSelectionListener(l);
    }

    public void clearTicketLines() {
        this.m_jTableModel.clear();
    }

    public void setTicketLine(int index, TicketLineInfo oLine) {
        this.m_jTableModel.setRow(index, oLine);
    }

    public void addTicketLine(TicketLineInfo oLine) {
        this.m_jTableModel.addRow(oLine);
        this.setSelectedIndex(this.m_jTableModel.getRowCount() - 1);
    }

    public void insertTicketLine(int index, TicketLineInfo oLine) {
        this.m_jTableModel.insertRow(index, oLine);
        this.setSelectedIndex(index);
    }

    public void removeTicketLine(int i) {
        this.m_jTableModel.removeRow(i);
        if (i >= this.m_jTableModel.getRowCount()) {
            i = this.m_jTableModel.getRowCount() - 1;
        }
        if (i >= 0 && i < this.m_jTableModel.getRowCount()) {
            this.setSelectedIndex(i);
        }
    }

    public void setSelectedIndex(int i) {
        this.m_jTicketTable.getSelectionModel().setSelectionInterval(i, i);
        Rectangle oRect = this.m_jTicketTable.getCellRect(i, 0, true);
        this.m_jTicketTable.scrollRectToVisible(oRect);
    }

    public int getSelectedIndex() {
        return this.m_jTicketTable.getSelectionModel().getMinSelectionIndex();
    }

    public void selectionDown() {
        int i = this.m_jTicketTable.getSelectionModel().getMaxSelectionIndex();
        if (i < 0) {
            i = 0;
        } else if (++i >= this.m_jTableModel.getRowCount()) {
            i = this.m_jTableModel.getRowCount() - 1;
        }
        if (i >= 0 && i < this.m_jTableModel.getRowCount()) {
            this.setSelectedIndex(i);
        }
    }

    public void selectionUp() {
        int i = this.m_jTicketTable.getSelectionModel().getMinSelectionIndex();
        if (i < 0) {
            i = this.m_jTableModel.getRowCount() - 1;
        } else if (--i < 0) {
            i = 0;
        }
        if (i >= 0 && i < this.m_jTableModel.getRowCount()) {
            this.setSelectedIndex(i);
        }
    }

    public void setSendStatus(Boolean state) {
        this.sendStatus = state;
    }

    private void initComponents() {
        this.m_jScrollTableTicket = new JScrollPane();
        this.m_jTicketTable = new JTable();
        this.setLayout(new BorderLayout());
        this.m_jScrollTableTicket.setHorizontalScrollBarPolicy(31);
        this.m_jScrollTableTicket.setVerticalScrollBarPolicy(22);
        this.m_jScrollTableTicket.setFont(new Font("Arial", 0, 12));
        this.m_jTicketTable.setFont(new Font("Arial", 0, 14));
        this.m_jTicketTable.setFocusable(false);
        this.m_jTicketTable.setIntercellSpacing(new Dimension(0, 1));
        this.m_jTicketTable.setRequestFocusEnabled(false);
        this.m_jTicketTable.setShowVerticalLines(false);
        this.m_jScrollTableTicket.setViewportView(this.m_jTicketTable);
        this.add((Component)this.m_jScrollTableTicket, "Center");
    }

    private static class ColumnTicket {
        public String name;
        public int width;
        public int align;
        public String value;

        private ColumnTicket() {
        }
    }

    private static class ColumnsHandler
    extends DefaultHandler {
        private ArrayList m_columns = null;

        private ColumnsHandler() {
        }

        public ColumnTicket[] getColumns() {
            return (ColumnTicket[]) this.m_columns.toArray(new ColumnTicket[this.m_columns.size()]);
        }

        @Override
        public void startDocument() throws SAXException {
            this.m_columns = new ArrayList();
        }

        @Override
        public void endDocument() throws SAXException {
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            if ("column".equals(qName)) {
                String sAlign;
                ColumnTicket c = new ColumnTicket();
                c.name = attributes.getValue("name");
                c.width = Integer.parseInt(attributes.getValue("width"));
                switch (sAlign = attributes.getValue("align")) {
                    case "right": {
                        c.align = 4;
                        break;
                    }
                    case "center": {
                        c.align = 0;
                        break;
                    }
                    default: {
                        c.align = 2;
                    }
                }
                c.value = attributes.getValue("value");
                this.m_columns.add(c);
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
        }
    }

    private static class TicketTableModel
    extends AbstractTableModel {
        private final ColumnTicket[] m_acolumns;
        private final ArrayList m_rows = new ArrayList();

        public TicketTableModel(ColumnTicket[] acolumns) {
            this.m_acolumns = acolumns;
        }

        @Override
        public int getRowCount() {
            return this.m_rows.size();
        }

        @Override
        public int getColumnCount() {
            return this.m_acolumns.length;
        }

        @Override
        public String getColumnName(int column) {
            return AppLocal.getIntString(this.m_acolumns[column].name);
        }

        @Override
        public Object getValueAt(int row, int column) {
            return ((String[])this.m_rows.get(row))[column];
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

        public void setRow(int index, TicketLineInfo oLine) {
            String[] row = (String[])this.m_rows.get(index);
            for (int i = 0; i < this.m_acolumns.length; ++i) {
                try {
                    ScriptEngine script = ScriptFactory.getScriptEngine("velocity");
                    script.put("ticketline", oLine);
                    row[i] = script.eval(this.m_acolumns[i].value).toString();
                }
                catch (ScriptException e) {
                    row[i] = null;
                }
                this.fireTableCellUpdated(index, i);
            }
        }

        public void addRow(TicketLineInfo oLine) {
            this.insertRow(this.m_rows.size(), oLine);
        }

        public void insertRow(int index, TicketLineInfo oLine) {
            String[] row = new String[this.m_acolumns.length];
            for (int i = 0; i < this.m_acolumns.length; ++i) {
                try {
                    ScriptEngine script = ScriptFactory.getScriptEngine("velocity");
                    script.put("ticketline", oLine);
                    row[i] = script.eval(this.m_acolumns[i].value).toString();
                    continue;
                }
                catch (ScriptException e) {
                    row[i] = null;
                }
            }
            this.m_rows.add(index, row);
            this.fireTableRowsInserted(index, index);
        }

        public void removeRow(int row) {
            this.m_rows.remove(row);
            this.fireTableRowsDeleted(row, row);
        }
    }

    private static class TicketCellRenderer
    extends DefaultTableCellRenderer {
        private final ColumnTicket[] m_acolumns;

        public TicketCellRenderer(ColumnTicket[] acolumns) {
            this.m_acolumns = acolumns;
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel aux = (JLabel)super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            aux.setVerticalAlignment(0);
            aux.setHorizontalAlignment(this.m_acolumns[column].align);
            aux.setFont(new Font("Segoe UI", 0, 18));
            return aux;
        }
    }

    private static class TicketCellRendererSent
    extends DefaultTableCellRenderer {
        private final ColumnTicket[] m_acolumns;

        public TicketCellRendererSent(ColumnTicket[] acolumns) {
            this.m_acolumns = acolumns;
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel aux = (JLabel)super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            aux.setVerticalAlignment(0);
            aux.setHorizontalAlignment(this.m_acolumns[column].align);
            aux.setFont(new Font("Segoe UI", 1, 18));
            aux.setBackground(Color.yellow);
            return aux;
        }
    }
}

