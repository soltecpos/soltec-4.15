/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.suppliers;

import com.openbravo.basic.BasicException;
import java.awt.Component;
import java.awt.event.ActionListener;

public interface SupplierTicketSelector {
    public void loadSupplierss() throws BasicException;

    public void setComponentEnabled(boolean var1);

    public Component getComponent();

    public void addActionListener(ActionListener var1);

    public void removeActionListener(ActionListener var1);
}

