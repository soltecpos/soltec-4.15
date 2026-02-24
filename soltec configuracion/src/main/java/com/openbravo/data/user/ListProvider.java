/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.user;

import com.openbravo.basic.BasicException;
import java.util.List;

public interface ListProvider<T> {
    public List<T> loadData() throws BasicException;

    public List<T> refreshData() throws BasicException;
}

