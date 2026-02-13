/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.payment;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.DataRead;
import com.openbravo.data.loader.SerializableRead;
import com.openbravo.format.Formats;
import com.openbravo.pos.payment.PaymentInfo;

public class VoucherPaymentInfo
extends PaymentInfo
implements SerializableRead {
    private static final long serialVersionUID = 8865238639097L;
    private double m_dTicket;
    private String m_sName;
    private String m_sVoucher;

    public VoucherPaymentInfo(double dTicket, String sName, String sVoucher) {
        this.m_dTicket = dTicket;
        this.m_sName = sName;
        this.m_sVoucher = sVoucher;
    }

    public VoucherPaymentInfo() {
        this.m_dTicket = 0.0;
        this.m_sName = null;
        this.m_sVoucher = null;
    }

    @Override
    public void readValues(DataRead dr) throws BasicException {
        this.m_sName = dr.getString(1);
        this.m_dTicket = dr.getDouble(2);
        this.m_sVoucher = dr.getString(3);
    }

    @Override
    public PaymentInfo copyPayment() {
        return new VoucherPaymentInfo(this.m_dTicket, this.m_sName, this.m_sVoucher);
    }

    @Override
    public String getName() {
        return this.m_sName;
    }

    @Override
    public double getTotal() {
        return this.m_dTicket;
    }

    @Override
    public String getTransactionID() {
        return null;
    }

    public String printPaid() {
        return Formats.CURRENCY.formatValue(this.m_dTicket);
    }

    public String printVoucherTotal() {
        return Formats.CURRENCY.formatValue(-this.m_dTicket);
    }

    @Override
    public double getPaid() {
        return this.m_dTicket;
    }

    @Override
    public double getChange() {
        return 0.0;
    }

    @Override
    public String getVoucher() {
        return this.m_sVoucher;
    }

    public String getCardType() {
        return null;
    }

    @Override
    public double getTendered() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getCardName() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

