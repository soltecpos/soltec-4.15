/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.inventory;

import com.openbravo.data.loader.IKeyed;
import java.io.Serializable;

public class TaxCategoryInfo
implements Serializable,
IKeyed {
    private static final long serialVersionUID = 8959679342805L;
    private String m_sID;
    private String m_sName;

    public TaxCategoryInfo(String sID, String sName) {
        this.m_sID = sID;
        this.m_sName = sName;
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

