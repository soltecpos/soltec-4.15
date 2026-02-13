/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.reports;

import com.openbravo.pos.reports.JParamsLocation;
import java.util.List;

public class JParamsLocationWithFirst
extends JParamsLocation {
    protected void addFirst(List a) {
        a.add(0, null);
    }
}

