/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.admin;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.DataRead;
import com.openbravo.data.loader.IKeyed;
import com.openbravo.data.loader.SerializableRead;

public class RoleInfo
implements SerializableRead,
IKeyed {
    private static final long serialVersionUID = 9110127845966L;
    private String m_sID = null;
    protected String m_sName = null;

    @Override
    public Object getKey() {
        return this.m_sID;
    }

    @Override
    public void readValues(DataRead dr) throws BasicException {
        this.m_sID = dr.getString(1);
        this.m_sName = dr.getString(2);
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

    public void setName(String sValue) {
        this.m_sName = sValue;
    }

    public String toString() {
        return this.m_sName;
    }
}

