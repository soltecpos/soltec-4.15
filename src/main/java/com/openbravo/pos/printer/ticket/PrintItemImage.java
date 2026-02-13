/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.printer.ticket;

import com.openbravo.pos.printer.ticket.PrintItem;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class PrintItemImage
implements PrintItem {
    protected BufferedImage image;
    protected double scale;

    public PrintItemImage(BufferedImage image, double scale) {
        this.image = image;
        this.scale = scale;
    }

    @Override
    public void draw(Graphics2D g, int x, int y, int width) {
        g.drawImage(this.image, x + (width - (int)((double)this.image.getWidth() * this.scale)) / 2, y, (int)((double)this.image.getWidth() * this.scale), (int)((double)this.image.getHeight() * this.scale), null);
    }

    @Override
    public int getHeight() {
        return (int)((double)this.image.getHeight() * this.scale);
    }
}

