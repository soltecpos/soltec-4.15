/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.editor;

import com.openbravo.editor.JEditorNumber;
import com.openbravo.format.Formats;

public class JEditorCurrencyPositive
extends JEditorNumber {
    @Override
    protected Formats getFormat() {
        return Formats.CURRENCY;
    }

    @Override
    protected int getMode() {
        return 2;
    }
}

