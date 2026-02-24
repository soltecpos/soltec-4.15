/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.inventory;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.DataRead;
import com.openbravo.data.loader.IKeyed;
import com.openbravo.data.loader.SerializableRead;
import com.openbravo.pos.ticket.ProductInfoExt;

public class MaterialProdInfo
implements SerializableRead,
IKeyed {
    private String m_sID;
    private String m_sName;
    private double m_dPriceBuy;
    private double m_dAmount;
    private String m_sUnit;

    public MaterialProdInfo() {
        this.m_sID = null;
        this.m_sName = null;
        this.m_dPriceBuy = 0.0;
        this.m_dAmount = 0.0;
        this.m_sUnit = null;
    }

    public MaterialProdInfo(ProductInfoExt p) {
        this.m_sID = p.getID();
        this.m_sName = p.getName();
        this.m_dPriceBuy = p.getPriceBuy();
        this.m_dAmount = 1.0;
    }

    @Override
    public void readValues(DataRead dr) throws BasicException {
        this.m_sID = dr.getString(1);
        this.m_sName = dr.getString(2);
        this.m_dPriceBuy = dr.getDouble(3);
        this.m_dAmount = dr.getDouble(4) == null ? 1.0 : dr.getDouble(4);
        this.m_sUnit = dr.getString(5);
    }

    public void setID(String id) {
        this.m_sID = id;
    }

    public String getID() {
        return this.m_sID;
    }

    public void setName(String name) {
        this.m_sName = name;
    }

    public String getName() {
        return this.m_sName;
    }

    public void setPriceBuy(double price) {
        this.m_dPriceBuy = price;
    }

    public double getPriceBuy() {
        return this.m_dPriceBuy;
    }

    public void setAmount(double amount) {
        this.m_dAmount = amount;
    }

    public double getAmount() {
        return this.m_dAmount;
    }

    public void setUnit(String unit) {
        this.m_sUnit = unit;
    }

    public String getUnit() {
        return this.m_sUnit;
    }

    @Override
    public Object getKey() {
        return this.m_sID;
    }

    public final String toString() {
        return this.m_sName + " - " + this.m_dAmount + " " + this.m_sUnit;
    }
}

