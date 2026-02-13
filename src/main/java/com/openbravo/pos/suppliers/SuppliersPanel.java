/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.suppliers;

import com.openbravo.basic.BasicException;
import com.openbravo.data.gui.ListCellRendererBasic;
import com.openbravo.data.loader.ComparatorCreator;
import com.openbravo.data.loader.TableDefinition;
import com.openbravo.data.loader.Vectorer;
import com.openbravo.data.user.EditorRecord;
import com.openbravo.data.user.ListProvider;
import com.openbravo.data.user.ListProviderCreator;
import com.openbravo.data.user.SaveProvider;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.panels.JPanelTable;
import com.openbravo.pos.suppliers.DataLogicSuppliers;
import com.openbravo.pos.suppliers.SupplierInfoGlobal;
import com.openbravo.pos.suppliers.SuppliersView;
import javax.swing.ListCellRenderer;

public class SuppliersPanel
extends JPanelTable {
    private TableDefinition tsuppliers;
    private SuppliersView jeditor;

    public SuppliersPanel() {
        SupplierInfoGlobal.getInstance().setEditableData(this.bd);
    }

    @Override
    protected void init() {
        DataLogicSuppliers dlSuppliers = (DataLogicSuppliers)this.app.getBean("com.openbravo.pos.suppliers.DataLogicSuppliers");
        this.tsuppliers = dlSuppliers.getTableSuppliers();
        this.jeditor = new SuppliersView(this.app, this.dirty);
    }

    @Override
    public void activate() throws BasicException {
        this.jeditor.activate();
        super.activate();
    }

    @Override
    public ListProvider<Object[]> getListProvider() {
        return new ListProviderCreator<Object[]>(this.tsuppliers);
    }

    @Override
    public SaveProvider<Object[]> getSaveProvider() {
        return new SaveProvider<Object[]>(this.tsuppliers, new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21});
    }

    @Override
    public Vectorer getVectorer() {
        return this.tsuppliers.getVectorerBasic(new int[]{1, 2, 3, 4});
    }

    @Override
    public ComparatorCreator getComparatorCreator() {
        return this.tsuppliers.getComparatorCreator(new int[]{1, 2, 3, 4});
    }

    public ListCellRenderer getListCellRenderer() {
        return new ListCellRendererBasic(this.tsuppliers.getRenderStringBasic(new int[]{3}));
    }

    @Override
    public EditorRecord getEditor() {
        return this.jeditor;
    }

    @Override
    public String getTitle() {
        return AppLocal.getIntString("Menu.SuppliersManagement");
    }
}

