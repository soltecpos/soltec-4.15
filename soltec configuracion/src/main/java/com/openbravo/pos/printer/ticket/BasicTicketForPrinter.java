/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.printer.ticket;

import com.openbravo.pos.printer.ticket.BasicTicket;
import java.awt.Font;
import java.awt.geom.AffineTransform;

public class BasicTicketForPrinter
extends BasicTicket {
    private static final Font BASEFONT = new Font("Monospaced", 0, 7).deriveFont(AffineTransform.getScaleInstance(1.0, 1.5));
    private static final int FONTHEIGHT = 14;
    private static final double IMAGE_SCALE = 0.65;

    @Override
    protected Font getBaseFont() {
        return BASEFONT;
    }

    @Override
    protected int getFontHeight() {
        return 14;
    }

    @Override
    protected double getImageScale() {
        return 0.65;
    }
}

