/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.printer.escpos;

public abstract class UnicodeTranslator {
    public abstract byte[] getCodeTable();

    public final byte[] transString(String sCad) {
        if (sCad == null) {
            return null;
        }
        byte[] bAux = new byte[sCad.length()];
        for (int i = 0; i < sCad.length(); ++i) {
            bAux[i] = this.transChar(sCad.charAt(i));
        }
        return bAux;
    }

    public abstract byte transChar(char var1);
}

