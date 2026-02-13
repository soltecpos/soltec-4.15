/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.voucher;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.DataRead;
import com.openbravo.data.loader.IKeyed;
import com.openbravo.data.loader.SerializerRead;

public class VoucherInfo
implements IKeyed {
    private String id;
    private String voucherNumber;
    private String customerId;
    private String customerName;
    private double amount;

    public VoucherInfo() {
    }

    public VoucherInfo(String id, String voucherNumber, String customerId, String customerName, double amount) {
        this.id = id;
        this.voucherNumber = voucherNumber;
        this.customerId = customerId;
        this.customerName = customerName;
        this.amount = amount;
    }

    @Override
    public Object getKey() {
        return this.getId();
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVoucherNumber() {
        return this.voucherNumber;
    }

    public void setVoucherNumber(String voucherNumber) {
        this.voucherNumber = voucherNumber;
    }

    public String getCustomerId() {
        return this.customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return this.customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public double getAmount() {
        return this.amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String toString() {
        return this.voucherNumber;
    }

    public static SerializerRead<VoucherInfo> getSerializerRead() {
        return new SerializerRead<VoucherInfo>(){

            @Override
            public VoucherInfo readValues(DataRead dr) throws BasicException {
                return new VoucherInfo(dr.getString(1), dr.getString(2), dr.getString(3), dr.getString(4), dr.getDouble(5));
            }
        };
    }
}

