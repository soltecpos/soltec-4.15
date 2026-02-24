/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.inventory;

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
import com.openbravo.pos.forms.DataLogicSales;
import com.openbravo.pos.inventory.TaxEditor;
import com.openbravo.pos.panels.JPanelTable;
import javax.swing.ListCellRenderer;

public class TaxPanel
extends JPanelTable {
    private TableDefinition ttaxes;
    private TaxEditor jeditor;

    @Override
    protected void init() {
        DataLogicSales dlSales2 = (DataLogicSales)this.app.getBean("com.openbravo.pos.forms.DataLogicSales");
        this.ttaxes = dlSales2.getTableTaxes();
        this.jeditor = new TaxEditor(this.app, this.dirty);
    }

    @Override
    public void activate() throws BasicException {
        this.jeditor.activate();
        super.activate();
    }

    @Override
    public ListProvider<Object[]> getListProvider() {
        return new ListProviderCreator<Object[]>(this.ttaxes);
    }

    @Override
    public SaveProvider<Object[]> getSaveProvider() {
        return new SaveProvider<Object[]>(this.ttaxes);
    }

    @Override
    public Vectorer getVectorer() {
        return this.ttaxes.getVectorerBasic(new int[]{1, 5, 7});
    }

    @Override
    public ComparatorCreator getComparatorCreator() {
        return this.ttaxes.getComparatorCreator(new int[]{1, 5, 7});
    }

    public ListCellRenderer getListCellRenderer() {
        return new ListCellRendererBasic(this.ttaxes.getRenderStringBasic(new int[]{1}));
    }

    @Override
    public EditorRecord getEditor() {
        return this.jeditor;
    }

    @Override
    public String getTitle() {
        return AppLocal.getIntString("Menu.Taxes");
    }
}

