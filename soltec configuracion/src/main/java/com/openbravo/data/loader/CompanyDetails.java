/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.loader;

import com.openbravo.data.loader.Session;
import com.openbravo.pos.forms.AppConfig;
import java.io.File;

public class CompanyDetails {
    private String db_url;
    private String db_user;
    private String db_password;
    private File m_config;
    private Session session;

    public CompanyDetails() {
        AppConfig config = new AppConfig(this.m_config);
    }

    public void loadProperties(AppConfig config) {
        this.db_url = config.getProperty("db.url");
        this.db_user = config.getProperty("db_user");
        this.db_password = config.getProperty("db.password");
    }

    public String getUser() {
        return this.db_user;
    }
}

