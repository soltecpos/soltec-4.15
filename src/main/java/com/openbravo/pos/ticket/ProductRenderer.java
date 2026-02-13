/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.ticket;

import com.openbravo.format.Formats;
import com.openbravo.pos.ticket.ProductInfoExt;
import com.openbravo.pos.util.ThumbNailBuilder;
import java.awt.Component;
import java.awt.Image;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JList;

public class ProductRenderer
extends DefaultListCellRenderer {
    ThumbNailBuilder tnbprod = new ThumbNailBuilder(48, 48, "com/openbravo/images/null.png");

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent((JList<?>)list, (Object)null, index, isSelected, cellHasFocus);
        ProductInfoExt prod = (ProductInfoExt)value;
        if (prod != null) {
            this.setText("<html><center>" + prod.getReference() + " - " + prod.getName() + "<br>&nbsp;&nbsp;&nbsp;&nbsp;" + Formats.CURRENCY.formatValue(prod.getPriceSell()));
            Image img = this.tnbprod.getThumbNail(prod.getImage());
            this.setIcon(img == null ? null : new ImageIcon(img));
        }
        return this;
    }
}

