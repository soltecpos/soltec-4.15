/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.inventory;

import com.openbravo.data.loader.IKeyed;

public class AttributeSetInfo
implements IKeyed {
    private String id;
    private String name;

    public AttributeSetInfo(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public Object getKey() {
        return this.id;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String toString() {
        return this.name;
    }
}

