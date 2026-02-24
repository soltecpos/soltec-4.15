/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.sales;

import com.openbravo.basic.BasicException;
import com.openbravo.data.gui.ComboBoxValModel;
import com.openbravo.data.gui.MessageInf;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.forms.DataLogicSales;
import com.openbravo.pos.inventory.TaxCategoryInfo;
import com.openbravo.pos.sales.TaxesLogic;
import com.openbravo.pos.ticket.CategoryInfo;
import com.openbravo.pos.ticket.TaxInfo;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class JDialogCreateProduct
extends JDialog {
    private final DataLogicSales dlSales;
    private final AppView m_App;
    private final ComboBoxValModel<TaxCategoryInfo> taxCatModel;
    private final ComboBoxValModel<CategoryInfo> catModel;
    private JTextField txtCode;
    private JTextField txtName;
    private JTextField txtPrice;
    private JTextField txtPriceTax;
    private JTextField txtPriceBuy;
    private JComboBox<TaxCategoryInfo> dboTaxCat;
    private JComboBox<CategoryInfo> dboCategory;
    private TaxesLogic taxesLogic;
    private boolean isUpdatingPrice = false;
    private boolean result = false;

    public JDialogCreateProduct(Window parent, DataLogicSales dlSales2, AppView app, ComboBoxValModel<TaxCategoryInfo> taxCatModel) {
        this(parent, dlSales2, app, taxCatModel, null);
    }

    public JDialogCreateProduct(Window parent, DataLogicSales dlSales2, AppView app, ComboBoxValModel<TaxCategoryInfo> taxCatModel, ComboBoxValModel<CategoryInfo> catModel) {
        super(parent, "Creaci\u00c3\u00b3n de Producto", Dialog.ModalityType.APPLICATION_MODAL);
        this.dlSales = dlSales2;
        this.m_App = app;
        this.taxCatModel = taxCatModel;
        this.catModel = catModel;
        try {
            List<TaxInfo> taxlist = dlSales2.getTaxList().list();
            this.taxesLogic = new TaxesLogic(taxlist);
        }
        catch (BasicException e) {
            this.taxesLogic = null;
        }
        this.initComponents();
    }

    private void initComponents() {
        this.setDefaultCloseOperation(2);
        this.setResizable(false);
        JPanel panelContent = new JPanel(new GridLayout(0, 2, 5, 5));
        panelContent.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelContent.add(new JLabel(AppLocal.getIntString("label.prodbarcode")));
        this.txtCode = new JTextField();
        this.txtCode.setEditable(true);
        panelContent.add(this.txtCode);
        panelContent.add(new JLabel(AppLocal.getIntString("label.prodname")));
        this.txtName = new JTextField();
        panelContent.add(this.txtName);
        panelContent.add(new JLabel(AppLocal.getIntString("label.prodpricebuy")));
        this.txtPriceBuy = new JTextField();
        panelContent.add(this.txtPriceBuy);
        panelContent.add(new JLabel(AppLocal.getIntString("label.prodpricesell")));
        this.txtPrice = new JTextField();
        panelContent.add(this.txtPrice);
        panelContent.add(new JLabel(AppLocal.getIntString("label.prodpriceselltax")));
        this.txtPriceTax = new JTextField();
        panelContent.add(this.txtPriceTax);
        panelContent.add(new JLabel(AppLocal.getIntString("label.taxcategory")));
        this.dboTaxCat = new JComboBox();
        this.dboTaxCat.setModel(this.taxCatModel);
        panelContent.add(this.dboTaxCat);
        if (this.catModel != null) {
            panelContent.add(new JLabel(AppLocal.getIntString("label.prodcategory")));
            this.dboCategory = new JComboBox();
            this.dboCategory.setModel(this.catModel);
            this.dboCategory.setEditable(true);
            panelContent.add(this.dboCategory);
        }
        this.dboTaxCat.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                JDialogCreateProduct.this.updatePricesFromNet();
            }
        });
        this.txtPrice.getDocument().addDocumentListener(new DocumentListener(){

            @Override
            public void changedUpdate(DocumentEvent e) {
                this.updateFromNet();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                this.updateFromNet();
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                this.updateFromNet();
            }

            private void updateFromNet() {
                if (!JDialogCreateProduct.this.isUpdatingPrice) {
                    JDialogCreateProduct.this.updatePricesFromNet();
                }
            }
        });
        this.txtPriceTax.getDocument().addDocumentListener(new DocumentListener(){

            @Override
            public void changedUpdate(DocumentEvent e) {
                this.updateFromGross();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                this.updateFromGross();
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                this.updateFromGross();
            }

            private void updateFromGross() {
                if (!JDialogCreateProduct.this.isUpdatingPrice) {
                    JDialogCreateProduct.this.updatePricesFromGross();
                }
            }
        });
        JPanel panelButtons = new JPanel(new FlowLayout(2));
        JButton btnOK = new JButton(AppLocal.getIntString("button.save"));
        btnOK.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                JDialogCreateProduct.this.onOK();
            }
        });
        JButton btnCancel = new JButton(AppLocal.getIntString("button.cancel"));
        btnCancel.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                JDialogCreateProduct.this.dispose();
            }
        });
        panelButtons.add(btnOK);
        panelButtons.add(btnCancel);
        this.setLayout(new BorderLayout());
        this.add((Component)panelContent, "Center");
        this.add((Component)panelButtons, "South");
        this.pack();
        this.setLocationRelativeTo(this.getParent());
    }

    private double getTaxRate() {
        if (this.taxesLogic == null) {
            return 0.0;
        }
        TaxCategoryInfo tci = (TaxCategoryInfo)this.dboTaxCat.getSelectedItem();
        if (tci == null) {
            return 0.0;
        }
        return this.taxesLogic.getTaxRate(tci);
    }

    private void updatePricesFromNet() {
        if (this.isUpdatingPrice) {
            return;
        }
        try {
            String sNet = this.txtPrice.getText();
            if (sNet.isEmpty()) {
                return;
            }
            sNet = sNet.replace(',', '.');
            double net = Double.parseDouble(sNet);
            double rate = this.getTaxRate();
            double gross = net * (1.0 + rate);
            this.isUpdatingPrice = true;
            try {
                this.txtPriceTax.setText(String.format(Locale.US, "%.2f", gross));
            }
            catch (Exception e) {
                this.txtPriceTax.setText(Double.toString(gross));
            }
            this.isUpdatingPrice = false;
        }
        catch (NumberFormatException numberFormatException) {
            // empty catch block
        }
    }

    private void updatePricesFromGross() {
        if (this.isUpdatingPrice) {
            return;
        }
        try {
            String sGross = this.txtPriceTax.getText();
            if (sGross.isEmpty()) {
                return;
            }
            sGross = sGross.replace(',', '.');
            double gross = Double.parseDouble(sGross);
            double rate = this.getTaxRate();
            double net = gross / (1.0 + rate);
            this.isUpdatingPrice = true;
            try {
                this.txtPrice.setText(String.format(Locale.US, "%.2f", net));
            }
            catch (Exception e) {
                this.txtPrice.setText(Double.toString(net));
            }
            this.isUpdatingPrice = false;
        }
        catch (NumberFormatException numberFormatException) {
            // empty catch block
        }
    }

    public boolean showDialog(String code) {
        this.txtCode.setText(code);
        this.txtName.setText("");
        this.txtPrice.setText("0.00");
        this.txtPriceTax.setText("0.00");
        this.txtPriceBuy.setText("0.00");
        this.updatePricesFromNet();
        this.txtName.requestFocus();
        this.setVisible(true);
        return this.result;
    }

    private void onOK() {
        try {
            if (this.txtName.getText().trim().isEmpty()) {
                Toolkit.getDefaultToolkit().beep();
                return;
            }
            String sPrice = this.txtPrice.getText().replace(',', '.');
            String sPriceBuy = this.txtPriceBuy.getText().replace(',', '.');
            double dPrice = 0.0;
            try {
                dPrice = Double.parseDouble(sPrice);
            }
            catch (NumberFormatException e) {
                Toolkit.getDefaultToolkit().beep();
                return;
            }
            double dPriceBuy = 0.0;
            try {
                dPriceBuy = Double.parseDouble(sPriceBuy);
            }
            catch (NumberFormatException numberFormatException) {
                // empty catch block
            }
            TaxCategoryInfo tci = (TaxCategoryInfo)this.dboTaxCat.getSelectedItem();
            if (tci == null) {
                Toolkit.getDefaultToolkit().beep();
                return;
            }
            String catID = "000";
            if (this.dboCategory != null) {
                String newCatName;
                Object selectedCat = this.dboCategory.getSelectedItem();
                if (selectedCat instanceof CategoryInfo) {
                    catID = ((CategoryInfo)selectedCat).getID();
                } else if (selectedCat instanceof String && !(newCatName = ((String)selectedCat).trim()).isEmpty()) {
                    catID = UUID.randomUUID().toString();
                    this.dlSales.createCategory(new Object[]{catID, newCatName, true});
                }
            } else {
                try {
                    List<CategoryInfo> roots = this.dlSales.getRootCategories();
                    if (roots != null && !roots.isEmpty()) {
                        catID = roots.get(0).getID();
                    }
                }
                catch (BasicException roots) {
                    // empty catch block
                }
            }
            Object[] myprod = new Object[]{UUID.randomUUID().toString(), this.txtCode.getText(), this.txtCode.getText(), "EAN", this.txtName.getText(), dPriceBuy, dPrice, catID, tci.getID(), null, 0.0, 0.0, null, false, false, false, false, false, false, null, this.txtName.getText(), false, false, null, false, 0.0, "1", null, "0", true, null};
            this.dlSales.getProductCatInsert().exec(myprod);
            this.result = true;
            this.dispose();
        }
        catch (Exception e) {
            new MessageInf(e).show(this);
        }
    }
}

