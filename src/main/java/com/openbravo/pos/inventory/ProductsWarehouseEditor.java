/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.inventory;

import com.openbravo.basic.BasicException;
import com.openbravo.data.user.DirtyManager;
import com.openbravo.data.user.EditorRecord;
import com.openbravo.format.Formats;
import com.openbravo.pos.forms.AppLocal;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;

public class ProductsWarehouseEditor
extends JPanel
implements EditorRecord {
    public Object id;
    public Object prodid;
    public Object prodref;
    public Object prodname;
    public Object location;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JTextField m_jMaximum;
    private JTextField m_jMinimum;
    private JTextField m_jQuantity;
    private JLabel m_jTitle;

    public ProductsWarehouseEditor(DirtyManager dirty) {
        this.initComponents();
        this.m_jMinimum.getDocument().addDocumentListener(dirty);
        this.m_jMaximum.getDocument().addDocumentListener(dirty);
    }

    public ProductsWarehouseEditor() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void writeValueEOF() {
        this.m_jTitle.setText(AppLocal.getIntString("label.recordeof"));
        this.id = null;
        this.prodid = null;
        this.prodref = null;
        this.prodname = null;
        this.location = null;
        this.m_jQuantity.setText(null);
        this.m_jMinimum.setText(null);
        this.m_jMaximum.setText(null);
        this.m_jMinimum.setEnabled(false);
        this.m_jMaximum.setEnabled(false);
    }

    @Override
    public void writeValueInsert() {
        this.m_jTitle.setText(AppLocal.getIntString("label.recordnew"));
        this.id = null;
        this.prodid = null;
        this.prodref = null;
        this.prodname = null;
        this.location = null;
        this.m_jQuantity.setText(null);
        this.m_jMinimum.setText(null);
        this.m_jMaximum.setText(null);
        this.m_jMinimum.setEnabled(true);
        this.m_jMaximum.setEnabled(true);
    }

    @Override
    public void writeValueEdit(Object value) {
        Object[] myprod = (Object[])value;
        this.id = myprod[0];
        this.prodid = myprod[1];
        this.prodref = myprod[2];
        this.prodname = myprod[3];
        this.location = myprod[4];
        this.m_jTitle.setText(Formats.STRING.formatValue(myprod[2]) + " - " + Formats.STRING.formatValue(myprod[3]));
        this.m_jQuantity.setText(Formats.DOUBLE.formatValue(myprod[7]));
        this.m_jMinimum.setText(Formats.DOUBLE.formatValue(myprod[5]));
        this.m_jMaximum.setText(Formats.DOUBLE.formatValue(myprod[6]));
        this.m_jMinimum.setEnabled(true);
        this.m_jMaximum.setEnabled(true);
    }

    @Override
    public void writeValueDelete(Object value) {
        Object[] myprod = (Object[])value;
        this.id = myprod[0];
        this.prodid = myprod[1];
        this.prodref = myprod[2];
        this.prodname = myprod[3];
        this.location = myprod[4];
        this.m_jTitle.setText(Formats.STRING.formatValue(myprod[2]) + " - " + Formats.STRING.formatValue(myprod[3]));
        this.m_jQuantity.setText(Formats.DOUBLE.formatValue(myprod[7]));
        this.m_jMinimum.setText(Formats.DOUBLE.formatValue(myprod[5]));
        this.m_jMaximum.setText(Formats.DOUBLE.formatValue(myprod[6]));
        this.m_jMinimum.setEnabled(false);
        this.m_jMaximum.setEnabled(false);
    }

    @Override
    public Object createValue() throws BasicException {
        return new Object[]{this.id, this.prodid, this.prodref, this.prodname, this.location, Formats.DOUBLE.parseValue(this.m_jMinimum.getText()), Formats.DOUBLE.parseValue(this.m_jMaximum.getText()), Formats.DOUBLE.parseValue(this.m_jQuantity.getText())};
    }

    @Override
    public Component getComponent() {
        return this;
    }

    @Override
    public void refresh() {
    }

    private void initComponents() {
        this.m_jTitle = new JLabel();
        this.jLabel3 = new JLabel();
        this.m_jQuantity = new JTextField();
        this.jLabel4 = new JLabel();
        this.m_jMinimum = new JTextField();
        this.jLabel5 = new JLabel();
        this.m_jMaximum = new JTextField();
        this.m_jTitle.setFont(new Font("Arial", 0, 14));
        this.m_jTitle.setPreferredSize(new Dimension(300, 30));
        this.jLabel3.setFont(new Font("Arial", 0, 14));
        this.jLabel3.setText(AppLocal.getIntString("label.units"));
        this.jLabel3.setPreferredSize(new Dimension(110, 30));
        this.m_jQuantity.setFont(new Font("Arial", 0, 14));
        this.m_jQuantity.setHorizontalAlignment(4);
        this.m_jQuantity.setEnabled(false);
        this.m_jQuantity.setPreferredSize(new Dimension(0, 30));
        this.jLabel4.setFont(new Font("Arial", 0, 14));
        this.jLabel4.setText(AppLocal.getIntString("label.minimum"));
        this.jLabel4.setPreferredSize(new Dimension(110, 30));
        this.m_jMinimum.setFont(new Font("Arial", 0, 14));
        this.m_jMinimum.setHorizontalAlignment(4);
        this.m_jMinimum.setPreferredSize(new Dimension(0, 30));
        this.jLabel5.setFont(new Font("Arial", 0, 14));
        this.jLabel5.setText(AppLocal.getIntString("label.maximum"));
        this.jLabel5.setPreferredSize(new Dimension(110, 30));
        this.m_jMaximum.setFont(new Font("Arial", 0, 14));
        this.m_jMaximum.setHorizontalAlignment(4);
        this.m_jMaximum.setPreferredSize(new Dimension(0, 30));
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.jLabel4, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.m_jMinimum, -2, 80, -2)).addGroup(layout.createSequentialGroup().addComponent(this.jLabel5, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.m_jMaximum, -2, 80, -2)).addGroup(layout.createSequentialGroup().addComponent(this.jLabel3, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.m_jQuantity, -2, 80, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.m_jTitle, -2, 314, -2))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.m_jTitle, -2, 30, -2)).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel3, -2, -1, -2).addComponent(this.m_jQuantity, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel4, -2, -1, -2).addComponent(this.m_jMinimum, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel5, -2, -1, -2).addComponent(this.m_jMaximum, -2, -1, -2)))).addContainerGap()));
    }
}

