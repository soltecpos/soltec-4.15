/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.customers;

import com.openbravo.basic.BasicException;
import com.openbravo.data.gui.ComboBoxValModel;
import com.openbravo.data.gui.JImageEditor;
import com.openbravo.data.loader.SentenceList;
import com.openbravo.data.user.DirtyManager;
import com.openbravo.data.user.EditorRecord;
import com.openbravo.format.Formats;
import com.openbravo.pos.customers.CustomerInfo;
import com.openbravo.pos.customers.CustomerTransaction;
import com.openbravo.pos.customers.MunicipiosColombia;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.forms.BeanFactoryException;
import com.openbravo.pos.forms.DataLogicSales;
import com.openbravo.pos.forms.JPanelView;
import com.openbravo.pos.inventory.TaxCustCategoryInfo;
import com.openbravo.pos.util.StringUtils;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

public class CustomersView
extends JPanel
implements EditorRecord,
JPanelView {
    private static final long serialVersionUID = 1L;
    private DataLogicSales dlSales;
    private DataLogicSales m_dlSales;
    private ComboBoxValModel<TaxCustCategoryInfo> m_CategoryModel;
    private Object m_oId;
    private SentenceList<TaxCustCategoryInfo> m_sentcat;
    private List<CustomerTransaction> customerTransactionList;
    private TransactionTableModel transactionModel;
    private DirtyManager m_Dirty;
    private AppView appView;
    private CustomerInfo customerInfo;
    private JPanel jPanelFE;
    private JLabel jLabelDv;
    private JTextField m_jDv;
    private JLabel jLabelDocType;
    private JComboBox<String> m_jDocType;
    private JLabel jLabelPersonType;
    private JComboBox<String> m_jPersonType;
    private JLabel jLabelLiability;
    private JComboBox<String> m_jLiability;
    private JLabel jLabelMunicipality;
    private JComboBox<String> m_jMunicipalityCode;
    private JLabel jLabelRegime;
    private JComboBox<String> m_jRegimeCode;
    private JButton jBtnClearCard;
    private JButton jBtnCreateCard;
    private JButton jBtnShowTrans;
    private JLabel jLabel1;
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
    private JLabel jLabel24;
    private JLabel jLabel3;
    private JLabel jLabel34;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JLabel jLabel8;
    private JLabel jLabel9;
    private JLabel jLblDiscount;
    private JLabel jLblDiscountpercent;
    private JLabel jLblVIP;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JPanel jPanel3;
    private JPanel jPanel4;
    private JPanel jPanel5;
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane3;
    private JTabbedPane jTabbedPane1;
    private JTable jTableCustomerTransactions;
    private JTextField jcard;
    private JComboBox<TaxCustCategoryInfo> m_jCategory;
    private JImageEditor m_jImage;
    private JTextField m_jName;
    private JTextArea m_jNotes;
    private JTextField m_jSearchkey;
    private JTextField m_jTaxID;
    private JCheckBox m_jVip;
    private JCheckBox m_jVisible;
    private JTextField txtAddress;
    private JTextField txtAddress2;
    private JTextField txtCity;
    private JTextField txtCountry;
    private JTextField txtCurdate;
    private JTextField txtCurdebt;
    private JTextField txtDiscount;
    private JTextField txtEmail;
    private JTextField txtFax;
    private JTextField txtFirstName;
    private JTextField txtLastName;
    private JTextField txtMaxdebt;
    private JTextField txtPhone;
    private JTextField txtPhone2;
    private JTextField txtPostal;
    private JTextField txtRegion;
    private JButton webBtnMail;

    public CustomersView(AppView app, DirtyManager dirty) {
        try {
            this.setAppView(app);
            this.m_dlSales = (DataLogicSales)app.getBean("com.openbravo.pos.forms.DataLogicSales");
            this.initComponents();
            this.m_sentcat = this.m_dlSales.getTaxCustCategoriesList();
            this.m_CategoryModel = new ComboBoxValModel();
            this.m_Dirty = dirty;
            this.m_jTaxID.getDocument().addDocumentListener(dirty);
            this.m_jSearchkey.getDocument().addDocumentListener(dirty);
            this.m_jName.getDocument().addDocumentListener(dirty);
            this.m_jCategory.addActionListener(dirty);
            this.m_jNotes.getDocument().addDocumentListener(dirty);
            this.txtMaxdebt.getDocument().addDocumentListener(dirty);
            this.txtCurdebt.getDocument().addDocumentListener(dirty);
            this.txtCurdate.getDocument().addDocumentListener(dirty);
            this.m_jVisible.addActionListener(dirty);
            this.m_jVip.addActionListener(dirty);
            this.txtDiscount.getDocument().addDocumentListener(dirty);
            this.txtFirstName.getDocument().addDocumentListener(dirty);
            this.txtLastName.getDocument().addDocumentListener(dirty);
            this.txtEmail.getDocument().addDocumentListener(dirty);
            this.txtPhone.getDocument().addDocumentListener(dirty);
            this.txtPhone2.getDocument().addDocumentListener(dirty);
            this.txtFax.getDocument().addDocumentListener(dirty);
            this.m_jImage.addPropertyChangeListener(dirty);
            this.txtAddress.getDocument().addDocumentListener(dirty);
            this.txtAddress2.getDocument().addDocumentListener(dirty);
            this.txtPostal.getDocument().addDocumentListener(dirty);
            this.txtCity.getDocument().addDocumentListener(dirty);
            this.txtRegion.getDocument().addDocumentListener(dirty);
            this.txtCountry.getDocument().addDocumentListener(dirty);
            this.init();
        }
        catch (BeanFactoryException ex) {
            Logger.getLogger(CustomersView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void init() {
        this.writeValueEOF();
    }

    @Override
    public void activate() throws BasicException {
        List<TaxCustCategoryInfo> a = this.m_dlSales.getTaxCustCategoriesList().list();
        a.add(0, null);
        this.m_CategoryModel = new ComboBoxValModel<TaxCustCategoryInfo>(a);
        this.m_jCategory.setModel(this.m_CategoryModel);
        Object cId = null;
    }

    @Override
    public void refresh() {
    }

    @Override
    public boolean deactivate() {
        return true;
    }

    @Override
    public void writeValueEOF() {
        this.m_oId = null;
        this.m_jTaxID.setText(null);
        this.m_jSearchkey.setText(null);
        this.m_jName.setText(null);
        this.m_CategoryModel.setSelectedKey(null);
        this.m_jNotes.setText(null);
        this.txtMaxdebt.setText(null);
        this.txtCurdebt.setText(null);
        this.txtCurdate.setText(null);
        this.m_jVisible.setSelected(false);
        this.m_jVip.setSelected(false);
        this.txtDiscount.setText(null);
        this.jcard.setText(null);
        this.txtFirstName.setText(null);
        this.txtLastName.setText(null);
        this.txtEmail.setText(null);
        this.txtPhone.setText(null);
        this.txtPhone2.setText(null);
        this.txtFax.setText(null);
        this.m_jImage.setImage(null);
        this.txtAddress.setText(null);
        this.txtAddress2.setText(null);
        this.txtPostal.setText(null);
        this.txtCity.setText(null);
        this.txtRegion.setText(null);
        this.txtCountry.setText(null);
        this.m_jTaxID.setEnabled(false);
        this.m_jSearchkey.setEnabled(false);
        this.m_jName.setEnabled(false);
        this.m_jCategory.setEnabled(false);
        this.m_jNotes.setEnabled(false);
        this.txtMaxdebt.setEnabled(false);
        this.txtCurdebt.setEnabled(false);
        this.txtCurdate.setEnabled(false);
        this.m_jVisible.setEnabled(false);
        this.m_jVip.setEnabled(false);
        this.txtDiscount.setEnabled(false);
        this.jcard.setEnabled(false);
        this.txtFirstName.setEnabled(false);
        this.txtLastName.setEnabled(false);
        this.txtEmail.setEnabled(false);
        this.txtPhone.setEnabled(false);
        this.txtPhone2.setEnabled(false);
        this.txtFax.setEnabled(false);
        this.m_jImage.setEnabled(false);
        this.txtAddress.setEnabled(false);
        this.txtAddress2.setEnabled(false);
        this.txtPostal.setEnabled(false);
        this.txtCity.setEnabled(false);
        this.txtRegion.setEnabled(false);
        this.txtCountry.setEnabled(false);
        this.jBtnCreateCard.setEnabled(false);
        this.jBtnClearCard.setEnabled(false);
        this.jTableCustomerTransactions.setEnabled(false);
    }

    @Override
    public void writeValueInsert() {
        this.m_oId = null;
        this.m_jTaxID.setText(null);
        this.m_jSearchkey.setText(null);
        this.m_jName.setText(null);
        this.txtPhone.setText(null);
        this.txtEmail.setText(null);
        this.m_CategoryModel.setSelectedKey(null);
        this.m_jNotes.setText(null);
        this.txtMaxdebt.setText(null);
        this.txtCurdebt.setText(null);
        this.txtCurdate.setText(null);
        this.m_jVisible.setSelected(true);
        this.m_jVip.setSelected(false);
        this.txtDiscount.setText(null);
        this.jcard.setText(null);
        this.txtFirstName.setText(null);
        this.txtLastName.setText(null);
        this.txtPhone2.setText(null);
        this.txtFax.setText(null);
        this.m_jImage.setImage(null);
        this.txtAddress.setText(null);
        this.txtAddress2.setText(null);
        this.txtPostal.setText(null);
        this.txtCity.setText(null);
        this.txtRegion.setText(null);
        this.txtCountry.setText(null);
        this.m_jDv.setText(null);
        if (this.m_jDocType != null) {
            this.m_jDocType.setSelectedIndex(-1);
        }
        if (this.m_jPersonType != null) {
            this.m_jPersonType.setSelectedIndex(-1);
        }
        if (this.m_jLiability != null && this.m_jLiability != null) {
            this.m_jLiability.setSelectedIndex(-1);
        }
        if (this.m_jMunicipalityCode != null) {
            this.m_jMunicipalityCode.setSelectedItem(null);
        }
        if (this.m_jRegimeCode != null) {
            this.m_jRegimeCode.setSelectedIndex(-1);
        }
        this.m_jTaxID.setEnabled(true);
        this.m_jSearchkey.setEnabled(true);
        this.m_jName.setEnabled(true);
        this.m_jCategory.setEnabled(true);
        this.m_jNotes.setEnabled(true);
        this.txtMaxdebt.setEnabled(true);
        this.txtCurdebt.setEnabled(true);
        this.txtCurdate.setEnabled(true);
        this.m_jVisible.setEnabled(true);
        this.m_jVip.setEnabled(true);
        this.txtDiscount.setEnabled(true);
        this.jcard.setEnabled(true);
        this.txtFirstName.setEnabled(true);
        this.txtLastName.setEnabled(true);
        this.txtEmail.setEnabled(true);
        this.webBtnMail.setEnabled(true);
        this.txtPhone.setEnabled(true);
        this.txtPhone2.setEnabled(true);
        this.txtFax.setEnabled(true);
        this.m_jImage.setEnabled(true);
        this.txtAddress.setEnabled(true);
        this.txtAddress2.setEnabled(true);
        this.txtPostal.setEnabled(true);
        this.txtCity.setEnabled(true);
        this.txtRegion.setEnabled(true);
        this.txtCountry.setEnabled(true);
        this.jBtnCreateCard.setEnabled(true);
        this.jBtnCreateCard.setEnabled(true);
        this.jBtnClearCard.setEnabled(true);
        this.m_jDv.setEnabled(true);
        this.m_jDocType.setEnabled(true);
        this.m_jPersonType.setEnabled(true);
        this.m_jLiability.setEnabled(true);
        this.m_jMunicipalityCode.setEnabled(true);
        this.m_jRegimeCode.setEnabled(true);
        this.jTableCustomerTransactions.setEnabled(false);
    }

    @Override
    public void writeValueDelete(Object value) {
        Object[] customer = (Object[])value;
        this.m_oId = customer[0];
        this.m_jSearchkey.setText((String)customer[1]);
        this.m_jTaxID.setText((String)customer[2]);
        this.m_jName.setText((String)customer[3]);
        this.m_CategoryModel.setSelectedKey(customer[4]);
        this.jcard.setText((String)customer[5]);
        this.txtMaxdebt.setText(Formats.CURRENCY.formatValue(customer[6]));
        this.txtAddress.setText(Formats.STRING.formatValue(customer[7]));
        this.txtAddress2.setText(Formats.STRING.formatValue(customer[8]));
        this.txtPostal.setText(Formats.STRING.formatValue(customer[9]));
        this.txtCity.setText(Formats.STRING.formatValue(customer[10]));
        this.txtRegion.setText(Formats.STRING.formatValue(customer[11]));
        this.txtCountry.setText(Formats.STRING.formatValue(customer[12]));
        this.txtFirstName.setText(Formats.STRING.formatValue(customer[13]));
        this.txtLastName.setText(Formats.STRING.formatValue(customer[14]));
        this.txtEmail.setText(Formats.STRING.formatValue(customer[15]));
        this.txtPhone.setText(Formats.STRING.formatValue(customer[16]));
        this.txtPhone2.setText(Formats.STRING.formatValue(customer[17]));
        this.txtFax.setText(Formats.STRING.formatValue(customer[18]));
        this.m_jNotes.setText((String)customer[19]);
        this.m_jVisible.setSelected((Boolean)customer[20]);
        this.txtCurdate.setText(Formats.DATE.formatValue(customer[21]));
        this.txtCurdebt.setText(Formats.CURRENCY.formatValue(customer[22]));
        this.m_jImage.setImage((BufferedImage)customer[23]);
        this.m_jVip.setSelected((Boolean)customer[24]);
        this.txtDiscount.setText(Formats.DOUBLE.formatValue(customer[25]));
        this.m_jDv.setText((String)customer[26]);
        this.m_jDocType.setSelectedItem((String)customer[27]);
        this.m_jPersonType.setSelectedItem((String)customer[28]);
        this.m_jLiability.setSelectedItem((String)customer[29]);
        this.m_jMunicipalityCode.setSelectedItem((String)customer[30]);
        this.m_jRegimeCode.setSelectedItem((String)customer[31]);
        this.m_jTaxID.setEnabled(false);
        this.m_jSearchkey.setEnabled(false);
        this.m_jName.setEnabled(false);
        this.m_jNotes.setEnabled(false);
        this.txtMaxdebt.setEnabled(false);
        this.txtCurdebt.setEnabled(false);
        this.txtCurdate.setEnabled(false);
        this.m_jVisible.setEnabled(false);
        this.m_jVip.setEnabled(false);
        this.txtDiscount.setEnabled(false);
        this.jcard.setEnabled(false);
        this.txtFirstName.setEnabled(false);
        this.txtLastName.setEnabled(false);
        this.txtEmail.setEnabled(false);
        this.webBtnMail.setEnabled(false);
        this.txtPhone.setEnabled(false);
        this.txtPhone2.setEnabled(false);
        this.txtFax.setEnabled(false);
        this.m_jImage.setEnabled(true);
        this.txtAddress.setEnabled(false);
        this.txtAddress2.setEnabled(false);
        this.txtPostal.setEnabled(false);
        this.txtCity.setEnabled(false);
        this.txtRegion.setEnabled(false);
        this.txtCountry.setEnabled(false);
        this.m_jCategory.setEnabled(false);
        this.jBtnCreateCard.setEnabled(false);
        this.jBtnCreateCard.setEnabled(false);
        this.jBtnClearCard.setEnabled(false);
        this.m_jDv.setEnabled(false);
        this.m_jDocType.setEnabled(false);
        this.m_jPersonType.setEnabled(false);
        this.m_jLiability.setEnabled(false);
        this.m_jMunicipalityCode.setEnabled(false);
        this.m_jRegimeCode.setEnabled(false);
        this.transactionModel = new TransactionTableModel(this.getTransactionOfName((String)this.m_oId));
        this.jTableCustomerTransactions.setModel(this.transactionModel);
        this.jTableCustomerTransactions.setEnabled(false);
    }

    @Override
    public void writeValueEdit(Object value) {
        Object[] customer = (Object[])value;
        this.m_oId = customer[0];
        this.m_jSearchkey.setText((String)customer[1]);
        this.m_jTaxID.setText((String)customer[2]);
        this.m_jName.setText((String)customer[3]);
        this.m_CategoryModel.setSelectedKey(customer[4]);
        this.jcard.setText((String)customer[5]);
        this.txtMaxdebt.setText(Formats.CURRENCY.formatValue(customer[6]));
        this.txtAddress.setText(Formats.STRING.formatValue(customer[7]));
        this.txtAddress2.setText(Formats.STRING.formatValue(customer[8]));
        this.txtPostal.setText(Formats.STRING.formatValue(customer[9]));
        this.txtCity.setText(Formats.STRING.formatValue(customer[10]));
        this.txtRegion.setText(Formats.STRING.formatValue(customer[11]));
        this.txtCountry.setText(Formats.STRING.formatValue(customer[12]));
        this.txtFirstName.setText(Formats.STRING.formatValue(customer[13]));
        this.txtLastName.setText(Formats.STRING.formatValue(customer[14]));
        this.txtEmail.setText(Formats.STRING.formatValue(customer[15]));
        this.txtPhone.setText(Formats.STRING.formatValue(customer[16]));
        this.txtPhone2.setText(Formats.STRING.formatValue(customer[17]));
        this.txtFax.setText(Formats.STRING.formatValue(customer[18]));
        this.m_jNotes.setText((String)customer[19]);
        this.m_jVisible.setSelected((Boolean)customer[20]);
        this.txtCurdate.setText(Formats.DATE.formatValue(customer[21]));
        this.txtCurdebt.setText(Formats.CURRENCY.formatValue(customer[22]));
        this.m_jImage.setImage((BufferedImage)customer[23]);
        this.m_jVip.setSelected((Boolean)customer[24]);
        this.txtDiscount.setText(Formats.DOUBLE.formatValue(customer[25]));
        this.m_jDv.setText((String)customer[26]);
        this.setComboSelection(this.m_jDocType, (String)customer[27]);
        this.setComboSelection(this.m_jPersonType, (String)customer[28]);
        this.setComboSelection(this.m_jLiability, (String)customer[29]);
        this.setComboSelection(this.m_jMunicipalityCode, (String)customer[30]);
        this.setComboSelection(this.m_jRegimeCode, (String)customer[31]);
        this.m_jTaxID.setEnabled(true);
        this.m_jSearchkey.setEnabled(true);
        this.m_jName.setEnabled(true);
        this.m_jNotes.setEnabled(true);
        this.txtMaxdebt.setEnabled(true);
        this.txtCurdebt.setEnabled(true);
        this.txtCurdate.setEnabled(true);
        this.m_jVisible.setEnabled(true);
        this.m_jVip.setEnabled(true);
        this.txtDiscount.setEnabled(true);
        this.jcard.setEnabled(true);
        this.txtFirstName.setEnabled(true);
        this.txtLastName.setEnabled(true);
        this.txtEmail.setEnabled(true);
        this.webBtnMail.setEnabled(true);
        this.txtPhone.setEnabled(true);
        this.txtPhone2.setEnabled(true);
        this.txtFax.setEnabled(true);
        this.m_jImage.setEnabled(true);
        this.txtAddress.setEnabled(true);
        this.txtAddress2.setEnabled(true);
        this.txtPostal.setEnabled(true);
        this.txtCity.setEnabled(true);
        this.txtRegion.setEnabled(true);
        this.txtCountry.setEnabled(true);
        this.m_jCategory.setEnabled(true);
        this.jBtnCreateCard.setEnabled(true);
        this.jBtnCreateCard.setEnabled(true);
        this.jBtnClearCard.setEnabled(true);
        this.m_jDv.setEnabled(true);
        this.m_jDocType.setEnabled(true);
        this.m_jPersonType.setEnabled(true);
        this.m_jLiability.setEnabled(true);
        this.m_jMunicipalityCode.setEnabled(true);
        this.m_jRegimeCode.setEnabled(true);
        this.jTableCustomerTransactions.setVisible(false);
        this.jTableCustomerTransactions.setEnabled(true);
        this.resetTranxTable();
        this.jTableCustomerTransactions.repaint();
        this.repaint();
        this.refresh();
    }

    public void resetTranxTable() {
        this.jTableCustomerTransactions.getColumnModel().getColumn(0).setPreferredWidth(50);
        this.jTableCustomerTransactions.getColumnModel().getColumn(1).setPreferredWidth(70);
        this.jTableCustomerTransactions.getColumnModel().getColumn(2).setPreferredWidth(280);
        this.jTableCustomerTransactions.getColumnModel().getColumn(3).setPreferredWidth(30);
        this.jTableCustomerTransactions.getColumnModel().getColumn(4).setPreferredWidth(50);
        this.jTableCustomerTransactions.repaint();
    }

    private Object getComboValue(JComboBox<?> box) {
        if (box.getSelectedIndex() >= 0) {
            return box.getSelectedItem();
        }
        return null;
    }

    private void setComboSelection(JComboBox<?> box, String code) {
        if (code == null) {
            box.setSelectedIndex(-1);
            return;
        }
        for (int i = 0; i < box.getItemCount(); ++i) {
            String s = box.getItemAt(i).toString();
            if (!s.startsWith(code + " - ") && !s.equals(code)) continue;
            box.setSelectedIndex(i);
            return;
        }
    }

    @Override
    public Object createValue() throws BasicException {
        Object[] customer = new Object[]{this.m_oId == null ? UUID.randomUUID().toString() : this.m_oId, this.m_jSearchkey.getText(), this.m_jTaxID.getText(), this.m_jName.getText(), this.m_CategoryModel.getSelectedKey(), Formats.STRING.parseValue(this.jcard.getText()), Formats.CURRENCY.parseValue(this.txtMaxdebt.getText(), 0.0), Formats.STRING.parseValue(this.txtAddress.getText()), Formats.STRING.parseValue(this.txtAddress2.getText()), Formats.STRING.parseValue(this.txtPostal.getText()), Formats.STRING.parseValue(this.txtCity.getText()), Formats.STRING.parseValue(this.txtRegion.getText()), Formats.STRING.parseValue(this.txtCountry.getText()), Formats.STRING.parseValue(this.txtFirstName.getText()), Formats.STRING.parseValue(this.txtLastName.getText()), Formats.STRING.parseValue(this.txtEmail.getText()), Formats.STRING.parseValue(this.txtPhone.getText()), Formats.STRING.parseValue(this.txtPhone2.getText()), Formats.STRING.parseValue(this.txtFax.getText()), this.m_jNotes.getText(), this.m_jVisible.isSelected(), Formats.TIMESTAMP.parseValue(this.txtCurdate.getText()), Formats.CURRENCY.parseValue(this.txtCurdebt.getText()), this.m_jImage.getImage(), this.m_jVip.isSelected(), Formats.CURRENCY.parseValue(this.txtDiscount.getText(), 0.0), this.m_jDv.getText(), this.getComboValue(this.m_jDocType), this.getComboValue(this.m_jPersonType), this.getComboValue(this.m_jLiability), this.getComboValue(this.m_jMunicipalityCode), this.getComboValue(this.m_jRegimeCode), this.getAppView()};
        return customer;
    }

    public AppView getAppView() {
        return this.appView;
    }

    public void setAppView(AppView appView) {
        this.appView = appView;
    }

    @Override
    public JComponent getComponent() {
        return this;
    }

    @Override
    public String getTitle() {
        return AppLocal.getIntString("Menu.CustomersManagement");
    }

    private List<CustomerTransaction> getTransactionOfName(String cId) {
        try {
            this.customerTransactionList = this.dlSales.getCustomersTransactionList(cId);
        }
        catch (BasicException ex) {
            Logger.getLogger(CustomersView.class.getName()).log(Level.SEVERE, null, ex);
        }
        ArrayList<CustomerTransaction> customerList = new ArrayList<CustomerTransaction>();
        for (CustomerTransaction customerTransaction : this.customerTransactionList) {
            String customerId = customerTransaction.getCustomerId();
            if (!customerId.equals(cId)) continue;
            customerList.add(customerTransaction);
        }
        this.txtCurdate.repaint();
        this.txtCurdebt.repaint();
        this.repaint();
        this.refresh();
        return customerList;
    }

    private void initComponents() {
        this.jLabel7 = new JLabel();
        this.m_jTaxID = new JTextField();
        this.jLabel8 = new JLabel();
        this.m_jSearchkey = new JTextField();
        this.jLabel3 = new JLabel();
        this.m_jName = new JTextField();
        this.jLabel5 = new JLabel();
        this.jcard = new JTextField();
        this.jBtnCreateCard = new JButton();
        this.jBtnClearCard = new JButton();
        this.jLabel9 = new JLabel();
        this.m_jCategory = new JComboBox();
        this.jLabel4 = new JLabel();
        this.m_jVisible = new JCheckBox();
        this.jLabel1 = new JLabel();
        this.txtMaxdebt = new JTextField();
        this.jLabel2 = new JLabel();
        this.txtCurdebt = new JTextField();
        this.jLabel6 = new JLabel();
        this.txtCurdate = new JTextField();
        this.jTabbedPane1 = new JTabbedPane();
        this.jPanel1 = new JPanel();
        this.jLabel19 = new JLabel();
        this.txtFirstName = new JTextField();
        this.jLabel15 = new JLabel();
        this.txtLastName = new JTextField();
        this.jLabel16 = new JLabel();
        this.txtEmail = new JTextField();
        this.jLabel17 = new JLabel();
        this.txtPhone = new JTextField();
        this.jLabel18 = new JLabel();
        this.txtPhone2 = new JTextField();
        this.jLabel14 = new JLabel();
        this.txtFax = new JTextField();
        this.webBtnMail = new JButton();
        this.jPanel2 = new JPanel();
        this.jLabel13 = new JLabel();
        this.txtAddress = new JTextField();
        this.jLabel20 = new JLabel();
        this.txtCountry = new JTextField();
        this.jLabel21 = new JLabel();
        this.txtAddress2 = new JTextField();
        this.jLabel22 = new JLabel();
        this.txtPostal = new JTextField();
        this.jLabel23 = new JLabel();
        this.txtCity = new JTextField();
        this.jLabel24 = new JLabel();
        this.txtRegion = new JTextField();
        this.jPanel4 = new JPanel();
        this.jBtnShowTrans = new JButton();
        this.jScrollPane3 = new JScrollPane();
        this.jTableCustomerTransactions = new JTable();
        this.jPanel5 = new JPanel();
        this.m_jImage = new JImageEditor();
        this.jLabel34 = new JLabel();
        this.jPanel3 = new JPanel();
        this.jScrollPane1 = new JScrollPane();
        this.m_jNotes = new JTextArea();
        this.jLblVIP = new JLabel();
        this.m_jVip = new JCheckBox();
        this.jLblDiscount = new JLabel();
        this.txtDiscount = new JTextField();
        this.jLblDiscountpercent = new JLabel();
        this.setFont(new Font("Arial", 0, 14));
        this.setPreferredSize(new Dimension(1000, 600));
        this.jLabel7.setFont(new Font("Arial", 0, 14));
        this.jLabel7.setText(AppLocal.getIntString("label.taxid"));
        this.jLabel7.setMaximumSize(new Dimension(150, 30));
        this.jLabel7.setMinimumSize(new Dimension(140, 25));
        this.jLabel7.setPreferredSize(new Dimension(150, 30));
        this.m_jTaxID.setFont(new Font("Arial", 0, 14));
        this.m_jTaxID.setPreferredSize(new Dimension(150, 30));
        this.jLabel8.setFont(new Font("Arial", 0, 14));
        this.jLabel8.setText(AppLocal.getIntString("label.searchkeym"));
        this.jLabel8.setPreferredSize(new Dimension(100, 30));
        this.m_jSearchkey.setFont(new Font("Arial", 0, 14));
        this.m_jSearchkey.setPreferredSize(new Dimension(140, 30));
        this.jLabel3.setFont(new Font("Arial", 0, 14));
        this.jLabel3.setText(AppLocal.getIntString("label.namem"));
        this.jLabel3.setMaximumSize(new Dimension(140, 25));
        this.jLabel3.setMinimumSize(new Dimension(140, 25));
        this.jLabel3.setPreferredSize(new Dimension(150, 30));
        this.m_jName.setFont(new Font("Arial", 0, 14));
        this.m_jName.setPreferredSize(new Dimension(410, 30));
        this.jLabel5.setFont(new Font("Arial", 0, 14));
        this.jLabel5.setText(AppLocal.getIntString("label.card"));
        this.jLabel5.setMaximumSize(new Dimension(140, 25));
        this.jLabel5.setMinimumSize(new Dimension(140, 25));
        this.jLabel5.setPreferredSize(new Dimension(150, 30));
        this.jcard.setFont(new Font("Arial", 0, 14));
        this.jcard.setPreferredSize(new Dimension(150, 30));
        this.jBtnCreateCard.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/encrypted.png")));
        this.jBtnCreateCard.setToolTipText("Create Key");
        this.jBtnCreateCard.setMaximumSize(new Dimension(64, 32));
        this.jBtnCreateCard.setMinimumSize(new Dimension(64, 32));
        this.jBtnCreateCard.setPreferredSize(new Dimension(64, 45));
        this.jBtnCreateCard.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                CustomersView.this.jBtnCreateCardActionPerformed(evt);
            }
        });
        this.jBtnClearCard.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/fileclose.png")));
        this.jBtnClearCard.setToolTipText("Clear Key");
        this.jBtnClearCard.setMaximumSize(new Dimension(64, 32));
        this.jBtnClearCard.setMinimumSize(new Dimension(64, 32));
        this.jBtnClearCard.setPreferredSize(new Dimension(64, 45));
        this.jBtnClearCard.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                CustomersView.this.jBtnClearCardActionPerformed(evt);
            }
        });
        this.jLabel9.setFont(new Font("Arial", 0, 14));
        this.jLabel9.setText(AppLocal.getIntString("label.custtaxcategory"));
        this.jLabel9.setMaximumSize(new Dimension(140, 25));
        this.jLabel9.setMinimumSize(new Dimension(140, 25));
        this.jLabel9.setPreferredSize(new Dimension(150, 30));
        this.m_jCategory.setFont(new Font("Arial", 0, 14));
        this.m_jCategory.setPreferredSize(new Dimension(180, 30));
        this.jLabel4.setFont(new Font("Arial", 0, 14));
        this.jLabel4.setText(AppLocal.getIntString("label.visible"));
        this.jLabel4.setMaximumSize(new Dimension(140, 25));
        this.jLabel4.setMinimumSize(new Dimension(140, 25));
        this.jLabel4.setPreferredSize(new Dimension(150, 30));
        this.m_jVisible.setFont(new Font("Arial", 0, 12));
        this.m_jVisible.setHorizontalAlignment(0);
        this.m_jVisible.setPreferredSize(new Dimension(30, 30));
        this.jLabel1.setFont(new Font("Arial", 0, 14));
        this.jLabel1.setText(AppLocal.getIntString("label.maxdebt"));
        this.jLabel1.setMaximumSize(new Dimension(140, 25));
        this.jLabel1.setMinimumSize(new Dimension(140, 25));
        this.jLabel1.setPreferredSize(new Dimension(150, 30));
        this.txtMaxdebt.setFont(new Font("Arial", 0, 14));
        this.txtMaxdebt.setHorizontalAlignment(4);
        this.txtMaxdebt.setPreferredSize(new Dimension(150, 30));
        this.jLabel2.setFont(new Font("Arial", 0, 14));
        this.jLabel2.setText(AppLocal.getIntString("label.curdebt"));
        this.jLabel2.setMaximumSize(new Dimension(140, 25));
        this.jLabel2.setMinimumSize(new Dimension(140, 25));
        this.jLabel2.setPreferredSize(new Dimension(150, 30));
        this.txtCurdebt.setEditable(false);
        this.txtCurdebt.setFont(new Font("Arial", 0, 14));
        this.txtCurdebt.setHorizontalAlignment(4);
        this.txtCurdebt.setPreferredSize(new Dimension(150, 30));
        this.jLabel6.setFont(new Font("Arial", 0, 14));
        this.jLabel6.setHorizontalAlignment(0);
        this.jLabel6.setText(AppLocal.getIntString("label.curdate"));
        this.jLabel6.setPreferredSize(new Dimension(150, 30));
        this.txtCurdate.setEditable(false);
        this.txtCurdate.setFont(new Font("Arial", 0, 14));
        this.txtCurdate.setHorizontalAlignment(0);
        this.txtCurdate.setPreferredSize(new Dimension(150, 30));
        this.jTabbedPane1.setFont(new Font("Arial", 0, 14));
        this.jTabbedPane1.setPreferredSize(new Dimension(650, 300));
        this.jPanel1.setFont(new Font("Arial", 0, 12));
        this.jLabel19.setFont(new Font("Arial", 0, 14));
        this.jLabel19.setText(AppLocal.getIntString("label.firstname"));
        this.jLabel19.setAlignmentX(0.5f);
        this.jLabel19.setPreferredSize(new Dimension(150, 30));
        this.txtFirstName.setFont(new Font("Arial", 0, 14));
        this.txtFirstName.setPreferredSize(new Dimension(300, 30));
        this.jLabel15.setFont(new Font("Arial", 0, 14));
        this.jLabel15.setText(AppLocal.getIntString("label.lastname"));
        this.jLabel15.setPreferredSize(new Dimension(150, 30));
        this.txtLastName.setFont(new Font("Arial", 0, 14));
        this.txtLastName.setPreferredSize(new Dimension(300, 30));
        this.jLabel16.setFont(new Font("Arial", 0, 14));
        this.jLabel16.setText(AppLocal.getIntString("label.email"));
        this.jLabel16.setPreferredSize(new Dimension(150, 30));
        this.txtEmail.setFont(new Font("Arial", 0, 14));
        this.txtEmail.setPreferredSize(new Dimension(300, 30));
        this.jLabel17.setFont(new Font("Arial", 0, 14));
        this.jLabel17.setText(AppLocal.getIntString("label.phone"));
        this.jLabel17.setPreferredSize(new Dimension(150, 30));
        this.txtPhone.setFont(new Font("Arial", 0, 14));
        this.txtPhone.setPreferredSize(new Dimension(300, 30));
        this.jLabel18.setFont(new Font("Arial", 0, 14));
        this.jLabel18.setText(AppLocal.getIntString("label.phone2"));
        this.jLabel18.setPreferredSize(new Dimension(150, 30));
        this.txtPhone2.setFont(new Font("Arial", 0, 14));
        this.txtPhone2.setPreferredSize(new Dimension(300, 30));
        this.jLabel14.setFont(new Font("Arial", 0, 14));
        this.jLabel14.setText(AppLocal.getIntString("label.fax"));
        this.jLabel14.setPreferredSize(new Dimension(150, 30));
        this.txtFax.setFont(new Font("Arial", 0, 14));
        this.txtFax.setPreferredSize(new Dimension(300, 30));
        this.webBtnMail.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/mail24.png")));
        ResourceBundle bundle = ResourceBundle.getBundle("pos_messages");
        this.webBtnMail.setText(bundle.getString("button.email"));
        this.webBtnMail.setFont(new Font("Arial", 0, 12));
        this.webBtnMail.setPreferredSize(new Dimension(90, 30));
        this.webBtnMail.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                CustomersView.this.webBtnMailActionPerformed(evt);
            }
        });
        GroupLayout jPanel1Layout = new GroupLayout(this.jPanel1);
        this.jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jLabel15, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.txtLastName, -2, -1, -2)).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jLabel14, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.txtFax, -2, -1, -2)).addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel16, -2, -1, -2).addComponent(this.jLabel17, -2, -1, -2).addComponent(this.jLabel18, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.txtPhone, -2, -1, -2).addComponent(this.txtEmail, -2, -1, -2).addComponent(this.txtPhone2, -2, -1, -2))).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jLabel19, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.txtFirstName, -2, -1, -2))).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.webBtnMail, -2, 89, -2).addContainerGap(76, Short.MAX_VALUE)));
        jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel19, -2, -1, -2).addComponent(this.txtFirstName, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel15, -2, -1, -2).addComponent(this.txtLastName, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel16, -2, -1, -2).addComponent(this.txtEmail, -2, -1, -2).addComponent(this.webBtnMail, -2, 31, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel17, -2, -1, -2).addComponent(this.txtPhone, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel18, -2, -1, -2).addComponent(this.txtPhone2, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.txtFax, -2, -1, -2).addComponent(this.jLabel14, -2, -1, -2)).addGap(57, 57, 57)));
        this.jTabbedPane1.addTab(AppLocal.getIntString("label.contact"), this.jPanel1);
        this.jLabel13.setFont(new Font("Arial", 0, 14));
        this.jLabel13.setText(AppLocal.getIntString("label.address"));
        this.jLabel13.setPreferredSize(new Dimension(150, 30));
        this.txtAddress.setFont(new Font("Arial", 0, 14));
        this.txtAddress.setPreferredSize(new Dimension(300, 30));
        this.jLabel20.setFont(new Font("Arial", 0, 14));
        this.jLabel20.setText(AppLocal.getIntString("label.country"));
        this.jLabel20.setPreferredSize(new Dimension(150, 30));
        this.txtCountry.setFont(new Font("Arial", 0, 14));
        this.txtCountry.setPreferredSize(new Dimension(300, 30));
        this.jLabel21.setFont(new Font("Arial", 0, 14));
        this.jLabel21.setText(AppLocal.getIntString("label.address2"));
        this.jLabel21.setPreferredSize(new Dimension(150, 30));
        this.txtAddress2.setFont(new Font("Arial", 0, 14));
        this.txtAddress2.setPreferredSize(new Dimension(300, 30));
        this.jLabel22.setFont(new Font("Arial", 0, 14));
        this.jLabel22.setText(AppLocal.getIntString("label.postal"));
        this.jLabel22.setPreferredSize(new Dimension(110, 30));
        this.txtPostal.setFont(new Font("Arial", 0, 14));
        this.txtPostal.setPreferredSize(new Dimension(0, 30));
        this.jLabel23.setFont(new Font("Arial", 0, 14));
        this.jLabel23.setText(AppLocal.getIntString("label.city"));
        this.jLabel23.setPreferredSize(new Dimension(150, 30));
        this.txtCity.setFont(new Font("Arial", 0, 14));
        this.txtCity.setPreferredSize(new Dimension(300, 30));
        this.jLabel24.setFont(new Font("Arial", 0, 14));
        this.jLabel24.setText(AppLocal.getIntString("label.region"));
        this.jLabel24.setPreferredSize(new Dimension(150, 30));
        this.txtRegion.setFont(new Font("Arial", 0, 14));
        this.txtRegion.setPreferredSize(new Dimension(300, 30));
        GroupLayout jPanel2Layout = new GroupLayout(this.jPanel2);
        this.jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel2Layout.createSequentialGroup().addContainerGap().addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.jLabel20, -2, -1, -2).addComponent(this.jLabel24, -2, -1, -2).addComponent(this.jLabel23, -2, -1, -2).addComponent(this.jLabel21, -2, -1, -2).addComponent(this.jLabel13, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.txtCity, -1, -1, Short.MAX_VALUE).addComponent(this.txtAddress, -1, -1, Short.MAX_VALUE).addComponent(this.txtAddress2, -1, -1, Short.MAX_VALUE).addComponent(this.txtRegion, -1, -1, Short.MAX_VALUE).addComponent(this.txtCountry, -1, -1, Short.MAX_VALUE)).addGap(18, 18, 18).addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.txtPostal, -2, 89, -2).addComponent(this.jLabel22, -2, -1, -2)).addContainerGap()));
        jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel2Layout.createSequentialGroup().addContainerGap().addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel13, -2, -1, -2).addComponent(this.txtAddress, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel21, -2, -1, -2).addComponent(this.txtAddress2, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel23, -2, -1, -2).addComponent(this.txtCity, -2, -1, -2).addComponent(this.jLabel22, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel24, -2, -1, -2).addComponent(this.txtRegion, -2, -1, -2).addComponent(this.txtPostal, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel20, -2, -1, -2).addComponent(this.txtCountry, -2, -1, -2)).addContainerGap()));
        this.jTabbedPane1.addTab(AppLocal.getIntString("label.location"), this.jPanel2);
        this.jPanel4.setFont(new Font("Arial", 0, 12));
        this.jPanel4.setPreferredSize(new Dimension(535, 0));
        this.jBtnShowTrans.setFont(new Font("Arial", 0, 12));
        this.jBtnShowTrans.setText(bundle.getString("button.CustomerTrans"));
        this.jBtnShowTrans.setToolTipText("");
        this.jBtnShowTrans.setPreferredSize(new Dimension(140, 30));
        this.jBtnShowTrans.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                CustomersView.this.jBtnShowTransActionPerformed(evt);
            }
        });
        this.jTableCustomerTransactions.setFont(new Font("Arial", 0, 12));
        this.jTableCustomerTransactions.setModel(new DefaultTableModel(new Object[][]{{null, null, null, null, null}, {null, null, null, null, null}, {null, null, null, null, null}, {null, null, null, null, null}, {null, null, null, null, null}, {null, null, null, null, null}, {null, null, null, null, null}, {null, null, null, null, null}, {null, null, null, null, null}, {null, null, null, null, null}}, new String[]{"TicketID", "Date", "Product", "Qty", "Total"}));
        this.jTableCustomerTransactions.setGridColor(new Color(102, 204, 255));
        this.jTableCustomerTransactions.setOpaque(false);
        this.jTableCustomerTransactions.setPreferredSize(new Dimension(375, 200));
        this.jTableCustomerTransactions.setRowHeight(20);
        this.jTableCustomerTransactions.setShowVerticalLines(false);
        this.jScrollPane3.setViewportView(this.jTableCustomerTransactions);
        GroupLayout jPanel4Layout = new GroupLayout(this.jPanel4);
        this.jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel4Layout.createSequentialGroup().addGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel4Layout.createSequentialGroup().addGap(492, 492, 492).addComponent(this.jBtnShowTrans, -2, -1, -2)).addGroup(jPanel4Layout.createSequentialGroup().addContainerGap().addComponent(this.jScrollPane3, -2, 622, -2))).addGap(23, 23, 23)));
        jPanel4Layout.setVerticalGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel4Layout.createSequentialGroup().addComponent(this.jBtnShowTrans, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jScrollPane3, -2, 217, -2).addContainerGap(-1, Short.MAX_VALUE)));
        this.jTabbedPane1.addTab(bundle.getString("label.Transactions"), this.jPanel4);
        this.m_jImage.setFont(new Font("Arial", 0, 12));
        this.m_jImage.setPreferredSize(new Dimension(300, 250));
        this.jLabel34.setFont(new Font("Arial", 0, 14));
        this.jLabel34.setText(bundle.getString("label.imagesize"));
        this.jLabel34.setPreferredSize(new Dimension(500, 30));
        GroupLayout jPanel5Layout = new GroupLayout(this.jPanel5);
        this.jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(jPanel5Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel5Layout.createSequentialGroup().addGroup(jPanel5Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel5Layout.createSequentialGroup().addContainerGap().addComponent(this.jLabel34, -2, -1, -2)).addGroup(jPanel5Layout.createSequentialGroup().addGap(86, 86, 86).addComponent(this.m_jImage, -2, -1, -2))).addContainerGap(135, Short.MAX_VALUE)));
        jPanel5Layout.setVerticalGroup(jPanel5Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup().addContainerGap().addComponent(this.m_jImage, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addComponent(this.jLabel34, -2, -1, -2).addContainerGap()));
        this.jTabbedPane1.addTab(bundle.getString("label.photo"), this.jPanel5);
        this.m_jNotes.setFont(new Font("Arial", 0, 14));
        this.m_jNotes.setPreferredSize(new Dimension(0, 0));
        this.jScrollPane1.setViewportView(this.m_jNotes);
        GroupLayout jPanel3Layout = new GroupLayout(this.jPanel3);
        this.jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel3Layout.createSequentialGroup().addContainerGap().addComponent(this.jScrollPane1, -2, 458, -2).addContainerGap(-1, Short.MAX_VALUE)));
        jPanel3Layout.setVerticalGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel3Layout.createSequentialGroup().addContainerGap().addComponent(this.jScrollPane1, -1, 247, Short.MAX_VALUE).addContainerGap()));
        this.jTabbedPane1.addTab(AppLocal.getIntString("label.notes"), this.jPanel3);
        this.jPanelFE = new JPanel();
        this.jLabelDv = new JLabel();
        this.m_jDv = new JTextField();
        this.jLabelDocType = new JLabel();
        this.m_jDocType = new JComboBox();
        this.jLabelPersonType = new JLabel();
        this.m_jPersonType = new JComboBox();
        this.jLabelLiability = new JLabel();
        this.m_jLiability = new JComboBox();
        this.jLabelMunicipality = new JLabel();
        this.m_jMunicipalityCode = new JComboBox<String>(MunicipiosColombia.LISTA);
        this.m_jMunicipalityCode.setEditable(true);
        this.jLabelRegime = new JLabel();
        this.m_jRegimeCode = new JComboBox();
        this.jPanelFE.setLayout(null);
        this.jLabelDv.setFont(new Font("Arial", 0, 14));
        this.jLabelDv.setText("DV (Digito Verif.)");
        this.jLabelDv.setBounds(20, 20, 150, 30);
        this.jPanelFE.add(this.jLabelDv);
        this.m_jDv.setFont(new Font("Arial", 0, 14));
        this.m_jDv.setBounds(180, 20, 50, 30);
        this.jPanelFE.add(this.m_jDv);
        this.jLabelDocType.setFont(new Font("Arial", 0, 14));
        this.jLabelDocType.setText("Tipo Documento");
        this.jLabelDocType.setBounds(20, 60, 150, 30);
        this.jPanelFE.add(this.jLabelDocType);
        this.m_jDocType.setFont(new Font("Arial", 0, 14));
        this.m_jDocType.setModel(new DefaultComboBoxModel<String>(new String[]{"13 - Cedula", "31 - NIT", "22 - Cedula Extranjeria", "41 - Pasaporte", "42 - Doc. Extranjero"}));
        this.m_jDocType.setBounds(180, 60, 250, 30);
        this.jPanelFE.add(this.m_jDocType);
        this.jLabelPersonType.setFont(new Font("Arial", 0, 14));
        this.jLabelPersonType.setText("Tipo Persona");
        this.jLabelPersonType.setBounds(20, 100, 150, 30);
        this.jPanelFE.add(this.jLabelPersonType);
        this.m_jPersonType.setFont(new Font("Arial", 0, 14));
        this.m_jPersonType.setModel(new DefaultComboBoxModel<String>(new String[]{"2 - Natural", "1 - Juridica"}));
        this.m_jPersonType.setBounds(180, 100, 250, 30);
        this.jPanelFE.add(this.m_jPersonType);
        this.jLabelLiability.setFont(new Font("Arial", 0, 14));
        this.jLabelLiability.setText("Responsabilidad");
        this.jLabelLiability.setBounds(20, 140, 150, 30);
        this.jPanelFE.add(this.jLabelLiability);
        this.m_jLiability.setFont(new Font("Arial", 0, 14));
        this.m_jLiability.setModel(new DefaultComboBoxModel<String>(new String[]{"R-99-PN", "O-13", "O-15", "O-23", "O-47"}));
        this.m_jLiability.setBounds(180, 140, 250, 30);
        this.jPanelFE.add(this.m_jLiability);
        this.jLabelMunicipality.setFont(new Font("Arial", 0, 14));
        this.jLabelMunicipality.setText("Cod. Municipio");
        this.jLabelMunicipality.setBounds(20, 180, 150, 30);
        this.jPanelFE.add(this.jLabelMunicipality);
        this.m_jMunicipalityCode.setFont(new Font("Arial", 0, 14));
        this.m_jMunicipalityCode.setBounds(180, 180, 100, 30);
        this.jPanelFE.add(this.m_jMunicipalityCode);
        this.jLabelRegime.setFont(new Font("Arial", 0, 14));
        this.jLabelRegime.setText("Regimen");
        this.jLabelRegime.setBounds(20, 220, 150, 30);
        this.jPanelFE.add(this.jLabelRegime);
        this.m_jRegimeCode.setFont(new Font("Arial", 0, 14));
        this.m_jRegimeCode.setModel(new DefaultComboBoxModel<String>(new String[]{"49 - No Responsable de IVA", "48 - Responsable de IVA"}));
        this.m_jRegimeCode.setBounds(180, 220, 250, 30);
        this.jPanelFE.add(this.m_jRegimeCode);
        this.jTabbedPane1.addTab("Facturacion Electronica", this.jPanelFE);
        this.jLblVIP.setFont(new Font("Arial", 0, 14));
        this.jLblVIP.setHorizontalAlignment(4);
        this.jLblVIP.setText(AppLocal.getIntString("label.vip"));
        this.jLblVIP.setPreferredSize(new Dimension(50, 30));
        this.m_jVip.setFont(new Font("Arial", 0, 12));
        this.m_jVip.setForeground(new Color(0, 188, 243));
        this.m_jVip.setPreferredSize(new Dimension(21, 30));
        this.m_jVip.addChangeListener(new ChangeListener(){

            @Override
            public void stateChanged(ChangeEvent evt) {
                CustomersView.this.m_jVipnone(evt);
            }
        });
        this.m_jVip.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                CustomersView.this.m_jVipActionPerformed(evt);
            }
        });
        this.jLblDiscount.setFont(new Font("Arial", 0, 14));
        this.jLblDiscount.setHorizontalAlignment(4);
        this.jLblDiscount.setText(AppLocal.getIntString("label.discount"));
        this.jLblDiscount.setPreferredSize(new Dimension(70, 30));
        this.txtDiscount.setFont(new Font("Arial", 0, 14));
        this.txtDiscount.setHorizontalAlignment(4);
        this.txtDiscount.setPreferredSize(new Dimension(50, 30));
        this.jLblDiscountpercent.setFont(new Font("Arial", 0, 14));
        this.jLblDiscountpercent.setText("%");
        this.jLblDiscountpercent.setPreferredSize(new Dimension(15, 30));
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.jLabel2, -2, -1, -2).addComponent(this.jLabel1, -1, -1, -2).addComponent(this.jLabel3, -2, -1, -2).addComponent(this.jLabel7, -1, -1, Short.MAX_VALUE).addComponent(this.jLabel5, -1, -1, -2).addComponent(this.jLabel9, -1, -1, -2)).addComponent(this.jLabel4, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.m_jCategory, -2, -1, -2).addComponent(this.txtMaxdebt, -2, -1, -2).addComponent(this.txtCurdebt, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.txtCurdate, -2, -1, -2).addComponent(this.jLabel6, -2, -1, -2).addGroup(layout.createSequentialGroup().addComponent(this.jLblVIP, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.m_jVip, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jLblDiscount, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.txtDiscount, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jLblDiscountpercent, -2, -1, -2)))).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addComponent(this.m_jName, GroupLayout.Alignment.LEADING, -1, -1, Short.MAX_VALUE).addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jcard, -2, -1, -2).addComponent(this.m_jTaxID, -2, -1, -2).addComponent(this.m_jVisible, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup().addComponent(this.jLabel8, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.m_jSearchkey, -1, -1, -2)).addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup().addComponent(this.jBtnCreateCard, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jBtnClearCard, -2, -1, -2))))))).addComponent(this.jTabbedPane1, -2, -1, -2)).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel7, -2, -1, -2).addComponent(this.jLabel8, -2, -1, -2).addComponent(this.m_jSearchkey, -2, -1, -2).addComponent(this.m_jTaxID, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel3, -2, -1, -2).addComponent(this.m_jName, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.jBtnClearCard, -2, -1, -2).addComponent(this.jBtnCreateCard, -2, -1, -2)).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel5, -2, -1, -2).addComponent(this.jcard, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel4, -2, -1, -2).addComponent(this.m_jVisible, -2, -1, -2)))).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.jLabel9, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jLabel1, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jLabel2, -2, -1, -2)).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLblDiscount, -2, -1, -2).addComponent(this.txtDiscount, -2, -1, -2).addComponent(this.jLblDiscountpercent, -2, -1, -2)).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.m_jVip, -2, -1, -2).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.m_jCategory, -2, -1, -2).addComponent(this.jLblVIP, -2, -1, -2)))).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.txtMaxdebt, -2, -1, -2).addComponent(this.jLabel6, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.txtCurdebt, -2, -1, -2).addComponent(this.txtCurdate, -2, -1, -2)))).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jTabbedPane1, -2, -1, -2).addContainerGap(-1, Short.MAX_VALUE)));
    }

    private void jBtnCreateCardActionPerformed(ActionEvent evt) {
        if (JOptionPane.showConfirmDialog(this, AppLocal.getIntString("message.cardnew"), AppLocal.getIntString("title.editor"), 0, 3) == 0) {
            this.jcard.setText("c" + StringUtils.getCardNumber());
            this.m_Dirty.setDirty(true);
        }
    }

    private void jBtnClearCardActionPerformed(ActionEvent evt) {
        if (JOptionPane.showConfirmDialog(this, AppLocal.getIntString("message.cardremove"), AppLocal.getIntString("title.editor"), 0, 3) == 0) {
            this.jcard.setText(null);
            this.m_Dirty.setDirty(true);
        }
    }

    private void webBtnMailActionPerformed(ActionEvent evt) {
        if (!"".equals(this.txtEmail.getText())) {
            Desktop desktop;
            if (Desktop.isDesktopSupported() && (desktop = Desktop.getDesktop()).isSupported(Desktop.Action.MAIL)) {
                URI mailto = null;
                try {
                    mailto = new URI("mailto:" + this.txtEmail.getText());
                }
                catch (URISyntaxException ex) {
                    Logger.getLogger(CustomersView.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    desktop.mail(mailto);
                }
                catch (IOException ex) {
                    Logger.getLogger(CustomersView.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                JOptionPane.showMessageDialog(this, AppLocal.getIntString("message.email"), "Email", 1);
            }
        }
    }

    private void jBtnShowTransActionPerformed(ActionEvent evt) {
        String cId = this.m_oId.toString();
        if (cId != null) {
            this.transactionModel = new TransactionTableModel(this.getTransactionOfName(cId));
            this.jTableCustomerTransactions.setModel(this.transactionModel);
            if (this.transactionModel.getRowCount() > 0) {
                this.jTableCustomerTransactions.setVisible(true);
            } else {
                this.jTableCustomerTransactions.setVisible(false);
                JOptionPane.showMessageDialog(null, "No Transactions for this Customer", "Transactions", 1);
            }
            this.resetTranxTable();
        }
    }

    private void m_jVipnone(ChangeEvent evt) {
    }

    private void m_jVipActionPerformed(ActionEvent evt) {
    }

    class TransactionTableModel
    extends AbstractTableModel {
        String tkt = AppLocal.getIntString("label.tblHeaderCol1");
        String dte = AppLocal.getIntString("label.tblHeaderCol2");
        String prd = AppLocal.getIntString("label.tblHeaderCol3");
        String qty = AppLocal.getIntString("label.tblHeaderCol4");
        String ttl = AppLocal.getIntString("label.tblHeaderCol5");
        List<CustomerTransaction> transactionList;
        String[] columnNames = new String[]{this.tkt, this.dte, this.prd, this.qty, this.ttl};
        public Double Tamount;

        public TransactionTableModel(List<CustomerTransaction> list) {
            this.transactionList = list;
        }

        @Override
        public int getColumnCount() {
            return 5;
        }

        @Override
        public int getRowCount() {
            return this.transactionList.size();
        }

        @Override
        public Object getValueAt(int row, int column) {
            CustomerTransaction customerTransaction = this.transactionList.get(row);
            CustomersView.this.jTableCustomerTransactions.setRowHeight(25);
            switch (column) {
                case 0: {
                    return customerTransaction.getTicketId();
                }
                case 1: {
                    Date transactionDate = customerTransaction.getTransactionDate();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    String formattedDate = sdf.format(transactionDate);
                    return formattedDate;
                }
                case 2: {
                    return customerTransaction.getProductName();
                }
                case 3: {
                    return customerTransaction.getUnit();
                }
                case 4: {
                    Double amount = customerTransaction.getTotal();
                    DecimalFormat df = new DecimalFormat("#.##");
                    String formattedAmount = df.format(amount);
                    return formattedAmount;
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

