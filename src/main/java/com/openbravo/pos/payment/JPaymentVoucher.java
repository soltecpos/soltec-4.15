/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.payment;

import com.openbravo.basic.BasicException;
import com.openbravo.data.gui.ComboBoxValModel;
import com.openbravo.data.loader.SentenceList;
import com.openbravo.editor.JEditorCurrencyPositive;
import com.openbravo.editor.JEditorKeys;
import com.openbravo.format.Formats;
import com.openbravo.pos.customers.CustomerInfoExt;
import com.openbravo.pos.customers.DataLogicCustomers;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.forms.DataLogicSales;
import com.openbravo.pos.payment.JPaymentInterface;
import com.openbravo.pos.payment.JPaymentNotifier;
import com.openbravo.pos.payment.PaymentInfo;
import com.openbravo.pos.payment.PaymentInfoTicket;
import com.openbravo.pos.util.RoundUtils;
import com.openbravo.pos.voucher.VoucherInfo;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;
import javax.swing.UIManager;

public class JPaymentVoucher
extends JPanel
implements JPaymentInterface {
    private static final long serialVersionUID = 1L;
    private final JPaymentNotifier m_notifier;
    private DataLogicSales dlSales;
    private DataLogicCustomers dlCustomers;
    private ComboBoxValModel<VoucherInfo> m_VoucherModel;
    private SentenceList<VoucherInfo> m_sentvouch;
    private VoucherInfo m_voucherInfo;
    private double m_dTicket;
    private double m_dTotal;
    private final String m_sVoucher;
    private String m_sVoucher1;
    private JLabel jLabel1;
    private JLabel jLabel5;
    private JPanel jPanel1;
    private JPanel jPanel11;
    private JPanel jPanel12;
    private JPanel jPanel4;
    private JEditorKeys m_jKeys;
    private JLabel m_jMoneyEuros;
    private JEditorCurrencyPositive m_jTendered;
    private JComboBox<VoucherInfo> m_jVoucher;
    private JLabel voucherID;
    private JLabel webLblCustomer;
    private JLabel webLblcustomerName;

    public JPaymentVoucher(AppView app, JPaymentNotifier notifier, String sVoucher) {
        this.m_notifier = notifier;
        this.m_sVoucher = sVoucher;
        this.m_dTotal = 0.0;
        this.init(app);
        this.m_jTendered.addPropertyChangeListener("Edition", new RecalculateState());
    }

    private void init(AppView app) {
        try {
            this.dlSales = (DataLogicSales)app.getBean("com.openbravo.pos.forms.DataLogicSales");
            this.dlCustomers = (DataLogicCustomers)app.getBean("com.openbravo.pos.customers.DataLogicCustomers");
            this.m_sentvouch = this.dlSales.getVoucherList();
            this.initComponents();
            this.m_VoucherModel = new ComboBoxValModel();
            List<VoucherInfo> a = this.m_sentvouch.list();
            a.add(0, null);
            this.m_VoucherModel = new ComboBoxValModel<VoucherInfo>(a);
            this.m_jVoucher.setModel(this.m_VoucherModel);
            this.webLblcustomerName.setText(null);
        }
        catch (BasicException basicException) {
            // empty catch block
        }
    }

    @Override
    public void activate(CustomerInfoExt customerext, double dTotal, String transID) {
        this.m_dTotal = dTotal;
        this.m_jTendered.reset();
        this.m_jKeys.setEnabled(false);
        this.m_jTendered.setEnabled(false);
        this.printState();
    }

    @Override
    public Component getComponent() {
        return this;
    }

    @Override
    public PaymentInfo executePayment() {
        try {
            String id = this.m_VoucherModel.getSelectedKey().toString();
            VoucherInfo m_voucherInfo1 = this.dlCustomers.getVoucherInfo(id);
            this.m_sVoucher1 = m_voucherInfo1.getVoucherNumber();
        }
        catch (BasicException basicException) {
            // empty catch block
        }
        return new PaymentInfoTicket(this.m_dTicket, this.m_sVoucher, this.m_sVoucher1);
    }

    private void printState() {
        Double value = this.m_jTendered.getDoubleValue();
        this.m_dTicket = value == null ? 0.0 : value;
        this.m_jMoneyEuros.setText(Formats.CURRENCY.formatValue(this.m_dTicket));
        int iCompare = RoundUtils.compare(this.m_dTicket, this.m_dTotal);
        this.m_notifier.setStatus(this.m_dTicket > 0.0, iCompare >= 0);
    }

    private void initComponents() {
        this.jPanel4 = new JPanel();
        this.jLabel5 = new JLabel();
        this.m_jVoucher = new JComboBox();
        this.jLabel1 = new JLabel();
        this.m_jMoneyEuros = new JLabel();
        this.webLblCustomer = new JLabel();
        this.webLblcustomerName = new JLabel();
        this.voucherID = new JLabel();
        this.jPanel11 = new JPanel();
        this.jPanel12 = new JPanel();
        this.m_jKeys = new JEditorKeys();
        this.jPanel1 = new JPanel();
        this.m_jTendered = new JEditorCurrencyPositive();
        this.setLayout(new BorderLayout());
        this.jLabel5.setFont(new Font("Arial", 1, 18));
        this.jLabel5.setLabelFor(this.m_jVoucher);
        this.jLabel5.setText(AppLocal.getIntString("label.voucher"));
        this.jLabel5.setPreferredSize(new Dimension(100, 30));
        this.m_jVoucher.setFont(new Font("Arial", 0, 14));
        this.m_jVoucher.setPreferredSize(new Dimension(180, 30));
        this.m_jVoucher.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPaymentVoucher.this.m_jVoucherActionPerformed(evt);
            }
        });
        this.jLabel1.setFont(new Font("Arial", 1, 18));
        this.jLabel1.setText(AppLocal.getIntString("label.voucherValue"));
        this.jLabel1.setPreferredSize(new Dimension(100, 30));
        this.m_jMoneyEuros.setBackground(new Color(204, 255, 51));
        this.m_jMoneyEuros.setFont(new Font("Arial", 1, 18));
        this.m_jMoneyEuros.setHorizontalAlignment(4);
        this.m_jMoneyEuros.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(UIManager.getDefaults().getColor("Button.darkShadow")), BorderFactory.createEmptyBorder(1, 4, 1, 4)));
        this.m_jMoneyEuros.setOpaque(true);
        this.m_jMoneyEuros.setPreferredSize(new Dimension(180, 30));
        this.webLblCustomer.setText(AppLocal.getIntString("label.customer"));
        this.webLblCustomer.setToolTipText("");
        this.webLblCustomer.setFont(new Font("Arial", 1, 14));
        this.webLblCustomer.setPreferredSize(new Dimension(100, 30));
        this.webLblcustomerName.setText(AppLocal.getIntString("label.customer"));
        this.webLblcustomerName.setToolTipText("");
        this.webLblcustomerName.setFont(new Font("Arial", 0, 14));
        this.webLblcustomerName.setPreferredSize(new Dimension(100, 30));
        this.voucherID.setText(AppLocal.getIntString("label.customer"));
        this.voucherID.setToolTipText("");
        this.voucherID.setFont(new Font("Arial", 0, 14));
        this.voucherID.setPreferredSize(new Dimension(100, 30));
        GroupLayout jPanel4Layout = new GroupLayout(this.jPanel4);
        this.jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel4Layout.createSequentialGroup().addContainerGap().addComponent(this.webLblCustomer, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.webLblcustomerName, -1, -1, Short.MAX_VALUE)).addGroup(jPanel4Layout.createSequentialGroup().addGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel4Layout.createSequentialGroup().addGap(10, 10, 10).addComponent(this.jLabel1, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.m_jMoneyEuros, -2, -1, -2)).addGroup(jPanel4Layout.createSequentialGroup().addContainerGap().addComponent(this.jLabel5, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.m_jVoucher, -2, -1, -2))).addGap(0, 0, Short.MAX_VALUE)).addGroup(jPanel4Layout.createSequentialGroup().addGap(120, 120, 120).addComponent(this.voucherID, -1, -1, Short.MAX_VALUE)));
        jPanel4Layout.setVerticalGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel4Layout.createSequentialGroup().addContainerGap().addGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel5, -2, -1, -2).addComponent(this.m_jVoucher, -2, -1, -2)).addGap(18, 18, 18).addGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel1, -2, -1, -2).addComponent(this.m_jMoneyEuros, -2, -1, -2)).addGap(18, 18, 18).addGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.webLblCustomer, -2, -1, -2).addComponent(this.webLblcustomerName, -2, -1, -2)).addGap(18, 18, 18).addComponent(this.voucherID, -2, -1, -2).addGap(197, 197, 197)));
        this.add((Component)this.jPanel4, "Center");
        this.jPanel11.setLayout(new BorderLayout());
        this.jPanel12.setLayout(new BoxLayout(this.jPanel12, 1));
        this.jPanel12.add(this.m_jKeys);
        this.jPanel1.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        this.jPanel1.setLayout(new BorderLayout());
        this.m_jTendered.setFont(new Font("Arial", 0, 12));
        this.m_jTendered.setPreferredSize(new Dimension(130, 30));
        this.jPanel1.add((Component)this.m_jTendered, "Center");
        this.jPanel12.add(this.jPanel1);
        this.jPanel11.add((Component)this.jPanel12, "North");
        this.add((Component)this.jPanel11, "East");
    }

    private void m_jVoucherActionPerformed(ActionEvent evt) {
        if (this.m_VoucherModel.getSelectedKey() != null) {
            try {
                String id = this.m_VoucherModel.getSelectedKey().toString();
                VoucherInfo m_voucherInfo = this.dlCustomers.getVoucherInfo(id);
                if (m_voucherInfo != null) {
                    this.m_jTendered.setDoubleValue(m_voucherInfo.getAmount());
                    this.m_jMoneyEuros.setText(Formats.CURRENCY.formatValue(m_voucherInfo.getAmount()));
                    this.webLblcustomerName.setText(m_voucherInfo.getCustomerName());
                    this.printState();
                }
            }
            catch (BasicException basicException) {
                // empty catch block
            }
        }
    }

    private class RecalculateState
    implements PropertyChangeListener {
        private RecalculateState() {
        }

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            JPaymentVoucher.this.printState();
        }
    }
}

