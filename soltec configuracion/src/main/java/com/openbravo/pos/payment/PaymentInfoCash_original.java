/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.payment;

import com.openbravo.format.Formats;
import com.openbravo.pos.payment.PaymentInfo;

public class PaymentInfoCash_original
extends PaymentInfo {
    private final double m_dPaid;
    private final double m_dTotal;
    private double m_dTendered;
    private final String m_dCardName;

    public PaymentInfoCash_original(double dTotal, double dPaid) {
        this.m_dCardName = null;
        this.m_dTotal = dTotal;
        this.m_dPaid = dPaid;
    }

    @Override
    public PaymentInfo copyPayment() {
        return new PaymentInfoCash_original(this.m_dTotal, this.m_dPaid);
    }

    @Override
    public String getTransactionID() {
        return "no ID";
    }

    @Override
    public String getName() {
        return "cash";
    }

    @Override
    public double getTotal() {
        return this.m_dTotal;
    }

    @Override
    public double getPaid() {
        return this.m_dPaid;
    }

    @Override
    public double getTendered() {
        return this.m_dTendered;
    }

    @Override
    public double getChange() {
        return this.m_dPaid - this.m_dTotal;
    }

    @Override
    public String getCardName() {
        return this.m_dCardName;
    }

    public String printPaid() {
        return Formats.CURRENCY.formatValue(this.m_dPaid);
    }

    public String printChange() {
        return Formats.CURRENCY.formatValue(this.m_dPaid - this.m_dTotal);
    }

    @Override
    public String getVoucher() {
        return null;
    }
}

