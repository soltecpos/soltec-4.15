/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.sales;

import com.openbravo.pos.customers.DataLogicCustomers;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.DataLogicSales;
import com.openbravo.pos.sales.SimpleReceipt;
import com.openbravo.pos.sales.TaxesLogic;
import com.openbravo.pos.ticket.TicketInfo;
import com.openbravo.pos.ticket.TicketLineInfo;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ReceiptSplit
extends JDialog {
    private boolean accepted;
    SimpleReceipt receiptone;
    SimpleReceipt receipttwo;
    private JButton jBtnToLeftAll;
    private JButton jBtnToLeftOne;
    private JButton jBtnToRightAll;
    private JButton jBtnToRightOne;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JPanel jPanel3;
    private JPanel jPanel4;
    private JPanel jPanel5;
    private JButton m_jButtonCancel;
    private JButton m_jButtonOK;

    protected ReceiptSplit(Frame parent) {
        super(parent, true);
    }

    protected ReceiptSplit(Dialog parent) {
        super(parent, true);
    }

    private void init(String ticketline, DataLogicSales dlSales2, DataLogicCustomers dlCustomers, TaxesLogic taxeslogic) {
        this.initComponents();
        this.getRootPane().setDefaultButton(this.m_jButtonOK);
        this.receiptone = new SimpleReceipt(ticketline, dlSales2, dlCustomers, taxeslogic);
        this.receiptone.setCustomerEnabled(false);
        this.jPanel5.add((Component)this.receiptone, "Center");
        this.receipttwo = new SimpleReceipt(ticketline, dlSales2, dlCustomers, taxeslogic);
        this.jPanel3.add((Component)this.receipttwo, "Center");
    }

    public static ReceiptSplit getDialog(Component parent, String ticketline, DataLogicSales dlSales2, DataLogicCustomers dlCustomers, TaxesLogic taxeslogic) {
        Window window = ReceiptSplit.getWindow(parent);
        ReceiptSplit myreceiptsplit = window instanceof Frame ? new ReceiptSplit((Frame)window) : new ReceiptSplit((Dialog)window);
        myreceiptsplit.init(ticketline, dlSales2, dlCustomers, taxeslogic);
        return myreceiptsplit;
    }

    protected static Window getWindow(Component parent) {
        if (parent == null) {
            return new JFrame();
        }
        if (parent instanceof Frame || parent instanceof Dialog) {
            return (Window)parent;
        }
        return ReceiptSplit.getWindow(parent.getParent());
    }

    public boolean showDialog(TicketInfo ticket, TicketInfo ticket2, Object ticketext) {
        this.receiptone.setTicket(ticket, ticketext);
        this.receipttwo.setTicket(ticket2, ticketext);
        this.setVisible(true);
        return this.accepted;
    }

    private void initComponents() {
        this.jPanel2 = new JPanel();
        this.m_jButtonCancel = new JButton();
        this.m_jButtonOK = new JButton();
        this.jPanel1 = new JPanel();
        this.jPanel5 = new JPanel();
        this.jPanel4 = new JPanel();
        this.jBtnToRightAll = new JButton();
        this.jBtnToRightOne = new JButton();
        this.jBtnToLeftOne = new JButton();
        this.jBtnToLeftAll = new JButton();
        this.jPanel3 = new JPanel();
        this.setDefaultCloseOperation(2);
        this.setTitle(AppLocal.getIntString("caption.split"));
        this.setResizable(false);
        this.jPanel2.setLayout(new FlowLayout(2));
        this.m_jButtonCancel.setFont(new Font("Arial", 0, 12));
        this.m_jButtonCancel.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/cancel.png")));
        this.m_jButtonCancel.setText(AppLocal.getIntString("button.cancel"));
        this.m_jButtonCancel.setFocusPainted(false);
        this.m_jButtonCancel.setFocusable(false);
        this.m_jButtonCancel.setMargin(new Insets(8, 16, 8, 16));
        this.m_jButtonCancel.setPreferredSize(new Dimension(110, 45));
        this.m_jButtonCancel.setRequestFocusEnabled(false);
        this.m_jButtonCancel.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReceiptSplit.this.m_jButtonCancelActionPerformed(evt);
            }
        });
        this.jPanel2.add(this.m_jButtonCancel);
        this.m_jButtonOK.setFont(new Font("Arial", 0, 12));
        this.m_jButtonOK.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/ok.png")));
        this.m_jButtonOK.setText(AppLocal.getIntString("button.OK"));
        this.m_jButtonOK.setFocusPainted(false);
        this.m_jButtonOK.setFocusable(false);
        this.m_jButtonOK.setMargin(new Insets(8, 16, 8, 16));
        this.m_jButtonOK.setPreferredSize(new Dimension(110, 45));
        this.m_jButtonOK.setRequestFocusEnabled(false);
        this.m_jButtonOK.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReceiptSplit.this.m_jButtonOKActionPerformed(evt);
            }
        });
        this.jPanel2.add(this.m_jButtonOK);
        this.getContentPane().add((Component)this.jPanel2, "South");
        this.jPanel1.setLayout(new BoxLayout(this.jPanel1, 2));
        this.jPanel5.setFont(new Font("Arial", 0, 12));
        this.jPanel5.setLayout(new BorderLayout());
        this.jPanel1.add(this.jPanel5);
        this.jPanel4.setFont(new Font("Arial", 0, 12));
        this.jPanel4.setLayout(new GridBagLayout());
        this.jBtnToRightAll.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/2rightarrow.png")));
        this.jBtnToRightAll.setToolTipText("Split All Line Items");
        this.jBtnToRightAll.setFocusPainted(false);
        this.jBtnToRightAll.setFocusable(false);
        this.jBtnToRightAll.setMargin(new Insets(8, 14, 8, 14));
        this.jBtnToRightAll.setPreferredSize(new Dimension(80, 45));
        this.jBtnToRightAll.setRequestFocusEnabled(false);
        this.jBtnToRightAll.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReceiptSplit.this.jBtnToRightAllActionPerformed(evt);
            }
        });
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridy = 0;
        this.jPanel4.add((Component)this.jBtnToRightAll, gridBagConstraints);
        this.jBtnToRightOne.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/1rightarrow.png")));
        this.jBtnToRightOne.setToolTipText("Split only one of the Line Items");
        this.jBtnToRightOne.setFocusPainted(false);
        this.jBtnToRightOne.setFocusable(false);
        this.jBtnToRightOne.setMargin(new Insets(8, 14, 8, 14));
        this.jBtnToRightOne.setPreferredSize(new Dimension(80, 45));
        this.jBtnToRightOne.setRequestFocusEnabled(false);
        this.jBtnToRightOne.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReceiptSplit.this.jBtnToRightOneActionPerformed(evt);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new Insets(5, 0, 0, 0);
        this.jPanel4.add((Component)this.jBtnToRightOne, gridBagConstraints);
        this.jBtnToLeftOne.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/1leftarrow.png")));
        this.jBtnToLeftOne.setToolTipText("Un-Split only one of the Line Items");
        this.jBtnToLeftOne.setFocusPainted(false);
        this.jBtnToLeftOne.setFocusable(false);
        this.jBtnToLeftOne.setMargin(new Insets(8, 14, 8, 14));
        this.jBtnToLeftOne.setPreferredSize(new Dimension(80, 45));
        this.jBtnToLeftOne.setRequestFocusEnabled(false);
        this.jBtnToLeftOne.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReceiptSplit.this.jBtnToLeftOneActionPerformed(evt);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new Insets(5, 0, 0, 0);
        this.jPanel4.add((Component)this.jBtnToLeftOne, gridBagConstraints);
        this.jBtnToLeftAll.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/2leftarrow.png")));
        this.jBtnToLeftAll.setToolTipText("Un-Split All Line Items");
        this.jBtnToLeftAll.setFocusPainted(false);
        this.jBtnToLeftAll.setFocusable(false);
        this.jBtnToLeftAll.setMargin(new Insets(8, 14, 8, 14));
        this.jBtnToLeftAll.setPreferredSize(new Dimension(80, 45));
        this.jBtnToLeftAll.setRequestFocusEnabled(false);
        this.jBtnToLeftAll.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReceiptSplit.this.jBtnToLeftAllActionPerformed(evt);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new Insets(5, 0, 0, 0);
        this.jPanel4.add((Component)this.jBtnToLeftAll, gridBagConstraints);
        this.jPanel1.add(this.jPanel4);
        this.jPanel3.setFont(new Font("Arial", 0, 12));
        this.jPanel3.setLayout(new BorderLayout());
        this.jPanel1.add(this.jPanel3);
        this.getContentPane().add((Component)this.jPanel1, "Center");
        this.setSize(new Dimension(730, 470));
        this.setLocationRelativeTo(null);
    }

    private void m_jButtonOKActionPerformed(ActionEvent evt) {
        if (this.receipttwo.getTicket().getLinesCount() > 0) {
            this.accepted = true;
            this.dispose();
        }
    }

    private void m_jButtonCancelActionPerformed(ActionEvent evt) {
        this.dispose();
    }

    private void jBtnToRightAllActionPerformed(ActionEvent evt) {
        TicketLineInfo[] lines = this.receiptone.getSelectedLines();
        if (lines != null) {
            this.receipttwo.addSelectedLines(lines);
        }
    }

    private void jBtnToRightOneActionPerformed(ActionEvent evt) {
        TicketLineInfo[] lines = this.receiptone.getSelectedLinesUnit();
        if (lines != null) {
            this.receipttwo.addSelectedLines(lines);
        }
    }

    private void jBtnToLeftOneActionPerformed(ActionEvent evt) {
        TicketLineInfo[] lines = this.receipttwo.getSelectedLinesUnit();
        if (lines != null) {
            this.receiptone.addSelectedLines(lines);
        }
    }

    private void jBtnToLeftAllActionPerformed(ActionEvent evt) {
        TicketLineInfo[] lines = this.receipttwo.getSelectedLines();
        if (lines != null) {
            this.receiptone.addSelectedLines(lines);
        }
    }
}

