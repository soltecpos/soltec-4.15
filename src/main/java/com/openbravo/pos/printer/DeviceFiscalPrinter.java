/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.printer;

import javax.swing.JComponent;

public interface DeviceFiscalPrinter {
    public String getFiscalName();

    public JComponent getFiscalComponent();

    public void beginReceipt();

    public void endReceipt();

    public void printLine(String var1, double var2, double var4, int var6);

    public void printMessage(String var1);

    public void printTotal(String var1, double var2);

    public void printZReport();

    public void printXReport();
}

