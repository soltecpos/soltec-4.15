/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.inventory;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.Datas;
import com.openbravo.data.loader.TableDefinition;
import com.openbravo.data.user.EditorRecord;
import com.openbravo.data.user.ListProvider;
import com.openbravo.data.user.ListProviderCreator;
import com.openbravo.data.user.SaveProvider;
import com.openbravo.format.Formats;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.DataLogicSales;
import com.openbravo.pos.inventory.StockDiaryEditor;
import com.openbravo.pos.panels.JPanelTable;

public class StockDiaryPanel
extends JPanelTable {
    private StockDiaryEditor jeditor;
    private DataLogicSales m_dlSales;
    private TableDefinition tstockdiary;

    @Override
    protected void init() {
        this.m_dlSales = (DataLogicSales)this.app.getBean("com.openbravo.pos.forms.DataLogicSales");
        this.jeditor = new StockDiaryEditor(this.app, this.dirty);
        this.tstockdiary = new TableDefinition(this.app.getSession(), "STOCKDIARY", new String[]{"ID", "DATENEW", "REASON", "PRODUCT", "ATTRIBUTE", "LOCATION", "UNITS", "PRICE", "APPUSER"}, new String[]{"ID", "DATE", "REASON", "PRODUCT", "ATTRIBUTE", "LOCATION", "UNITS", "PRICE", "APPUSER"}, new Datas[]{Datas.STRING, Datas.TIMESTAMP, Datas.INT, Datas.STRING, Datas.STRING, Datas.STRING, Datas.DOUBLE, Datas.DOUBLE, Datas.STRING}, new Formats[]{Formats.STRING, Formats.TIMESTAMP, Formats.INT, Formats.STRING, Formats.STRING, Formats.STRING, Formats.DOUBLE, Formats.CURRENCY, Formats.STRING}, new int[]{0});
    }

    @Override
    public ListProvider<Object[]> getListProvider() {
        return new ListProviderCreator<Object[]>(this.tstockdiary);
    }

    @Override
    public SaveProvider<Object[]> getSaveProvider() {
        return new SaveProvider<Object[]>(null, this.m_dlSales.getStockDiaryInsert(), this.m_dlSales.getStockDiaryDelete());
    }

    @Override
    public EditorRecord getEditor() {
        return this.jeditor;
    }

    @Override
    public String getTitle() {
        return AppLocal.getIntString("Menu.StockDiary");
    }

    @Override
    public void activate() throws BasicException {
        this.jeditor.activate();
        super.activate();
    }
}

