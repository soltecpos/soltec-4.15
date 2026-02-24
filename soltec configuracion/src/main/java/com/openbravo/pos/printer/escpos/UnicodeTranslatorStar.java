/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.printer.escpos;

import com.openbravo.pos.printer.escpos.UnicodeTranslator;

public class UnicodeTranslatorStar
extends UnicodeTranslator {
    @Override
    public byte[] getCodeTable() {
        return new byte[]{27, 29, 116, 1};
    }

    @Override
    public byte transChar(char sChar) {
        if (sChar >= '\u0000' && sChar < '\u0080') {
            return (byte)sChar;
        }
        switch (sChar) {
            case '\u00c1': {
                return 65;
            }
            case '\u00c9': {
                return 69;
            }
            case '\u00cd': {
                return 73;
            }
            case '\u00d3': {
                return 79;
            }
            case '\u00da': {
                return 85;
            }
            case '\u00c7': {
                return -128;
            }
            case '\u00fc': {
                return -127;
            }
            case '\u00e9': {
                return -126;
            }
            case '\u00e4': {
                return -124;
            }
            case '\u00e5': {
                return -122;
            }
            case '\u00e7': {
                return -121;
            }
            case '\u00c4': {
                return -114;
            }
            case '\u00c5': {
                return -113;
            }
            case '\u00f6': {
                return -108;
            }
            case '\u00d6': {
                return -103;
            }
            case '\u00dc': {
                return -102;
            }
            case '\u00a3': {
                return -100;
            }
            case '\u00a5': {
                return -99;
            }
            case '\u00e1': {
                return -96;
            }
            case '\u00ed': {
                return -95;
            }
            case '\u00f3': {
                return -94;
            }
            case '\u00fa': {
                return -93;
            }
            case '\u00f1': {
                return -92;
            }
            case '\u00d1': {
                return -91;
            }
            case '\u00bf': {
                return -88;
            }
            case '\u00a1': {
                return -83;
            }
            case '\u20ac': {
                return -18;
            }
        }
        return 63;
    }
}

