/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.printer.escpos;

import com.openbravo.pos.printer.DevicePrinter;
import com.openbravo.pos.printer.TicketPrinterException;
import com.openbravo.pos.printer.escpos.PrinterWritter;
import com.openbravo.pos.printer.escpos.UnicodeTranslator;
import com.openbravo.pos.printer.escpos.UnicodeTranslatorStar;
import java.awt.image.BufferedImage;
import javax.swing.JComponent;

public class DevicePrinterPlain
implements DevicePrinter {
    private static final byte[] NEW_LINE = new byte[]{13, 10};
    private final PrinterWritter out;
    private final UnicodeTranslator trans;

    public DevicePrinterPlain(PrinterWritter CommOutputPrinter) throws TicketPrinterException {
        this.out = CommOutputPrinter;
        this.trans = new UnicodeTranslatorStar();
    }

    @Override
    public String getPrinterName() {
        return "Plain";
    }

    @Override
    public String getPrinterDescription() {
        return null;
    }

    @Override
    public JComponent getPrinterComponent() {
        return null;
    }

    @Override
    public void reset() {
    }

    @Override
    public void beginReceipt() {
    }

    @Override
    public void printImage(BufferedImage image) {
    }

    @Override
    public void printLogo() {
    }

    @Override
    public void printBarCode(String type, String position, String code) {
        if (!"none".equals(position)) {
            this.out.write(code);
            this.out.write(NEW_LINE);
        }
    }

    @Override
    public void beginLine(int iTextSize) {
    }

    @Override
    public void printText(int iStyle, String sText) {
        this.out.write(this.trans.transString(sText));
    }

    @Override
    public void endLine() {
        this.out.write(NEW_LINE);
    }

    @Override
    public void endReceipt() {
        this.out.write(NEW_LINE);
        this.out.write(NEW_LINE);
        this.out.write(NEW_LINE);
        this.out.write(NEW_LINE);
        this.out.write(NEW_LINE);
        this.out.flush();
    }

    @Override
    public void openDrawer() {
    }
}

