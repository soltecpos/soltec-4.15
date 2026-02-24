/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.sf.jasperreports.engine.JRException
 *  net.sf.jasperreports.engine.JRExporterParameter
 *  net.sf.jasperreports.engine.JasperPrint
 *  net.sf.jasperreports.engine.export.JRGraphics2DExporter
 *  net.sf.jasperreports.engine.export.JRGraphics2DExporterParameter
 *  net.sf.jasperreports.engine.util.JRGraphEnvInitializer
 */
package com.openbravo.pos.util;

import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import javax.print.PrintService;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRGraphics2DExporter;
import net.sf.jasperreports.engine.export.JRGraphics2DExporterParameter;
import net.sf.jasperreports.engine.util.JRGraphEnvInitializer;

public class JRPrinterAWT300
implements Printable {
    private JasperPrint jasperPrint = null;
    private int pageOffset = 0;

    protected JRPrinterAWT300(JasperPrint jrPrint) throws JRException {
        JRGraphEnvInitializer.initializeGraphEnv();
        this.jasperPrint = jrPrint;
    }

    public static boolean printPages(JasperPrint jrPrint, int firstPageIndex, int lastPageIndex, PrintService service) throws JRException {
        JRPrinterAWT300 printer = new JRPrinterAWT300(jrPrint);
        return printer.printPages(firstPageIndex, lastPageIndex, service);
    }

    public static Image printPageToImage(JasperPrint jrPrint, int pageIndex, float zoom) throws JRException {
        JRPrinterAWT300 printer = new JRPrinterAWT300(jrPrint);
        return printer.printPageToImage(pageIndex, zoom);
    }

    private boolean printPages(int firstPageIndex, int lastPageIndex, PrintService service) throws JRException {
        boolean isOK = true;
        if (firstPageIndex < 0 || firstPageIndex > lastPageIndex || lastPageIndex >= this.jasperPrint.getPages().size()) {
            throw new JRException("Invalid page index range : " + firstPageIndex + " - " + lastPageIndex + " of " + this.jasperPrint.getPages().size());
        }
        this.pageOffset = firstPageIndex;
        PrinterJob printJob = PrinterJob.getPrinterJob();
        JRPrinterAWT300.initPrinterJobFields(printJob);
        PageFormat pageFormat = printJob.defaultPage();
        Paper paper = pageFormat.getPaper();
        printJob.setJobName("uniCentaReport - " + this.jasperPrint.getName());
        switch (this.jasperPrint.getOrientationValue()) {
            case LANDSCAPE: {
                pageFormat.setOrientation(0);
                paper.setSize(this.jasperPrint.getPageHeight(), this.jasperPrint.getPageWidth());
                paper.setImageableArea(0.0, 0.0, this.jasperPrint.getPageHeight(), this.jasperPrint.getPageWidth());
                break;
            }
            default: {
                pageFormat.setOrientation(1);
                paper.setSize(this.jasperPrint.getPageWidth(), this.jasperPrint.getPageHeight());
                paper.setImageableArea(0.0, 0.0, this.jasperPrint.getPageWidth(), this.jasperPrint.getPageHeight());
            }
        }
        pageFormat.setPaper(paper);
        Book book = new Book();
        book.append(this, pageFormat, lastPageIndex - firstPageIndex + 1);
        printJob.setPageable(book);
        try {
            if (service == null) {
                if (printJob.printDialog()) {
                    printJob.print();
                }
            } else {
                printJob.setPrintService(service);
                printJob.print();
            }
        }
        catch (HeadlessException | PrinterException ex) {
            throw new JRException("Error printing report.", (Throwable)ex);
        }
        return isOK;
    }

    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        if (Thread.currentThread().isInterrupted()) {
            throw new PrinterException("Current thread interrupted.");
        }
        if ((pageIndex += this.pageOffset) < 0 || pageIndex >= this.jasperPrint.getPages().size()) {
            return 1;
        }
        try {
            JRGraphics2DExporter exporter = new JRGraphics2DExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, (Object)this.jasperPrint);
            exporter.setParameter((JRExporterParameter)JRGraphics2DExporterParameter.GRAPHICS_2D, (Object)graphics);
            exporter.setParameter(JRExporterParameter.PAGE_INDEX, (Object)new Integer(pageIndex));
            exporter.exportReport();
        }
        catch (JRException e) {
            throw new PrinterException(e.getMessage());
        }
        return 0;
    }

    private Image printPageToImage(int pageIndex, float zoom) throws JRException {
        BufferedImage pageImage = new BufferedImage((int)((float)this.jasperPrint.getPageWidth() * zoom) + 1, (int)((float)this.jasperPrint.getPageHeight() * zoom) + 1, 1);
        JRGraphics2DExporter exporter = new JRGraphics2DExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, (Object)this.jasperPrint);
        exporter.setParameter((JRExporterParameter)JRGraphics2DExporterParameter.GRAPHICS_2D, (Object)((Image)pageImage).getGraphics());
        exporter.setParameter(JRExporterParameter.PAGE_INDEX, (Object)new Integer(pageIndex));
        exporter.setParameter((JRExporterParameter)JRGraphics2DExporterParameter.ZOOM_RATIO, (Object)new Float(zoom));
        exporter.exportReport();
        return pageImage;
    }

    public static void initPrinterJobFields(PrinterJob job) {
        try {
            job.setPrintService(job.getPrintService());
        }
        catch (PrinterException printerException) {
            // empty catch block
        }
    }

    public static long getImageSize(JasperPrint jasperPrint, float zoom) {
        int width = (int)((float)jasperPrint.getPageWidth() * zoom) + 1;
        int height = (int)((float)jasperPrint.getPageHeight() * zoom) + 1;
        return width * height;
    }
}

