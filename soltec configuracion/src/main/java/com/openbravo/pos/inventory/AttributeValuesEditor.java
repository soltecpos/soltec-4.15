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
import java.util.UUID;
import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;

public class AttributeValuesEditor
extends JPanel
implements EditorRecord {
    private Object id;
    private Object attid;
    private Object insertid;
    private JLabel jLabel2;
    private JTextField jValue;

    public AttributeValuesEditor(DirtyManager dirty) {
        this.initComponents();
        this.jValue.getDocument().addDocumentListener(dirty);
    }

    public void setInsertId(String insertid) {
        this.insertid = insertid;
    }

    @Override
    public void refresh() {
    }

    @Override
    public void writeValueEOF() {
        this.id = null;
        this.attid = null;
        this.jValue.setText(null);
        this.jValue.setEnabled(false);
    }

    @Override
    public void writeValueInsert() {
        this.id = UUID.randomUUID().toString();
        this.attid = this.insertid;
        this.jValue.setText(null);
        this.jValue.setEnabled(true);
    }

    @Override
    public void writeValueEdit(Object value) {
        Object[] obj = (Object[])value;
        this.id = obj[0];
        this.attid = obj[1];
        this.jValue.setText(Formats.STRING.formatValue(obj[2]));
        this.jValue.setEnabled(true);
    }

    @Override
    public void writeValueDelete(Object value) {
        Object[] obj = (Object[])value;
        this.id = obj[0];
        this.attid = obj[1];
        this.jValue.setText(Formats.STRING.formatValue(obj[2]));
        this.jValue.setEnabled(false);
    }

    @Override
    public Component getComponent() {
        return this;
    }

    @Override
    public Object createValue() throws BasicException {
        return new Object[]{this.id, this.attid, Formats.STRING.formatValue(this.jValue.getText())};
    }

    private void initComponents() {
        this.jLabel2 = new JLabel();
        this.jValue = new JTextField();
        this.jLabel2.setFont(new Font("Arial", 0, 14));
        this.jLabel2.setText(AppLocal.getIntString("label.value"));
        this.jLabel2.setPreferredSize(new Dimension(110, 30));
        this.jValue.setFont(new Font("Arial", 0, 14));
        this.jValue.setPreferredSize(new Dimension(220, 30));
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jLabel2, -2, 80, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jValue, -2, 200, -2).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel2, -2, -1, -2).addComponent(this.jValue, -2, -1, -2)).addContainerGap()));
    }
}

