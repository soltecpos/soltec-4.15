/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.payment;

import com.openbravo.pos.payment.PaymentInfo;

public class PaymentInfoMagcard
extends PaymentInfo {
    protected double m_dTotal;
    protected double m_dTip;
    protected String m_sHolderName;
    protected String m_sCardNumber;
    protected String m_sExpirationDate;
    protected String track1;
    protected String track2;
    protected String track3;
    protected String m_sTransactionID;
    protected String m_sAuthorization;
    protected String m_sErrorMessage;
    protected String m_sReturnMessage;
    protected String encryptedTrack;
    protected String encryptionKey;

    public PaymentInfoMagcard(String sHolderName, String sCardNumber, String sExpirationDate, String track1, String track2, String track3, String encryptedCard, String encryptKey, String sTransactionID, double dTotal) {
        this.m_sHolderName = sHolderName;
        this.m_sCardNumber = sCardNumber;
        this.m_sExpirationDate = sExpirationDate;
        this.track1 = track1;
        this.track2 = track2;
        this.track3 = track3;
        this.encryptedTrack = encryptedCard;
        this.encryptionKey = encryptKey;
        this.m_sTransactionID = sTransactionID;
        this.m_dTotal = dTotal;
        this.m_sAuthorization = null;
        this.m_sErrorMessage = null;
        this.m_sReturnMessage = null;
    }

    public PaymentInfoMagcard(String sHolderName, String sCardNumber, String sExpirationDate, String sTransactionID, double dTotal) {
        this(sHolderName, sCardNumber, sExpirationDate, null, null, null, null, null, sTransactionID, dTotal);
    }

    @Override
    public PaymentInfo copyPayment() {
        PaymentInfoMagcard p = new PaymentInfoMagcard(this.m_sHolderName, this.m_sCardNumber, this.m_sExpirationDate, this.track1, this.track2, this.track3, this.encryptedTrack, this.encryptionKey, this.m_sTransactionID, this.m_dTotal);
        p.m_sAuthorization = this.m_sAuthorization;
        p.m_sErrorMessage = this.m_sErrorMessage;
        return p;
    }

    @Override
    public String getName() {
        return "magcard";
    }

    @Override
    public double getTotal() {
        return this.m_dTotal;
    }

    public double getTip() {
        return this.m_dTip;
    }

    public boolean isPaymentOK() {
        return this.m_sAuthorization != null;
    }

    public String getHolderName() {
        return this.m_sHolderName;
    }

    @Override
    public String getCardName() {
        return this.getCardType(this.m_sCardNumber);
    }

    public String getCardNumber() {
        return this.m_sCardNumber;
    }

    public String getExpirationDate() {
        return this.m_sExpirationDate;
    }

    @Override
    public String getTransactionID() {
        return this.m_sTransactionID;
    }

    public String getEncryptedCardData() {
        return this.encryptedTrack;
    }

    public String getEncryptionKey() {
        return this.encryptionKey;
    }

    public String getCardType(String sCardNumber) {
        String c = "UNKNOWN";
        if (sCardNumber.startsWith("4")) {
            c = "VISA";
        } else if (sCardNumber.startsWith("6")) {
            c = "DISC";
        } else if (sCardNumber.startsWith("5")) {
            c = "MAST";
        } else if (sCardNumber.startsWith("34") || sCardNumber.startsWith("37")) {
            c = "AMEX";
        } else if (sCardNumber.startsWith("3528") || sCardNumber.startsWith("3589")) {
            c = "JCB";
        } else if (sCardNumber.startsWith("3")) {
            c = "DINE";
        }
        this.m_sCardNumber = c;
        return c;
    }

    public String getTrack1(boolean framingChar) {
        return framingChar ? this.track1 : this.track1.substring(1, this.track1.length() - 2);
    }

    public String getTrack2(boolean framingChar) {
        return framingChar ? this.track2 : this.track2.substring(1, this.track2.length() - 2);
    }

    public String getTrack3(boolean framingChar) {
        return framingChar ? this.track3 : this.track3.substring(1, this.track3.length() - 2);
    }

    public String getAuthorization() {
        return this.m_sAuthorization;
    }

    public String getMessage() {
        return this.m_sErrorMessage;
    }

    public void paymentError(String sMessage, String moreInfo) {
        this.m_sAuthorization = null;
        this.m_sErrorMessage = sMessage + "\n" + moreInfo;
    }

    public void setReturnMessage(String returnMessage) {
        this.m_sReturnMessage = returnMessage;
    }

    public String getReturnMessage() {
        return this.m_sReturnMessage;
    }

    public void paymentOK(String sAuthorization, String sTransactionId, String sReturnMessage) {
        this.m_sAuthorization = sAuthorization;
        this.m_sTransactionID = sTransactionId;
        this.m_sReturnMessage = sReturnMessage;
        this.m_sErrorMessage = null;
    }

    public String printCardNumber() {
        if (this.m_sCardNumber.length() > 4) {
            return this.m_sCardNumber.substring(0, this.m_sCardNumber.length() - 4).replaceAll("\\.", "*") + this.m_sCardNumber.substring(this.m_sCardNumber.length() - 4);
        }
        return "****";
    }

    public String printExpirationDate() {
        return this.m_sExpirationDate;
    }

    public String printAuthorization() {
        return this.m_sAuthorization;
    }

    public String printTransactionID() {
        return this.m_sTransactionID;
    }

    public boolean getIsProcessed() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setIsProcessed(boolean value) {
        throw new UnsupportedOperationException("Not supported yet.");
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
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getVoucher() {
        return null;
    }
}

