/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.printer;

import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.printer.DevicePrinter;
import java.awt.image.BufferedImage;
import javax.swing.JComponent;

public class DevicePrinterNull
implements DevicePrinter {
    private String m_sName = AppLocal.getIntString("printer.null");
    private String m_sDescription;

    public DevicePrinterNull() {
        this(null);
    }

    public DevicePrinterNull(String desc) {
        this.m_sDescription = desc;
    }

    @Override
    public String getPrinterName() {
        return this.m_sName;
    }

    @Override
    public String getPrinterDescription() {
        return this.m_sDescription;
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
    public void printBarCode(String type, String position, String code) {
    }

    @Override
    public void printImage(BufferedImage image) {
    }

    @Override
    public void beginLine(int iTextSize) {
    }

    @Override
    public void printText(int iStyle, String sText) {
    }

    @Override
    public void endLine() {
    }

    @Override
    public void endReceipt() {
    }

    @Override
    public void openDrawer() {
    }

    @Override
    public void printLogo() {
    }
}

