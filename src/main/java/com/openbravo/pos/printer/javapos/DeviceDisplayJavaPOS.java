/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jpos.JposException
 *  jpos.LineDisplay
 */
package com.openbravo.pos.printer.javapos;

import com.openbravo.pos.printer.DeviceDisplay;
import com.openbravo.pos.printer.DeviceDisplayBase;
import com.openbravo.pos.printer.DeviceDisplayImpl;
import com.openbravo.pos.printer.TicketPrinterException;
import javax.swing.JComponent;
import jpos.JposException;
import jpos.LineDisplay;

public class DeviceDisplayJavaPOS
implements DeviceDisplay,
DeviceDisplayImpl {
    private String m_sName;
    private LineDisplay m_ld;
    private DeviceDisplayBase m_displaylines;

    public DeviceDisplayJavaPOS(String sDeviceName) throws TicketPrinterException {
        this.m_sName = sDeviceName;
        this.m_ld = new LineDisplay();
        try {
            this.m_ld.open(this.m_sName);
            this.m_ld.claim(10000);
            this.m_ld.setDeviceEnabled(true);
        }
        catch (JposException e) {
            throw new TicketPrinterException(e.getMessage(), e);
        }
        this.m_displaylines = new DeviceDisplayBase(this);
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

    @Override
    public void repaintLines() {
        try {
            this.m_ld.displayTextAt(0, 0, this.m_displaylines.getLine1(), 0);
            this.m_ld.displayTextAt(1, 0, this.m_displaylines.getLine2(), 0);
        }
        catch (JposException jposException) {
            // empty catch block
        }
    }

    public void finalize() throws Throwable {
        this.m_ld.setDeviceEnabled(false);
        this.m_ld.release();
        this.m_ld.close();
        super.finalize();
    }
}

