/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.payment;

import com.openbravo.pos.customers.CustomerInfoExt;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.payment.JPaymentInterface;
import com.openbravo.pos.payment.JPaymentNotifier;
import com.openbravo.pos.payment.PaymentInfo;
import com.openbravo.pos.payment.PaymentInfoFree;
import java.awt.Component;
import java.awt.Font;
import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class JPaymentFree
extends JPanel
implements JPaymentInterface {
    private double m_dTotal;
    private JPaymentNotifier m_notifier;
    private JLabel jLabel1;

    public JPaymentFree(JPaymentNotifier notifier) {
        this.m_notifier = notifier;
        this.initComponents();
    }

    @Override
    public void activate(CustomerInfoExt customerext, double dTotal, String transID) {
        this.m_dTotal = dTotal;
        this.m_notifier.setStatus(true, true);
    }

    @Override
    public PaymentInfo executePayment() {
        return new PaymentInfoFree(this.m_dTotal);
    }

    @Override
    public Component getComponent() {
        return this;
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.jLabel1.setFont(new Font("Arial", 1, 36));
        this.jLabel1.setHorizontalAlignment(0);
        this.jLabel1.setText(AppLocal.getIntString("message.paymentfree"));
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(148, 148, 148).addComponent(this.jLabel1).addContainerGap(165, Short.MAX_VALUE)));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(126, 126, 126).addComponent(this.jLabel1).addContainerGap(131, Short.MAX_VALUE)));
    }
}

