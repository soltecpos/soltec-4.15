/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.loader;

import com.openbravo.basic.BasicException;
import java.util.Date;

public interface DataWrite {
    public void setInt(int var1, Integer var2) throws BasicException;

    public void setString(int var1, String var2) throws BasicException;

    public void setDouble(int var1, Double var2) throws BasicException;

    public void setBoolean(int var1, Boolean var2) throws BasicException;

    public void setTimestamp(int var1, Date var2) throws BasicException;

    public void setBytes(int var1, byte[] var2) throws BasicException;

    public void setObject(int var1, Object var2) throws BasicException;
}

