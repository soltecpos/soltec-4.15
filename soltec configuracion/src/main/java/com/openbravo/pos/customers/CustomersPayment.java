/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.customers;

import com.openbravo.basic.BasicException;
import com.openbravo.data.gui.MessageInf;
import com.openbravo.data.user.DirtyManager;
import com.openbravo.editor.JEditorKeys;
import com.openbravo.editor.JEditorString;
import com.openbravo.format.Formats;
import com.openbravo.pos.customers.CustomerInfo;
import com.openbravo.pos.customers.CustomerInfoExt;
import com.openbravo.pos.customers.DataLogicCustomers;
import com.openbravo.pos.customers.JCustomerFinder;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.forms.BeanFactoryApp;
import com.openbravo.pos.forms.BeanFactoryException;
import com.openbravo.pos.forms.DataLogicSales;
import com.openbravo.pos.forms.DataLogicSystem;
import com.openbravo.pos.forms.JPanelView;
import com.openbravo.pos.payment.JPaymentSelect;
import com.openbravo.pos.payment.JPaymentSelectCustomer;
import com.openbravo.pos.payment.PaymentInfo;
import com.openbravo.pos.payment.PaymentInfoTicket;
import com.openbravo.pos.printer.TicketParser;
import com.openbravo.pos.printer.TicketPrinterException;
import com.openbravo.pos.scripting.ScriptEngine;
import com.openbravo.pos.scripting.ScriptException;
import com.openbravo.pos.scripting.ScriptFactory;
import com.openbravo.pos.ticket.TicketInfo;
import com.openbravo.pos.util.RoundUtils;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;

