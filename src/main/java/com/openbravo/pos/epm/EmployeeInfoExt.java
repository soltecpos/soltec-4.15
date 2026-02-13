/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.epm;

import com.openbravo.pos.epm.EmployeeInfo;

public class EmployeeInfoExt
extends EmployeeInfo {
    protected boolean visible;

    public EmployeeInfoExt(String id) {
        super(id);
    }

    public boolean isVisible() {
        return this.visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}

