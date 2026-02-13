/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.ticket;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.DataRead;
import com.openbravo.data.loader.ImageUtils;
import com.openbravo.data.loader.SerializerRead;
import com.openbravo.format.Formats;
import com.openbravo.pos.ticket.TaxInfo;
import java.awt.image.BufferedImage;
import java.util.Properties;

public class ProductInfoExt {
    private static final long serialVersionUID = 7587696873036L;
    protected String m_ID = null;
    protected String m_sRef = "0000";
    protected String m_sCode = "0000";
    protected String m_sCodetype = null;
    protected String m_sName = null;
    protected double m_dPriceBuy = 0.0;
    protected double m_dPriceSell = 0.0;
    protected String categoryid = null;
    protected String taxcategoryid = null;
    protected String attributesetid = null;
    protected double m_stockCost = 0.0;
    protected double m_stockVolume = 0.0;
    protected BufferedImage m_Image = null;
    protected boolean m_bCom = false;
    protected boolean m_bScale = false;
    protected boolean m_bConstant = false;
    protected boolean m_bPrintKB = false;
    protected boolean m_bSendStatus = false;
    private boolean m_bService = false;
    protected Properties attributes = new Properties();
    protected String m_sDisplay = null;
    protected boolean m_bVprice = false;
    protected boolean m_bVerpatrib = false;
    protected String m_sTextTip = null;
    protected boolean m_bWarranty = false;
    public double m_dStockUnits = 0.0;
    public String m_sPrinter = null;
    public String supplierid = "0";
    private String uomid = "0";

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

    public final String getCodetype() {
        return this.m_sCodetype;
    }

    public final void setCodetype(String sCodetype) {
        this.m_sCodetype = sCodetype;
    }

    public final String getName() {
        return this.m_sName;
    }

    public final void setName(String sName) {
        this.m_sName = sName;
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

    public final String getCategoryID() {
        return this.categoryid;
    }

    public final void setCategoryID(String sCategoryID) {
        this.categoryid = sCategoryID;
    }

    public final String getTaxCategoryID() {
        return this.taxcategoryid;
    }

    public final void setTaxCategoryID(String value) {
        this.taxcategoryid = value;
    }

    public final String getAttributeSetID() {
        return this.attributesetid;
    }

    public final void setAttributeSetID(String value) {
        this.attributesetid = value;
    }

    public final double getStockCost() {
        return this.m_stockCost;
    }

    public final void setStockCost(double dPrice) {
        this.m_stockCost = dPrice;
    }

    public final double getStockVolume() {
        return this.m_stockVolume;
    }

    public final void setStockVolume(double dStockVolume) {
        this.m_stockVolume = dStockVolume;
    }

    public BufferedImage getImage() {
        return this.m_Image;
    }

    public void setImage(BufferedImage img) {
        this.m_Image = img;
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

    public final boolean isConstant() {
        return this.m_bConstant;
    }

    public final void setConstant(boolean bValue) {
        this.m_bConstant = bValue;
    }

    public final boolean isPrintKB() {
        return this.m_bPrintKB;
    }

    public final void setPrintKB(boolean bValue) {
        this.m_bPrintKB = bValue;
    }

    public final boolean isSendStatus() {
        return this.m_bSendStatus;
    }

    public final void setSendStatus(boolean bValue) {
        this.m_bSendStatus = bValue;
    }

    public final boolean isService() {
        return this.m_bService;
    }

    public final void setService(boolean bValue) {
        this.m_bService = bValue;
    }

    public String getProperty(String key) {
        return this.attributes.getProperty(key);
    }

    public String getProperty(String key, String defaultvalue) {
        return this.attributes.getProperty(key, defaultvalue);
    }

    public void setProperty(String key, String value) {
        this.attributes.setProperty(key, value);
    }

    public Properties getProperties() {
        return this.attributes;
    }

    public final String getDisplay() {
        return this.m_sDisplay;
    }

    public final void setDisplay(String sDisplay) {
        this.m_sDisplay = sDisplay;
    }

    public final boolean isVprice() {
        return this.m_bVprice;
    }

    public final boolean isVerpatrib() {
        return this.m_bVerpatrib;
    }

    public final String getTextTip() {
        return this.m_sTextTip;
    }

    public final void setTextTip(String value) {
        this.m_sTextTip = value;
    }

    public final boolean getWarranty() {
        return this.m_bWarranty;
    }

    public final void setWarranty(boolean bValue) {
        this.m_bWarranty = bValue;
    }

    public final Double getStockUnits() {
        return this.m_dStockUnits;
    }

    public final void setStockUnits(double dStockUnits) {
        this.m_dStockUnits = dStockUnits;
    }

    public String printPriceSell() {
        return Formats.CURRENCY.formatValue(this.getPriceSell());
    }

    public final double getPriceSellTax(TaxInfo tax) {
        return this.m_dPriceSell * (1.0 + tax.getRate());
    }

    public String printPriceSellTax(TaxInfo tax) {
        return Formats.CURRENCY.formatValue(this.getPriceSellTax(tax));
    }

    public final String getPrinter() {
        return this.m_sPrinter;
    }

    public final void setPrinter(String value) {
        this.m_sPrinter = value;
    }

    public final String getSupplierID() {
        return this.supplierid;
    }

    public final void setSupplierID(String sSupplierID) {
        this.supplierid = sSupplierID;
    }

    public final String getUomID() {
        return this.uomid;
    }

    public final void setUomID(String sUomID) {
        this.uomid = sUomID;
    }

    public static SerializerRead<ProductInfoExt> getSerializerRead() {
        return new SerializerRead<ProductInfoExt>(){

            @Override
            public ProductInfoExt readValues(DataRead dr) throws BasicException {
                ProductInfoExt product = new ProductInfoExt();
                product.m_ID = dr.getString(1);
                product.m_sRef = dr.getString(2);
                product.m_sCode = dr.getString(3);
                product.m_sCodetype = dr.getString(4);
                product.m_sName = dr.getString(5);
                product.m_dPriceBuy = dr.getDouble(6);
                product.m_dPriceSell = dr.getDouble(7);
                product.categoryid = dr.getString(8);
                product.taxcategoryid = dr.getString(9);
                product.attributesetid = dr.getString(10);
                product.m_stockCost = dr.getDouble(11);
                product.m_stockVolume = dr.getDouble(12);
                product.m_Image = ImageUtils.readImage(dr.getBytes(13));
                product.m_bCom = dr.getBoolean(14);
                product.m_bScale = dr.getBoolean(15);
                product.m_bConstant = dr.getBoolean(16);
                product.m_bPrintKB = dr.getBoolean(17);
                product.m_bSendStatus = dr.getBoolean(18);
                product.m_bService = dr.getBoolean(19);
                product.attributes = ImageUtils.readProperties(dr.getBytes(20));
                product.m_sDisplay = dr.getString(21);
                product.m_bVprice = dr.getBoolean(22);
                product.m_bVerpatrib = dr.getBoolean(23);
                product.m_sTextTip = dr.getString(24);
                product.m_bWarranty = dr.getBoolean(25);
                product.m_dStockUnits = dr.getDouble(26);
                product.m_sPrinter = dr.getString(27);
                product.supplierid = dr.getString(28);
                product.uomid = dr.getString(29);
                return product;
            }
        };
    }

    public final String toString() {
        return this.m_sRef + " - " + this.m_sName;
    }
}

