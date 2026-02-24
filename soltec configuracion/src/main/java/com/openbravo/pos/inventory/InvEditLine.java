/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.inventory;

import com.openbravo.pos.forms.AppLocal;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;

public class InvEditLine
extends JDialog {
    private boolean ok;
    private JButton webButtonCancel;
    private JButton webButtonOK;
    private JLabel webLabel1;
    private JTextField webTxtPriceBuy;

    public InvEditLine(Frame parent, boolean modal) {
        super(parent, modal);
        this.initComponents();
    }

    public boolean isOK() {
        boolean ok = false;
        return ok;
    }

    private void initComponents() {
        this.webLabel1 = new JLabel();
        this.webTxtPriceBuy = new JTextField();
        this.webButtonOK = new JButton();
        this.webButtonCancel = new JButton();
        this.setDefaultCloseOperation(2);
        this.webLabel1.setText(AppLocal.getIntString("button.exit"));
        this.webLabel1.setFont(new Font("Arial", 0, 14));
        this.webLabel1.setPreferredSize(new Dimension(130, 30));
        this.webTxtPriceBuy.setText("webTextField1");
        this.webTxtPriceBuy.setFont(new Font("Arial", 0, 14));
        this.webTxtPriceBuy.setPreferredSize(new Dimension(200, 30));
        this.webButtonOK.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/ok.png")));
        this.webButtonOK.setPreferredSize(new Dimension(80, 45));
        this.webButtonOK.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                InvEditLine.this.webButtonOKActionPerformed(evt);
            }
        });
        this.webButtonCancel.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/cancel.png")));
        this.webButtonCancel.setPreferredSize(new Dimension(80, 45));
        this.webButtonCancel.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                InvEditLine.this.webButtonCancelActionPerformed(evt);
            }
        });
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(25, 25, 25).addComponent(this.webLabel1, -2, -1, -2).addGap(18, 18, 18).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addGroup(layout.createSequentialGroup().addComponent(this.webButtonCancel, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addComponent(this.webButtonOK, -2, -1, -2)).addComponent(this.webTxtPriceBuy, -2, -1, -2)).addContainerGap(27, Short.MAX_VALUE)));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(27, 27, 27).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.webLabel1, -2, -1, -2).addComponent(this.webTxtPriceBuy, -2, -1, -2)).addGap(18, 18, 18).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.webButtonOK, -2, 45, -2).addComponent(this.webButtonCancel, -2, -1, -2)).addContainerGap(19, Short.MAX_VALUE)));
        this.pack();
    }

    private void webButtonOKActionPerformed(ActionEvent evt) {
        this.ok = true;
        String invlinevalue = this.webTxtPriceBuy.getText();
        this.dispose();
    }

    private void webButtonCancelActionPerformed(ActionEvent evt) {
        this.dispose();
    }
}

