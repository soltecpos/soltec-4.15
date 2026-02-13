/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.payment;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.DataRead;
import com.openbravo.data.loader.SerializableRead;
import com.openbravo.format.Formats;
import com.openbravo.pos.payment.PaymentInfo;

public class PaymentInfoTicket
extends PaymentInfo
implements SerializableRead {
    private static final long serialVersionUID = 8865238639097L;
    private double m_dTicket;
    private String m_sName;
    private String m_transactionID;
    private double m_dTendered;
    private double m_change;
    private String m_dCardName = null;
    private double m_dTip;
    private boolean m_isProcessed;
    private String m_returnMessage;
    private String m_sVoucher;

    public PaymentInfoTicket(double dTicket, String sName) {
        this.m_sName = sName;
        this.m_dTicket = dTicket;
    }

    public PaymentInfoTicket(double dTicket, String sName, String transactionID, String sVoucher) {
        this.m_sName = sName;
        this.m_dTicket = dTicket;
        this.m_transactionID = transactionID;
        this.m_sVoucher = sVoucher;
    }

    public PaymentInfoTicket(double dTicket, String sName, String sVoucher) {
        this.m_sName = sName;
        this.m_dTicket = dTicket;
        this.m_sVoucher = sVoucher;
    }

    public PaymentInfoTicket() {
        this.m_sName = null;
        this.m_dTicket = 0.0;
        this.m_transactionID = null;
        this.m_dTendered = 0.0;
    }

    @Override
    public void readValues(DataRead dr) throws BasicException {
        this.m_sName = dr.getString(1);
        this.m_dTicket = dr.getDouble(2);
        this.m_transactionID = dr.getString(3);
        if (dr.getDouble(4) != null) {
            this.m_dTendered = dr.getDouble(4);
        }
        this.m_dCardName = dr.getString(5);
    }

    @Override
    public PaymentInfo copyPayment() {
        return new PaymentInfoTicket(this.m_dTicket, this.m_sName);
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
        return this.m_transactionID;
    }

    @Override
    public double getPaid() {
        return 0.0;
    }

    @Override
    public double getChange() {
        return this.m_dTendered - this.m_dTicket;
    }

    @Override
    public double getTendered() {
        return 0.0;
    }

    @Override
    public String getCardName() {
        return this.m_dCardName;
    }

    public String printPaid() {
        return Formats.CURRENCY.formatValue(this.m_dTicket);
    }

    public String printVoucherTotal() {
        return Formats.CURRENCY.formatValue(-this.m_dTicket);
    }

    public String printChange() {
        return Formats.CURRENCY.formatValue(this.m_dTendered - this.m_dTicket);
    }

    public String printTendered() {
        return Formats.CURRENCY.formatValue(this.m_dTendered);
    }

    @Override
    public String getVoucher() {
        return this.m_sVoucher;
    }

    public String printVoucher() {
        return this.m_sVoucher;
    }
}

