/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.printer.printer;

import com.openbravo.data.gui.JMessageDialog;
import com.openbravo.data.gui.MessageInf;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.printer.DevicePrinter;
import com.openbravo.pos.printer.printer.PrintableBasicTicket;
import com.openbravo.pos.printer.ticket.BasicTicket;
import com.openbravo.pos.printer.ticket.BasicTicketForPrinter;
import com.openbravo.pos.util.ReportUtils;
import com.openbravo.pos.util.SelectPrinter;
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.standard.JobName;
import javax.print.attribute.standard.Media;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.OrientationRequested;
import javax.swing.JComponent;

public class DevicePrinterPrinter
implements DevicePrinter {
    private static final Logger logger = Logger.getLogger("com.openbravo.pos.printer.printer.DevicePrinterPrinter");
    private Component parent;
    private String m_sName;
    private BasicTicket m_ticketcurrent;
    private PrintService printservice;
    private int imageable_width;
    private int imageable_height;
    private int imageable_x;
    private int imageable_y;
    private Media media;
    private static final HashMap<String, MediaSizeName> mediasizenamemap = new HashMap();

    public DevicePrinterPrinter(Component parent, String printername, int imageable_x, int imageable_y, int imageable_width, int imageable_height, String mediasizename) {
        this.parent = parent;
        this.m_sName = "Printer";
        this.m_ticketcurrent = null;
        this.printservice = ReportUtils.getPrintService(printername);
        this.imageable_x = imageable_x;
        this.imageable_y = imageable_y;
        this.imageable_width = imageable_width;
        this.imageable_height = imageable_height;
        this.media = DevicePrinterPrinter.getMedia(mediasizename);
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
        this.m_ticketcurrent = null;
    }

    @Override
    public void beginReceipt() {
        this.m_ticketcurrent = new BasicTicketForPrinter();
    }

    @Override
    public void printImage(BufferedImage image) {
        this.m_ticketcurrent.printImage(image);
    }

    @Override
    public void printLogo() {
    }

    @Override
    public void printBarCode(String type, String position, String code) {
        this.m_ticketcurrent.printBarCode(type, position, code);
    }

    @Override
    public void beginLine(int iTextSize) {
        this.m_ticketcurrent.beginLine(iTextSize);
    }

    @Override
    public void printText(int iStyle, String sText) {
        this.m_ticketcurrent.printText(iStyle, sText);
    }

    @Override
    public void endLine() {
        this.m_ticketcurrent.endLine();
    }

    @Override
    public void endReceipt() {
        try {
            PrintService ps;
            if (this.printservice == null) {
                String[] printers = ReportUtils.getPrintNames();
                if (printers.length == 0) {
                    logger.warning(AppLocal.getIntString("message.noprinters"));
                    ps = null;
                } else {
                    SelectPrinter selectprinter = SelectPrinter.getSelectPrinter(this.parent, printers);
                    selectprinter.setVisible(true);
                    ps = selectprinter.isOK() ? ReportUtils.getPrintService(selectprinter.getPrintService()) : null;
                }
            } else {
                ps = this.printservice;
            }
            if (ps != null) {
                HashPrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
                aset.add(OrientationRequested.PORTRAIT);
                aset.add(new JobName("SOLTEC POS - Document", null));
                aset.add(this.media);
                DocPrintJob printjob = ps.createPrintJob();
                SimpleDoc doc = new SimpleDoc(new PrintableBasicTicket(this.m_ticketcurrent, this.imageable_x, this.imageable_y, this.imageable_width, this.imageable_height), DocFlavor.SERVICE_FORMATTED.PRINTABLE, null);
                printjob.print(doc, aset);
            }
        }
        catch (PrintException ex) {
            logger.log(Level.WARNING, AppLocal.getIntString("message.printererror"), ex);
            JMessageDialog.showMessage(this.parent, new MessageInf(-33554432, AppLocal.getIntString("message.printererror"), ex));
        }
        this.m_ticketcurrent = null;
    }

    @Override
    public void openDrawer() {
        Toolkit.getDefaultToolkit().beep();
    }

    private static MediaSizeName getMedia(String mediasizename) {
        return mediasizenamemap.get(mediasizename);
    }

    static {
        mediasizenamemap.put("Postcard", MediaSizeName.JAPANESE_POSTCARD);
        mediasizenamemap.put("Statement", MediaSizeName.INVOICE);
        mediasizenamemap.put("Letter", MediaSizeName.NA_LETTER);
        mediasizenamemap.put("Executive", MediaSizeName.EXECUTIVE);
        mediasizenamemap.put("Legal", MediaSizeName.NA_LEGAL);
        mediasizenamemap.put("A0", MediaSizeName.ISO_A0);
        mediasizenamemap.put("A1", MediaSizeName.ISO_A1);
        mediasizenamemap.put("A2", MediaSizeName.ISO_A2);
        mediasizenamemap.put("A3", MediaSizeName.ISO_A3);
        mediasizenamemap.put("A4", MediaSizeName.ISO_A4);
        mediasizenamemap.put("A5", MediaSizeName.ISO_A5);
        mediasizenamemap.put("A6", MediaSizeName.ISO_A6);
        mediasizenamemap.put("A7", MediaSizeName.ISO_A7);
        mediasizenamemap.put("A8", MediaSizeName.ISO_A8);
        mediasizenamemap.put("A9", MediaSizeName.ISO_A9);
        mediasizenamemap.put("A10", MediaSizeName.ISO_A10);
        mediasizenamemap.put("B0", MediaSizeName.JIS_B0);
        mediasizenamemap.put("B1", MediaSizeName.JIS_B1);
        mediasizenamemap.put("B2", MediaSizeName.JIS_B2);
        mediasizenamemap.put("B3", MediaSizeName.JIS_B3);
        mediasizenamemap.put("B4", MediaSizeName.JIS_B4);
        mediasizenamemap.put("B5", MediaSizeName.JIS_B5);
        mediasizenamemap.put("B6", MediaSizeName.JIS_B6);
        mediasizenamemap.put("B7", MediaSizeName.JIS_B7);
        mediasizenamemap.put("B8", MediaSizeName.JIS_B8);
        mediasizenamemap.put("B9", MediaSizeName.JIS_B9);
        mediasizenamemap.put("B10", MediaSizeName.JIS_B10);
        mediasizenamemap.put("ISOB0", MediaSizeName.ISO_B0);
        mediasizenamemap.put("ISOB1", MediaSizeName.ISO_B1);
        mediasizenamemap.put("ISOB2", MediaSizeName.ISO_B2);
        mediasizenamemap.put("ISOB3", MediaSizeName.ISO_B3);
        mediasizenamemap.put("ISOB4", MediaSizeName.ISO_B4);
        mediasizenamemap.put("ISOB5", MediaSizeName.ISO_B5);
        mediasizenamemap.put("ISOB6", MediaSizeName.ISO_B6);
        mediasizenamemap.put("ISOB7", MediaSizeName.ISO_B7);
        mediasizenamemap.put("ISOB8", MediaSizeName.ISO_B8);
        mediasizenamemap.put("ISOB9", MediaSizeName.ISO_B9);
        mediasizenamemap.put("ISOB10", MediaSizeName.ISO_B10);
        mediasizenamemap.put("EnvISOB0", MediaSizeName.ISO_B0);
        mediasizenamemap.put("EnvISOB1", MediaSizeName.ISO_B1);
        mediasizenamemap.put("EnvISOB2", MediaSizeName.ISO_B2);
        mediasizenamemap.put("EnvISOB3", MediaSizeName.ISO_B3);
        mediasizenamemap.put("EnvISOB4", MediaSizeName.ISO_B4);
        mediasizenamemap.put("EnvISOB5", MediaSizeName.ISO_B5);
        mediasizenamemap.put("EnvISOB6", MediaSizeName.ISO_B6);
        mediasizenamemap.put("EnvISOB7", MediaSizeName.ISO_B7);
        mediasizenamemap.put("EnvISOB8", MediaSizeName.ISO_B8);
        mediasizenamemap.put("EnvISOB9", MediaSizeName.ISO_B9);
        mediasizenamemap.put("EnvISOB10", MediaSizeName.ISO_B10);
        mediasizenamemap.put("C0", MediaSizeName.ISO_C0);
        mediasizenamemap.put("C1", MediaSizeName.ISO_C1);
        mediasizenamemap.put("C2", MediaSizeName.ISO_C2);
        mediasizenamemap.put("C3", MediaSizeName.ISO_C3);
        mediasizenamemap.put("C4", MediaSizeName.ISO_C4);
        mediasizenamemap.put("C5", MediaSizeName.ISO_C5);
        mediasizenamemap.put("C6", MediaSizeName.ISO_C6);
        mediasizenamemap.put("EnvPersonal", MediaSizeName.PERSONAL_ENVELOPE);
        mediasizenamemap.put("EnvMonarch", MediaSizeName.MONARCH_ENVELOPE);
        mediasizenamemap.put("Monarch", MediaSizeName.MONARCH_ENVELOPE);
        mediasizenamemap.put("Env9", MediaSizeName.NA_NUMBER_9_ENVELOPE);
        mediasizenamemap.put("Env10", MediaSizeName.NA_NUMBER_10_ENVELOPE);
        mediasizenamemap.put("Env11", MediaSizeName.NA_NUMBER_11_ENVELOPE);
        mediasizenamemap.put("Env12", MediaSizeName.NA_NUMBER_12_ENVELOPE);
        mediasizenamemap.put("Env14", MediaSizeName.NA_NUMBER_14_ENVELOPE);
        mediasizenamemap.put("c8x10", MediaSizeName.NA_8X10);
        mediasizenamemap.put("EnvDL", MediaSizeName.ISO_DESIGNATED_LONG);
        mediasizenamemap.put("DL", MediaSizeName.ISO_DESIGNATED_LONG);
        mediasizenamemap.put("EnvC0", MediaSizeName.ISO_C0);
        mediasizenamemap.put("EnvC1", MediaSizeName.ISO_C1);
        mediasizenamemap.put("EnvC2", MediaSizeName.ISO_C2);
        mediasizenamemap.put("EnvC3", MediaSizeName.ISO_C3);
        mediasizenamemap.put("EnvC4", MediaSizeName.ISO_C4);
        mediasizenamemap.put("EnvC5", MediaSizeName.ISO_C5);
        mediasizenamemap.put("EnvC6", MediaSizeName.ISO_C6);
    }
}

