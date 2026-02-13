/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.inventory;

import com.openbravo.data.gui.ListCellRendererBasic;
import com.openbravo.data.loader.TableDefinition;
import com.openbravo.data.loader.Vectorer;
import com.openbravo.data.user.EditorRecord;
import com.openbravo.data.user.ListProvider;
import com.openbravo.data.user.ListProviderCreator;
import com.openbravo.data.user.SaveProvider;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.DataLogicSales;
import com.openbravo.pos.inventory.UomEditor;
import com.openbravo.pos.panels.JPanelTable;
import javax.swing.ListCellRenderer;

public class UomPanel
extends JPanelTable {
    private TableDefinition tuom;
    private UomEditor jeditor;

    @Override
    protected void init() {
        DataLogicSales dlSales2 = (DataLogicSales)this.app.getBean("com.openbravo.pos.forms.DataLogicSales");
        this.tuom = dlSales2.getTableUom();
        this.jeditor = new UomEditor(this.app, this.dirty);
    }

    @Override
    public EditorRecord getEditor() {
        return this.jeditor;
    }

    @Override
    public ListProvider<Object[]> getListProvider() {
        return new ListProviderCreator<Object[]>(this.tuom);
    }

    @Override
    public SaveProvider<Object[]> getSaveProvider() {
        return new SaveProvider<Object[]>(this.tuom);
    }

    @Override
    public Vectorer getVectorer() {
        return this.tuom.getVectorerBasic(new int[]{1});
    }

    public ListCellRenderer getListCellRenderer() {
        return new ListCellRendererBasic(this.tuom.getRenderStringBasic(new int[]{1}));
    }

    @Override
    public String getTitle() {
        return AppLocal.getIntString("Menu.Uom");
    }
}

