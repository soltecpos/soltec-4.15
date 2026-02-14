/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jdesktop.layout.GroupLayout
 *  org.jdesktop.layout.GroupLayout$Group
 */
package com.openbravo.pos.inventory;

import com.openbravo.basic.BasicException;
import com.openbravo.beans.DateUtils;
import com.openbravo.beans.JCalendarDialog;
import com.openbravo.data.gui.ComboBoxValModel;
import com.openbravo.data.gui.MessageInf;
import com.openbravo.data.loader.SentenceList;
import com.openbravo.data.user.DirtyManager;
import com.openbravo.data.user.EditorRecord;
import com.openbravo.format.Formats;
import com.openbravo.pos.catalog.CatalogSelector;
import com.openbravo.pos.catalog.JCatalog;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.forms.DataLogicSales;
import com.openbravo.pos.forms.DataLogicSystem;
import com.openbravo.pos.inventory.LocationInfo;
import com.openbravo.pos.inventory.MovementReason;
import com.openbravo.pos.panels.JProductFinder;
import com.openbravo.pos.printer.TicketParser;
import com.openbravo.pos.sales.JProductAttEdit;
import com.openbravo.pos.suppliers.DataLogicSuppliers;
import com.openbravo.pos.suppliers.SupplierInfo;
import com.openbravo.pos.ticket.ProductInfoExt;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.jdesktop.layout.GroupLayout;

