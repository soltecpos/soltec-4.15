/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.sales;

import com.openbravo.pos.sales.JProductAttEditI;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;

public class JProductAttListItem
extends JPanel
implements JProductAttEditI {
    private static final long serialVersionUID = 1L;
    private final String attid;
    private JLabel jLabel1;
    private JComboBox<String> jValues;

    public JProductAttListItem(String attid, String label, String value, List<String> values) {
        this.attid = attid;
        this.initComponents();
        this.jLabel1.setText(label);
        values.stream().forEach(item -> this.jValues.addItem((String)item));
        this.jValues.setSelectedItem(value);
    }

    @Override
    public void assignSelection() {
        EventQueue.invokeLater(() -> this.jValues.requestFocus());
    }

    @Override
    public String getAttribute() {
        return this.attid;
    }

    @Override
    public String getValue() {
        return (String)this.jValues.getSelectedItem();
    }

    @Override
    public Component getComponent() {
        return this;
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.jValues = new JComboBox();
        this.jLabel1.setFont(new Font("Arial", 0, 14));
        this.jLabel1.setText("jLabel1");
        this.jLabel1.setPreferredSize(new Dimension(110, 30));
        this.jValues.setEditable(true);
        this.jValues.setFont(new Font("Arial", 0, 14));
        this.jValues.setPreferredSize(new Dimension(200, 30));
        this.jValues.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JProductAttListItem.this.jValuesActionPerformed(evt);
            }
        });
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jLabel1, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jValues, -2, -1, -2).addContainerGap(66, Short.MAX_VALUE)));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1, -2, -1, -2).addComponent(this.jValues, -2, -1, -2))));
    }

    private void jValuesActionPerformed(ActionEvent evt) {
    }
}

