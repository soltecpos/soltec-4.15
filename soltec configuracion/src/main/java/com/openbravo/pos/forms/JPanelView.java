/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.forms;

import com.openbravo.basic.BasicException;
import javax.swing.JComponent;

public interface JPanelView {
    public String getTitle();

    public void activate() throws BasicException;

    public boolean deactivate();

    public JComponent getComponent();
}

