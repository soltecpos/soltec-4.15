/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.panels;

import com.openbravo.basic.BasicException;
import com.openbravo.beans.JCalendarDialog;
import com.openbravo.data.gui.ComboBoxValModel;
import com.openbravo.data.gui.ListQBFModelNumber;
import com.openbravo.data.gui.MessageInf;
import com.openbravo.data.loader.QBFCompareEnum;
import com.openbravo.data.loader.SentenceList;
import com.openbravo.data.user.EditorCreator;
import com.openbravo.data.user.ListProvider;
import com.openbravo.data.user.ListProviderCreator;
import com.openbravo.editor.JEditorCurrency;
import com.openbravo.editor.JEditorIntegerPositive;
import com.openbravo.editor.JEditorKeys;
import com.openbravo.format.Formats;
import com.openbravo.pos.customers.DataLogicCustomers;
import com.openbravo.pos.customers.JCustomerFinder;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.DataLogicSales;
import com.openbravo.pos.inventory.TaxCategoryInfo;
import com.openbravo.pos.ticket.FindTicketsInfo;
import com.openbravo.pos.ticket.FindTicketsRenderer;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class JTicketsFinder
extends JDialog
implements EditorCreator {
    private static final long serialVersionUID = 1L;
    private ListProvider<FindTicketsInfo> lpr;
    private SentenceList<TaxCategoryInfo> m_sentcat;
    private ComboBoxValModel<TaxCategoryInfo> m_CategoryModel;
    private DataLogicSales dlSales;
    private DataLogicCustomers dlCustomers;
    private FindTicketsInfo selectedTicket;
    private JButton btnCustomer;
    private JButton btnDateEnd;
    private JButton btnDateStart;
    private JComboBox<String> jComboBoxTicket;
    private JLabel jLabel1;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JList jListTickets;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JPanel jPanel3;
    private JPanel jPanel4;
    private JPanel jPanel5;
    private JPanel jPanel6;
    private JPanel jPanel7;
    private JPanel jPanel8;
    private JScrollPane jScrollPane1;
    private JTextField jTxtEndDate;
    private JTextField jTxtStartDate;
    private JButton jbtnExecute;
    private JButton jbtnReset;
    private JComboBox<Object> jcboMoney;
    private JComboBox<TaxCategoryInfo> jcboUser;
    private JButton jcmdCancel;
    private JButton jcmdOK;
    private JTextField jtxtCustomer;
    private JEditorCurrency jtxtMoney;
    private JEditorIntegerPositive jtxtTicketID;
    private JLabel labelCustomer;
    private JEditorKeys m_jKeys;

    private JTicketsFinder(Frame parent, boolean modal) {
        super(parent, modal);
    }

    private JTicketsFinder(Dialog parent, boolean modal) {
        super(parent, modal);
    }

    public static JTicketsFinder getReceiptFinder(Component parent, DataLogicSales dlSales2, DataLogicCustomers dlCustomers) {
        Window window = JTicketsFinder.getWindow(parent);
        JTicketsFinder myMsg = window instanceof Frame ? new JTicketsFinder((Frame)window, true) : new JTicketsFinder((Dialog)window, true);
        myMsg.init(dlSales2, dlCustomers);
        myMsg.applyComponentOrientation(parent.getComponentOrientation());
        return myMsg;
    }

    public FindTicketsInfo getSelectedCustomer() {
        return this.selectedTicket;
    }

    private void init(DataLogicSales dlSales2, DataLogicCustomers dlCustomers) {
        this.dlSales = dlSales2;
        this.dlCustomers = dlCustomers;
        this.initComponents();
        this.jScrollPane1.getVerticalScrollBar().setPreferredSize(new Dimension(35, 35));
        this.jtxtTicketID.addEditorKeys(this.m_jKeys);
        this.jtxtMoney.addEditorKeys(this.m_jKeys);
        this.lpr = new ListProviderCreator<FindTicketsInfo>(dlSales2.getTicketsList(), this);
        this.jListTickets.setCellRenderer(new FindTicketsRenderer());
        this.getRootPane().setDefaultButton(this.jcmdOK);
        this.initCombos();
        this.defaultValues();
        this.selectedTicket = null;
    }

    public void executeSearch() {
        try {
            this.jListTickets.setModel(new MyListData(this.lpr.loadData()));
            if (this.jListTickets.getModel().getSize() > 0) {
                this.jListTickets.setSelectedIndex(0);
            }
        }
        catch (BasicException basicException) {
            // empty catch block
        }
    }

    private void initCombos() {
        String[] values = new String[]{AppLocal.getIntString("label.sales"), AppLocal.getIntString("label.refunds"), AppLocal.getIntString("label.all")};
        this.jComboBoxTicket.setModel(new DefaultComboBoxModel<String>(values));
        this.jcboMoney.setModel(ListQBFModelNumber.getMandatoryNumber());
        this.m_sentcat = this.dlSales.getUserList();
        this.m_CategoryModel = new ComboBoxValModel();
        List<TaxCategoryInfo> catlist = null;
        try {
            catlist = this.m_sentcat.list();
        }
        catch (BasicException ex) {
            ex.getMessage();
        }
        catlist.add(0, null);
        this.m_CategoryModel = new ComboBoxValModel<TaxCategoryInfo>(catlist);
        this.jcboUser.setModel(this.m_CategoryModel);
    }

    private void defaultValues() {
        this.jListTickets.setModel(new MyListData(new ArrayList()));
        this.jcboUser.setSelectedItem(null);
        this.jtxtTicketID.reset();
        this.jtxtTicketID.activate();
        this.jTxtStartDate.setText(null);
        this.jTxtEndDate.setText(null);
        this.jtxtCustomer.setText(null);
        this.jComboBoxTicket.setSelectedIndex(0);
        this.jcboUser.setSelectedItem(null);
        this.jcboMoney.setSelectedItem(((ListQBFModelNumber)this.jcboMoney.getModel()).getElementAt(0));
        this.jcboMoney.revalidate();
        this.jcboMoney.repaint();
        this.jtxtMoney.reset();
        this.jTxtStartDate.setText(null);
        this.jTxtEndDate.setText(null);
        this.jtxtCustomer.setText(null);
    }

    @Override
    public Object createValue() throws BasicException {
        Object[] afilter = new Object[14];
        if (this.jtxtTicketID.getText() == null || this.jtxtTicketID.getText().equals("")) {
            afilter[0] = QBFCompareEnum.COMP_NONE;
            afilter[1] = null;
        } else {
            afilter[0] = QBFCompareEnum.COMP_EQUALS;
            afilter[1] = this.jtxtTicketID.getValueInteger();
        }
        if (this.jComboBoxTicket.getSelectedIndex() == 2) {
            afilter[2] = QBFCompareEnum.COMP_DISTINCT;
            afilter[3] = 2;
        } else if (this.jComboBoxTicket.getSelectedIndex() == 0) {
            afilter[2] = QBFCompareEnum.COMP_EQUALS;
            afilter[3] = 0;
        } else if (this.jComboBoxTicket.getSelectedIndex() == 1) {
            afilter[2] = QBFCompareEnum.COMP_EQUALS;
            afilter[3] = 1;
        }
        afilter[5] = this.jtxtMoney.getDoubleValue();
        afilter[4] = afilter[5] == null ? QBFCompareEnum.COMP_NONE : this.jcboMoney.getSelectedItem();
        Object startdate = Formats.TIMESTAMP.parseValue(this.jTxtStartDate.getText());
        Object enddate = Formats.TIMESTAMP.parseValue(this.jTxtEndDate.getText());
        afilter[6] = startdate == null ? QBFCompareEnum.COMP_NONE : QBFCompareEnum.COMP_GREATEROREQUALS;
        afilter[7] = startdate;
        afilter[8] = enddate == null ? QBFCompareEnum.COMP_NONE : QBFCompareEnum.COMP_LESS;
        afilter[9] = enddate;
        if (this.jcboUser.getSelectedItem() == null) {
            afilter[10] = QBFCompareEnum.COMP_NONE;
            afilter[11] = null;
        } else {
            afilter[10] = QBFCompareEnum.COMP_EQUALS;
            afilter[11] = ((TaxCategoryInfo)this.jcboUser.getSelectedItem()).getName();
        }
        if (this.jtxtCustomer.getText() == null || this.jtxtCustomer.getText().equals("")) {
            afilter[12] = QBFCompareEnum.COMP_NONE;
            afilter[13] = null;
        } else {
            afilter[12] = QBFCompareEnum.COMP_RE;
            afilter[13] = "%" + this.jtxtCustomer.getText() + "%";
        }
        return afilter;
    }

    private static Window getWindow(Component parent) {
        if (parent == null) {
            return new JFrame();
        }
        if (parent instanceof Frame || parent instanceof Dialog) {
            return (Window)parent;
        }
        return JTicketsFinder.getWindow(parent.getParent());
    }

    private void initComponents() {
        this.jPanel3 = new JPanel();
        this.jPanel5 = new JPanel();
        this.jPanel7 = new JPanel();
        this.jLabel1 = new JLabel();
        this.jLabel6 = new JLabel();
        this.jLabel7 = new JLabel();
        this.jtxtMoney = new JEditorCurrency();
        this.jcboUser = new JComboBox();
        this.jcboMoney = new JComboBox();
        this.jtxtTicketID = new JEditorIntegerPositive();
        this.labelCustomer = new JLabel();
        this.jLabel3 = new JLabel();
        this.jLabel4 = new JLabel();
        this.jTxtStartDate = new JTextField();
        this.jTxtEndDate = new JTextField();
        this.btnDateStart = new JButton();
        this.btnDateEnd = new JButton();
        this.jtxtCustomer = new JTextField();
        this.btnCustomer = new JButton();
        this.jComboBoxTicket = new JComboBox();
        this.jPanel6 = new JPanel();
        this.jbtnReset = new JButton();
        this.jbtnExecute = new JButton();
        this.jPanel4 = new JPanel();
        this.jScrollPane1 = new JScrollPane();
        this.jListTickets = new JList();
        this.jPanel2 = new JPanel();
        this.m_jKeys = new JEditorKeys();
        this.jPanel8 = new JPanel();
        this.jPanel1 = new JPanel();
        this.jcmdCancel = new JButton();
        this.jcmdOK = new JButton();
        this.setDefaultCloseOperation(2);
        this.setTitle(AppLocal.getIntString("form.tickettitle"));
        this.setPreferredSize(new Dimension(568, 600));
        this.jPanel3.setLayout(new BorderLayout());
        this.jPanel5.setLayout(new BorderLayout());
        this.jPanel7.setPreferredSize(new Dimension(0, 210));
        this.jLabel1.setFont(new Font("Arial", 0, 14));
        this.jLabel1.setText(AppLocal.getIntString("label.ticketid"));
        this.jLabel1.setPreferredSize(new Dimension(100, 30));
        this.jLabel6.setFont(new Font("Arial", 0, 14));
        this.jLabel6.setText(AppLocal.getIntString("label.user"));
        this.jLabel6.setPreferredSize(new Dimension(100, 30));
        this.jLabel7.setFont(new Font("Arial", 0, 14));
        this.jLabel7.setText(AppLocal.getIntString("label.totalcash"));
        this.jLabel7.setPreferredSize(new Dimension(100, 30));
        this.jtxtMoney.setFont(new Font("Arial", 0, 14));
        this.jtxtMoney.setPreferredSize(new Dimension(150, 30));
        this.jcboUser.setFont(new Font("Arial", 0, 14));
        this.jcboUser.setPreferredSize(new Dimension(220, 30));
        this.jcboUser.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JTicketsFinder.this.jcboUserActionPerformed(evt);
            }
        });
        this.jcboMoney.setFont(new Font("Arial", 0, 14));
        this.jcboMoney.setPreferredSize(new Dimension(150, 30));
        this.jtxtTicketID.setFont(new Font("Arial", 0, 12));
        this.jtxtTicketID.setPreferredSize(new Dimension(150, 30));
        this.labelCustomer.setFont(new Font("Arial", 0, 14));
        this.labelCustomer.setText(AppLocal.getIntString("label.customer"));
        this.labelCustomer.setPreferredSize(new Dimension(100, 30));
        this.jLabel3.setFont(new Font("Arial", 0, 14));
        this.jLabel3.setText(AppLocal.getIntString("label.StartDate"));
        this.jLabel3.setPreferredSize(new Dimension(100, 30));
        this.jLabel4.setFont(new Font("Arial", 0, 14));
        this.jLabel4.setText(AppLocal.getIntString("label.EndDate"));
        this.jLabel4.setPreferredSize(new Dimension(100, 30));
        this.jTxtStartDate.setFont(new Font("Arial", 0, 12));
        this.jTxtStartDate.setPreferredSize(new Dimension(150, 30));
        this.jTxtEndDate.setFont(new Font("Arial", 0, 12));
        this.jTxtEndDate.setPreferredSize(new Dimension(150, 30));
        this.btnDateStart.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/date.png")));
        this.btnDateStart.setToolTipText("Open Calendar");
        this.btnDateStart.setPreferredSize(new Dimension(100, 30));
        this.btnDateStart.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JTicketsFinder.this.btnDateStartActionPerformed(evt);
            }
        });
        this.btnDateEnd.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/date.png")));
        this.btnDateEnd.setToolTipText("Open Calendar");
        this.btnDateEnd.setPreferredSize(new Dimension(100, 30));
        this.btnDateEnd.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JTicketsFinder.this.btnDateEndActionPerformed(evt);
            }
        });
        this.jtxtCustomer.setFont(new Font("Arial", 0, 12));
        this.jtxtCustomer.setPreferredSize(new Dimension(150, 30));
        this.btnCustomer.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/customer_sml.png")));
        this.btnCustomer.setToolTipText("Open Customers");
        this.btnCustomer.setFocusPainted(false);
        this.btnCustomer.setFocusable(false);
        this.btnCustomer.setMargin(new Insets(8, 14, 8, 14));
        this.btnCustomer.setPreferredSize(new Dimension(100, 30));
        this.btnCustomer.setRequestFocusEnabled(false);
        this.btnCustomer.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JTicketsFinder.this.btnCustomerActionPerformed(evt);
            }
        });
        this.jComboBoxTicket.setFont(new Font("Arial", 0, 14));
        this.jComboBoxTicket.setPreferredSize(new Dimension(150, 30));
        GroupLayout jPanel7Layout = new GroupLayout(this.jPanel7);
        this.jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(jPanel7Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel7Layout.createSequentialGroup().addContainerGap().addGroup(jPanel7Layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addComponent(this.jLabel6, GroupLayout.Alignment.LEADING, -1, 115, Short.MAX_VALUE).addComponent(this.labelCustomer, GroupLayout.Alignment.LEADING, -1, -1, Short.MAX_VALUE).addComponent(this.jLabel4, GroupLayout.Alignment.LEADING, -1, -1, Short.MAX_VALUE).addComponent(this.jLabel3, GroupLayout.Alignment.LEADING, -1, -1, Short.MAX_VALUE).addComponent(this.jLabel1, GroupLayout.Alignment.LEADING, -1, -1, Short.MAX_VALUE).addComponent(this.jLabel7, -1, -1, Short.MAX_VALUE)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel7Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel7Layout.createSequentialGroup().addGroup(jPanel7Layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addComponent(this.jtxtCustomer, GroupLayout.Alignment.LEADING, -1, -1, Short.MAX_VALUE).addComponent(this.jTxtStartDate, GroupLayout.Alignment.LEADING, -1, -1, Short.MAX_VALUE).addComponent(this.jTxtEndDate, GroupLayout.Alignment.LEADING, -1, -1, Short.MAX_VALUE)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel7Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.btnDateEnd, -2, -1, -2).addComponent(this.btnDateStart, -2, -1, -2).addComponent(this.btnCustomer, -2, -1, -2))).addGroup(jPanel7Layout.createSequentialGroup().addComponent(this.jcboMoney, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jtxtMoney, -2, 131, -2)).addGroup(jPanel7Layout.createSequentialGroup().addComponent(this.jtxtTicketID, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jComboBoxTicket, -2, -1, -2)).addComponent(this.jcboUser, -2, 150, -2)).addContainerGap(15, Short.MAX_VALUE)));
        jPanel7Layout.setVerticalGroup(jPanel7Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup().addGroup(jPanel7Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel1, -2, -1, -2).addGroup(jPanel7Layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.jComboBoxTicket, -1, -1, -2).addComponent(this.jtxtTicketID, -2, -1, -2))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel7Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel3, -2, -1, -2).addComponent(this.btnDateStart, -2, -1, -2).addComponent(this.jTxtStartDate, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel7Layout.createParallelGroup(GroupLayout.Alignment.CENTER).addComponent(this.jLabel4, -2, -1, -2).addComponent(this.jTxtEndDate, -2, -1, -2).addComponent(this.btnDateEnd, GroupLayout.Alignment.LEADING, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel7Layout.createParallelGroup(GroupLayout.Alignment.CENTER).addComponent(this.labelCustomer, -2, -1, -2).addComponent(this.jtxtCustomer, -2, -1, -2).addComponent(this.btnCustomer, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel7Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jcboUser, -1, -1, -2).addComponent(this.jLabel6, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel7Layout.createParallelGroup(GroupLayout.Alignment.CENTER).addComponent(this.jtxtMoney, GroupLayout.Alignment.LEADING, -2, -1, -2).addGroup(GroupLayout.Alignment.TRAILING, jPanel7Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jcboMoney, -1, -1, Short.MAX_VALUE).addComponent(this.jLabel7, -2, -1, -2))).addGap(19, 19, 19)));
        this.jPanel5.add((Component)this.jPanel7, "Center");
        this.jbtnReset.setFont(new Font("Arial", 0, 12));
        this.jbtnReset.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/reload.png")));
        this.jbtnReset.setText(AppLocal.getIntString("button.clean"));
        this.jbtnReset.setToolTipText("Clear Filter");
        this.jbtnReset.setPreferredSize(new Dimension(110, 45));
        this.jbtnReset.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JTicketsFinder.this.jbtnResetActionPerformed(evt);
            }
        });
        this.jPanel6.add(this.jbtnReset);
        this.jbtnExecute.setFont(new Font("Arial", 0, 12));
        this.jbtnExecute.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/ok.png")));
        this.jbtnExecute.setText(AppLocal.getIntString("button.executefilter"));
        this.jbtnExecute.setToolTipText("Execute Filter");
        this.jbtnExecute.setFocusPainted(false);
        this.jbtnExecute.setFocusable(false);
        this.jbtnExecute.setPreferredSize(new Dimension(110, 45));
        this.jbtnExecute.setRequestFocusEnabled(false);
        this.jbtnExecute.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JTicketsFinder.this.jbtnExecuteActionPerformed(evt);
            }
        });
        this.jPanel6.add(this.jbtnExecute);
        this.jPanel5.add((Component)this.jPanel6, "South");
        this.jPanel3.add((Component)this.jPanel5, "First");
        this.jPanel4.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        this.jPanel4.setLayout(new BorderLayout());
        this.jListTickets.setFont(new Font("Arial", 0, 14));
        this.jListTickets.setFocusable(false);
        this.jListTickets.setRequestFocusEnabled(false);
        this.jListTickets.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent evt) {
                JTicketsFinder.this.jListTicketsMouseClicked(evt);
            }
        });
        this.jListTickets.addListSelectionListener(new ListSelectionListener(){

            @Override
            public void valueChanged(ListSelectionEvent evt) {
                JTicketsFinder.this.jListTicketsValueChanged(evt);
            }
        });
        this.jScrollPane1.setViewportView(this.jListTickets);
        this.jPanel4.add((Component)this.jScrollPane1, "Center");
        this.jPanel3.add((Component)this.jPanel4, "Center");
        this.getContentPane().add((Component)this.jPanel3, "Center");
        this.jPanel2.setPreferredSize(new Dimension(300, 250));
        this.jPanel2.setLayout(new BorderLayout());
        this.m_jKeys.setPreferredSize(new Dimension(290, 300));
        this.m_jKeys.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JTicketsFinder.this.m_jKeysActionPerformed(evt);
            }
        });
        this.jPanel2.add((Component)this.m_jKeys, "North");
        this.jPanel8.setLayout(new BorderLayout());
        this.jcmdCancel.setFont(new Font("Arial", 0, 12));
        this.jcmdCancel.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/cancel.png")));
        this.jcmdCancel.setText(AppLocal.getIntString("button.cancel"));
        this.jcmdCancel.setFocusPainted(false);
        this.jcmdCancel.setFocusable(false);
        this.jcmdCancel.setMargin(new Insets(8, 16, 8, 16));
        this.jcmdCancel.setPreferredSize(new Dimension(110, 45));
        this.jcmdCancel.setRequestFocusEnabled(false);
        this.jcmdCancel.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JTicketsFinder.this.jcmdCancelActionPerformed(evt);
            }
        });
        this.jPanel1.add(this.jcmdCancel);
        this.jcmdOK.setFont(new Font("Arial", 0, 12));
        this.jcmdOK.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/ok.png")));
        this.jcmdOK.setText(AppLocal.getIntString("button.OK"));
        this.jcmdOK.setEnabled(false);
        this.jcmdOK.setFocusPainted(false);
        this.jcmdOK.setFocusable(false);
        this.jcmdOK.setMargin(new Insets(8, 16, 8, 16));
        this.jcmdOK.setMaximumSize(new Dimension(103, 44));
        this.jcmdOK.setMinimumSize(new Dimension(103, 44));
        this.jcmdOK.setPreferredSize(new Dimension(110, 45));
        this.jcmdOK.setRequestFocusEnabled(false);
        this.jcmdOK.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JTicketsFinder.this.jcmdOKActionPerformed(evt);
            }
        });
        this.jPanel1.add(this.jcmdOK);
        this.jPanel8.add((Component)this.jPanel1, "After");
        this.jPanel2.add((Component)this.jPanel8, "Last");
        this.getContentPane().add((Component)this.jPanel2, "After");
        this.setSize(new Dimension(758, 634));
        this.setLocationRelativeTo(null);
    }

    private void jcmdOKActionPerformed(ActionEvent evt) {
        this.selectedTicket = (FindTicketsInfo)this.jListTickets.getSelectedValue();
        this.dispose();
    }

    private void jcmdCancelActionPerformed(ActionEvent evt) {
        this.dispose();
    }

    private void jbtnExecuteActionPerformed(ActionEvent evt) {
        this.executeSearch();
    }

    private void jListTicketsValueChanged(ListSelectionEvent evt) {
        this.jcmdOK.setEnabled(this.jListTickets.getSelectedValue() != null);
    }

    private void jListTicketsMouseClicked(MouseEvent evt) {
        if (evt.getClickCount() == 2) {
            this.selectedTicket = (FindTicketsInfo)this.jListTickets.getSelectedValue();
            this.dispose();
        }
    }

    private void jbtnResetActionPerformed(ActionEvent evt) {
        this.defaultValues();
    }

    private void btnDateStartActionPerformed(ActionEvent evt) {
        Date date;
        try {
            date = (Date)Formats.TIMESTAMP.parseValue(this.jTxtStartDate.getText());
        }
        catch (BasicException e) {
            date = null;
        }
        date = JCalendarDialog.showCalendarTimeHours(this, date);
        if (date != null) {
            this.jTxtStartDate.setText(Formats.TIMESTAMP.formatValue(date));
        }
    }

    private void btnDateEndActionPerformed(ActionEvent evt) {
        Date date;
        try {
            date = (Date)Formats.TIMESTAMP.parseValue(this.jTxtEndDate.getText());
        }
        catch (BasicException e) {
            date = null;
        }
        date = JCalendarDialog.showCalendarTimeHours(this, date);
        if (date != null) {
            this.jTxtEndDate.setText(Formats.TIMESTAMP.formatValue(date));
        }
    }

    private void btnCustomerActionPerformed(ActionEvent evt) {
        JCustomerFinder finder = JCustomerFinder.getCustomerFinder(this, this.dlCustomers);
        finder.search(null);
        finder.setVisible(true);
        try {
            this.jtxtCustomer.setText(finder.getSelectedCustomer() == null ? null : this.dlSales.loadCustomerExt(finder.getSelectedCustomer().getId()).toString());
        }
        catch (BasicException e) {
            MessageInf msg = new MessageInf(-33554432, AppLocal.getIntString("message.cannotfindcustomer"), e);
            msg.show(this);
        }
    }

    private void m_jKeysActionPerformed(ActionEvent evt) {
    }

    private void jcboUserActionPerformed(ActionEvent evt) {
    }

    private static class MyListData
    extends AbstractListModel<Object> {
        private static final long serialVersionUID = 1L;
        private final List m_data;

        public MyListData(List data) {
            this.m_data = data;
        }

        @Override
        public Object getElementAt(int index) {
            return this.m_data.get(index);
        }

        @Override
        public int getSize() {
            return this.m_data.size();
        }
    }
}

