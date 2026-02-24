/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.printer.escpos;

import com.openbravo.pos.printer.DeviceTicket;
import com.openbravo.pos.printer.escpos.DeviceDisplaySerial;
import com.openbravo.pos.printer.escpos.ESCPOS;
import com.openbravo.pos.printer.escpos.PrinterWritter;
import com.openbravo.pos.printer.escpos.UnicodeTranslator;

public class DeviceDisplayESCPOS
extends DeviceDisplaySerial {
    private UnicodeTranslator trans;

    public DeviceDisplayESCPOS(PrinterWritter display, UnicodeTranslator trans) {
        this.trans = trans;
        this.init(display);
    }

    @Override
    public void initVisor() {
        this.display.init(ESCPOS.INIT);
        this.display.write(ESCPOS.SELECT_DISPLAY);
        this.display.write(this.trans.getCodeTable());
        this.display.write(ESCPOS.VISOR_HIDE_CURSOR);
        this.display.write(ESCPOS.VISOR_CLEAR);
        this.display.write(ESCPOS.VISOR_HOME);
        this.display.flush();
    }

    @Override
    public void repaintLines() {
        this.display.write(ESCPOS.SELECT_DISPLAY);
        this.display.write(ESCPOS.VISOR_CLEAR);
        this.display.write(ESCPOS.VISOR_HOME);
        this.display.write(this.trans.transString(DeviceTicket.alignLeft(this.m_displaylines.getLine1(), 20)));
        this.display.write(this.trans.transString(DeviceTicket.alignLeft(this.m_displaylines.getLine2(), 20)));
        this.display.flush();
    }
}

