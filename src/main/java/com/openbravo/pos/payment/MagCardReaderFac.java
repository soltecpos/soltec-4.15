/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.payment;

import com.openbravo.pos.payment.MagCardReader;
import com.openbravo.pos.payment.MagCardReaderGeneric;
import com.openbravo.pos.payment.MagCardReaderIntelligent;

public class MagCardReaderFac {
    private MagCardReaderFac() {
    }

    public static MagCardReader getMagCardReader(String sReader) {
        switch (sReader) {
            case "Intelligent": {
                return new MagCardReaderIntelligent();
            }
            case "Generic": {
                return new MagCardReaderGeneric();
            }
        }
        return null;
    }
}

