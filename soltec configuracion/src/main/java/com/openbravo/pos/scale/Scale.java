/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.scale;

import com.openbravo.pos.scale.ScaleException;

public interface Scale {
    public Double readWeight() throws ScaleException;
}

