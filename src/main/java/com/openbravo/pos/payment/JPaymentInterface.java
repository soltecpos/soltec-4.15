/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.payment;

import com.openbravo.pos.customers.CustomerInfoExt;
import com.openbravo.pos.payment.PaymentInfo;
import java.awt.Component;

public interface JPaymentInterface {
    public void activate(CustomerInfoExt var1, double var2, String var4);

    public PaymentInfo executePayment();

    public Component getComponent();
}

