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

public final class AttributeSetsEditor
extends JPanel
implements EditorRecord {
    private Object id;
    private JLabel jLabel2;
    private JTextField m_jName;

    public AttributeSetsEditor(DirtyManager dirty) {
        this.initComponents();
        this.m_jName.getDocument().addDocumentListener(dirty);
        this.writeValueEOF();
    }

    @Override
    public void writeValueEOF() {
        this.id = null;
        this.m_jName.setText(null);
        this.m_jName.setEnabled(false);
    }

    @Override
    public void writeValueInsert() {
        this.id = UUID.randomUUID().toString();
        this.m_jName.setText(null);
        this.m_jName.setEnabled(true);
    }

    @Override
    public void writeValueDelete(Object value) {
        Object[] attrset = (Object[])value;
        this.id = attrset[0];
        this.m_jName.setText(Formats.STRING.formatValue(attrset[1]));
        this.m_jName.setEnabled(false);
    }

    @Override
    public void writeValueEdit(Object value) {
        Object[] attrset = (Object[])value;
        this.id = attrset[0];
        this.m_jName.setText(Formats.STRING.formatValue(attrset[1]));
        this.m_jName.setEnabled(true);
    }

    @Override
    public Object createValue() throws BasicException {
        Object[] attrset = new Object[]{this.id, this.m_jName.getText()};
        return attrset;
    }

    @Override
    public Component getComponent() {
        return this;
    }

    @Override
    public void refresh() {
    }

    private void initComponents() {
        this.jLabel2 = new JLabel();
        this.m_jName = new JTextField();
        this.jLabel2.setFont(new Font("Arial", 0, 14));
        this.jLabel2.setText(AppLocal.getIntString("label.namem"));
        this.jLabel2.setPreferredSize(new Dimension(110, 30));
        this.m_jName.setFont(new Font("Arial", 0, 14));
        this.m_jName.setPreferredSize(new Dimension(220, 30));
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jLabel2, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.m_jName, -2, -1, -2).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel2, -2, -1, -2).addComponent(this.m_jName, -2, -1, -2)).addContainerGap(-1, Short.MAX_VALUE)));
    }
}

