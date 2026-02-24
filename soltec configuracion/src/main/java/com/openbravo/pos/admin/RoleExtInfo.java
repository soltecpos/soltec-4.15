/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.admin;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.DataRead;
import com.openbravo.pos.admin.RoleInfo;

public class RoleExtInfo
extends RoleInfo {
    protected byte[] m_aPermissions = null;

    @Override
    public void readValues(DataRead dr) throws BasicException {
        this.m_sName = dr.getString(1);
        this.m_aPermissions = dr.getBytes(2);
    }

    public static String[] getHeaders() {
        return new String[]{"Name"};
    }

    public String[] toStringArray() {
        return new String[]{this.m_sName};
    }

    public Comparable[] toComparableArray() {
        return new Comparable[]{this.m_sName};
    }
}

