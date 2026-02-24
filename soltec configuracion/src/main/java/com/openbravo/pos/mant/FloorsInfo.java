/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.mant;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.DataRead;
import com.openbravo.data.loader.IKeyed;
import com.openbravo.data.loader.SerializableRead;

public class FloorsInfo
implements SerializableRead,
IKeyed {
    private static final long serialVersionUID = 8906929819402L;
    private String m_sID = null;
    private String m_sName = null;

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

    public void setName(String sName) {
        this.m_sName = sName;
    }

    public String toString() {
        return this.m_sName;
    }
}

