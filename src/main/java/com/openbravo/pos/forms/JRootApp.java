/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.dalsemi.onewire.OneWireAccessProvider
 *  com.dalsemi.onewire.OneWireException
 *  com.dalsemi.onewire.adapter.DSPortAdapter
 *  com.dalsemi.onewire.application.monitor.DeviceMonitor
 *  com.dalsemi.onewire.application.monitor.DeviceMonitorEvent
 *  com.dalsemi.onewire.application.monitor.DeviceMonitorEventListener
 *  com.dalsemi.onewire.application.monitor.DeviceMonitorException
 *  com.dalsemi.onewire.container.OneWireContainer
 *  com.dalsemi.onewire.utils.Address
 *  org.joda.time.DateTime
 */
package com.openbravo.pos.forms;

import com.dalsemi.onewire.OneWireAccessProvider;
import com.dalsemi.onewire.OneWireException;
import com.dalsemi.onewire.adapter.DSPortAdapter;
import com.dalsemi.onewire.application.monitor.DeviceMonitor;
import com.dalsemi.onewire.application.monitor.DeviceMonitorEvent;
import com.dalsemi.onewire.application.monitor.DeviceMonitorEventListener;
import com.dalsemi.onewire.application.monitor.DeviceMonitorException;
import com.dalsemi.onewire.container.OneWireContainer;
import com.dalsemi.onewire.utils.Address;
import com.openbravo.basic.BasicException;
import com.openbravo.beans.JFlowPanel;
import com.openbravo.beans.JPasswordDialog;
import com.openbravo.data.gui.JMessageDialog;
import com.openbravo.data.gui.MessageInf;
import com.openbravo.data.loader.BatchSentenceResource;
import com.openbravo.data.loader.Session;
import com.openbravo.format.Formats;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.AppProperties;
import com.openbravo.pos.forms.AppUser;
import com.openbravo.pos.forms.AppUserView;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.forms.AppViewConnection;
import com.openbravo.pos.forms.BeanFactory;
import com.openbravo.pos.forms.BeanFactoryApp;
import com.openbravo.pos.forms.BeanFactoryException;
import com.openbravo.pos.forms.BeanFactoryObj;
import com.openbravo.pos.forms.BeanFactoryScript;
import com.openbravo.pos.forms.DataLogicSystem;
import com.openbravo.pos.forms.JPrincipalApp;
import com.openbravo.pos.printer.DeviceTicket;
import com.openbravo.pos.printer.TicketParser;
import com.openbravo.pos.printer.TicketPrinterException;
import com.openbravo.pos.scale.DeviceScale;
import com.openbravo.pos.scanpal2.DeviceScanner;
import com.openbravo.pos.scanpal2.DeviceScannerFactory;
import com.openbravo.pos.util.uOWWatch;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import org.joda.time.DateTime;

