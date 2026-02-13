/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jpos.CashDrawer
 *  jpos.JposException
 *  jpos.POSPrinter
 */
package com.openbravo.pos.printer.javapos;

import com.openbravo.data.loader.ImageUtils;
import com.openbravo.pos.printer.DevicePrinter;
import com.openbravo.pos.printer.TicketPrinterException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.swing.JComponent;
import jpos.CashDrawer;
import jpos.JposException;
import jpos.POSPrinter;

public class DevicePrinterJavaPOS
implements DevicePrinter {
    private static final String JPOS_SIZE0 = "\u001b|1C";
    private static final String JPOS_SIZE1 = "\u001b|2C";
    private static final String JPOS_SIZE2 = "\u001b|3C";
    private static final String JPOS_SIZE3 = "\u001b|4C";
    private static final String JPOS_LF = "\n";
    private static final String JPOS_BOLD = "\u001b|bC";
    private static final String JPOS_UNDERLINE = "\u001b|uC";
    private static final String JPOS_CUT = "\u001b|100fP";
    private String m_sName;
    private POSPrinter m_printer = null;
    private CashDrawer m_drawer = null;
    private StringBuilder m_sline;

    public DevicePrinterJavaPOS(String sDevicePrinterName, String sDeviceDrawerName) throws TicketPrinterException {
        this.m_sName = sDevicePrinterName;
        if (sDeviceDrawerName != null && !sDeviceDrawerName.equals("")) {
            this.m_sName = this.m_sName + " - " + sDeviceDrawerName;
        }
        try {
            this.m_printer = new POSPrinter();
            this.m_printer.open(sDevicePrinterName);
            this.m_printer.claim(10000);
            this.m_printer.setDeviceEnabled(true);
            this.m_printer.setMapMode(4);
        }
        catch (JposException e) {
            throw new TicketPrinterException(e.getMessage(), e);
        }
        try {
            this.m_drawer = new CashDrawer();
            this.m_drawer.open(sDeviceDrawerName);
            this.m_drawer.claim(10000);
            this.m_drawer.setDeviceEnabled(true);
        }
        catch (JposException e) {
            this.m_drawer = null;
        }
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
        try {
            this.m_printer.transactionPrint(2, 11);
        }
        catch (JposException jposException) {
            // empty catch block
        }
    }

    @Override
    public void printImage(BufferedImage image) {
        block7: {
            try {
                if (!this.m_printer.getCapRecBitmap()) break block7;
                File f = File.createTempFile("jposimg", ".png");
                try (FileOutputStream out = new FileOutputStream(f);){
                    ((OutputStream)out).write(ImageUtils.writeImage(image));
                }
                this.m_printer.printBitmap(2, f.getAbsolutePath(), -11, -2);
            }
            catch (IOException | JposException throwable) {
                // empty catch block
            }
        }
    }

    @Override
    public void printLogo() {
    }

    @Override
    public void printBarCode(String type, String position, String code) {
        try {
            if (this.m_printer.getCapRecBarCode()) {
                if ("none".equals(position)) {
                    this.m_printer.printBarCode(2, code, 104, 1000, 6000, -2, -11);
                } else {
                    this.m_printer.printBarCode(2, code, 104, 1000, 6000, -2, -13);
                }
            }
        }
        catch (JposException jposException) {
            // empty catch block
        }
    }

    @Override
    public void beginLine(int iTextSize) {
        this.m_sline = new StringBuilder();
        if (iTextSize == 0) {
            this.m_sline.append(JPOS_SIZE0);
        } else if (iTextSize == 1) {
            this.m_sline.append(JPOS_SIZE1);
        } else if (iTextSize == 2) {
            this.m_sline.append(JPOS_SIZE2);
        } else if (iTextSize == 3) {
            this.m_sline.append(JPOS_SIZE3);
        } else {
            this.m_sline.append(JPOS_SIZE0);
        }
    }

    @Override
    public void printText(int iStyle, String sText) {
        if ((iStyle & 1) != 0) {
            this.m_sline.append(JPOS_BOLD);
        }
        if ((iStyle & 2) != 0) {
            this.m_sline.append(JPOS_UNDERLINE);
        }
        this.m_sline.append(sText);
    }

    @Override
    public void endLine() {
        this.m_sline.append(JPOS_LF);
        try {
            this.m_printer.printNormal(2, this.m_sline.toString());
        }
        catch (JposException jposException) {
            // empty catch block
        }
        this.m_sline = null;
    }

    @Override
    public void endReceipt() {
        try {
            this.m_printer.printNormal(2, JPOS_CUT);
            this.m_printer.transactionPrint(2, 12);
        }
        catch (JposException jposException) {
            // empty catch block
        }
    }

    @Override
    public void openDrawer() {
        if (this.m_drawer != null) {
            try {
                this.m_drawer.openDrawer();
            }
            catch (JposException jposException) {
                // empty catch block
            }
        }
    }

    public void finalize() throws Throwable {
        this.m_printer.setDeviceEnabled(false);
        this.m_printer.release();
        this.m_printer.close();
        if (this.m_drawer != null) {
            this.m_drawer.setDeviceEnabled(false);
            this.m_drawer.release();
            this.m_drawer.close();
        }
        super.finalize();
    }
}

