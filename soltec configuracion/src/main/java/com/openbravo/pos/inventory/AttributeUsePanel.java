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
import com.openbravo.pos.inventory.AttributeSetFilter;
import com.openbravo.pos.inventory.AttributeUseEditor;
import com.openbravo.pos.panels.JPanelTable2;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AttributeUsePanel
extends JPanelTable2 {
    private AttributeUseEditor editor;
    private AttributeSetFilter filter;

    @Override
    protected void init() {
        this.filter = new AttributeSetFilter();
        this.filter.init(this.app);
        this.filter.addActionListener(new ReloadActionListener());
        this.row = new Row(new Field("ID", Datas.STRING, Formats.STRING), new Field("ATRIBUTESET_ID", Datas.STRING, Formats.STRING), new Field("ATTRIBUTE_ID", Datas.STRING, Formats.STRING), new Field(AppLocal.getIntString("label.order"), Datas.INT, Formats.INT, false, true, true), new Field(AppLocal.getIntString("label.name"), Datas.STRING, Formats.STRING, true, true, true));
        Table table = new Table("attributeuse", new PrimaryKey("ID"), new Column("attributeset_ID"), new Column("ATTRIBUTE_ID"), new Column("LINENO"));
        this.lpr = this.row.getListProvider(this.app.getSession(), "SELECT ATTUSE.ID, ATTUSE.attributeset_ID, ATTUSE.ATTRIBUTE_ID, ATTUSE.LINENO, ATT.NAME FROM attributeuse ATTUSE, attribute ATT WHERE ATTUSE.ATTRIBUTE_ID = ATT.ID AND ATTUSE.attributeset_ID = ? ORDER BY LINENO", this.filter);
        this.spr = this.row.getSaveProvider(this.app.getSession(), table);
        this.editor = new AttributeUseEditor(this.app, this.dirty);
    }

    @Override
    public void activate() throws BasicException {
        this.filter.activate();
        this.editor.activate();
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
        String attsetid = (String)this.filter.createValue();
        this.editor.setInsertId(attsetid);
        this.bd.setEditable(attsetid != null);
        this.bd.actionLoad();
    }

    @Override
    public String getTitle() {
        return AppLocal.getIntString("Menu.AttributeUse");
    }

    private class ReloadActionListener
    implements ActionListener {
        private ReloadActionListener() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                AttributeUsePanel.this.reload();
            }
            catch (BasicException basicException) {
                // empty catch block
            }
        }
    }
}

