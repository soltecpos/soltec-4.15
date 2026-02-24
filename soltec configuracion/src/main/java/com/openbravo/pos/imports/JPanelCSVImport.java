/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.csvreader.CsvReader
 *  org.apache.commons.lang.StringUtils
 */
package com.openbravo.pos.imports;

import com.csvreader.CsvReader;
import com.openbravo.basic.BasicException;
import com.openbravo.data.gui.ComboBoxValModel;
import com.openbravo.data.loader.Datas;
import com.openbravo.data.loader.IKeyed;
import com.openbravo.data.loader.PreparedSentence;
import com.openbravo.data.loader.SentenceList;
import com.openbravo.data.loader.SerializerWriteBasicExt;
import com.openbravo.data.loader.Session;
import com.openbravo.data.user.SaveProvider;
import com.openbravo.pos.forms.AppConfig;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.AppProperties;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.forms.AppViewConnection;
import com.openbravo.pos.forms.DataLogicSales;
import com.openbravo.pos.forms.DataLogicSystem;
import com.openbravo.pos.forms.JPanelView;
import com.openbravo.pos.inventory.TaxCategoryInfo;
import com.openbravo.pos.sales.TaxesLogic;
import com.openbravo.pos.suppliers.SupplierInfo;
import com.openbravo.pos.ticket.CategoryInfo;
import com.openbravo.pos.ticket.ProductInfoExt;
import com.openbravo.pos.ticket.TaxInfo;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
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
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.SwingWorker;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.commons.lang.StringUtils;

