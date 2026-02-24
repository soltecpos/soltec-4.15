/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.sales.shared;

import com.openbravo.basic.BasicException;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.sales.DataLogicReceipts;
import com.openbravo.pos.sales.SharedTicketInfo;
import com.openbravo.pos.sales.shared.JTicketsBagShared;
import com.openbravo.pos.ticket.TicketInfo;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class JTicketsBagSharedList
extends JDialog {
    private String m_sDialogTicket;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JPanel jPanel3;
    private JPanel jPanel4;
    private JScrollPane jScrollPane1;
    private JButton m_jButtonCancel;
    private JPanel m_jtickets;

    private JTicketsBagSharedList(Frame parent, boolean modal) {
        super(parent, modal);
    }

    private JTicketsBagSharedList(Dialog parent, boolean modal) {
        super(parent, modal);
    }

    public String showTicketsList(List<SharedTicketInfo> atickets, DataLogicReceipts dlReceipts) {
        Object m_Ticket = null;
        for (SharedTicketInfo aticket : atickets) {
            this.m_jtickets.add(new JButtonTicket(aticket, dlReceipts));
        }
        this.m_sDialogTicket = null;
        int lsize = atickets.size();
        if (lsize > 0) {
            this.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, AppLocal.getIntString("message.nosharedtickets"), AppLocal.getIntString("message.sharedtickettitle"), 0);
        }
        return this.m_sDialogTicket;
    }

    public static JTicketsBagSharedList newJDialog(JTicketsBagShared ticketsbagshared) {
        Window window = JTicketsBagSharedList.getWindow(ticketsbagshared);
        JTicketsBagSharedList mydialog = window instanceof Frame ? new JTicketsBagSharedList((Frame)window, true) : new JTicketsBagSharedList((Dialog)window, true);
        mydialog.initComponents();
        mydialog.jScrollPane1.getVerticalScrollBar().setPreferredSize(new Dimension(35, 35));
        mydialog.jScrollPane1.getHorizontalScrollBar().setPreferredSize(new Dimension(25, 25));
        return mydialog;
    }

    private static Window getWindow(Component parent) {
        if (parent == null) {
            return new JFrame();
        }
        if (parent instanceof Frame || parent instanceof Dialog) {
            return (Window)parent;
        }
        return JTicketsBagSharedList.getWindow(parent.getParent());
    }

    private void initComponents() {
        this.jPanel1 = new JPanel();
        this.jScrollPane1 = new JScrollPane();
        this.jPanel2 = new JPanel();
        this.m_jtickets = new JPanel();
        this.jPanel3 = new JPanel();
        this.jPanel4 = new JPanel();
        this.m_jButtonCancel = new JButton();
        this.setTitle(AppLocal.getIntString("caption.tickets"));
        this.setResizable(false);
        this.jPanel1.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        this.jPanel1.setLayout(new BorderLayout());
        this.jPanel2.setFont(new Font("Arial", 0, 14));
        this.jPanel2.setLayout(new BorderLayout());
        this.m_jtickets.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        this.m_jtickets.setLayout(new GridLayout(0, 1, 5, 5));
        this.jPanel2.add((Component)this.m_jtickets, "North");
        this.jScrollPane1.setViewportView(this.jPanel2);
        this.jPanel1.add((Component)this.jScrollPane1, "Center");
        this.getContentPane().add((Component)this.jPanel1, "Center");
        this.jPanel3.setLayout(new FlowLayout(2));
        this.jPanel3.add(this.jPanel4);
        this.m_jButtonCancel.setFont(new Font("Arial", 0, 12));
        this.m_jButtonCancel.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/cancel.png")));
        this.m_jButtonCancel.setText(AppLocal.getIntString("button.close"));
        this.m_jButtonCancel.setFocusPainted(false);
        this.m_jButtonCancel.setFocusable(false);
        this.m_jButtonCancel.setMargin(new Insets(8, 16, 8, 16));
        this.m_jButtonCancel.setPreferredSize(new Dimension(80, 45));
        this.m_jButtonCancel.setRequestFocusEnabled(false);
        this.m_jButtonCancel.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JTicketsBagSharedList.this.m_jButtonCancelActionPerformed(evt);
            }
        });
        this.jPanel3.add(this.m_jButtonCancel);
        this.getContentPane().add((Component)this.jPanel3, "South");
        this.setSize(new Dimension(458, 334));
        this.setLocationRelativeTo(null);
    }

    private void m_jButtonCancelActionPerformed(ActionEvent evt) {
        this.dispose();
    }

    private class JButtonTicket
    extends JButton {
        private final SharedTicketInfo m_Ticket;

        public JButtonTicket(SharedTicketInfo ticket, DataLogicReceipts dlReceipts) {
            String total;
            this.m_Ticket = ticket;
            this.setFocusPainted(false);
            this.setFocusable(false);
            this.setRequestFocusEnabled(false);
            this.setMargin(new Insets(8, 14, 8, 14));
            this.setFont(new Font("Dialog", 0, 14));
            this.setBackground(new Color(220, 220, 220));
            this.addActionListener(new ActionListenerImpl());
            try {
                TicketInfo ticket2 = dlReceipts.getSharedTicket(ticket.getId());
                total = " - " + ticket2.printTotal();
            }
            catch (BasicException ex) {
                total = "";
            }
            this.setText(ticket.getName() + total);
        }

        private class ActionListenerImpl
        implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent evt) {
                JTicketsBagSharedList.this.m_sDialogTicket = JButtonTicket.this.m_Ticket.getId();
                JTicketsBagSharedList.this.setVisible(false);
            }
        }
    }
}

