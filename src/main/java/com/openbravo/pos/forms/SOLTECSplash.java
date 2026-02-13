/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.forms;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JWindow;
import javax.swing.Timer;

public class SOLTECSplash
extends JWindow {
    private int progress = 0;
    private Timer timer;
    private String loadingText = "Loading...";

    public SOLTECSplash() {
        this.setSize(500, 300);
        this.centerWindow();
        this.timer = new Timer(50, new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                SOLTECSplash.this.progress++;
                if (SOLTECSplash.this.progress > 100) {
                    SOLTECSplash.this.progress = 0;
                }
                if (SOLTECSplash.this.progress % 20 == 0) {
                    if (SOLTECSplash.this.loadingText.length() < 10) {
                        SOLTECSplash.this.loadingText = SOLTECSplash.this.loadingText + ".";
                    } else {
                        SOLTECSplash.this.loadingText = "Loading";
                    }
                }
                SOLTECSplash.this.repaint();
            }
        });
    }

    private void centerWindow() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screen.width - this.getWidth()) / 2;
        int y = (screen.height - this.getHeight()) / 2;
        this.setLocation(x, y);
    }

    public void showSplash() {
        this.setVisible(true);
        this.timer.start();
    }

    public void closeSplash() {
        this.timer.stop();
        this.setVisible(false);
        this.dispose();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        Color color1 = new Color(30, 30, 40);
        Color color2 = new Color(10, 10, 15);
        GradientPaint gp = new GradientPaint(0.0f, 0.0f, color1, 0.0f, this.getHeight(), color2);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
        g2d.setColor(Color.WHITE);
        Font font = new Font("Arial", 1, 48);
        g2d.setFont(font);
        String text = "SOLTEC";
        FontMetrics fm = g2d.getFontMetrics();
        int textX = (this.getWidth() - fm.stringWidth(text)) / 2;
        int textY = (this.getHeight() - fm.getHeight()) / 2 + fm.getAscent() - 20;
        g2d.drawString(text, textX, textY);
        g2d.setColor(new Color(200, 200, 200));
        g2d.setFont(new Font("Arial", 0, 16));
        String sub = "Point of Sale System";
        fm = g2d.getFontMetrics();
        g2d.drawString(sub, (this.getWidth() - fm.stringWidth(sub)) / 2, textY + 30);
        int barWidth = 300;
        int barHeight = 4;
        int barX = (this.getWidth() - barWidth) / 2;
        int barY = this.getHeight() - 50;
        g2d.setColor(new Color(60, 60, 70));
        g2d.fillRoundRect(barX, barY, barWidth, barHeight, 4, 4);
        g2d.setColor(new Color(63, 169, 245));
        int animWidth = 80;
        int animX = barX + (int)((double)(this.getWidth() - 200) * (Math.sin((double)System.currentTimeMillis() / 300.0) + 1.0) / 2.0);
        int maxPos = barWidth - animWidth;
        int pos = (int)((double)Math.abs(this.progress % 100 - 50) * 2.0 / 100.0 * (double)maxPos);
        int p = (int)(System.currentTimeMillis() / 10L % (long)(barWidth + animWidth)) - animWidth;
        g2d.setClip(barX, barY, barWidth, barHeight);
        g2d.fillRect(barX + p, barY, animWidth, barHeight);
        g2d.setClip(null);
        g2d.setColor(Color.GRAY);
        g2d.setFont(new Font("SansSerif", 0, 12));
        g2d.drawString(this.loadingText, (this.getWidth() - g2d.getFontMetrics().stringWidth(this.loadingText)) / 2, barY + 25);
        g2d.setColor(new Color(80, 80, 90));
        g2d.drawRect(0, 0, this.getWidth() - 1, this.getHeight() - 1);
    }
}

