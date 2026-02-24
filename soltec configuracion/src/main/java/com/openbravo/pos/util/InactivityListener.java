/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.util;

import java.awt.AWTEvent;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Action;
import javax.swing.Timer;

public class InactivityListener
implements ActionListener,
AWTEventListener {
    public static final long KEY_EVENTS = 8L;
    public static final long MOUSE_EVENTS = 48L;
    public static final long USER_EVENTS = 56L;
    private Action action;
    private int interval;
    private long eventMask;
    private Timer timer = new Timer(0, this);

    public InactivityListener(Action action, int seconds) {
        this.action = action;
        this.eventMask = 56L;
        this.timer.setInitialDelay(seconds);
    }

    public void start() {
        this.timer.setRepeats(false);
        this.timer.start();
        Toolkit.getDefaultToolkit().addAWTEventListener(this, this.eventMask);
    }

    public void stop() {
        Toolkit.getDefaultToolkit().removeAWTEventListener(this);
        this.timer.stop();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.action.actionPerformed(e);
    }

    @Override
    public void eventDispatched(AWTEvent e) {
        if (this.timer.isRunning()) {
            this.timer.restart();
        }
    }

    public void restart() {
        this.timer.restart();
    }

    public void setRunning() {
        if (!this.timer.isRunning()) {
            this.timer.restart();
        }
    }
}

