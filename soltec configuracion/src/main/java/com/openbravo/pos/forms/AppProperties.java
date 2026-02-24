/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.forms;

import java.io.File;

public interface AppProperties {
    public File getConfigFile();

    public String getHost();

    public String getProperty(String var1);

    public String getWhatsAppPhone();

    public String getWhatsAppApiKey();

    public String getWhatsAppLabel();
}

