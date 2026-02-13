/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.printer.ticket;

import com.openbravo.pos.printer.ticket.PrintItem;
import com.openbravo.pos.printer.ticket.PrintItemBarcode;
import com.openbravo.pos.printer.ticket.PrintItemImage;
import com.openbravo.pos.printer.ticket.PrintItemLine;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public abstract class BasicTicket
implements PrintItem {
    protected List<PrintItem> m_aCommands = new ArrayList<PrintItem>();
    protected PrintItemLine pil = null;
    protected int m_iBodyHeight = 0;

    protected abstract Font getBaseFont();

    protected abstract int getFontHeight();

    protected abstract double getImageScale();

    @Override
    public int getHeight() {
        return this.m_iBodyHeight;
    }

    @Override
    public void draw(Graphics2D g2d, int x, int y, int width) {
        int currenty = y;
        for (PrintItem pi : this.m_aCommands) {
            pi.draw(g2d, x, currenty, width);
            currenty += pi.getHeight();
        }
    }

    public List<PrintItem> getCommands() {
        return this.m_aCommands;
    }

    public void printImage(BufferedImage image) {
        PrintItemImage pi = new PrintItemImage(image, this.getImageScale());
        this.m_aCommands.add(pi);
        this.m_iBodyHeight += pi.getHeight();
    }

    public void printBarCode(String type, String position, String code) {
        PrintItemBarcode pi = new PrintItemBarcode(type, position, code, this.getImageScale());
        this.m_aCommands.add(pi);
        this.m_iBodyHeight += pi.getHeight();
    }

    public void beginLine(int iTextSize) {
        this.pil = new PrintItemLine(iTextSize, this.getBaseFont(), this.getFontHeight());
    }

    public void printText(int iStyle, String sText) {
        if (this.pil != null) {
            this.pil.addText(iStyle, sText);
        }
    }

    public void endLine() {
        if (this.pil != null) {
            this.m_aCommands.add(this.pil);
            this.m_iBodyHeight += this.pil.getHeight();
            this.pil = null;
        }
    }
}

