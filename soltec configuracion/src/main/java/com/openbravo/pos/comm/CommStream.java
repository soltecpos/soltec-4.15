/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  gnu.io.CommPort
 *  gnu.io.CommPortIdentifier
 *  gnu.io.ParallelPort
 *  gnu.io.SerialPort
 */
package com.openbravo.pos.comm;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.ParallelPort;
import gnu.io.SerialPort;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class CommStream {
    private String m_sPort;
    private CommPortIdentifier m_PortIdPrinter;
    private CommPort m_CommPortPrinter;
    private OutputStream m_out;
    private InputStream m_in;

    public CommStream(String port) {
        this.m_sPort = port;
        this.m_PortIdPrinter = null;
        this.m_CommPortPrinter = null;
        this.m_out = null;
        this.m_in = null;
    }

    private void init() {
        try {
            if (this.m_out == null) {
                this.m_PortIdPrinter = CommPortIdentifier.getPortIdentifier((String)this.m_sPort);
                this.m_CommPortPrinter = this.m_PortIdPrinter.open("PORTID", 2000);
                this.m_out = this.m_CommPortPrinter.getOutputStream();
                if (this.m_PortIdPrinter.getPortType() == 1) {
                    ((SerialPort)this.m_CommPortPrinter).setSerialPortParams(9600, 8, 1, 0);
                } else if (this.m_PortIdPrinter.getPortType() == 2) {
                    ((ParallelPort)this.m_CommPortPrinter).setMode(1);
                }
            }
        }
        catch (Exception e) {
            this.m_PortIdPrinter = null;
            this.m_CommPortPrinter = null;
            this.m_out = null;
            this.m_in = null;
        }
    }

    public void closeAll() throws IOException {
        if (this.m_out != null) {
            this.m_in = null;
            this.m_out.flush();
            this.m_out.close();
            this.m_out = null;
            this.m_CommPortPrinter = null;
            this.m_PortIdPrinter = null;
        }
    }

    public String getPort() {
        return this.m_sPort;
    }

    public InputStream getIn() {
        this.init();
        return this.m_in;
    }

    public OutputStream getOut() {
        this.init();
        return this.m_out;
    }
}

