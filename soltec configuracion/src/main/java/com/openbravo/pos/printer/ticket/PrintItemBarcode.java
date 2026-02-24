/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.krysalis.barcode4j.BarcodeDimension
 *  org.krysalis.barcode4j.HumanReadablePlacement
 *  org.krysalis.barcode4j.impl.AbstractBarcodeBean
 *  org.krysalis.barcode4j.impl.code128.Code128Bean
 *  org.krysalis.barcode4j.impl.upcean.EAN13Bean
 *  org.krysalis.barcode4j.output.CanvasProvider
 *  org.krysalis.barcode4j.output.java2d.Java2DCanvasProvider
 */
package com.openbravo.pos.printer.ticket;

import com.openbravo.pos.printer.ticket.PrintItem;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import org.krysalis.barcode4j.BarcodeDimension;
import org.krysalis.barcode4j.HumanReadablePlacement;
import org.krysalis.barcode4j.impl.AbstractBarcodeBean;
import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.impl.upcean.EAN13Bean;
import org.krysalis.barcode4j.output.CanvasProvider;
import org.krysalis.barcode4j.output.java2d.Java2DCanvasProvider;

public class PrintItemBarcode
implements PrintItem {
    protected AbstractBarcodeBean m_barcode;
    protected String m_sCode;
    protected int m_iWidth;
    protected int m_iHeight;
    protected double scale;

    public PrintItemBarcode(String type, String position, String code, double scale) {
        this.m_sCode = code;
        this.scale = scale;
        this.m_barcode = "CODE128".equals(type) ? new Code128Bean() : new EAN13Bean();
        if (this.m_barcode != null) {
            this.m_barcode.setModuleWidth(1.0);
            this.m_barcode.setBarHeight(40.0);
            this.m_barcode.setFontSize(10.0);
            this.m_barcode.setQuietZone(10.0);
            this.m_barcode.doQuietZone(true);
            if ("none".equals(position)) {
                this.m_barcode.setMsgPosition(HumanReadablePlacement.HRP_NONE);
            } else {
                this.m_barcode.setMsgPosition(HumanReadablePlacement.HRP_BOTTOM);
            }
            BarcodeDimension dim = this.m_barcode.calcDimensions(this.m_sCode);
            this.m_iWidth = (int)dim.getWidth(0);
            this.m_iHeight = (int)dim.getHeight(0);
        }
    }

    @Override
    public void draw(Graphics2D g, int x, int y, int width) {
        if (this.m_barcode != null) {
            Graphics2D g2d = g;
            AffineTransform oldt = g2d.getTransform();
            g2d.translate(x - 10 + (width - (int)((double)this.m_iWidth * this.scale)) / 2, y + 10);
            g2d.scale(this.scale, this.scale);
            try {
                this.m_barcode.generateBarcode((CanvasProvider)new Java2DCanvasProvider(g2d, 0), this.m_sCode);
            }
            catch (IllegalArgumentException e) {
                g2d.drawRect(0, 0, this.m_iWidth, this.m_iHeight);
                g2d.drawLine(0, 0, this.m_iWidth, this.m_iHeight);
                g2d.drawLine(this.m_iWidth, 0, 0, this.m_iHeight);
            }
            g2d.setTransform(oldt);
        }
    }

    @Override
    public int getHeight() {
        return (int)((double)this.m_iHeight * this.scale) + 20;
    }
}

