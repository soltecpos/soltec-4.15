/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.inventory;

import com.openbravo.basic.BasicException;
import com.openbravo.data.user.DirtyManager;
import com.openbravo.data.user.EditorRecord;
import com.openbravo.format.Formats;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.AppView;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.UUID;
import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;

public final class UomEditor
extends JPanel
implements EditorRecord {
    private Object m_id;
    private JLabel jLabel2;
    private JTextField m_jName;

    public UomEditor(AppView app, DirtyManager dirty) {
        this.initComponents();
        this.m_jName.getDocument().addDocumentListener(dirty);
        this.writeValueEOF();
    }

    private void initComponents() {
        this.jLabel2 = new JLabel();
        this.m_jName = new JTextField();
        this.setFont(new Font("Arial", 0, 12));
        this.jLabel2.setFont(new Font("Arial", 0, 14));
        this.jLabel2.setText(AppLocal.getIntString("label.name"));
        this.jLabel2.setPreferredSize(new Dimension(100, 30));
        this.m_jName.setFont(new Font("Arial", 0, 14));
        this.m_jName.setPreferredSize(new Dimension(200, 30));
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(36, 36, 36).addComponent(this.jLabel2, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.m_jName, -2, -1, -2).addContainerGap(54, Short.MAX_VALUE)));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(25, 25, 25).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel2, -2, -1, -2).addComponent(this.m_jName, -2, -1, -2)).addContainerGap(35, Short.MAX_VALUE)));
    }

    @Override
    public void writeValueEOF() {
        this.m_id = null;
        this.m_jName.setText(null);
        this.m_jName.setEnabled(false);
    }

    @Override
    public void writeValueInsert() {
        this.m_id = UUID.randomUUID().toString();
        this.m_jName.setText(null);
        this.m_jName.setEnabled(true);
    }

    @Override
    public void writeValueEdit(Object value) {
        Object[] obj = (Object[])value;
        this.m_id = obj[0];
        this.m_jName.setText(Formats.STRING.formatValue(obj[1]));
        this.m_jName.setEnabled(true);
    }

    @Override
    public void writeValueDelete(Object value) {
        Object[] obj = (Object[])value;
        this.m_id = obj[0];
        this.m_jName.setText(Formats.STRING.formatValue(obj[1]));
        this.m_jName.setEnabled(false);
    }

    @Override
    public void refresh() {
    }

    @Override
    public Component getComponent() {
        return this;
    }

    @Override
    public Object createValue() throws BasicException {
        Object[] obj = new Object[]{this.m_id, this.m_jName.getText()};
        return obj;
    }

    public boolean isDataValid() {
        return true;
    }
}

