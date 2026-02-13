/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.ticket;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.DataRead;
import com.openbravo.data.loader.DataWrite;
import com.openbravo.data.loader.SerializableRead;
import com.openbravo.data.loader.SerializableWrite;
import com.openbravo.format.Formats;
import com.openbravo.pos.ticket.ProductInfoExt;
import com.openbravo.pos.ticket.TaxInfo;
import com.openbravo.pos.util.StringUtils;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Properties;

public class TicketLineInfo
implements SerializableWrite,
SerializableRead,
Serializable {
    private static final long serialVersionUID = 6608012948284450199L;
    private String m_sTicket;
    private int m_iLine;
    private double multiply;
    private double price;
    private TaxInfo tax;
    private Properties attributes;
    private String productid;
    private String attsetinstid;
    private Boolean updated = false;

    public TicketLineInfo(String productid, double dMultiply, double dPrice, TaxInfo tax, Properties props) {
        this.init(productid, null, dMultiply, dPrice, tax, props);
    }

    public TicketLineInfo(String productid, double dMultiply, double dPrice, TaxInfo tax) {
        this.init(productid, null, dMultiply, dPrice, tax, new Properties());
    }

    public TicketLineInfo(String productid, String productname, String producttaxcategory, double dMultiply, double dPrice, TaxInfo tax) {
        Properties props = new Properties();
        props.setProperty("product.name", productname);
        props.setProperty("product.taxcategoryid", producttaxcategory);
        this.init(productid, null, dMultiply, dPrice, tax, props);
    }

    public TicketLineInfo(String productname, String producttaxcategory, double dMultiply, double dPrice, TaxInfo tax) {
        Properties props = new Properties();
        props.setProperty("product.name", productname);
        props.setProperty("product.taxcategoryid", producttaxcategory);
        this.init(null, null, dMultiply, dPrice, tax, props);
    }

    public TicketLineInfo() {
        this.init(null, null, 0.0, 0.0, null, new Properties());
    }

    public TicketLineInfo(ProductInfoExt product, double dMultiply, double dPrice, TaxInfo tax, Properties attributes) {
        String pid;
        if (product == null) {
            pid = null;
        } else {
            pid = product.getID();
            attributes.setProperty("product.name", product.getName());
            attributes.setProperty("product.com", product.isCom() ? "true" : "false");
            attributes.setProperty("product.constant", product.isConstant() ? "true" : "false");
            if (product.getPrinter() == null) {
                attributes.setProperty("product.printer", "1");
            } else {
                attributes.setProperty("product.printer", product.getPrinter());
            }
            attributes.setProperty("product.service", product.isService() ? "true" : "false");
            attributes.setProperty("product.vprice", product.isVprice() ? "true" : "false");
            attributes.setProperty("product.verpatrib", product.isVerpatrib() ? "true" : "false");
            if (product.getTextTip() != null) {
                attributes.setProperty("product.texttip", product.getTextTip());
            }
            attributes.setProperty("product.warranty", product.getWarranty() ? "true" : "false");
            if (product.getAttributeSetID() != null) {
                attributes.setProperty("product.attsetid", product.getAttributeSetID());
            }
            attributes.setProperty("product.taxcategoryid", product.getTaxCategoryID());
            if (product.getCategoryID() != null) {
                attributes.setProperty("product.categoryid", product.getCategoryID());
            }
        }
        this.init(pid, null, dMultiply, dPrice, tax, attributes);
    }

    public TicketLineInfo(ProductInfoExt oProduct, double dPrice, TaxInfo tax, Properties attributes) {
        this(oProduct, 1.0, dPrice, tax, attributes);
    }

    public TicketLineInfo(TicketLineInfo line) {
        this.init(line.productid, line.attsetinstid, line.multiply, line.price, line.tax, (Properties)line.attributes.clone());
    }

    private void init(String productid, String attsetinstid, double dMultiply, double dPrice, TaxInfo tax, Properties attributes) {
        this.productid = productid;
        this.attsetinstid = attsetinstid;
        this.multiply = dMultiply;
        this.price = dPrice;
        this.tax = tax;
        this.attributes = attributes;
        this.m_sTicket = null;
        this.m_iLine = -1;
    }

    void setTicket(String ticket, int line) {
        this.m_sTicket = ticket;
        this.m_iLine = line;
    }

    @Override
    public void writeValues(DataWrite dp) throws BasicException {
        dp.setString(1, this.m_sTicket);
        dp.setInt(2, this.m_iLine);
        dp.setString(3, this.productid);
        dp.setString(4, this.attsetinstid);
        dp.setDouble(5, this.multiply);
        dp.setDouble(6, this.price);
        dp.setString(7, this.tax.getId());
        try {
            ByteArrayOutputStream o = new ByteArrayOutputStream();
            this.attributes.storeToXML(o, "SOLTEC POS", "UTF-8");
            dp.setBytes(8, o.toByteArray());
        }
        catch (IOException e) {
            dp.setBytes(8, null);
        }
    }

    @Override
    public void readValues(DataRead dr) throws BasicException {
        this.m_sTicket = dr.getString(1);
        this.m_iLine = dr.getInt(2);
        this.productid = dr.getString(3);
        this.attsetinstid = dr.getString(4);
        this.multiply = dr.getDouble(5);
        this.price = dr.getDouble(6);
        this.tax = new TaxInfo(dr.getString(7), dr.getString(8), dr.getString(9), dr.getString(10), dr.getString(11), dr.getDouble(12), dr.getBoolean(13), dr.getInt(14));
        this.attributes = new Properties();
        try {
            byte[] img = dr.getBytes(15);
            if (img != null) {
                this.attributes.loadFromXML(new ByteArrayInputStream(img));
            }
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    public TicketLineInfo copyTicketLine() {
        TicketLineInfo l = new TicketLineInfo();
        l.productid = this.productid;
        l.attsetinstid = this.attsetinstid;
        l.multiply = this.multiply;
        l.price = this.price;
        l.tax = this.tax;
        l.attributes = (Properties)this.attributes.clone();
        return l;
    }

    public int getTicketLine() {
        return this.m_iLine;
    }

    public String getProductID() {
        return this.productid;
    }

    public String getProductCategoryID() {
        return this.attributes.getProperty("product.categoryid");
    }

    public String getProductAttSetId() {
        return this.attributes.getProperty("product.attsetid");
    }

    public String getProductAttSetInstId() {
        return this.attsetinstid;
    }

    public String getProductAttSetInstDesc() {
        return this.attributes.getProperty("product.attsetdesc", "");
    }

    public String getProductTaxCategoryID() {
        return this.attributes.getProperty("product.taxcategoryid");
    }

    public TaxInfo getTaxInfo() {
        return this.tax;
    }

    public String getProductName() {
        return this.attributes.getProperty("product.name");
    }

    public double getPrice() {
        return this.price;
    }

    public double getMultiply() {
        return this.multiply;
    }

    public double getTaxRate() {
        return this.tax == null ? 0.0 : this.tax.getRate();
    }

    public double getPriceTax() {
        return this.price * (1.0 + this.getTaxRate());
    }

    public Properties getProperties() {
        return this.attributes;
    }

    public String getProperty(String key) {
        return this.attributes.getProperty(key);
    }

    public String getProperty(String key, String defaultvalue) {
        return this.attributes.getProperty(key, defaultvalue);
    }

    public double getSubValue() {
        return this.price * this.multiply;
    }

    public double getTax() {
        return this.price * this.multiply * this.getTaxRate();
    }

    public double getValue() {
        return this.price * this.multiply * (1.0 + this.getTaxRate());
    }

    public void setPrice(double dValue) {
        this.price = dValue;
    }

    public void setPriceTax(double dValue) {
        this.price = dValue / (1.0 + this.getTaxRate());
    }

    public void setTaxInfo(TaxInfo value) {
        this.tax = value;
    }

    public void setMultiply(double dValue) {
        this.multiply = dValue;
    }

    public void setProperty(String key, String value) {
        this.attributes.setProperty(key, value);
    }

    public void setProductTaxCategoryID(String taxID) {
        this.attributes.setProperty("product.taxcategoryid", taxID);
    }

    public void setProductAttSetInstId(String value) {
        this.attsetinstid = value;
    }

    public void setProductAttSetInstDesc(String value) {
        if (value == null) {
            this.attributes.remove(value);
        } else {
            this.attributes.setProperty("product.attsetdesc", value);
        }
    }

    public String printName() {
        return StringUtils.encodeXML(this.attributes.getProperty("product.name"));
    }

    public String printPrice() {
        return Formats.CURRENCY.formatValue(this.getPrice());
    }

    public String printPriceTax() {
        return Formats.CURRENCY.formatValue(this.getPriceTax());
    }

    public String printMultiply() {
        return Formats.DOUBLE.formatValue(this.multiply);
    }

    public String printValue() {
        return Formats.CURRENCY.formatValue(this.getValue());
    }

    public String printTaxRate() {
        return Formats.PERCENT.formatValue(this.getTaxRate());
    }

    public String printSubValue() {
        return Formats.CURRENCY.formatValue(this.getSubValue());
    }

    public String printTax() {
        return Formats.CURRENCY.formatValue(this.getTax());
    }

    public String printTextTip() {
        return this.attributes.getProperty("product.texttip");
    }

    public String printPrinter() {
        return StringUtils.encodeXML(this.attributes.getProperty("product.printer"));
    }

    public boolean isProductCom() {
        return "true".equals(this.attributes.getProperty("product.com"));
    }

    public boolean isProductService() {
        return "true".equals(this.attributes.getProperty("product.service"));
    }

    public boolean isProductVprice() {
        return "true".equals(this.attributes.getProperty("product.vprice"));
    }

    public boolean isProductVerpatrib() {
        return "true".equals(this.attributes.getProperty("product.verpatrib"));
    }

    public boolean isProductWarranty() {
        return "true".equals(this.attributes.getProperty("product.warranty"));
    }

    public boolean getUpdated() {
        return this.updated;
    }

    public void setUpdated(Boolean value) {
        this.updated = value;
    }
}

