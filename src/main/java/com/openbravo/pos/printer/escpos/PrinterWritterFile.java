/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.printer.escpos;

import com.openbravo.pos.printer.escpos.PrinterWritter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class PrinterWritterFile
extends PrinterWritter {
    private String m_sFilePrinter;
    private OutputStream m_out;

    public PrinterWritterFile(String sFilePrinter) {
        this.m_sFilePrinter = sFilePrinter;
        this.m_out = null;
    }

    @Override
    protected void internalWrite(byte[] data) {
        try {
            if (this.m_out == null) {
                this.m_out = new FileOutputStream(this.m_sFilePrinter);
            }
            this.m_out.write(data);
        }
        catch (IOException e) {
            System.err.println(e);
        }
    }

    @Override
    protected void internalFlush() {
        try {
            if (this.m_out != null) {
                this.m_out.flush();
                this.m_out.close();
                this.m_out = null;
            }
        }
        catch (IOException e) {
            System.err.println(e);
        }
    }

    @Override
    protected void internalClose() {
        try {
            if (this.m_out != null) {
                this.m_out.flush();
                this.m_out.close();
                this.m_out = null;
            }
        }
        catch (IOException e) {
            System.err.println(e);
        }
    }
}

