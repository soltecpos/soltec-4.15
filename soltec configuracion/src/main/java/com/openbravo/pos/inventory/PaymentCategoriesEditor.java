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
import java.awt.Font;
import java.util.UUID;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public final class PaymentCategoriesEditor
extends JPanel
implements EditorRecord {
    private Object m_oId;
    private JLabel jLabelName;
    private JTextField m_jName;

    public PaymentCategoriesEditor(DirtyManager dirty) {
        this.initComponents();
        this.m_jName.getDocument().addDocumentListener(dirty);
        this.writeValueEOF();
    }

    @Override
    public void writeValueEOF() {
        this.m_oId = null;
        this.m_jName.setText(null);
        this.m_jName.setEnabled(false);
    }

    @Override
    public void writeValueInsert() {
        this.m_oId = UUID.randomUUID().toString();
        this.m_jName.setText(null);
        this.m_jName.setEnabled(true);
    }

    @Override
    public void writeValueDelete(Object value) {
        Object[] cat = (Object[])value;
        this.m_oId = cat[0];
        this.m_jName.setText(Formats.STRING.formatValue(cat[1]));
        this.m_jName.setEnabled(false);
    }

    @Override
    public void writeValueEdit(Object value) {
        Object[] cat = (Object[])value;
        this.m_oId = cat[0];
        this.m_jName.setText(Formats.STRING.formatValue(cat[1]));
        this.m_jName.setEnabled(true);
    }

    @Override
    public Object createValue() throws BasicException {
        Object[] cat = new Object[]{this.m_oId, this.m_jName.getText()};
        return cat;
    }

    @Override
    public Component getComponent() {
        return this;
    }

    @Override
    public void refresh() {
    }

    private void initComponents() {
        this.setLayout(null);
        this.jLabelName = new JLabel();
        this.jLabelName.setFont(new Font("Arial", 0, 12));
        this.jLabelName.setText(AppLocal.getIntString("label.name"));
        this.add(this.jLabelName);
        this.jLabelName.setBounds(20, 20, 80, 25);
        this.m_jName = new JTextField();
        this.m_jName.setFont(new Font("Arial", 0, 12));
        this.add(this.m_jName);
        this.m_jName.setBounds(100, 20, 200, 25);
    }
}

