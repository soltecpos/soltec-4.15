/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.beans;

import java.util.EventObject;

public class JNumberEvent
extends EventObject {
    private char m_cKey;

    public JNumberEvent(Object source, char cKey) {
        super(source);
        this.m_cKey = cKey;
    }

    public char getKey() {
        return this.m_cKey;
    }
}

