/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.inventory;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.DataRead;
import com.openbravo.data.loader.Datas;
import com.openbravo.data.loader.PreparedSentence;
import com.openbravo.data.loader.SentenceExecTransaction;
import com.openbravo.data.loader.SerializerRead;
import com.openbravo.data.loader.SerializerWriteBasicExt;
import com.openbravo.data.model.Field;
import com.openbravo.data.model.Row;
import com.openbravo.data.user.EditorRecord;
import com.openbravo.data.user.ListProviderCreator;
import com.openbravo.data.user.SaveProvider;
import com.openbravo.format.Formats;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.inventory.ProductsWarehouseEditor;
import com.openbravo.pos.panels.JPanelTable2;
import com.openbravo.pos.reports.JParamsLocation;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.UUID;

public class ProductsWarehousePanel
extends JPanelTable2 {
    private JParamsLocation m_paramslocation;
    private ProductsWarehouseEditor jeditor;

    @Override
    protected void init() {
        this.m_paramslocation = new JParamsLocation();
        this.m_paramslocation.init(this.app);
        this.m_paramslocation.addActionListener(new ReloadActionListener());
        this.row = new Row(new Field("ID", Datas.STRING, Formats.STRING), new Field("PRODUCT_ID", Datas.STRING, Formats.STRING), new Field(AppLocal.getIntString("label.prodref"), Datas.STRING, Formats.STRING, true, true, true), new Field(AppLocal.getIntString("label.prodname"), Datas.STRING, Formats.STRING, true, true, true), new Field("LOCATION", Datas.STRING, Formats.STRING), new Field("STOCKSECURITY", Datas.DOUBLE, Formats.DOUBLE), new Field("STOCKMAXIMUM", Datas.DOUBLE, Formats.DOUBLE), new Field("UNITS", Datas.DOUBLE, Formats.DOUBLE));
        this.lpr = new ListProviderCreator(new PreparedSentence<Object[], Object[]>(this.app.getSession(), "SELECT L.ID, P.ID, P.REFERENCE, P.NAME,L.STOCKSECURITY, L.STOCKMAXIMUM, COALESCE(S.SUMUNITS, 0) FROM products P LEFT OUTER JOIN (SELECT ID, PRODUCT, LOCATION, STOCKSECURITY, STOCKMAXIMUM FROM stocklevel WHERE LOCATION = ?) L ON P.ID = L.PRODUCT LEFT OUTER JOIN (SELECT PRODUCT, SUM(UNITS) AS SUMUNITS FROM stockcurrent WHERE LOCATION = ? GROUP BY PRODUCT) S ON P.ID = S.PRODUCT ORDER BY P.NAME", new SerializerWriteBasicExt(new Datas[]{Datas.OBJECT, Datas.STRING}, new int[]{1, 1}), new WarehouseSerializerRead()), this.m_paramslocation);
        SentenceExecTransaction<Object[]> updatesent = new SentenceExecTransaction<Object[]>(this.app.getSession()){

            @Override
            public int execInTransaction(Object[] params) throws BasicException {
                Object[] values = params;
                if (values[4] == null) {
                    throw new BasicException(AppLocal.getIntString("message.warehouse_required"));
                }
                if (values[0] == null) {
                    values[0] = UUID.randomUUID().toString();
                    return new PreparedSentence(ProductsWarehousePanel.this.app.getSession(), "INSERT INTO stocklevel (ID, LOCATION, PRODUCT, STOCKSECURITY, STOCKMAXIMUM) VALUES (?, ?, ?, ?, ?)", new SerializerWriteBasicExt(ProductsWarehousePanel.this.row.getDatas(), new int[]{0, 4, 1, 5, 6})).exec(params);
                }
                return new PreparedSentence(ProductsWarehousePanel.this.app.getSession(), "UPDATE stocklevel SET STOCKSECURITY = ?, STOCKMAXIMUM = ? WHERE ID = ?", new SerializerWriteBasicExt(ProductsWarehousePanel.this.row.getDatas(), new int[]{5, 6, 0})).exec(params);
            }
        };
        this.spr = new SaveProvider<Object[]>(updatesent, null, null);
        this.jeditor = new ProductsWarehouseEditor(this.dirty);
    }

    @Override
    public Component getFilter() {
        return this.m_paramslocation.getComponent();
    }

    @Override
    public EditorRecord getEditor() {
        return this.jeditor;
    }

    @Override
    public void activate() throws BasicException {
        this.m_paramslocation.activate();
        super.activate();
    }

    @Override
    public String getTitle() {
        return AppLocal.getIntString("Menu.ProductsWarehouse");
    }

    private class ReloadActionListener
    implements ActionListener {
        private ReloadActionListener() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                ProductsWarehousePanel.this.bd.actionLoad();
            }
            catch (BasicException basicException) {
                // empty catch block
            }
        }
    }

    private class WarehouseSerializerRead
    implements SerializerRead<Object[]> {
        private WarehouseSerializerRead() {
        }

        @Override
        public Object[] readValues(DataRead dr) throws BasicException {
            return new Object[]{dr.getString(1), dr.getString(2), dr.getString(3), dr.getString(4), ((Object[])ProductsWarehousePanel.this.m_paramslocation.createValue())[1], dr.getDouble(5), dr.getDouble(6), dr.getDouble(7)};
        }
    }
}

