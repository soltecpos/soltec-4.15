/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  gnu.io.CommPort
 *  gnu.io.CommPortIdentifier
 *  gnu.io.NoSuchPortException
 *  gnu.io.PortInUseException
 *  gnu.io.SerialPort
 *  gnu.io.UnsupportedCommOperationException
 */
package com.openbravo.pos.printer.escpos;

import com.openbravo.pos.printer.TicketPrinterException;
import com.openbravo.pos.printer.escpos.PrinterWritter;
import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;
import java.io.IOException;
import java.io.OutputStream;

public class PrinterWritterRXTX
extends PrinterWritter {
    private CommPortIdentifier m_PortIdPrinter;
    private CommPort m_CommPortPrinter;
    private String m_sPortPrinter;
    private OutputStream m_out;

    public PrinterWritterRXTX(String sPortPrinter) throws TicketPrinterException {
        this.m_sPortPrinter = sPortPrinter;
        this.m_out = null;
    }

    @Override
    protected void internalWrite(byte[] data) {
        try {
            if (this.m_out == null) {
                this.m_PortIdPrinter = CommPortIdentifier.getPortIdentifier((String)this.m_sPortPrinter);
                this.m_CommPortPrinter = this.m_PortIdPrinter.open("PORTID", 2000);
                this.m_out = this.m_CommPortPrinter.getOutputStream();
                if (this.m_PortIdPrinter.getPortType() == 1) {
                    ((SerialPort)this.m_CommPortPrinter).setSerialPortParams(9600, 8, 1, 0);
                    ((SerialPort)this.m_CommPortPrinter).setFlowControlMode(1);
                }
            }
            this.m_out.write(data);
        }
        catch (NoSuchPortException | PortInUseException | UnsupportedCommOperationException | IOException e) {
            System.err.println(e);
        }
    }

    @Override
    protected void internalFlush() {
        try {
            if (this.m_out != null) {
                this.m_out.flush();
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
                this.m_CommPortPrinter = null;
                this.m_PortIdPrinter = null;
            }
        }
        catch (IOException e) {
            System.err.println(e);
        }
    }
}