public class JPanelCSVImport
extends JPanel
implements JPanelView {
    private ArrayList<String> Headers = new ArrayList();
    private Session s;
    private Connection con;
    private String csvFileName;
    private Double dOriginalRate = 0.0;
    private String dCategory;
    private String dSupplier;
    private String csvMessage = "";
    private CsvReader products;
    private double oldSellPrice = 0.0;
    private double oldBuyPrice = 0.0;
    private int currentRecord;
    private int rowCount = 0;
    private String last_folder;
    private File config_file;
    private static String category_default = "[ USE DEFAULT CATEGORY ]";
    private static String reject_bad_category = "[ REJECT ITEMS WITH BAD CATEGORIES ]";
    private static String supplier_default = "[ USE DEFAULT SUPPLIER ]";
    private static String reject_bad_supplier = "[ REJECT ITEMS WITH BAD SUPPLIER ]";
    private DataLogicSales m_dlSales;
    private DataLogicSystem m_dlSystem;
    protected SaveProvider<Object[]> spr;
    private String Category;
    private String categoryName;
    private String categoryParentid;
    private Integer categoryCatorder;
    private SentenceList<CategoryInfo> m_sentcat;
    private ComboBoxValModel<Object> m_CategoryModel;
    private HashMap<String, String> cat_list = new HashMap();
    private ArrayList<String> badCategories = new ArrayList();
    private String productReference;
    private String productBarcode;
    private String productBarcodetype;
    private String productName;
    private Double productBuyPrice;
    private Double productSellPrice;
    private String productTax;
    private String Supplier;
    private String supplierName;
    private SentenceList<SupplierInfo> m_sentsupp;
    private ComboBoxValModel<Object> m_SupplierModel;
    private HashMap<String, String> supp_list = new HashMap();
    private ArrayList<String> badSuppliers = new ArrayList();
    private SentenceList<TaxCategoryInfo> taxcatsent;
    private ComboBoxValModel<TaxCategoryInfo> taxcatmodel;
    private SentenceList<TaxInfo> taxsent;
    private TaxesLogic taxeslogic;
    private DocumentListener documentListener;
    private ProductInfoExt prodInfo;
    private String recordType = null;
    private int newRecords = 0;
    private int invalidRecords = 0;
    private int priceUpdates = 0;
    private int missingData = 0;
    private int noChanges = 0;
    private int badPrice = 0;
    private double dTaxRate;
    private Integer progress = 0;
    private JCheckBox jCheckInCatalogue;
    private JCheckBox jCheckSellIncTax;
    private JComboBox<String> jComboBarcode;
    private JComboBox<String> jComboBuy;
    private JComboBox<String> jComboCategory;
    private JComboBox<Object> jComboDefaultCategory;
    private JComboBox<Object> jComboDefaultSupplier;
    private JComboBox<String> jComboName;
    private JComboBox<String> jComboReference;
    private JComboBox<String> jComboSell;
    private JComboBox<String> jComboSeparator;
    private JComboBox<String> jComboSupplier;
    private JComboBox<String> jComboTax;
    private JPanel jFileChooserPanel;
    private JTextField jFileName;
    private JButton jHeaderRead;
    private JButton jImport;
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
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JLabel jLabel8;
    private JLabel jLabel9;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JTextField jTextBadCats;
    private JTextField jTextBadPrice;
    private JTextField jTextInvalid;
    private JTextField jTextMissing;
    private JTextField jTextNew;
    private JTextField jTextNoChange;
    private JTextField jTextRecords;
    private JTextField jTextUpdate;
    private JLabel jTextUpdates;
    private JButton jbtnFileChoose;
    private JButton jbtnReset;
    private JProgressBar webPBar;

    public JPanelCSVImport(AppView oApp) {
        this(oApp.getProperties());
    }

    public JPanelCSVImport(AppProperties props) {
        this.initComponents();
        try {
            this.s = AppViewConnection.createSession(props);
            this.con = this.s.getConnection();
        }
        catch (BasicException | SQLException exception) {
            // empty catch block
        }
        this.m_dlSales = new DataLogicSales();
        this.m_dlSales.init(this.s);
        this.m_dlSystem = new DataLogicSystem();
        this.m_dlSystem.init(this.s);
        this.spr = new SaveProvider<Object[]>(this.m_dlSales.getProductCatUpdate(), this.m_dlSales.getProductCatInsert(), this.m_dlSales.getProductCatDelete());
        this.last_folder = props.getProperty("CSV.last_folder");
        this.config_file = props.getConfigFile();
        this.jFileName.getDocument().addDocumentListener(this.documentListener);
        this.documentListener = new DocumentListener(){

            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
                JPanelCSVImport.this.jHeaderRead.setEnabled(true);
            }

            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                if (!"".equals(JPanelCSVImport.this.jFileName.getText().trim())) {
                    JPanelCSVImport.this.jHeaderRead.setEnabled(true);
                }
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                if (JPanelCSVImport.this.jFileName.getText().trim().equals("")) {
                    JPanelCSVImport.this.jHeaderRead.setEnabled(false);
                }
            }
        };
        this.jFileName.getDocument().addDocumentListener(this.documentListener);
    }

    private void GetheadersFromFile(String CSVFileName) throws IOException {
        File f = new File(CSVFileName);
        if (f.exists()) {
            this.products = new CsvReader(CSVFileName);
            this.products.setDelimiter(((String)this.jComboSeparator.getSelectedItem()).charAt(0));
            this.products.readHeaders();
            if (this.products.getHeaderCount() < 5) {
                JOptionPane.showMessageDialog(null, "Incorrect header in your source file", "Header Error", 2);
                this.products.close();
                return;
            }
            this.rowCount = 0;
            this.Headers.clear();
            this.Headers.add("");
            this.jComboName.addItem("");
            this.jComboReference.addItem("");
            this.jComboBarcode.addItem("");
            this.jComboBuy.addItem("");
            this.jComboSell.addItem("");
            this.jComboTax.addItem("");
            this.jComboCategory.addItem("");
            this.jComboSupplier.addItem("");
            for (int i = 0; i < this.products.getHeaderCount(); ++i) {
                this.jComboName.addItem(this.products.getHeader(i));
                this.jComboReference.addItem(this.products.getHeader(i));
                this.jComboBarcode.addItem(this.products.getHeader(i));
                this.jComboBuy.addItem(this.products.getHeader(i));
                this.jComboSell.addItem(this.products.getHeader(i));
                this.jComboTax.addItem(this.products.getHeader(i));
                this.jComboCategory.addItem(this.products.getHeader(i));
                this.jComboSupplier.addItem(this.products.getHeader(i));
                this.Headers.add(this.products.getHeader(i));
            }
            this.enableCheckBoxes();
            while (this.products.readRecord()) {
                ++this.rowCount;
            }
            this.jTextRecords.setText(Long.toString(this.rowCount));
            this.products.close();
        } else {
            JOptionPane.showMessageDialog(null, "Unable to locate " + CSVFileName, "File not found", 2);
        }
    }

    private void enableCheckBoxes() {
        this.jHeaderRead.setEnabled(false);
        this.jImport.setEnabled(false);
        this.jbtnReset.setEnabled(true);
        this.jComboReference.setEnabled(true);
        this.jComboName.setEnabled(true);
        this.jComboBarcode.setEnabled(true);
        this.jComboBuy.setEnabled(true);
        this.jComboSell.setEnabled(true);
        this.jComboTax.setEnabled(true);
        this.jComboCategory.setEnabled(true);
        this.jComboDefaultCategory.setEnabled(true);
        this.jComboSupplier.setEnabled(true);
        this.jComboDefaultSupplier.setEnabled(true);
        this.jCheckInCatalogue.setEnabled(true);
        this.jCheckSellIncTax.setEnabled(true);
    }

    private void setWorker() {
        this.progress = 0;
        this.webPBar.setStringPainted(true);
        SwingWorker<Integer, Integer> pbWorker = new SwingWorker<Integer, Integer>(){

            @Override
            protected final Integer doInBackground() throws Exception {
                while (JPanelCSVImport.this.progress >= 0 && JPanelCSVImport.this.progress < 100) {
                    Thread.sleep(50L);
                    this.publish(JPanelCSVImport.this.progress);
                }
                this.publish(100);
                this.done();
                return 100;
            }

            @Override
            protected final void process(List<Integer> chunks) {
                JPanelCSVImport.this.webPBar.setValue(chunks.get(0));
                if (JPanelCSVImport.this.progress > 100) {
                    JPanelCSVImport.this.progress = 100;
                    JPanelCSVImport.this.webPBar.setString("Imported 100%");
                } else {
                    JPanelCSVImport.this.webPBar.setString("Imported " + JPanelCSVImport.this.progress + "%");
                }
            }
        };
        pbWorker.execute();
    }

    private void ImportCsvFile(String CSVFileName) throws IOException {
        File f = new File(CSVFileName);
        if (f.exists()) {
            this.webPBar.setString("Starting...");
            this.webPBar.setVisible(true);
            this.jImport.setEnabled(false);
            this.products = new CsvReader(CSVFileName);
            this.products.setDelimiter(((String)this.jComboSeparator.getSelectedItem()).charAt(0));
            this.products.readHeaders();
            this.currentRecord = 0;
            block15: while (this.products.readRecord()) {
                this.productReference = this.products.get((String)this.jComboReference.getSelectedItem());
                this.productName = this.products.get((String)this.jComboName.getSelectedItem());
                this.productBarcode = this.products.get((String)this.jComboBarcode.getSelectedItem());
                String BuyPrice = this.products.get((String)this.jComboBuy.getSelectedItem());
                String SellPrice = this.products.get((String)this.jComboSell.getSelectedItem());
                this.productTax = this.products.get((String)this.jComboTax.getSelectedItem());
                this.Category = this.products.get((String)this.jComboCategory.getSelectedItem());
                this.Supplier = this.products.get((String)this.jComboSupplier.getSelectedItem());
                ++this.currentRecord;
                this.progress = this.currentRecord;
                BuyPrice = StringUtils.replaceChars((String)BuyPrice, (String)"$", (String)"");
                SellPrice = StringUtils.replaceChars((String)SellPrice, (String)"$", (String)"");
                BuyPrice = StringUtils.replaceChars((String)BuyPrice, (String)"\u00c2\u00a3", (String)"");
                SellPrice = StringUtils.replaceChars((String)SellPrice, (String)"\u00c2\u00a3", (String)"");
                BuyPrice = StringUtils.replaceChars((String)BuyPrice, (String)"\u00e2\u201a\u00ac", (String)"");
                SellPrice = StringUtils.replaceChars((String)SellPrice, (String)"\u00e2\u201a\u00ac", (String)"");
                this.dCategory = this.getCategory();
                this.csvMessage = "Bad Category".equals(this.dCategory) ? "Bad category details" : "Missing data or Invalid number";
                this.dSupplier = this.getSupplier();
                this.csvMessage = "Bad Supplier".equals(this.dSupplier) ? "Bad Supplier details" : "Missing data or Invalid number";
                this.productBuyPrice = this.validateNumber(BuyPrice) != false ? Double.valueOf(Double.parseDouble(BuyPrice)) : null;
                this.productSellPrice = this.validateNumber(SellPrice) != false ? this.getSellPrice(SellPrice) : null;
                if ("".equals(this.productReference) | "".equals(this.productName) | "".equals(this.productBarcode) | "".equals(BuyPrice) | "".equals(SellPrice) | "".equals(this.productTax) | this.productBuyPrice == null | this.productSellPrice == null | "Bad Category".equals(this.dCategory)) {
                    if (this.productBuyPrice == null | this.productSellPrice == null) {
                        ++this.badPrice;
                    } else {
                        ++this.missingData;
                    }
                    this.createCSVEntry(this.csvMessage, null, null);
                    continue;
                }
                switch (this.recordType = this.getRecord()) {
                    case "new": {
                        this.createProduct("new");
                        ++this.newRecords;
                        this.createCSVEntry("New product", null, null);
                        continue block15;
                    }
                    case "name error": 
                    case "barcode error": 
                    case "reference error": 
                    case "Duplicate Reference found.": 
                    case "Duplicate Barcode found.": 
                    case "Duplicate Description found.": 
                    case "tax error": 
                    case "Exception": {
                        ++this.invalidRecords;
                        this.createCSVEntry(this.recordType, null, null);
                        continue block15;
                    }
                }
                this.updateRecord(this.recordType);
            }
            this.products.close();
        } else {
            JOptionPane.showMessageDialog(null, "Unable to locate " + CSVFileName, "File not found", 2);
        }
        this.jTextNew.setText(Integer.toString(this.newRecords));
        this.jTextUpdate.setText(Integer.toString(this.priceUpdates));
        this.jTextInvalid.setText(Integer.toString(this.invalidRecords));
        this.jTextMissing.setText(Integer.toString(this.missingData));
        this.jTextNoChange.setText(Integer.toString(this.noChanges));
        this.jTextBadPrice.setText(Integer.toString(this.badPrice));
        if (this.badCategories.size() == 1 && this.badCategories.get(0) == "") {
            this.jTextBadCats.setText("0");
        } else {
            this.jTextBadCats.setText(Integer.toString(this.badCategories.size()));
        }
        JOptionPane.showMessageDialog(null, "Import Complete", "Imported", 2);
        this.progress = 100;
        this.webPBar.setValue(this.progress);
        this.webPBar.setString("Imported" + this.progress);
    }

    private Boolean validateNumber(String testString) {
        try {
            Double res = Double.parseDouble(testString);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }

    private String getCategory() {
        String cat;
        if (this.jComboCategory.getSelectedItem() != category_default && (cat = this.cat_list.get(this.Category)) != null) {
            return cat;
        }
        if (!this.Category.equals("")) {
            Object[] newcat = new Object[]{UUID.randomUUID().toString(), this.Category, true};
            try {
                this.m_dlSales.createCategory(newcat);
                this.cat_list = new HashMap();
                for (CategoryInfo category : this.m_sentcat.list()) {
                    this.m_CategoryModel.setSelectedItem(category);
                    this.cat_list.put(((Object)category).toString(), this.m_CategoryModel.getSelectedKey().toString());
                }
                String cat2 = this.cat_list.get(this.Category);
                return cat2;
            }
            catch (BasicException ex) {
                Logger.getLogger(JPanelCSVImport.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (!this.badCategories.contains(this.Category)) {
            this.badCategories.add(this.Category.trim());
        }
        return this.jComboDefaultCategory.getSelectedItem() == reject_bad_category ? "Bad Category" : this.cat_list.get(this.m_CategoryModel.getSelectedText());
    }

    private String getSupplier() {
        String supp;
        if (this.jComboSupplier.getSelectedItem() != supplier_default && (supp = this.supp_list.get(this.Supplier)) != null) {
            return supp;
        }
        if (!this.Supplier.equals("")) {
            Object[] newsupp = new Object[]{UUID.randomUUID().toString(), this.Supplier, this.Supplier, true};
            try {
                this.m_dlSales.createSupplier(newsupp);
                this.supp_list = new HashMap();
                for (SupplierInfo supplier : this.m_sentsupp.list()) {
                    this.m_SupplierModel.setSelectedItem(supplier);
                    this.supp_list.put(((Object)supplier).toString(), this.m_SupplierModel.getSelectedKey().toString());
                }
                String supp2 = this.supp_list.get(this.Supplier);
                return supp2;
            }
            catch (BasicException ex) {
                Logger.getLogger(JPanelCSVImport.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (!this.badSuppliers.contains(this.Supplier)) {
            this.badSuppliers.add(this.Supplier.trim());
        }
        return this.jComboDefaultSupplier.getSelectedItem() == reject_bad_supplier ? "Bad Supplier" : this.supp_list.get(this.m_SupplierModel.getSelectedText());
    }

    private Double getSellPrice(String pSellPrice) {
        this.dTaxRate = this.taxeslogic.getTaxRate(this.productTax);
        if (this.jCheckSellIncTax.isSelected()) {
            this.productSellPrice = Double.parseDouble(pSellPrice) / (1.0 + this.dTaxRate);
            return this.productSellPrice;
        }
        return Double.parseDouble(pSellPrice);
    }

    private void updateRecord(String pID) {
        this.prodInfo = new ProductInfoExt();
        try {
            this.prodInfo = this.m_dlSales.getProductInfo(pID);
            this.dOriginalRate = this.taxeslogic.getTaxRate(this.prodInfo.getTaxCategoryID());
            this.dCategory = this.cat_list.get(this.prodInfo.getCategoryID()) == null ? this.prodInfo.getCategoryID() : this.cat_list.get(this.prodInfo.getCategoryID());
            this.oldBuyPrice = this.prodInfo.getPriceBuy();
            this.oldSellPrice = this.prodInfo.getPriceSell();
            this.productSellPrice = this.productSellPrice * (1.0 + this.dOriginalRate);
            String string = this.dSupplier = this.supp_list.get(this.prodInfo.getSupplierID()) == null ? this.prodInfo.getSupplierID() : this.supp_list.get(this.prodInfo.getSupplierID());
            if (this.oldBuyPrice != this.productBuyPrice || this.oldSellPrice != this.productSellPrice) {
                this.createCSVEntry("Updated Price Details", this.oldBuyPrice, this.jCheckSellIncTax.isSelected() ? this.oldSellPrice * (1.0 + this.dOriginalRate) : this.oldSellPrice);
                this.createProduct("update");
                ++this.priceUpdates;
            } else {
                ++this.noChanges;
            }
        }
        catch (BasicException ex) {
            Logger.getLogger(JPanelCSVImport.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getTitle() {
        return AppLocal.getIntString("Menu.CSVImport");
    }

    @Override
    public JComponent getComponent() {
        return this;
    }

    @Override
    public void activate() throws BasicException {
        this.taxsent = this.m_dlSales.getTaxList();
        this.taxeslogic = new TaxesLogic(this.taxsent.list());
        this.taxcatsent = this.m_dlSales.getTaxCategoriesList();
        this.taxcatmodel = new ComboBoxValModel<TaxCategoryInfo>(this.taxcatsent.list());
        this.m_sentcat = this.m_dlSales.getCategoriesList();
        this.m_CategoryModel = new ComboBoxValModel(new ArrayList(this.m_sentcat.list()));
        this.m_CategoryModel.add(reject_bad_category);
        this.jComboDefaultCategory.setModel(this.m_CategoryModel);
        this.cat_list = new HashMap();
        for (CategoryInfo categoryInfo : this.m_sentcat.list()) {
            this.m_CategoryModel.setSelectedItem(categoryInfo);
            this.cat_list.put(((Object)categoryInfo).toString(), this.m_CategoryModel.getSelectedKey().toString());
        }
        this.m_sentsupp = this.m_dlSales.getSuppList();
        this.m_SupplierModel = new ComboBoxValModel(new ArrayList(this.m_sentsupp.list()));
        this.m_SupplierModel.add(reject_bad_supplier);
        this.jComboDefaultSupplier.setModel(this.m_SupplierModel);
        this.supp_list = new HashMap();
        for (IKeyed iKeyed : this.m_sentsupp.list()) {
            this.m_SupplierModel.setSelectedItem(iKeyed);
            this.supp_list.put(iKeyed.toString(), this.m_SupplierModel.getSelectedKey().toString());
        }
        this.m_CategoryModel.setSelectedItem(null);
        this.m_SupplierModel.setSelectedItem(null);
        this.jComboSeparator.removeAllItems();
        this.jComboSeparator.addItem(",");
        this.jComboSeparator.addItem(";");
        this.jComboSeparator.addItem("~");
        this.jComboSeparator.addItem("^");
    }

    public void resetFields() {
        this.jComboReference.removeAllItems();
        this.jComboReference.setEnabled(false);
        this.jComboName.removeAllItems();
        this.jComboName.setEnabled(false);
        this.jComboBarcode.removeAllItems();
        this.jComboBarcode.setEnabled(false);
        this.jComboBuy.removeAllItems();
        this.jComboBuy.setEnabled(false);
        this.jComboSell.removeAllItems();
        this.jComboSell.setEnabled(false);
        this.jComboTax.removeAllItems();
        this.jComboTax.setEnabled(false);
        this.jComboCategory.removeAllItems();
        this.jComboCategory.setEnabled(false);
        this.jComboDefaultCategory.setEnabled(false);
        this.jComboSupplier.removeAllItems();
        this.jComboSupplier.setEnabled(false);
        this.jComboDefaultSupplier.setEnabled(false);
        this.jImport.setEnabled(false);
        this.jbtnReset.setEnabled(true);
        this.jHeaderRead.setEnabled(false);
        this.jCheckInCatalogue.setSelected(true);
        this.jCheckInCatalogue.setEnabled(true);
        this.jCheckSellIncTax.setSelected(true);
        this.jCheckSellIncTax.setEnabled(true);
        this.jFileName.setText(null);
        this.csvFileName = "";
        this.jTextNew.setText("");
        this.jTextUpdate.setText("");
        this.jTextInvalid.setText("");
        this.jTextMissing.setText("");
        this.jTextNoChange.setText("");
        this.jTextRecords.setText("");
        this.jTextBadPrice.setText("");
        this.jTextBadCats.setText("");
        this.progress = 0;
        this.Headers.clear();
        this.newRecords = 0;
        this.invalidRecords = 0;
        this.priceUpdates = 0;
        this.missingData = 0;
        this.noChanges = 0;
        this.badPrice = 0;
    }

    public void checkFieldMapping() {
        if (this.jComboReference.getSelectedItem() != "" & this.jComboName.getSelectedItem() != "" & this.jComboBarcode.getSelectedItem() != "" & this.jComboBuy.getSelectedItem() != "" & this.jComboSell.getSelectedItem() != "" & this.jComboTax.getSelectedItem() != "" & this.jComboCategory.getSelectedItem() != "" & this.m_CategoryModel.getSelectedText() != null) {
            this.jImport.setEnabled(true);
            this.jbtnReset.setEnabled(true);
        } else {
            this.jImport.setEnabled(false);
            this.jbtnReset.setEnabled(false);
        }
    }

    @Override
    public boolean deactivate() {
        this.resetFields();
        return true;
    }

    public void createCategory(String cType) {
        Object[] mycat = new Object[]{UUID.randomUUID().toString(), this.categoryName, this.categoryParentid, null, "<html><center><h4>" + this.categoryName, true, this.categoryCatorder};
        try {
            if ("new".equals(cType)) {
                this.spr.insertData(mycat);
            } else {
                this.spr.updateData(mycat);
            }
        }
        catch (BasicException ex) {
            Logger.getLogger(JPanelCSVImport.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void createSupplier(String sType) {
        Object[] mysupp = new Object[]{UUID.randomUUID().toString(), this.supplierName, this.supplierName, true};
        try {
            if ("new".equals(sType)) {
                this.spr.insertData(mysupp);
            } else {
                this.spr.updateData(mysupp);
            }
        }
        catch (BasicException ex) {
            Logger.getLogger(JPanelCSVImport.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void createProduct(String pType) {
        Object[] myprod = new Object[]{"new".equals(pType) ? UUID.randomUUID().toString() : this.prodInfo.getID(), this.productReference, this.productBarcode, null, this.productName, this.productBuyPrice, this.productSellPrice, this.dCategory, this.productTax, null, 0.0, 0.0, null, false, false, false, false, false, false, null, "<html><center>" + this.productName, false, false, "<html><center><h4>" + this.productName, false, 0.0, "1", this.dSupplier, "0", this.jCheckInCatalogue.isSelected(), null};
        try {
            if ("new".equals(pType)) {
                this.spr.insertData(myprod);
                this.addStockCurrent("0", myprod[0].toString(), 0.0);
                this.webPBar.setString("Adding record " + this.progress);
            } else {
                this.spr.updateData(myprod);
                this.webPBar.setString("Updating record " + this.progress);
            }
        }
        catch (BasicException ex) {
            Logger.getLogger(JPanelCSVImport.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addStockCurrent(String LocationID, String ProductID, Double Units) throws BasicException {
        Object[] values = new Object[]{"0", ProductID, (double)Units};
        PreparedSentence sentence = new PreparedSentence(this.s, "INSERT INTO stockcurrent ( LOCATION, PRODUCT, UNITS) VALUES (?, ?, ?)", new SerializerWriteBasicExt(new Datas[]{Datas.STRING, Datas.STRING, Datas.DOUBLE}, new int[]{0, 1, 2}));
        sentence.exec(values);
    }

    public void createCSVEntry(String csvError, Double previousBuy, Double previousSell) {
        Object[] myprod = new Object[]{UUID.randomUUID().toString(), Integer.toString(this.currentRecord), csvError, this.productReference, this.productBarcode, this.productName, this.productBuyPrice, this.productSellPrice, previousBuy, previousSell, this.Category, this.productTax, this.Supplier};
        try {
            this.m_dlSystem.execAddCSVEntry(myprod);
        }
        catch (BasicException ex) {
            Logger.getLogger(JPanelCSVImport.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getRecord() {
        Object[] myprod = new Object[]{this.productReference, this.productBarcode, this.productName};
        try {
            return this.m_dlSystem.getProductRecordType(myprod);
        }
        catch (BasicException ex) {
            Logger.getLogger(JPanelCSVImport.class.getName()).log(Level.SEVERE, null, ex);
            return "Exception";
        }
    }

    private void initComponents() {
        this.jFileChooserPanel = new JPanel();
        this.jLabel1 = new JLabel();
        this.jFileName = new JTextField();
        this.jbtnFileChoose = new JButton();
        this.jPanel1 = new JPanel();
        this.jComboReference = new JComboBox();
        this.jComboBarcode = new JComboBox();
        this.jComboName = new JComboBox();
        this.jComboBuy = new JComboBox();
        this.jComboSell = new JComboBox();
        this.jComboDefaultCategory = new JComboBox();
        this.jComboTax = new JComboBox();
        this.jLabel3 = new JLabel();
        this.jLabel4 = new JLabel();
        this.jLabel5 = new JLabel();
        this.jLabel10 = new JLabel();
        this.jLabel11 = new JLabel();
        this.jLabel7 = new JLabel();
        this.jCheckInCatalogue = new JCheckBox();
        this.jLabel8 = new JLabel();
        this.jCheckSellIncTax = new JCheckBox();
        this.jLabel12 = new JLabel();
        this.jComboCategory = new JComboBox();
        this.jLabel20 = new JLabel();
        this.jImport = new JButton();
        this.jLabel6 = new JLabel();
        this.jbtnReset = new JButton();
        this.jComboSupplier = new JComboBox();
        this.jLabel21 = new JLabel();
        this.jLabel22 = new JLabel();
        this.jComboDefaultSupplier = new JComboBox();
        this.jLabel17 = new JLabel();
        this.jLabel18 = new JLabel();
        this.jHeaderRead = new JButton();
        this.jPanel2 = new JPanel();
        this.jLabel9 = new JLabel();
        this.jLabel14 = new JLabel();
        this.jLabel16 = new JLabel();
        this.jTextUpdates = new JLabel();
        this.jLabel2 = new JLabel();
        this.jLabel15 = new JLabel();
        this.jLabel13 = new JLabel();
        this.jTextRecords = new JTextField();
        this.jTextNew = new JTextField();
        this.jTextInvalid = new JTextField();
        this.jTextUpdate = new JTextField();
        this.jTextMissing = new JTextField();
        this.jTextBadPrice = new JTextField();
        this.jTextNoChange = new JTextField();
        this.jLabel19 = new JLabel();
        this.jTextBadCats = new JTextField();
        this.jComboSeparator = new JComboBox();
        this.webPBar = new JProgressBar();
        this.setFont(new Font("Arial", 0, 14));
        this.setOpaque(false);
        this.setPreferredSize(new Dimension(750, 500));
        this.jLabel1.setFont(new Font("Arial", 0, 14));
        ResourceBundle bundle = ResourceBundle.getBundle("pos_messages");
        this.jLabel1.setText(bundle.getString("label.csvfile"));
        this.jLabel1.setPreferredSize(new Dimension(100, 30));
        this.jFileName.setFont(new Font("Arial", 0, 14));
        this.jFileName.setPreferredSize(new Dimension(400, 30));
        this.jFileName.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPanelCSVImport.this.jFileNameActionPerformed(evt);
            }
        });
        this.jbtnFileChoose.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/fileopen.png")));
        this.jbtnFileChoose.setMaximumSize(new Dimension(64, 32));
        this.jbtnFileChoose.setMinimumSize(new Dimension(64, 32));
        this.jbtnFileChoose.setPreferredSize(new Dimension(80, 45));
        this.jbtnFileChoose.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPanelCSVImport.this.jbtnFileChooseActionPerformed(evt);
            }
        });
        GroupLayout jFileChooserPanelLayout = new GroupLayout(this.jFileChooserPanel);
        this.jFileChooserPanel.setLayout(jFileChooserPanelLayout);
        jFileChooserPanelLayout.setHorizontalGroup(jFileChooserPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jFileChooserPanelLayout.createSequentialGroup().addComponent(this.jLabel1, -1, -1, Short.MAX_VALUE).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jFileName, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jbtnFileChoose, -2, -1, -2).addGap(120, 120, 120)));
        jFileChooserPanelLayout.setVerticalGroup(jFileChooserPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jFileChooserPanelLayout.createSequentialGroup().addGroup(jFileChooserPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1, -2, -1, -2).addComponent(this.jFileName, -2, -1, -2)).addContainerGap()).addComponent(this.jbtnFileChoose, -1, -1, Short.MAX_VALUE));
        this.jPanel1.setFont(new Font("Arial", 0, 14));
        this.jPanel1.setPreferredSize(new Dimension(430, 400));
        this.jComboReference.setFont(new Font("Arial", 0, 14));
        this.jComboReference.setEnabled(false);
        this.jComboReference.setMinimumSize(new Dimension(32, 25));
        this.jComboReference.setPreferredSize(new Dimension(300, 30));
        this.jComboReference.addItemListener(new ItemListener(){

            @Override
            public void itemStateChanged(ItemEvent evt) {
                JPanelCSVImport.this.jComboReferenceItemStateChanged(evt);
            }
        });
        this.jComboReference.addFocusListener(new FocusAdapter(){

            @Override
            public void focusGained(FocusEvent evt) {
                JPanelCSVImport.this.jComboReferenceFocusGained(evt);
            }
        });
        this.jComboBarcode.setFont(new Font("Arial", 0, 14));
        this.jComboBarcode.setEnabled(false);
        this.jComboBarcode.setMinimumSize(new Dimension(32, 25));
        this.jComboBarcode.setPreferredSize(new Dimension(300, 30));
        this.jComboBarcode.addItemListener(new ItemListener(){

            @Override
            public void itemStateChanged(ItemEvent evt) {
                JPanelCSVImport.this.jComboBarcodeItemStateChanged(evt);
            }
        });
        this.jComboBarcode.addFocusListener(new FocusAdapter(){

            @Override
            public void focusGained(FocusEvent evt) {
                JPanelCSVImport.this.jComboBarcodeFocusGained(evt);
            }
        });
        this.jComboName.setFont(new Font("Arial", 0, 14));
        this.jComboName.setEnabled(false);
        this.jComboName.setMinimumSize(new Dimension(32, 25));
        this.jComboName.setPreferredSize(new Dimension(300, 30));
        this.jComboName.addItemListener(new ItemListener(){

            @Override
            public void itemStateChanged(ItemEvent evt) {
                JPanelCSVImport.this.jComboNameItemStateChanged(evt);
            }
        });
        this.jComboName.addFocusListener(new FocusAdapter(){

            @Override
            public void focusGained(FocusEvent evt) {
                JPanelCSVImport.this.jComboNameFocusGained(evt);
            }
        });
        this.jComboBuy.setFont(new Font("Arial", 0, 14));
        this.jComboBuy.setModel(new DefaultComboBoxModel<String>(new String[]{""}));
        this.jComboBuy.setSelectedIndex(-1);
        this.jComboBuy.setEnabled(false);
        this.jComboBuy.setMinimumSize(new Dimension(32, 25));
        this.jComboBuy.setPreferredSize(new Dimension(300, 30));
        this.jComboBuy.addItemListener(new ItemListener(){

            @Override
            public void itemStateChanged(ItemEvent evt) {
                JPanelCSVImport.this.jComboBuyItemStateChanged(evt);
            }
        });
        this.jComboBuy.addFocusListener(new FocusAdapter(){

            @Override
            public void focusGained(FocusEvent evt) {
                JPanelCSVImport.this.jComboBuyFocusGained(evt);
            }
        });
        this.jComboSell.setFont(new Font("Arial", 0, 14));
        this.jComboSell.setEnabled(false);
        this.jComboSell.setMinimumSize(new Dimension(32, 25));
        this.jComboSell.setPreferredSize(new Dimension(300, 30));
        this.jComboSell.addItemListener(new ItemListener(){

            @Override
            public void itemStateChanged(ItemEvent evt) {
                JPanelCSVImport.this.jComboSellItemStateChanged(evt);
            }
        });
        this.jComboSell.addFocusListener(new FocusAdapter(){

            @Override
            public void focusGained(FocusEvent evt) {
                JPanelCSVImport.this.jComboSellFocusGained(evt);
            }
        });
        this.jComboDefaultCategory.setFont(new Font("Arial", 0, 14));
        this.jComboDefaultCategory.setEnabled(false);
        this.jComboDefaultCategory.setMinimumSize(new Dimension(32, 25));
        this.jComboDefaultCategory.setPreferredSize(new Dimension(300, 30));
        this.jComboDefaultCategory.addItemListener(new ItemListener(){

            @Override
            public void itemStateChanged(ItemEvent evt) {
                JPanelCSVImport.this.jComboDefaultCategoryItemStateChanged(evt);
            }
        });
        this.jComboDefaultCategory.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPanelCSVImport.this.jComboDefaultCategoryActionPerformed(evt);
            }
        });
        this.jComboTax.setFont(new Font("Arial", 0, 14));
        this.jComboTax.setEnabled(false);
        this.jComboTax.setPreferredSize(new Dimension(300, 30));
        this.jComboTax.addItemListener(new ItemListener(){

            @Override
            public void itemStateChanged(ItemEvent evt) {
                JPanelCSVImport.this.jComboTaxItemStateChanged(evt);
            }
        });
        this.jComboTax.addFocusListener(new FocusAdapter(){

            @Override
            public void focusGained(FocusEvent evt) {
                JPanelCSVImport.this.jComboTaxFocusGained(evt);
            }
        });
        this.jLabel3.setFont(new Font("Arial", 0, 14));
        this.jLabel3.setText(bundle.getString("label.prodref"));
        this.jLabel3.setPreferredSize(new Dimension(100, 30));
        this.jLabel4.setFont(new Font("Arial", 0, 14));
        this.jLabel4.setText(bundle.getString("label.prodbarcode"));
        this.jLabel4.setPreferredSize(new Dimension(100, 30));
        this.jLabel5.setFont(new Font("Arial", 0, 14));
        this.jLabel5.setText(bundle.getString("label.prodname"));
        this.jLabel5.setPreferredSize(new Dimension(100, 30));
        this.jLabel10.setFont(new Font("Arial", 0, 14));
        this.jLabel10.setText(bundle.getString("label.prodpricebuy"));
        this.jLabel10.setPreferredSize(new Dimension(100, 30));
        this.jLabel11.setFont(new Font("Arial", 0, 14));
        this.jLabel11.setText(bundle.getString("label.prodcategory"));
        this.jLabel11.setPreferredSize(new Dimension(100, 30));
        this.jLabel7.setFont(new Font("Arial", 0, 14));
        this.jLabel7.setText(bundle.getString("label.prodtaxcode"));
        this.jLabel7.setPreferredSize(new Dimension(100, 30));
        this.jCheckInCatalogue.setFont(new Font("Arial", 0, 14));
        this.jCheckInCatalogue.setEnabled(false);
        this.jCheckInCatalogue.setPreferredSize(new Dimension(30, 30));
        this.jLabel8.setFont(new Font("Arial", 0, 14));
        this.jLabel8.setHorizontalAlignment(2);
        this.jLabel8.setText(bundle.getString("label.prodincatalog"));
        this.jLabel8.setPreferredSize(new Dimension(100, 30));
        this.jCheckSellIncTax.setFont(new Font("Arial", 0, 14));
        this.jCheckSellIncTax.setEnabled(false);
        this.jCheckSellIncTax.setPreferredSize(new Dimension(30, 30));
        this.jLabel12.setFont(new Font("Arial", 0, 14));
        this.jLabel12.setHorizontalAlignment(2);
        this.jLabel12.setText(bundle.getString("label.csvsellingintax"));
        this.jLabel12.setPreferredSize(new Dimension(200, 30));
        this.jComboCategory.setFont(new Font("Arial", 0, 14));
        this.jComboCategory.setEnabled(false);
        this.jComboCategory.setMinimumSize(new Dimension(32, 25));
        this.jComboCategory.setName("");
        this.jComboCategory.setPreferredSize(new Dimension(300, 30));
        this.jComboCategory.addItemListener(new ItemListener(){

            @Override
            public void itemStateChanged(ItemEvent evt) {
                JPanelCSVImport.this.jComboCategoryItemStateChanged(evt);
            }
        });
        this.jComboCategory.addFocusListener(new FocusAdapter(){

            @Override
            public void focusGained(FocusEvent evt) {
                JPanelCSVImport.this.jComboCategoryFocusGained(evt);
            }
        });
        this.jLabel20.setFont(new Font("Arial", 0, 14));
        this.jLabel20.setText(bundle.getString("label.prodpricesell"));
        this.jLabel20.setPreferredSize(new Dimension(100, 30));
        this.jImport.setFont(new Font("Arial", 0, 14));
        this.jImport.setText(bundle.getString("label.csvimpostbtn"));
        this.jImport.setEnabled(false);
        this.jImport.setPreferredSize(new Dimension(110, 45));
        this.jImport.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPanelCSVImport.this.jImportActionPerformed(evt);
            }
        });
        this.jLabel6.setFont(new Font("Arial", 0, 14));
        this.jLabel6.setText(bundle.getString("label.proddefaultcategory"));
        this.jLabel6.setPreferredSize(new Dimension(100, 30));
        this.jbtnReset.setFont(new Font("Arial", 0, 14));
        this.jbtnReset.setText(bundle.getString("button.reset"));
        this.jbtnReset.setPreferredSize(new Dimension(110, 45));
        this.jbtnReset.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPanelCSVImport.this.jbtnResetActionPerformed(evt);
            }
        });
        this.jComboSupplier.setFont(new Font("Arial", 0, 14));
        this.jComboSupplier.setEnabled(false);
        this.jComboSupplier.setMinimumSize(new Dimension(32, 25));
        this.jComboSupplier.setName("");
        this.jComboSupplier.setPreferredSize(new Dimension(300, 30));
        this.jComboSupplier.addItemListener(new ItemListener(){

            @Override
            public void itemStateChanged(ItemEvent evt) {
                JPanelCSVImport.this.jComboSupplierItemStateChanged(evt);
            }
        });
        this.jComboSupplier.addFocusListener(new FocusAdapter(){

            @Override
            public void focusGained(FocusEvent evt) {
                JPanelCSVImport.this.jComboSupplierFocusGained(evt);
            }
        });
        this.jComboSupplier.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPanelCSVImport.this.jComboSupplierActionPerformed(evt);
            }
        });
        this.jLabel21.setFont(new Font("Arial", 0, 14));
        this.jLabel21.setText(bundle.getString("label.suppliername"));
        this.jLabel21.setPreferredSize(new Dimension(100, 30));
        this.jLabel22.setFont(new Font("Arial", 0, 14));
        this.jLabel22.setText(bundle.getString("label.proddefaultsupplier"));
        this.jLabel22.setPreferredSize(new Dimension(100, 30));
        this.jComboDefaultSupplier.setFont(new Font("Arial", 0, 14));
        this.jComboDefaultSupplier.setEnabled(false);
        this.jComboDefaultSupplier.setMinimumSize(new Dimension(32, 25));
        this.jComboDefaultSupplier.setPreferredSize(new Dimension(300, 30));
        this.jComboDefaultSupplier.addItemListener(new ItemListener(){

            @Override
            public void itemStateChanged(ItemEvent evt) {
                JPanelCSVImport.this.jComboDefaultSupplierItemStateChanged(evt);
            }
        });
        this.jComboDefaultSupplier.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPanelCSVImport.this.jComboDefaultSupplierActionPerformed(evt);
            }
        });
        GroupLayout jPanel1Layout = new GroupLayout(this.jPanel1);
        this.jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jLabel8, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jCheckInCatalogue, -2, -1, -2).addGap(59, 59, 59).addComponent(this.jCheckSellIncTax, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jLabel12, -2, -1, -2)).addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup().addComponent(this.jbtnReset, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addComponent(this.jImport, -2, -1, -2).addGap(93, 93, 93)))).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jLabel7, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jComboTax, -2, -1, -2)).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jLabel4, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jComboBarcode, -2, -1, -2)).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jLabel3, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jComboReference, -2, -1, -2)).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jLabel5, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jComboName, -2, -1, -2)).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jLabel10, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jComboBuy, -2, -1, -2)).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jLabel20, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jComboSell, -2, -1, -2)))).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jLabel6, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jComboDefaultCategory, -2, -1, -2)).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jLabel11, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jComboCategory, -2, -1, -2)).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jLabel21, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jComboSupplier, -2, -1, -2)).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jLabel22, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jComboDefaultSupplier, -2, -1, -2))).addContainerGap()));
        jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addGap(2, 2, 2).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jComboReference, -2, -1, -2).addComponent(this.jLabel3, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel4, -2, -1, -2).addComponent(this.jComboBarcode, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jComboName, -2, -1, -2).addComponent(this.jLabel5, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jComboBuy, -2, -1, -2).addComponent(this.jLabel10, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jComboSell, -2, -1, -2).addComponent(this.jLabel20, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jComboTax, -2, -1, -2).addComponent(this.jLabel7, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel11, GroupLayout.Alignment.TRAILING, -2, -1, -2).addComponent(this.jComboCategory, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jComboDefaultCategory, -2, -1, -2).addComponent(this.jLabel6, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel21, GroupLayout.Alignment.TRAILING, -2, -1, -2).addComponent(this.jComboSupplier, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jComboDefaultSupplier, -2, -1, -2).addComponent(this.jLabel22, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel12, -2, -1, -2).addComponent(this.jCheckSellIncTax, -2, -1, -2).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.jLabel8, -2, -1, -2).addComponent(this.jCheckInCatalogue, -2, -1, -2))).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jImport, -2, -1, -2).addComponent(this.jbtnReset, -2, -1, -2)).addContainerGap(-1, Short.MAX_VALUE)));
        this.jLabel17.setFont(new Font("Arial", 0, 10));
        this.jLabel17.setText("Import version 4.2");
        this.jLabel18.setFont(new Font("Arial", 0, 14));
        this.jLabel18.setText(bundle.getString("label.csvdelimit"));
        this.jLabel18.setPreferredSize(new Dimension(100, 30));
        this.jHeaderRead.setFont(new Font("Arial", 0, 12));
        this.jHeaderRead.setText(bundle.getString("label.csvread"));
        this.jHeaderRead.setEnabled(false);
        this.jHeaderRead.setPreferredSize(new Dimension(110, 45));
        this.jHeaderRead.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPanelCSVImport.this.jHeaderReadActionPerformed(evt);
            }
        });
        this.jPanel2.setBorder(BorderFactory.createTitledBorder(new LineBorder(new Color(153, 153, 153), 1, true), bundle.getString("title.CSVImport"), 0, 0, new Font("Arial", 1, 14), new Color(102, 102, 102)));
        this.jLabel9.setFont(new Font("Arial", 0, 14));
        this.jLabel9.setText(bundle.getString("label.csvrecordsfound"));
        this.jLabel9.setPreferredSize(new Dimension(150, 30));
        this.jLabel14.setFont(new Font("Arial", 0, 14));
        this.jLabel14.setText(bundle.getString("label.csvnewproducts"));
        this.jLabel14.setMaximumSize(new Dimension(77, 14));
        this.jLabel14.setMinimumSize(new Dimension(77, 14));
        this.jLabel14.setPreferredSize(new Dimension(150, 30));
        this.jLabel16.setFont(new Font("Arial", 0, 14));
        this.jLabel16.setText(bundle.getString("label.cvsinvalid"));
        this.jLabel16.setPreferredSize(new Dimension(150, 30));
        this.jTextUpdates.setFont(new Font("Arial", 0, 14));
        this.jTextUpdates.setText(bundle.getString("label.csvpriceupdated"));
        this.jTextUpdates.setPreferredSize(new Dimension(150, 30));
        this.jLabel2.setFont(new Font("Arial", 0, 14));
        this.jLabel2.setText(bundle.getString("label.csvmissing"));
        this.jLabel2.setPreferredSize(new Dimension(150, 30));
        this.jLabel15.setFont(new Font("Arial", 0, 14));
        this.jLabel15.setText(bundle.getString("label.csvbad"));
        this.jLabel15.setPreferredSize(new Dimension(150, 30));
        this.jLabel13.setFont(new Font("Arial", 0, 14));
        this.jLabel13.setText(bundle.getString("label.cvsnotchanged"));
        this.jLabel13.setPreferredSize(new Dimension(150, 30));
        this.jTextRecords.setFont(new Font("Arial", 0, 14));
        this.jTextRecords.setForeground(new Color(102, 102, 102));
        this.jTextRecords.setHorizontalAlignment(4);
        this.jTextRecords.setBorder(null);
        this.jTextRecords.setDisabledTextColor(new Color(0, 0, 0));
        this.jTextRecords.setEnabled(false);
        this.jTextRecords.setPreferredSize(new Dimension(100, 30));
        this.jTextNew.setFont(new Font("Arial", 0, 14));
        this.jTextNew.setForeground(new Color(102, 102, 102));
        this.jTextNew.setHorizontalAlignment(4);
        this.jTextNew.setBorder(null);
        this.jTextNew.setDisabledTextColor(new Color(0, 0, 0));
        this.jTextNew.setEnabled(false);
        this.jTextNew.setPreferredSize(new Dimension(100, 30));
        this.jTextInvalid.setFont(new Font("Arial", 0, 14));
        this.jTextInvalid.setForeground(new Color(102, 102, 102));
        this.jTextInvalid.setHorizontalAlignment(4);
        this.jTextInvalid.setBorder(null);
        this.jTextInvalid.setDisabledTextColor(new Color(0, 0, 0));
        this.jTextInvalid.setEnabled(false);
        this.jTextInvalid.setPreferredSize(new Dimension(100, 30));
        this.jTextUpdate.setFont(new Font("Arial", 0, 14));
        this.jTextUpdate.setForeground(new Color(102, 102, 102));
        this.jTextUpdate.setHorizontalAlignment(4);
        this.jTextUpdate.setBorder(null);
        this.jTextUpdate.setDisabledTextColor(new Color(0, 0, 0));
        this.jTextUpdate.setEnabled(false);
        this.jTextUpdate.setPreferredSize(new Dimension(100, 30));
        this.jTextMissing.setFont(new Font("Arial", 0, 14));
        this.jTextMissing.setForeground(new Color(102, 102, 102));
        this.jTextMissing.setHorizontalAlignment(4);
        this.jTextMissing.setBorder(null);
        this.jTextMissing.setDisabledTextColor(new Color(0, 0, 0));
        this.jTextMissing.setEnabled(false);
        this.jTextMissing.setPreferredSize(new Dimension(100, 30));
        this.jTextBadPrice.setFont(new Font("Arial", 0, 14));
        this.jTextBadPrice.setForeground(new Color(255, 0, 204));
        this.jTextBadPrice.setHorizontalAlignment(4);
        this.jTextBadPrice.setBorder(null);
        this.jTextBadPrice.setDisabledTextColor(new Color(0, 0, 0));
        this.jTextBadPrice.setEnabled(false);
        this.jTextBadPrice.setPreferredSize(new Dimension(100, 30));
        this.jTextNoChange.setFont(new Font("Arial", 0, 14));
        this.jTextNoChange.setForeground(new Color(102, 102, 102));
        this.jTextNoChange.setHorizontalAlignment(4);
        this.jTextNoChange.setBorder(null);
        this.jTextNoChange.setDisabledTextColor(new Color(0, 0, 0));
        this.jTextNoChange.setEnabled(false);
        this.jTextNoChange.setPreferredSize(new Dimension(100, 30));
        this.jLabel19.setFont(new Font("Arial", 0, 14));
        this.jLabel19.setText(bundle.getString("label.cvsbadcats"));
        this.jLabel19.setPreferredSize(new Dimension(150, 30));
        this.jTextBadCats.setFont(new Font("Arial", 0, 14));
        this.jTextBadCats.setForeground(new Color(255, 0, 204));
        this.jTextBadCats.setHorizontalAlignment(4);
        this.jTextBadCats.setBorder(null);
        this.jTextBadCats.setDisabledTextColor(new Color(0, 0, 0));
        this.jTextBadCats.setEnabled(false);
        this.jTextBadCats.setPreferredSize(new Dimension(100, 30));
        GroupLayout jPanel2Layout = new GroupLayout(this.jPanel2);
        this.jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel2Layout.createSequentialGroup().addContainerGap().addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel2Layout.createSequentialGroup().addComponent(this.jLabel9, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jTextRecords, -2, -1, -2)).addGroup(jPanel2Layout.createSequentialGroup().addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addComponent(this.jLabel19, -1, -1, -2).addComponent(this.jLabel13, GroupLayout.Alignment.LEADING, -1, -1, -2).addComponent(this.jLabel16, GroupLayout.Alignment.LEADING, -1, -1, -2).addComponent(this.jTextUpdates, GroupLayout.Alignment.LEADING, -1, -1, -2).addComponent(this.jLabel2, GroupLayout.Alignment.LEADING, -1, -1, -2).addComponent(this.jLabel15, GroupLayout.Alignment.LEADING, -1, -1, -2).addComponent(this.jLabel14, -1, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jTextBadCats, -2, -1, -2).addComponent(this.jTextNew, -2, -1, -2).addComponent(this.jTextInvalid, -2, -1, -2).addComponent(this.jTextUpdate, -2, -1, -2).addComponent(this.jTextMissing, -2, -1, -2).addComponent(this.jTextBadPrice, -2, -1, -2).addComponent(this.jTextNoChange, -2, -1, -2)))).addContainerGap()));
        jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel2Layout.createSequentialGroup().addGap(0, 0, 0).addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel9, -2, -1, -2).addComponent(this.jTextRecords, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel14, -2, -1, -2).addComponent(this.jTextNew, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel16, -2, -1, -2).addComponent(this.jTextInvalid, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jTextUpdates, -2, -1, -2).addComponent(this.jTextUpdate, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel2, -2, -1, -2).addComponent(this.jTextMissing, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel15, -2, -1, -2).addComponent(this.jTextBadPrice, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel13, -2, -1, -2).addComponent(this.jTextNoChange, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel19, -2, -1, -2).addComponent(this.jTextBadCats, -2, -1, -2)).addContainerGap()));
        this.jComboSeparator.setFont(new Font("Arial", 0, 12));
        this.jComboSeparator.setPreferredSize(new Dimension(50, 30));
        this.webPBar.setFont(new Font("Arial", 0, 13));
        this.webPBar.setPreferredSize(new Dimension(240, 30));
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(0, 655, Short.MAX_VALUE).addComponent(this.jLabel17)).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jFileChooserPanel, -2, -1, -2).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jPanel1, -2, -1, -2).addGroup(layout.createSequentialGroup().addComponent(this.jLabel18, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jComboSeparator, -2, -1, -2).addGap(138, 138, 138).addComponent(this.jHeaderRead, -2, -1, -2))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.jPanel2, -1, -1, Short.MAX_VALUE).addComponent(this.webPBar, -1, -1, Short.MAX_VALUE)))).addGap(0, 0, Short.MAX_VALUE))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.jLabel17).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jFileChooserPanel, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jComboSeparator, -2, -1, -2).addComponent(this.jLabel18, -2, -1, -2).addComponent(this.jHeaderRead, -2, -1, -2)).addComponent(this.webPBar, -2, 32, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jPanel2, -2, -1, -2).addComponent(this.jPanel1, -2, 469, -2)).addContainerGap()));
    }

    private void jHeaderReadActionPerformed(ActionEvent evt) {
        try {
            this.GetheadersFromFile(this.jFileName.getText());
            this.webPBar.setString("Source file Header OK");
        }
        catch (IOException ex) {
            Logger.getLogger(JPanelCSVImport.class.getName()).log(Level.SEVERE, null, ex);
            this.webPBar.setString("Source file Header error!");
        }
    }

    private void jImportActionPerformed(ActionEvent evt) {
        this.jImport.setEnabled(false);
        workProcess work = new workProcess();
        Thread thread2 = new Thread(work);
        thread2.start();
    }

    private void jFileNameActionPerformed(ActionEvent evt) {
        this.jImport.setEnabled(false);
        this.jHeaderRead.setEnabled(true);
    }

    private void jbtnFileChooseActionPerformed(ActionEvent evt) {
        String csv;
        this.resetFields();
        this.setWorker();
        JFileChooser chooser = new JFileChooser(this.last_folder == null ? "C:\\" : this.last_folder);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("csv files", "csv");
        chooser.setFileFilter(filter);
        chooser.showOpenDialog(null);
        File csvFile = chooser.getSelectedFile();
        if (csvFile == null) {
            return;
        }
        File current_folder = chooser.getCurrentDirectory();
        if (this.last_folder == null || !this.last_folder.equals(current_folder.getAbsolutePath())) {
            AppConfig CSVConfig = new AppConfig(this.config_file);
            CSVConfig.load();
            CSVConfig.setProperty("CSV.last_folder", current_folder.getAbsolutePath());
            this.last_folder = current_folder.getAbsolutePath();
            try {
                CSVConfig.save();
            }
            catch (IOException ex) {
                Logger.getLogger(JPanelCSVImport.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (!(csv = csvFile.getName()).trim().equals("")) {
            this.csvFileName = csvFile.getAbsolutePath();
            this.jFileName.setText(this.csvFileName);
        }
    }

    private void jComboCategoryFocusGained(FocusEvent evt) {
        this.jComboCategory.removeAllItems();
        this.jComboCategory.addItem("");
        for (int i = 1; i < this.Headers.size(); ++i) {
            if (!(this.Headers.get(i) != this.jComboBarcode.getSelectedItem() & this.Headers.get(i) != this.jComboReference.getSelectedItem() & this.Headers.get(i) != this.jComboName.getSelectedItem() & this.Headers.get(i) != this.jComboBuy.getSelectedItem() & this.Headers.get(i) != this.jComboSell.getSelectedItem() & this.Headers.get(i) != this.jComboTax.getSelectedItem() & this.Headers.get(i) != this.jComboSupplier.getSelectedItem())) continue;
            this.jComboCategory.addItem(this.Headers.get(i));
        }
        this.jComboCategory.addItem(category_default);
    }

    private void jComboCategoryItemStateChanged(ItemEvent evt) {
        try {
            if (this.jComboCategory.getSelectedItem() == "[ USE DEFAULT CATEGORY ]") {
                this.m_CategoryModel = new ComboBoxValModel(new ArrayList(this.m_sentcat.list()));
                this.jComboDefaultCategory.setModel(this.m_CategoryModel);
            } else {
                this.m_CategoryModel = new ComboBoxValModel(new ArrayList(this.m_sentcat.list()));
                this.m_CategoryModel.add(reject_bad_category);
                this.jComboDefaultCategory.setModel(this.m_CategoryModel);
            }
        }
        catch (BasicException ex) {
            Logger.getLogger(JPanelCSVImport.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.checkFieldMapping();
    }

    private void jComboDefaultCategoryActionPerformed(ActionEvent evt) {
        this.checkFieldMapping();
    }

    private void jComboDefaultCategoryItemStateChanged(ItemEvent evt) {
    }

    private void jComboSellFocusGained(FocusEvent evt) {
        this.jComboSell.removeAllItems();
        this.jComboSell.addItem("");
        for (int i = 1; i < this.Headers.size(); ++i) {
            if (!(this.Headers.get(i) != this.jComboCategory.getSelectedItem() & this.Headers.get(i) != this.jComboReference.getSelectedItem() & this.Headers.get(i) != this.jComboName.getSelectedItem() & this.Headers.get(i) != this.jComboBarcode.getSelectedItem() & this.Headers.get(i) != this.jComboBuy.getSelectedItem() & this.Headers.get(i) != this.jComboTax.getSelectedItem() & this.Headers.get(i) != this.jComboSupplier.getSelectedItem())) continue;
            this.jComboSell.addItem(this.Headers.get(i));
        }
    }

    private void jComboSellItemStateChanged(ItemEvent evt) {
        this.checkFieldMapping();
    }

    private void jComboBuyFocusGained(FocusEvent evt) {
        this.jComboBuy.removeAllItems();
        this.jComboBuy.addItem("");
        for (int i = 1; i < this.Headers.size(); ++i) {
            if (!(this.Headers.get(i) != this.jComboCategory.getSelectedItem() & this.Headers.get(i) != this.jComboReference.getSelectedItem() & this.Headers.get(i) != this.jComboName.getSelectedItem() & this.Headers.get(i) != this.jComboBarcode.getSelectedItem() & this.Headers.get(i) != this.jComboSell.getSelectedItem() & this.Headers.get(i) != this.jComboTax.getSelectedItem() & this.Headers.get(i) != this.jComboSupplier.getSelectedItem())) continue;
            this.jComboBuy.addItem(this.Headers.get(i));
        }
    }

    private void jComboBuyItemStateChanged(ItemEvent evt) {
        this.checkFieldMapping();
    }

    private void jComboNameFocusGained(FocusEvent evt) {
        this.jComboName.removeAllItems();
        this.jComboName.addItem("");
        for (int i = 1; i < this.Headers.size(); ++i) {
            if (!(this.Headers.get(i) != this.jComboCategory.getSelectedItem() & this.Headers.get(i) != this.jComboReference.getSelectedItem() & this.Headers.get(i) != this.jComboBarcode.getSelectedItem() & this.Headers.get(i) != this.jComboBuy.getSelectedItem() & this.Headers.get(i) != this.jComboSell.getSelectedItem() & this.Headers.get(i) != this.jComboTax.getSelectedItem() & this.Headers.get(i) != this.jComboSupplier.getSelectedItem())) continue;
            this.jComboName.addItem(this.Headers.get(i));
        }
    }

    private void jComboNameItemStateChanged(ItemEvent evt) {
        this.checkFieldMapping();
    }

    private void jComboBarcodeFocusGained(FocusEvent evt) {
        this.jComboBarcode.removeAllItems();
        this.jComboBarcode.addItem("");
        for (int i = 1; i < this.Headers.size(); ++i) {
            if (!(this.Headers.get(i) != this.jComboCategory.getSelectedItem() & this.Headers.get(i) != this.jComboReference.getSelectedItem() & this.Headers.get(i) != this.jComboName.getSelectedItem() & this.Headers.get(i) != this.jComboBuy.getSelectedItem() & this.Headers.get(i) != this.jComboSell.getSelectedItem() & this.Headers.get(i) != this.jComboTax.getSelectedItem() & this.Headers.get(i) != this.jComboSupplier.getSelectedItem())) continue;
            this.jComboBarcode.addItem(this.Headers.get(i));
        }
    }

    private void jComboBarcodeItemStateChanged(ItemEvent evt) {
        this.checkFieldMapping();
    }

    private void jComboReferenceFocusGained(FocusEvent evt) {
        this.jComboReference.removeAllItems();
        this.jComboReference.addItem("");
        for (int i = 1; i < this.Headers.size(); ++i) {
            if (!(this.Headers.get(i) != this.jComboCategory.getSelectedItem() & this.Headers.get(i) != this.jComboBarcode.getSelectedItem() & this.Headers.get(i) != this.jComboName.getSelectedItem() & this.Headers.get(i) != this.jComboBuy.getSelectedItem() & this.Headers.get(i) != this.jComboSell.getSelectedItem() & this.Headers.get(i) != this.jComboTax.getSelectedItem() & this.Headers.get(i) != this.jComboSupplier.getSelectedItem())) continue;
            this.jComboReference.addItem(this.Headers.get(i));
        }
    }

    private void jComboReferenceItemStateChanged(ItemEvent evt) {
        this.checkFieldMapping();
    }

    private void jbtnResetActionPerformed(ActionEvent evt) {
        this.resetFields();
        this.progress = -1;
        this.webPBar.setString("Waiting...");
    }

    private void jComboTaxFocusGained(FocusEvent evt) {
        this.jComboTax.removeAllItems();
        this.jComboTax.addItem("");
        for (int i = 1; i < this.Headers.size(); ++i) {
            if (!(this.Headers.get(i) != this.jComboCategory.getSelectedItem() & this.Headers.get(i) != this.jComboReference.getSelectedItem() & this.Headers.get(i) != this.jComboBarcode.getSelectedItem() & this.Headers.get(i) != this.jComboName.getSelectedItem() & this.Headers.get(i) != this.jComboBuy.getSelectedItem() & this.Headers.get(i) != this.jComboSell.getSelectedItem() & this.Headers.get(i) != this.jComboSupplier.getSelectedItem())) continue;
            this.jComboTax.addItem(this.Headers.get(i));
        }
    }

    private void jComboTaxItemStateChanged(ItemEvent evt) {
        this.checkFieldMapping();
    }

    private void jComboSupplierItemStateChanged(ItemEvent evt) {
        try {
            if (this.jComboSupplier.getSelectedItem() == "[ USE DEFAULT SUPPLIER ]") {
                this.m_SupplierModel = new ComboBoxValModel(new ArrayList(this.m_sentsupp.list()));
                this.jComboDefaultSupplier.setModel(this.m_SupplierModel);
            } else {
                this.m_SupplierModel = new ComboBoxValModel(new ArrayList(this.m_sentsupp.list()));
                this.m_SupplierModel.add(reject_bad_supplier);
                this.jComboDefaultSupplier.setModel(this.m_SupplierModel);
            }
        }
        catch (BasicException ex) {
            Logger.getLogger(JPanelCSVImport.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.checkFieldMapping();
    }

    private void jComboSupplierFocusGained(FocusEvent evt) {
        this.jComboSupplier.removeAllItems();
        this.jComboSupplier.addItem("");
        for (int i = 1; i < this.Headers.size(); ++i) {
            if (!(this.Headers.get(i) != this.jComboCategory.getSelectedItem() & this.Headers.get(i) != this.jComboReference.getSelectedItem() & this.Headers.get(i) != this.jComboBarcode.getSelectedItem() & this.Headers.get(i) != this.jComboName.getSelectedItem() & this.Headers.get(i) != this.jComboBuy.getSelectedItem() & this.Headers.get(i) != this.jComboSell.getSelectedItem() & this.Headers.get(i) != this.jComboTax.getSelectedItem())) continue;
            this.jComboSupplier.addItem(this.Headers.get(i));
        }
    }

    private void jComboDefaultSupplierItemStateChanged(ItemEvent evt) {
    }

    private void jComboDefaultSupplierActionPerformed(ActionEvent evt) {
        this.checkFieldMapping();
    }

    private void jComboSupplierActionPerformed(ActionEvent evt) {
    }

    private class workProcess
    implements Runnable {
        private workProcess() {
        }

        @Override
        public void run() {
            try {
                JPanelCSVImport.this.ImportCsvFile(JPanelCSVImport.this.jFileName.getText());
            }
            catch (IOException ex) {
                Logger.getLogger(JPanelCSVImport.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

