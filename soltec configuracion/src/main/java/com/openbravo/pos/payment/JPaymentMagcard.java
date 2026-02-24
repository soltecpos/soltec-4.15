/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.payment;

import com.openbravo.pos.customers.CustomerInfoExt;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.payment.JPaymentInterface;
import com.openbravo.pos.payment.JPaymentNotifier;
import com.openbravo.pos.payment.PaymentGateway;
import com.openbravo.pos.payment.PaymentGatewayFac;
import com.openbravo.pos.payment.PaymentInfo;
import com.openbravo.pos.payment.PaymentInfoMagcard;
import com.openbravo.pos.payment.PaymentPanel;
import com.openbravo.pos.payment.PaymentPanelFac;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class JPaymentMagcard
extends JPanel
implements JPaymentInterface {
    private PaymentPanel m_cardpanel;
    private final PaymentGateway m_paymentgateway;
    private final JPaymentNotifier m_notifier;
    private String transaction;
    private JPanel jPanel1;
    private JLabel jlblMessage;

    public JPaymentMagcard(AppView app, JPaymentNotifier notifier) {
        this.initComponents();
        this.m_notifier = notifier;
        this.m_paymentgateway = PaymentGatewayFac.getPaymentGateway(app.getProperties());
        if (this.m_paymentgateway == null) {
            this.jlblMessage.setText(AppLocal.getIntString("message.nopaymentgateway"));
        } else {
            this.m_cardpanel = PaymentPanelFac.getPaymentPanel(app.getProperties().getProperty("payment.magcardreader"), notifier);
            this.add((Component)this.m_cardpanel.getComponent(), "Center");
            this.jlblMessage.setText(null);
        }
    }

    @Override
    public void activate(CustomerInfoExt customerext, double dTotal, String transID) {
        this.transaction = transID;
        if (this.m_cardpanel == null) {
            this.jlblMessage.setText(AppLocal.getIntString("message.nopaymentgateway"));
            this.m_notifier.setStatus(false, false);
        } else {
            this.jlblMessage.setText(null);
            this.m_cardpanel.activate(this.transaction, dTotal);
        }
    }

    @Override
    public PaymentInfo executePayment() {
        this.jlblMessage.setText(null);
        PaymentInfoMagcard payinfo = this.m_cardpanel.getPaymentInfoMagcard();
        this.m_paymentgateway.execute(payinfo);
        if (payinfo.isPaymentOK()) {
            return payinfo;
        }
        this.jlblMessage.setText(payinfo.getMessage());
        return null;
    }

    @Override
    public Component getComponent() {
        return this;
    }

    public void setTransaction(String transid) {
        this.transaction = transid;
    }

    private void initComponents() {
        this.jPanel1 = new JPanel();
        this.jlblMessage = new JLabel();
        this.setMinimumSize(new Dimension(300, 40));
        this.setPreferredSize(new Dimension(500, 50));
        this.jPanel1.setFont(new Font("Arial", 0, 12));
        this.jPanel1.setMinimumSize(new Dimension(290, 35));
        this.jPanel1.setPreferredSize(new Dimension(500, 45));
        this.jlblMessage.setFont(new Font("Arial", 0, 18));
        this.jlblMessage.setHorizontalAlignment(0);
        GroupLayout jPanel1Layout = new GroupLayout(this.jPanel1);
        this.jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addComponent(this.jlblMessage, -1, -1, Short.MAX_VALUE)));
        jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jlblMessage, -1, -1, Short.MAX_VALUE).addContainerGap()));
        this.add(this.jPanel1);
    }
}

