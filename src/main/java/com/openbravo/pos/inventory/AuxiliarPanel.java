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
import com.openbravo.pos.inventory.AuxiliarEditor;
import com.openbravo.pos.panels.AuxiliarFilter;
import com.openbravo.pos.panels.JPanelTable2;
import com.openbravo.pos.ticket.ProductInfoExt;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AuxiliarPanel
extends JPanelTable2 {
    private AuxiliarEditor editor;
    private AuxiliarFilter filter;

    @Override
    protected void init() {
        this.filter = new AuxiliarFilter();
        this.filter.init(this.app);
        this.filter.addActionListener(new ReloadActionListener());
        this.row = new Row(new Field("ID", Datas.STRING, Formats.STRING), new Field("PRODUCT1", Datas.STRING, Formats.STRING), new Field("PRODUCT2", Datas.STRING, Formats.STRING), new Field(AppLocal.getIntString("label.prodref"), Datas.STRING, Formats.STRING, true, true, true), new Field(AppLocal.getIntString("label.prodbarcode"), Datas.STRING, Formats.STRING, false, true, true), new Field(AppLocal.getIntString("label.prodname"), Datas.STRING, Formats.STRING, true, true, true));
        Table table = new Table("products_com", new PrimaryKey("ID"), new Column("PRODUCT"), new Column("PRODUCT2"));
        this.lpr = this.row.getListProvider(this.app.getSession(), "SELECT COM.ID, COM.PRODUCT, COM.PRODUCT2, P.REFERENCE, P.CODE, P.NAME FROM products_com COM, products P WHERE COM.PRODUCT2 = P.ID AND COM.PRODUCT = ?", this.filter);
        this.spr = this.row.getSaveProvider(this.app.getSession(), table);
        this.editor = new AuxiliarEditor(this.app, this.dirty);
    }

    @Override
    public void activate() throws BasicException {
        this.filter.activate();
        this.startNavigation();
        this.reload(this.filter);
    }

    @Override
    public Component getFilter() {
        return this.filter.getComponent();
    }

    @Override
    public EditorRecord getEditor() {
        return this.editor;
    }

    @Override
    public String getTitle() {
        return AppLocal.getIntString("Menu.Auxiliar");
    }

    private void reload(AuxiliarFilter filter) throws BasicException {
        ProductInfoExt prod = filter.getProductInfoExt();
        this.editor.setInsertProduct(prod);
        this.bd.setEditable(prod != null);
        this.bd.actionLoad();
    }

    private class ReloadActionListener
    implements ActionListener {
        private ReloadActionListener() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                AuxiliarPanel.this.reload((AuxiliarFilter)e.getSource());
            }
            catch (BasicException basicException) {
                // empty catch block
            }
        }
    }
}

