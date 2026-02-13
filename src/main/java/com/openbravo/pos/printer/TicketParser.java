/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.printer;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.LocalRes;
import com.openbravo.pos.forms.DataLogicSystem;
import com.openbravo.pos.printer.DevicePrinter;
import com.openbravo.pos.printer.DeviceTicket;
import com.openbravo.pos.printer.TicketPrinterException;
import com.openbravo.pos.ticket.TicketInfo;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class TicketParser
extends DefaultHandler {
    private static SAXParser m_sp = null;
    private DeviceTicket m_printer;
    private DataLogicSystem m_system;
    private StringBuilder text;
    private String bctype;
    private String bcposition;
    private int m_iTextAlign;
    private int m_iTextLength;
    private int m_iTextStyle;
    private int size;
    private StringBuilder m_sVisorLine;
    private int m_iVisorAnimation;
    private String m_sVisorLine1;
    private String m_sVisorLine2;
    private double m_dValue1;
    private double m_dValue2;
    private int attribute3;
    private int m_iOutputType;
    private static final int OUTPUT_NONE = 0;
    private static final int OUTPUT_DISPLAY = 1;
    private static final int OUTPUT_TICKET = 2;
    private static final int OUTPUT_FISCAL = 3;
    private DevicePrinter m_oOutputPrinter;
    private DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    private Date today;
    private String cUser;
    private String ticketId;
    private String pickupId;

    public TicketParser(DeviceTicket printer, DataLogicSystem system) {
        this.m_printer = printer;
        this.m_system = system;
        this.today = Calendar.getInstance().getTime();
    }

    public void printTicket(String sIn, TicketInfo ticket) throws TicketPrinterException {
        this.cUser = ticket.getName();
        this.ticketId = Integer.toString(ticket.getTicketId());
        this.pickupId = Integer.toString(ticket.getPickupId());
        if (ticket.getTicketId() == 0) {
            this.ticketId = "No Sale";
        }
        if (ticket.getPickupId() == 0) {
            this.pickupId = "No PickupId";
        }
        this.printTicket(new StringReader(sIn));
    }

    public void printTicket(String sIn) throws TicketPrinterException {
        this.printTicket(new StringReader(sIn));
    }

    public void printTicket(Reader in) throws TicketPrinterException {
        try {
            if (m_sp == null) {
                SAXParserFactory spf = SAXParserFactory.newInstance();
                m_sp = spf.newSAXParser();
            }
            m_sp.parse(new InputSource(in), (DefaultHandler)this);
        }
        catch (ParserConfigurationException ePC) {
            throw new TicketPrinterException(LocalRes.getIntString("exception.parserconfig"), ePC);
        }
        catch (SAXException eSAX) {
            throw new TicketPrinterException(LocalRes.getIntString("exception.xmlfile"), eSAX);
        }
        catch (IOException eIO) {
            throw new TicketPrinterException(LocalRes.getIntString("exception.iofile"), eIO);
        }
    }

    @Override
    public void startDocument() throws SAXException {
        this.text = null;
        this.bctype = null;
        this.bcposition = null;
        this.m_sVisorLine = null;
        this.m_iVisorAnimation = 0;
        this.m_sVisorLine1 = null;
        this.m_sVisorLine2 = null;
        this.m_iOutputType = 0;
        this.m_oOutputPrinter = null;
    }

    @Override
    public void endDocument() throws SAXException {
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        String openDate = this.df.format(this.today);
        Date dNow = new Date();
        switch (this.m_iOutputType) {
            case 0: {
                switch (qName) {
                    case "opendrawer": {
                        this.m_printer.getDevicePrinter(this.readString(attributes.getValue("printer"), "1")).openDrawer();
                        try {
                            this.m_system.execDrawerOpened(new Object[]{this.cUser, this.ticketId});
                        }
                        catch (BasicException basicException) {}
                        break;
                    }
                    case "play": {
                        this.text = new StringBuilder();
                        break;
                    }
                    case "ticket": {
                        this.m_iOutputType = 2;
                        this.m_oOutputPrinter = this.m_printer.getDevicePrinter(this.readString(attributes.getValue("printer"), "1"));
                        this.m_oOutputPrinter.beginReceipt();
                        break;
                    }
                    case "display": {
                        this.m_iOutputType = 1;
                        String animation = attributes.getValue("animation");
                        this.m_iVisorAnimation = "scroll".equals(animation) ? 2 : ("flyer".equals(animation) ? 1 : ("blink".equals(animation) ? 3 : ("curtain".equals(animation) ? 4 : 0)));
                        this.m_sVisorLine1 = null;
                        this.m_sVisorLine2 = null;
                        this.m_oOutputPrinter = null;
                        break;
                    }
                    case "fiscalreceipt": {
                        this.m_iOutputType = 3;
                        this.m_printer.getFiscalPrinter().beginReceipt();
                        break;
                    }
                    case "fiscalzreport": {
                        this.m_printer.getFiscalPrinter().printZReport();
                        break;
                    }
                    case "fiscalxreport": {
                        this.m_printer.getFiscalPrinter().printXReport();
                    }
                }
                break;
            }
            case 2: {
                if ("logo".equals(qName)) {
                    this.text = new StringBuilder();
                    break;
                }
                if ("image".equals(qName)) {
                    this.text = new StringBuilder();
                    break;
                }
                if ("barcode".equals(qName)) {
                    this.text = new StringBuilder();
                    this.bctype = attributes.getValue("type");
                    this.bcposition = attributes.getValue("position");
                    break;
                }
                if ("line".equals(qName)) {
                    this.m_oOutputPrinter.beginLine(this.parseInt(attributes.getValue("size"), 0));
                    break;
                }
                if (!"text".equals(qName)) break;
                this.text = new StringBuilder();
                this.m_iTextStyle = 1 | ("true".equals(attributes.getValue("underline")) ? 2 : 0);
                String sAlign = attributes.getValue("align");
                this.m_iTextAlign = "right".equals(sAlign) ? 1 : ("center".equals(sAlign) ? 2 : 0);
                this.m_iTextLength = this.parseInt(attributes.getValue("length"), 0);
                break;
            }
            case 1: {
                if ("line".equals(qName)) {
                    this.m_sVisorLine = new StringBuilder();
                    break;
                }
                if ("line1".equals(qName)) {
                    this.m_sVisorLine = new StringBuilder();
                    break;
                }
                if ("line2".equals(qName)) {
                    this.m_sVisorLine = new StringBuilder();
                    break;
                }
                if (!"text".equals(qName)) break;
                this.text = new StringBuilder();
                String sAlign = attributes.getValue("align");
                this.m_iTextAlign = "right".equals(sAlign) ? 1 : ("center".equals(sAlign) ? 2 : 0);
                this.m_iTextLength = this.parseInt(attributes.getValue("length"));
                break;
            }
            case 3: {
                if ("line".equals(qName)) {
                    this.text = new StringBuilder();
                    this.m_dValue1 = this.parseDouble(attributes.getValue("price"));
                    this.m_dValue2 = this.parseDouble(attributes.getValue("units"), 1.0);
                    this.attribute3 = this.parseInt(attributes.getValue("tax"));
                    break;
                }
                if ("message".equals(qName)) {
                    this.text = new StringBuilder();
                    break;
                }
                if (!"total".equals(qName)) break;
                this.text = new StringBuilder();
                this.m_dValue1 = this.parseDouble(attributes.getValue("paid"));
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch (this.m_iOutputType) {
            case 0: {
                if (!"play".equals(qName)) break;
                try {
                    URL url = this.getClass().getClassLoader().getResource(this.text.toString());
                    if (url != null) {
                        Clip clip = AudioSystem.getClip();
                        AudioInputStream inputStream = AudioSystem.getAudioInputStream(url);
                        clip.open(inputStream);
                        clip.start();
                    }
                }
                catch (Exception url) {
                    // empty catch block
                }
                this.text = null;
                break;
            }
            case 2: {
                if ("logo".equals(qName)) {
                    this.m_oOutputPrinter.printLogo();
                    break;
                }
                if ("image".equals(qName)) {
                    try {
                        BufferedImage image = this.m_system.getResourceAsImage(this.text.toString());
                        if (image != null) {
                            this.m_oOutputPrinter.printImage(image);
                        }
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                    this.text = null;
                    break;
                }
                if ("barcode".equals(qName)) {
                    this.m_oOutputPrinter.printBarCode(this.bctype, this.bcposition, this.text.toString());
                    this.text = null;
                    break;
                }
                if ("text".equals(qName)) {
                    if (this.m_iTextLength > 0) {
                        switch (this.m_iTextAlign) {
                            case 1: {
                                this.m_oOutputPrinter.printText(this.m_iTextStyle, DeviceTicket.alignRight(this.text.toString(), this.m_iTextLength));
                                break;
                            }
                            case 2: {
                                this.m_oOutputPrinter.printText(this.m_iTextStyle, DeviceTicket.alignCenter(this.text.toString(), this.m_iTextLength));
                                break;
                            }
                            default: {
                                this.m_oOutputPrinter.printText(this.m_iTextStyle, DeviceTicket.alignLeft(this.text.toString(), this.m_iTextLength));
                                break;
                            }
                        }
                    } else {
                        this.m_oOutputPrinter.printText(this.m_iTextStyle, this.text.toString());
                    }
                    this.text = null;
                    break;
                }
                if ("line".equals(qName)) {
                    this.m_oOutputPrinter.endLine();
                    break;
                }
                if (!"ticket".equals(qName)) break;
                this.m_oOutputPrinter.endReceipt();
                this.m_iOutputType = 0;
                this.m_oOutputPrinter = null;
                break;
            }
            case 1: {
                if ("line".equals(qName)) {
                    if (this.m_sVisorLine1 == null) {
                        this.m_sVisorLine1 = this.m_sVisorLine.toString();
                    } else {
                        this.m_sVisorLine2 = this.m_sVisorLine.toString();
                    }
                    this.m_sVisorLine = null;
                    break;
                }
                if ("line1".equals(qName)) {
                    this.m_sVisorLine1 = this.m_sVisorLine.toString();
                    this.m_sVisorLine = null;
                    break;
                }
                if ("line2".equals(qName)) {
                    this.m_sVisorLine2 = this.m_sVisorLine.toString();
                    this.m_sVisorLine = null;
                    break;
                }
                if ("text".equals(qName)) {
                    if (this.m_iTextLength > 0) {
                        switch (this.m_iTextAlign) {
                            case 1: {
                                this.m_sVisorLine.append(DeviceTicket.alignRight(this.text.toString(), this.m_iTextLength));
                                break;
                            }
                            case 2: {
                                this.m_sVisorLine.append(DeviceTicket.alignCenter(this.text.toString(), this.m_iTextLength));
                                break;
                            }
                            default: {
                                this.m_sVisorLine.append(DeviceTicket.alignLeft(this.text.toString(), this.m_iTextLength));
                                break;
                            }
                        }
                    } else {
                        this.m_sVisorLine.append((CharSequence)this.text);
                    }
                    this.text = null;
                    break;
                }
                if (!"display".equals(qName)) break;
                this.m_printer.getDeviceDisplay().writeVisor(this.m_iVisorAnimation, this.m_sVisorLine1, this.m_sVisorLine2);
                this.m_iVisorAnimation = 0;
                this.m_sVisorLine1 = null;
                this.m_sVisorLine2 = null;
                this.m_iOutputType = 0;
                this.m_oOutputPrinter = null;
                break;
            }
            case 3: {
                if ("fiscalreceipt".equals(qName)) {
                    this.m_printer.getFiscalPrinter().endReceipt();
                    this.m_iOutputType = 0;
                    break;
                }
                if ("line".equals(qName)) {
                    this.m_printer.getFiscalPrinter().printLine(this.text.toString(), this.m_dValue1, this.m_dValue2, this.attribute3);
                    this.text = null;
                    break;
                }
                if ("message".equals(qName)) {
                    this.m_printer.getFiscalPrinter().printMessage(this.text.toString());
                    this.text = null;
                    break;
                }
                if (!"total".equals(qName)) break;
                this.m_printer.getFiscalPrinter().printTotal(this.text.toString(), this.m_dValue1);
                this.text = null;
            }
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (this.text != null) {
            this.text.append(ch, start, length);
        }
    }

    private int parseInt(String sValue, int iDefault) {
        try {
            return Integer.parseInt(sValue);
        }
        catch (NumberFormatException eNF) {
            return iDefault;
        }
    }

    private int parseInt(String sValue) {
        return this.parseInt(sValue, 0);
    }

    private double parseDouble(String sValue, double ddefault) {
        try {
            return Double.parseDouble(sValue);
        }
        catch (NumberFormatException eNF) {
            return ddefault;
        }
    }

    private double parseDouble(String sValue) {
        return this.parseDouble(sValue, 0.0);
    }

    private String readString(String sValue, String sDefault) {
        if (sValue == null || sValue.equals("")) {
            return sDefault;
        }
        return sValue;
    }
}

