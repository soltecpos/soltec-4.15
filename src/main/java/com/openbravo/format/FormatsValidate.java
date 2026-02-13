/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.format;

import com.openbravo.format.Formats;
import com.openbravo.format.FormatsConstrain;
import java.text.ParseException;

public class FormatsValidate
extends Formats {
    private Formats m_fmt;
    private FormatsConstrain[] m_aConstrains;

    public FormatsValidate(Formats fmt, FormatsConstrain[] constrains) {
        this.m_fmt = fmt;
        this.m_aConstrains = constrains;
    }

    public FormatsValidate(Formats fmt) {
        this(fmt, new FormatsConstrain[0]);
    }

    public FormatsValidate(Formats fmt, FormatsConstrain constrain) {
        this(fmt, new FormatsConstrain[]{constrain});
    }

    @Override
    protected String formatValueInt(Object value) {
        return this.m_fmt.formatValueInt(value);
    }

    @Override
    protected Object parseValueInt(String value) throws ParseException {
        Object val = this.m_fmt.parseValueInt(value);
        for (int i = 0; i < this.m_aConstrains.length; ++i) {
            val = this.m_aConstrains[i].check(val);
        }
        return val;
    }

    @Override
    public int getAlignment() {
        return this.m_fmt.getAlignment();
    }
}

