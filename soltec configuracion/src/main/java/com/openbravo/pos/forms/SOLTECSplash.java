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

    private long startTime;

    public void showSplash() {
        this.startTime = System.currentTimeMillis();
        this.setVisible(true);
        // this.timer.start(); // Timer is not needed for GIF, but safe to leave if used for other things
    }

    public void closeSplash() {
        long elapsed = System.currentTimeMillis() - this.startTime;
        long minDuration = 4000; // 4 seconds minimum
        
        if (elapsed < minDuration) {
            int delay = (int) (minDuration - elapsed);
            Timer exitTimer = new Timer(delay, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    realClose();
                }
            });
            exitTimer.setRepeats(false);
            exitTimer.start();
        } else {
            realClose();
        }
    }

    private void realClose() {
        if (this.timer != null) {
            this.timer.stop();
        }
        this.setVisible(false);
        this.dispose();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D)g;
        
        // Background
        g2d.setColor(new Color(30, 30, 40));
        g2d.fillRect(0, 0, this.getWidth(), this.getHeight());

        // Draw Animated GIF
        java.net.URL imgUrl = getClass().getResource("/com/openbravo/images/logoanimado.gif");
        if (imgUrl != null) {
            javax.swing.ImageIcon icon = new javax.swing.ImageIcon(imgUrl);
            // Center the image
            int x = (this.getWidth() - icon.getIconWidth()) / 2;
            int y = (this.getHeight() - icon.getIconHeight()) / 2;
            icon.paintIcon(this, g2d, x, y);
        } else {
             // Fallback text if image not found
            g2d.setColor(Color.WHITE);
            g2d.drawString("SOLTEC", 50, 50);
        }

        // Draw Loading Text (bottom center)
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("SansSerif", Font.PLAIN, 12));
        FontMetrics fm = g2d.getFontMetrics();
        int textX = (this.getWidth() - fm.stringWidth(this.loadingText)) / 2;
        int textY = this.getHeight() - 20;
        g2d.drawString(this.loadingText, textX, textY);
        
        // Border
        g2d.setColor(new Color(80, 80, 90));
        g2d.drawRect(0, 0, this.getWidth() - 1, this.getHeight() - 1);
    }
}

