/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.editor;

import com.openbravo.editor.JEditorNumber;
import com.openbravo.format.Formats;

public class JEditorCurrency
extends JEditorNumber {
    private static final long serialVersionUID = 5096754100573262803L;

    @Override
    protected Formats getFormat() {
        return Formats.CURRENCY;
    }

    @Override
    protected int getMode() {
        return 1;
    }
}

