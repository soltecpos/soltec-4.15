/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.printer.escpos;

import com.openbravo.pos.printer.escpos.ESCPOS;
import com.openbravo.pos.printer.escpos.PrinterWritter;
import com.openbravo.pos.printer.escpos.UnicodeTranslatorInt;
import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.Locale;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.standard.DocumentName;
import javax.print.attribute.standard.JobName;

public final class PrinterWritterRaw
extends PrinterWritter {
    private byte[] m_printData = null;
    private PrintService m_printService;
    private final DocFlavor m_docFlavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
    private PrinterBuffer m_buff = new PrinterBuffer();
    private OutputStream m_out;

    public PrinterWritterRaw(String sRawPrinter) {
        PrintService[] services;
        this.init();
        for (PrintService ps : services = PrintServiceLookup.lookupPrintServices(this.m_docFlavor, null)) {
            if (!ps.getName().contains(sRawPrinter)) continue;
            this.m_printService = ps;
            this.write(ESCPOS.INIT);
            return;
        }
    }

    public void init() {
        byte[] inicode = this.concatByteArrays(ESCPOS.SELECT_PRINTER, new UnicodeTranslatorInt().getCodeTable());
        this.m_printData = this.concatByteArrays(inicode, this.m_printData);
    }

    @Override
    public void write(byte[] data) {
        this.m_printData = this.concatByteArrays(this.m_printData, data);
    }

    @Override
    public void write(String sValue) {
        this.m_buff.putData(sValue.getBytes());
    }

    @Override
    protected void internalWrite(byte[] data) {
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

    @Override
    protected void internalFlush() {
    }

    @Override
    public void flush() {
        this.printJob();
    }

    private byte[] concatByteArrays(byte[] a, byte[] b) {
        if (a == null) {
            return b;
        }
        if (b == null) {
            return a;
        }
        byte[] concat = new byte[a.length + b.length];
        System.arraycopy(a, 0, concat, 0, a.length);
        System.arraycopy(b, 0, concat, a.length, b.length);
        return concat;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void printJob() {
        if (null != this.m_printService) {
            try {
                DocPrintJob pj = this.m_printService.createPrintJob();
                HashDocAttributeSet docattributes = new HashDocAttributeSet();
                docattributes.add(new DocumentName("Ticket", Locale.getDefault()));
                HashPrintRequestAttributeSet jobattributes = new HashPrintRequestAttributeSet();
                jobattributes.add(new JobName("unicenta", Locale.getDefault()));
                SimpleDoc doc = new SimpleDoc(this.m_printData, this.m_docFlavor, docattributes);
                pj.print(doc, jobattributes);
            }
            catch (PrintException printException) {
            }
            finally {
                this.m_printData = null;
            }
        }
    }

    private class PrinterBuffer {
        private final LinkedList m_list = new LinkedList();

        public synchronized void putData(Object data) {
            this.m_list.addFirst(data);
            this.notifyAll();
        }

        public synchronized Object getData() {
            while (this.m_list.isEmpty()) {
                try {
                    this.wait();
                }
                catch (InterruptedException interruptedException) {}
            }
            this.notifyAll();
            return this.m_list.removeLast();
        }
    }
}

