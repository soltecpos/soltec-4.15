/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.panels;

import com.openbravo.basic.BasicException;
import com.openbravo.data.gui.MessageInf;
import com.openbravo.data.gui.TableRendererBasic;
import com.openbravo.data.loader.Session;
import com.openbravo.format.Formats;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.AppUser;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.forms.BeanFactoryApp;
import com.openbravo.pos.forms.BeanFactoryException;
import com.openbravo.pos.forms.DataLogicSystem;
import com.openbravo.pos.forms.JPanelView;
import com.openbravo.pos.panels.PaymentsReprintModel;
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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class JPanelCloseMoneyReprint
extends JPanel
implements JPanelView,
BeanFactoryApp {
    private AppView m_App;
    private DataLogicSystem m_dlSystem;
    private PaymentsReprintModel m_PaymentsClosed = null;
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
    private JTextField m_jCash;
    private JTextField m_jCount;
    private JTextField m_jLinesRemoved;
    private JTextField m_jMaxDate;
    private JTextField m_jMinDate;
    private JTextField m_jNoCashSales;
    private JButton m_jPrint;
    private JTextField m_jSales;
    private JTextField m_jSalesSubtotal;
    private JTextField m_jSalesTaxes;
    private JTextField m_jSalesTotal;
    private JScrollPane m_jScrollSales;
    private JScrollPane m_jScrollTableTicket;
    private JTextField m_jSequence;
    private JTable m_jTicketTable;
    private JTable m_jsalestable;
    private JButton webBtnFindSequence;

    public JPanelCloseMoneyReprint() {
        this.initComponents();
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
        this.m_jPrint.setEnabled(false);
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
    }

    @Override
    public boolean deactivate() {
        return true;
    }

    public String getInputSequence() {
        return this.m_jSequence.getText();
    }

    private void loadData() throws BasicException {
        this.m_jSequence.setText(null);
        this.m_jMinDate.setText(null);
        this.m_jMaxDate.setText(null);
        this.m_jCount.setText(null);
        this.m_jCash.setText(null);
        this.m_jSales.setText(null);
        this.m_jSalesSubtotal.setText(null);
        this.m_jSalesTaxes.setText(null);
        this.m_jSalesTotal.setText(null);
        this.m_jTicketTable.setModel(new DefaultTableModel());
        this.m_jsalestable.setModel(new DefaultTableModel());
        this.m_PaymentsClosed = PaymentsReprintModel.loadInstance(this.m_App);
        if (this.m_PaymentsClosed.getPayments() > 0 || this.m_PaymentsClosed.getSales() > 0) {
            this.m_jSequence.setText(this.m_PaymentsClosed.printSequence());
            this.m_jMinDate.setText(this.m_PaymentsClosed.reformDateStart());
            this.m_jMaxDate.setText(this.m_PaymentsClosed.reformDateEnd());
            this.m_jPrint.setEnabled(true);
            this.m_jCount.setText(this.m_PaymentsClosed.printPayments());
            this.m_jCash.setText(this.m_PaymentsClosed.printPaymentsTotal());
            this.m_jSales.setText(this.m_PaymentsClosed.printSales());
            this.m_jSalesSubtotal.setText(this.m_PaymentsClosed.printSalesBase());
            this.m_jSalesTaxes.setText(this.m_PaymentsClosed.printSalesTaxes());
            this.m_jSalesTotal.setText(this.m_PaymentsClosed.printSalesTotal());
            this.m_jTicketTable.setModel(this.m_PaymentsClosed.getPaymentsReprintModel());
            TableColumnModel jColumns = this.m_jTicketTable.getColumnModel();
            jColumns.getColumn(0).setPreferredWidth(225);
            jColumns.getColumn(0).setResizable(false);
            jColumns.getColumn(1).setPreferredWidth(100);
            jColumns.getColumn(1).setResizable(false);
            this.m_jsalestable.setModel(this.m_PaymentsClosed.getSalesModel());
            jColumns = this.m_jsalestable.getColumnModel();
            jColumns.getColumn(0).setPreferredWidth(125);
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
                this.SQL = "PostgreSQL".equals(sdbmanager) ? "SELECT * FROM draweropened WHERE TICKETID = 'No Sale' AND OPENDATE > '" + this.m_PaymentsClosed.printDateStart() + "'" : "SELECT * FROM draweropened WHERE TICKETID = 'No Sale' AND DATE(OPENDATE) = '" + this.m_PaymentsClosed.printDateStart() + "'";
                this.stmt = this.con.createStatement();
                this.rs = this.stmt.executeQuery(this.SQL);
                while (this.rs.next()) {
                    n = this.result;
                    this.result = this.result + 1;
                }
                this.rs = null;
                this.dresult = 0;
                this.SQL = "PostgreSQL".equals(sdbmanager) ? "SELECT * FROM lineremoved WHERE TICKETID = 'Void' AND REMOVEDDATE > '" + this.m_PaymentsClosed.printDateStart() + "'" : "SELECT * FROM lineremoved WHERE TICKETID = 'Void' AND DATE(REMOVEDDATE) = '" + this.m_PaymentsClosed.printDateStart() + "'";
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
    }

    private void printPayments(String report) {
        String sresource = this.m_dlSystem.getResourceAsXML(report);
        if (sresource == null) {
            MessageInf msg = new MessageInf(-33554432, AppLocal.getIntString("message.cannotprintticket"));
            msg.show(this);
        } else {
            try {
                ScriptEngine script = ScriptFactory.getScriptEngine("velocity");
                script.put("payments", this.m_PaymentsClosed);
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
        this.webBtnFindSequence = new JButton();
        this.jLabel2 = new JLabel();
        this.m_jMinDate = new JTextField();
        this.jLabel3 = new JLabel();
        this.m_jMaxDate = new JTextField();
        this.m_jScrollTableTicket = new JScrollPane();
        this.m_jTicketTable = new JTable();
        this.m_jScrollSales = new JScrollPane();
        this.m_jsalestable = new JTable();
        this.jLabel1 = new JLabel();
        this.m_jCount = new JTextField();
        this.jLabel5 = new JLabel();
        this.m_jSales = new JTextField();
        this.jLabel6 = new JLabel();
        this.m_jSalesSubtotal = new JTextField();
        this.jLabel12 = new JLabel();
        this.m_jSalesTaxes = new JTextField();
        this.jLabel7 = new JLabel();
        this.m_jSalesTotal = new JTextField();
        this.jLabel4 = new JLabel();
        this.m_jCash = new JTextField();
        this.jLabel9 = new JLabel();
        this.m_jLinesRemoved = new JTextField();
        this.jLabel8 = new JLabel();
        this.m_jNoCashSales = new JTextField();
        this.m_jPrint = new JButton();
        this.setLayout(new BorderLayout());
        this.jPanel1.setFont(new Font("Arial", 0, 14));
        this.jLabel11.setFont(new Font("Arial", 0, 14));
        this.jLabel11.setText(AppLocal.getIntString("label.sequence"));
        this.jLabel11.setPreferredSize(new Dimension(125, 30));
        this.m_jSequence.setEditable(false);
        this.m_jSequence.setFont(new Font("Arial", 0, 14));
        this.m_jSequence.setHorizontalAlignment(4);
        this.m_jSequence.setPreferredSize(new Dimension(150, 30));
        this.webBtnFindSequence.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/search32.png")));
        this.webBtnFindSequence.setToolTipText("");
        this.webBtnFindSequence.setFont(new Font("Arial", 0, 14));
        this.webBtnFindSequence.setPreferredSize(new Dimension(80, 45));
        this.webBtnFindSequence.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPanelCloseMoneyReprint.this.webBtnFindSequenceActionPerformed(evt);
            }
        });
        this.jLabel2.setFont(new Font("Arial", 0, 14));
        this.jLabel2.setHorizontalAlignment(2);
        this.jLabel2.setText(AppLocal.getIntString("label.StartDate"));
        this.jLabel2.setPreferredSize(new Dimension(125, 30));
        this.m_jMinDate.setEditable(false);
        this.m_jMinDate.setFont(new Font("Arial", 0, 14));
        this.m_jMinDate.setHorizontalAlignment(4);
        this.m_jMinDate.setPreferredSize(new Dimension(150, 30));
        this.jLabel3.setFont(new Font("Arial", 0, 14));
        this.jLabel3.setHorizontalAlignment(2);
        this.jLabel3.setText(AppLocal.getIntString("label.EndDate"));
        this.jLabel3.setPreferredSize(new Dimension(150, 30));
        this.m_jMaxDate.setEditable(false);
        this.m_jMaxDate.setFont(new Font("Arial", 0, 14));
        this.m_jMaxDate.setHorizontalAlignment(4);
        this.m_jMaxDate.setPreferredSize(new Dimension(150, 30));
        this.m_jScrollTableTicket.setBorder(null);
        this.m_jScrollTableTicket.setHorizontalScrollBarPolicy(31);
        this.m_jScrollTableTicket.setFont(new Font("Arial", 0, 14));
        this.m_jScrollTableTicket.setMinimumSize(new Dimension(350, 140));
        this.m_jScrollTableTicket.setPreferredSize(new Dimension(350, 150));
        this.m_jTicketTable.setFont(new Font("Arial", 0, 12));
        this.m_jTicketTable.setModel(new DefaultTableModel(new Object[0][], new String[0]));
        this.m_jTicketTable.setFocusable(false);
        this.m_jTicketTable.setIntercellSpacing(new Dimension(0, 1));
        this.m_jTicketTable.setRequestFocusEnabled(false);
        this.m_jTicketTable.setShowVerticalLines(false);
        this.m_jScrollTableTicket.setViewportView(this.m_jTicketTable);
        this.m_jScrollSales.setBorder(null);
        this.m_jScrollSales.setHorizontalScrollBarPolicy(31);
        this.m_jScrollSales.setFont(new Font("Arial", 0, 14));
        this.m_jScrollSales.setPreferredSize(new Dimension(350, 150));
        this.m_jsalestable.setFont(new Font("Arial", 0, 12));
        this.m_jsalestable.setFocusable(false);
        this.m_jsalestable.setIntercellSpacing(new Dimension(0, 1));
        this.m_jsalestable.setRequestFocusEnabled(false);
        this.m_jsalestable.setShowVerticalLines(false);
        this.m_jScrollSales.setViewportView(this.m_jsalestable);
        this.jLabel1.setFont(new Font("Arial", 0, 14));
        this.jLabel1.setText(AppLocal.getIntString("label.Tickets"));
        this.jLabel1.setPreferredSize(new Dimension(150, 30));
        this.m_jCount.setEditable(false);
        this.m_jCount.setFont(new Font("Arial", 0, 14));
        this.m_jCount.setHorizontalAlignment(4);
        this.m_jCount.setPreferredSize(new Dimension(150, 30));
        this.jLabel5.setFont(new Font("Arial", 0, 14));
        this.jLabel5.setText(AppLocal.getIntString("label.sales"));
        this.jLabel5.setPreferredSize(new Dimension(150, 30));
        this.m_jSales.setEditable(false);
        this.m_jSales.setFont(new Font("Arial", 0, 14));
        this.m_jSales.setHorizontalAlignment(4);
        this.m_jSales.setPreferredSize(new Dimension(150, 30));
        this.jLabel6.setFont(new Font("Arial", 0, 14));
        this.jLabel6.setText(AppLocal.getIntString("label.totalnet"));
        this.jLabel6.setPreferredSize(new Dimension(150, 30));
        this.m_jSalesSubtotal.setEditable(false);
        this.m_jSalesSubtotal.setFont(new Font("Arial", 0, 14));
        this.m_jSalesSubtotal.setHorizontalAlignment(4);
        this.m_jSalesSubtotal.setPreferredSize(new Dimension(150, 30));
        this.jLabel12.setFont(new Font("Arial", 0, 14));
        this.jLabel12.setText(AppLocal.getIntString("label.taxes"));
        this.jLabel12.setPreferredSize(new Dimension(150, 30));
        this.m_jSalesTaxes.setEditable(false);
        this.m_jSalesTaxes.setFont(new Font("Arial", 0, 14));
        this.m_jSalesTaxes.setHorizontalAlignment(4);
        this.m_jSalesTaxes.setPreferredSize(new Dimension(150, 30));
        this.jLabel7.setFont(new Font("Arial", 0, 14));
        this.jLabel7.setText(AppLocal.getIntString("label.total"));
        this.jLabel7.setPreferredSize(new Dimension(150, 30));
        this.m_jSalesTotal.setEditable(false);
        this.m_jSalesTotal.setFont(new Font("Arial", 0, 14));
        this.m_jSalesTotal.setHorizontalAlignment(4);
        this.m_jSalesTotal.setPreferredSize(new Dimension(150, 30));
        this.jLabel4.setFont(new Font("Arial", 0, 14));
        this.jLabel4.setText(AppLocal.getIntString("label.Money"));
        this.jLabel4.setPreferredSize(new Dimension(150, 30));
        this.m_jCash.setEditable(false);
        this.m_jCash.setFont(new Font("Arial", 0, 14));
        this.m_jCash.setHorizontalAlignment(4);
        this.m_jCash.setPreferredSize(new Dimension(150, 30));
        this.jLabel9.setFont(new Font("Arial", 0, 14));
        this.jLabel9.setHorizontalAlignment(2);
        ResourceBundle bundle = ResourceBundle.getBundle("pos_messages");
        this.jLabel9.setText(bundle.getString("label.linevoids"));
        this.jLabel9.setPreferredSize(new Dimension(150, 30));
        this.m_jLinesRemoved.setEditable(false);
        this.m_jLinesRemoved.setFont(new Font("Arial", 0, 14));
        this.m_jLinesRemoved.setHorizontalAlignment(4);
        this.m_jLinesRemoved.setPreferredSize(new Dimension(150, 30));
        this.jLabel8.setFont(new Font("Arial", 0, 14));
        this.jLabel8.setHorizontalAlignment(2);
        this.jLabel8.setText(bundle.getString("label.nocashsales"));
        this.jLabel8.setPreferredSize(new Dimension(150, 30));
        this.m_jNoCashSales.setEditable(false);
        this.m_jNoCashSales.setFont(new Font("Arial", 0, 14));
        this.m_jNoCashSales.setHorizontalAlignment(4);
        this.m_jNoCashSales.setPreferredSize(new Dimension(150, 30));
        this.m_jPrint.setFont(new Font("Arial", 0, 12));
        this.m_jPrint.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/printer.png")));
        this.m_jPrint.setText(AppLocal.getIntString("button.print"));
        this.m_jPrint.setToolTipText(bundle.getString("tooltip.btn.closecash"));
        this.m_jPrint.setIconTextGap(2);
        this.m_jPrint.setInheritsPopupMenu(true);
        this.m_jPrint.setMaximumSize(new Dimension(85, 33));
        this.m_jPrint.setMinimumSize(new Dimension(85, 33));
        this.m_jPrint.setPreferredSize(new Dimension(80, 45));
        this.m_jPrint.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPanelCloseMoneyReprint.this.m_jPrintActionPerformed(evt);
            }
        });
        GroupLayout jPanel1Layout = new GroupLayout(this.jPanel1);
        this.jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.m_jScrollSales, -1, -1, -2).addComponent(this.m_jScrollTableTicket, -1, -1, -2)).addGap(18, 18, 18).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jLabel3, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addComponent(this.m_jMaxDate, -2, -1, -2)).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jLabel5, -1, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.m_jSales, -2, -1, -2)).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jLabel1, -1, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.m_jCount, -1, -1, -2)).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jLabel6, -1, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.m_jSalesSubtotal, -1, -1, -2)).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jLabel12, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.m_jSalesTaxes, -1, -1, -2)).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jLabel7, -1, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.m_jSalesTotal, -2, -1, -2))).addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jLabel4, -1, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.m_jCash, -2, -1, -2)).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jLabel9, -1, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.m_jLinesRemoved, -2, -1, -2)).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jLabel8, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.m_jNoCashSales, -2, -1, -2))))).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jLabel2, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.m_jMinDate, -2, -1, -2)).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jLabel11, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.m_jSequence, -1, -1, -2).addGap(18, 18, 18).addComponent(this.webBtnFindSequence, -2, -1, -2)))).addGroup(jPanel1Layout.createSequentialGroup().addGap(141, 141, 141).addComponent(this.m_jPrint, -2, -1, -2))).addContainerGap(-1, Short.MAX_VALUE)));
        jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.CENTER).addComponent(this.webBtnFindSequence, -2, -1, -2).addComponent(this.m_jSequence, -2, -1, -2).addComponent(this.jLabel11, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel3, -2, -1, -2).addComponent(this.m_jMaxDate, -2, -1, -2)).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel2, -2, -1, -2).addComponent(this.m_jMinDate, -2, -1, -2))).addGap(18, 18, 18).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1, -2, -1, -2).addComponent(this.m_jCount, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.m_jSales, -2, -1, -2).addComponent(this.jLabel5, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel6, -2, -1, -2).addComponent(this.m_jSalesSubtotal, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel12, -2, -1, -2).addComponent(this.m_jSalesTaxes, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.m_jSalesTotal, -2, -1, -2).addComponent(this.jLabel7, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.m_jCash, -2, -1, -2).addComponent(this.jLabel4, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel9, -2, -1, -2).addComponent(this.m_jLinesRemoved, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel8, -2, -1, -2).addComponent(this.m_jNoCashSales, -2, -1, -2))).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.m_jScrollTableTicket, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.m_jScrollSales, -2, -1, -2))).addGap(18, 18, 18).addComponent(this.m_jPrint, -2, -1, -2).addContainerGap(15, Short.MAX_VALUE)));
        this.add((Component)this.jPanel1, "Center");
    }

    private void m_jPrintActionPerformed(ActionEvent evt) {
        this.printPayments("Printer.CloseCash");
    }

    private void webBtnFindSequenceActionPerformed(ActionEvent evt) {
        try {
            this.m_jPrint.setEnabled(false);
            this.loadData();
            if (this.m_jCount.getText() != null) {
                this.m_jPrint.setEnabled(true);
            } else {
                this.m_jPrint.setEnabled(false);
                JOptionPane.showMessageDialog(this, AppLocal.getIntString("Nope"), AppLocal.getIntString("message.title"), 0);
            }
            this.repaint();
        }
        catch (BasicException ex) {
            Logger.getLogger(JPanelCloseMoneyReprint.class.getName()).log(Level.SEVERE, null, ex);
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
}