public final class StockDiaryEditor
extends JPanel
implements EditorRecord {
    private static final long serialVersionUID = 1L;
    private final AppView m_App;
    private final DataLogicSystem m_dlSystem;
    private final DataLogicSales m_dlSales;
    private final DataLogicSuppliers m_dlSuppliers;
    private final TicketParser m_TTP;
    private final CatalogSelector m_cat;
    private final ComboBoxValModel<MovementReason> m_ReasonModel;
    private final SentenceList<LocationInfo> m_sentlocations;
    private ComboBoxValModel<LocationInfo> m_LocationsModel;
    private ComboBoxValModel<LocationInfo> m_LocationsModelDes;
    private final SentenceList<SupplierInfo> m_sentsuppliers;
    private ComboBoxValModel<SupplierInfo> m_SuppliersModel;
    private String m_sID;
    private String productid;
    private String productref;
    private String productcode;
    private String productname;
    private String attsetid;
    private String attsetinstid;
    private String attsetinstdesc;
    private String sAppUser;
    private final String user;
    private JPanel catcontainer;
    private JButton jEditAttributes;
    private JButton jEditProduct;
    private JLabel jLBCode;
    private JLabel jLblAtt;
    private JLabel jLblDate;
    private JLabel jLblLocation;
    private JLabel jLblLocation1;
    private JLabel jLblMoveTo;
    private JLabel jLblName;
    private JLabel jLblPrice;
    private JLabel jLblReason;
    private JLabel jLblRef;
    private JLabel jLblUnits;
    private JPanel jPanel1;
    private JTextField jattributes;
    private JTextField jproduct;
    private JButton m_jEnter;
    private JButton m_jEnter1;
    private JComboBox<LocationInfo> m_jLocation;
    private JComboBox<LocationInfo> m_jLocationDes;
    private JComboBox<SupplierInfo> m_jSupplier;
    private JButton m_jbtndate;
    private JTextField m_jcodebar;
    private JTextField m_jdate;
    private JTextField m_jprice;
    private JComboBox<MovementReason> m_jreason;
    private JTextField m_jreference;
    private JTextField m_junits;

    public StockDiaryEditor(AppView app, DirtyManager dirty) {
        this.m_App = app;
        this.m_dlSystem = (DataLogicSystem)this.m_App.getBean("com.openbravo.pos.forms.DataLogicSystem");
        this.m_dlSales = (DataLogicSales)this.m_App.getBean("com.openbravo.pos.forms.DataLogicSales");
        this.m_dlSuppliers = (DataLogicSuppliers)this.m_App.getBean("com.openbravo.pos.suppliers.DataLogicSuppliers");
        this.m_TTP = new TicketParser(this.m_App.getDeviceTicket(), this.m_dlSystem);
        this.initComponents();
        this.user = this.m_App.getAppUserView().getUser().getName();
        this.m_sentlocations = this.m_dlSales.getLocationsList();
        this.m_LocationsModel = new ComboBoxValModel();
        this.m_LocationsModelDes = new ComboBoxValModel();
        this.m_sentsuppliers = this.m_dlSuppliers.getSupplierList();
        this.m_SuppliersModel = new ComboBoxValModel();
        this.m_ReasonModel = new ComboBoxValModel();
        this.m_ReasonModel.add(MovementReason.IN_PURCHASE);
        this.m_ReasonModel.add(MovementReason.IN_REFUND);
        this.m_ReasonModel.add(MovementReason.IN_MOVEMENT);
        this.m_ReasonModel.add(MovementReason.OUT_SALE);
        this.m_ReasonModel.add(MovementReason.OUT_REFUND);
        this.m_ReasonModel.add(MovementReason.OUT_BREAK);
        this.m_ReasonModel.add(MovementReason.OUT_MOVEMENT);
        this.m_ReasonModel.add(MovementReason.OUT_CROSSING);
        this.m_jreason.setModel(this.m_ReasonModel);
        this.m_cat = new JCatalog(this.m_dlSales);
        this.m_cat.addActionListener(new CatalogListener());
        this.catcontainer.add(this.m_cat.getComponent(), "Center");
        this.m_jdate.getDocument().addDocumentListener(dirty);
        this.m_jreason.addActionListener(dirty);
        this.m_jLocation.addActionListener(dirty);
        this.m_jLocationDes.addActionListener(dirty);
        this.jproduct.getDocument().addDocumentListener(dirty);
        this.jattributes.getDocument().addDocumentListener(dirty);
        this.m_junits.getDocument().addDocumentListener(dirty);
        this.m_jprice.getDocument().addDocumentListener(dirty);
        this.m_jSupplier.addActionListener(dirty);
        this.writeValueEOF();
    }

    public void activate() throws BasicException {
        this.m_cat.loadCatalog();
        List<LocationInfo> l = this.m_sentlocations.list();
        this.m_LocationsModel = new ComboBoxValModel<LocationInfo>(this.m_sentlocations.list());
        this.m_jLocation.setModel(this.m_LocationsModel);
        this.m_LocationsModelDes = new ComboBoxValModel<LocationInfo>(l);
        this.m_jLocationDes.setModel(this.m_LocationsModelDes);
        List<SupplierInfo> sl = this.m_sentsuppliers.list();
        this.m_SuppliersModel = new ComboBoxValModel<SupplierInfo>(this.m_sentsuppliers.list());
        this.m_jSupplier.setModel(this.m_SuppliersModel);
    }

    @Override
    public void refresh() {
    }

    @Override
    public void writeValueEOF() {
        this.m_sID = null;
        this.m_jdate.setText(null);
        this.m_ReasonModel.setSelectedKey(null);
        this.m_LocationsModel.setSelectedKey(this.m_App.getInventoryLocation());
        this.m_LocationsModelDes.setSelectedKey(this.m_App.getInventoryLocation());
        this.m_SuppliersModel.setSelectedKey(null);
        this.productid = null;
        this.productref = null;
        this.productcode = null;
        this.productname = null;
        this.m_jreference.setText(null);
        this.m_jcodebar.setText(null);
        this.jproduct.setText(null);
        this.attsetid = null;
        this.attsetinstid = null;
        this.attsetinstdesc = null;
        this.jattributes.setText(null);
        this.m_junits.setText(null);
        this.m_jprice.setText(null);
        this.m_jdate.setEnabled(false);
        this.m_jbtndate.setEnabled(false);
        this.m_jreason.setEnabled(false);
        this.m_jreference.setEnabled(false);
        this.m_jEnter1.setEnabled(false);
        this.m_jcodebar.setEnabled(false);
        this.m_jEnter.setEnabled(false);
        this.m_jLocation.setEnabled(false);
        this.jproduct.setEnabled(false);
        this.jEditProduct.setEnabled(false);
        this.jattributes.setEnabled(false);
        this.jEditAttributes.setEnabled(false);
        this.m_junits.setEnabled(false);
        this.m_jprice.setEnabled(false);
        this.m_cat.setComponentEnabled(false);
    }

    @Override
    public void writeValueInsert() {
        this.m_sID = UUID.randomUUID().toString();
        this.m_jdate.setText(Formats.TIMESTAMP.formatValue(DateUtils.getTodayMinutes()));
        this.m_ReasonModel.setSelectedItem(MovementReason.IN_PURCHASE);
        this.m_LocationsModel.setSelectedKey(this.m_App.getInventoryLocation());
        this.m_LocationsModelDes.setSelectedKey(this.m_App.getInventoryLocation());
        this.m_SuppliersModel.setSelectedKey(null);
        this.productid = null;
        this.productref = null;
        this.productcode = null;
        this.productname = null;
        this.m_jreference.setText(null);
        this.m_jcodebar.setText(null);
        this.jproduct.setText(null);
        this.attsetid = null;
        this.attsetinstid = null;
        this.attsetinstdesc = null;
        this.jattributes.setText(null);
        this.m_jcodebar.setText(null);
        this.m_junits.setText(null);
        this.m_jprice.setText(null);
        this.m_jdate.setEnabled(true);
        this.m_jbtndate.setEnabled(true);
        this.m_jreason.setEnabled(true);
        this.m_jreference.setEnabled(true);
        this.m_jEnter1.setEnabled(true);
        this.m_jcodebar.setEnabled(true);
        this.m_jEnter.setEnabled(true);
        this.m_jLocation.setEnabled(true);
        this.m_jSupplier.setEnabled(true);
        this.jproduct.setEnabled(true);
        this.jEditProduct.setEnabled(true);
        this.jattributes.setEnabled(true);
        this.jEditAttributes.setEnabled(true);
        this.m_junits.setEnabled(true);
        this.m_jprice.setEnabled(true);
        this.m_cat.setComponentEnabled(true);
    }

    @Override
    public void writeValueDelete(Object value) {
        Object[] diary = (Object[])value;
        this.m_sID = (String)diary[0];
        this.m_jdate.setText(Formats.TIMESTAMP.formatValue(diary[1]));
        this.m_ReasonModel.setSelectedKey(diary[2]);
        this.m_LocationsModel.setSelectedKey(diary[3]);
        this.productid = (String)diary[4];
        this.attsetinstid = (String)diary[5];
        this.m_junits.setText(Formats.DOUBLE.formatValue(this.signum((Double)diary[6], (Integer)diary[2])));
        this.m_jprice.setText(Formats.CURRENCY.formatValue(diary[7]));
        this.productref = (String)diary[8];
        this.productcode = (String)diary[9];
        this.productname = (String)diary[10];
        this.attsetid = (String)diary[11];
        this.attsetinstdesc = (String)diary[12];
        this.m_SuppliersModel.setSelectedKey(diary[13]);
        this.m_jreference.setText(this.productref);
        this.m_jcodebar.setText(this.productcode);
        this.jproduct.setText(this.productname);
        this.jattributes.setText(this.attsetinstdesc);
        this.m_jdate.setEnabled(false);
        this.m_jbtndate.setEnabled(false);
        this.m_jreason.setEnabled(false);
        this.m_jreference.setEnabled(false);
        this.m_jEnter1.setEnabled(false);
        this.m_jcodebar.setEnabled(false);
        this.m_jEnter.setEnabled(false);
        this.m_jLocation.setEnabled(false);
        this.m_jLocationDes.setEnabled(false);
        this.m_jSupplier.setEnabled(false);
        this.jproduct.setEnabled(false);
        this.jEditProduct.setEnabled(false);
        this.jattributes.setEnabled(false);
        this.jEditAttributes.setEnabled(false);
        this.m_junits.setEnabled(false);
        this.m_jprice.setEnabled(false);
        this.m_cat.setComponentEnabled(false);
    }

    @Override
    public void writeValueEdit(Object value) {
        Object[] diary = (Object[])value;
        this.m_sID = (String)diary[0];
        this.m_jdate.setText(Formats.TIMESTAMP.formatValue(diary[1]));
        this.m_ReasonModel.setSelectedKey(diary[2]);
        this.m_LocationsModel.setSelectedKey(diary[3]);
        this.productid = (String)diary[4];
        this.attsetinstid = (String)diary[5];
        this.m_junits.setText(Formats.DOUBLE.formatValue(this.signum((Double)diary[6], (Integer)diary[2])));
        this.m_jprice.setText(Formats.CURRENCY.formatValue(diary[7]));
        this.sAppUser = (String)diary[8];
        this.productref = (String)diary[9];
        this.productcode = (String)diary[10];
        this.productname = (String)diary[11];
        this.attsetid = (String)diary[12];
        this.attsetinstdesc = (String)diary[13];
        this.m_SuppliersModel.setSelectedKey(diary[14]);
        this.m_jreference.setText(this.productref);
        this.m_jcodebar.setText(this.productcode);
        this.jproduct.setText(this.productname);
        this.jattributes.setText(this.attsetinstdesc);
        this.m_jdate.setEnabled(false);
        this.m_jbtndate.setEnabled(false);
        this.m_jreason.setEnabled(false);
        this.m_jreference.setEnabled(false);
        this.m_jEnter1.setEnabled(false);
        this.m_jcodebar.setEnabled(false);
        this.m_jEnter.setEnabled(false);
        this.m_jLocation.setEnabled(false);
        this.m_jLocationDes.setEnabled(false);
        this.m_jSupplier.setEnabled(false);
        this.jproduct.setEnabled(true);
        this.jEditProduct.setEnabled(true);
        this.jattributes.setEnabled(false);
        this.jEditAttributes.setEnabled(false);
        this.m_junits.setEnabled(false);
        this.m_jprice.setEnabled(false);
        this.m_cat.setComponentEnabled(false);
    }

    @Override
    public Object createValue() throws BasicException {
        return new Object[]{this.m_sID, Formats.TIMESTAMP.parseValue(this.m_jdate.getText()), this.m_ReasonModel.getSelectedKey(), this.m_LocationsModel.getSelectedKey(), this.productid, this.attsetinstid, this.samesignum((Double)Formats.DOUBLE.parseValue(this.m_junits.getText()), (Integer)this.m_ReasonModel.getSelectedKey()), Formats.CURRENCY.parseValue(this.m_jprice.getText()), this.m_App.getAppUserView().getUser().getName(), this.productref, this.productcode, this.productname, this.attsetid, this.attsetinstdesc};
    }

    @Override
    public Component getComponent() {
        return this;
    }

    private Double signum(Double d, Integer i) {
        if (d == null || i == null) {
            return d;
        }
        if (i < 0) {
            return -d.doubleValue();
        }
        return d;
    }

    private Double samesignum(Double d, Integer i) {
        if (d == null || i == null) {
            return d;
        }
        if (i > 0 && d < 0.0 || i < 0 && d > 0.0) {
            return -d.doubleValue();
        }
        return d;
    }

    private void assignProduct(ProductInfoExt prod) {
        if (this.jproduct.isEnabled()) {
            if (prod == null) {
                this.productid = null;
                this.productref = null;
                this.productcode = null;
                this.productname = null;
                this.attsetid = null;
                this.attsetinstid = null;
                this.attsetinstdesc = null;
                this.jproduct.setText(null);
                this.m_jcodebar.setText(null);
                this.m_jreference.setText(null);
                this.jattributes.setText(null);
            } else {
                this.productid = prod.getID();
                this.productref = prod.getReference();
                this.productcode = prod.getCode();
                this.productname = prod.toString();
                this.attsetid = prod.getAttributeSetID();
                this.attsetinstid = null;
                this.attsetinstdesc = null;
                this.jproduct.setText(this.productname);
                this.m_jcodebar.setText(this.productcode);
                this.m_jreference.setText(this.productref);
                this.jattributes.setText(null);
                MovementReason reason = (MovementReason)this.m_ReasonModel.getSelectedItem();
                Double dPrice = reason.getPrice(prod.getPriceBuy(), prod.getPriceSell());
                this.m_jprice.setText(Formats.CURRENCY.formatValue(dPrice));
            }
        }
    }

    private void assignProductByCode() {
        try {
            ProductInfoExt oProduct = this.m_dlSales.getProductInfoByCode(this.m_jcodebar.getText());
            if (oProduct == null) {
                this.assignProduct(null);
                Toolkit.getDefaultToolkit().beep();
            } else {
                this.assignProduct(oProduct);
            }
        }
        catch (BasicException eData) {
            this.assignProduct(null);
            MessageInf msg = new MessageInf(eData);
            msg.show(this);
        }
    }

    private void assignProductByReference() {
        try {
            ProductInfoExt oProduct = this.m_dlSales.getProductInfoByReference(this.m_jreference.getText());
            if (oProduct == null) {
                this.assignProduct(null);
                Toolkit.getDefaultToolkit().beep();
            } else {
                this.assignProduct(oProduct);
            }
        }
        catch (BasicException eData) {
            this.assignProduct(null);
            MessageInf msg = new MessageInf(eData);
            msg.show(this);
        }
    }

    private void initComponents() {
        this.jPanel1 = new JPanel();
        this.jLblDate = new JLabel();
        this.m_jdate = new JTextField();
        this.m_jbtndate = new JButton();
        this.jLblReason = new JLabel();
        this.m_jreason = new JComboBox();
        this.jLblName = new JLabel();
        this.jproduct = new JTextField();
        this.jEditProduct = new JButton();
        this.jLblLocation = new JLabel();
        this.m_jLocation = new JComboBox();
        this.jLBCode = new JLabel();
        this.m_jcodebar = new JTextField();
        this.m_jEnter = new JButton();
        this.jLblRef = new JLabel();
        this.m_jreference = new JTextField();
        this.m_jEnter1 = new JButton();
        this.jLblAtt = new JLabel();
        this.jattributes = new JTextField();
        this.jEditAttributes = new JButton();
        this.jLblUnits = new JLabel();
        this.m_junits = new JTextField();
        this.jLblPrice = new JLabel();
        this.m_jprice = new JTextField();
        this.catcontainer = new JPanel();
        this.m_jLocationDes = new JComboBox();
        this.jLblMoveTo = new JLabel();
        this.m_jSupplier = new JComboBox();
        this.jLblLocation1 = new JLabel();
        this.setFont(new Font("Arial", 0, 12));
        this.setMinimumSize(new Dimension(550, 250));
        this.setPreferredSize(new Dimension(1000, 550));
        this.setLayout(new BorderLayout());
        this.jPanel1.setFont(new Font("Arial", 0, 12));
        this.jPanel1.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        this.jPanel1.setMinimumSize(new Dimension(780, 260));
        this.jPanel1.setPreferredSize(new Dimension(400, 600));
        this.jLblDate.setFont(new Font("Arial", 0, 14));
        this.jLblDate.setText(AppLocal.getIntString("label.stockdate"));
        this.jLblDate.setMaximumSize(new Dimension(23, 20));
        this.jLblDate.setMinimumSize(new Dimension(23, 20));
        this.jLblDate.setPreferredSize(new Dimension(110, 30));
        this.m_jdate.setFont(new Font("Arial", 0, 14));
        this.m_jdate.setMinimumSize(new Dimension(40, 20));
        this.m_jdate.setPreferredSize(new Dimension(170, 30));
        this.m_jbtndate.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/date.png")));
        this.m_jbtndate.setToolTipText("Open Calendar");
        this.m_jbtndate.setPreferredSize(new Dimension(64, 45));
        this.m_jbtndate.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                StockDiaryEditor.this.m_jbtndateActionPerformed(evt);
            }
        });
        this.jLblReason.setFont(new Font("Arial", 0, 14));
        this.jLblReason.setText(AppLocal.getIntString("label.stockreason"));
        this.jLblReason.setMaximumSize(new Dimension(36, 20));
        this.jLblReason.setMinimumSize(new Dimension(36, 20));
        this.jLblReason.setPreferredSize(new Dimension(110, 30));
        this.m_jreason.setFont(new Font("Arial", 0, 14));
        this.m_jreason.setPreferredSize(new Dimension(170, 30));
        this.m_jreason.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                StockDiaryEditor.this.m_jreasonActionPerformed(evt);
            }
        });
        this.jLblName.setFont(new Font("Arial", 0, 14));
        this.jLblName.setText(AppLocal.getIntString("label.prodname"));
        this.jLblName.setMaximumSize(new Dimension(40, 20));
        this.jLblName.setMinimumSize(new Dimension(40, 20));
        this.jLblName.setPreferredSize(new Dimension(110, 30));
        this.jproduct.setEditable(false);
        this.jproduct.setFont(new Font("Arial", 0, 14));
        this.jproduct.setText("  ");
        this.jproduct.setPreferredSize(new Dimension(170, 30));
        this.jproduct.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                StockDiaryEditor.this.jproductActionPerformed(evt);
            }
        });
        this.jEditProduct.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/search24.png")));
        this.jEditProduct.setToolTipText("Search Product List");
        this.jEditProduct.setPreferredSize(new Dimension(64, 45));
        this.jEditProduct.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                StockDiaryEditor.this.jEditProductActionPerformed(evt);
            }
        });
        this.jLblLocation.setFont(new Font("Arial", 0, 14));
        this.jLblLocation.setHorizontalAlignment(2);
        this.jLblLocation.setText("Location");
        this.jLblLocation.setPreferredSize(new Dimension(110, 30));
        this.m_jLocation.setFont(new Font("Arial", 0, 14));
        this.m_jLocation.setPreferredSize(new Dimension(170, 30));
        this.jLBCode.setFont(new Font("Arial", 0, 14));
        this.jLBCode.setText(AppLocal.getIntString("label.prodbarcode"));
        this.jLBCode.setMaximumSize(new Dimension(40, 20));
        this.jLBCode.setMinimumSize(new Dimension(40, 20));
        this.jLBCode.setPreferredSize(new Dimension(110, 30));
        this.m_jcodebar.setFont(new Font("Arial", 0, 14));
        this.m_jcodebar.setPreferredSize(new Dimension(170, 30));
        this.m_jcodebar.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                StockDiaryEditor.this.m_jcodebarActionPerformed(evt);
            }
        });
        this.m_jEnter.setIcon(com.openbravo.pos.plaf.SOLTECTheme.getScaledIcon(this.getClass().getResource("/com/openbravo/images/barcode.png"), 24, 24));
        this.m_jEnter.setToolTipText("Get Barcode");
        this.m_jEnter.setFocusPainted(false);
        this.m_jEnter.setFocusable(false);
        this.m_jEnter.setMaximumSize(new Dimension(54, 33));
        this.m_jEnter.setMinimumSize(new Dimension(54, 33));
        this.m_jEnter.setPreferredSize(new Dimension(64, 45));
        this.m_jEnter.setRequestFocusEnabled(false);
        this.m_jEnter.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                StockDiaryEditor.this.m_jEnterActionPerformed(evt);
            }
        });
        this.jLblRef.setFont(new Font("Arial", 0, 14));
        this.jLblRef.setText(AppLocal.getIntString("label.prodref"));
        this.jLblRef.setMaximumSize(new Dimension(40, 20));
        this.jLblRef.setMinimumSize(new Dimension(40, 20));
        this.jLblRef.setPreferredSize(new Dimension(110, 30));
        this.m_jreference.setFont(new Font("Arial", 0, 14));
        this.m_jreference.setPreferredSize(new Dimension(170, 30));
        this.m_jreference.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                StockDiaryEditor.this.m_jreferenceActionPerformed(evt);
            }
        });
        this.m_jEnter1.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/products.png")));
        this.m_jEnter1.setToolTipText("Enter Product ID");
        this.m_jEnter1.setFocusPainted(false);
        this.m_jEnter1.setFocusable(false);
        this.m_jEnter1.setMaximumSize(new Dimension(64, 33));
        this.m_jEnter1.setMinimumSize(new Dimension(64, 33));
        this.m_jEnter1.setPreferredSize(new Dimension(64, 45));
        this.m_jEnter1.setRequestFocusEnabled(false);
        this.m_jEnter1.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                StockDiaryEditor.this.m_jEnter1ActionPerformed(evt);
            }
        });
        this.jLblAtt.setFont(new Font("Arial", 0, 14));
        this.jLblAtt.setText(AppLocal.getIntString("label.attributes"));
        this.jLblAtt.setMaximumSize(new Dimension(48, 20));
        this.jLblAtt.setMinimumSize(new Dimension(48, 20));
        this.jLblAtt.setPreferredSize(new Dimension(110, 30));
        this.jattributes.setEditable(false);
        this.jattributes.setFont(new Font("Arial", 0, 14));
        this.jattributes.setPreferredSize(new Dimension(170, 30));
        this.jattributes.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                StockDiaryEditor.this.jattributesActionPerformed(evt);
            }
        });
        this.jEditAttributes.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/attributes.png")));
        this.jEditAttributes.setToolTipText("Product Attributes");
        this.jEditAttributes.setMaximumSize(new Dimension(65, 33));
        this.jEditAttributes.setMinimumSize(new Dimension(65, 33));
        this.jEditAttributes.setPreferredSize(new Dimension(64, 45));
        this.jEditAttributes.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                StockDiaryEditor.this.jEditAttributesActionPerformed(evt);
            }
        });
        this.jLblUnits.setFont(new Font("Arial", 0, 14));
        this.jLblUnits.setText(AppLocal.getIntString("label.units"));
        this.jLblUnits.setMaximumSize(new Dimension(40, 20));
        this.jLblUnits.setMinimumSize(new Dimension(40, 20));
        this.jLblUnits.setPreferredSize(new Dimension(110, 30));
        this.m_junits.setFont(new Font("Arial", 0, 14));
        this.m_junits.setHorizontalAlignment(4);
        this.m_junits.setPreferredSize(new Dimension(100, 30));
        this.jLblPrice.setFont(new Font("Arial", 0, 14));
        this.jLblPrice.setText(AppLocal.getIntString("label.price"));
        this.jLblPrice.setPreferredSize(new Dimension(110, 30));
        this.m_jprice.setFont(new Font("Arial", 0, 14));
        this.m_jprice.setHorizontalAlignment(4);
        this.m_jprice.setPreferredSize(new Dimension(170, 30));
        this.catcontainer.setBackground(new Color(255, 255, 255));
        this.catcontainer.setFont(new Font("Arial", 0, 12));
        this.catcontainer.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        this.catcontainer.setMinimumSize(new Dimension(0, 250));
        this.catcontainer.setPreferredSize(new Dimension(600, 0));
        this.catcontainer.setLayout(new BorderLayout());
        this.m_jLocationDes.setFont(new Font("Arial", 0, 14));
        this.m_jLocationDes.setEnabled(false);
        this.m_jLocationDes.setPreferredSize(new Dimension(170, 30));
        this.m_jLocationDes.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                StockDiaryEditor.this.m_jLocationDesActionPerformed(evt);
            }
        });
        this.jLblMoveTo.setFont(new Font("Arial", 0, 14));
        this.jLblMoveTo.setHorizontalAlignment(2);
        ResourceBundle bundle = ResourceBundle.getBundle("pos_messages");
        this.jLblMoveTo.setText(bundle.getString("label.moveto"));
        this.jLblMoveTo.setEnabled(false);
        this.jLblMoveTo.setPreferredSize(new Dimension(110, 30));
        this.m_jSupplier.setFont(new Font("Arial", 0, 14));
        this.m_jSupplier.setPreferredSize(new Dimension(170, 30));
        this.jLblLocation1.setFont(new Font("Arial", 0, 14));
        this.jLblLocation1.setHorizontalAlignment(2);
        this.jLblLocation1.setText(AppLocal.getIntString("button.exit"));
        this.jLblLocation1.setPreferredSize(new Dimension(110, 30));
        GroupLayout jPanel1Layout = new GroupLayout((Container)this.jPanel1);
        this.jPanel1.setLayout((LayoutManager)jPanel1Layout);
        jPanel1Layout.setHorizontalGroup((GroupLayout.Group)jPanel1Layout.createParallelGroup(1).add((GroupLayout.Group)jPanel1Layout.createSequentialGroup().addContainerGap().add((GroupLayout.Group)jPanel1Layout.createParallelGroup(1).add((GroupLayout.Group)jPanel1Layout.createSequentialGroup().add((Component)this.jLblDate, -2, 110, -2).addPreferredGap(0).add((Component)this.m_jdate, -2, -1, -2).addPreferredGap(1).add((Component)this.m_jbtndate, -2, -1, -2)).add((Component)this.jLblReason, -2, -1, -2).add((GroupLayout.Group)jPanel1Layout.createSequentialGroup().add((Component)this.jLblLocation, -2, -1, -2).addPreferredGap(0).add((GroupLayout.Group)jPanel1Layout.createParallelGroup(1, false).add(this.m_jreason, 0, -1, -2).add(this.m_jLocation, 0, -1, -2))).add((Component)this.jLblMoveTo, -2, -1, -2).add((GroupLayout.Group)jPanel1Layout.createSequentialGroup().add((Component)this.jLblLocation1, -2, -1, -2).addPreferredGap(0).add((GroupLayout.Group)jPanel1Layout.createParallelGroup(1, false).add(this.m_jLocationDes, 0, -1, -2).add(this.m_jSupplier, 0, -1, -2))).add((GroupLayout.Group)jPanel1Layout.createSequentialGroup().add((Component)this.jLblAtt, -2, -1, -2).addPreferredGap(0).add((Component)this.jattributes, -2, -1, -2).addPreferredGap(1).add((Component)this.jEditAttributes, -2, -1, -2)).add((GroupLayout.Group)jPanel1Layout.createSequentialGroup().add((Component)this.jLBCode, -2, -1, -2).addPreferredGap(0).add((Component)this.m_jcodebar, -2, -1, -2).addPreferredGap(1).add((Component)this.m_jEnter, -2, -1, -2)).add((GroupLayout.Group)jPanel1Layout.createSequentialGroup().add((Component)this.jLblUnits, -2, -1, -2).addPreferredGap(0).add((Component)this.m_junits, -2, -1, -2)).add((GroupLayout.Group)jPanel1Layout.createSequentialGroup().add((Component)this.jLblPrice, -2, -1, -2).addPreferredGap(0).add((Component)this.m_jprice, -2, -1, -2)).add((GroupLayout.Group)jPanel1Layout.createSequentialGroup().add((GroupLayout.Group)jPanel1Layout.createParallelGroup(1).add((Component)this.jLblName, -2, -1, -2).add((GroupLayout.Group)jPanel1Layout.createSequentialGroup().add((Component)this.jLblRef, -2, -1, -2).addPreferredGap(0).add((Component)this.m_jreference, -2, -1, -2)).add((GroupLayout.Group)jPanel1Layout.createSequentialGroup().add(114, 114, 114).add((Component)this.jproduct, -1, -1, -2))).addPreferredGap(1).add((GroupLayout.Group)jPanel1Layout.createParallelGroup(1).add((Component)this.m_jEnter1, -2, -1, -2).add((Component)this.jEditProduct, -2, -1, -2)))).addPreferredGap(1).add((Component)this.catcontainer, -1, 612, Short.MAX_VALUE).addContainerGap()));
        jPanel1Layout.setVerticalGroup((GroupLayout.Group)jPanel1Layout.createParallelGroup(1).add((GroupLayout.Group)jPanel1Layout.createSequentialGroup().add((GroupLayout.Group)jPanel1Layout.createParallelGroup(1).add((GroupLayout.Group)jPanel1Layout.createSequentialGroup().add((GroupLayout.Group)jPanel1Layout.createParallelGroup(1).add((GroupLayout.Group)jPanel1Layout.createParallelGroup(3).add((Component)this.m_jdate, -2, -1, -2).add((Component)this.jLblDate, -2, -1, -2)).add((Component)this.m_jbtndate, -2, -1, -2)).addPreferredGap(0).add((GroupLayout.Group)jPanel1Layout.createParallelGroup(3).add(this.m_jreason, -2, -1, -2).add((Component)this.jLblReason, -2, -1, -2)).addPreferredGap(1).add((GroupLayout.Group)jPanel1Layout.createParallelGroup(3).add(this.m_jLocation, -2, -1, -2).add((Component)this.jLblLocation, -2, -1, -2)).addPreferredGap(1).add((GroupLayout.Group)jPanel1Layout.createParallelGroup(3).add((Component)this.jLblMoveTo, -2, -1, -2).add(this.m_jLocationDes, -2, -1, -2)).addPreferredGap(1).add((GroupLayout.Group)jPanel1Layout.createParallelGroup(3).add(this.m_jSupplier, -2, -1, -2).add((Component)this.jLblLocation1, -2, -1, -2)).addPreferredGap(0).add((GroupLayout.Group)jPanel1Layout.createParallelGroup(1).add((GroupLayout.Group)jPanel1Layout.createParallelGroup(3).add((Component)this.jLblName, -2, -1, -2).add((Component)this.jproduct, -2, -1, -2)).add((Component)this.jEditProduct, -2, -1, -2)).addPreferredGap(1).add((GroupLayout.Group)jPanel1Layout.createParallelGroup(1).add((GroupLayout.Group)jPanel1Layout.createParallelGroup(3).add((Component)this.m_jreference, -2, -1, -2).add((Component)this.jLblRef, -2, -1, -2)).add((Component)this.m_jEnter1, -2, -1, -2)).addPreferredGap(1).add((GroupLayout.Group)jPanel1Layout.createParallelGroup(1).add((GroupLayout.Group)jPanel1Layout.createParallelGroup(3).add((Component)this.jattributes, -2, -1, -2).add((Component)this.jLblAtt, -2, -1, -2)).add((Component)this.jEditAttributes, -2, -1, -2)).addPreferredGap(1).add((GroupLayout.Group)jPanel1Layout.createParallelGroup(1).add((Component)this.m_jEnter, -2, -1, -2).add((GroupLayout.Group)jPanel1Layout.createSequentialGroup().add((GroupLayout.Group)jPanel1Layout.createParallelGroup(3).add((Component)this.m_jcodebar, -2, -1, -2).add((Component)this.jLBCode, -2, -1, -2)).add(18, 18, 18).add((GroupLayout.Group)jPanel1Layout.createParallelGroup(1).add((Component)this.jLblUnits, -2, -1, -2).add((Component)this.m_junits, -2, -1, -2)).add(18, 18, 18).add((GroupLayout.Group)jPanel1Layout.createParallelGroup(1).add((Component)this.jLblPrice, -2, -1, -2).add((Component)this.m_jprice, -2, -1, -2)))).add(0, 47, Short.MAX_VALUE)).add((Component)this.catcontainer, -1, -1, Short.MAX_VALUE)).addContainerGap(48, Short.MAX_VALUE)));
        this.catcontainer.getAccessibleContext().setAccessibleParent(this.jPanel1);
        this.add((Component)this.jPanel1, "First");
    }

    private void m_jEnter1ActionPerformed(ActionEvent evt) {
        this.assignProductByReference();
    }

    private void m_jreferenceActionPerformed(ActionEvent evt) {
        this.assignProductByReference();
    }

    private void m_jcodebarActionPerformed(ActionEvent evt) {
        this.assignProductByCode();
    }

    private void m_jEnterActionPerformed(ActionEvent evt) {
        this.assignProductByCode();
    }

    private void jEditAttributesActionPerformed(ActionEvent evt) {
        if (this.productid == null) {
            MessageInf msg = new MessageInf(-33554432, AppLocal.getIntString("message.productnotselected"));
            msg.show(this);
        } else {
            try {
                JProductAttEdit attedit = JProductAttEdit.getAttributesEditor(this, this.m_App.getSession());
                attedit.editAttributes(this.attsetid, this.attsetinstid);
                attedit.setVisible(true);
                if (attedit.isOK()) {
                    this.attsetinstid = attedit.getAttributeSetInst();
                    this.attsetinstdesc = attedit.getAttributeSetInstDescription();
                    this.jattributes.setText(this.attsetinstdesc);
                }
            }
            catch (BasicException ex) {
                MessageInf msg = new MessageInf(-33554432, AppLocal.getIntString("message.cannotfindattributes"), ex);
                msg.show(this);
            }
        }
    }

    private void m_jbtndateActionPerformed(ActionEvent evt) {
        Date date;
        try {
            date = (Date)Formats.TIMESTAMP.parseValue(this.m_jdate.getText());
        }
        catch (BasicException e) {
            date = null;
        }
        date = JCalendarDialog.showCalendarTime(this, date);
        if (date != null) {
            this.m_jdate.setText(Formats.TIMESTAMP.formatValue(date));
        }
    }

    private void jEditProductActionPerformed(ActionEvent evt) {
        this.assignProduct(JProductFinder.showMessage(this, this.m_dlSales));
    }

    private void jattributesActionPerformed(ActionEvent evt) {
    }

    private void m_jLocationDesActionPerformed(ActionEvent evt) {
    }

    private void jproductActionPerformed(ActionEvent evt) {
    }

    private void m_jreasonActionPerformed(ActionEvent evt) {
        if (this.m_ReasonModel.getSelectedItem() == MovementReason.OUT_CROSSING) {
            JOptionPane.showMessageDialog(this, "Transfer option in development. Please us (In) + (Out) Movement options");
        }
    }

    private class CatalogListener
    implements ActionListener {
        private CatalogListener() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            StockDiaryEditor.this.assignProduct((ProductInfoExt)e.getSource());
        }
    }
}

