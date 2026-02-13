/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.ticket;

import java.io.Serializable;

public class UserInfo
implements Serializable {
    private static final long serialVersionUID = 7537578737839L;
    private String m_sId;
    private String m_sName;

    public UserInfo(String id, String name) {
        this.m_sId = id;
        this.m_sName = name;
    }

    public String getId() {
        return this.m_sId;
    }

    public String getName() {
        return this.m_sName;
    }
}

