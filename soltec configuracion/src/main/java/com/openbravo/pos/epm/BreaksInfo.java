/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.epm;

import com.openbravo.data.loader.IKeyed;

public class BreaksInfo
implements IKeyed {
    private static final long serialVersionUID = 8936482715929L;
    private String m_sID;
    private String m_sName;

    public BreaksInfo(String id, String name) {
        this.m_sID = id;
        this.m_sName = name;
    }

    @Override
    public Object getKey() {
        return this.m_sID;
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

