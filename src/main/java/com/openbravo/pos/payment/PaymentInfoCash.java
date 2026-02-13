/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.payment;

import com.openbravo.format.Formats;
import com.openbravo.pos.payment.PaymentInfo;

public class PaymentInfoCash
extends PaymentInfo {
    private double prePayAmount = 0.0;
    private double m_dPaid;
    private double m_dTotal;
    private double m_dTendered;
    private String m_dCardName = null;

    public PaymentInfoCash(double dTotal, double dPaid, double dTendered) {
        this.m_dTotal = dTotal;
        this.m_dPaid = dPaid;
        this.m_dTendered = dTendered;
    }

    public PaymentInfoCash(double dTotal, double dPaid, double dTendered, double prePayAmount) {
        this(dTotal, dTendered, dPaid);
        this.prePayAmount = prePayAmount;
    }

    public PaymentInfoCash(double dTotal, double dPaid) {
        this.m_dTotal = dTotal;
        this.m_dPaid = dPaid;
    }

    @Override
    public PaymentInfo copyPayment() {
        return new PaymentInfoCash(this.m_dTotal, this.m_dPaid, this.m_dTendered, this.prePayAmount);
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

    public boolean hasPrePay() {
        return this.prePayAmount > 0.0;
    }

    public double getPrePaid() {
        return this.prePayAmount;
    }

    public String printTendered() {
        return Formats.CURRENCY.formatValue(this.m_dTendered);
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

