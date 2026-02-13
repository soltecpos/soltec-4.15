/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.printer.screen;

import com.openbravo.pos.printer.ticket.BasicTicket;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Toolkit;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

class JTicket
extends JPanel {
    private static final int H_GAP = 8;
    private static final int V_GAP = 8;
    private static final int COLUMNS = 42;
    private static final int LINEWIDTH = 294;
    private final BasicTicket basict;
    private final Map desktophints;

    public JTicket(BasicTicket t) {
        this.basict = t;
        this.desktophints = (Map)Toolkit.getDefaultToolkit().getDesktopProperty("awt.font.desktophints");
        this.initComponents();
    }

    @Override
    protected void paintComponent(Graphics g) {
        this.paintBorder(g);
        Graphics2D g2d = (Graphics2D)g;
        if (this.desktophints != null) {
            g2d.addRenderingHints(this.desktophints);
        }
        Insets i = this.getInsets();
        g2d.setPaint(new GradientPaint(this.getWidth() - i.left - i.right - 100, this.getHeight() - i.top - i.bottom - 100, this.getBackground(), this.getWidth() - i.left - i.right, this.getHeight() - i.top - i.bottom, new Color(0xF0F0F0), true));
        g2d.fillRect(i.left, i.top, this.getWidth() - i.left - i.right, this.getHeight() - i.top - i.bottom);
        g.setColor(this.getForeground());
        this.basict.draw(g2d, i.left + 8, i.top + 8, 294);
    }

    @Override
    public Dimension getPreferredSize() {
        Insets ins = this.getInsets();
        return new Dimension(310 + ins.left + ins.right, this.basict.getHeight() + 16 + ins.top + ins.bottom);
    }

    @Override
    public Dimension getMaximumSize() {
        return this.getPreferredSize();
    }

    @Override
    public Dimension getMinimumSize() {
        return this.getPreferredSize();
    }

    private void initComponents() {
        this.setBackground(new Color(255, 255, 255));
        this.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        this.setFont(new Font("Arial", 0, 14));
        this.setLayout(new BorderLayout());
    }
}

