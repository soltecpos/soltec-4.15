/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.customers;

import com.openbravo.basic.BasicException;
import com.openbravo.data.gui.ComboBoxValModel;
import com.openbravo.data.gui.MessageInf;
import com.openbravo.data.loader.LocalRes;
import com.openbravo.data.loader.SentenceExec;
import com.openbravo.data.loader.SentenceList;
import com.openbravo.data.loader.TableDefinition;
import com.openbravo.format.Formats;
import com.openbravo.pos.customers.CustomerInfoExt;
import com.openbravo.pos.customers.DataLogicCustomers;
import com.openbravo.pos.customers.MunicipiosColombia;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.forms.DataLogicSales;
import com.openbravo.pos.inventory.TaxCustCategoryInfo;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.UUID;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class JDialogNewCustomer
extends JDialog {
    private static final long serialVersionUID = 1L;
    private DataLogicCustomers dlCustomer;
    private DataLogicSales dlSales;
    private ComboBoxValModel<TaxCustCategoryInfo> m_CategoryModel;
    private SentenceList<TaxCustCategoryInfo> m_sentcat;
    private SentenceExec<Object[]> m_sentinsert;
    private TableDefinition tcustomers;
    private CustomerInfoExt selectedCustomer;
    private Object m_oId;
    private JLabel jLabel1;
    private JLabel jLblCustTaxCat;
    private JLabel jLblDiscount;
    private JLabel jLblDiscountpercent;
    private JLabel jLblEmail;
    private JLabel jLblFirstName;
    private JLabel jLblLastName;
    private JLabel jLblName;
    private JLabel jLblSearchkey;
    private JLabel jLblTaxID;
    private JLabel jLblTelephone1;
    private JLabel jLblTelephone2;
    private JLabel jLblVIP;
    private JPanel jPanel2;
    private JPanel jPanel3;
    private JButton m_jBtnCancel;
    private JButton m_jBtnOK;
    private JComboBox<TaxCustCategoryInfo> m_jCategory;
    private JTextField m_jName;
    private JTextField m_jSearchkey;
    private JTextField m_jTaxID;
    private JCheckBox m_jVip;
    private JTextField txtDiscount;
    private JTextField txtEmail;
    private JTextField txtFirstName;
    private JTextField txtLastName;
    private JTextField txtMaxdebt;
    private JTextField txtPhone;
    private JTextField txtPhone2;
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

    protected JDialogNewCustomer(Frame parent) {
        super(parent, true);
    }

    protected JDialogNewCustomer(Dialog parent) {
        super(parent, true);
    }

    private void init(AppView app) {
        try {
            this.dlSales = (DataLogicSales)app.getBean("com.openbravo.pos.forms.DataLogicSales");
            this.dlCustomer = (DataLogicCustomers)app.getBean("com.openbravo.pos.customers.DataLogicCustomers");
            this.m_sentcat = this.dlSales.getTaxCustCategoriesList();
            this.tcustomers = this.dlCustomer.getTableCustomers();
            this.initComponents();
            if (this.tcustomers.getFields().length < 32) {
                String status = "ERROR: Version Vieja (" + this.tcustomers.getFields().length + " cols)";
                try {
                    DataLogicCustomers dlTemp = new DataLogicCustomers();
                    dlTemp.init(app.getSession());
                    if (dlTemp.getTableCustomers().getFields().length >= 32) {
                        this.tcustomers = dlTemp.getTableCustomers();
                        status = "Nuevo Cliente (FE) - REPARADO";
                    }
                }
                catch (Exception e) {
                    status = status + " - Fallo Reparacion";
                }
                this.setTitle(status);
            } else {
                this.setTitle("Nuevo Cliente (FE) - OK (" + this.tcustomers.getFields().length + " cols)");
            }
            this.m_CategoryModel = new ComboBoxValModel();
            List<TaxCustCategoryInfo> a = this.m_sentcat.list();
            a.add(0, null);
            this.m_CategoryModel = new ComboBoxValModel<TaxCustCategoryInfo>(a);
            this.m_jCategory.setModel(this.m_CategoryModel);
            this.getRootPane().setDefaultButton(this.m_jBtnOK);
        }
        catch (BasicException basicException) {
            // empty catch block
        }
    }

    public Object createValue() throws BasicException {
        Object[] customer = new Object[]{this.m_oId, this.m_jSearchkey.getText(), this.m_jTaxID.getText(), this.m_jName.getText(), this.m_CategoryModel.getSelectedKey(), null, Formats.CURRENCY.parseValue(this.txtMaxdebt.getText(), 0.0), null, null, null, null, null, null, Formats.STRING.parseValue(this.txtFirstName.getText()), Formats.STRING.parseValue(this.txtLastName.getText()), Formats.STRING.parseValue(this.txtEmail.getText()), Formats.STRING.parseValue(this.txtPhone.getText()), Formats.STRING.parseValue(this.txtPhone2.getText()), null, null, true, null, 0.0, null, this.m_jVip.isSelected(), Formats.DOUBLE.parseValue(this.txtDiscount.getText()), this.m_jDv.getText(), this.getComboValue(this.m_jDocType), this.getComboValue(this.m_jPersonType), this.getComboValue(this.m_jLiability), this.getComboValue(this.m_jMunicipalityCode), this.getComboValue(this.m_jRegimeCode)};
        return customer;
    }

    private void selectedVip(boolean value) {
        this.m_jVip.setSelected(value);
        this.jLblDiscountpercent.setVisible(value);
        this.txtDiscount.setVisible(value);
    }

    public static JDialogNewCustomer getDialog(Component parent, AppView app) {
        Window window = JDialogNewCustomer.getWindow(parent);
        JDialogNewCustomer quicknewcustomer = window instanceof Frame ? new JDialogNewCustomer((Frame)window) : new JDialogNewCustomer((Dialog)window);
        quicknewcustomer.init(app);
        return quicknewcustomer;
    }

    protected static Window getWindow(Component parent) {
        if (parent == null) {
            return new JFrame();
        }
        if (parent instanceof Frame || parent instanceof Dialog) {
            return (Window)parent;
        }
        return JDialogNewCustomer.getWindow(parent.getParent());
    }

    public CustomerInfoExt getSelectedCustomer() {
        return this.selectedCustomer;
    }

    private void initComponents() {
        this.jPanel3 = new JPanel();
        this.jLblName = new JLabel();
        this.m_jName = new JTextField();
        this.jLblSearchkey = new JLabel();
        this.m_jSearchkey = new JTextField();
        this.jLblTaxID = new JLabel();
        this.m_jTaxID = new JTextField();
        this.jLblCustTaxCat = new JLabel();
        this.m_jCategory = new JComboBox();
        this.jLblVIP = new JLabel();
        this.m_jVip = new JCheckBox();
        this.jLblDiscount = new JLabel();
        this.txtDiscount = new JTextField();
        this.jLblDiscountpercent = new JLabel();
        this.jLblFirstName = new JLabel();
        this.txtFirstName = new JTextField();
        this.jLblLastName = new JLabel();
        this.txtLastName = new JTextField();
        this.jLblEmail = new JLabel();
        this.txtEmail = new JTextField();
        this.jLblTelephone1 = new JLabel();
        this.txtPhone = new JTextField();
        this.jLblTelephone2 = new JLabel();
        this.txtPhone2 = new JTextField();
        this.jLabel1 = new JLabel();
        this.txtMaxdebt = new JTextField();
        this.jPanel2 = new JPanel();
        this.m_jBtnOK = new JButton();
        this.m_jBtnCancel = new JButton();
        this.setDefaultCloseOperation(2);
        this.setTitle("Nuevo Cliente (FE Habilitado)");
        this.setPreferredSize(new Dimension(610, 430));
        this.setResizable(false);
        this.jPanel3.setFont(new Font("Arial", 0, 14));
        this.jLblName.setFont(new Font("Arial", 0, 14));
        this.jLblName.setText(AppLocal.getIntString("label.namem"));
        this.jLblName.setMaximumSize(new Dimension(140, 25));
        this.jLblName.setMinimumSize(new Dimension(140, 25));
        this.jLblName.setPreferredSize(new Dimension(150, 30));
        this.m_jName.setFont(new Font("Arial", 0, 14));
        this.m_jName.setPreferredSize(new Dimension(300, 30));
        this.jLblSearchkey.setFont(new Font("Arial", 0, 14));
        this.jLblSearchkey.setText(AppLocal.getIntString("label.searchkeym"));
        this.jLblSearchkey.setPreferredSize(new Dimension(150, 30));
        this.m_jSearchkey.setFont(new Font("Arial", 0, 14));
        this.m_jSearchkey.setHorizontalAlignment(2);
        this.m_jSearchkey.setPreferredSize(new Dimension(150, 30));
        this.jLblTaxID.setFont(new Font("Arial", 0, 14));
        this.jLblTaxID.setText(AppLocal.getIntString("label.taxid"));
        this.jLblTaxID.setMaximumSize(new Dimension(150, 30));
        this.jLblTaxID.setMinimumSize(new Dimension(140, 25));
        this.jLblTaxID.setPreferredSize(new Dimension(100, 30));
        this.m_jTaxID.setFont(new Font("Arial", 0, 14));
        this.m_jTaxID.setPreferredSize(new Dimension(100, 30));
        this.jLblCustTaxCat.setFont(new Font("Arial", 0, 14));
        this.jLblCustTaxCat.setText(AppLocal.getIntString("label.custtaxcategory"));
        this.jLblCustTaxCat.setMaximumSize(new Dimension(140, 25));
        this.jLblCustTaxCat.setMinimumSize(new Dimension(140, 25));
        this.jLblCustTaxCat.setPreferredSize(new Dimension(150, 30));
        this.m_jCategory.setFont(new Font("Arial", 0, 14));
        this.m_jCategory.setPreferredSize(new Dimension(0, 30));
        this.jLblVIP.setFont(new Font("Arial", 0, 14));
        this.jLblVIP.setText(AppLocal.getIntString("label.vip"));
        this.jLblVIP.setPreferredSize(new Dimension(150, 30));
        this.m_jVip.setForeground(new Color(0, 188, 243));
        this.m_jVip.setPreferredSize(new Dimension(21, 30));
        this.m_jVip.addChangeListener(new ChangeListener(){

            @Override
            public void stateChanged(ChangeEvent evt) {
                JDialogNewCustomer.this.m_jVipnone(evt);
            }
        });
        this.jLblDiscount.setFont(new Font("Arial", 0, 14));
        this.jLblDiscount.setText(AppLocal.getIntString("label.discount"));
        this.jLblDiscount.setPreferredSize(new Dimension(55, 30));
        this.txtDiscount.setFont(new Font("Arial", 0, 14));
        this.txtDiscount.setHorizontalAlignment(4);
        this.txtDiscount.setPreferredSize(new Dimension(50, 30));
        this.jLblDiscountpercent.setFont(new Font("Arial", 0, 14));
        this.jLblDiscountpercent.setText("%");
        this.jLblDiscountpercent.setPreferredSize(new Dimension(12, 30));
        this.jLblFirstName.setFont(new Font("Arial", 0, 14));
        this.jLblFirstName.setText(AppLocal.getIntString("label.firstname"));
        this.jLblFirstName.setAlignmentX(0.5f);
        this.jLblFirstName.setPreferredSize(new Dimension(150, 30));
        this.txtFirstName.setFont(new Font("Arial", 0, 14));
        this.txtFirstName.setPreferredSize(new Dimension(200, 30));
        this.jLblLastName.setFont(new Font("Arial", 0, 14));
        this.jLblLastName.setText(AppLocal.getIntString("label.lastname"));
        this.jLblLastName.setPreferredSize(new Dimension(150, 30));
        this.txtLastName.setFont(new Font("Arial", 0, 14));
        this.txtLastName.setPreferredSize(new Dimension(200, 30));
        this.jLblEmail.setFont(new Font("Arial", 0, 14));
        this.jLblEmail.setText(AppLocal.getIntString("label.email"));
        this.jLblEmail.setPreferredSize(new Dimension(150, 30));
        this.txtEmail.setFont(new Font("Arial", 0, 14));
        this.txtEmail.setPreferredSize(new Dimension(200, 30));
        this.jLblTelephone1.setFont(new Font("Arial", 0, 14));
        this.jLblTelephone1.setText(AppLocal.getIntString("label.phone"));
        this.jLblTelephone1.setPreferredSize(new Dimension(150, 30));
        this.txtPhone.setFont(new Font("Arial", 0, 14));
        this.txtPhone.setPreferredSize(new Dimension(200, 30));
        this.jLblTelephone2.setFont(new Font("Arial", 0, 14));
        this.jLblTelephone2.setText(AppLocal.getIntString("label.phone2"));
        this.jLblTelephone2.setPreferredSize(new Dimension(150, 30));
        this.txtPhone2.setFont(new Font("Arial", 0, 14));
        this.txtPhone2.setPreferredSize(new Dimension(200, 30));
        this.jLabel1.setFont(new Font("Arial", 0, 14));
        this.jLabel1.setText(AppLocal.getIntString("label.maxdebt"));
        this.jLabel1.setMaximumSize(new Dimension(140, 25));
        this.jLabel1.setMinimumSize(new Dimension(140, 25));
        this.jLabel1.setPreferredSize(new Dimension(150, 30));
        this.txtMaxdebt.setFont(new Font("Arial", 0, 14));
        this.txtMaxdebt.setHorizontalAlignment(4);
        this.txtMaxdebt.setPreferredSize(new Dimension(100, 30));
        GroupLayout jPanel3Layout = new GroupLayout(this.jPanel3);
        this.jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel3Layout.createSequentialGroup().addContainerGap().addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel3Layout.createSequentialGroup().addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLblEmail, -1, -1, -2).addComponent(this.jLblTelephone1, -1, -1, -2).addComponent(this.jLblTelephone2, GroupLayout.Alignment.TRAILING, -1, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.txtEmail, -1, -1, -2).addComponent(this.txtPhone, -1, -1, -2).addComponent(this.txtPhone2, -1, -1, -2))).addGroup(jPanel3Layout.createSequentialGroup().addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLblFirstName, -1, -1, -2).addComponent(this.jLblLastName, GroupLayout.Alignment.TRAILING, -1, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.txtLastName, -1, -1, -2).addComponent(this.txtFirstName, -1, -1, -2))).addGroup(jPanel3Layout.createSequentialGroup().addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup().addComponent(this.jLblName, -1, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.m_jName, -2, -1, -2)).addGroup(jPanel3Layout.createSequentialGroup().addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel3Layout.createSequentialGroup().addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLblCustTaxCat, -2, -1, -2).addComponent(this.jLblVIP, GroupLayout.Alignment.TRAILING, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.m_jCategory, -2, 163, -2).addGroup(jPanel3Layout.createSequentialGroup().addComponent(this.m_jVip, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jLblDiscount, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.txtDiscount, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jLblDiscountpercent, -2, -1, -2)).addComponent(this.m_jSearchkey, -2, -1, -2))).addComponent(this.jLblSearchkey, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addComponent(this.jLabel1, GroupLayout.Alignment.LEADING, -2, 1, Short.MAX_VALUE).addComponent(this.jLblTaxID, GroupLayout.Alignment.LEADING, -1, 140, Short.MAX_VALUE)))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.m_jTaxID, -2, -1, -2).addComponent(this.txtMaxdebt, -2, -1, -2)))).addContainerGap(-1, Short.MAX_VALUE)));
        jPanel3Layout.setVerticalGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup().addContainerGap().addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLblName, -2, -1, -2).addComponent(this.m_jName, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLblSearchkey, -2, -1, -2).addComponent(this.m_jSearchkey, -2, -1, -2).addComponent(this.jLblTaxID, -2, -1, -2).addComponent(this.m_jTaxID, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLblCustTaxCat, -2, -1, -2).addComponent(this.m_jCategory, -2, -1, -2).addComponent(this.jLabel1, -2, -1, -2).addComponent(this.txtMaxdebt, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLblVIP, -2, -1, -2).addGroup(GroupLayout.Alignment.TRAILING, jPanel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLblDiscount, -2, -1, -2).addComponent(this.txtDiscount, -2, -1, -2).addComponent(this.jLblDiscountpercent, -2, -1, -2))).addComponent(this.m_jVip, -2, 25, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.txtFirstName, -2, -1, -2).addComponent(this.jLblFirstName, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLblLastName, -2, -1, -2).addComponent(this.txtLastName, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLblEmail, -2, -1, -2).addComponent(this.txtEmail, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLblTelephone1, -2, -1, -2).addComponent(this.txtPhone, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLblTelephone2, -2, -1, -2).addComponent(this.txtPhone2, -2, -1, -2))));
        this.jPanel3.getAccessibleContext().setAccessibleName("");
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
        this.m_jMunicipalityCode = new JComboBox<String>(MunicipiosColombia.LISTA);
        this.m_jMunicipalityCode.setEditable(true);
        this.m_jMunicipalityCode.setFont(new Font("Arial", 0, 14));
        this.m_jMunicipalityCode.setBounds(180, 180, 250, 30);
        this.jPanelFE.add(this.m_jMunicipalityCode);
        this.jLabelRegime.setFont(new Font("Arial", 0, 14));
        this.jLabelRegime.setText("Regimen");
        this.jLabelRegime.setBounds(20, 220, 150, 30);
        this.jPanelFE.add(this.jLabelRegime);
        this.m_jRegimeCode.setFont(new Font("Arial", 0, 14));
        this.m_jRegimeCode.setModel(new DefaultComboBoxModel<String>(new String[]{"49 - No Responsable de IVA", "48 - Responsable de IVA"}));
        this.m_jRegimeCode.setBounds(180, 220, 250, 30);
        this.jPanelFE.add(this.m_jRegimeCode);
        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("General", this.jPanel3);
        tabs.addTab("Fact. Elect.", this.jPanelFE);
        this.getContentPane().add((Component)tabs, "Center");
        this.jPanel2.setLayout(new FlowLayout(2));
        this.m_jBtnOK.setFont(new Font("Arial", 0, 14));
        this.m_jBtnOK.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/ok.png")));
        this.m_jBtnOK.setText(AppLocal.getIntString("Button.OK"));
        this.m_jBtnOK.setFocusPainted(false);
        this.m_jBtnOK.setFocusable(false);
        this.m_jBtnOK.setMargin(new Insets(8, 16, 8, 16));
        this.m_jBtnOK.setPreferredSize(new Dimension(80, 45));
        this.m_jBtnOK.setRequestFocusEnabled(false);
        this.m_jBtnOK.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JDialogNewCustomer.this.m_jBtnOKActionPerformed(evt);
            }
        });
        this.jPanel2.add(this.m_jBtnOK);
        this.m_jBtnCancel.setFont(new Font("Arial", 0, 14));
        this.m_jBtnCancel.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/cancel.png")));
        this.m_jBtnCancel.setText(AppLocal.getIntString("Button.Cancel"));
        this.m_jBtnCancel.setFocusPainted(false);
        this.m_jBtnCancel.setFocusable(false);
        this.m_jBtnCancel.setMargin(new Insets(8, 16, 8, 16));
        this.m_jBtnCancel.setPreferredSize(new Dimension(80, 45));
        this.m_jBtnCancel.setRequestFocusEnabled(false);
        this.m_jBtnCancel.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JDialogNewCustomer.this.m_jBtnCancelActionPerformed(evt);
            }
        });
        this.jPanel2.add(this.m_jBtnCancel);
        this.getContentPane().add((Component)this.jPanel2, "South");
        this.setSize(new Dimension(700, 500));
        this.setLocationRelativeTo(null);
    }

    private void m_jBtnOKActionPerformed(ActionEvent evt) {
        if ("".equals(this.m_jSearchkey.getText()) || "".equals(this.m_jName.getText())) {
            JOptionPane.showMessageDialog(null, AppLocal.getIntString("message.customercheck"), "Customer check", 0);
        } else {
            try {
                this.m_oId = UUID.randomUUID().toString();
                Object[] customer = (Object[])this.createValue();
                System.out.println("DEBUG: Guardando cliente. Array size: " + customer.length);
                int status = this.dlCustomer.insertCustomerFE(customer);
                if (status > 0) {
                    this.selectedCustomer = this.dlSales.loadCustomerExt(this.m_oId.toString());
                    this.dispose();
                } else {
                    MessageInf msg = new MessageInf(-67108864, LocalRes.getIntString("message.nosave"), "Error save");
                    msg.show(this);
                }
            }
            catch (Exception ex) {
                ex.printStackTrace();
                String msg = "Error: " + ex.getMessage();
                if (ex.getCause() != null) {
                    msg = msg + "\nCausa: " + ex.getCause().getMessage();
                }
                JOptionPane.showMessageDialog(this, msg, "Error al Guardar (Debug)", 0);
            }
        }
    }

    private void m_jBtnCancelActionPerformed(ActionEvent evt) {
        this.dispose();
    }

    private void m_jVipnone(ChangeEvent evt) {
    }

    private String getComboValue(JComboBox<?> box) {
        if (box.getSelectedItem() == null) {
            return null;
        }
        String s = box.getSelectedItem().toString();
        if (s.contains(" - ")) {
            return s.split(" - ")[0];
        }
        return s;
    }
}

