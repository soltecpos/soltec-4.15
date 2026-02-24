/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.loader;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.DataField;
import java.util.Date;

public interface DataRead {
    public Integer getInt(int var1) throws BasicException;

    public String getString(int var1) throws BasicException;

    public Double getDouble(int var1) throws BasicException;

    public Boolean getBoolean(int var1) throws BasicException;

    public Date getTimestamp(int var1) throws BasicException;

    public byte[] getBytes(int var1) throws BasicException;

    public Object getObject(int var1) throws BasicException;

    public DataField[] getDataField() throws BasicException;
}

