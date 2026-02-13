/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.inventory;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.Datas;
import com.openbravo.data.model.Column;
import com.openbravo.data.model.Field;
import com.openbravo.data.model.PrimaryKey;
import com.openbravo.data.model.Row;
import com.openbravo.data.model.Table;
import com.openbravo.data.user.EditorRecord;
import com.openbravo.format.Formats;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.inventory.AttributeFilter;
import com.openbravo.pos.inventory.AttributeValuesEditor;
import com.openbravo.pos.panels.JPanelTable2;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AttributeValuesPanel
extends JPanelTable2 {
    private AttributeValuesEditor editor;
    private AttributeFilter filter;

    @Override
    protected void init() {
        this.filter = new AttributeFilter();
        this.filter.init(this.app);
        this.filter.addActionListener(new ReloadActionListener());
        this.row = new Row(new Field("ID", Datas.STRING, Formats.STRING), new Field("ATTRIBUTE_ID", Datas.STRING, Formats.STRING), new Field(AppLocal.getIntString("label.value"), Datas.STRING, Formats.STRING, true, true, true));
        Table table = new Table("attributevalue", new PrimaryKey("ID"), new Column("ATTRIBUTE_ID"), new Column("VALUE"));
        this.lpr = this.row.getListProvider(this.app.getSession(), "SELECT ID, ATTRIBUTE_ID, VALUE FROM attributevalue WHERE ATTRIBUTE_ID = ? ORDER BY VALUE ", this.filter);
        this.spr = this.row.getSaveProvider(this.app.getSession(), table);
        this.editor = new AttributeValuesEditor(this.dirty);
    }

    @Override
    public void activate() throws BasicException {
        this.filter.activate();
        this.startNavigation();
        this.reload();
    }

    @Override
    public Component getFilter() {
        return this.filter.getComponent();
    }

    @Override
    public EditorRecord getEditor() {
        return this.editor;
    }

    private void reload() throws BasicException {
        String attid = (String)this.filter.createValue();
        this.editor.setInsertId(attid);
        this.bd.setEditable(attid != null);
        this.bd.actionLoad();
    }

    @Override
    public String getTitle() {
        return AppLocal.getIntString("Menu.AttributeValues");
    }

    private class ReloadActionListener
    implements ActionListener {
        private ReloadActionListener() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                AttributeValuesPanel.this.reload();
            }
            catch (BasicException basicException) {
                // empty catch block
            }
        }
    }
}

