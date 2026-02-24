/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.gui;

import java.awt.Component;
import java.awt.Graphics;
import javax.swing.Icon;

public class CompoundIcon
implements Icon {
    private Icon m_icon1;
    private Icon m_icon2;

    public CompoundIcon(Icon icon1, Icon icon2) {
        this.m_icon1 = icon1;
        this.m_icon2 = icon2;
    }

    @Override
    public int getIconHeight() {
        return Math.max(this.m_icon1.getIconHeight(), this.m_icon2.getIconHeight());
    }

    @Override
    public int getIconWidth() {
        return this.m_icon1.getIconWidth() + this.m_icon2.getIconWidth();
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        this.m_icon1.paintIcon(c, g, x, y);
        this.m_icon2.paintIcon(c, g, x + this.m_icon1.getIconWidth(), y);
    }
}

