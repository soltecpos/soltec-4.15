/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.ticket;

import com.openbravo.pos.ticket.FindTicketsInfo;
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JList;

public class FindTicketsRenderer
extends DefaultListCellRenderer {
    private final Icon icoTicketNormal = new ImageIcon(this.getClass().getClassLoader().getResource("com/openbravo/images/pay.png"));
    private final Icon icoTicketRefund = new ImageIcon(this.getClass().getClassLoader().getResource("com/openbravo/images/refundit.png"));
    public static final int RECEIPT_NORMAL = 0;

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent((JList<?>)list, (Object)null, index, isSelected, cellHasFocus);
        int ticketType = ((FindTicketsInfo)value).getTicketType();
        this.setText("<html><table>" + value.toString() + "</table></html>");
        if (ticketType == 0) {
            this.setIcon(this.icoTicketNormal);
        } else {
            this.setIcon(this.icoTicketRefund);
        }
        return this;
    }
}

