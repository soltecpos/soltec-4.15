/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bsh.EvalError
 *  bsh.Interpreter
 *  net.sf.jasperreports.engine.JRDataSource
 *  net.sf.jasperreports.engine.JRException
 *  net.sf.jasperreports.engine.JasperCompileManager
 *  net.sf.jasperreports.engine.JasperFillManager
 *  net.sf.jasperreports.engine.JasperPrint
 *  net.sf.jasperreports.engine.JasperReport
 *  net.sf.jasperreports.engine.data.JRMapArrayDataSource
 *  net.sf.jasperreports.engine.design.JasperDesign
 *  net.sf.jasperreports.engine.xml.JRXmlLoader
 */
package com.openbravo.pos.sales;

import bsh.EvalError;
import bsh.Interpreter;
import com.openbravo.basic.BasicException;
import com.openbravo.beans.JNumberEvent;
import com.openbravo.beans.JNumberEventListener;
import com.openbravo.beans.JNumberKeys;
import com.openbravo.data.gui.ComboBoxValModel;
import com.openbravo.data.gui.JMessageDialog;
import com.openbravo.data.gui.ListKeyed;
import com.openbravo.data.gui.MessageInf;
import com.openbravo.data.loader.SentenceList;
import com.openbravo.format.Formats;
import com.openbravo.pos.customers.CustomerInfo;
import com.openbravo.pos.customers.CustomerInfoExt;
import com.openbravo.pos.customers.CustomerInfoGlobal;
import com.openbravo.pos.customers.DataLogicCustomers;
import com.openbravo.pos.customers.JDialogNewCustomer;
import com.openbravo.pos.forms.AppConfig;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.forms.BeanFactoryApp;
import com.openbravo.pos.forms.BeanFactoryException;
import com.openbravo.pos.forms.DataLogicSales;
import com.openbravo.pos.forms.DataLogicSystem;
import com.openbravo.pos.forms.EmisionAPI;
import com.openbravo.pos.forms.JPanelView;
import com.openbravo.pos.forms.JRootApp;
import com.openbravo.pos.inventory.ProductStock;
import com.openbravo.pos.inventory.TaxCategoryInfo;
import com.openbravo.pos.panels.JProductFinder;
import com.openbravo.pos.panels.JProductQuickSearch;
import com.openbravo.pos.payment.JPaymentSelect;
import com.openbravo.pos.payment.JPaymentSelectReceipt;
import com.openbravo.pos.payment.JPaymentSelectRefund;
import com.openbravo.pos.plaf.SOLTECTheme;
import com.openbravo.pos.printer.TicketParser;
import com.openbravo.pos.printer.TicketPrinterException;
import com.openbravo.pos.sales.DataLogicReceipts;
import com.openbravo.pos.sales.JDialogCreateProduct;
import com.openbravo.pos.sales.JMooringDetails;
import com.openbravo.pos.sales.JPanelButtons;
import com.openbravo.pos.sales.JProductAttEdit2;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import com.openbravo.pos.sales.JProductLineEdit;
import com.openbravo.pos.sales.JTicketLines;
import com.openbravo.pos.sales.JTicketsBag;
import com.openbravo.pos.sales.KitchenDisplay;
import com.openbravo.pos.sales.ReceiptSplit;
import com.openbravo.pos.sales.TaxesException;
import com.openbravo.pos.sales.TaxesLogic;
import com.openbravo.pos.sales.TicketsEditor;
import com.openbravo.pos.sales.restaurant.RestaurantDBUtils;
import com.openbravo.pos.scale.ScaleException;
import com.openbravo.pos.scripting.ScriptEngine;
import com.openbravo.pos.scripting.ScriptException;
import com.openbravo.pos.scripting.ScriptFactory;
import com.openbravo.pos.ticket.CategoryInfo;
import com.openbravo.pos.ticket.ProductInfoExt;
import com.openbravo.pos.ticket.TaxInfo;
import com.openbravo.pos.ticket.TicketInfo;
import com.openbravo.pos.ticket.TicketLineInfo;
import com.openbravo.pos.ticket.TicketTaxInfo;
import com.openbravo.pos.util.AltEncrypter;
import com.openbravo.pos.util.InactivityListener;
import com.openbravo.pos.util.JRPrinterAWT300;
import com.openbravo.pos.util.ReportUtils;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.PrintService;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.KeyStroke;
import javax.swing.Scrollable;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRMapArrayDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

