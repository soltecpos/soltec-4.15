/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.sales;

import com.openbravo.basic.BasicException;
import com.openbravo.pos.catalog.CatalogSelector;
import com.openbravo.pos.catalog.JCatalog;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.forms.DataLogicSales;
import com.openbravo.pos.forms.DataLogicSystem;
import com.openbravo.pos.sales.JPanelTicketEdits;
import com.openbravo.pos.sales.JRefundLines;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JPanel;

public class JTicketCatalogLines
extends JPanel {
    private JRefundLines m_reflines;
    private CatalogSelector m_catalog;

    public JTicketCatalogLines(AppView app, JPanelTicketEdits jTicketEdit, boolean pricevisible, boolean taxesincluded, int width, int height) {
        DataLogicSystem dlSystem = null;
        DataLogicSales dlSales2 = null;
        dlSystem = (DataLogicSystem)app.getBean("com.openbravo.pos.forms.DataLogicSystem");
        dlSales2 = (DataLogicSales)app.getBean("com.openbravo.pos.forms.DataLogicSales");
        this.initComponents();
        this.m_reflines = new JRefundLines(dlSystem, jTicketEdit);
        this.add((Component)this.m_reflines, "reflines");
        this.m_catalog = new JCatalog(dlSales2, pricevisible, taxesincluded, width, height);
        this.m_catalog.getComponent().setPreferredSize(new Dimension(0, 245));
        this.add(this.m_catalog.getComponent(), "catalog");
    }

    public void showCatalog() {
        this.showView("catalog");
    }

    public void loadCatalog() throws BasicException {
        this.m_catalog.loadCatalog();
    }

    public void addActionListener(ActionListener l) {
        this.m_catalog.addActionListener(l);
    }

    public void removeActionListener(ActionListener l) {
        this.m_catalog.addActionListener(l);
    }

    public void showRefundLines(List aRefundLines) {
        this.m_reflines.setLines(aRefundLines);
        this.showView("reflines");
    }

    private void showView(String sView) {
        CardLayout cl = (CardLayout)this.getLayout();
        cl.show(this, sView);
    }

    private void initComponents() {
        this.setFont(new Font("Arial", 0, 12));
        this.setLayout(new CardLayout());
    }
}

