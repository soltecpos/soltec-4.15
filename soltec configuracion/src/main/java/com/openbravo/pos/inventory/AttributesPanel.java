/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.inventory;

import com.openbravo.data.loader.Datas;
import com.openbravo.data.model.Column;
import com.openbravo.data.model.Field;
import com.openbravo.data.model.PrimaryKey;
import com.openbravo.data.model.Row;
import com.openbravo.data.model.Table;
import com.openbravo.data.user.EditorRecord;
import com.openbravo.format.Formats;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.inventory.AttributesEditor;
import com.openbravo.pos.panels.JPanelTable2;

public class AttributesPanel
extends JPanelTable2 {
    private static final long serialVersionUID = 1L;
    private EditorRecord editor;

    @Override
    protected void init() {
        this.row = new Row(new Field("ID", Datas.STRING, Formats.STRING), new Field(AppLocal.getIntString("label.name"), Datas.STRING, Formats.STRING, true, true, true));
        Table table = new Table("attribute", new PrimaryKey("ID"), new Column("NAME"));
        this.lpr = this.row.getListProvider(this.app.getSession(), table);
        this.spr = this.row.getSaveProvider(this.app.getSession(), table);
        this.editor = new AttributesEditor(this.dirty);
    }

    @Override
    public EditorRecord getEditor() {
        return this.editor;
    }

    @Override
    public String getTitle() {
        return AppLocal.getIntString("Menu.Attributes");
    }
}

