/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jdesktop.layout.GroupLayout
 *  org.jdesktop.layout.GroupLayout$Group
 */
package com.openbravo.pos.payment;

import com.openbravo.format.Formats;
import com.openbravo.pos.customers.CustomerInfoExt;
import com.openbravo.pos.customers.DataLogicCustomers;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.forms.DataLogicSales;
import com.openbravo.pos.forms.DataLogicSystem;
import com.openbravo.pos.payment.JPaymentBank;
import com.openbravo.pos.payment.JPaymentCashPos;
import com.openbravo.pos.payment.JPaymentCheque;
import com.openbravo.pos.payment.JPaymentDebt;
import com.openbravo.pos.payment.JPaymentFree;
import com.openbravo.pos.payment.JPaymentInterface;
import com.openbravo.pos.payment.JPaymentMagcard;
import com.openbravo.pos.payment.JPaymentNotifier;
import com.openbravo.pos.payment.JPaymentRefund;
import com.openbravo.pos.payment.JPaymentSlip;
import com.openbravo.pos.payment.JPaymentVoucher;
import com.openbravo.pos.payment.PaymentInfo;
import com.openbravo.pos.payment.PaymentInfoList;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JToggleButton;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.jdesktop.layout.GroupLayout;

public abstract class JPaymentSelect
extends JDialog
implements JPaymentNotifier {
    private PaymentInfoList m_aPaymentInfo;
    private boolean printselected;
    private boolean accepted;
    private AppView app;
    private double m_dTotal;
    private CustomerInfoExt customerext;
    private DataLogicSystem dlSystem;
    private DataLogicCustomers dlCustomers;
    DataLogicSales dlSales;
    private Map<String, JPaymentInterface> payments = new HashMap<String, JPaymentInterface>();
    private String m_sTransactionID;
    private static PaymentInfo returnPayment = null;
    private JPanel jPanel2;
    private JPanel jPanel3;
    private JPanel jPanel4;
    private JPanel jPanel5;
    private JPanel jPanel6;
    private JLabel jlblPrinterStatus;
    private JButton m_jButtonAdd;
    private JButton m_jButtonCancel;
    private JButton m_jButtonOK;
    private JToggleButton m_jButtonPrint;
    private JButton m_jButtonRemove;
    private JLabel m_jLblRemainingEuros;
    private JLabel m_jLblTotalEuros1;
    private JLabel m_jRemaininglEuros;
    private JTabbedPane m_jTabPayment;
    private JLabel m_jTotalEuros;

    public static PaymentInfo getReturnPayment() {
        return returnPayment;
    }

    public static void setReturnPayment(PaymentInfo returnPayment) {
        JPaymentSelect.returnPayment = returnPayment;
    }

    protected JPaymentSelect(Frame parent, boolean modal, ComponentOrientation o) {
        super(parent, modal);
        this.initComponents();
        this.applyComponentOrientation(o);
        this.getRootPane().setDefaultButton(this.m_jButtonOK);
    }

    protected JPaymentSelect(Dialog parent, boolean modal, ComponentOrientation o) {
        super(parent, modal);
        this.initComponents();
        this.m_jButtonPrint.setVisible(true);
        this.applyComponentOrientation(o);
        if (this.printselected) {
            this.jlblPrinterStatus.setText("Printer ON");
        } else {
            this.jlblPrinterStatus.setText("Printer OFF");
        }
    }

    public void init(AppView app) {
        this.app = app;
        this.dlSystem = (DataLogicSystem)app.getBean("com.openbravo.pos.forms.DataLogicSystem");
        this.dlCustomers = (DataLogicCustomers)app.getBean("com.openbravo.pos.customers.DataLogicCustomers");
        this.dlSales = (DataLogicSales)app.getBean("com.openbravo.pos.forms.DataLogicSales");
        this.printselected = false;
        if (this.printselected) {
            this.jlblPrinterStatus.setText("Printer ON");
        } else {
            this.jlblPrinterStatus.setText("Printer OFF");
        }
    }

    public void setPrintSelected(boolean value) {
        this.printselected = value;
    }

    public boolean isPrintSelected() {
        return this.printselected;
    }

    public List<PaymentInfo> getSelectedPayments() {
        return this.m_aPaymentInfo.getPayments();
    }

    public boolean showDialog(double total, CustomerInfoExt customerext, double deposit) {
        this.m_aPaymentInfo = new PaymentInfoList();
        this.accepted = false;
        this.m_dTotal = total -= deposit;
        this.customerext = customerext;
        this.m_jButtonPrint.setVisible(true);
        this.m_jButtonPrint.setSelected(this.printselected);
        this.m_jTotalEuros.setText(Formats.CURRENCY.formatValue(this.m_dTotal));
        this.addTabs();
        this.printselected = this.m_jButtonPrint.isSelected();
        this.m_jTabPayment.removeAll();
        return this.accepted;
    }

    public boolean showDialog(double total, CustomerInfoExt customerext) {
        this.m_aPaymentInfo = new PaymentInfoList();
        this.accepted = false;
        this.m_dTotal = total;
        this.customerext = customerext;
        this.setPrintSelected(!Boolean.parseBoolean(this.app.getProperties().getProperty("till.receiptprintoff")));
        this.m_jButtonPrint.setSelected(this.printselected);
        this.m_jTotalEuros.setText(Formats.CURRENCY.formatValue(this.m_dTotal));
        if (this.printselected) {
            this.jlblPrinterStatus.setText("Printer ON");
        } else {
            this.jlblPrinterStatus.setText("Printer OFF");
        }
        Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension thisDim = this.getSize();
        int x = (screenDim.width - thisDim.width) / 2;
        int y = (screenDim.height - thisDim.height) / 2;
        this.setLocation(x, y);
        this.addTabs();
        if (this.m_jTabPayment.getTabCount() == 0) {
            this.m_aPaymentInfo.add(this.getDefaultPayment(total));
            this.accepted = true;
        } else {
            this.getRootPane().setDefaultButton(this.m_jButtonOK);
            this.printState();
            this.setVisible(true);
        }
        this.printselected = this.m_jButtonPrint.isSelected();
        this.m_jTabPayment.removeAll();
        return this.accepted;
    }

    protected abstract void addTabs();

    protected abstract void setStatusPanel(boolean var1, boolean var2);

    protected abstract PaymentInfo getDefaultPayment(double var1);

    protected void setOKEnabled(boolean value) {
        this.m_jButtonOK.setEnabled(value);
    }

    protected void setAddEnabled(boolean value) {
        this.m_jButtonAdd.setEnabled(value);
    }

    protected void addTabPayment(JPaymentCreator jpay) {
        try {
            if (this.app.getAppUserView().getUser().hasPermission(jpay.getKey()) || "payment.nequi".equals(jpay.getKey()) || "payment.daviplata".equals(jpay.getKey())) {
                JPaymentInterface jpayinterface = this.payments.get(jpay.getKey());
                if (jpayinterface == null) {
                    jpayinterface = jpay.createJPayment();
                    this.payments.put(jpay.getKey(), jpayinterface);
                }
                jpayinterface.getComponent().applyComponentOrientation(this.getComponentOrientation());
                this.m_jTabPayment.addTab(AppLocal.getIntString(jpay.getLabelKey()), new ImageIcon(this.getClass().getResource(jpay.getIconKey())), jpayinterface.getComponent());
            }
        }
        catch (Throwable t) {
            t.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error adding payment tab: " + t.getMessage());
        }
    }

    private void printState() {
        this.m_jRemaininglEuros.setText(Formats.CURRENCY.formatValue(this.m_dTotal - this.m_aPaymentInfo.getTotal()));
        this.m_jButtonRemove.setEnabled(!this.m_aPaymentInfo.isEmpty());
        this.m_jTabPayment.setSelectedIndex(0);
        ((JPaymentInterface)((Object)this.m_jTabPayment.getSelectedComponent())).activate(this.customerext, this.m_dTotal - this.m_aPaymentInfo.getTotal(), this.m_sTransactionID);
    }

    protected static Window getWindow(Component parent) {
        if (parent == null) {
            return new JFrame();
        }
        if (parent instanceof Frame || parent instanceof Dialog) {
            return (Window)parent;
        }
        return JPaymentSelect.getWindow(parent.getParent());
    }

    @Override
    public void setStatus(boolean isPositive, boolean isComplete) {
        this.setStatusPanel(isPositive, isComplete);
    }

    public void setTransactionID(String tID) {
        this.m_sTransactionID = tID;
    }

    private void initComponents() {
        this.jPanel4 = new JPanel();
        this.m_jLblTotalEuros1 = new JLabel();
        this.m_jTotalEuros = new JLabel();
        this.m_jLblRemainingEuros = new JLabel();
        this.m_jRemaininglEuros = new JLabel();
        this.jPanel6 = new JPanel();
        this.m_jButtonRemove = new JButton();
        this.m_jButtonAdd = new JButton();
        this.jPanel3 = new JPanel();
        this.m_jTabPayment = new JTabbedPane();
        this.jPanel5 = new JPanel();
        this.jPanel2 = new JPanel();
        this.m_jButtonCancel = new JButton();
        this.m_jButtonOK = new JButton();
        this.m_jButtonPrint = new JToggleButton();
        this.jlblPrinterStatus = new JLabel();
        this.setDefaultCloseOperation(2);
        this.setTitle(AppLocal.getIntString("payment.title"));
        this.setPreferredSize(new Dimension(900, 550));
        this.setResizable(true);
        this.jPanel4.setFont(new Font("Arial", 0, 14));
        this.m_jLblTotalEuros1.setFont(new Font("Arial", 0, 24));
        this.m_jLblTotalEuros1.setText(AppLocal.getIntString("label.totalcash"));
        this.m_jLblTotalEuros1.setPreferredSize(new Dimension(100, 30));
        this.m_jTotalEuros.setFont(new Font("Arial", 0, 24));
        this.m_jTotalEuros.setHorizontalAlignment(2);
        this.m_jTotalEuros.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(UIManager.getDefaults().getColor("Button.darkShadow")), BorderFactory.createEmptyBorder(1, 4, 1, 4)));
        this.m_jTotalEuros.setOpaque(true);
        this.m_jTotalEuros.setPreferredSize(new Dimension(150, 30));
        this.m_jTotalEuros.setRequestFocusEnabled(false);
        this.m_jLblRemainingEuros.setFont(new Font("Arial", 0, 24));
        this.m_jLblRemainingEuros.setText(AppLocal.getIntString("label.remainingcash"));
        this.m_jLblRemainingEuros.setPreferredSize(new Dimension(120, 30));
        this.m_jRemaininglEuros.setFont(new Font("Arial", 1, 24));
        this.m_jRemaininglEuros.setHorizontalAlignment(2);
        this.m_jRemaininglEuros.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(UIManager.getDefaults().getColor("Button.darkShadow")), BorderFactory.createEmptyBorder(1, 4, 1, 4)));
        this.m_jRemaininglEuros.setOpaque(true);
        this.m_jRemaininglEuros.setPreferredSize(new Dimension(150, 30));
        this.m_jRemaininglEuros.setRequestFocusEnabled(false);
        this.jPanel6.setLayout(new FlowLayout(1, 5, 0));
        this.m_jButtonRemove.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/btnminus.png")));
        this.m_jButtonRemove.setToolTipText("Delete Part Payment");
        this.m_jButtonRemove.setPreferredSize(new Dimension(80, 45));
        this.m_jButtonRemove.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPaymentSelect.this.m_jButtonRemoveActionPerformed(evt);
            }
        });
        this.m_jButtonAdd.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/btnplus.png")));
        this.m_jButtonAdd.setToolTipText("Add Part Payment");
        this.m_jButtonAdd.setPreferredSize(new Dimension(80, 45));
        this.m_jButtonAdd.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPaymentSelect.this.m_jButtonAddActionPerformed(evt);
            }
        });
        GroupLayout jPanel4Layout = new GroupLayout((Container)this.jPanel4);
        this.jPanel4.setLayout((LayoutManager)jPanel4Layout);
        jPanel4Layout.setHorizontalGroup((GroupLayout.Group)jPanel4Layout.createParallelGroup(1).add((GroupLayout.Group)jPanel4Layout.createSequentialGroup().add(5, 5, 5).add((Component)this.m_jLblTotalEuros1, -2, -1, -2).addPreferredGap(1).add((Component)this.m_jTotalEuros, -2, -1, -2).addPreferredGap(1).add((GroupLayout.Group)jPanel4Layout.createParallelGroup(1).add((Component)this.jPanel6, -2, -1, -2).add((GroupLayout.Group)jPanel4Layout.createSequentialGroup().add((Component)this.m_jLblRemainingEuros, -2, 120, -2).addPreferredGap(0).add((Component)this.m_jRemaininglEuros, -2, -1, -2))).add(18, 18, 18).add((Component)this.m_jButtonAdd, -2, -1, -2).addPreferredGap(0).add((Component)this.m_jButtonRemove, -2, -1, -2).add(4, 4, 4)));
        jPanel4Layout.setVerticalGroup((GroupLayout.Group)jPanel4Layout.createParallelGroup(1).add(2, (GroupLayout.Group)jPanel4Layout.createSequentialGroup().add(0, 0, Short.MAX_VALUE).add((GroupLayout.Group)jPanel4Layout.createParallelGroup(1).add(2, (Component)this.m_jButtonRemove, -2, -1, -2).add(2, (Component)this.m_jButtonAdd, -2, -1, -2))).add(2, (GroupLayout.Group)jPanel4Layout.createSequentialGroup().add(5, 5, 5).add((Component)this.jPanel6, -2, -1, -2).addPreferredGap(0).add((GroupLayout.Group)jPanel4Layout.createParallelGroup(1).add((Component)this.m_jLblTotalEuros1, -2, -1, -2).add((Component)this.m_jRemaininglEuros, -2, -1, -2).add((Component)this.m_jLblRemainingEuros, -2, -1, -2).add((Component)this.m_jTotalEuros, -2, -1, -2)).addContainerGap()));
        this.getContentPane().add((Component)this.jPanel4, "North");
        this.jPanel3.setNextFocusableComponent(this.m_jTabPayment);
        this.jPanel3.setLayout(new BorderLayout());
        this.m_jTabPayment.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        this.m_jTabPayment.setTabPlacement(2);
        this.m_jTabPayment.setTabLayoutPolicy(1);
        this.m_jTabPayment.setFont(new Font("Arial", 0, 14));
        this.m_jTabPayment.setRequestFocusEnabled(false);
        this.m_jTabPayment.addChangeListener(new ChangeListener(){

            @Override
            public void stateChanged(ChangeEvent evt) {
                JPaymentSelect.this.m_jTabPaymentStateChanged(evt);
            }
        });
        this.m_jTabPayment.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                JPaymentSelect.this.m_jTabPaymentKeyPressed(evt);
            }
        });
        this.jPanel3.add((Component)this.m_jTabPayment, "Center");
        this.getContentPane().add((Component)this.jPanel3, "Center");
        this.jPanel5.setLayout(new BorderLayout());
        this.m_jButtonCancel.setFont(new Font("Arial", 0, 12));
        this.m_jButtonCancel.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/cancel.png")));
        this.m_jButtonCancel.setText(AppLocal.getIntString("button.cancel"));
        this.m_jButtonCancel.setFocusPainted(false);
        this.m_jButtonCancel.setFocusable(false);
        this.m_jButtonCancel.setMargin(new Insets(8, 16, 8, 16));
        this.m_jButtonCancel.setPreferredSize(new Dimension(110, 45));
        this.m_jButtonCancel.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPaymentSelect.this.m_jButtonCancelActionPerformed(evt);
            }
        });
        this.jPanel2.add(this.m_jButtonCancel);
        this.m_jButtonOK.setFont(new Font("Arial", 0, 14));
        this.m_jButtonOK.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/ok.png")));
        this.m_jButtonOK.setText(AppLocal.getIntString("button.OK"));
        this.m_jButtonOK.setFocusPainted(false);
        this.m_jButtonOK.setFocusable(false);
        this.m_jButtonOK.setMargin(new Insets(8, 16, 8, 16));
        this.m_jButtonOK.setMaximumSize(new Dimension(100, 44));
        this.m_jButtonOK.setPreferredSize(new Dimension(110, 45));
        this.m_jButtonOK.setRequestFocusEnabled(false);
        this.m_jButtonOK.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPaymentSelect.this.m_jButtonOKActionPerformed(evt);
            }
        });
        this.jPanel2.add(this.m_jButtonOK);
        this.jPanel5.add((Component)this.jPanel2, "After");
        this.m_jButtonPrint.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/printer24_off.png")));
        this.m_jButtonPrint.setSelected(true);
        this.m_jButtonPrint.setToolTipText("Print Receipt");
        this.m_jButtonPrint.setFocusPainted(false);
        this.m_jButtonPrint.setFocusable(false);
        this.m_jButtonPrint.setMargin(new Insets(8, 16, 8, 16));
        this.m_jButtonPrint.setPreferredSize(new Dimension(80, 45));
        this.m_jButtonPrint.setRequestFocusEnabled(false);
        this.m_jButtonPrint.setSelectedIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/printer24.png")));
        this.m_jButtonPrint.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPaymentSelect.this.m_jButtonPrintActionPerformed(evt);
            }
        });
        this.jPanel5.add((Component)this.m_jButtonPrint, "Before");
        this.jlblPrinterStatus.setFont(new Font("Arial", 0, 24));
        this.jlblPrinterStatus.setHorizontalAlignment(0);
        ResourceBundle bundle = ResourceBundle.getBundle("pos_messages");
        this.jlblPrinterStatus.setText(bundle.getString("label.printerstatusOn"));
        this.jPanel5.add((Component)this.jlblPrinterStatus, "Center");
        this.getContentPane().add((Component)this.jPanel5, "South");
        this.setSize(new Dimension(758, 497));
        this.setLocationRelativeTo(null);
    }

    private void m_jButtonRemoveActionPerformed(ActionEvent evt) {
        this.m_aPaymentInfo.removeLast();
        this.printState();
    }

    private void m_jButtonAddActionPerformed(ActionEvent evt) {
        PaymentInfo returnPayment = ((JPaymentInterface)((Object)this.m_jTabPayment.getSelectedComponent())).executePayment();
        if (returnPayment != null) {
            this.m_aPaymentInfo.add(returnPayment);
            this.printState();
        }
    }

    private void m_jTabPaymentStateChanged(ChangeEvent evt) {
        if (this.m_jTabPayment.getSelectedComponent() != null) {
            ((JPaymentInterface)((Object)this.m_jTabPayment.getSelectedComponent())).activate(this.customerext, this.m_dTotal - this.m_aPaymentInfo.getTotal(), this.m_sTransactionID);
            this.m_jRemaininglEuros.setText(Formats.CURRENCY.formatValue(this.m_dTotal - this.m_aPaymentInfo.getTotal()));
        }
    }

    private void m_jButtonOKActionPerformed(ActionEvent evt) {
        SwingWorker worker = new SwingWorker(){

            protected Object doInBackground() throws Exception {
                JPaymentSelect.setReturnPayment(((JPaymentInterface)((Object)JPaymentSelect.this.m_jTabPayment.getSelectedComponent())).executePayment());
                return null;
            }

            @Override
            public void done() {
                if (returnPayment != null) {
                    JPaymentSelect.this.m_aPaymentInfo.add(returnPayment);
                    JPaymentSelect.this.accepted = true;
                    JPaymentSelect.this.dispose();
                }
            }
        };
        worker.execute();
    }

    private void m_jButtonCancelActionPerformed(ActionEvent evt) {
        this.dispose();
    }

    private void m_jButtonPrintActionPerformed(ActionEvent evt) {
        if (!this.m_jButtonPrint.isSelected()) {
            this.jlblPrinterStatus.setText("Printer OFF");
        } else {
            this.jlblPrinterStatus.setText("Printer ON");
        }
    }

    private void m_jTabPaymentKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 112 || evt.getKeyCode() == 113) {
            // empty if block
        }
    }

    public static interface JPaymentCreator {
        public JPaymentInterface createJPayment();

        public String getKey();

        public String getLabelKey();

        public String getIconKey();
    }

    public class JPaymentSlipCreator
    implements JPaymentCreator {
        @Override
        public JPaymentInterface createJPayment() {
            return new JPaymentSlip(JPaymentSelect.this);
        }

        @Override
        public String getKey() {
            return "payment.slip";
        }

        @Override
        public String getLabelKey() {
            return "tab.slip";
        }

        @Override
        public String getIconKey() {
            return "/com/openbravo/images/slip.png";
        }
    }

    public class JPaymentBankCreator
    implements JPaymentCreator {
        @Override
        public JPaymentInterface createJPayment() {
            return new JPaymentBank(JPaymentSelect.this);
        }

        @Override
        public String getKey() {
            return "payment.bank";
        }

        @Override
        public String getLabelKey() {
            return "tab.bank";
        }

        @Override
        public String getIconKey() {
            return "/com/openbravo/images/bank.png";
        }
    }

    public class JPaymentMagcardRefundCreator
    implements JPaymentCreator {
        @Override
        public JPaymentInterface createJPayment() {
            return new JPaymentMagcard(JPaymentSelect.this.app, JPaymentSelect.this);
        }

        @Override
        public String getKey() {
            return "refund.magcard";
        }

        @Override
        public String getLabelKey() {
            return "tab.magcard";
        }

        @Override
        public String getIconKey() {
            return "/com/openbravo/images/ccard.png";
        }
    }

    public class JPaymentVoucherRefundCreator
    implements JPaymentCreator {
        @Override
        public JPaymentInterface createJPayment() {
            return new JPaymentRefund(JPaymentSelect.this, "voucherout");
        }

        @Override
        public String getKey() {
            return "refund.voucher";
        }

        @Override
        public String getLabelKey() {
            return "tab.voucher";
        }

        @Override
        public String getIconKey() {
            return "/com/openbravo/images/voucher.png";
        }
    }

    public class JPaymentChequeRefundCreator
    implements JPaymentCreator {
        @Override
        public JPaymentInterface createJPayment() {
            return new JPaymentRefund(JPaymentSelect.this, "chequerefund");
        }

        @Override
        public String getKey() {
            return "refund.cheque";
        }

        @Override
        public String getLabelKey() {
            return "tab.chequerefund";
        }

        @Override
        public String getIconKey() {
            return "/com/openbravo/images/cheque.png";
        }
    }

    public class JPaymentCashRefundCreator
    implements JPaymentCreator {
        @Override
        public JPaymentInterface createJPayment() {
            return new JPaymentRefund(JPaymentSelect.this, "cashrefund");
        }

        @Override
        public String getKey() {
            return "refund.cash";
        }

        @Override
        public String getLabelKey() {
            return "tab.cashrefund";
        }

        @Override
        public String getIconKey() {
            return "/com/openbravo/images/cash.png";
        }
    }

    public class JPaymentDebtCreator
    implements JPaymentCreator {
        @Override
        public JPaymentInterface createJPayment() {
            return new JPaymentDebt(JPaymentSelect.this);
        }

        @Override
        public String getKey() {
            return "payment.debt";
        }

        @Override
        public String getLabelKey() {
            return "tab.debt";
        }

        @Override
        public String getIconKey() {
            return "/com/openbravo/images/customer.png";
        }
    }

    public class JPaymentFreeCreator
    implements JPaymentCreator {
        @Override
        public JPaymentInterface createJPayment() {
            return new JPaymentFree(JPaymentSelect.this);
        }

        @Override
        public String getKey() {
            return "payment.free";
        }

        @Override
        public String getLabelKey() {
            return "tab.free";
        }

        @Override
        public String getIconKey() {
            return "/com/openbravo/images/wallet.png";
        }
    }

    public class JPaymentMagcardCreator
    implements JPaymentCreator {
        @Override
        public JPaymentInterface createJPayment() {
            return new JPaymentMagcard(JPaymentSelect.this.app, JPaymentSelect.this);
        }

        @Override
        public String getKey() {
            return "payment.magcard";
        }

        @Override
        public String getLabelKey() {
            return "tab.magcard";
        }

        @Override
        public String getIconKey() {
            return "/com/openbravo/images/ccard.png";
        }
    }

    public class JPaymentVoucherCreator
    implements JPaymentCreator {
        @Override
        public JPaymentInterface createJPayment() {
            return new JPaymentVoucher(JPaymentSelect.this.app, JPaymentSelect.this, "voucherin");
        }

        @Override
        public String getKey() {
            return "payment.voucher";
        }

        @Override
        public String getLabelKey() {
            return "tab.voucher";
        }

        @Override
        public String getIconKey() {
            return "/com/openbravo/images/voucher.png";
        }
    }

    public class JPaymentChequeCreator
    implements JPaymentCreator {
        @Override
        public JPaymentInterface createJPayment() {
            return new JPaymentCheque(JPaymentSelect.this);
        }

        @Override
        public String getKey() {
            return "payment.cheque";
        }

        @Override
        public String getLabelKey() {
            return "tab.cheque";
        }

        @Override
        public String getIconKey() {
            return "/com/openbravo/images/cheque.png";
        }
    }

    public class JPaymentCashCreator
    implements JPaymentCreator {
        @Override
        public JPaymentInterface createJPayment() {
            return new JPaymentCashPos(JPaymentSelect.this, JPaymentSelect.this.dlSystem);
        }

        @Override
        public String getKey() {
            return "payment.cash";
        }

        @Override
        public String getLabelKey() {
            return "tab.cash";
        }

        @Override
        public String getIconKey() {
            return "/com/openbravo/images/cash.png";
        }
    }

    public class JPaymentDaviplataCreator
    implements JPaymentCreator {
        @Override
        public JPaymentInterface createJPayment() {
            return new JPaymentBank(JPaymentSelect.this, "daviplata");
        }

        @Override
        public String getKey() {
            return "payment.daviplata";
        }

        @Override
        public String getLabelKey() {
            return "tab.daviplata";
        }

        @Override
        public String getIconKey() {
            return "/com/openbravo/images/bank.png";
        }
    }

    public class JPaymentNequiCreator
    implements JPaymentCreator {
        @Override
        public JPaymentInterface createJPayment() {
            return new JPaymentBank(JPaymentSelect.this, "nequi");
        }

        @Override
        public String getKey() {
            return "payment.nequi";
        }

        @Override
        public String getLabelKey() {
            return "tab.nequi";
        }

        @Override
        public String getIconKey() {
            return "/com/openbravo/images/bank.png";
        }
    }
}

