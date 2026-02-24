/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.payment;

import com.openbravo.pos.payment.PaymentInfo;
import com.openbravo.pos.payment.PaymentInfoMagcard;

public class PaymentInfoMagcardRefund
extends PaymentInfoMagcard {
    public PaymentInfoMagcardRefund(String sHolderName, String sCardNumber, String sExpirationDate, String track1, String track2, String track3, String encryptCard, String encryptKey, String sTransactionID, double dTotal) {
        super(sHolderName, sCardNumber, sExpirationDate, track1, track2, track3, encryptCard, encryptKey, sTransactionID, dTotal);
    }

    public PaymentInfoMagcardRefund(String sHolderName, String sCardNumber, String sExpirationDate, String sTransactionID, String encryptedCard, String encryptionKey, double dTotal) {
        super(sHolderName, sCardNumber, sExpirationDate, sTransactionID, dTotal);
    }

    @Override
    public PaymentInfo copyPayment() {
        PaymentInfoMagcardRefund p = new PaymentInfoMagcardRefund(this.m_sHolderName, this.m_sCardNumber, this.m_sExpirationDate, this.track1, this.track2, this.track3, this.encryptedTrack, this.encryptionKey, this.m_sTransactionID, this.m_dTotal);
        p.m_sAuthorization = this.m_sAuthorization;
        p.m_sErrorMessage = this.m_sErrorMessage;
        return p;
    }

    @Override
    public String getName() {
        return "magcardrefund";
    }
}

