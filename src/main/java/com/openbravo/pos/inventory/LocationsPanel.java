/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.inventory;

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
import com.openbravo.pos.inventory.LocationsView;
import com.openbravo.pos.panels.JPanelTable;
import javax.swing.ListCellRenderer;

public class LocationsPanel
extends JPanelTable {
    private TableDefinition tlocations;
    private LocationsView jeditor;

    @Override
    protected void init() {
        DataLogicSales dlSales2 = (DataLogicSales)this.app.getBean("com.openbravo.pos.forms.DataLogicSales");
        this.tlocations = dlSales2.getTableLocations();
        this.jeditor = new LocationsView(this.dirty);
    }

    @Override
    public ListProvider<Object[]> getListProvider() {
        return new ListProviderCreator<Object[]>(this.tlocations);
    }

    @Override
    public SaveProvider<Object[]> getSaveProvider() {
        return new SaveProvider<Object[]>(this.tlocations);
    }

    @Override
    public Vectorer getVectorer() {
        return this.tlocations.getVectorerBasic(new int[]{1, 2});
    }

    @Override
    public ComparatorCreator getComparatorCreator() {
        return this.tlocations.getComparatorCreator(new int[]{1, 2});
    }

    public ListCellRenderer getListCellRenderer() {
        return new ListCellRendererBasic(this.tlocations.getRenderStringBasic(new int[]{1}));
    }

    @Override
    public EditorRecord getEditor() {
        return this.jeditor;
    }

    @Override
    public String getTitle() {
        return AppLocal.getIntString("Menu.Locations");
    }
}

