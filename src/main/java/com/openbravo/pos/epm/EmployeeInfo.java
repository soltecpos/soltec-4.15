/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.epm;

import com.openbravo.pos.util.StringUtils;
import java.io.Serializable;

public class EmployeeInfo
implements Serializable {
    private static final long serialVersionUID = 9083257536541L;
    protected String id;
    protected String name;

    public EmployeeInfo(String id) {
        this.id = id;
        this.name = null;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String printName() {
        return StringUtils.encodeXML(this.name);
    }

    public String toString() {
        return this.getName();
    }
}

