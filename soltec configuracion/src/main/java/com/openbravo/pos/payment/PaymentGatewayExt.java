/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.payment;

import com.openbravo.pos.payment.PaymentGateway;
import com.openbravo.pos.payment.PaymentInfoMagcard;

public class PaymentGatewayExt
implements PaymentGateway {
    @Override
    public void execute(PaymentInfoMagcard payinfo) {
        payinfo.paymentOK("OK", payinfo.getTransactionID(), "");
    }
}

