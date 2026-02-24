/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.printer.ticket;

import com.openbravo.pos.printer.ticket.MyPrinterState;
import com.openbravo.pos.printer.ticket.PrintItem;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class PrintItemLine
implements PrintItem {
    protected Font font;
    protected int fontheight;
    protected int textsize;
    protected List<StyledText> m_atext;

    public PrintItemLine(int textsize, Font font, int fontheight) {
        this.textsize = textsize;
        this.font = font;
        this.fontheight = fontheight;
        this.m_atext = new ArrayList<StyledText>();
    }

    public void addText(int style, String text) {
        this.m_atext.add(new StyledText(style, text));
    }

    @Override
    public void draw(Graphics2D g, int x, int y, int width) {
        MyPrinterState ps = new MyPrinterState(this.textsize);
        float left = x;
        for (int i = 0; i < this.m_atext.size(); ++i) {
            StyledText t = this.m_atext.get(i);
            g.setFont(ps.getFont(this.font, t.style));
            g.drawString(t.text, left, (float)y);
            left = (float)((double)left + g.getFontMetrics().getStringBounds(t.text, g).getWidth());
        }
    }

    @Override
    public int getHeight() {
        return this.fontheight * MyPrinterState.getLineMult(this.textsize);
    }

    protected static class StyledText {
        public int style;
        public String text;

        public StyledText(int style, String text) {
            this.style = style;
            this.text = text;
        }
    }
}

