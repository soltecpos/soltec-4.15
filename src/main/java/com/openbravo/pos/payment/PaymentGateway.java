/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.payment;

import com.openbravo.pos.payment.PaymentInfoMagcard;

public interface PaymentGateway {
    public void execute(PaymentInfoMagcard var1);
}

