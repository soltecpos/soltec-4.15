/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.inventory;

import com.openbravo.basic.BasicException;
import com.openbravo.data.user.EditorListener;
import com.openbravo.data.user.EditorRecord;
import com.openbravo.data.user.ListProviderCreator;
import com.openbravo.data.user.SaveProvider;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.DataLogicSales;
import com.openbravo.pos.inventory.JDlgUploadProducts;
import com.openbravo.pos.inventory.ProductsEditor;
import com.openbravo.pos.panels.JPanelTable2;
import com.openbravo.pos.ticket.ProductFilter;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public class ProductsPanel
extends JPanelTable2
implements EditorListener {
    private ProductsEditor jeditor;
    private ProductFilter jproductfilter;
    private DataLogicSales m_dlSales = null;

    @Override
    protected void init() {
        this.m_dlSales = (DataLogicSales)this.app.getBean("com.openbravo.pos.forms.DataLogicSales");
        this.jproductfilter = new ProductFilter();
        this.jproductfilter.init(this.app);
        this.row = this.m_dlSales.getProductsRow();
        this.lpr = new ListProviderCreator<Object[]>(this.m_dlSales.getProductCatQBF(), this.jproductfilter);
        this.spr = new SaveProvider<Object[]>(this.m_dlSales.getProductCatUpdate(), this.m_dlSales.getProductCatInsert(), this.m_dlSales.getProductCatDelete());
        this.jeditor = new ProductsEditor(this.app, this.dirty);
    }

    @Override
    public EditorRecord getEditor() {
        return this.jeditor;
    }

    @Override
    public Component getFilter() {
        return this.jproductfilter.getComponent();
    }

    @Override
    public Component getToolbarExtras() {
        JButton btnScanPal = new JButton();
        btnScanPal.setText("ScanPal");
        btnScanPal.setVisible(this.app.getDeviceScanner() != null);
        btnScanPal.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ProductsPanel.this.btnScanPalActionPerformed(evt);
            }
        });
        return btnScanPal;
    }

    private void btnScanPalActionPerformed(ActionEvent evt) {
        JDlgUploadProducts.showMessage(this, this.app.getDeviceScanner(), this.bd);
    }

    @Override
    public String getTitle() {
        return AppLocal.getIntString("Menu.Products");
    }

    @Override
    public void activate() throws BasicException {
        this.jeditor.activate();
        this.jproductfilter.activate();
        super.activate();
    }

    @Override
    public void updateValue(Object value) {
    }
}

