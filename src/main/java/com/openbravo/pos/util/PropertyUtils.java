/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyUtils {
    private Properties m_propsconfig;
    private File configFile;
    private final String APP_ID = "upos-app";

    public PropertyUtils() {
        this.init(this.getDefaultConfig());
    }

    private void init(File configfile) {
        this.configFile = configfile;
        this.load();
    }

    private File getDefaultConfig() {
        return new File(new File("C:\\Documents and Settings\\jack"), "unicentaopos.properties");
    }

    private void load() {
        try {
            FileInputStream in = new FileInputStream(this.configFile);
            if (in != null) {
                this.m_propsconfig = new Properties();
                this.m_propsconfig.load(in);
                ((InputStream)in).close();
            }
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    public String getProperty(String sKey) {
        return this.m_propsconfig.getProperty(sKey);
    }

    public String getDriverName() {
        return this.m_propsconfig.getProperty("db.driver");
    }

    public String getUrl() {
        return this.m_propsconfig.getProperty("db.URL");
    }

    public String getDBUser() {
        return this.m_propsconfig.getProperty("db.user");
    }

    public String getDBPassword() {
        String m_password = "[color=#FF0000]YourDBPassword[/color]";
        return m_password;
    }
}

