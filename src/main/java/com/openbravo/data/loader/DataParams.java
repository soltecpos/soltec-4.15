/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.loader;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.DataWrite;
import java.util.Date;

public abstract class DataParams
implements DataWrite {
    protected DataWrite dw;

    public abstract void writeValues() throws BasicException;

    @Override
    public void setInt(int paramIndex, Integer iValue) throws BasicException {
        this.dw.setInt(paramIndex, iValue);
    }

    @Override
    public void setString(int paramIndex, String sValue) throws BasicException {
        this.dw.setString(paramIndex, sValue);
    }

    @Override
    public void setDouble(int paramIndex, Double dValue) throws BasicException {
        this.dw.setDouble(paramIndex, dValue);
    }

    @Override
    public void setBoolean(int paramIndex, Boolean bValue) throws BasicException {
        this.dw.setBoolean(paramIndex, bValue);
    }

    @Override
    public void setTimestamp(int paramIndex, Date dValue) throws BasicException {
        this.dw.setTimestamp(paramIndex, dValue);
    }

    @Override
    public void setBytes(int paramIndex, byte[] value) throws BasicException {
        this.dw.setBytes(paramIndex, value);
    }

    @Override
    public void setObject(int paramIndex, Object value) throws BasicException {
        this.dw.setObject(paramIndex, value);
    }

    public DataWrite getDataWrite() {
        return this.dw;
    }

    public void setDataWrite(DataWrite dw) {
        this.dw = dw;
    }
}

