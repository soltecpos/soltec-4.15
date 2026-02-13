/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.config;

import com.openbravo.data.user.DirtyManager;
import com.openbravo.pos.config.PanelConfig;
import com.openbravo.pos.config.ParametersConfig;
import com.openbravo.pos.config.ParametersPrinter;
import com.openbravo.pos.forms.AppConfig;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.util.ReportUtils;
import com.openbravo.pos.util.StringParser;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.swing.GroupLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class JPanelConfigPeripheral
extends JPanel
implements PanelConfig {
    private final DirtyManager dirty = new DirtyManager();
    private PrintService[] printServices;
    private final ParametersConfig printer1printerparams;
    private final ParametersConfig printer2printerparams;
    private final ParametersConfig printer3printerparams;
    private final ParametersConfig printer4printerparams;
    private final ParametersConfig printer5printerparams;
    private final ParametersConfig printer6printerparams;
    private JComboBox<String> cboPrinters;
    private JLabel jLabel10;
    private JLabel jLabel11;
    private JLabel jLabel12;
    private JLabel jLabel13;
    private JLabel jLabel14;
    private JLabel jLabel20;
    private JLabel jLabel21;
    private JLabel jLabel22;
    private JLabel jLabel23;
    private JLabel jLabel24;
    private JLabel jLabel27;
    private JLabel jLabel28;
    private JLabel jLabel30;
    private JLabel jLabel31;
    private JLabel jLabel33;
    private JLabel jLabel34;
    private JLabel jLabel36;
    private JLabel jLabel37;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JLabel jLabel8;
    private JLabel jLabel9;
    private JPanel jPanel1;
    private JPanel jPanel10;
    private JPanel jPanel11;
    private JPanel jPanel12;
    private JPanel jPanel13;
    private JPanel jPanel14;
    private JPanel jPanel15;
    private JPanel jPanel16;
    private JPanel jPanel17;
    private JPanel jPanel18;
    private JPanel jPanel19;
    private JPanel jPanel2;
    private JPanel jPanel20;
    private JPanel jPanel21;
    private JPanel jPanel22;
    private JPanel jPanel23;
    private JPanel jPanel24;
    private JPanel jPanel25;
    private JPanel jPanel26;
    private JPanel jPanel27;
    private JPanel jPanel3;
    private JPanel jPanel4;
    private JPanel jPanel5;
    private JPanel jPanel6;
    private JPanel jPanel7;
    private JPanel jPanel8;
    private JPanel jPanel9;
    private JComboBox<String> jcboConnDisplay;
    private JComboBox<String> jcboConnPrinter;
    private JComboBox<String> jcboConnPrinter2;
    private JComboBox<String> jcboConnPrinter3;
    private JComboBox<String> jcboConnPrinter4;
    private JComboBox<String> jcboConnPrinter5;
    private JComboBox<String> jcboConnPrinter6;
    private JComboBox<String> jcboMachineDisplay;
    private JComboBox<String> jcboMachinePrinter;
    private JComboBox<String> jcboMachinePrinter2;
    private JComboBox<String> jcboMachinePrinter3;
    private JComboBox<String> jcboMachinePrinter4;
    private JComboBox<String> jcboMachinePrinter5;
    private JComboBox<String> jcboMachinePrinter6;
    private JComboBox<String> jcboMachineScale;
    private JComboBox<String> jcboMachineScanner;
    private JComboBox<String> jcboSerialDisplay;
    private JComboBox<String> jcboSerialPrinter;
    private JComboBox<String> jcboSerialPrinter2;
    private JComboBox<String> jcboSerialPrinter3;
    private JComboBox<String> jcboSerialPrinter4;
    private JComboBox<String> jcboSerialPrinter5;
    private JComboBox<String> jcboSerialPrinter6;
    private JComboBox<String> jcboSerialScale;
    private JComboBox<String> jcboSerialScanner;
    private JLabel jlblConnDisplay;
    private JLabel jlblConnPrinter;
    private JLabel jlblConnPrinter2;
    private JLabel jlblConnPrinter3;
    private JLabel jlblConnPrinter4;
    private JLabel jlblConnPrinter5;
    private JLabel jlblConnPrinter6;
    private JLabel jlblDisplayPort;
    private JLabel jlblPrinterPort;
    private JLabel jlblPrinterPort2;
    private JLabel jlblPrinterPort3;
    private JLabel jlblPrinterPort4;
    private JLabel jlblPrinterPort5;
    private JLabel jlblPrinterPort6;
    private JLabel jlblPrinterPort7;
    private JLabel jlblPrinterPort8;
    private JPanel m_jDisplayParams;
    private JPanel m_jPrinterParams1;
    private JPanel m_jPrinterParams2;
    private JPanel m_jPrinterParams3;
    private JPanel m_jPrinterParams4;
    private JPanel m_jPrinterParams5;
    private JPanel m_jPrinterParams6;
    private JPanel m_jScaleParams;
    private JPanel m_jScannerParams;
    private JTextField m_jtxtJPOSDrawer;
    private JTextField m_jtxtJPOSDrawer2;
    private JTextField m_jtxtJPOSDrawer3;
    private JTextField m_jtxtJPOSDrawer4;
    private JTextField m_jtxtJPOSDrawer5;
    private JTextField m_jtxtJPOSDrawer6;
    private JTextField m_jtxtJPOSName;
    private JTextField m_jtxtJPOSPrinter;
    private JTextField m_jtxtJPOSPrinter2;
    private JTextField m_jtxtJPOSPrinter3;
    private JTextField m_jtxtJPOSPrinter4;
    private JTextField m_jtxtJPOSPrinter5;
    private JTextField m_jtxtJPOSPrinter6;
    private JLabel webLabel1;
    private JLabel webLabel2;
    private JLabel webLabel3;
    private JLabel webLbliButton;
    private JSlider webSlider;
    private JCheckBox webSwtch_iButton;

    public JPanelConfigPeripheral() {
        this.initComponents();
        this.printServices = PrintServiceLookup.lookupPrintServices(null, null);
        String[] printernames = ReportUtils.getPrintNames();
        this.jcboMachineDisplay.addActionListener(this.dirty);
        this.jcboConnDisplay.addActionListener(this.dirty);
        this.jcboSerialDisplay.addActionListener(this.dirty);
        this.m_jtxtJPOSName.getDocument().addDocumentListener(this.dirty);
        this.jcboMachinePrinter.addActionListener(this.dirty);
        this.jcboConnPrinter.addActionListener(this.dirty);
        this.jcboSerialPrinter.addActionListener(this.dirty);
        this.m_jtxtJPOSPrinter.getDocument().addDocumentListener(this.dirty);
        this.m_jtxtJPOSDrawer.getDocument().addDocumentListener(this.dirty);
        this.printer1printerparams = new ParametersPrinter(printernames);
        this.printer1printerparams.addDirtyManager(this.dirty);
        this.m_jPrinterParams1.add(this.printer1printerparams.getComponent(), "printer");
        this.jcboMachinePrinter2.addActionListener(this.dirty);
        this.jcboConnPrinter2.addActionListener(this.dirty);
        this.jcboSerialPrinter2.addActionListener(this.dirty);
        this.m_jtxtJPOSPrinter2.getDocument().addDocumentListener(this.dirty);
        this.m_jtxtJPOSDrawer2.getDocument().addDocumentListener(this.dirty);
        this.printer2printerparams = new ParametersPrinter(printernames);
        this.printer2printerparams.addDirtyManager(this.dirty);
        this.m_jPrinterParams2.add(this.printer2printerparams.getComponent(), "printer");
        this.jcboMachinePrinter3.addActionListener(this.dirty);
        this.jcboConnPrinter3.addActionListener(this.dirty);
        this.jcboSerialPrinter3.addActionListener(this.dirty);
        this.m_jtxtJPOSPrinter3.getDocument().addDocumentListener(this.dirty);
        this.m_jtxtJPOSDrawer3.getDocument().addDocumentListener(this.dirty);
        this.printer3printerparams = new ParametersPrinter(printernames);
        this.printer3printerparams.addDirtyManager(this.dirty);
        this.m_jPrinterParams3.add(this.printer3printerparams.getComponent(), "printer");
        this.jcboMachinePrinter4.addActionListener(this.dirty);
        this.jcboConnPrinter4.addActionListener(this.dirty);
        this.jcboSerialPrinter4.addActionListener(this.dirty);
        this.m_jtxtJPOSPrinter4.getDocument().addDocumentListener(this.dirty);
        this.m_jtxtJPOSDrawer4.getDocument().addDocumentListener(this.dirty);
        this.printer4printerparams = new ParametersPrinter(printernames);
        this.printer4printerparams.addDirtyManager(this.dirty);
        this.m_jPrinterParams4.add(this.printer4printerparams.getComponent(), "printer");
        this.jcboMachinePrinter5.addActionListener(this.dirty);
        this.jcboConnPrinter5.addActionListener(this.dirty);
        this.jcboSerialPrinter5.addActionListener(this.dirty);
        this.m_jtxtJPOSPrinter5.getDocument().addDocumentListener(this.dirty);
        this.m_jtxtJPOSDrawer5.getDocument().addDocumentListener(this.dirty);
        this.printer5printerparams = new ParametersPrinter(printernames);
        this.printer5printerparams.addDirtyManager(this.dirty);
        this.m_jPrinterParams5.add(this.printer5printerparams.getComponent(), "printer");
        this.jcboMachinePrinter6.addActionListener(this.dirty);
        this.jcboConnPrinter6.addActionListener(this.dirty);
        this.jcboSerialPrinter6.addActionListener(this.dirty);
        this.m_jtxtJPOSPrinter6.getDocument().addDocumentListener(this.dirty);
        this.m_jtxtJPOSDrawer6.getDocument().addDocumentListener(this.dirty);
        this.printer6printerparams = new ParametersPrinter(printernames);
        this.printer6printerparams.addDirtyManager(this.dirty);
        this.m_jPrinterParams6.add(this.printer6printerparams.getComponent(), "printer");
        this.jcboMachineScale.addActionListener(this.dirty);
        this.jcboSerialScale.addActionListener(this.dirty);
        this.jcboMachineScanner.addActionListener(this.dirty);
        this.jcboSerialScanner.addActionListener(this.dirty);
        this.cboPrinters.addActionListener(this.dirty);
        this.webSwtch_iButton.addActionListener(this.dirty);
        this.webSlider.addChangeListener(this.dirty);
        this.jcboMachinePrinter.addItem("Not defined");
        this.jcboMachinePrinter.addItem("screen");
        this.jcboMachinePrinter.addItem("printer");
        this.jcboMachinePrinter.addItem("epson");
        this.jcboMachinePrinter.addItem("tmu220");
        this.jcboMachinePrinter.addItem("star");
        this.jcboMachinePrinter.addItem("ODP1000");
        this.jcboMachinePrinter.addItem("ithaca");
        this.jcboMachinePrinter.addItem("surepos");
        this.jcboMachinePrinter.addItem("plain");
        this.jcboMachinePrinter.addItem("javapos");
        this.jcboConnPrinter.addItem("file");
        this.jcboConnPrinter.addItem("serial");
        this.jcboSerialPrinter.addItem("COM1");
        this.jcboSerialPrinter.addItem("COM2");
        this.jcboSerialPrinter.addItem("COM3");
        this.jcboSerialPrinter.addItem("COM4");
        this.jcboSerialPrinter.addItem("COM5");
        this.jcboSerialPrinter.addItem("COM6");
        this.jcboSerialPrinter.addItem("COM7");
        this.jcboSerialPrinter.addItem("COM8");
        this.jcboSerialPrinter.addItem("LPT1");
        this.jcboSerialPrinter.addItem("/dev/ttyS0");
        this.jcboSerialPrinter.addItem("/dev/ttyS1");
        this.jcboSerialPrinter.addItem("/dev/ttyS2");
        this.jcboSerialPrinter.addItem("/dev/ttyS3");
        this.jcboSerialPrinter.addItem("/dev/ttyS4");
        this.jcboSerialPrinter.addItem("/dev/ttyS5");
        this.jcboMachinePrinter2.addItem("Not defined");
        this.jcboMachinePrinter2.addItem("screen");
        this.jcboMachinePrinter2.addItem("printer");
        this.jcboMachinePrinter2.addItem("epson");
        this.jcboMachinePrinter2.addItem("tmu220");
        this.jcboMachinePrinter2.addItem("star");
        this.jcboMachinePrinter2.addItem("ODP1000");
        this.jcboMachinePrinter2.addItem("ithaca");
        this.jcboMachinePrinter2.addItem("surepos");
        this.jcboMachinePrinter2.addItem("plain");
        this.jcboMachinePrinter2.addItem("javapos");
        this.jcboConnPrinter2.addItem("file");
        this.jcboConnPrinter2.addItem("serial");
        this.jcboSerialPrinter2.addItem("COM1");
        this.jcboSerialPrinter2.addItem("COM2");
        this.jcboSerialPrinter2.addItem("COM3");
        this.jcboSerialPrinter2.addItem("COM4");
        this.jcboSerialPrinter2.addItem("COM5");
        this.jcboSerialPrinter2.addItem("COM6");
        this.jcboSerialPrinter2.addItem("COM7");
        this.jcboSerialPrinter2.addItem("COM8");
        this.jcboSerialPrinter2.addItem("LPT1");
        this.jcboSerialPrinter2.addItem("/dev/ttyS0");
        this.jcboSerialPrinter2.addItem("/dev/ttyS1");
        this.jcboSerialPrinter2.addItem("/dev/ttyS2");
        this.jcboSerialPrinter2.addItem("/dev/ttyS3");
        this.jcboSerialPrinter2.addItem("/dev/ttyS4");
        this.jcboSerialPrinter2.addItem("/dev/ttyS5");
        this.jcboMachinePrinter3.addItem("Not defined");
        this.jcboMachinePrinter3.addItem("screen");
        this.jcboMachinePrinter3.addItem("printer");
        this.jcboMachinePrinter3.addItem("epson");
        this.jcboMachinePrinter3.addItem("tmu220");
        this.jcboMachinePrinter3.addItem("star");
        this.jcboMachinePrinter3.addItem("ODP1000");
        this.jcboMachinePrinter3.addItem("ithaca");
        this.jcboMachinePrinter3.addItem("surepos");
        this.jcboMachinePrinter3.addItem("plain");
        this.jcboMachinePrinter3.addItem("javapos");
        this.jcboConnPrinter3.addItem("file");
        this.jcboConnPrinter3.addItem("serial");
        this.jcboSerialPrinter3.addItem("COM1");
        this.jcboSerialPrinter3.addItem("COM2");
        this.jcboSerialPrinter3.addItem("COM3");
        this.jcboSerialPrinter3.addItem("COM4");
        this.jcboSerialPrinter3.addItem("COM5");
        this.jcboSerialPrinter3.addItem("COM6");
        this.jcboSerialPrinter3.addItem("COM7");
        this.jcboSerialPrinter3.addItem("COM8");
        this.jcboSerialPrinter3.addItem("LPT1");
        this.jcboSerialPrinter3.addItem("/dev/ttyS0");
        this.jcboSerialPrinter3.addItem("/dev/ttyS1");
        this.jcboSerialPrinter3.addItem("/dev/ttyS2");
        this.jcboSerialPrinter3.addItem("/dev/ttyS3");
        this.jcboSerialPrinter3.addItem("/dev/ttyS4");
        this.jcboSerialPrinter3.addItem("/dev/ttyS5");
        this.jcboMachinePrinter4.addItem("Not defined");
        this.jcboMachinePrinter4.addItem("screen");
        this.jcboMachinePrinter4.addItem("printer");
        this.jcboMachinePrinter4.addItem("epson");
        this.jcboMachinePrinter4.addItem("tmu220");
        this.jcboMachinePrinter4.addItem("star");
        this.jcboMachinePrinter4.addItem("ODP1000");
        this.jcboMachinePrinter4.addItem("ithaca");
        this.jcboMachinePrinter4.addItem("surepos");
        this.jcboMachinePrinter4.addItem("plain");
        this.jcboMachinePrinter4.addItem("javapos");
        this.jcboConnPrinter4.addItem("file");
        this.jcboConnPrinter4.addItem("serial");
        this.jcboSerialPrinter4.addItem("COM1");
        this.jcboSerialPrinter4.addItem("COM2");
        this.jcboSerialPrinter4.addItem("COM3");
        this.jcboSerialPrinter4.addItem("COM4");
        this.jcboSerialPrinter4.addItem("COM5");
        this.jcboSerialPrinter4.addItem("COM6");
        this.jcboSerialPrinter4.addItem("COM7");
        this.jcboSerialPrinter4.addItem("COM8");
        this.jcboSerialPrinter4.addItem("LPT1");
        this.jcboSerialPrinter4.addItem("/dev/ttyS0");
        this.jcboSerialPrinter4.addItem("/dev/ttyS1");
        this.jcboSerialPrinter4.addItem("/dev/ttyS2");
        this.jcboSerialPrinter4.addItem("/dev/ttyS3");
        this.jcboSerialPrinter4.addItem("/dev/ttyS4");
        this.jcboSerialPrinter4.addItem("/dev/ttyS5");
        this.jcboMachinePrinter5.addItem("Not defined");
        this.jcboMachinePrinter5.addItem("screen");
        this.jcboMachinePrinter5.addItem("printer");
        this.jcboMachinePrinter5.addItem("epson");
        this.jcboMachinePrinter5.addItem("tmu220");
        this.jcboMachinePrinter5.addItem("star");
        this.jcboMachinePrinter5.addItem("ODP1000");
        this.jcboMachinePrinter5.addItem("ithaca");
        this.jcboMachinePrinter5.addItem("surepos");
        this.jcboMachinePrinter5.addItem("plain");
        this.jcboMachinePrinter5.addItem("javapos");
        this.jcboConnPrinter5.addItem("file");
        this.jcboConnPrinter5.addItem("serial");
        this.jcboSerialPrinter5.addItem("COM1");
        this.jcboSerialPrinter5.addItem("COM2");
        this.jcboSerialPrinter5.addItem("COM3");
        this.jcboSerialPrinter5.addItem("COM4");
        this.jcboSerialPrinter5.addItem("COM5");
        this.jcboSerialPrinter5.addItem("COM6");
        this.jcboSerialPrinter5.addItem("COM7");
        this.jcboSerialPrinter5.addItem("COM8");
        this.jcboSerialPrinter5.addItem("LPT1");
        this.jcboSerialPrinter5.addItem("/dev/ttyS0");
        this.jcboSerialPrinter5.addItem("/dev/ttyS1");
        this.jcboSerialPrinter5.addItem("/dev/ttyS2");
        this.jcboSerialPrinter5.addItem("/dev/ttyS3");
        this.jcboSerialPrinter5.addItem("/dev/ttyS4");
        this.jcboSerialPrinter5.addItem("/dev/ttyS5");
        this.jcboMachinePrinter6.addItem("Not defined");
        this.jcboMachinePrinter6.addItem("screen");
        this.jcboMachinePrinter6.addItem("printer");
        this.jcboMachinePrinter6.addItem("epson");
        this.jcboMachinePrinter6.addItem("tmu220");
        this.jcboMachinePrinter6.addItem("star");
        this.jcboMachinePrinter6.addItem("ODP1000");
        this.jcboMachinePrinter6.addItem("ithaca");
        this.jcboMachinePrinter6.addItem("surepos");
        this.jcboMachinePrinter6.addItem("plain");
        this.jcboMachinePrinter6.addItem("javapos");
        this.jcboConnPrinter6.addItem("file");
        this.jcboConnPrinter6.addItem("serial");
        this.jcboSerialPrinter6.addItem("COM1");
        this.jcboSerialPrinter6.addItem("COM2");
        this.jcboSerialPrinter6.addItem("COM3");
        this.jcboSerialPrinter6.addItem("COM4");
        this.jcboSerialPrinter6.addItem("COM5");
        this.jcboSerialPrinter6.addItem("COM6");
        this.jcboSerialPrinter6.addItem("COM7");
        this.jcboSerialPrinter6.addItem("COM8");
        this.jcboSerialPrinter6.addItem("LPT1");
        this.jcboSerialPrinter6.addItem("/dev/ttyS0");
        this.jcboSerialPrinter6.addItem("/dev/ttyS1");
        this.jcboSerialPrinter6.addItem("/dev/ttyS2");
        this.jcboSerialPrinter6.addItem("/dev/ttyS3");
        this.jcboSerialPrinter6.addItem("/dev/ttyS4");
        this.jcboSerialPrinter6.addItem("/dev/ttyS5");
        this.jcboMachineDisplay.addItem("Not defined");
        this.jcboMachineDisplay.addItem("screen");
        this.jcboMachineDisplay.addItem("window");
        this.jcboMachineDisplay.addItem("javapos");
        this.jcboMachineDisplay.addItem("epson");
        this.jcboMachineDisplay.addItem("ld200");
        this.jcboMachineDisplay.addItem("surepos");
        this.jcboConnDisplay.addItem("serial");
        this.jcboConnDisplay.addItem("file");
        this.jcboSerialDisplay.addItem("COM1");
        this.jcboSerialDisplay.addItem("COM2");
        this.jcboSerialDisplay.addItem("COM3");
        this.jcboSerialDisplay.addItem("COM4");
        this.jcboSerialDisplay.addItem("COM5");
        this.jcboSerialDisplay.addItem("COM6");
        this.jcboSerialDisplay.addItem("COM7");
        this.jcboSerialDisplay.addItem("COM8");
        this.jcboSerialDisplay.addItem("LPT1");
        this.jcboSerialDisplay.addItem("/dev/ttyS0");
        this.jcboSerialDisplay.addItem("/dev/ttyS1");
        this.jcboSerialDisplay.addItem("/dev/ttyS2");
        this.jcboSerialDisplay.addItem("/dev/ttyS3");
        this.jcboSerialDisplay.addItem("/dev/ttyS4");
        this.jcboSerialDisplay.addItem("/dev/ttyS5");
        this.jcboMachineScale.addItem("Not defined");
        this.jcboMachineScale.addItem("screen");
        this.jcboMachineScale.addItem("casiopd1");
        this.jcboMachineScale.addItem("caspdii");
        this.jcboMachineScale.addItem("dialog1");
        this.jcboMachineScale.addItem("mtind221");
        this.jcboMachineScale.addItem("samsungesp");
        this.jcboSerialScale.addItem("COM1");
        this.jcboSerialScale.addItem("COM2");
        this.jcboSerialScale.addItem("COM3");
        this.jcboSerialScale.addItem("COM4");
        this.jcboSerialScale.addItem("COM5");
        this.jcboSerialScale.addItem("COM6");
        this.jcboSerialScale.addItem("COM7");
        this.jcboSerialScale.addItem("COM8");
        this.jcboSerialScale.addItem("/dev/ttyS0");
        this.jcboSerialScale.addItem("/dev/ttyS1");
        this.jcboSerialScale.addItem("/dev/ttyS2");
        this.jcboSerialScale.addItem("/dev/ttyS3");
        this.jcboSerialScale.addItem("/dev/ttyS4");
        this.jcboSerialScale.addItem("/dev/ttyS5");
        this.jcboMachineScanner.addItem("Not defined");
        this.jcboMachineScanner.addItem("scanpal2");
        this.jcboSerialScanner.addItem("COM1");
        this.jcboSerialScanner.addItem("COM2");
        this.jcboSerialScanner.addItem("COM3");
        this.jcboSerialScanner.addItem("COM4");
        this.jcboSerialScanner.addItem("COM5");
        this.jcboSerialScanner.addItem("COM6");
        this.jcboSerialScanner.addItem("COM7");
        this.jcboSerialScanner.addItem("COM8");
        this.jcboSerialScanner.addItem("/dev/ttyS0");
        this.jcboSerialScanner.addItem("/dev/ttyS1");
        this.jcboSerialScanner.addItem("/dev/ttyS2");
        this.jcboSerialScanner.addItem("/dev/ttyS3");
        this.jcboSerialScanner.addItem("/dev/ttyS4");
        this.jcboSerialScanner.addItem("/dev/ttyS5");
        this.cboPrinters.addItem("(Default)");
        this.cboPrinters.addItem("(Show dialog)");
        for (String name : printernames) {
            this.cboPrinters.addItem(name);
        }
    }

    @Override
    public boolean hasChanged() {
        return this.dirty.isDirty();
    }

    @Override
    public Component getConfigComponent() {
        return this;
    }

    @Override
    public void loadProperties(AppConfig config) {
        String sparam;
        StringParser p = new StringParser(config.getProperty("machine.printer"));
        switch (sparam = this.unifySerialInterface(p.nextToken(':'))) {
            case "serial": 
            case "file": {
                this.jcboMachinePrinter.setSelectedItem("epson");
                this.jcboConnPrinter.setSelectedItem(sparam);
                this.jcboSerialPrinter.setSelectedItem(p.nextToken(','));
                break;
            }
            case "javapos": {
                this.jcboMachinePrinter.setSelectedItem(sparam);
                this.m_jtxtJPOSPrinter.setText(p.nextToken(','));
                this.m_jtxtJPOSDrawer.setText(p.nextToken(','));
                break;
            }
            case "printer": {
                this.jcboMachinePrinter.setSelectedItem(sparam);
                this.printer1printerparams.setParameters(p);
                break;
            }
            default: {
                this.jcboMachinePrinter.setSelectedItem(sparam);
                this.jcboConnPrinter.setSelectedItem(this.unifySerialInterface(p.nextToken(',')));
                this.jcboSerialPrinter.setSelectedItem(p.nextToken(','));
            }
        }
        p = new StringParser(config.getProperty("machine.printer.2"));
        switch (sparam = this.unifySerialInterface(p.nextToken(':'))) {
            case "serial": 
            case "file": {
                this.jcboMachinePrinter2.setSelectedItem("epson");
                this.jcboConnPrinter2.setSelectedItem(sparam);
                this.jcboSerialPrinter2.setSelectedItem(p.nextToken(','));
                break;
            }
            case "javapos": {
                this.jcboMachinePrinter2.setSelectedItem(sparam);
                this.m_jtxtJPOSPrinter2.setText(p.nextToken(','));
                this.m_jtxtJPOSDrawer2.setText(p.nextToken(','));
                break;
            }
            case "printer": {
                this.jcboMachinePrinter2.setSelectedItem(sparam);
                this.printer2printerparams.setParameters(p);
                break;
            }
            default: {
                this.jcboMachinePrinter2.setSelectedItem(sparam);
                this.jcboConnPrinter2.setSelectedItem(this.unifySerialInterface(p.nextToken(',')));
                this.jcboSerialPrinter2.setSelectedItem(p.nextToken(','));
            }
        }
        p = new StringParser(config.getProperty("machine.printer.3"));
        switch (sparam = this.unifySerialInterface(p.nextToken(':'))) {
            case "serial": 
            case "file": {
                this.jcboMachinePrinter3.setSelectedItem("epson");
                this.jcboConnPrinter3.setSelectedItem(sparam);
                this.jcboSerialPrinter3.setSelectedItem(p.nextToken(','));
                break;
            }
            case "javapos": {
                this.jcboMachinePrinter3.setSelectedItem(sparam);
                this.m_jtxtJPOSPrinter3.setText(p.nextToken(','));
                this.m_jtxtJPOSDrawer3.setText(p.nextToken(','));
                break;
            }
            case "printer": {
                this.jcboMachinePrinter3.setSelectedItem(sparam);
                this.printer3printerparams.setParameters(p);
                break;
            }
            default: {
                this.jcboMachinePrinter3.setSelectedItem(sparam);
                this.jcboConnPrinter3.setSelectedItem(this.unifySerialInterface(p.nextToken(',')));
                this.jcboSerialPrinter3.setSelectedItem(p.nextToken(','));
            }
        }
        p = new StringParser(config.getProperty("machine.printer.4"));
        switch (sparam = this.unifySerialInterface(p.nextToken(':'))) {
            case "serial": 
            case "file": {
                this.jcboMachinePrinter4.setSelectedItem("epson");
                this.jcboConnPrinter4.setSelectedItem(sparam);
                this.jcboSerialPrinter4.setSelectedItem(p.nextToken(','));
                break;
            }
            case "javapos": {
                this.jcboMachinePrinter4.setSelectedItem(sparam);
                this.m_jtxtJPOSPrinter4.setText(p.nextToken(','));
                this.m_jtxtJPOSDrawer4.setText(p.nextToken(','));
                break;
            }
            case "printer": {
                this.jcboMachinePrinter4.setSelectedItem(sparam);
                this.printer4printerparams.setParameters(p);
                break;
            }
            default: {
                this.jcboMachinePrinter4.setSelectedItem(sparam);
                this.jcboConnPrinter4.setSelectedItem(this.unifySerialInterface(p.nextToken(',')));
                this.jcboSerialPrinter4.setSelectedItem(p.nextToken(','));
            }
        }
        p = new StringParser(config.getProperty("machine.printer.5"));
        switch (sparam = this.unifySerialInterface(p.nextToken(':'))) {
            case "serial": 
            case "file": {
                this.jcboMachinePrinter5.setSelectedItem("epson");
                this.jcboConnPrinter5.setSelectedItem(sparam);
                this.jcboSerialPrinter5.setSelectedItem(p.nextToken(','));
                break;
            }
            case "javapos": {
                this.jcboMachinePrinter5.setSelectedItem(sparam);
                this.m_jtxtJPOSPrinter5.setText(p.nextToken(','));
                this.m_jtxtJPOSDrawer5.setText(p.nextToken(','));
                break;
            }
            case "printer": {
                this.jcboMachinePrinter5.setSelectedItem(sparam);
                this.printer5printerparams.setParameters(p);
                break;
            }
            default: {
                this.jcboMachinePrinter5.setSelectedItem(sparam);
                this.jcboConnPrinter5.setSelectedItem(this.unifySerialInterface(p.nextToken(',')));
                this.jcboSerialPrinter5.setSelectedItem(p.nextToken(','));
            }
        }
        p = new StringParser(config.getProperty("machine.printer.6"));
        switch (sparam = this.unifySerialInterface(p.nextToken(':'))) {
            case "serial": 
            case "file": {
                this.jcboMachinePrinter6.setSelectedItem("epson");
                this.jcboConnPrinter6.setSelectedItem(sparam);
                this.jcboSerialPrinter6.setSelectedItem(p.nextToken(','));
                break;
            }
            case "javapos": {
                this.jcboMachinePrinter6.setSelectedItem(sparam);
                this.m_jtxtJPOSPrinter6.setText(p.nextToken(','));
                this.m_jtxtJPOSDrawer6.setText(p.nextToken(','));
                break;
            }
            case "printer": {
                this.jcboMachinePrinter6.setSelectedItem(sparam);
                this.printer6printerparams.setParameters(p);
                break;
            }
            default: {
                this.jcboMachinePrinter6.setSelectedItem(sparam);
                this.jcboConnPrinter6.setSelectedItem(this.unifySerialInterface(p.nextToken(',')));
                this.jcboSerialPrinter6.setSelectedItem(p.nextToken(','));
            }
        }
        p = new StringParser(config.getProperty("machine.display"));
        switch (sparam = this.unifySerialInterface(p.nextToken(':'))) {
            case "serial": 
            case "file": {
                this.jcboMachineDisplay.setSelectedItem("epson");
                this.jcboConnDisplay.setSelectedItem(sparam);
                this.jcboSerialDisplay.setSelectedItem(p.nextToken(','));
                break;
            }
            case "javapos": {
                this.jcboMachineDisplay.setSelectedItem(sparam);
                this.m_jtxtJPOSName.setText(p.nextToken(','));
                break;
            }
            default: {
                this.jcboMachineDisplay.setSelectedItem(sparam);
                this.jcboConnDisplay.setSelectedItem(this.unifySerialInterface(p.nextToken(',')));
                this.jcboSerialDisplay.setSelectedItem(p.nextToken(','));
            }
        }
        p = new StringParser(config.getProperty("machine.scale"));
        sparam = p.nextToken(':');
        this.jcboMachineScale.setSelectedItem(sparam);
        if ("casiopd1".equals(sparam) || "dialog1".equals(sparam) || "samsungesp".equals(sparam) || "caspdii".equals(sparam) || "mtind221".equals(sparam)) {
            this.jcboSerialScale.setSelectedItem(p.nextToken(','));
        }
        p = new StringParser(config.getProperty("machine.scanner"));
        sparam = p.nextToken(':');
        this.jcboMachineScanner.setSelectedItem(sparam);
        if ("scanpal2".equals(sparam)) {
            this.jcboSerialScanner.setSelectedItem(p.nextToken(','));
        }
        this.cboPrinters.setSelectedItem(config.getProperty("machine.printername"));
        this.webSwtch_iButton.setSelected(Boolean.parseBoolean(config.getProperty("machine.iButton")));
        this.webSlider.setValue(Integer.valueOf(config.getProperty("machine.iButtonResponse")));
        this.dirty.setDirty(false);
    }

    @Override
    public void saveProperties(AppConfig config) {
        String sMachineDisplay;
        String sMachinePrinter6;
        String sMachinePrinter5;
        String sMachinePrinter4;
        String sMachinePrinter3;
        String sMachinePrinter2;
        String sMachinePrinter;
        switch (sMachinePrinter = this.comboValue(this.jcboMachinePrinter.getSelectedItem())) {
            case "epson": 
            case "tmu220": 
            case "star": 
            case "ODP1000": 
            case "ithaca": 
            case "surepos": {
                config.setProperty("machine.printer", sMachinePrinter + ":" + this.comboValue(this.jcboConnPrinter.getSelectedItem()) + "," + this.comboValue(this.jcboSerialPrinter.getSelectedItem()));
                break;
            }
            case "javapos": {
                config.setProperty("machine.printer", sMachinePrinter + ":" + this.m_jtxtJPOSPrinter.getText() + "," + this.m_jtxtJPOSDrawer.getText());
                break;
            }
            case "printer": {
                config.setProperty("machine.printer", sMachinePrinter + ":" + this.printer1printerparams.getParameters());
                break;
            }
            default: {
                config.setProperty("machine.printer", sMachinePrinter);
            }
        }
        switch (sMachinePrinter2 = this.comboValue(this.jcboMachinePrinter2.getSelectedItem())) {
            case "epson": 
            case "tmu220": 
            case "star": 
            case "ODP1000": 
            case "ithaca": 
            case "surepos": {
                config.setProperty("machine.printer.2", sMachinePrinter2 + ":" + this.comboValue(this.jcboConnPrinter2.getSelectedItem()) + "," + this.comboValue(this.jcboSerialPrinter2.getSelectedItem()));
                break;
            }
            case "javapos": {
                config.setProperty("machine.printer.2", sMachinePrinter2 + ":" + this.m_jtxtJPOSPrinter2.getText() + "," + this.m_jtxtJPOSDrawer2.getText());
                break;
            }
            case "printer": {
                config.setProperty("machine.printer.2", sMachinePrinter2 + ":" + this.printer2printerparams.getParameters());
                break;
            }
            default: {
                config.setProperty("machine.printer.2", sMachinePrinter2);
            }
        }
        switch (sMachinePrinter3 = this.comboValue(this.jcboMachinePrinter3.getSelectedItem())) {
            case "epson": 
            case "tmu220": 
            case "star": 
            case "ODP1000": 
            case "ithaca": 
            case "surepos": {
                config.setProperty("machine.printer.3", sMachinePrinter3 + ":" + this.comboValue(this.jcboConnPrinter3.getSelectedItem()) + "," + this.comboValue(this.jcboSerialPrinter3.getSelectedItem()));
                break;
            }
            case "javapos": {
                config.setProperty("machine.printer.3", sMachinePrinter3 + ":" + this.m_jtxtJPOSPrinter3.getText() + "," + this.m_jtxtJPOSDrawer3.getText());
                break;
            }
            case "printer": {
                config.setProperty("machine.printer.3", sMachinePrinter3 + ":" + this.printer3printerparams.getParameters());
                break;
            }
            default: {
                config.setProperty("machine.printer.3", sMachinePrinter3);
            }
        }
        switch (sMachinePrinter4 = this.comboValue(this.jcboMachinePrinter4.getSelectedItem())) {
            case "epson": 
            case "tmu220": 
            case "star": 
            case "ODP1000": 
            case "ithaca": 
            case "surepos": {
                config.setProperty("machine.printer.4", sMachinePrinter4 + ":" + this.comboValue(this.jcboConnPrinter4.getSelectedItem()) + "," + this.comboValue(this.jcboSerialPrinter4.getSelectedItem()));
                break;
            }
            case "javapos": {
                config.setProperty("machine.printer.4", sMachinePrinter4 + ":" + this.m_jtxtJPOSPrinter4.getText() + "," + this.m_jtxtJPOSDrawer4.getText());
                break;
            }
            case "printer": {
                config.setProperty("machine.printer.4", sMachinePrinter4 + ":" + this.printer4printerparams.getParameters());
                break;
            }
            default: {
                config.setProperty("machine.printer.4", sMachinePrinter4);
            }
        }
        switch (sMachinePrinter5 = this.comboValue(this.jcboMachinePrinter5.getSelectedItem())) {
            case "epson": 
            case "tmu220": 
            case "star": 
            case "ODP1000": 
            case "ithaca": 
            case "surepos": {
                config.setProperty("machine.printer.5", sMachinePrinter5 + ":" + this.comboValue(this.jcboConnPrinter5.getSelectedItem()) + "," + this.comboValue(this.jcboSerialPrinter5.getSelectedItem()));
                break;
            }
            case "javapos": {
                config.setProperty("machine.printer.5", sMachinePrinter5 + ":" + this.m_jtxtJPOSPrinter5.getText() + "," + this.m_jtxtJPOSDrawer5.getText());
                break;
            }
            case "printer": {
                config.setProperty("machine.printer.5", sMachinePrinter5 + ":" + this.printer5printerparams.getParameters());
                break;
            }
            default: {
                config.setProperty("machine.printer.5", sMachinePrinter5);
            }
        }
        switch (sMachinePrinter6 = this.comboValue(this.jcboMachinePrinter6.getSelectedItem())) {
            case "epson": 
            case "tmu220": 
            case "star": 
            case "ODP1000": 
            case "ithaca": 
            case "surepos": {
                config.setProperty("machine.printer.6", sMachinePrinter6 + ":" + this.comboValue(this.jcboConnPrinter6.getSelectedItem()) + "," + this.comboValue(this.jcboSerialPrinter6.getSelectedItem()));
                break;
            }
            case "javapos": {
                config.setProperty("machine.printer.6", sMachinePrinter6 + ":" + this.m_jtxtJPOSPrinter6.getText() + "," + this.m_jtxtJPOSDrawer6.getText());
                break;
            }
            case "printer": {
                config.setProperty("machine.printer.6", sMachinePrinter6 + ":" + this.printer6printerparams.getParameters());
                break;
            }
            default: {
                config.setProperty("machine.printer.6", sMachinePrinter6);
            }
        }
        switch (sMachineDisplay = this.comboValue(this.jcboMachineDisplay.getSelectedItem())) {
            case "epson": 
            case "ld200": 
            case "surepos": {
                config.setProperty("machine.display", sMachineDisplay + ":" + this.comboValue(this.jcboConnDisplay.getSelectedItem()) + "," + this.comboValue(this.jcboSerialDisplay.getSelectedItem()));
                break;
            }
            case "javapos": {
                config.setProperty("machine.display", sMachineDisplay + ":" + this.m_jtxtJPOSName.getText());
                break;
            }
            default: {
                config.setProperty("machine.display", sMachineDisplay);
            }
        }
        String sMachineScale = this.comboValue(this.jcboMachineScale.getSelectedItem());
        if ("casiopd1".equals(sMachineScale) || "dialog1".equals(sMachineScale) || "samsungesp".equals(sMachineScale) || "caspdii".equals(sMachineScale) || "mtind221".equals(sMachineScale)) {
            config.setProperty("machine.scale", sMachineScale + ":" + this.comboValue(this.jcboSerialScale.getSelectedItem()));
        } else {
            config.setProperty("machine.scale", sMachineScale);
        }
        String sMachineScanner = this.comboValue(this.jcboMachineScanner.getSelectedItem());
        if ("scanpal2".equals(sMachineScanner)) {
            config.setProperty("machine.scanner", sMachineScanner + ":" + this.comboValue(this.jcboSerialScanner.getSelectedItem()));
        } else {
            config.setProperty("machine.scanner", sMachineScanner);
        }
        config.setProperty("machine.printername", this.comboValue(this.cboPrinters.getSelectedItem()));
        config.setProperty("machine.iButton", Boolean.toString(this.webSwtch_iButton.isSelected()));
        config.setProperty("machine.iButtonResponse", Integer.toString(this.webSlider.getValue()));
        this.dirty.setDirty(false);
    }

    private String unifySerialInterface(String sparam) {
        return "rxtx".equals(sparam) ? "serial" : sparam;
    }

    private String comboValue(Object value) {
        return value == null ? "" : value.toString();
    }

    private void buildPrinterList(JComboBox<String> comboBox) {
        comboBox.addItem("COM1");
        comboBox.addItem("COM2");
        comboBox.addItem("COM3");
        comboBox.addItem("COM4");
        comboBox.addItem("COM5");
        comboBox.addItem("COM6");
        comboBox.addItem("COM7");
        comboBox.addItem("COM8");
        comboBox.addItem("COM9");
        comboBox.addItem("COM10");
        comboBox.addItem("COM11");
        comboBox.addItem("COM12");
        comboBox.addItem("LPT1");
        comboBox.addItem("/dev/ttyS0");
        comboBox.addItem("/dev/ttyS1");
        comboBox.addItem("/dev/ttyS2");
        comboBox.addItem("/dev/ttyS3");
        comboBox.addItem("/dev/ttyS4");
        comboBox.addItem("/dev/ttyS5");
    }

    private void addRegisteredPrinters(JComboBox<String> comboBox) {
        for (PrintService printer : this.printServices) {
            comboBox.addItem(printer.getName());
        }
    }

    private void initComponents() {
        this.jPanel13 = new JPanel();
        this.jLabel5 = new JLabel();
        this.jLabel6 = new JLabel();
        this.jLabel7 = new JLabel();
        this.jLabel8 = new JLabel();
        this.jLabel9 = new JLabel();
        this.jLabel10 = new JLabel();
        this.jLabel11 = new JLabel();
        this.jLabel12 = new JLabel();
        this.jLabel13 = new JLabel();
        this.jLabel14 = new JLabel();
        this.jcboMachineDisplay = new JComboBox();
        this.jcboMachinePrinter = new JComboBox();
        this.jcboMachinePrinter2 = new JComboBox();
        this.jcboMachinePrinter3 = new JComboBox();
        this.jcboMachinePrinter4 = new JComboBox();
        this.jcboMachinePrinter5 = new JComboBox();
        this.jcboMachinePrinter6 = new JComboBox();
        this.jcboMachineScale = new JComboBox();
        this.jcboMachineScanner = new JComboBox();
        this.cboPrinters = new JComboBox();
        this.m_jDisplayParams = new JPanel();
        this.jPanel2 = new JPanel();
        this.jPanel1 = new JPanel();
        this.jlblConnDisplay = new JLabel();
        this.jcboConnDisplay = new JComboBox();
        this.jlblDisplayPort = new JLabel();
        this.jcboSerialDisplay = new JComboBox();
        this.jPanel3 = new JPanel();
        this.jLabel20 = new JLabel();
        this.m_jtxtJPOSName = new JTextField();
        this.m_jPrinterParams1 = new JPanel();
        this.jPanel5 = new JPanel();
        this.jPanel6 = new JPanel();
        this.jlblConnPrinter = new JLabel();
        this.jcboConnPrinter = new JComboBox();
        this.jlblPrinterPort = new JLabel();
        this.jcboSerialPrinter = new JComboBox();
        this.jPanel4 = new JPanel();
        this.jLabel21 = new JLabel();
        this.m_jtxtJPOSPrinter = new JTextField();
        this.m_jtxtJPOSDrawer = new JTextField();
        this.jLabel24 = new JLabel();
        this.m_jPrinterParams2 = new JPanel();
        this.jPanel7 = new JPanel();
        this.jPanel8 = new JPanel();
        this.jlblConnPrinter2 = new JLabel();
        this.jcboConnPrinter2 = new JComboBox();
        this.jlblPrinterPort2 = new JLabel();
        this.jcboSerialPrinter2 = new JComboBox();
        this.jPanel11 = new JPanel();
        this.m_jtxtJPOSPrinter2 = new JTextField();
        this.m_jtxtJPOSDrawer2 = new JTextField();
        this.jLabel27 = new JLabel();
        this.jLabel22 = new JLabel();
        this.m_jPrinterParams3 = new JPanel();
        this.jPanel9 = new JPanel();
        this.jPanel10 = new JPanel();
        this.jlblConnPrinter3 = new JLabel();
        this.jcboConnPrinter3 = new JComboBox();
        this.jlblPrinterPort3 = new JLabel();
        this.jcboSerialPrinter3 = new JComboBox();
        this.jPanel12 = new JPanel();
        this.m_jtxtJPOSPrinter3 = new JTextField();
        this.m_jtxtJPOSDrawer3 = new JTextField();
        this.jLabel28 = new JLabel();
        this.jLabel23 = new JLabel();
        this.m_jPrinterParams4 = new JPanel();
        this.jPanel14 = new JPanel();
        this.jPanel15 = new JPanel();
        this.jlblConnPrinter4 = new JLabel();
        this.jcboConnPrinter4 = new JComboBox();
        this.jlblPrinterPort6 = new JLabel();
        this.jcboSerialPrinter4 = new JComboBox();
        this.jPanel18 = new JPanel();
        this.m_jtxtJPOSPrinter4 = new JTextField();
        this.m_jtxtJPOSDrawer4 = new JTextField();
        this.jLabel30 = new JLabel();
        this.jLabel31 = new JLabel();
        this.m_jPrinterParams5 = new JPanel();
        this.jPanel20 = new JPanel();
        this.jPanel21 = new JPanel();
        this.jlblConnPrinter5 = new JLabel();
        this.jcboConnPrinter5 = new JComboBox();
        this.jlblPrinterPort7 = new JLabel();
        this.jcboSerialPrinter5 = new JComboBox();
        this.jPanel22 = new JPanel();
        this.m_jtxtJPOSPrinter5 = new JTextField();
        this.m_jtxtJPOSDrawer5 = new JTextField();
        this.jLabel33 = new JLabel();
        this.jLabel34 = new JLabel();
        this.m_jPrinterParams6 = new JPanel();
        this.jPanel23 = new JPanel();
        this.jPanel25 = new JPanel();
        this.jlblConnPrinter6 = new JLabel();
        this.jcboConnPrinter6 = new JComboBox();
        this.jlblPrinterPort8 = new JLabel();
        this.jcboSerialPrinter6 = new JComboBox();
        this.jPanel26 = new JPanel();
        this.m_jtxtJPOSPrinter6 = new JTextField();
        this.m_jtxtJPOSDrawer6 = new JTextField();
        this.jLabel36 = new JLabel();
        this.jLabel37 = new JLabel();
        this.m_jScaleParams = new JPanel();
        this.jPanel16 = new JPanel();
        this.jPanel17 = new JPanel();
        this.jlblPrinterPort4 = new JLabel();
        this.jcboSerialScale = new JComboBox();
        this.m_jScannerParams = new JPanel();
        this.jPanel24 = new JPanel();
        this.jPanel19 = new JPanel();
        this.jcboSerialScanner = new JComboBox();
        this.jlblPrinterPort5 = new JLabel();
        this.jPanel27 = new JPanel();
        this.webSwtch_iButton = new JCheckBox();
        this.webLbliButton = new JLabel();
        this.webSlider = new JSlider();
        this.webLabel1 = new JLabel();
        this.webLabel2 = new JLabel();
        this.webLabel3 = new JLabel();
        this.setBackground(new Color(255, 255, 255));
        this.setFont(new Font("Arial", 0, 12));
        this.setPreferredSize(new Dimension(800, 525));
        this.jPanel13.setBackground(new Color(255, 255, 255));
        this.jPanel13.setPreferredSize(new Dimension(800, 520));
        this.jLabel5.setFont(new Font("Arial", 0, 14));
        this.jLabel5.setText(AppLocal.getIntString("label.MachineDisplay"));
        this.jLabel5.setPreferredSize(new Dimension(150, 30));
        this.jLabel6.setFont(new Font("Arial", 0, 14));
        this.jLabel6.setText(AppLocal.getIntString("label.MachinePrinter"));
        this.jLabel6.setPreferredSize(new Dimension(150, 30));
        this.jLabel7.setFont(new Font("Arial", 0, 14));
        this.jLabel7.setText(AppLocal.getIntString("label.MachinePrinter2"));
        this.jLabel7.setPreferredSize(new Dimension(150, 30));
        this.jLabel8.setFont(new Font("Arial", 0, 14));
        this.jLabel8.setText(AppLocal.getIntString("label.MachinePrinter3"));
        this.jLabel8.setPreferredSize(new Dimension(150, 30));
        this.jLabel9.setFont(new Font("Arial", 0, 14));
        this.jLabel9.setText(AppLocal.getIntString("label.MachinePrinter4"));
        this.jLabel9.setPreferredSize(new Dimension(150, 30));
        this.jLabel10.setFont(new Font("Arial", 0, 14));
        this.jLabel10.setText(AppLocal.getIntString("label.MachinePrinter5"));
        this.jLabel10.setPreferredSize(new Dimension(150, 30));
        this.jLabel11.setFont(new Font("Arial", 0, 14));
        this.jLabel11.setText(AppLocal.getIntString("label.MachinePrinter6"));
        this.jLabel11.setPreferredSize(new Dimension(150, 30));
        this.jLabel12.setFont(new Font("Arial", 0, 14));
        this.jLabel12.setText(AppLocal.getIntString("label.scale"));
        this.jLabel12.setPreferredSize(new Dimension(150, 30));
        this.jLabel13.setFont(new Font("Arial", 0, 14));
        this.jLabel13.setText(AppLocal.getIntString("label.scanner"));
        this.jLabel13.setPreferredSize(new Dimension(150, 30));
        this.jLabel14.setFont(new Font("Arial", 0, 14));
        this.jLabel14.setText(AppLocal.getIntString("label.reportsprinter"));
        this.jLabel14.setPreferredSize(new Dimension(150, 30));
        this.jcboMachineDisplay.setFont(new Font("Arial", 0, 14));
        this.jcboMachineDisplay.setCursor(new Cursor(0));
        this.jcboMachineDisplay.setPreferredSize(new Dimension(200, 30));
        this.jcboMachineDisplay.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPanelConfigPeripheral.this.jcboMachineDisplayActionPerformed(evt);
            }
        });
        this.jcboMachinePrinter.setFont(new Font("Arial", 0, 14));
        this.jcboMachinePrinter.setCursor(new Cursor(0));
        this.jcboMachinePrinter.setPreferredSize(new Dimension(200, 30));
        this.jcboMachinePrinter.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPanelConfigPeripheral.this.jcboMachinePrinterActionPerformed(evt);
            }
        });
        this.jcboMachinePrinter2.setFont(new Font("Arial", 0, 14));
        this.jcboMachinePrinter2.setCursor(new Cursor(0));
        this.jcboMachinePrinter2.setPreferredSize(new Dimension(200, 30));
        this.jcboMachinePrinter2.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPanelConfigPeripheral.this.jcboMachinePrinter2ActionPerformed(evt);
            }
        });
        this.jcboMachinePrinter3.setFont(new Font("Arial", 0, 14));
        this.jcboMachinePrinter3.setCursor(new Cursor(0));
        this.jcboMachinePrinter3.setPreferredSize(new Dimension(200, 30));
        this.jcboMachinePrinter3.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPanelConfigPeripheral.this.jcboMachinePrinter3ActionPerformed(evt);
            }
        });
        this.jcboMachinePrinter4.setFont(new Font("Arial", 0, 14));
        this.jcboMachinePrinter4.setCursor(new Cursor(0));
        this.jcboMachinePrinter4.setPreferredSize(new Dimension(200, 30));
        this.jcboMachinePrinter4.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPanelConfigPeripheral.this.jcboMachinePrinter4ActionPerformed(evt);
            }
        });
        this.jcboMachinePrinter5.setFont(new Font("Arial", 0, 14));
        this.jcboMachinePrinter5.setCursor(new Cursor(0));
        this.jcboMachinePrinter5.setPreferredSize(new Dimension(200, 30));
        this.jcboMachinePrinter5.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPanelConfigPeripheral.this.jcboMachinePrinter5ActionPerformed(evt);
            }
        });
        this.jcboMachinePrinter6.setFont(new Font("Arial", 0, 14));
        this.jcboMachinePrinter6.setCursor(new Cursor(0));
        this.jcboMachinePrinter6.setPreferredSize(new Dimension(200, 30));
        this.jcboMachinePrinter6.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPanelConfigPeripheral.this.jcboMachinePrinter6ActionPerformed(evt);
            }
        });
        this.jcboMachineScale.setFont(new Font("Arial", 0, 14));
        this.jcboMachineScale.setCursor(new Cursor(0));
        this.jcboMachineScale.setPreferredSize(new Dimension(200, 30));
        this.jcboMachineScale.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPanelConfigPeripheral.this.jcboMachineScaleActionPerformed(evt);
            }
        });
        this.jcboMachineScanner.setFont(new Font("Arial", 0, 14));
        this.jcboMachineScanner.setCursor(new Cursor(0));
        this.jcboMachineScanner.setPreferredSize(new Dimension(200, 30));
        this.jcboMachineScanner.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPanelConfigPeripheral.this.jcboMachineScannerActionPerformed(evt);
            }
        });
        this.cboPrinters.setFont(new Font("Arial", 0, 14));
        this.cboPrinters.setCursor(new Cursor(0));
        this.cboPrinters.setPreferredSize(new Dimension(200, 30));
        this.m_jDisplayParams.setPreferredSize(new Dimension(400, 30));
        this.m_jDisplayParams.setLayout(new CardLayout());
        this.jPanel2.setBackground(new Color(255, 255, 255));
        this.jPanel2.setFont(new Font("Arial", 0, 14));
        this.jPanel2.setPreferredSize(new Dimension(400, 30));
        this.m_jDisplayParams.add((Component)this.jPanel2, "empty");
        this.jPanel1.setBackground(new Color(255, 255, 255));
        this.jPanel1.setPreferredSize(new Dimension(400, 30));
        this.jlblConnDisplay.setFont(new Font("Arial", 0, 12));
        this.jlblConnDisplay.setText(AppLocal.getIntString("label.machinedisplayconn"));
        this.jlblConnDisplay.setPreferredSize(new Dimension(50, 30));
        this.jcboConnDisplay.setFont(new Font("Arial", 0, 12));
        this.jcboConnDisplay.setMinimumSize(new Dimension(80, 28));
        this.jcboConnDisplay.setPreferredSize(new Dimension(80, 30));
        this.jlblDisplayPort.setFont(new Font("Arial", 0, 12));
        this.jlblDisplayPort.setText(AppLocal.getIntString("label.machinedisplayport"));
        this.jlblDisplayPort.setPreferredSize(new Dimension(50, 30));
        this.jcboSerialDisplay.setEditable(true);
        this.jcboSerialDisplay.setFont(new Font("Arial", 0, 12));
        this.jcboSerialDisplay.setPreferredSize(new Dimension(150, 28));
        GroupLayout jPanel1Layout = new GroupLayout(this.jPanel1);
        this.jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addComponent(this.jlblConnDisplay, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jcboConnDisplay, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jlblDisplayPort, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jcboSerialDisplay, -2, -1, -2).addContainerGap(62, Short.MAX_VALUE)));
        jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jcboConnDisplay, -2, -1, -2).addComponent(this.jlblDisplayPort, -2, -1, -2).addComponent(this.jcboSerialDisplay, -2, -1, -2).addComponent(this.jlblConnDisplay, -2, -1, -2)).addGap(0, 0, 0)));
        this.m_jDisplayParams.add((Component)this.jPanel1, "comm");
        this.jLabel20.setFont(new Font("Arial", 0, 12));
        this.jLabel20.setText(AppLocal.getIntString("Label.Name"));
        this.jLabel20.setPreferredSize(new Dimension(50, 25));
        this.m_jtxtJPOSName.setFont(new Font("Arial", 0, 12));
        this.m_jtxtJPOSName.setPreferredSize(new Dimension(120, 25));
        this.m_jtxtJPOSName.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPanelConfigPeripheral.this.m_jtxtJPOSNameActionPerformed(evt);
            }
        });
        GroupLayout jPanel3Layout = new GroupLayout(this.jPanel3);
        this.jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel3Layout.createSequentialGroup().addContainerGap().addComponent(this.jLabel20, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.m_jtxtJPOSName, -2, -1, -2).addContainerGap(224, Short.MAX_VALUE)));
        jPanel3Layout.setVerticalGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel3Layout.createSequentialGroup().addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.m_jtxtJPOSName, -2, -1, -2).addComponent(this.jLabel20, -2, -1, -2)).addGap(0, 0, 0)));
        this.m_jDisplayParams.add((Component)this.jPanel3, "javapos");
        this.m_jPrinterParams1.setFont(new Font("Arial", 0, 12));
        this.m_jPrinterParams1.setPreferredSize(new Dimension(400, 30));
        this.m_jPrinterParams1.setLayout(new CardLayout());
        this.jPanel5.setBackground(new Color(255, 255, 255));
        this.jPanel5.setFont(new Font("Arial", 0, 14));
        this.jPanel5.setPreferredSize(new Dimension(450, 30));
        this.m_jPrinterParams1.add((Component)this.jPanel5, "empty");
        this.jPanel6.setBackground(new Color(255, 255, 255));
        this.jPanel6.setPreferredSize(new Dimension(450, 30));
        this.jlblConnPrinter.setFont(new Font("Arial", 0, 12));
        this.jlblConnPrinter.setText(AppLocal.getIntString("label.machinedisplayconn"));
        this.jlblConnPrinter.setPreferredSize(new Dimension(50, 30));
        this.jcboConnPrinter.setFont(new Font("Arial", 0, 12));
        this.jcboConnPrinter.setMinimumSize(new Dimension(80, 28));
        this.jcboConnPrinter.setPreferredSize(new Dimension(80, 30));
        this.jcboConnPrinter.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPanelConfigPeripheral.this.jcboConnPrinterActionPerformed(evt);
            }
        });
        this.jlblPrinterPort.setFont(new Font("Arial", 0, 12));
        this.jlblPrinterPort.setText(AppLocal.getIntString("label.machineprinterport"));
        this.jlblPrinterPort.setPreferredSize(new Dimension(50, 30));
        this.jcboSerialPrinter.setEditable(true);
        this.jcboSerialPrinter.setFont(new Font("Arial", 0, 12));
        this.jcboSerialPrinter.setPreferredSize(new Dimension(150, 28));
        GroupLayout jPanel6Layout = new GroupLayout(this.jPanel6);
        this.jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(jPanel6Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel6Layout.createSequentialGroup().addContainerGap().addComponent(this.jlblConnPrinter, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jcboConnPrinter, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jlblPrinterPort, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jcboSerialPrinter, -2, -1, -2).addContainerGap()));
        jPanel6Layout.setVerticalGroup(jPanel6Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel6Layout.createSequentialGroup().addGroup(jPanel6Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jcboConnPrinter, -2, -1, -2).addComponent(this.jlblPrinterPort, -2, -1, -2).addComponent(this.jcboSerialPrinter, -2, -1, -2).addComponent(this.jlblConnPrinter, -2, -1, -2)).addGap(0, 0, 0)));
        this.m_jPrinterParams1.add((Component)this.jPanel6, "comm");
        this.jLabel21.setFont(new Font("Arial", 0, 12));
        this.jLabel21.setText(AppLocal.getIntString("label.javapos.drawer"));
        this.jLabel21.setPreferredSize(new Dimension(50, 25));
        this.m_jtxtJPOSPrinter.setFont(new Font("Arial", 0, 12));
        this.m_jtxtJPOSPrinter.setPreferredSize(new Dimension(120, 25));
        this.m_jtxtJPOSDrawer.setFont(new Font("Arial", 0, 12));
        this.m_jtxtJPOSDrawer.setPreferredSize(new Dimension(120, 25));
        this.jLabel24.setFont(new Font("Arial", 0, 12));
        this.jLabel24.setText(AppLocal.getIntString("label.javapos.printer"));
        this.jLabel24.setPreferredSize(new Dimension(50, 25));
        GroupLayout jPanel4Layout = new GroupLayout(this.jPanel4);
        this.jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel4Layout.createSequentialGroup().addContainerGap().addComponent(this.jLabel24, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.m_jtxtJPOSPrinter, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jLabel21, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.m_jtxtJPOSDrawer, -2, -1, -2).addContainerGap(-1, Short.MAX_VALUE)));
        jPanel4Layout.setVerticalGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel4Layout.createSequentialGroup().addGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.m_jtxtJPOSPrinter, -2, -1, -2).addComponent(this.jLabel21, -2, -1, -2).addComponent(this.m_jtxtJPOSDrawer, -2, -1, -2).addComponent(this.jLabel24, -2, -1, -2)).addGap(0, 0, 0)));
        this.m_jPrinterParams1.add((Component)this.jPanel4, "javapos");
        this.m_jPrinterParams2.setPreferredSize(new Dimension(400, 30));
        this.m_jPrinterParams2.setLayout(new CardLayout());
        this.jPanel7.setBackground(new Color(255, 255, 255));
        this.jPanel7.setFont(new Font("Arial", 0, 14));
        this.jPanel7.setPreferredSize(new Dimension(200, 30));
        this.m_jPrinterParams2.add((Component)this.jPanel7, "empty");
        this.jPanel8.setBackground(new Color(255, 255, 255));
        this.jPanel8.setPreferredSize(new Dimension(450, 30));
        this.jlblConnPrinter2.setFont(new Font("Arial", 0, 12));
        this.jlblConnPrinter2.setText(AppLocal.getIntString("label.machinedisplayconn"));
        this.jlblConnPrinter2.setPreferredSize(new Dimension(50, 30));
        this.jcboConnPrinter2.setFont(new Font("Arial", 0, 12));
        this.jcboConnPrinter2.setMinimumSize(new Dimension(80, 28));
        this.jcboConnPrinter2.setPreferredSize(new Dimension(80, 30));
        this.jcboConnPrinter2.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPanelConfigPeripheral.this.jcboConnPrinter2ActionPerformed(evt);
            }
        });
        this.jlblPrinterPort2.setFont(new Font("Arial", 0, 12));
        this.jlblPrinterPort2.setText(AppLocal.getIntString("label.machineprinterport"));
        this.jlblPrinterPort2.setPreferredSize(new Dimension(50, 30));
        this.jcboSerialPrinter2.setEditable(true);
        this.jcboSerialPrinter2.setFont(new Font("Arial", 0, 12));
        this.jcboSerialPrinter2.setPreferredSize(new Dimension(150, 28));
        GroupLayout jPanel8Layout = new GroupLayout(this.jPanel8);
        this.jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(jPanel8Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel8Layout.createSequentialGroup().addContainerGap().addComponent(this.jlblConnPrinter2, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jcboConnPrinter2, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jlblPrinterPort2, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jcboSerialPrinter2, -2, -1, -2).addContainerGap()));
        jPanel8Layout.setVerticalGroup(jPanel8Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel8Layout.createSequentialGroup().addGroup(jPanel8Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jcboConnPrinter2, -2, -1, -2).addComponent(this.jlblPrinterPort2, -2, -1, -2).addComponent(this.jcboSerialPrinter2, -2, -1, -2).addComponent(this.jlblConnPrinter2, -2, -1, -2)).addGap(0, 0, 0)));
        this.m_jPrinterParams2.add((Component)this.jPanel8, "comm");
        this.m_jtxtJPOSPrinter2.setFont(new Font("Arial", 0, 12));
        this.m_jtxtJPOSPrinter2.setPreferredSize(new Dimension(120, 25));
        this.m_jtxtJPOSDrawer2.setFont(new Font("Arial", 0, 12));
        this.m_jtxtJPOSDrawer2.setPreferredSize(new Dimension(120, 25));
        this.jLabel27.setFont(new Font("Arial", 0, 12));
        this.jLabel27.setText(AppLocal.getIntString("label.javapos.printer"));
        this.jLabel27.setPreferredSize(new Dimension(50, 25));
        this.jLabel22.setFont(new Font("Arial", 0, 12));
        this.jLabel22.setText(AppLocal.getIntString("label.javapos.drawer"));
        this.jLabel22.setPreferredSize(new Dimension(50, 25));
        GroupLayout jPanel11Layout = new GroupLayout(this.jPanel11);
        this.jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(jPanel11Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel11Layout.createSequentialGroup().addContainerGap().addComponent(this.jLabel27, -2, 50, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.m_jtxtJPOSPrinter2, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jLabel22, -2, 50, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.m_jtxtJPOSDrawer2, -2, -1, -2).addContainerGap(-1, Short.MAX_VALUE)));
        jPanel11Layout.setVerticalGroup(jPanel11Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel11Layout.createSequentialGroup().addGroup(jPanel11Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.m_jtxtJPOSPrinter2, -2, -1, -2).addComponent(this.jLabel22, -2, -1, -2).addComponent(this.m_jtxtJPOSDrawer2, -2, -1, -2).addComponent(this.jLabel27, -2, -1, -2)).addGap(0, 0, 0)));
        this.m_jPrinterParams2.add((Component)this.jPanel11, "javapos");
        this.m_jPrinterParams3.setPreferredSize(new Dimension(400, 30));
        this.m_jPrinterParams3.setLayout(new CardLayout());
        this.jPanel9.setBackground(new Color(255, 255, 255));
        this.jPanel9.setFont(new Font("Arial", 0, 14));
        this.jPanel9.setPreferredSize(new Dimension(200, 30));
        this.m_jPrinterParams3.add((Component)this.jPanel9, "empty");
        this.jPanel10.setBackground(new Color(255, 255, 255));
        this.jPanel10.setPreferredSize(new Dimension(450, 30));
        this.jlblConnPrinter3.setFont(new Font("Arial", 0, 12));
        this.jlblConnPrinter3.setText(AppLocal.getIntString("label.machinedisplayconn"));
        this.jlblConnPrinter3.setPreferredSize(new Dimension(50, 30));
        this.jcboConnPrinter3.setFont(new Font("Arial", 0, 12));
        this.jcboConnPrinter3.setMinimumSize(new Dimension(80, 28));
        this.jcboConnPrinter3.setPreferredSize(new Dimension(80, 30));
        this.jcboConnPrinter3.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPanelConfigPeripheral.this.jcboConnPrinter3ActionPerformed(evt);
            }
        });
        this.jlblPrinterPort3.setFont(new Font("Arial", 0, 12));
        this.jlblPrinterPort3.setText(AppLocal.getIntString("label.machineprinterport"));
        this.jlblPrinterPort3.setPreferredSize(new Dimension(50, 30));
        this.jcboSerialPrinter3.setEditable(true);
        this.jcboSerialPrinter3.setFont(new Font("Arial", 0, 12));
        this.jcboSerialPrinter3.setPreferredSize(new Dimension(150, 28));
        GroupLayout jPanel10Layout = new GroupLayout(this.jPanel10);
        this.jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(jPanel10Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel10Layout.createSequentialGroup().addContainerGap().addComponent(this.jlblConnPrinter3, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jcboConnPrinter3, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jlblPrinterPort3, -2, 50, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jcboSerialPrinter3, -2, -1, -2).addContainerGap()));
        jPanel10Layout.setVerticalGroup(jPanel10Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel10Layout.createSequentialGroup().addGroup(jPanel10Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jcboConnPrinter3, -2, -1, -2).addComponent(this.jlblPrinterPort3, -2, -1, -2).addComponent(this.jcboSerialPrinter3, -2, -1, -2).addComponent(this.jlblConnPrinter3, -2, -1, -2)).addGap(0, 0, 0)));
        this.m_jPrinterParams3.add((Component)this.jPanel10, "comm");
        this.m_jtxtJPOSPrinter3.setFont(new Font("Arial", 0, 12));
        this.m_jtxtJPOSPrinter3.setPreferredSize(new Dimension(120, 25));
        this.m_jtxtJPOSDrawer3.setFont(new Font("Arial", 0, 12));
        this.m_jtxtJPOSDrawer3.setPreferredSize(new Dimension(120, 25));
        this.jLabel28.setFont(new Font("Arial", 0, 12));
        this.jLabel28.setText(AppLocal.getIntString("label.javapos.printer"));
        this.jLabel28.setPreferredSize(new Dimension(50, 25));
        this.jLabel23.setFont(new Font("Arial", 0, 12));
        this.jLabel23.setText(AppLocal.getIntString("label.javapos.drawer"));
        this.jLabel23.setPreferredSize(new Dimension(50, 25));
        GroupLayout jPanel12Layout = new GroupLayout(this.jPanel12);
        this.jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(jPanel12Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel12Layout.createSequentialGroup().addContainerGap().addComponent(this.jLabel28, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.m_jtxtJPOSPrinter3, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jLabel23, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.m_jtxtJPOSDrawer3, -2, -1, -2).addContainerGap(-1, Short.MAX_VALUE)));
        jPanel12Layout.setVerticalGroup(jPanel12Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel12Layout.createSequentialGroup().addGroup(jPanel12Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.m_jtxtJPOSPrinter3, -2, -1, -2).addComponent(this.jLabel23, -2, -1, -2).addComponent(this.m_jtxtJPOSDrawer3, -2, -1, -2).addComponent(this.jLabel28, -2, -1, -2)).addGap(0, 0, 0)));
        this.m_jPrinterParams3.add((Component)this.jPanel12, "javapos");
        this.m_jPrinterParams4.setPreferredSize(new Dimension(400, 30));
        this.m_jPrinterParams4.setLayout(new CardLayout());
        this.jPanel14.setBackground(new Color(255, 255, 255));
        this.jPanel14.setFont(new Font("Arial", 0, 14));
        this.jPanel14.setPreferredSize(new Dimension(200, 30));
        this.m_jPrinterParams4.add((Component)this.jPanel14, "empty");
        this.jPanel15.setBackground(new Color(255, 255, 255));
        this.jPanel15.setPreferredSize(new Dimension(450, 30));
        this.jlblConnPrinter4.setFont(new Font("Arial", 0, 12));
        this.jlblConnPrinter4.setText(AppLocal.getIntString("label.machinedisplayconn"));
        this.jlblConnPrinter4.setPreferredSize(new Dimension(50, 30));
        this.jcboConnPrinter4.setFont(new Font("Arial", 0, 12));
        this.jcboConnPrinter4.setMinimumSize(new Dimension(80, 28));
        this.jcboConnPrinter4.setPreferredSize(new Dimension(80, 30));
        this.jcboConnPrinter4.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPanelConfigPeripheral.this.jcboConnPrinter4ActionPerformed(evt);
            }
        });
        this.jlblPrinterPort6.setFont(new Font("Arial", 0, 12));
        this.jlblPrinterPort6.setText(AppLocal.getIntString("label.machineprinterport"));
        this.jlblPrinterPort6.setPreferredSize(new Dimension(50, 30));
        this.jcboSerialPrinter4.setEditable(true);
        this.jcboSerialPrinter4.setFont(new Font("Arial", 0, 12));
        this.jcboSerialPrinter4.setPreferredSize(new Dimension(150, 28));
        GroupLayout jPanel15Layout = new GroupLayout(this.jPanel15);
        this.jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(jPanel15Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel15Layout.createSequentialGroup().addContainerGap().addComponent(this.jlblConnPrinter4, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jcboConnPrinter4, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jlblPrinterPort6, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jcboSerialPrinter4, -2, -1, -2).addContainerGap()));
        jPanel15Layout.setVerticalGroup(jPanel15Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel15Layout.createSequentialGroup().addGroup(jPanel15Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jcboConnPrinter4, -2, -1, -2).addComponent(this.jlblPrinterPort6, -2, -1, -2).addComponent(this.jcboSerialPrinter4, -2, -1, -2).addComponent(this.jlblConnPrinter4, -2, -1, -2)).addGap(0, 0, 0)));
        this.m_jPrinterParams4.add((Component)this.jPanel15, "comm");
        this.m_jtxtJPOSPrinter4.setFont(new Font("Arial", 0, 12));
        this.m_jtxtJPOSPrinter4.setPreferredSize(new Dimension(120, 25));
        this.m_jtxtJPOSDrawer4.setFont(new Font("Arial", 0, 12));
        this.m_jtxtJPOSDrawer4.setPreferredSize(new Dimension(120, 25));
        this.jLabel30.setFont(new Font("Arial", 0, 12));
        this.jLabel30.setText(AppLocal.getIntString("label.javapos.printer"));
        this.jLabel30.setPreferredSize(new Dimension(50, 25));
        this.jLabel31.setFont(new Font("Arial", 0, 12));
        this.jLabel31.setText(AppLocal.getIntString("label.javapos.drawer"));
        this.jLabel31.setPreferredSize(new Dimension(50, 25));
        GroupLayout jPanel18Layout = new GroupLayout(this.jPanel18);
        this.jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(jPanel18Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel18Layout.createSequentialGroup().addContainerGap().addComponent(this.jLabel30, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.m_jtxtJPOSPrinter4, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jLabel31, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.m_jtxtJPOSDrawer4, -2, -1, -2).addContainerGap(-1, Short.MAX_VALUE)));
        jPanel18Layout.setVerticalGroup(jPanel18Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel18Layout.createSequentialGroup().addGroup(jPanel18Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.m_jtxtJPOSPrinter4, -2, -1, -2).addComponent(this.jLabel31, -2, -1, -2).addComponent(this.m_jtxtJPOSDrawer4, -2, -1, -2).addComponent(this.jLabel30, -2, -1, -2)).addGap(0, 0, 0)));
        this.m_jPrinterParams4.add((Component)this.jPanel18, "javapos");
        this.m_jPrinterParams5.setPreferredSize(new Dimension(400, 30));
        this.m_jPrinterParams5.setLayout(new CardLayout());
        this.jPanel20.setBackground(new Color(255, 255, 255));
        this.jPanel20.setFont(new Font("Arial", 0, 14));
        this.jPanel20.setPreferredSize(new Dimension(200, 30));
        this.m_jPrinterParams5.add((Component)this.jPanel20, "empty");
        this.jPanel21.setBackground(new Color(255, 255, 255));
        this.jPanel21.setPreferredSize(new Dimension(450, 30));
        this.jlblConnPrinter5.setFont(new Font("Arial", 0, 12));
        this.jlblConnPrinter5.setText(AppLocal.getIntString("label.machinedisplayconn"));
        this.jlblConnPrinter5.setPreferredSize(new Dimension(50, 30));
        this.jcboConnPrinter5.setFont(new Font("Arial", 0, 12));
        this.jcboConnPrinter5.setMinimumSize(new Dimension(80, 28));
        this.jcboConnPrinter5.setPreferredSize(new Dimension(80, 30));
        this.jcboConnPrinter5.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPanelConfigPeripheral.this.jcboConnPrinter5ActionPerformed(evt);
            }
        });
        this.jlblPrinterPort7.setFont(new Font("Arial", 0, 12));
        this.jlblPrinterPort7.setText(AppLocal.getIntString("label.machineprinterport"));
        this.jlblPrinterPort7.setPreferredSize(new Dimension(50, 30));
        this.jcboSerialPrinter5.setEditable(true);
        this.jcboSerialPrinter5.setFont(new Font("Arial", 0, 12));
        this.jcboSerialPrinter5.setPreferredSize(new Dimension(150, 28));
        GroupLayout jPanel21Layout = new GroupLayout(this.jPanel21);
        this.jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(jPanel21Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel21Layout.createSequentialGroup().addContainerGap().addComponent(this.jlblConnPrinter5, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jcboConnPrinter5, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jlblPrinterPort7, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jcboSerialPrinter5, -2, -1, -2).addContainerGap()));
        jPanel21Layout.setVerticalGroup(jPanel21Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel21Layout.createSequentialGroup().addGroup(jPanel21Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jcboConnPrinter5, -2, -1, -2).addComponent(this.jlblPrinterPort7, -2, -1, -2).addComponent(this.jcboSerialPrinter5, -2, -1, -2).addComponent(this.jlblConnPrinter5, -2, -1, -2)).addGap(0, 0, 0)));
        this.m_jPrinterParams5.add((Component)this.jPanel21, "comm");
        this.m_jtxtJPOSPrinter5.setFont(new Font("Arial", 0, 12));
        this.m_jtxtJPOSPrinter5.setPreferredSize(new Dimension(120, 25));
        this.m_jtxtJPOSDrawer5.setFont(new Font("Arial", 0, 12));
        this.m_jtxtJPOSDrawer5.setPreferredSize(new Dimension(120, 25));
        this.jLabel33.setFont(new Font("Arial", 0, 12));
        this.jLabel33.setText(AppLocal.getIntString("label.javapos.printer"));
        this.jLabel33.setPreferredSize(new Dimension(50, 25));
        this.jLabel34.setFont(new Font("Arial", 0, 12));
        this.jLabel34.setText(AppLocal.getIntString("label.javapos.drawer"));
        this.jLabel34.setPreferredSize(new Dimension(50, 25));
        GroupLayout jPanel22Layout = new GroupLayout(this.jPanel22);
        this.jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(jPanel22Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel22Layout.createSequentialGroup().addContainerGap().addComponent(this.jLabel33, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.m_jtxtJPOSPrinter5, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jLabel34, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.m_jtxtJPOSDrawer5, -2, -1, -2).addContainerGap(-1, Short.MAX_VALUE)));
        jPanel22Layout.setVerticalGroup(jPanel22Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel22Layout.createSequentialGroup().addGroup(jPanel22Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.m_jtxtJPOSPrinter5, -2, -1, -2).addComponent(this.jLabel34, -2, -1, -2).addComponent(this.m_jtxtJPOSDrawer5, -2, -1, -2).addComponent(this.jLabel33, -2, -1, -2)).addGap(0, 0, 0)));
        this.m_jPrinterParams5.add((Component)this.jPanel22, "javapos");
        this.m_jPrinterParams6.setPreferredSize(new Dimension(400, 30));
        this.m_jPrinterParams6.setLayout(new CardLayout());
        this.jPanel23.setBackground(new Color(255, 255, 255));
        this.jPanel23.setFont(new Font("Arial", 0, 14));
        this.jPanel23.setPreferredSize(new Dimension(200, 30));
        this.m_jPrinterParams6.add((Component)this.jPanel23, "empty");
        this.jPanel25.setBackground(new Color(255, 255, 255));
        this.jPanel25.setPreferredSize(new Dimension(450, 30));
        this.jlblConnPrinter6.setFont(new Font("Arial", 0, 12));
        this.jlblConnPrinter6.setText(AppLocal.getIntString("label.machinedisplayconn"));
        this.jlblConnPrinter6.setPreferredSize(new Dimension(50, 30));
        this.jcboConnPrinter6.setFont(new Font("Arial", 0, 12));
        this.jcboConnPrinter6.setPreferredSize(new Dimension(80, 30));
        this.jcboConnPrinter6.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPanelConfigPeripheral.this.jcboConnPrinter6ActionPerformed(evt);
            }
        });
        this.jlblPrinterPort8.setFont(new Font("Arial", 0, 12));
        this.jlblPrinterPort8.setText(AppLocal.getIntString("label.machineprinterport"));
        this.jlblPrinterPort8.setPreferredSize(new Dimension(50, 25));
        this.jcboSerialPrinter6.setEditable(true);
        this.jcboSerialPrinter6.setFont(new Font("Arial", 0, 12));
        this.jcboSerialPrinter6.setPreferredSize(new Dimension(150, 28));
        GroupLayout jPanel25Layout = new GroupLayout(this.jPanel25);
        this.jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(jPanel25Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel25Layout.createSequentialGroup().addContainerGap().addComponent(this.jlblConnPrinter6, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jcboConnPrinter6, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jlblPrinterPort8, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jcboSerialPrinter6, -2, -1, -2).addContainerGap()));
        jPanel25Layout.setVerticalGroup(jPanel25Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel25Layout.createSequentialGroup().addGroup(jPanel25Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jcboConnPrinter6, -2, -1, -2).addComponent(this.jlblPrinterPort8, -2, -1, -2).addComponent(this.jcboSerialPrinter6, -2, -1, -2).addComponent(this.jlblConnPrinter6, -2, -1, -2)).addGap(0, 0, 0)));
        this.m_jPrinterParams6.add((Component)this.jPanel25, "comm");
        this.m_jtxtJPOSPrinter6.setFont(new Font("Arial", 0, 12));
        this.m_jtxtJPOSPrinter6.setPreferredSize(new Dimension(120, 25));
        this.m_jtxtJPOSDrawer6.setFont(new Font("Arial", 0, 12));
        this.m_jtxtJPOSDrawer6.setPreferredSize(new Dimension(120, 25));
        this.jLabel36.setFont(new Font("Arial", 0, 12));
        this.jLabel36.setText(AppLocal.getIntString("label.javapos.printer"));
        this.jLabel36.setPreferredSize(new Dimension(50, 25));
        this.jLabel37.setFont(new Font("Arial", 0, 12));
        this.jLabel37.setText(AppLocal.getIntString("label.javapos.drawer"));
        this.jLabel37.setPreferredSize(new Dimension(50, 25));
        GroupLayout jPanel26Layout = new GroupLayout(this.jPanel26);
        this.jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(jPanel26Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel26Layout.createSequentialGroup().addContainerGap().addComponent(this.jLabel36, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.m_jtxtJPOSPrinter6, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jLabel37, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.m_jtxtJPOSDrawer6, -2, -1, -2).addContainerGap(-1, Short.MAX_VALUE)));
        jPanel26Layout.setVerticalGroup(jPanel26Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel26Layout.createSequentialGroup().addGroup(jPanel26Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.m_jtxtJPOSPrinter6, -2, -1, -2).addComponent(this.jLabel37, -2, -1, -2).addComponent(this.m_jtxtJPOSDrawer6, -2, -1, -2).addComponent(this.jLabel36, -2, -1, -2)).addGap(0, 0, 0)));
        this.m_jPrinterParams6.add((Component)this.jPanel26, "javapos");
        this.m_jScaleParams.setPreferredSize(new Dimension(400, 30));
        this.m_jScaleParams.setLayout(new CardLayout());
        this.jPanel16.setBackground(new Color(255, 255, 255));
        this.jPanel16.setFont(new Font("Arial", 0, 14));
        this.jPanel16.setPreferredSize(new Dimension(400, 30));
        this.m_jScaleParams.add((Component)this.jPanel16, "empty");
        this.jPanel17.setBackground(new Color(255, 255, 255));
        this.jPanel17.setPreferredSize(new Dimension(400, 30));
        this.jlblPrinterPort4.setFont(new Font("Arial", 0, 12));
        this.jlblPrinterPort4.setText(AppLocal.getIntString("label.machineprinterport"));
        this.jlblPrinterPort4.setPreferredSize(new Dimension(50, 30));
        this.jcboSerialScale.setEditable(true);
        this.jcboSerialScale.setFont(new Font("Arial", 0, 12));
        this.jcboSerialScale.setMinimumSize(new Dimension(80, 28));
        this.jcboSerialScale.setPreferredSize(new Dimension(80, 28));
        GroupLayout jPanel17Layout = new GroupLayout(this.jPanel17);
        this.jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(jPanel17Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel17Layout.createSequentialGroup().addContainerGap().addComponent(this.jlblPrinterPort4, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jcboSerialScale, -2, -1, -2).addContainerGap()));
        jPanel17Layout.setVerticalGroup(jPanel17Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel17Layout.createSequentialGroup().addGroup(jPanel17Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jcboSerialScale, -2, -1, -2).addComponent(this.jlblPrinterPort4, -2, -1, -2)).addGap(0, 0, 0)));
        this.m_jScaleParams.add((Component)this.jPanel17, "comm");
        this.m_jScannerParams.setPreferredSize(new Dimension(400, 30));
        this.m_jScannerParams.setLayout(new CardLayout());
        this.jPanel24.setBackground(new Color(255, 255, 255));
        this.jPanel24.setFont(new Font("Arial", 0, 14));
        this.jPanel24.setPreferredSize(new Dimension(400, 30));
        this.m_jScannerParams.add((Component)this.jPanel24, "empty");
        this.jPanel19.setBackground(new Color(255, 255, 255));
        this.jPanel19.setPreferredSize(new Dimension(400, 30));
        this.jcboSerialScanner.setEditable(true);
        this.jcboSerialScanner.setFont(new Font("Arial", 0, 12));
        this.jcboSerialScanner.setMinimumSize(new Dimension(80, 28));
        this.jcboSerialScanner.setPreferredSize(new Dimension(80, 28));
        this.jlblPrinterPort5.setFont(new Font("Arial", 0, 12));
        this.jlblPrinterPort5.setText(AppLocal.getIntString("label.machineprinterport"));
        this.jlblPrinterPort5.setPreferredSize(new Dimension(50, 30));
        GroupLayout jPanel19Layout = new GroupLayout(this.jPanel19);
        this.jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(jPanel19Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel19Layout.createSequentialGroup().addContainerGap().addComponent(this.jlblPrinterPort5, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jcboSerialScanner, -2, -1, -2).addContainerGap()));
        jPanel19Layout.setVerticalGroup(jPanel19Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel19Layout.createSequentialGroup().addGroup(jPanel19Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jcboSerialScanner, -2, -1, -2).addComponent(this.jlblPrinterPort5, -2, -1, -2)).addGap(6, 6, 6)));
        this.m_jScannerParams.add((Component)this.jPanel19, "comm");
        this.jPanel27.setBackground(new Color(255, 255, 255));
        this.webSwtch_iButton.setFont(new Font("Arial", 0, 14));
        this.webSwtch_iButton.setPreferredSize(new Dimension(80, 30));
        this.webSwtch_iButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPanelConfigPeripheral.this.webSwtch_iButtonActionPerformed(evt);
            }
        });
        this.webLbliButton.setText(AppLocal.getIntString("label.ibutton"));
        this.webLbliButton.setFont(new Font("Arial", 0, 14));
        this.webLbliButton.setPreferredSize(new Dimension(150, 30));
        this.webSlider.setMajorTickSpacing(10);
        this.webSlider.setMinorTickSpacing(5);
        this.webSlider.setPaintLabels(true);
        this.webSlider.setPaintTicks(true);
        this.webSlider.setSnapToTicks(true);
        this.webSlider.setToolTipText("");
        this.webSlider.setValue(5);
        this.webSlider.setFont(new Font("Arial", 0, 14));
        this.webSlider.setPreferredSize(new Dimension(270, 47));
        this.webSlider.addChangeListener(new ChangeListener(){

            @Override
            public void stateChanged(ChangeEvent evt) {
                JPanelConfigPeripheral.this.webSliderStateChanged(evt);
            }
        });
        this.webLabel1.setHorizontalAlignment(4);
        ResourceBundle bundle = ResourceBundle.getBundle("pos_messages");
        this.webLabel1.setText(bundle.getString("label.ibuttonresponsespeed"));
        this.webLabel1.setFont(new Font("Arial", 0, 14));
        this.webLabel1.setPreferredSize(new Dimension(150, 30));
        this.webLabel2.setText(bundle.getString("label.fast"));
        this.webLabel2.setFont(new Font("Arial", 0, 14));
        this.webLabel3.setText(bundle.getString("label.slow"));
        this.webLabel3.setFont(new Font("Arial", 0, 14));
        GroupLayout jPanel27Layout = new GroupLayout(this.jPanel27);
        this.jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(jPanel27Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel27Layout.createSequentialGroup().addComponent(this.webLbliButton, -2, 156, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.webSwtch_iButton, -2, -1, -2).addGap(18, 18, 18).addComponent(this.webLabel1, -2, -1, -2).addGap(14, 14, 14).addGroup(jPanel27Layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(jPanel27Layout.createSequentialGroup().addComponent(this.webLabel2, -2, -1, -2).addGap(194, 194, 194).addComponent(this.webLabel3, -2, -1, -2)).addComponent(this.webSlider, -2, -1, -2)).addContainerGap()));
        jPanel27Layout.setVerticalGroup(jPanel27Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel27Layout.createSequentialGroup().addContainerGap().addGroup(jPanel27Layout.createParallelGroup(GroupLayout.Alignment.CENTER).addComponent(this.webLbliButton, -1, -1, -2).addComponent(this.webSwtch_iButton, -1, -1, -2).addComponent(this.webLabel1, -2, 30, -2).addComponent(this.webSlider, -1, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE).addGroup(jPanel27Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.webLabel2, -2, -1, -2).addComponent(this.webLabel3, -2, -1, -2)).addContainerGap()));
        GroupLayout jPanel13Layout = new GroupLayout(this.jPanel13);
        this.jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(jPanel13Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel13Layout.createSequentialGroup().addContainerGap().addGroup(jPanel13Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel13Layout.createSequentialGroup().addComponent(this.jLabel7, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jcboMachinePrinter2, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.m_jPrinterParams2, -1, 414, Short.MAX_VALUE)).addGroup(jPanel13Layout.createSequentialGroup().addComponent(this.jLabel8, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jcboMachinePrinter3, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.m_jPrinterParams3, -1, -1, Short.MAX_VALUE)).addGroup(jPanel13Layout.createSequentialGroup().addComponent(this.jLabel12, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jcboMachineScale, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.m_jScaleParams, -1, -1, Short.MAX_VALUE)).addGroup(jPanel13Layout.createSequentialGroup().addGroup(jPanel13Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel13Layout.createSequentialGroup().addComponent(this.jLabel13, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jcboMachineScanner, -2, -1, -2)).addGroup(jPanel13Layout.createSequentialGroup().addComponent(this.jLabel14, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.cboPrinters, -2, -1, -2))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.m_jScannerParams, -2, -1, -2)).addGroup(jPanel13Layout.createSequentialGroup().addComponent(this.jLabel9, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jcboMachinePrinter4, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.m_jPrinterParams4, -1, -1, Short.MAX_VALUE)).addGroup(jPanel13Layout.createSequentialGroup().addComponent(this.jLabel10, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jcboMachinePrinter5, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.m_jPrinterParams5, -1, -1, Short.MAX_VALUE)).addGroup(jPanel13Layout.createSequentialGroup().addComponent(this.jLabel11, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jcboMachinePrinter6, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.m_jPrinterParams6, -1, -1, Short.MAX_VALUE)).addGroup(jPanel13Layout.createSequentialGroup().addGroup(jPanel13Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.jLabel5, -2, -1, -2).addComponent(this.jLabel6, -1, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel13Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel13Layout.createSequentialGroup().addComponent(this.jcboMachineDisplay, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.m_jDisplayParams, -1, -1, Short.MAX_VALUE)).addGroup(jPanel13Layout.createSequentialGroup().addComponent(this.jcboMachinePrinter, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.m_jPrinterParams1, -1, -1, Short.MAX_VALUE)))).addGroup(jPanel13Layout.createSequentialGroup().addComponent(this.jPanel27, -2, -1, -2).addGap(0, 0, Short.MAX_VALUE))).addGap(16, 16, 16)));
        jPanel13Layout.setVerticalGroup(jPanel13Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel13Layout.createSequentialGroup().addContainerGap().addGroup(jPanel13Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel13Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel5, -2, -1, -2).addComponent(this.jcboMachineDisplay, -2, -1, -2)).addComponent(this.m_jDisplayParams, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel13Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel13Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel6, -2, -1, -2).addComponent(this.jcboMachinePrinter, -2, -1, -2)).addComponent(this.m_jPrinterParams1, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel13Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel13Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel7, -2, -1, -2).addComponent(this.jcboMachinePrinter2, -2, -1, -2)).addComponent(this.m_jPrinterParams2, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel13Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel13Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel8, -2, -1, -2).addComponent(this.jcboMachinePrinter3, -2, -1, -2)).addComponent(this.m_jPrinterParams3, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel13Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel13Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel9, -2, -1, -2).addComponent(this.jcboMachinePrinter4, -2, -1, -2)).addComponent(this.m_jPrinterParams4, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel13Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel13Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel10, -2, -1, -2).addComponent(this.jcboMachinePrinter5, -2, -1, -2)).addComponent(this.m_jPrinterParams5, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel13Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel13Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel11, -2, -1, -2).addComponent(this.jcboMachinePrinter6, -2, -1, -2)).addComponent(this.m_jPrinterParams6, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel13Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel13Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel12, -2, -1, -2).addComponent(this.jcboMachineScale, -2, -1, -2)).addComponent(this.m_jScaleParams, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel13Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel13Layout.createSequentialGroup().addGroup(jPanel13Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel13, -2, -1, -2).addComponent(this.jcboMachineScanner, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel13Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel14, -2, -1, -2).addComponent(this.cboPrinters, -2, -1, -2))).addComponent(this.m_jScannerParams, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jPanel27, -1, -1, Short.MAX_VALUE)));
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.jPanel13, -2, -1, -2).addGap(0, 0, Short.MAX_VALUE)));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.jPanel13, -2, -1, -2).addContainerGap(-1, Short.MAX_VALUE)));
    }

    private void jcboMachinePrinter3ActionPerformed(ActionEvent evt) {
        CardLayout cl = (CardLayout)this.m_jPrinterParams3.getLayout();
        if ("epson".equals(this.jcboMachinePrinter3.getSelectedItem()) || "ODP1000".equals(this.jcboMachinePrinter3.getSelectedItem()) || "tmu220".equals(this.jcboMachinePrinter3.getSelectedItem()) || "star".equals(this.jcboMachinePrinter3.getSelectedItem()) || "ithaca".equals(this.jcboMachinePrinter3.getSelectedItem()) || "surepos".equals(this.jcboMachinePrinter3.getSelectedItem())) {
            cl.show(this.m_jPrinterParams3, "comm");
        } else if ("javapos".equals(this.jcboMachinePrinter3.getSelectedItem())) {
            cl.show(this.m_jPrinterParams3, "javapos");
        } else if ("printer".equals(this.jcboMachinePrinter3.getSelectedItem())) {
            cl.show(this.m_jPrinterParams3, "printer");
        } else {
            cl.show(this.m_jPrinterParams3, "empty");
        }
    }

    private void jcboMachinePrinter2ActionPerformed(ActionEvent evt) {
        CardLayout cl = (CardLayout)this.m_jPrinterParams2.getLayout();
        if ("epson".equals(this.jcboMachinePrinter2.getSelectedItem()) || "ODP1000".equals(this.jcboMachinePrinter2.getSelectedItem()) || "tmu220".equals(this.jcboMachinePrinter2.getSelectedItem()) || "star".equals(this.jcboMachinePrinter2.getSelectedItem()) || "ithaca".equals(this.jcboMachinePrinter2.getSelectedItem()) || "surepos".equals(this.jcboMachinePrinter2.getSelectedItem())) {
            cl.show(this.m_jPrinterParams2, "comm");
        } else if ("javapos".equals(this.jcboMachinePrinter2.getSelectedItem())) {
            cl.show(this.m_jPrinterParams2, "javapos");
        } else if ("printer".equals(this.jcboMachinePrinter2.getSelectedItem())) {
            cl.show(this.m_jPrinterParams2, "printer");
        } else {
            cl.show(this.m_jPrinterParams2, "empty");
        }
    }

    private void jcboMachinePrinterActionPerformed(ActionEvent evt) {
        CardLayout cl = (CardLayout)this.m_jPrinterParams1.getLayout();
        if ("epson".equals(this.jcboMachinePrinter.getSelectedItem()) || "ODP1000".equals(this.jcboMachinePrinter.getSelectedItem()) || "tmu220".equals(this.jcboMachinePrinter.getSelectedItem()) || "star".equals(this.jcboMachinePrinter.getSelectedItem()) || "ithaca".equals(this.jcboMachinePrinter.getSelectedItem()) || "surepos".equals(this.jcboMachinePrinter.getSelectedItem())) {
            cl.show(this.m_jPrinterParams1, "comm");
        } else if ("javapos".equals(this.jcboMachinePrinter.getSelectedItem())) {
            cl.show(this.m_jPrinterParams1, "javapos");
        } else if ("printer".equals(this.jcboMachinePrinter.getSelectedItem())) {
            cl.show(this.m_jPrinterParams1, "printer");
        } else {
            cl.show(this.m_jPrinterParams1, "empty");
        }
    }

    private void jcboMachinePrinter4ActionPerformed(ActionEvent evt) {
        CardLayout cl = (CardLayout)this.m_jPrinterParams4.getLayout();
        if ("epson".equals(this.jcboMachinePrinter4.getSelectedItem()) || "ODP1000".equals(this.jcboMachinePrinter4.getSelectedItem()) || "tmu220".equals(this.jcboMachinePrinter4.getSelectedItem()) || "star".equals(this.jcboMachinePrinter4.getSelectedItem()) || "ithaca".equals(this.jcboMachinePrinter4.getSelectedItem()) || "surepos".equals(this.jcboMachinePrinter4.getSelectedItem())) {
            cl.show(this.m_jPrinterParams4, "comm");
        } else if ("javapos".equals(this.jcboMachinePrinter4.getSelectedItem())) {
            cl.show(this.m_jPrinterParams4, "javapos");
        } else if ("printer".equals(this.jcboMachinePrinter4.getSelectedItem())) {
            cl.show(this.m_jPrinterParams4, "printer");
        } else {
            cl.show(this.m_jPrinterParams4, "empty");
        }
    }

    private void jcboMachinePrinter5ActionPerformed(ActionEvent evt) {
        CardLayout cl = (CardLayout)this.m_jPrinterParams5.getLayout();
        if ("epson".equals(this.jcboMachinePrinter5.getSelectedItem()) || "ODP1000".equals(this.jcboMachinePrinter5.getSelectedItem()) || "tmu220".equals(this.jcboMachinePrinter5.getSelectedItem()) || "star".equals(this.jcboMachinePrinter5.getSelectedItem()) || "ithaca".equals(this.jcboMachinePrinter5.getSelectedItem()) || "surepos".equals(this.jcboMachinePrinter5.getSelectedItem())) {
            cl.show(this.m_jPrinterParams5, "comm");
        } else if ("javapos".equals(this.jcboMachinePrinter5.getSelectedItem())) {
            cl.show(this.m_jPrinterParams5, "javapos");
        } else if ("printer".equals(this.jcboMachinePrinter5.getSelectedItem())) {
            cl.show(this.m_jPrinterParams5, "printer");
        } else {
            cl.show(this.m_jPrinterParams5, "empty");
        }
    }

    private void jcboMachinePrinter6ActionPerformed(ActionEvent evt) {
        CardLayout cl = (CardLayout)this.m_jPrinterParams6.getLayout();
        if ("epson".equals(this.jcboMachinePrinter6.getSelectedItem()) || "ODP1000".equals(this.jcboMachinePrinter6.getSelectedItem()) || "tmu220".equals(this.jcboMachinePrinter6.getSelectedItem()) || "star".equals(this.jcboMachinePrinter6.getSelectedItem()) || "ithaca".equals(this.jcboMachinePrinter6.getSelectedItem()) || "surepos".equals(this.jcboMachinePrinter6.getSelectedItem())) {
            cl.show(this.m_jPrinterParams6, "comm");
        } else if ("javapos".equals(this.jcboMachinePrinter6.getSelectedItem())) {
            cl.show(this.m_jPrinterParams6, "javapos");
        } else if ("printer".equals(this.jcboMachinePrinter6.getSelectedItem())) {
            cl.show(this.m_jPrinterParams6, "printer");
        } else {
            cl.show(this.m_jPrinterParams6, "empty");
        }
    }

    private void m_jtxtJPOSNameActionPerformed(ActionEvent evt) {
    }

    private void jcboMachineScannerActionPerformed(ActionEvent evt) {
        CardLayout cl = (CardLayout)this.m_jScannerParams.getLayout();
        if ("scanpal2".equals(this.jcboMachineScanner.getSelectedItem())) {
            cl.show(this.m_jScannerParams, "comm");
        } else {
            cl.show(this.m_jScannerParams, "empty");
        }
    }

    private void jcboMachineScaleActionPerformed(ActionEvent evt) {
        CardLayout cl = (CardLayout)this.m_jScaleParams.getLayout();
        if ("casiopd1".equals(this.jcboMachineScale.getSelectedItem()) || "dialog1".equals(this.jcboMachineScale.getSelectedItem()) || "samsungesp".equals(this.jcboMachineScale.getSelectedItem()) || "caspdii".equals(this.jcboMachineScale.getSelectedItem()) || "mtind221".equals(this.jcboMachineScale.getSelectedItem())) {
            cl.show(this.m_jScaleParams, "comm");
        } else {
            cl.show(this.m_jScaleParams, "empty");
        }
    }

    private void jcboMachineDisplayActionPerformed(ActionEvent evt) {
        CardLayout cl = (CardLayout)this.m_jDisplayParams.getLayout();
        if ("epson".equals(this.jcboMachineDisplay.getSelectedItem()) || "ld200".equals(this.jcboMachineDisplay.getSelectedItem()) || "surepos".equals(this.jcboMachineDisplay.getSelectedItem())) {
            cl.show(this.m_jDisplayParams, "comm");
        } else if ("javapos".equals(this.jcboMachineDisplay.getSelectedItem())) {
            cl.show(this.m_jDisplayParams, "javapos");
        } else {
            cl.show(this.m_jDisplayParams, "empty");
        }
    }

    private void webSwtch_iButtonActionPerformed(ActionEvent evt) {
        if (this.webSwtch_iButton.isSelected()) {
            // empty if block
        }
    }

    private void webSliderStateChanged(ChangeEvent evt) {
        this.webSlider.getValue();
    }

    private void jcboConnPrinterActionPerformed(ActionEvent evt) {
        this.jcboSerialPrinter.removeAllItems();
        if ("raw".equals(this.jcboConnPrinter.getSelectedItem()) || "usb".equals(this.jcboConnPrinter.getSelectedItem())) {
            this.jlblPrinterPort.setText("Printer");
            this.addRegisteredPrinters(this.jcboSerialPrinter);
        } else {
            this.jlblPrinterPort.setText("Port");
            this.buildPrinterList(this.jcboSerialPrinter);
        }
        this.jcboSerialPrinter.setSelectedItem(null);
    }

    private void jcboConnPrinter2ActionPerformed(ActionEvent evt) {
        this.jcboSerialPrinter2.removeAllItems();
        if ("raw".equals(this.jcboConnPrinter2.getSelectedItem()) || "usb".equals(this.jcboConnPrinter2.getSelectedItem())) {
            this.jlblPrinterPort2.setText("Printer");
            this.addRegisteredPrinters(this.jcboSerialPrinter2);
        } else {
            this.jlblPrinterPort2.setText("Port");
            this.buildPrinterList(this.jcboSerialPrinter2);
        }
        this.jcboSerialPrinter2.setSelectedItem(null);
    }

    private void jcboConnPrinter3ActionPerformed(ActionEvent evt) {
        this.jcboSerialPrinter3.removeAllItems();
        if ("raw".equals(this.jcboConnPrinter3.getSelectedItem()) || "usb".equals(this.jcboConnPrinter3.getSelectedItem())) {
            this.jlblPrinterPort3.setText("Printer");
            this.addRegisteredPrinters(this.jcboSerialPrinter3);
        } else {
            this.jlblPrinterPort3.setText("Port");
            this.buildPrinterList(this.jcboSerialPrinter3);
        }
        this.jcboSerialPrinter2.setSelectedItem(null);
    }

    private void jcboConnPrinter4ActionPerformed(ActionEvent evt) {
        this.jcboSerialPrinter4.removeAllItems();
        if ("raw".equals(this.jcboConnPrinter4.getSelectedItem()) || "usb".equals(this.jcboConnPrinter4.getSelectedItem())) {
            this.jlblPrinterPort4.setText("Printer");
            this.addRegisteredPrinters(this.jcboSerialPrinter4);
        } else {
            this.jlblPrinterPort4.setText("Port");
            this.buildPrinterList(this.jcboSerialPrinter4);
        }
        this.jcboSerialPrinter4.setSelectedItem(null);
    }

    private void jcboConnPrinter5ActionPerformed(ActionEvent evt) {
        this.jcboSerialPrinter5.removeAllItems();
        if ("raw".equals(this.jcboConnPrinter5.getSelectedItem()) || "usb".equals(this.jcboConnPrinter5.getSelectedItem())) {
            this.jlblPrinterPort5.setText("Printer");
            this.addRegisteredPrinters(this.jcboSerialPrinter5);
        } else {
            this.jlblPrinterPort5.setText("Port");
            this.buildPrinterList(this.jcboSerialPrinter5);
        }
        this.jcboSerialPrinter5.setSelectedItem(null);
    }

    private void jcboConnPrinter6ActionPerformed(ActionEvent evt) {
        this.jcboSerialPrinter6.removeAllItems();
        if ("raw".equals(this.jcboConnPrinter.getSelectedItem()) || "usb".equals(this.jcboConnPrinter6.getSelectedItem())) {
            this.jlblPrinterPort6.setText("Printer");
            this.addRegisteredPrinters(this.jcboSerialPrinter6);
        } else {
            this.jlblPrinterPort6.setText("Port");
            this.buildPrinterList(this.jcboSerialPrinter6);
        }
        this.jcboSerialPrinter6.setSelectedItem(null);
    }
}

