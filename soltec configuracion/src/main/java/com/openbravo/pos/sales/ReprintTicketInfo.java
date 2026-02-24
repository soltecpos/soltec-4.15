/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.sales;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.DataRead;
import com.openbravo.data.loader.DataWrite;
import com.openbravo.data.loader.SerializableRead;
import com.openbravo.data.loader.SerializableWrite;

public class ReprintTicketInfo
implements SerializableRead,
SerializableWrite {
    private static final long serialVersionUID = 7640633837719L;
    private String id;
    private String total;
    private String ticketDate;
    private String UserName;

    @Override
    public void readValues(DataRead dr) throws BasicException {
        this.id = dr.getString(1);
        this.total = dr.getString(2);
        this.ticketDate = dr.getString(3);
        this.UserName = dr.getString(4);
    }

    @Override
    public void writeValues(DataWrite dp) throws BasicException {
        dp.setString(1, this.id);
        dp.setString(2, this.total);
        dp.setString(3, this.ticketDate);
        dp.setString(4, this.UserName);
    }

    public String getId() {
        return this.id;
    }

    public String getTotal() {
        return this.total;
    }

    public String getTicketDate() {
        return this.ticketDate;
    }

    public String getUserName() {
        return this.UserName;
    }
}

