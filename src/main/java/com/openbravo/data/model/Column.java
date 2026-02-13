/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.model;

public class Column {
    private String name;

    public Column(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public boolean isPK() {
        return false;
    }
}

