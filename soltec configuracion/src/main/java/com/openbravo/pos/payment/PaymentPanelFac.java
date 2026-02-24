/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.payment;

import com.openbravo.pos.payment.JPaymentNotifier;
import com.openbravo.pos.payment.MagCardReaderGeneric;
import com.openbravo.pos.payment.MagCardReaderIntelligent;
import com.openbravo.pos.payment.PaymentPanel;
import com.openbravo.pos.payment.PaymentPanelBasic;
import com.openbravo.pos.payment.PaymentPanelMagCard;
import com.openbravo.pos.payment.PaymentPanelType;

public class PaymentPanelFac {
    private PaymentPanelFac() {
    }

    public static PaymentPanel getPaymentPanel(String sReader, JPaymentNotifier notifier) {
        switch (sReader) {
            case "Intelligent": {
                return new PaymentPanelMagCard(new MagCardReaderIntelligent(), notifier);
            }
            case "Generic": {
                return new PaymentPanelMagCard(new MagCardReaderGeneric(), notifier);
            }
            case "Keyboard": {
                return new PaymentPanelType(notifier);
            }
        }
        return new PaymentPanelBasic(notifier);
    }
}

