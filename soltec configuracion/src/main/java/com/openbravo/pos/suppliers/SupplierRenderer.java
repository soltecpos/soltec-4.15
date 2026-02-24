/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.suppliers;

import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JList;

public class SupplierRenderer
extends DefaultListCellRenderer {
    private Icon icosupplier = new ImageIcon(this.getClass().getClassLoader().getResource("com/openbravo/images/supplier_sml.png"));

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent((JList<?>)list, (Object)null, index, isSelected, cellHasFocus);
        this.setText(value.toString());
        this.setIcon(this.icosupplier);
        return this;
    }
}

