/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.config;

import com.openbravo.data.user.DirtyManager;
import com.openbravo.pos.config.PanelConfig;
import com.openbravo.pos.forms.AppConfig;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

public class JPanelConfigFE
extends JPanel
implements PanelConfig {
    private final DirtyManager dirty = new DirtyManager();
    private JCheckBox jchkEnableFE;
    private JCheckBox jchkEnablePOSE;
    private JCheckBox jchkEnableDocs;
    private JTextField jtxtPrefixFE;
    private JTextField jtxtResolucionFE;
    private JTextField jtxtPrefixPOSE;
    private JTextField jtxtResolucionPOSE;
    private JTextField jtxtPrefixDocs;
    private JTextField jtxtResolucionDocs;
    private JTextField jtxtPrefixNOC;
    private JTextField jtxtResolucionNOC;
    private JTextField jtxtNitEmpresa;
    private JTextField jtxtCashierCode;
    private JTextField jtxtCashRegisterCode;
    private JTextField jtxtApiToken;
    private JSpinner jspnConsecutivoFE;
    private JSpinner jspnConsecutivoPOSE;
    private JSpinner jspnConsecutivoDocs;
    private JSpinner jspnConsecutivoNOC;

    public JPanelConfigFE() {
        this.initComponents();
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
        this.jchkEnableFE.setSelected(Boolean.parseBoolean(config.getProperty("fe.enabled")));
        this.jchkEnablePOSE.setSelected(Boolean.parseBoolean(config.getProperty("pose.enabled")));
        this.jchkEnableDocs.setSelected(Boolean.parseBoolean(config.getProperty("docs.enabled")));
        this.jtxtPrefixFE.setText(config.getProperty("till.receiptprefixfe"));
        this.jtxtResolucionFE.setText(config.getProperty("till.resolucionfe"));
        this.jtxtPrefixPOSE.setText(config.getProperty("till.receiptprefix"));
        this.jtxtResolucionPOSE.setText(config.getProperty("pose.resolution_name"));
        this.jtxtPrefixDocs.setText(config.getProperty("docs.prefix"));
        this.jtxtResolucionDocs.setText(config.getProperty("docs.resolucionfe"));
        this.jtxtPrefixNOC.setText(config.getProperty("noc.prefix"));
        this.jtxtResolucionNOC.setText(config.getProperty("noc.resolucion"));
        this.jtxtNitEmpresa.setText(config.getProperty("docs.nit"));
        this.jtxtCashierCode.setText(config.getProperty("pose.cashier_code"));
        this.jtxtCashRegisterCode.setText(config.getProperty("pose.cash_register_code"));
        this.jtxtApiToken.setText(config.getProperty("fe.api_token"));
        String conseFE = config.getProperty("fe.consecutivo");
        String consePOSE = config.getProperty("pose.consecutivo");
        String conseDocs = config.getProperty("docs.consecutivo");
        String conseNOC = config.getProperty("noc.consecutivo");
        this.jspnConsecutivoFE.setValue(conseFE != null ? Integer.parseInt(conseFE) : 1);
        this.jspnConsecutivoPOSE.setValue(consePOSE != null ? Integer.parseInt(consePOSE) : 1);
        this.jspnConsecutivoDocs.setValue(conseDocs != null ? Integer.parseInt(conseDocs) : 1);
        this.jspnConsecutivoNOC.setValue(conseNOC != null ? Integer.parseInt(conseNOC) : 1);
        this.dirty.setDirty(false);
    }

    @Override
    public void saveProperties(AppConfig config) {
        config.setProperty("fe.enabled", Boolean.toString(this.jchkEnableFE.isSelected()));
        config.setProperty("pose.enabled", Boolean.toString(this.jchkEnablePOSE.isSelected()));
        config.setProperty("docs.enabled", Boolean.toString(this.jchkEnableDocs.isSelected()));
        config.setProperty("till.receiptprefixfe", this.jtxtPrefixFE.getText());
        config.setProperty("till.resolucionfe", this.jtxtResolucionFE.getText());
        config.setProperty("pose.resolution_name", this.jtxtResolucionPOSE.getText());
        config.setProperty("pose.cashier_code", this.jtxtCashierCode.getText());
        config.setProperty("pose.cash_register_code", this.jtxtCashRegisterCode.getText());
        config.setProperty("pose.cashier_type", "CONTADOR");
        config.setProperty("docs.prefix", this.jtxtPrefixDocs.getText());
        config.setProperty("docs.resolucionfe", this.jtxtResolucionDocs.getText());
        config.setProperty("docs.nit", this.jtxtNitEmpresa.getText());
        config.setProperty("noc.prefix", this.jtxtPrefixNOC.getText());
        config.setProperty("noc.resolucion", this.jtxtResolucionNOC.getText());
        config.setProperty("noc.poseresolucion", this.jtxtResolucionNOC.getText());
        config.setProperty("noc.docsresolucion", this.jtxtResolucionNOC.getText());
        config.setProperty("fe.api_token", this.jtxtApiToken.getText());
        config.setProperty("fe.consecutivo", this.jspnConsecutivoFE.getValue().toString());
        config.setProperty("pose.consecutivo", this.jspnConsecutivoPOSE.getValue().toString());
        config.setProperty("docs.consecutivo", this.jspnConsecutivoDocs.getValue().toString());
        config.setProperty("noc.consecutivo", this.jspnConsecutivoNOC.getValue().toString());
        this.dirty.setDirty(false);
    }

    private void initComponents() {
        this.setBackground(Color.WHITE);
        this.setPreferredSize(new Dimension(700, 550));
        this.setLayout(null);
        Font labelFont = new Font("Arial", 0, 14);
        Font fieldFont = new Font("Arial", 0, 14);
        Font headerFont = new Font("Arial", 1, 16);
        int y = 10;
        int labelWidth = 180;
        int fieldWidth = 200;
        int leftMargin = 20;
        int rowHeight = 35;
        JLabel lblHeader1 = new JLabel("Habilitaci\u00f3n de Documentos Electr\u00f3nicos");
        lblHeader1.setFont(headerFont);
        lblHeader1.setBounds(leftMargin, y, 400, 25);
        this.add(lblHeader1);
        this.jchkEnableFE = new JCheckBox("Habilitar Factura Electr\u00f3nica (FE)");
        this.jchkEnableFE.setFont(labelFont);
        this.jchkEnableFE.setBackground(Color.WHITE);
        this.jchkEnableFE.setBounds(leftMargin, y += 30, 300, 25);
        this.jchkEnableFE.addActionListener(this.dirty);
        this.add(this.jchkEnableFE);
        this.jchkEnablePOSE = new JCheckBox("Habilitar POS Electr\u00f3nico");
        this.jchkEnablePOSE.setFont(labelFont);
        this.jchkEnablePOSE.setBackground(Color.WHITE);
        this.jchkEnablePOSE.setBounds(leftMargin + 320, y, 250, 25);
        this.jchkEnablePOSE.addActionListener(this.dirty);
        this.add(this.jchkEnablePOSE);
        this.jchkEnableDocs = new JCheckBox("Habilitar Documentos Soporte");
        this.jchkEnableDocs.setFont(labelFont);
        this.jchkEnableDocs.setBackground(Color.WHITE);
        this.jchkEnableDocs.setBounds(leftMargin, y += 30, 300, 25);
        this.jchkEnableDocs.addActionListener(this.dirty);
        this.add(this.jchkEnableDocs);
        JSeparator sep1 = new JSeparator();
        sep1.setBounds(leftMargin, y += 40, 650, 2);
        this.add(sep1);
        JLabel lblHeader2 = new JLabel("Factura Electr\u00f3nica (FE)");
        lblHeader2.setFont(headerFont);
        lblHeader2.setBounds(leftMargin, y += 15, 300, 25);
        this.add(lblHeader2);
        JLabel lblPrefixFE = new JLabel("Prefijo FE:");
        lblPrefixFE.setFont(labelFont);
        lblPrefixFE.setBounds(leftMargin, y += rowHeight, labelWidth, 25);
        this.add(lblPrefixFE);
        this.jtxtPrefixFE = new JTextField();
        this.jtxtPrefixFE.setFont(fieldFont);
        this.jtxtPrefixFE.setBounds(leftMargin + labelWidth + 10, y, 100, 28);
        this.jtxtPrefixFE.getDocument().addDocumentListener(this.dirty);
        this.add(this.jtxtPrefixFE);
        JLabel lblResolucionFE = new JLabel("Resoluci\u00f3n FE:");
        lblResolucionFE.setFont(labelFont);
        lblResolucionFE.setBounds(leftMargin + 330, y, 120, 25);
        this.add(lblResolucionFE);
        this.jtxtResolucionFE = new JTextField();
        this.jtxtResolucionFE.setFont(fieldFont);
        this.jtxtResolucionFE.setBounds(leftMargin + 460, y, 180, 28);
        this.jtxtResolucionFE.getDocument().addDocumentListener(this.dirty);
        this.add(this.jtxtResolucionFE);
        JLabel lblConseFE = new JLabel("Consecutivo FE:");
        lblConseFE.setFont(labelFont);
        lblConseFE.setBounds(leftMargin, y += rowHeight, labelWidth, 25);
        this.add(lblConseFE);
        this.jspnConsecutivoFE = new JSpinner(new SpinnerNumberModel(1, 1, 999999999, 1));
        this.jspnConsecutivoFE.setFont(fieldFont);
        this.jspnConsecutivoFE.setBounds(leftMargin + labelWidth + 10, y, 120, 28);
        this.jspnConsecutivoFE.addChangeListener(this.dirty);
        this.add(this.jspnConsecutivoFE);
        JLabel lblHeader3 = new JLabel("POS Electr\u00f3nico (POSE)");
        lblHeader3.setFont(headerFont);
        lblHeader3.setBounds(leftMargin, y += rowHeight + 10, 300, 25);
        this.add(lblHeader3);
        JLabel lblPrefixPOSE = new JLabel("Prefijo POSE:");
        lblPrefixPOSE.setFont(labelFont);
        lblPrefixPOSE.setBounds(leftMargin, y += rowHeight, labelWidth, 25);
        this.add(lblPrefixPOSE);
        this.jtxtPrefixPOSE = new JTextField();
        this.jtxtPrefixPOSE.setFont(fieldFont);
        this.jtxtPrefixPOSE.setBounds(leftMargin + labelWidth + 10, y, 100, 28);
        this.jtxtPrefixPOSE.getDocument().addDocumentListener(this.dirty);
        this.add(this.jtxtPrefixPOSE);
        JLabel lblResolucionPOSE = new JLabel("Resoluci\u00f3n POSE:");
        lblResolucionPOSE.setFont(labelFont);
        lblResolucionPOSE.setBounds(leftMargin + 330, y, 130, 25);
        this.add(lblResolucionPOSE);
        this.jtxtResolucionPOSE = new JTextField();
        this.jtxtResolucionPOSE.setFont(fieldFont);
        this.jtxtResolucionPOSE.setBounds(leftMargin + 460, y, 180, 28);
        this.jtxtResolucionPOSE.getDocument().addDocumentListener(this.dirty);
        this.add(this.jtxtResolucionPOSE);
        JLabel lblConsePOSE = new JLabel("Consecutivo POSE:");
        lblConsePOSE.setFont(labelFont);
        lblConsePOSE.setBounds(leftMargin, y += rowHeight, labelWidth, 25);
        this.add(lblConsePOSE);
        this.jspnConsecutivoPOSE = new JSpinner(new SpinnerNumberModel(1, 1, 999999999, 1));
        this.jspnConsecutivoPOSE.setFont(fieldFont);
        this.jspnConsecutivoPOSE.setBounds(leftMargin + labelWidth + 10, y, 120, 28);
        this.jspnConsecutivoPOSE.addChangeListener(this.dirty);
        this.add(this.jspnConsecutivoPOSE);
        JLabel lblHeader4 = new JLabel("Documentos Soporte (DS)");
        lblHeader4.setFont(headerFont);
        lblHeader4.setBounds(leftMargin, y += rowHeight + 10, 300, 25);
        this.add(lblHeader4);
        JLabel lblPrefixDocs = new JLabel("Prefijo Docs:");
        lblPrefixDocs.setFont(labelFont);
        lblPrefixDocs.setBounds(leftMargin, y += rowHeight, labelWidth, 25);
        this.add(lblPrefixDocs);
        this.jtxtPrefixDocs = new JTextField();
        this.jtxtPrefixDocs.setFont(fieldFont);
        this.jtxtPrefixDocs.setBounds(leftMargin + labelWidth + 10, y, 100, 28);
        this.jtxtPrefixDocs.getDocument().addDocumentListener(this.dirty);
        this.add(this.jtxtPrefixDocs);
        JLabel lblResolucionDocs = new JLabel("Resoluci\u00f3n Docs:");
        lblResolucionDocs.setFont(labelFont);
        lblResolucionDocs.setBounds(leftMargin + 330, y, 130, 25);
        this.add(lblResolucionDocs);
        this.jtxtResolucionDocs = new JTextField();
        this.jtxtResolucionDocs.setFont(fieldFont);
        this.jtxtResolucionDocs.setBounds(leftMargin + 460, y, 180, 28);
        this.jtxtResolucionDocs.getDocument().addDocumentListener(this.dirty);
        this.add(this.jtxtResolucionDocs);
        JLabel lblConseDocs = new JLabel("Consecutivo Docs:");
        lblConseDocs.setFont(labelFont);
        lblConseDocs.setBounds(leftMargin, y += rowHeight, labelWidth, 25);
        this.add(lblConseDocs);
        this.jspnConsecutivoDocs = new JSpinner(new SpinnerNumberModel(1, 1, 999999999, 1));
        this.jspnConsecutivoDocs.setFont(fieldFont);
        this.jspnConsecutivoDocs.setBounds(leftMargin + labelWidth + 10, y, 120, 28);
        this.jspnConsecutivoDocs.addChangeListener(this.dirty);
        this.add(this.jspnConsecutivoDocs);
        JLabel lblHeader5 = new JLabel("Notas Cr\u00e9dito (NOC)");
        lblHeader5.setFont(headerFont);
        lblHeader5.setBounds(leftMargin, y += rowHeight + 10, 300, 25);
        this.add(lblHeader5);
        JLabel lblPrefixNOC = new JLabel("Prefijo NOC:");
        lblPrefixNOC.setFont(labelFont);
        lblPrefixNOC.setBounds(leftMargin, y += rowHeight, labelWidth, 25);
        this.add(lblPrefixNOC);
        this.jtxtPrefixNOC = new JTextField();
        this.jtxtPrefixNOC.setFont(fieldFont);
        this.jtxtPrefixNOC.setBounds(leftMargin + labelWidth + 10, y, 100, 28);
        this.jtxtPrefixNOC.getDocument().addDocumentListener(this.dirty);
        this.add(this.jtxtPrefixNOC);
        JLabel lblResolucionNOC = new JLabel("Resoluci\u00f3n NOC:");
        lblResolucionNOC.setFont(labelFont);
        lblResolucionNOC.setBounds(leftMargin + 330, y, 130, 25);
        this.add(lblResolucionNOC);
        this.jtxtResolucionNOC = new JTextField();
        this.jtxtResolucionNOC.setFont(fieldFont);
        this.jtxtResolucionNOC.setBounds(leftMargin + 460, y, 180, 28);
        this.jtxtResolucionNOC.getDocument().addDocumentListener(this.dirty);
        this.add(this.jtxtResolucionNOC);
        JLabel lblConseNOC = new JLabel("Consecutivo NOC:");
        lblConseNOC.setFont(labelFont);
        lblConseNOC.setBounds(leftMargin, y += rowHeight, labelWidth, 25);
        this.add(lblConseNOC);
        this.jspnConsecutivoNOC = new JSpinner(new SpinnerNumberModel(1, 1, 999999999, 1));
        this.jspnConsecutivoNOC.setFont(fieldFont);
        this.jspnConsecutivoNOC.setBounds(leftMargin + labelWidth + 10, y, 120, 28);
        this.jspnConsecutivoNOC.addChangeListener(this.dirty);
        this.add(this.jspnConsecutivoNOC);
        JSeparator sep2 = new JSeparator();
        sep2.setBounds(leftMargin, y += rowHeight + 10, 650, 2);
        this.add(sep2);
        JLabel lblHeader6 = new JLabel("Datos de Empresa y Conexi\u00f3n API");
        lblHeader6.setFont(headerFont);
        lblHeader6.setBounds(leftMargin, y += 15, 350, 25);
        this.add(lblHeader6);
        JLabel lblNit = new JLabel("NIT Empresa:");
        lblNit.setFont(labelFont);
        lblNit.setBounds(leftMargin, y += rowHeight, labelWidth, 25);
        this.add(lblNit);
        this.jtxtNitEmpresa = new JTextField();
        this.jtxtNitEmpresa.setFont(fieldFont);
        this.jtxtNitEmpresa.setBounds(leftMargin + labelWidth + 10, y, 150, 28);
        this.jtxtNitEmpresa.getDocument().addDocumentListener(this.dirty);
        this.add(this.jtxtNitEmpresa);
        JLabel lblCashier = new JLabel("C\u00f3digo Cajero:");
        lblCashier.setFont(labelFont);
        lblCashier.setBounds(leftMargin + 380, y, 120, 25);
        this.add(lblCashier);
        this.jtxtCashierCode = new JTextField();
        this.jtxtCashierCode.setFont(fieldFont);
        this.jtxtCashierCode.setBounds(leftMargin + 510, y, 130, 28);
        this.jtxtCashierCode.getDocument().addDocumentListener(this.dirty);
        this.add(this.jtxtCashierCode);
        JLabel lblCashRegister = new JLabel("C\u00f3digo Caja:");
        lblCashRegister.setFont(labelFont);
        lblCashRegister.setBounds(leftMargin, y += rowHeight, labelWidth, 25);
        this.add(lblCashRegister);
        this.jtxtCashRegisterCode = new JTextField();
        this.jtxtCashRegisterCode.setFont(fieldFont);
        this.jtxtCashRegisterCode.setBounds(leftMargin + labelWidth + 10, y, 150, 28);
        this.jtxtCashRegisterCode.getDocument().addDocumentListener(this.dirty);
        this.add(this.jtxtCashRegisterCode);
        JLabel lblApiToken = new JLabel("Token API Emision.co:");
        lblApiToken.setFont(labelFont);
        lblApiToken.setBounds(leftMargin, y += rowHeight, labelWidth, 25);
        this.add(lblApiToken);
        this.jtxtApiToken = new JTextField();
        this.jtxtApiToken.setFont(fieldFont);
        this.jtxtApiToken.setBounds(leftMargin + labelWidth + 10, y, 400, 28);
        this.jtxtApiToken.getDocument().addDocumentListener(this.dirty);
        this.add(this.jtxtApiToken);
    }
}

