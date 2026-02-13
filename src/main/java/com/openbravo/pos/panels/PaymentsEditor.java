/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.panels;

import com.openbravo.basic.BasicException;
import com.openbravo.data.gui.ComboBoxValModel;
import com.openbravo.data.gui.JMessageDialog;
import com.openbravo.data.gui.MessageInf;
import com.openbravo.data.loader.IKeyed;
import com.openbravo.data.user.DirtyManager;
import com.openbravo.data.user.EditorRecord;
import com.openbravo.editor.JEditorAbstract;
import com.openbravo.editor.JEditorCurrency;
import com.openbravo.editor.JEditorString;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.forms.DataLogicSales;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.LayoutStyle;

public final class PaymentsEditor
extends JPanel
implements EditorRecord {
    private static final long serialVersionUID = 1L;
    private final ComboBoxValModel<PaymentReason> m_ReasonModel;
    private String m_sId;
    private String m_sPaymentId;
    private Date datenew;
    private final AppView m_App;
    private String m_sNotes;
    private final ComboBoxValModel<CategoryKey> m_CategoryModel;
    private boolean m_bResetting = false;
    private JToggleButton m_btnEdit;
    private JLabel jLabel3;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JPanel jPanel3;
    private JEditorCurrency jTotal;
    private JEditorString m_jNotes;
    private JComboBox<CategoryKey> m_jcategory;
    private JComboBox<PaymentReason> m_jreason;

    public PaymentsEditor(AppView oApp, final DirtyManager dirty) {
        this.m_App = oApp;
        this.initComponents();
        this.m_ReasonModel = new ComboBoxValModel();
        this.m_ReasonModel.add(new PaymentReasonPositive("cashin", AppLocal.getIntString("transpayment.cashin")));
        this.m_ReasonModel.add(new PaymentReasonNegative("cashout", AppLocal.getIntString("transpayment.cashout")));
        this.m_jreason.setModel(this.m_ReasonModel);
        this.m_CategoryModel = new ComboBoxValModel();
        try {
            List<Object[]> categories = ((DataLogicSales)this.m_App.getBean("com.openbravo.pos.forms.DataLogicSales")).getPaymentCategoriesGlobal(false).list();
            for (Object[] category : categories) {
                this.m_CategoryModel.add(new CategoryKey(category[0], category[1]));
            }
        }
        catch (Exception e) {
            JMessageDialog.showMessage(this, new MessageInf(-33554432, "Error loading categories: " + e.getMessage(), e));
        }
        this.m_jcategory.setModel(this.m_CategoryModel);
        ActionListener dirtyAction = new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!PaymentsEditor.this.m_bResetting) {
                    dirty.setDirty(true);
                }
            }
        };
        PropertyChangeListener dirtyProperty = new PropertyChangeListener(){

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (!PaymentsEditor.this.m_bResetting) {
                    dirty.setDirty(true);
                }
            }
        };
        this.m_jreason.addActionListener(dirtyAction);
        this.m_jcategory.addActionListener(dirtyAction);
        this.jTotal.addPropertyChangeListener("Text", dirtyProperty);
        this.m_jNotes.addPropertyChangeListener("Text", dirtyProperty);
        this.m_btnEdit.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                PaymentsEditor.this.enableFields(PaymentsEditor.this.m_btnEdit.isSelected());
            }
        });
        KeyAdapter keyListener = new KeyAdapter(){

            @Override
            public void keyTyped(KeyEvent e) {
                JEditorAbstract editor = (JEditorAbstract)e.getSource();
                if (editor.isEnabled()) {
                    editor.typeChar(e.getKeyChar());
                }
                e.consume();
            }
        };
        this.jTotal.addKeyListener(keyListener);
        this.m_jNotes.addKeyListener(keyListener);
        this.writeValueEOF();
    }

    public void setIgnoreDirty(boolean ignore) {
        this.m_bResetting = ignore;
    }

    public void enableFields(boolean enabled) {
        this.m_jreason.setEnabled(enabled);
        this.jTotal.setEnabled(enabled);
        this.m_jNotes.setEnabled(enabled);
        this.m_jcategory.setEnabled(enabled);
        Color bgColor = enabled ? Color.WHITE : new Color(240, 240, 240);
        this.m_jreason.setBackground(bgColor);
        this.jTotal.setBackground(bgColor);
        this.m_jNotes.setBackground(bgColor);
        this.m_jcategory.setBackground(bgColor);
        this.m_jreason.repaint();
        this.jTotal.repaint();
        this.m_jNotes.repaint();
        this.m_jcategory.repaint();
    }

    @Override
    public void writeValueEOF() {
        this.m_bResetting = true;
        try {
            this.m_sId = null;
            this.m_sPaymentId = null;
            this.datenew = null;
            this.setReasonTotal(null, null);
            this.jTotal.reset();
            this.enableFields(false);
            this.m_btnEdit.setVisible(false);
            this.m_sNotes = null;
            this.m_jNotes.setText(null);
            this.m_jNotes.reset();
            this.m_CategoryModel.setSelectedKey(null);
        }
        finally {
            this.m_bResetting = false;
        }
    }

    @Override
    public void writeValueInsert() {
        this.m_bResetting = true;
        try {
            this.m_sId = null;
            this.m_sPaymentId = null;
            this.datenew = null;
            this.setReasonTotal("cashin", null);
            this.jTotal.reset();
            this.enableFields(true);
            this.m_btnEdit.setSelected(false);
            this.m_btnEdit.setVisible(false);
            this.m_sNotes = "";
            this.m_jNotes.setText(this.m_sNotes);
            this.m_jNotes.reset();
            this.m_CategoryModel.setSelectedKey(null);
        }
        finally {
            this.m_bResetting = false;
        }
    }

    @Override
    public void writeValueDelete(Object value) {
        this.m_bResetting = true;
        try {
            Object[] payment = (Object[])value;
            this.m_sId = (String)payment[0];
            this.datenew = (Date)payment[2];
            this.m_sPaymentId = (String)payment[3];
            this.setReasonTotal(payment[4], payment[5]);
            this.enableFields(false);
            this.m_btnEdit.setSelected(false);
            this.m_btnEdit.setVisible(true);
            this.m_sNotes = (String)payment[6];
            this.m_jNotes.setText(this.m_sNotes);
            this.m_CategoryModel.setSelectedKey(payment[9]);
        }
        finally {
            this.m_bResetting = false;
        }
    }

    @Override
    public void writeValueEdit(Object value) {
        this.m_bResetting = true;
        try {
            Object[] payment = (Object[])value;
            this.m_sId = (String)payment[0];
            this.datenew = (Date)payment[2];
            this.m_sPaymentId = (String)payment[3];
            this.setReasonTotal(payment[4], payment[5]);
            this.enableFields(false);
            this.m_btnEdit.setSelected(false);
            this.m_btnEdit.setVisible(true);
            this.m_sNotes = (String)payment[6];
            this.m_jNotes.setText(this.m_sNotes);
            this.m_CategoryModel.setSelectedKey(payment[9]);
        }
        finally {
            this.m_bResetting = false;
        }
    }

    @Override
    public Object createValue() throws BasicException {
        Object[] payment = new Object[10];
        payment[0] = this.m_sId == null ? UUID.randomUUID().toString() : this.m_sId;
        payment[1] = this.m_App.getActiveCashIndex();
        payment[2] = this.datenew == null ? new Date() : this.datenew;
        payment[3] = this.m_sPaymentId == null ? UUID.randomUUID().toString() : this.m_sPaymentId;
        payment[4] = this.m_ReasonModel.getSelectedKey();
        PaymentReason reason = (PaymentReason)this.m_ReasonModel.getSelectedItem();
        Double dtotal = this.jTotal.getDoubleValue();
        payment[5] = reason == null ? dtotal : reason.addSignum(dtotal);
        String snotes = "";
        this.m_sNotes = this.m_jNotes.getText();
        payment[6] = this.m_sNotes == null ? snotes : this.m_sNotes;
        Object selCat = this.m_CategoryModel.getSelectedItem();
        payment[7] = selCat != null ? selCat.toString() : null;
        payment[8] = this.m_App.getAppUserView().getUser().getUserInfo().getId();
        payment[9] = this.m_CategoryModel.getSelectedKey();
        return payment;
    }

    @Override
    public Component getComponent() {
        return this;
    }

    @Override
    public void refresh() {
    }

    private void setReasonTotal(Object reasonfield, Object totalfield) {
        this.m_ReasonModel.setSelectedKey(reasonfield);
        PaymentReason reason = (PaymentReason)this.m_ReasonModel.getSelectedItem();
        if (reason == null) {
            this.jTotal.setDoubleValue((Double)totalfield);
        } else {
            this.jTotal.setDoubleValue(reason.positivize((Double)totalfield));
        }
    }

    private void initComponents() {
        this.jPanel3 = new JPanel();
        this.jLabel5 = new JLabel();
        this.m_jreason = new JComboBox();
        this.jLabel3 = new JLabel();
        this.jTotal = new JEditorCurrency();
        this.m_jNotes = new JEditorString();
        this.jLabel6 = new JLabel();
        this.m_jcategory = new JComboBox();
        this.m_btnEdit = new JToggleButton("Editar/Desbloquear");
        this.setLayout(new BorderLayout());
        JPanel topPanel = new JPanel(new FlowLayout(0));
        topPanel.add(this.m_btnEdit);
        this.add((Component)topPanel, "North");
        this.jLabel5.setFont(new Font("Arial", 0, 14));
        this.jLabel5.setText(AppLocal.getIntString("label.paymentreason"));
        this.jLabel5.setPreferredSize(new Dimension(110, 30));
        this.m_jreason.setFont(new Font("Arial", 0, 14));
        this.m_jreason.setFocusable(true);
        this.m_jreason.setPreferredSize(new Dimension(200, 30));
        this.jLabel3.setFont(new Font("Arial", 0, 14));
        this.jLabel3.setText(AppLocal.getIntString("label.paymenttotal"));
        this.jLabel3.setPreferredSize(new Dimension(110, 30));
        this.jTotal.setFont(new Font("Arial", 0, 14));
        this.jTotal.setPreferredSize(new Dimension(200, 30));
        this.jTotal.setFocusable(true);
        this.m_jNotes.setFont(new Font("Arial", 0, 14));
        this.m_jNotes.setPreferredSize(new Dimension(132, 100));
        this.m_jNotes.setFocusable(true);
        this.jLabel6.setFont(new Font("Arial", 0, 14));
        this.jLabel6.setText(AppLocal.getIntString("label.paymentcategory"));
        this.jLabel6.setPreferredSize(new Dimension(110, 30));
        this.m_jcategory.setFont(new Font("Arial", 0, 14));
        this.m_jcategory.setFocusable(true);
        this.m_jcategory.setPreferredSize(new Dimension(200, 30));
        GroupLayout jPanel3Layout = new GroupLayout(this.jPanel3);
        this.jPanel3.setLayout(jPanel3Layout);
        int GAP = 15;
        int ROW_HEIGHT = 40;
        jPanel3Layout.setHorizontalGroup(jPanel3Layout.createSequentialGroup().addContainerGap().addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel5, -2, 150, -2).addComponent(this.jLabel6, -2, 150, -2).addComponent(this.jLabel3, -2, 150, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.m_jreason, 0, 300, Short.MAX_VALUE).addComponent(this.m_jcategory, 0, 300, Short.MAX_VALUE).addComponent(this.jTotal, 0, 300, Short.MAX_VALUE).addComponent(this.m_jNotes, 0, 300, Short.MAX_VALUE)).addContainerGap());
        jPanel3Layout.setVerticalGroup(jPanel3Layout.createSequentialGroup().addContainerGap().addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.CENTER).addComponent(this.jLabel5, -2, ROW_HEIGHT, -2).addComponent(this.m_jreason, -2, ROW_HEIGHT, -2)).addGap(GAP).addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.CENTER).addComponent(this.jLabel6, -2, ROW_HEIGHT, -2).addComponent(this.m_jcategory, -2, ROW_HEIGHT, -2)).addGap(GAP).addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.CENTER).addComponent(this.jLabel3, -2, ROW_HEIGHT, -2).addComponent(this.jTotal, -2, ROW_HEIGHT, -2)).addGap(GAP).addComponent(this.m_jNotes, -2, 120, -2).addContainerGap(-1, Short.MAX_VALUE));
        this.add((Component)this.jPanel3, "Center");
    }

    private static class PaymentReasonPositive
    extends PaymentReason {
        public PaymentReasonPositive(String key, String text) {
            super(key, text);
        }

        @Override
        public Double positivize(Double d) {
            return d;
        }

        @Override
        public Double addSignum(Double d) {
            if (d == null) {
                return null;
            }
            if (d < 0.0) {
                return -d.doubleValue();
            }
            return d;
        }
    }

    private static class PaymentReasonNegative
    extends PaymentReason {
        public PaymentReasonNegative(String key, String text) {
            super(key, text);
        }

        @Override
        public Double positivize(Double d) {
            return d == null ? null : Double.valueOf(-d.doubleValue());
        }

        @Override
        public Double addSignum(Double d) {
            if (d == null) {
                return null;
            }
            if (d > 0.0) {
                return -d.doubleValue();
            }
            return d;
        }
    }

    private static class CategoryKey
    implements IKeyed {
        private Object key;
        private Object value;

        public CategoryKey(Object key, Object value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public Object getKey() {
            return this.key;
        }

        public String toString() {
            return this.value == null ? "" : this.value.toString();
        }
    }

    private static abstract class PaymentReason
    implements IKeyed {
        private String m_sKey;
        private String m_sText;

        public PaymentReason(String key, String text) {
            this.m_sKey = key;
            this.m_sText = text;
        }

        @Override
        public Object getKey() {
            return this.m_sKey;
        }

        public abstract Double positivize(Double var1);

        public abstract Double addSignum(Double var1);

        public String toString() {
            return this.m_sText;
        }
    }
}