public class JRootApp
extends JPanel
implements AppView,
DeviceMonitorEventListener {
    private AppProperties m_props;
    private Session session;
    private DataLogicSystem m_dlSystem;
    private Properties m_propsdb = null;
    private String m_sActiveCashIndex;
    private int m_iActiveCashSequence;
    private Date m_dActiveCashDateStart;
    private Date m_dActiveCashDateEnd;
    private Double m_dActiveCashNotes;
    private Double m_dActiveCashCoins;
    private Double m_dActiveCashCards;
    private String m_sClosedCashIndex;
    private int m_iClosedCashSequence;
    private Date m_dClosedCashDateStart;
    private Date m_dClosedCashDateEnd;
    private String m_sInventoryLocation;
    private StringBuilder inputtext;
    private DeviceScale m_Scale;
    private DeviceScanner m_Scanner;
    private DeviceTicket m_TP;
    private TicketParser m_TTP;
    private final Map<String, BeanFactory> m_aBeanFactories = new HashMap<String, BeanFactory>();
    private JPrincipalApp m_principalapp = null;
    private static HashMap<String, String> m_oldclasses;
    private String m_clock;
    private String m_date;
    private Connection con;
    private ResultSet rs;
    private Statement stmt;
    private String SQL;
    private String sJLVersion;
    private DatabaseMetaData md;
    private final int m_rate = 0;
    private DSPortAdapter m_oneWireAdapter;
    private DeviceMonitor m_oneWireMonitor;
    static final int UNIQUE_KEY_FAMILY = 1;
    private Box.Filler filler2;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JPanel jPanel3;
    private JPanel jPanel4;
    private JPanel jPanel5;
    private JPanel jPanel8;
    private JScrollPane jScrollPane1;
    private JButton m_jClose;
    private JLabel m_jHost;
    private JLabel m_jLblTitle;
    private JPanel m_jLogonName;
    private JPanel m_jPanelContainer;
    private JPanel m_jPanelDown;
    private JPanel m_jPanelLogin;
    private JPanel m_jPanelTitle;
    private JTextField m_txtKeys;
    private JPanel panelTask;
    private JLabel poweredby;
    private JProgressBar serverMonitor;

    private DateTime getDateTime() {
        DateTime dt = DateTime.now();
        return dt;
    }

    private String getLineTimer() {
        return Formats.HOURMIN.formatValue(new Date());
    }

    private String getLineDate() {
        DateFormat df = DateFormat.getDateInstance(2, JRootApp.getDefaultLocale());
        return df.format(new Date());
    }

    public JRootApp() {
        this.initComponents();
        this.jScrollPane1.getVerticalScrollBar().setPreferredSize(new Dimension(30, 30));
        this.serverMonitor.setVisible(false);
    }

    private void initIButtonMonitor() {
        assert (this.m_oneWireMonitor == null);
        try {
            this.m_oneWireAdapter = OneWireAccessProvider.getDefaultAdapter();
            this.m_oneWireAdapter.setSearchAllDevices();
            this.m_oneWireAdapter.targetFamily(1);
            this.m_oneWireAdapter.setSpeed(0);
            this.m_oneWireMonitor = new DeviceMonitor(this.m_oneWireAdapter);
            this.m_oneWireMonitor.setMaxStateCount(5);
            this.m_oneWireMonitor.addDeviceMonitorEventListener((DeviceMonitorEventListener)this);
            new Thread((Runnable)this.m_oneWireMonitor).start();
        }
        catch (OneWireException e) {
            JMessageDialog.showMessage(this, new MessageInf(-33554432, AppLocal.getIntString("message.ibuttonnotfound"), (Object)e));
        }
    }

    private void shutdownIButtonMonitor() {
        if (this.m_oneWireMonitor != null) {
            this.m_oneWireMonitor.killMonitor();
            try {
                this.m_oneWireAdapter.freePort();
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }

    public void releaseResources() {
        this.shutdownIButtonMonitor();
    }

    private boolean isDeviceRelevant(OneWireContainer container) {
        String iButtonId = container.getAddressAsString();
        try {
            if (container.getAdapter().getAdapterAddress().equals(iButtonId)) {
                return false;
            }
        }
        catch (OneWireException oneWireException) {
            // empty catch block
        }
        byte familyNumber = Address.toByteArray((String)iButtonId)[0];
        return familyNumber == 1;
    }

    public void deviceArrival(DeviceMonitorEvent devt) {
        assert (this.m_dlSystem != null);
        for (int i = 0; i < devt.getDeviceCount(); ++i) {
            AppUser user;
            block5: {
                OneWireContainer container = devt.getContainerAt(i);
                if (!this.isDeviceRelevant(container)) continue;
                String iButtonId = devt.getAddressAsStringAt(i);
                user = null;
                try {
                    user = this.m_dlSystem.findPeopleByCard(iButtonId);
                }
                catch (BasicException e) {
                    if (user != null) break block5;
                    JOptionPane.showMessageDialog(this, AppLocal.getIntString("message.ibuttonnotassign"), AppLocal.getIntString("title.editor"), 1);
                }
            }
            if (user == null) {
                JOptionPane.showMessageDialog(this, AppLocal.getIntString("message.ibuttonnotassign"), AppLocal.getIntString("title.editor"), 1);
                continue;
            }
            this.setVisible(false);
            this.openAppView(user);
            this.setVisible(true);
        }
    }

    public void deviceDeparture(DeviceMonitorEvent devt) {
        for (int i = 0; i < devt.getDeviceCount(); ++i) {
            AppUser currentUser;
            OneWireContainer container = devt.getContainerAt(i);
            if (!this.isDeviceRelevant(container)) continue;
            String iButtonId = devt.getAddressAsStringAt(i);
            if (this.m_principalapp == null || (currentUser = this.m_principalapp.getUser()) == null || !currentUser.getCard().equals(iButtonId)) continue;
            this.closeAppView();
        }
    }

    public void networkException(DeviceMonitorException dexc) {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean initApp(AppProperties props) {
        String url;
        String sWareHouse;
        ResultSet tables;
        DatabaseMetaData md;
        this.m_props = props;
        this.m_jPanelDown.setVisible(Boolean.valueOf(this.m_props.getProperty("till.hideinfo")) == false);
        this.applyComponentOrientation(ComponentOrientation.getOrientation(Locale.getDefault()));
        try {
            this.session = AppViewConnection.createSession(this.m_props);
        }
        catch (BasicException e) {
            JMessageDialog.showMessage(this, new MessageInf(-16777216, e.getMessage(), e));
            return false;
        }
        this.m_dlSystem = (DataLogicSystem)this.getBean("com.openbravo.pos.forms.DataLogicSystem");
        String sDBVersion = this.readDataBaseVersion();
        if (!AppLocal.APP_VERSION.equals(sDBVersion)) {
            String sScript;
            String string = sScript = sDBVersion == null ? this.m_dlSystem.getInitScript() + "-create.sql" : this.m_dlSystem.getInitScript() + "-upgrade-" + sDBVersion + ".sql";
            if (JRootApp.class.getResource(sScript) == null) {
                JMessageDialog.showMessage(this, new MessageInf(-16777216, sDBVersion == null ? AppLocal.getIntString("message.databasenotsupported", this.session.DB.getName() + " " + sDBVersion) : AppLocal.getIntString("message.noupdatescript")));
                this.session.close();
                return false;
            }
            if (JOptionPane.showConfirmDialog(this, AppLocal.getIntString(sDBVersion == null ? "message.createdatabase" : "message.updatedatabase", this.session.DB.getName() + " " + sDBVersion), AppLocal.getIntString("message.title"), 0, 3) != 0) {
                this.session.close();
                return false;
            }
            try {
                BatchSentenceResource bsentence = new BatchSentenceResource(this.session, sScript);
                bsentence.putParameter("APP_ID", Matcher.quoteReplacement("soltecpos"));
                bsentence.putParameter("APP_NAME", Matcher.quoteReplacement(AppLocal.APP_NAME));
                bsentence.putParameter("APP_VERSION", Matcher.quoteReplacement(AppLocal.APP_VERSION));
                List l = bsentence.list();
                if (l.size() > 0) {
                    JMessageDialog.showMessage(this, new MessageInf(-33554432, AppLocal.getIntString("database.scriptwarning"), l.toArray(new Throwable[l.size()])));
                }
            }
            catch (BasicException e) {
                JMessageDialog.showMessage(this, new MessageInf(-16777216, AppLocal.getIntString("database.scripterror"), e));
                this.session.close();
                return false;
            }
        }
        try {
            md = this.session.getConnection().getMetaData();
            tables = md.getTables(null, null, "PAYMENT_CATEGORIES", null);
            if (!tables.next()) {
                JMessageDialog.showMessage(this, new MessageInf(-67108864, "Updating database schema for Payment Categories..."));
                String sScript = "/com/openbravo/pos/scripts/MySQL-payment_categories.sql";
                BatchSentenceResource bsentence = new BatchSentenceResource(this.session, sScript);
                bsentence.list();
            }
            tables.close();
        }
        catch (BasicException | SQLException e) {
            JMessageDialog.showMessage(this, new MessageInf(-33554432, "Failed to update database schema for Payment Categories.", e));
        }
        try {
            md = this.session.getConnection().getMetaData();
            tables = md.getTables(null, null, "TICKETSNUM_FE", null);
            if (!tables.next()) {
                ResultSet tablesLower = md.getTables(null, null, "ticketsnum_fe", null);
                if (!tablesLower.next()) {
                    JMessageDialog.showMessage(this, new MessageInf(-67108864, "Updating database schema for Electronic Invoicing sequences..."));
                    String sScript = "/com/openbravo/pos/scripts/" + this.session.DB.getName() + "-fe_ticketnums.sql";
                    if (JRootApp.class.getResource(sScript) != null) {
                        BatchSentenceResource bsentence = new BatchSentenceResource(this.session, sScript);
                        bsentence.list();
                    }
                }
                tablesLower.close();
            }
            tables.close();
        }
        catch (BasicException | SQLException e) {
            Logger.getLogger(JRootApp.class.getName()).log(Level.WARNING, "Failed to update FE ticket numbers schema", e);
        }
        this.m_propsdb = this.m_dlSystem.getResourceAsProperties(this.m_props.getHost() + "/properties");
        try {
            Object[] valcash;
            String sActiveCashIndex = this.m_propsdb.getProperty("activecash");
            Object[] objectArray = valcash = sActiveCashIndex == null ? null : this.m_dlSystem.findActiveCash(sActiveCashIndex);
            if (valcash == null || !this.m_props.getHost().equals(valcash[0])) {
                this.setActiveCash(UUID.randomUUID().toString(), this.m_dlSystem.getSequenceCash(this.m_props.getHost()) + 1, new Date(), null);
                this.m_dlSystem.execInsertCash(new Object[]{this.getActiveCashIndex(), this.m_props.getHost(), this.getActiveCashSequence(), this.getActiveCashDateStart(), this.getActiveCashDateEnd()});
            } else {
                this.setActiveCash(sActiveCashIndex, (Integer)valcash[1], (Date)valcash[2], (Date)valcash[3]);
            }
        }
        catch (BasicException e) {
            MessageInf msg = new MessageInf(-67108864, AppLocal.getIntString("message.cannotclosecash"), e);
            msg.show(this);
            this.session.close();
            return false;
        }
        this.m_sInventoryLocation = this.m_propsdb.getProperty("location");
        if (this.m_sInventoryLocation == null) {
            this.m_sInventoryLocation = "0";
            this.m_propsdb.setProperty("location", this.m_sInventoryLocation);
            this.m_dlSystem.setResourceAsProperties(this.m_props.getHost() + "/properties", this.m_propsdb);
        }
        this.m_TP = new DeviceTicket(this, this.m_props);
        this.m_TTP = new TicketParser(this.getDeviceTicket(), this.m_dlSystem);
        this.printerStart();
        this.m_Scale = new DeviceScale(this, this.m_props);
        this.m_Scanner = DeviceScannerFactory.createInstance(this.m_props);
        new Timer(250, new PrintTimeAction()).start();
        try {
            sWareHouse = this.m_dlSystem.findLocationName(this.m_sInventoryLocation);
        }
        catch (BasicException e) {
            sWareHouse = null;
        }
        try {
            url = this.session.getURL();
        }
        catch (SQLException e) {
            url = "";
        }
        this.m_jHost.setText("<html>" + this.m_props.getHost() + " - " + sWareHouse + "<br>" + url);
        String newLogo = this.m_props.getProperty("start.logo");
        if (newLogo != null) {
            if ("".equals(newLogo)) {
                this.jLabel1.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/logo.png")));
            } else {
                this.jLabel1.setIcon(new ImageIcon(newLogo));
            }
        }
        this.jLabel1.setText("<html><center><b><font size='5' color='#004488'>SOLTEC POS</font></b><br><font size='3'>Soluciones Tecnol\u00f3gicas</font><br><br><b>Sistema de Facturaci\u00f3n e Inventario</b><br>Facturaci\u00f3n Electr\u00f3nica DIAN<br>Control Total de Inventarios y Bodegas<br>Gesti\u00f3n de Mesas y Domicilios<br>Reportes Financieros Detallados<br><br><b><font color='blue'><u>Haga Clic Aqu\u00ed para Soporte Remoto</u></font></b><br><br>Software Licenciado a: <b>" + this.m_props.getHost() + "</b><br></center></html>");
        this.jLabel1.setCursor(Cursor.getPredefinedCursor(12));
        this.jLabel1.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent evt) {
                JRootApp.this.launchRemoteSupport();
            }
        });
        this.jLabel1.setAlignmentX(0.5f);
        this.jLabel1.setHorizontalTextPosition(0);
        this.jLabel1.setMaximumSize(new Dimension(800, 1024));
        this.jLabel1.setVerticalTextPosition(3);
        this.showLogin();
        String ibutton = this.m_props.getProperty("machine.iButton");
        if (ibutton.equals("true")) {
            this.initIButtonMonitor();
            uOWWatch.iButtonOn();
        }
        return true;
    }

    private String readDataBaseVersion() {
        try {
            return this.m_dlSystem.findVersion();
        }
        catch (BasicException ed) {
            return null;
        }
    }

    public void tryToClose() {
        if (this.closeAppView()) {
            this.m_TP.getDeviceDisplay().clearVisor();
            this.shutdownIButtonMonitor();
            this.session.close();
            SwingUtilities.getWindowAncestor(this).dispose();
        }
    }

    @Override
    public DeviceTicket getDeviceTicket() {
        return this.m_TP;
    }

    @Override
    public DeviceScale getDeviceScale() {
        return this.m_Scale;
    }

    @Override
    public DeviceScanner getDeviceScanner() {
        return this.m_Scanner;
    }

    @Override
    public Session getSession() {
        return this.session;
    }

    @Override
    public String getInventoryLocation() {
        return this.m_sInventoryLocation;
    }

    @Override
    public String getActiveCashIndex() {
        return this.m_sActiveCashIndex;
    }

    @Override
    public int getActiveCashSequence() {
        return this.m_iActiveCashSequence;
    }

    @Override
    public Date getActiveCashDateStart() {
        return this.m_dActiveCashDateStart;
    }

    @Override
    public Date getActiveCashDateEnd() {
        return this.m_dActiveCashDateEnd;
    }

    @Override
    public void setActiveCash(String sIndex, int iSeq, Date dStart, Date dEnd) {
        this.m_sActiveCashIndex = sIndex;
        this.m_iActiveCashSequence = iSeq;
        this.m_dActiveCashDateStart = dStart;
        this.m_dActiveCashDateEnd = dEnd;
        this.m_propsdb.setProperty("activecash", this.m_sActiveCashIndex);
        this.m_dlSystem.setResourceAsProperties(this.m_props.getHost() + "/properties", this.m_propsdb);
    }

    @Override
    public String getClosedCashIndex() {
        return this.m_sClosedCashIndex;
    }

    @Override
    public int getClosedCashSequence() {
        return this.m_iClosedCashSequence;
    }

    @Override
    public Date getClosedCashDateStart() {
        return this.m_dClosedCashDateStart;
    }

    @Override
    public Date getClosedCashDateEnd() {
        return this.m_dClosedCashDateEnd;
    }

    @Override
    public void setClosedCash(String sIndex, int iSeq, Date dStart, Date dEnd) {
        this.m_sClosedCashIndex = sIndex;
        this.m_iClosedCashSequence = iSeq;
        this.m_dClosedCashDateStart = dStart;
        this.m_dClosedCashDateEnd = dEnd;
        this.m_dlSystem.setResourceAsProperties(this.m_props.getHost() + "/properties", this.m_propsdb);
    }

    @Override
    public AppProperties getProperties() {
        return this.m_props;
    }

    @Override
    public Object getBean(String beanfactory) throws BeanFactoryException {
        BeanFactory bf = this.m_aBeanFactories.get(beanfactory = JRootApp.mapNewClass(beanfactory));
        if (bf == null) {
            if (beanfactory.startsWith("/")) {
                bf = new BeanFactoryScript(beanfactory);
            } else {
                try {
                    Class<?> bfclass = Class.forName(beanfactory);
                    if (BeanFactory.class.isAssignableFrom(bfclass)) {
                        bf = (BeanFactory)bfclass.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
                    } else {
                        Constructor<?> constMyView = bfclass.getConstructor(AppView.class);
                        Object bean = constMyView.newInstance(this);
                        bf = new BeanFactoryObj(bean);
                    }
                }
                catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
                    throw new BeanFactoryException(e);
                }
            }
            this.m_aBeanFactories.put(beanfactory, bf);
            if (bf instanceof BeanFactoryApp) {
                ((BeanFactoryApp)bf).init(this);
            }
        }
        return bf.getBean();
    }

    private static String mapNewClass(String classname) {
        String newclass = m_oldclasses.get(classname);
        return newclass == null ? classname : newclass;
    }

    private static void initOldClasses() {
        m_oldclasses = new HashMap();
        m_oldclasses.put("com.openbravo.pos.reports.JReportCustomers", "/com/openbravo/reports/customers.bs");
        m_oldclasses.put("com.openbravo.pos.reports.JReportCustomersB", "/com/openbravo/reports/customersb.bs");
        m_oldclasses.put("com.openbravo.pos.reports.JReportClosedPos", "/com/openbravo/reports/closedpos.bs");
        m_oldclasses.put("com.openbravo.pos.reports.JReportClosedProducts", "/com/openbravo/reports/closedproducts.bs");
        m_oldclasses.put("com.openbravo.pos.reports.JChartSales", "/com/openbravo/reports/chartsales.bs");
        m_oldclasses.put("com.openbravo.pos.reports.JReportInventory", "/com/openbravo/reports/inventory.bs");
        m_oldclasses.put("com.openbravo.pos.reports.JReportInventory2", "/com/openbravo/reports/inventoryb.bs");
        m_oldclasses.put("com.openbravo.pos.reports.JReportInventoryBroken", "/com/openbravo/reports/inventorybroken.bs");
        m_oldclasses.put("com.openbravo.pos.reports.JReportInventoryDiff", "/com/openbravo/reports/inventorydiff.bs");
        m_oldclasses.put("com.openbravo.pos.reports.JReportPeople", "/com/openbravo/reports/people.bs");
        m_oldclasses.put("com.openbravo.pos.reports.JReportTaxes", "/com/openbravo/reports/taxes.bs");
        m_oldclasses.put("com.openbravo.pos.reports.JReportUserSales", "/com/openbravo/reports/usersales.bs");
        m_oldclasses.put("com.openbravo.pos.reports.JReportProducts", "/com/openbravo/reports/products.bs");
        m_oldclasses.put("com.openbravo.pos.reports.JReportCatalog", "/com/openbravo/reports/productscatalog.bs");
        m_oldclasses.put("com.openbravo.pos.panels.JPanelTax", "com.openbravo.pos.inventory.TaxPanel");
    }

    @Override
    public void waitCursorBegin() {
        this.setCursor(Cursor.getPredefinedCursor(3));
    }

    @Override
    public void waitCursorEnd() {
        this.setCursor(Cursor.getPredefinedCursor(0));
    }

    @Override
    public AppUserView getAppUserView() {
        return this.m_principalapp;
    }

    private void printerStart() {
        String sresource = this.m_dlSystem.getResourceAsXML("Printer.Start");
        if (sresource == null) {
            this.m_TP.getDeviceDisplay().writeVisor(AppLocal.APP_NAME, AppLocal.APP_VERSION);
        } else {
            try {
                this.m_TTP.printTicket(sresource);
            }
            catch (TicketPrinterException eTP) {
                this.m_TP.getDeviceDisplay().writeVisor(AppLocal.APP_NAME, AppLocal.APP_VERSION);
            }
        }
    }

    private void listPeople() {
        try {
            this.jScrollPane1.getViewport().setView(null);
            JFlowPanel jPeople = new JFlowPanel();
            jPeople.applyComponentOrientation(this.getComponentOrientation());
            List<AppUser> people = this.m_dlSystem.listPeopleVisible();
            for (AppUser user : people) {
                JButton btn = new JButton(new AppUserAction(user));
                btn.applyComponentOrientation(this.getComponentOrientation());
                btn.setFocusPainted(false);
                btn.setFocusable(false);
                btn.setRequestFocusEnabled(false);
                btn.setMaximumSize(new Dimension(160, 80));
                btn.setPreferredSize(new Dimension(160, 80));
                btn.setMinimumSize(new Dimension(160, 80));
                btn.setHorizontalAlignment(0);
                btn.setHorizontalTextPosition(0);
                btn.setVerticalTextPosition(3);
                jPeople.add(btn);
            }
            this.jScrollPane1.getViewport().setView(jPeople);
        }
        catch (BasicException basicException) {
            // empty catch block
        }
    }

    private void showView(String view) {
        CardLayout cl = (CardLayout)this.m_jPanelContainer.getLayout();
        cl.show(this.m_jPanelContainer, view);
    }

    private void openAppView(AppUser user) {
        if (this.closeAppView()) {
            try {
                com.openbravo.pos.forms.DataLogicSales dlSales = (com.openbravo.pos.forms.DataLogicSales) this.getBean("com.openbravo.pos.forms.DataLogicSales");
                java.util.List<com.openbravo.pos.inventory.InventoryTaskInfo> pendingTasks = dlSales.getPendingInventoryTasks(user.getId(), this.m_sInventoryLocation);
                if (pendingTasks != null && !pendingTasks.isEmpty()) {
                    boolean success = com.openbravo.pos.inventory.JDialogBlindInventory.showMessage(this, dlSales, pendingTasks.get(0), user);
                    if (!success) {
                        return;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            this.m_principalapp = new JPrincipalApp(this, user);
            this.jPanel3.add(this.m_principalapp.getNotificator());
            this.jPanel3.revalidate();
            this.m_jPanelContainer.add((Component)this.m_principalapp, "_" + this.m_principalapp.getUser().getId());
            this.showView("_" + this.m_principalapp.getUser().getId());
            this.m_principalapp.activate();
        }
    }

    public void exitToLogin() {
        this.closeAppView();
        this.showLogin();
    }

    public boolean closeAppView() {
        if (this.m_principalapp == null) {
            return true;
        }
        if (!this.m_principalapp.deactivate()) {
            return false;
        }
        this.jPanel3.remove(this.m_principalapp.getNotificator());
        this.jPanel3.revalidate();
        this.jPanel3.repaint();
        this.m_jPanelContainer.remove(this.m_principalapp);
        this.m_principalapp = null;
        this.showLogin();
        return true;
    }

    private void showLogin() {
        this.listPeople();
        this.showView("login");
        this.printerStart();
        this.inputtext = new StringBuilder();
        this.m_txtKeys.setText(null);
        EventQueue.invokeLater(new Runnable(){

            @Override
            public void run() {
                JRootApp.this.m_txtKeys.requestFocus();
            }
        });
    }

    private void processKey(char c) {
        if (c == '\n' || c == '?') {
            AppUser user = null;
            try {
                user = this.m_dlSystem.findPeopleByCard(this.inputtext.toString());
            }
            catch (BasicException basicException) {
                // empty catch block
            }
            if (user == null) {
                MessageInf msg = new MessageInf(-33554432, AppLocal.getIntString("message.nocard"));
                msg.show(this);
            } else {
                this.openAppView(user);
            }
            this.inputtext = new StringBuilder();
        } else {
            this.inputtext.append(c);
        }
    }

    private int getProgressBar() {
        int rate = this.serverMonitor.getValue();
        return rate;
    }

    private boolean pingServer() throws UnknownHostException {
        this.serverMonitor.setString("Checking...");
        InetAddress addr = InetAddress.getByName(AppLocal.getIntString("db.ip"));
        int port = 3306;
        InetSocketAddress sockaddr = new InetSocketAddress(addr, port);
        Socket sock = new Socket();
        try {
            sock.connect(sockaddr, 2000);
            this.serverMonitor.setString("Server is alive!");
            this.serverMonitor.setValue(0);
            return true;
        }
        catch (IOException ex) {
            Logger.getLogger(JRootApp.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    private ImageIcon safeIcon(String path) {
        java.net.URL imgUrl = this.getClass().getResource(path);
        if (imgUrl != null) {
            return new ImageIcon(imgUrl);
        } else {
            System.err.println("WARNING: Image resource not found: " + path);
            return null;
        }
    }

    private void initComponents() {
        this.m_jPanelTitle = new JPanel();
        this.m_jLblTitle = new JLabel();
        this.poweredby = new JLabel();
        this.jLabel2 = new JLabel();
        this.m_jPanelContainer = new JPanel();
        this.m_jPanelLogin = new JPanel();
        this.jPanel4 = new JPanel();
        this.jLabel1 = new JLabel();
        this.filler2 = new Box.Filler(new Dimension(0, 0), new Dimension(0, 10), new Dimension(Short.MAX_VALUE, 0));
        this.jPanel5 = new JPanel();
        this.m_jLogonName = new JPanel();
        this.jPanel2 = new JPanel();
        this.jPanel8 = new JPanel();
        this.jScrollPane1 = new JScrollPane();
        this.jPanel1 = new JPanel();
        this.m_txtKeys = new JTextField();
        this.m_jClose = new JButton();
        this.m_jPanelDown = new JPanel();
        this.panelTask = new JPanel();
        this.m_jHost = new JLabel();
        this.serverMonitor = new JProgressBar();
        this.jPanel3 = new JPanel();
        this.setEnabled(false);
        this.setPreferredSize(new Dimension(1024, 768));
        this.setLayout(new BorderLayout());
        this.m_jPanelTitle.setBorder(null);
        this.m_jPanelTitle.setPreferredSize(new Dimension(449, 45));
        this.m_jPanelTitle.setLayout(new BorderLayout());
        this.m_jLblTitle.setFont(new Font("Segoe UI", 1, 18));
        this.m_jLblTitle.setHorizontalAlignment(0);
        this.m_jLblTitle.setText("Window.Title");
        this.m_jPanelTitle.add((Component)this.m_jLblTitle, "Center");
        this.poweredby.setHorizontalAlignment(4);
        this.poweredby.setText("Soltec by Andres Beltran");
        this.poweredby.setFont(new Font("Segoe UI", 0, 12));
        this.poweredby.setForeground(new Color(0, 51, 102));
        this.poweredby.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 10));
        this.poweredby.setHorizontalTextPosition(4);
        this.poweredby.setMaximumSize(new Dimension(250, 34));
        this.poweredby.setPreferredSize(new Dimension(250, 34));
        this.m_jPanelTitle.add((Component)this.poweredby, "After");
        this.jLabel2.setFont(new Font("Segoe UI", 1, 16));
        this.jLabel2.setForeground(new Color(102, 102, 102));
        this.jLabel2.setPreferredSize(new Dimension(180, 34));
        this.m_jPanelTitle.add((Component)this.jLabel2, "Before");
        this.add((Component)this.m_jPanelTitle, "North");
        this.m_jPanelContainer.setLayout(new CardLayout());
        this.m_jPanelLogin.setLayout(new GridBagLayout());
        this.jPanel4.setLayout(new BorderLayout());
        this.jLabel1.setFont(new Font("Segoe UI", 0, 14));
        this.jLabel1.setHorizontalAlignment(0);
        this.jLabel1.setVerticalAlignment(0);
        
        this.jLabel1.setIcon(safeIcon("/com/openbravo/images/logo.png"));
        
        final ImageIcon originalIcon = (ImageIcon) this.jLabel1.getIcon();
        
        this.jPanel4.addComponentListener(new ComponentAdapter(){

            @Override
            public void componentResized(ComponentEvent e) {
                if (originalIcon == null || originalIcon.getImage() == null) {
                    return;
                }
                int width = JRootApp.this.jPanel4.getWidth();
                int height = JRootApp.this.jPanel4.getHeight();
                if (width > 0 && height > 0) {
                    Image img = originalIcon.getImage();
                    int imgW = img.getWidth(null);
                    int imgH = img.getHeight(null);
                    if (imgW <= 0 || imgH <= 0) {
                        return;
                    }
                    double scaleX = (double)width / (double)imgW;
                    double scaleY = (double)height / (double)imgH;
                    double scale = Math.min(scaleX, scaleY) * 0.8;
                    if (scale > 0.0) {
                        Image newImg = img.getScaledInstance((int)((double)imgW * scale), (int)((double)imgH * scale), 4);
                        JRootApp.this.jLabel1.setIcon(new ImageIcon(newImg));
                    }
                }
            }
        });
        this.jLabel1.setText("<html><center><b><font size='5' color='#004488'>SOLTEC POS</font></b><br><font size='3'>Soluciones Tecnol\u00f3gicas</font><br><br><b>Sistema de Facturaci\u00f3n e Inventario</b><br>Facturaci\u00f3n Electr\u00f3nica DIAN<br>Control Total de Inventarios y Bodegas<br>Gesti\u00f3n de Mesas y Domicilios<br>Reportes Financieros Detallados<br><br><br>Software Licenciado por: <b>SOLTEC</b><br></center></html>");
        this.jPanel4.add((Component)this.jLabel1, "Center");
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = 1;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new Insets(50, 50, 50, 50);
        this.m_jPanelLogin.add((Component)this.jPanel4, gridBagConstraints);
        this.jPanel5.setPreferredSize(new Dimension(400, 500));
        this.jPanel5.setLayout(new BorderLayout());
        this.jScrollPane1.setBorder(null);
        this.jScrollPane1.setHorizontalScrollBarPolicy(31);
        this.jPanel5.add((Component)this.jScrollPane1, "Center");
        this.m_txtKeys.setPreferredSize(new Dimension(0, 0));
        this.m_txtKeys.addKeyListener(new KeyAdapter(){

            @Override
            public void keyTyped(KeyEvent evt) {
                JRootApp.this.m_txtKeysKeyTyped(evt);
            }
        });
        this.m_jClose.setFont(new Font("Segoe UI", 1, 14));
        this.m_jClose.setIcon(safeIcon("/com/openbravo/images/exit.png"));
        this.m_jClose.setText(AppLocal.getIntString("button.close"));
        this.m_jClose.setFocusPainted(false);
        this.m_jClose.setFocusable(false);
        this.m_jClose.setPreferredSize(new Dimension(100, 50));
        this.m_jClose.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JRootApp.this.m_jCloseActionPerformed(evt);
            }
        });
        JLabel lblSupport = new JLabel();
        lblSupport.setText("<html><u><font color='blue' size='4'>Haga Clic Aqu\u00ed para Soporte Remoto</font></u></html>");
        lblSupport.setHorizontalAlignment(0);
        lblSupport.setCursor(new Cursor(12));
        lblSupport.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        lblSupport.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent evt) {
                JRootApp.this.launchRemoteSupport();
            }
        });
        this.jPanel1.setLayout(new BorderLayout());
        this.jPanel1.add((Component)this.m_txtKeys, "North");
        JPanel panelBtnContainer = new JPanel(new GridLayout(2, 1));
        panelBtnContainer.add(lblSupport);
        panelBtnContainer.add(this.m_jClose);
        this.jPanel1.add((Component)panelBtnContainer, "Center");
        this.jPanel5.add((Component)this.jPanel1, "South");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = 1;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new Insets(50, 50, 50, 50);
        this.m_jPanelLogin.add((Component)this.jPanel5, gridBagConstraints);
        this.m_jPanelContainer.add((Component)this.m_jPanelLogin, "login");
        this.add((Component)this.m_jPanelContainer, "Center");
        this.m_jPanelDown.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        this.m_jPanelDown.setLayout(new BorderLayout());
        this.m_jHost.setFont(new Font("Segoe UI", 0, 12));
        this.m_jHost.setIcon(safeIcon("/com/openbravo/images/display.png"));

        this.m_jHost.setText("*Hostname");
        this.panelTask.add(this.m_jHost);
        this.serverMonitor.setToolTipText("");
        this.serverMonitor.setFont(new Font("Segoe UI", 0, 12));
        this.serverMonitor.setMaximumSize(new Dimension(50, 18));
        this.serverMonitor.setPreferredSize(new Dimension(150, 30));
        this.serverMonitor.setString("Keep Alive");
        this.serverMonitor.setStringPainted(true);
        this.panelTask.add(this.serverMonitor);
        this.m_jPanelDown.add((Component)this.panelTask, "Before");
        this.m_jPanelDown.add((Component)this.jPanel3, "After");
        this.add((Component)this.m_jPanelDown, "South");
    }

    private void m_txtKeysKeyTyped(KeyEvent evt) {
        this.m_txtKeys.setText("0");
        this.processKey(evt.getKeyChar());
    }

    private void m_jCloseActionPerformed(ActionEvent evt) {
        this.tryToClose();
    }

    private void launchRemoteSupport() {
        try {
            File anydesk = new File("C:\\Program Files (x86)\\AnyDesk\\AnyDesk.exe");
            if (!anydesk.exists()) {
                anydesk = new File("C:\\Program Files\\AnyDesk\\AnyDesk.exe");
            }
            if (anydesk.exists()) {
                Runtime.getRuntime().exec(anydesk.getAbsolutePath());
                return;
            }
            
             try {
                Runtime.getRuntime().exec("cmd /c start AnyDesk");
                return;
            } catch (Exception e) {
                // Continue to RustDesk
            }

            File rustdesk = new File("C:\\Program Files\\RustDesk\\rustdesk.exe");
            if (!rustdesk.exists()) {
                String userHome = System.getProperty("user.home");
                rustdesk = new File(userHome + "\\Desktop\\rustdesk.exe");
            }
            if (!rustdesk.exists()) {
                 String programData = System.getenv("ProgramData");
                 if (programData != null) {
                    rustdesk = new File(programData + "\\RustDesk\\rustdesk.exe");
                 }
            }
            
            if (rustdesk.exists()) {
                Runtime.getRuntime().exec(rustdesk.getAbsolutePath());
                return;
            }
            
             try {
                Runtime.getRuntime().exec("cmd /c start rustdesk");
                return;
            } catch (Exception e) {
                 // Final failure
            }
            
             JOptionPane.showMessageDialog(this, "No se encontr\u00c3\u00b3 AnyDesk ni RustDesk instalado.\nPor favor, contacte soporte al: SOPORTE_SOLTEC", "Soporte Remoto", 1);

        } catch (Exception e) {
             JOptionPane.showMessageDialog(this, "Error al iniciar soporte remoto: " + e.getMessage(), "Error", 0);
        }
    }

    static {
        JRootApp.initOldClasses();
    }

    private class PrintTimeAction
    implements ActionListener {
        private PrintTimeAction() {
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            JRootApp.this.m_clock = JRootApp.this.getLineTimer();
            JRootApp.this.m_date = JRootApp.this.getLineDate();
            DateTime m_datetime = JRootApp.this.getDateTime();
            JRootApp.this.m_jLblTitle.setText("SOLTEC - SOLUCIONES TECNOLOGICAS CO");
            JRootApp.this.m_jLblTitle.repaint();
            JRootApp.this.jLabel2.setText("  " + JRootApp.this.m_date + " " + JRootApp.this.m_clock);
        }
    }

    class AppUserAction
    extends AbstractAction {
        private final AppUser m_actionuser;

        public AppUserAction(AppUser user) {
            this.m_actionuser = user;
            this.putValue("SmallIcon", this.m_actionuser.getIcon());
            this.putValue("Name", this.m_actionuser.getName());
        }

        public AppUser getUser() {
            return this.m_actionuser;
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            if (this.m_actionuser.authenticate()) {
                JRootApp.this.openAppView(this.m_actionuser);
            } else {
                String sPassword = JPasswordDialog.showEditPassword(JRootApp.this, AppLocal.getIntString("label.Password"), this.m_actionuser.getName(), this.m_actionuser.getIcon());
                if (sPassword != null) {
                    if (this.m_actionuser.authenticate(sPassword)) {
                        JRootApp.this.openAppView(this.m_actionuser);
                    } else {
                        MessageInf msg = new MessageInf(-33554432, AppLocal.getIntString("message.BadPassword"));
                        msg.show(JRootApp.this);
                    }
                }
            }
        }
    }
}

