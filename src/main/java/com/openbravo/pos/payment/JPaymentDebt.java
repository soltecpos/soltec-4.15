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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.UIManager;
import org.netbeans.lib.awtextra.AbsoluteLayout;

public class JPaymentDebt
extends JPanel
implements JPaymentInterface {
    private JPaymentNotifier notifier;
    private CustomerInfoExt customerext;
    private double m_dPaid;
    private double m_dTotal;
    private JLabel jLabel12;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel6;
    private JLabel jLabel8;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JPanel jPanel3;
    private JPanel jPanel4;
    private JPanel jPanel5;
    private JPanel jPanel6;
    private JScrollPane jScrollPane1;
    private JTextArea jlblMessage;
    private JEditorKeys m_jKeys;
    private JLabel m_jMoneyEuros;
    private JTextField m_jName;
    private JTextArea m_jNotes;
    private JEditorCurrencyPositive m_jTendered;
    private JTextField txtCurdate;
    private JTextField txtCurdebt;
    private JTextField txtMaxdebt;

    public JPaymentDebt(JPaymentNotifier notifier) {
        this.notifier = notifier;
        this.initComponents();
        this.m_jTendered.addPropertyChangeListener("Edition", new RecalculateState());
        this.m_jTendered.addEditorKeys(this.m_jKeys);
    }

    @Override
    public void activate(CustomerInfoExt customerext, double dTotal, String transID) {
        this.customerext = customerext;
        this.m_dTotal = dTotal;
        this.m_jTendered.reset();
        if (customerext == null) {
            this.m_jName.setText(null);
            this.m_jNotes.setText(null);
            this.txtMaxdebt.setText(null);
            this.txtCurdate.setText(null);
            this.txtCurdebt.setText(null);
            this.m_jKeys.setEnabled(false);
            this.m_jTendered.setEnabled(false);
        } else {
            this.m_jName.setText(customerext.getName());
            this.m_jNotes.setText(customerext.getNotes());
            this.txtMaxdebt.setText(Formats.CURRENCY.formatValue(RoundUtils.getValue(customerext.getMaxdebt())));
            this.txtCurdate.setText(Formats.DATE.formatValue(customerext.getCurdate()));
            this.txtCurdebt.setText(Formats.CURRENCY.formatValue(RoundUtils.getValue(customerext.getCurdebt())));
            if (RoundUtils.compare(RoundUtils.getValue(customerext.getCurdebt()), RoundUtils.getValue(customerext.getMaxdebt())) >= 0) {
                this.m_jKeys.setEnabled(false);
                this.m_jTendered.setEnabled(false);
            } else {
                this.m_jKeys.setEnabled(true);
                this.m_jTendered.setEnabled(true);
                this.m_jTendered.activate();
            }
        }
        this.printState();
    }

    @Override
    public PaymentInfo executePayment() {
        return new PaymentInfoTicket(this.m_dPaid, "debt");
    }

    @Override
    public Component getComponent() {
        return this;
    }

    private void printState() {
        if (this.customerext == null) {
            this.m_jMoneyEuros.setText(null);
            this.jlblMessage.setText(AppLocal.getIntString("message.nocustomernodebt"));
            this.notifier.setStatus(false, false);
        } else {
            Double value = this.m_jTendered.getDoubleValue();
            this.m_dPaid = value == null || value == 0.0 ? this.m_dTotal : value;
            this.m_jMoneyEuros.setText(Formats.CURRENCY.formatValue(this.m_dPaid));
            if (RoundUtils.compare(RoundUtils.getValue(this.customerext.getCurdebt()) + this.m_dPaid, RoundUtils.getValue(this.customerext.getMaxdebt())) >= 0) {
                this.jlblMessage.setText(AppLocal.getIntString("message.customerdebtexceded"));
                this.notifier.setStatus(false, false);
            } else {
                this.jlblMessage.setText(null);
                int iCompare = RoundUtils.compare(this.m_dPaid, this.m_dTotal);
                this.notifier.setStatus(this.m_dPaid > 0.0 && iCompare <= 0, iCompare == 0);
            }
        }
    }

    private void initComponents() {
        this.jPanel5 = new JPanel();
        this.jPanel4 = new JPanel();
        this.jLabel8 = new JLabel();
        this.m_jMoneyEuros = new JLabel();
        this.jLabel3 = new JLabel();
        this.m_jName = new JTextField();
        this.jLabel12 = new JLabel();
        this.jLabel2 = new JLabel();
        this.txtMaxdebt = new JTextField();
        this.jLabel4 = new JLabel();
        this.txtCurdebt = new JTextField();
        this.jLabel6 = new JLabel();
        this.txtCurdate = new JTextField();
        this.jScrollPane1 = new JScrollPane();
        this.m_jNotes = new JTextArea();
        this.jlblMessage = new JTextArea();
        this.jPanel6 = new JPanel();
        this.jPanel2 = new JPanel();
        this.jPanel1 = new JPanel();
        this.m_jKeys = new JEditorKeys();
        this.jPanel3 = new JPanel();
        this.m_jTendered = new JEditorCurrencyPositive();
        this.setLayout(new BorderLayout());
        this.jPanel5.setLayout(new BorderLayout());
        this.jLabel8.setFont(new Font("Arial", 0, 14));
        this.jLabel8.setText(AppLocal.getIntString("label.debt"));
        this.jLabel8.setPreferredSize(new Dimension(110, 30));
        this.m_jMoneyEuros.setBackground(new Color(204, 255, 51));
        this.m_jMoneyEuros.setFont(new Font("Arial", 1, 18));
        this.m_jMoneyEuros.setHorizontalAlignment(4);
        this.m_jMoneyEuros.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(UIManager.getDefaults().getColor("Button.darkShadow")), BorderFactory.createEmptyBorder(1, 4, 1, 4)));
        this.m_jMoneyEuros.setOpaque(true);
        this.m_jMoneyEuros.setPreferredSize(new Dimension(200, 30));
        this.jLabel3.setFont(new Font("Arial", 0, 14));
        this.jLabel3.setText(AppLocal.getIntString("label.name"));
        this.jLabel3.setPreferredSize(new Dimension(110, 30));
        this.m_jName.setEditable(false);
        this.m_jName.setFont(new Font("Arial", 0, 12));
        this.m_jName.setPreferredSize(new Dimension(200, 30));
        this.jLabel12.setFont(new Font("Arial", 0, 14));
        this.jLabel12.setText(AppLocal.getIntString("label.notes"));
        this.jLabel12.setPreferredSize(new Dimension(110, 30));
        this.jLabel2.setFont(new Font("Arial", 0, 14));
        this.jLabel2.setText(AppLocal.getIntString("label.maxdebt"));
        this.jLabel2.setPreferredSize(new Dimension(110, 30));
        this.txtMaxdebt.setEditable(false);
        this.txtMaxdebt.setFont(new Font("Arial", 0, 12));
        this.txtMaxdebt.setHorizontalAlignment(4);
        this.txtMaxdebt.setPreferredSize(new Dimension(200, 30));
        this.jLabel4.setFont(new Font("Arial", 0, 14));
        this.jLabel4.setText(AppLocal.getIntString("label.curdebt"));
        this.jLabel4.setPreferredSize(new Dimension(110, 30));
        this.txtCurdebt.setEditable(false);
        this.txtCurdebt.setFont(new Font("Arial", 0, 12));
        this.txtCurdebt.setHorizontalAlignment(4);
        this.txtCurdebt.setPreferredSize(new Dimension(200, 30));
        this.jLabel6.setFont(new Font("Arial", 0, 14));
        this.jLabel6.setText(AppLocal.getIntString("label.curdate"));
        this.jLabel6.setPreferredSize(new Dimension(110, 30));
        this.txtCurdate.setEditable(false);
        this.txtCurdate.setFont(new Font("Arial", 0, 12));
        this.txtCurdate.setHorizontalAlignment(0);
        this.txtCurdate.setPreferredSize(new Dimension(200, 30));
        this.m_jNotes.setEditable(false);
        this.m_jNotes.setBackground(new Color(240, 240, 240));
        this.m_jNotes.setFont(new Font("Arial", 0, 14));
        this.m_jNotes.setEnabled(false);
        this.m_jNotes.setPreferredSize(new Dimension(150, 80));
        this.jScrollPane1.setViewportView(this.m_jNotes);
        this.jlblMessage.setEditable(false);
        this.jlblMessage.setFont(new Font("Arial", 0, 14));
        this.jlblMessage.setForeground(new Color(204, 0, 102));
        this.jlblMessage.setLineWrap(true);
        this.jlblMessage.setWrapStyleWord(true);
        this.jlblMessage.setBorder(BorderFactory.createEtchedBorder());
        this.jlblMessage.setFocusable(false);
        this.jlblMessage.setPreferredSize(new Dimension(300, 60));
        this.jlblMessage.setRequestFocusEnabled(false);
        GroupLayout jPanel4Layout = new GroupLayout(this.jPanel4);
        this.jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel4Layout.createSequentialGroup().addContainerGap().addGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jlblMessage, GroupLayout.Alignment.TRAILING, -2, -1, -2).addGroup(GroupLayout.Alignment.TRAILING, jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel4Layout.createSequentialGroup().addComponent(this.jLabel4, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.txtCurdebt, -2, -1, -2)).addGroup(jPanel4Layout.createSequentialGroup().addComponent(this.jLabel8, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.m_jMoneyEuros, -2, -1, -2)).addGroup(jPanel4Layout.createSequentialGroup().addComponent(this.jLabel3, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.m_jName, -2, -1, -2)).addGroup(jPanel4Layout.createSequentialGroup().addComponent(this.jLabel12, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jScrollPane1, -2, 200, -2)).addGroup(jPanel4Layout.createSequentialGroup().addComponent(this.jLabel2, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.txtMaxdebt, -2, -1, -2)).addGroup(jPanel4Layout.createSequentialGroup().addComponent(this.jLabel6, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.txtCurdate, -2, -1, -2))))));
        jPanel4Layout.setVerticalGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel4Layout.createSequentialGroup().addContainerGap().addGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel8, -2, 25, -2).addComponent(this.m_jMoneyEuros, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel3, -2, 25, -2).addComponent(this.m_jName, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel12, -2, 25, -2).addComponent(this.jScrollPane1, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.txtMaxdebt, -2, -1, -2).addComponent(this.jLabel2, -2, 25, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.txtCurdebt, -2, -1, -2).addComponent(this.jLabel4, -2, 25, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.txtCurdate, -2, -1, -2).addComponent(this.jLabel6, -2, 25, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jlblMessage, -2, -1, -2).addContainerGap()));
        this.jPanel5.add((Component)this.jPanel4, "Center");
        this.jPanel6.setLayout(new AbsoluteLayout());
        this.jPanel5.add((Component)this.jPanel6, "Last");
        this.add((Component)this.jPanel5, "Center");
        this.jPanel2.setLayout(new BorderLayout());
        this.jPanel1.setLayout(new BoxLayout(this.jPanel1, 1));
        this.m_jKeys.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPaymentDebt.this.m_jKeysActionPerformed(evt);
            }
        });
        this.jPanel1.add(this.m_jKeys);
        this.jPanel3.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        this.jPanel3.setFont(new Font("Arial", 0, 14));
        this.m_jTendered.setFont(new Font("Arial", 0, 14));
        this.m_jTendered.setPreferredSize(new Dimension(200, 30));
        GroupLayout jPanel3Layout = new GroupLayout(this.jPanel3);
        this.jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel3Layout.createSequentialGroup().addGap(40, 40, 40).addComponent(this.m_jTendered, -2, -1, -2).addContainerGap(50, Short.MAX_VALUE)));
        jPanel3Layout.setVerticalGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.m_jTendered, -2, -1, -2));
        this.jPanel1.add(this.jPanel3);
        this.jPanel2.add((Component)this.jPanel1, "North");
        this.add((Component)this.jPanel2, "East");
    }

    private void m_jKeysActionPerformed(ActionEvent evt) {
    }

    private class RecalculateState
    implements PropertyChangeListener {
        private RecalculateState() {
        }

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            JPaymentDebt.this.printState();
        }
    }
}

