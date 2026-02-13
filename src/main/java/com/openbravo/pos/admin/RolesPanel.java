/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.admin;

import com.openbravo.data.gui.ListCellRendererBasic;
import com.openbravo.data.loader.ComparatorCreator;
import com.openbravo.data.loader.TableDefinition;
import com.openbravo.data.loader.Vectorer;
import com.openbravo.data.user.EditorRecord;
import com.openbravo.data.user.ListProvider;
import com.openbravo.data.user.ListProviderCreator;
import com.openbravo.data.user.SaveProvider;
import com.openbravo.pos.admin.DataLogicAdmin;
import com.openbravo.pos.admin.RolesView;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.panels.JPanelTable;
import javax.swing.ListCellRenderer;

public class RolesPanel
extends JPanelTable {
    private static final long serialVersionUID = 1L;
    private TableDefinition troles;
    private TableDefinition trolesmenu;
    private RolesView jeditor;

    @Override
    protected void init() {
        DataLogicAdmin dlAdmin = (DataLogicAdmin)this.app.getBean("com.openbravo.pos.admin.DataLogicAdmin");
        this.troles = dlAdmin.getTableRoles();
        this.jeditor = new RolesView(this.dirty);
    }

    @Override
    public ListProvider<Object[]> getListProvider() {
        return new ListProviderCreator<Object[]>(this.troles);
    }

    @Override
    public SaveProvider<Object[]> getSaveProvider() {
        return new SaveProvider<Object[]>(this.troles);
    }

    @Override
    public Vectorer getVectorer() {
        return this.troles.getVectorerBasic(new int[]{1});
    }

    @Override
    public ComparatorCreator getComparatorCreator() {
        return this.troles.getComparatorCreator(new int[]{1});
    }

    @Override
    public ListCellRenderer<? super Object[]> getListCellRenderer() {
        return new ListCellRendererBasic(this.troles.getRenderStringBasic(new int[]{1}));
    }

    @Override
    public EditorRecord getEditor() {
        return this.jeditor;
    }

    @Override
    public String getTitle() {
        return AppLocal.getIntString("Menu.Roles");
    }
}

