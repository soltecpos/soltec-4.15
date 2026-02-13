/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.inventory;

import com.openbravo.format.Formats;
import com.openbravo.pos.ticket.ProductInfoExt;
import com.openbravo.pos.util.StringUtils;

public class InventoryLine {
    private double m_dMultiply;
    private double m_dPrice;
    private String m_sProdID;
    private String m_sProdName;
    private String attsetid;
    private String attsetinstid;
    private String attsetinstdesc;

    public InventoryLine(ProductInfoExt oProduct) {
        this.m_sProdID = oProduct.getID();
        this.m_sProdName = oProduct.getName();
        this.m_dMultiply = 1.0;
        this.m_dPrice = oProduct.getPriceBuy();
        this.attsetid = oProduct.getAttributeSetID();
        this.attsetinstid = null;
        this.attsetinstdesc = null;
    }

    public InventoryLine(ProductInfoExt oProduct, double dpor, double dprice) {
        this.m_sProdID = oProduct.getID();
        this.m_sProdName = oProduct.getName();
        this.m_dMultiply = dpor;
        this.m_dPrice = dprice;
        this.attsetid = oProduct.getAttributeSetID();
        this.attsetinstid = null;
        this.attsetinstdesc = null;
    }

    public String getProductID() {
        return this.m_sProdID;
    }

    public String getProductName() {
        return this.m_sProdName;
    }

    public void setProductName(String sValue) {
        if (this.m_sProdID == null) {
            this.m_sProdName = sValue;
        }
    }

    public double getMultiply() {
        return this.m_dMultiply;
    }

    public void setMultiply(double dValue) {
        this.m_dMultiply = dValue;
    }

    public double getPrice() {
        return this.m_dPrice;
    }

    public void setPrice(double dValue) {
        this.m_dPrice = dValue;
    }

    public double getSubValue() {
        return this.m_dMultiply * this.m_dPrice;
    }

    public String getProductAttSetInstId() {
        return this.attsetinstid;
    }

    public void setProductAttSetInstId(String value) {
        this.attsetinstid = value;
    }

    public String getProductAttSetId() {
        return this.attsetid;
    }

    public String getProductAttSetInstDesc() {
        return this.attsetinstdesc;
    }

    public void setProductAttSetInstDesc(String value) {
        this.attsetinstdesc = value;
    }

    public String printName() {
        return StringUtils.encodeXML(this.m_sProdName);
    }

    public String printPrice() {
        if (this.m_dMultiply == 1.0) {
            return "";
        }
        return Formats.CURRENCY.formatValue(this.getPrice());
    }

    public String printMultiply() {
        return Formats.DOUBLE.formatValue(this.m_dMultiply);
    }

    public String printSubValue() {
        return Formats.CURRENCY.formatValue(this.getSubValue());
    }
}

