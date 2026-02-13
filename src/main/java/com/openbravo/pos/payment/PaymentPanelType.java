/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.payment;

import com.openbravo.editor.JEditorKeys;
import com.openbravo.editor.JEditorString;
import com.openbravo.editor.JEditorStringNumber;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.payment.JPaymentNotifier;
import com.openbravo.pos.payment.PaymentInfoMagcard;
import com.openbravo.pos.payment.PaymentInfoMagcardRefund;
import com.openbravo.pos.payment.PaymentPanel;
import com.openbravo.pos.util.LuhnAlgorithm;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;

public class PaymentPanelType
extends JPanel
implements PaymentPanel {
    private double m_dTotal;
    private String m_sTransactionID;
    private JPaymentNotifier m_notifier;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JLabel jLabel8;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JPanel jPanel4;
    private JPanel jPanel5;
    private JEditorStringNumber m_jCardNumber;
    private JEditorStringNumber m_jExpirationDate;
    private JEditorString m_jHolderName;
    private JEditorKeys m_jKeys;

    public PaymentPanelType(JPaymentNotifier notifier) {
        this.m_notifier = notifier;
        this.initComponents();
        this.m_jHolderName.addPropertyChangeListener("Edition", new RecalculateName());
        this.m_jCardNumber.addPropertyChangeListener("Edition", new RecalculateName());
        this.m_jExpirationDate.addPropertyChangeListener("Edition", new RecalculateName());
        this.m_jHolderName.addEditorKeys(this.m_jKeys);
        this.m_jCardNumber.addEditorKeys(this.m_jKeys);
        this.m_jExpirationDate.addEditorKeys(this.m_jKeys);
    }

    @Override
    public JComponent getComponent() {
        return this;
    }

    @Override
    public void activate(String sTransaction, double dTotal) {
        this.m_sTransactionID = sTransaction;
        this.m_dTotal = dTotal;
        this.resetState();
        this.m_jHolderName.activate();
    }

    private void resetState() {
        this.m_notifier.setStatus(false, false);
        this.m_jHolderName.setText(null);
        this.m_jCardNumber.setText(null);
        this.m_jExpirationDate.setText(null);
    }

    @Override
    public PaymentInfoMagcard getPaymentInfoMagcard() {
        if (this.m_dTotal > 0.0) {
            return new PaymentInfoMagcard(this.m_jHolderName.getText(), this.m_jCardNumber.getText(), this.m_jExpirationDate.getText(), null, null, null, null, null, this.m_sTransactionID, this.m_dTotal);
        }
        return new PaymentInfoMagcardRefund(this.m_jHolderName.getText(), this.m_jCardNumber.getText(), this.m_jExpirationDate.getText(), null, null, null, null, null, this.m_sTransactionID, this.m_dTotal);
    }

    private boolean isValidHolder() {
        return this.m_jHolderName.getText() != null && !this.m_jHolderName.getText().equals("");
    }

    private boolean isValidCardNumber() {
        return LuhnAlgorithm.checkCC(this.m_jCardNumber.getText()) && this.m_jCardNumber.getText().length() > 13 && this.m_jCardNumber.getText().length() < 20;
    }

    private boolean isValidExpirationDate() {
        return this.m_jExpirationDate.getText() != null && this.m_jExpirationDate.getText().length() == 4;
    }

    private void initComponents() {
        this.jPanel2 = new JPanel();
        this.jPanel1 = new JPanel();
        this.m_jKeys = new JEditorKeys();
        this.jPanel4 = new JPanel();
        this.m_jCardNumber = new JEditorStringNumber();
        this.m_jExpirationDate = new JEditorStringNumber();
        this.m_jHolderName = new JEditorString();
        this.jLabel8 = new JLabel();
        this.jLabel6 = new JLabel();
        this.jLabel7 = new JLabel();
        this.jLabel2 = new JLabel();
        this.jPanel5 = new JPanel();
        this.jLabel1 = new JLabel();
        this.setLayout(new BorderLayout());
        this.jPanel2.setLayout(new BorderLayout());
        this.jPanel1.setLayout(new BoxLayout(this.jPanel1, 1));
        this.m_jKeys.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                PaymentPanelType.this.m_jKeysActionPerformed(evt);
            }
        });
        this.jPanel1.add(this.m_jKeys);
        this.jPanel2.add((Component)this.jPanel1, "North");
        this.add((Component)this.jPanel2, "East");
        this.jPanel4.setPreferredSize(new Dimension(400, 105));
        this.m_jCardNumber.setFont(new Font("Arial", 0, 14));
        this.m_jCardNumber.setMinimumSize(new Dimension(100, 30));
        this.m_jCardNumber.setPreferredSize(new Dimension(150, 30));
        this.m_jExpirationDate.setFont(new Font("Arial", 0, 14));
        this.m_jExpirationDate.setMinimumSize(new Dimension(100, 30));
        this.m_jExpirationDate.setPreferredSize(new Dimension(150, 30));
        this.m_jHolderName.setFont(new Font("Arial", 0, 14));
        this.m_jHolderName.setMinimumSize(new Dimension(100, 30));
        this.m_jHolderName.setPreferredSize(new Dimension(150, 30));
        this.jLabel8.setFont(new Font("Arial", 0, 14));
        this.jLabel8.setText(AppLocal.getIntString("label.cardholder"));
        this.jLabel8.setPreferredSize(new Dimension(150, 30));
        this.jLabel6.setFont(new Font("Arial", 0, 14));
        this.jLabel6.setText(AppLocal.getIntString("label.cardnumber"));
        this.jLabel6.setPreferredSize(new Dimension(150, 30));
        this.jLabel7.setFont(new Font("Arial", 0, 14));
        this.jLabel7.setText(AppLocal.getIntString("label.cardexpdate"));
        this.jLabel7.setPreferredSize(new Dimension(150, 30));
        this.jLabel2.setFont(new Font("Arial", 0, 14));
        this.jLabel2.setText("MMYY");
        this.jLabel2.setPreferredSize(new Dimension(150, 30));
        GroupLayout jPanel4Layout = new GroupLayout(this.jPanel4);
        this.jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel4Layout.createSequentialGroup().addContainerGap().addGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel4Layout.createSequentialGroup().addComponent(this.jLabel7, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.m_jExpirationDate, -2, 90, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jLabel2, -2, 61, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)).addGroup(jPanel4Layout.createSequentialGroup().addComponent(this.jLabel8, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.m_jHolderName, -2, 200, -2)).addGroup(jPanel4Layout.createSequentialGroup().addComponent(this.jLabel6, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.m_jCardNumber, -2, 200, -2))).addContainerGap()));
        jPanel4Layout.setVerticalGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel4Layout.createSequentialGroup().addContainerGap().addGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel8, -2, -1, -2).addComponent(this.m_jHolderName, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel6, -2, -1, -2).addComponent(this.m_jCardNumber, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.m_jExpirationDate, -2, -1, -2).addComponent(this.jLabel7, -2, -1, -2).addComponent(this.jLabel2, -2, -1, -2)).addContainerGap(177, Short.MAX_VALUE)));
        this.add((Component)this.jPanel4, "Center");
        this.jPanel5.setPreferredSize(new Dimension(350, 45));
        this.jLabel1.setFont(new Font("Arial", 1, 18));
        this.jLabel1.setText(AppLocal.getIntString("message.paymentgatewaytype"));
        this.jPanel5.add(this.jLabel1);
        this.add((Component)this.jPanel5, "North");
    }

    private void m_jKeysActionPerformed(ActionEvent evt) {
    }

    private class RecalculateName
    implements PropertyChangeListener {
        private RecalculateName() {
        }

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            boolean isvalid = PaymentPanelType.this.isValidHolder() && PaymentPanelType.this.isValidCardNumber() && PaymentPanelType.this.isValidExpirationDate();
            PaymentPanelType.this.m_notifier.setStatus(isvalid, isvalid);
        }
    }
}

