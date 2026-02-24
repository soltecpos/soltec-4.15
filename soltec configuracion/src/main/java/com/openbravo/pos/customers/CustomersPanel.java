/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.customers;

import com.openbravo.basic.BasicException;
import com.openbravo.data.gui.ListCellRendererBasic;
import com.openbravo.data.loader.ComparatorCreator;
import com.openbravo.data.loader.TableDefinition;
import com.openbravo.data.loader.Vectorer;
import com.openbravo.data.user.EditorRecord;
import com.openbravo.data.user.ListProvider;
import com.openbravo.data.user.ListProviderCreator;
import com.openbravo.data.user.SaveProvider;
import com.openbravo.pos.customers.CustomersView;
import com.openbravo.pos.customers.DataLogicCustomers;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.panels.JPanelTable;
import javax.swing.ListCellRenderer;

public class CustomersPanel
extends JPanelTable {
    private static final long serialVersionUID = 1L;
    private TableDefinition tcustomers;
    private CustomersView jeditor;

    @Override
    protected void init() {
        DataLogicCustomers dlCustomers = (DataLogicCustomers)this.app.getBean("com.openbravo.pos.customers.DataLogicCustomers");
        this.tcustomers = dlCustomers.getTableCustomers();
        this.jeditor = new CustomersView(this.app, this.dirty);
    }

    @Override
    public void activate() throws BasicException {
        this.jeditor.activate();
        super.activate();
    }

    @Override
    public ListProvider<Object[]> getListProvider() {
        return new ListProviderCreator<Object[]>(this.tcustomers);
    }

    @Override
    public SaveProvider<Object[]> getSaveProvider() {
        return new SaveProvider<Object[]>(this.tcustomers, new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31});
    }

    @Override
    public Vectorer getVectorer() {
        return this.tcustomers.getVectorerBasic(new int[]{1, 2, 3, 4});
    }

    @Override
    public ComparatorCreator getComparatorCreator() {
        return this.tcustomers.getComparatorCreator(new int[]{1, 2, 3, 4});
    }

    @Override
    public ListCellRenderer<? super Object[]> getListCellRenderer() {
        return new ListCellRendererBasic(this.tcustomers.getRenderStringBasic(new int[]{3}));
    }

    @Override
    public EditorRecord getEditor() {
        return this.jeditor;
    }

    @Override
    public String getTitle() {
        return AppLocal.getIntString("Menu.CustomersManagement");
    }
}

