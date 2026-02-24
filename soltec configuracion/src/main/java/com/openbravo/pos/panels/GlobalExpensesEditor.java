/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.panels;

import com.openbravo.basic.BasicException;
import com.openbravo.data.gui.ComboBoxValModel;
import com.openbravo.data.loader.Datas;
import com.openbravo.data.loader.IKeyed;
import com.openbravo.data.loader.SerializerReadBasic;
import com.openbravo.data.loader.SerializerWriteBasic;
import com.openbravo.data.loader.StaticSentence;
import com.openbravo.data.user.DirtyManager;
import com.openbravo.data.user.EditorRecord;
import com.openbravo.editor.JEditorCurrency;
import com.openbravo.editor.JEditorString;
import com.openbravo.pos.forms.AppView;
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
import java.util.UUID;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public final class GlobalExpensesEditor
extends JPanel
implements EditorRecord {
    private static final long serialVersionUID = 1L;
    private final ComboBoxValModel<PaymentMethod> m_MethodModel;
    private final DefaultComboBoxModel<Object> m_CategoryModel;
    private String m_sId;
    private String m_sPaymentId;
    private Date datenew;
    private final AppView m_App;
    private boolean m_bResetting = false;
    private JEditorCurrency jTotal;
    private JLabel lblCategory;
    private JLabel lblMethod;
    private JLabel lblNotes;
    private JLabel lblTitle;
    private JLabel lblTotal;
    private JEditorString m_jNotes;
    private JComboBox<Object> m_jcategory;
    private JComboBox<PaymentMethod> m_jMethod;
    private JPanel jPanelMain;

    public GlobalExpensesEditor(AppView oApp, final DirtyManager dirty) {
        this.m_App = oApp;
        this.initComponents();
        this.m_MethodModel = new ComboBoxValModel();
        this.m_MethodModel.add(new PaymentMethod("cash", "Efectivo (Caja)"));
        this.m_MethodModel.add(new PaymentMethod("nequi", "Nequi"));
        this.m_MethodModel.add(new PaymentMethod("daviplata", "Daviplata"));
        this.m_MethodModel.add(new PaymentMethod("magcard", "Tarjeta de Cr\u00e9dito/D\u00e9bito"));
        this.m_MethodModel.add(new PaymentMethod("bank", "Transferencia Bancaria"));
        this.m_jMethod.setModel(this.m_MethodModel);
        this.m_CategoryModel = new DefaultComboBoxModel();
        this.loadCategories();
        this.m_jcategory.setModel(this.m_CategoryModel);
        this.m_jcategory.setEditable(true);
        ActionListener dirtyAction = new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!GlobalExpensesEditor.this.m_bResetting) {
                    dirty.setDirty(true);
                }
            }
        };
        this.m_jMethod.addActionListener(dirtyAction);
        this.m_jcategory.addActionListener(dirtyAction);
        this.jTotal.addPropertyChangeListener("value", e -> {
            if (!this.m_bResetting) {
                dirty.setDirty(true);
            }
        });
        this.jTotal.addPropertyChangeListener("Text", e -> {
            if (!this.m_bResetting) {
                dirty.setDirty(true);
            }
        });
        Component editor = this.m_jcategory.getEditor().getEditorComponent();
        if (editor instanceof JTextField) {
            ((JTextField)editor).getDocument().addDocumentListener(new DocumentListener(){

                @Override
                public void insertUpdate(DocumentEvent e) {
                    dirty.setDirty(true);
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    dirty.setDirty(true);
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    dirty.setDirty(true);
                }
            });
        }
        this.writeValueEOF();
    }

    private void loadCategories() {
        try {
            if (this.m_App == null) {
                return;
            }
            if (this.m_App.getSession() == null) {
                return;
            }
            try {
                new StaticSentence(this.m_App.getSession(), "SELECT ID, NAME FROM payment_categories WHERE IS_GLOBAL = 1").list();
            }
            catch (Exception e) {
                try {
                    new StaticSentence(this.m_App.getSession(), "ALTER TABLE PAYMENT_CATEGORIES ADD COLUMN IS_GLOBAL BIT DEFAULT 0").exec();
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
            List categories = new StaticSentence<Object[], Object[]>(this.m_App.getSession(), "SELECT ID, NAME FROM payment_categories WHERE IS_GLOBAL = ? ORDER BY NAME", new SerializerWriteBasic(Datas.BOOLEAN), new SerializerReadBasic(new Datas[]{Datas.STRING, Datas.STRING})).list(new Object[]{Boolean.TRUE});
            Object current = this.m_jcategory.getSelectedItem();
            this.m_CategoryModel.removeAllElements();
            if (categories != null) {
                for (Object obj : categories) {
                    Object[] category = (Object[]) obj;
                    this.m_CategoryModel.addElement(new CategoryItem((String)category[0], (String)category[1]));
                }
            }
            this.m_jcategory.setSelectedItem(current);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getOrCreateCategory(String name) throws BasicException {
        if (name == null || name.trim().isEmpty()) {
            return null;
        }
        if (this.m_App.getSession() == null) {
            throw new BasicException("Database SESSION is NULL. Cannot check categories.");
        }
        try {
            List cats = new StaticSentence<Object[], Object[]>(this.m_App.getSession(), "SELECT ID, NAME FROM payment_categories WHERE IS_GLOBAL = ? ORDER BY NAME", new SerializerWriteBasic(Datas.BOOLEAN), new SerializerReadBasic(new Datas[]{Datas.STRING, Datas.STRING})).list(new Object[]{Boolean.TRUE});
            if (cats != null) {
                for (Object obj : cats) {
                    Object[] c = (Object[]) obj;
                    if (c == null || c.length <= 1 || !name.equalsIgnoreCase((String)c[1])) continue;
                    return (String)c[0];
                }
            }
        }
        catch (Exception e) {
            throw new BasicException("Error checking existing categories (Direct Query): " + e.getMessage(), e);
        }
        try {
            String newId = UUID.randomUUID().toString();
            new StaticSentence(this.m_App.getSession(), "INSERT INTO payment_categories (ID, NAME, IS_GLOBAL) VALUES (?, ?, 1)", new SerializerWriteBasic(Datas.STRING, Datas.STRING), null).exec(new Object[]{newId, name});
            this.loadCategories();
            return newId;
        }
        catch (Exception e) {
            throw new BasicException("Error inserting new category: " + e.toString(), e);
        }
    }

    @Override
    public void writeValueEOF() {
        this.m_bResetting = true;
        try {
            this.m_sId = null;
            this.m_sPaymentId = null;
            this.datenew = null;
            this.m_MethodModel.setSelectedItem(null);
            this.jTotal.reset();
            this.m_jNotes.reset();
            this.m_jcategory.setSelectedItem(null);
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
            this.m_MethodModel.setSelectedKey("cash");
            this.jTotal.reset();
            this.m_jNotes.setText("");
            this.m_jcategory.setSelectedItem(null);
        }
        finally {
            this.m_bResetting = false;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void writeValueDelete(Object value) {
        this.m_bResetting = true;
        try {
            Object[] payment = (Object[])value;
            this.m_sId = (String)payment[0];
            this.datenew = (Date)payment[2];
            this.m_sPaymentId = (String)payment[3];
            this.m_MethodModel.setSelectedKey(payment[4]);
            Double total = (Double)payment[5];
            this.jTotal.setDoubleValue(Math.abs(total));
            this.m_jNotes.setText((String)payment[6]);
            String catName = (String)payment[7];
            String catId = (String)payment[9];
            if (catId != null) {
                this.m_jcategory.setSelectedItem(new CategoryItem(catId, catName));
            } else {
                this.m_jcategory.setSelectedItem(catName);
            }
        }
        finally {
            this.m_bResetting = false;
        }
    }

    @Override
    public void writeValueEdit(Object value) {
        this.writeValueDelete(value);
    }

    @Override
    public Object createValue() throws BasicException {
        if (this.m_App == null) {
            throw new BasicException("Error interno: m_App es nulo");
        }
        Object[] payment = new Object[10];
        payment[0] = this.m_sId == null ? UUID.randomUUID().toString() : this.m_sId;
        String activeCash = this.m_App.getActiveCashIndex();
        if (activeCash == null || activeCash.trim().isEmpty()) {
            throw new BasicException("Error: No hay una Caja Abierta (Active Cash ID es nulo).");
        }
        payment[1] = activeCash;
        payment[2] = this.datenew == null ? new Date() : this.datenew;
        Object object = payment[3] = this.m_sPaymentId == null ? UUID.randomUUID().toString() : this.m_sPaymentId;
        if (this.m_MethodModel == null) {
            throw new BasicException("Error interno: m_MethodModel es nulo");
        }
        Object methodKey = this.m_MethodModel.getSelectedKey();
        Object object2 = payment[4] = methodKey == null ? "cash" : methodKey;
        if (this.jTotal == null) {
            throw new BasicException("Error interno: jTotal es nulo");
        }
        Double dtotal = null;
        try {
            dtotal = this.jTotal.getDoubleValue();
        }
        catch (Exception e) {
            dtotal = 0.0;
        }
        payment[5] = -Math.abs(dtotal == null ? 0.0 : dtotal);
        if (this.m_jNotes == null) {
            throw new BasicException("Error interno: m_jNotes es nulo");
        }
        try {
            payment[6] = this.m_jNotes.getText();
        }
        catch (NullPointerException e) {
            payment[6] = "";
        }
        String categoryId = null;
        String categoryName = null;
        if (this.m_jcategory == null) {
            throw new BasicException("Error interno: m_jcategory es nulo");
        }
        try {
            Object cat = this.m_jcategory.getSelectedItem();
            if (cat == null || cat.toString().trim().isEmpty()) {
                categoryId = null;
                categoryName = null;
            } else if (cat instanceof CategoryItem) {
                categoryId = ((CategoryItem)cat).getId();
                categoryName = cat.toString();
            } else {
                categoryName = cat.toString();
                if (categoryName == null) {
                    throw new BasicException("Category name is null (String conversion).");
                }
                categoryId = this.getOrCreateCategory(categoryName);
            }
        }
        catch (BasicException be) {
            throw be;
        }
        catch (Exception e) {
            e.printStackTrace();
            String msg = e.getMessage();
            if (msg == null) {
                msg = e.toString();
            }
            throw new BasicException("Error procesando categor\u00eda (DEBUG - Direct Query): " + msg, e);
        }
        payment[7] = categoryName;
        payment[9] = categoryId;
        String userId = "0";
        try {
            if (this.m_App.getAppUserView() != null && this.m_App.getAppUserView().getUser() != null) {
                userId = this.m_App.getAppUserView().getUser().getId();
            }
        }
        catch (NullPointerException e) {
            userId = "0";
        }
        payment[8] = userId;
        return payment;
    }

    @Override
    public Component getComponent() {
        return this;
    }

    @Override
    public void refresh() {
    }

    private void initComponents() {
        this.jPanelMain = new JPanel();
        this.lblTitle = new JLabel();
        this.lblMethod = new JLabel();
        this.m_jMethod = new JComboBox();
        this.lblCategory = new JLabel();
        this.m_jcategory = new JComboBox();
        this.lblTotal = new JLabel();
        this.jTotal = new JEditorCurrency();
        this.lblNotes = new JLabel();
        this.m_jNotes = new JEditorString();
        this.setBackground(new Color(255, 255, 255));
        this.setLayout(new BorderLayout());
        this.jPanelMain.setBackground(new Color(255, 255, 255));
        this.jPanelMain.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        this.jPanelMain.setLayout(new GridBagLayout());
        this.lblTitle.setFont(new Font("Segoe UI", 1, 24));
        this.lblTitle.setForeground(new Color(44, 62, 80));
        this.lblTitle.setText("Detalles del Gasto Gerencial");
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = 17;
        gridBagConstraints.insets = new Insets(0, 0, 30, 0);
        this.jPanelMain.add((Component)this.lblTitle, gridBagConstraints);
        this.lblMethod.setFont(new Font("Segoe UI", 1, 14));
        this.lblMethod.setText("Medio de Pago:");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = 17;
        gridBagConstraints.insets = new Insets(0, 0, 10, 20);
        this.jPanelMain.add((Component)this.lblMethod, gridBagConstraints);
        this.m_jMethod.setFont(new Font("Segoe UI", 0, 14));
        this.m_jMethod.setPreferredSize(new Dimension(300, 40));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = 2;
        gridBagConstraints.insets = new Insets(0, 0, 10, 0);
        this.jPanelMain.add(this.m_jMethod, gridBagConstraints);
        this.lblCategory.setFont(new Font("Segoe UI", 1, 14));
        this.lblCategory.setText("Categor\u00eda Gerencial:");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = 17;
        gridBagConstraints.insets = new Insets(0, 0, 10, 20);
        this.jPanelMain.add((Component)this.lblCategory, gridBagConstraints);
        this.m_jcategory.setFont(new Font("Segoe UI", 0, 14));
        this.m_jcategory.setPreferredSize(new Dimension(300, 40));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = 2;
        gridBagConstraints.insets = new Insets(0, 0, 10, 0);
        this.jPanelMain.add(this.m_jcategory, gridBagConstraints);
        this.lblTotal.setFont(new Font("Segoe UI", 1, 14));
        this.lblTotal.setText("Valor / Monto:");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = 17;
        gridBagConstraints.insets = new Insets(0, 0, 10, 20);
        this.jPanelMain.add((Component)this.lblTotal, gridBagConstraints);
        this.jTotal.setFont(new Font("Segoe UI", 1, 18));
        this.jTotal.setPreferredSize(new Dimension(300, 50));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = 2;
        gridBagConstraints.insets = new Insets(0, 0, 10, 0);
        this.jPanelMain.add((Component)this.jTotal, gridBagConstraints);
        this.lblNotes.setFont(new Font("Segoe UI", 1, 14));
        this.lblNotes.setText("Notas / Descripci\u00f3n:");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = 18;
        gridBagConstraints.insets = new Insets(10, 0, 0, 20);
        this.jPanelMain.add((Component)this.lblNotes, gridBagConstraints);
        this.m_jNotes.setPreferredSize(new Dimension(300, 100));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = 1;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        this.jPanelMain.add((Component)this.m_jNotes, gridBagConstraints);
        this.add((Component)this.jPanelMain, "Center");
    }

    private static class PaymentMethod
    implements IKeyed {
        private String key;
        private String text;

        public PaymentMethod(String key, String text) {
            this.key = key;
            this.text = text;
        }

        @Override
        public Object getKey() {
            return this.key;
        }

        public String toString() {
            return this.text;
        }
    }

    private static class CategoryItem {
        private String id;
        private String name;

        public CategoryItem(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getId() {
            return this.id;
        }

        public String toString() {
            return this.name;
        }

        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (obj instanceof CategoryItem) {
                return this.id.equals(((CategoryItem)obj).id);
            }
            if (obj instanceof String) {
                return this.name.equalsIgnoreCase((String)obj);
            }
            return false;
        }
    }
}

