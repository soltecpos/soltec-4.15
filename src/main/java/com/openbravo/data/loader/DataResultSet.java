/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.loader;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.DataRead;

public interface DataResultSet
extends DataRead {
    public Object getCurrent() throws BasicException;

    public boolean next() throws BasicException;

    public void close() throws BasicException;

    public int updateCount() throws BasicException;
}

