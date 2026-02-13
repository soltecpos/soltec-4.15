/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.sales.shared;

import com.openbravo.basic.BasicException;
import com.openbravo.data.gui.MessageInf;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.forms.DataLogicSales;
import com.openbravo.pos.plaf.SOLTECTheme;
import com.openbravo.pos.sales.DataLogicReceipts;
import com.openbravo.pos.sales.JTicketsBag;
import com.openbravo.pos.sales.ReprintTicketInfo;
import com.openbravo.pos.sales.SharedTicketInfo;
import com.openbravo.pos.sales.TicketsEditor;
import com.openbravo.pos.sales.shared.JTicketsBagSharedList;
import com.openbravo.pos.sales.shared.JTicketsReprintList;
import com.openbravo.pos.ticket.TicketInfo;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class JTicketsBagShared
extends JTicketsBag {
    private String m_sCurrentTicket = null;
    private DataLogicReceipts dlReceipts = null;
    private DataLogicSales dlSales = null;
    private JPanel jPanel1;
    private JButton m_jDelTicket;
    private JButton m_jHold;
    private JButton m_jListTickets;
    private JButton m_jNewTicket;
    private JButton m_jReprintTickets;

    public JTicketsBagShared(AppView app, TicketsEditor panelticket) {
        super(app, panelticket);
        this.dlReceipts = (DataLogicReceipts)app.getBean("com.openbravo.pos.sales.DataLogicReceipts");
        this.dlSales = (DataLogicSales)app.getBean("com.openbravo.pos.forms.DataLogicSales");
        this.initComponents();
        this.m_jReprintTickets.setVisible(false);
    }

    @Override
    public void activate() {
        this.m_sCurrentTicket = null;
        this.selectValidTicket();
        this.m_jDelTicket.setEnabled(this.m_App.getAppUserView().getUser().hasPermission("com.openbravo.pos.sales.JPanelTicketEdits"));
    }

    @Override
    public boolean deactivate() {
        this.saveCurrentTicket();
        this.m_sCurrentTicket = null;
        this.m_panelticket.setActiveTicket(null, null);
        return true;
    }

    @Override
    public void deleteTicket() {
        this.m_sCurrentTicket = null;
        this.selectValidTicket();
    }

    public void updateCount() {
        try {
            List<SharedTicketInfo> l = this.dlReceipts.getSharedTicketList();
            int count = l.size();
            if (count > 0) {
                this.m_jListTickets.setText(Integer.toString(count));
            } else {
                this.m_jListTickets.setText("");
            }
        }
        catch (BasicException ex) {
            new MessageInf(ex).show(this);
            this.m_jListTickets.setText("");
        }
    }

    @Override
    protected JComponent getBagComponent() {
        return this;
    }

    @Override
    protected JComponent getNullComponent() {
        return new JPanel();
    }

    private void saveCurrentTicket() {
        if (this.m_sCurrentTicket != null) {
            try {
                this.dlReceipts.insertSharedTicket(this.m_sCurrentTicket, this.m_panelticket.getActiveTicket(), this.m_panelticket.getActiveTicket().getPickupId());
                this.m_jListTickets.setText("*");
                TicketInfo l = this.dlReceipts.getSharedTicket(this.m_sCurrentTicket);
                if (l.getLinesCount() == 0) {
                    this.dlReceipts.deleteSharedTicket(this.m_sCurrentTicket);
                }
            }
            catch (BasicException e) {
                new MessageInf(e).show(this);
            }
        }
        this.updateCount();
    }

    private void setActiveTicket(String id) throws BasicException {
        TicketInfo ticket = this.dlReceipts.getSharedTicket(id);
        if (ticket == null) {
            this.m_jListTickets.setText("");
            throw new BasicException(AppLocal.getIntString("message.noticket"));
        }
        this.dlReceipts.getPickupId(id);
        Integer pickUp = this.dlReceipts.getPickupId(id);
        this.dlReceipts.deleteSharedTicket(id);
        this.m_sCurrentTicket = id;
        this.m_panelticket.setActiveTicket(ticket, null);
        ticket.setPickupId(pickUp);
        this.updateCount();
    }

    private void setActiveReprintTicket(String id) throws BasicException {
        TicketInfo ticket = this.dlSales.getReprintTicket(id);
        this.m_sCurrentTicket = id;
    }

    private void selectValidTicket() {
        this.newTicket();
        this.updateCount();
        try {
            List<SharedTicketInfo> l = this.dlReceipts.getSharedTicketList();
            if (l.isEmpty()) {
                this.m_jListTickets.setText("");
                this.newTicket();
            } else {
                this.m_jListTickets.doClick();
            }
        }
        catch (BasicException e) {
            new MessageInf(e).show(this);
            this.newTicket();
        }
    }

    private void newTicket() {
        this.saveCurrentTicket();
        TicketInfo ticket = new TicketInfo();
        this.m_sCurrentTicket = UUID.randomUUID().toString();
        this.m_panelticket.setActiveTicket(ticket, null);
        this.updateCount();
    }

    private void initComponents() {
        this.jPanel1 = new JPanel();
        this.m_jNewTicket = new JButton();
        this.m_jDelTicket = new JButton();
        this.m_jListTickets = new JButton();
        this.m_jReprintTickets = new JButton();
        this.setFont(new Font("Arial", 0, 12));
        this.setLayout(new BorderLayout());
        this.m_jNewTicket.setIcon(SOLTECTheme.getScaledIcon(this.getClass().getResource("/com/openbravo/images/addnt.png"), 55, 55));
        ResourceBundle bundle = ResourceBundle.getBundle("pos_messages");
        this.m_jNewTicket.setToolTipText(bundle.getString("tooltip.addnewticket"));
        this.m_jNewTicket.setFocusPainted(false);
        this.m_jNewTicket.setFocusable(false);
        this.m_jNewTicket.setMargin(new Insets(0, 4, 0, 4));
        this.m_jNewTicket.setMaximumSize(new Dimension(90, 70));
        this.m_jNewTicket.setMinimumSize(new Dimension(90, 70));
        this.m_jNewTicket.setPreferredSize(new Dimension(90, 70));
        this.m_jNewTicket.setRequestFocusEnabled(false);
        this.m_jNewTicket.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JTicketsBagShared.this.m_jNewTicketActionPerformed(evt);
            }
        });
        SOLTECTheme.applyIconButtonStyle(this.m_jNewTicket);
        this.m_jDelTicket.setIcon(SOLTECTheme.getScaledIcon(this.getClass().getResource("/com/openbravo/images/sale_delete.png"), 55, 55));
        this.m_jDelTicket.setToolTipText(bundle.getString("tooltip.delete"));
        SOLTECTheme.applyIconButtonStyle(this.m_jDelTicket);
        this.m_jDelTicket.setPreferredSize(new Dimension(90, 70));
        this.m_jDelTicket.setMinimumSize(new Dimension(90, 70));
        this.m_jDelTicket.setMaximumSize(new Dimension(90, 70));
        this.m_jDelTicket.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JTicketsBagShared.this.m_jDelTicketActionPerformed(evt);
            }
        });
        this.jPanel1.add(this.m_jDelTicket);
        this.m_jListTickets.setFont(new Font("Arial", 1, 14));
        this.m_jListTickets.setForeground(new Color(255, 0, 153));
        this.m_jListTickets.setIcon(SOLTECTheme.getScaledIcon(this.getClass().getResource("/com/openbravo/images/pin.png"), 55, 55));
        this.m_jListTickets.setToolTipText(bundle.getString("tooltip.layaway"));
        SOLTECTheme.applyIconButtonStyle(this.m_jListTickets);
        this.m_jListTickets.setHorizontalAlignment(2);
        this.m_jListTickets.setHorizontalTextPosition(4);
        this.m_jListTickets.setIconTextGap(1);
        this.m_jListTickets.setPreferredSize(new Dimension(90, 70));
        this.m_jListTickets.setMinimumSize(new Dimension(90, 70));
        this.m_jListTickets.setMaximumSize(new Dimension(90, 70));
        this.m_jListTickets.setVerticalTextPosition(1);
        this.m_jListTickets.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JTicketsBagShared.this.m_jListTicketsActionPerformed(evt);
            }
        });
        this.jPanel1.add(this.m_jListTickets);
        this.jPanel1.add(this.m_jNewTicket);
        this.m_jReprintTickets.setFont(new Font("Arial", 1, 14));
        this.m_jReprintTickets.setForeground(new Color(255, 0, 153));
        this.m_jReprintTickets.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/reprint24.png")));
        this.m_jReprintTickets.setToolTipText(bundle.getString("tooltip.reprint"));
        SOLTECTheme.applyIconButtonStyle(this.m_jReprintTickets);
        this.m_jReprintTickets.setIconTextGap(1);
        this.m_jReprintTickets.setPreferredSize(new Dimension(80, 45));
        this.m_jReprintTickets.setVerticalTextPosition(1);
        this.m_jReprintTickets.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JTicketsBagShared.this.m_jReprintTicketsActionPerformed(evt);
            }
        });
        this.jPanel1.add(this.m_jReprintTickets);
        this.add((Component)this.jPanel1, "West");
    }

    private void m_jListTicketsActionPerformed(ActionEvent evt) {
        SwingUtilities.invokeLater(new Runnable(){

            @Override
            public void run() {
                try {
                    if (!JTicketsBagShared.this.m_App.getAppUserView().getUser().hasPermission("sales.ViewSharedTicket")) {
                        JOptionPane.showMessageDialog(null, AppLocal.getIntString("message.sharedticket"), AppLocal.getIntString("message.sharedtickettitle"), 1);
                    } else if ("0".equals(JTicketsBagShared.this.m_App.getAppUserView().getUser().getRole()) || "1".equals(JTicketsBagShared.this.m_App.getAppUserView().getUser().getRole()) || JTicketsBagShared.this.m_App.getAppUserView().getUser().hasPermission("sales.ViewSharedTicket") || JTicketsBagShared.this.m_App.getAppUserView().getUser().hasPermission("sales.Override")) {
                        List<SharedTicketInfo> l = JTicketsBagShared.this.dlReceipts.getSharedTicketList();
                        JTicketsBagSharedList listDialog = JTicketsBagSharedList.newJDialog(JTicketsBagShared.this);
                        String id = listDialog.showTicketsList(l, JTicketsBagShared.this.dlReceipts);
                        if (id != null) {
                            JTicketsBagShared.this.saveCurrentTicket();
                            JTicketsBagShared.this.setActiveTicket(id);
                        }
                    } else {
                        String appuser = JTicketsBagShared.this.m_App.getAppUserView().getUser().getId();
                        List<SharedTicketInfo> l = JTicketsBagShared.this.dlReceipts.getUserSharedTicketList(appuser);
                        JTicketsBagSharedList listDialog = JTicketsBagSharedList.newJDialog(JTicketsBagShared.this);
                        String id = listDialog.showTicketsList(l, JTicketsBagShared.this.dlReceipts);
                        if (id != null) {
                            JTicketsBagShared.this.saveCurrentTicket();
                            JTicketsBagShared.this.setActiveTicket(id);
                        }
                    }
                }
                catch (BasicException e) {
                    new MessageInf(e).show(JTicketsBagShared.this);
                    JTicketsBagShared.this.newTicket();
                }
            }
        });
    }

    private void m_jDelTicketActionPerformed(ActionEvent evt) {
        int res = JOptionPane.showConfirmDialog(this, AppLocal.getIntString("message.wannadelete"), AppLocal.getIntString("title.editor"), 0, 3);
        if (res == 0) {
            this.deleteTicket();
        }
    }

    private void m_jNewTicketActionPerformed(ActionEvent evt) {
        this.newTicket();
    }

    private void m_jReprintTicketsActionPerformed(ActionEvent evt) {
        SwingUtilities.invokeLater(new Runnable(){

            @Override
            public void run() {
                try {
                    if (!JTicketsBagShared.this.m_App.getAppUserView().getUser().hasPermission("sales.ReprintTicket")) {
                        JOptionPane.showMessageDialog(null, AppLocal.getIntString("message.reprintticket"), AppLocal.getIntString("message.reprinttickettitle"), 1);
                    } else if ("0".equals(JTicketsBagShared.this.m_App.getAppUserView().getUser().getRole()) || "1".equals(JTicketsBagShared.this.m_App.getAppUserView().getUser().getRole()) || JTicketsBagShared.this.m_App.getAppUserView().getUser().hasPermission("sales.ViewSharedTicket") || JTicketsBagShared.this.m_App.getAppUserView().getUser().hasPermission("sales.Override")) {
                        List<ReprintTicketInfo> l = JTicketsBagShared.this.dlSales.getReprintTicketList();
                        JTicketsReprintList listDialog = JTicketsReprintList.newJDialog(JTicketsBagShared.this);
                        String string = listDialog.showTicketsList(l, JTicketsBagShared.this.dlSales);
                    } else {
                        String appuser = JTicketsBagShared.this.m_App.getAppUserView().getUser().getId();
                        List<ReprintTicketInfo> l = JTicketsBagShared.this.dlSales.getReprintTicketList();
                        JTicketsReprintList listDialog = JTicketsReprintList.newJDialog(JTicketsBagShared.this);
                        String id = listDialog.showTicketsList(l, JTicketsBagShared.this.dlSales);
                        if (id != null) {
                            JTicketsBagShared.this.saveCurrentTicket();
                            JTicketsBagShared.this.setActiveReprintTicket(id);
                        }
                    }
                }
                catch (BasicException e) {
                    new MessageInf(e).show(JTicketsBagShared.this);
                    JTicketsBagShared.this.newTicket();
                }
            }
        });
    }
}

