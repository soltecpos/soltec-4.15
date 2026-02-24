/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.suppliers;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.DataRead;
import com.openbravo.data.loader.SerializerRead;
import com.openbravo.pos.forms.DataLogicSales;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SupplierTransaction {
    Date transactionDate;
    String productName;
    String unit;
    Double price;
    Integer reason;
    String supplierId;

    public SupplierTransaction() {
    }

    public SupplierTransaction(Date transactionDate, String productName, String unit, Double price, Integer reason, String sId) {
        this.transactionDate = transactionDate;
        this.productName = productName;
        this.unit = unit;
        this.price = price;
        this.reason = reason;
        this.supplierId = sId;
    }

    public Date getTransactionDate() {
        return this.transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getProductName() {
        return this.productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getUnit() {
        return this.unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Double getPrice() {
        return this.price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getReason() {
        return this.reason;
    }

    public void setReason(Integer reason) {
        this.reason = reason;
    }

    public String getSupplierId() {
        return this.supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public static SerializerRead<SupplierTransaction> getSerializerRead() {
        return new SerializerRead<SupplierTransaction>(){

            @Override
            public SupplierTransaction readValues(DataRead dr) throws BasicException {
                String dateValue = dr.getString(1);
                String productName = dr.getString(2);
                String unit = dr.getString(3);
                Double price = dr.getDouble(4);
                Integer reason = dr.getInt(5);
                String supplierId = dr.getString(6);
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Date date = null;
                try {
                    date = formatter.parse(dateValue);
                }
                catch (ParseException ex) {
                    Logger.getLogger(DataLogicSales.class.getName()).log(Level.SEVERE, null, ex);
                }
                return new SupplierTransaction(date, productName, unit, price, reason, supplierId);
            }
        };
    }
}

