/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.forms;

import com.openbravo.beans.LocaleResources;

public class AppLocal {
    public static final String APP_NAME = "SOLTEC POS";
    public static final String APP_ID = "soltecpos";
    public static final String APP_VERSION = "4.51";
    private static final LocaleResources m_resources = new LocaleResources();

    private AppLocal() {
    }

    public static String getIntString(String sKey) {
        return m_resources.getString(sKey);
    }

    public static String getIntString(String sKey, Object ... sValues) {
        return m_resources.getString(sKey, sValues);
    }

    static {
        m_resources.addBundleName("pos_messages");
        m_resources.addBundleName("erp_messages");
    }
}

