/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.panels;

import com.openbravo.data.loader.ComparatorCreator;
import com.openbravo.data.loader.Vectorer;
import com.openbravo.data.model.Row;
import com.openbravo.data.user.ListProvider;
import com.openbravo.data.user.SaveProvider;
import com.openbravo.pos.panels.JPanelTable;
import javax.swing.ListCellRenderer;

public abstract class JPanelTable2
extends JPanelTable {
    protected Row row;
    protected ListProvider<Object[]> lpr;
    protected SaveProvider<Object[]> spr;

    @Override
    public final ListProvider<Object[]> getListProvider() {
        return this.lpr;
    }

    @Override
    public final SaveProvider<Object[]> getSaveProvider() {
        return this.spr;
    }

    @Override
    public final Vectorer getVectorer() {
        return this.row.getVectorer();
    }

    @Override
    public final ComparatorCreator getComparatorCreator() {
        return this.row.getComparatorCreator();
    }

    public final ListCellRenderer getListCellRenderer() {
        return this.row.getListCellRenderer();
    }
}

