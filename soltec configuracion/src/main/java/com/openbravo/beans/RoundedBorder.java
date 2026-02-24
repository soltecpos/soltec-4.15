/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.beans;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.Stroke;
import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;

public class RoundedBorder
extends AbstractBorder {
    private static Border blackLine;
    private static Border grayLine;
    private static Border gradientBorder;
    protected Color colorBorder;
    protected Color colorgradient;
    protected int roundedRadius;
    protected float thickness;
    protected boolean filled;
    private float ftop;
    private float fbottom;
    private float ftopinset;
    private float fbottominset;

    public static Border createBlackLineBorder() {
        if (blackLine == null) {
            blackLine = new RoundedBorder(Color.BLACK);
        }
        return blackLine;
    }

    public static Border createGrayLineBorder() {
        if (grayLine == null) {
            grayLine = new RoundedBorder(Color.GRAY);
        }
        return grayLine;
    }

    public static Border createGradientBorder() {
        if (gradientBorder == null) {
            gradientBorder = new RoundedBorder(Color.GRAY, 0.0f, 8, false, false);
        }
        return gradientBorder;
    }

    public RoundedBorder(Color colorBorder) {
        this(colorBorder, Color.WHITE, 1.0f, 0, true, true);
    }

    public RoundedBorder(Color colorBorder, float thickness) {
        this(colorBorder, Color.WHITE, thickness, 0, true, true);
    }

    public RoundedBorder(Color colorBorder, float thickness, int roundedRadius) {
        this(colorBorder, Color.WHITE, thickness, roundedRadius, true, true);
    }

    public RoundedBorder(Color colorBorder, float thickness, int roundedRadius, boolean btopborder, boolean bbottomborder) {
        this(colorBorder, Color.WHITE, thickness, roundedRadius, btopborder, bbottomborder);
    }

    public RoundedBorder(Color colorBorder, Color colorgradient, float thickness, int roundedRadius, boolean btopborder, boolean bbottomborder) {
        this.colorBorder = colorBorder;
        this.colorgradient = colorgradient;
        this.thickness = thickness;
        this.roundedRadius = roundedRadius;
        this.filled = true;
        this.ftop = btopborder ? 0.0f : thickness + (float)roundedRadius;
        this.fbottom = bbottomborder ? 0.0f : thickness + (float)roundedRadius;
        this.ftopinset = btopborder ? 0.0f : thickness;
        this.fbottominset = bbottomborder ? 0.0f : thickness;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2d = (Graphics2D)g;
        Object oldAntialias = g2d.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
        Stroke oldStroke = g2d.getStroke();
        Paint oldColor = g2d.getPaint();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        float imedium = this.thickness;
        if (this.filled) {
            if (c.getComponentOrientation() == ComponentOrientation.RIGHT_TO_LEFT) {
                g2d.setPaint(new GradientPaint(0.0f, 0.0f, c.getBackground(), width, 0.0f, this.colorgradient));
            } else {
                g2d.setPaint(new GradientPaint(0.0f, 0.0f, this.colorgradient, width, 0.0f, c.getBackground()));
            }
            g2d.fillRoundRect((int)((float)x + this.thickness), (int)((float)y + this.thickness - this.ftop), (int)((float)width - this.thickness - this.thickness), (int)((float)height - this.thickness - this.thickness + this.ftop + this.fbottom), (int)((float)(this.roundedRadius * 2) - imedium), (int)((float)(this.roundedRadius * 2) - imedium));
        }
        if (this.thickness > 0.0f) {
            g2d.setStroke(new BasicStroke(this.thickness));
            g2d.setPaint(this.colorBorder);
            g2d.drawRoundRect(x, (int)((float)y - this.ftop), (int)((float)width - this.thickness), (int)((float)height - this.thickness + this.ftop + this.fbottom), this.roundedRadius * 2, this.roundedRadius * 2);
        }
        g2d.setPaint(oldColor);
        g2d.setStroke(oldStroke);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, oldAntialias);
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets((int)(0.5 + (double)this.thickness - (double)this.ftopinset), (int)(0.5 + (double)this.thickness + (double)this.roundedRadius), (int)(0.5 + (double)this.thickness - (double)this.fbottominset), (int)(0.5 + (double)this.thickness + (double)this.roundedRadius));
    }

    @Override
    public Insets getBorderInsets(Component c, Insets insets) {
        insets.top = (int)(0.5 + (double)this.thickness - (double)this.ftopinset);
        insets.left = (int)(0.5 + (double)this.thickness + (double)this.roundedRadius);
        insets.bottom = (int)(0.5 + (double)this.thickness - (double)this.fbottominset);
        insets.right = (int)(0.5 + (double)this.thickness + (double)this.roundedRadius);
        return insets;
    }

    public Color getLineColor() {
        return this.colorBorder;
    }

    public float getThickness() {
        return this.thickness;
    }

    public boolean isFilled() {
        return this.filled;
    }

    @Override
    public boolean isBorderOpaque() {
        return true;
    }
}

