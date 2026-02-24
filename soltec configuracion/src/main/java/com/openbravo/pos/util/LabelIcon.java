/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.util;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.Icon;
import javax.swing.JLabel;

public class LabelIcon
extends JLabel
implements Icon {
    private int iconwidth;
    private int iconheight;

    public LabelIcon(int width, int height) {
        this.iconwidth = width;
        this.iconheight = height;
    }

    public BufferedImage getImage(int mywidth, int myheight) {
        BufferedImage imgtext = new BufferedImage(mywidth, myheight, 2);
        Graphics2D g2d = imgtext.createGraphics();
        this.setBounds(0, 0, mywidth, myheight);
        this.paint(g2d);
        g2d.dispose();
        return imgtext;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        this.setBounds(0, 0, this.iconwidth, this.iconheight);
        g.translate(x, y);
        this.paint(g);
        g.translate(-x, -y);
    }

    @Override
    public int getIconWidth() {
        return this.iconwidth;
    }

    @Override
    public int getIconHeight() {
        return this.iconheight;
    }
}

