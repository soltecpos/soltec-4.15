/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.admin;

import com.openbravo.basic.BasicException;
import com.openbravo.data.gui.ListCellRendererBasic;
import com.openbravo.data.loader.ComparatorCreator;
import com.openbravo.data.loader.TableDefinition;
import com.openbravo.data.loader.Vectorer;
import com.openbravo.data.user.EditorRecord;
import com.openbravo.data.user.ListProvider;
import com.openbravo.data.user.ListProviderCreator;
import com.openbravo.data.user.SaveProvider;
import com.openbravo.pos.admin.DataLogicAdmin;
import com.openbravo.pos.admin.PeopleView;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.panels.JPanelTable;
import javax.swing.ListCellRenderer;

public class PeoplePanel
extends JPanelTable {
    private static final long serialVersionUID = 1L;
    private TableDefinition tpeople;
    private PeopleView jeditor;

    @Override
    protected void init() {
        DataLogicAdmin dlAdmin = (DataLogicAdmin)this.app.getBean("com.openbravo.pos.admin.DataLogicAdmin");
        this.tpeople = dlAdmin.getTablePeople();
        this.jeditor = new PeopleView(dlAdmin, this.dirty);
    }

    @Override
    public ListProvider<Object[]> getListProvider() {
        return new ListProviderCreator<Object[]>(this.tpeople);
    }

    @Override
    public SaveProvider<Object[]> getSaveProvider() {
        return new SaveProvider<Object[]>(this.tpeople);
    }

    @Override
    public Vectorer getVectorer() {
        return this.tpeople.getVectorerBasic(new int[]{1});
    }

    @Override
    public ComparatorCreator getComparatorCreator() {
        return this.tpeople.getComparatorCreator(new int[]{1, 3});
    }

    @Override
    public ListCellRenderer<? super Object[]> getListCellRenderer() {
        return new ListCellRendererBasic(this.tpeople.getRenderStringBasic(new int[]{1}));
    }

    @Override
    public EditorRecord getEditor() {
        return this.jeditor;
    }

    @Override
    public void activate() throws BasicException {
        this.jeditor.activate();
        super.activate();
    }

    @Override
    public String getTitle() {
        return AppLocal.getIntString("Menu.Users");
    }
}

