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

public class JPaymentSelectReceipt
extends JPaymentSelect {
    protected JPaymentSelectReceipt(Frame parent, boolean modal, ComponentOrientation o) {
        super(parent, modal, o);
    }

    protected JPaymentSelectReceipt(Dialog parent, boolean modal, ComponentOrientation o) {
        super(parent, modal, o);
    }

    public static JPaymentSelect getDialog(Component parent) {
        Window window = JPaymentSelectReceipt.getWindow(parent);
        if (window instanceof Frame) {
            return new JPaymentSelectReceipt((Frame)window, true, parent.getComponentOrientation());
        }
        return new JPaymentSelectReceipt((Dialog)window, true, parent.getComponentOrientation());
    }

    @Override
    protected void addTabs() {
        this.addTabPayment(new JPaymentSelect.JPaymentCashCreator());
        this.addTabPayment(new JPaymentSelect.JPaymentMagcardCreator());
        this.addTabPayment(new JPaymentSelect.JPaymentDebtCreator());
        this.addTabPayment(new JPaymentSelect.JPaymentNequiCreator());
        this.addTabPayment(new JPaymentSelect.JPaymentDaviplataCreator());
    }

    @Override
    protected void setStatusPanel(boolean isPositive, boolean isComplete) {
        this.setAddEnabled(isPositive && !isComplete);
        this.setOKEnabled(isComplete);
    }

    @Override
    protected PaymentInfo getDefaultPayment(double total) {
        return new PaymentInfoCash(total, total);
    }
}

