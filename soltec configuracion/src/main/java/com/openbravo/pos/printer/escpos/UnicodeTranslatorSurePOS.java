/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.printer.escpos;

import com.openbravo.pos.printer.escpos.UnicodeTranslator;

public class UnicodeTranslatorSurePOS
extends UnicodeTranslator {
    @Override
    public byte[] getCodeTable() {
        return new byte[]{2};
    }

    @Override
    public byte transChar(char sChar) {
        if (sChar >= '\u0000' && sChar < '\u0080') {
            return (byte)sChar;
        }
        switch (sChar) {
            case '\u00c7': {
                return -128;
            }
            case '\u00fc': {
                return -127;
            }
            case '\u00e2': {
                return -125;
            }
            case '\u00e4': {
                return -124;
            }
            case '\u00e0': {
                return -123;
            }
            case '\u00e5': {
                return -122;
            }
            case '\u00e7': {
                return -121;
            }
            case '\u00ea': {
                return -120;
            }
            case '\u00eb': {
                return -119;
            }
            case '\u00e8': {
                return -118;
            }
            case '\u00ef': {
                return -117;
            }
            case '\u00ee': {
                return -116;
            }
            case '\u00ec': {
                return -115;
            }
            case '\u00c4': {
                return -114;
            }
            case '\u00c5': {
                return -113;
            }
            case '\u00c9': {
                return -112;
            }
            case '\u00e6': {
                return -111;
            }
            case '\u00c6': {
                return -110;
            }
            case '\u00f4': {
                return -109;
            }
            case '\u00f6': {
                return -108;
            }
            case '\u00f2': {
                return -107;
            }
            case '\u00fb': {
                return -106;
            }
            case '\u00f9': {
                return -105;
            }
            case '\u00ff': {
                return -104;
            }
            case '\u00d6': {
                return -103;
            }
            case '\u00dc': {
                return -102;
            }
            case '\u00f8': {
                return -101;
            }
            case '\u00a3': {
                return -100;
            }
            case '\u00d8': {
                return -99;
            }
            case '\u00d7': {
                return -98;
            }
            case '\u00e1': {
                return -96;
            }
            case '\u00e9': {
                return -126;
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
            case '\u00aa': {
                return -90;
            }
            case '\u00ba': {
                return -89;
            }
            case '\u00bf': {
                return -88;
            }
            case '\u00ae': {
                return -87;
            }
            case '\u00ac': {
                return -86;
            }
            case '\u00bd': {
                return -85;
            }
            case '\u00bc': {
                return -84;
            }
            case '\u00a1': {
                return -83;
            }
            case '\u00ab': {
                return -82;
            }
            case '\u00bb': {
                return -81;
            }
            case '\u2591': {
                return -80;
            }
            case '\u2592': {
                return -79;
            }
            case '\u2593': {
                return -78;
            }
            case '\u2502': {
                return -77;
            }
            case '\u2524': {
                return -76;
            }
            case '\u00c1': {
                return -75;
            }
            case '\u00c2': {
                return -74;
            }
            case '\u00c0': {
                return -73;
            }
            case '\u00a9': {
                return -72;
            }
            case '\u2563': {
                return -71;
            }
            case '\u2551': {
                return -70;
            }
            case '\u2557': {
                return -69;
            }
            case '\u255d': {
                return -68;
            }
            case '\u00a2': {
                return -67;
            }
            case '\u00a5': {
                return -66;
            }
            case '\u2510': {
                return -65;
            }
            case '\u2514': {
                return -64;
            }
            case '\u00ca': {
                return -46;
            }
            case '\u00cb': {
                return -45;
            }
            case '\u00c8': {
                return -44;
            }
            case '\u20ac': {
                return -43;
            }
            case '\u00cd': {
                return -42;
            }
            case '\u00ce': {
                return -41;
            }
            case '\u00cf': {
                return -40;
            }
            case '|': {
                return -35;
            }
            case '\u00cc': {
                return -34;
            }
            case '\u00d3': {
                return -32;
            }
            case '\u00d4': {
                return -30;
            }
            case '\u00d2': {
                return -29;
            }
            case '\u00da': {
                return -23;
            }
            case '\u00db': {
                return -22;
            }
            case '\u00d9': {
                return -21;
            }
            case '\u00fd': {
                return -20;
            }
            case '\u00dd': {
                return -19;
            }
            case '\u00b4': {
                return -17;
            }
            case '\u00a8': {
                return -7;
            }
        }
        return 63;
    }
}

