/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.util;

import java.awt.FontMetrics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDialog;
import javax.swing.Timer;

public class SwingUtils {
    public static void fadeIn(JDialog dialog) {
        SwingUtils.fadeIn(dialog, 5, 0.05f);
    }

    public static void fadeIn(final JDialog dialog, int delay, final float incrementSize) {
        final Timer timer = new Timer(delay, null);
        timer.setRepeats(true);
        timer.addActionListener(new ActionListener(){
            private float opacity = 0.0f;

            @Override
            public void actionPerformed(ActionEvent e) {
                this.opacity += incrementSize;
                dialog.setOpacity(Math.min(this.opacity, 1.0f));
                if (this.opacity >= 1.0f) {
                    timer.stop();
                }
            }
        });
        dialog.setOpacity(0.0f);
        timer.start();
        dialog.setVisible(true);
    }

    public static void fadeOut(JDialog dialog) {
        SwingUtils.fadeOut(dialog, 5, 0.05f);
    }

    public static void fadeOut(final JDialog dialog, int delay, final float incrementSize) {
        final Timer timer = new Timer(delay, null);
        timer.setRepeats(true);
        timer.addActionListener(new ActionListener(){
            private float opacity = 1.0f;

            @Override
            public void actionPerformed(ActionEvent e) {
                this.opacity -= incrementSize;
                dialog.setOpacity(Math.max(this.opacity, 0.0f));
                if (this.opacity < 0.0f) {
                    timer.stop();
                    dialog.dispose();
                }
            }
        });
        dialog.setOpacity(1.0f);
        timer.start();
    }

    public static void fadeInAndOut(JDialog dialog) {
        SwingUtils.fadeInAndOut(dialog, 50, 0.05f, 10000);
    }

    public static void fadeInAndOut(final JDialog dialog, final int delay, final float incrementSize, final int displayTime) {
        final Timer timer = new Timer(delay, null);
        timer.setRepeats(true);
        timer.addActionListener(new ActionListener(){
            private float opacity = 0.0f;
            private boolean displayed = false;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!this.displayed) {
                    this.opacity += incrementSize;
                    dialog.setOpacity(Math.min(this.opacity, 1.0f));
                    if (this.opacity >= 1.0f) {
                        timer.setDelay(displayTime);
                        this.displayed = true;
                    }
                } else {
                    timer.setDelay(delay);
                    this.opacity -= incrementSize;
                    dialog.setOpacity(Math.max(this.opacity, 0.0f));
                    if (this.opacity < 0.0f) {
                        timer.stop();
                        dialog.dispose();
                    }
                }
            }
        });
        dialog.setOpacity(0.0f);
        timer.start();
        dialog.setVisible(true);
    }

    static FontMetrics getDefaultLabelFontMetrics() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

