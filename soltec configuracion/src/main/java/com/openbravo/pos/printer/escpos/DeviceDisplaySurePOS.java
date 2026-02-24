/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.printer.escpos;

import com.openbravo.pos.printer.DeviceTicket;
import com.openbravo.pos.printer.escpos.DeviceDisplaySerial;
import com.openbravo.pos.printer.escpos.PrinterWritter;
import com.openbravo.pos.printer.escpos.UnicodeTranslator;
import com.openbravo.pos.printer.escpos.UnicodeTranslatorSurePOS;

public class DeviceDisplaySurePOS
extends DeviceDisplaySerial {
    private UnicodeTranslator trans = new UnicodeTranslatorSurePOS();

    public DeviceDisplaySurePOS(PrinterWritter display) {
        this.init(display);
    }

    @Override
    public void initVisor() {
        this.display.write(new byte[]{0, 1});
        this.display.write(new byte[]{2});
        this.display.write(this.trans.getCodeTable());
        this.display.write(new byte[]{17});
        this.display.write(new byte[]{20});
        this.display.write(new byte[]{16, 0});
        this.display.flush();
    }

    @Override
    public void repaintLines() {
        this.display.write(new byte[]{16, 0});
        this.display.write(this.trans.transString(DeviceTicket.alignLeft(this.m_displaylines.getLine1(), 20)));
        this.display.write(new byte[]{16, 20});
        this.display.write(this.trans.transString(DeviceTicket.alignLeft(this.m_displaylines.getLine2(), 20)));
        this.display.flush();
    }
}

