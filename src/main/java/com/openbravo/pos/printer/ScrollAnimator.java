/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.printer;

import com.openbravo.pos.printer.BaseAnimator;
import com.openbravo.pos.printer.DeviceTicket;

public class ScrollAnimator
extends BaseAnimator {
    private int msglength;

    public ScrollAnimator(String line1, String line2) {
        this.msglength = Math.max(line1.length(), line2.length());
        this.baseLine1 = DeviceTicket.alignLeft(line1, this.msglength);
        this.baseLine2 = DeviceTicket.alignLeft(line2, this.msglength);
    }

    @Override
    public void setTiming(int i) {
        int j = i / 2 % (this.msglength + 20);
        if (j < 20) {
            this.currentLine1 = DeviceTicket.alignLeft(DeviceTicket.getWhiteString(20 - j) + this.baseLine1, 20);
            this.currentLine2 = DeviceTicket.alignLeft(DeviceTicket.getWhiteString(20 - j) + this.baseLine2, 20);
        } else {
            this.currentLine1 = DeviceTicket.alignLeft(this.baseLine1.substring(j - 20), 20);
            this.currentLine2 = DeviceTicket.alignLeft(this.baseLine2.substring(j - 20), 20);
        }
    }
}

