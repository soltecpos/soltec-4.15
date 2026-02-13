/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.printer;

import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.printer.DeviceDisplay;
import javax.swing.JComponent;

public class DeviceDisplayNull
implements DeviceDisplay {
    private String m_sName = AppLocal.getIntString("display.null");
    private String m_sDescription;

    public DeviceDisplayNull() {
        this(null);
    }

    public DeviceDisplayNull(String desc) {
        this.m_sDescription = desc;
    }

    @Override
    public String getDisplayName() {
        return this.m_sName;
    }

    @Override
    public String getDisplayDescription() {
        return this.m_sDescription;
    }

    @Override
    public JComponent getDisplayComponent() {
        return null;
    }

    @Override
    public void clearVisor() {
    }

    @Override
    public void writeVisor(String sLine1, String sLine2) {
    }

    @Override
    public void writeVisor(int animation, String sLine1, String sLine2) {
    }
}

