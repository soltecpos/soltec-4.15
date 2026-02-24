/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  gnu.io.CommPortIdentifier
 *  gnu.io.NoSuchPortException
 *  gnu.io.PortInUseException
 *  gnu.io.SerialPort
 *  gnu.io.SerialPortEvent
 *  gnu.io.SerialPortEventListener
 *  gnu.io.UnsupportedCommOperationException
 */
package com.openbravo.pos.scale;

import com.openbravo.pos.scale.Scale;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.TooManyListenersException;

public class ScaleSamsungEsp
implements Scale,
SerialPortEventListener {
    private CommPortIdentifier m_PortIdPrinter;
    private SerialPort m_CommPortPrinter;
    private String m_sPortScale;
    private OutputStream m_out;
    private InputStream m_in;
    private static final int SCALE_READY = 0;
    private static final int SCALE_READING = 1;
    private static final int SCALE_READINGDECIMALS = 2;
    private double m_dWeightBuffer;
    private double m_dWeightDecimals;
    private int m_iStatusScale;

    public ScaleSamsungEsp(String sPortPrinter) {
        this.m_sPortScale = sPortPrinter;
        this.m_out = null;
        this.m_in = null;
        this.m_iStatusScale = 0;
        this.m_dWeightBuffer = 0.0;
        this.m_dWeightDecimals = 1.0;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Double readWeight() {
        ScaleSamsungEsp scaleSamsungEsp = this;
        synchronized (scaleSamsungEsp) {
            if (this.m_iStatusScale != 0) {
                try {
                    this.wait(1000L);
                }
                catch (InterruptedException interruptedException) {
                    // empty catch block
                }
                if (this.m_iStatusScale != 0) {
                    this.m_iStatusScale = 0;
                }
            }
            this.m_dWeightBuffer = 0.0;
            this.m_dWeightDecimals = 1.0;
            this.write(new byte[]{36});
            this.flush();
            try {
                this.wait(1000L);
            }
            catch (InterruptedException interruptedException) {
                // empty catch block
            }
            if (this.m_iStatusScale == 0) {
                double dWeight = this.m_dWeightBuffer / this.m_dWeightDecimals;
                this.m_dWeightBuffer = 0.0;
                this.m_dWeightDecimals = 1.0;
                return new Double(dWeight);
            }
            this.m_iStatusScale = 0;
            this.m_dWeightBuffer = 0.0;
            this.m_dWeightDecimals = 1.0;
            return new Double(0.0);
        }
    }

    private void flush() {
        try {
            this.m_out.flush();
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    private void write(byte[] data) {
        try {
            if (this.m_out == null) {
                this.m_PortIdPrinter = CommPortIdentifier.getPortIdentifier((String)this.m_sPortScale);
                this.m_CommPortPrinter = (SerialPort)this.m_PortIdPrinter.open("PORTID", 2000);
                this.m_out = this.m_CommPortPrinter.getOutputStream();
                this.m_in = this.m_CommPortPrinter.getInputStream();
                this.m_CommPortPrinter.addEventListener((SerialPortEventListener)this);
                this.m_CommPortPrinter.notifyOnDataAvailable(true);
                this.m_CommPortPrinter.setSerialPortParams(4800, 8, 1, 1);
            }
            this.m_out.write(data);
        }
        catch (NoSuchPortException | PortInUseException | UnsupportedCommOperationException | IOException | TooManyListenersException throwable) {
            // empty catch block
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void serialEvent(SerialPortEvent e) {
        if (e.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
            try {
                while (this.m_in.available() > 0) {
                    int b = this.m_in.read();
                    if (b == 13) {
                        synchronized (this) {
                            this.m_iStatusScale = 0;
                            this.notifyAll();
                        }
                    } else if ((b > 47 && b < 58) || b == 46) {
                        synchronized (this) {
                            if (this.m_iStatusScale == 0) {
                                this.m_dWeightBuffer = 0.0;
                                this.m_dWeightDecimals = 1.0;
                                this.m_iStatusScale = 1;
                            }
                            if (b == 46) {
                                this.m_iStatusScale = 2;
                            } else {
                                this.m_dWeightBuffer = this.m_dWeightBuffer * 10.0 + (double)b - 48.0;
                                if (this.m_iStatusScale == 2) {
                                    this.m_dWeightDecimals *= 10.0;
                                }
                            }
                        }
                    } else {
                        this.m_dWeightBuffer = 0.0;
                        this.m_dWeightDecimals = 1.0;
                        this.m_iStatusScale = 0;
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}

