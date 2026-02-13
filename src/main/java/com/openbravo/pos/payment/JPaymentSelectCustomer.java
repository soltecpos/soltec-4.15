/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.payment;

import com.openbravo.pos.payment.JPaymentSelect;
import com.openbravo.pos.payment.PaymentInfo;
import com.openbravo.pos.payment.PaymentInfoCash;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;

public class JPaymentSelectCustomer
extends JPaymentSelect {
    protected JPaymentSelectCustomer(Frame parent, boolean modal, ComponentOrientation o) {
        super(parent, modal, o);
    }

    protected JPaymentSelectCustomer(Dialog parent, boolean modal, ComponentOrientation o) {
        super(parent, modal, o);
    }

    public static JPaymentSelect getDialog(Component parent) {
        Window window = JPaymentSelectCustomer.getWindow(parent);
        if (window instanceof Frame) {
            return new JPaymentSelectCustomer((Frame)window, true, parent.getComponentOrientation());
        }
        return new JPaymentSelectCustomer((Dialog)window, true, parent.getComponentOrientation());
    }

    @Override
    protected void addTabs() {
        this.addTabPayment(new JPaymentSelect.JPaymentCashCreator());
        this.addTabPayment(new JPaymentSelect.JPaymentChequeCreator());
        this.addTabPayment(new JPaymentSelect.JPaymentVoucherCreator());
        this.addTabPayment(new JPaymentSelect.JPaymentBankCreator());
        this.addTabPayment(new JPaymentSelect.JPaymentMagcardCreator());
        this.addTabPayment(new JPaymentSelect.JPaymentSlipCreator());
    }

    @Override
    protected void setStatusPanel(boolean isPositive, boolean isComplete) {
        this.setAddEnabled(isPositive && !isComplete);
        this.setOKEnabled(isPositive);
    }

    @Override
    protected PaymentInfo getDefaultPayment(double total) {
        return new PaymentInfoCash(total, total);
    }
}

