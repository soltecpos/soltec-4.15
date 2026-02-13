/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.payment;

import com.openbravo.pos.forms.AppConfig;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.payment.PaymentConfiguration;
import com.openbravo.pos.util.AltEncrypter;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;

public class ConfigPaymentPanelGeneric
extends JPanel
implements PaymentConfiguration {
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JTextField jtxtCommerceID;
    private JPasswordField jtxtCommercePwd;

    public ConfigPaymentPanelGeneric() {
        this.initComponents();
    }

    @Override
    public JPanel getComponent() {
        return this;
    }

    @Override
    public void loadProperties(AppConfig config) {
        String sCommerceID = config.getProperty("payment.commerceid");
        String sCommercePass = config.getProperty("payment.commercepassword");
        if (sCommerceID != null && sCommercePass != null && sCommercePass.startsWith("crypt:")) {
            this.jtxtCommerceID.setText(config.getProperty("payment.commerceid"));
            AltEncrypter cypher = new AltEncrypter("cypherkey" + config.getProperty("payment.commerceid"));
            this.jtxtCommercePwd.setText(cypher.decrypt(config.getProperty("payment.commercepassword").substring(6)));
        }
    }

    @Override
    public void saveProperties(AppConfig config) {
        config.setProperty("payment.commerceid", this.jtxtCommerceID.getText());
        AltEncrypter cypher = new AltEncrypter("cypherkey" + this.jtxtCommerceID.getText());
        config.setProperty("payment.commercepassword", "crypt:" + cypher.encrypt(new String(this.jtxtCommercePwd.getPassword())));
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.jtxtCommerceID = new JTextField();
        this.jLabel2 = new JLabel();
        this.jtxtCommercePwd = new JPasswordField();
        this.setBackground(new Color(255, 255, 255));
        this.setPreferredSize(new Dimension(400, 90));
        this.jLabel1.setFont(new Font("Arial", 0, 14));
        this.jLabel1.setText(AppLocal.getIntString("label.commerceid"));
        this.jLabel1.setPreferredSize(new Dimension(150, 30));
        this.jtxtCommerceID.setFont(new Font("Arial", 0, 14));
        this.jtxtCommerceID.setPreferredSize(new Dimension(200, 30));
        this.jLabel2.setFont(new Font("Arial", 0, 14));
        this.jLabel2.setText(AppLocal.getIntString("label.commercepwd"));
        this.jLabel2.setPreferredSize(new Dimension(150, 30));
        this.jtxtCommercePwd.setFont(new Font("Arial", 0, 14));
        this.jtxtCommercePwd.setPreferredSize(new Dimension(200, 30));
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.jLabel2, -1, -1, Short.MAX_VALUE).addComponent(this.jLabel1, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jtxtCommerceID, -2, -1, -2).addComponent(this.jtxtCommercePwd, -2, -1, -2)).addContainerGap(30, Short.MAX_VALUE)));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1, -2, -1, -2).addComponent(this.jtxtCommerceID, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jtxtCommercePwd, -2, -1, -2).addComponent(this.jLabel2, -2, -1, -2)).addContainerGap(-1, Short.MAX_VALUE)));
    }
}

