/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.admin;

import com.openbravo.basic.BasicException;
import com.openbravo.data.user.DirtyManager;
import com.openbravo.data.user.EditorRecord;
import com.openbravo.format.Formats;
import com.openbravo.pos.forms.AppLocal;
import java.awt.Component;
import java.awt.Font;
import java.util.UUID;
import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;

public final class RolesView
extends JPanel
implements EditorRecord {
    private Object m_oId;
    private JLabel jLabel2;
    private JScrollPane jScrollPane1;
    private JTextField m_jName;
    private JTextArea m_jText;

    public RolesView(DirtyManager dirty) {
        this.initComponents();
        this.m_jName.getDocument().addDocumentListener(dirty);
        this.m_jText.getDocument().addDocumentListener(dirty);
        this.writeValueEOF();
    }

    @Override
    public void writeValueEOF() {
        this.m_oId = null;
        this.m_jName.setText(null);
        this.m_jText.setText(null);
        this.m_jName.setEnabled(false);
        this.m_jText.setEnabled(false);
    }

    @Override
    public void writeValueInsert() {
        this.m_oId = null;
        this.m_jName.setText(null);
        this.m_jText.setText(null);
        this.m_jName.setEnabled(true);
        this.m_jText.setEnabled(true);
    }

    @Override
    public void writeValueDelete(Object value) {
        Object[] role = (Object[])value;
        this.m_oId = role[0];
        this.m_jName.setText(Formats.STRING.formatValue(role[1]));
        this.m_jText.setText(Formats.BYTEA.formatValue(role[2]));
        this.m_jText.setCaretPosition(0);
        this.m_jName.setEnabled(false);
        this.m_jText.setEnabled(false);
    }

    @Override
    public void writeValueEdit(Object value) {
        Object[] role = (Object[])value;
        this.m_oId = role[0];
        this.m_jName.setText(Formats.STRING.formatValue(role[1]));
        this.m_jText.setText(Formats.BYTEA.formatValue(role[2]));
        this.m_jText.setCaretPosition(0);
        this.m_jName.setEnabled(true);
        this.m_jText.setEnabled(true);
    }

    @Override
    public Object createValue() throws BasicException {
        Object[] role = new Object[]{this.m_oId == null ? UUID.randomUUID().toString() : this.m_oId, this.m_jName.getText(), Formats.BYTEA.parseValue(this.m_jText.getText())};
        return role;
    }

    @Override
    public Component getComponent() {
        return this;
    }

    @Override
    public void refresh() {
    }

    private void initComponents() {
        this.jScrollPane1 = new JScrollPane();
        this.m_jText = new JTextArea();
        this.jLabel2 = new JLabel();
        this.m_jName = new JTextField();
        this.m_jText.setFont(new Font("Arial", 0, 12));
        this.jScrollPane1.setViewportView(this.m_jText);
        this.jLabel2.setFont(new Font("Arial", 0, 12));
        this.jLabel2.setText(AppLocal.getIntString("label.namem"));
        this.m_jName.setFont(new Font("Arial", 0, 12));
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jScrollPane1, -1, 477, Short.MAX_VALUE).addGroup(layout.createSequentialGroup().addComponent(this.jLabel2, -2, 80, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.m_jName, -2, 260, -2))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel2, -2, 25, -2).addComponent(this.m_jName, -2, 25, -2)).addGap(18, 18, 18).addComponent(this.jScrollPane1, -1, 312, Short.MAX_VALUE).addContainerGap()));
    }
}

