/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.sales.simple;

import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.sales.JTicketsBag;
import com.openbravo.pos.sales.TicketsEditor;
import com.openbravo.pos.ticket.TicketInfo;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class JTicketsBagSimple
extends JTicketsBag {
    private JButton m_jDelTicket;

    public JTicketsBagSimple(AppView app, TicketsEditor panelticket) {
        super(app, panelticket);
        this.initComponents();
    }

    @Override
    public void activate() {
        this.m_panelticket.setActiveTicket(new TicketInfo(), null);
        this.m_jDelTicket.setEnabled(this.m_App.getAppUserView().getUser().hasPermission("com.openbravo.pos.sales.JPanelTicketEdits"));
    }

    @Override
    public boolean deactivate() {
        this.m_panelticket.setActiveTicket(null, null);
        return true;
    }

    @Override
    public void deleteTicket() {
        this.m_panelticket.setActiveTicket(new TicketInfo(), null);
    }

    @Override
    protected JComponent getBagComponent() {
        return this;
    }

    @Override
    protected JComponent getNullComponent() {
        return new JPanel();
    }

    private void initComponents() {
        this.m_jDelTicket = new JButton();
        this.setFont(new Font("Arial", 0, 12));
        this.setLayout(new FlowLayout(0));
        this.m_jDelTicket.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/sale_delete.png")));
        ResourceBundle bundle = ResourceBundle.getBundle("pos_messages");
        this.m_jDelTicket.setToolTipText(bundle.getString("tooltip.delete"));
        this.m_jDelTicket.setFocusPainted(false);
        this.m_jDelTicket.setFocusable(false);
        this.m_jDelTicket.setMargin(new Insets(0, 4, 0, 4));
        this.m_jDelTicket.setMaximumSize(new Dimension(50, 40));
        this.m_jDelTicket.setMinimumSize(new Dimension(50, 40));
        this.m_jDelTicket.setPreferredSize(new Dimension(80, 45));
        this.m_jDelTicket.setRequestFocusEnabled(false);
        this.m_jDelTicket.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JTicketsBagSimple.this.m_jDelTicketActionPerformed(evt);
            }
        });
        this.add(this.m_jDelTicket);
    }

    private void m_jDelTicketActionPerformed(ActionEvent evt) {
        int res = JOptionPane.showConfirmDialog(this, AppLocal.getIntString("message.wannadelete"), AppLocal.getIntString("title.editor"), 0, 3);
        if (res == 0) {
            this.deleteTicket();
        }
    }
}

