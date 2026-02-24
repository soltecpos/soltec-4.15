/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.sales.shared;

import com.openbravo.basic.BasicException;
import com.openbravo.data.gui.ListKeyed;
import com.openbravo.data.gui.MessageInf;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.forms.DataLogicSales;
import com.openbravo.pos.forms.DataLogicSystem;
import com.openbravo.pos.printer.DeviceTicket;
import com.openbravo.pos.printer.TicketParser;
import com.openbravo.pos.printer.TicketPrinterException;
import com.openbravo.pos.sales.ReprintTicketInfo;
import com.openbravo.pos.sales.TaxesException;
import com.openbravo.pos.sales.TaxesLogic;
import com.openbravo.pos.sales.shared.JTicketsBagShared;
import com.openbravo.pos.scripting.ScriptEngine;
import com.openbravo.pos.scripting.ScriptException;
import com.openbravo.pos.scripting.ScriptFactory;
import com.openbravo.pos.ticket.TicketInfo;
import com.openbravo.pos.ticket.TicketTaxInfo;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class JTicketsReprintList
extends JDialog {
    private String m_sDialogTicket;
    private final DeviceTicket m_TP;
    private final TicketParser m_TTP;
    private TaxesLogic taxeslogic;
    private ListKeyed taxcollection;
    private TicketInfo m_ticket;
    private TicketInfo m_ticketCopy;
    private AppView m_App;
    private DataLogicSystem dlSystem = null;
    private DataLogicSales dlSales = null;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JPanel jPanel3;
    private JPanel jPanel4;
    private JScrollPane jScrollPane1;
    private JButton m_jButtonCancel;
    private JPanel m_jtickets;

    private JTicketsReprintList(Frame parent, boolean modal) {
        super(parent, modal);
        Object app = null;
        this.m_App = (AppView) app;
        Object props = null;
        this.m_TP = new DeviceTicket();
        this.m_TTP = new TicketParser(this.m_TP, this.dlSystem);
    }

    private JTicketsReprintList(Dialog parent, boolean modal) {
        super(parent, modal);
        Object props = null;
        this.m_TP = new DeviceTicket();
        this.m_TTP = new TicketParser(this.m_TP, this.dlSystem);
    }

    public String showTicketsList(List<ReprintTicketInfo> atickets, DataLogicSales dlSales2) {
        this.m_ticket = null;
        this.m_ticketCopy = null;
        Object m_Ticket = null;
        for (ReprintTicketInfo aticket : atickets) {
            this.m_jtickets.add(new JButtonTicket(aticket, dlSales2));
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

    public static JTicketsReprintList newJDialog(JTicketsBagShared ticketsbagshared) {
        Window window = JTicketsReprintList.getWindow(ticketsbagshared);
        JTicketsReprintList mydialog = window instanceof Frame ? new JTicketsReprintList((Frame)window, true) : new JTicketsReprintList((Dialog)window, true);
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
        return JTicketsReprintList.getWindow(parent.getParent());
    }

    private void readTicket(String Id2) {
        try {
            TicketInfo ticket = this.dlSales.getReprintTicket(Id2);
            if (ticket == null) {
                JFrame frame = new JFrame();
                JOptionPane.showMessageDialog(frame, AppLocal.getIntString("message.notexiststicket"), AppLocal.getIntString("message.notexiststickettitle"), 2);
            } else {
                TicketTaxInfo[] frame2;
                this.m_ticket = ticket;
                this.m_ticketCopy = null;
                if (this.m_ticket.getTicketType() == 1 || this.m_ticket.getTicketStatus() > 0) {
                    JFrame newFrame = new JFrame();
                    JOptionPane.showMessageDialog(newFrame, AppLocal.getIntString("message.ticketrefunded"), AppLocal.getIntString("message.ticketrefundedtitle"), 2);
                }
                try {
                    this.taxeslogic.calculateTaxes(this.m_ticket);
                    frame2 = this.m_ticket.getTaxLines();
                }
                catch (TaxesException e) {}
            }
        }
        catch (BasicException e) {
            MessageInf msg = new MessageInf(-33554432, AppLocal.getIntString("message.cannotloadticket"), e);
            msg.show(this);
        }
    }

    private void printTicket(String sresourcename, TicketInfo ticket, Object ticketext) {
        String sresource = this.dlSystem.getResourceAsXML(sresourcename);
        if (sresource == null) {
            MessageInf messageInf = new MessageInf(-33554432, AppLocal.getIntString("message.cannotprintticket"));
        } else {
            if (ticket.getPickupId() == 0) {
                try {
                    ticket.setPickupId(this.dlSales.getNextPickupIndex());
                }
                catch (BasicException e) {
                    ticket.setPickupId(0);
                }
            }
            try {
                ScriptEngine script = ScriptFactory.getScriptEngine("velocity");
                if (Boolean.parseBoolean(this.m_App.getProperties().getProperty("receipt.newlayout"))) {
                    script.put("taxes", ticket.getTaxLines());
                } else {
                    script.put("taxes", this.taxcollection);
                }
                script.put("taxeslogic", this.taxeslogic);
                script.put("ticket", ticket);
                script.put("place", ticketext);
                this.m_TTP.printTicket(script.eval(sresource).toString(), ticket);
            }
            catch (TicketPrinterException | ScriptException e) {
                MessageInf messageInf = new MessageInf(-33554432, AppLocal.getIntString("message.cannotprintticket"), e);
            }
        }
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
                JTicketsReprintList.this.m_jButtonCancelActionPerformed(evt);
            }
        });
        this.jPanel3.add(this.m_jButtonCancel);
        this.getContentPane().add((Component)this.jPanel3, "South");
        this.setSize(new Dimension(519, 399));
        this.setLocationRelativeTo(null);
    }

    private void m_jButtonCancelActionPerformed(ActionEvent evt) {
        this.dispose();
    }

    private class JButtonTicket
    extends JButton {
        private final ReprintTicketInfo m_Ticket;

        public JButtonTicket(ReprintTicketInfo ticket, DataLogicSales dlSales2) {
            this.m_Ticket = ticket;
            this.setFocusPainted(false);
            this.setFocusable(false);
            this.setRequestFocusEnabled(false);
            this.setMargin(new Insets(8, 14, 8, 14));
            this.setFont(new Font("Dialog", 0, 14));
            this.setBackground(new Color(220, 220, 220));
            this.addActionListener(new ActionListenerImpl());
            this.setText(ticket.getId() + " - " + ticket.getTicketDate() + " - " + ticket.getUserName());
        }

        private class ActionListenerImpl
        implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent evt) {
                block5: {
                    try {
                        JTicketsReprintList.this.m_sDialogTicket = JButtonTicket.this.m_Ticket.getId();
                        JTicketsReprintList.this.setVisible(false);
                        int iTkt = Integer.valueOf(JTicketsReprintList.this.m_sDialogTicket);
                        int iTt = 0;
                        TicketInfo ticket = JTicketsReprintList.this.dlSales.loadTicket(iTt, iTkt);
                        if (ticket == null) {
                            JFrame frame = new JFrame();
                            JOptionPane.showMessageDialog(frame, AppLocal.getIntString("message.notexiststicket"), AppLocal.getIntString("message.notexiststickettitle"), 2);
                            break block5;
                        }
                        JTicketsReprintList.this.m_ticket = ticket;
                        JTicketsReprintList.this.m_ticketCopy = null;
                        try {
                            JTicketsReprintList.this.taxeslogic.calculateTaxes(JTicketsReprintList.this.m_ticket);
                            TicketTaxInfo[] ticketTaxInfoArray = JTicketsReprintList.this.m_ticket.getTaxLines();
                        }
                        catch (TaxesException taxesException) {
                            // empty catch block
                        }
                        JTicketsReprintList.this.printTicket("Printer.ReprintLastTicket", JTicketsReprintList.this.m_ticket, null);
                    }
                    catch (BasicException ex) {
                        Logger.getLogger(JTicketsReprintList.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }
}