public abstract class JPanelTicket
extends JPanel
implements JPanelView,
BeanFactoryApp,
TicketsEditor {
    private static final int NUMBERZERO = 0;
    private static final int NUMBERVALID = 1;
    private static final int NUMBER_INPUTZERO = 0;
    private static final int NUMBER_INPUTZERODEC = 1;
    private static final int NUMBER_INPUTINT = 2;
    private static final int NUMBER_INPUTDEC = 3;
    private static final int NUMBER_PORZERO = 4;
    private static final int NUMBER_PORZERODEC = 5;
    private static final int NUMBER_PORINT = 6;
    private static final int NUMBER_PORDEC = 7;
    protected JTicketLines m_ticketlines;
    private TicketParser m_TTP;
    protected TicketInfo m_oTicket;
    protected Object m_oTicketExt;
    private int m_iNumberStatus;
    private int m_iNumberStatusInput;
    private int m_iNumberStatusPor;
    private StringBuffer m_sBarcode;
    private JTicketsBag m_ticketsbag;
    private SentenceList<TaxInfo> senttax;
    private ListKeyed<TaxInfo> taxcollection;
    private SentenceList<TaxCategoryInfo> senttaxcategories;
    private ListKeyed<TaxCategoryInfo> taxcategoriescollection;
    private ComboBoxValModel<TaxCategoryInfo> taxcategoriesmodel;
    private ComboBoxValModel<CategoryInfo> categoriesmodel;
    private TaxesLogic taxeslogic;
    protected JPanelButtons m_jbtnconfig;
    protected AppView m_App;
    protected DataLogicSystem dlSystem;
    protected DataLogicSales dlSales;
    protected DataLogicCustomers dlCustomers;
    private JPaymentSelect paymentdialogreceipt;
    private JPaymentSelect paymentdialogrefund;
    private JRootApp root;
    private Object m_principalapp;
    private Boolean restaurant;
    private Action logout;
    private InactivityListener listener;
    private Integer delay = 0;
    private final String m_sCurrentTicket;
    protected TicketsEditor m_panelticket;
    private DataLogicReceipts dlReceipts = null;
    private Boolean priceWith00;
    private final String temp_jPrice = "";
    private String tableDetails;
    private RestaurantDBUtils restDB;
    private KitchenDisplay kitchenDisplay;
    private String ticketPrintType;
    private Boolean warrantyPrint = false;
    private TicketInfo m_ticket;
    private TicketInfo m_ticketCopy;
    private AppConfig m_config;
    private JButton btnCreateCustomer;
    private JComboBox<CustomerInfo> cbCustomerSearch;
    private Timer searchTimer;
    private final int SEARCH_DELAY = 500;
    private ActionListener searchActionListener;
    private JButton btnCreateProduct;
    private JComboBox<ProductInfoExt> cbProductSearch;
    private Timer productSearchTimer;
    private ActionListener productSearchActionListener;
    private JLabel lblScaleDisplay;
    private Timer scaleTimer;
    private JToggleButton btnFE;
    private ImageIcon iconFeActive;
    private ImageIcon iconFeInactive;
    private JButton btnReprint1;
    private JButton btnSplit;
    private JPanel catcontainer;
    private Box.Filler filler2;
    private JButton jCheckStock;
    private JButton jEditAttributes;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JPanel jPanel4;
    private JPanel jPanel5;
    private JPanel jPanel9;
    private JPanel jPanel3;
    private JButton j_btnRemotePrt;
    private JButton jbtnMooring;
    private JPanel m_jButtons;
    private JPanel m_jButtonsExt;
    private JPanel m_jContEntries;
    private JButton m_jDelete;
    private JButton m_jEditLine;
    private JButton m_jEnter;
    private JTextField m_jKeyFactory;
    private JLabel m_jLblTotalEuros1;
    private JLabel m_jLblTotalEuros2;
    private JLabel m_jLblTotalEuros3;
    private JButton m_jList;
    private JNumberKeys m_jNumberKeys;
    private JPanel m_jOptions;
    private JPanel m_jPanContainer;
    private JPanel m_jPanEntries;
    private JPanel m_jPanTicket;
    private JPanel m_jPanTotals;
    private JPanel m_jPanelBag;
    private JPanel m_jPanelCentral;
    private JPanel m_jPanelScripts;
    private JLabel m_jPor;
    private JLabel m_jPrice;
    private JLabel m_jSubtotalEuros;
    private JComboBox<TaxCategoryInfo> m_jTax;
    private JLabel m_jTaxesEuros;
    private JLabel m_jTicketId;
    private JLabel m_jTotalEuros;
    private JCheckBox m_jaddtax;
    private JButton m_jbtnScale;

    private javax.swing.Icon safeIcon(String path) {
        java.net.URL url = getClass().getResource(path);
        if (url == null) {
            // Return a blank icon or a placeholder if resource is missing
            return new ImageIcon(new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB));
        }
        return new ImageIcon(url);
    }
    
    public JPanelTicket() {
        this.m_sCurrentTicket = null;
        try {
            this.initComponents();
        }
        catch (Throwable t) {
            try {
                String logPath = System.getProperty("user.home") + "/soltec_error.log";
                FileWriter fw = new FileWriter(logPath, true);
                PrintWriter pw = new PrintWriter(fw);
                t.printStackTrace(pw);
                pw.close();
                fw.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            throw new RuntimeException(t);
        }
    }

    @Override
    public void init(AppView app) throws BeanFactoryException {
        try {
            this.m_config = (AppConfig)app.getProperties();
            this.m_App = app;
            this.restDB = new RestaurantDBUtils(this.m_App);
            this.dlSystem = (DataLogicSystem)this.m_App.getBean("com.openbravo.pos.forms.DataLogicSystem");
            this.dlSales = (DataLogicSales)this.m_App.getBean("com.openbravo.pos.forms.DataLogicSales");
            this.dlCustomers = (DataLogicCustomers)this.m_App.getBean("com.openbravo.pos.customers.DataLogicCustomers");
            this.dlReceipts = (DataLogicReceipts)app.getBean("com.openbravo.pos.sales.DataLogicReceipts");
            if (!this.m_App.getDeviceScale().existsScale()) {
                this.m_jbtnScale.setVisible(false);
                this.lblScaleDisplay.setVisible(false);
            }
            if (Boolean.valueOf(this.m_App.getProperties().getProperty("till.amountattop")).booleanValue()) {
                this.m_jPanEntries.remove(this.jPanel9);
                this.m_jPanEntries.remove(this.m_jNumberKeys);
                this.m_jPanEntries.add(this.jPanel9);
                this.m_jPanEntries.add(this.m_jNumberKeys);
            }
            this.jbtnMooring.setVisible(Boolean.valueOf(this.m_App.getProperties().getProperty("till.marineoption")));
            this.priceWith00 = "true".equals(this.m_App.getProperties().getProperty("till.pricewith00"));
            if (this.priceWith00.booleanValue()) {
                this.m_jNumberKeys.dotIs00(true);
            }
            this.m_ticketsbag = this.getJTicketsBag();
            this.m_jPanelBag.add((Component)this.m_ticketsbag.getBagComponent(), "Before");
            this.add((Component)this.m_ticketsbag.getNullComponent(), "null");
            this.m_ticketlines = new JTicketLines(this.dlSystem.getResourceAsXML("Ticket.Line"));
            this.m_jPanelCentral.add((Component)this.m_ticketlines, "Center");
            this.m_TTP = new TicketParser(this.m_App.getDeviceTicket(), this.dlSystem);
            this.m_jbtnconfig = new JPanelButtons("Ticket.Buttons", this);
            this.m_jButtonsExt.add(this.m_jbtnconfig);
            boolean isGlobalFeEnabled = "true".equalsIgnoreCase(this.m_App.getProperties().getProperty("fe.enabled"));
            if (isGlobalFeEnabled) {
                try {
                    this.iconFeActive = SOLTECTheme.getScaledIcon(this.getClass().getResource("/com/openbravo/pos/images/fe_active.png"), 48, 48);
                    this.iconFeInactive = SOLTECTheme.getScaledIcon(this.getClass().getResource("/com/openbravo/pos/images/fe_inactive.png"), 48, 48);
                }
                catch (Exception e) {
                    System.err.println("FE Icons not found");
                }
                this.btnFE = new JToggleButton();
                this.btnFE.setPreferredSize(new Dimension(90, 70));
                this.btnFE.setHorizontalTextPosition(0);
                this.btnFE.setVerticalTextPosition(3);
                this.btnFE.setFont(new Font("Arial", 1, 10));
                this.btnFE.setToolTipText("Activar/Desactivar Facturaci\u00c3\u00b3n Electr\u00c3\u00b3nica");
                SOLTECTheme.applyToggleButtonStyle(this.btnFE);
                String feState = this.m_App.getProperties().getProperty("machine.fe_active");
                boolean isFeActive = feState != null ? "true".equals(feState) : isGlobalFeEnabled;
                this.btnFE.setSelected(isFeActive);
                this.updateFeButtonState(isFeActive);
                this.btnFE.addActionListener(new ActionListener(){

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        boolean active = JPanelTicket.this.btnFE.isSelected();
                        JPanelTicket.this.m_config.setProperty("machine.fe_active", Boolean.toString(active));
                        try {
                            JPanelTicket.this.m_config.save();
                        }
                        catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        if (JPanelTicket.this.m_oTicket != null) {
                            JPanelTicket.this.m_oTicket.setIsFe(active);
                            boolean isPoseEnabled = "true".equalsIgnoreCase(JPanelTicket.this.m_App.getProperties().getProperty("pose.enabled"));
                            if (isPoseEnabled) {
                                JPanelTicket.this.m_oTicket.setIsPose(!active);
                            }
                        }
                        JPanelTicket.this.updateFeButtonState(active);
                    }
                });
                this.btnFE.setIcon(this.iconFeInactive);
                this.btnFE.setSelectedIcon(this.iconFeActive);
                this.jPanel2.add((Component)this.btnFE, 0);
                this.jPanel2.revalidate();
                this.jPanel2.repaint();
            }
            this.catcontainer.add(this.getSouthComponent(), "Center");
            this.senttax = this.dlSales.getTaxList();
            this.senttaxcategories = this.dlSales.getTaxCategoriesList();
            this.taxcategoriesmodel = new ComboBoxValModel();
            this.stateToZero();
            this.m_oTicket = null;
            this.m_oTicketExt = null;
            this.jCheckStock.setText(AppLocal.getIntString("message.title.checkstock"));
            InputMap inputMap = this.getInputMap(2);
            ActionMap actionMap = this.getActionMap();
            inputMap.put(KeyStroke.getKeyStroke(10, 512), "productFinder");
            actionMap.put("productFinder", new AbstractAction(){

                @Override
                public void actionPerformed(ActionEvent e) {
                    ProductInfoExt product = JProductQuickSearch.showMessage(JPanelTicket.this, JPanelTicket.this.dlSales);
                    if (product != null) {
                        JPanelTicket.this.addTicketLine(product, 1.0, product.getPriceSell());
                    }
                    Timer timer = new Timer(200, new ActionListener(){

                        @Override
                        public void actionPerformed(ActionEvent evt) {
                            JPanelTicket.this.m_jKeyFactory.setEnabled(true);
                            JPanelTicket.this.m_jKeyFactory.requestFocusInWindow();
                        }
                    });
                    timer.setRepeats(false);
                    timer.start();
                }
            });
        }
        catch (Throwable t) {
            try {
                String logPath = System.getProperty("user.home") + "/soltec_error.log";
                FileWriter fw = new FileWriter(logPath, true);
                PrintWriter pw = new PrintWriter(fw);
                t.printStackTrace(pw);
                pw.close();
                fw.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            throw new BeanFactoryException(t);
        }
    }

    private void updateFeButtonState(boolean active) {
        if (this.btnFE != null) {
            this.btnFE.setSelected(active);
            if (active) {
                this.btnFE.setText("FE ACTIVA");
                this.btnFE.setBackground(new Color(46, 204, 113));
                this.btnFE.setForeground(Color.WHITE);
            } else {
                this.btnFE.setText("FE INACTIVA");
                this.btnFE.setBackground(Color.WHITE);
                this.btnFE.setForeground(Color.BLACK);
            }
        }
    }

    @Override
    public Object getBean() {
        return this;
    }

    @Override
    public JComponent getComponent() {
        return this;
    }

    private void saveCurrentTicket() {
        String currentTicket = (String)this.m_oTicketExt;
        if (currentTicket != null) {
            try {
                this.dlReceipts.updateSharedTicket(currentTicket, this.m_oTicket, this.m_oTicket.getPickupId());
            }
            catch (BasicException e) {
                new MessageInf(e).show(this);
            }
        }
    }

    @Override
    public void activate() throws BasicException {
        logout logout2 = new logout();
        String autoLogoff = this.m_App.getProperties().getProperty("till.autoLogoff");
        if (autoLogoff != null && autoLogoff.equals("true")) {
            try {
                this.delay = Integer.parseInt(this.m_App.getProperties().getProperty("till.autotimer"));
            }
            catch (NumberFormatException e) {
                this.delay = 0;
            }
            this.delay = this.delay * 1000;
        }
        if (this.delay != 0) {
            this.listener = new InactivityListener(logout2, this.delay);
            this.listener.start();
        }
        this.paymentdialogreceipt = JPaymentSelectReceipt.getDialog(this);
        this.paymentdialogreceipt.init(this.m_App);
        this.paymentdialogrefund = JPaymentSelectRefund.getDialog(this);
        this.paymentdialogrefund.init(this.m_App);
        this.m_jaddtax.setSelected("true".equals(this.m_jbtnconfig.getProperty("taxesincluded")));
        List<TaxInfo> taxlist = this.senttax.list();
        this.taxcollection = new ListKeyed<TaxInfo>(taxlist);
        List<TaxCategoryInfo> taxcategorieslist = this.senttaxcategories.list();
        this.taxcategoriescollection = new ListKeyed<TaxCategoryInfo>(taxcategorieslist);
        this.taxcategoriesmodel = new ComboBoxValModel<TaxCategoryInfo>(taxcategorieslist);
        this.m_jTax.setModel(this.taxcategoriesmodel);
        List<CategoryInfo> categorieslist = this.dlSales.getRootCategories();
        this.categoriesmodel = new ComboBoxValModel<CategoryInfo>(categorieslist);
        String taxesid = this.m_jbtnconfig.getProperty("taxcategoryid");
        if (taxesid == null) {
            if (this.m_jTax.getItemCount() > 0) {
                this.m_jTax.setSelectedIndex(0);
            }
        } else {
            this.taxcategoriesmodel.setSelectedKey(taxesid);
        }
        this.taxeslogic = new TaxesLogic(taxlist);
        this.m_jaddtax.setSelected(Boolean.parseBoolean(this.m_App.getProperties().getProperty("till.taxincluded")));
        if (this.m_App.getAppUserView().getUser().hasPermission("sales.ChangeTaxOptions")) {
            this.m_jTax.setVisible(true);
            this.m_jaddtax.setVisible(true);
        } else {
            this.m_jTax.setVisible(false);
            this.m_jaddtax.setVisible(false);
        }
        if (this.m_App.getAppUserView().getUser().hasPermission("sales.PrintKitchen")) {
            this.j_btnRemotePrt.setVisible(true);
        } else {
            this.j_btnRemotePrt.setVisible(false);
        }
        this.m_jDelete.setEnabled(this.m_App.getAppUserView().getUser().hasPermission("sales.EditLines"));
        this.m_jNumberKeys.setMinusEnabled(this.m_App.getAppUserView().getUser().hasPermission("sales.EditLines"));
        this.m_jNumberKeys.setEqualsEnabled(this.m_App.getAppUserView().getUser().hasPermission("sales.Total"));
        this.m_jbtnconfig.setPermissions(this.m_App.getAppUserView().getUser());
        if (this.m_App.getAppUserView().getUser().hasPermission("sales.CreateProduct")) {
            this.btnCreateProduct.setVisible(true);
        } else {
            this.btnCreateProduct.setVisible(false);
        }
        this.m_ticketsbag.activate();
        CustomerInfoGlobal customerInfoGlobal = CustomerInfoGlobal.getInstance();
        if (customerInfoGlobal.getCustomerInfoExt() != null && this.m_oTicket != null) {
            this.m_oTicket.setCustomer(customerInfoGlobal.getCustomerInfoExt());
        }
        this.refreshTicket();
        if (this.scaleTimer != null && this.m_App.getDeviceScale().existsScale()) {
            this.scaleTimer.start();
        }
    }

    @Override
    public boolean deactivate() {
        if (this.listener != null) {
            this.listener.stop();
        }
        if (this.scaleTimer != null) {
            this.scaleTimer.stop();
        }
        return this.m_ticketsbag.deactivate();
    }

    protected abstract JTicketsBag getJTicketsBag();

    protected abstract Component getSouthComponent();

    protected abstract void resetSouthComponent();

    @Override
    public void setActiveTicket(TicketInfo oTicket, Object oTicketExt) {
        switch (this.m_App.getProperties().getProperty("machine.ticketsbag")) {
            case "restaurant": {
                if (!"true".equals(this.m_App.getProperties().getProperty("till.autoLogoffrestaurant")) || this.listener == null) break;
                this.listener.restart();
            }
        }
        this.m_oTicket = oTicket;
        this.m_oTicketExt = oTicketExt;
        if (this.m_oTicket != null) {
            boolean isFeActive;
            boolean isFeEnabled = "true".equalsIgnoreCase(this.m_App.getProperties().getProperty("fe.enabled"));
            boolean isPoseEnabled = "true".equalsIgnoreCase(this.m_App.getProperties().getProperty("pose.enabled"));
            boolean bl = isFeActive = this.btnFE != null ? this.btnFE.isSelected() : isFeEnabled;
            if (isFeEnabled && isFeActive) {
                this.m_oTicket.setIsFe(true);
                this.m_oTicket.setIsPose(false);
            } else if (isPoseEnabled) {
                this.m_oTicket.setIsFe(false);
                this.m_oTicket.setIsPose(true);
            }
            this.m_oTicket.setUser(this.m_App.getAppUserView().getUser().getUserInfo());
            this.m_oTicket.setActiveCash(this.m_App.getActiveCashIndex());
            this.m_oTicket.setDate(new Date());
            if (this.btnFE != null) {
                boolean currentFeState = this.m_oTicket.getIsFe();
                this.btnFE.setSelected(currentFeState);
                this.updateFeButtonState(currentFeState);
            }
            if ("restaurant".equals(this.m_App.getProperties().getProperty("machine.ticketsbag")) && !oTicket.getOldTicket()) {
                if (this.restDB.getCustomerNameInTable(oTicketExt.toString()) == null && this.m_oTicket.getCustomer() != null) {
                    this.restDB.setCustomerNameInTable(this.m_oTicket.getCustomer().toString(), oTicketExt.toString());
                }
                if (this.restDB.getWaiterNameInTable(oTicketExt.toString()) == null || "".equals(this.restDB.getWaiterNameInTable(oTicketExt.toString()))) {
                    this.restDB.setWaiterNameInTable(this.m_App.getAppUserView().getUser().getName(), oTicketExt.toString());
                }
                this.restDB.setTicketIdInTable(this.m_oTicket.getId(), oTicketExt.toString());
            }
        }
        if (this.m_oTicket == null || Boolean.parseBoolean(this.m_App.getProperties().getProperty("table.showwaiterdetails")) || Boolean.valueOf(this.m_App.getProperties().getProperty("table.showcustomerdetails")).booleanValue()) {
            // empty if block
        }
        if (this.m_oTicket != null && (Boolean.valueOf(this.m_App.getProperties().getProperty("table.showcustomerdetails")).booleanValue() || Boolean.parseBoolean(this.m_App.getProperties().getProperty("table.showwaiterdetails"))) && this.restDB.getTableMovedFlag(this.m_oTicket.getId()).booleanValue()) {
            this.restDB.moveCustomer(oTicketExt.toString(), this.m_oTicket.getId());
        }
        this.executeEvent(this.m_oTicket, this.m_oTicketExt, "ticket.show", new ScriptArg[0]);
        this.j_btnRemotePrt.setVisible(this.m_App.getAppUserView().getUser().hasPermission("sales.PrintKitchen"));
        this.refreshTicket();
    }

    @Override
    public TicketInfo getActiveTicket() {
        return this.m_oTicket;
    }

    private void refreshTicket() {
        CardLayout cl = (CardLayout)this.getLayout();
        if (this.m_oTicket == null) {
            this.m_jTicketId.setText(null);
            this.m_ticketlines.clearTicketLines();
            this.m_jSubtotalEuros.setText(null);
            this.m_jTaxesEuros.setText(null);
            this.m_jTotalEuros.setText(null);
            this.jCheckStock.setText(null);
            this.checkStock();
            this.countArticles();
            this.stateToZero();
            this.repaint();
            cl.show(this, "null");
            if (this.m_oTicket != null && this.m_oTicket.getLinesCount() == 0) {
                this.resetSouthComponent();
            }
        } else {
            if (this.m_oTicket.getTicketType() == 1) {
                this.m_jEditLine.setVisible(false);
                this.m_jList.setVisible(false);
            }
            for (TicketLineInfo line : this.m_oTicket.getLines()) {
                line.setTaxInfo(this.taxeslogic.getTaxInfo(line.getProductTaxCategoryID(), this.m_oTicket.getCustomer()));
            }
            this.m_jTicketId.setText(this.m_oTicket.getName(this.m_oTicketExt));
            this.m_ticketlines.clearTicketLines();
            for (int i = 0; i < this.m_oTicket.getLinesCount(); ++i) {
                this.m_ticketlines.addTicketLine(this.m_oTicket.getLine(i));
            }
            this.countArticles();
            this.printPartialTotals();
            this.stateToZero();
            cl.show(this, "ticket");
            if (this.m_oTicket.getLinesCount() == 0) {
                this.resetSouthComponent();
            }
            this.m_jKeyFactory.setText(null);
            EventQueue.invokeLater(new Runnable(){

                @Override
                public void run() {
                    JPanelTicket.this.m_jKeyFactory.requestFocus();
                }
            });
        }
    }

    private void countArticles() {
        if (this.m_oTicket != null) {
            for (int i = 0; i < this.m_oTicket.getLinesCount(); ++i) {
                if (this.m_App.getAppUserView().getUser().hasPermission("sales.Total") && this.m_oTicket.getArticlesCount() > 1.0) {
                    this.btnSplit.setEnabled(true);
                    continue;
                }
                this.btnSplit.setEnabled(false);
            }
        }
    }

    private void printPartialTotals() {
        if (this.m_oTicket.getLinesCount() == 0) {
            this.m_jSubtotalEuros.setText(null);
            this.m_jTaxesEuros.setText(null);
            this.m_jTotalEuros.setText(null);
        } else {
            this.m_jSubtotalEuros.setText(this.m_oTicket.printSubTotal());
            this.m_jTaxesEuros.setText(this.m_oTicket.printTax());
            this.m_jTotalEuros.setText(this.m_oTicket.printTotal());
            this.repaint();
        }
        this.repaint();
    }

    private void paintTicketLine(int index, TicketLineInfo oLine) {
        if (this.executeEventAndRefresh("ticket.setline", new ScriptArg("index", index), new ScriptArg("line", oLine)) == null) {
            this.m_oTicket.setLine(index, oLine);
            this.m_ticketlines.setTicketLine(index, oLine);
            this.m_ticketlines.setSelectedIndex(index);
            this.countArticles();
            this.visorTicketLine(oLine);
            this.printPartialTotals();
            this.stateToZero();
            this.executeEventAndRefresh("ticket.change", new ScriptArg[0]);
        }
    }

    private void addTicketLine(ProductInfoExt oProduct, double dMul, double dPrice) {
        if (oProduct.isVprice()) {
            TaxInfo tax = this.taxeslogic.getTaxInfo(oProduct.getTaxCategoryID(), this.m_oTicket.getCustomer());
            if (this.m_jaddtax.isSelected()) {
                dPrice /= 1.0 + tax.getRate();
            }
            this.addTicketLine(new TicketLineInfo(oProduct, dMul, dPrice, tax, (Properties)oProduct.getProperties().clone()));
        } else if (oProduct.getID().equals("xxx998_998xxx_x8x8x8")) {
            if (this.m_App.getProperties().getProperty("till.SCOnOff").equals("true")) {
                TaxInfo tax = this.taxeslogic.getTaxInfo(oProduct.getTaxCategoryID(), this.m_oTicket.getCustomer());
                String SCRate = this.m_App.getProperties().getProperty("till.SCRate");
                double scharge = Double.parseDouble(SCRate);
                scharge = this.m_oTicket.getTotal() * (scharge / 100.0);
                this.addTicketLine(new TicketLineInfo(oProduct, 1.0, scharge, tax, (Properties)oProduct.getProperties().clone()));
            } else {
                JOptionPane.showMessageDialog(this, "Service Charge Not Enabled");
            }
        } else {
            TaxInfo tax = this.taxeslogic.getTaxInfo(oProduct.getTaxCategoryID(), this.m_oTicket.getCustomer());
            this.addTicketLine(new TicketLineInfo(oProduct, dMul, dPrice, tax, (Properties)oProduct.getProperties().clone()));
        }
        this.j_btnRemotePrt.setEnabled(true);
    }

    protected void addTicketLine(TicketLineInfo oLine) {
        try {
            if (oLine.getProductID() != null) {
                double qtyInTicket = 0.0;
                for (TicketLineInfo l : this.m_oTicket.getLines()) {
                    if (!oLine.getProductID().equals(l.getProductID())) continue;
                    qtyInTicket += l.getMultiply();
                }
                String warehouse = this.m_App.getInventoryLocation();
                Object[] stockState = this.dlSales.getProductStockState(warehouse, oLine.getProductID());
                if (stockState != null) {
                    Double current = (Double)stockState[0];
                    Double security = (Double)stockState[1];
                    if (security != null && security > 0.0 && current - (qtyInTicket + oLine.getMultiply()) < security) {
                        JOptionPane.showMessageDialog(this, AppLocal.getIntString("message.lowstockwarning") + "\n" + oLine.getProductName() + "\nStock: " + Formats.DOUBLE.formatValue(current) + " / Min: " + Formats.DOUBLE.formatValue(security), AppLocal.getIntString("message.lowstockwarning"), 2);
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        if (this.executeEventAndRefresh("ticket.addline", new ScriptArg("line", oLine)) == null) {
            if (oLine.isProductCom()) {
                int i = this.m_ticketlines.getSelectedIndex();
                if (i >= 0 && !this.m_oTicket.getLine(i).isProductCom()) {
                    ++i;
                }
                while (i >= 0 && i < this.m_oTicket.getLinesCount() && this.m_oTicket.getLine(i).isProductCom()) {
                    ++i;
                }
                if (i >= 0) {
                    this.m_oTicket.insertLine(i, oLine);
                    this.m_ticketlines.insertTicketLine(i, oLine);
                } else {
                    Toolkit.getDefaultToolkit().beep();
                }
            } else {
                this.m_oTicket.addLine(oLine);
                this.m_ticketlines.addTicketLine(oLine);
                try {
                    int i = this.m_ticketlines.getSelectedIndex();
                    TicketLineInfo line = this.m_oTicket.getLine(i);
                    if (line.isProductVerpatrib()) {
                        JProductAttEdit2 attedit = JProductAttEdit2.getAttributesEditor(this, this.m_App.getSession());
                        attedit.editAttributes(line.getProductAttSetId(), line.getProductAttSetInstId());
                        attedit.setVisible(true);
                        if (attedit.isOK()) {
                            line.setProductAttSetInstId(attedit.getAttributeSetInst());
                            line.setProductAttSetInstDesc(attedit.getAttributeSetInstDescription());
                            this.paintTicketLine(i, line);
                        }
                    }
                }
                catch (BasicException ex) {
                    MessageInf msg = new MessageInf(-33554432, AppLocal.getIntString("message.cannotfindattributes"), ex);
                    msg.show(this);
                }
            }
            this.visorTicketLine(oLine);
            this.printPartialTotals();
            this.stateToZero();
            this.checkStock();
            this.countArticles();
            this.executeEvent(this.m_oTicket, this.m_oTicketExt, "ticket.change", new ScriptArg[0]);
        }
    }

    private void removeTicketLine(int i) {
        if (this.executeEventAndRefresh("ticket.removeline", new ScriptArg("index", i)) == null) {
            String ticketID = Integer.toString(this.m_oTicket.getTicketId());
            if (this.m_oTicket.getTicketId() == 0) {
                ticketID = "Void";
            }
            try {
                TicketLineInfo oLine = this.m_oTicket.getLine(i);
                boolean sentToKitchen = "true".equals(oLine.getProperty("kitchen_printed"));
                String info = sentToKitchen ? "Sent to Kitchen: YES" : "Sent to Kitchen: NO";
                Object[] auditParams = new Object[]{UUID.randomUUID().toString(), new Date(), "DELETE", this.m_App.getAppUserView().getUser().getName(), oLine.getProductName(), oLine.getMultiply(), null, null, info, ticketID};
                this.dlSystem.execAuditEntry(auditParams);
            }
            catch (Exception oLine) {
                // empty catch block
            }
            this.dlSystem.execLineRemoved(new Object[]{this.m_App.getAppUserView().getUser().getName(), ticketID, this.m_oTicket.getLine(i).getProductID(), this.m_oTicket.getLine(i).getProductName(), this.m_oTicket.getLine(i).getMultiply()});
            if (this.m_oTicket.getLine(i).isProductCom()) {
                this.m_oTicket.removeLine(i);
                this.m_ticketlines.removeTicketLine(i);
            } else if (i < 1) {
                if (this.m_App.getAppUserView().getUser().hasPermission("sales.DeleteLines")) {
                    int input = JOptionPane.showConfirmDialog(this, AppLocal.getIntString("message.deletelineyes"), AppLocal.getIntString("label.deleteline"), 0);
                    if (input == 0) {
                        this.m_oTicket.removeLine(i);
                        this.m_ticketlines.removeTicketLine(i);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, AppLocal.getIntString("message.deletelineno"), AppLocal.getIntString("label.deleteline"), 2);
                }
            } else {
                this.m_oTicket.removeLine(i);
                this.m_ticketlines.removeTicketLine(i);
                while (i < this.m_oTicket.getLinesCount() && this.m_oTicket.getLine(i).isProductCom()) {
                    this.m_oTicket.removeLine(i);
                    this.m_ticketlines.removeTicketLine(i);
                }
            }
            this.visorTicketLine(null);
            this.printPartialTotals();
            this.stateToZero();
            this.checkStock();
            this.countArticles();
            this.executeEventAndRefresh("ticket.change", new ScriptArg[0]);
        }
    }

    private ProductInfoExt getInputProduct() {
        ProductInfoExt oProduct = new ProductInfoExt();
        oProduct.setID("xxx999_999xxx_x9x9x9");
        oProduct.setReference(null);
        oProduct.setCode(null);
        oProduct.setName("***");
        oProduct.setTaxCategoryID(((TaxCategoryInfo)this.taxcategoriesmodel.getSelectedItem()).getID());
        oProduct.setPriceSell(this.includeTaxes(oProduct.getTaxCategoryID(), this.getInputValue()));
        return oProduct;
    }

    private double includeTaxes(String tcid, double dValue) {
        if (this.m_jaddtax.isSelected()) {
            TaxInfo tax = this.taxeslogic.getTaxInfo(tcid, this.m_oTicket.getCustomer());
            double dTaxRate = tax == null ? 0.0 : tax.getRate();
            return dValue / (1.0 + dTaxRate);
        }
        return dValue;
    }

    private double excludeTaxes(String tcid, double dValue) {
        TaxInfo tax = this.taxeslogic.getTaxInfo(tcid, this.m_oTicket.getCustomer());
        double dTaxRate = tax == null ? 0.0 : tax.getRate();
        return dValue / (1.0 + dTaxRate);
    }

    private double getInputValue() {
        try {
            return Double.parseDouble(this.m_jPrice.getText());
        }
        catch (NumberFormatException e) {
            return 0.0;
        }
    }

    private double getPorValue() {
        try {
            return Double.parseDouble(this.m_jPor.getText().substring(1));
        }
        catch (NumberFormatException | StringIndexOutOfBoundsException e) {
            return 1.0;
        }
    }

    private void stateToZero() {
        this.m_jPor.setText("");
        this.m_jPrice.setText("");
        this.m_sBarcode = new StringBuffer();
        this.m_iNumberStatus = 0;
        this.m_iNumberStatusInput = 0;
        this.m_iNumberStatusPor = 0;
        this.repaint();
    }

    private void incProductByCode(String sCode) {
        try {
            ProductInfoExt oProduct = this.dlSales.getProductInfoByCode(sCode);
            if (oProduct == null) {
                this.askToCreateProduct(sCode);
            } else {
                this.incProduct(oProduct);
            }
        }
        catch (BasicException eData) {
            this.stateToZero();
            new MessageInf(eData).show(this);
        }
    }

    private void incProductByCodePrice(String sCode, double dPriceSell) {
        try {
            ProductInfoExt oProduct = this.dlSales.getProductInfoByCode(sCode);
            if (oProduct == null) {
                Toolkit.getDefaultToolkit().beep();
                new MessageInf(-33554432, AppLocal.getIntString("message.noproduct")).show(this);
                this.stateToZero();
            } else if (this.m_jaddtax.isSelected()) {
                TaxInfo tax = this.taxeslogic.getTaxInfo(oProduct.getTaxCategoryID(), this.m_oTicket.getCustomer());
                this.addTicketLine(oProduct, 1.0, dPriceSell / (1.0 + tax.getRate()));
            } else {
                this.addTicketLine(oProduct, 1.0, dPriceSell);
            }
        }
        catch (BasicException eData) {
            this.stateToZero();
            new MessageInf(eData).show(this);
        }
    }

    private void incProduct(ProductInfoExt prod) {
        if (prod.isScale() && this.m_App.getDeviceScale().existsScale()) {
            try {
                Double value = this.m_App.getDeviceScale().readWeight();
                if (value != null) {
                    this.incProduct(value, prod);
                }
            }
            catch (ScaleException e) {
                Toolkit.getDefaultToolkit().beep();
                new MessageInf(-33554432, AppLocal.getIntString("message.noweight"), e).show(this);
                this.stateToZero();
            }
        } else if (!prod.isVprice()) {
            this.incProduct(1.0, prod);
        } else {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, AppLocal.getIntString("message.novprice"));
        }
    }

    private void incProduct(double dPor, ProductInfoExt prod) {
        if (prod.isVprice()) {
            this.addTicketLine(prod, this.getPorValue(), this.getInputValue());
        } else {
            this.addTicketLine(prod, dPor, prod.getPriceSell());
        }
    }

    protected void buttonTransition(ProductInfoExt prod) {
        if (this.m_iNumberStatusInput == 0 && this.m_iNumberStatusPor == 0) {
            this.incProduct(prod);
        } else if (this.m_iNumberStatusInput == 1 && this.m_iNumberStatusPor == 0) {
            this.incProduct(this.getInputValue(), prod);
        } else if (prod.isVprice()) {
            this.addTicketLine(prod, this.getPorValue(), this.getInputValue());
        } else {
            Toolkit.getDefaultToolkit().beep();
        }
    }

    private void askToCreateProduct(String sCode) {
        Toolkit.getDefaultToolkit().beep();
        int res = JOptionPane.showConfirmDialog(this, AppLocal.getIntString("message.noproduct") + " " + sCode + "\n\n\u00c2\u00bfQuieres crear este producto?", AppLocal.getIntString("title.createproduct"), 0, 3);
        if (res == 0) {
            Window w = SwingUtilities.getWindowAncestor(this);
            JDialogCreateProduct dialog = new JDialogCreateProduct(w, this.dlSales, this.m_App, this.taxcategoriesmodel, this.categoriesmodel);
            if (dialog.showDialog(sCode)) {
                this.incProductByCode(sCode);
            } else {
                this.stateToZero();
            }
        } else {
            this.stateToZero();
        }
    }

    private void stateTransition(char cTrans) {
        block171: {
            if (cTrans == '\n' || cTrans == '?') {
                if (this.m_sBarcode.length() > 0) {
                    String sCode = this.m_sBarcode.toString();
                    String sCodetype = "EAN";
                    sCodetype = "true".equals(this.m_App.getProperties().getProperty("machine.barcodetype")) ? "UPC" : "EAN";
                    if (sCode.startsWith("C") || sCode.startsWith("c")) {
                        try {
                            String card = sCode;
                            CustomerInfoExt newcustomer = this.dlSales.findCustomerExt(card);
                            if (newcustomer == null) {
                                Toolkit.getDefaultToolkit().beep();
                                new MessageInf(-33554432, AppLocal.getIntString("message.nocustomer")).show(this);
                            } else {
                                this.m_oTicket.setCustomer(newcustomer);
                                this.m_jTicketId.setText(this.m_oTicket.getName(this.m_oTicketExt));
                            }
                        }
                        catch (BasicException e) {
                            Toolkit.getDefaultToolkit().beep();
                            new MessageInf(-33554432, AppLocal.getIntString("message.nocustomer"), e).show(this);
                        }
                        this.stateToZero();
                    } else if (sCode.startsWith(";")) {
                        this.stateToZero();
                    } else if ("EAN".equals(sCodetype) && (sCode.startsWith("2") || sCode.startsWith("02")) && (sCode.length() == 13 || sCode.length() == 12)) {
                        try {
                            ProductInfoExt oProduct = this.dlSales.getProductInfoByShortCode(sCode);
                            if (oProduct == null) {
                                this.askToCreateProduct(sCode);
                                break block171;
                            }
                            if (!"EAN-13".equals(oProduct.getCodetype())) break block171;
                            oProduct.setProperty("product.barcode", sCode);
                            double dPriceSell = oProduct.getPriceSell();
                            double weight = 0.0;
                            double dUnits = 0.0;
                            String sVariableTypePrefix = sCode.substring(0, 2);
                            String sVariableNum = sCode.length() == 13 ? sCode.substring(8, 12) : sCode.substring(7, 11);
                            switch (sVariableTypePrefix) {
                                case "02": {
                                    dUnits = Double.parseDouble(sVariableNum) / 100.0 / oProduct.getPriceSell();
                                    break;
                                }
                                case "20": {
                                    dUnits = Double.parseDouble(sVariableNum) / 100.0 / oProduct.getPriceSell();
                                    break;
                                }
                                case "21": {
                                    dUnits = Double.parseDouble(sVariableNum) / 10.0 / oProduct.getPriceSell();
                                    break;
                                }
                                case "22": {
                                    dUnits = Double.parseDouble(sVariableNum) / oProduct.getPriceSell();
                                    break;
                                }
                                case "23": {
                                    dUnits = weight = Double.parseDouble(sVariableNum) / 1000.0;
                                    break;
                                }
                                case "24": {
                                    dUnits = weight = Double.parseDouble(sVariableNum) / 100.0;
                                    break;
                                }
                                case "25": {
                                    dUnits = weight = Double.parseDouble(sVariableNum) / 10.0;
                                    break;
                                }
                            }
                            TaxInfo tax = this.taxeslogic.getTaxInfo(oProduct.getTaxCategoryID(), this.m_oTicket.getCustomer());
                            switch (sVariableTypePrefix) {
                                case "02": {
                                    dPriceSell = oProduct.getPriceSellTax(tax) / (1.0 + tax.getRate());
                                    dUnits = Double.parseDouble(sVariableNum) / 100.0 / oProduct.getPriceSellTax(tax);
                                    oProduct.setProperty("product.price", Double.toString(oProduct.getPriceSell()));
                                    break;
                                }
                                case "20": {
                                    dPriceSell = oProduct.getPriceSellTax(tax) / (1.0 + tax.getRate());
                                    dUnits = Double.parseDouble(sVariableNum) / 100.0 / oProduct.getPriceSellTax(tax);
                                    oProduct.setProperty("product.price", Double.toString(oProduct.getPriceSellTax(tax)));
                                    break;
                                }
                                case "21": {
                                    dPriceSell = oProduct.getPriceSellTax(tax) / (1.0 + tax.getRate());
                                    dUnits = Double.parseDouble(sVariableNum) / 10.0 / oProduct.getPriceSellTax(tax);
                                    oProduct.setProperty("product.price", Double.toString(oProduct.getPriceSell()));
                                    break;
                                }
                                case "22": {
                                    dPriceSell = oProduct.getPriceSellTax(tax) / (1.0 + tax.getRate());
                                    dUnits = Double.parseDouble(sVariableNum) / 1.0 / oProduct.getPriceSellTax(tax);
                                    oProduct.setProperty("product.price", Double.toString(oProduct.getPriceSell()));
                                    break;
                                }
                                case "23": {
                                    dUnits = weight = Double.parseDouble(sVariableNum) / 1000.0;
                                    oProduct.setProperty("product.weight", Double.toString(weight));
                                    oProduct.setProperty("product.price", Double.toString(dPriceSell));
                                    break;
                                }
                                case "24": {
                                    dUnits = weight = Double.parseDouble(sVariableNum) / 100.0;
                                    oProduct.setProperty("product.weight", Double.toString(weight));
                                    oProduct.setProperty("product.price", Double.toString(dPriceSell));
                                    break;
                                }
                                case "25": {
                                    dUnits = weight = Double.parseDouble(sVariableNum) / 10.0;
                                    oProduct.setProperty("product.weight", Double.toString(weight));
                                    oProduct.setProperty("product.price", Double.toString(dPriceSell));
                                    break;
                                }
                            }
                            if (this.m_jaddtax.isSelected()) {
                                dPriceSell = oProduct.getPriceSellTax(tax);
                                this.addTicketLine(oProduct, dUnits, dPriceSell);
                                break block171;
                            }
                            this.addTicketLine(oProduct, dUnits, dPriceSell);
                        }
                        catch (BasicException eData) {
                            this.stateToZero();
                            new MessageInf(eData).show(this);
                        }
                    } else if ("UPC".equals(sCodetype) && sCode.startsWith("2") && sCode.length() == 12) {
                        try {
                            ProductInfoExt oProduct = this.dlSales.getProductInfoByUShortCode(sCode);
                            if (oProduct == null) {
                                this.askToCreateProduct(sCode);
                                break block171;
                            }
                            if (!"Upc-A".equals(oProduct.getCodetype())) break block171;
                            oProduct.setProperty("product.barcode", sCode);
                            double dPriceSell = oProduct.getPriceSell();
                            double weight = 0.0;
                            double dUnits = 0.0;
                            String sVariableNum = sCode.substring(7, 11);
                            TaxInfo tax = this.taxeslogic.getTaxInfo(oProduct.getTaxCategoryID(), this.m_oTicket.getCustomer());
                            if (oProduct.getPriceSell() != 0.0) {
                                dUnits = weight = Double.parseDouble(sVariableNum) / 100.0;
                                oProduct.setProperty("product.weight", Double.toString(weight));
                                oProduct.setProperty("product.price", Double.toString(oProduct.getPriceSell()));
                                dPriceSell = oProduct.getPriceSellTax(tax);
                                dUnits = Double.parseDouble(sVariableNum) / 100.0 / oProduct.getPriceSellTax(tax);
                            } else {
                                dPriceSell = Double.parseDouble(sVariableNum) / 100.0;
                                dUnits = 1.0;
                            }
                            if (this.m_jaddtax.isSelected()) {
                                this.addTicketLine(oProduct, dUnits, dPriceSell);
                                break block171;
                            }
                            this.addTicketLine(oProduct, dUnits, dPriceSell / (1.0 + tax.getRate()));
                        }
                        catch (BasicException eData) {
                            this.stateToZero();
                            new MessageInf(eData).show(this);
                        }
                    } else {
                        this.incProductByCode(sCode);
                    }
                } else {
                    Toolkit.getDefaultToolkit().beep();
                }
            } else {
                this.m_sBarcode.append(cTrans);
                if (cTrans == '\u007f') {
                    this.stateToZero();
                } else if (cTrans == '0' && this.m_iNumberStatus == 0) {
                    this.m_jPrice.setText(Character.toString('0'));
                } else if ((cTrans == '1' || cTrans == '2' || cTrans == '3' || cTrans == '4' || cTrans == '5' || cTrans == '6' || cTrans == '7' || cTrans == '8' || cTrans == '9') && this.m_iNumberStatus == 0) {
                    if (!this.priceWith00.booleanValue()) {
                        this.m_jPrice.setText(this.m_jPrice.getText() + cTrans);
                    } else {
                        this.m_jPrice.setText(this.setTempjPrice(this.m_jPrice.getText() + cTrans));
                    }
                    this.m_iNumberStatus = 2;
                    this.m_iNumberStatusInput = 1;
                } else if ((cTrans == '0' || cTrans == '1' || cTrans == '2' || cTrans == '3' || cTrans == '4' || cTrans == '5' || cTrans == '6' || cTrans == '7' || cTrans == '8' || cTrans == '9') && this.m_iNumberStatus == 2) {
                    if (!this.priceWith00.booleanValue()) {
                        this.m_jPrice.setText(this.m_jPrice.getText() + cTrans);
                    } else {
                        this.m_jPrice.setText(this.setTempjPrice(this.m_jPrice.getText() + cTrans));
                    }
                } else if (cTrans == '.' && this.m_iNumberStatus == 0 && !this.priceWith00.booleanValue()) {
                    this.m_jPrice.setText("0.");
                    this.m_iNumberStatus = 1;
                } else if (cTrans == '.' && this.m_iNumberStatus == 0) {
                    this.m_jPrice.setText("");
                    this.m_iNumberStatus = 0;
                } else if (cTrans == '.' && this.m_iNumberStatus == 2 && !this.priceWith00.booleanValue()) {
                    this.m_jPrice.setText(this.m_jPrice.getText() + ".");
                    this.m_iNumberStatus = 3;
                } else if (cTrans == '.' && this.m_iNumberStatus == 2) {
                    if (!this.priceWith00.booleanValue()) {
                        this.m_jPrice.setText(this.m_jPrice.getText() + "00");
                    } else {
                        this.m_jPrice.setText(this.setTempjPrice(this.m_jPrice.getText() + "00"));
                    }
                    this.m_iNumberStatus = 2;
                } else if (cTrans == '0' && (this.m_iNumberStatus == 1 || this.m_iNumberStatus == 3)) {
                    if (!this.priceWith00.booleanValue()) {
                        this.m_jPrice.setText(this.m_jPrice.getText() + cTrans);
                    } else {
                        this.m_jPrice.setText(this.setTempjPrice(this.m_jPrice.getText() + cTrans));
                    }
                } else if (!(cTrans != '1' && cTrans != '2' && cTrans != '3' && cTrans != '4' && cTrans != '5' && cTrans != '6' && cTrans != '7' && cTrans != '8' && cTrans != '9' || this.m_iNumberStatus != 1 && this.m_iNumberStatus != 3)) {
                    this.m_jPrice.setText(this.m_jPrice.getText() + cTrans);
                    this.m_iNumberStatus = 3;
                    this.m_iNumberStatusInput = 1;
                } else if (cTrans == '*' && (this.m_iNumberStatus == 2 || this.m_iNumberStatus == 3)) {
                    this.m_jPor.setText("x");
                    this.m_iNumberStatus = 4;
                } else if (cTrans == '*' && (this.m_iNumberStatus == 0 || this.m_iNumberStatus == 1)) {
                    this.m_jPrice.setText("0");
                    this.m_jPor.setText("x");
                    this.m_iNumberStatus = 4;
                } else if (cTrans == '0' && this.m_iNumberStatus == 4) {
                    this.m_jPor.setText("x0");
                } else if ((cTrans == '1' || cTrans == '2' || cTrans == '3' || cTrans == '4' || cTrans == '5' || cTrans == '6' || cTrans == '7' || cTrans == '8' || cTrans == '9') && this.m_iNumberStatus == 4) {
                    this.m_jPor.setText("x" + Character.toString(cTrans));
                    this.m_iNumberStatus = 6;
                    this.m_iNumberStatusPor = 1;
                } else if ((cTrans == '0' || cTrans == '1' || cTrans == '2' || cTrans == '3' || cTrans == '4' || cTrans == '5' || cTrans == '6' || cTrans == '7' || cTrans == '8' || cTrans == '9') && this.m_iNumberStatus == 6) {
                    this.m_jPor.setText(this.m_jPor.getText() + cTrans);
                } else if (cTrans == '.' && this.m_iNumberStatus == 4 && !this.priceWith00.booleanValue()) {
                    this.m_jPor.setText("x0.");
                    this.m_iNumberStatus = 5;
                } else if (cTrans == '.' && this.m_iNumberStatus == 4) {
                    this.m_jPor.setText("x");
                    this.m_iNumberStatus = 1;
                } else if (cTrans == '.' && this.m_iNumberStatus == 6 && !this.priceWith00.booleanValue()) {
                    this.m_jPor.setText(this.m_jPor.getText() + ".");
                    this.m_iNumberStatus = 7;
                } else if (cTrans == '.' && this.m_iNumberStatus == 6) {
                    this.m_jPor.setText(this.m_jPor.getText() + "00");
                    this.m_iNumberStatus = 1;
                } else if (cTrans == '0' && (this.m_iNumberStatus == 5 || this.m_iNumberStatus == 7)) {
                    this.m_jPor.setText(this.m_jPor.getText() + cTrans);
                } else if (!(cTrans != '1' && cTrans != '2' && cTrans != '3' && cTrans != '4' && cTrans != '5' && cTrans != '6' && cTrans != '7' && cTrans != '8' && cTrans != '9' || this.m_iNumberStatus != 5 && this.m_iNumberStatus != 7)) {
                    this.m_jPor.setText(this.m_jPor.getText() + cTrans);
                    this.m_iNumberStatus = 7;
                    this.m_iNumberStatusPor = 1;
                } else if (cTrans == '\u00a7' && this.m_iNumberStatusInput == 1 && this.m_iNumberStatusPor == 0) {
                    if (this.m_App.getDeviceScale().existsScale() && this.m_App.getAppUserView().getUser().hasPermission("sales.EditLines")) {
                        try {
                            Double value = this.m_App.getDeviceScale().readWeight();
                            if (value != null) {
                                ProductInfoExt product = this.getInputProduct();
                                this.addTicketLine(product, value, product.getPriceSell());
                            }
                        }
                        catch (ScaleException e) {
                            Toolkit.getDefaultToolkit().beep();
                            new MessageInf(-33554432, AppLocal.getIntString("message.noweight"), e).show(this);
                            this.stateToZero();
                        }
                    } else {
                        Toolkit.getDefaultToolkit().beep();
                    }
                } else if (cTrans == '\u00a7' && this.m_iNumberStatusInput == 0 && this.m_iNumberStatusPor == 0) {
                    int i = this.m_ticketlines.getSelectedIndex();
                    if (i < 0) {
                        Toolkit.getDefaultToolkit().beep();
                    } else if (this.m_App.getDeviceScale().existsScale()) {
                        try {
                            Double value = this.m_App.getDeviceScale().readWeight();
                            if (value != null) {
                                TicketLineInfo newline = new TicketLineInfo(this.m_oTicket.getLine(i));
                                newline.setMultiply(value);
                                newline.setPrice(Math.abs(newline.getPrice()));
                                this.paintTicketLine(i, newline);
                            }
                        }
                        catch (ScaleException e) {
                            Toolkit.getDefaultToolkit().beep();
                            new MessageInf(-33554432, AppLocal.getIntString("message.noweight"), e).show(this);
                            this.stateToZero();
                        }
                    } else {
                        Toolkit.getDefaultToolkit().beep();
                    }
                } else if (cTrans == '+' && this.m_iNumberStatusInput == 0 && this.m_iNumberStatusPor == 0) {
                    int i = this.m_ticketlines.getSelectedIndex();
                    if (i < 0) {
                        Toolkit.getDefaultToolkit().beep();
                    } else {
                        TicketLineInfo newline = new TicketLineInfo(this.m_oTicket.getLine(i));
                        if (this.m_oTicket.getTicketType() == 1) {
                            newline.setMultiply(newline.getMultiply() - 1.0);
                            this.paintTicketLine(i, newline);
                        } else {
                            newline.setMultiply(newline.getMultiply() + 1.0);
                            this.paintTicketLine(i, newline);
                        }
                    }
                } else if (cTrans == '-' && this.m_iNumberStatusInput == 0 && this.m_iNumberStatusPor == 0 && this.m_App.getAppUserView().getUser().hasPermission("sales.EditLines")) {
                    int i = this.m_ticketlines.getSelectedIndex();
                    if (i < 0) {
                        Toolkit.getDefaultToolkit().beep();
                    } else {
                        TicketLineInfo newline = new TicketLineInfo(this.m_oTicket.getLine(i));
                        if (this.m_oTicket.getTicketType() == 1) {
                            newline.setMultiply(newline.getMultiply() + 1.0);
                            if (newline.getMultiply() >= 0.0) {
                                this.removeTicketLine(i);
                            } else {
                                this.paintTicketLine(i, newline);
                            }
                        } else {
                            newline.setMultiply(newline.getMultiply() - 1.0);
                            if (newline.getMultiply() <= 0.0) {
                                this.removeTicketLine(i);
                            } else {
                                this.paintTicketLine(i, newline);
                            }
                        }
                    }
                } else if (cTrans == '+' && this.m_iNumberStatusInput == 0 && this.m_iNumberStatusPor == 1) {
                    int i = this.m_ticketlines.getSelectedIndex();
                    if (i < 0) {
                        Toolkit.getDefaultToolkit().beep();
                    } else {
                        double dPor = this.getPorValue();
                        TicketLineInfo newline = new TicketLineInfo(this.m_oTicket.getLine(i));
                        if (this.m_oTicket.getTicketType() == 1) {
                            newline.setMultiply(-dPor);
                            newline.setPrice(Math.abs(newline.getPrice()));
                            this.paintTicketLine(i, newline);
                        } else {
                            newline.setMultiply(dPor);
                            newline.setPrice(Math.abs(newline.getPrice()));
                            this.paintTicketLine(i, newline);
                        }
                    }
                } else if (cTrans == '-' && this.m_iNumberStatusInput == 0 && this.m_iNumberStatusPor == 1 && this.m_App.getAppUserView().getUser().hasPermission("sales.EditLines")) {
                    int i = this.m_ticketlines.getSelectedIndex();
                    if (i < 0) {
                        Toolkit.getDefaultToolkit().beep();
                    } else {
                        double dPor = this.getPorValue();
                        TicketLineInfo newline = new TicketLineInfo(this.m_oTicket.getLine(i));
                        if (this.m_oTicket.getTicketType() == 0) {
                            newline.setMultiply(dPor);
                            newline.setPrice(-Math.abs(newline.getPrice()));
                            this.paintTicketLine(i, newline);
                        }
                    }
                } else if (cTrans == '+' && this.m_iNumberStatusInput == 1 && this.m_iNumberStatusPor == 0 && this.m_App.getAppUserView().getUser().hasPermission("sales.EditLines")) {
                    ProductInfoExt product = this.getInputProduct();
                    this.addTicketLine(product, 1.0, product.getPriceSell());
                    this.m_jEditLine.doClick();
                } else if (cTrans == '-' && this.m_iNumberStatusInput == 1 && this.m_iNumberStatusPor == 0 && this.m_App.getAppUserView().getUser().hasPermission("sales.EditLines")) {
                    ProductInfoExt product = this.getInputProduct();
                    this.addTicketLine(product, 1.0, -product.getPriceSell());
                    this.m_jEditLine.doClick();
                } else if (cTrans == '+' && this.m_iNumberStatusInput == 1 && this.m_iNumberStatusPor == 1 && this.m_App.getAppUserView().getUser().hasPermission("sales.EditLines")) {
                    ProductInfoExt product = this.getInputProduct();
                    this.addTicketLine(product, this.getPorValue(), product.getPriceSell());
                } else if (cTrans == '-' && this.m_iNumberStatusInput == 1 && this.m_iNumberStatusPor == 1 && this.m_App.getAppUserView().getUser().hasPermission("sales.EditLines")) {
                    ProductInfoExt product = this.getInputProduct();
                    this.addTicketLine(product, this.getPorValue(), -product.getPriceSell());
                } else if (cTrans == ' ' || cTrans == '=') {
                    if (this.m_oTicket.getLinesCount() > 0) {
                        if (this.closeTicket(this.m_oTicket, this.m_oTicketExt)) {
                            this.m_ticketsbag.deleteTicket();
                            String autoLogoff = this.m_App.getProperties().getProperty("till.autoLogoff");
                            if (autoLogoff != null && autoLogoff.equals("true")) {
                                if ("restaurant".equals(this.m_App.getProperties().getProperty("machine.ticketsbag")) && "true".equals(this.m_App.getProperties().getProperty("till.autoLogoffrestaurant"))) {
                                    this.deactivate();
                                    this.setActiveTicket(null, null);
                                } else {
                                    ((JRootApp)this.m_App).closeAppView();
                                }
                            }
                        } else {
                            this.refreshTicket();
                        }
                    } else {
                        Toolkit.getDefaultToolkit().beep();
                    }
                }
            }
        }
    }

    private boolean closeTicket(TicketInfo ticket, Object ticketext) {
        if (this.listener != null) {
            this.listener.stop();
        }
        boolean resultok = false;
        if (this.m_App.getAppUserView().getUser().hasPermission("sales.Total")) {
            block15: {
                this.warrantyCheck(ticket);
                try {
                    this.taxeslogic.calculateTaxes(ticket);
                    if (ticket.getTotal() >= 0.0) {
                        ticket.resetPayments();
                    }
                    if (this.executeEvent(ticket, ticketext, "ticket.total", new ScriptArg[0]) != null) break block15;
                    if (this.listener != null) {
                        this.listener.stop();
                    }
                    this.printTicket("Printer.TicketTotal", ticket, ticketext);
                    JPaymentSelect paymentdialog = ticket.getTicketType() == 0 ? this.paymentdialogreceipt : this.paymentdialogrefund;
                    paymentdialog.setPrintSelected("true".equals(this.m_jbtnconfig.getProperty("printselected", "true")));
                    paymentdialog.setTransactionID(ticket.getTransactionID());
                    if (!paymentdialog.showDialog(ticket.getTotal(), ticket.getCustomer())) break block15;
                    ticket.setPayments(paymentdialog.getSelectedPayments());
                    ticket.setUser(this.m_App.getAppUserView().getUser().getUserInfo());
                    ticket.setActiveCash(this.m_App.getActiveCashIndex());
                    ticket.setDate(new Date());
                    if (this.executeEvent(ticket, ticketext, "ticket.save", new ScriptArg[0]) != null) break block15;
                    try {
                        this.dlSales.saveTicket(ticket, this.m_App.getInventoryLocation());
                        this.m_config.setProperty("lastticket.number", Integer.toString(ticket.getTicketId()));
                        this.m_config.setProperty("lastticket.type", Integer.toString(ticket.getTicketType()));
                        this.m_config.save();
                    }
                    catch (BasicException eData) {
                        MessageInf msg = new MessageInf(-67108864, AppLocal.getIntString("message.nosaveticket"), eData);
                        msg.show(this);
                    }
                    catch (IOException ex) {
                        Logger.getLogger(JPanelTicket.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if (ticket.getIsFe() || ticket.getIsPose() || ticket.getIsDocs()) {
                        try {
                            EmisionAPI emisionApi = new EmisionAPI();
                            CompletableFuture<EmisionAPI.AsyncSaveTicketResponse> future = emisionApi.saveTicketAsync(ticket, this.m_App.getInventoryLocation(), this, ticket.getPayments(), this.m_App, ticketext, this.m_config, ticket.getTaxes());
                            EmisionAPI.AsyncSaveTicketResponse response = future.get();
                            if (response != null && response.getTicket() != null) {
                                ticket.setCufe(response.getTicket().getCufe());
                                if (response.getTicket().getcufeDiv() != null) {
                                    ticket.setcufeDiv(response.getTicket().getcufeDiv());
                                }
                            }
                        }
                        catch (Exception eFe) {
                            System.err.println("Error en Facturaci\u00f3n Electr\u00f3nica: " + eFe.getMessage());
                            eFe.printStackTrace();
                            MessageInf msgFe = new MessageInf(-33554432, "Error al enviar Factura Electr\u00f3nica: " + eFe.getMessage());
                            msgFe.show(this);
                        }
                    }
                    this.executeEvent(ticket, ticketext, "ticket.close", new ScriptArg("print", paymentdialog.isPrintSelected()));
                    this.printTicket(paymentdialog.isPrintSelected() || this.warrantyPrint != false ? "Printer.Ticket" : "Printer.Ticket2", ticket, ticketext);
                    this.Notify(AppLocal.getIntString("notify.printing"));
                    resultok = true;
                    if ("restaurant".equals(this.m_App.getProperties().getProperty("machine.ticketsbag")) && !ticket.getOldTicket()) {
                        this.restDB.clearCustomerNameInTable(ticketext.toString());
                        this.restDB.clearWaiterNameInTable(ticketext.toString());
                        this.restDB.clearTicketIdInTable(ticketext.toString());
                    }
                }
                catch (TaxesException e) {
                    MessageInf msg = new MessageInf(-33554432, AppLocal.getIntString("message.cannotcalculatetaxes"));
                    msg.show(this);
                    resultok = false;
                }
            }
            this.m_oTicket.resetTaxes();
            this.m_oTicket.resetPayments();
            this.jCheckStock.setText("");
        }
        return resultok;
    }

    private boolean warrantyCheck(TicketInfo ticket) {
        this.warrantyPrint = false;
        for (int lines = 0; lines < ticket.getLinesCount(); ++lines) {
            if (this.warrantyPrint.booleanValue()) continue;
            this.warrantyPrint = ticket.getLine(lines).isProductWarranty();
            return true;
        }
        return false;
    }

    public String getPickupString(TicketInfo pTicket) {
        if (pTicket == null) {
            return "0";
        }
        String tmpPickupId = Integer.toString(pTicket.getPickupId());
        String pickupSize = this.m_App.getProperties().getProperty("till.pickupsize");
        if (pickupSize != null && Integer.parseInt(pickupSize) >= tmpPickupId.length()) {
            while (tmpPickupId.length() < Integer.parseInt(pickupSize)) {
                tmpPickupId = "0" + tmpPickupId;
            }
        }
        return tmpPickupId;
    }

    private void printTicket(String sresourcename, TicketInfo ticket, Object ticketext) {
        String sresource = this.dlSystem.getResourceAsXML(sresourcename);
        if (sresource == null) {
            MessageInf msg = new MessageInf(-33554432, AppLocal.getIntString("message.cannotprintticket"));
            msg.show(this);
        } else {
            if (ticket.getPickupId() == 0) {
                try {
                    ticket.setPickupId(this.dlSales.getNextPickupIndex());
                }
                catch (BasicException e) {
                    ticket.setPickupId(0);
                }
            }
            try {
                ScriptEngine script = ScriptFactory.getScriptEngine("velocity");
                if (Boolean.parseBoolean(this.m_App.getProperties().getProperty("receipt.newlayout"))) {
                    script.put("taxes", ticket.getTaxLines());
                } else {
                    script.put("taxes", this.taxcollection);
                }
                script.put("taxeslogic", this.taxeslogic);
                script.put("ticket", ticket);
                script.put("place", ticketext);
                script.put("warranty", this.warrantyPrint);
                script.put("pickupid", this.getPickupString(ticket));
                this.refreshTicket();
                this.m_TTP.printTicket(script.eval(sresource).toString(), ticket);
            }
            catch (TicketPrinterException | ScriptException e) {
                MessageInf msg = new MessageInf(-33554432, AppLocal.getIntString("message.cannotprintticket"), e);
                msg.show(this);
            }
        }
    }

    public void printTicket(String resource) {
        if (resource == null) {
            MessageInf msg = new MessageInf(-33554432, AppLocal.getIntString("message.cannotexecute"));
            msg.show(this);
        } else {
            try {
                this.taxeslogic.calculateTaxes(this.m_oTicket);
                this.printTicket(resource, this.m_oTicket, this.m_oTicketExt);
            }
            catch (TaxesException e) {
                MessageInf msg = new MessageInf(-33554432, AppLocal.getIntString("message.cannotcalculatetaxes"));
                msg.show(this);
            }
        }
        this.Notify(AppLocal.getIntString("notify.printed"));
        this.j_btnRemotePrt.setEnabled(false);
    }

    public void customerAdd(String resource) {
        this.Notify(AppLocal.getIntString("notify.customeradd"));
    }

    public void customerRemove(String resource) {
        this.Notify(AppLocal.getIntString("notify.customerremove"));
    }

    public void customerChange(String resource) {
        this.Notify(AppLocal.getIntString("notify.customerchange"));
    }

    public void Notify(String msg) {
        Logger.getLogger(JPanelTicket.class.getName()).log(Level.INFO, "Notify: {0}", msg);
    }

    private void printReport(String resourcefile, TicketInfo ticket, Object ticketext) {
        try {
            JasperReport jr;
            InputStream in = this.getClass().getResourceAsStream(resourcefile + ".ser");
            if (in == null) {
                JasperDesign jd = JRXmlLoader.load((InputStream)this.getClass().getResourceAsStream(resourcefile + ".jrxml"));
                jr = JasperCompileManager.compileReport((JasperDesign)jd);
            } else {
                try (ObjectInputStream oin = new ObjectInputStream(in);){
                    jr = (JasperReport)oin.readObject();
                }
            }
            HashMap<String, Object> reportparams = new HashMap<String, Object>();
            try {
                reportparams.put("REPORT_RESOURCE_BUNDLE", ResourceBundle.getBundle(resourcefile + ".properties"));
            }
            catch (MissingResourceException missingResourceException) {
                // empty catch block
            }
            reportparams.put("TAXESLOGIC", this.taxeslogic);
            HashMap<String, Object> reportfields = new HashMap<String, Object>();
            reportfields.put("TICKET", ticket);
            reportfields.put("PLACE", ticketext);
            JasperPrint jp = JasperFillManager.fillReport((JasperReport)jr, reportparams, (JRDataSource)new JRMapArrayDataSource(new Object[]{reportfields}));
            PrintService service = ReportUtils.getPrintService(this.m_App.getProperties().getProperty("machine.printername"));
            JRPrinterAWT300.printPages(jp, 0, jp.getPages().size() - 1, service);
        }
        catch (IOException | ClassNotFoundException | JRException e) {
            MessageInf msg = new MessageInf(-33554432, AppLocal.getIntString("message.cannotloadreport"), e);
            msg.show(this);
        }
    }

    private void visorTicketLine(TicketLineInfo oLine) {
        if (oLine == null) {
            this.m_App.getDeviceTicket().getDeviceDisplay().clearVisor();
        } else {
            try {
                ScriptEngine script = ScriptFactory.getScriptEngine("velocity");
                script.put("ticketline", oLine);
                this.m_TTP.printTicket(script.eval(this.dlSystem.getResourceAsXML("Printer.TicketLine")).toString());
            }
            catch (TicketPrinterException | ScriptException e) {
                MessageInf msg = new MessageInf(-33554432, AppLocal.getIntString("message.cannotprintline"), e);
                msg.show(this);
            }
        }
    }

    private Object evalScript(ScriptObject scr, String resource, ScriptArg ... args) {
        try {
            scr.setSelectedIndex(this.m_ticketlines.getSelectedIndex());
            return scr.evalScript(this.dlSystem.getResourceAsXML(resource), args);
        }
        catch (ScriptException e) {
            MessageInf msg = new MessageInf(-33554432, AppLocal.getIntString("message.cannotexecute"), e);
            msg.show(this);
            return msg;
        }
    }

    public void evalScriptAndRefresh(String resource, ScriptArg ... args) {
        if (resource == null) {
            MessageInf msg = new MessageInf(-33554432, AppLocal.getIntString("message.cannotexecute"));
            msg.show(this);
        } else {
            ScriptObject scr = new ScriptObject(this.m_oTicket, this.m_oTicketExt);
            scr.setSelectedIndex(this.m_ticketlines.getSelectedIndex());
            this.evalScript(scr, resource, args);
            this.refreshTicket();
            this.setSelectedIndex(scr.getSelectedIndex());
            Timer timer = new Timer(200, new ActionListener(){

                @Override
                public void actionPerformed(ActionEvent evt) {
                    JPanelTicket.this.m_jKeyFactory.setEnabled(true);
                    JPanelTicket.this.m_jKeyFactory.requestFocusInWindow();
                }
            });
            timer.setRepeats(false);
            timer.start();
        }
    }

    private Object executeEventAndRefresh(String eventkey, ScriptArg ... args) {
        String resource = this.m_jbtnconfig.getEvent(eventkey);
        if (resource == null) {
            return null;
        }
        ScriptObject scr = new ScriptObject(this.m_oTicket, this.m_oTicketExt);
        scr.setSelectedIndex(this.m_ticketlines.getSelectedIndex());
        Object result = this.evalScript(scr, resource, args);
        this.refreshTicket();
        this.setSelectedIndex(scr.getSelectedIndex());
        Timer timer = new Timer(200, new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPanelTicket.this.m_jKeyFactory.setEnabled(true);
                JPanelTicket.this.m_jKeyFactory.requestFocusInWindow();
            }
        });
        timer.setRepeats(false);
        timer.start();
        return result;
    }

    private Object executeEvent(TicketInfo ticket, Object ticketext, String eventkey, ScriptArg ... args) {
        String resource = this.m_jbtnconfig.getEvent(eventkey);
        if (resource == null) {
            return null;
        }
        ScriptObject scr = new ScriptObject(ticket, ticketext);
        return this.evalScript(scr, resource, args);
    }

    public String getResourceAsXML(String sresourcename) {
        return this.dlSystem.getResourceAsXML(sresourcename);
    }

    public BufferedImage getResourceAsImage(String sresourcename) {
        return this.dlSystem.getResourceAsImage(sresourcename);
    }

    private void setSelectedIndex(int i) {
        if (i >= 0 && i < this.m_oTicket.getLinesCount()) {
            this.m_ticketlines.setSelectedIndex(i);
        } else if (this.m_oTicket.getLinesCount() > 0) {
            this.m_ticketlines.setSelectedIndex(this.m_oTicket.getLinesCount() - 1);
        }
    }

    private String setTempjPrice(String jPrice) {
        jPrice = jPrice.replace(".", "");
        long tempL = Long.parseLong(jPrice);
        jPrice = Long.toString(tempL);
        while (jPrice.length() < 3) {
            jPrice = "0" + jPrice;
        }
        return jPrice.length() <= 2 ? jPrice : new StringBuffer(jPrice).insert(jPrice.length() - 2, ".").toString();
    }

    public void checkStock() {
        int i = this.m_ticketlines.getSelectedIndex();
        if (i >= 0) {
            try {
                TicketLineInfo line = this.m_oTicket.getLine(i);
                String pId = line.getProductID();
                ProductStock checkProduct = this.dlSales.getProductStockState(pId);
                if (checkProduct != null) {
                    if (checkProduct.getUnits() <= 0.0) {
                        this.jCheckStock.setForeground(Color.magenta);
                    } else {
                        this.jCheckStock.setForeground(Color.darkGray);
                    }
                    double dUnits = checkProduct.getUnits();
                    int iUnits = (int)dUnits;
                    this.jCheckStock.setText(Integer.toString(iUnits));
                } else {
                    this.jCheckStock.setText(null);
                }
            }
            catch (BasicException ex) {
                Logger.getLogger(JPanelTicket.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void checkCustomer() {
        if (this.m_oTicket.getCustomer().isVIP()) {
            String vip = this.m_oTicket.getCustomer().isVIP() ? AppLocal.getIntString("message.vipyes") : AppLocal.getIntString("message.vipno");
            String discount = this.m_oTicket.getCustomer().getDiscount() > 0.0 ? AppLocal.getIntString("message.discyes") + this.m_oTicket.getCustomer().getDiscount() + "%" : AppLocal.getIntString("message.discno");
            String content = "<html><b>" + AppLocal.getIntString("label.vip") + " : </b>" + vip + "<br><b>" + AppLocal.getIntString("label.discount") + " : </b>" + discount + "<br>";
            JFrame frame = new JFrame();
            JOptionPane.showMessageDialog(frame, content, "Info", 2);
        }
    }

    private void initComponents() {
        this.m_jPanContainer = new ScrollablePanel();
        this.m_jOptions = new JPanel();
        this.m_jButtons = new JPanel();
        this.btnSplit = new JButton();
        this.btnReprint1 = new JButton();
        this.m_jPanelScripts = new JPanel();
        this.m_jButtonsExt = new JPanel();
        this.jPanel1 = new JPanel();
        this.m_jbtnScale = new JButton();
        this.jbtnMooring = new JButton();
        this.j_btnRemotePrt = new JButton();
        this.m_jPanelBag = new JPanel();
        this.m_jPanTicket = new JPanel();
        this.jPanel5 = new JPanel();
        this.jPanel2 = new JPanel();
        this.m_jDelete = new JButton();
        this.m_jList = new JButton();
        this.m_jEditLine = new JButton();
        this.jEditAttributes = new JButton();
        this.jCheckStock = new JButton();
        this.m_jPanelCentral = new JPanel();
        this.jPanel4 = new JPanel();
        this.filler2 = new Box.Filler(new Dimension(5, 0), new Dimension(5, 0), new Dimension(5, Short.MAX_VALUE));
        this.m_jTicketId = new JLabel();
        this.m_jPanTotals = new JPanel();
        this.m_jLblTotalEuros3 = new JLabel();
        this.m_jLblTotalEuros2 = new JLabel();
        this.m_jLblTotalEuros1 = new JLabel();
        this.m_jSubtotalEuros = new JLabel();
        this.m_jTaxesEuros = new JLabel();
        this.m_jTotalEuros = new JLabel();
        this.m_jContEntries = new JPanel();
        this.m_jPanEntries = new JPanel();
        this.m_jNumberKeys = new JNumberKeys();
        this.btnCreateCustomer = new JButton();
        this.cbCustomerSearch = new JComboBox();
        this.jPanel3 = new JPanel();
        this.jPanel9 = new JPanel();
        this.m_jaddtax = new JCheckBox();
        this.m_jPrice = new JLabel();
        this.m_jPor = new JLabel();
        this.m_jEnter = new JButton();
        this.m_jTax = new JComboBox();
        this.m_jKeyFactory = new JTextField();
        this.catcontainer = new JPanel();
        this.setBackground(new Color(255, 204, 153));
        this.setLayout(new CardLayout());
        this.m_jPanContainer.setLayout(new BorderLayout());
        this.m_jOptions.setLayout(new BorderLayout());
        this.m_jOptions.setBackground(Color.WHITE);
        JPanel m_jOptionsLeft = new JPanel(new FlowLayout(0, 2, 2));
        m_jOptionsLeft.setBackground(Color.WHITE);
        m_jOptionsLeft.setOpaque(true);
        JPanel m_jOptionsRight = new JPanel(new FlowLayout(2, 2, 2));
        m_jOptionsRight.setBackground(Color.WHITE);
        m_jOptionsRight.setOpaque(true);
        Dimension dimBtn = new Dimension(55, 55);
        this.m_jPanelBag.setBackground(Color.WHITE);
        // Aumentar contenedor padre para evitar cortes en botones con texto
        this.m_jPanelBag.setPreferredSize(new Dimension(220, 95));
        m_jOptionsLeft.add(this.m_jPanelBag);
        Dimension dimSearch = new Dimension(145, 55);
        Border roundedBorder = BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true);
        JPanel pnlProductSearch = new JPanel(new BorderLayout());
        pnlProductSearch.setBackground(Color.WHITE);
        pnlProductSearch.setBorder(roundedBorder);
        pnlProductSearch.setPreferredSize(dimSearch);
        pnlProductSearch.setMaximumSize(dimSearch);
        JLabel lblSearchIconProd = new JLabel(safeIcon("/com/openbravo/images/search24.png"));
        lblSearchIconProd.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
        pnlProductSearch.add((Component)lblSearchIconProd, "West");
        this.btnCreateProduct = new JButton();
        this.btnCreateProduct.setIcon(SOLTECTheme.getScaledIcon(this.getClass().getResource("/com/openbravo/images/edit_add.png"), 32, 32));
        if (this.btnCreateProduct.getIcon() == null) {
            this.btnCreateProduct.setText("+");
        }
        this.btnCreateProduct.setToolTipText(AppLocal.getIntString("label.prodcreate"));
        this.btnCreateProduct.setBackground(Color.WHITE);
        this.btnCreateProduct.setPreferredSize(dimBtn);
        this.btnCreateProduct.setMinimumSize(dimBtn);
        this.btnCreateProduct.setMaximumSize(dimBtn);
        SOLTECTheme.applyIconButtonStyle(this.btnCreateProduct);
        this.btnCreateProduct.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPanelTicket.this.btnCreateProductActionPerformed(evt);
            }
        });
        this.cbProductSearch = new JComboBox();
        this.cbProductSearch.setEditable(true);
        this.cbProductSearch.setBackground(Color.WHITE);
        this.cbProductSearch.setBorder(BorderFactory.createEmptyBorder());
        this.cbProductSearch.setFont(new Font("Arial", 1, 14));
        final JTextField txtProdSearch = (JTextField)this.cbProductSearch.getEditor().getEditorComponent();
        txtProdSearch.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
        String prodPlaceholder = "Buscar Producto...";
        if (txtProdSearch.getText().isEmpty()) {
            txtProdSearch.setText("Buscar Producto...");
            txtProdSearch.setForeground(Color.GRAY);
        }
        txtProdSearch.addFocusListener(new FocusListener(){

            @Override
            public void focusGained(FocusEvent e) {
                if (txtProdSearch.getText().equals("Buscar Producto...")) {
                    txtProdSearch.setText("");
                    txtProdSearch.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtProdSearch.getText().isEmpty()) {
                    txtProdSearch.setText("Buscar Producto...");
                    txtProdSearch.setForeground(Color.GRAY);
                }
            }
        });
        txtProdSearch.addKeyListener(new KeyAdapter(){

            @Override
            public void keyReleased(KeyEvent evt) {
                JPanelTicket.this.cbProductSearchKeyReleased(evt);
            }
        });
        this.productSearchActionListener = new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                if ("comboBoxChanged".equals(evt.getActionCommand())) {
                    JPanelTicket.this.cbProductSearchActionPerformed(evt);
                }
            }
        };
        this.cbProductSearch.addActionListener(this.productSearchActionListener);
        pnlProductSearch.add(this.cbProductSearch, "Center");
        pnlProductSearch.add(this.cbProductSearch, "Center");
        m_jOptionsLeft.add(pnlProductSearch);
        m_jOptionsLeft.add(this.btnCreateProduct);
        
        // BOTÓN AGREGAR CLIENTE (Gigante + Texto)
        Dimension dimGiantBtn = new Dimension(75, 85);
        this.btnCreateCustomer.setFont(new Font("Arial", Font.BOLD, 10));
        this.btnCreateCustomer.setText("Cliente");
        this.btnCreateCustomer.setIcon(SOLTECTheme.getScaledIcon(this.getClass().getResource("/com/openbravo/images/agregarcliente.png"), 55, 55));
        this.btnCreateCustomer.setToolTipText(AppLocal.getIntString("label.customercreate"));
        this.btnCreateCustomer.setBackground(Color.WHITE);
        this.btnCreateCustomer.setVerticalTextPosition(JButton.BOTTOM);
        this.btnCreateCustomer.setHorizontalTextPosition(JButton.CENTER);
        this.btnCreateCustomer.setPreferredSize(dimGiantBtn);
        this.btnCreateCustomer.setMinimumSize(dimGiantBtn);
        this.btnCreateCustomer.setMaximumSize(dimGiantBtn);
        SOLTECTheme.applyIconButtonStyle(this.btnCreateCustomer);
        this.btnCreateCustomer.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPanelTicket.this.btnCreateCustomerActionPerformed(evt);
            }
        });
        JPanel pnlCustomerSearch = new JPanel(new BorderLayout());
        pnlCustomerSearch.setBackground(Color.WHITE);
        pnlCustomerSearch.setBorder(roundedBorder);
        pnlCustomerSearch.setPreferredSize(dimSearch);
        pnlCustomerSearch.setMaximumSize(dimSearch);
        JLabel lblSearchIconCust = new JLabel(safeIcon("/com/openbravo/images/search24.png"));
        lblSearchIconCust.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
        pnlCustomerSearch.add((Component)lblSearchIconCust, "West");
        this.cbCustomerSearch.setEditable(true);
        this.cbCustomerSearch.setBackground(Color.WHITE);
        this.cbCustomerSearch.setBorder(BorderFactory.createEmptyBorder());
        this.cbCustomerSearch.setFont(new Font("Arial", 1, 14));
        final JTextField txtSearch = (JTextField)this.cbCustomerSearch.getEditor().getEditorComponent();
        txtSearch.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
        String custPlaceholder = "Buscar Cliente...";
        if (txtSearch.getText().isEmpty()) {
            txtSearch.setText("Buscar Cliente...");
            txtSearch.setForeground(Color.GRAY);
        }
        txtSearch.addFocusListener(new FocusListener(){

            @Override
            public void focusGained(FocusEvent e) {
                if (txtSearch.getText().equals("Buscar Cliente...")) {
                    txtSearch.setText("");
                    txtSearch.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtSearch.getText().isEmpty()) {
                    txtSearch.setText("Buscar Cliente...");
                    txtSearch.setForeground(Color.GRAY);
                }
            }
        });
        txtSearch.addKeyListener(new KeyAdapter(){

            @Override
            public void keyReleased(KeyEvent evt) {
                JPanelTicket.this.cbCustomerSearchKeyReleased(evt);
            }
        });
        this.searchActionListener = new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                if ("comboBoxChanged".equals(evt.getActionCommand())) {
                    JPanelTicket.this.cbCustomerSearchActionPerformed(evt);
                }
            }
        };
        this.cbCustomerSearch.addActionListener(this.searchActionListener);
        pnlCustomerSearch.add(this.cbCustomerSearch, "Center");
        m_jOptionsLeft.add(pnlCustomerSearch);
        m_jOptionsLeft.add(this.btnCreateCustomer);
        
        // Ajuste masivo: Iconos GIGANTES (55px) + TEXTO debajo
        // Dimensiones ajustadas a 75x85 para resoluciones 1024x768
        Dimension dimTopBtn = new Dimension(75, 85); 
        
        // Botón Reimprimir Último Ticket
        this.btnReprint1.setFont(new Font("Arial", Font.BOLD, 10)); // Fuente pequeña para que quepa
        this.btnReprint1.setText("Reimprimir");
        this.btnReprint1.setIcon(SOLTECTheme.getScaledIcon(this.getClass().getResource("/com/openbravo/images/printer.png"), 55, 55));
        this.btnReprint1.setBackground(Color.WHITE);
        this.btnReprint1.setToolTipText(AppLocal.getIntString("tooltip.reprintLastTicket"));
        SOLTECTheme.applyIconButtonStyle(this.btnReprint1);
        this.btnReprint1.setVerticalTextPosition(JButton.BOTTOM);
        this.btnReprint1.setHorizontalTextPosition(JButton.CENTER);
        this.btnReprint1.setPreferredSize(dimTopBtn);
        this.btnReprint1.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent evt) {
                JPanelTicket.this.btnReprint1ActionPerformed(evt);
            }
        });
        m_jOptionsLeft.add(this.btnReprint1);
        
        // Botón Dividir Cuenta
        this.btnSplit.setFont(new Font("Arial", Font.BOLD, 10));
        this.btnSplit.setText("Dividir");
        this.btnSplit.setIcon(SOLTECTheme.getScaledIcon(this.getClass().getResource("/com/openbravo/images/splitticket.png"), 55, 55));
        this.btnSplit.setBackground(Color.WHITE);
        this.btnSplit.setToolTipText(AppLocal.getIntString("tooltip.salesplit"));
        this.btnSplit.setEnabled(false);
        this.btnSplit.setFocusPainted(false);
        this.btnSplit.setFocusable(false);
        this.btnSplit.setMargin(new Insets(2, 2, 2, 2));
        this.btnSplit.setPreferredSize(dimTopBtn);
        this.btnSplit.setMinimumSize(dimTopBtn);
        this.btnSplit.setMaximumSize(dimTopBtn);
        this.btnSplit.setRequestFocusEnabled(false);
        SOLTECTheme.applyIconButtonStyle(this.btnSplit);
        this.btnSplit.setVerticalTextPosition(JButton.BOTTOM);
        this.btnSplit.setHorizontalTextPosition(JButton.CENTER);
        this.btnSplit.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent evt) {
                JPanelTicket.this.btnSplitActionPerformed(evt);
            }
        });
        m_jOptionsLeft.add(this.btnSplit);
        
        // Botón Impresión Remota / Cocina
        this.j_btnRemotePrt.setIcon(SOLTECTheme.getScaledIcon(this.getClass().getResource("/com/openbravo/images/icon_kitchen_print_modern.png"), 28, 28));
        this.j_btnRemotePrt.setToolTipText(AppLocal.getIntString("button.toremote"));
        SOLTECTheme.applyBorderlessButtonStyle(this.j_btnRemotePrt);

        this.j_btnRemotePrt.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent evt) {
                JPanelTicket.this.j_btnRemotePrtActionPerformed(evt);
            }
        });
        // No se agrega a m_jOptionsLeft, se agregará a pnlControls más abajo.

        this.lblScaleDisplay = new JLabel();
        Dimension dimScale = new Dimension(100, 55);
        this.lblScaleDisplay.setPreferredSize(dimScale);
        this.lblScaleDisplay.setMinimumSize(dimScale);
        this.lblScaleDisplay.setMaximumSize(dimScale);
        this.lblScaleDisplay.setOpaque(true);
        this.lblScaleDisplay.setBackground(Color.DARK_GRAY);
        this.lblScaleDisplay.setForeground(Color.GREEN);
        this.lblScaleDisplay.setFont(new Font("Arial", 1, 18));
        this.lblScaleDisplay.setHorizontalAlignment(0);
        this.lblScaleDisplay.setText("0.000 kg");
        this.lblScaleDisplay.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        m_jOptionsRight.add(this.lblScaleDisplay);
        this.scaleTimer = new Timer(500, new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                block5: {
                    if (JPanelTicket.this.m_App.getDeviceScale().existsScale()) {
                        try {
                            Double value = JPanelTicket.this.m_App.getDeviceScale().readWeight();
                            if (value != null) {
                                JPanelTicket.this.lblScaleDisplay.setText(Formats.DOUBLE.formatValue(value) + " kg");
                                break block5;
                            }
                            JPanelTicket.this.lblScaleDisplay.setText("---");
                        }
                        catch (ScaleException e) {
                            JPanelTicket.this.lblScaleDisplay.setText("Err");
                        }
                    } else {
                        JPanelTicket.this.lblScaleDisplay.setText("No Scale");
                    }
                }
            }
        });
        this.m_jOptions.add((Component)m_jOptionsLeft, "Center");
        this.m_jOptions.add((Component)m_jOptionsRight, "East");
        this.m_jPanContainer.add((Component)this.m_jOptions, "North");
        this.m_jPanTicket.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        this.m_jPanTicket.setLayout(new BorderLayout());
        this.jPanel5.setLayout(new BorderLayout());
        // JScrollPane para permitir scroll de los botones laterales
        JScrollPane scrollPanel2 = new JScrollPane(this.jPanel2);
        scrollPanel2.setBorder(null);
        scrollPanel2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPanel2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPanel2.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        scrollPanel2.setPreferredSize(new Dimension(55, 0)); // Ancho compacto
        this.m_jPanTicket.add(scrollPanel2, "Before");
        
        this.jPanel2.setLayout(new GridLayout(0, 1, 0, 5));
        this.jPanel2.setBorder(BorderFactory.createEmptyBorder(5, 2, 5, 2));
        
        this.m_jDelete.setIcon(SOLTECTheme.getScaledIcon(this.getClass().getResource("/com/openbravo/images/deletelineticket.png"), 32, 32));
        this.m_jDelete.setToolTipText(AppLocal.getIntString("label.deleteline"));
        SOLTECTheme.applyBorderlessButtonStyle(this.m_jDelete);
        this.m_jDelete.setPreferredSize(new Dimension(45, 45));
        this.m_jDelete.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPanelTicket.this.m_jDeleteActionPerformed(evt);
            }
        });
        this.jPanel2.add(this.m_jDelete);
        
        this.m_jList.setIcon(SOLTECTheme.getScaledIcon(this.getClass().getResource("/com/openbravo/images/searchnew.png"), 32, 32));
        this.m_jList.setToolTipText(AppLocal.getIntString("label.search"));
        SOLTECTheme.applyBorderlessButtonStyle(this.m_jList);
        this.m_jList.setPreferredSize(new Dimension(45, 45));
        this.m_jList.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPanelTicket.this.m_jListActionPerformed(evt);
            }
        });
        this.jPanel2.add(this.m_jList);
        
        this.m_jEditLine.setIcon(SOLTECTheme.getScaledIcon(this.getClass().getResource("/com/openbravo/images/editline.png"), 32, 32));
        this.m_jEditLine.setToolTipText(AppLocal.getIntString("label.editline"));
        SOLTECTheme.applyBorderlessButtonStyle(this.m_jEditLine);
        this.m_jEditLine.setPreferredSize(new Dimension(45, 45));
        this.m_jEditLine.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPanelTicket.this.m_jEditLineActionPerformed(evt);
            }
        });
        this.jPanel2.add(this.m_jEditLine);
        
        this.jEditAttributes.setIcon(SOLTECTheme.getScaledIcon(this.getClass().getResource("/com/openbravo/images/atributos.png"), 32, 32));
        this.jEditAttributes.setToolTipText(AppLocal.getIntString("tooltip.saleattributes"));
        SOLTECTheme.applyBorderlessButtonStyle(this.jEditAttributes);
        this.jEditAttributes.setPreferredSize(new Dimension(45, 45));
        this.jEditAttributes.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPanelTicket.this.jEditAttributesActionPerformed(evt);
            }
        });
        this.jPanel2.add(this.jEditAttributes);
        final JPopupMenu m_popMore = new JPopupMenu();
        final JButton m_btnMore = new JButton();
        m_btnMore.setIcon(SOLTECTheme.getScaledIcon(this.getClass().getResource("/com/openbravo/images/auxpanel.png"), 24, 24));
        m_btnMore.setText("Mas");
        m_btnMore.setToolTipText("Panel Auxiliar");
        SOLTECTheme.applyBorderlessButtonStyle(m_btnMore);
        m_btnMore.setPreferredSize(new Dimension(45, 45));
        m_btnMore.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                m_popMore.show(m_btnMore, m_btnMore.getWidth(), 0);
            }
        });
        this.jPanel2.add(m_btnMore);
        JButton btnExit = new JButton();
        btnExit.setIcon(SOLTECTheme.getScaledIcon(this.getClass().getResource("/com/openbravo/images/exitback.png"), 24, 24));
        btnExit.setText("Salir");
        btnExit.setToolTipText(AppLocal.getIntString("tooltip.quicklogoff"));
        SOLTECTheme.applyBorderlessButtonStyle(btnExit);
        btnExit.setPreferredSize(new Dimension(45, 45));
        btnExit.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPanelTicket.this.m_ticketsbag.deactivate();
                ((JRootApp)JPanelTicket.this.m_App).closeAppView();
            }
        });
        this.jPanel2.add(btnExit);
        Dimension dimBtnSide = new Dimension(90, 70);
        int iconSizeSide = 48;
        this.m_jbtnScale.setIcon(SOLTECTheme.getScaledIcon(this.getClass().getResource("/com/openbravo/images/balance.png"), 40, 40));
        this.m_jbtnScale.setText(AppLocal.getIntString("button.scale"));
        this.m_jbtnScale.setToolTipText(AppLocal.getIntString("button.scale"));
        this.m_jbtnScale.setBackground(Color.WHITE);
        this.m_jbtnScale.setPreferredSize(new Dimension(90, 48));
        SOLTECTheme.applyBorderlessButtonStyle(this.m_jbtnScale);
        this.m_jbtnScale.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                int i = JPanelTicket.this.m_ticketlines.getSelectedIndex();
                if (i >= 0) {
                    TicketLineInfo oLine = JPanelTicket.this.m_oTicket.getLine(i);
                    try {
                        Double value = JPanelTicket.this.m_App.getDeviceScale().readWeight();
                        if (value != null) {
                            oLine.setMultiply(value);
                            JPanelTicket.this.m_ticketlines.setTicketLine(i, oLine);
                        }
                    }
                    catch (ScaleException e) {
                        JMessageDialog.showMessage(JPanelTicket.this, new MessageInf(-33554432, AppLocal.getIntString("message.scaleexception"), e));
                    }
                } else {
                    Toolkit.getDefaultToolkit().beep();
                }
            }
        });
        this.jPanel2.add(this.m_jbtnScale);
        // Eliminado código antiguo de jPanel2 para usar JScrollPane definido arriba
        // this.m_jPanTicket.add((Component)this.jPanel2, "Before");
        // this.jPanel2.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        // this.jPanel2.setPreferredSize(new Dimension(95, 250));
        // this.jPanel2.setLayout(new GridLayout(0, 1, 0, 5));
        // m_jPanTicket ya tiene su layout y borde configurados arriba (línea ~2052)
        // jPanel5 ya tiene su layout configurado arriba (línea ~2054)
        this.jCheckStock.setFont(new Font("Arial", 1, 14));
        this.jCheckStock.setForeground(new Color(76, 197, 237));
        this.jCheckStock.setToolTipText(AppLocal.getIntString("button.stock"));
        this.jCheckStock.setFocusPainted(false);
        this.jCheckStock.setFocusable(false);
        this.jCheckStock.setHorizontalTextPosition(4);
        this.jCheckStock.setBackground(Color.WHITE);
        this.jCheckStock.setMargin(new Insets(4, 8, 4, 8));
        this.jCheckStock.setMaximumSize(new Dimension(45, 40));
        this.jCheckStock.setMinimumSize(new Dimension(45, 40));
        this.jCheckStock.setPreferredSize(new Dimension(45, 40));
        this.jCheckStock.setRequestFocusEnabled(false);
        this.jCheckStock.setVerticalTextPosition(1);
        this.jCheckStock.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent evt) {
                JPanelTicket.this.jCheckStockMouseClicked(evt);
            }
        });
        this.jCheckStock.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                System.out.println("DEBUG: jCheckStock Clicked");
                JPanelTicket.this.jCheckStockActionPerformed(evt);
            }
        });
        m_popMore.add(this.jCheckStock);
        JMenuItem menuDiscount = new JMenuItem("Descuento Total");
        menuDiscount.setIcon(safeIcon("/com/openbravo/images/img.discount.png"));
        menuDiscount.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPanelTicket.this.evalScriptAndRefresh("script.totaldiscount", new ScriptArg[0]);
            }
        });
        m_popMore.add(menuDiscount);
        JMenuItem menuDrawer = new JMenuItem("Abrir Caj\u00c3\u00b3n");
        menuDrawer.setIcon(SOLTECTheme.getScaledIcon(this.getClass().getResource("/com/openbravo/images/cashdr.png"), 32, 32));
        menuDrawer.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPanelTicket.this.printTicket("Printer.OpenDrawer");
            }
        });
        m_popMore.add(menuDrawer);
        
        // OPCIÓN PRE-FACTURA (Panel Auxiliar)
        JMenuItem menuPreBill = new JMenuItem("Pre-Factura / Cuenta");
        menuPreBill.setIcon(SOLTECTheme.getScaledIcon(this.getClass().getResource("/com/openbravo/images/ticket_print.png"), 32, 32));
        menuPreBill.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent evt) {
                JPanelTicket.this.printTicket("Printer.TicketPreview");
            }
        });
        m_popMore.add(menuPreBill);
        // jPanel2 ya está dentro de scrollPanel2 (arriba ~línea 2056)
        // No agregar de nuevo a jPanel5 para evitar conflicto
        this.m_jPanelCentral.setFont(new Font("Arial", 0, 14));
        this.m_jPanelCentral.setPreferredSize(new Dimension(450, 240));
        this.m_jPanelCentral.setLayout(new BorderLayout());
        this.jPanel4.setLayout(new BorderLayout());
        this.jPanel4.add((Component)this.filler2, "Before");
        this.m_jTicketId.setFont(new Font("Arial", 1, 12));
        this.m_jTicketId.setHorizontalAlignment(2);
        this.m_jTicketId.setVerticalAlignment(1);
        this.m_jTicketId.setOpaque(true);
        this.m_jTicketId.setPreferredSize(new Dimension(300, 40));
        this.m_jTicketId.setRequestFocusEnabled(false);
        this.m_jTicketId.setVerticalTextPosition(1);
        this.jPanel4.add((Component)this.m_jTicketId, "Center");
        this.m_jPanTotals.setPreferredSize(new Dimension(375, 60));
        this.m_jPanTotals.setLayout(new GridLayout(2, 3, 4, 0));
        this.m_jLblTotalEuros3.setFont(new Font("Arial", 1, 14));
        this.m_jLblTotalEuros3.setHorizontalAlignment(0);
        this.m_jLblTotalEuros3.setLabelFor(this.m_jSubtotalEuros);
        this.m_jLblTotalEuros3.setText(AppLocal.getIntString("label.subtotalcash"));
        this.m_jPanTotals.add(this.m_jLblTotalEuros3);
        this.m_jLblTotalEuros2.setFont(new Font("Arial", 1, 14));
        this.m_jLblTotalEuros2.setHorizontalAlignment(0);
        this.m_jLblTotalEuros2.setLabelFor(this.m_jSubtotalEuros);
        this.m_jLblTotalEuros2.setText(AppLocal.getIntString("label.taxcash"));
        this.m_jPanTotals.add(this.m_jLblTotalEuros2);
        this.m_jLblTotalEuros1.setFont(new Font("Arial", 1, 14));
        this.m_jLblTotalEuros1.setHorizontalAlignment(0);
        this.m_jLblTotalEuros1.setLabelFor(this.m_jTotalEuros);
        this.m_jLblTotalEuros1.setText(AppLocal.getIntString("label.totalcash"));
        this.m_jPanTotals.add(this.m_jLblTotalEuros1);
        this.m_jSubtotalEuros.setBackground(this.m_jEditLine.getBackground());
        this.m_jSubtotalEuros.setFont(new Font("Arial", 0, 18));
        this.m_jSubtotalEuros.setForeground(this.m_jEditLine.getForeground());
        this.m_jSubtotalEuros.setHorizontalAlignment(0);
        this.m_jSubtotalEuros.setLabelFor(this.m_jSubtotalEuros);
        this.m_jSubtotalEuros.setToolTipText(AppLocal.getIntString("tooltip.salesubtotal"));
        this.m_jSubtotalEuros.setBorder(new LineBorder(new Color(153, 153, 153), 1, true));
        this.m_jSubtotalEuros.setMaximumSize(new Dimension(125, 25));
        this.m_jSubtotalEuros.setMinimumSize(new Dimension(80, 25));
        this.m_jSubtotalEuros.setPreferredSize(new Dimension(80, 25));
        this.m_jSubtotalEuros.setRequestFocusEnabled(false);
        this.m_jPanTotals.add(this.m_jSubtotalEuros);
        this.m_jTaxesEuros.setBackground(this.m_jEditLine.getBackground());
        this.m_jTaxesEuros.setFont(new Font("Arial", 0, 18));
        this.m_jTaxesEuros.setForeground(this.m_jEditLine.getForeground());
        this.m_jTaxesEuros.setHorizontalAlignment(0);
        this.m_jTaxesEuros.setLabelFor(this.m_jTaxesEuros);
        this.m_jTaxesEuros.setToolTipText(AppLocal.getIntString("tooltip.saletax"));
        this.m_jTaxesEuros.setBorder(new LineBorder(new Color(153, 153, 153), 1, true));
        this.m_jTaxesEuros.setMaximumSize(new Dimension(125, 25));
        this.m_jTaxesEuros.setMinimumSize(new Dimension(80, 25));
        this.m_jTaxesEuros.setPreferredSize(new Dimension(80, 25));
        this.m_jTaxesEuros.setRequestFocusEnabled(false);
        this.m_jPanTotals.add(this.m_jTaxesEuros);
        this.m_jTotalEuros.setBackground(this.m_jEditLine.getBackground());
        this.m_jTotalEuros.setFont(new Font("Arial", 1, 18));
        this.m_jTotalEuros.setForeground(this.m_jEditLine.getForeground());
        this.m_jTotalEuros.setHorizontalAlignment(0);
        this.m_jTotalEuros.setLabelFor(this.m_jTotalEuros);
        this.m_jTotalEuros.setToolTipText(AppLocal.getIntString("tooltip.saletotal"));
        this.m_jTotalEuros.setBorder(new LineBorder(new Color(153, 153, 153), 1, true));
        this.m_jTotalEuros.setMaximumSize(new Dimension(125, 25));
        this.m_jTotalEuros.setMinimumSize(new Dimension(80, 25));
        this.m_jTotalEuros.setPreferredSize(new Dimension(100, 25));
        this.m_jTotalEuros.setRequestFocusEnabled(false);
        this.m_jPanTotals.add(this.m_jTotalEuros);
        this.jPanel4.add((Component)this.m_jPanTotals, "After");
        this.m_jPanelCentral.add((Component)this.jPanel4, "South");
        this.m_jPanTicket.add((Component)this.m_jPanelCentral, "Center");
        this.m_jPanContainer.add((Component)this.m_jPanTicket, "Center");
        this.m_jContEntries.setFont(new Font("Arial", 0, 12));
        this.m_jContEntries.setMinimumSize(new Dimension(220, 300));
        this.m_jContEntries.setLayout(new BorderLayout());
        this.m_jPanEntries.setPreferredSize(new Dimension(220, 300));
        this.m_jPanEntries.setLayout(new BoxLayout(this.m_jPanEntries, 1));
        this.m_jNumberKeys.setMinimumSize(new Dimension(220, 150));
        this.m_jNumberKeys.setPreferredSize(new Dimension(220, 180));
        this.m_jPanEntries.removeAll();
        this.m_jPanEntries.add(this.jPanel9);
        this.m_jPanEntries.add(Box.createRigidArea(new Dimension(0, 5)));
        this.m_jPanEntries.add(this.m_jNumberKeys);
        this.m_jNumberKeys.addJNumberEventListener(new JNumberEventListener(){

            @Override
            public void keyPerformed(JNumberEvent evt) {
                JPanelTicket.this.m_jNumberKeysKeyPerformed(evt);
            }
        });
        this.jPanel9.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        this.jPanel9.setBackground(Color.WHITE);
        this.m_jaddtax.setBorder(null);
        this.m_jaddtax.setToolTipText(AppLocal.getIntString("tooltip.switchtax"));
        this.m_jaddtax.setFont(new Font("Arial", 0, 14));
        this.m_jaddtax.setPreferredSize(new Dimension(60, 30));
        this.m_jaddtax.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPanelTicket.this.m_jaddtaxActionPerformed(evt);
            }
        });
        this.m_jPrice.setFont(new Font("Arial", 1, 24));
        this.m_jPrice.setForeground(new Color(0, 51, 102));
        this.m_jPrice.setHorizontalAlignment(4);
        this.m_jPrice.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(0, 51, 102), 2), BorderFactory.createEmptyBorder(5, 8, 5, 8)));
        this.m_jPrice.setOpaque(true);
        this.m_jPrice.setBackground(Color.WHITE);
        this.m_jPrice.setPreferredSize(new Dimension(140, 45));
        this.m_jPrice.setRequestFocusEnabled(false);
        this.m_jPor.setFont(new Font("Arial", 0, 12));
        this.m_jPor.setHorizontalAlignment(4);
        this.m_jPor.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(UIManager.getDefaults().getColor("Button.darkShadow")), BorderFactory.createEmptyBorder(1, 4, 1, 4)));
        this.m_jPor.setOpaque(true);
        this.m_jPor.setPreferredSize(new Dimension(22, 25));
        this.m_jPor.setRequestFocusEnabled(false);
        this.m_jEnter.setIcon(SOLTECTheme.getScaledIcon(this.getClass().getResource("/com/openbravo/images/barcode.png"), 28, 28));
        this.m_jEnter.setToolTipText(AppLocal.getIntString("tooltip.salebarcode"));
        this.m_jEnter.setFocusPainted(false);
        this.m_jEnter.setFocusable(false);
        this.m_jEnter.setPreferredSize(new Dimension(80, 45));
        this.m_jEnter.setMargin(new Insets(0, 0, 0, 0));
        this.m_jEnter.setRequestFocusEnabled(false);
        this.m_jEnter.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPanelTicket.this.m_jEnterActionPerformed(evt);
            }
        });
        this.m_jTax.setFont(new Font("Arial", 0, 14));
        this.m_jTax.setToolTipText(AppLocal.getIntString("tooltip.salestaxswitch"));
        this.m_jTax.setFocusable(false);
        this.m_jTax.setPreferredSize(new Dimension(28, 25));
        this.m_jTax.setRequestFocusEnabled(false);
        this.m_jTax.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPanelTicket.this.m_jTaxActionPerformed(evt);
            }
        });
        this.m_jKeyFactory.setEditable(true);
        this.m_jKeyFactory.setFont(new Font("Arial", 0, 11));
        this.m_jKeyFactory.setForeground(UIManager.getDefaults().getColor("Panel.background"));
        this.m_jKeyFactory.setAutoscrolls(false);
        this.m_jKeyFactory.setBorder(null);
        this.m_jKeyFactory.setCaretColor(UIManager.getDefaults().getColor("Panel.background"));
        this.m_jKeyFactory.setMinimumSize(new Dimension(0, 0));
        this.m_jKeyFactory.setPreferredSize(new Dimension(1, 1));
        this.m_jKeyFactory.setRequestFocusEnabled(true);
        this.m_jKeyFactory.setVerifyInputWhenFocusTarget(false);
        this.m_jKeyFactory.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPanelTicket.this.m_jKeyFactoryActionPerformed(evt);
            }
        });
        this.m_jKeyFactory.addKeyListener(new KeyAdapter(){

            @Override
            public void keyTyped(KeyEvent evt) {
                JPanelTicket.this.m_jKeyFactoryKeyTyped(evt);
            }
        });
        this.jPanel9.setLayout(new BorderLayout(0, 5));
        JPanel pnlDisplay = new JPanel(new BorderLayout(0, 0));
        pnlDisplay.setBackground(Color.WHITE);
        pnlDisplay.add((Component)this.m_jPrice, "Center");
        pnlDisplay.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent evt) {
                JPanelTicket.this.m_jKeyFactory.setEnabled(true);
                JPanelTicket.this.m_jKeyFactory.requestFocusInWindow();
            }
        });
        this.m_jPrice.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent evt) {
                JPanelTicket.this.m_jKeyFactory.setEnabled(true);
                JPanelTicket.this.m_jKeyFactory.requestFocusInWindow();
            }
        });
        JPanel pnlPor = new JPanel(new FlowLayout(1, 0, 0));
        pnlPor.setBackground(Color.WHITE);
        pnlPor.add(this.m_jPor);
        this.jPanel9.add((Component)pnlDisplay, "North");
        JPanel pnlControls = new JPanel(new FlowLayout(1, 4, 0));
        pnlControls.setBackground(new Color(240, 240, 240));
        pnlControls.setBorder(BorderFactory.createEmptyBorder(2, 0, 2, 0));
        Border borderCtrl = BorderFactory.createLineBorder(new Color(160, 160, 160), 1);
        
        // Asignamos el estilo y tamaño al boton remoto (Reemplaza m_jaddtax)
        this.j_btnRemotePrt.setBorder(borderCtrl);
        this.j_btnRemotePrt.setPreferredSize(new Dimension(66, 40));
        this.j_btnRemotePrt.setOpaque(true);
        this.j_btnRemotePrt.setBackground(Color.WHITE);
        
        this.m_jTax.setBorder(borderCtrl);
        this.m_jTax.setOpaque(true);
        this.m_jTax.setBackground(Color.WHITE);
        this.m_jTax.setPreferredSize(new Dimension(66, 40));
        this.m_jEnter.setBorder(borderCtrl);
        this.m_jEnter.setOpaque(true);
        this.m_jEnter.setBackground(Color.WHITE);
        this.m_jEnter.setPreferredSize(new Dimension(66, 40));
        
        pnlControls.add(this.j_btnRemotePrt);
        pnlControls.add(this.m_jTax);
        pnlControls.add(this.m_jEnter);
        this.jPanel9.add((Component)pnlControls, "Center");
        this.jPanel9.setPreferredSize(new Dimension(220, 100));
        this.jPanel9.setMinimumSize(new Dimension(220, 100));
        this.jPanel9.setMaximumSize(new Dimension(220, 100));
        this.jPanel9.add((Component)this.m_jKeyFactory, "South");
        this.m_jContEntries.add((Component)this.m_jPanEntries, "North");
        this.m_jPanContainer.add((Component)this.m_jContEntries, "After");
        this.catcontainer.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        this.catcontainer.setFont(new Font("Arial", 0, 14));
        this.catcontainer.setLayout(new BorderLayout());
        this.catcontainer.setPreferredSize(new Dimension(0, 195));
        this.m_jPanContainer.add((Component)this.catcontainer, "South");
        JScrollPane scrollContainer = new JScrollPane(this.m_jPanContainer);
        scrollContainer.setBorder(null);
        scrollContainer.setHorizontalScrollBarPolicy(31);
        scrollContainer.getVerticalScrollBar().setPreferredSize(new Dimension(35, 0));
        scrollContainer.getVerticalScrollBar().setUnitIncrement(30);
        this.add((Component)scrollContainer, "ticket");
    }

    private void m_jbtnScaleActionPerformed(ActionEvent evt) {
        this.stateTransition('\u00a7');
    }

    private void m_jEditLineActionPerformed(ActionEvent evt) {
        block9: {
            int i = this.m_ticketlines.getSelectedIndex();
            if (i < 0) {
                Toolkit.getDefaultToolkit().beep();
            } else {
                try {
                    TicketLineInfo oldLine = this.m_oTicket.getLine(i);
                    double oldPrice = oldLine.getPrice();
                    double oldQty = oldLine.getMultiply();
                    TicketLineInfo newline = JProductLineEdit.showMessage(this, this.m_App, this.m_oTicket.getLine(i));
                    if (newline == null) break block9;
                    this.paintTicketLine(i, newline);
                    try {
                        String user = this.m_App.getAppUserView().getUser().getName();
                        String ticketID = Integer.toString(this.m_oTicket.getTicketId());
                        if (this.m_oTicket.getTicketId() == 0) {
                            ticketID = "Void";
                        }
                        if (oldPrice != newline.getPrice()) {
                            this.dlSystem.execAuditEntry(new Object[]{UUID.randomUUID().toString(), new Date(), "EDIT_PRICE", user, newline.getProductName(), newline.getMultiply(), Formats.CURRENCY.formatValue(oldPrice), Formats.CURRENCY.formatValue(newline.getPrice()), "Manual Edit", ticketID});
                        }
                        if (oldQty != newline.getMultiply()) {
                            this.dlSystem.execAuditEntry(new Object[]{UUID.randomUUID().toString(), new Date(), "EDIT_QTY", user, newline.getProductName(), newline.getMultiply(), Double.toString(oldQty), Double.toString(newline.getMultiply()), "Manual Edit", ticketID});
                        }
                    }
                    catch (Exception exception) {}
                }
                catch (BasicException e) {
                    new MessageInf(e).show(this);
                }
            }
        }
    }

    private void m_jEnterActionPerformed(ActionEvent evt) {
        this.stateTransition('\n');
    }

    private void m_jNumberKeysKeyPerformed(JNumberEvent evt) {
        this.stateTransition(evt.getKey());
        this.j_btnRemotePrt.setEnabled(true);
        this.j_btnRemotePrt.revalidate();
    }

    private void m_jKeyFactoryKeyTyped(KeyEvent evt) {
        this.m_jKeyFactory.setText(null);
        this.stateTransition(evt.getKeyChar());
    }

    private void m_jDeleteActionPerformed(ActionEvent evt) {
        int i = this.m_ticketlines.getSelectedIndex();
        if (i < 0) {
            Toolkit.getDefaultToolkit().beep();
        } else {
            try {
                TicketLineInfo line;
                if (this.m_oTicket != null && i < this.m_oTicket.getLinesCount() && (line = this.m_oTicket.getLine(i)) != null) {
                    String user = this.m_App.getAppUserView().getUser().getName();
                    String ticketID = this.m_oTicket.getTicketId() == 0 ? "Current" : Integer.toString(this.m_oTicket.getTicketId());
                    this.dlSystem.execAuditEntry(new Object[]{UUID.randomUUID().toString(), new Date(), "DELETE_LINE", user, line.getProductName(), line.getMultiply(), Formats.CURRENCY.formatValue(line.getPrice()), "Deleted", "Manual Line Delete", ticketID});
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
            this.removeTicketLine(i);
            this.jCheckStock.setText("");
        }
    }

    private void m_jListActionPerformed(ActionEvent evt) {
        ProductInfoExt prod = JProductFinder.showMessage(this, this.dlSales);
        if (prod != null) {
            this.buttonTransition(prod);
        }
    }

    private void jEditAttributesActionPerformed(ActionEvent evt) {
        int i;
        if (this.listener != null) {
            this.listener.stop();
        }
        if ((i = this.m_ticketlines.getSelectedIndex()) < 0) {
            Toolkit.getDefaultToolkit().beep();
        } else {
            try {
                TicketLineInfo line = this.m_oTicket.getLine(i);
                JProductAttEdit2 attedit = JProductAttEdit2.getAttributesEditor(this, this.m_App.getSession());
                attedit.editAttributes(line.getProductAttSetId(), line.getProductAttSetInstId());
                attedit.setVisible(true);
                if (attedit.isOK()) {
                    line.setProductAttSetInstId(attedit.getAttributeSetInst());
                    line.setProductAttSetInstDesc(attedit.getAttributeSetInstDescription());
                    this.paintTicketLine(i, line);
                }
            }
            catch (BasicException ex) {
                JOptionPane.showMessageDialog(this, AppLocal.getIntString("message.cannotfindattributes"), AppLocal.getIntString("message.title"), 1);
            }
        }
        if (this.listener != null) {
            this.listener.restart();
        }
    }

    private void jbtnMooringActionPerformed(ActionEvent evt) {
        if (this.listener != null) {
            this.listener.stop();
        }
        JMooringDetails mooring = JMooringDetails.getMooringDetails(this, this.m_App.getSession());
        mooring.setVisible(true);
        if (mooring.isCreate() && mooring.getVesselDays() > 0 && mooring.getVesselSize() > 1) {
            try {
                ProductInfoExt vProduct = this.dlSales.getProductInfoByCode("BFeesDay1");
                vProduct.setName("Berth Fees 1st Day " + mooring.getVesselName());
                this.addTicketLine(vProduct, mooring.getVesselSize().intValue(), vProduct.getPriceSell());
                if (mooring.getVesselDays() > 1) {
                    vProduct = this.dlSales.getProductInfoByCode("BFeesDay2");
                    vProduct.setName("Additional Days " + (mooring.getVesselDays() - 1));
                    this.addTicketLine(vProduct, mooring.getVesselSize() * (mooring.getVesselDays() - 1), vProduct.getPriceSell());
                }
                if (mooring.getVesselPower().booleanValue()) {
                    vProduct = this.dlSales.getProductInfoByCode("PowerSupplied");
                    this.addTicketLine(vProduct, mooring.getVesselDays().intValue(), vProduct.getPriceSell());
                }
            }
            catch (BasicException basicException) {
                // empty catch block
            }
        }
        this.refreshTicket();
    }

    private void j_btnRemotePrtActionPerformed(ActionEvent evt) {
        if (this.m_oTicket == null || this.m_oTicket.getLinesCount() == 0) {
            return;
        }
        
        System.out.println("cocina: === JAVA SendOrder START === lines=" + this.m_oTicket.getLinesCount());
        
        // Collect info about which printers need triggering and which lines are new
        boolean[] printerNeeded = new boolean[6]; // P1-P6
        java.util.List<TicketLineInfo> allLines = this.m_oTicket.getLines();
        
        // Pre-initialize sendstatus for new lines
        for (int idx = 0; idx < allLines.size(); idx++) {
            TicketLineInfo line = allLines.get(idx);
            String status = line.getProperty("sendstatus");
            if (status == null) {
                line.setProperty("sendstatus", "No");
                status = "No";
            }
            String prt = line.getProperty("product.printer");
            System.out.println("cocina: Line " + idx + " (" + line.getProductName() + ") printer=" + prt + " status=" + status);
            
            if (prt != null && "No".equals(status)) {
                try {
                    int printerIdx = Integer.parseInt(prt) - 1;
                    if (printerIdx >= 0 && printerIdx < 6) {
                        printerNeeded[printerIdx] = true;
                    }
                } catch (NumberFormatException e) { /* ignore */ }
            }
        }
        
        // Print using a COPY of the ticket with only unsent lines (never modifies original)
        String[] printerNames = {"Printer.Ticket.P1", "Printer.Ticket.P2", "Printer.Ticket.P3",
                                  "Printer.Ticket.P4", "Printer.Ticket.P5", "Printer.Ticket.P6"};
        for (int p = 0; p < 6; p++) {
            if (!printerNeeded[p]) continue;
            
            String printerNum = String.valueOf(p + 1);
            
            // Build filtered list: only unsent lines for this printer
            java.util.List<TicketLineInfo> filteredLines = new java.util.ArrayList<>();
            for (TicketLineInfo line : allLines) {
                String prt = line.getProperty("product.printer");
                String status = line.getProperty("sendstatus");
                if (printerNum.equals(prt) && "No".equals(status)) {
                    filteredLines.add(line);
                }
            }
            
            if (filteredLines.isEmpty()) continue;
            
            System.out.println("cocina: Triggering " + printerNames[p] + " with " + filteredLines.size() + " NEW lines");
            
            // Create a copy of the ticket for printing — original ticket is untouched
            TicketInfo printCopy = this.m_oTicket.copyTicket();
            printCopy.setLines(filteredLines);
            
            // Print using the copy (3-arg method sends directly to printer)
            this.printTicket(printerNames[p], printCopy, this.m_oTicketExt);
        }
        
        // Mark all lines as sent
        for (TicketLineInfo line : allLines) {
             line.setProperty("sendstatus", "Yes");
        }
        
        // Persist the updated ticket to DB so sendstatus survives table switches
        try {
            if (this.m_oTicketExt != null && this.dlReceipts != null) {
                if (this.m_oTicket.getUser() == null) {
                    this.m_oTicket.setUser(new com.openbravo.pos.ticket.UserInfo(this.m_App.getAppUserView().getUser().getId(), this.m_App.getAppUserView().getUser().getName()));
                }
                
                String sharedId = this.m_oTicket.getProperty("table.id");
                if (sharedId == null) {
                    sharedId = this.m_oTicketExt.toString();
                }
                
                if (sharedId != null) {
                    this.dlReceipts.updateSharedTicket(sharedId, this.m_oTicket, this.m_oTicket.getPickupId());
                    System.out.println("cocina: Persisted sendstatus to DB for sharedId=" + sharedId);
                }
            }
        } catch (BasicException ex) {
            Logger.getLogger(JPanelTicket.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("cocina: === JAVA SendOrder END ===");
        this.refreshTicket();
    }

    private void m_jKeyFactoryActionPerformed(ActionEvent evt) {
    }

    private void m_jTaxActionPerformed(ActionEvent evt) {
    }

    private void btnReprint1ActionPerformed(ActionEvent evt) {
        block6: {
            if (this.m_config.getProperty("lastticket.number") != null) {
                try {
                    TicketInfo ticket = this.dlSales.loadTicket(Integer.parseInt(this.m_config.getProperty("lastticket.type")), Integer.parseInt(this.m_config.getProperty("lastticket.number")));
                    if (ticket == null) {
                        JFrame frame = new JFrame();
                        JOptionPane.showMessageDialog(frame, AppLocal.getIntString("message.notexiststicket"), AppLocal.getIntString("message.notexiststickettitle"), 2);
                        break block6;
                    }
                    this.m_ticket = ticket;
                    this.m_ticketCopy = null;
                    try {
                        this.taxeslogic.calculateTaxes(this.m_ticket);
                        TicketTaxInfo[] frame = this.m_ticket.getTaxLines();
                    }
                    catch (TaxesException frame) {
                        // empty catch block
                    }
                    this.printTicket("Printer.ReprintTicket", this.m_ticket, null);
                    this.Notify("'Printed'");
                }
                catch (BasicException e) {
                    MessageInf msg = new MessageInf(-33554432, AppLocal.getIntString("message.cannotloadticket"), e);
                    msg.show(this);
                }
            }
        }
    }

    private void btnSplitActionPerformed(ActionEvent evt) {
        if (this.m_oTicket.getLinesCount() > 0) {
            ReceiptSplit splitdialog = ReceiptSplit.getDialog(this, this.dlSystem.getResourceAsXML("Ticket.Line"), this.dlSales, this.dlCustomers, this.taxeslogic);
            TicketInfo ticket1 = this.m_oTicket.copyTicket();
            TicketInfo ticket2 = new TicketInfo();
            ticket2.setCustomer(this.m_oTicket.getCustomer());
            if (splitdialog.showDialog(ticket1, ticket2, this.m_oTicketExt) && this.closeTicket(ticket2, this.m_oTicketExt)) {
                this.setActiveTicket(ticket1, this.m_oTicketExt);
            }
        }
    }

    private void btnCreateCustomerActionPerformed(ActionEvent evt) {
        JDialogNewCustomer dialog = JDialogNewCustomer.getDialog(this, this.m_App);
        dialog.setVisible(true);
        CustomerInfoExt m_customerInfo = dialog.getSelectedCustomer();
        if (dialog.getSelectedCustomer() != null) {
            try {
                this.m_oTicket.setCustomer(this.dlSales.loadCustomerExt(dialog.getSelectedCustomer().getId()));
                this.refreshTicket();
            }
            catch (BasicException ex) {
                Logger.getLogger(JPanelTicket.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void btnCreateProductActionPerformed(ActionEvent evt) {
        if (!this.m_App.getAppUserView().getUser().hasPermission("sales.CreateProduct")) {
            return;
        }
        JDialogCreateProduct dialog = new JDialogCreateProduct(SwingUtilities.getWindowAncestor(this), this.dlSales, this.m_App, this.taxcategoriesmodel, this.categoriesmodel);
        String searchText = "";
        try {
            JTextField txt = (JTextField)this.cbProductSearch.getEditor().getEditorComponent();
            searchText = txt.getText();
            if (searchText != null) {
                if (searchText.startsWith("<html>")) {
                    searchText = searchText.replaceAll("\\<.*?\\>", "");
                }
                if (searchText.contains("Create new:")) {
                    searchText = searchText.replace("Create new:", "").trim();
                }
            }
        }
        catch (Exception txt) {
            // empty catch block
        }
        if (dialog.showDialog(searchText)) {
            try {
                List<ProductInfoExt> products = this.dlSales.getProductsByKeyword(searchText);
                for (ProductInfoExt p : products) {
                    if (!p.getName().equalsIgnoreCase(searchText) && !p.getCode().equalsIgnoreCase(searchText)) continue;
                    this.addTicketLine(p, 1.0, p.getPriceSell());
                    break;
                }
            }
            catch (BasicException e) {
                e.printStackTrace();
            }
            this.cbProductSearch.setSelectedItem(null);
        }
    }

    private void cbProductSearchKeyReleased(KeyEvent evt) {
        if (this.productSearchTimer != null && this.productSearchTimer.isRunning()) {
            this.productSearchTimer.stop();
        }
        if (evt.getKeyCode() == 40 || evt.getKeyCode() == 38 || evt.getKeyCode() == 10) {
            return;
        }
        this.productSearchTimer = new Timer(500, new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                JPanelTicket.this.performProductSearch();
            }
        });
        this.productSearchTimer.setRepeats(false);
        this.productSearchTimer.start();
    }

    private void performProductSearch() {
        JTextField txt = (JTextField)this.cbProductSearch.getEditor().getEditorComponent();
        String text = txt.getText();
        if (text == null || text.length() < 2) {
            return;
        }
        try {
            String keyword = "%" + text + "%";
            List<ProductInfoExt> products = this.dlSales.getProductsByKeyword(keyword);
            if (products.isEmpty() && this.m_App.getAppUserView().getUser().hasPermission("sales.CreateProduct")) {
                ProductInfoExt createDummy = new ProductInfoExt();
                createDummy.setID("CREATE_NEW_PROD_DUMMY_ID");
                createDummy.setName("Create new: " + text);
                products.add(createDummy);
            }
            String currentText = text;
            int caretPos = txt.getCaretPosition();
            this.cbProductSearch.removeActionListener(this.productSearchActionListener);
            DefaultComboBoxModel<ProductInfoExt> model = new DefaultComboBoxModel<ProductInfoExt>(products.toArray(new ProductInfoExt[0]));
            this.cbProductSearch.setModel(model);
            txt.setText(currentText);
            try {
                txt.setCaretPosition(caretPos);
            }
            catch (Exception exception) {
                // empty catch block
            }
            if (products.size() > 0) {
                this.cbProductSearch.showPopup();
            } else {
                this.cbProductSearch.hidePopup();
            }
            this.cbProductSearch.addActionListener(this.productSearchActionListener);
        }
        catch (BasicException e) {
            e.printStackTrace();
        }
    }

    private void cbProductSearchActionPerformed(ActionEvent evt) {
        Object selected = this.cbProductSearch.getSelectedItem();
        if (selected instanceof ProductInfoExt) {
            ProductInfoExt product = (ProductInfoExt)selected;
            if ("CREATE_NEW_PROD_DUMMY_ID".equals(product.getID())) {
                this.btnCreateProductActionPerformed(null);
                return;
            }
            this.addTicketLine(product, 1.0, product.getPriceSell());
            EventQueue.invokeLater(new Runnable(){

                @Override
                public void run() {
                    JPanelTicket.this.cbProductSearch.setSelectedItem(null);
                }
            });
        }
    }

    private void cbCustomerSearchKeyReleased(KeyEvent evt) {
        if (this.searchTimer != null && this.searchTimer.isRunning()) {
            this.searchTimer.stop();
        }
        if (evt.getKeyCode() == 40 || evt.getKeyCode() == 38 || evt.getKeyCode() == 10) {
            return;
        }
        this.searchTimer = new Timer(500, new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                JPanelTicket.this.performCustomerSearch();
            }
        });
        this.searchTimer.setRepeats(false);
        this.searchTimer.start();
    }

    private void performCustomerSearch() {
        JTextField txt = (JTextField)this.cbCustomerSearch.getEditor().getEditorComponent();
        String text = txt.getText();
        if (text == null || text.length() < 2) {
            return;
        }
        try {
            String keyword = "%" + text + "%";
            List<CustomerInfo> customers = this.dlCustomers.getCustomersByKeyword(keyword);
            if (customers.isEmpty()) {
                CustomerInfo createDummy = new CustomerInfo("CREATE_NEW_DUMMY_ID");
                createDummy.setName("Create new: " + text);
                customers.add(createDummy);
            }
            String currentText = text;
            int caretPos = txt.getCaretPosition();
            this.cbCustomerSearch.removeActionListener(this.searchActionListener);
            DefaultComboBoxModel<CustomerInfo> model = new DefaultComboBoxModel<CustomerInfo>(customers.toArray(new CustomerInfo[0]));
            this.cbCustomerSearch.setModel(model);
            txt.setText(currentText);
            try {
                txt.setCaretPosition(caretPos);
            }
            catch (Exception exception) {
                // empty catch block
            }
            if (customers.size() > 0) {
                this.cbCustomerSearch.showPopup();
            } else {
                this.cbCustomerSearch.hidePopup();
            }
            this.cbCustomerSearch.addActionListener(this.searchActionListener);
        }
        catch (BasicException e) {
            e.printStackTrace();
        }
    }

    private void cbCustomerSearchActionPerformed(ActionEvent evt) {
        Object selected = this.cbCustomerSearch.getSelectedItem();
        if (selected instanceof CustomerInfo) {
            CustomerInfo customer = (CustomerInfo)selected;
            if ("CREATE_NEW_DUMMY_ID".equals(customer.getId())) {
                this.btnCreateCustomerActionPerformed(null);
                return;
            }
            try {
                this.m_oTicket.setCustomer(this.dlSales.loadCustomerExt(customer.getId()));
                if ("restaurant".equals(this.m_App.getProperties().getProperty("machine.ticketsbag"))) {
                    this.restDB.setCustomerNameInTableByTicketId(this.dlSales.loadCustomerExt(customer.getId()).toString(), this.m_oTicket.getId());
                }
                this.checkCustomer();
                this.m_jTicketId.setText(this.m_oTicket.getName(this.m_oTicketExt));
                this.refreshTicket();
            }
            catch (BasicException e) {
                MessageInf msg = new MessageInf(-33554432, AppLocal.getIntString("message.cannotfindcustomer"), e);
                msg.show(this);
            }
        }
    }

    private void jCheckStockActionPerformed(ActionEvent evt) {
        int i;
        if (this.listener != null) {
            this.listener.stop();
        }
        if ((i = this.m_ticketlines.getSelectedIndex()) < 0) {
            Toolkit.getDefaultToolkit().beep();
        } else {
            try {
                TicketLineInfo line = this.m_oTicket.getLine(i);
                String pId = line.getProductID();
                ProductStock checkProduct = this.dlSales.getProductStockState(pId);
                if (checkProduct != null) {
                    if (checkProduct.getUnits() <= 0.0) {
                        this.jCheckStock.setForeground(Color.magenta);
                    } else {
                        this.jCheckStock.setForeground(Color.darkGray);
                    }
                    double dUnits = checkProduct.getUnits();
                    int iUnits = (int)dUnits;
                    this.jCheckStock.setText(Integer.toString(iUnits));
                } else {
                    this.jCheckStock.setText(null);
                }
            }
            catch (BasicException ex) {
                Logger.getLogger(JPanelTicket.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (this.listener != null) {
            this.listener.restart();
        }
    }

    private void jCheckStockMouseClicked(MouseEvent evt) {
        if (evt.getClickCount() == 2) {
            int i;
            if (this.listener != null) {
                this.listener.stop();
            }
            if ((i = this.m_ticketlines.getSelectedIndex()) < 0) {
                Toolkit.getDefaultToolkit().beep();
            } else {
                try {
                    TicketLineInfo line = this.m_oTicket.getLine(i);
                    String pId = line.getProductID();
                    ProductStock checkProduct = this.dlSales.getProductStockState(pId);
                    Double pMin = checkProduct.getMinimum() != null ? checkProduct.getMinimum() : Double.valueOf(0.0);
                    Double pMax = checkProduct.getMaximum() != null ? checkProduct.getMaximum() : Double.valueOf(0.0);
                    String content = "<html><b>" + AppLocal.getIntString("label.locationplace") + " : </b>" + checkProduct.getLocation() + "<br><b>" + AppLocal.getIntString("label.maximum") + " : </b>" + pMax + "<br><b>" + AppLocal.getIntString("label.minimum") + " : </b>" + pMin + "<br>";
                    JFrame frame = new JFrame();
                    JOptionPane.showMessageDialog(frame, content, "Info", 1);
                }
                catch (BasicException ex) {
                    Logger.getLogger(JPanelTicket.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (this.listener != null) {
                this.listener.restart();
            }
        }
    }

    private void m_jaddtaxActionPerformed(ActionEvent evt) {
        this.m_jKeyFactory.requestFocus();
    }

    private class logout
    extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent ae) {
            switch (JPanelTicket.this.m_App.getProperties().getProperty("machine.ticketsbag")) {
                case "restaurant": {
                    if ("false".equals(JPanelTicket.this.m_App.getProperties().getProperty("till.autoLogoffrestaurant"))) {
                        JPanelTicket.this.deactivate();
                        ((JRootApp)JPanelTicket.this.m_App).closeAppView();
                        break;
                    }
                    JPanelTicket.this.deactivate();
                    JPanelTicket.this.setActiveTicket(null, null);
                    break;
                }
                default: {
                    JPanelTicket.this.deactivate();
                    ((JRootApp)JPanelTicket.this.m_App).closeAppView();
                }
            }
        }
    }

    public static class ScriptArg {
        private final String key;
        private final Object value;

        public ScriptArg(String key, Object value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return this.key;
        }

        public Object getValue() {
            return this.value;
        }
    }

    public class ScriptObject {
        private final TicketInfo ticket;
        private final Object ticketext;
        private int selectedindex;

        private ScriptObject(TicketInfo ticket, Object ticketext) {
            this.ticket = ticket;
            this.ticketext = ticketext;
        }

        public double getInputValue() {
            if (JPanelTicket.this.m_iNumberStatusInput == 1 && JPanelTicket.this.m_iNumberStatusPor == 0) {
                return JPanelTicket.this.getInputValue();
            }
            return 0.0;
        }

        public int getSelectedIndex() {
            return this.selectedindex;
        }

        public void setSelectedIndex(int i) {
            this.selectedindex = i;
        }

        public void printReport(String resourcefile) {
            JPanelTicket.this.printReport(resourcefile, this.ticket, this.ticketext);
        }

        public void printTicket(String sresourcename) {
            JPanelTicket.this.printTicket(sresourcename, this.ticket, this.ticketext);
            JPanelTicket.this.j_btnRemotePrt.setEnabled(false);
        }

        public Object evalScript(String code, ScriptArg ... args) throws ScriptException {
            ScriptEngine script = ScriptFactory.getScriptEngine("beanshell");
            String sDBUser = JPanelTicket.this.m_App.getProperties().getProperty("db.user");
            String sDBPassword = JPanelTicket.this.m_App.getProperties().getProperty("db.password");
            if (sDBUser != null && sDBPassword != null && sDBPassword.startsWith("crypt:")) {
                AltEncrypter cypher = new AltEncrypter("cypherkey" + sDBUser);
                sDBPassword = cypher.decrypt(sDBPassword.substring(6));
            }
            script.put("hostname", JPanelTicket.this.m_App.getProperties().getProperty("machine.hostname"));
            script.put("dbURL", JPanelTicket.this.m_App.getProperties().getProperty("db.URL"));
            script.put("dbUser", sDBUser);
            script.put("dbPassword", sDBPassword);
            script.put("ticket", this.ticket);
            script.put("place", this.ticketext);
            script.put("taxes", JPanelTicket.this.taxcollection);
            script.put("taxeslogic", JPanelTicket.this.taxeslogic);
            script.put("user", JPanelTicket.this.m_App.getAppUserView().getUser());
            script.put("sales", this);
            script.put("taxesinc", JPanelTicket.this.m_jaddtax.isSelected());
            script.put("warranty", JPanelTicket.this.warrantyPrint);
            script.put("pickupid", JPanelTicket.this.getPickupString(this.ticket));
            for (ScriptArg arg : args) {
                script.put(arg.getKey(), arg.getValue());
            }
            return script.eval(code);
        }
    }

    public class ScrollablePanel
    extends JPanel
    implements Scrollable {
        @Override
        public Dimension getPreferredScrollableViewportSize() {
            return this.getPreferredSize();
        }

        @Override
        public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
            return 30;
        }

        @Override
        public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
            return 100;
        }

        @Override
        public boolean getScrollableTracksViewportWidth() {
            return true;
        }

        @Override
        public boolean getScrollableTracksViewportHeight() {
            return false;
        }
    }
}

