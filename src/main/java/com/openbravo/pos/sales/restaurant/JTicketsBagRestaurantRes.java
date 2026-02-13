/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.sales.restaurant;

import com.openbravo.basic.BasicException;
import com.openbravo.beans.DateUtils;
import com.openbravo.beans.JCalendarPanel;
import com.openbravo.beans.JTimePanel;
import com.openbravo.data.gui.JCounter;
import com.openbravo.data.gui.JLabelDirty;
import com.openbravo.data.gui.JListNavigator;
import com.openbravo.data.gui.JNavigator;
import com.openbravo.data.gui.JSaver;
import com.openbravo.data.gui.MessageInf;
import com.openbravo.data.loader.LocalRes;
import com.openbravo.data.user.BrowsableEditableData;
import com.openbravo.data.user.DirtyManager;
import com.openbravo.data.user.EditorCreator;
import com.openbravo.data.user.EditorRecord;
import com.openbravo.data.user.ListProviderCreator;
import com.openbravo.data.user.SaveProvider;
import com.openbravo.editor.JEditorIntegerPositive;
import com.openbravo.editor.JEditorKeys;
import com.openbravo.editor.JEditorString;
import com.openbravo.format.Formats;
import com.openbravo.pos.customers.CustomerInfo;
import com.openbravo.pos.customers.DataLogicCustomers;
import com.openbravo.pos.customers.JCustomerFinder;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.sales.restaurant.JCalendarItemRenderer;
import com.openbravo.pos.sales.restaurant.JTicketsBagRestaurantMap;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Comparator;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.UUID;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;
import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;

