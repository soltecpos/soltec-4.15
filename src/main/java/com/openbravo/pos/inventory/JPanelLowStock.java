/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.inventory;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.DataRead;
import com.openbravo.data.loader.Datas;
import com.openbravo.data.loader.PreparedSentence;
import com.openbravo.data.loader.SerializerRead;
import com.openbravo.data.loader.SerializerWriteBasicExt;
import com.openbravo.data.model.Field;
import com.openbravo.data.model.Row;
import com.openbravo.data.user.EditorRecord;
import com.openbravo.data.user.ListProviderCreator;
import com.openbravo.format.Formats;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.panels.JPanelTable2;
import com.openbravo.pos.reports.JParamsLocation;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JPanelLowStock
extends JPanelTable2 {
    private JParamsLocation m_paramslocation;

    @Override
    protected void init() {
        this.m_paramslocation = new JParamsLocation();
        this.m_paramslocation.init(this.app);
        this.m_paramslocation.addActionListener(new ReloadActionListener());
        this.row = new Row(new Field(AppLocal.getIntString("label.prodref"), Datas.STRING, Formats.STRING), new Field(AppLocal.getIntString("label.prodname"), Datas.STRING, Formats.STRING), new Field("STOCKSECURITY", Datas.DOUBLE, Formats.DOUBLE), new Field("UNITS", Datas.DOUBLE, Formats.DOUBLE));
        this.lpr = new ListProviderCreator(new PreparedSentence(this.app.getSession(), "SELECT P.REFERENCE, P.NAME, L.STOCKSECURITY, COALESCE(S.UNITS, 0) FROM PRODUCTS P JOIN STOCKLEVEL L ON P.ID = L.PRODUCT LEFT JOIN STOCKCURRENT S ON P.ID = S.PRODUCT AND L.LOCATION = S.LOCATION WHERE L.LOCATION = ? AND COALESCE(S.UNITS, 0) < L.STOCKSECURITY ORDER BY P.NAME", new SerializerWriteBasicExt(new Datas[]{Datas.OBJECT, Datas.STRING}, new int[]{1}), new SerializerRead(){

            public Object readValues(DataRead dr) throws BasicException {
                return new Object[]{dr.getString(1), dr.getString(2), dr.getDouble(3), dr.getDouble(4)};
            }
        }), this.m_paramslocation);
        this.spr = null;
    }

    @Override
    public Component getFilter() {
        return this.m_paramslocation.getComponent();
    }

    @Override
    public EditorRecord getEditor() {
        return null;
    }

    @Override
    public void activate() throws BasicException {
        this.m_paramslocation.activate();
        super.activate();
    }

    @Override
    public String getTitle() {
        return AppLocal.getIntString("Menu.LowStock");
    }

    private class ReloadActionListener
    implements ActionListener {
        private ReloadActionListener() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                JPanelLowStock.this.bd.actionLoad();
            }
            catch (BasicException basicException) {
                // empty catch block
            }
        }
    }
}

