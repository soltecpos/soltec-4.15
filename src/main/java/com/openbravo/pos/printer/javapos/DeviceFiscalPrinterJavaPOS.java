/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jpos.FiscalPrinter
 *  jpos.JposException
 */
package com.openbravo.pos.printer.javapos;

import com.openbravo.pos.printer.DeviceFiscalPrinter;
import com.openbravo.pos.printer.TicketPrinterException;
import com.openbravo.pos.util.RoundUtils;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;
import jpos.FiscalPrinter;
import jpos.JposException;

public class DeviceFiscalPrinterJavaPOS
extends JPanel
implements DeviceFiscalPrinter {
    private String m_sName;
    private FiscalPrinter m_fiscal;
    private JButton jButton1;
    private JButton jButton2;
    private JCheckBox jCheckBox1;
    private JCheckBox jCheckBox2;
    private JCheckBox jCheckBox3;
    private JCheckBox jCheckBox4;
    private JCheckBox jCheckBox5;
    private JPanel jPanel1;
    private JTextField jTextField1;
    private JTextField jTextField2;
    private JTextField jTextField3;
    private JTextField jTextField4;
    private JTextField jTextField5;

    public DeviceFiscalPrinterJavaPOS(String sDeviceFiscalPrinterName) throws TicketPrinterException {
        this.m_sName = sDeviceFiscalPrinterName;
        this.m_fiscal = new FiscalPrinter();
        try {
            this.m_fiscal.open(this.m_sName);
            this.m_fiscal.claim(10000);
            this.m_fiscal.setDeviceEnabled(true);
            this.m_fiscal.setCheckTotal(false);
        }
        catch (JposException e) {
            throw new TicketPrinterException(e.getMessage(), e);
        }
        this.initComponents();
    }

    @Override
    public String getFiscalName() {
        return this.m_sName;
    }

    @Override
    public JComponent getFiscalComponent() {
        return this;
    }

    @Override
    public void beginReceipt() {
        try {
            this.m_fiscal.beginFiscalReceipt(true);
        }
        catch (JposException jposException) {
            // empty catch block
        }
    }

    @Override
    public void endReceipt() {
        try {
            this.m_fiscal.endFiscalReceipt(false);
        }
        catch (JposException jposException) {
            // empty catch block
        }
    }

    @Override
    public void printLine(String sproduct, double dprice, double dunits, int taxinfo) {
        try {
            this.m_fiscal.printRecItem(sproduct, (long)this.roundFiscal(dprice * dunits), (int)(dunits * 1000.0), taxinfo, (long)this.roundFiscal(dprice), "");
        }
        catch (JposException jposException) {
            // empty catch block
        }
    }

    @Override
    public void printMessage(String smessage) {
        try {
            this.m_fiscal.printRecMessage(smessage);
        }
        catch (JposException jposException) {
            // empty catch block
        }
    }

    @Override
    public void printTotal(String sPayment, double dpaid) {
        try {
            this.m_fiscal.printRecTotal(0L, (long)this.roundFiscal(dpaid), sPayment);
        }
        catch (JposException jposException) {
            // empty catch block
        }
    }

    @Override
    public void printZReport() {
        try {
            this.m_fiscal.printZReport();
        }
        catch (JposException jposException) {
            // empty catch block
        }
    }

    @Override
    public void printXReport() {
        try {
            this.m_fiscal.printXReport();
        }
        catch (JposException jposException) {
            // empty catch block
        }
    }

    public void finalize() throws Throwable {
        this.m_fiscal.setDeviceEnabled(false);
        this.m_fiscal.release();
        this.m_fiscal.close();
        super.finalize();
    }

    private int roundFiscal(double value) {
        return (int)Math.floor(RoundUtils.round(value) * 10000.0 + 0.5);
    }

    private void initComponents() {
        this.jButton1 = new JButton();
        this.jPanel1 = new JPanel();
        this.jTextField1 = new JTextField();
        this.jCheckBox1 = new JCheckBox();
        this.jTextField2 = new JTextField();
        this.jCheckBox2 = new JCheckBox();
        this.jTextField3 = new JTextField();
        this.jTextField4 = new JTextField();
        this.jTextField5 = new JTextField();
        this.jCheckBox3 = new JCheckBox();
        this.jCheckBox4 = new JCheckBox();
        this.jCheckBox5 = new JCheckBox();
        this.jButton2 = new JButton();
        this.setLayout(null);
        this.jButton1.setFont(new Font("Arial", 0, 12));
        this.jButton1.setText("*X Report");
        this.jButton1.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                DeviceFiscalPrinterJavaPOS.this.jButton1ActionPerformed(evt);
            }
        });
        this.add(this.jButton1);
        this.jButton1.setBounds(30, 10, 130, 23);
        this.jPanel1.setBorder(BorderFactory.createTitledBorder("*Receipt Title"));
        this.jPanel1.setFont(new Font("Arial", 0, 12));
        this.jPanel1.setLayout(null);
        this.jTextField1.setFont(new Font("Arial", 0, 12));
        this.jTextField1.setText("jTextField1");
        this.jPanel1.add(this.jTextField1);
        this.jTextField1.setBounds(20, 30, 260, 25);
        this.jCheckBox1.setFont(new Font("Arial", 0, 12));
        this.jCheckBox1.setText("jCheckBox1");
        this.jCheckBox1.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        this.jCheckBox1.setMargin(new Insets(0, 0, 0, 0));
        this.jPanel1.add(this.jCheckBox1);
        this.jCheckBox1.setBounds(290, 30, 110, 25);
        this.jTextField2.setFont(new Font("Arial", 0, 12));
        this.jTextField2.setText("jTextField2");
        this.jPanel1.add(this.jTextField2);
        this.jTextField2.setBounds(20, 60, 260, 25);
        this.jCheckBox2.setFont(new Font("Arial", 0, 12));
        this.jCheckBox2.setText("jCheckBox2");
        this.jCheckBox2.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        this.jCheckBox2.setMargin(new Insets(0, 0, 0, 0));
        this.jPanel1.add(this.jCheckBox2);
        this.jCheckBox2.setBounds(290, 60, 110, 25);
        this.jTextField3.setFont(new Font("Arial", 0, 12));
        this.jTextField3.setText("jTextField3");
        this.jPanel1.add(this.jTextField3);
        this.jTextField3.setBounds(20, 90, 260, 25);
        this.jTextField4.setFont(new Font("Arial", 0, 12));
        this.jTextField4.setText("jTextField4");
        this.jPanel1.add(this.jTextField4);
        this.jTextField4.setBounds(20, 120, 260, 25);
        this.jTextField5.setFont(new Font("Arial", 0, 12));
        this.jTextField5.setText("jTextField5");
        this.jPanel1.add(this.jTextField5);
        this.jTextField5.setBounds(20, 150, 260, 25);
        this.jCheckBox3.setFont(new Font("Arial", 0, 12));
        this.jCheckBox3.setText("jCheckBox3");
        this.jCheckBox3.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        this.jCheckBox3.setMargin(new Insets(0, 0, 0, 0));
        this.jPanel1.add(this.jCheckBox3);
        this.jCheckBox3.setBounds(290, 90, 110, 25);
        this.jCheckBox4.setFont(new Font("Arial", 0, 12));
        this.jCheckBox4.setText("jCheckBox4");
        this.jCheckBox4.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        this.jCheckBox4.setMargin(new Insets(0, 0, 0, 0));
        this.jPanel1.add(this.jCheckBox4);
        this.jCheckBox4.setBounds(290, 120, 110, 25);
        this.jCheckBox5.setFont(new Font("Arial", 0, 12));
        this.jCheckBox5.setText("jCheckBox5");
        this.jCheckBox5.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        this.jCheckBox5.setMargin(new Insets(0, 0, 0, 0));
        this.jPanel1.add(this.jCheckBox5);
        this.jCheckBox5.setBounds(290, 150, 110, 25);
        this.jButton2.setFont(new Font("Arial", 0, 12));
        this.jButton2.setText("*Z Report");
        this.jButton2.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                DeviceFiscalPrinterJavaPOS.this.jButton2ActionPerformed(evt);
            }
        });
        this.jPanel1.add(this.jButton2);
        this.jButton2.setBounds(20, 220, 130, 23);
        this.add(this.jPanel1);
        this.jPanel1.setBounds(10, 60, 470, 260);
    }

    private void jButton2ActionPerformed(ActionEvent evt) {
        this.printZReport();
    }

    private void jButton1ActionPerformed(ActionEvent evt) {
        this.printXReport();
    }
}

