/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.sales;

import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.DataLogicSystem;
import com.openbravo.pos.sales.JPanelTicketEdits;
import com.openbravo.pos.sales.JTicketLines;
import com.openbravo.pos.ticket.TicketLineInfo;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class JRefundLines
extends JPanel {
    private JTicketLines ticketlines;
    private List m_aLines;
    private JPanelTicketEdits m_jTicketEdit;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JPanel jPanel3;
    private JButton m_jbtnAddAll;
    private JButton m_jbtnAddLine;
    private JButton m_jbtnAddOne;
    private JLabel webLabel1;

    public JRefundLines(DataLogicSystem dlSystem, JPanelTicketEdits jTicketEdit) {
        this.m_jTicketEdit = jTicketEdit;
        this.initComponents();
        this.ticketlines = new JTicketLines(dlSystem.getResourceAsXML("Ticket.Line"));
        this.jPanel3.add((Component)this.ticketlines, "Center");
    }

    public void setLines(List aRefundLines) {
        this.m_aLines = aRefundLines;
        this.ticketlines.clearTicketLines();
        if (this.m_aLines != null) {
            for (int i = 0; i < this.m_aLines.size(); ++i) {
                this.ticketlines.addTicketLine((TicketLineInfo)this.m_aLines.get(i));
            }
        }
    }

    private void initComponents() {
        this.jPanel3 = new JPanel();
        this.jPanel1 = new JPanel();
        this.jPanel2 = new JPanel();
        this.webLabel1 = new JLabel();
        this.m_jbtnAddOne = new JButton();
        this.m_jbtnAddLine = new JButton();
        this.m_jbtnAddAll = new JButton();
        this.setFont(new Font("Arial", 0, 12));
        this.setPreferredSize(new Dimension(15, 200));
        this.setLayout(new BorderLayout());
        this.jPanel3.setLayout(new BorderLayout());
        this.jPanel1.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
        this.jPanel1.setLayout(new BorderLayout());
        this.webLabel1.setHorizontalAlignment(0);
        ResourceBundle bundle = ResourceBundle.getBundle("pos_messages");
        this.webLabel1.setText(bundle.getString("label.refunds"));
        this.webLabel1.setFont(new Font("Arial", 1, 14));
        this.m_jbtnAddOne.setFont(new Font("Arial", 0, 12));
        this.m_jbtnAddOne.setText(AppLocal.getIntString("button.refundone"));
        this.m_jbtnAddOne.setToolTipText(bundle.getString("tooltip.refunditem"));
        this.m_jbtnAddOne.setFocusPainted(false);
        this.m_jbtnAddOne.setFocusable(false);
        this.m_jbtnAddOne.setMargin(new Insets(8, 14, 8, 14));
        this.m_jbtnAddOne.setPreferredSize(new Dimension(110, 45));
        this.m_jbtnAddOne.setRequestFocusEnabled(false);
        this.m_jbtnAddOne.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JRefundLines.this.m_jbtnAddOneActionPerformed(evt);
            }
        });
        this.m_jbtnAddLine.setFont(new Font("Arial", 0, 12));
        this.m_jbtnAddLine.setText(AppLocal.getIntString("button.refundline"));
        this.m_jbtnAddLine.setToolTipText(bundle.getString("tooltip.refundline"));
        this.m_jbtnAddLine.setFocusPainted(false);
        this.m_jbtnAddLine.setFocusable(false);
        this.m_jbtnAddLine.setMargin(new Insets(8, 14, 8, 14));
        this.m_jbtnAddLine.setPreferredSize(new Dimension(110, 45));
        this.m_jbtnAddLine.setRequestFocusEnabled(false);
        this.m_jbtnAddLine.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JRefundLines.this.m_jbtnAddLineActionPerformed(evt);
            }
        });
        this.m_jbtnAddAll.setFont(new Font("Arial", 0, 12));
        this.m_jbtnAddAll.setText(AppLocal.getIntString("button.refundall"));
        this.m_jbtnAddAll.setToolTipText(bundle.getString("tooltip.refundticket"));
        this.m_jbtnAddAll.setFocusPainted(false);
        this.m_jbtnAddAll.setFocusable(false);
        this.m_jbtnAddAll.setMargin(new Insets(8, 14, 8, 14));
        this.m_jbtnAddAll.setPreferredSize(new Dimension(110, 45));
        this.m_jbtnAddAll.setRequestFocusEnabled(false);
        this.m_jbtnAddAll.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JRefundLines.this.m_jbtnAddAllActionPerformed(evt);
            }
        });
        GroupLayout jPanel2Layout = new GroupLayout(this.jPanel2);
        this.jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.webLabel1, -2, 110, -2).addComponent(this.m_jbtnAddOne, -2, -1, -2).addComponent(this.m_jbtnAddLine, -2, -1, -2).addComponent(this.m_jbtnAddAll, -2, -1, -2));
        jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel2Layout.createSequentialGroup().addGap(1, 1, 1).addComponent(this.webLabel1, -2, 52, -2).addGap(5, 5, 5).addComponent(this.m_jbtnAddOne, -2, -1, -2).addGap(5, 5, 5).addComponent(this.m_jbtnAddLine, -2, -1, -2).addGap(5, 5, 5).addComponent(this.m_jbtnAddAll, -2, -1, -2)));
        this.jPanel1.add((Component)this.jPanel2, "After");
        this.jPanel3.add((Component)this.jPanel1, "East");
        this.add((Component)this.jPanel3, "West");
    }

    private void m_jbtnAddAllActionPerformed(ActionEvent evt) {
        for (int i = 0; i < this.m_aLines.size(); ++i) {
            TicketLineInfo oLine = (TicketLineInfo)this.m_aLines.get(i);
            TicketLineInfo oNewLine = new TicketLineInfo(oLine);
            oNewLine.setMultiply(-oLine.getMultiply());
            this.m_jTicketEdit.addTicketLine(oNewLine);
        }
    }

    private void m_jbtnAddOneActionPerformed(ActionEvent evt) {
        int index = this.ticketlines.getSelectedIndex();
        if (index >= 0) {
            TicketLineInfo oLine = (TicketLineInfo)this.m_aLines.get(index);
            TicketLineInfo oNewLine = new TicketLineInfo(oLine);
            oNewLine.setMultiply(-1.0);
            this.m_jTicketEdit.addTicketLine(oNewLine);
        }
    }

    private void m_jbtnAddLineActionPerformed(ActionEvent evt) {
        int index = this.ticketlines.getSelectedIndex();
        if (index >= 0) {
            TicketLineInfo oLine = (TicketLineInfo)this.m_aLines.get(index);
            TicketLineInfo oNewLine = new TicketLineInfo(oLine);
            oNewLine.setMultiply(-oLine.getMultiply());
            this.m_jTicketEdit.addTicketLine(oNewLine);
        }
    }
}

