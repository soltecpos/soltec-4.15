/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.sales;

import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.forms.DataLogicSales;
import com.openbravo.pos.sales.TicketsEditor;
import com.openbravo.pos.sales.restaurant.JTicketsBagRestaurantMap;
import com.openbravo.pos.sales.shared.JTicketsBagShared;
import com.openbravo.pos.sales.simple.JTicketsBagSimple;
import javax.swing.JComponent;
import javax.swing.JPanel;

public abstract class JTicketsBag
extends JPanel {
    protected AppView m_App;
    protected DataLogicSales m_dlSales;
    protected TicketsEditor m_panelticket;

    public JTicketsBag(AppView oApp, TicketsEditor panelticket) {
        this.m_App = oApp;
        this.m_panelticket = panelticket;
        this.m_dlSales = (DataLogicSales)this.m_App.getBean("com.openbravo.pos.forms.DataLogicSales");
    }

    public abstract void activate();

    public abstract boolean deactivate();

    public abstract void deleteTicket();

    protected abstract JComponent getBagComponent();

    protected abstract JComponent getNullComponent();

    public static JTicketsBag createTicketsBag(String sName, AppView app, TicketsEditor panelticket) {
        switch (sName) {
            case "standard": {
                return new JTicketsBagShared(app, panelticket);
            }
            case "restaurant": {
                return new JTicketsBagRestaurantMap(app, panelticket);
            }
        }
        return new JTicketsBagSimple(app, panelticket);
    }
}

