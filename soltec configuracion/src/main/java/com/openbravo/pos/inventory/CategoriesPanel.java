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
import com.openbravo.pos.inventory.CategoriesEditor;
import com.openbravo.pos.panels.JPanelTable;
import javax.swing.ListCellRenderer;

public class CategoriesPanel
extends JPanelTable {
    private static final long serialVersionUID = 1L;
    private TableDefinition tcategories;
    private CategoriesEditor jeditor;

    @Override
    protected void init() {
        DataLogicSales dlSales2 = (DataLogicSales)this.app.getBean("com.openbravo.pos.forms.DataLogicSales");
        this.tcategories = dlSales2.getTableCategories();
        this.jeditor = new CategoriesEditor(this.app, this.dirty);
    }

    @Override
    public ListProvider<Object[]> getListProvider() {
        return new ListProviderCreator<Object[]>(this.tcategories);
    }

    @Override
    public SaveProvider<Object[]> getSaveProvider() {
        return new SaveProvider<Object[]>(this.tcategories);
    }

    @Override
    public Vectorer getVectorer() {
        return this.tcategories.getVectorerBasic(new int[]{1});
    }

    @Override
    public ComparatorCreator getComparatorCreator() {
        return this.tcategories.getComparatorCreator(new int[]{1});
    }

    @Override
    public ListCellRenderer<? super Object[]> getListCellRenderer() {
        return new ListCellRendererBasic(this.tcategories.getRenderStringBasic(new int[]{1}));
    }

    @Override
    public EditorRecord getEditor() {
        return this.jeditor;
    }

    @Override
    public String getTitle() {
        return AppLocal.getIntString("Menu.Categories");
    }
}

