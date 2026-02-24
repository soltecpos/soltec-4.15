/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.epm;

import com.openbravo.basic.BasicException;
import com.openbravo.data.gui.ListCellRendererBasic;
import com.openbravo.data.loader.ComparatorCreator;
import com.openbravo.data.loader.TableDefinition;
import com.openbravo.data.loader.Vectorer;
import com.openbravo.data.user.EditorRecord;
import com.openbravo.data.user.ListProvider;
import com.openbravo.data.user.ListProviderCreator;
import com.openbravo.data.user.SaveProvider;
import com.openbravo.pos.epm.DataLogicPresenceManagement;
import com.openbravo.pos.epm.LeavesView;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.panels.JPanelTable;
import javax.swing.ListCellRenderer;

public class LeavesPanel
extends JPanelTable {
    private TableDefinition tleaves;
    private LeavesView jeditor;

    @Override
    protected void init() {
        DataLogicPresenceManagement dlPresenceManagement = (DataLogicPresenceManagement)this.app.getBean("com.openbravo.pos.epm.DataLogicPresenceManagement");
        this.tleaves = dlPresenceManagement.getTableLeaves();
        this.jeditor = new LeavesView(this.app, this.dirty);
    }

    @Override
    public void activate() throws BasicException {
        this.jeditor.activate();
        super.activate();
    }

    @Override
    public ListProvider<Object[]> getListProvider() {
        return new ListProviderCreator<Object[]>(this.tleaves);
    }

    @Override
    public SaveProvider<Object[]> getSaveProvider() {
        return new SaveProvider<Object[]>(this.tleaves, new int[]{0, 1, 2, 3, 4, 5});
    }

    @Override
    public Vectorer getVectorer() {
        return this.tleaves.getVectorerBasic(new int[]{2, 5});
    }

    @Override
    public ComparatorCreator getComparatorCreator() {
        return this.tleaves.getComparatorCreator(new int[]{2, 3, 4, 5});
    }

    public ListCellRenderer getListCellRenderer() {
        return new ListCellRendererBasic(this.tleaves.getRenderStringBasic(new int[]{2}));
    }

    @Override
    public EditorRecord getEditor() {
        return this.jeditor;
    }

    @Override
    public String getTitle() {
        return AppLocal.getIntString("Menu.Leaves");
    }
}

