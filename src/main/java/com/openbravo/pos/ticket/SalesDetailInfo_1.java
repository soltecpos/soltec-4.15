/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.ticket;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.DataRead;
import com.openbravo.data.loader.IKeyed;
import com.openbravo.data.loader.SerializerRead;
import com.openbravo.format.Formats;
import com.openbravo.pos.ticket.SalesDetailInfo;

public class SalesDetailInfo_1
implements IKeyed {
    private static final long serialVersionUID = 8612449444103L;
    private String productName;
    private int lineNO;
    private double price;

    public int getLineNO() {
        return this.lineNO;
    }

    public void setLineNO(int lineNO) {
        this.lineNO = lineNO;
    }

    public double getPrice() {
        return this.price;
    }

    public String printPrice() {
        return Formats.CURRENCY.formatValue(this.price);
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getProductName() {
        return this.productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public SalesDetailInfo_1(int lineNo, String productName, double price) {
        this.lineNO = lineNo;
        this.productName = productName;
        this.price = price;
    }

    public static SerializerRead<SalesDetailInfo> getSerializerRead() {
        return new SerializerRead<SalesDetailInfo>(){

            @Override
            public SalesDetailInfo readValues(DataRead dr) throws BasicException {
                return new SalesDetailInfo(dr.getInt(1), dr.getString(2), dr.getDouble(3));
            }
        };
    }

    @Override
    public Object getKey() {
        return this.getLineNO();
    }
}

