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

public final class LocationsView
extends JPanel
implements EditorRecord {
    private String m_sID;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JTextField m_jAddress;
    private JTextField m_jName;

    public LocationsView(DirtyManager dirty) {
        this.initComponents();
        this.m_jName.getDocument().addDocumentListener(dirty);
        this.m_jAddress.getDocument().addDocumentListener(dirty);
        this.writeValueEOF();
    }

    @Override
    public void writeValueEOF() {
        this.m_sID = null;
        this.m_jName.setText(null);
        this.m_jAddress.setText(null);
        this.m_jName.setEnabled(false);
        this.m_jAddress.setEnabled(false);
    }

    @Override
    public void writeValueInsert() {
        this.m_sID = UUID.randomUUID().toString();
        this.m_jName.setText(null);
        this.m_jAddress.setText(null);
        this.m_jName.setEnabled(true);
        this.m_jAddress.setEnabled(true);
    }

    @Override
    public void writeValueDelete(Object value) {
        Object[] location = (Object[])value;
        this.m_sID = Formats.STRING.formatValue(location[0]);
        this.m_jName.setText(Formats.STRING.formatValue(location[1]));
        this.m_jAddress.setText(Formats.STRING.formatValue(location[2]));
        this.m_jName.setEnabled(false);
        this.m_jAddress.setEnabled(false);
    }

    @Override
    public void writeValueEdit(Object value) {
        Object[] location = (Object[])value;
        this.m_sID = Formats.STRING.formatValue(location[0]);
        this.m_jName.setText(Formats.STRING.formatValue(location[1]));
        this.m_jAddress.setText(Formats.STRING.formatValue(location[2]));
        this.m_jName.setEnabled(true);
        this.m_jAddress.setEnabled(true);
    }

    @Override
    public Object createValue() throws BasicException {
        Object[] location = new Object[]{this.m_sID, this.m_jName.getText(), this.m_jAddress.getText()};
        return location;
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
        this.jLabel3 = new JLabel();
        this.m_jAddress = new JTextField();
        this.jLabel2.setFont(new Font("Arial", 0, 14));
        this.jLabel2.setText(AppLocal.getIntString("label.locationname"));
        this.jLabel2.setPreferredSize(new Dimension(110, 30));
        this.m_jName.setFont(new Font("Arial", 0, 14));
        this.m_jName.setPreferredSize(new Dimension(220, 30));
        this.jLabel3.setFont(new Font("Arial", 0, 14));
        this.jLabel3.setText(AppLocal.getIntString("label.locationaddress"));
        this.jLabel3.setPreferredSize(new Dimension(110, 30));
        this.m_jAddress.setFont(new Font("Arial", 0, 14));
        this.m_jAddress.setPreferredSize(new Dimension(220, 30));
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel3, -2, -1, -2).addComponent(this.jLabel2, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.m_jAddress, -2, -1, -2).addComponent(this.m_jName, -2, -1, -2)).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.m_jName, -2, -1, -2).addComponent(this.jLabel2, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.m_jAddress, -2, -1, -2).addComponent(this.jLabel3, -2, -1, -2))));
    }
}

