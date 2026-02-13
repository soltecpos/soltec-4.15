/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.sales;

import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.sales.JTicketsBagTicket;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class JTicketsBagTicketBag
extends JPanel {
    private final JTicketsBagTicket m_ticketsbagticket;
    private JPanel jPanEdit;
    private JPanel jPanRefund;
    private JButton m_jBtnCancel;
    private JButton m_jBtnCancel1;
    private JButton m_jBtnDelete;

    public JTicketsBagTicketBag(JTicketsBagTicket ticketsbagticket) {
        this.m_ticketsbagticket = ticketsbagticket;
        this.initComponents();
    }

    public void showEdit() {
        this.showView("edit");
    }

    public void showRefund() {
        this.showView("refund");
    }

    private void showView(String view) {
        CardLayout cl = (CardLayout)this.getLayout();
        cl.show(this, view);
    }

    private void initComponents() {
        this.jPanEdit = new JPanel();
        this.m_jBtnDelete = new JButton();
        this.m_jBtnCancel = new JButton();
        this.jPanRefund = new JPanel();
        this.m_jBtnCancel1 = new JButton();
        this.setLayout(new CardLayout());
        this.jPanEdit.setLayout(new FlowLayout(0));
        this.m_jBtnDelete.setFont(new Font("Arial", 0, 12));
        this.m_jBtnDelete.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/sale_delete.png")));
        this.m_jBtnDelete.setText(AppLocal.getIntString("button.deleteticket"));
        this.m_jBtnDelete.setToolTipText("Delete current Ticket");
        this.m_jBtnDelete.setFocusPainted(false);
        this.m_jBtnDelete.setFocusable(false);
        this.m_jBtnDelete.setMargin(new Insets(0, 4, 0, 4));
        this.m_jBtnDelete.setMaximumSize(new Dimension(50, 40));
        this.m_jBtnDelete.setMinimumSize(new Dimension(50, 40));
        this.m_jBtnDelete.setPreferredSize(new Dimension(80, 45));
        this.m_jBtnDelete.setRequestFocusEnabled(false);
        this.m_jBtnDelete.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JTicketsBagTicketBag.this.m_jBtnDeleteActionPerformed(evt);
            }
        });
        this.jPanEdit.add(this.m_jBtnDelete);
        this.m_jBtnCancel.setFont(new Font("Arial", 0, 12));
        this.m_jBtnCancel.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/cancel.png")));
        this.m_jBtnCancel.setText(AppLocal.getIntString("button.cancel"));
        this.m_jBtnCancel.setToolTipText("Cancel Action");
        this.m_jBtnCancel.setFocusPainted(false);
        this.m_jBtnCancel.setFocusable(false);
        this.m_jBtnCancel.setMargin(new Insets(0, 4, 0, 4));
        this.m_jBtnCancel.setMaximumSize(new Dimension(50, 40));
        this.m_jBtnCancel.setMinimumSize(new Dimension(50, 40));
        this.m_jBtnCancel.setPreferredSize(new Dimension(80, 45));
        this.m_jBtnCancel.setRequestFocusEnabled(false);
        this.m_jBtnCancel.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JTicketsBagTicketBag.this.m_jBtnCancelActionPerformed(evt);
            }
        });
        this.jPanEdit.add(this.m_jBtnCancel);
        this.add((Component)this.jPanEdit, "edit");
        this.jPanRefund.setLayout(new FlowLayout(0));
        this.m_jBtnCancel1.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/fileclose.png")));
        this.m_jBtnCancel1.setFocusPainted(false);
        this.m_jBtnCancel1.setFocusable(false);
        this.m_jBtnCancel1.setMargin(new Insets(8, 14, 8, 14));
        this.m_jBtnCancel1.setPreferredSize(new Dimension(80, 45));
        this.m_jBtnCancel1.setRequestFocusEnabled(false);
        this.m_jBtnCancel1.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JTicketsBagTicketBag.this.m_jBtnCancel1ActionPerformed(evt);
            }
        });
        this.jPanRefund.add(this.m_jBtnCancel1);
        this.add((Component)this.jPanRefund, "refund");
    }

    private void m_jBtnCancel1ActionPerformed(ActionEvent evt) {
        this.m_ticketsbagticket.canceleditionTicket();
    }

    private void m_jBtnDeleteActionPerformed(ActionEvent evt) {
        int res = JOptionPane.showConfirmDialog(this, AppLocal.getIntString("message.wannadelete"), AppLocal.getIntString("title.editor"), 0, 3);
        if (res == 0) {
            this.m_ticketsbagticket.deleteTicket();
        }
    }

    private void m_jBtnCancelActionPerformed(ActionEvent evt) {
        this.m_ticketsbagticket.canceleditionTicket();
    }
}

