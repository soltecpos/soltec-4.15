/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.sales;

import com.openbravo.basic.BasicException;
import com.openbravo.data.gui.MessageInf;
import com.openbravo.pos.customers.DataLogicCustomers;
import com.openbravo.pos.customers.JCustomerFinder;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.DataLogicSales;
import com.openbravo.pos.sales.JTicketLines;
import com.openbravo.pos.sales.TaxesLogic;
import com.openbravo.pos.ticket.TicketInfo;
import com.openbravo.pos.ticket.TicketLineInfo;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class SimpleReceipt
extends JPanel {
    protected DataLogicCustomers dlCustomers;
    protected DataLogicSales dlSales;
    protected TaxesLogic taxeslogic;
    private JTicketLines ticketlines;
    private TicketInfo ticket;
    private Object ticketext;
    private JButton btnCustomer;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JPanel m_jButtons;
    private JLabel m_jLblTotalEuros1;
    private JLabel m_jLblTotalEuros2;
    private JLabel m_jLblTotalEuros3;
    private JPanel m_jPanTotals;
    private JLabel m_jSubtotalEuros;
    private JLabel m_jTaxesEuros;
    private JLabel m_jTicketId;
    private JLabel m_jTotalEuros;

    public SimpleReceipt(String ticketline, DataLogicSales dlSales2, DataLogicCustomers dlCustomers, TaxesLogic taxeslogic) {
        this.initComponents();
        this.ticketlines = new JTicketLines(ticketline);
        this.dlCustomers = dlCustomers;
        this.dlSales = dlSales2;
        this.taxeslogic = taxeslogic;
        this.jPanel2.add((Component)this.ticketlines, "Center");
    }

    public void setCustomerEnabled(boolean value) {
        this.btnCustomer.setEnabled(value);
    }

    public void setTicket(TicketInfo ticket, Object ticketext) {
        this.ticket = ticket;
        this.ticketext = ticketext;
        this.m_jTicketId.setText(ticket.getName(ticketext));
        this.ticketlines.clearTicketLines();
        for (int i = 0; i < ticket.getLinesCount(); ++i) {
            this.ticketlines.addTicketLine(ticket.getLine(i));
        }
        if (ticket.getLinesCount() > 0) {
            this.ticketlines.setSelectedIndex(0);
        }
        this.printTotals();
    }

    private void refreshTicketTaxes() {
        for (TicketLineInfo line : this.ticket.getLines()) {
            line.setTaxInfo(this.taxeslogic.getTaxInfo(line.getProductTaxCategoryID(), this.ticket.getCustomer()));
        }
    }

    private void printTotals() {
        if (this.ticket.getLinesCount() == 0) {
            this.m_jSubtotalEuros.setText(null);
            this.m_jTaxesEuros.setText(null);
            this.m_jTotalEuros.setText(null);
        } else {
            this.m_jSubtotalEuros.setText(this.ticket.printSubTotal());
            this.m_jTaxesEuros.setText(this.ticket.printTax());
            this.m_jTotalEuros.setText(this.ticket.printTotal());
        }
    }

    public TicketInfo getTicket() {
        return this.ticket;
    }

    private int findFirstNonAuxiliarLine() {
        int i;
        for (i = this.ticketlines.getSelectedIndex(); i >= 0 && this.ticket.getLine(i).isProductCom(); --i) {
        }
        return i;
    }

    public TicketLineInfo[] getSelectedLines() {
        int i = this.findFirstNonAuxiliarLine();
        if (i >= 0) {
            ArrayList<TicketLineInfo> l = new ArrayList<TicketLineInfo>();
            TicketLineInfo line = this.ticket.getLine(i);
            l.add(line);
            this.ticket.removeLine(i);
            this.ticketlines.removeTicketLine(i);
            while (i < this.ticket.getLinesCount() && this.ticket.getLine(i).isProductCom()) {
                l.add(this.ticket.getLine(i));
                this.ticket.removeLine(i);
                this.ticketlines.removeTicketLine(i);
            }
            this.printTotals();
            return l.toArray(new TicketLineInfo[l.size()]);
        }
        return null;
    }

    public TicketLineInfo[] getSelectedLinesUnit() {
        int i = this.findFirstNonAuxiliarLine();
        if (i >= 0) {
            TicketLineInfo line = this.ticket.getLine(i);
            if (line.getMultiply() >= 1.0) {
                ArrayList<TicketLineInfo> l = new ArrayList<TicketLineInfo>();
                if (line.getMultiply() > 1.0) {
                    line.setMultiply(line.getMultiply() - 1.0);
                    this.ticketlines.setTicketLine(i, line);
                    line = line.copyTicketLine();
                    line.setMultiply(1.0);
                    l.add(line);
                    ++i;
                } else {
                    l.add(line);
                    this.ticket.removeLine(i);
                    this.ticketlines.removeTicketLine(i);
                }
                while (i < this.ticket.getLinesCount() && this.ticket.getLine(i).isProductCom()) {
                    l.add(this.ticket.getLine(i));
                    this.ticket.removeLine(i);
                    this.ticketlines.removeTicketLine(i);
                }
                this.printTotals();
                return l.toArray(new TicketLineInfo[l.size()]);
            }
            return null;
        }
        return null;
    }

    public void addSelectedLines(TicketLineInfo[] lines) {
        int i = this.findFirstNonAuxiliarLine();
        TicketLineInfo firstline = lines[0];
        if (i >= 0 && this.ticket.getLine(i).getProductID() != null && firstline.getProductID() != null && this.ticket.getLine(i).getProductID().equals(firstline.getProductID()) && this.ticket.getLine(i).getTaxInfo().getId().equals(firstline.getTaxInfo().getId()) && this.ticket.getLine(i).getPrice() == firstline.getPrice()) {
            for (int j = 1; j < lines.length; ++j) {
                this.ticket.insertLine(i + 1, lines[j]);
                this.ticketlines.insertTicketLine(i + 1, lines[j]);
            }
            this.ticket.getLine(i).setMultiply(this.ticket.getLine(i).getMultiply() + firstline.getMultiply());
            this.ticketlines.setTicketLine(i, this.ticket.getLine(i));
            this.ticketlines.setSelectedIndex(i);
        } else {
            int insertpoint = this.ticket.getLinesCount();
            for (int j = lines.length - 1; j >= 0; --j) {
                this.ticket.insertLine(insertpoint, lines[j]);
                this.ticketlines.insertTicketLine(insertpoint, lines[j]);
            }
        }
        this.printTotals();
    }

    private void initComponents() {
        this.jPanel1 = new JPanel();
        this.m_jPanTotals = new JPanel();
        this.m_jTotalEuros = new JLabel();
        this.m_jLblTotalEuros1 = new JLabel();
        this.m_jSubtotalEuros = new JLabel();
        this.m_jTaxesEuros = new JLabel();
        this.m_jLblTotalEuros2 = new JLabel();
        this.m_jLblTotalEuros3 = new JLabel();
        this.m_jButtons = new JPanel();
        this.m_jTicketId = new JLabel();
        this.btnCustomer = new JButton();
        this.jPanel2 = new JPanel();
        this.setLayout(new BorderLayout());
        this.jPanel1.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        this.jPanel1.setLayout(new BorderLayout());
        this.m_jPanTotals.setLayout(new GridBagLayout());
        this.m_jTotalEuros.setFont(new Font("Arial", 0, 14));
        this.m_jTotalEuros.setHorizontalAlignment(4);
        this.m_jTotalEuros.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(UIManager.getDefaults().getColor("Button.darkShadow")), BorderFactory.createEmptyBorder(1, 4, 1, 4)));
        this.m_jTotalEuros.setOpaque(true);
        this.m_jTotalEuros.setPreferredSize(new Dimension(150, 30));
        this.m_jTotalEuros.setRequestFocusEnabled(false);
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = 1;
        gridBagConstraints.anchor = 23;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new Insets(5, 5, 0, 0);
        this.m_jPanTotals.add((Component)this.m_jTotalEuros, gridBagConstraints);
        this.m_jLblTotalEuros1.setFont(new Font("Arial", 0, 14));
        this.m_jLblTotalEuros1.setText(AppLocal.getIntString("label.totalcash"));
        this.m_jLblTotalEuros1.setPreferredSize(new Dimension(110, 30));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = 23;
        gridBagConstraints.insets = new Insets(5, 0, 0, 0);
        this.m_jPanTotals.add((Component)this.m_jLblTotalEuros1, gridBagConstraints);
        this.m_jSubtotalEuros.setFont(new Font("Arial", 0, 14));
        this.m_jSubtotalEuros.setHorizontalAlignment(4);
        this.m_jSubtotalEuros.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(UIManager.getDefaults().getColor("Button.darkShadow")), BorderFactory.createEmptyBorder(1, 4, 1, 4)));
        this.m_jSubtotalEuros.setOpaque(true);
        this.m_jSubtotalEuros.setPreferredSize(new Dimension(150, 30));
        this.m_jSubtotalEuros.setRequestFocusEnabled(false);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = 1;
        gridBagConstraints.anchor = 23;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new Insets(0, 5, 0, 0);
        this.m_jPanTotals.add((Component)this.m_jSubtotalEuros, gridBagConstraints);
        this.m_jTaxesEuros.setFont(new Font("Arial", 0, 14));
        this.m_jTaxesEuros.setHorizontalAlignment(4);
        this.m_jTaxesEuros.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(UIManager.getDefaults().getColor("Button.darkShadow")), BorderFactory.createEmptyBorder(1, 4, 1, 4)));
        this.m_jTaxesEuros.setOpaque(true);
        this.m_jTaxesEuros.setPreferredSize(new Dimension(150, 30));
        this.m_jTaxesEuros.setRequestFocusEnabled(false);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = 1;
        gridBagConstraints.anchor = 23;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new Insets(5, 5, 0, 0);
        this.m_jPanTotals.add((Component)this.m_jTaxesEuros, gridBagConstraints);
        this.m_jLblTotalEuros2.setFont(new Font("Arial", 0, 14));
        this.m_jLblTotalEuros2.setText(AppLocal.getIntString("label.taxcash"));
        this.m_jLblTotalEuros2.setPreferredSize(new Dimension(110, 30));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = 23;
        gridBagConstraints.insets = new Insets(5, 0, 0, 0);
        this.m_jPanTotals.add((Component)this.m_jLblTotalEuros2, gridBagConstraints);
        this.m_jLblTotalEuros3.setFont(new Font("Arial", 0, 14));
        this.m_jLblTotalEuros3.setText(AppLocal.getIntString("label.subtotalcash"));
        this.m_jLblTotalEuros3.setPreferredSize(new Dimension(110, 30));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = 23;
        this.m_jPanTotals.add((Component)this.m_jLblTotalEuros3, gridBagConstraints);
        this.jPanel1.add((Component)this.m_jPanTotals, "East");
        this.add((Component)this.jPanel1, "South");
        this.m_jButtons.setLayout(new FlowLayout(0));
        this.m_jTicketId.setFont(new Font("Arial", 0, 12));
        this.m_jTicketId.setHorizontalAlignment(0);
        this.m_jTicketId.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(UIManager.getDefaults().getColor("Button.darkShadow")), BorderFactory.createEmptyBorder(1, 4, 1, 4)));
        this.m_jTicketId.setOpaque(true);
        this.m_jTicketId.setPreferredSize(new Dimension(160, 25));
        this.m_jTicketId.setRequestFocusEnabled(false);
        this.m_jButtons.add(this.m_jTicketId);
        this.btnCustomer.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/customer_sml.png")));
        this.btnCustomer.setToolTipText("Show Customers");
        this.btnCustomer.setFocusPainted(false);
        this.btnCustomer.setFocusable(false);
        this.btnCustomer.setMargin(new Insets(8, 14, 8, 14));
        this.btnCustomer.setMaximumSize(new Dimension(50, 40));
        this.btnCustomer.setMinimumSize(new Dimension(50, 40));
        this.btnCustomer.setPreferredSize(new Dimension(80, 45));
        this.btnCustomer.setRequestFocusEnabled(false);
        this.btnCustomer.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                SimpleReceipt.this.btnCustomerActionPerformed(evt);
            }
        });
        this.m_jButtons.add(this.btnCustomer);
        this.add((Component)this.m_jButtons, "North");
        this.jPanel2.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        this.jPanel2.setFont(new Font("Tahoma", 0, 12));
        this.jPanel2.setLayout(new BorderLayout());
        this.add((Component)this.jPanel2, "Center");
    }

    private void btnCustomerActionPerformed(ActionEvent evt) {
        JCustomerFinder finder = JCustomerFinder.getCustomerFinder(this, this.dlCustomers);
        finder.search(this.ticket.getCustomer());
        finder.setVisible(true);
        try {
            this.ticket.setCustomer(finder.getSelectedCustomer() == null ? null : this.dlSales.loadCustomerExt(finder.getSelectedCustomer().getId()));
        }
        catch (BasicException e) {
            MessageInf msg = new MessageInf(-33554432, AppLocal.getIntString("message.cannotfindcustomer"), e);
            msg.show(this);
        }
        this.m_jTicketId.setText(this.ticket.getName(this.ticketext));
        this.refreshTicketTaxes();
        this.setTicket(this.ticket, this.ticketext);
    }
}

