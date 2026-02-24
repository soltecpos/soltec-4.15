/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.alee.laf.combobox.WebComboBox
 */
package com.openbravo.pos.panels;

import com.alee.laf.combobox.WebComboBox;
import com.openbravo.basic.BasicException;
import com.openbravo.data.gui.ComboBoxValModel;
import com.openbravo.data.gui.MessageInf;
import com.openbravo.data.gui.TableRendererBasic;
import com.openbravo.data.loader.Datas;
import com.openbravo.data.loader.SerializerWriteBasic;
import com.openbravo.data.loader.Session;
import com.openbravo.data.loader.StaticSentence;
import com.openbravo.format.Formats;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.AppUser;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.forms.BeanFactoryApp;
import com.openbravo.pos.forms.BeanFactoryException;
import com.openbravo.pos.forms.DataLogicSystem;
import com.openbravo.pos.forms.JPanelView;
import com.openbravo.pos.panels.PaymentsModel;
import com.openbravo.pos.printer.TicketParser;
import com.openbravo.pos.printer.TicketPrinterException;
import com.openbravo.pos.scripting.ScriptEngine;
import com.openbravo.pos.scripting.ScriptException;
import com.openbravo.pos.scripting.ScriptFactory;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.UUID;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;

public class JPanelCloseMoney
extends JPanel
implements JPanelView,
BeanFactoryApp {
    private AppView m_App;
    private DataLogicSystem m_dlSystem;
    private PaymentsModel m_PaymentsToClose = null;
    private TicketParser m_TTP;
    private final DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    private Session s;
    private Connection con;
    private Statement stmt;
    private Integer result;
    private Integer dresult;
    private String SQL;
    private ResultSet rs;
    private AppUser m_User;
    private final ComboBoxValModel<String> m_ReasonModel;
    private JLabel jLabel1;
    private JLabel jLabel11;
    private JLabel jLabel12;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JLabel jLabel8;
    private JLabel jLabel9;
    private JPanel jPanel1;
    private JSeparator jSeparator1;
    private JTextField m_jCash;
    private JButton m_jCloseCash;
    private JTextField m_jCount;
    private JTextField m_jLinesRemoved;
    private JTextField m_jMaxDate;
    private JTextField m_jMinDate;
    private JTextField m_jNoCashSales;
    private JButton m_jPrintCash;
    private JTextField m_jSales;
    private JTextField m_jSalesSubtotal;
    private JTextField m_jSalesTaxes;
    private JTextField m_jSalesTotal;
    private JScrollPane m_jScrollSales;
    private JScrollPane m_jScrollTableTicket;
    private JTextField m_jSequence;
    private JTable m_jTicketTable;
    private JTable m_jsalestable;
    private WebComboBox webCBCloseCash;

    public JPanelCloseMoney() {
        this.initComponents();
        this.m_ReasonModel = new ComboBoxValModel();
        this.m_ReasonModel.add(AppLocal.getIntString("cboption.preview"));
        this.m_ReasonModel.add(AppLocal.getIntString("cboption.reprint"));
        this.webCBCloseCash.setModel(this.m_ReasonModel);
    }

    @Override
    public void init(AppView app) throws BeanFactoryException {
        this.m_App = app;
        this.m_dlSystem = (DataLogicSystem)this.m_App.getBean("com.openbravo.pos.forms.DataLogicSystem");
        this.m_TTP = new TicketParser(this.m_App.getDeviceTicket(), this.m_dlSystem);
        this.m_jTicketTable.setDefaultRenderer(Object.class, new TableRendererBasic(new Formats[]{new FormatsPayment(), Formats.CURRENCY}));
        this.m_jTicketTable.setAutoResizeMode(0);
        this.m_jScrollTableTicket.getVerticalScrollBar().setPreferredSize(new Dimension(25, 25));
        this.m_jTicketTable.getTableHeader().setReorderingAllowed(false);
        this.m_jTicketTable.setRowHeight(25);
        this.m_jTicketTable.getSelectionModel().setSelectionMode(0);
        this.m_jsalestable.setDefaultRenderer(Object.class, new TableRendererBasic(new Formats[]{Formats.STRING, Formats.CURRENCY, Formats.CURRENCY, Formats.CURRENCY}));
        this.m_jsalestable.setAutoResizeMode(0);
        this.m_jScrollSales.getVerticalScrollBar().setPreferredSize(new Dimension(25, 25));
        this.m_jsalestable.getTableHeader().setReorderingAllowed(false);
        this.m_jsalestable.setRowHeight(25);
        this.m_jsalestable.getSelectionModel().setSelectionMode(0);
    }

    @Override
    public Object getBean() {
        return this;
    }

    @Override
    public JComponent getComponent() {
        return this;
    }

    @Override
    public String getTitle() {
        return AppLocal.getIntString("Menu.CloseTPV");
    }

    @Override
    public void activate() throws BasicException {
        this.loadData();
    }

    @Override
    public boolean deactivate() {
        return true;
    }

    private void loadData() throws BasicException {
        this.m_jSequence.setText(null);
        this.m_jMinDate.setText(null);
        this.m_jMaxDate.setText(null);
        this.m_jPrintCash.setEnabled(false);
        this.m_jCloseCash.setEnabled(false);
        this.m_jCount.setText(null);
        this.m_jCash.setText(null);
        this.m_jSales.setText(null);
        this.m_jSalesSubtotal.setText(null);
        this.m_jSalesTaxes.setText(null);
        this.m_jSalesTotal.setText(null);
        this.m_jTicketTable.setModel(new DefaultTableModel());
        this.m_jsalestable.setModel(new DefaultTableModel());
        this.m_PaymentsToClose = PaymentsModel.loadInstance(this.m_App);
        this.m_jSequence.setText(this.m_PaymentsToClose.printSequence());
        this.m_jMinDate.setText(this.m_PaymentsToClose.printDateStart());
        this.m_jMaxDate.setText(this.m_PaymentsToClose.printDateEnd());
        if (this.m_PaymentsToClose.getPayments() != 0 || this.m_PaymentsToClose.getSales() != 0) {
            this.m_jPrintCash.setEnabled(true);
            this.m_jCloseCash.setEnabled(true);
            this.m_jCount.setText(this.m_PaymentsToClose.printPayments());
            this.m_jCash.setText(this.m_PaymentsToClose.printPaymentsTotal());
            this.m_jSales.setText(this.m_PaymentsToClose.printSales());
            this.m_jSalesSubtotal.setText(this.m_PaymentsToClose.printSalesBase());
            this.m_jSalesTaxes.setText(this.m_PaymentsToClose.printSalesTaxes());
            this.m_jSalesTotal.setText(this.m_PaymentsToClose.printSalesTotal());
        }
        this.m_jTicketTable.setModel(this.m_PaymentsToClose.getPaymentsModel());
        TableColumnModel jColumns = this.m_jTicketTable.getColumnModel();
        jColumns.getColumn(0).setPreferredWidth(200);
        jColumns.getColumn(0).setResizable(false);
        jColumns.getColumn(1).setPreferredWidth(100);
        jColumns.getColumn(1).setResizable(false);
        this.m_jsalestable.setModel(this.m_PaymentsToClose.getSalesModel());
        jColumns = this.m_jsalestable.getColumnModel();
        jColumns.getColumn(0).setPreferredWidth(100);
        jColumns.getColumn(0).setResizable(false);
        jColumns.getColumn(1).setPreferredWidth(100);
        jColumns.getColumn(1).setResizable(false);
        jColumns.getColumn(2).setPreferredWidth(100);
        jColumns.getColumn(2).setResizable(false);
        try {
            Integer n;
            this.result = 0;
            this.s = this.m_App.getSession();
            this.con = this.s.getConnection();
            String sdbmanager = this.m_dlSystem.getDBVersion();
            this.SQL = "PostgreSQL".equals(sdbmanager) ? "SELECT * FROM draweropened WHERE TICKETID = 'No Sale' AND OPENDATE > '" + this.m_PaymentsToClose.printDateStart() + "'" : "SELECT * FROM draweropened WHERE TICKETID = 'No Sale' AND OPENDATE > {fn TIMESTAMP('" + this.m_PaymentsToClose.getDateStartDerby() + "')}";
            this.stmt = this.con.createStatement();
            this.rs = this.stmt.executeQuery(this.SQL);
            while (this.rs.next()) {
                n = this.result;
                this.result = this.result + 1;
            }
            this.rs = null;
            this.dresult = 0;
            this.SQL = "PostgreSQL".equals(sdbmanager) ? "SELECT * FROM lineremoved WHERE TICKETID = 'Void' AND REMOVEDDATE > '" + this.m_PaymentsToClose.printDateStart() + "'" : "SELECT * FROM lineremoved WHERE TICKETID = 'Void' AND REMOVEDDATE > {fn TIMESTAMP('" + this.m_PaymentsToClose.getDateStartDerby() + "')}";
            this.stmt = this.con.createStatement();
            this.rs = this.stmt.executeQuery(this.SQL);
            while (this.rs.next()) {
                n = this.dresult;
                this.dresult = this.dresult + 1;
            }
            this.rs = null;
            this.con = null;
            this.s = null;
        }
        catch (SQLException sQLException) {
            // empty catch block
        }
        this.m_jLinesRemoved.setText(this.dresult.toString());
        this.m_jNoCashSales.setText(this.result.toString());
    }

    private void CloseCash() {
        int res = JOptionPane.showConfirmDialog(this, AppLocal.getIntString("message.wannaclosecash"), AppLocal.getIntString("message.title"), 0, 3);
        if (res == 0) {
            MessageInf msg;
            Date dNow = new Date();
            try {
                if (this.m_App.getActiveCashDateEnd() == null) {
                    new StaticSentence(this.m_App.getSession(), "UPDATE closedcash SET DATEEND = ?, NOSALES = ? WHERE HOST = ? AND MONEY = ?", new SerializerWriteBasic(Datas.TIMESTAMP, Datas.INT, Datas.STRING, Datas.STRING)).exec(new Object[]{dNow, this.result, this.m_App.getProperties().getHost(), this.m_App.getActiveCashIndex()});
                }
            }
            catch (BasicException e) {
                msg = new MessageInf(-67108864, AppLocal.getIntString("message.cannotclosecash"), e);
                msg.show(this);
            }
            try {
                this.m_App.setActiveCash(UUID.randomUUID().toString(), this.m_App.getActiveCashSequence() + 1, dNow, null);
                this.m_dlSystem.execInsertCash(new Object[]{this.m_App.getActiveCashIndex(), this.m_App.getProperties().getHost(), this.m_App.getActiveCashSequence(), this.m_App.getActiveCashDateStart(), this.m_App.getActiveCashDateEnd(), 0});
                this.m_dlSystem.execDrawerOpened(new Object[]{this.m_App.getAppUserView().getUser().getName(), "Close Cash"});
                this.m_PaymentsToClose.setDateEnd(dNow);
                this.m_PaymentsToClose.setDateEnd(dNow);
                this.printPayments("Printer.CloseCash");
                // Send WhatsApp Notification
                this.sendWhatsAppNotification(this.m_PaymentsToClose.printSequence(), this.m_PaymentsToClose.getPaymentsTotal(), this.m_PaymentsToClose.getSalesTotal());
                
                JOptionPane.showMessageDialog(this, AppLocal.getIntString("message.closecashok"), AppLocal.getIntString("message.title"), 1);
            }
            catch (BasicException e) {
                msg = new MessageInf(-67108864, AppLocal.getIntString("message.cannotclosecash"), e);
                msg.show(this);
            }
            try {
                this.loadData();
            }
            catch (BasicException e) {
                msg = new MessageInf(-67108864, AppLocal.getIntString("label.noticketstoclose"), e);
                msg.show(this);
            }
        }
    }

    private void printPayments(String report) {
        String sresource = this.m_dlSystem.getResourceAsXML(report);
        if (sresource == null) {
            MessageInf msg = new MessageInf(-33554432, AppLocal.getIntString("message.cannotprintticket"));
            msg.show(this);
        } else {
            try {
                ScriptEngine script = ScriptFactory.getScriptEngine("velocity");
                script.put("payments", this.m_PaymentsToClose);
                script.put("nosales", this.result.toString());
                this.m_TTP.printTicket(script.eval(sresource).toString());
            }
            catch (TicketPrinterException | ScriptException e) {
                MessageInf msg = new MessageInf(-33554432, AppLocal.getIntString("message.cannotprintticket"), e);
                msg.show(this);
            }
        }
    }

    private void initComponents() {
        this.jPanel1 = new JPanel();
        this.jLabel11 = new JLabel();
        this.m_jSequence = new JTextField();
        this.jLabel2 = new JLabel();
        this.m_jMinDate = new JTextField();
        this.jLabel3 = new JLabel();
        this.m_jMaxDate = new JTextField();
        this.webCBCloseCash = new WebComboBox();
        this.jLabel5 = new JLabel();
        this.m_jCash = new JTextField();
        this.jLabel4 = new JLabel();
        this.m_jCount = new JTextField();
        this.m_jLinesRemoved = new JTextField();
        this.jLabel1 = new JLabel();
        this.jSeparator1 = new JSeparator();
        this.m_jScrollTableTicket = new JScrollPane();
        this.m_jTicketTable = new JTable();
        this.jLabel9 = new JLabel();
        this.m_jNoCashSales = new JTextField();
        this.jLabel8 = new JLabel();
        this.m_jSalesTotal = new JTextField();
        this.jLabel7 = new JLabel();
        this.jLabel12 = new JLabel();
        this.m_jSalesTaxes = new JTextField();
        this.m_jScrollSales = new JScrollPane();
        this.m_jsalestable = new JTable();
        this.m_jSales = new JTextField();
        this.m_jSalesSubtotal = new JTextField();
        this.jLabel6 = new JLabel();
        this.m_jCloseCash = new JButton();
        this.m_jPrintCash = new JButton();
        this.setLayout(new BorderLayout());
        this.jPanel1.setFont(new Font("Arial", 0, 14));
        this.jPanel1.setLayout(new AbsoluteLayout());
        this.jLabel11.setFont(new Font("Arial", 0, 14));
        this.jLabel11.setText(AppLocal.getIntString("label.sequence"));
        this.jLabel11.setPreferredSize(new Dimension(125, 30));
        this.jPanel1.add((Component)this.jLabel11, new AbsoluteConstraints(10, 11, -1, -1));
        this.m_jSequence.setEditable(false);
        this.m_jSequence.setFont(new Font("Arial", 0, 14));
        this.m_jSequence.setHorizontalAlignment(4);
        this.m_jSequence.setPreferredSize(new Dimension(150, 30));
        this.jPanel1.add((Component)this.m_jSequence, new AbsoluteConstraints(145, 12, -1, -1));
        this.jLabel2.setFont(new Font("Arial", 0, 14));
        this.jLabel2.setHorizontalAlignment(2);
        this.jLabel2.setText(AppLocal.getIntString("label.StartDate"));
        this.jLabel2.setPreferredSize(new Dimension(125, 30));
        this.jPanel1.add((Component)this.jLabel2, new AbsoluteConstraints(10, 50, -1, -1));
        this.m_jMinDate.setEditable(false);
        this.m_jMinDate.setFont(new Font("Arial", 0, 14));
        this.m_jMinDate.setHorizontalAlignment(4);
        this.m_jMinDate.setPreferredSize(new Dimension(150, 30));
        this.jPanel1.add((Component)this.m_jMinDate, new AbsoluteConstraints(145, 50, -1, -1));
        this.jLabel3.setFont(new Font("Arial", 0, 14));
        this.jLabel3.setHorizontalAlignment(2);
        this.jLabel3.setText(AppLocal.getIntString("label.EndDate"));
        this.jLabel3.setPreferredSize(new Dimension(125, 30));
        this.jPanel1.add((Component)this.jLabel3, new AbsoluteConstraints(355, 50, -1, -1));
        this.m_jMaxDate.setFont(new Font("Arial", 0, 14));
        this.m_jMaxDate.setHorizontalAlignment(4);
        this.m_jMaxDate.setEnabled(false);
        this.m_jMaxDate.setPreferredSize(new Dimension(150, 30));
        this.jPanel1.add((Component)this.m_jMaxDate, new AbsoluteConstraints(515, 50, -1, -1));
        this.webCBCloseCash.setToolTipText(AppLocal.getIntString("tooltip.closecashactions"));
        this.webCBCloseCash.setExpandIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/calculator.png")));
        this.webCBCloseCash.setFont(new Font("Arial", 0, 14));
        this.webCBCloseCash.setPreferredSize(new Dimension(150, 45));
        this.webCBCloseCash.setRound(3);
        this.webCBCloseCash.setShadeWidth(3);
        this.webCBCloseCash.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPanelCloseMoney.this.webCBCloseCashActionPerformed(evt);
            }
        });
        this.jPanel1.add((Component)this.webCBCloseCash, new AbsoluteConstraints(515, 450, -1, -1));
        this.jLabel5.setFont(new Font("Arial", 0, 14));
        this.jLabel5.setText(AppLocal.getIntString("label.sales"));
        this.jLabel5.setPreferredSize(new Dimension(150, 30));
        this.jPanel1.add((Component)this.jLabel5, new AbsoluteConstraints(355, 137, -1, -1));
        this.m_jCash.setEditable(false);
        this.m_jCash.setFont(new Font("Arial", 0, 14));
        this.m_jCash.setHorizontalAlignment(4);
        this.m_jCash.setPreferredSize(new Dimension(150, 30));
        this.jPanel1.add((Component)this.m_jCash, new AbsoluteConstraints(515, 306, -1, -1));
        this.jLabel4.setFont(new Font("Arial", 0, 14));
        this.jLabel4.setText(AppLocal.getIntString("label.Money"));
        this.jLabel4.setPreferredSize(new Dimension(150, 30));
        this.jPanel1.add((Component)this.jLabel4, new AbsoluteConstraints(355, 305, -1, -1));
        this.m_jCount.setEditable(false);
        this.m_jCount.setFont(new Font("Arial", 0, 14));
        this.m_jCount.setHorizontalAlignment(4);
        this.m_jCount.setPreferredSize(new Dimension(150, 30));
        this.jPanel1.add((Component)this.m_jCount, new AbsoluteConstraints(515, 96, -1, -1));
        this.m_jLinesRemoved.setEditable(false);
        this.m_jLinesRemoved.setFont(new Font("Arial", 0, 14));
        this.m_jLinesRemoved.setHorizontalAlignment(4);
        this.m_jLinesRemoved.setPreferredSize(new Dimension(150, 30));
        this.jPanel1.add((Component)this.m_jLinesRemoved, new AbsoluteConstraints(513, 360, -1, -1));
        this.jLabel1.setFont(new Font("Arial", 0, 14));
        this.jLabel1.setText(AppLocal.getIntString("label.Tickets"));
        this.jLabel1.setPreferredSize(new Dimension(150, 30));
        this.jPanel1.add((Component)this.jLabel1, new AbsoluteConstraints(355, 95, -1, -1));
        this.jPanel1.add((Component)this.jSeparator1, new AbsoluteConstraints(353, 347, 312, 10));
        this.m_jScrollTableTicket.setBorder(null);
        this.m_jScrollTableTicket.setHorizontalScrollBarPolicy(31);
        this.m_jScrollTableTicket.setFont(new Font("Arial", 0, 14));
        this.m_jScrollTableTicket.setMinimumSize(new Dimension(350, 140));
        this.m_jScrollTableTicket.setPreferredSize(new Dimension(325, 150));
        this.m_jTicketTable.setFont(new Font("Arial", 0, 12));
        this.m_jTicketTable.setModel(new DefaultTableModel(new Object[0][], new String[0]));
        this.m_jTicketTable.setFocusable(false);
        this.m_jTicketTable.setIntercellSpacing(new Dimension(0, 1));
        this.m_jTicketTable.setRequestFocusEnabled(false);
        this.m_jTicketTable.setShowVerticalLines(false);
        this.m_jScrollTableTicket.setViewportView(this.m_jTicketTable);
        this.jPanel1.add((Component)this.m_jScrollTableTicket, new AbsoluteConstraints(10, 95, -1, -1));
        this.jLabel9.setFont(new Font("Arial", 0, 14));
        this.jLabel9.setHorizontalAlignment(2);
        ResourceBundle bundle = ResourceBundle.getBundle("pos_messages");
        this.jLabel9.setText(bundle.getString("label.linevoids"));
        this.jLabel9.setPreferredSize(new Dimension(150, 30));
        this.jPanel1.add((Component)this.jLabel9, new AbsoluteConstraints(353, 359, -1, -1));
        this.m_jNoCashSales.setEditable(false);
        this.m_jNoCashSales.setFont(new Font("Arial", 0, 14));
        this.m_jNoCashSales.setHorizontalAlignment(4);
        this.m_jNoCashSales.setPreferredSize(new Dimension(150, 30));
        this.jPanel1.add((Component)this.m_jNoCashSales, new AbsoluteConstraints(513, 402, -1, -1));
        this.jLabel8.setFont(new Font("Arial", 0, 14));
        this.jLabel8.setHorizontalAlignment(2);
        this.jLabel8.setText(bundle.getString("label.nocashsales"));
        this.jLabel8.setPreferredSize(new Dimension(150, 30));
        this.jPanel1.add((Component)this.jLabel8, new AbsoluteConstraints(353, 401, -1, -1));
        this.m_jSalesTotal.setEditable(false);
        this.m_jSalesTotal.setFont(new Font("Arial", 0, 14));
        this.m_jSalesTotal.setHorizontalAlignment(4);
        this.m_jSalesTotal.setPreferredSize(new Dimension(150, 30));
        this.jPanel1.add((Component)this.m_jSalesTotal, new AbsoluteConstraints(515, 264, -1, -1));
        this.jLabel7.setFont(new Font("Arial", 0, 14));
        this.jLabel7.setText(AppLocal.getIntString("label.total"));
        this.jLabel7.setPreferredSize(new Dimension(150, 30));
        this.jPanel1.add((Component)this.jLabel7, new AbsoluteConstraints(355, 263, -1, -1));
        this.jLabel12.setFont(new Font("Arial", 0, 14));
        this.jLabel12.setText(AppLocal.getIntString("label.taxes"));
        this.jLabel12.setPreferredSize(new Dimension(150, 30));
        this.jPanel1.add((Component)this.jLabel12, new AbsoluteConstraints(355, 221, -1, -1));
        this.m_jSalesTaxes.setEditable(false);
        this.m_jSalesTaxes.setFont(new Font("Arial", 0, 14));
        this.m_jSalesTaxes.setHorizontalAlignment(4);
        this.m_jSalesTaxes.setPreferredSize(new Dimension(150, 30));
        this.jPanel1.add((Component)this.m_jSalesTaxes, new AbsoluteConstraints(515, 222, -1, -1));
        this.m_jScrollSales.setBorder(null);
        this.m_jScrollSales.setHorizontalScrollBarPolicy(31);
        this.m_jScrollSales.setFont(new Font("Arial", 0, 14));
        this.m_jScrollSales.setPreferredSize(new Dimension(325, 150));
        this.m_jsalestable.setFont(new Font("Arial", 0, 12));
        this.m_jsalestable.setFocusable(false);
        this.m_jsalestable.setIntercellSpacing(new Dimension(0, 1));
        this.m_jsalestable.setRequestFocusEnabled(false);
        this.m_jsalestable.setShowVerticalLines(false);
        this.m_jScrollSales.setViewportView(this.m_jsalestable);
        this.jPanel1.add((Component)this.m_jScrollSales, new AbsoluteConstraints(10, 250, -1, -1));
        this.m_jSales.setEditable(false);
        this.m_jSales.setFont(new Font("Arial", 0, 14));
        this.m_jSales.setHorizontalAlignment(4);
        this.m_jSales.setPreferredSize(new Dimension(150, 30));
        this.jPanel1.add((Component)this.m_jSales, new AbsoluteConstraints(515, 138, -1, -1));
        this.m_jSalesSubtotal.setEditable(false);
        this.m_jSalesSubtotal.setFont(new Font("Arial", 0, 14));
        this.m_jSalesSubtotal.setHorizontalAlignment(4);
        this.m_jSalesSubtotal.setPreferredSize(new Dimension(150, 30));
        this.jPanel1.add((Component)this.m_jSalesSubtotal, new AbsoluteConstraints(515, 180, -1, -1));
        this.jLabel6.setFont(new Font("Arial", 0, 14));
        this.jLabel6.setText(AppLocal.getIntString("label.totalnet"));
        this.jLabel6.setPreferredSize(new Dimension(150, 30));
        this.jPanel1.add((Component)this.jLabel6, new AbsoluteConstraints(355, 179, -1, -1));
        this.m_jCloseCash.setFont(new Font("Arial", 0, 12));
        this.m_jCloseCash.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/calculator.png")));
        this.m_jCloseCash.setText(AppLocal.getIntString("button.closecash"));
        this.m_jCloseCash.setToolTipText(bundle.getString("tooltip.btn.closecash"));
        this.m_jCloseCash.setHorizontalAlignment(2);
        this.m_jCloseCash.setIconTextGap(2);
        this.m_jCloseCash.setInheritsPopupMenu(true);
        this.m_jCloseCash.setMaximumSize(new Dimension(85, 33));
        this.m_jCloseCash.setMinimumSize(new Dimension(85, 33));
        this.m_jCloseCash.setPreferredSize(new Dimension(150, 45));
        this.m_jCloseCash.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPanelCloseMoney.this.m_jCloseCashActionPerformed(evt);
            }
        });
        this.jPanel1.add((Component)this.m_jCloseCash, new AbsoluteConstraints(170, 450, -1, -1));
        this.m_jPrintCash.setFont(new Font("Arial", 0, 12));
        this.m_jPrintCash.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/printer.png")));
        this.m_jPrintCash.setText(AppLocal.getIntString("button.printcash"));
        this.m_jPrintCash.setToolTipText(bundle.getString("tooltip.btn.partialcash"));
        this.m_jPrintCash.setHorizontalAlignment(2);
        this.m_jPrintCash.setIconTextGap(2);
        this.m_jPrintCash.setMaximumSize(new Dimension(85, 33));
        this.m_jPrintCash.setMinimumSize(new Dimension(85, 33));
        this.m_jPrintCash.setPreferredSize(new Dimension(150, 45));
        this.m_jPrintCash.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPanelCloseMoney.this.m_jPrintCashActionPerformed(evt);
            }
        });
        this.jPanel1.add((Component)this.m_jPrintCash, new AbsoluteConstraints(10, 450, -1, -1));
        this.add((Component)this.jPanel1, "Center");
    }

    private void m_jCloseCashActionPerformed(ActionEvent evt) {
        int res = JOptionPane.showConfirmDialog(this, AppLocal.getIntString("message.wannaclosecash"), AppLocal.getIntString("message.title"), 0, 3);
        if (res == 0) {
            MessageInf msg;
            Date dNow = new Date();
            try {
                if (this.m_App.getActiveCashDateEnd() == null) {
                    new StaticSentence(this.m_App.getSession(), "UPDATE closedcash SET DATEEND = ?, NOSALES = ? WHERE HOST = ? AND MONEY = ?", new SerializerWriteBasic(Datas.TIMESTAMP, Datas.INT, Datas.STRING, Datas.STRING)).exec(new Object[]{dNow, this.result, this.m_App.getProperties().getHost(), this.m_App.getActiveCashIndex()});
                }
            }
            catch (BasicException e) {
                msg = new MessageInf(-67108864, AppLocal.getIntString("message.cannotclosecash"), e);
                msg.show(this);
            }
            try {
                this.m_App.setActiveCash(UUID.randomUUID().toString(), this.m_App.getActiveCashSequence() + 1, dNow, null);
                this.m_dlSystem.execInsertCash(new Object[]{this.m_App.getActiveCashIndex(), this.m_App.getProperties().getHost(), this.m_App.getActiveCashSequence(), this.m_App.getActiveCashDateStart(), this.m_App.getActiveCashDateEnd(), 0});
                this.m_dlSystem.execDrawerOpened(new Object[]{this.m_App.getAppUserView().getUser().getName(), "Close Cash"});
                this.m_PaymentsToClose.setDateEnd(dNow);
                this.printPayments("Printer.CloseCash");
                JOptionPane.showMessageDialog(this, AppLocal.getIntString("message.closecashok"), AppLocal.getIntString("message.title"), 1);
            }
            catch (BasicException e) {
                msg = new MessageInf(-67108864, AppLocal.getIntString("message.cannotclosecash"), e);
                msg.show(this);
            }
            try {
                this.loadData();
            }
            catch (BasicException e) {
                msg = new MessageInf(-67108864, AppLocal.getIntString("label.noticketstoclose"), e);
                msg.show(this);
            }
        }
    }

    private void m_jPrintCashActionPerformed(ActionEvent evt) {
        this.printPayments("Printer.PartialCash");
    }

    private void webCBCloseCashActionPerformed(ActionEvent evt) {
        if (this.webCBCloseCash.getSelectedIndex() == 0) {
            this.printPayments("Printer.CloseCash.Preview");
        }
        if (this.webCBCloseCash.getSelectedIndex() == 1) {
            this.m_App.getAppUserView().showTask("com.openbravo.pos.panels.JPanelCloseMoneyReprint");
        }
    }

    private class FormatsPayment
    extends Formats {
        private FormatsPayment() {
        }

        @Override
        protected String formatValueInt(Object value) {
            return AppLocal.getIntString("transpayment." + (String)value);
        }

        @Override
        protected Object parseValueInt(String value) throws ParseException {
            return value;
        }

        @Override
        public int getAlignment() {
            return 2;
        }
    }
    private void sendWhatsAppNotification(String ticketId, double cashTotal, double salesTotal) {
        String phone = m_App.getProperties().getWhatsAppPhone();
        String apiKey = m_App.getProperties().getWhatsAppApiKey();
        String label = m_App.getProperties().getWhatsAppLabel();

        if (phone != null && !phone.isEmpty() && apiKey != null && !apiKey.isEmpty()) {
            new Thread(() -> {
                try {
                    String message = "Cierre de Caja " + (label != null ? label : "") + "\n" +
                            "Secuencia: " + ticketId + "\n" +
                            "Fecha: " + df.format(new Date()) + "\n" +
                            "Total Ventas: " + Formats.CURRENCY.formatValue(salesTotal) + "\n" +
                            "Total Efectivo: " + Formats.CURRENCY.formatValue(cashTotal);
                    
                    String encodedMessage = java.net.URLEncoder.encode(message, "UTF-8");
                    String urlString = "https://api.callmebot.com/whatsapp.php?phone=" + phone + "&text=" + encodedMessage + "&apikey=" + apiKey;
                    
                    java.net.URL url = new java.net.URL(urlString);
                    java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    int responseCode = conn.getResponseCode();
                    
                    if (responseCode == 200) {
                        System.out.println("WhatsApp notification sent successfully.");
                    } else {
                        System.err.println("Failed to send WhatsApp notification. Response code: " + responseCode);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.err.println("Error sending WhatsApp notification: " + e.getMessage());
                }
            }).start();
        }
    }
}

