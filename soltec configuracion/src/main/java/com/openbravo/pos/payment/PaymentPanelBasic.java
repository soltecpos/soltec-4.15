/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.payment;

import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.payment.JPaymentNotifier;
import com.openbravo.pos.payment.PaymentInfoMagcard;
import com.openbravo.pos.payment.PaymentInfoMagcardRefund;
import com.openbravo.pos.payment.PaymentPanel;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PaymentPanelBasic
extends JPanel
implements PaymentPanel {
    private double m_dTotal;
    private String m_sTransactionID;
    private JPaymentNotifier m_notifier;
    private JLabel jLabel1;

    public PaymentPanelBasic(JPaymentNotifier notifier) {
        this.m_notifier = notifier;
        this.initComponents();
    }

    @Override
    public JComponent getComponent() {
        return this;
    }

    @Override
    public void activate(String sTransaction, double dTotal) {
        this.m_sTransactionID = sTransaction;
        this.m_dTotal = dTotal;
        this.jLabel1.setText(this.m_dTotal > 0.0 ? AppLocal.getIntString("message.paymentgatewayext") : AppLocal.getIntString("message.paymentgatewayextrefund"));
        this.m_notifier.setStatus(true, true);
    }

    @Override
    public PaymentInfoMagcard getPaymentInfoMagcard() {
        if (this.m_dTotal > 0.0) {
            return new PaymentInfoMagcard("", "", "", null, null, null, null, null, this.m_sTransactionID, this.m_dTotal);
        }
        return new PaymentInfoMagcardRefund("", "", "", null, null, null, null, null, this.m_sTransactionID, this.m_dTotal);
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.add(this.jLabel1);
    }
}

