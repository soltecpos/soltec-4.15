/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.inventory;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.DataRead;
import com.openbravo.data.loader.Datas;
import com.openbravo.data.loader.SerializerRead;
import com.openbravo.data.loader.StaticSentence;
import com.openbravo.data.model.Field;
import com.openbravo.data.model.Row;
import com.openbravo.data.user.EditorRecord;
import com.openbravo.data.user.ListProviderCreator;
import com.openbravo.format.Formats;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.panels.JPanelTable2;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class JPanelAudit
extends JPanelTable2 {
    private AuditEditor m_editor;

    @Override
    protected void init() {
        this.row = new Row(new Field("Date", Datas.TIMESTAMP, Formats.TIMESTAMP, true, true, true), new Field("Type", Datas.STRING, Formats.STRING, true, true, true), new Field("User", Datas.STRING, Formats.STRING, true, true, true), new Field("Product", Datas.STRING, Formats.STRING, false, false, true), new Field("Qty", Datas.DOUBLE, Formats.DOUBLE, false, false, true), new Field("Old Value", Datas.STRING, Formats.STRING, false, false, true), new Field("New Value", Datas.STRING, Formats.STRING, false, false, true), new Field("Info", Datas.STRING, Formats.STRING, false, false, true));
        this.lpr = new ListProviderCreator(new StaticSentence(this.app.getSession(), "SELECT DATE, TYPE, USER, PRODUCT, QTY, OLD_VALUE, NEW_VALUE, INFO FROM AUDIT_LINES ORDER BY DATE DESC LIMIT 500", null, new SerializerRead(){

            public Object readValues(DataRead dr) throws BasicException {
                return new Object[]{dr.getTimestamp(1), dr.getString(2), dr.getString(3), dr.getString(4), dr.getDouble(5), dr.getString(6), dr.getString(7), dr.getString(8)};
            }
        }));
        this.spr = null;
        this.m_editor = new AuditEditor();
    }

    @Override
    public Component getFilter() {
        return null;
    }

    @Override
    public EditorRecord getEditor() {
        return this.m_editor;
    }

    @Override
    public String getTitle() {
        return AppLocal.getIntString("Menu.Audit");
    }

    private static class AuditEditor
    extends JPanel
    implements EditorRecord {
        private Object[] m_data;
        private JLabel lblDate;
        private JLabel lblType;
        private JLabel lblUser;
        private JLabel lblProduct;
        private JLabel lblQty;
        private JTextArea txtOldValue;
        private JTextArea txtNewValue;
        private JTextArea txtInfo;
        private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        public AuditEditor() {
            this.initComponents();
        }

        private void initComponents() {
            this.setLayout(new BorderLayout(10, 10));
            this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            JPanel topPanel = new JPanel(new GridLayout(5, 2, 10, 5));
            topPanel.setBorder(BorderFactory.createTitledBorder("Detalles del Registro"));
            topPanel.add(new JLabel("Fecha:"));
            this.lblDate = new JLabel("-");
            topPanel.add(this.lblDate);
            topPanel.add(new JLabel("Tipo:"));
            this.lblType = new JLabel("-");
            topPanel.add(this.lblType);
            topPanel.add(new JLabel("Usuario:"));
            this.lblUser = new JLabel("-");
            topPanel.add(this.lblUser);
            topPanel.add(new JLabel("Producto:"));
            this.lblProduct = new JLabel("-");
            topPanel.add(this.lblProduct);
            topPanel.add(new JLabel("Cantidad:"));
            this.lblQty = new JLabel("-");
            topPanel.add(this.lblQty);
            this.add((Component)topPanel, "North");
            JPanel centerPanel = new JPanel(new GridLayout(1, 3, 10, 10));
            JPanel oldPanel = new JPanel(new BorderLayout());
            oldPanel.setBorder(BorderFactory.createTitledBorder("Valor Anterior"));
            this.txtOldValue = new JTextArea(5, 20);
            this.txtOldValue.setEditable(false);
            this.txtOldValue.setLineWrap(true);
            oldPanel.add((Component)new JScrollPane(this.txtOldValue), "Center");
            centerPanel.add(oldPanel);
            JPanel newPanel = new JPanel(new BorderLayout());
            newPanel.setBorder(BorderFactory.createTitledBorder("Valor Nuevo"));
            this.txtNewValue = new JTextArea(5, 20);
            this.txtNewValue.setEditable(false);
            this.txtNewValue.setLineWrap(true);
            newPanel.add((Component)new JScrollPane(this.txtNewValue), "Center");
            centerPanel.add(newPanel);
            JPanel infoPanel = new JPanel(new BorderLayout());
            infoPanel.setBorder(BorderFactory.createTitledBorder("Informaci\u00c3\u00b3n Adicional"));
            this.txtInfo = new JTextArea(5, 20);
            this.txtInfo.setEditable(false);
            this.txtInfo.setLineWrap(true);
            infoPanel.add((Component)new JScrollPane(this.txtInfo), "Center");
            centerPanel.add(infoPanel);
            this.add((Component)centerPanel, "Center");
        }

        private void clearFields() {
            this.lblDate.setText("-");
            this.lblType.setText("-");
            this.lblUser.setText("-");
            this.lblProduct.setText("-");
            this.lblQty.setText("-");
            this.txtOldValue.setText("");
            this.txtNewValue.setText("");
            this.txtInfo.setText("");
        }

        private void displayData() {
            if (this.m_data == null) {
                this.clearFields();
                return;
            }
            Date date = (Date)this.m_data[0];
            this.lblDate.setText(date != null ? this.dateFormat.format(date) : "-");
            this.lblType.setText(this.m_data[1] != null ? this.m_data[1].toString() : "-");
            this.lblUser.setText(this.m_data[2] != null ? this.m_data[2].toString() : "-");
            this.lblProduct.setText(this.m_data[3] != null ? this.m_data[3].toString() : "-");
            this.lblQty.setText(this.m_data[4] != null ? this.m_data[4].toString() : "-");
            this.txtOldValue.setText(this.m_data[5] != null ? this.m_data[5].toString() : "");
            this.txtNewValue.setText(this.m_data[6] != null ? this.m_data[6].toString() : "");
            this.txtInfo.setText(this.m_data[7] != null ? this.m_data[7].toString() : "");
        }

        @Override
        public void writeValueEOF() {
            this.m_data = null;
            this.clearFields();
        }

        @Override
        public void writeValueInsert() {
            this.m_data = null;
            this.clearFields();
        }

        @Override
        public void writeValueDelete(Object value) {
            this.m_data = (Object[])value;
            this.displayData();
        }

        @Override
        public void writeValueEdit(Object value) {
            this.m_data = (Object[])value;
            this.displayData();
        }

        @Override
        public Object createValue() throws BasicException {
            return this.m_data;
        }

        @Override
        public Component getComponent() {
            return this;
        }

        @Override
        public void refresh() {
        }
    }
}

