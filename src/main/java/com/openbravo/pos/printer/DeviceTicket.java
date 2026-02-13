/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.printer;

import com.openbravo.pos.forms.AppProperties;
import com.openbravo.pos.printer.DeviceDisplay;
import com.openbravo.pos.printer.DeviceDisplayNull;
import com.openbravo.pos.printer.DeviceFiscalPrinter;
import com.openbravo.pos.printer.DeviceFiscalPrinterNull;
import com.openbravo.pos.printer.DevicePrinter;
import com.openbravo.pos.printer.DevicePrinterNull;
import com.openbravo.pos.printer.TicketPrinterException;
import com.openbravo.pos.printer.escpos.CodesEpson;
import com.openbravo.pos.printer.escpos.CodesIthaca;
import com.openbravo.pos.printer.escpos.CodesStar;
import com.openbravo.pos.printer.escpos.CodesSurePOS;
import com.openbravo.pos.printer.escpos.CodesTMU220;
import com.openbravo.pos.printer.escpos.DeviceDisplayESCPOS;
import com.openbravo.pos.printer.escpos.DeviceDisplaySurePOS;
import com.openbravo.pos.printer.escpos.DevicePrinterESCPOS;
import com.openbravo.pos.printer.escpos.DevicePrinterPlain;
import com.openbravo.pos.printer.escpos.PrinterWritter;
import com.openbravo.pos.printer.escpos.PrinterWritterFile;
import com.openbravo.pos.printer.escpos.PrinterWritterRXTX;
import com.openbravo.pos.printer.escpos.UnicodeTranslatorEur;
import com.openbravo.pos.printer.escpos.UnicodeTranslatorInt;
import com.openbravo.pos.printer.escpos.UnicodeTranslatorStar;
import com.openbravo.pos.printer.escpos.UnicodeTranslatorSurePOS;
import com.openbravo.pos.printer.javapos.DeviceDisplayJavaPOS;
import com.openbravo.pos.printer.javapos.DeviceFiscalPrinterJavaPOS;
import com.openbravo.pos.printer.javapos.DevicePrinterJavaPOS;
import com.openbravo.pos.printer.printer.DevicePrinterPrinter;
import com.openbravo.pos.printer.screen.DeviceDisplayPanel;
import com.openbravo.pos.printer.screen.DeviceDisplayWindow;
import com.openbravo.pos.printer.screen.DevicePrinterPanel;
import com.openbravo.pos.util.StringParser;
import java.awt.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DeviceTicket {
    private static final Logger logger = Logger.getLogger("com.openbravo.pos.printer.DeviceTicket");
    private DeviceFiscalPrinter m_deviceFiscal;
    private DeviceDisplay m_devicedisplay;
    private DevicePrinter m_nullprinter;
    private Map<String, DevicePrinter> m_deviceprinters;
    private List<DevicePrinter> m_deviceprinterslist;

    public DeviceTicket() {
        this.m_deviceFiscal = new DeviceFiscalPrinterNull();
        this.m_devicedisplay = new DeviceDisplayNull();
        this.m_nullprinter = new DevicePrinterNull();
        this.m_deviceprinters = new HashMap<String, DevicePrinter>();
        this.m_deviceprinterslist = new ArrayList<DevicePrinter>();
        DevicePrinterPanel p = new DevicePrinterPanel();
        this.m_deviceprinters.put("1", p);
        this.m_deviceprinterslist.add(p);
    }

    public DeviceTicket(Component parent, AppProperties props) {
        PrinterWritterPool pws = new PrinterWritterPool();
        StringParser sf = new StringParser(props.getProperty("machine.fiscalprinter"));
        String sFiscalType = sf.nextToken(':');
        String sFiscalParam1 = sf.nextToken(',');
        try {
            this.m_deviceFiscal = "javapos".equals(sFiscalType) ? new DeviceFiscalPrinterJavaPOS(sFiscalParam1) : new DeviceFiscalPrinterNull();
        }
        catch (TicketPrinterException e) {
            this.m_deviceFiscal = new DeviceFiscalPrinterNull(e.getMessage());
        }
        StringParser sd = new StringParser(props.getProperty("machine.display"));
        String sDisplayType = sd.nextToken(':');
        String sDisplayParam1 = sd.nextToken(',');
        String sDisplayParam2 = sd.nextToken(',');
        if ("serial".equals(sDisplayType) || "rxtx".equals(sDisplayType) || "file".equals(sDisplayType)) {
            sDisplayParam2 = sDisplayParam1;
            sDisplayParam1 = sDisplayType;
            sDisplayType = "epson";
        }
        try {
            switch (sDisplayType) {
                case "screen": {
                    this.m_devicedisplay = new DeviceDisplayPanel();
                    break;
                }
                case "window": {
                    this.m_devicedisplay = new DeviceDisplayWindow();
                    break;
                }
                case "epson": {
                    this.m_devicedisplay = new DeviceDisplayESCPOS(pws.getPrinterWritter(sDisplayParam1, sDisplayParam2), new UnicodeTranslatorInt());
                    break;
                }
                case "surepos": {
                    this.m_devicedisplay = new DeviceDisplaySurePOS(pws.getPrinterWritter(sDisplayParam1, sDisplayParam2));
                    break;
                }
                case "ld200": {
                    this.m_devicedisplay = new DeviceDisplayESCPOS(pws.getPrinterWritter(sDisplayParam1, sDisplayParam2), new UnicodeTranslatorEur());
                    break;
                }
                case "javapos": {
                    this.m_devicedisplay = new DeviceDisplayJavaPOS(sDisplayParam1);
                    break;
                }
                default: {
                    this.m_devicedisplay = new DeviceDisplayNull();
                    break;
                }
            }
        }
        catch (TicketPrinterException e) {
            logger.log(Level.WARNING, e.getMessage(), e);
            this.m_devicedisplay = new DeviceDisplayNull(e.getMessage());
        }
        this.m_nullprinter = new DevicePrinterNull();
        this.m_deviceprinters = new HashMap<String, DevicePrinter>();
        this.m_deviceprinterslist = new ArrayList<DevicePrinter>();
        int iPrinterIndex = 1;
        String sPrinterIndex = Integer.toString(iPrinterIndex);
        String sprinter = props.getProperty("machine.printer");
        while (sprinter != null && !"".equals(sprinter)) {
            StringParser sp = new StringParser(sprinter);
            String sPrinterType = sp.nextToken(':');
            String sPrinterParam1 = sp.nextToken(',');
            String sPrinterParam2 = sp.nextToken(',');
            if ("serial".equals(sPrinterType) || "rxtx".equals(sPrinterType) || "file".equals(sPrinterType)) {
                sPrinterParam2 = sPrinterParam1;
                sPrinterParam1 = sPrinterType;
                sPrinterType = "epson";
            }
            try {
                switch (sPrinterType) {
                    case "screen": {
                        this.addPrinter(sPrinterIndex, new DevicePrinterPanel());
                        break;
                    }
                    case "printer": {
                        if (sPrinterParam2 == null || sPrinterParam2.equals("") || sPrinterParam2.equals("true")) {
                            sPrinterParam2 = "receipt";
                        } else if (sPrinterParam2.equals("false")) {
                            sPrinterParam2 = "standard";
                        }
                        this.addPrinter(sPrinterIndex, new DevicePrinterPrinter(parent, sPrinterParam1, Integer.parseInt(props.getProperty("paper." + sPrinterParam2 + ".x")), Integer.parseInt(props.getProperty("paper." + sPrinterParam2 + ".y")), Integer.parseInt(props.getProperty("paper." + sPrinterParam2 + ".width")), Integer.parseInt(props.getProperty("paper." + sPrinterParam2 + ".height")), props.getProperty("paper." + sPrinterParam2 + ".mediasizename")));
                        break;
                    }
                    case "epson": {
                        this.addPrinter(sPrinterIndex, new DevicePrinterESCPOS(pws.getPrinterWritter(sPrinterParam1, sPrinterParam2), new CodesEpson(), new UnicodeTranslatorInt()));
                        break;
                    }
                    case "tmu220": {
                        this.addPrinter(sPrinterIndex, new DevicePrinterESCPOS(pws.getPrinterWritter(sPrinterParam1, sPrinterParam2), new CodesTMU220(), new UnicodeTranslatorInt()));
                        break;
                    }
                    case "star": {
                        this.addPrinter(sPrinterIndex, new DevicePrinterESCPOS(pws.getPrinterWritter(sPrinterParam1, sPrinterParam2), new CodesStar(), new UnicodeTranslatorStar()));
                        break;
                    }
                    case "ithaca": {
                        this.addPrinter(sPrinterIndex, new DevicePrinterESCPOS(pws.getPrinterWritter(sPrinterParam1, sPrinterParam2), new CodesIthaca(), new UnicodeTranslatorInt()));
                        break;
                    }
                    case "surepos": {
                        this.addPrinter(sPrinterIndex, new DevicePrinterESCPOS(pws.getPrinterWritter(sPrinterParam1, sPrinterParam2), new CodesSurePOS(), new UnicodeTranslatorSurePOS()));
                        break;
                    }
                    case "plain": {
                        this.addPrinter(sPrinterIndex, new DevicePrinterPlain(pws.getPrinterWritter(sPrinterParam1, sPrinterParam2)));
                        break;
                    }
                    case "javapos": {
                        this.addPrinter(sPrinterIndex, new DevicePrinterJavaPOS(sPrinterParam1, sPrinterParam2));
                    }
                }
            }
            catch (TicketPrinterException e) {
                logger.log(Level.WARNING, e.getMessage(), e);
            }
            sPrinterIndex = Integer.toString(++iPrinterIndex);
            sprinter = props.getProperty("machine.printer." + sPrinterIndex);
        }
    }

    private void addPrinter(String sPrinterIndex, DevicePrinter p) {
        this.m_deviceprinters.put(sPrinterIndex, p);
        this.m_deviceprinterslist.add(p);
    }

    public DeviceFiscalPrinter getFiscalPrinter() {
        return this.m_deviceFiscal;
    }

    public DeviceDisplay getDeviceDisplay() {
        return this.m_devicedisplay;
    }

    public DevicePrinter getDevicePrinter(String key) {
        DevicePrinter printer = this.m_deviceprinters.get(key);
        return printer == null ? this.m_nullprinter : printer;
    }

    public List<DevicePrinter> getDevicePrinterAll() {
        return this.m_deviceprinterslist;
    }

    public static String getWhiteString(int iSize, char cWhiteChar) {
        char[] cFill = new char[iSize];
        for (int i = 0; i < iSize; ++i) {
            cFill[i] = cWhiteChar;
        }
        return new String(cFill);
    }

    public static String getWhiteString(int iSize) {
        return DeviceTicket.getWhiteString(iSize, ' ');
    }

    public static String alignBarCode(String sLine, int iSize) {
        if (sLine.length() > iSize) {
            return sLine.substring(sLine.length() - iSize);
        }
        return DeviceTicket.getWhiteString(iSize - sLine.length(), '0') + sLine;
    }

    public static String alignLeft(String sLine, int iSize) {
        if (sLine.length() > iSize) {
            return sLine.substring(0, iSize);
        }
        return sLine + DeviceTicket.getWhiteString(iSize - sLine.length());
    }

    public static String alignRight(String sLine, int iSize) {
        if (sLine.length() > iSize) {
            return sLine.substring(sLine.length() - iSize);
        }
        return DeviceTicket.getWhiteString(iSize - sLine.length()) + sLine;
    }

    public static String alignCenter(String sLine, int iSize) {
        if (sLine.length() > iSize) {
            return DeviceTicket.alignRight(sLine.substring(0, (sLine.length() + iSize) / 2), iSize);
        }
        return DeviceTicket.alignRight(sLine + DeviceTicket.getWhiteString((iSize - sLine.length()) / 2), iSize);
    }

    public static String alignCenter(String sLine) {
        return DeviceTicket.alignCenter(sLine, 42);
    }

    public static byte[] transNumber(String sCad) {
        if (sCad == null) {
            return null;
        }
        byte[] bAux = new byte[sCad.length()];
        for (int i = 0; i < sCad.length(); ++i) {
            bAux[i] = DeviceTicket.transNumberChar(sCad.charAt(i));
        }
        return bAux;
    }

    public static byte transNumberChar(char sChar) {
        switch (sChar) {
            case '0': {
                return 48;
            }
            case '1': {
                return 49;
            }
            case '2': {
                return 50;
            }
            case '3': {
                return 51;
            }
            case '4': {
                return 52;
            }
            case '5': {
                return 53;
            }
            case '6': {
                return 54;
            }
            case '7': {
                return 55;
            }
            case '8': {
                return 56;
            }
            case '9': {
                return 57;
            }
        }
        return 48;
    }

    private static class PrinterWritterPool {
        private final Map<String, PrinterWritter> m_apool = new HashMap<String, PrinterWritter>();

        private PrinterWritterPool() {
        }

        public PrinterWritter getPrinterWritter(String con, String port) throws TicketPrinterException {
            String skey = con + "-->" + port;
            PrinterWritter pw = this.m_apool.get(skey);
            if (pw == null) {
                switch (con) {
                    case "serial": 
                    case "rxtx": {
                        pw = new PrinterWritterRXTX(port);
                        this.m_apool.put(skey, pw);
                        break;
                    }
                    case "file": {
                        pw = new PrinterWritterFile(port);
                        this.m_apool.put(skey, pw);
                        break;
                    }
                    default: {
                        throw new TicketPrinterException();
                    }
                }
            }
            return pw;
        }
    }
}

