/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.editor;

import com.openbravo.editor.EditorComponent;

public interface EditorKeys {
    public static final int MODE_STRING = 0;
    public static final int MODE_DOUBLE = 1;
    public static final int MODE_DOUBLE_POSITIVE = 2;
    public static final int MODE_INTEGER = 3;
    public static final int MODE_INTEGER_POSITIVE = 4;

    public void setActive(EditorComponent var1, int var2);

    public void setInactive(EditorComponent var1);
}

