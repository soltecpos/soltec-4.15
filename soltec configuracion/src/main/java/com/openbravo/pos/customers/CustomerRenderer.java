/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.customers;

import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JList;

public class CustomerRenderer
extends DefaultListCellRenderer {
    private final Icon icocustomer = new ImageIcon(this.getClass().getClassLoader().getResource("com/openbravo/images/customer_sml.png"));

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent((JList<?>)list, (Object)null, index, isSelected, cellHasFocus);
        this.setIcon(this.icocustomer);
        this.setText(value.toString());
        return this;
    }
}

