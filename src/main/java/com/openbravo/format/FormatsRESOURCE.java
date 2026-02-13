/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.format;

import com.openbravo.format.Formats;
import java.text.ParseException;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class FormatsRESOURCE
extends Formats {
    private ResourceBundle m_rb;
    private String m_sPrefix;

    public FormatsRESOURCE(ResourceBundle rb, String sPrefix) {
        this.m_rb = rb;
        this.m_sPrefix = sPrefix;
    }

    @Override
    protected String formatValueInt(Object value) {
        try {
            return this.m_rb.getString(this.m_sPrefix + (String)value);
        }
        catch (MissingResourceException e) {
            return (String)value;
        }
    }

    @Override
    protected Object parseValueInt(String value) throws ParseException {
        return value;
    }

    @Override
    public int getAlignment() {
        return 2;
    }
}

