/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.payment;

import com.openbravo.pos.payment.PaymentInfo;

public class PaymentInfoFree
extends PaymentInfo {
    private double m_dTotal;
    private double m_dTendered;
    private String m_dCardName = null;

    public PaymentInfoFree(double dTotal) {
        this.m_dTotal = dTotal;
    }

    @Override
    public PaymentInfo copyPayment() {
        return new PaymentInfoFree(this.m_dTotal);
    }

    @Override
    public String getTransactionID() {
        return "no ID";
    }

    @Override
    public String getName() {
        return "free";
    }

    @Override
    public double getTotal() {
        return this.m_dTotal;
    }

    @Override
    public double getPaid() {
        return 0.0;
    }

    @Override
    public double getChange() {
        return 0.0;
    }

    @Override
    public double getTendered() {
        return this.m_dTendered;
    }

    @Override
    public String getCardName() {
        return this.m_dCardName;
    }

    @Override
    public String getVoucher() {
        return null;
    }
}

