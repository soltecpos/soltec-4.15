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
import com.openbravo.pos.admin.ResourcesView;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.DataLogicSystem;
import com.openbravo.pos.panels.JPanelTable;
import javax.swing.ListCellRenderer;

public class ResourcesPanel
extends JPanelTable {
    private static final long serialVersionUID = 1L;
    private TableDefinition tresources;
    private ResourcesView jeditor;

    @Override
    protected void init() {
        DataLogicAdmin dlAdmin = (DataLogicAdmin)this.app.getBean("com.openbravo.pos.admin.DataLogicAdmin");
        this.tresources = dlAdmin.getTableResources();
        this.jeditor = new ResourcesView(this.dirty);
    }

    @Override
    public boolean deactivate() {
        if (super.deactivate()) {
            DataLogicSystem dlSystem = (DataLogicSystem)this.app.getBean("com.openbravo.pos.forms.DataLogicSystem");
            dlSystem.resetResourcesCache();
            return true;
        }
        return false;
    }

    @Override
    public ListProvider<Object[]> getListProvider() {
        return new ListProviderCreator<Object[]>(this.tresources);
    }

    @Override
    public SaveProvider<Object[]> getSaveProvider() {
        return new SaveProvider<Object[]>(this.tresources);
    }

    @Override
    public Vectorer getVectorer() {
        return this.tresources.getVectorerBasic(new int[]{1});
    }

    @Override
    public ComparatorCreator getComparatorCreator() {
        return this.tresources.getComparatorCreator(new int[]{1, 2});
    }

    @Override
    public ListCellRenderer<? super Object[]> getListCellRenderer() {
        return new ListCellRendererBasic(this.tresources.getRenderStringBasic(new int[]{1}));
    }

    @Override
    public EditorRecord getEditor() {
        return this.jeditor;
    }

    @Override
    public String getTitle() {
        return AppLocal.getIntString("Menu.Resources");
    }
}

