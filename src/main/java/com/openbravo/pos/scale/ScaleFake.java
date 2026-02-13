/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.scale;

import com.openbravo.pos.scale.Scale;
import com.openbravo.pos.scale.ScaleException;

public class ScaleFake
implements Scale {
    @Override
    public Double readWeight() throws ScaleException {
        return Math.random() * 2.0;
    }
}

