/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.inventory;

import com.openbravo.basic.BasicException;
import com.openbravo.data.gui.ComboBoxValModel;
import com.openbravo.data.gui.JImageEditor;
import com.openbravo.data.loader.SentenceFind;
import com.openbravo.data.loader.SentenceList;
import com.openbravo.data.user.DirtyManager;
import com.openbravo.data.user.EditorRecord;
import com.openbravo.format.Formats;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.forms.DataLogicSales;
import com.openbravo.pos.inventory.AttributeSetInfo;
import com.openbravo.pos.inventory.ProductStock;
import com.openbravo.pos.inventory.TaxCategoryInfo;
import com.openbravo.pos.inventory.UomInfo;
import com.openbravo.pos.sales.TaxesLogic;
import com.openbravo.pos.suppliers.DataLogicSuppliers;
import com.openbravo.pos.suppliers.JDialogNewSupplier;
import com.openbravo.pos.suppliers.SupplierInfo;
import com.openbravo.pos.ticket.CategoryInfo;
import com.openbravo.pos.ticket.TaxInfo;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

public final class ProductsEditor
extends JPanel
implements EditorRecord {
    private static final long serialVersionUID = 1L;
    private Object m_oId;
    private final DataLogicSales dlSales;
    private final DataLogicSuppliers m_dlSuppliers;
    private final SentenceList<CategoryInfo> m_sentcat;
    private ComboBoxValModel<CategoryInfo> m_CategoryModel;
    private final SentenceList<TaxCategoryInfo> taxcatsent;
    private ComboBoxValModel<TaxCategoryInfo> taxcatmodel;
    private final SentenceList<AttributeSetInfo> attsent;
    private ComboBoxValModel<AttributeSetInfo> attmodel;
    private final SentenceList<SupplierInfo> m_sentsuppliers;
    private ComboBoxValModel<SupplierInfo> m_SuppliersModel;
    private final SentenceList<TaxInfo> taxsent;
    private TaxesLogic taxeslogic;
    private Object pricesell;
    private boolean priceselllock = false;
    private boolean reportlock = false;
    private int btn;
    private List<ProductStock> productStockList;
    private StockTableModel stockModel;
    private SentenceList<UomInfo> m_sentuom;
    private ComboBoxValModel<UomInfo> m_UomModel;
    private AppView appView;
    private final SentenceFind loadimage;
    private JButton jBtnShowTrans;
    private JButton jButtonHTML;
    private JLabel jLabel1;
    private JLabel jLabel10;
    private JLabel jLabel11;
    private JLabel jLabel12;
    private JLabel jLabel13;
    private JLabel jLabel14;
    private JLabel jLabel15;
    private JLabel jLabel16;
    private JLabel jLabel17;
    private JLabel jLabel18;
    private JLabel jLabel19;
    private JLabel jLabel2;
    private JLabel jLabel20;
    private JLabel jLabel21;
    private JLabel jLabel22;
    private JLabel jLabel23;
    private JLabel jLabel26;
    private JLabel jLabel28;
    private JLabel jLabel3;
    private JLabel jLabel33;
    private JLabel jLabel34;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JLabel jLabel8;
    private JLabel jLabel9;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JPanel jPanel3;
    private JPanel jPanel4;
    private JPanel jPanel6;
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane2;
    private JScrollPane jScrollPane3;
    private JTabbedPane jTabbedPane1;
    private JComboBox<AttributeSetInfo> m_jAtt;
    private JTextField m_jCatalogOrder;
    private JComboBox<CategoryInfo> m_jCategory;
    private JCheckBox m_jCheckWarrantyReceipt;
    private JTextField m_jCode;
    private JComboBox<String> m_jCodetype;
    private JCheckBox m_jComment;
    private JCheckBox m_jConstant;
    private JTextArea m_jDisplay;
    private JTextField m_jGrossProfit;
    private JImageEditor m_jImage;
    private JCheckBox m_jInCatalog;
    private JTextField m_jName;
    private JTextField m_jPriceBuy;
    private JTextField m_jPriceSell;
    private JTextField m_jPriceSellTax;
    private JCheckBox m_jPrintKB;
    private JComboBox<String> m_jPrintTo;
    private JTextField m_jRef;
    private JCheckBox m_jScale;
    private JCheckBox m_jSendStatus;
    private JCheckBox m_jService;
    private JTextField m_jStockUnits;
    private JComboBox<SupplierInfo> m_jSupplier;
    private JComboBox<TaxCategoryInfo> m_jTax;
    private JTextField m_jTextTip;
    private JLabel m_jTitle;
    private JComboBox<UomInfo> m_jUom;
    private JCheckBox m_jVerpatrib;
    private JCheckBox m_jVprice;
    private JTextField m_jmargin;
    private JTextField m_jstockcost;
    private JTextField m_jstockvolume;
    private JTextArea txtAttributes;
    private JButton webBtnBold;
    private JButton webBtnBreak;
    private JButton webBtnColour;
    private JButton webBtnItalic;
    private JButton webBtnLarge;
    private JButton webBtnReset;
    private JButton webBtnSmall;
    private JButton webBtnSupplier;
    private JButton webBtnURL;
    private JLabel webLabel1;
    private JTable jTableProductStock;
    private JTextField colourChooser;

    public ProductsEditor(AppView app, DirtyManager dirty) {
        this.setAppView(app);
        this.dlSales = (DataLogicSales)app.getBean("com.openbravo.pos.forms.DataLogicSales");
        this.m_dlSuppliers = (DataLogicSuppliers)app.getBean("com.openbravo.pos.suppliers.DataLogicSuppliers");
        this.initComponents();
        this.loadimage = this.dlSales.getProductImage();
        this.taxsent = this.dlSales.getTaxList();
        this.m_sentcat = this.dlSales.getCategoriesList();
        this.m_CategoryModel = new ComboBoxValModel();
        this.taxcatsent = this.dlSales.getTaxCategoriesList();
        this.taxcatmodel = new ComboBoxValModel();
        this.attsent = this.dlSales.getAttributeSetList();
        this.attmodel = new ComboBoxValModel();
        this.m_sentsuppliers = this.m_dlSuppliers.getSupplierList();
        this.m_SuppliersModel = new ComboBoxValModel();
        this.m_sentuom = this.dlSales.getUomList();
        this.m_UomModel = new ComboBoxValModel();
        this.m_jRef.getDocument().addDocumentListener(dirty);
        this.m_jCode.getDocument().addDocumentListener(dirty);
        this.m_jCodetype.addActionListener(dirty);
        this.m_jName.getDocument().addDocumentListener(dirty);
        this.m_jCategory.addActionListener(dirty);
        this.m_jAtt.addActionListener(dirty);
        this.m_jVerpatrib.addActionListener(dirty);
        this.m_jTax.addActionListener(dirty);
        this.m_jUom.addActionListener(dirty);
        this.m_jPriceBuy.getDocument().addDocumentListener(dirty);
        this.m_jPriceSell.getDocument().addDocumentListener(dirty);
        this.m_jPrintTo.addActionListener(dirty);
        this.m_jInCatalog.addActionListener(dirty);
        this.m_jConstant.addActionListener(dirty);
        this.m_jCatalogOrder.getDocument().addDocumentListener(dirty);
        this.m_jSupplier.addActionListener(dirty);
        this.m_jService.addActionListener(dirty);
        this.m_jCheckWarrantyReceipt.addActionListener(dirty);
        this.m_jComment.addActionListener(dirty);
        this.m_jScale.addActionListener(dirty);
        this.m_jVprice.addActionListener(dirty);
        this.m_jstockcost.getDocument().addDocumentListener(dirty);
        this.m_jstockvolume.getDocument().addDocumentListener(dirty);
        this.m_jPrintKB.addActionListener(dirty);
        this.m_jSendStatus.addActionListener(dirty);
        this.m_jImage.addPropertyChangeListener("image", dirty);
        this.m_jDisplay.getDocument().addDocumentListener(dirty);
        this.m_jTextTip.getDocument().addDocumentListener(dirty);
        this.colourChooser.addActionListener(dirty);
        this.m_jDisplay.addCaretListener(null);
        this.txtAttributes.getDocument().addDocumentListener(dirty);
        FieldsManager fm = new FieldsManager();
        this.m_jPriceBuy.getDocument().addDocumentListener(fm);
        this.m_jPriceSell.getDocument().addDocumentListener(new PriceSellManager());
        this.m_jTax.addActionListener(fm);
        this.m_jPriceSellTax.getDocument().addDocumentListener(new PriceTaxManager());
        this.m_jmargin.getDocument().addDocumentListener(new MarginManager());
        this.m_jGrossProfit.getDocument().addDocumentListener(new MarginManager());
        this.init();
    }

    private void init() {
        this.writeValueEOF();
    }

    public void activate() throws BasicException {
        this.taxeslogic = new TaxesLogic(this.taxsent.list());
        this.m_CategoryModel = new ComboBoxValModel<CategoryInfo>(this.m_sentcat.list());
        this.m_jCategory.setModel(this.m_CategoryModel);
        this.taxcatmodel = new ComboBoxValModel<TaxCategoryInfo>(this.taxcatsent.list());
        this.m_jTax.setModel(this.taxcatmodel);
        this.attmodel = new ComboBoxValModel<AttributeSetInfo>(this.attsent.list());
        this.attmodel.add(0, null);
        this.m_jAtt.setModel(this.attmodel);
        this.m_SuppliersModel = new ComboBoxValModel<SupplierInfo>(this.m_sentsuppliers.list());
        this.m_jSupplier.setModel(this.m_SuppliersModel);
        this.m_UomModel = new ComboBoxValModel<UomInfo>(this.m_sentuom.list());
        this.m_jUom.setModel(this.m_UomModel);
        Object pId = null;
    }

    @Override
    public void refresh() {
    }

    @Override
    public void writeValueEOF() {
        this.reportlock = true;
        this.m_jTitle.setText(AppLocal.getIntString("label.recordeof"));
        this.m_oId = null;
        this.m_jRef.setText(null);
        this.m_jCode.setText(null);
        this.m_jCodetype.setSelectedIndex(0);
        this.m_jName.setText(null);
        this.m_CategoryModel.setSelectedKey(null);
        this.taxcatmodel.setSelectedKey(null);
        this.attmodel.setSelectedKey(null);
        this.m_jVerpatrib.setSelected(false);
        this.m_UomModel.setSelectedKey(0);
        this.m_jPriceBuy.setText("0");
        this.setPriceSell(null);
        this.m_SuppliersModel.setSelectedKey(0);
        this.m_jInCatalog.setSelected(false);
        this.m_jConstant.setSelected(false);
        this.m_jCatalogOrder.setText(null);
        this.m_jPrintTo.setSelectedIndex(1);
        this.m_jService.setSelected(false);
        this.m_jCheckWarrantyReceipt.setSelected(false);
        this.m_jComment.setSelected(false);
        this.m_jScale.setSelected(false);
        this.m_jVprice.setSelected(false);
        this.m_jstockcost.setText("0");
        this.m_jstockvolume.setText("0");
        this.m_jPrintKB.setVisible(false);
        this.m_jSendStatus.setVisible(false);
        this.m_jStockUnits.setVisible(false);
        this.m_jImage.setImage(null);
        this.m_jDisplay.setText(null);
        this.m_jTextTip.setText(null);
        this.colourChooser.setText("0,0,0");
        this.txtAttributes.setText(null);
        this.reportlock = false;
        this.m_jRef.setEnabled(false);
        this.m_jCode.setEnabled(false);
        this.m_jCodetype.setEnabled(false);
        this.m_jName.setEnabled(false);
        this.m_jCategory.setEnabled(false);
        this.m_jAtt.setEnabled(false);
        this.m_jVerpatrib.setEnabled(false);
        this.m_jTax.setEnabled(false);
        this.m_jUom.setEnabled(false);
        this.m_jPriceBuy.setEnabled(false);
        this.m_jPriceSell.setEnabled(false);
        this.m_jPriceSellTax.setEnabled(false);
        this.m_jmargin.setEnabled(false);
        this.m_jSupplier.setEnabled(false);
        this.m_jInCatalog.setEnabled(false);
        this.m_jConstant.setEnabled(false);
        this.m_jCatalogOrder.setEnabled(false);
        this.m_jPrintTo.setEnabled(false);
        this.m_jService.setEnabled(false);
        this.m_jCheckWarrantyReceipt.setEnabled(false);
        this.m_jComment.setEnabled(false);
        this.m_jScale.setEnabled(false);
        this.m_jVprice.setEnabled(false);
        this.m_jstockcost.setEnabled(false);
        this.m_jstockvolume.setEnabled(false);
        this.jTableProductStock.setEnabled(false);
        this.m_jImage.setEnabled(false);
        this.m_jDisplay.setEnabled(false);
        this.m_jTextTip.setEnabled(false);
        this.colourChooser.setEnabled(false);
        this.txtAttributes.setEnabled(false);
        this.calculateMargin();
        this.calculatePriceSellTax();
        this.calculateGP();
    }

    @Override
    public void writeValueInsert() {
        boolean selectedIndex = false;
        this.reportlock = true;
        this.m_jTitle.setText(AppLocal.getIntString("label.recordnew"));
        this.m_oId = UUID.randomUUID().toString();
        this.m_jRef.setText(null);
        this.m_jCode.setText(null);
        this.m_jCodetype.setSelectedIndex(0);
        this.m_jName.setText(null);
        this.m_CategoryModel.setSelectedKey("000");
        this.attmodel.setSelectedKey(null);
        this.m_jVerpatrib.setSelected(false);
        this.taxcatmodel.setSelectedKey("001");
        this.m_UomModel.setSelectedKey("0");
        this.m_jPriceBuy.setText("0");
        this.setPriceSell(null);
        this.m_jSupplier.setSelectedIndex(0);
        this.m_jInCatalog.setSelected(true);
        this.m_jConstant.setSelected(false);
        this.m_jCatalogOrder.setText(null);
        this.m_jPrintTo.setSelectedIndex(1);
        this.m_jService.setSelected(false);
        this.m_jCheckWarrantyReceipt.setSelected(false);
        this.m_jComment.setSelected(false);
        this.m_jScale.setSelected(false);
        this.m_jVprice.setSelected(false);
        this.m_jstockcost.setText("0");
        this.m_jstockvolume.setText("0");
        this.m_jImage.setImage(null);
        this.m_jDisplay.setText(null);
        this.m_jTextTip.setText(null);
        this.colourChooser.setEnabled(false);
        this.txtAttributes.setText(null);
        this.reportlock = false;
        this.m_jRef.setEnabled(true);
        this.m_jCode.setEnabled(true);
        this.m_jCodetype.setEnabled(true);
        this.m_jName.setEnabled(true);
        this.m_jCategory.setEnabled(true);
        this.m_jTax.setEnabled(true);
        this.m_jAtt.setEnabled(true);
        this.m_jVerpatrib.setEnabled(true);
        this.m_jUom.setEnabled(true);
        this.m_jPriceBuy.setEnabled(true);
        this.m_jPriceSell.setEnabled(true);
        this.m_jPriceSellTax.setEnabled(true);
        this.m_jmargin.setEnabled(true);
        this.m_jSupplier.setEnabled(true);
        this.m_jInCatalog.setEnabled(true);
        this.m_jConstant.setEnabled(true);
        this.m_jCatalogOrder.setEnabled(false);
        this.m_jPrintTo.setEnabled(true);
        this.m_jService.setEnabled(true);
        this.m_jCheckWarrantyReceipt.setEnabled(true);
        this.m_jComment.setEnabled(true);
        this.m_jScale.setEnabled(true);
        this.m_jVprice.setEnabled(true);
        this.m_jstockcost.setEnabled(true);
        this.m_jstockvolume.setEnabled(true);
        this.jTableProductStock.setEnabled(false);
        this.m_jImage.setEnabled(true);
        this.m_jDisplay.setEnabled(true);
        this.m_jTextTip.setEnabled(true);
        this.colourChooser.setEnabled(true);
        this.txtAttributes.setEnabled(true);
        this.calculateMargin();
        this.calculatePriceSellTax();
        this.calculateGP();
    }

    @Override
    public Object createValue() throws BasicException {
        Object[] myprod = new Object[]{this.m_oId == null ? UUID.randomUUID().toString() : this.m_oId, this.m_jRef.getText(), this.m_jCode.getText(), this.m_jCodetype.getSelectedItem(), this.m_jName.getText(), Formats.CURRENCY.parseValue(this.m_jPriceBuy.getText()), this.pricesell, this.m_CategoryModel.getSelectedKey(), this.taxcatmodel.getSelectedKey(), this.attmodel.getSelectedKey(), Formats.CURRENCY.parseValue(this.m_jstockcost.getText()), Formats.DOUBLE.parseValue(this.m_jstockvolume.getText()), this.m_jImage.getImage(), this.m_jComment.isSelected(), this.m_jScale.isSelected(), this.m_jConstant.isSelected(), this.m_jPrintKB.isSelected(), this.m_jSendStatus.isSelected(), this.m_jService.isSelected(), Formats.BYTEA.parseValue(this.txtAttributes.getText()), this.m_jDisplay.getText(), this.m_jVprice.isSelected(), this.m_jVerpatrib.isSelected(), this.m_jTextTip.getText(), this.m_jCheckWarrantyReceipt.isSelected(), Formats.DOUBLE.parseValue(this.m_jStockUnits.getText()), this.m_jPrintTo.getSelectedItem().toString(), this.m_SuppliersModel.getSelectedKey(), this.m_UomModel.getSelectedKey(), this.m_jInCatalog.isSelected(), Formats.INT.parseValue(this.m_jCatalogOrder.getText())};
        return myprod;
    }

    @Override
    public void writeValueEdit(Object value) {
        this.reportlock = true;
        Object[] myprod = (Object[])value;
        this.m_jTitle.setText(Formats.STRING.formatValue(myprod[1]) + " - " + Formats.STRING.formatValue(myprod[4]));
        this.m_oId = myprod[0];
        this.m_jRef.setText(Formats.STRING.formatValue(myprod[1]));
        this.m_jCode.setText(Formats.STRING.formatValue(myprod[2]));
        this.m_jCodetype.setSelectedItem(myprod[3]);
        this.m_jName.setText(Formats.STRING.formatValue(myprod[4]));
        this.m_jPriceBuy.setText(Formats.CURRENCY.formatValue(myprod[5]));
        this.setPriceSell(myprod[6]);
        this.m_CategoryModel.setSelectedKey(myprod[7]);
        this.taxcatmodel.setSelectedKey(myprod[8]);
        this.attmodel.setSelectedKey(myprod[9]);
        this.m_jstockcost.setText(Formats.CURRENCY.formatValue(myprod[10]));
        this.m_jstockvolume.setText(Formats.DOUBLE.formatValue(myprod[11]));
        this.m_jImage.setImage(this.findImage(this.m_oId));
        this.m_jComment.setSelected((Boolean)myprod[13]);
        this.m_jScale.setSelected((Boolean)myprod[14]);
        this.m_jConstant.setSelected((Boolean)myprod[15]);
        this.m_jPrintKB.setSelected((Boolean)myprod[16]);
        this.m_jSendStatus.setSelected((Boolean)myprod[17]);
        this.m_jService.setSelected((Boolean)myprod[18]);
        this.txtAttributes.setText(Formats.BYTEA.formatValue(myprod[19]));
        this.m_jDisplay.setText(Formats.STRING.formatValue(myprod[20]));
        this.m_jVprice.setSelected((Boolean)myprod[21]);
        this.m_jVerpatrib.setSelected((Boolean)myprod[22]);
        this.m_jTextTip.setText(Formats.STRING.formatValue(myprod[23]));
        this.m_jCheckWarrantyReceipt.setSelected((Boolean)myprod[24]);
        this.m_jStockUnits.setText(Formats.DOUBLE.formatValue(myprod[25]));
        this.m_jPrintTo.setSelectedItem(myprod[26]);
        this.m_SuppliersModel.setSelectedKey(myprod[27]);
        this.m_UomModel.setSelectedKey(myprod[28]);
        this.m_jInCatalog.setSelected((Boolean)myprod[29]);
        this.m_jCatalogOrder.setText(Formats.INT.formatValue(myprod[30]));
        this.txtAttributes.setCaretPosition(0);
        this.reportlock = false;
        this.m_jRef.setEnabled(true);
        this.m_jCode.setEnabled(true);
        this.m_jCodetype.setEnabled(true);
        this.m_jName.setEnabled(true);
        this.m_jCategory.setEnabled(true);
        this.m_jTax.setEnabled(true);
        this.m_jAtt.setEnabled(true);
        this.m_jVerpatrib.setEnabled(true);
        this.m_jUom.setEnabled(true);
        this.m_jPriceBuy.setEnabled(true);
        this.m_jPriceSell.setEnabled(true);
        this.m_jPriceSellTax.setEnabled(true);
        this.m_jmargin.setEnabled(true);
        this.m_jSupplier.setEnabled(true);
        this.m_jInCatalog.setEnabled(true);
        this.m_jConstant.setEnabled(true);
        this.m_jCatalogOrder.setEnabled(this.m_jInCatalog.isSelected());
        this.m_jPrintTo.setEnabled(true);
        this.m_jService.setEnabled(true);
        this.m_jCheckWarrantyReceipt.setEnabled(true);
        this.m_jComment.setEnabled(true);
        this.m_jScale.setEnabled(true);
        this.m_jVprice.setEnabled(true);
        this.m_jstockcost.setEnabled(true);
        this.m_jstockvolume.setEnabled(true);
        this.jTableProductStock.setVisible(false);
        this.jTableProductStock.setEnabled(true);
        this.m_jImage.setEnabled(true);
        this.m_jDisplay.setEnabled(true);
        this.m_jTextTip.setEnabled(true);
        this.colourChooser.setEnabled(true);
        this.setButtonHTML();
        this.resetTranxTable();
        this.jTableProductStock.repaint();
        this.txtAttributes.setEnabled(true);
        this.calculateMargin();
        this.calculatePriceSellTax();
        this.calculateGP();
    }

    @Override
    public void writeValueDelete(Object value) {
        this.reportlock = true;
        Object[] myprod = (Object[])value;
        this.m_jTitle.setText(Formats.STRING.formatValue(myprod[1]) + " - " + Formats.STRING.formatValue(myprod[4]) + " " + AppLocal.getIntString("label.recorddeleted"));
        this.m_oId = myprod[0];
        this.m_jRef.setText(Formats.STRING.formatValue(myprod[1]));
        this.m_jCode.setText(Formats.STRING.formatValue(myprod[2]));
        this.m_jCodetype.setSelectedItem(myprod[3]);
        this.m_jName.setText(Formats.STRING.formatValue(myprod[4]));
        this.m_jPriceBuy.setText(Formats.CURRENCY.formatValue(myprod[5]));
        this.setPriceSell(myprod[6]);
        this.m_CategoryModel.setSelectedKey(myprod[7]);
        this.taxcatmodel.setSelectedKey(myprod[8]);
        this.attmodel.setSelectedKey(myprod[9]);
        this.m_jstockcost.setText(Formats.CURRENCY.formatValue(myprod[10]));
        this.m_jstockvolume.setText(Formats.DOUBLE.formatValue(myprod[11]));
        this.m_jImage.setImage(this.findImage(this.m_oId));
        this.m_jComment.setSelected((Boolean)myprod[13]);
        this.m_jScale.setSelected((Boolean)myprod[14]);
        this.m_jConstant.setSelected((Boolean)myprod[15]);
        this.m_jPrintKB.setSelected((Boolean)myprod[16]);
        this.m_jService.setSelected((Boolean)myprod[17]);
        this.m_jSendStatus.setSelected((Boolean)myprod[18]);
        this.txtAttributes.setText(Formats.BYTEA.formatValue(myprod[19]));
        this.m_jDisplay.setText(Formats.STRING.formatValue(myprod[20]));
        this.m_jVprice.setSelected((Boolean)myprod[21]);
        this.m_jVerpatrib.setSelected((Boolean)myprod[22]);
        this.m_jTextTip.setText(Formats.STRING.formatValue(myprod[23]));
        this.m_jCheckWarrantyReceipt.setSelected((Boolean)myprod[24]);
        this.m_jStockUnits.setText(Formats.DOUBLE.formatValue(myprod[25]));
        this.m_jPrintTo.setSelectedItem(myprod[26]);
        this.m_SuppliersModel.setSelectedKey(myprod[27]);
        this.m_UomModel.setSelectedKey(myprod[28]);
        this.m_jInCatalog.setSelected((Boolean)myprod[29]);
        this.m_jCatalogOrder.setText(Formats.INT.formatValue(myprod[30]));
        this.txtAttributes.setCaretPosition(0);
        this.reportlock = false;
        this.m_jRef.setEnabled(false);
        this.m_jCode.setEnabled(false);
        this.m_jCodetype.setEnabled(false);
        this.m_jName.setEnabled(false);
        this.m_jCategory.setEnabled(false);
        this.m_jTax.setEnabled(false);
        this.m_jAtt.setEnabled(false);
        this.m_jVerpatrib.setEnabled(false);
        this.m_jUom.setEnabled(false);
        this.m_jPriceBuy.setEnabled(false);
        this.m_jPriceSell.setEnabled(false);
        this.m_jPriceSellTax.setEnabled(false);
        this.m_jmargin.setEnabled(false);
        this.m_jPrintTo.setEnabled(false);
        this.m_jInCatalog.setEnabled(false);
        this.m_jConstant.setEnabled(false);
        this.m_jCatalogOrder.setEnabled(false);
        this.m_jSupplier.setEnabled(false);
        this.m_jService.setEnabled(false);
        this.m_jCheckWarrantyReceipt.setEnabled(false);
        this.m_jComment.setEnabled(false);
        this.m_jScale.setEnabled(false);
        this.m_jVprice.setEnabled(false);
        this.m_jstockcost.setEnabled(false);
        this.m_jstockvolume.setEnabled(false);
        this.stockModel = new StockTableModel(this.getProductOfName((String)this.m_oId));
        this.jTableProductStock.setModel(this.stockModel);
        this.jTableProductStock.setEnabled(false);
        this.m_jImage.setEnabled(false);
        this.m_jDisplay.setEnabled(false);
        this.m_jTextTip.setEnabled(false);
        this.colourChooser.setEnabled(false);
        this.txtAttributes.setEnabled(false);
        this.calculateMargin();
        this.calculatePriceSellTax();
        this.calculateGP();
    }

    public void resetTranxTable() {
        this.jTableProductStock.getColumnModel().getColumn(0).setPreferredWidth(100);
        this.jTableProductStock.getColumnModel().getColumn(1).setPreferredWidth(50);
        this.jTableProductStock.getColumnModel().getColumn(2).setPreferredWidth(50);
        this.jTableProductStock.getColumnModel().getColumn(3).setPreferredWidth(50);
        this.jTableProductStock.repaint();
    }

    @Override
    public Component getComponent() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.add((Component)this, "North");
        JScrollPane scroll = new JScrollPane(wrapper);
        scroll.getVerticalScrollBar().setUnitIncrement(30);
        scroll.setBorder(null);
        return scroll;
    }

    private void setCode() {
        Long lDateTime = new Date().getTime();
        if (!this.reportlock) {
            this.reportlock = true;
            if (this.m_jRef == null) {
                this.m_jCode.setText(Long.toString(lDateTime));
            } else if (this.m_jCode.getText() == null || "".equals(this.m_jCode.getText())) {
                this.m_jCode.setText(this.m_jRef.getText());
            }
            this.reportlock = false;
        }
    }

    private List<ProductStock> getProductOfName(String pId) {
        try {
            this.productStockList = this.dlSales.getProductStockList(pId);
        }
        catch (BasicException ex) {
            Logger.getLogger(ProductsEditor.class.getName()).log(Level.SEVERE, null, ex);
        }
        ArrayList<ProductStock> productList = new ArrayList<ProductStock>();
        for (ProductStock productStock : this.productStockList) {
            String productId = productStock.getProductId();
            if (!productId.equals(pId)) continue;
            productList.add(productStock);
        }
        this.repaint();
        this.refresh();
        return productList;
    }

    public AppView getAppView() {
        return this.appView;
    }

    public void setAppView(AppView appView) {
        this.appView = appView;
    }

    private void setDisplay(int btn) {
        String htmlString = this.m_jDisplay.getText();
        String ohtmlString = "<html><center>" + this.m_jName.getText();
        switch (btn) {
            case 1: {
                this.m_jDisplay.insert("<br>", this.m_jDisplay.getCaretPosition());
                break;
            }
            case 2: {
                this.m_jDisplay.insert("<font color=" + this.colourChooser.getText() + ">", this.m_jDisplay.getCaretPosition());
                break;
            }
            case 3: {
                this.m_jDisplay.insert("<font size=+2>LARGE TEXT", this.m_jDisplay.getCaretPosition());
                break;
            }
            case 4: {
                this.m_jDisplay.insert("<font size=-2>small text", this.m_jDisplay.getCaretPosition());
                break;
            }
            case 5: {
                this.m_jDisplay.insert("<b>Bold Text</b>", this.m_jDisplay.getCaretPosition());
                break;
            }
            case 6: {
                this.m_jDisplay.insert("<i>Italic Text</i>", this.m_jDisplay.getCaretPosition());
                break;
            }
            case 7: {
                this.m_jDisplay.insert("<img src=Image URL>", this.m_jDisplay.getCaretPosition());
                break;
            }
            case 8: {
                htmlString = ohtmlString;
                this.m_jDisplay.setText(htmlString);
            }
            default: {
                htmlString = htmlString + "";
                this.m_jDisplay.setText(htmlString);
            }
        }
    }

    private void setButtonHTML() {
        this.jButtonHTML.setText(this.m_jDisplay.getText());
    }

    private BufferedImage findImage(Object id) {
        try {
            return (BufferedImage)this.loadimage.find(id);
        }
        catch (BasicException e) {
            return null;
        }
    }

    private void calculateMargin() {
        if (!this.reportlock) {
            this.reportlock = true;
            Double dPriceBuy = ProductsEditor.readCurrency(this.m_jPriceBuy.getText());
            Double dPriceSell = (Double)this.pricesell;
            if (dPriceBuy == null || dPriceSell == null) {
                this.m_jmargin.setText(null);
            } else {
                this.m_jmargin.setText(Formats.PERCENT.formatValue(dPriceSell / dPriceBuy - 1.0));
            }
            this.reportlock = false;
        }
    }

    private void calculatePriceSellTax() {
        if (!this.reportlock) {
            this.reportlock = true;
            Double dPriceSell = (Double)this.pricesell;
            if (dPriceSell == null) {
                this.m_jPriceSellTax.setText(null);
            } else {
                double dTaxRate = this.taxeslogic.getTaxRate((TaxCategoryInfo)this.taxcatmodel.getSelectedItem());
                this.m_jPriceSellTax.setText(Formats.CURRENCY.formatValue(dPriceSell * (1.0 + dTaxRate)));
            }
            this.reportlock = false;
        }
    }

    private void calculateGP() {
        if (!this.reportlock) {
            this.reportlock = true;
            Double dPriceBuy = ProductsEditor.readCurrency(this.m_jPriceBuy.getText());
            Double dPriceSell = (Double)this.pricesell;
            if (dPriceBuy == null || dPriceSell == null) {
                this.m_jGrossProfit.setText(null);
            } else {
                this.m_jGrossProfit.setText(Formats.PERCENT.formatValue((dPriceSell - dPriceBuy) / dPriceSell));
            }
            this.reportlock = false;
        }
    }

    private void calculatePriceSellfromMargin() {
        if (!this.reportlock) {
            this.reportlock = true;
            Double dPriceBuy = ProductsEditor.readCurrency(this.m_jPriceBuy.getText());
            Double dMargin = ProductsEditor.readPercent(this.m_jmargin.getText());
            if (dMargin == null || dPriceBuy == null) {
                this.setPriceSell(null);
            } else {
                this.setPriceSell(dPriceBuy * (1.0 + dMargin));
            }
            this.reportlock = false;
        }
    }

    private void calculatePriceSellfromPST() {
        if (!this.reportlock) {
            this.reportlock = true;
            Double dPriceSellTax = ProductsEditor.readCurrency(this.m_jPriceSellTax.getText());
            if (dPriceSellTax == null) {
                this.setPriceSell(null);
            } else {
                double dTaxRate = this.taxeslogic.getTaxRate((TaxCategoryInfo)this.taxcatmodel.getSelectedItem());
                this.setPriceSell(dPriceSellTax / (1.0 + dTaxRate));
            }
            this.reportlock = false;
        }
    }

    private void setPriceSell(Object value) {
        if (!this.priceselllock) {
            this.priceselllock = true;
            this.pricesell = value;
            this.m_jPriceSell.setText(Formats.CURRENCY.formatValue(this.pricesell));
            this.priceselllock = false;
        }
    }

    private static Double readCurrency(String sValue) {
        try {
            return (Double)Formats.CURRENCY.parseValue(sValue);
        }
        catch (BasicException e) {
            return null;
        }
    }

    private static Double readPercent(String sValue) {
        try {
            return (Double)Formats.PERCENT.parseValue(sValue);
        }
        catch (BasicException e) {
            return null;
        }
    }

    private void initComponents() {
        this.m_jTitle = new JLabel();
        this.jTabbedPane1 = new JTabbedPane();
        this.jPanel1 = new JPanel();
        this.jLabel1 = new JLabel();
        this.m_jRef = new JTextField();
        this.jLabel6 = new JLabel();
        this.m_jCode = new JTextField();
        this.m_jCodetype = new JComboBox();
        this.jLabel2 = new JLabel();
        this.m_jName = new JTextField();
        this.jLabel5 = new JLabel();
        this.m_jCategory = new JComboBox();
        this.jLabel13 = new JLabel();
        this.m_jAtt = new JComboBox();
        this.jLabel7 = new JLabel();
        this.m_jTax = new JComboBox();
        this.jLabel16 = new JLabel();
        this.m_jPriceSellTax = new JTextField();
        this.jLabel4 = new JLabel();
        this.m_jPriceSell = new JTextField();
        this.jLabel19 = new JLabel();
        this.m_jmargin = new JTextField();
        this.jLabel3 = new JLabel();
        this.m_jPriceBuy = new JTextField();
        this.m_jVerpatrib = new JCheckBox();
        this.m_jGrossProfit = new JTextField();
        this.jLabel22 = new JLabel();
        this.jLabel26 = new JLabel();
        this.m_jUom = new JComboBox();
        this.jLabel17 = new JLabel();
        this.m_jSupplier = new JComboBox();
        this.webBtnSupplier = new JButton();
        this.m_jstockvolume = new JTextField();
        this.jLabel8 = new JLabel();
        this.m_jInCatalog = new JCheckBox();
        this.jLabel18 = new JLabel();
        this.m_jCatalogOrder = new JTextField();
        this.jLabel15 = new JLabel();
        this.m_jService = new JCheckBox();
        this.jLabel11 = new JLabel();
        this.m_jComment = new JCheckBox();
        this.jLabel12 = new JLabel();
        this.m_jScale = new JCheckBox();
        this.m_jConstant = new JCheckBox();
        this.jLabel14 = new JLabel();
        this.jLabel20 = new JLabel();
        this.m_jVprice = new JCheckBox();
        this.jLabel33 = new JLabel();
        this.m_jCheckWarrantyReceipt = new JCheckBox();
        this.jLabel23 = new JLabel();
        this.webLabel1 = new JLabel();
        this.m_jPrintTo = new JComboBox();
        this.jBtnShowTrans = new JButton();
        this.jScrollPane2 = new JScrollPane();
        this.jTableProductStock = new JTable();
        this.m_jPrintKB = new JCheckBox();
        this.m_jSendStatus = new JCheckBox();
        this.m_jStockUnits = new JTextField();
        this.jPanel6 = new JPanel();
        this.m_jImage = new JImageEditor();
        this.jLabel34 = new JLabel();
        this.jPanel4 = new JPanel();
        this.jLabel28 = new JLabel();
        this.jButtonHTML = new JButton();
        this.colourChooser = new JTextField();
        this.webBtnBreak = new JButton();
        this.webBtnColour = new JButton();
        this.webBtnLarge = new JButton();
        this.webBtnSmall = new JButton();
        this.webBtnBold = new JButton();
        this.webBtnItalic = new JButton();
        this.webBtnURL = new JButton();
        this.webBtnReset = new JButton();
        this.jLabel21 = new JLabel();
        this.m_jTextTip = new JTextField();
        this.jScrollPane3 = new JScrollPane();
        this.m_jDisplay = new JTextArea();
        this.jPanel3 = new JPanel();
        this.jScrollPane1 = new JScrollPane();
        this.txtAttributes = new JTextArea();
        this.jLabel9 = new JLabel();
        this.jLabel10 = new JLabel();
        this.m_jstockcost = new JTextField();
        this.jPanel2 = new JPanel();
        this.setLayout(new BorderLayout());
        this.m_jTitle.setBackground(new Color(255, 255, 255));
        this.m_jTitle.setFont(new Font("Arial", 0, 14));
        this.m_jTitle.setForeground(new Color(102, 102, 102));
        this.m_jTitle.setHorizontalAlignment(4);
        this.m_jTitle.setVerticalAlignment(3);
        this.m_jTitle.setFocusable(false);
        this.m_jTitle.setHorizontalTextPosition(4);
        this.m_jTitle.setOpaque(true);
        this.m_jTitle.setPreferredSize(new Dimension(260, 25));
        this.add((Component)this.m_jTitle, "North");
        this.jTabbedPane1.setFont(new Font("Arial", 0, 14));
        this.jLabel1.setFont(new Font("Arial", 0, 14));
        this.jLabel1.setText(AppLocal.getIntString("label.prodrefm"));
        this.jLabel1.setPreferredSize(new Dimension(110, 30));
        this.m_jRef.setFont(new Font("Arial", 0, 14));
        this.m_jRef.setToolTipText("");
        this.m_jRef.setPreferredSize(new Dimension(150, 30));
        this.m_jRef.addFocusListener(new FocusAdapter(){

            @Override
            public void focusLost(FocusEvent evt) {
                ProductsEditor.this.m_jRefFocusLost(evt);
            }
        });
        this.jLabel6.setFont(new Font("Arial", 0, 14));
        this.jLabel6.setText(AppLocal.getIntString("label.prodbarcodem"));
        this.jLabel6.setPreferredSize(new Dimension(100, 30));
        this.m_jCode.setFont(new Font("Arial", 0, 14));
        this.m_jCode.setPreferredSize(new Dimension(125, 30));
        this.m_jCodetype.setFont(new Font("Arial", 0, 14));
        this.m_jCodetype.setModel(new DefaultComboBoxModel<String>(new String[]{"EAN-13", "EAN-8", "CODE128", "Upc-A", "Upc-E"}));
        this.m_jCodetype.setPreferredSize(new Dimension(80, 30));
        this.jLabel2.setFont(new Font("Arial", 0, 14));
        this.jLabel2.setText(AppLocal.getIntString("label.prodnamem"));
        this.jLabel2.setPreferredSize(new Dimension(110, 30));
        this.m_jName.setFont(new Font("Arial", 0, 14));
        this.m_jName.setPreferredSize(new Dimension(450, 30));
        this.m_jName.addFocusListener(new FocusAdapter(){

            @Override
            public void focusLost(FocusEvent evt) {
                ProductsEditor.this.m_jNameFocusLost(evt);
            }
        });
        this.jLabel5.setFont(new Font("Arial", 0, 14));
        this.jLabel5.setText(AppLocal.getIntString("label.prodcategorym"));
        this.jLabel5.setPreferredSize(new Dimension(110, 30));
        this.m_jCategory.setFont(new Font("Arial", 0, 14));
        this.m_jCategory.setBorder(null);
        this.m_jCategory.setPreferredSize(new Dimension(450, 30));
        this.jLabel13.setFont(new Font("Arial", 0, 14));
        this.jLabel13.setText(AppLocal.getIntString("label.attributes"));
        this.jLabel13.setPreferredSize(new Dimension(110, 30));
        this.m_jAtt.setFont(new Font("Arial", 0, 14));
        this.m_jAtt.setPreferredSize(new Dimension(200, 30));
        this.jLabel7.setFont(new Font("Arial", 0, 14));
        this.jLabel7.setText(AppLocal.getIntString("label.taxcategorym"));
        this.jLabel7.setPreferredSize(new Dimension(110, 30));
        this.m_jTax.setFont(new Font("Arial", 0, 14));
        this.m_jTax.setPreferredSize(new Dimension(200, 30));
        this.jLabel16.setFont(new Font("Arial", 0, 14));
        this.jLabel16.setText(AppLocal.getIntString("label.prodpriceselltaxm"));
        this.jLabel16.setPreferredSize(new Dimension(110, 30));
        this.m_jPriceSellTax.setFont(new Font("Arial", 0, 14));
        this.m_jPriceSellTax.setHorizontalAlignment(4);
        this.m_jPriceSellTax.setPreferredSize(new Dimension(200, 30));
        this.jLabel4.setFont(new Font("Arial", 0, 14));
        this.jLabel4.setHorizontalAlignment(2);
        this.jLabel4.setText(AppLocal.getIntString("label.prodpricesell"));
        this.jLabel4.setPreferredSize(new Dimension(110, 30));
        this.m_jPriceSell.setFont(new Font("Arial", 0, 14));
        this.m_jPriceSell.setHorizontalAlignment(4);
        this.m_jPriceSell.setPreferredSize(new Dimension(200, 30));
        this.jLabel19.setFont(new Font("Arial", 0, 14));
        this.jLabel19.setHorizontalAlignment(0);
        ResourceBundle bundle = ResourceBundle.getBundle("pos_messages");
        this.jLabel19.setText(bundle.getString("label.margin"));
        this.jLabel19.setPreferredSize(new Dimension(110, 30));
        this.m_jmargin.setFont(new Font("Arial", 0, 14));
        this.m_jmargin.setHorizontalAlignment(4);
        this.m_jmargin.setCursor(new Cursor(2));
        this.m_jmargin.setEnabled(false);
        this.m_jmargin.setPreferredSize(new Dimension(110, 30));
        this.jLabel3.setFont(new Font("Arial", 0, 14));
        this.jLabel3.setText(AppLocal.getIntString("label.prodpricebuym"));
        this.jLabel3.setPreferredSize(new Dimension(110, 30));
        this.m_jPriceBuy.setFont(new Font("Arial", 0, 14));
        this.m_jPriceBuy.setHorizontalAlignment(4);
        this.m_jPriceBuy.setText("0");
        this.m_jPriceBuy.setPreferredSize(new Dimension(200, 30));
        this.m_jVerpatrib.setFont(new Font("Arial", 0, 14));
        this.m_jVerpatrib.setText(bundle.getString("label.mandatory"));
        this.m_jVerpatrib.setHorizontalTextPosition(10);
        this.m_jVerpatrib.setPreferredSize(new Dimension(49, 30));
        this.m_jVerpatrib.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ProductsEditor.this.none(evt);
            }
        });
        this.m_jGrossProfit.setFont(new Font("Arial", 0, 14));
        this.m_jGrossProfit.setHorizontalAlignment(4);
        this.m_jGrossProfit.setDisabledTextColor(new Color(0, 0, 0));
        this.m_jGrossProfit.setEnabled(false);
        this.m_jGrossProfit.setPreferredSize(new Dimension(110, 30));
        this.jLabel22.setFont(new Font("Arial", 0, 14));
        this.jLabel22.setHorizontalAlignment(0);
        this.jLabel22.setText(bundle.getString("label.grossprofit"));
        this.jLabel22.setHorizontalTextPosition(0);
        this.jLabel22.setPreferredSize(new Dimension(110, 30));
        this.jLabel26.setFont(new Font("Arial", 0, 14));
        this.jLabel26.setText(AppLocal.getIntString("label.UOM"));
        this.jLabel26.setPreferredSize(new Dimension(70, 30));
        this.m_jUom.setFont(new Font("Arial", 0, 14));
        this.m_jUom.setPreferredSize(new Dimension(150, 30));
        this.jLabel17.setFont(new Font("Arial", 0, 14));
        this.jLabel17.setText(AppLocal.getIntString("label.prodsupplier"));
        this.jLabel17.setPreferredSize(new Dimension(110, 30));
        this.m_jSupplier.setFont(new Font("Arial", 0, 14));
        this.m_jSupplier.setPreferredSize(new Dimension(200, 30));
        this.webBtnSupplier.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/customer_add_sml.png")));
        this.webBtnSupplier.setText(AppLocal.getIntString("label.supplier"));
        this.webBtnSupplier.setFont(new Font("Arial", 0, 11));
        this.webBtnSupplier.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ProductsEditor.this.webBtnSupplierActionPerformed(evt);
            }
        });
        GroupLayout jPanel1Layout = new GroupLayout(this.jPanel1);
        this.jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.jLabel2, -1, -1, Short.MAX_VALUE).addComponent(this.jLabel5, -1, -1, Short.MAX_VALUE))).addGroup(jPanel1Layout.createSequentialGroup().addGap(10, 10, 10).addComponent(this.jLabel13, -2, -1, -2)).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addComponent(this.jLabel7, -2, -1, -2)).addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup().addContainerGap().addComponent(this.jLabel16, -2, -1, -2))).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addComponent(this.jLabel4, -2, -1, -2))).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.m_jPriceSell, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.m_jGrossProfit, -2, -1, -2).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.m_jUom, -2, -1, -2).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jLabel22, -2, -1, -2).addGap(18, 18, 18).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.m_jmargin, -1, -1, Short.MAX_VALUE).addComponent(this.jLabel19, -2, -1, -2))))).addGap(115, 115, 115)).addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.m_jName, -1, -1, -2).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.m_jAtt, -2, -1, -2).addGap(18, 18, 18).addComponent(this.m_jVerpatrib, -2, 120, -2)).addComponent(this.m_jTax, -2, -1, -2).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.m_jPriceSellTax, -2, -1, -2).addGap(18, 18, 18).addComponent(this.jLabel26, -2, -1, -2)).addComponent(this.m_jCategory, -2, -1, -2)).addGap(0, 0, Short.MAX_VALUE))).addGap(50, 50, 50)).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jLabel1, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.m_jRef, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jLabel6, -2, 78, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.m_jCode, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.m_jCodetype, -2, -1, -2)).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jLabel3, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.m_jPriceBuy, -2, -1, -2)).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jLabel17, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.m_jSupplier, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.webBtnSupplier, -2, -1, -2))).addContainerGap(-1, Short.MAX_VALUE)));
        jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1, -2, -1, -2).addComponent(this.m_jRef, -2, -1, -2).addComponent(this.jLabel6, -2, -1, -2).addComponent(this.m_jCode, -2, -1, -2).addComponent(this.m_jCodetype, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.m_jName, -2, -1, -2).addComponent(this.jLabel2, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.m_jCategory, -2, -1, -2).addComponent(this.jLabel5, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel13, -2, -1, -2).addComponent(this.m_jAtt, -2, -1, -2).addComponent(this.m_jVerpatrib, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.m_jTax, -2, -1, -2).addComponent(this.jLabel7, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel16, -2, -1, -2).addComponent(this.m_jPriceSellTax, -2, -1, -2)).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel26, -2, -1, -2).addComponent(this.m_jUom, -2, -1, -2))).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel4, -2, -1, -2).addComponent(this.m_jPriceSell, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel3, -2, -1, -2).addComponent(this.m_jPriceBuy, -2, -1, -2))).addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel22, -2, -1, -2).addComponent(this.jLabel19, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.m_jGrossProfit, -2, -1, -2).addComponent(this.m_jmargin, -2, -1, -2)))).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.webBtnSupplier, GroupLayout.Alignment.TRAILING, -1, -1, Short.MAX_VALUE).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel17, -2, -1, -2).addComponent(this.m_jSupplier, -2, -1, -2))).addContainerGap()));
        this.jTabbedPane1.addTab(AppLocal.getIntString("label.prodgeneral"), this.jPanel1);
        this.jLabel9.setFont(new Font("Arial", 0, 14));
        this.jLabel9.setHorizontalAlignment(4);
        this.jLabel9.setText(AppLocal.getIntString("label.prodstockcost"));
        this.jLabel9.setPreferredSize(new Dimension(150, 30));
        this.m_jstockcost.setFont(new Font("Arial", 0, 14));
        this.m_jstockcost.setHorizontalAlignment(4);
        this.m_jstockcost.setPreferredSize(new Dimension(110, 30));
        this.jLabel10.setFont(new Font("Arial", 0, 14));
        this.jLabel10.setHorizontalAlignment(4);
        this.jLabel10.setText(AppLocal.getIntString("label.prodstockvol"));
        this.jLabel10.setPreferredSize(new Dimension(150, 30));
        this.m_jstockvolume.setFont(new Font("Arial", 0, 14));
        this.m_jstockvolume.setHorizontalAlignment(4);
        this.m_jstockvolume.setPreferredSize(new Dimension(110, 30));
        this.jLabel8.setFont(new Font("Arial", 0, 14));
        this.jLabel8.setText(AppLocal.getIntString("label.prodincatalog"));
        this.jLabel8.setPreferredSize(new Dimension(150, 30));
        this.m_jInCatalog.setFont(new Font("Tahoma", 0, 12));
        this.m_jInCatalog.setSelected(true);
        this.m_jInCatalog.setPreferredSize(new Dimension(50, 30));
        this.m_jInCatalog.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ProductsEditor.this.m_jInCatalogActionPerformed(evt);
            }
        });
        this.jLabel18.setFont(new Font("Arial", 0, 14));
        this.jLabel18.setText(AppLocal.getIntString("label.prodorder"));
        this.jLabel18.setPreferredSize(new Dimension(150, 30));
        this.m_jCatalogOrder.setFont(new Font("Arial", 0, 14));
        this.m_jCatalogOrder.setHorizontalAlignment(4);
        this.m_jCatalogOrder.setPreferredSize(new Dimension(50, 30));
        this.jLabel15.setFont(new Font("Arial", 0, 14));
        this.jLabel15.setText(bundle.getString("label.service"));
        this.jLabel15.setPreferredSize(new Dimension(150, 30));
        this.m_jService.setFont(new Font("Tahoma", 0, 12));
        this.m_jService.setToolTipText("A Service Item will not be deducted from the Inventory");
        this.m_jService.setPreferredSize(new Dimension(50, 30));
        this.jLabel11.setFont(new Font("Arial", 0, 14));
        this.jLabel11.setText(AppLocal.getIntString("label.prodaux"));
        this.jLabel11.setPreferredSize(new Dimension(150, 30));
        this.m_jComment.setFont(new Font("Tahoma", 0, 12));
        this.m_jComment.setPreferredSize(new Dimension(50, 30));
        this.jLabel12.setFont(new Font("Arial", 0, 14));
        this.jLabel12.setText(AppLocal.getIntString("label.prodscale"));
        this.jLabel12.setPreferredSize(new Dimension(150, 30));
        this.m_jScale.setFont(new Font("Tahoma", 0, 12));
        this.m_jScale.setPreferredSize(new Dimension(50, 30));
        this.m_jConstant.setFont(new Font("Tahoma", 0, 12));
        this.m_jConstant.setPreferredSize(new Dimension(50, 30));
        this.jLabel14.setFont(new Font("Arial", 0, 14));
        this.jLabel14.setText(bundle.getString("label.prodconstant"));
        this.jLabel14.setPreferredSize(new Dimension(150, 30));
        this.jLabel20.setFont(new Font("Arial", 0, 14));
        this.jLabel20.setText(bundle.getString("label.variableprice"));
        this.jLabel20.setPreferredSize(new Dimension(150, 30));
        this.m_jVprice.setFont(new Font("Tahoma", 0, 12));
        this.m_jVprice.setPreferredSize(new Dimension(50, 30));
        this.jLabel33.setFont(new Font("Arial", 0, 14));
        this.jLabel33.setText(bundle.getString("label.warranty"));
        this.jLabel33.setToolTipText(bundle.getString("label.warranty"));
        this.jLabel33.setPreferredSize(new Dimension(150, 30));
        this.m_jCheckWarrantyReceipt.setFont(new Font("Arial", 0, 14));
        this.m_jCheckWarrantyReceipt.setText(bundle.getString("label.productreceipt"));
        this.m_jCheckWarrantyReceipt.setPreferredSize(new Dimension(50, 30));
        this.jLabel23.setFont(new Font("Arial", 0, 12));
        this.jLabel23.setHorizontalAlignment(0);
        this.jLabel23.setText(bundle.getString("label.prodminmax"));
        this.jLabel23.setVerticalAlignment(1);
        this.jLabel23.setPreferredSize(new Dimension(531, 20));
        this.webLabel1.setText(bundle.getString("label.printto"));
        this.webLabel1.setToolTipText(bundle.getString("tooltip.printto"));
        this.webLabel1.setFont(new Font("Arial", 0, 14));
        this.webLabel1.setPreferredSize(new Dimension(150, 30));
        this.m_jPrintTo.setFont(new Font("Arial", 0, 14));
        this.m_jPrintTo.setModel(new DefaultComboBoxModel<String>(new String[]{"0", "1", "2", "3", "4", "5", "6"}));
        this.m_jPrintTo.setPreferredSize(new Dimension(80, 30));
        this.jBtnShowTrans.setFont(new Font("Arial", 0, 12));
        this.jBtnShowTrans.setText(bundle.getString("button.ProductStock"));
        this.jBtnShowTrans.setToolTipText("");
        this.jBtnShowTrans.setPreferredSize(new Dimension(140, 25));
        this.jBtnShowTrans.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ProductsEditor.this.jBtnShowTransActionPerformed(evt);
            }
        });
        this.jScrollPane2.setFont(new Font("Arial", 0, 14));
        this.jTableProductStock.setModel(new DefaultTableModel(new Object[][]{{null, null, null, null}, {null, null, null, null}, {null, null, null, null}, {null, null, null, null}, {null, null, null, null}, {null, null, null, null}, {null, null, null, null}, {null, null, null, null}}, new String[]{"Location", "Current", "Minimum", "Maximum"}));
        this.jTableProductStock.setFont(new Font("Arial", 0, 14));
        this.jTableProductStock.setRowHeight(25);
        this.jScrollPane2.setViewportView(this.jTableProductStock);
        this.m_jPrintKB.setFont(new Font("Tahoma", 0, 12));
        this.m_jSendStatus.setFont(new Font("Tahoma", 0, 12));
        this.m_jStockUnits.setEditable(false);
        this.m_jStockUnits.setFont(new Font("Arial", 0, 12));
        this.m_jStockUnits.setHorizontalAlignment(4);
        this.m_jStockUnits.setText("0");
        this.m_jStockUnits.setBorder(null);
        GroupLayout jPanel2Layout = new GroupLayout(this.jPanel2);
        this.jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel2Layout.createSequentialGroup().addContainerGap().addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel2Layout.createSequentialGroup().addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.jLabel18, -2, -1, -2).addComponent(this.jLabel8, GroupLayout.Alignment.LEADING, -2, -1, -2).addComponent(this.jLabel14, GroupLayout.Alignment.LEADING, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.m_jCatalogOrder, -2, -1, -2).addComponent(this.m_jConstant, -2, -1, -2).addComponent(this.m_jInCatalog, -2, -1, -2)).addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel2Layout.createSequentialGroup().addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 115, Short.MAX_VALUE).addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.jLabel9, -2, -1, -2).addComponent(this.jLabel10, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.m_jstockvolume, -1, -1, Short.MAX_VALUE).addComponent(this.m_jstockcost, -2, -1, -2)).addContainerGap(-1, Short.MAX_VALUE)).addGroup(GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup().addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addComponent(this.jLabel23, -2, 360, -2)))).addGroup(jPanel2Layout.createSequentialGroup().addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addGroup(jPanel2Layout.createSequentialGroup().addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel15, -2, -1, -2).addGroup(jPanel2Layout.createSequentialGroup().addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel33, -2, -1, -2).addComponent(this.jLabel11, -2, -1, -2).addComponent(this.jLabel12, -2, -1, -2).addComponent(this.jLabel20, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.m_jService, -2, -1, -2).addComponent(this.m_jCheckWarrantyReceipt, -2, -1, -2).addComponent(this.m_jComment, -2, -1, -2).addComponent(this.m_jScale, -2, -1, -2).addComponent(this.m_jVprice, -2, -1, -2))).addGroup(jPanel2Layout.createSequentialGroup().addComponent(this.m_jPrintKB, -2, 30, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.m_jSendStatus, -2, 30, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.m_jStockUnits, -2, 24, -2))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jScrollPane2, -2, 372, -2)).addGroup(GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup().addComponent(this.webLabel1, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.m_jPrintTo, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addComponent(this.jBtnShowTrans, -2, -1, -2))).addContainerGap(25, Short.MAX_VALUE)))));
        jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel2Layout.createSequentialGroup().addContainerGap().addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel2Layout.createSequentialGroup().addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel8, -2, -1, -2).addComponent(this.m_jInCatalog, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel14, -2, -1, -2).addComponent(this.m_jConstant, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.m_jCatalogOrder, -2, -1, -2).addComponent(this.jLabel18, -2, -1, -2))).addGroup(jPanel2Layout.createSequentialGroup().addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.m_jstockcost, -2, -1, -2).addComponent(this.jLabel9, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.m_jstockvolume, -2, -1, -2).addGroup(jPanel2Layout.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE).addComponent(this.jLabel10, -2, -1, -2))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jLabel23, -2, 30, -2))).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jBtnShowTrans, -2, 30, -2).addGroup(GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.m_jPrintTo, -2, -1, -2).addComponent(this.webLabel1, -2, -1, -2))).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jScrollPane2, -2, 206, -2).addGroup(jPanel2Layout.createSequentialGroup().addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel15, -2, -1, -2).addComponent(this.m_jService, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel33, -2, -1, -2).addComponent(this.m_jCheckWarrantyReceipt, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel11, -2, -1, -2).addComponent(this.m_jComment, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel12, -2, -1, -2).addComponent(this.m_jScale, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel20, -2, -1, -2).addComponent(this.m_jVprice, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.m_jPrintKB, -2, 25, -2).addComponent(this.m_jSendStatus, -2, 25, -2)).addComponent(this.m_jStockUnits, GroupLayout.Alignment.TRAILING, -2, 25, -2)))).addGap(42, 42, 42)));
        this.m_jService.getAccessibleContext().setAccessibleDescription("null");
        this.jTabbedPane1.addTab(AppLocal.getIntString("label.prodstock"), this.jPanel2);
        this.m_jImage.setFont(new Font("Arial", 0, 14));
        this.m_jImage.setPreferredSize(new Dimension(350, 300));
        this.jLabel34.setFont(new Font("Arial", 0, 14));
        this.jLabel34.setText(bundle.getString("label.imagesize"));
        this.jLabel34.setPreferredSize(new Dimension(500, 30));
        GroupLayout jPanel6Layout = new GroupLayout(this.jPanel6);
        this.jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(jPanel6Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel6Layout.createSequentialGroup().addContainerGap().addGroup(jPanel6Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel34, -2, -1, -2).addComponent(this.m_jImage, -2, -1, -2)).addContainerGap(105, Short.MAX_VALUE)));
        jPanel6Layout.setVerticalGroup(jPanel6Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel6Layout.createSequentialGroup().addContainerGap().addComponent(this.m_jImage, -2, -1, -2).addGap(18, 18, 18).addComponent(this.jLabel34, -2, -1, -2).addContainerGap()));
        this.jTabbedPane1.addTab(bundle.getString("label.image"), this.jPanel6);
        this.jPanel4.setFont(new Font("Arial", 0, 12));
        this.jPanel4.setPreferredSize(new Dimension(0, 0));
        this.jLabel28.setFont(new Font("Arial", 0, 14));
        this.jLabel28.setText(bundle.getString("label.prodbuttonhtml"));
        this.jLabel28.setPreferredSize(new Dimension(250, 30));
        this.jButtonHTML.setFont(new Font("Arial", 0, 12));
        this.jButtonHTML.setText(bundle.getString("button.htmltest"));
        this.jButtonHTML.setMargin(new Insets(1, 1, 1, 1));
        this.jButtonHTML.setMaximumSize(new Dimension(96, 72));
        this.jButtonHTML.setMinimumSize(new Dimension(96, 72));
        this.jButtonHTML.setPreferredSize(new Dimension(96, 72));
        this.jButtonHTML.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ProductsEditor.this.jButtonHTMLActionPerformed(evt);
            }
        });
        this.colourChooser.setForeground(new Color(0, 153, 255));
        this.colourChooser.setToolTipText(bundle.getString("tooltip.prodhtmldisplayColourChooser"));
        this.colourChooser.setFont(new Font("Arial", 0, 14));
        this.colourChooser.setPreferredSize(new Dimension(120, 30));
        this.colourChooser.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ProductsEditor.this.colourChooserActionPerformed(evt);
            }
        });
        this.webBtnBreak.setText(bundle.getString("button.prodhtmldisplayBreak"));
        this.webBtnBreak.setToolTipText(bundle.getString("tooltip.prodhtmldisplayBreak"));
        this.webBtnBreak.setFont(new Font("Arial", 0, 12));
        this.webBtnBreak.setPreferredSize(new Dimension(80, 35));
        this.webBtnBreak.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ProductsEditor.this.webBtnBreakActionPerformed(evt);
            }
        });
        this.webBtnColour.setForeground(new Color(102, 153, 255));
        this.webBtnColour.setText(bundle.getString("button.prodhtmldisplayColour"));
        this.webBtnColour.setToolTipText(bundle.getString("tooltip.prodhtmldisplayColour"));
        this.webBtnColour.setFont(new Font("Arial", 1, 12));
        this.webBtnColour.setPreferredSize(new Dimension(80, 35));
        this.webBtnColour.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ProductsEditor.this.webBtnColourActionPerformed(evt);
            }
        });
        this.webBtnLarge.setText(bundle.getString("button.prodhtmldisplayLarge"));
        this.webBtnLarge.setToolTipText(bundle.getString("tooltip.prodhtmldisplayLarge"));
        this.webBtnLarge.setFont(new Font("Arial", 0, 14));
        this.webBtnLarge.setPreferredSize(new Dimension(80, 35));
        this.webBtnLarge.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ProductsEditor.this.webBtnLargeActionPerformed(evt);
            }
        });
        this.webBtnSmall.setText(bundle.getString("button.prodhtmldisplaySmall"));
        this.webBtnSmall.setToolTipText(bundle.getString("tooltip.prodhtmldisplaySmall"));
        this.webBtnSmall.setFont(new Font("Arial", 0, 11));
        this.webBtnSmall.setPreferredSize(new Dimension(80, 35));
        this.webBtnSmall.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ProductsEditor.this.webBtnSmallActionPerformed(evt);
            }
        });
        this.webBtnBold.setText(bundle.getString("button.prodhtmldisplayBold"));
        this.webBtnBold.setToolTipText(bundle.getString("tooltip.prodhtmldisplayBold"));
        this.webBtnBold.setFont(new Font("Arial", 1, 12));
        this.webBtnBold.setPreferredSize(new Dimension(80, 35));
        this.webBtnBold.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ProductsEditor.this.webBtnBoldActionPerformed(evt);
            }
        });
        this.webBtnItalic.setText(bundle.getString("button.prodhtmldisplayItalic"));
        this.webBtnItalic.setToolTipText(bundle.getString("tooltip.prodhtmldisplayItalic"));
        this.webBtnItalic.setFont(new Font("Arial", 2, 12));
        this.webBtnItalic.setPreferredSize(new Dimension(80, 35));
        this.webBtnItalic.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ProductsEditor.this.webBtnItalicActionPerformed(evt);
            }
        });
        this.webBtnURL.setText(bundle.getString("button.prodhtmldisplayImage"));
        this.webBtnURL.setToolTipText(bundle.getString("tooltip.prodhtmldisplayImage"));
        this.webBtnURL.setFont(new Font("Arial", 0, 12));
        this.webBtnURL.setPreferredSize(new Dimension(80, 35));
        this.webBtnURL.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ProductsEditor.this.webBtnURLActionPerformed(evt);
            }
        });
        this.webBtnReset.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/reload.png")));
        this.webBtnReset.setText(bundle.getString("button.prodhtmldisplayReset"));
        this.webBtnReset.setToolTipText(bundle.getString("tooltip.prodhtmldisplayReset"));
        this.webBtnReset.setFont(new Font("Arial", 0, 12));
        this.webBtnReset.setPreferredSize(new Dimension(80, 35));
        this.webBtnReset.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ProductsEditor.this.webBtnResetActionPerformed(evt);
            }
        });
        this.jLabel21.setFont(new Font("Arial", 0, 14));
        this.jLabel21.setText(bundle.getString("label.texttip"));
        this.jLabel21.setPreferredSize(new Dimension(110, 30));
        this.m_jTextTip.setFont(new Font("Arial", 0, 14));
        this.m_jTextTip.setPreferredSize(new Dimension(400, 30));
        this.m_jDisplay.setColumns(20);
        this.m_jDisplay.setLineWrap(true);
        this.m_jDisplay.setRows(4);
        this.m_jDisplay.setWrapStyleWord(true);
        this.m_jDisplay.setPreferredSize(new Dimension(160, 100));
        this.jScrollPane3.setViewportView(this.m_jDisplay);
        GroupLayout jPanel4Layout = new GroupLayout(this.jPanel4);
        this.jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel4Layout.createSequentialGroup().addGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel4Layout.createSequentialGroup().addContainerGap().addGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel4Layout.createSequentialGroup().addComponent(this.jLabel21, -2, -1, -2).addGap(18, 18, 18).addComponent(this.m_jTextTip, -2, -1, -2)).addGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addGroup(GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup().addComponent(this.jLabel28, -2, 230, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addComponent(this.webBtnReset, -2, 100, -2)).addComponent(this.jScrollPane3, GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup().addGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.jButtonHTML, -2, -1, -2).addGroup(jPanel4Layout.createSequentialGroup().addComponent(this.webBtnBreak, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.webBtnColour, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.webBtnLarge, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.webBtnSmall, -2, -1, -2))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.webBtnBold, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.webBtnItalic, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.webBtnURL, -2, -1, -2))))).addGroup(jPanel4Layout.createSequentialGroup().addGap(74, 74, 74).addComponent(this.colourChooser, -2, -1, -2))).addContainerGap()));
        jPanel4Layout.setVerticalGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel4Layout.createSequentialGroup().addContainerGap().addGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel28, -2, -1, -2).addComponent(this.webBtnReset, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jScrollPane3, -2, 100, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.webBtnBreak, -2, -1, -2).addComponent(this.webBtnColour, -2, -1, -2).addComponent(this.webBtnLarge, -2, -1, -2).addComponent(this.webBtnSmall, -2, -1, -2).addGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.webBtnBold, -2, -1, -2).addComponent(this.webBtnItalic, -2, -1, -2).addComponent(this.webBtnURL, -2, -1, -2))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.colourChooser, -2, -1, -2).addGap(8, 8, 8).addComponent(this.jButtonHTML, -2, 70, -2).addGap(30, 30, 30).addGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel21, -2, -1, -2).addComponent(this.m_jTextTip, -2, -1, -2)).addContainerGap()));
        this.colourChooser.getAccessibleContext().setAccessibleName("colourChooser");
        this.jTabbedPane1.addTab(AppLocal.getIntString("label.button"), this.jPanel4);
        this.jPanel3.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        this.jPanel3.setLayout(new BorderLayout());
        this.jScrollPane1.setPreferredSize(new Dimension(700, 400));
        this.txtAttributes.setFont(new Font("Monospaced", 0, 14));
        this.txtAttributes.setLineWrap(true);
        this.txtAttributes.setWrapStyleWord(true);
        this.txtAttributes.setPreferredSize(new Dimension(600, 400));
        this.jScrollPane1.setViewportView(this.txtAttributes);
        this.jPanel3.add((Component)this.jScrollPane1, "Center");
        this.jTabbedPane1.addTab(AppLocal.getIntString("label.properties"), this.jPanel3);
        this.add((Component)this.jTabbedPane1, "Center");
    }

    private void m_jInCatalogActionPerformed(ActionEvent evt) {
        if (this.m_jInCatalog.isSelected()) {
            this.m_jCatalogOrder.setEnabled(true);
        } else {
            this.m_jCatalogOrder.setEnabled(false);
            this.m_jCatalogOrder.setText(null);
        }
    }

    private void jButtonHTMLActionPerformed(ActionEvent evt) {
        this.setButtonHTML();
    }

    private void none(ActionEvent evt) {
    }

    private void m_jNameFocusLost(FocusEvent evt) {
        this.setDisplay(this.btn);
    }

    private void m_jRefFocusLost(FocusEvent evt) {
        this.setCode();
    }

    private void webBtnBreakActionPerformed(ActionEvent evt) {
        this.btn = 1;
        this.setDisplay(this.btn);
    }

    private void webBtnColourActionPerformed(ActionEvent evt) {
        this.btn = 2;
        this.setDisplay(this.btn);
    }

    private void webBtnLargeActionPerformed(ActionEvent evt) {
        this.btn = 3;
        this.setDisplay(this.btn);
    }

    private void webBtnSmallActionPerformed(ActionEvent evt) {
        this.btn = 4;
        this.setDisplay(this.btn);
    }

    private void webBtnBoldActionPerformed(ActionEvent evt) {
        this.btn = 5;
        this.setDisplay(this.btn);
    }

    private void webBtnItalicActionPerformed(ActionEvent evt) {
        this.btn = 6;
        this.setDisplay(this.btn);
    }

    private void webBtnResetActionPerformed(ActionEvent evt) {
        this.btn = 8;
        this.setDisplay(this.btn);
    }

    private void webBtnURLActionPerformed(ActionEvent evt) {
        this.btn = 7;
        this.setDisplay(this.btn);
    }

    private void jBtnShowTransActionPerformed(ActionEvent evt) {
        String pId = this.m_oId.toString();
        if (pId != null) {
            this.stockModel = new StockTableModel(this.getProductOfName(pId));
            this.jTableProductStock.setModel(this.stockModel);
            if (this.stockModel.getRowCount() > 0) {
                this.jTableProductStock.setVisible(true);
            } else {
                this.jTableProductStock.setVisible(false);
                JOptionPane.showMessageDialog(null, "No Stock Locations for this Product", "Locations", 1);
            }
            this.resetTranxTable();
        }
    }

    private void colourChooserActionPerformed(ActionEvent evt) {
    }

    private void webBtnSupplierActionPerformed(ActionEvent evt) {
        JDialogNewSupplier dialog = JDialogNewSupplier.getDialog(this, this.appView);
        dialog.setVisible(true);
        if (dialog.getSelectedSupplier() != null) {
            try {
                this.m_SuppliersModel = new ComboBoxValModel<SupplierInfo>(this.m_sentsuppliers.list());
                this.m_jSupplier.setModel(this.m_SuppliersModel);
            }
            catch (BasicException ex) {
                Logger.getLogger(ProductsEditor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private class FieldsManager
    implements DocumentListener,
    ActionListener {
        private FieldsManager() {
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            ProductsEditor.this.calculateMargin();
            ProductsEditor.this.calculatePriceSellTax();
            ProductsEditor.this.calculateGP();
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            ProductsEditor.this.calculateMargin();
            ProductsEditor.this.calculatePriceSellTax();
            ProductsEditor.this.calculateGP();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            ProductsEditor.this.calculateMargin();
            ProductsEditor.this.calculatePriceSellTax();
            ProductsEditor.this.calculateGP();
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            ProductsEditor.this.calculateMargin();
            ProductsEditor.this.calculatePriceSellTax();
            ProductsEditor.this.calculateGP();
        }
    }

    private class PriceSellManager
    implements DocumentListener {
        private PriceSellManager() {
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            if (!ProductsEditor.this.priceselllock) {
                ProductsEditor.this.priceselllock = true;
                ProductsEditor.this.pricesell = ProductsEditor.readCurrency(ProductsEditor.this.m_jPriceSell.getText());
                ProductsEditor.this.priceselllock = false;
            }
            ProductsEditor.this.calculateMargin();
            ProductsEditor.this.calculatePriceSellTax();
            ProductsEditor.this.calculateGP();
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            if (!ProductsEditor.this.priceselllock) {
                ProductsEditor.this.priceselllock = true;
                ProductsEditor.this.pricesell = ProductsEditor.readCurrency(ProductsEditor.this.m_jPriceSell.getText());
                ProductsEditor.this.priceselllock = false;
            }
            ProductsEditor.this.calculateMargin();
            ProductsEditor.this.calculatePriceSellTax();
            ProductsEditor.this.calculateGP();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            if (!ProductsEditor.this.priceselllock) {
                ProductsEditor.this.priceselllock = true;
                ProductsEditor.this.pricesell = ProductsEditor.readCurrency(ProductsEditor.this.m_jPriceSell.getText());
                ProductsEditor.this.priceselllock = false;
            }
            ProductsEditor.this.calculateMargin();
            ProductsEditor.this.calculatePriceSellTax();
            ProductsEditor.this.calculateGP();
        }
    }

    private class PriceTaxManager
    implements DocumentListener {
        private PriceTaxManager() {
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            ProductsEditor.this.calculatePriceSellfromPST();
            ProductsEditor.this.calculateMargin();
            ProductsEditor.this.calculateGP();
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            ProductsEditor.this.calculatePriceSellfromPST();
            ProductsEditor.this.calculateMargin();
            ProductsEditor.this.calculateGP();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            ProductsEditor.this.calculatePriceSellfromPST();
            ProductsEditor.this.calculateMargin();
            ProductsEditor.this.calculateGP();
        }
    }

    private class MarginManager
    implements DocumentListener {
        private MarginManager() {
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            ProductsEditor.this.calculatePriceSellfromMargin();
            ProductsEditor.this.calculatePriceSellTax();
            ProductsEditor.this.calculateGP();
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            ProductsEditor.this.calculatePriceSellfromMargin();
            ProductsEditor.this.calculatePriceSellTax();
            ProductsEditor.this.calculateGP();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            ProductsEditor.this.calculatePriceSellfromMargin();
            ProductsEditor.this.calculatePriceSellTax();
            ProductsEditor.this.calculateGP();
        }
    }

    class StockTableModel
    extends AbstractTableModel {
        String loc = AppLocal.getIntString("label.tblProdHeaderCol1");
        String qty = AppLocal.getIntString("label.tblProdHeaderCol2");
        String min = AppLocal.getIntString("label.tblProdHeaderCol3");
        String max = AppLocal.getIntString("label.tblProdHeaderCol4");
        List<ProductStock> stockList;
        String[] columnNames = new String[]{this.loc, this.qty, this.min, this.max};

        public StockTableModel(List<ProductStock> list) {
            this.stockList = list;
        }

        @Override
        public int getColumnCount() {
            return 4;
        }

        @Override
        public int getRowCount() {
            return this.stockList.size();
        }

        @Override
        public Object getValueAt(int row, int column) {
            ProductStock productStock = this.stockList.get(row);
            switch (column) {
                case 0: {
                    return productStock.getLocation();
                }
                case 1: {
                    return productStock.getUnits();
                }
                case 2: {
                    return productStock.getMinimum();
                }
                case 3: {
                    return productStock.getMaximum();
                }
                case 4: {
                    return productStock.getProductId();
                }
            }
            return "";
        }

        @Override
        public String getColumnName(int col) {
            return this.columnNames[col];
        }
    }
}

