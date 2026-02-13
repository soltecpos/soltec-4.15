/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.editor;

import com.openbravo.editor.JEditorNumber;
import com.openbravo.format.Formats;

public class JEditorIntegerPositive
extends JEditorNumber {
    @Override
    protected Formats getFormat() {
        return Formats.INT;
    }

    @Override
    protected int getMode() {
        return 4;
    }
}

