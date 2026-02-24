/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.catalog;

import com.openbravo.basic.BasicException;
import java.awt.Component;
import java.awt.event.ActionListener;

public interface CatalogSelector {
    public void loadCatalog() throws BasicException;

    public void showCatalogPanel(String var1);

    public void setComponentEnabled(boolean var1);

    public Component getComponent();

    public void addActionListener(ActionListener var1);

    public void removeActionListener(ActionListener var1);
}

