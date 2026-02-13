/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.sales;

import com.openbravo.basic.BasicException;
import com.openbravo.pos.sales.JPanelTicket;
import com.openbravo.pos.sales.JTicketCatalogLines;
import com.openbravo.pos.sales.JTicketsBag;
import com.openbravo.pos.sales.JTicketsBagTicket;
import com.openbravo.pos.ticket.ProductInfoExt;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class JPanelTicketEdits
extends JPanelTicket {
    private JTicketCatalogLines m_catandlines;

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public void activate() throws BasicException {
        super.activate();
        this.m_catandlines.loadCatalog();
    }

    public void reLoadCatalog() {
    }

    public void showCatalog() {
        this.m_jbtnconfig.setVisible(true);
        this.m_catandlines.showCatalog();
    }

    public void showRefundLines(List aRefundLines) {
        this.m_jbtnconfig.setVisible(false);
        this.m_catandlines.showRefundLines(aRefundLines);
    }

    @Override
    protected JTicketsBag getJTicketsBag() {
        return new JTicketsBagTicket(this.m_App, this);
    }

    @Override
    protected Component getSouthComponent() {
        this.m_catandlines = new JTicketCatalogLines(this.m_App, this, "true".equals(this.m_jbtnconfig.getProperty("pricevisible")), "true".equals(this.m_jbtnconfig.getProperty("taxesincluded")), Integer.parseInt(this.m_jbtnconfig.getProperty("img-width", "64")), Integer.parseInt(this.m_jbtnconfig.getProperty("img-height", "54")));
        this.m_catandlines.setPreferredSize(new Dimension(0, Integer.parseInt(this.m_jbtnconfig.getProperty("cat-height", "245"))));
        this.m_catandlines.addActionListener(new CatalogListener());
        return this.m_catandlines;
    }

    @Override
    protected void resetSouthComponent() {
    }

    private class CatalogListener
    implements ActionListener {
        private CatalogListener() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JPanelTicketEdits.this.buttonTransition((ProductInfoExt)e.getSource());
        }
    }
}

