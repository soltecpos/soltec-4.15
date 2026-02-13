/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.printer.escpos;

import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.printer.DevicePrinter;
import com.openbravo.pos.printer.TicketPrinterException;
import com.openbravo.pos.printer.escpos.Codes;
import com.openbravo.pos.printer.escpos.ESCPOS;
import com.openbravo.pos.printer.escpos.PrinterWritter;
import com.openbravo.pos.printer.escpos.UnicodeTranslator;
import java.awt.image.BufferedImage;
import javax.swing.JComponent;

public class DevicePrinterESCPOS
implements DevicePrinter {
    private PrinterWritter m_CommOutputPrinter;
    private Codes m_codes;
    private UnicodeTranslator m_trans;
    private String m_sName = AppLocal.getIntString("printer.serial");

    public DevicePrinterESCPOS(PrinterWritter CommOutputPrinter, Codes codes, UnicodeTranslator trans) throws TicketPrinterException {
        this.m_CommOutputPrinter = CommOutputPrinter;
        this.m_codes = codes;
        this.m_trans = trans;
        this.m_CommOutputPrinter.init(ESCPOS.INIT);
        this.m_CommOutputPrinter.write(ESCPOS.SELECT_PRINTER);
        this.m_CommOutputPrinter.init(this.m_codes.getInitSequence());
        this.m_CommOutputPrinter.write(this.m_trans.getCodeTable());
        this.m_CommOutputPrinter.flush();
    }

    @Override
    public String getPrinterName() {
        return this.m_sName;
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
        this.m_CommOutputPrinter.write(ESCPOS.SELECT_PRINTER);
        this.m_CommOutputPrinter.write(this.m_codes.transImage(image));
    }

    @Override
    public void printLogo() {
        this.m_CommOutputPrinter.write(ESCPOS.SELECT_PRINTER);
        this.m_CommOutputPrinter.write(this.m_codes.getImageLogo());
    }

    @Override
    public void printBarCode(String type, String position, String code) {
        this.m_CommOutputPrinter.write(ESCPOS.SELECT_PRINTER);
        this.m_codes.printBarcode(this.m_CommOutputPrinter, type, position, code);
    }

    @Override
    public void beginLine(int iTextSize) {
        this.m_CommOutputPrinter.write(ESCPOS.SELECT_PRINTER);
        if (iTextSize == 0) {
            this.m_CommOutputPrinter.write(this.m_codes.getSize0());
        } else if (iTextSize == 1) {
            this.m_CommOutputPrinter.write(this.m_codes.getSize1());
        } else if (iTextSize == 2) {
            this.m_CommOutputPrinter.write(this.m_codes.getSize2());
        } else if (iTextSize == 3) {
            this.m_CommOutputPrinter.write(this.m_codes.getSize3());
        } else {
            this.m_CommOutputPrinter.write(this.m_codes.getSize0());
        }
    }

    @Override
    public void printText(int iStyle, String sText) {
        this.m_CommOutputPrinter.write(ESCPOS.SELECT_PRINTER);
        if ((iStyle & 1) != 0) {
            this.m_CommOutputPrinter.write(this.m_codes.getBoldSet());
        }
        if ((iStyle & 2) != 0) {
            this.m_CommOutputPrinter.write(this.m_codes.getUnderlineSet());
        }
        this.m_CommOutputPrinter.write(this.m_trans.transString(sText));
        if ((iStyle & 2) != 0) {
            this.m_CommOutputPrinter.write(this.m_codes.getUnderlineReset());
        }
        if ((iStyle & 1) != 0) {
            this.m_CommOutputPrinter.write(this.m_codes.getBoldReset());
        }
    }

    @Override
    public void endLine() {
        this.m_CommOutputPrinter.write(ESCPOS.SELECT_PRINTER);
        this.m_CommOutputPrinter.write(this.m_codes.getNewLine());
    }

    @Override
    public void endReceipt() {
        this.m_CommOutputPrinter.write(ESCPOS.SELECT_PRINTER);
        this.m_CommOutputPrinter.write(this.m_codes.getNewLine());
        this.m_CommOutputPrinter.write(this.m_codes.getNewLine());
        this.m_CommOutputPrinter.write(this.m_codes.getNewLine());
        this.m_CommOutputPrinter.write(this.m_codes.getNewLine());
        this.m_CommOutputPrinter.write(this.m_codes.getNewLine());
        this.m_CommOutputPrinter.write(this.m_codes.getCutReceipt());
        this.m_CommOutputPrinter.flush();
    }

    @Override
    public void openDrawer() {
        this.m_CommOutputPrinter.write(ESCPOS.SELECT_PRINTER);
        System.out.print(this.m_codes.getOpenDrawer());
        this.m_CommOutputPrinter.write(this.m_codes.getOpenDrawer());
        this.m_CommOutputPrinter.flush();
    }
}

