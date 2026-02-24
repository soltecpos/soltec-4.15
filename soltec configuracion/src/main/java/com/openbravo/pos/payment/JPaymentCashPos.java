/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.payment;

import com.openbravo.editor.JEditorCurrencyPositive;
import com.openbravo.editor.JEditorKeys;
import com.openbravo.format.Formats;
import com.openbravo.pos.customers.CustomerInfoExt;
import com.openbravo.pos.forms.AppConfig;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.DataLogicSystem;
import com.openbravo.pos.payment.JPaymentInterface;
import com.openbravo.pos.payment.JPaymentNotifier;
import com.openbravo.pos.payment.PaymentInfo;
import com.openbravo.pos.payment.PaymentInfoCash;
import com.openbravo.pos.util.RoundUtils;
import com.openbravo.pos.util.ThumbNailBuilder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.net.URL;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class JPaymentCashPos
extends JPanel
implements JPaymentInterface {
    private final JPaymentNotifier m_notifier;
    private double m_dPaid;
    private double m_dTotal;
    private final Boolean priceWith00;
    private JLabel jLabel6;
    private JLabel jLabel8;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JPanel jPanel3;
    private JPanel jPanel4;
    private JPanel jPanel5;
    private JPanel jPanel6;
    private JLabel m_jChangeEuros;
    private JEditorKeys m_jKeys;
    private JLabel m_jMoneyEuros;
    private JEditorCurrencyPositive m_jTendered;

    public JPaymentCashPos(JPaymentNotifier notifier, DataLogicSystem dlSystem) {
        this.m_notifier = notifier;
        this.initComponents();
        this.m_jTendered.addPropertyChangeListener("Edition", new RecalculateState());
        this.m_jTendered.addEditorKeys(this.m_jKeys);
        AppConfig m_config = new AppConfig(new File(System.getProperty("user.home"), "soltecpos.properties"));
        m_config.load();
        this.priceWith00 = "true".equals(m_config.getProperty("till.pricewith00"));
        if (this.priceWith00.booleanValue()) {
            this.m_jKeys.dotIs00(true);
        }
        ScriptPaymentCash payment = new ScriptPaymentCash(dlSystem);
        
        // Updated buttons with icons from iconosmonedasybilletes
        payment.addImageButton("50m", 50.0);
        payment.addImageButton("100m", 100.0);
        payment.addImageButton("200m", 200.0);
        payment.addImageButton("500m", 500.0);
        payment.addImageButton("1000m", 1000.0);
        payment.addImageButton("1000b", 1000.0);
        payment.addImageButton("2000b", 2000.0);
        payment.addImageButton("5000b", 5000.0);
        payment.addImageButton("10000b", 10000.0);
        payment.addImageButton("20000b", 20000.0);
        payment.addImageButton("50000b", 50000.0);
        payment.addImageButton("100000b", 100000.0);
    }

    @Override
    public void activate(CustomerInfoExt customerext, double dTotal, String transID) {
        this.m_dTotal = dTotal;
        this.m_jTendered.reset();
        this.m_jTendered.activate();
        this.printState();
    }

    @Override
    public PaymentInfo executePayment() {
        if (this.m_dPaid - this.m_dTotal >= 0.0) {
            return new PaymentInfoCash(this.m_dTotal, this.m_dPaid);
        }
        return new PaymentInfoCash(this.m_dPaid, this.m_dPaid);
    }

    @Override
    public Component getComponent() {
        return this;
    }

    private void printState() {
        Double value = this.m_jTendered.getDoubleValue();
        this.m_dPaid = value == null || value == 0.0 ? this.m_dTotal : value;
        int iCompare = RoundUtils.compare(this.m_dPaid, this.m_dTotal);
        this.m_jMoneyEuros.setText(Formats.CURRENCY.formatValue(this.m_dPaid));
        this.m_jChangeEuros.setText(iCompare > 0 ? Formats.CURRENCY.formatValue(this.m_dPaid - this.m_dTotal) : null);
        this.m_notifier.setStatus(this.m_dPaid > 0.0, iCompare >= 0);
    }

    private void initComponents() {
        this.jPanel5 = new JPanel();
        this.jPanel4 = new JPanel();
        this.m_jChangeEuros = new JLabel();
        this.jLabel6 = new JLabel();
        this.jLabel8 = new JLabel();
        this.m_jMoneyEuros = new JLabel();
        this.jPanel6 = new JPanel();
        this.jPanel2 = new JPanel();
        this.jPanel1 = new JPanel();
        this.m_jKeys = new JEditorKeys();
        this.jPanel3 = new JPanel();
        this.m_jTendered = new JEditorCurrencyPositive();
        this.setPreferredSize(new Dimension(800, 400));
        this.setLayout(new BorderLayout());
        this.jPanel5.setLayout(new BorderLayout());
        this.jPanel4.setFont(new Font("Arial", 0, 12));
        this.jPanel4.setPreferredSize(new Dimension(600, 70));
        this.jPanel4.setLayout(null);
        this.m_jChangeEuros.setBackground(new Color(255, 255, 255));
        this.m_jChangeEuros.setFont(new Font("Arial", 1, 18));
        this.m_jChangeEuros.setHorizontalAlignment(4);
        this.m_jChangeEuros.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(UIManager.getDefaults().getColor("Button.darkShadow")), BorderFactory.createEmptyBorder(1, 4, 1, 4)));
        this.m_jChangeEuros.setOpaque(true);
        this.m_jChangeEuros.setPreferredSize(new Dimension(180, 30));
        this.jPanel4.add(this.m_jChangeEuros);
        this.m_jChangeEuros.setBounds(120, 36, 180, 30);
        this.jLabel6.setFont(new Font("Arial", 0, 18));
        this.jLabel6.setText(AppLocal.getIntString("label.ChangeCash"));
        this.jLabel6.setPreferredSize(new Dimension(100, 30));
        this.jPanel4.add(this.jLabel6);
        this.jLabel6.setBounds(10, 36, 100, 30);
        this.jLabel8.setFont(new Font("Arial", 0, 18));
        this.jLabel8.setText(AppLocal.getIntString("label.InputCash"));
        this.jLabel8.setPreferredSize(new Dimension(100, 30));
        this.jPanel4.add(this.jLabel8);
        this.jLabel8.setBounds(10, 4, 100, 30);
        this.m_jMoneyEuros.setBackground(new Color(255, 255, 255));
        this.m_jMoneyEuros.setFont(new Font("Arial", 0, 18));
        this.m_jMoneyEuros.setHorizontalAlignment(4);
        this.m_jMoneyEuros.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(UIManager.getDefaults().getColor("Button.darkShadow")), BorderFactory.createEmptyBorder(1, 4, 1, 4)));
        this.m_jMoneyEuros.setOpaque(true);
        this.m_jMoneyEuros.setPreferredSize(new Dimension(180, 30));
        this.jPanel4.add(this.m_jMoneyEuros);
        this.m_jMoneyEuros.setBounds(120, 4, 180, 30);
        this.jPanel5.add((Component)this.jPanel4, "North");
        this.jPanel6.setLayout(new GridLayout(0, 3, 5, 5));
        this.jPanel5.add((Component)this.jPanel6, "Center");
        this.add((Component)this.jPanel5, "Center");
        this.jPanel2.setLayout(new BorderLayout());
        this.jPanel1.setLayout(new BoxLayout(this.jPanel1, 1));
        this.jPanel1.add(this.m_jKeys);
        this.jPanel3.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        this.jPanel3.setLayout(new BorderLayout());
        this.m_jTendered.setFont(new Font("Arial", 1, 14));
        this.jPanel3.add((Component)this.m_jTendered, "Center");
        this.jPanel2.add((Component)this.jPanel1, "North");
        this.add((Component)this.jPanel2, "After");
    }

    private class RecalculateState
    implements PropertyChangeListener {
        private RecalculateState() {
        }

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            JPaymentCashPos.this.printState();
        }
    }

    public class ScriptPaymentCash {
        private final DataLogicSystem dlSystem;
        private final ThumbNailBuilder tnbbutton;
        private final AppConfig m_config;

        public ScriptPaymentCash(DataLogicSystem dlSystem) {
            AppConfig m_config = new AppConfig(new File(System.getProperty("user.home"), "soltecpos.properties"));
            m_config.load();
            this.m_config = m_config;
            this.dlSystem = dlSystem;
            this.tnbbutton = new ThumbNailBuilder(120, 65, "com/openbravo/images/cash.png");
        }

        public void addImageButton(String imageName, double amount) {
            JButton btn = new JButton();
            // Updated path to user's folder
            URL imgUrl = this.getClass().getResource("/com/openbravo/images/iconosmonedasybilletes/" + imageName + ".png");
            if (imgUrl != null) {
                btn.setIcon(new ImageIcon(imgUrl));
            } else {
                btn.setText(Formats.CURRENCY.formatValue(amount));
            }
            btn.setFocusPainted(false);
            btn.setFocusable(false);
            btn.setRequestFocusEnabled(false);
            btn.setHorizontalTextPosition(0);
            btn.setVerticalTextPosition(3);
            btn.setMargin(new Insets(0, 0, 0, 0));
            btn.addActionListener(new AddAmount(amount));
            JPaymentCashPos.this.jPanel6.add(btn);
        }

        public void addButton(String image, double amount) {
            JButton btn = new JButton();
            try {
                btn.setIcon(new ImageIcon(this.tnbbutton.getThumbNail(this.dlSystem.getResourceAsImage(image))));
                btn.setText(Formats.CURRENCY.formatValue(amount));
            }
            catch (Exception e) {
                btn.setText(Formats.CURRENCY.formatValue(amount));
            }
            btn.setFocusPainted(false);
            btn.setFocusable(false);
            btn.setRequestFocusEnabled(false);
            btn.setHorizontalTextPosition(0);
            btn.setVerticalTextPosition(3);
            btn.setMargin(new Insets(0, 0, 0, 0));
            btn.addActionListener(new AddAmount(amount));
            JPaymentCashPos.this.jPanel6.add(btn);
        }
    }

    private class AddAmount
    implements ActionListener {
        private final double amount;

        public AddAmount(double amount) {
            this.amount = amount;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Double tendered = JPaymentCashPos.this.m_jTendered.getDoubleValue();
            if (tendered == null) {
                JPaymentCashPos.this.m_jTendered.setDoubleValue(this.amount);
            } else {
                JPaymentCashPos.this.m_jTendered.setDoubleValue(tendered + this.amount);
            }
            JPaymentCashPos.this.printState();
        }
    }
}
