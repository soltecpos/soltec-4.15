/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.inventory;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.DataRead;
import com.openbravo.data.loader.IKeyed;
import com.openbravo.data.loader.SerializerRead;

public class UomInfo
implements IKeyed {
    private String m_sID;
    private String m_sName;

    public UomInfo(String id, String name) {
        this.m_sID = id;
        this.m_sName = name;
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

    @Override
    public Object getKey() {
        return this.m_sID;
    }

    public String toString() {
        return this.m_sName;
    }

    public static SerializerRead<UomInfo> getSerializerRead() {
        return new SerializerRead<UomInfo>(){

            @Override
            public UomInfo readValues(DataRead dr) throws BasicException {
                return new UomInfo(dr.getString(1), dr.getString(2));
            }
        };
    }
}

