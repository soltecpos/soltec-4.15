/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.util;

import com.openbravo.pos.forms.AppLocal;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;
import javax.swing.SwingUtilities;

public class SelectPrinter
extends JDialog {
    private String printservice;
    private boolean ok;
    private JLabel jLabel7;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JPanel jPanel8;
    private JComboBox jPrinters;
    private JButton jcmdCancel;
    private JButton jcmdOK;

    private SelectPrinter(Frame parent, boolean modal) {
        super(parent, modal);
    }

    private SelectPrinter(Dialog parent, boolean modal) {
        super(parent, modal);
    }

    public static SelectPrinter getSelectPrinter(Component parent, String[] printers) {
        Window window = SwingUtilities.windowForComponent(parent);
        SelectPrinter myMsg = window instanceof Frame ? new SelectPrinter((Frame)window, true) : new SelectPrinter((Dialog)window, true);
        myMsg.init(printers);
        myMsg.applyComponentOrientation(parent.getComponentOrientation());
        return myMsg;
    }

    private void init(String[] printers) {
        this.initComponents();
        this.jPrinters.removeAllItems();
        this.jPrinters.addItem("(Default)");
        for (String name : printers) {
            this.jPrinters.addItem(name);
        }
        this.jPrinters.setSelectedIndex(0);
        this.getRootPane().setDefaultButton(this.jcmdOK);
        this.ok = false;
        this.printservice = null;
    }

    public boolean isOK() {
        return this.ok;
    }

    public String getPrintService() {
        return this.printservice;
    }

    private void initComponents() {
        this.jPanel8 = new JPanel();
        this.jPanel2 = new JPanel();
        this.jLabel7 = new JLabel();
        this.jPrinters = new JComboBox();
        this.jPanel1 = new JPanel();
        this.jcmdCancel = new JButton();
        this.jcmdOK = new JButton();
        this.setDefaultCloseOperation(2);
        this.setTitle(AppLocal.getIntString("form.selectprintertitle"));
        this.jPanel8.setLayout(new BorderLayout());
        this.jLabel7.setFont(new Font("Arial", 0, 14));
        this.jLabel7.setText(AppLocal.getIntString("label.MachinePrinter"));
        this.jLabel7.setPreferredSize(new Dimension(150, 30));
        this.jPrinters.setFont(new Font("Arial", 0, 14));
        this.jPrinters.setPreferredSize(new Dimension(150, 30));
        GroupLayout jPanel2Layout = new GroupLayout(this.jPanel2);
        this.jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel2Layout.createSequentialGroup().addContainerGap().addComponent(this.jLabel7, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jPrinters, -2, -1, -2).addContainerGap()));
        jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel2Layout.createSequentialGroup().addContainerGap().addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel7, -2, -1, -2).addComponent(this.jPrinters, -2, -1, -2)).addContainerGap()));
        this.jcmdCancel.setFont(new Font("Arial", 0, 12));
        this.jcmdCancel.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/cancel.png")));
        this.jcmdCancel.setText(AppLocal.getIntString("button.cancel"));
        this.jcmdCancel.setMargin(new Insets(8, 16, 8, 16));
        this.jcmdCancel.setPreferredSize(new Dimension(80, 45));
        this.jcmdCancel.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                SelectPrinter.this.jcmdCancelActionPerformed(evt);
            }
        });
        this.jPanel1.add(this.jcmdCancel);
        this.jcmdOK.setFont(new Font("Arial", 0, 12));
        this.jcmdOK.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/ok.png")));
        this.jcmdOK.setText(AppLocal.getIntString("button.OK"));
        this.jcmdOK.setToolTipText("");
        this.jcmdOK.setMargin(new Insets(8, 16, 8, 16));
        this.jcmdOK.setPreferredSize(new Dimension(80, 45));
        this.jcmdOK.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                SelectPrinter.this.jcmdOKActionPerformed(evt);
            }
        });
        this.jPanel1.add(this.jcmdOK);
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jPanel2, -2, -1, -2).addComponent(this.jPanel8, -2, 345, -2).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addContainerGap().addComponent(this.jPanel1, -2, -1, -2).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.jPanel2, -2, -1, -2).addGap(0, 0, 0).addComponent(this.jPanel8, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addComponent(this.jPanel1, -2, -1, -2).addContainerGap()));
        this.setSize(new Dimension(353, 184));
        this.setLocationRelativeTo(null);
    }

    private void jcmdOKActionPerformed(ActionEvent evt) {
        this.ok = true;
        this.printservice = (String)this.jPrinters.getSelectedItem();
        this.dispose();
    }

    private void jcmdCancelActionPerformed(ActionEvent evt) {
        this.dispose();
    }
}

