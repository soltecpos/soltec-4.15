/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.printer.screen;

import com.openbravo.pos.printer.screen.JTicket;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Rectangle;
import javax.swing.JPanel;

class JTicketContainer
extends JPanel {
    protected int H_GAP = 8;
    protected int V_GAP = 8;

    public JTicketContainer() {
        this.initComponents();
        this.setLayout(null);
    }

    @Override
    public Dimension getPreferredSize() {
        Insets ins = this.getInsets();
        int iMaxx = 0;
        int iMaxy = ins.top + this.V_GAP;
        int n = this.getComponentCount();
        for (int i = 0; i < n; ++i) {
            Component comp = this.getComponent(i);
            Dimension dc = comp.getPreferredSize();
            if (dc.width > iMaxx) {
                iMaxx = dc.width;
            }
            iMaxy += this.V_GAP + dc.height;
        }
        return new Dimension(iMaxx + 2 * this.H_GAP + ins.left + ins.right, iMaxy + ins.bottom);
    }

    @Override
    public Dimension getMaximumSize() {
        return this.getPreferredSize();
    }

    @Override
    public Dimension getMinimumSize() {
        return this.getPreferredSize();
    }

    @Override
    public void doLayout() {
        Insets ins = this.getInsets();
        int x = ins.left + this.H_GAP;
        int y = ins.top + this.V_GAP;
        int n = this.getComponentCount();
        for (int i = 0; i < n; ++i) {
            Component comp = this.getComponent(i);
            Dimension dc = comp.getPreferredSize();
            comp.setBounds(x, y, dc.width, dc.height);
            y += this.V_GAP + dc.height;
        }
    }

    public void addTicket(JTicket ticket) {
        this.add(ticket);
        this.doLayout();
        this.revalidate();
        this.scrollRectToVisible(new Rectangle(0, this.getPreferredSize().height - 1, 1, 1));
    }

    public void removeAllTickets() {
        this.removeAll();
        this.doLayout();
        this.revalidate();
        this.scrollRectToVisible(new Rectangle(0, 0, 1, 1));
    }

    private void initComponents() {
        this.setFont(new Font("Arial", 0, 12));
        this.setPreferredSize(new Dimension(700, 600));
        this.setLayout(new BorderLayout());
    }
}

