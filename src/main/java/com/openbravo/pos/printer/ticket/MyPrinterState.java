/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.printer.ticket;

import java.awt.Font;
import java.awt.geom.AffineTransform;

public class MyPrinterState {
    private int m_iSize;

    public MyPrinterState(int iSize) {
        this.m_iSize = iSize;
    }

    public int getLineMult() {
        return MyPrinterState.getLineMult(this.m_iSize);
    }

    public static int getLineMult(int iSize) {
        switch (iSize) {
            case 0: 
            case 2: {
                return 1;
            }
            case 1: 
            case 3: {
                return 2;
            }
        }
        return 1;
    }

    public Font getFont(Font baseFont, int iStyle) {
        Font f;
        switch (this.m_iSize) {
            case 0: {
                f = baseFont;
                break;
            }
            case 2: {
                AffineTransform a = AffineTransform.getScaleInstance(2.0, 1.0);
                a.preConcatenate(baseFont.getTransform());
                f = baseFont.deriveFont(a);
                break;
            }
            case 1: {
                AffineTransform a = AffineTransform.getScaleInstance(1.0, 2.0);
                a.preConcatenate(baseFont.getTransform());
                f = baseFont.deriveFont(a);
                break;
            }
            case 3: {
                AffineTransform a = AffineTransform.getScaleInstance(2.0, 2.0);
                a.preConcatenate(baseFont.getTransform());
                f = baseFont.deriveFont(a);
                break;
            }
            default: {
                f = baseFont;
            }
        }
        f = f.deriveFont((iStyle & 1) != 0 ? 1 : baseFont.getStyle());
        return f;
    }
}

