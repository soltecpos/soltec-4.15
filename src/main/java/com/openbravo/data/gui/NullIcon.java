/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.gui;

import java.awt.Component;
import java.awt.Graphics;
import javax.swing.Icon;

public class NullIcon
implements Icon {
    private int m_iWidth;
    private int m_iHeight;

    public NullIcon(int width, int height) {
        this.m_iWidth = width;
        this.m_iHeight = height;
    }

    @Override
    public int getIconHeight() {
        return this.m_iHeight;
    }

    @Override
    public int getIconWidth() {
        return this.m_iWidth;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
    }
}

