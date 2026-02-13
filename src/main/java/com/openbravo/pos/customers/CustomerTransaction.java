/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.customers;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.DataRead;
import com.openbravo.data.loader.SerializerRead;
import com.openbravo.pos.forms.DataLogicSales;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomerTransaction {
    String ticketId;
    String productName;
    String unit;
    Double amount;
    Double total;
    Date transactionDate;
    String customerId;

    public CustomerTransaction() {
    }

    public CustomerTransaction(String ticketId, String productName, String unit, Double amount, Double total, Date transactionDate, String cId) {
        this.ticketId = ticketId;
        this.productName = productName;
        this.unit = unit;
        this.amount = amount;
        this.total = total;
        this.transactionDate = transactionDate;
        this.customerId = cId;
    }

    public String getTicketId() {
        return this.ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public Double getAmount() {
        return this.amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getTotal() {
        return this.total;
    }

    public String getProductName() {
        return this.productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Date getTransactionDate() {
        return this.transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getUnit() {
        return this.unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getCustomerId() {
        return this.customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public static SerializerRead<CustomerTransaction> getSerializerRead() {
        return new SerializerRead<CustomerTransaction>(){

            @Override
            public CustomerTransaction readValues(DataRead dr) throws BasicException {
                String ticketId = dr.getString(1);
                String productName = dr.getString(2);
                String unit = dr.getString(3);
                Double amount = dr.getDouble(4);
                Double total = dr.getDouble(5);
                String dateValue = dr.getString(6);
                String customerId = dr.getString(7);
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Date date = null;
                try {
                    date = formatter.parse(dateValue);
                }
                catch (ParseException ex) {
                    Logger.getLogger(DataLogicSales.class.getName()).log(Level.SEVERE, null, ex);
                }
                return new CustomerTransaction(ticketId, productName, unit, amount, total, date, customerId);
            }
        };
    }
}

