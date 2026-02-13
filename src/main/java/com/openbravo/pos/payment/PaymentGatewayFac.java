/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.payment;

import com.openbravo.pos.forms.AppProperties;
import com.openbravo.pos.payment.PaymentGateway;
import com.openbravo.pos.payment.PaymentGatewayExt;

public class PaymentGatewayFac {
    private PaymentGatewayFac() {
    }

    public static PaymentGateway getPaymentGateway(AppProperties props) {
        String sReader;
        switch (sReader = props.getProperty("payment.gateway")) {
            case "external": {
                return new PaymentGatewayExt();
            }
        }
        return null;
    }
}

