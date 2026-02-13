/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.editor;

import com.openbravo.editor.EditorKeys;
import java.awt.Component;

public interface EditorComponent {
    public void addEditorKeys(EditorKeys var1);

    public Component getComponent();

    public void deactivate();

    public void typeChar(char var1);

    public void transChar(char var1);
}

