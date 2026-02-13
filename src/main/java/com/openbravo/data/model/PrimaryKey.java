/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.model;

import com.openbravo.data.model.Column;

public class PrimaryKey
extends Column {
    public PrimaryKey(String name) {
        super(name);
    }

    @Override
    public boolean isPK() {
        return true;
    }
}

