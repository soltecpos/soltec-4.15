/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.loader;

import com.openbravo.beans.LocaleResources;

public class LocalRes {
    private static LocaleResources m_resources = new LocaleResources();

    private LocalRes() {
    }

    public static String getIntString(String sKey) {
        return m_resources.getString(sKey);
    }

    static {
        m_resources.addBundleName("data_messages");
    }
}

