/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.inventory;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.DataRead;
import com.openbravo.data.loader.IKeyed;
import com.openbravo.data.loader.SerializableRead;

public class LocationInfo
implements SerializableRead,
IKeyed {
    private static final long serialVersionUID = 9032683595230L;
    private String m_sID = null;
    private String m_sName = null;
    private String m_sAddress = null;

    @Override
    public Object getKey() {
        return this.m_sID;
    }

    @Override
    public void readValues(DataRead dr) throws BasicException {
        this.m_sID = dr.getString(1);
        this.m_sName = dr.getString(2);
        this.m_sAddress = dr.getString(3);
    }

    public void setID(String sID) {
        this.m_sID = sID;
    }

    public String getID() {
        return this.m_sID;
    }

    public String getName() {
        return this.m_sName;
    }

    public void setName(String sName) {
        this.m_sName = sName;
    }

    public String getAddress() {
        return this.m_sAddress;
    }

    public void setAddress(String sAddress) {
        this.m_sAddress = sAddress;
    }

    public String toString() {
        return this.m_sName;
    }
}

