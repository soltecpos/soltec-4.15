/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.loader;

import com.openbravo.basic.BasicException;
import java.util.List;

public interface SentenceList<T> {
    public List<T> list() throws BasicException;

    public List<T> list(Object ... var1) throws BasicException;

    public List<T> list(Object var1) throws BasicException;

    public List<T> listPage(int var1, int var2) throws BasicException;

    public List<T> listPage(Object var1, int var2, int var3) throws BasicException;
}

