/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.printer;

import com.openbravo.pos.printer.DisplayAnimator;

public abstract class BaseAnimator
implements DisplayAnimator {
    protected String baseLine1;
    protected String baseLine2;
    protected String currentLine1;
    protected String currentLine2;

    public BaseAnimator() {
        this.baseLine1 = null;
        this.baseLine2 = null;
    }

    public BaseAnimator(String line1, String line2) {
        this.baseLine1 = line1;
        this.baseLine2 = line2;
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

