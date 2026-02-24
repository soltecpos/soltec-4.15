/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.config;

import com.openbravo.pos.forms.AppConfig;
import java.awt.Component;

public interface PanelConfig {
    public void loadProperties(AppConfig var1);

    public void saveProperties(AppConfig var1);

    public boolean hasChanged();

    public Component getConfigComponent();
}

