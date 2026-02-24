/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.user;

import com.openbravo.data.user.EditorCreator;
import java.awt.Component;

public interface EditorRecord
extends EditorCreator {
    public void writeValueEOF();

    public void writeValueInsert();

    public void writeValueEdit(Object var1);

    public void writeValueDelete(Object var1);

    public void refresh();

    public Component getComponent();
}

