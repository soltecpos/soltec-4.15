/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.config;

import com.openbravo.data.user.DirtyManager;
import com.openbravo.pos.util.StringParser;
import java.awt.Component;

public interface ParametersConfig {
    public Component getComponent();

    public void addDirtyManager(DirtyManager var1);

    public void setParameters(StringParser var1);

    public String getParameters();
}

