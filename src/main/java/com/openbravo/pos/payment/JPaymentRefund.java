/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.payment;

import com.openbravo.pos.customers.CustomerInfoExt;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.payment.JPaymentInterface;
import com.openbravo.pos.payment.JPaymentNotifier;
import com.openbravo.pos.payment.PaymentInfo;
import com.openbravo.pos.payment.PaymentInfoTicket;
import java.awt.Component;
import java.awt.Font;
import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class JPaymentRefund
extends JPanel
implements JPaymentInterface {
    private JPaymentNotifier m_notifier;
    private double m_dTotal;
    private String m_sName;
    private JLabel jLabel1;

    public JPaymentRefund(JPaymentNotifier notifier, String sName) {
        this.m_notifier = notifier;
        this.m_sName = sName;
        this.initComponents();
    }

    @Override
    public void activate(CustomerInfoExt customerext, double dTotal, String transID) {
        this.m_dTotal = dTotal;
        this.m_notifier.setStatus(true, true);
    }

    @Override
    public PaymentInfo executePayment() {
        return new PaymentInfoTicket(this.m_dTotal, this.m_sName);
    }

    @Override
    public Component getComponent() {
        return this;
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.jLabel1.setFont(new Font("Arial", 0, 30));
        this.jLabel1.setText(AppLocal.getIntString("message.paymentcashneg"));
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap(35, Short.MAX_VALUE).addComponent(this.jLabel1).addContainerGap(35, Short.MAX_VALUE)));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(131, 131, 131).addComponent(this.jLabel1).addContainerGap(155, Short.MAX_VALUE)));
    }
}