public class CustomersPayment
extends JPanel
implements JPanelView,
BeanFactoryApp {
    private AppView app;
    private DataLogicCustomers dlcustomers;
    private DataLogicSales dlsales;
    private DataLogicSystem dlsystem;
    private TicketParser ttp;
    private JPaymentSelect paymentdialog;
    private CustomerInfoExt customerext;
    private DirtyManager dirty;
    private JButton btnCustomer;
    private JButton btnPay;
    private JButton btnPrePay;
    private JButton btnSave;
    private JEditorString editorcard;
    private JButton jButton1;
    private JLabel jLabel1;
    private JLabel jLabel12;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JPanel jPanel3;
    private JPanel jPanel4;
    private JPanel jPanel5;
    private JPanel jPanel6;
    private JSeparator jSeparator1;
    private JEditorKeys m_jKeys;
    private JTextField txtCard;
    private JTextField txtCurdate;
    private JTextField txtCurdebt;
    private JTextField txtMaxdebt;
    private JTextField txtName;
    private JEditorString txtNotes;
    private JEditorString txtPrePay;
    private JTextField txtTaxId;

    public CustomersPayment() {
        this.initComponents();
        this.editorcard.addEditorKeys(this.m_jKeys);
        this.txtNotes.addEditorKeys(this.m_jKeys);
        this.txtPrePay.addEditorKeys(this.m_jKeys);
        this.dirty = new DirtyManager();
        this.txtNotes.addPropertyChangeListener("Text", this.dirty);
        this.txtPrePay.addPropertyChangeListener("Number", this.dirty);
    }

    @Override
    public void init(AppView app) throws BeanFactoryException {
        this.app = app;
        this.dlcustomers = (DataLogicCustomers)app.getBean("com.openbravo.pos.customers.DataLogicCustomers");
        this.dlsales = (DataLogicSales)app.getBean("com.openbravo.pos.forms.DataLogicSales");
        this.dlsystem = (DataLogicSystem)app.getBean("com.openbravo.pos.forms.DataLogicSystem");
        this.ttp = new TicketParser(app.getDeviceTicket(), this.dlsystem);
    }

    @Override
    public Object getBean() {
        return this;
    }

    @Override
    public String getTitle() {
        return AppLocal.getIntString("Menu.CustomersPayment");
    }

    @Override
    public void activate() throws BasicException {
        this.paymentdialog = JPaymentSelectCustomer.getDialog(this);
        this.paymentdialog.init(this.app);
        this.resetCustomer();
        this.editorcard.reset();
        this.editorcard.activate();
    }

    @Override
    public boolean deactivate() {
        if (this.dirty.isDirty()) {
            int res = JOptionPane.showConfirmDialog(this, AppLocal.getIntString("message.wannasave"), AppLocal.getIntString("title.editor"), 1, 3);
            if (res == 0) {
                this.save();
                return true;
            }
            return res == 1;
        }
        return true;
    }

    @Override
    public JComponent getComponent() {
        return this;
    }

    private void editCustomer(CustomerInfoExt customer) {
        this.customerext = customer;
        this.txtTaxId.setText(customer.getTaxid());
        this.txtName.setText(customer.getName());
        this.txtCard.setText(customer.getCard());
        this.txtNotes.reset();
        this.txtNotes.setText(customer.getNotes());
        this.txtMaxdebt.setText(Formats.CURRENCY.formatValue(customer.getMaxdebt()));
        this.txtCurdebt.setText(Formats.CURRENCY.formatValue(customer.getCurdebt()));
        this.txtCurdate.setText(Formats.DATE.formatValue(customer.getCurdate()));
        this.txtPrePay.setText(null);
        this.txtNotes.setEnabled(true);
        this.txtPrePay.setEnabled(true);
        this.dirty.setDirty(false);
        this.btnSave.setEnabled(true);
        this.btnPay.setEnabled(true);
        this.btnPrePay.setEnabled(true);
    }

    private void resetCustomer() {
        this.customerext = null;
        this.txtTaxId.setText(null);
        this.txtName.setText(null);
        this.txtCard.setText(null);
        this.txtNotes.reset();
        this.txtMaxdebt.setText(null);
        this.txtCurdebt.setText(null);
        this.txtCurdate.setText(null);
        this.txtPrePay.setText(null);
        this.txtNotes.setEnabled(false);
        this.txtPrePay.setEnabled(false);
        this.dirty.setDirty(false);
        this.btnSave.setEnabled(false);
        this.btnPay.setEnabled(false);
        this.btnPrePay.setEnabled(false);
    }

    private void readCustomer() {
        try {
            CustomerInfoExt customer = this.dlsales.findCustomerExt(this.editorcard.getText());
            if (customer == null) {
                MessageInf msg = new MessageInf(-33554432, AppLocal.getIntString("message.cannotfindcustomer"));
                msg.show(this);
            } else {
                this.editCustomer(customer);
            }
        }
        catch (BasicException ex) {
            MessageInf msg = new MessageInf(-33554432, AppLocal.getIntString("message.cannotfindcustomer"), ex);
            msg.show(this);
        }
        this.editorcard.reset();
        this.editorcard.activate();
    }

    private void save() {
        this.customerext.setNotes(this.txtNotes.getText());
        this.customerext.setPrePay(this.txtPrePay.getText());
        try {
            this.dlcustomers.updateCustomerExt(this.customerext);
            this.editCustomer(this.customerext);
        }
        catch (BasicException e) {
            MessageInf msg = new MessageInf(-67108864, AppLocal.getIntString("message.nosave"), e);
            msg.show(this);
        }
    }

    private void printTicket(String resname, TicketInfo ticket, CustomerInfoExt customer) {
        String resource = this.dlsystem.getResourceAsXML(resname);
        if (resource == null) {
            MessageInf msg = new MessageInf(-33554432, AppLocal.getIntString("message.cannotprintticket"));
            msg.show(this);
        } else {
            try {
                ScriptEngine script = ScriptFactory.getScriptEngine("velocity");
                script.put("ticket", ticket);
                script.put("customer", customer);
                this.ttp.printTicket(script.eval(resource).toString());
            }
            catch (TicketPrinterException | ScriptException e) {
                MessageInf msg = new MessageInf(-33554432, AppLocal.getIntString("message.cannotprintticket"), e);
                msg.show(this);
            }
        }
    }

    private void initComponents() {
        this.jPanel2 = new JPanel();
        this.jPanel6 = new JPanel();
        this.btnCustomer = new JButton();
        this.btnSave = new JButton();
        this.jSeparator1 = new JSeparator();
        this.btnPay = new JButton();
        this.btnPrePay = new JButton();
        this.jPanel3 = new JPanel();
        this.jPanel4 = new JPanel();
        this.m_jKeys = new JEditorKeys();
        this.jPanel5 = new JPanel();
        this.editorcard = new JEditorString();
        this.jButton1 = new JButton();
        this.jPanel1 = new JPanel();
        this.jLabel3 = new JLabel();
        this.jLabel12 = new JLabel();
        this.jLabel5 = new JLabel();
        this.txtCard = new JTextField();
        this.jLabel1 = new JLabel();
        this.jLabel2 = new JLabel();
        this.txtCurdebt = new JTextField();
        this.txtCurdate = new JTextField();
        this.jLabel6 = new JLabel();
        this.txtName = new JTextField();
        this.txtMaxdebt = new JTextField();
        this.txtPrePay = new JEditorString();
        this.txtTaxId = new JTextField();
        this.jLabel7 = new JLabel();
        this.jLabel4 = new JLabel();
        this.txtNotes = new JEditorString();
        this.setLayout(new BorderLayout());
        this.jPanel2.setLayout(new BorderLayout());
        this.btnCustomer.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/customer_sml.png")));
        this.btnCustomer.setToolTipText("Customer Account");
        this.btnCustomer.setFocusPainted(false);
        this.btnCustomer.setFocusable(false);
        this.btnCustomer.setMargin(new Insets(8, 14, 8, 14));
        this.btnCustomer.setPreferredSize(new Dimension(110, 45));
        this.btnCustomer.setRequestFocusEnabled(false);
        this.btnCustomer.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                CustomersPayment.this.btnCustomerActionPerformed(evt);
            }
        });
        this.jPanel6.add(this.btnCustomer);
        this.btnSave.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/filesave.png")));
        this.btnSave.setToolTipText("Save");
        this.btnSave.setFocusPainted(false);
        this.btnSave.setFocusable(false);
        this.btnSave.setMargin(new Insets(8, 14, 8, 14));
        this.btnSave.setPreferredSize(new Dimension(110, 45));
        this.btnSave.setRequestFocusEnabled(false);
        this.btnSave.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                CustomersPayment.this.btnSaveActionPerformed(evt);
            }
        });
        this.jPanel6.add(this.btnSave);
        this.jPanel6.add(this.jSeparator1);
        this.btnPay.setFont(new Font("Arial", 0, 12));
        this.btnPay.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/pay.png")));
        this.btnPay.setText(AppLocal.getIntString("button.pay"));
        this.btnPay.setToolTipText("Pay Account");
        this.btnPay.setFocusPainted(false);
        this.btnPay.setFocusable(false);
        this.btnPay.setMargin(new Insets(8, 14, 8, 14));
        this.btnPay.setMaximumSize(new Dimension(110, 44));
        this.btnPay.setMinimumSize(new Dimension(110, 44));
        this.btnPay.setPreferredSize(new Dimension(110, 45));
        this.btnPay.setRequestFocusEnabled(false);
        this.btnPay.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                CustomersPayment.this.btnPayActionPerformed(evt);
            }
        });
        this.jPanel6.add(this.btnPay);
        this.btnPrePay.setFont(new Font("Arial", 0, 12));
        this.btnPrePay.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/customer_add_sml.png")));
        this.btnPrePay.setText(AppLocal.getIntString("button.prepay"));
        ResourceBundle bundle = ResourceBundle.getBundle("pos_messages");
        this.btnPrePay.setToolTipText(bundle.getString("tooltip.prepay"));
        this.btnPrePay.setFocusPainted(false);
        this.btnPrePay.setFocusable(false);
        this.btnPrePay.setMargin(new Insets(8, 14, 8, 14));
        this.btnPrePay.setMaximumSize(new Dimension(110, 44));
        this.btnPrePay.setMinimumSize(new Dimension(110, 44));
        this.btnPrePay.setPreferredSize(new Dimension(110, 45));
        this.btnPrePay.setRequestFocusEnabled(false);
        this.btnPrePay.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                CustomersPayment.this.btnPrePayActionPerformed(evt);
            }
        });
        this.jPanel6.add(this.btnPrePay);
        this.jPanel2.add((Component)this.jPanel6, "Before");
        this.add((Component)this.jPanel2, "First");
        this.jPanel3.setLayout(new BorderLayout());
        this.jPanel4.setLayout(new BoxLayout(this.jPanel4, 1));
        this.m_jKeys.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                CustomersPayment.this.m_jKeysActionPerformed(evt);
            }
        });
        this.jPanel4.add(this.m_jKeys);
        this.jPanel5.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        this.jPanel5.setLayout(new GridBagLayout());
        this.editorcard.setFont(new Font("Arial", 0, 14));
        this.editorcard.setPreferredSize(new Dimension(200, 30));
        this.jPanel5.add((Component)this.editorcard, new GridBagConstraints());
        this.jButton1.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/ok.png")));
        this.jButton1.setFocusPainted(false);
        this.jButton1.setFocusable(false);
        this.jButton1.setMargin(new Insets(8, 14, 8, 14));
        this.jButton1.setPreferredSize(new Dimension(110, 45));
        this.jButton1.setRequestFocusEnabled(false);
        this.jButton1.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                CustomersPayment.this.jButton1ActionPerformed(evt);
            }
        });
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = 1;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new Insets(0, 5, 0, 0);
        this.jPanel5.add((Component)this.jButton1, gridBagConstraints);
        this.jPanel4.add(this.jPanel5);
        this.jPanel3.add((Component)this.jPanel4, "North");
        this.add((Component)this.jPanel3, "After");
        this.jLabel3.setFont(new Font("Arial", 0, 14));
        this.jLabel3.setText(AppLocal.getIntString("label.name"));
        this.jLabel3.setPreferredSize(new Dimension(150, 30));
        this.jLabel12.setFont(new Font("Arial", 0, 14));
        this.jLabel12.setText(AppLocal.getIntString("label.notes"));
        this.jLabel12.setPreferredSize(new Dimension(150, 30));
        this.jLabel5.setFont(new Font("Arial", 0, 14));
        this.jLabel5.setText(AppLocal.getIntString("label.card"));
        this.jLabel5.setPreferredSize(new Dimension(150, 30));
        this.txtCard.setEditable(false);
        this.txtCard.setFont(new Font("Arial", 0, 14));
        this.txtCard.setFocusable(false);
        this.txtCard.setPreferredSize(new Dimension(0, 30));
        this.txtCard.setRequestFocusEnabled(false);
        this.jLabel1.setFont(new Font("Arial", 0, 14));
        this.jLabel1.setHorizontalAlignment(0);
        this.jLabel1.setText(AppLocal.getIntString("label.maxdebt"));
        this.jLabel1.setPreferredSize(new Dimension(120, 30));
        this.jLabel2.setFont(new Font("Arial", 0, 14));
        this.jLabel2.setHorizontalAlignment(0);
        this.jLabel2.setText(AppLocal.getIntString("label.curdebt"));
        this.jLabel2.setPreferredSize(new Dimension(120, 30));
        this.txtCurdebt.setEditable(false);
        this.txtCurdebt.setFont(new Font("Arial", 0, 14));
        this.txtCurdebt.setHorizontalAlignment(4);
        this.txtCurdebt.setFocusable(false);
        this.txtCurdebt.setPreferredSize(new Dimension(120, 30));
        this.txtCurdebt.setRequestFocusEnabled(false);
        this.txtCurdate.setEditable(false);
        this.txtCurdate.setFont(new Font("Arial", 0, 14));
        this.txtCurdate.setHorizontalAlignment(0);
        this.txtCurdate.setFocusable(false);
        this.txtCurdate.setPreferredSize(new Dimension(120, 30));
        this.txtCurdate.setRequestFocusEnabled(false);
        this.jLabel6.setFont(new Font("Arial", 0, 14));
        this.jLabel6.setHorizontalAlignment(0);
        this.jLabel6.setText(AppLocal.getIntString("label.curdate"));
        this.jLabel6.setPreferredSize(new Dimension(120, 30));
        this.txtName.setEditable(false);
        this.txtName.setFont(new Font("Arial", 0, 14));
        this.txtName.setFocusable(false);
        this.txtName.setPreferredSize(new Dimension(0, 30));
        this.txtName.setRequestFocusEnabled(false);
        this.txtMaxdebt.setEditable(false);
        this.txtMaxdebt.setFont(new Font("Arial", 0, 14));
        this.txtMaxdebt.setHorizontalAlignment(4);
        this.txtMaxdebt.setFocusable(false);
        this.txtMaxdebt.setPreferredSize(new Dimension(120, 30));
        this.txtMaxdebt.setRequestFocusEnabled(false);
        this.txtPrePay.setForeground(new Color(0, 204, 255));
        this.txtPrePay.setEnabled(false);
        this.txtPrePay.setFont(new Font("Arial", 1, 14));
        this.txtPrePay.setPreferredSize(new Dimension(200, 30));
        this.txtTaxId.setEditable(false);
        this.txtTaxId.setFont(new Font("Arial", 0, 14));
        this.txtTaxId.setFocusable(false);
        this.txtTaxId.setPreferredSize(new Dimension(150, 30));
        this.txtTaxId.setRequestFocusEnabled(false);
        this.jLabel7.setFont(new Font("Arial", 0, 14));
        this.jLabel7.setText(AppLocal.getIntString("label.taxid"));
        this.jLabel7.setPreferredSize(new Dimension(150, 30));
        this.jLabel4.setFont(new Font("Arial", 0, 14));
        this.jLabel4.setText(AppLocal.getIntString("label.prepay"));
        this.jLabel4.setPreferredSize(new Dimension(120, 30));
        this.txtNotes.setEnabled(false);
        this.txtNotes.setFont(new Font("Arial", 0, 14));
        this.txtNotes.setPreferredSize(new Dimension(250, 100));
        GroupLayout jPanel1Layout = new GroupLayout(this.jPanel1);
        this.jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jLabel3, -2, 140, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.txtName, -2, 240, -2)).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jLabel5, -2, 140, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.txtCard, -2, 240, -2)).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jLabel7, -2, 140, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.txtTaxId, -2, 240, -2)).addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel12, -2, 140, -2).addComponent(this.jLabel4, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.txtPrePay, -2, -1, -2).addComponent(this.txtNotes, -2, 250, -2))))).addGroup(jPanel1Layout.createSequentialGroup().addGap(18, 18, 18).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.txtMaxdebt, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.txtCurdebt, -2, -1, -2)).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jLabel1, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jLabel2, -2, -1, -2))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel6, -2, -1, -2).addComponent(this.txtCurdate, -2, -1, -2)))).addContainerGap(-1, Short.MAX_VALUE)));
        jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel7, -2, -1, -2).addComponent(this.txtTaxId, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel3, -2, -1, -2).addComponent(this.txtName, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel5, -2, -1, -2).addComponent(this.txtCard, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel12, -2, -1, -2).addComponent(this.txtNotes, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.jLabel4, -2, -1, -2).addComponent(this.txtPrePay, -2, -1, -2)).addGap(30, 30, 30).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1, -2, -1, -2).addComponent(this.jLabel2, -2, -1, -2).addComponent(this.jLabel6, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.txtCurdebt, -2, -1, -2).addComponent(this.txtMaxdebt, -2, -1, -2).addComponent(this.txtCurdate, -2, -1, -2)).addContainerGap(-1, Short.MAX_VALUE)));
        this.add((Component)this.jPanel1, "Center");
    }

    private void jButton1ActionPerformed(ActionEvent evt) {
        this.readCustomer();
    }

    private void m_jKeysActionPerformed(ActionEvent evt) {
        this.readCustomer();
    }

    private void btnCustomerActionPerformed(ActionEvent evt) {
        JCustomerFinder finder = JCustomerFinder.getCustomerFinder(this, this.dlcustomers);
        finder.search(null);
        finder.setVisible(true);
        CustomerInfo customer = finder.getSelectedCustomer();
        if (customer != null) {
            try {
                CustomerInfoExt c = this.dlsales.loadCustomerExt(customer.getId());
                if (c == null) {
                    MessageInf msg = new MessageInf(-33554432, AppLocal.getIntString("message.cannotfindcustomer"));
                    msg.show(this);
                } else {
                    this.editCustomer(c);
                }
            }
            catch (BasicException ex) {
                MessageInf msg = new MessageInf(-33554432, AppLocal.getIntString("message.cannotfindcustomer"), ex);
                msg.show(this);
            }
        }
        this.editorcard.reset();
        this.editorcard.activate();
    }

    private void btnPayActionPerformed(ActionEvent evt) {
        this.paymentdialog.setPrintSelected(true);
        if (this.paymentdialog.showDialog(this.customerext.getCurdebt(), null)) {
            CustomerInfoExt c;
            MessageInf msg;
            TicketInfo ticket = new TicketInfo();
            ticket.setTicketType(2);
            List<PaymentInfo> payments = this.paymentdialog.getSelectedPayments();
            double total = 0.0;
            for (PaymentInfo p : payments) {
                total += p.getTotal();
            }
            payments.add(new PaymentInfoTicket(-total, "debtpaid"));
            ticket.setPayments(payments);
            ticket.setUser(this.app.getAppUserView().getUser().getUserInfo());
            ticket.setActiveCash(this.app.getActiveCashIndex());
            ticket.setDate(new Date());
            ticket.setCustomer(this.customerext);
            try {
                this.dlsales.saveTicket(ticket, this.app.getInventoryLocation());
            }
            catch (BasicException eData) {
                msg = new MessageInf(-67108864, AppLocal.getIntString("message.nosaveticket"), eData);
                msg.show(this);
            }
            try {
                c = this.dlsales.loadCustomerExt(this.customerext.getId());
                if (c == null) {
                    msg = new MessageInf(-33554432, AppLocal.getIntString("message.cannotfindcustomer"));
                    msg.show(this);
                } else {
                    this.editCustomer(c);
                }
            }
            catch (BasicException ex) {
                c = null;
                MessageInf msg2 = new MessageInf(-33554432, AppLocal.getIntString("message.cannotfindcustomer"), ex);
                msg2.show(this);
            }
            this.printTicket(this.paymentdialog.isPrintSelected() ? "Printer.CustomerPaid" : "Printer.CustomerPaid2", ticket, c);
        }
        this.editorcard.reset();
        this.editorcard.activate();
    }

    private void btnSaveActionPerformed(ActionEvent evt) {
        if (this.dirty.isDirty()) {
            this.save();
            this.editorcard.reset();
            this.editorcard.activate();
        }
    }

    private void btnPrePayActionPerformed(ActionEvent evt) {
        this.txtPrePay.setFocusable(true);
        this.txtPrePay.requestFocusInWindow();
        if (this.txtPrePay.getText() != null) {
            double prepay = Double.parseDouble(this.txtPrePay.getText());
            Formats.CURRENCY.formatValue(RoundUtils.getValue(prepay));
            this.paymentdialog.setPrintSelected(true);
            if (this.paymentdialog.showDialog(prepay, null)) {
                CustomerInfoExt c;
                MessageInf msg;
                TicketInfo ticket = new TicketInfo();
                ticket.setTicketType(2);
                List<PaymentInfo> payments = this.paymentdialog.getSelectedPayments();
                double total = 0.0;
                for (PaymentInfo p : payments) {
                    total += p.getTotal();
                }
                total = Double.parseDouble(this.txtPrePay.getText());
                payments.add(new PaymentInfoTicket(-total, "debtpaid"));
                ticket.setPayments(payments);
                ticket.setUser(this.app.getAppUserView().getUser().getUserInfo());
                ticket.setActiveCash(this.app.getActiveCashIndex());
                ticket.setDate(new Date());
                ticket.setCustomer(this.customerext);
                try {
                    this.dlsales.saveTicket(ticket, this.app.getInventoryLocation());
                }
                catch (BasicException eData) {
                    msg = new MessageInf(-67108864, AppLocal.getIntString("message.nosaveticket"), eData);
                    msg.show(this);
                }
                try {
                    c = this.dlsales.loadCustomerExt(this.customerext.getId());
                    if (c == null) {
                        msg = new MessageInf(-33554432, AppLocal.getIntString("message.cannotfindcustomer"));
                        msg.show(this);
                    } else {
                        this.editCustomer(c);
                    }
                }
                catch (BasicException ex) {
                    c = null;
                    MessageInf msg2 = new MessageInf(-33554432, AppLocal.getIntString("message.cannotfindcustomer"), ex);
                    msg2.show(this);
                }
                this.printTicket(this.paymentdialog.isPrintSelected() ? "Printer.CustomerPaid" : "Printer.CustomerPaid2", ticket, c);
            }
            this.editorcard.reset();
            this.editorcard.activate();
        }
    }
}

