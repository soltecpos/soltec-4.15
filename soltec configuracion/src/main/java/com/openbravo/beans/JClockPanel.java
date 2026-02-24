/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.beans;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.JPanel;

public class JClockPanel
extends JPanel {
    private static Calendar m_calendar = new GregorianCalendar();
    private Date m_date;
    private boolean m_bSeconds;
    private long m_lPeriod;

    public JClockPanel() {
        this(true);
    }

    public JClockPanel(boolean bSeconds) {
        this.initComponents();
        this.m_bSeconds = bSeconds;
        this.m_date = null;
        this.m_lPeriod = 0L;
    }

    public void setSecondsVisible(boolean bValue) {
        this.m_bSeconds = bValue;
        this.repaint();
    }

    public boolean isSecondsVisible() {
        return this.m_bSeconds;
    }

    public void setPeriod(long period) {
        if (period >= 0L) {
            this.m_lPeriod = period;
            this.repaint();
        }
    }

    public long getPeriod() {
        return this.m_lPeriod;
    }

    public void setTime(Date dDate) {
        this.m_date = dDate;
        this.repaint();
    }

    public Date getTime() {
        return this.m_date;
    }

    @Override
    public void paintComponent(Graphics g) {
        int i;
        super.paintComponent(g);
        int width = this.getWidth();
        int height = this.getHeight();
        double dhour = 0.0;
        double dminute = 0.0;
        double dsecond = 0.0;
        if (this.m_date != null) {
            m_calendar.setTime(this.m_date);
            dhour = m_calendar.get(11);
            dminute = m_calendar.get(12);
            dsecond = m_calendar.get(13);
        }
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Paint oldPainter = g2.getPaint();
        AffineTransform oldt = g2.getTransform();
        int icenterx = width / 2;
        int icentery = height / 2;
        int iradius = Math.min(icenterx, icentery);
        g2.transform(AffineTransform.getTranslateInstance(icenterx, icentery));
        g2.transform(AffineTransform.getScaleInstance((double)iradius / 1100.0, (double)iradius / 1100.0));
        AffineTransform mytrans = g2.getTransform();
        g2.setPaint(this.isEnabled() ? new GradientPaint(-1200.0f, -1200.0f, Color.BLUE, 1200.0f, 1200.0f, Color.CYAN) : new GradientPaint(-1200.0f, -1200.0f, Color.GRAY, 1200.0f, 1200.0f, Color.LIGHT_GRAY));
        g2.fillOval(-1000, -1000, 2000, 2000);
        g2.setPaint(this.isEnabled() ? new GradientPaint(-1200.0f, -1200.0f, Color.CYAN, 1200.0f, 1200.0f, Color.BLUE) : new GradientPaint(-1200.0f, -1200.0f, Color.LIGHT_GRAY, 1200.0f, 1200.0f, Color.GRAY));
        g2.fillOval(-900, -900, 1800, 1800);
        g2.setColor(Color.BLACK);
        g2.drawOval(-1000, -1000, 2000, 2000);
        for (i = 0; i < 60; ++i) {
            g2.setColor(Color.WHITE);
            g2.fillRect(900, -5, 50, 10);
            g2.transform(AffineTransform.getRotateInstance(0.10471975511965977));
        }
        g2.setTransform(mytrans);
        for (i = 0; i < 12; ++i) {
            g2.setColor(Color.WHITE);
            g2.fillRect(800, -15, 150, 30);
            g2.transform(AffineTransform.getRotateInstance(0.5235987755982988));
        }
        if (this.m_date != null) {
            g2.setTransform(mytrans);
            g2.transform(AffineTransform.getRotateInstance((dhour + dminute / 60.0) * Math.PI / 6.0));
            if (this.m_lPeriod > 0L) {
                int iArc = (int)(this.m_lPeriod / 120000L);
                g2.setColor(new Color(255, 255, 255, 100));
                g2.fillArc(-1000, -1000, 2000, 2000, 90 - iArc, iArc);
                g2.setColor(Color.DARK_GRAY);
                g2.drawArc(-1000, -1000, 2000, 2000, 90 - iArc, iArc);
            } else {
                g2.setColor(Color.WHITE);
                g2.fillPolygon(new int[]{0, -35, 0, 35}, new int[]{100, 0, -600, 0}, 4);
                g2.setColor(Color.DARK_GRAY);
                g2.drawPolygon(new int[]{0, -35, 0, 35}, new int[]{100, 0, -600, 0}, 4);
                g2.setTransform(mytrans);
                g2.transform(AffineTransform.getRotateInstance(dminute * Math.PI / 30.0));
                g2.setColor(Color.WHITE);
                g2.fillPolygon(new int[]{0, -35, 0, 35}, new int[]{100, 0, -900, 0}, 4);
                g2.setColor(Color.DARK_GRAY);
                g2.drawPolygon(new int[]{0, -35, 0, 35}, new int[]{100, 0, -900, 0}, 4);
                if (this.m_bSeconds) {
                    g2.setTransform(mytrans);
                    g2.transform(AffineTransform.getRotateInstance(dsecond * Math.PI / 30.0));
                    g2.setColor(Color.YELLOW);
                    g2.fillPolygon(new int[]{-15, 0, 15}, new int[]{200, -900, 200}, 3);
                    g2.setColor(Color.DARK_GRAY);
                    g2.drawPolygon(new int[]{-15, 0, 15}, new int[]{200, -900, 200}, 3);
                    g2.setTransform(mytrans);
                    g2.setColor(Color.YELLOW);
                    g2.fillOval(-25, -25, 50, 50);
                    g2.setColor(Color.DARK_GRAY);
                    g2.drawOval(-25, -25, 50, 50);
                }
            }
        }
        g2.setColor(Color.WHITE);
        g2.fillOval(-10, -10, 20, 20);
        g2.setColor(Color.BLACK);
        g2.drawOval(-10, -10, 20, 20);
        g2.setTransform(oldt);
        g2.setPaint(oldPainter);
    }

    private void initComponents() {
        this.setFont(new Font("Arial", 0, 12));
    }
}

