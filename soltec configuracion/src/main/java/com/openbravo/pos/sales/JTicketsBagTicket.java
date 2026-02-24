/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jdesktop.layout.GroupLayout
 *  org.jdesktop.layout.GroupLayout$Group
 */
package com.openbravo.pos.sales;

import com.openbravo.basic.BasicException;
import com.openbravo.data.gui.JMessageDialog;
import com.openbravo.data.gui.ListKeyed;
import com.openbravo.data.gui.MessageInf;
import com.openbravo.editor.JEditorIntegerPositive;
import com.openbravo.editor.JEditorKeys;
import com.openbravo.format.Formats;
import com.openbravo.pos.customers.DataLogicCustomers;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.forms.DataLogicSales;
import com.openbravo.pos.forms.DataLogicSystem;
import com.openbravo.pos.panels.JTicketsFinder;
import com.openbravo.pos.printer.DeviceTicket;
import com.openbravo.pos.printer.TicketParser;
import com.openbravo.pos.printer.TicketPrinterException;
import com.openbravo.pos.sales.JPanelTicketEdits;
import com.openbravo.pos.sales.JTicketsBag;
import com.openbravo.pos.sales.JTicketsBagTicketBag;
import com.openbravo.pos.sales.TaxesException;
import com.openbravo.pos.sales.TaxesLogic;
import com.openbravo.pos.scripting.ScriptEngine;
import com.openbravo.pos.scripting.ScriptException;
import com.openbravo.pos.scripting.ScriptFactory;
import com.openbravo.pos.ticket.FindTicketsInfo;
import com.openbravo.pos.ticket.TicketInfo;
import com.openbravo.pos.ticket.TicketLineInfo;
import com.openbravo.pos.ticket.TicketTaxInfo;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.UUID;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.UIManager;
import org.jdesktop.layout.GroupLayout;

