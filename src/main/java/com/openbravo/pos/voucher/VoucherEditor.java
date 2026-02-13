/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.voucher;

import com.openbravo.basic.BasicException;
import com.openbravo.data.gui.ComboBoxValModel;
import com.openbravo.data.user.DirtyManager;
import com.openbravo.data.user.EditorRecord;
import com.openbravo.format.Formats;
import com.openbravo.pos.customers.CustomerInfo;
import com.openbravo.pos.customers.DataLogicCustomers;
import com.openbravo.pos.customers.JCustomerFinder;
import com.openbravo.pos.customers.JDialogNewCustomer;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.forms.DataLogicSystem;
import com.openbravo.pos.util.ValidateBuilder;
import com.openbravo.pos.voucher.JDialogReportPanel;
import com.openbravo.pos.voucher.VoucherInfo;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;

public final class VoucherEditor
extends JPanel
implements EditorRecord {
    private static final long serialVersionUID = 1L;
    private static final DateFormat m_simpledate = new SimpleDateFormat("MM-yy");
    private Object id;
    private final DataLogicCustomers dlCustomers;
    private final DataLogicSystem dlSystem;
    private CustomerInfo customerInfo;
    private final AppView m_app;
    private final ComboBoxValModel<String> m_ReasonModel;
    private JButton jButtonPrint;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel5;
    private JTextField m_jAmount;
    private JTextField m_jCustomer;
    private JTextField m_jNumber;
    private JComboBox<String> webCBCustomer;

    public VoucherEditor(DirtyManager dirty, AppView app) {
        this.m_app = app;
        this.initComponents();
        this.dlCustomers = (DataLogicCustomers)app.getBean("com.openbravo.pos.customers.DataLogicCustomers");
        this.dlSystem = (DataLogicSystem)app.getBean("com.openbravo.pos.forms.DataLogicSystem");
        this.m_jNumber.getDocument().addDocumentListener(dirty);
        this.m_jCustomer.getDocument().addDocumentListener(dirty);
        this.m_jAmount.getDocument().addDocumentListener(dirty);
        this.jButtonPrint.setVisible(false);
        this.m_ReasonModel = new ComboBoxValModel();
        this.m_ReasonModel.add(AppLocal.getIntString("cboption.find"));
        this.m_ReasonModel.add(AppLocal.getIntString("cboption.create"));
        this.webCBCustomer.setModel(this.m_ReasonModel);
        this.writeValueEOF();
    }

    @Override
    public void writeValueEOF() {
        this.id = null;
        this.m_jNumber.setText(null);
        this.m_jNumber.setEnabled(false);
        this.m_jCustomer.setText(null);
        this.m_jCustomer.setEnabled(false);
        this.m_jAmount.setText(null);
        this.m_jAmount.setEnabled(false);
    }

    @Override
    public void writeValueInsert() {
        this.id = UUID.randomUUID().toString();
        this.m_jNumber.setText(this.generateVoucherNumber());
        this.m_jNumber.setEnabled(true);
        this.m_jCustomer.setText(null);
        this.m_jCustomer.setEnabled(true);
        this.m_jAmount.setText(null);
        this.m_jAmount.setEnabled(true);
        this.jButtonPrint.setEnabled(true);
    }

    @Override
    public void writeValueDelete(Object value) {
        try {
            Object[] attr = (Object[])value;
            this.id = attr[0];
            this.m_jNumber.setText(Formats.STRING.formatValue(attr[1]));
            this.m_jNumber.setEnabled(false);
            this.customerInfo = this.dlCustomers.getCustomerInfo(attr[2].toString());
            this.m_jCustomer.setText(this.customerInfo.getName());
            this.m_jCustomer.setEnabled(false);
            this.m_jAmount.setText(Formats.CURRENCY.formatValue(attr[3]));
            this.m_jAmount.setEnabled(false);
        }
        catch (BasicException ex) {
            Logger.getLogger(VoucherEditor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void writeValueEdit(Object value) {
        try {
            Object[] attr = (Object[])value;
            this.id = attr[0];
            this.m_jNumber.setText(Formats.STRING.formatValue(attr[1]));
            this.m_jNumber.setEnabled(true);
            this.customerInfo = this.dlCustomers.getCustomerInfo(attr[2].toString());
            this.m_jCustomer.setText(this.customerInfo.getName());
            this.m_jCustomer.setEnabled(true);
            this.m_jAmount.setText(Formats.CURRENCY.formatValue(attr[3]));
            this.m_jAmount.setEnabled(true);
        }
        catch (BasicException ex) {
            Logger.getLogger(VoucherEditor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Object createValue() throws BasicException {
        Object[] attr = new Object[]{this.id, this.m_jNumber.getText(), this.customerInfo.getId(), Formats.DOUBLE.parseValue(this.m_jAmount.getText())};
        return attr;
    }

    @Override
    public Component getComponent() {
        return this;
    }

    @Override
    public void refresh() {
    }

    private void initComponents() {
        this.jLabel2 = new JLabel();
        this.m_jNumber = new JTextField();
        this.jLabel3 = new JLabel();
        this.m_jCustomer = new JTextField();
        this.m_jAmount = new JTextField();
        this.jLabel5 = new JLabel();
        this.jButtonPrint = new JButton();
        this.webCBCustomer = new JComboBox();
        this.setFont(new Font("Arial", 0, 14));
        this.jLabel2.setFont(new Font("Arial", 0, 14));
        this.jLabel2.setText(AppLocal.getIntString("label.Number"));
        this.jLabel2.setPreferredSize(new Dimension(100, 30));
        this.m_jNumber.setPreferredSize(new Dimension(240, 30));
        this.jLabel3.setFont(new Font("Arial", 0, 14));
        this.jLabel3.setText(AppLocal.getIntString("label.customer"));
        this.jLabel3.setPreferredSize(new Dimension(100, 30));
        this.m_jCustomer.setEditable(false);
        this.m_jCustomer.setPreferredSize(new Dimension(240, 30));
        this.m_jAmount.setPreferredSize(new Dimension(240, 30));
        this.jLabel5.setFont(new Font("Arial", 0, 14));
        this.jLabel5.setText(AppLocal.getIntString("label.paymenttotal"));
        this.jLabel5.setPreferredSize(new Dimension(100, 30));
        this.jButtonPrint.setFont(new Font("Arial", 0, 12));
        this.jButtonPrint.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/printer24.png")));
        this.jButtonPrint.setToolTipText(AppLocal.getIntString("button.print"));
        this.jButtonPrint.setFocusPainted(false);
        this.jButtonPrint.setFocusable(false);
        this.jButtonPrint.setMargin(new Insets(8, 14, 8, 14));
        this.jButtonPrint.setPreferredSize(new Dimension(80, 45));
        this.jButtonPrint.setRequestFocusEnabled(false);
        this.jButtonPrint.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                VoucherEditor.this.jButtonPrintActionPerformed(evt);
            }
        });
        this.webCBCustomer.setModel(new DefaultComboBoxModel<String>(new String[]{"Find", "Create"}));
        this.webCBCustomer.setToolTipText(AppLocal.getIntString("tooltip.vouchercustomer"));
        this.webCBCustomer.setFont(new Font("Arial", 0, 14));
        this.webCBCustomer.setPreferredSize(new Dimension(110, 45));
        this.webCBCustomer.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                VoucherEditor.this.webCBCustomerActionPerformed(evt);
            }
        });
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(187, 187, 187).addComponent(this.jButtonPrint, -2, -1, -2)).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel2, -2, -1, -2).addComponent(this.jLabel3, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.m_jCustomer, -2, -1, -2).addComponent(this.m_jNumber, -2, -1, -2))).addGroup(layout.createSequentialGroup().addComponent(this.jLabel5, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.m_jAmount, -2, -1, -2))).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.webCBCustomer, -2, 110, -2))).addContainerGap(40, Short.MAX_VALUE)));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(11, 11, 11).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel2, -2, -1, -2).addComponent(this.m_jNumber, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel3, -2, -1, -2).addComponent(this.m_jCustomer, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel5, -2, -1, -2).addComponent(this.m_jAmount, -2, -1, -2))).addComponent(this.webCBCustomer, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jButtonPrint, -2, -1, -2).addContainerGap()));
    }

    private void jButtonPrintActionPerformed(ActionEvent evt) {
        try {
            VoucherInfo voucherInfo = this.dlCustomers.getVoucherInfoAll(this.id.toString());
            BufferedImage image = this.dlSystem.getResourceAsImage("Window.Logo");
            if (voucherInfo != null) {
                JDialogReportPanel dialog = JDialogReportPanel.getDialog(this, this.m_app, voucherInfo, image);
                dialog.setVisible(true);
            }
        }
        catch (BasicException ex) {
            Logger.getLogger(VoucherEditor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void webCBCustomerActionPerformed(ActionEvent evt) {
        if (this.webCBCustomer.getSelectedIndex() == 0) {
            JCustomerFinder finder = JCustomerFinder.getCustomerFinder(this, this.dlCustomers);
            finder.setVisible(true);
            this.customerInfo = finder.getSelectedCustomer();
            if (finder.getSelectedCustomer() != null) {
                this.m_jCustomer.setText(this.customerInfo.getName());
            }
        } else {
            JDialogNewCustomer dialog = JDialogNewCustomer.getDialog(this, this.m_app);
            dialog.setVisible(true);
            this.customerInfo = dialog.getSelectedCustomer();
            if (dialog.getSelectedCustomer() != null) {
                this.m_jCustomer.setText(this.customerInfo.getName());
            }
        }
    }

    public boolean isDataValid() {
        ValidateBuilder validate = new ValidateBuilder(this);
        validate.setValidate(this.m_jNumber.getText(), ValidateBuilder.IS_NOT_EMPTY, AppLocal.getIntString("message.message.emptynumber"));
        validate.setValidate(this.m_jCustomer.getText(), ValidateBuilder.IS_NOT_EMPTY, AppLocal.getIntString("message.emptycustomer"));
        validate.setValidate(this.m_jAmount.getText(), ValidateBuilder.IS_DOUBLE, AppLocal.getIntString("message.numericamount"));
        return validate.getValid();
    }

    public String generateVoucherNumber() {
        String result = "";
        try {
            result = "VO-";
            String date = m_simpledate.format(new Date());
            result = result + date;
            String lastNumber = (String)this.dlCustomers.getVoucherNumber().find((Object)result);
            int newNumber = 1;
            if (lastNumber != null) {
                newNumber = Integer.parseInt(lastNumber) + 1;
            }
            result = result + "-" + this.getNewNumber(newNumber);
            return result;
        }
        catch (BasicException basicException) {
            return result;
        }
    }

    private String getNewNumber(int newNumber) {
        String newNo = newNumber + "";
        String zero = "";
        for (int i = 0; i < 3 - newNo.length(); ++i) {
            zero = zero + "0";
        }
        return zero + newNo;
    }
}

