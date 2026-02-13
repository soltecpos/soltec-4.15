/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.printer.ticket;

import com.openbravo.pos.printer.ticket.BasicTicket;
import java.awt.Font;
import java.awt.geom.AffineTransform;

public class BasicTicketForScreen
extends BasicTicket {
    private static Font BASEFONT = new Font("Monospaced", 0, 12).deriveFont(AffineTransform.getScaleInstance(1.0, 1.4));
    private static int FONTHEIGHT = 20;
    private static double IMAGE_SCALE = 1.0;

    @Override
    protected Font getBaseFont() {
        return BASEFONT;
    }

    @Override
    protected int getFontHeight() {
        return FONTHEIGHT;
    }

    @Override
    protected double getImageScale() {
        return IMAGE_SCALE;
    }
}

