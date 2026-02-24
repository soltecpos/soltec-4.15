/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.ticket;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.DataRead;
import com.openbravo.data.loader.SerializableRead;
import com.openbravo.format.Formats;
import java.util.Date;

public class FindTicketsInfo
implements SerializableRead {
    private int ticketid;
    private int tickettype;
    private Date date;
    private String name;
    private String customer;
    private double total;
    private int ticketstatus;

    @Override
    public void readValues(DataRead dr) throws BasicException {
        this.ticketid = dr.getInt(1);
        this.tickettype = dr.getInt(2);
        this.date = dr.getTimestamp(3);
        this.name = dr.getString(4);
        this.customer = dr.getString(5);
        this.total = dr.getObject(6) == null ? 0.0 : dr.getDouble(6);
        this.ticketstatus = dr.getInt(7);
    }

    public String toString() {
        String sCustomer = this.customer == null ? "" : this.customer;
        String sHtml = "<tr><td width=\"75\">[" + this.ticketid + "]</td><td width=\"75\">" + Formats.TIMESTAMP.formatValue(this.date) + "</td><td align=\"right\" width=\"100\">" + Formats.CURRENCY.formatValue(this.total) + "</td><td align=\"left\" width=\"100\">" + sCustomer + "</td><td align=\"left\" width=\"100\">" + Formats.STRING.formatValue(this.name) + "</td></tr>";
        return sHtml;
    }

    public int getTicketId() {
        return this.ticketid;
    }

    public int getTicketType() {
        return this.tickettype;
    }

    public int getTicketStatus() {
        return this.ticketstatus;
    }
}

