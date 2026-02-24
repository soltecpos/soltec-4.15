/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.printer;

import com.openbravo.pos.printer.BaseAnimator;
import com.openbravo.pos.printer.DeviceTicket;

public class BlinkAnimator
extends BaseAnimator {
    public BlinkAnimator(String line1, String line2) {
        this.baseLine1 = DeviceTicket.alignLeft(line1, 20);
        this.baseLine2 = DeviceTicket.alignLeft(line2, 20);
    }

    @Override
    public void setTiming(int i) {
        if (i % 10 < 5) {
            this.currentLine1 = "";
            this.currentLine2 = "";
        } else {
            this.currentLine1 = this.baseLine1;
            this.currentLine2 = this.baseLine2;
        }
    }
}

