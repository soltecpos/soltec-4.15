/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.sales.restaurant;

import com.openbravo.basic.BasicException;
import com.openbravo.data.gui.MessageInf;
import com.openbravo.data.gui.NullIcon;
import com.openbravo.data.loader.SerializerReadClass;
import com.openbravo.data.loader.StaticSentence;
import com.openbravo.pos.admin.DataLogicAdmin;
import com.openbravo.pos.admin.PeopleInfo;
import com.openbravo.pos.customers.CustomerInfo;
import com.openbravo.pos.forms.AppConfig;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.forms.DataLogicSales;
import com.openbravo.pos.inventory.AuditLogger;
import com.openbravo.pos.sales.DataLogicReceipts;
import com.openbravo.pos.sales.JTicketsBag;
import com.openbravo.pos.sales.SharedTicketInfo;
import com.openbravo.pos.sales.TicketsEditor;
import com.openbravo.pos.sales.restaurant.Floor;
import com.openbravo.pos.sales.restaurant.JTicketsBagRestaurant;
import com.openbravo.pos.sales.restaurant.JTicketsBagRestaurantRes;
import com.openbravo.pos.sales.restaurant.Place;
import com.openbravo.pos.sales.restaurant.RestaurantDBUtils;
import com.openbravo.pos.ticket.TicketInfo;
import com.openbravo.pos.ticket.TicketLineInfo;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.EventListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JToggleButton;
import javax.swing.Timer;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class JTicketsBagRestaurantMap
extends JTicketsBag {
    private List<Place> m_aplaces;
    private List<Floor> m_afloors;
    private JTicketsBagRestaurant m_restaurantmap;
    private final JTicketsBagRestaurantRes m_jreservations;
    private Place m_PlaceCurrent;
    private Map<String, SharedTicketInfo> m_ticketInfoMap;
    private Map<String, String> m_userMap;
    private ServerCurrent m_ServerCurrent;
    private Place m_PlaceClipboard;
    private CustomerInfo customer;
    private DataLogicReceipts dlReceipts = null;
    private DataLogicSales dlSales = null;
    private DataLogicAdmin dlAdmin = null;
    private final RestaurantDBUtils restDB;
    private static final Icon ICO_OCU_SM = new ImageIcon(Place.class.getResource("/com/openbravo/images/edit_group_sm.png"));
    private static final Icon ICO_WAITER = new NullIcon(1, 1);
    private static final Icon ICO_FRE = new NullIcon(22, 22);
    private String waiterDetails;
    private String customerDetails;
    private String tableName;
    private Boolean transBtns;
    private JToggleButton m_jbtnEdit;
    private boolean m_bEditMode = false;
    private Point initialClick;
    private final int SNAP_THRESHOLD = 50;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JPanel m_jPanelMap;
    private JLabel m_jText;
    private JButton m_jbtnRefresh;
    private JButton m_jbtnReservations;
    private JButton m_jbtnAddTables;
    private JLabel webLblautoRefresh;

    public JTicketsBagRestaurantMap(AppView app, TicketsEditor panelticket) {
        super(app, panelticket);
        StaticSentence sent;
        this.restDB = new RestaurantDBUtils(app);
        this.transBtns = AppConfig.getInstance().getBoolean("table.transbtn");
        this.dlReceipts = (DataLogicReceipts)app.getBean("com.openbravo.pos.sales.DataLogicReceipts");
        this.dlSales = (DataLogicSales)this.m_App.getBean("com.openbravo.pos.forms.DataLogicSales");
        this.dlAdmin = (DataLogicAdmin)this.m_App.getBean("com.openbravo.pos.admin.DataLogicAdmin");
        this.m_userMap = new HashMap<String, String>();
        try {
            List<PeopleInfo> people = this.dlAdmin.getPeopleList().list();
            for (PeopleInfo p : people) {
                this.m_userMap.put(p.getID(), p.getName());
            }
        }
        catch (BasicException ex) {
            new MessageInf(ex).show(this);
        }
        this.m_restaurantmap = new JTicketsBagRestaurant(app, this);
        this.m_PlaceCurrent = null;
        this.m_PlaceClipboard = null;
        this.customer = null;
        try {
            sent = new StaticSentence(app.getSession(), "SELECT ID, NAME, IMAGE FROM floors ORDER BY NAME", null, new SerializerReadClass<Floor>(Floor.class));
            this.m_afloors = sent.list();
        }
        catch (BasicException eD) {
            this.m_afloors = new ArrayList<Floor>();
        }
        try {
            sent = new StaticSentence(app.getSession(), "SELECT ID, NAME, X, Y, FLOOR, CUSTOMER, WAITER, TICKETID, TABLEMOVED FROM places ORDER BY FLOOR", null, new SerializerReadClass<Place>(Place.class));
            this.m_aplaces = sent.list();
        }
        catch (BasicException eD) {
            this.m_aplaces = new ArrayList<Place>();
        }
        this.initComponents();
        if (this.m_afloors.size() > 1) {
            JTabbedPane jTabFloors = new JTabbedPane();
            jTabFloors.applyComponentOrientation(this.getComponentOrientation());
            jTabFloors.setBorder(new EmptyBorder(new Insets(5, 5, 5, 5)));
            jTabFloors.setTabLayoutPolicy(1);
            jTabFloors.setFocusable(false);
            jTabFloors.setRequestFocusEnabled(false);
            this.m_jPanelMap.add((Component)jTabFloors, "Center");
            this.m_afloors.stream().map(f -> {
                f.getContainer().applyComponentOrientation(this.getComponentOrientation());
                return f;
            }).forEach(f -> {
                JScrollPane jScrCont = new JScrollPane();
                jScrCont.applyComponentOrientation(this.getComponentOrientation());
                JPanel jPanCont = new JPanel();
                jPanCont.applyComponentOrientation(this.getComponentOrientation());
                jTabFloors.addTab(f.getName(), f.getIcon(), jScrCont);
                jScrCont.setViewportView(jPanCont);
                jPanCont.add(f.getContainer());
            });
        } else if (this.m_afloors.size() == 1) {
            Floor f2 = this.m_afloors.get(0);
            f2.getContainer().applyComponentOrientation(this.getComponentOrientation());
            JPanel jPlaces = new JPanel();
            jPlaces.applyComponentOrientation(this.getComponentOrientation());
            jPlaces.setLayout(new BorderLayout());
            jPlaces.setBorder(new CompoundBorder(new EmptyBorder(new Insets(5, 5, 5, 5)), new TitledBorder(f2.getName())));
            JScrollPane jScrCont = new JScrollPane();
            jScrCont.applyComponentOrientation(this.getComponentOrientation());
            JPanel jPanCont = new JPanel();
            jPanCont.applyComponentOrientation(this.getComponentOrientation());
            this.m_jPanelMap.add((Component)jPlaces, "Center");
            jPlaces.add((Component)jScrCont, "Center");
            jScrCont.setViewportView(jPanCont);
            jPanCont.add(f2.getContainer());
        }
        Floor currfloor = null;
        for (Place pl : this.m_aplaces) {
            int iFloor = 0;
            if (currfloor == null || !currfloor.getID().equals(pl.getFloor())) {
                while (!(currfloor = this.m_afloors.get(iFloor++)).getID().equals(pl.getFloor())) {
                }
            }
            currfloor.getContainer().add(pl.getButton());
            pl.setButtonBounds();
            if (this.transBtns.booleanValue()) {
                pl.getButton().setOpaque(false);
                pl.getButton().setContentAreaFilled(false);
                pl.getButton().setBorderPainted(false);
            }
            pl.getButton().addActionListener(new MyActionListener(pl));
        }
        this.m_jreservations = new JTicketsBagRestaurantRes(app, this);
        this.add((Component)this.m_jreservations, "res");
        if (this.m_App.getProperties().getProperty("till.autoRefreshTableMap").equals("true")) {
            this.webLblautoRefresh.setText(ResourceBundle.getBundle("pos_messages").getString("label.autoRefreshTableMapTimerON"));
            Timer autoRefreshTimer = new Timer(Integer.parseInt(this.m_App.getProperties().getProperty("till.autoRefreshTimer")) * 1000, new tableMapRefresh());
            autoRefreshTimer.start();
            try {
                Thread.sleep(1000L);
            }
            catch (InterruptedException e) {
                autoRefreshTimer.stop();
            }
        } else {
            this.webLblautoRefresh.setText(ResourceBundle.getBundle("pos_messages").getString("label.autoRefreshTableMapTimerOFF"));
        }
        this.m_jbtnEdit.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                JTicketsBagRestaurantMap.this.m_jbtnEditActionPerformed(e);
            }
        });
        Timer timeUpdater = new Timer(60000, new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                if (JTicketsBagRestaurantMap.this.isShowing()) {
                    JTicketsBagRestaurantMap.this.printState();
                }
            }
        });
        timeUpdater.start();
    }

    private void m_jbtnEditActionPerformed(ActionEvent evt) {
        this.setEditMode(this.m_jbtnEdit.isSelected());
    }

    private void setEditMode(boolean enable) {
        this.m_bEditMode = enable;
        for (Place pl : this.m_aplaces) {
            JButton btn = pl.getButton();
            for (ActionListener actionListener : btn.getActionListeners()) {
                if (!(actionListener instanceof MyActionListener)) continue;
                btn.removeActionListener(actionListener);
            }
            for (EventListener eventListener : btn.getMouseListeners()) {
                if (!(eventListener instanceof PlaceMouseAdapter)) continue;
                btn.removeMouseListener((MouseListener)eventListener);
            }
            for (EventListener eventListener : btn.getMouseMotionListeners()) {
                if (!(eventListener instanceof PlaceMouseAdapter)) continue;
                btn.removeMouseMotionListener((MouseMotionListener)eventListener);
            }
            if (this.m_bEditMode) {
                PlaceMouseAdapter adapter = new PlaceMouseAdapter(pl);
                btn.addMouseListener(adapter);
                btn.addMouseMotionListener(adapter);
                btn.setCursor(Cursor.getPredefinedCursor(13));
                continue;
            }
            btn.addActionListener(new MyActionListener(pl));
            btn.setCursor(Cursor.getDefaultCursor());
            try {
                this.dlSales.updatePlaceCoordinates(pl.getId(), pl.getX(), pl.getY());
            }
            catch (BasicException ex) {
                new MessageInf(ex).show(this);
            }
        }
        if (!this.m_bEditMode) {
            JOptionPane.showMessageDialog(null, AppLocal.getIntString("message.positionssaved"));
        }
    }

    @Override
    public void activate() {
        this.m_PlaceClipboard = null;
        this.customer = null;
        this.loadTickets();
        this.printState();
        this.m_panelticket.setActiveTicket(null, null);
        this.m_restaurantmap.activate();
        this.showView("map");
    }

    @Override
    public boolean deactivate() {
        if (this.viewTables()) {
            this.m_PlaceClipboard = null;
            this.customer = null;
            if (this.m_PlaceCurrent != null) {
                try {
                    String remoteUser = this.dlReceipts.getUserId(this.m_PlaceCurrent.getId());
                    String currentUser = this.m_App.getAppUserView().getUser().getId();
                    if (this.m_panelticket.getActiveTicket().getLinesCount() == 0) {
                        if (remoteUser != null && !remoteUser.equals(currentUser)) {
                            AuditLogger.logEvent(this.m_App.getSession(), "SafeSave-Deactivate", this.m_App.getAppUserView().getUser().getName(), null, null, "RemoteUser=" + remoteUser, "LocalLines=0", "Mesa: " + this.m_PlaceCurrent.getName() + " - Prevented empty overwrite on deactivate");
                            this.dlReceipts.unlockSharedTicket(this.m_PlaceCurrent.getId(), null);
                        } else {
                            AuditLogger.logEvent(this.m_App.getSession(), "AutoClean-Deactivate", this.m_App.getAppUserView().getUser().getName(), null, null, null, "LocalLines=0", "Mesa: " + this.m_PlaceCurrent.getName() + " - Deleted empty ticket on deactivate");
                            this.dlReceipts.deleteSharedTicket(this.m_PlaceCurrent.getId());
                            this.m_PlaceCurrent.setPeople(false);
                        }
                    } else if (remoteUser == null || remoteUser.equals(currentUser)) {
                        this.dlReceipts.updateSharedTicket(this.m_PlaceCurrent.getId(), this.m_panelticket.getActiveTicket(), this.m_panelticket.getActiveTicket().getPickupId());
                        this.dlReceipts.unlockSharedTicket(this.m_PlaceCurrent.getId(), null);
                    } else {
                        AuditLogger.logEvent(this.m_App.getSession(), "BlockOverWrite-Deactivate", this.m_App.getAppUserView().getUser().getName(), null, null, "RemoteUser=" + remoteUser, "LocalLines=" + this.m_panelticket.getActiveTicket().getLinesCount(), "Mesa: " + this.m_PlaceCurrent.getName() + " - Prevented overwrite by non-owner on deactivate");
                    }
                }
                catch (BasicException e) {
                    new MessageInf(e).show(this);
                }
                this.m_PlaceCurrent = null;
            }
            this.printState();
            this.m_panelticket.setActiveTicket(null, null);
            return true;
        }
        return false;
    }

    public void reloadActiveTicket() {
        if (this.m_panelticket != null && this.m_PlaceCurrent != null) {
            this.m_panelticket.setActiveTicket(this.m_panelticket.getActiveTicket(), this.m_PlaceCurrent.getName());
        }
    }

    @Override
    protected JComponent getBagComponent() {
        return this.m_restaurantmap;
    }

    @Override
    protected JComponent getNullComponent() {
        return this;
    }

    public TicketInfo getActiveTicket() {
        return this.m_panelticket.getActiveTicket();
    }

    public void moveTicket() {
        if (this.m_PlaceCurrent != null) {
            try {
                // CRITICAL FIX: Update properties for the current ticket before moving/saving
                if (this.m_panelticket.getActiveTicket() != null) {
                     this.m_panelticket.getActiveTicket().setProperty("table.id", this.m_PlaceCurrent.getId());
                }
                this.dlReceipts.updateRSharedTicket(this.m_PlaceCurrent.getId(), this.m_panelticket.getActiveTicket(), this.m_panelticket.getActiveTicket().getPickupId());
            }
            catch (BasicException e) {
                new MessageInf(e).show(this);
            }
            this.m_PlaceClipboard = this.m_PlaceCurrent;
            this.customer = null;
            this.m_PlaceCurrent = null;
        }
        this.printState();
        this.m_panelticket.setActiveTicket(null, null);
    }

    public boolean viewTables(CustomerInfo c) {
        if (this.m_jreservations.deactivate()) {
            this.showView("map");
            this.m_PlaceClipboard = null;
            this.customer = c;
            this.printState();
            return true;
        }
        return false;
    }

    public boolean viewTables() {
        return this.viewTables(null);
    }

    public void newTicket() {
        if (this.m_PlaceCurrent != null) {
            try {
                String m_lockState = this.dlReceipts.getLockState(this.m_PlaceCurrent.getId(), null);
                String remoteUser = this.dlReceipts.getUserId(this.m_PlaceCurrent.getId());
                String currentUser = this.m_App.getAppUserView().getUser().getId();
                if (remoteUser == null || remoteUser.equals(currentUser) || "override".equals(m_lockState) || "locked".equals(m_lockState)) {
                    if (this.m_panelticket.getActiveTicket().getLinesCount() == 0) {
                        if (remoteUser != null && !remoteUser.equals(currentUser)) {
                            AuditLogger.logEvent(this.m_App.getSession(), "SafeSave-NewTicket", this.m_App.getAppUserView().getUser().getName(), null, null, "RemoteUser=" + remoteUser, "LocalLines=0", "Mesa: " + this.m_PlaceCurrent.getName() + " - Prevented empty overwrite on newTicket");
                            this.dlReceipts.unlockSharedTicket(this.m_PlaceCurrent.getId(), null);
                        } else {
                            AuditLogger.logEvent(this.m_App.getSession(), "AutoClean-NewTicket", this.m_App.getAppUserView().getUser().getName(), null, null, null, "LocalLines=0", "Mesa: " + this.m_PlaceCurrent.getName() + " - Deleted empty ticket on newTicket");
                            this.dlReceipts.deleteSharedTicket(this.m_PlaceCurrent.getId());
                            this.m_PlaceCurrent.setPeople(false);
                        }
                    } else {
                        // CRITICAL FIX: Ensure property is set before update
                        if (this.m_panelticket.getActiveTicket() != null) {
                             this.m_panelticket.getActiveTicket().setProperty("table.id", this.m_PlaceCurrent.getId());
                        }
                        this.dlReceipts.updateSharedTicket(this.m_PlaceCurrent.getId(), this.m_panelticket.getActiveTicket(), this.m_panelticket.getActiveTicket().getPickupId());
                        this.dlReceipts.unlockSharedTicket(this.m_PlaceCurrent.getId(), null);
                    }
                    this.m_PlaceCurrent = null;
                } else {
                    JOptionPane.showMessageDialog(null, AppLocal.getIntString("message.sharedticketlockoverriden"), AppLocal.getIntString("title.editor"), 1);
                }
            }
            catch (BasicException ex) {
                Logger.getLogger(JTicketsBagRestaurantMap.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.printState();
        this.m_panelticket.setActiveTicket(null, null);
    }

    public String getTable() {
        String id = null;
        if (this.m_PlaceCurrent != null) {
            id = this.m_PlaceCurrent.getId();
        }
        return id;
    }

    public String getTableName() {
        String stableName = null;
        if (this.m_PlaceCurrent != null) {
            stableName = this.m_PlaceCurrent.getName();
        }
        return stableName;
    }

    @Override
    public void deleteTicket() {
        if (this.m_PlaceCurrent != null) {
            String id = this.m_PlaceCurrent.getId();
            try {
                this.dlReceipts.deleteSharedTicket(id);
            }
            catch (BasicException e) {
                new MessageInf(e).show(this);
            }
            this.m_PlaceCurrent.setPeople(false);
            this.m_PlaceCurrent = null;
        }
        this.printState();
        this.m_panelticket.setActiveTicket(null, null);
    }

    public void changeServer() {
        if (this.m_ServerCurrent != null) {
            // empty if block
        }
    }

    public void loadTickets() {
        try {
            this.dlReceipts.clearStaleLocks(600000L);
        }
        catch (BasicException e) {
            Logger.getLogger(JTicketsBagRestaurantMap.class.getName()).log(Level.WARNING, "Error clearing stale locks", e);
        }
        HashSet atickets = new HashSet();
        this.m_ticketInfoMap = new HashMap<String, SharedTicketInfo>();
        try {
            List<SharedTicketInfo> l = this.dlReceipts.getSharedTicketList();
            l.stream().forEach(ticket -> {
                atickets.add(ticket.getId());
                this.m_ticketInfoMap.put(ticket.getId(), (SharedTicketInfo)ticket);
            });
        }
        catch (BasicException e) {
            new MessageInf(e).show(this);
        }
        this.m_aplaces.stream().forEach(table -> table.setPeople(atickets.contains(table.getId())));
    }

    private void printState() {
        String sDB = this.m_App.getProperties().getProperty("db.engine");
        if (this.m_PlaceClipboard == null) {
            if (this.customer == null) {
                this.m_jText.setText(null);
                this.m_aplaces.stream().map(place -> {
                    place.getButton().setEnabled(true);
                    return place;
                }).map(place -> {
                    this.tableName = this.m_App.getProperties().getProperty("table.tablecolour") == null ? "<style=font-size:9px;font-weight:bold;><font color = black>" + place.getName() + "</font></style>" : "<style=font-size:9px;font-weight:bold;><font color =" + this.m_App.getProperties().getProperty("table.tablecolour") + ">" + place.getName() + "</font></style>";
                    return place;
                }).map(place -> {
                    if (Boolean.parseBoolean(this.m_App.getProperties().getProperty("table.showwaiterdetails"))) {
                        this.waiterDetails = this.m_App.getProperties().getProperty("table.waitercolour") == null ? (this.restDB.getWaiterNameInTable(place.getName()) == null ? "" : "<style=font-size:9px;font-weight:bold;><font color = red>" + this.restDB.getWaiterNameInTableById(place.getId()) + "</font></style><br>") : (this.restDB.getWaiterNameInTable(place.getName()) == null ? "" : "<style=font-size:9px;font-weight:bold;><font color =" + this.m_App.getProperties().getProperty("table.waitercolour") + ">" + this.restDB.getWaiterNameInTableById(place.getId()) + "</font></style><br>");
                        place.getButton().setIcon(ICO_OCU_SM);
                    } else {
                        this.waiterDetails = "";
                    }
                    return place;
                }).map(place -> {
                    if (Boolean.parseBoolean(this.m_App.getProperties().getProperty("table.showcustomerdetails"))) {
                        place.getButton().setIcon(Boolean.parseBoolean(this.m_App.getProperties().getProperty("table.showwaiterdetails")) && this.restDB.getCustomerNameInTable(place.getName()) != null ? ICO_WAITER : ICO_OCU_SM);
                        this.customerDetails = this.m_App.getProperties().getProperty("table.customercolour") == null ? (this.restDB.getCustomerNameInTable(place.getName()) == null ? "" : "<style=font-size:9px;font-weight:bold;><font color = blue>" + this.restDB.getCustomerNameInTableById(place.getId()) + "</font></style><br>") : (this.restDB.getCustomerNameInTable(place.getName()) == null ? "" : "<style=font-size:9px;font-weight:bold;><font color =" + this.m_App.getProperties().getProperty("table.customercolour") + ">" + this.restDB.getCustomerNameInTableById(place.getId()) + "</font></style><br>");
                    } else {
                        this.customerDetails = "";
                    }
                    return place;
                }).map(place -> {
                    if (Boolean.parseBoolean(this.m_App.getProperties().getProperty("table.showwaiterdetails")) || Boolean.parseBoolean(this.m_App.getProperties().getProperty("table.showcustomerdetails"))) {
                        place.getButton().setText("<html><center>" + this.customerDetails + this.waiterDetails + this.tableName + "</html>");
                    } else {
                        this.customerDetails = this.m_App.getProperties().getProperty("table.tablecolour") == null ? "" : "";
                    }
                    String extraInfo = "";
                    if (place.hasPeople() && this.m_ticketInfoMap != null && this.m_ticketInfoMap.containsKey(place.getId())) {
                        SharedTicketInfo info = this.m_ticketInfoMap.get(place.getId());
                        String waiter = info.getWaiter();
                        if (waiter != null) {
                            if (waiter.equals("0") || waiter.startsWith("0")) {
                                waiter = "Administrator";
                            } else if (this.m_userMap != null && this.m_userMap.containsKey(waiter)) {
                                waiter = this.m_userMap.get(waiter);
                            }
                            extraInfo = extraInfo + "<br><font size=\"-2\">Mes: " + waiter + "</font>";
                        }
                        if (info.getDate() != null) {
                            long duration = new Date().getTime() - info.getDate().getTime();
                            long minutes = TimeUnit.MILLISECONDS.toMinutes(duration);
                            long hours = minutes / 60L;
                            extraInfo = extraInfo + String.format("<br><font size=\"-2\">Tm: %02d:%02d</font>", hours, minutes %= 60L);
                        }
                    }
                    place.getButton().setText("<html><center>" + this.customerDetails + this.waiterDetails + this.tableName + extraInfo + "</html>");
                    return place;
                }).map(place -> {
                    if (place.hasPeople()) {
                        SharedTicketInfo info;
                        SharedTicketInfo sharedTicketInfo = info = this.m_ticketInfoMap != null ? this.m_ticketInfoMap.get(place.getId()) : null;
                        if (info != null && info.isLocked()) {
                            place.getButton().setBackground(new Color(220, 53, 69));
                            place.getButton().setForeground(Color.WHITE);
                            place.getButton().setOpaque(true);
                        } else {
                            place.getButton().setBackground(new Color(255, 165, 0));
                            place.getButton().setForeground(Color.BLACK);
                            place.getButton().setOpaque(true);
                        }
                    } else {
                        place.getButton().setBackground(new Color(40, 167, 69));
                        place.getButton().setForeground(Color.WHITE);
                        place.getButton().setOpaque(true);
                    }
                    return place;
                }).filter(place -> !place.hasPeople()).forEach(place -> place.getButton().setIcon(ICO_FRE));
                this.m_jbtnReservations.setEnabled(true);
            } else {
                this.m_jText.setText(AppLocal.getIntString("label.restaurantcustomer", this.customer.getName()));
                this.m_aplaces.stream().forEach(place -> place.getButton().setEnabled(!place.hasPeople()));
                this.m_jbtnReservations.setEnabled(false);
            }
        } else {
            this.m_jText.setText(AppLocal.getIntString("label.restaurantmove", this.m_PlaceClipboard.getName()));
            this.m_aplaces.stream().forEach(place -> place.getButton().setEnabled(true));
            this.m_jbtnReservations.setEnabled(false);
        }
    }

    private TicketInfo getTicketInfo(Place place) {
        try {
            return this.dlReceipts.getSharedTicket(place.getId());
        }
        catch (BasicException e) {
            new MessageInf(e).show(this);
            return null;
        }
    }

    private void setActivePlace(Place place, TicketInfo ticket) {
        this.m_PlaceCurrent = place;
        if (ticket != null) {
            ticket.setProperty("table.id", place.getId());
        }
        this.m_panelticket.setActiveTicket(ticket, this.m_PlaceCurrent.getName());
        try {
            this.dlReceipts.lockSharedTicket(this.m_PlaceCurrent.getId(), "locked");
        }
        catch (BasicException ex) {
            Logger.getLogger(JTicketsBagRestaurantMap.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void showView(String view) {
        CardLayout cl = (CardLayout)this.getLayout();
        cl.show(this, view);
    }

    public void setButtonTextBags(String btnText) {
        this.m_PlaceClipboard.setButtonText(btnText);
    }

    private void initComponents() {
        this.m_jPanelMap = new JPanel();
        this.jPanel1 = new JPanel();
        this.jPanel2 = new JPanel();
        this.m_jbtnReservations = new JButton();
        this.m_jbtnRefresh = new JButton();
        this.m_jbtnEdit = new JToggleButton();
        this.m_jbtnAddTables = new JButton();
        this.m_jText = new JLabel();
        this.webLblautoRefresh = new JLabel();
        this.setLayout(new CardLayout());
        this.m_jPanelMap.setFont(new Font("Arial", 0, 12));
        this.m_jPanelMap.setLayout(new BorderLayout());
        this.jPanel1.setLayout(new BorderLayout());
        this.jPanel2.setLayout(new FlowLayout(0));
        this.m_jbtnReservations.setFont(new Font("Arial", 0, 12));
        this.m_jbtnReservations.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/date.png")));
        this.m_jbtnReservations.setText(AppLocal.getIntString("button.reservations"));
        this.m_jbtnReservations.setToolTipText("Abrir pantalla de Reservas");
        this.m_jbtnReservations.setFocusPainted(false);
        this.m_jbtnReservations.setFocusable(false);
        this.m_jbtnReservations.setMargin(new Insets(8, 14, 8, 14));
        this.m_jbtnReservations.setMaximumSize(new Dimension(133, 40));
        this.m_jbtnReservations.setMinimumSize(new Dimension(133, 40));
        this.m_jbtnReservations.setPreferredSize(new Dimension(133, 45));
        this.m_jbtnReservations.setRequestFocusEnabled(false);
        this.m_jbtnReservations.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JTicketsBagRestaurantMap.this.m_jbtnReservationsActionPerformed(evt);
            }
        });
        this.jPanel2.add(this.m_jbtnReservations);
        this.m_jbtnRefresh.setFont(new Font("Arial", 0, 12));
        this.m_jbtnRefresh.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/reload.png")));
        this.m_jbtnRefresh.setText(AppLocal.getIntString("button.reloadticket"));
        this.m_jbtnRefresh.setToolTipText("Actualizar informaci\u00c3\u00b3n de mesas");
        this.m_jbtnRefresh.setFocusPainted(false);
        this.m_jbtnRefresh.setFocusable(false);
        this.m_jbtnRefresh.setMargin(new Insets(8, 14, 8, 14));
        this.m_jbtnRefresh.setMaximumSize(new Dimension(100, 40));
        this.m_jbtnRefresh.setMinimumSize(new Dimension(100, 40));
        this.m_jbtnRefresh.setPreferredSize(new Dimension(100, 45));
        this.m_jbtnRefresh.setRequestFocusEnabled(false);
        this.m_jbtnRefresh.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JTicketsBagRestaurantMap.this.m_jbtnRefreshActionPerformed(evt);
            }
        });
        this.jPanel2.add(this.m_jbtnRefresh);
        this.m_jbtnEdit.setFont(new Font("Arial", 0, 12));
        this.m_jbtnEdit.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/edit.png")));
        this.m_jbtnEdit.setText("Modo Edici\u00c3\u00b3n");
        this.m_jbtnEdit.setToolTipText("Editar posiciones de mesas");
        this.m_jbtnEdit.setFocusPainted(false);
        this.m_jbtnEdit.setFocusable(false);
        this.m_jbtnEdit.setMargin(new Insets(8, 14, 8, 14));
        this.m_jbtnEdit.setMaximumSize(new Dimension(100, 40));
        this.m_jbtnEdit.setMinimumSize(new Dimension(100, 40));
        this.m_jbtnEdit.setPreferredSize(new Dimension(100, 45));
        this.m_jbtnEdit.setRequestFocusEnabled(false);
        this.m_jbtnEdit.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JTicketsBagRestaurantMap.this.m_jbtnEditActionPerformed(evt);
            }
        });
        this.jPanel2.add(this.m_jbtnEdit);
        this.m_jbtnAddTables.setFont(new Font("Arial", 0, 12));
        this.m_jbtnAddTables.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/editnew.png")));
        this.m_jbtnAddTables.setText("Agregar Mesas");
        this.m_jbtnAddTables.setToolTipText("Agregar M\u00c3\u00baltiples Mesas");
        this.m_jbtnAddTables.setFocusPainted(false);
        this.m_jbtnAddTables.setFocusable(false);
        this.m_jbtnAddTables.setMargin(new Insets(8, 14, 8, 14));
        this.m_jbtnAddTables.setMaximumSize(new Dimension(130, 40));
        this.m_jbtnAddTables.setMinimumSize(new Dimension(130, 40));
        this.m_jbtnAddTables.setPreferredSize(new Dimension(130, 45));
        this.m_jbtnAddTables.setRequestFocusEnabled(false);
        this.m_jbtnAddTables.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JTicketsBagRestaurantMap.this.m_jbtnAddTablesActionPerformed(evt);
            }
        });
        this.jPanel2.add(this.m_jbtnAddTables);
        this.m_jText.setFont(new Font("Arial", 0, 14));
        this.jPanel2.add(this.m_jText);
        this.jPanel1.add((Component)this.jPanel2, "Before");
        this.webLblautoRefresh.setHorizontalAlignment(4);
        ResourceBundle bundle = ResourceBundle.getBundle("pos_messages");
        this.webLblautoRefresh.setText(bundle.getString("label.autoRefreshTableMapTimerON"));
        this.webLblautoRefresh.setFont(new Font("Arial", 0, 14));
        this.jPanel1.add((Component)this.webLblautoRefresh, "Center");
        this.m_jPanelMap.add((Component)this.jPanel1, "North");
        this.add((Component)this.m_jPanelMap, "map");
    }

    private void m_jbtnRefreshActionPerformed(ActionEvent evt) {
        this.m_PlaceClipboard = null;
        this.customer = null;
        this.loadTickets();
        this.printState();
    }

    private void m_jbtnReservationsActionPerformed(ActionEvent evt) {
        this.showView("res");
        this.m_jreservations.activate();
    }

    private void m_jbtnAddTablesActionPerformed(ActionEvent evt) {
        String sTables = JOptionPane.showInputDialog(this, "Cuantas mesas desea agregar?", "Agregar Mesas", 3);
        try {
            if (sTables == null || sTables.isEmpty()) {
                return;
            }
            int count = Integer.parseInt(sTables);
            if (count <= 0) {
                return;
            }
            if (this.m_afloors.isEmpty()) {
                return;
            }
            Floor currentFloor = null;
            if (this.m_afloors.size() > 1) {
                for (Component c : this.m_jPanelMap.getComponents()) {
                    if (!(c instanceof JTabbedPane)) continue;
                    int idx = ((JTabbedPane)c).getSelectedIndex();
                    if (idx < 0 || idx >= this.m_afloors.size()) break;
                    currentFloor = this.m_afloors.get(idx);
                    break;
                }
                if (currentFloor == null && !this.m_afloors.isEmpty()) {
                    currentFloor = this.m_afloors.get(0);
                }
            } else if (this.m_afloors.size() == 1) {
                currentFloor = this.m_afloors.get(0);
            }
            if (currentFloor == null) {
                return;
            }
            int startNum = this.restDB.getNextTableNumber();
            int x = 50;
            int y = 50;
            int tablesCreated = 0;
            for (int i = 0; i < count; ++i) {
                String name = "Mesa " + startNum;
                while (this.restDB.isPlaceNameExists(name)) {
                    name = "Mesa " + ++startNum;
                }
                String id = UUID.randomUUID().toString();
                this.restDB.createPlace(id, name, x, y, currentFloor.getID());
                ++tablesCreated;
                ++startNum;
                if ((x += 100) <= 800) continue;
                x = 50;
                y += 80;
            }
            if (tablesCreated > 0) {
                JOptionPane.showMessageDialog(this, tablesCreated + " mesas agregadas exitosamente.");
                this.m_jbtnRefresh.doClick();
            }
        }
        catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese un n\u00c3\u00bamero v\u00c3\u00a1lido.");
        }
    }

    private class MyActionListener
    implements ActionListener {
        private final Place m_place;

        public MyActionListener(Place place) {
            this.m_place = place;
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            JTicketsBagRestaurantMap.this.m_App.getAppUserView().getUser();
            if (JTicketsBagRestaurantMap.this.m_PlaceClipboard == null) {
                TicketInfo ticket = JTicketsBagRestaurantMap.this.getTicketInfo(this.m_place);
                if (ticket == null) {
                    ticket = new TicketInfo();
                    ticket.setUser(JTicketsBagRestaurantMap.this.m_App.getAppUserView().getUser().getUserInfo());
                    // CRITICAL FIX: Set table.id BEFORE inserting into DB
                    ticket.setProperty("table.id", this.m_place.getId());
                    
                    try {
                        JTicketsBagRestaurantMap.this.dlReceipts.insertSharedTicket(this.m_place.getId(), ticket, ticket.getPickupId());
                        JTicketsBagRestaurantMap.this.restDB.setWaiterNameInTable(JTicketsBagRestaurantMap.this.m_App.getAppUserView().getUser().getName(), this.m_place.getName());
                    }
                    catch (BasicException e) {
                        new MessageInf(e).show(JTicketsBagRestaurantMap.this);
                    }
                    this.m_place.setPeople(true);
                    JTicketsBagRestaurantMap.this.setActivePlace(this.m_place, ticket);
                } else {
                    // CRITICAL FIX: Ensure property is present on loaded tickets too
                    if (ticket != null) {
                        ticket.setProperty("table.id", this.m_place.getId());
                    }
                    
                    String m_lockState = null;
                    String m_user = JTicketsBagRestaurantMap.this.m_App.getAppUserView().getUser().getName();
                    try {
                        m_lockState = JTicketsBagRestaurantMap.this.dlReceipts.getLockState(this.m_place.getId(), m_lockState);
                        String currentWaiter = JTicketsBagRestaurantMap.this.restDB.getWaiterNameInTableById(this.m_place.getId());
                        if (m_user.equals(currentWaiter)) {
                            this.m_place.setPeople(true);
                            JTicketsBagRestaurantMap.this.m_PlaceClipboard = null;
                            JTicketsBagRestaurantMap.this.setActivePlace(this.m_place, ticket);
                        } else if ("locked".equals(m_lockState) || "override".equals(m_lockState)) {
                            if (JTicketsBagRestaurantMap.this.m_App.getAppUserView().getUser().hasPermission("sales.ViewSharedTicket")) {
                                int res;
                                JOptionPane.showMessageDialog(null, AppLocal.getIntString("message.sharedticketlock"));
                                if (JTicketsBagRestaurantMap.this.m_App.getAppUserView().getUser().hasPermission("sales.Override") && (res = JOptionPane.showConfirmDialog(null, AppLocal.getIntString("message.sharedticketlockoverride"), AppLocal.getIntString("title.editor"), 0, 2)) == 0) {
                                    AuditLogger.logEvent(JTicketsBagRestaurantMap.this.m_App.getSession(), "Override", JTicketsBagRestaurantMap.this.m_App.getAppUserView().getUser().getName(), null, null, "LockState=" + m_lockState, "TicketLines=" + (ticket != null ? Integer.valueOf(ticket.getLinesCount()) : "null"), "Mesa: " + this.m_place.getName() + " - User forced override access");
                                    this.m_place.setPeople(true);
                                    JTicketsBagRestaurantMap.this.m_PlaceClipboard = null;
                                    JTicketsBagRestaurantMap.this.setActivePlace(this.m_place, ticket);
                                    JTicketsBagRestaurantMap.this.dlReceipts.lockSharedTicket(JTicketsBagRestaurantMap.this.m_PlaceCurrent.getId(), "override");
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, AppLocal.getIntString("message.sharedticketlock"));
                            }
                        } else {
                            this.m_place.setPeople(true);
                            JTicketsBagRestaurantMap.this.m_PlaceClipboard = null;
                            JTicketsBagRestaurantMap.this.setActivePlace(this.m_place, ticket);
                        }
                    }
                    catch (BasicException ex) {
                        Logger.getLogger(JTicketsBagRestaurantMap.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            if (JTicketsBagRestaurantMap.this.m_PlaceClipboard != null) {
                TicketInfo ticketclip = JTicketsBagRestaurantMap.this.getTicketInfo(JTicketsBagRestaurantMap.this.m_PlaceClipboard);
                if (ticketclip != null) {
                    TicketInfo ticket;
                    if (JTicketsBagRestaurantMap.this.m_PlaceClipboard == this.m_place) {
                        Place placeclip = JTicketsBagRestaurantMap.this.m_PlaceClipboard;
                        JTicketsBagRestaurantMap.this.m_PlaceClipboard = null;
                        JTicketsBagRestaurantMap.this.customer = null;
                        JTicketsBagRestaurantMap.this.printState();
                        JTicketsBagRestaurantMap.this.setActivePlace(placeclip, ticketclip);
                    }
                    if (this.m_place.hasPeople()) {
                        ticket = JTicketsBagRestaurantMap.this.getTicketInfo(this.m_place);
                        if (ticket != null) {
                            if (JOptionPane.showConfirmDialog(JTicketsBagRestaurantMap.this, AppLocal.getIntString("message.mergetablequestion"), AppLocal.getIntString("message.mergetable"), 0) == 0) {
                                try {
                                    JTicketsBagRestaurantMap.this.m_PlaceClipboard.setPeople(false);
                                    if (ticket.getCustomer() == null) {
                                        ticket.setCustomer(ticketclip.getCustomer());
                                    }
                                    ticketclip.getLines().stream().forEach(line -> ticket.addLine((TicketLineInfo)line));
                                    AuditLogger.logEvent(JTicketsBagRestaurantMap.this.m_App.getSession(), "Merge", JTicketsBagRestaurantMap.this.m_App.getAppUserView().getUser().getName(), null, null, "From=" + JTicketsBagRestaurantMap.this.m_PlaceClipboard.getName(), "To=" + this.m_place.getName(), "Merged " + ticketclip.getLinesCount() + " lines into " + ticket.getLinesCount() + " existing lines");
                                    JTicketsBagRestaurantMap.this.dlReceipts.updateRSharedTicket(this.m_place.getId(), ticket, ticket.getPickupId());
                                    JTicketsBagRestaurantMap.this.dlReceipts.deleteSharedTicket(JTicketsBagRestaurantMap.this.m_PlaceClipboard.getId());
                                }
                                catch (BasicException e) {
                                    new MessageInf(e).show(JTicketsBagRestaurantMap.this);
                                }
                                JTicketsBagRestaurantMap.this.m_PlaceClipboard = null;
                                JTicketsBagRestaurantMap.this.customer = null;
                                JTicketsBagRestaurantMap.this.restDB.clearCustomerNameInTable(JTicketsBagRestaurantMap.this.restDB.getTableDetails(ticketclip.getId()));
                                JTicketsBagRestaurantMap.this.restDB.clearWaiterNameInTable(JTicketsBagRestaurantMap.this.restDB.getTableDetails(ticketclip.getId()));
                                JTicketsBagRestaurantMap.this.restDB.clearTableMovedFlag(JTicketsBagRestaurantMap.this.restDB.getTableDetails(ticketclip.getId()));
                                JTicketsBagRestaurantMap.this.restDB.clearTicketIdInTable(JTicketsBagRestaurantMap.this.restDB.getTableDetails(ticketclip.getId()));
                                JTicketsBagRestaurantMap.this.printState();
                                JTicketsBagRestaurantMap.this.setActivePlace(this.m_place, ticket);
                            } else {
                                Place placeclip = JTicketsBagRestaurantMap.this.m_PlaceClipboard;
                                JTicketsBagRestaurantMap.this.m_PlaceClipboard = null;
                                JTicketsBagRestaurantMap.this.customer = null;
                                JTicketsBagRestaurantMap.this.printState();
                                JTicketsBagRestaurantMap.this.setActivePlace(placeclip, ticketclip);
                            }
                        } else {
                            new MessageInf(-33554432, AppLocal.getIntString("message.tableempty")).show(JTicketsBagRestaurantMap.this);
                            this.m_place.setPeople(false);
                        }
                    } else {
                        ticket = JTicketsBagRestaurantMap.this.getTicketInfo(this.m_place);
                        if (ticket == null) {
                            try {
                                JTicketsBagRestaurantMap.this.dlReceipts.insertRSharedTicket(this.m_place.getId(), ticketclip, ticketclip.getPickupId());
                                this.m_place.setPeople(true);
                                JTicketsBagRestaurantMap.this.dlReceipts.deleteSharedTicket(JTicketsBagRestaurantMap.this.m_PlaceClipboard.getId());
                                JTicketsBagRestaurantMap.this.m_PlaceClipboard.setPeople(false);
                            }
                            catch (BasicException e) {
                                new MessageInf(e).show(JTicketsBagRestaurantMap.this);
                            }
                            JTicketsBagRestaurantMap.this.m_PlaceClipboard = null;
                            JTicketsBagRestaurantMap.this.customer = null;
                            JTicketsBagRestaurantMap.this.printState();
                            JTicketsBagRestaurantMap.this.setActivePlace(this.m_place, ticketclip);
                        } else {
                            new MessageInf(-33554432, AppLocal.getIntString("message.tablefull")).show(JTicketsBagRestaurantMap.this);
                            JTicketsBagRestaurantMap.this.m_PlaceClipboard.setPeople(true);
                            JTicketsBagRestaurantMap.this.printState();
                        }
                    }
                } else {
                    new MessageInf(-33554432, AppLocal.getIntString("message.tableempty")).show(JTicketsBagRestaurantMap.this);
                    JTicketsBagRestaurantMap.this.m_PlaceClipboard.setPeople(false);
                    JTicketsBagRestaurantMap.this.m_PlaceClipboard = null;
                    JTicketsBagRestaurantMap.this.customer = null;
                    JTicketsBagRestaurantMap.this.printState();
                }
            }
        }
    }

    class tableMapRefresh
    implements ActionListener {
        tableMapRefresh() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JTicketsBagRestaurantMap.this.loadTickets();
            JTicketsBagRestaurantMap.this.printState();
        }
    }

    private class PlaceMouseAdapter
    extends MouseAdapter {
        private final Place place;
        private final JButton btn;

        public PlaceMouseAdapter(Place place) {
            this.place = place;
            this.btn = place.getButton();
        }

        @Override
        public void mousePressed(MouseEvent e) {
            JTicketsBagRestaurantMap.this.initialClick = e.getPoint();
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            int thisX = this.btn.getLocation().x;
            int thisY = this.btn.getLocation().y;
            int xMoved = e.getX() - ((JTicketsBagRestaurantMap)JTicketsBagRestaurantMap.this).initialClick.x;
            int yMoved = e.getY() - ((JTicketsBagRestaurantMap)JTicketsBagRestaurantMap.this).initialClick.y;
            int newX = thisX + xMoved;
            int newY = thisY + yMoved;
            for (Place other : JTicketsBagRestaurantMap.this.m_aplaces) {
                if (other == this.place || !other.getFloor().equals(this.place.getFloor())) continue;
                JButton otherBtn = other.getButton();
                int otherX = otherBtn.getLocation().x;
                int otherY = otherBtn.getLocation().y;
                if (Math.abs(newX - otherX) < 50) {
                    newX = otherX;
                }
                if (Math.abs(newY - otherY) >= 50) continue;
                newY = otherY;
            }
            this.btn.setLocation(newX, newY);
            this.place.setX(newX + this.btn.getWidth() / 2);
            this.place.setY(newY + this.btn.getHeight() / 2);
        }
    }

    private static class ServerCurrent {
    }
}

