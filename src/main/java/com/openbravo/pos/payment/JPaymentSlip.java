/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.payment;

import com.openbravo.editor.JEditorCurrencyPositive;
import com.openbravo.editor.JEditorKeys;
import com.openbravo.format.Formats;
import com.openbravo.pos.customers.CustomerInfoExt;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.payment.JPaymentInterface;
import com.openbravo.pos.payment.JPaymentNotifier;
import com.openbravo.pos.payment.PaymentInfo;
import com.openbravo.pos.payment.PaymentInfoTicket;
import com.openbravo.pos.util.RoundUtils;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class JPaymentSlip
extends JPanel
implements JPaymentInterface {
    private final JPaymentNotifier m_notifier;
    private double m_dPaid;
    private double m_dTotal;
    private JLabel jLabel8;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JPanel jPanel3;
    private JPanel jPanel4;
    private JEditorKeys m_jKeys;
    private JLabel m_jMoneyEuros;
    private JEditorCurrencyPositive m_jTendered;

    public JPaymentSlip(JPaymentNotifier notifier) {
        this.m_notifier = notifier;
        this.initComponents();
        this.m_jTendered.addPropertyChangeListener("Edition", new RecalculateState());
        this.m_jTendered.addEditorKeys(this.m_jKeys);
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
        return new PaymentInfoTicket(this.m_dPaid, "slip");
    }

    @Override
    public Component getComponent() {
        return this;
    }

    private void printState() {
        Double value = this.m_jTendered.getDoubleValue();
        this.m_dPaid = value == null ? this.m_dTotal : value;
        this.m_jMoneyEuros.setText(Formats.CURRENCY.formatValue(this.m_dPaid));
        int iCompare = RoundUtils.compare(this.m_dPaid, this.m_dTotal);
        this.m_notifier.setStatus(this.m_dPaid > 0.0 && iCompare <= 0, iCompare == 0);
    }

    private void initComponents() {
        this.jPanel2 = new JPanel();
        this.jPanel1 = new JPanel();
        this.m_jKeys = new JEditorKeys();
        this.jPanel3 = new JPanel();
        this.m_jTendered = new JEditorCurrencyPositive();
        this.jPanel4 = new JPanel();
        this.jLabel8 = new JLabel();
        this.m_jMoneyEuros = new JLabel();
        this.setLayout(new BorderLayout());
        this.jPanel2.setLayout(new BorderLayout());
        this.jPanel1.setLayout(new BoxLayout(this.jPanel1, 1));
        this.jPanel1.add(this.m_jKeys);
        this.jPanel3.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        this.jPanel3.setLayout(new BorderLayout());
        this.m_jTendered.setFont(new Font("Tahoma", 0, 12));
        this.jPanel3.add((Component)this.m_jTendered, "Center");
        this.jPanel1.add(this.jPanel3);
        this.jPanel2.add((Component)this.jPanel1, "North");
        this.add((Component)this.jPanel2, "East");
        this.jPanel4.setFont(new Font("Arial", 0, 12));
        this.jPanel4.setLayout(null);
        this.jLabel8.setFont(new Font("Arial", 1, 18));
        this.jLabel8.setText(AppLocal.getIntString("label.InputCash"));
        this.jLabel8.setPreferredSize(new Dimension(100, 30));
        this.jPanel4.add(this.jLabel8);
        this.jLabel8.setBounds(10, 4, 100, 30);
        this.m_jMoneyEuros.setBackground(new Color(204, 255, 51));
        this.m_jMoneyEuros.setFont(new Font("Arial", 1, 18));
        this.m_jMoneyEuros.setHorizontalAlignment(4);
        this.m_jMoneyEuros.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(UIManager.getDefaults().getColor("Button.darkShadow")), BorderFactory.createEmptyBorder(1, 4, 1, 4)));
        this.m_jMoneyEuros.setOpaque(true);
        this.m_jMoneyEuros.setPreferredSize(new Dimension(180, 30));
        this.jPanel4.add(this.m_jMoneyEuros);
        this.m_jMoneyEuros.setBounds(120, 4, 180, 30);
        this.add((Component)this.jPanel4, "Center");
    }

    private class RecalculateState
    implements PropertyChangeListener {
        private RecalculateState() {
        }

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            JPaymentSlip.this.printState();
        }
    }
}

