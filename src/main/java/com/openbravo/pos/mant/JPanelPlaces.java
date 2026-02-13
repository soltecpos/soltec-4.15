/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.mant;

import com.openbravo.basic.BasicException;
import com.openbravo.data.gui.ListCellRendererBasic;
import com.openbravo.data.loader.Datas;
import com.openbravo.data.loader.TableDefinition;
import com.openbravo.data.loader.Vectorer;
import com.openbravo.data.user.EditorRecord;
import com.openbravo.data.user.ListProvider;
import com.openbravo.data.user.ListProviderCreator;
import com.openbravo.data.user.SaveProvider;
import com.openbravo.format.Formats;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.DataLogicSales;
import com.openbravo.pos.mant.PlacesEditor;
import com.openbravo.pos.panels.JPanelTable;
import javax.swing.ListCellRenderer;

public class JPanelPlaces
extends JPanelTable {
    private TableDefinition tplaces;
    private PlacesEditor jeditor;

    @Override
    protected void init() {
        DataLogicSales dlSales2 = null;
        dlSales2 = (DataLogicSales)this.app.getBean("com.openbravo.pos.forms.DataLogicSales");
        this.tplaces = new TableDefinition(this.app.getSession(), "places", new String[]{"ID", "NAME", "X", "Y", "FLOOR"}, new String[]{"ID", AppLocal.getIntString("label.name"), "X", "Y", AppLocal.getIntString("label.placefloor")}, new Datas[]{Datas.STRING, Datas.STRING, Datas.INT, Datas.INT, Datas.STRING}, new Formats[]{Formats.STRING, Formats.STRING, Formats.INT, Formats.INT, Formats.NULL}, new int[]{0});
        this.jeditor = new PlacesEditor(dlSales2, this.dirty);
    }

    @Override
    public ListProvider<Object[]> getListProvider() {
        return new ListProviderCreator<Object[]>(this.tplaces);
    }

    @Override
    public SaveProvider<Object[]> getSaveProvider() {
        return new SaveProvider<Object[]>(this.tplaces);
    }

    @Override
    public Vectorer getVectorer() {
        return this.tplaces.getVectorerBasic(new int[]{1});
    }

    public ListCellRenderer getListCellRenderer() {
        return new ListCellRendererBasic(this.tplaces.getRenderStringBasic(new int[]{1}));
    }

    @Override
    public EditorRecord getEditor() {
        return this.jeditor;
    }

    @Override
    public String getTitle() {
        return AppLocal.getIntString("Menu.Tables");
    }

    @Override
    public void activate() throws BasicException {
        this.jeditor.activate();
        super.activate();
    }
}

