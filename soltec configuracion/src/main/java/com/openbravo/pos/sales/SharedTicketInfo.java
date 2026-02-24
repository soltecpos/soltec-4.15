/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.sales;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.DataRead;
import com.openbravo.data.loader.DataWrite;
import com.openbravo.data.loader.SerializableRead;
import com.openbravo.data.loader.SerializableWrite;
import com.openbravo.pos.ticket.TicketInfo;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.Date;

public class SharedTicketInfo
implements SerializableRead,
SerializableWrite {
    private static final long serialVersionUID = 7640633837719L;
    private String id;
    private String name;
    private Date m_dDate;
    private String m_sWaiter;
    private String m_sLocked;

    @Override
    public void readValues(DataRead dr) throws BasicException {
        this.id = dr.getString(1);
        this.name = dr.getString(2);
        try {
            this.m_sLocked = dr.getString(6);
        }
        catch (Exception e) {
            this.m_sLocked = null;
        }
        this.m_sWaiter = dr.getString(4);
        this.m_sWaiter = dr.getString(4);
        try {
            Object value = dr.getObject(3);
            if (value != null) {
                byte[] img;
                ByteArrayInputStream bis;
                ObjectInputStream ois;
                Object o;
                TicketInfo t = null;
                if (value instanceof TicketInfo) {
                    t = (TicketInfo)value;
                } else if (value instanceof byte[] && (o = (ois = new ObjectInputStream(bis = new ByteArrayInputStream(img = (byte[])value))).readObject()) instanceof TicketInfo) {
                    t = (TicketInfo)o;
                }
                if (t != null) {
                    this.m_dDate = t.getDate();
                    if (t.getUser() != null) {
                        this.m_sWaiter = t.getUser().getName();
                    }
                } else {
                    this.m_dDate = new Date();
                }
            } else {
                this.m_dDate = new Date();
            }
        }
        catch (Exception e) {
            this.m_dDate = new Date();
        }
    }

    @Override
    public void writeValues(DataWrite dp) throws BasicException {
        dp.setString(1, this.id);
        dp.setString(2, this.name);
        dp.setString(3, null);
        dp.setString(4, this.m_sWaiter);
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getAppUser() {
        return this.m_sWaiter;
    }

    public String getWaiter() {
        return this.m_sWaiter;
    }

    public Date getDate() {
        return this.m_dDate;
    }

    public String getLocked() {
        return this.m_sLocked;
    }

    public boolean isLocked() {
        return "locked".equals(this.m_sLocked) || "override".equals(this.m_sLocked);
    }
}

