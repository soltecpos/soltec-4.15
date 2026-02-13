/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.ticket;

import java.awt.image.BufferedImage;
import java.util.Properties;

public class ProductInfoEdit {
    protected String m_ID = null;
    protected String m_sRef = "0000";
    protected String m_sCode = "0000";
    protected String m_sCodeType = null;
    protected String m_sName = null;
    protected double m_dPriceBuy = 0.0;
    protected double m_dPriceSell = 0.0;
    protected String m_sCategoryID = null;
    protected String m_sTaxID = null;
    protected String attributeuseid = null;
    protected Double m_dStockCost = null;
    protected Double m_dStockVolume = null;
    protected BufferedImage m_Image = null;
    protected boolean m_bCom = false;
    protected boolean m_bScale = false;
    protected boolean m_bKitchen = false;
    protected boolean m_bPrintKB = false;
    protected boolean m_bSendStatus = false;
    protected boolean m_bService = false;
    protected Properties attributes = new Properties();
    protected String m_sDisplay = null;
    protected boolean m_bVprice = false;
    protected boolean m_bVerpatrib = false;
    protected String m_sTextTip = null;
    protected boolean m_bWarranty = false;
    protected double m_dStockUnits = 0.0;
    protected Integer m_iCatalogOrder = null;
    protected String m_sSupplierID = null;
    protected String m_sUomID = null;

    public final String getID() {
        return this.m_ID;
    }

    public final void setID(String id) {
        this.m_ID = id;
    }

    public final String getReference() {
        return this.m_sRef;
    }

    public final void setReference(String sRef) {
        this.m_sRef = sRef;
    }

    public final String getCode() {
        return this.m_sCode;
    }

    public final void setCode(String sCode) {
        this.m_sCode = sCode;
    }

    public final String getName() {
        return this.m_sName;
    }

    public final void setName(String sName) {
        this.m_sName = sName;
    }

    public final boolean isCom() {
        return this.m_bCom;
    }

    public final void setCom(boolean bValue) {
        this.m_bCom = bValue;
    }

    public final boolean isScale() {
        return this.m_bScale;
    }

    public final void setScale(boolean bValue) {
        this.m_bScale = bValue;
    }

    public final String getCategoryID() {
        return this.m_sCategoryID;
    }

    public final void setCategoryID(String sCategoryID) {
        this.m_sCategoryID = sCategoryID;
    }

    public final String getTaxID() {
        return this.m_sTaxID;
    }

    public final void setTaxID(String sTaxID) {
        this.m_sTaxID = sTaxID;
    }

    public final String getAttributeUseID() {
        return this.attributeuseid;
    }

    public final void setAttributeUseID(String value) {
        this.attributeuseid = value;
    }

    public final double getPriceBuy() {
        return this.m_dPriceBuy;
    }

    public final void setPriceBuy(double dPrice) {
        this.m_dPriceBuy = dPrice;
    }

    public final double getPriceSell() {
        return this.m_dPriceSell;
    }

    public final void setPriceSell(double dPrice) {
        this.m_dPriceSell = dPrice;
    }

    public BufferedImage getImage() {
        return this.m_Image;
    }

    public void setImage(BufferedImage img) {
        this.m_Image = img;
    }

    public final String toString() {
        return this.m_sRef + " - " + this.m_sName;
    }
}

