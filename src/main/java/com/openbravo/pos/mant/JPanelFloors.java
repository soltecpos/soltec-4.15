/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.mant;

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
import com.openbravo.pos.mant.FloorsEditor;
import com.openbravo.pos.panels.JPanelTable;
import javax.swing.ListCellRenderer;

public class JPanelFloors
extends JPanelTable {
    private TableDefinition tfloors;
    private FloorsEditor jeditor;

    @Override
    protected void init() {
        this.tfloors = new TableDefinition(this.app.getSession(), "floors", new String[]{"ID", "NAME", "IMAGE"}, new String[]{"ID", AppLocal.getIntString("label.name"), "IMAGE"}, new Datas[]{Datas.STRING, Datas.STRING, Datas.IMAGE}, new Formats[]{Formats.NULL, Formats.STRING}, new int[]{0});
        this.jeditor = new FloorsEditor(this.dirty);
    }

    @Override
    public ListProvider<Object[]> getListProvider() {
        return new ListProviderCreator<Object[]>(this.tfloors);
    }

    @Override
    public Vectorer getVectorer() {
        return this.tfloors.getVectorerBasic(new int[]{1});
    }

    public ListCellRenderer getListCellRenderer() {
        return new ListCellRendererBasic(this.tfloors.getRenderStringBasic(new int[]{1}));
    }

    @Override
    public SaveProvider<Object[]> getSaveProvider() {
        return new SaveProvider<Object[]>(this.tfloors);
    }

    @Override
    public EditorRecord getEditor() {
        return this.jeditor;
    }

    @Override
    public String getTitle() {
        return AppLocal.getIntString("Menu.Floors");
    }
}

