/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.suppliers;

import com.openbravo.basic.BasicException;
import com.openbravo.data.user.DirtyManager;
import com.openbravo.data.user.EditorRecord;
import com.openbravo.format.Formats;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.forms.BeanFactoryException;
import com.openbravo.pos.inventory.MovementReason;
import com.openbravo.pos.suppliers.DataLogicSuppliers;
import com.openbravo.pos.suppliers.SupplierInfo;
import com.openbravo.pos.suppliers.SupplierTransaction;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

public final class SuppliersView
extends JPanel
implements EditorRecord {
    private static final long serialVersionUID = 1L;
    private Object m_oId;
    private List<SupplierTransaction> supplierTransactionList;
    private TransactionTableModel transactionModel;
    private DirtyManager m_Dirty;
    private DataLogicSuppliers dlSuppliers;
    private AppView appView;
    private SupplierInfo supplierInfo;
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
    private JLabel jLabel4;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JLabel jLabel8;
    private JLabel jLabel9;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JPanel jPanel3;
    private JPanel jPanel4;
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane3;
    private JTabbedPane jTabbedPane1;
    private JTable jTableSupplierTransactions;
    private JTextField m_jName;
    private JTextArea m_jNotes;
    private JTextField m_jSearchkey;
    private JTextField m_jTaxID;
    private JTextField m_jVATID;
    private JCheckBox m_jVisible;
    private JTextField txtAddress;
    private JTextField txtAddress2;
    private JTextField txtCity;
    private JTextField txtCountry;
    private JTextField txtCurdate;
    private JTextField txtCurdebt;
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

    public SuppliersView(AppView app, DirtyManager dirty) {
        try {
            this.setAppView(app);
            this.dlSuppliers = (DataLogicSuppliers)app.getBean("com.openbravo.pos.suppliers.DataLogicSuppliers");
            this.initComponents();
            this.m_Dirty = dirty;
            this.m_jTaxID.getDocument().addDocumentListener(dirty);
            this.m_jVATID.getDocument().addDocumentListener(dirty);
            this.m_jSearchkey.getDocument().addDocumentListener(dirty);
            this.m_jName.getDocument().addDocumentListener(dirty);
            this.m_jNotes.getDocument().addDocumentListener(dirty);
            this.txtMaxdebt.getDocument().addDocumentListener(dirty);
            this.m_jVisible.addActionListener(dirty);
            this.txtFirstName.getDocument().addDocumentListener(dirty);
            this.txtLastName.getDocument().addDocumentListener(dirty);
            this.txtEmail.getDocument().addDocumentListener(dirty);
            this.txtPhone.getDocument().addDocumentListener(dirty);
            this.txtPhone2.getDocument().addDocumentListener(dirty);
            this.txtFax.getDocument().addDocumentListener(dirty);
            this.txtAddress.getDocument().addDocumentListener(dirty);
            this.txtAddress2.getDocument().addDocumentListener(dirty);
            this.txtPostal.getDocument().addDocumentListener(dirty);
            this.txtCity.getDocument().addDocumentListener(dirty);
            this.txtRegion.getDocument().addDocumentListener(dirty);
            this.txtCountry.getDocument().addDocumentListener(dirty);
            this.init();
        }
        catch (BeanFactoryException ex) {
            Logger.getLogger(SuppliersView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void init() {
        this.writeValueEOF();
    }

    public void activate() throws BasicException {
        Object sId = null;
    }

    @Override
    public void refresh() {
    }

    @Override
    public void writeValueEOF() {
        this.m_oId = null;
        this.m_jTaxID.setText(null);
        this.m_jVATID.setText(null);
        this.m_jSearchkey.setText(null);
        this.m_jName.setText(null);
        this.m_jNotes.setText(null);
        this.txtMaxdebt.setText(null);
        this.txtCurdebt.setText(null);
        this.txtCurdate.setText(null);
        this.m_jVisible.setSelected(false);
        this.txtFirstName.setText(null);
        this.txtLastName.setText(null);
        this.txtEmail.setText(null);
        this.txtPhone.setText(null);
        this.txtPhone2.setText(null);
        this.txtFax.setText(null);
        this.txtAddress.setText(null);
        this.txtAddress2.setText(null);
        this.txtPostal.setText(null);
        this.txtCity.setText(null);
        this.txtRegion.setText(null);
        this.txtCountry.setText(null);
        this.m_jTaxID.setEnabled(false);
        this.m_jVATID.setEnabled(false);
        this.m_jSearchkey.setEnabled(false);
        this.m_jName.setEnabled(false);
        this.m_jNotes.setEnabled(false);
        this.txtMaxdebt.setEnabled(false);
        this.txtCurdebt.setEnabled(false);
        this.txtCurdate.setEnabled(false);
        this.m_jVisible.setEnabled(false);
        this.txtFirstName.setEnabled(false);
        this.txtLastName.setEnabled(false);
        this.txtEmail.setEnabled(false);
        this.txtPhone.setEnabled(false);
        this.txtPhone2.setEnabled(false);
        this.txtFax.setEnabled(false);
        this.txtAddress.setEnabled(false);
        this.txtAddress2.setEnabled(false);
        this.txtPostal.setEnabled(false);
        this.txtCity.setEnabled(false);
        this.txtRegion.setEnabled(false);
        this.txtCountry.setEnabled(false);
        this.jTableSupplierTransactions.setEnabled(false);
    }

    @Override
    public void writeValueInsert() {
        this.m_oId = null;
        this.m_jTaxID.setText(null);
        this.m_jVATID.setText(null);
        this.m_jSearchkey.setText(null);
        this.m_jName.setText(null);
        this.txtMaxdebt.setText(null);
        this.txtCurdebt.setText(null);
        this.txtCurdate.setText(null);
        this.txtFirstName.setText(null);
        this.txtLastName.setText(null);
        this.txtPhone.setText(null);
        this.txtPhone2.setText(null);
        this.txtEmail.setText(null);
        this.txtFax.setText(null);
        this.txtAddress.setText(null);
        this.txtAddress2.setText(null);
        this.txtPostal.setText(null);
        this.txtCity.setText(null);
        this.txtRegion.setText(null);
        this.txtCountry.setText(null);
        this.m_jNotes.setText(null);
        this.m_jVisible.setSelected(true);
        this.m_jTaxID.setEnabled(true);
        this.m_jVATID.setEnabled(true);
        this.m_jSearchkey.setEnabled(true);
        this.m_jName.setEnabled(true);
        this.txtFirstName.setEnabled(true);
        this.txtLastName.setEnabled(true);
        this.txtEmail.setEnabled(true);
        this.webBtnMail.setEnabled(true);
        this.txtPhone.setEnabled(true);
        this.txtPhone2.setEnabled(true);
        this.txtFax.setEnabled(true);
        this.txtAddress.setEnabled(true);
        this.txtAddress2.setEnabled(true);
        this.txtPostal.setEnabled(true);
        this.txtCity.setEnabled(true);
        this.txtRegion.setEnabled(true);
        this.txtCountry.setEnabled(true);
        this.m_jNotes.setEnabled(true);
        this.txtMaxdebt.setEnabled(true);
        this.txtCurdebt.setEnabled(true);
        this.txtCurdate.setEnabled(true);
        this.m_jVisible.setEnabled(true);
        this.jTableSupplierTransactions.setEnabled(false);
    }

    @Override
    public void writeValueDelete(Object value) {
        Object[] supplier = (Object[])value;
        this.m_oId = supplier[0];
        this.m_jSearchkey.setText((String)supplier[1]);
        this.m_jTaxID.setText((String)supplier[2]);
        this.m_jName.setText((String)supplier[3]);
        this.txtMaxdebt.setText(Formats.CURRENCY.formatValue(supplier[4]));
        this.txtAddress.setText(Formats.STRING.formatValue(supplier[5]));
        this.txtAddress2.setText(Formats.STRING.formatValue(supplier[6]));
        this.txtPostal.setText(Formats.STRING.formatValue(supplier[7]));
        this.txtCity.setText(Formats.STRING.formatValue(supplier[8]));
        this.txtRegion.setText(Formats.STRING.formatValue(supplier[9]));
        this.txtCountry.setText(Formats.STRING.formatValue(supplier[10]));
        this.txtFirstName.setText(Formats.STRING.formatValue(supplier[11]));
        this.txtLastName.setText(Formats.STRING.formatValue(supplier[12]));
        this.txtEmail.setText(Formats.STRING.formatValue(supplier[13]));
        this.txtPhone.setText(Formats.STRING.formatValue(supplier[14]));
        this.txtPhone2.setText(Formats.STRING.formatValue(supplier[15]));
        this.txtFax.setText(Formats.STRING.formatValue(supplier[16]));
        this.m_jNotes.setText((String)supplier[17]);
        this.m_jVisible.setSelected((Boolean)supplier[18]);
        this.txtCurdate.setText(Formats.DATE.formatValue(supplier[19]));
        this.txtCurdebt.setText(Formats.CURRENCY.formatValue(supplier[20]));
        this.m_jVATID.setText((String)supplier[21]);
        this.m_jTaxID.setEnabled(false);
        this.m_jVATID.setEnabled(false);
        this.m_jSearchkey.setEnabled(false);
        this.m_jName.setEnabled(false);
        this.m_jNotes.setEnabled(false);
        this.txtMaxdebt.setEnabled(false);
        this.txtCurdebt.setEnabled(false);
        this.txtCurdate.setEnabled(false);
        this.m_jVisible.setEnabled(false);
        this.txtFirstName.setEnabled(false);
        this.txtLastName.setEnabled(false);
        this.txtEmail.setEnabled(false);
        this.webBtnMail.setEnabled(false);
        this.txtPhone.setEnabled(false);
        this.txtPhone2.setEnabled(false);
        this.txtFax.setEnabled(false);
        this.txtAddress.setEnabled(false);
        this.txtAddress2.setEnabled(false);
        this.txtPostal.setEnabled(false);
        this.txtCity.setEnabled(false);
        this.txtRegion.setEnabled(false);
        this.txtCountry.setEnabled(false);
        this.transactionModel = new TransactionTableModel(this.getTransactionOfName((String)this.m_oId));
        this.jTableSupplierTransactions.setModel(this.transactionModel);
        this.jTableSupplierTransactions.setEnabled(false);
    }

    @Override
    public void writeValueEdit(Object value) {
        Object[] supplier = (Object[])value;
        this.m_oId = supplier[0];
        this.m_jSearchkey.setText((String)supplier[1]);
        this.m_jTaxID.setText((String)supplier[2]);
        this.m_jName.setText((String)supplier[3]);
        this.txtMaxdebt.setText(Formats.CURRENCY.formatValue(supplier[4]));
        this.txtAddress.setText(Formats.STRING.formatValue(supplier[5]));
        this.txtAddress2.setText(Formats.STRING.formatValue(supplier[6]));
        this.txtPostal.setText(Formats.STRING.formatValue(supplier[7]));
        this.txtCity.setText(Formats.STRING.formatValue(supplier[8]));
        this.txtRegion.setText(Formats.STRING.formatValue(supplier[9]));
        this.txtCountry.setText(Formats.STRING.formatValue(supplier[10]));
        this.txtFirstName.setText(Formats.STRING.formatValue(supplier[11]));
        this.txtLastName.setText(Formats.STRING.formatValue(supplier[12]));
        this.txtEmail.setText(Formats.STRING.formatValue(supplier[13]));
        this.txtPhone.setText(Formats.STRING.formatValue(supplier[14]));
        this.txtPhone2.setText(Formats.STRING.formatValue(supplier[15]));
        this.txtFax.setText(Formats.STRING.formatValue(supplier[16]));
        this.m_jNotes.setText((String)supplier[17]);
        this.m_jVisible.setSelected((Boolean)supplier[18]);
        this.txtCurdate.setText(Formats.DATE.formatValue(supplier[19]));
        this.txtCurdebt.setText(Formats.CURRENCY.formatValue(supplier[20]));
        this.m_jVATID.setText((String)supplier[21]);
        this.m_jSearchkey.setEnabled(true);
        this.m_jTaxID.setEnabled(true);
        this.m_jName.setEnabled(true);
        this.txtMaxdebt.setEnabled(true);
        this.txtAddress.setEnabled(true);
        this.txtAddress2.setEnabled(true);
        this.txtPostal.setEnabled(true);
        this.txtCity.setEnabled(true);
        this.txtRegion.setEnabled(true);
        this.txtCountry.setEnabled(true);
        this.txtFirstName.setEnabled(true);
        this.txtLastName.setEnabled(true);
        this.txtEmail.setEnabled(true);
        this.webBtnMail.setEnabled(true);
        this.txtPhone.setEnabled(true);
        this.txtPhone2.setEnabled(true);
        this.txtFax.setEnabled(true);
        this.m_jNotes.setEnabled(true);
        this.m_jVisible.setEnabled(true);
        this.txtCurdebt.setEnabled(true);
        this.txtCurdate.setEnabled(true);
        this.m_jVATID.setEnabled(true);
        this.jTableSupplierTransactions.setVisible(false);
        this.jTableSupplierTransactions.setEnabled(true);
        this.resetTranxTable();
        this.txtCurdate.repaint();
        this.txtCurdebt.repaint();
        this.jTableSupplierTransactions.repaint();
        this.repaint();
        this.refresh();
    }

    public void resetTranxTable() {
        this.jTableSupplierTransactions.getColumnModel().getColumn(0).setPreferredWidth(100);
        this.jTableSupplierTransactions.getColumnModel().getColumn(1).setPreferredWidth(225);
        this.jTableSupplierTransactions.getColumnModel().getColumn(2).setPreferredWidth(30);
        this.jTableSupplierTransactions.getColumnModel().getColumn(3).setPreferredWidth(50);
        this.jTableSupplierTransactions.getColumnModel().getColumn(4).setPreferredWidth(55);
        this.jTableSupplierTransactions.repaint();
    }

    @Override
    public Object createValue() throws BasicException {
        Object[] supplier = new Object[]{this.m_oId == null ? UUID.randomUUID().toString() : this.m_oId, this.m_jSearchkey.getText(), this.m_jTaxID.getText(), this.m_jName.getText(), Formats.CURRENCY.parseValue(this.txtMaxdebt.getText(), 0.0), Formats.STRING.parseValue(this.txtAddress.getText()), Formats.STRING.parseValue(this.txtAddress2.getText()), Formats.STRING.parseValue(this.txtPostal.getText()), Formats.STRING.parseValue(this.txtCity.getText()), Formats.STRING.parseValue(this.txtRegion.getText()), Formats.STRING.parseValue(this.txtCountry.getText()), Formats.STRING.parseValue(this.txtFirstName.getText()), Formats.STRING.parseValue(this.txtLastName.getText()), Formats.STRING.parseValue(this.txtEmail.getText()), Formats.STRING.parseValue(this.txtPhone.getText()), Formats.STRING.parseValue(this.txtPhone2.getText()), Formats.STRING.parseValue(this.txtFax.getText()), this.m_jNotes.getText(), this.m_jVisible.isSelected(), Formats.TIMESTAMP.parseValue(this.txtCurdate.getText()), Formats.CURRENCY.parseValue(this.txtCurdebt.getText()), this.m_jVATID.getText(), this.getAppView()};
        return supplier;
    }

    public AppView getAppView() {
        return this.appView;
    }

    public void setAppView(AppView appView) {
        this.appView = appView;
    }

    @Override
    public Component getComponent() {
        return this;
    }

    private List<SupplierTransaction> getTransactionOfName(String sId) {
        try {
            this.supplierTransactionList = this.dlSuppliers.getSuppliersTransactionList(sId);
        }
        catch (BasicException ex) {
            Logger.getLogger(SuppliersView.class.getName()).log(Level.SEVERE, null, ex);
        }
        ArrayList<SupplierTransaction> supplierList = new ArrayList<SupplierTransaction>();
        for (SupplierTransaction supplierTransaction : this.supplierTransactionList) {
            String supplierId = supplierTransaction.getSupplierId();
            if (!supplierId.equals(sId)) continue;
            supplierList.add(supplierTransaction);
        }
        this.txtCurdate.repaint();
        this.txtCurdebt.repaint();
        this.repaint();
        this.refresh();
        return supplierList;
    }

    private void initComponents() {
        this.jLabel7 = new JLabel();
        this.m_jTaxID = new JTextField();
        this.jLabel8 = new JLabel();
        this.m_jSearchkey = new JTextField();
        this.jLabel3 = new JLabel();
        this.m_jName = new JTextField();
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
        this.jTableSupplierTransactions = new JTable();
        this.jPanel3 = new JPanel();
        this.jScrollPane1 = new JScrollPane();
        this.m_jNotes = new JTextArea();
        this.jLabel9 = new JLabel();
        this.m_jVATID = new JTextField();
        this.setFont(new Font("Arial", 0, 14));
        this.setPreferredSize(new Dimension(1000, 600));
        this.jLabel7.setFont(new Font("Arial", 0, 14));
        this.jLabel7.setText(AppLocal.getIntString("label.suppliertaxid"));
        this.jLabel7.setMaximumSize(new Dimension(150, 30));
        this.jLabel7.setMinimumSize(new Dimension(140, 25));
        this.jLabel7.setPreferredSize(new Dimension(150, 30));
        this.m_jTaxID.setFont(new Font("Arial", 0, 14));
        this.m_jTaxID.setPreferredSize(new Dimension(150, 30));
        this.jLabel8.setFont(new Font("Arial", 0, 14));
        this.jLabel8.setText(AppLocal.getIntString("label.searchkeym"));
        this.m_jSearchkey.setFont(new Font("Arial", 0, 14));
        this.m_jSearchkey.setPreferredSize(new Dimension(0, 30));
        this.jLabel3.setFont(new Font("Arial", 0, 14));
        this.jLabel3.setText(AppLocal.getIntString("label.supplier"));
        this.jLabel3.setMaximumSize(new Dimension(140, 25));
        this.jLabel3.setMinimumSize(new Dimension(140, 25));
        this.jLabel3.setPreferredSize(new Dimension(150, 30));
        this.m_jName.setFont(new Font("Arial", 0, 14));
        this.m_jName.setPreferredSize(new Dimension(0, 30));
        this.jLabel4.setFont(new Font("Arial", 0, 14));
        this.jLabel4.setText(AppLocal.getIntString("label.visible"));
        this.jLabel4.setMaximumSize(new Dimension(140, 25));
        this.jLabel4.setMinimumSize(new Dimension(140, 25));
        this.jLabel4.setPreferredSize(new Dimension(150, 30));
        this.m_jVisible.setFont(new Font("Arial", 0, 12));
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
        this.jLabel19.setPreferredSize(new Dimension(0, 30));
        this.txtFirstName.setFont(new Font("Arial", 0, 14));
        this.txtFirstName.setPreferredSize(new Dimension(200, 30));
        this.jLabel15.setFont(new Font("Arial", 0, 14));
        this.jLabel15.setText(AppLocal.getIntString("label.lastname"));
        this.jLabel15.setPreferredSize(new Dimension(0, 30));
        this.txtLastName.setFont(new Font("Arial", 0, 14));
        this.txtLastName.setPreferredSize(new Dimension(200, 30));
        this.jLabel16.setFont(new Font("Arial", 0, 14));
        this.jLabel16.setText(AppLocal.getIntString("label.email"));
        this.jLabel16.setPreferredSize(new Dimension(0, 30));
        this.txtEmail.setFont(new Font("Arial", 0, 14));
        this.txtEmail.setPreferredSize(new Dimension(200, 30));
        this.jLabel17.setFont(new Font("Arial", 0, 14));
        this.jLabel17.setText(AppLocal.getIntString("label.phone"));
        this.jLabel17.setPreferredSize(new Dimension(0, 30));
        this.txtPhone.setFont(new Font("Arial", 0, 14));
        this.txtPhone.setPreferredSize(new Dimension(200, 30));
        this.jLabel18.setFont(new Font("Arial", 0, 14));
        this.jLabel18.setText(AppLocal.getIntString("label.phone2"));
        this.jLabel18.setPreferredSize(new Dimension(0, 30));
        this.txtPhone2.setFont(new Font("Arial", 0, 14));
        this.txtPhone2.setPreferredSize(new Dimension(200, 30));
        this.jLabel14.setFont(new Font("Arial", 0, 14));
        this.jLabel14.setText(AppLocal.getIntString("label.fax"));
        this.jLabel14.setPreferredSize(new Dimension(0, 30));
        this.txtFax.setFont(new Font("Arial", 0, 14));
        this.txtFax.setPreferredSize(new Dimension(200, 30));
        this.webBtnMail.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/mail24.png")));
        ResourceBundle bundle = ResourceBundle.getBundle("pos_messages");
        this.webBtnMail.setText(bundle.getString("button.email"));
        this.webBtnMail.setFont(new Font("Arial", 0, 12));
        this.webBtnMail.setPreferredSize(new Dimension(90, 30));
        this.webBtnMail.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                SuppliersView.this.webBtnMailActionPerformed(evt);
            }
        });
        GroupLayout jPanel1Layout = new GroupLayout(this.jPanel1);
        this.jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jLabel15, -2, 110, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.txtLastName, -2, -1, -2)).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jLabel14, -2, 110, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.txtFax, -2, -1, -2)).addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel16, -2, 110, -2).addComponent(this.jLabel17, -2, 110, -2).addComponent(this.jLabel18, -2, 110, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.txtPhone, -2, -1, -2).addComponent(this.txtEmail, -2, -1, -2).addComponent(this.txtPhone2, -2, -1, -2))).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jLabel19, -2, 110, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.txtFirstName, -2, -1, -2))).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.webBtnMail, -2, 89, -2).addContainerGap(216, Short.MAX_VALUE)));
        jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel19, -2, -1, -2).addComponent(this.txtFirstName, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel15, -2, -1, -2).addComponent(this.txtLastName, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel16, -2, -1, -2).addComponent(this.txtEmail, -2, -1, -2).addComponent(this.webBtnMail, -2, 31, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel17, -2, -1, -2).addComponent(this.txtPhone, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel18, -2, -1, -2).addComponent(this.txtPhone2, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.txtFax, -2, -1, -2).addComponent(this.jLabel14, -2, -1, -2)).addGap(57, 57, 57)));
        this.jTabbedPane1.addTab(AppLocal.getIntString("label.contact"), this.jPanel1);
        this.jLabel13.setFont(new Font("Arial", 0, 14));
        this.jLabel13.setText(AppLocal.getIntString("label.address"));
        this.jLabel13.setPreferredSize(new Dimension(110, 30));
        this.txtAddress.setFont(new Font("Arial", 0, 14));
        this.txtAddress.setPreferredSize(new Dimension(200, 30));
        this.jLabel20.setFont(new Font("Arial", 0, 14));
        this.jLabel20.setText(AppLocal.getIntString("label.country"));
        this.jLabel20.setPreferredSize(new Dimension(110, 30));
        this.txtCountry.setFont(new Font("Arial", 0, 14));
        this.txtCountry.setPreferredSize(new Dimension(200, 30));
        this.jLabel21.setFont(new Font("Arial", 0, 14));
        this.jLabel21.setText(AppLocal.getIntString("label.address2"));
        this.jLabel21.setPreferredSize(new Dimension(110, 30));
        this.txtAddress2.setFont(new Font("Arial", 0, 14));
        this.txtAddress2.setPreferredSize(new Dimension(200, 30));
        this.jLabel22.setFont(new Font("Arial", 0, 14));
        this.jLabel22.setText(AppLocal.getIntString("label.postal"));
        this.jLabel22.setPreferredSize(new Dimension(110, 30));
        this.txtPostal.setFont(new Font("Arial", 0, 14));
        this.txtPostal.setPreferredSize(new Dimension(0, 30));
        this.jLabel23.setFont(new Font("Arial", 0, 14));
        this.jLabel23.setText(AppLocal.getIntString("label.city"));
        this.jLabel23.setPreferredSize(new Dimension(110, 30));
        this.txtCity.setFont(new Font("Arial", 0, 14));
        this.txtCity.setPreferredSize(new Dimension(200, 30));
        this.jLabel24.setFont(new Font("Arial", 0, 14));
        this.jLabel24.setText(AppLocal.getIntString("label.region"));
        this.jLabel24.setPreferredSize(new Dimension(110, 30));
        this.txtRegion.setFont(new Font("Arial", 0, 14));
        this.txtRegion.setPreferredSize(new Dimension(200, 30));
        GroupLayout jPanel2Layout = new GroupLayout(this.jPanel2);
        this.jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel2Layout.createSequentialGroup().addContainerGap().addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.jLabel20, -2, -1, -2).addComponent(this.jLabel24, -2, -1, -2).addComponent(this.jLabel23, -2, -1, -2).addComponent(this.jLabel21, -2, -1, -2).addComponent(this.jLabel13, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.txtCity, -1, -1, Short.MAX_VALUE).addComponent(this.txtAddress, -1, -1, Short.MAX_VALUE).addComponent(this.txtAddress2, -1, -1, Short.MAX_VALUE).addComponent(this.txtRegion, -1, -1, Short.MAX_VALUE).addComponent(this.txtCountry, -1, -1, Short.MAX_VALUE)).addGap(18, 18, 18).addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.txtPostal, -2, 89, -2).addComponent(this.jLabel22, -2, -1, -2)).addContainerGap()));
        jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel2Layout.createSequentialGroup().addContainerGap().addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel13, -2, -1, -2).addComponent(this.txtAddress, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel21, -2, -1, -2).addComponent(this.txtAddress2, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel23, -2, -1, -2).addComponent(this.txtCity, -2, -1, -2).addComponent(this.jLabel22, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel24, -2, -1, -2).addComponent(this.txtRegion, -2, -1, -2).addComponent(this.txtPostal, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel20, -2, -1, -2).addComponent(this.txtCountry, -2, -1, -2)).addContainerGap()));
        this.jTabbedPane1.addTab(AppLocal.getIntString("label.location"), this.jPanel2);
        this.jPanel4.setFont(new Font("Arial", 0, 12));
        this.jPanel4.setPreferredSize(new Dimension(535, 0));
        this.jBtnShowTrans.setFont(new Font("Arial", 0, 12));
        this.jBtnShowTrans.setText(bundle.getString("button.SupplierTrans"));
        this.jBtnShowTrans.setToolTipText("");
        this.jBtnShowTrans.setPreferredSize(new Dimension(140, 25));
        this.jBtnShowTrans.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                SuppliersView.this.jBtnShowTransActionPerformed(evt);
            }
        });
        this.jScrollPane3.setFont(new Font("Arial", 0, 12));
        this.jTableSupplierTransactions.setFont(new Font("Arial", 0, 12));
        this.jTableSupplierTransactions.setModel(new DefaultTableModel(new Object[][]{{null, null, null, null, null}, {null, null, null, null, null}, {null, null, null, null, null}, {null, null, null, null, null}, {null, null, null, null, null}, {null, null, null, null, null}, {null, null, null, null, null}, {null, null, null, null, null}, {null, null, null, null, null}, {null, null, null, null, null}}, new String[]{"Date", "Product", "Qty", "Price", "Reason"}));
        this.jTableSupplierTransactions.setGridColor(new Color(102, 204, 255));
        this.jTableSupplierTransactions.setOpaque(false);
        this.jTableSupplierTransactions.setPreferredSize(new Dimension(375, 200));
        this.jTableSupplierTransactions.setRowHeight(20);
        this.jTableSupplierTransactions.setShowVerticalLines(false);
        this.jScrollPane3.setViewportView(this.jTableSupplierTransactions);
        GroupLayout jPanel4Layout = new GroupLayout(this.jPanel4);
        this.jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel4Layout.createSequentialGroup().addGap(10, 10, 10).addGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.jScrollPane3, -2, 622, -2).addComponent(this.jBtnShowTrans, -2, -1, -2)).addGap(23, 23, 23)));
        jPanel4Layout.setVerticalGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel4Layout.createSequentialGroup().addContainerGap().addComponent(this.jBtnShowTrans, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jScrollPane3, -2, 217, -2).addContainerGap(-1, Short.MAX_VALUE)));
        this.jTabbedPane1.addTab(bundle.getString("label.SupplierTransactions"), this.jPanel4);
        this.m_jNotes.setFont(new Font("Arial", 0, 14));
        this.m_jNotes.setPreferredSize(new Dimension(0, 0));
        this.jScrollPane1.setViewportView(this.m_jNotes);
        GroupLayout jPanel3Layout = new GroupLayout(this.jPanel3);
        this.jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel3Layout.createSequentialGroup().addContainerGap().addComponent(this.jScrollPane1, -2, 458, -2).addContainerGap(-1, Short.MAX_VALUE)));
        jPanel3Layout.setVerticalGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel3Layout.createSequentialGroup().addContainerGap().addComponent(this.jScrollPane1, -1, 247, Short.MAX_VALUE).addContainerGap()));
        this.jTabbedPane1.addTab(AppLocal.getIntString("label.notes"), this.jPanel3);
        this.jLabel9.setFont(new Font("Arial", 0, 14));
        this.jLabel9.setText(AppLocal.getIntString("label.suppliervatid"));
        this.jLabel9.setMaximumSize(new Dimension(150, 30));
        this.jLabel9.setMinimumSize(new Dimension(140, 25));
        this.jLabel9.setPreferredSize(new Dimension(150, 30));
        this.m_jVATID.setFont(new Font("Arial", 0, 14));
        this.m_jVATID.setPreferredSize(new Dimension(150, 30));
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jTabbedPane1, -2, -1, -2).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.jLabel3, -2, -1, -2).addComponent(this.jLabel7, -1, -1, Short.MAX_VALUE).addComponent(this.jLabel9, -1, -1, Short.MAX_VALUE)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addGroup(layout.createSequentialGroup().addComponent(this.m_jTaxID, -2, -1, -2).addGap(18, 18, 18).addComponent(this.jLabel8, -2, 87, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.m_jSearchkey, -2, 136, -2)).addComponent(this.m_jName, -2, 395, -2).addComponent(this.m_jVATID, -2, -1, -2))).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.jLabel2, -2, -1, -2).addComponent(this.jLabel1, -1, -1, Short.MAX_VALUE)).addComponent(this.jLabel4, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.m_jVisible, -2, 21, -2).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.txtMaxdebt, -2, -1, -2).addComponent(this.txtCurdebt, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.txtCurdate, -2, -1, -2).addComponent(this.jLabel6, -2, -1, -2)))))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel7, -2, -1, -2).addComponent(this.jLabel8, -2, -1, -2).addComponent(this.m_jSearchkey, -2, -1, -2).addComponent(this.m_jTaxID, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel9, -2, -1, -2).addComponent(this.m_jVATID, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel3, -2, -1, -2).addComponent(this.m_jName, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel4, -2, -1, -2).addComponent(this.m_jVisible, -2, 25, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1, -2, -1, -2).addComponent(this.txtMaxdebt, -2, -1, -2).addComponent(this.jLabel6, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.txtCurdate, -2, -1, -2).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel2, -2, -1, -2).addComponent(this.txtCurdebt, -2, -1, -2))).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jTabbedPane1, -2, -1, -2).addContainerGap(48, Short.MAX_VALUE)));
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
                    Logger.getLogger(SuppliersView.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    desktop.mail(mailto);
                }
                catch (IOException ex) {
                    Logger.getLogger(SuppliersView.class.getName()).log(Level.SEVERE, null, ex);
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
            this.jTableSupplierTransactions.setModel(this.transactionModel);
            if (this.transactionModel.getRowCount() > 0) {
                this.jTableSupplierTransactions.setVisible(true);
            } else {
                this.jTableSupplierTransactions.setVisible(false);
                JOptionPane.showMessageDialog(null, AppLocal.getIntString("message.nosupptranx"), "Transactions", 1);
            }
            this.resetTranxTable();
        }
    }

    class TransactionTableModel
    extends AbstractTableModel {
        String dte = AppLocal.getIntString("label.suptblHeaderCol1");
        String prd = AppLocal.getIntString("label.suptblHeaderCol2");
        String qty = AppLocal.getIntString("label.suptblHeaderCol3");
        String pri = AppLocal.getIntString("label.suptblHeaderCol4");
        String rsn = AppLocal.getIntString("label.suptblHeaderCol5");
        List<SupplierTransaction> transactionList;
        String[] columnNames = new String[]{this.dte, this.prd, this.qty, this.pri, this.rsn};
        public Double Tamount;

        public TransactionTableModel(List<SupplierTransaction> list) {
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
            SupplierTransaction supplierTransaction = this.transactionList.get(row);
            SuppliersView.this.jTableSupplierTransactions.setRowHeight(25);
            switch (column) {
                case 0: {
                    Date transactionDate = supplierTransaction.getTransactionDate();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    String formattedDate = sdf.format(transactionDate);
                    return formattedDate;
                }
                case 1: {
                    return supplierTransaction.getProductName();
                }
                case 2: {
                    return supplierTransaction.getUnit();
                }
                case 3: {
                    Double price = supplierTransaction.getPrice();
                    DecimalFormat df = new DecimalFormat("#.##");
                    String formattedAmount = df.format(price);
                    return formattedAmount;
                }
                case 4: {
                    Integer reason = supplierTransaction.getReason();
                    String s = String.valueOf(reason);
                    if (s.equals("1")) {
                        s = MovementReason.IN_PURCHASE.toString();
                    }
                    if (s.equals("2")) {
                        s = MovementReason.IN_REFUND.toString();
                    }
                    if (s.equals("4")) {
                        s = MovementReason.IN_MOVEMENT.toString();
                    }
                    if (s.equals("-1")) {
                        s = MovementReason.OUT_SALE.toString();
                    }
                    if (s.equals("-2")) {
                        s = MovementReason.OUT_REFUND.toString();
                    }
                    if (s.equals("-3")) {
                        s = MovementReason.OUT_BREAK.toString();
                    }
                    if (s.equals("-4")) {
                        s = MovementReason.OUT_MOVEMENT.toString();
                    }
                    if (s.equals("-5")) {
                        s = MovementReason.OUT_SAMPLE.toString();
                    }
                    if (s.equals("-6")) {
                        s = MovementReason.OUT_FREE.toString();
                    }
                    if (s.equals("-7")) {
                        s = MovementReason.OUT_USED.toString();
                    }
                    if (s.equals("-8")) {
                        s = MovementReason.OUT_SUBTRACT.toString();
                    }
                    return s;
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

