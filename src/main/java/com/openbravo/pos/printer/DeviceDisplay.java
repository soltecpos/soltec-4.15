/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.printer;

import javax.swing.JComponent;

public interface DeviceDisplay {
    public String getDisplayName();

    public String getDisplayDescription();

    public JComponent getDisplayComponent();

    public void writeVisor(int var1, String var2, String var3);

    public void writeVisor(String var1, String var2);

    public void clearVisor();
}

