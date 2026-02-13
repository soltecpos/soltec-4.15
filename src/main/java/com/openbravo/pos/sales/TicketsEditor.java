/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.sales;

import com.openbravo.pos.ticket.TicketInfo;

public interface TicketsEditor {
    public void setActiveTicket(TicketInfo var1, Object var2);

    public TicketInfo getActiveTicket();
}

