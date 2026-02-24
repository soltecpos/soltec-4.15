/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.sales;

import com.openbravo.editor.JEditorKeys;
import com.openbravo.editor.JEditorString;
import com.openbravo.pos.sales.JProductAttEditI;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;

public class JProductAttEditItem
extends JPanel
implements JProductAttEditI {
    private String attid;
    private JLabel jLabel1;
    private JEditorString jValue;

    public JProductAttEditItem(String attid, String label, String value, JEditorKeys keys) {
        this.attid = attid;
        this.initComponents();
        this.jLabel1.setText(label);
        this.jValue.addEditorKeys(keys);
        this.jValue.setText(value);
    }

    @Override
    public void assignSelection() {
        this.jValue.activate();
    }

    @Override
    public String getAttribute() {
        return this.attid;
    }

    @Override
    public String getValue() {
        return this.jValue.getText();
    }

    @Override
    public Component getComponent() {
        return this;
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.jValue = new JEditorString();
        this.jLabel1.setFont(new Font("Arial", 0, 14));
        this.jLabel1.setText("jLabel1");
        this.jLabel1.setPreferredSize(new Dimension(110, 30));
        this.jValue.setFont(new Font("Arial", 0, 14));
        this.jValue.setPreferredSize(new Dimension(0, 30));
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jLabel1, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jValue, -2, 224, -2).addContainerGap(56, Short.MAX_VALUE)));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.jLabel1, GroupLayout.Alignment.TRAILING, -1, -1, Short.MAX_VALUE).addComponent(this.jValue, GroupLayout.Alignment.TRAILING, -1, -1, Short.MAX_VALUE))));
    }
}