public class JTicketsBagRestaurantRes
extends JPanel
implements EditorRecord {
    private final JTicketsBagRestaurantMap m_restaurantmap;
    private DataLogicCustomers dlCustomers = null;
    private final DirtyManager m_Dirty;
    private Object m_sID;
    private CustomerInfo customer;
    private Date m_dCreated;
    private final JTimePanel m_timereservation;
    private boolean m_bReceived;
    private final BrowsableEditableData m_bd;
    private Date m_dcurrentday;
    private final JCalendarPanel m_datepanel;
    private final JTimePanel m_timepanel;
    private boolean m_bpaintlock = false;
    private JButton jButton1;
    private JPanel jCalendar;
    private JLabel jLabel1;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JPanel jPanNorth;
    private JPanel jPanSouth;
    private JPanel jPanelDate;
    private JPanel jPanelTime;
    private JButton jbtnShowCalendar;
    private JEditorKeys m_jKeys;
    private JPanel m_jPanelList;
    private JPanel m_jPanelTime;
    private JPanel m_jToolbar;
    private JButton m_jbtnReceive;
    private JButton m_jbtnTables;
    private JEditorIntegerPositive m_jtxtChairs;
    private JEditorString m_jtxtDescription;
    private JEditorString txtCustomer;

    public JTicketsBagRestaurantRes(AppView oApp, JTicketsBagRestaurantMap restaurantmap) {
        this.m_restaurantmap = restaurantmap;
        this.dlCustomers = (DataLogicCustomers)oApp.getBean("com.openbravo.pos.customers.DataLogicCustomers");
        this.m_dcurrentday = null;
        this.initComponents();
        this.jCalendar.setVisible(false);
        this.m_datepanel = new JCalendarPanel();
        this.jPanelDate.add((Component)this.m_datepanel, "Center");
        this.m_datepanel.addPropertyChangeListener("Date", new DateChangeCalendarListener());
        this.m_timepanel = new JTimePanel(null, 1);
        this.m_timepanel.setPeriod(3600000L);
        this.jPanelTime.add((Component)this.m_timepanel, "Center");
        this.m_timepanel.addPropertyChangeListener("Date", new DateChangeTimeListener());
        this.m_timereservation = new JTimePanel(null, 2);
        this.m_jPanelTime.add((Component)this.m_timereservation, "Center");
        this.txtCustomer.addEditorKeys(this.m_jKeys);
        this.m_jtxtChairs.addEditorKeys(this.m_jKeys);
        this.m_jtxtDescription.addEditorKeys(this.m_jKeys);
        this.m_Dirty = new DirtyManager();
        this.m_timereservation.addPropertyChangeListener("Date", this.m_Dirty);
        this.txtCustomer.addPropertyChangeListener("Text", this.m_Dirty);
        this.txtCustomer.addPropertyChangeListener("Text", new PropertyChangeListener(){

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                JTicketsBagRestaurantRes.this.customer = new CustomerInfo(null);
                JTicketsBagRestaurantRes.this.customer.setTaxid(null);
                JTicketsBagRestaurantRes.this.customer.setSearchkey(null);
                JTicketsBagRestaurantRes.this.customer.setName(JTicketsBagRestaurantRes.this.txtCustomer.getText());
            }
        });
        this.m_jtxtChairs.addPropertyChangeListener("Text", this.m_Dirty);
        this.m_jtxtDescription.addPropertyChangeListener("Text", this.m_Dirty);
        this.writeValueEOF();
        ListProviderCreator<Object[]> lpr = new ListProviderCreator<Object[]>(this.dlCustomers.getReservationsList(), new MyDateFilter());
        SaveProvider<Object[]> spr = new SaveProvider<Object[]>(this.dlCustomers.getReservationsUpdate(), this.dlCustomers.getReservationsInsert(), this.dlCustomers.getReservationsDelete());
        this.m_bd = new BrowsableEditableData<Object[]>(lpr, spr, new CompareReservations(), this, this.m_Dirty);
        JListNavigator nl = new JListNavigator(this.m_bd, true);
        nl.setCellRenderer(new JCalendarItemRenderer());
        this.m_jPanelList.add(nl, "Center");
        this.m_jToolbar.add(new JLabelDirty(this.m_Dirty));
        this.m_jToolbar.add(new JCounter(this.m_bd));
        this.m_jToolbar.add(new JNavigator(this.m_bd));
        this.m_jToolbar.add(new JSaver(this.m_bd));
    }

    public void activate() {
        this.reload(DateUtils.getTodayHours(new Date()));
    }

    @Override
    public void refresh() {
    }

    public boolean deactivate() {
        try {
            return this.m_bd.actionClosingForm(this);
        }
        catch (BasicException eD) {
            MessageInf msg = new MessageInf(-67108864, AppLocal.getIntString("message.CannotMove"), eD);
            msg.show(this);
            return false;
        }
    }

    @Override
    public void writeValueEOF() {
        this.m_sID = null;
        this.m_dCreated = null;
        this.m_timereservation.setDate(null);
        this.assignCustomer(new CustomerInfo(null));
        this.m_jtxtChairs.reset();
        this.m_bReceived = false;
        this.m_jtxtDescription.reset();
        this.m_timereservation.setEnabled(false);
        this.txtCustomer.setEnabled(false);
        this.m_jtxtChairs.setEnabled(false);
        this.m_jtxtDescription.setEnabled(false);
        this.m_jKeys.setEnabled(false);
        this.m_jbtnReceive.setEnabled(false);
    }

    @Override
    public void writeValueInsert() {
        this.m_sID = null;
        this.m_dCreated = null;
        this.m_timereservation.setCheckDates(this.m_dcurrentday, new Date(this.m_dcurrentday.getTime() + 3600000L));
        this.m_timereservation.setDate(this.m_dcurrentday);
        this.assignCustomer(new CustomerInfo(null));
        this.m_jtxtChairs.setValueInteger(2);
        this.m_bReceived = false;
        this.m_jtxtDescription.reset();
        this.m_timereservation.setEnabled(true);
        this.txtCustomer.setEnabled(true);
        this.m_jtxtChairs.setEnabled(true);
        this.m_jtxtDescription.setEnabled(true);
        this.m_jKeys.setEnabled(true);
        this.m_jbtnReceive.setEnabled(true);
        this.txtCustomer.activate();
    }

    @Override
    public void writeValueDelete(Object value) {
        Object[] res = (Object[])value;
        this.m_sID = res[0];
        this.m_dCreated = (Date)res[1];
        this.m_timereservation.setCheckDates(this.m_dcurrentday, new Date(this.m_dcurrentday.getTime() + 3600000L));
        this.m_timereservation.setDate((Date)res[2]);
        CustomerInfo c = new CustomerInfo((String)res[3]);
        c.setTaxid((String)res[4]);
        c.setSearchkey((String)res[5]);
        c.setName((String)res[6]);
        this.assignCustomer(c);
        this.m_jtxtChairs.setValueInteger((Integer)res[7]);
        this.m_bReceived = (Boolean)res[8];
        this.m_jtxtDescription.setText(Formats.STRING.formatValue(res[9]));
        this.m_timereservation.setEnabled(false);
        this.txtCustomer.setEnabled(false);
        this.m_jtxtChairs.setEnabled(false);
        this.m_jtxtDescription.setEnabled(false);
        this.m_jKeys.setEnabled(false);
        this.m_jbtnReceive.setEnabled(false);
    }

    @Override
    public void writeValueEdit(Object value) {
        Object[] res = (Object[])value;
        this.m_sID = res[0];
        this.m_dCreated = (Date)res[1];
        this.m_timereservation.setCheckDates(this.m_dcurrentday, new Date(this.m_dcurrentday.getTime() + 3600000L));
        this.m_timereservation.setDate((Date)res[2]);
        CustomerInfo c = new CustomerInfo((String)res[3]);
        c.setTaxid((String)res[4]);
        c.setSearchkey((String)res[5]);
        c.setName((String)res[6]);
        this.assignCustomer(c);
        this.m_jtxtChairs.setValueInteger((Integer)res[7]);
        this.m_bReceived = (Boolean)res[8];
        this.m_jtxtDescription.setText(Formats.STRING.formatValue(res[9]));
        this.m_timereservation.setEnabled(true);
        this.txtCustomer.setEnabled(true);
        this.m_jtxtChairs.setEnabled(true);
        this.m_jtxtDescription.setEnabled(true);
        this.m_jKeys.setEnabled(true);
        this.m_jbtnReceive.setEnabled(!this.m_bReceived);
        this.txtCustomer.activate();
    }

    @Override
    public Object createValue() throws BasicException {
        Object[] res = new Object[]{this.m_sID == null ? UUID.randomUUID().toString() : this.m_sID, this.m_dCreated == null ? new Date() : this.m_dCreated, this.m_timereservation.getDate(), this.customer.getId(), this.customer.getTaxid(), this.customer.getSearchkey(), this.customer.getName(), this.m_jtxtChairs.getValueInteger(), this.m_bReceived, this.m_jtxtDescription.getText()};
        return res;
    }

    @Override
    public Component getComponent() {
        return this;
    }

    private void reload(Date dDate) {
        if (!dDate.equals(this.m_dcurrentday)) {
            Date doldcurrentday = this.m_dcurrentday;
            this.m_dcurrentday = dDate;
            try {
                this.m_bd.actionLoad();
            }
            catch (BasicException eD) {
                MessageInf msg = new MessageInf(-67108864, LocalRes.getIntString("message.noreload"), eD);
                msg.show(this);
                this.m_dcurrentday = doldcurrentday;
            }
        }
        this.paintDate();
    }

    private void paintDate() {
        this.m_bpaintlock = true;
        this.m_datepanel.setDate(this.m_dcurrentday);
        this.m_timepanel.setDate(this.m_dcurrentday);
        this.m_bpaintlock = false;
    }

    private void assignCustomer(CustomerInfo c) {
        this.txtCustomer.setText(c.getName());
        this.customer = c;
    }

    private void initComponents() {
        this.jPanNorth = new JPanel();
        this.m_jToolbar = new JPanel();
        this.m_jbtnReceive = new JButton();
        this.m_jbtnTables = new JButton();
        this.m_jPanelList = new JPanel();
        this.jLabel5 = new JLabel();
        this.jButton1 = new JButton();
        this.txtCustomer = new JEditorString();
        this.jLabel3 = new JLabel();
        this.m_jtxtChairs = new JEditorIntegerPositive();
        this.jLabel4 = new JLabel();
        this.m_jtxtDescription = new JEditorString();
        this.jLabel1 = new JLabel();
        this.m_jPanelTime = new JPanel();
        this.jbtnShowCalendar = new JButton();
        this.m_jKeys = new JEditorKeys();
        this.jPanSouth = new JPanel();
        this.jCalendar = new JPanel();
        this.jPanelTime = new JPanel();
        this.jPanelDate = new JPanel();
        this.setPreferredSize(new Dimension(1000, 750));
        this.setLayout(new BorderLayout());
        this.jPanNorth.setPreferredSize(new Dimension(1000, 350));
        this.m_jToolbar.setPreferredSize(new Dimension(500, 55));
        this.m_jToolbar.setLayout(new BorderLayout());
        this.m_jbtnReceive.setFont(new Font("Arial", 0, 12));
        this.m_jbtnReceive.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/receive.png")));
        this.m_jbtnReceive.setText(AppLocal.getIntString("button.receive"));
        this.m_jbtnReceive.setToolTipText("Receive pre-Booked Customer");
        this.m_jbtnReceive.setFocusPainted(false);
        this.m_jbtnReceive.setFocusable(false);
        this.m_jbtnReceive.setPreferredSize(new Dimension(130, 45));
        this.m_jbtnReceive.setRequestFocusEnabled(false);
        this.m_jbtnReceive.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JTicketsBagRestaurantRes.this.m_jbtnReceiveActionPerformed(evt);
            }
        });
        this.m_jbtnTables.setFont(new Font("Arial", 0, 12));
        this.m_jbtnTables.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/tables.png")));
        this.m_jbtnTables.setText(AppLocal.getIntString("button.tables"));
        this.m_jbtnTables.setToolTipText("Go to Table Plan");
        this.m_jbtnTables.setFocusPainted(false);
        this.m_jbtnTables.setFocusable(false);
        this.m_jbtnTables.setPreferredSize(new Dimension(130, 45));
        this.m_jbtnTables.setRequestFocusEnabled(false);
        this.m_jbtnTables.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JTicketsBagRestaurantRes.this.m_jbtnTablesActionPerformed(evt);
            }
        });
        this.m_jPanelList.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        this.m_jPanelList.setFont(new Font("Arial", 0, 14));
        this.m_jPanelList.setPreferredSize(new Dimension(300, 200));
        this.m_jPanelList.setLayout(new BorderLayout());
        this.jLabel5.setFont(new Font("Arial", 0, 14));
        this.jLabel5.setText(AppLocal.getIntString("rest.label.selectcustomer"));
        this.jLabel5.setPreferredSize(new Dimension(100, 45));
        this.jButton1.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/customer_add_sml.png")));
        this.jButton1.setToolTipText("Show Customers");
        this.jButton1.setFocusPainted(false);
        this.jButton1.setFocusable(false);
        this.jButton1.setMaximumSize(new Dimension(40, 33));
        this.jButton1.setMinimumSize(new Dimension(40, 33));
        this.jButton1.setPreferredSize(new Dimension(80, 45));
        this.jButton1.setRequestFocusEnabled(false);
        this.jButton1.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JTicketsBagRestaurantRes.this.jButton1ActionPerformed(evt);
            }
        });
        this.txtCustomer.setFont(new Font("Arial", 0, 14));
        this.txtCustomer.setMaximumSize(new Dimension(250, 30));
        this.txtCustomer.setMinimumSize(new Dimension(200, 25));
        this.txtCustomer.setPreferredSize(new Dimension(250, 30));
        this.jLabel3.setFont(new Font("Arial", 0, 14));
        this.jLabel3.setText(AppLocal.getIntString("rest.label.chairs"));
        this.jLabel3.setPreferredSize(new Dimension(100, 30));
        this.m_jtxtChairs.setFont(new Font("Arial", 0, 14));
        this.m_jtxtChairs.setMaximumSize(new Dimension(50, 25));
        this.m_jtxtChairs.setMinimumSize(new Dimension(50, 25));
        this.m_jtxtChairs.setPreferredSize(new Dimension(100, 30));
        this.jLabel4.setFont(new Font("Arial", 0, 14));
        this.jLabel4.setText(AppLocal.getIntString("rest.label.notes"));
        this.jLabel4.setPreferredSize(new Dimension(100, 30));
        this.m_jtxtDescription.setFont(new Font("Arial", 0, 14));
        this.m_jtxtDescription.setMaximumSize(new Dimension(180, 25));
        this.m_jtxtDescription.setPreferredSize(new Dimension(250, 30));
        this.jLabel1.setFont(new Font("Arial", 0, 14));
        this.jLabel1.setText(AppLocal.getIntString("rest.label.date"));
        this.jLabel1.setVerticalAlignment(1);
        this.jLabel1.setPreferredSize(new Dimension(100, 30));
        this.m_jPanelTime.setFont(new Font("Arial", 0, 14));
        this.m_jPanelTime.setPreferredSize(new Dimension(200, 200));
        this.m_jPanelTime.setLayout(new BorderLayout());
        this.jbtnShowCalendar.setFont(new Font("Arial", 0, 14));
        this.jbtnShowCalendar.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/date.png")));
        ResourceBundle bundle = ResourceBundle.getBundle("pos_messages");
        this.jbtnShowCalendar.setText(bundle.getString("rest.label.showcalendar"));
        this.jbtnShowCalendar.setToolTipText(bundle.getString("rest.label.showcalendar"));
        this.jbtnShowCalendar.setPreferredSize(new Dimension(129, 45));
        this.jbtnShowCalendar.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JTicketsBagRestaurantRes.this.jbtnShowCalendarActionPerformed(evt);
            }
        });
        this.m_jKeys.setPreferredSize(new Dimension(5, 5));
        GroupLayout jPanNorthLayout = new GroupLayout(this.jPanNorth);
        this.jPanNorth.setLayout(jPanNorthLayout);
        jPanNorthLayout.setHorizontalGroup(jPanNorthLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanNorthLayout.createSequentialGroup().addGap(10, 10, 10).addGroup(jPanNorthLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addGroup(jPanNorthLayout.createSequentialGroup().addGroup(jPanNorthLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanNorthLayout.createSequentialGroup().addComponent(this.jLabel5, -2, -1, -2).addGap(10, 10, 10).addComponent(this.jButton1, -2, 70, -2).addGap(6, 6, 6).addComponent(this.txtCustomer, -2, -1, -2).addGap(4, 4, 4).addComponent(this.jLabel3, -2, -1, -2).addGap(4, 4, 4).addComponent(this.m_jtxtChairs, -2, 90, -2)).addGroup(jPanNorthLayout.createSequentialGroup().addComponent(this.m_jPanelTime, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanNorthLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jbtnShowCalendar, -2, 200, -2).addComponent(this.jLabel1, -2, -1, -2)))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanNorthLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanNorthLayout.createSequentialGroup().addComponent(this.jLabel4, -2, -1, -2).addGap(4, 4, 4).addComponent(this.m_jtxtDescription, -2, -1, -2)).addGroup(jPanNorthLayout.createSequentialGroup().addGap(22, 22, 22).addComponent(this.m_jPanelList, -2, -1, -2))).addContainerGap()).addGroup(jPanNorthLayout.createSequentialGroup().addComponent(this.m_jToolbar, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.m_jKeys, -2, 1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addComponent(this.m_jbtnReceive, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.m_jbtnTables, -2, -1, -2).addGap(42, 42, 42)))));
        jPanNorthLayout.setVerticalGroup(jPanNorthLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanNorthLayout.createSequentialGroup().addGroup(jPanNorthLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.m_jToolbar, -2, -1, -2).addGroup(jPanNorthLayout.createSequentialGroup().addGap(24, 24, 24).addComponent(this.m_jKeys, -2, 32, -2)).addGroup(jPanNorthLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.m_jbtnTables, -2, -1, -2).addComponent(this.m_jbtnReceive, -2, -1, -2))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanNorthLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanNorthLayout.createSequentialGroup().addGap(5, 5, 5).addComponent(this.jLabel5, -2, 40, -2)).addComponent(this.jButton1, -2, -1, -2).addGroup(jPanNorthLayout.createSequentialGroup().addGap(7, 7, 7).addComponent(this.txtCustomer, -2, -1, -2)).addGroup(jPanNorthLayout.createSequentialGroup().addGap(7, 7, 7).addComponent(this.jLabel3, -2, -1, -2)).addGroup(jPanNorthLayout.createSequentialGroup().addGap(7, 7, 7).addComponent(this.m_jtxtChairs, -2, -1, -2)).addGroup(jPanNorthLayout.createSequentialGroup().addGap(7, 7, 7).addComponent(this.jLabel4, -2, -1, -2)).addGroup(jPanNorthLayout.createSequentialGroup().addGap(7, 7, 7).addComponent(this.m_jtxtDescription, -2, -1, -2))).addGroup(jPanNorthLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanNorthLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.CENTER, jPanNorthLayout.createSequentialGroup().addGap(31, 31, 31).addComponent(this.jLabel1, -2, -1, -2).addGap(42, 42, 42).addComponent(this.jbtnShowCalendar, -2, -1, -2)).addGroup(GroupLayout.Alignment.CENTER, jPanNorthLayout.createSequentialGroup().addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.m_jPanelTime, -2, -1, -2))).addGroup(jPanNorthLayout.createSequentialGroup().addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.m_jPanelList, -2, -1, -2))).addContainerGap()));
        this.jLabel5.getAccessibleContext().setAccessibleName("Select Person");
        this.add((Component)this.jPanNorth, "Center");
        this.jPanSouth.setPreferredSize(new Dimension(1000, 350));
        this.jCalendar.setPreferredSize(new Dimension(1000, 350));
        this.jCalendar.setLayout(new AbsoluteLayout());
        this.jPanelTime.setPreferredSize(new Dimension(250, 250));
        this.jPanelTime.setLayout(new BorderLayout());
        this.jCalendar.add((Component)this.jPanelTime, new AbsoluteConstraints(0, 50, -1, -1));
        this.jPanelDate.setPreferredSize(new Dimension(700, 345));
        this.jPanelDate.setLayout(new BorderLayout());
        this.jCalendar.add((Component)this.jPanelDate, new AbsoluteConstraints(270, 5, -1, -1));
        GroupLayout jPanSouthLayout = new GroupLayout(this.jPanSouth);
        this.jPanSouth.setLayout(jPanSouthLayout);
        jPanSouthLayout.setHorizontalGroup(jPanSouthLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 1012, Short.MAX_VALUE).addGroup(jPanSouthLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanSouthLayout.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE).addComponent(this.jCalendar, -2, -1, -2).addGap(0, 0, Short.MAX_VALUE))));
        jPanSouthLayout.setVerticalGroup(jPanSouthLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 350, Short.MAX_VALUE).addGroup(jPanSouthLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanSouthLayout.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE).addComponent(this.jCalendar, -2, -1, -2).addGap(0, 0, Short.MAX_VALUE))));
        this.add((Component)this.jPanSouth, "South");
    }

    private void m_jbtnReceiveActionPerformed(ActionEvent evt) {
        this.m_bReceived = true;
        this.m_Dirty.setDirty(true);
        try {
            this.m_bd.saveData();
            this.m_restaurantmap.viewTables(this.customer);
        }
        catch (BasicException eD) {
            MessageInf msg = new MessageInf(-67108864, LocalRes.getIntString("message.nosaveticket"), eD);
            msg.show(this);
        }
    }

    private void m_jbtnTablesActionPerformed(ActionEvent evt) {
        this.m_restaurantmap.viewTables();
    }

    private void jButton1ActionPerformed(ActionEvent evt) {
        JCustomerFinder finder = JCustomerFinder.getCustomerFinder(this, this.dlCustomers);
        finder.search(this.customer);
        finder.setVisible(true);
        CustomerInfo c = finder.getSelectedCustomer();
        if (c == null) {
            this.assignCustomer(new CustomerInfo(null));
        } else {
            this.assignCustomer(c);
        }
    }

    private void jbtnShowCalendarActionPerformed(ActionEvent evt) {
        if (this.jCalendar.isShowing()) {
            this.jCalendar.setVisible(false);
            this.jbtnShowCalendar.setText("Show Calendar");
        } else {
            this.jbtnShowCalendar.setText("Hide Calendar");
            this.jCalendar.setVisible(true);
        }
    }

    private class DateChangeCalendarListener
    implements PropertyChangeListener {
        private DateChangeCalendarListener() {
        }

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if (!JTicketsBagRestaurantRes.this.m_bpaintlock) {
                JTicketsBagRestaurantRes.this.reload(DateUtils.getTodayHours(DateUtils.getDate(JTicketsBagRestaurantRes.this.m_datepanel.getDate(), JTicketsBagRestaurantRes.this.m_timepanel.getDate())));
            }
        }
    }

    private class DateChangeTimeListener
    implements PropertyChangeListener {
        private DateChangeTimeListener() {
        }

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if (!JTicketsBagRestaurantRes.this.m_bpaintlock) {
                JTicketsBagRestaurantRes.this.reload(DateUtils.getTodayHours(DateUtils.getDate(JTicketsBagRestaurantRes.this.m_datepanel.getDate(), JTicketsBagRestaurantRes.this.m_timepanel.getDate())));
            }
        }
    }

    private class MyDateFilter
    implements EditorCreator {
        private MyDateFilter() {
        }

        @Override
        public Object createValue() throws BasicException {
            return new Object[]{JTicketsBagRestaurantRes.this.m_dcurrentday, new Date(JTicketsBagRestaurantRes.this.m_dcurrentday.getTime() + 3600000L)};
        }
    }

    private static class CompareReservations
    implements Comparator {
        private CompareReservations() {
        }

        public int compare(Object o1, Object o2) {
            Object[] a1 = (Object[])o1;
            Date d1 = (Date)a1[2];
            Object[] a2 = (Object[])o2;
            Date d2 = (Date)a2[2];
            int c = d1.compareTo(d2);
            if (c == 0) {
                d1 = (Date)a1[1];
                d2 = (Date)a2[1];
                return d1.compareTo(d2);
            }
            return c;
        }
    }
}

