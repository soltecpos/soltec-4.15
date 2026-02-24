/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.JLabel;

public class ThumbNailBuilder {
    private Image m_imgdefault;
    private int m_width;
    private int m_height;

    public ThumbNailBuilder(int width, int height) {
        this.init(width, height, null);
    }

    public ThumbNailBuilder(int width, int height, Image imgdef) {
        this.init(width, height, imgdef);
    }

    public ThumbNailBuilder(int width, int height, String img) {
        try {
            this.init(width, height, ImageIO.read(this.getClass().getClassLoader().getResourceAsStream(img)));
        }
        catch (Exception fnfe) {
            this.init(width, height, null);
        }
    }

    private void init(int width, int height, Image imgdef) {
        this.m_width = width;
        this.m_height = height;
        this.m_imgdefault = imgdef == null ? null : this.createThumbNail(imgdef);
    }

    public Image getThumbNail(Image img) {
        if (img == null) {
            return this.m_imgdefault;
        }
        return this.createThumbNail(img);
    }

    public Image getThumbNailText(Image img, String text) {
        img = this.getThumbNail(img);
        BufferedImage imgtext = new BufferedImage(img.getWidth(null), img.getHeight(null), 2);
        Graphics2D g2d = imgtext.createGraphics();
        JLabel label = new JLabel();
        label.setOpaque(false);
        label.setText(text);
        label.setHorizontalAlignment(0);
        label.setVerticalAlignment(3);
        Dimension d = label.getPreferredSize();
        label.setBounds(0, 0, imgtext.getWidth(), d.height);
        Color c1 = new Color(255, 255, 255, 64);
        Color c2 = new Color(255, 255, 255, 208);
        GradientPaint gpaint = new GradientPaint(new Point(0, 0), c1, new Point(label.getWidth() / 2, 0), c2, true);
        g2d.drawImage(img, 0, 0, null);
        g2d.translate(0, imgtext.getHeight() - label.getHeight());
        g2d.setPaint(gpaint);
        g2d.fillRect(0, 0, imgtext.getWidth(), label.getHeight());
        label.paint(g2d);
        g2d.dispose();
        return imgtext;
    }

    private Image createThumbNail(Image img) {
        int targeth;
        int targetw;
        double scaley;
        double scalex = (double)this.m_width / (double)img.getWidth(null);
        if (scalex < (scaley = (double)this.m_height / (double)img.getHeight(null))) {
            targetw = this.m_width;
            targeth = (int)((double)img.getHeight(null) * scalex);
        } else {
            targetw = (int)((double)img.getWidth(null) * scaley);
            targeth = this.m_height;
        }
        int midw = img.getWidth(null);
        int midh = img.getHeight(null);
        BufferedImage midimg = null;
        Graphics2D g2d = null;
        Image previmg = img;
        int prevw = img.getWidth(null);
        int prevh = img.getHeight(null);
        do {
            if (midw > targetw) {
                if ((midw /= 2) < targetw) {
                    midw = targetw;
                }
            } else {
                midw = targetw;
            }
            if (midh > targeth) {
                if ((midh /= 2) < targeth) {
                    midh = targeth;
                }
            } else {
                midh = targeth;
            }
            if (midimg == null) {
                midimg = new BufferedImage(midw, midh, 2);
                g2d = midimg.createGraphics();
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            }
            g2d.drawImage(previmg, 0, 0, midw, midh, 0, 0, prevw, prevh, null);
            prevw = midw;
            prevh = midh;
            previmg = midimg;
        } while (midw != targetw || midh != targeth);
        g2d.dispose();
        if (this.m_width != midimg.getWidth() || this.m_height != midimg.getHeight()) {
            midimg = new BufferedImage(this.m_width, this.m_height, 2);
            int x = this.m_width > targetw ? (this.m_width - targetw) / 2 : 0;
            int y = this.m_height > targeth ? (this.m_height - targeth) / 2 : 0;
            g2d = midimg.createGraphics();
            g2d.drawImage(previmg, x, y, x + targetw, y + targeth, 0, 0, targetw, targeth, null);
            g2d.dispose();
            previmg = midimg;
        }
        return previmg;
    }
}

