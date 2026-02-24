/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.printer;

import com.openbravo.pos.printer.DeviceTicket;
import com.openbravo.pos.printer.DisplayAnimator;

public class NullAnimator
implements DisplayAnimator {
    protected String currentLine1;
    protected String currentLine2;

    public NullAnimator(String line1, String line2) {
        this.currentLine1 = DeviceTicket.alignLeft(line1, 20);
        this.currentLine2 = DeviceTicket.alignLeft(line2, 20);
    }

    @Override
    public void setTiming(int i) {
    }

    @Override
    public String getLine1() {
        return this.currentLine1;
    }

    @Override
    public String getLine2() {
        return this.currentLine2;
    }
}

