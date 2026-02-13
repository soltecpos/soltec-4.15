/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.ticket;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.Vectorer;
import com.openbravo.format.Formats;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.ticket.ProductInfoExt;

public class ProductVectorer
implements Vectorer {
    private static String[] m_sHeaders = new String[]{AppLocal.getIntString("label.prodref"), AppLocal.getIntString("label.prodbarcode"), AppLocal.getIntString("label.prodname"), AppLocal.getIntString("label.prodpricebuy"), AppLocal.getIntString("label.prodpricesell")};

    @Override
    public String[] getHeaders() throws BasicException {
        return m_sHeaders;
    }

    @Override
    public String[] getValues(Object obj) throws BasicException {
        ProductInfoExt myprod = (ProductInfoExt)obj;
        String[] m_sValues = new String[]{Formats.STRING.formatValue(myprod.getReference()), Formats.STRING.formatValue(myprod.getCode()), Formats.STRING.formatValue(myprod.getName()), Formats.CURRENCY.formatValue(myprod.getPriceBuy()), Formats.CURRENCY.formatValue(myprod.getPriceSell())};
        return m_sValues;
    }
}