public class JTicketsBagTicket
extends JTicketsBag {
    private DataLogicSystem m_dlSystem = null;
    protected DataLogicCustomers dlCustomers = null;
    private final DataLogicSales m_dlSales;
    private TaxesLogic taxeslogic;
    private ListKeyed taxcollection;
    private final DeviceTicket m_TP;
    private final TicketParser m_TTP;
    private final TicketParser m_TTP2;
    private TicketInfo m_ticket;
    private TicketInfo m_ticketCopy;
    private final JTicketsBagTicketBag m_TicketsBagTicketBag;
    private final JPanelTicketEdits m_panelticketedit;
    private final AppView m_App;
    private ButtonGroup buttonGroup1;
    private JButton jButton1;
    private JButton jButton2;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JPanel jPanel3;
    private JPanel jPanel4;
    private JPanel jPanel5;
    private JRadioButton jrbRefunds;
    private JRadioButton jrbSales;
    private JPanel m_jButtons;
    private JButton m_jEdit;
    private JEditorKeys m_jKeys;
    private JPanel m_jOptions;
    private JPanel m_jPanelTicket;
    private JButton m_jPrint;
    private JButton m_jRefund;
    private JEditorIntegerPositive m_jTicketEditor;
    private JLabel m_jTicketId;

    public JTicketsBagTicket(AppView app, JPanelTicketEdits panelticket) {
        super(app, panelticket);
        this.m_App = app;
        this.m_panelticketedit = panelticket;
        this.m_dlSystem = (DataLogicSystem)this.m_App.getBean("com.openbravo.pos.forms.DataLogicSystem");
        this.m_dlSales = (DataLogicSales)this.m_App.getBean("com.openbravo.pos.forms.DataLogicSales");
        this.dlCustomers = (DataLogicCustomers)this.m_App.getBean("com.openbravo.pos.customers.DataLogicCustomers");
        Object props = null;
        this.m_TP = new DeviceTicket();
        this.m_TTP = new TicketParser(this.m_TP, this.m_dlSystem);
        this.m_TTP2 = new TicketParser(this.m_App.getDeviceTicket(), this.m_dlSystem);
        this.initComponents();
        this.m_TicketsBagTicketBag = new JTicketsBagTicketBag(this);
        this.m_jTicketEditor.addEditorKeys(this.m_jKeys);
        this.m_jPanelTicket.add((Component)this.m_TP.getDevicePrinter("1").getPrinterComponent(), "Center");
        try {
            this.taxeslogic = new TaxesLogic(this.m_dlSales.getTaxList().list());
        }
        catch (BasicException basicException) {
            // empty catch block
        }
    }

    @Override
    public void activate() {
        this.m_ticket = null;
        this.m_ticketCopy = null;
        this.printTicket();
        this.m_jTicketEditor.reset();
        this.m_jTicketEditor.activate();
        this.m_panelticketedit.setActiveTicket(null, null);
        this.jrbSales.setSelected(true);
        this.m_jEdit.setVisible(this.m_App.getAppUserView().getUser().hasPermission("sales.EditTicket"));
        this.m_jRefund.setVisible(this.m_App.getAppUserView().getUser().hasPermission("sales.RefundTicket"));
        this.m_jPrint.setVisible(this.m_App.getAppUserView().getUser().hasPermission("sales.PrintTicket"));
    }

    @Override
    public boolean deactivate() {
        this.m_ticket = null;
        this.m_ticketCopy = null;
        return true;
    }

    @Override
    public void deleteTicket() {
        if (this.m_ticketCopy != null) {
            try {
                String user = this.m_App.getAppUserView().getUser().getName();
                for (int i = 0; i < this.m_ticketCopy.getLinesCount(); ++i) {
                    TicketLineInfo line = this.m_ticketCopy.getLine(i);
                    this.m_dlSystem.execAuditEntry(new Object[]{UUID.randomUUID().toString(), new Date(), "DELETE_TICKET", user, line.getProductName(), line.getMultiply(), Formats.CURRENCY.formatValue(line.getPrice()), "Deleted Ticket", "Full Ticket Delete ID: " + this.m_ticketCopy.getTicketId(), Integer.toString(this.m_ticketCopy.getTicketId())});
                }
            }
            catch (Exception user) {
                // empty catch block
            }
            try {
                this.m_dlSales.deleteTicket(this.m_ticketCopy, this.m_App.getInventoryLocation());
            }
            catch (BasicException eData) {
                MessageInf msg = new MessageInf(-67108864, AppLocal.getIntString("message.nosaveticket"), eData);
                msg.show(this);
            }
        }
        this.m_ticket = null;
        this.m_ticketCopy = null;
        this.resetToTicket();
    }

    public void canceleditionTicket() {
        this.m_ticketCopy = null;
        this.resetToTicket();
    }

    private void resetToTicket() {
        this.printTicket();
        this.m_jTicketEditor.reset();
        this.m_jTicketEditor.activate();
        this.m_panelticketedit.setActiveTicket(null, null);
    }

    @Override
    protected JComponent getBagComponent() {
        return this.m_TicketsBagTicketBag;
    }

    @Override
    protected JComponent getNullComponent() {
        return this;
    }

    private void readTicket(int iTicketid, int iTickettype) {
        block9: {
            Integer findTicket = 0;
            try {
                findTicket = this.m_jTicketEditor.getValueInteger();
            }
            catch (Exception exception) {
                // empty catch block
            }
            try {
                TicketTaxInfo[] frame3;
                TicketInfo ticket;
                TicketInfo ticketInfo = ticket = iTicketid == -1 ? this.m_dlSales.loadTicket(iTickettype, findTicket) : this.m_dlSales.loadTicket(iTickettype, iTicketid);
                if (ticket == null) {
                    JFrame frame2 = new JFrame();
                    JOptionPane.showMessageDialog(frame2, AppLocal.getIntString("message.notexiststicket"), AppLocal.getIntString("message.notexiststickettitle"), 2);
                    break block9;
                }
                this.m_ticket = ticket;
                this.m_ticketCopy = null;
                if (this.m_ticket.getTicketType() == 1 || this.m_ticket.getTicketStatus() > 0) {
                    JFrame newFrame = new JFrame();
                    JOptionPane.showMessageDialog(newFrame, AppLocal.getIntString("message.ticketrefunded"), AppLocal.getIntString("message.ticketrefundedtitle"), 2);
                    this.m_jEdit.setEnabled(false);
                    this.m_jRefund.setEnabled(false);
                } else {
                    this.m_jEdit.setEnabled(true);
                    this.m_jRefund.setEnabled(true);
                }
                try {
                    this.taxeslogic.calculateTaxes(this.m_ticket);
                    frame3 = this.m_ticket.getTaxLines();
                }
                catch (TaxesException e) {
                    // empty catch block
                }
                this.printTicket();
            }
            catch (BasicException e) {
                MessageInf msg = new MessageInf(-33554432, AppLocal.getIntString("message.cannotloadticket"), e);
                msg.show(this);
            }
        }
        this.m_jTicketEditor.reset();
        this.m_jTicketEditor.activate();
    }

    private void printTicket() {
        try {
            this.m_jEdit.setEnabled(this.m_ticket != null && this.m_ticket.getTicketType() == 0 && this.m_ticket.getTicketStatus() == 0 && this.m_dlSales.isCashActive(this.m_ticket.getActiveCash()));
        }
        catch (BasicException e) {
            this.m_jEdit.setEnabled(false);
            this.m_jRefund.setEnabled(false);
        }
        if (this.m_ticket != null && this.m_ticket.getTicketType() == 0 && this.m_ticket.getTicketStatus() == 0) {
            this.m_jRefund.setEnabled(true);
        }
        this.m_jPrint.setEnabled(this.m_ticket != null);
        this.m_TP.getDevicePrinter("1").reset();
        if (this.m_ticket == null) {
            this.m_jTicketId.setText(null);
        } else {
            this.m_jTicketId.setText(this.m_ticket.getName());
            try {
                ScriptEngine script = ScriptFactory.getScriptEngine("velocity");
                script.put("ticket", this.m_ticket);
                script.put("taxes", this.m_ticket.getTaxLines());
                this.m_TTP.printTicket(script.eval(this.m_dlSystem.getResourceAsXML("Printer.TicketPreview")).toString());
            }
            catch (TicketPrinterException | ScriptException e) {
                MessageInf msg = new MessageInf(-33554432, AppLocal.getIntString("message.cannotprintticket"), e);
                msg.show(this);
            }
        }
    }

    private void initComponents() {
        this.buttonGroup1 = new ButtonGroup();
        this.m_jOptions = new JPanel();
        this.m_jButtons = new JPanel();
        this.m_jTicketId = new JLabel();
        this.jButton2 = new JButton();
        this.m_jEdit = new JButton();
        this.m_jRefund = new JButton();
        this.m_jPrint = new JButton();
        this.jPanel2 = new JPanel();
        this.m_jPanelTicket = new JPanel();
        this.jPanel3 = new JPanel();
        this.jPanel4 = new JPanel();
        this.m_jKeys = new JEditorKeys();
        this.jPanel5 = new JPanel();
        this.jButton1 = new JButton();
        this.m_jTicketEditor = new JEditorIntegerPositive();
        this.jPanel1 = new JPanel();
        this.jrbSales = new JRadioButton();
        this.jrbRefunds = new JRadioButton();
        this.setLayout(new BorderLayout());
        this.m_jTicketId.setFont(new Font("Arial", 0, 14));
        this.m_jTicketId.setHorizontalAlignment(0);
        this.m_jTicketId.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(UIManager.getDefaults().getColor("Button.darkShadow")), BorderFactory.createEmptyBorder(1, 4, 1, 4)));
        this.m_jTicketId.setOpaque(true);
        this.m_jTicketId.setPreferredSize(new Dimension(200, 30));
        this.m_jTicketId.setRequestFocusEnabled(false);
        this.jButton2.setFont(new Font("Arial", 0, 11));
        this.jButton2.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/search24.png")));
        ResourceBundle bundle = ResourceBundle.getBundle("pos_messages");
        this.jButton2.setToolTipText(bundle.getString("tooltip.ticketsearch"));
        this.jButton2.setFocusPainted(false);
        this.jButton2.setFocusable(false);
        this.jButton2.setMargin(new Insets(0, 4, 0, 4));
        this.jButton2.setMaximumSize(new Dimension(50, 40));
        this.jButton2.setMinimumSize(new Dimension(50, 40));
        this.jButton2.setPreferredSize(new Dimension(80, 45));
        this.jButton2.setRequestFocusEnabled(false);
        this.jButton2.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JTicketsBagTicket.this.jButton2ActionPerformed(evt);
            }
        });
        this.m_jEdit.setFont(new Font("Arial", 0, 11));
        this.m_jEdit.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/sale_editline.png")));
        this.m_jEdit.setToolTipText(bundle.getString("tooltip.ticketedit"));
        this.m_jEdit.setFocusPainted(false);
        this.m_jEdit.setFocusable(false);
        this.m_jEdit.setMargin(new Insets(0, 4, 0, 4));
        this.m_jEdit.setMaximumSize(new Dimension(50, 40));
        this.m_jEdit.setMinimumSize(new Dimension(50, 40));
        this.m_jEdit.setPreferredSize(new Dimension(80, 45));
        this.m_jEdit.setRequestFocusEnabled(false);
        this.m_jEdit.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JTicketsBagTicket.this.m_jEditActionPerformed(evt);
            }
        });
        this.m_jRefund.setFont(new Font("Arial", 0, 11));
        this.m_jRefund.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/inbox.png")));
        this.m_jRefund.setToolTipText(bundle.getString("tooltip.ticketrefund"));
        this.m_jRefund.setFocusPainted(false);
        this.m_jRefund.setFocusable(false);
        this.m_jRefund.setMargin(new Insets(0, 4, 0, 4));
        this.m_jRefund.setMaximumSize(new Dimension(50, 40));
        this.m_jRefund.setMinimumSize(new Dimension(50, 40));
        this.m_jRefund.setPreferredSize(new Dimension(80, 45));
        this.m_jRefund.setRequestFocusEnabled(false);
        this.m_jRefund.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JTicketsBagTicket.this.m_jRefundActionPerformed(evt);
            }
        });
        this.m_jPrint.setFont(new Font("Arial", 0, 11));
        this.m_jPrint.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/printer24.png")));
        this.m_jPrint.setToolTipText(bundle.getString("tooltip.ticketreprint"));
        this.m_jPrint.setFocusPainted(false);
        this.m_jPrint.setFocusable(false);
        this.m_jPrint.setMargin(new Insets(0, 4, 0, 4));
        this.m_jPrint.setMaximumSize(new Dimension(50, 40));
        this.m_jPrint.setMinimumSize(new Dimension(50, 40));
        this.m_jPrint.setPreferredSize(new Dimension(80, 45));
        this.m_jPrint.setRequestFocusEnabled(false);
        this.m_jPrint.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JTicketsBagTicket.this.m_jPrintActionPerformed(evt);
            }
        });
        GroupLayout m_jButtonsLayout = new GroupLayout((Container)this.m_jButtons);
        this.m_jButtons.setLayout((LayoutManager)m_jButtonsLayout);
        m_jButtonsLayout.setHorizontalGroup((GroupLayout.Group)m_jButtonsLayout.createParallelGroup(1).add((GroupLayout.Group)m_jButtonsLayout.createSequentialGroup().addContainerGap().add((Component)this.m_jTicketId, -2, -1, -2).addPreferredGap(1, -1, Short.MAX_VALUE).add((Component)this.jButton2, -2, -1, -2).add(5, 5, 5).add((Component)this.m_jEdit, -2, -1, -2).add(5, 5, 5).add((Component)this.m_jRefund, -2, -1, -2).add(5, 5, 5).add((Component)this.m_jPrint, -2, -1, -2)));
        m_jButtonsLayout.setVerticalGroup((GroupLayout.Group)m_jButtonsLayout.createParallelGroup(1).add((GroupLayout.Group)m_jButtonsLayout.createSequentialGroup().addContainerGap().add((Component)this.m_jTicketId, -2, -1, -2)).add((GroupLayout.Group)m_jButtonsLayout.createSequentialGroup().add(5, 5, 5).add((Component)this.jButton2, -2, -1, -2)).add((GroupLayout.Group)m_jButtonsLayout.createSequentialGroup().add(5, 5, 5).add((Component)this.m_jEdit, -2, -1, -2)).add((GroupLayout.Group)m_jButtonsLayout.createSequentialGroup().add(5, 5, 5).add((Component)this.m_jRefund, -2, -1, -2)).add((GroupLayout.Group)m_jButtonsLayout.createSequentialGroup().add(5, 5, 5).add((Component)this.m_jPrint, -2, -1, -2)));
        this.m_jOptions.add(this.m_jButtons);
        this.jPanel2.setLayout(new FlowLayout(0));
        this.m_jOptions.add(this.jPanel2);
        this.add((Component)this.m_jOptions, "North");
        this.m_jPanelTicket.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        this.m_jPanelTicket.setFont(new Font("Tahoma", 0, 12));
        this.m_jPanelTicket.setLayout(new BorderLayout());
        this.add((Component)this.m_jPanelTicket, "Center");
        this.jPanel3.setLayout(new BorderLayout());
        this.jPanel4.setLayout(new BoxLayout(this.jPanel4, 1));
        this.m_jKeys.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JTicketsBagTicket.this.m_jKeysActionPerformed(evt);
            }
        });
        this.jPanel4.add(this.m_jKeys);
        this.jPanel5.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        this.jPanel5.setLayout(new GridBagLayout());
        this.jButton1.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/ok.png")));
        this.jButton1.setToolTipText("Enter Receipt and touch to Find by Number");
        this.jButton1.setFocusPainted(false);
        this.jButton1.setFocusable(false);
        this.jButton1.setMargin(new Insets(8, 14, 8, 14));
        this.jButton1.setPreferredSize(new Dimension(80, 45));
        this.jButton1.setRequestFocusEnabled(false);
        this.jButton1.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JTicketsBagTicket.this.jButton1ActionPerformed(evt);
            }
        });
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = 1;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new Insets(0, 5, 0, 0);
        this.jPanel5.add((Component)this.jButton1, gridBagConstraints);
        this.m_jTicketEditor.setFont(new Font("Arial", 0, 12));
        this.m_jTicketEditor.setPreferredSize(new Dimension(130, 30));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        this.jPanel5.add((Component)this.m_jTicketEditor, gridBagConstraints);
        this.jPanel4.add(this.jPanel5);
        this.jPanel3.add((Component)this.jPanel4, "North");
        this.buttonGroup1.add(this.jrbSales);
        this.jrbSales.setFont(new Font("Arial", 1, 14));
        this.jrbSales.setText(AppLocal.getIntString("label.sales"));
        this.jrbSales.setToolTipText("Show Sales Only");
        this.jrbSales.setFocusPainted(false);
        this.jrbSales.setFocusable(false);
        this.jrbSales.setRequestFocusEnabled(false);
        this.jPanel1.add(this.jrbSales);
        this.buttonGroup1.add(this.jrbRefunds);
        this.jrbRefunds.setFont(new Font("Arial", 1, 14));
        this.jrbRefunds.setForeground(new Color(255, 0, 0));
        this.jrbRefunds.setText(AppLocal.getIntString("label.refunds"));
        this.jrbRefunds.setToolTipText("Show Refunds Only");
        this.jrbRefunds.setFocusPainted(false);
        this.jrbRefunds.setFocusable(false);
        this.jrbRefunds.setRequestFocusEnabled(false);
        this.jPanel1.add(this.jrbRefunds);
        this.jPanel3.add((Component)this.jPanel1, "Center");
        this.add((Component)this.jPanel3, "East");
    }

    private void m_jEditActionPerformed(ActionEvent evt) {
        this.m_ticketCopy = this.m_ticket;
        this.m_TicketsBagTicketBag.showEdit();
        this.m_panelticketedit.showCatalog();
        this.m_ticketCopy.setOldTicket(true);
        this.m_panelticketedit.setActiveTicket(this.m_ticket.copyTicket(), null);
    }

    private void m_jPrintActionPerformed(ActionEvent evt) {
        if (this.m_ticket != null) {
            try {
                ScriptEngine script = ScriptFactory.getScriptEngine("velocity");
                script.put("ticket", this.m_ticket);
                script.put("taxes", this.m_ticket.getTaxLines());
                this.m_TTP2.printTicket(script.eval(this.m_dlSystem.getResourceAsXML("Printer.TicketPreview")).toString());
            }
            catch (TicketPrinterException | ScriptException e) {
                JMessageDialog.showMessage(this, new MessageInf(-67108864, AppLocal.getIntString("message.cannotprint"), e));
            }
        }
    }

    private void m_jRefundActionPerformed(ActionEvent evt) {
        ArrayList<TicketLineInfo> aRefundLines = new ArrayList<TicketLineInfo>();
        for (int i = 0; i < this.m_ticket.getLinesCount(); ++i) {
            TicketLineInfo newline = new TicketLineInfo(this.m_ticket.getLine(i));
            aRefundLines.add(newline);
        }
        this.m_ticketCopy = null;
        this.m_TicketsBagTicketBag.showRefund();
        this.m_panelticketedit.showRefundLines(aRefundLines);
        TicketInfo refundticket = new TicketInfo();
        refundticket.setTicketType(1);
        refundticket.setTicketStatus(this.m_ticket.getTicketId());
        refundticket.setCustomer(this.m_ticket.getCustomer());
        refundticket.setPayments(this.m_ticket.getPayments());
        refundticket.setOldTicket(true);
        this.m_panelticketedit.setActiveTicket(refundticket, null);
    }

    private void jButton1ActionPerformed(ActionEvent evt) {
        this.readTicket(-1, this.jrbSales.isSelected() ? 0 : 1);
    }

    private void m_jKeysActionPerformed(ActionEvent evt) {
        this.readTicket(-1, this.jrbSales.isSelected() ? 0 : 1);
    }

    private void jButton2ActionPerformed(ActionEvent evt) {
        JTicketsFinder finder = JTicketsFinder.getReceiptFinder(this, this.m_dlSales, this.dlCustomers);
        finder.setVisible(true);
        FindTicketsInfo selectedTicket = finder.getSelectedCustomer();
        if (selectedTicket == null) {
            this.m_jTicketEditor.reset();
            this.m_jTicketEditor.activate();
        } else {
            this.readTicket(selectedTicket.getTicketId(), selectedTicket.getTicketType());
        }
    }
}

