/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.model;

import com.openbravo.data.loader.Datas;
import com.openbravo.format.Formats;

public class Field {
    private String label;
    private Datas data;
    private Formats format;
    private boolean searchable;
    private boolean comparable;
    private boolean title;

    public Field(String label, Datas data, Formats format, boolean title, boolean searchable, boolean comparable) {
        this.label = label;
        this.data = data;
        this.format = format;
        this.title = title;
        this.searchable = searchable;
        this.comparable = comparable;
    }

    public Field(String label, Datas data, Formats format) {
        this(label, data, format, false, false, false);
    }

    public String getLabel() {
        return this.label;
    }

    public Formats getFormat() {
        return this.format;
    }

    public Datas getData() {
        return this.data;
    }

    public boolean isSearchable() {
        return this.searchable;
    }

    public boolean isComparable() {
        return this.comparable;
    }

    public boolean isTitle() {
        return this.title;
    }
}

