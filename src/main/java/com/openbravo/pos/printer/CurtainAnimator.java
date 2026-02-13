/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.printer;

import com.openbravo.pos.printer.BaseAnimator;
import com.openbravo.pos.printer.DeviceTicket;

public class CurtainAnimator
extends BaseAnimator {
    public CurtainAnimator(String line1, String line2) {
        this.baseLine1 = DeviceTicket.alignLeft(line1, 20);
        this.baseLine2 = DeviceTicket.alignLeft(line2, 20);
    }

    @Override
    public void setTiming(int i) {
        int j = i / 2;
        if (j < 10) {
            this.currentLine1 = DeviceTicket.alignCenter(this.baseLine1.substring(10 - j, 10 + j), 20);
            this.currentLine2 = DeviceTicket.alignCenter(this.baseLine2.substring(10 - j, 10 + j), 20);
        } else {
            this.currentLine1 = this.baseLine1;
            this.currentLine2 = this.baseLine2;
        }
    }
}

