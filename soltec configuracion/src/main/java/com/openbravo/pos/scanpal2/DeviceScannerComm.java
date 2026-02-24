/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  gnu.io.CommPortIdentifier
 *  gnu.io.SerialPort
 *  gnu.io.SerialPortEvent
 *  gnu.io.SerialPortEventListener
 */
package com.openbravo.pos.scanpal2;

import com.openbravo.pos.scanpal2.DeviceScanner;
import com.openbravo.pos.scanpal2.DeviceScannerException;
import com.openbravo.pos.scanpal2.ProductDownloaded;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class DeviceScannerComm
implements DeviceScanner,
SerialPortEventListener {
    private CommPortIdentifier m_PortIdPrinter;
    private SerialPort m_CommPortPrinter;
    private String m_sPort;
    private OutputStream m_out;
    private InputStream m_in;
    private static final byte[] COMMAND_READ = new byte[]{82, 69, 65, 68};
    private static final byte[] COMMAND_CIPHER = new byte[]{67, 73, 80, 72, 69, 82};
    private static final byte[] COMMAND_OVER = new byte[]{79, 86, 69, 82};
    private static final byte[] COMMAND_ACK = new byte[]{65, 67, 75};
    private Queue<byte[]> m_aLines;
    private ByteArrayOutputStream m_abuffer;
    private int m_iStatus;
    private int m_iProductOrder;

    DeviceScannerComm(String sPort) {
        this.m_sPort = sPort;
        this.m_PortIdPrinter = null;
        this.m_CommPortPrinter = null;
        this.m_out = null;
        this.m_in = null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void connectDevice() throws DeviceScannerException {
        try {
            this.m_PortIdPrinter = CommPortIdentifier.getPortIdentifier((String)this.m_sPort);
            this.m_CommPortPrinter = (SerialPort)this.m_PortIdPrinter.open("PORTID", 2000);
            this.m_out = this.m_CommPortPrinter.getOutputStream();
            this.m_in = this.m_CommPortPrinter.getInputStream();
            this.m_CommPortPrinter.addEventListener((SerialPortEventListener)this);
            this.m_CommPortPrinter.notifyOnDataAvailable(true);
            this.m_CommPortPrinter.setSerialPortParams(115200, 8, 1, 0);
        }
        catch (Exception e) {
            this.m_PortIdPrinter = null;
            this.m_CommPortPrinter = null;
            this.m_out = null;
            this.m_in = null;
            throw new DeviceScannerException(e);
        }
        DeviceScannerComm deviceScannerComm = this;
        synchronized (deviceScannerComm) {
            this.m_aLines = new LinkedList<byte[]>();
            this.m_abuffer = new ByteArrayOutputStream();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void disconnectDevice() {
        try {
            this.m_out.close();
            this.m_in.close();
            this.m_CommPortPrinter.close();
        }
        catch (IOException iOException) {
            // empty catch block
        }
        DeviceScannerComm deviceScannerComm = this;
        synchronized (deviceScannerComm) {
            this.m_aLines = null;
            this.m_abuffer = null;
        }
        this.m_PortIdPrinter = null;
        this.m_CommPortPrinter = null;
        this.m_out = null;
        this.m_in = null;
    }

    @Override
    public void startDownloadProduct() throws DeviceScannerException {
        this.writeLine(COMMAND_READ);
        this.readCommand(COMMAND_ACK);
    }

    @Override
    public ProductDownloaded recieveProduct() throws DeviceScannerException {
        byte[] line = this.readLine();
        if (this.checkCommand(COMMAND_OVER, line)) {
            return null;
        }
        ProductDownloaded p = new ProductDownloaded();
        try {
            String sLine = new String(line, 1, line.length - 3, "ISO-8859-1");
            StringTokenizer T = new StringTokenizer(sLine, "|");
            while (T.hasMoreTokens()) {
                String sToken = T.nextToken();
                if (sToken.startsWith("IEAN")) {
                    p.setCode(sToken.substring(4).trim());
                    continue;
                }
                if (!sToken.startsWith("ICANT")) continue;
                try {
                    p.setQuantity(Double.parseDouble(sToken.substring(5).trim()));
                }
                catch (NumberFormatException numberFormatException) {}
            }
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            // empty catch block
        }
        this.writeLine(COMMAND_ACK);
        return p;
    }

    @Override
    public void startUploadProduct() throws DeviceScannerException {
        this.writeLine(COMMAND_CIPHER);
        this.readCommand(COMMAND_ACK);
        this.m_iProductOrder = 0;
    }

    @Override
    public void sendProduct(String sName, String sCode, Double dPrice) throws DeviceScannerException {
        ++this.m_iProductOrder;
        ByteArrayOutputStream lineout = new ByteArrayOutputStream();
        try {
            lineout.write(this.convert(Integer.toString(this.m_iProductOrder)));
            lineout.write(124);
            lineout.write(this.convert(sName));
            lineout.write(124);
            lineout.write(this.convert(sCode));
            lineout.write(124);
            lineout.write(124);
            lineout.write(124);
            lineout.write(124);
            lineout.write(this.convert(dPrice.toString()));
            lineout.write(124);
            lineout.write(this.calcCheckSum1(lineout.toByteArray()));
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
        }
        catch (IOException iOException) {
            // empty catch block
        }
        this.writeLine(lineout.toByteArray());
        this.readCommand(COMMAND_ACK);
    }

    @Override
    public void stopUploadProduct() throws DeviceScannerException {
        this.writeLine(COMMAND_OVER);
        this.readCommand(COMMAND_ACK);
    }

    private void readCommand(byte[] cmd) throws DeviceScannerException {
        byte[] b = this.readLine();
        if (!this.checkCommand(cmd, b)) {
            throw new DeviceScannerException("Command not expected");
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void writeLine(byte[] aline) throws DeviceScannerException {
        if (this.m_CommPortPrinter == null) {
            throw new DeviceScannerException("No Serial port opened");
        }
        DeviceScannerComm deviceScannerComm = this;
        synchronized (deviceScannerComm) {
            try {
                this.m_out.write(aline);
                this.m_out.write(13);
                this.m_out.flush();
            }
            catch (IOException e) {
                throw new DeviceScannerException(e);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private byte[] readLine() throws DeviceScannerException {
        DeviceScannerComm deviceScannerComm = this;
        synchronized (deviceScannerComm) {
            if (!this.m_aLines.isEmpty()) {
                return this.m_aLines.poll();
            }
            try {
                this.wait(1000L);
            }
            catch (InterruptedException interruptedException) {
                // empty catch block
            }
            if (this.m_aLines.isEmpty()) {
                throw new DeviceScannerException("Timeout");
            }
            return this.m_aLines.poll();
        }
    }

    private byte[] convert(String sdata) {
        if (sdata == null) {
            return new byte[0];
        }
        byte[] result = new byte[sdata.length()];
        for (int i = 0; i < sdata.length(); ++i) {
            char c = sdata.charAt(i);
            result[i] = (byte) (c == '|' ? 46 : (c >= ' ' && c < '\u0080' ? (int)c : 32));
        }
        return result;
    }

    private byte[] calcCheckSum1(byte[] adata) {
        byte low;
        int isum = 0;
        for (int i = 0; i < adata.length; ++i) {
            isum += adata[i];
        }
        byte high = (byte)((isum & 0xFF00) >> 8);
        if (high == 13) {
            high = 14;
        }
        if ((low = (byte)(isum & 0xFF)) == 13) {
            low = 14;
        }
        byte[] result = new byte[]{high, low};
        return result;
    }

    private boolean checkCommand(byte[] bcommand, byte[] brecieved) {
        if (bcommand.length == brecieved.length) {
            for (int i = 0; i < bcommand.length; ++i) {
                if (bcommand[i] == brecieved[i]) continue;
                return false;
            }
            return true;
        }
        return false;
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
                    synchronized (this) {
                        if (b == 13) {
                            this.m_aLines.add(this.m_abuffer.toByteArray());
                            this.m_abuffer.reset();
                            this.notifyAll();
                        } else {
                            this.m_abuffer.write(b);
                        }
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}

