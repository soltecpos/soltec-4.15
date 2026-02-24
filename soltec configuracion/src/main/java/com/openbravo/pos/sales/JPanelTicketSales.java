/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.sales;

import com.openbravo.basic.BasicException;
import com.openbravo.pos.catalog.CatalogSelector;
import com.openbravo.pos.catalog.JCatalog;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.sales.JPanelTicket;
import com.openbravo.pos.sales.JTicketsBag;
import com.openbravo.pos.ticket.ProductInfoExt;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class JPanelTicketSales
extends JPanelTicket {
    private CatalogSelector m_cat;

    @Override
    public void init(AppView app) {
        super.init(app);
        this.m_ticketlines.addListSelectionListener(new CatalogSelectionListener());
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    protected Component getSouthComponent() {
        this.m_cat = new JCatalog(this.dlSales, true, "true".equals(this.m_jbtnconfig.getProperty("taxesincluded")), Integer.parseInt(this.m_jbtnconfig.getProperty("img-width", "80")), Integer.parseInt(this.m_jbtnconfig.getProperty("img-height", "70")));
        this.m_cat.addActionListener(new CatalogListener());
        this.m_cat.getComponent().setPreferredSize(new Dimension(0, Integer.parseInt(this.m_jbtnconfig.getProperty("cat-height", "250"))));
        return this.m_cat.getComponent();
    }

    @Override
    protected void resetSouthComponent() {
        this.m_cat.showCatalogPanel(null);
    }

    @Override
    protected JTicketsBag getJTicketsBag() {
        return JTicketsBag.createTicketsBag(this.m_App.getProperties().getProperty("machine.ticketsbag"), this.m_App, this);
    }

    @Override
    public void activate() throws BasicException {
        super.activate();
        this.m_cat.loadCatalog();
    }

    public void reLoadCatalog() {
        try {
            this.m_cat.loadCatalog();
        }
        catch (BasicException basicException) {
            // empty catch block
        }
    }

    private class CatalogSelectionListener
    implements ListSelectionListener {
        private CatalogSelectionListener() {
        }

        @Override
        public void valueChanged(ListSelectionEvent e) {
            int i;
            if (!e.getValueIsAdjusting() && (i = JPanelTicketSales.this.m_ticketlines.getSelectedIndex()) >= 0) {
                while (i >= 0 && JPanelTicketSales.this.m_oTicket.getLine(i).isProductCom()) {
                    --i;
                }
                if (i >= 0) {
                    JPanelTicketSales.this.m_cat.showCatalogPanel(JPanelTicketSales.this.m_oTicket.getLine(i).getProductID());
                } else {
                    JPanelTicketSales.this.m_cat.showCatalogPanel(null);
                }
            }
        }
    }

    private class CatalogListener
    implements ActionListener {
        private CatalogListener() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JPanelTicketSales.this.buttonTransition((ProductInfoExt)e.getSource());
        }
    }
}

