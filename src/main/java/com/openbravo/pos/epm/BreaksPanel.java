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
import com.openbravo.pos.epm.BreaksView;
import com.openbravo.pos.epm.DataLogicPresenceManagement;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.panels.JPanelTable;
import javax.swing.ListCellRenderer;

public class BreaksPanel
extends JPanelTable {
    private static final long serialVersionUID = 1L;
    private TableDefinition tbreaks;
    private BreaksView jeditor;

    @Override
    protected void init() {
        DataLogicPresenceManagement dlPresenceManagement = (DataLogicPresenceManagement)this.app.getBean("com.openbravo.pos.epm.DataLogicPresenceManagement");
        this.tbreaks = dlPresenceManagement.getTableBreaks();
        this.jeditor = new BreaksView(this.app, this.dirty);
    }

    @Override
    public void activate() throws BasicException {
        this.jeditor.activate();
        super.activate();
    }

    @Override
    public ListProvider<Object[]> getListProvider() {
        return new ListProviderCreator<Object[]>(this.tbreaks);
    }

    @Override
    public SaveProvider<Object[]> getSaveProvider() {
        return new SaveProvider<Object[]>(this.tbreaks, new int[]{0, 1, 2, 3});
    }

    @Override
    public Vectorer getVectorer() {
        return this.tbreaks.getVectorerBasic(new int[]{1, 2});
    }

    @Override
    public ComparatorCreator getComparatorCreator() {
        return this.tbreaks.getComparatorCreator(new int[]{1, 2});
    }

    @Override
    public ListCellRenderer<? super Object[]> getListCellRenderer() {
        return new ListCellRendererBasic(this.tbreaks.getRenderStringBasic(new int[]{1}));
    }

    @Override
    public EditorRecord getEditor() {
        return this.jeditor;
    }

    @Override
    public String getTitle() {
        return AppLocal.getIntString("Menu.Breaks");
    }
}

