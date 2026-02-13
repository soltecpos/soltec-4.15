/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.config;

import com.openbravo.data.user.DirtyManager;
import com.openbravo.pos.config.ParametersConfig;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.util.StringParser;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;

public class ParametersPrinter
extends JPanel
implements ParametersConfig {
    private String othersizename = "standard";
    private JComboBox jPrinters;
    private JCheckBox jReceiptPrinter;

    public ParametersPrinter(String[] printernames) {
        this.initComponents();
        this.jPrinters.addItem("(Default)");
        this.jPrinters.addItem("(Show dialog)");
        for (String name : printernames) {
            this.jPrinters.addItem(name);
        }
    }

    @Override
    public Component getComponent() {
        return this;
    }

    @Override
    public void addDirtyManager(DirtyManager dirty) {
        this.jPrinters.addActionListener(dirty);
        this.jReceiptPrinter.addActionListener(dirty);
    }

    @Override
    public void setParameters(StringParser p) {
        this.jPrinters.setSelectedItem(p.nextToken(','));
        String sizename = p.nextToken(',');
        this.jReceiptPrinter.setSelected("receipt".equals(sizename));
        this.othersizename = "receipt".equals(sizename) ? "standard" : sizename;
    }

    @Override
    public String getParameters() {
        return ParametersPrinter.comboValue(this.jPrinters.getSelectedItem()) + "," + this.boolValue(this.jReceiptPrinter.isSelected());
    }

    private static String comboValue(Object value) {
        return value == null ? "" : value.toString();
    }

    private String boolValue(boolean value) {
        return value ? "receipt" : this.othersizename;
    }

    private void initComponents() {
        this.jPrinters = new JComboBox();
        this.jReceiptPrinter = new JCheckBox();
        this.setBackground(new Color(255, 255, 255));
        this.jPrinters.setFont(new Font("Arial", 0, 14));
        this.jPrinters.setPreferredSize(new Dimension(200, 30));
        this.jPrinters.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ParametersPrinter.this.jPrintersActionPerformed(evt);
            }
        });
        this.jReceiptPrinter.setBackground(new Color(255, 255, 255));
        this.jReceiptPrinter.setFont(new Font("Arial", 0, 12));
        this.jReceiptPrinter.setSelected(true);
        this.jReceiptPrinter.setText(AppLocal.getIntString("label.receiptprinter"));
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jPrinters, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jReceiptPrinter).addContainerGap(-1, Short.MAX_VALUE)));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jPrinters, -2, -1, -2).addComponent(this.jReceiptPrinter, -2, 25, -2)).addGap(0, 0, Short.MAX_VALUE)));
    }

    private void jPrintersActionPerformed(ActionEvent evt) {
    }
}

