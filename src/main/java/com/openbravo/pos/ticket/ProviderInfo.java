/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.ticket;

public class ProviderInfo {
    private int m_iProviderID = 0;
    private String m_sName = "";

    public int getProviderID() {
        return this.m_iProviderID;
    }

    public void setProviderID(int iProviderID) {
        this.m_iProviderID = iProviderID;
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

