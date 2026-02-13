/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.voucher;

import com.openbravo.data.loader.Datas;
import com.openbravo.data.model.Column;
import com.openbravo.data.model.Field;
import com.openbravo.data.model.PrimaryKey;
import com.openbravo.data.model.Row;
import com.openbravo.data.model.Table;
import com.openbravo.data.user.EditorRecord;
import com.openbravo.format.Formats;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.panels.JPanelTable2;
import com.openbravo.pos.voucher.VoucherEditor;

public class VoucherPanel
extends JPanelTable2 {
    private VoucherEditor editor;

    @Override
    protected void init() {
        this.row = new Row(new Field("ID", Datas.STRING, Formats.STRING), new Field(AppLocal.getIntString("label.Number"), Datas.STRING, Formats.STRING, true, true, true), new Field(AppLocal.getIntString("label.customer"), Datas.STRING, Formats.STRING), new Field(AppLocal.getIntString("label.paymenttotal"), Datas.DOUBLE, Formats.DOUBLE));
        Table table = new Table("vouchers", new PrimaryKey("ID"), new Column("VOUCHER_NUMBER"), new Column("CUSTOMER"), new Column("AMOUNT"));
        this.lpr = this.row.getListProvider(this.app.getSession(), table);
        this.spr = this.row.getSaveProvider(this.app.getSession(), table);
        this.editor = new VoucherEditor(this.dirty, this.app);
    }

    @Override
    public EditorRecord getEditor() {
        return this.editor;
    }

    @Override
    public String getTitle() {
        return AppLocal.getIntString("Menu.Vouchers");
    }
}

