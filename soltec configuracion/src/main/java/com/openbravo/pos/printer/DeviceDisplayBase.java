/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.printer;

import com.openbravo.pos.printer.BlinkAnimator;
import com.openbravo.pos.printer.CurtainAnimator;
import com.openbravo.pos.printer.DeviceDisplayImpl;
import com.openbravo.pos.printer.DisplayAnimator;
import com.openbravo.pos.printer.FlyerAnimator;
import com.openbravo.pos.printer.NullAnimator;
import com.openbravo.pos.printer.ScrollAnimator;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class DeviceDisplayBase {
    public static final int ANIMATION_NULL = 0;
    public static final int ANIMATION_FLYER = 1;
    public static final int ANIMATION_SCROLL = 2;
    public static final int ANIMATION_BLINK = 3;
    public static final int ANIMATION_CURTAIN = 4;
    private DeviceDisplayImpl impl;
    private DisplayAnimator anim;
    private Timer m_tTimeTimer;
    private int counter = 0;

    public DeviceDisplayBase(DeviceDisplayImpl impl) {
        this.impl = impl;
        this.anim = new NullAnimator("", "");
        this.m_tTimeTimer = new Timer(50, new PrintTimeAction());
    }

    public void writeVisor(int animation, String sLine1, String sLine2) {
        this.m_tTimeTimer.stop();
        switch (animation) {
            case 1: {
                this.anim = new FlyerAnimator(sLine1, sLine2);
                break;
            }
            case 2: {
                this.anim = new ScrollAnimator(sLine1, sLine2);
                break;
            }
            case 3: {
                this.anim = new BlinkAnimator(sLine1, sLine2);
                break;
            }
            case 4: {
                this.anim = new CurtainAnimator(sLine1, sLine2);
                break;
            }
            default: {
                this.anim = new NullAnimator(sLine1, sLine2);
            }
        }
        this.counter = 0;
        this.anim.setTiming(this.counter);
        this.impl.repaintLines();
        if (animation != 0) {
            this.counter = 0;
            this.m_tTimeTimer.start();
        }
    }

    public void writeVisor(String sLine1, String sLine2) {
        this.writeVisor(0, sLine1, sLine2);
    }

    public void clearVisor() {
        this.writeVisor(0, "", "");
    }

    public String getLine1() {
        return this.anim.getLine1();
    }

    public String getLine2() {
        return this.anim.getLine2();
    }

    private class PrintTimeAction
    implements ActionListener {
        private PrintTimeAction() {
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            DeviceDisplayBase.this.counter++;
            DeviceDisplayBase.this.anim.setTiming(DeviceDisplayBase.this.counter);
            DeviceDisplayBase.this.impl.repaintLines();
        }
    }
}

