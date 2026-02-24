/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.payment;

import com.openbravo.pos.forms.AppConfig;
import com.openbravo.pos.payment.PaymentConfiguration;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.GroupLayout;
import javax.swing.JPanel;

public class ConfigPaymentPanelEmpty
extends JPanel
implements PaymentConfiguration {
    public ConfigPaymentPanelEmpty() {
        this.initComponents();
    }

    @Override
    public JPanel getComponent() {
        return this;
    }

    @Override
    public void loadProperties(AppConfig config) {
    }

    @Override
    public void saveProperties(AppConfig config) {
    }

    private void initComponents() {
        this.setBackground(new Color(255, 255, 255));
        this.setFont(new Font("Arial", 0, 14));
        this.setPreferredSize(new Dimension(650, 75));
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 650, Short.MAX_VALUE));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 150, Short.MAX_VALUE));
    }
}

