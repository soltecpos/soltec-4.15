/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.payment;

import com.openbravo.pos.payment.PaymentInfoMagcard;
import javax.swing.JComponent;

public interface PaymentPanel {
    public void activate(String var1, double var2);

    public JComponent getComponent();

    public PaymentInfoMagcard getPaymentInfoMagcard();
}

