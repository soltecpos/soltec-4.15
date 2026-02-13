/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.gui;

import com.openbravo.data.loader.IRenderString;
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

public class ListCellRendererBasic
extends DefaultListCellRenderer {
    private IRenderString m_renderer;

    public ListCellRendererBasic(IRenderString renderer) {
        this.m_renderer = renderer;
    }

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, (Object)null, index, isSelected, cellHasFocus);
        String s = this.m_renderer.getRenderString(value);
        this.setText(s == null || s.equals("") ? " " : s);
        return this;
    }
}

