/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.printer.ticket;

import java.awt.Graphics2D;

public interface PrintItem {
    public int getHeight();

    public void draw(Graphics2D var1, int var2, int var3, int var4);
}

