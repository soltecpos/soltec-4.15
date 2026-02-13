/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.util;

import javax.print.DocFlavor;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;

public class ReportUtils {
    private ReportUtils() {
    }

    public static PrintService getPrintService(String printername) {
        PrintService[] pservices;
        if (printername == null) {
            return PrintServiceLookup.lookupDefaultPrintService();
        }
        if ("(Show dialog)".equals(printername)) {
            return null;
        }
        if ("(Default)".equals(printername)) {
            return PrintServiceLookup.lookupDefaultPrintService();
        }
        for (PrintService s : pservices = PrintServiceLookup.lookupPrintServices(DocFlavor.SERVICE_FORMATTED.PRINTABLE, null)) {
            if (!printername.equals(s.getName())) continue;
            return s;
        }
        return PrintServiceLookup.lookupDefaultPrintService();
    }

    public static String[] getPrintNames() {
        PrintService[] pservices = PrintServiceLookup.lookupPrintServices(DocFlavor.SERVICE_FORMATTED.PRINTABLE, null);
        String[] printers = new String[pservices.length];
        for (int i = 0; i < pservices.length; ++i) {
            printers[i] = pservices[i].getName();
        }
        return printers;
    }
}

