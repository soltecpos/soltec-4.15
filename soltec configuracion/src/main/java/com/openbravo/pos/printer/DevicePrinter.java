/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.printer;

import java.awt.image.BufferedImage;
import javax.swing.JComponent;

public interface DevicePrinter {
    public static final int SIZE_0 = 0;
    public static final int SIZE_1 = 1;
    public static final int SIZE_2 = 2;
    public static final int SIZE_3 = 3;
    public static final int STYLE_PLAIN = 0;
    public static final int STYLE_BOLD = 1;
    public static final int STYLE_UNDERLINE = 2;
    public static final int ALIGN_LEFT = 0;
    public static final int ALIGN_RIGHT = 1;
    public static final int ALIGN_CENTER = 2;
    public static final String POSITION_BOTTOM = "bottom";
    public static final String POSITION_NONE = "none";
    public static final String BARCODE_EAN8 = "EAN8";
    public static final String BARCODE_EAN13 = "EAN13";
    public static final String BARCODE_UPCA = "UPC-A";
    public static final String BARCODE_UPCE = "UPC-E";
    public static final String BARCODE_CODE128 = "CODE128";
    public static final String BARCODE_CODE39 = "CODE39";

    public String getPrinterName();

    public String getPrinterDescription();

    public JComponent getPrinterComponent();

    public void reset();

    public void beginReceipt();

    public void printImage(BufferedImage var1);

    public void printLogo();

    public void printBarCode(String var1, String var2, String var3);

    public void beginLine(int var1);

    public void printText(int var1, String var2);

    public void endLine();

    public void endReceipt();

    public void openDrawer();
}

