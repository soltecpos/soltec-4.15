/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.printer.escpos;

import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.printer.DeviceDisplay;
import com.openbravo.pos.printer.DeviceDisplayBase;
import com.openbravo.pos.printer.DeviceDisplayImpl;
import com.openbravo.pos.printer.escpos.PrinterWritter;
import javax.swing.JComponent;

public abstract class DeviceDisplaySerial
implements DeviceDisplay,
DeviceDisplayImpl {
    private String m_sName;
    protected PrinterWritter display;
    protected DeviceDisplayBase m_displaylines = new DeviceDisplayBase(this);

    protected void init(PrinterWritter display) {
        this.m_sName = AppLocal.getIntString("printer.serial");
        this.display = display;
        this.initVisor();
    }

    @Override
    public String getDisplayName() {
        return this.m_sName;
    }

    @Override
    public String getDisplayDescription() {
        return null;
    }

    @Override
    public JComponent getDisplayComponent() {
        return null;
    }

    @Override
    public void writeVisor(int animation, String sLine1, String sLine2) {
        this.m_displaylines.writeVisor(animation, sLine1, sLine2);
    }

    @Override
    public void writeVisor(String sLine1, String sLine2) {
        this.m_displaylines.writeVisor(sLine1, sLine2);
    }

    @Override
    public void clearVisor() {
        this.m_displaylines.clearVisor();
    }

    public abstract void initVisor();
}

