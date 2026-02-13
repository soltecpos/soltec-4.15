/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.payment;

import com.openbravo.pos.payment.JPaymentSelect;
import com.openbravo.pos.payment.PaymentInfo;
import com.openbravo.pos.payment.PaymentInfoTicket;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;

public class JPaymentSelectRefund
extends JPaymentSelect {
    protected JPaymentSelectRefund(Frame parent, boolean modal, ComponentOrientation o) {
        super(parent, modal, o);
    }

    protected JPaymentSelectRefund(Dialog parent, boolean modal, ComponentOrientation o) {
        super(parent, modal, o);
    }

    public static JPaymentSelect getDialog(Component parent) {
        Window window = JPaymentSelectRefund.getWindow(parent);
        if (window instanceof Frame) {
            return new JPaymentSelectRefund((Frame)window, true, parent.getComponentOrientation());
        }
        return new JPaymentSelectRefund((Dialog)window, true, parent.getComponentOrientation());
    }

    @Override
    protected void addTabs() {
        this.addTabPayment(new JPaymentSelect.JPaymentCashRefundCreator());
        this.addTabPayment(new JPaymentSelect.JPaymentChequeRefundCreator());
        this.addTabPayment(new JPaymentSelect.JPaymentVoucherRefundCreator());
        this.addTabPayment(new JPaymentSelect.JPaymentMagcardRefundCreator());
    }

    @Override
    protected void setStatusPanel(boolean isPositive, boolean isComplete) {
        this.setAddEnabled(isPositive && !isComplete);
        this.setOKEnabled(isComplete);
    }

    @Override
    protected PaymentInfo getDefaultPayment(double total) {
        return new PaymentInfoTicket(total, "cashrefund");
    }
}

