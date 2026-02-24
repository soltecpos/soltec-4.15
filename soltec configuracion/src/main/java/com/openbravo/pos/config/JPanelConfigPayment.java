/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.config;

import com.openbravo.data.user.DirtyManager;
import com.openbravo.pos.config.PanelConfig;
import com.openbravo.pos.forms.AppConfig;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.payment.ConfigPaymentPanelEmpty;
import com.openbravo.pos.payment.PaymentConfiguration;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;

public class JPanelConfigPayment
extends JPanel
implements PanelConfig {
    private final DirtyManager dirty = new DirtyManager();
    private final Map<String, PaymentConfiguration> paymentsName = new HashMap<String, PaymentConfiguration>();
    private PaymentConfiguration pc;
    private JLabel jLabel11;
    private JLabel jLabel13;
    private JPanel jPanel2;
    private JComboBox<String> jcboCardReader;
    private JComboBox<String> jcboPaymentGateway;
    private JCheckBox jchkPaymentTest;

    public JPanelConfigPayment() {
        this.initComponents();
        this.jcboCardReader.addActionListener(this.dirty);
        this.jcboPaymentGateway.addActionListener(this.dirty);
        this.jchkPaymentTest.addActionListener(this.dirty);
        this.initPayments("Not defined", new ConfigPaymentPanelEmpty());
        this.initPayments("external", new ConfigPaymentPanelEmpty());
        this.jcboCardReader.addItem("Not defined");
        this.jcboCardReader.addItem("Generic");
        this.jcboCardReader.addItem("Intelligent");
        this.jcboCardReader.addItem("Keyboard");
        this.jcboCardReader.addItem("NFC/RFID");
    }

    @Override
    public boolean hasChanged() {
        return this.dirty.isDirty();
    }

    @Override
    public Component getConfigComponent() {
        return this;
    }

    @Override
    public void loadProperties(AppConfig config) {
        this.jcboCardReader.setSelectedItem(config.getProperty("payment.magcardreader"));
        this.jcboPaymentGateway.setSelectedItem(config.getProperty("payment.gateway"));
        this.jchkPaymentTest.setSelected(Boolean.parseBoolean(config.getProperty("payment.testmode")));
        this.pc.loadProperties(config);
        this.dirty.setDirty(false);
    }

    @Override
    public void saveProperties(AppConfig config) {
        config.setProperty("payment.magcardreader", this.comboValue(this.jcboCardReader.getSelectedItem()));
        config.setProperty("payment.gateway", this.comboValue(this.jcboPaymentGateway.getSelectedItem()));
        config.setProperty("payment.testmode", Boolean.toString(this.jchkPaymentTest.isSelected()));
        this.pc.saveProperties(config);
        this.dirty.setDirty(false);
    }

    private void initPayments(String name, PaymentConfiguration pc) {
        this.jcboPaymentGateway.addItem(name);
        this.paymentsName.put(name, pc);
    }

    private String comboValue(Object value) {
        return value == null ? "" : value.toString();
    }

    private void initComponents() {
        this.jPanel2 = new JPanel();
        this.jLabel13 = new JLabel();
        this.jcboPaymentGateway = new JComboBox();
        this.jchkPaymentTest = new JCheckBox();
        this.jLabel11 = new JLabel();
        this.jcboCardReader = new JComboBox();
        this.setBackground(new Color(255, 255, 255));
        this.setFont(new Font("Arial", 0, 12));
        this.setPreferredSize(new Dimension(590, 450));
        this.jPanel2.setBackground(new Color(255, 255, 255));
        this.jPanel2.setBorder(BorderFactory.createLineBorder(new Color(204, 204, 204)));
        this.jPanel2.setFont(new Font("Arial", 0, 12));
        this.jPanel2.setPreferredSize(new Dimension(600, 200));
        this.jPanel2.setLayout(new GridLayout(1, 1));
        this.jLabel13.setFont(new Font("Arial", 0, 14));
        this.jLabel13.setText(AppLocal.getIntString("label.paymentgateway"));
        this.jLabel13.setHorizontalTextPosition(2);
        this.jLabel13.setPreferredSize(new Dimension(150, 30));
        this.jcboPaymentGateway.setFont(new Font("Arial", 0, 14));
        this.jcboPaymentGateway.setPreferredSize(new Dimension(200, 30));
        this.jcboPaymentGateway.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPanelConfigPayment.this.jcboPaymentGatewayActionPerformed(evt);
            }
        });
        this.jchkPaymentTest.setBackground(new Color(255, 255, 255));
        this.jchkPaymentTest.setFont(new Font("Arial", 0, 14));
        this.jchkPaymentTest.setText(AppLocal.getIntString("label.paymenttestmode"));
        this.jchkPaymentTest.setPreferredSize(new Dimension(200, 30));
        this.jLabel11.setFont(new Font("Arial", 0, 14));
        this.jLabel11.setText(AppLocal.getIntString("label.cardreader"));
        this.jLabel11.setHorizontalTextPosition(2);
        this.jLabel11.setPreferredSize(new Dimension(150, 30));
        this.jcboCardReader.setFont(new Font("Arial", 0, 14));
        this.jcboCardReader.setPreferredSize(new Dimension(200, 30));
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(10, 10, 10).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.jPanel2, -2, -1, -2).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addComponent(this.jLabel11, -1, -1, Short.MAX_VALUE).addComponent(this.jLabel13, -1, -1, Short.MAX_VALUE)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jcboCardReader, -2, -1, -2).addGroup(layout.createSequentialGroup().addComponent(this.jcboPaymentGateway, -2, -1, -2).addGap(18, 18, 18).addComponent(this.jchkPaymentTest, -2, 130, -2)))))));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel11, -2, -1, -2).addComponent(this.jcboCardReader, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel13, -2, -1, -2).addComponent(this.jcboPaymentGateway, -2, -1, -2).addGroup(layout.createSequentialGroup().addGap(3, 3, 3).addComponent(this.jchkPaymentTest, -2, -1, -2))).addGap(18, 18, 18).addComponent(this.jPanel2, -2, -1, -2).addContainerGap(-1, Short.MAX_VALUE)));
    }

    private void jcboPaymentGatewayActionPerformed(ActionEvent evt) {
        this.pc = this.paymentsName.get(this.comboValue(this.jcboPaymentGateway.getSelectedItem()));
        if (this.pc != null) {
            this.jPanel2.removeAll();
            this.jPanel2.add(this.pc.getComponent());
            this.jPanel2.revalidate();
            this.jPanel2.repaint();
        }
    }
}

