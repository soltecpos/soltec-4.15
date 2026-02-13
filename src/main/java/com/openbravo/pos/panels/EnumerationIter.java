/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.panels;

import java.util.Enumeration;
import java.util.Iterator;

public class EnumerationIter
implements Enumeration {
    private Iterator i;

    public EnumerationIter(Iterator i) {
        this.i = i;
    }

    @Override
    public boolean hasMoreElements() {
        return this.i.hasNext();
    }

    public Object nextElement() {
        return this.i.next();
    }
}

