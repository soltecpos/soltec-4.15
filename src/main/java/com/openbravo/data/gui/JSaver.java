/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.gui;

import com.openbravo.basic.BasicException;
import com.openbravo.data.gui.MessageInf;
import com.openbravo.data.loader.LocalRes;
import com.openbravo.data.user.BrowsableEditableData;
import com.openbravo.data.user.StateListener;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;

public class JSaver
extends JPanel
implements StateListener {
    protected BrowsableEditableData m_bd;
    private JButton jbtnDelete;
    private JButton jbtnNew;
    private JButton jbtnSave;

    public JSaver(BrowsableEditableData bd) {
        this.initComponents();
        this.m_bd = bd;
        this.m_bd.addStateListener(this);
    }

    @Override
    public void updateState(int iState) {
        switch (iState) {
            case 3: {
                this.jbtnNew.setEnabled(this.m_bd.canInsertData());
                this.jbtnDelete.setEnabled(false);
                this.jbtnSave.setEnabled(this.m_bd.canInsertData());
                break;
            }
            case 2: {
                this.jbtnNew.setEnabled(this.m_bd.canInsertData());
                this.jbtnDelete.setEnabled(false);
                this.jbtnSave.setEnabled(this.m_bd.canDeleteData());
                break;
            }
            case 0: {
                this.jbtnNew.setEnabled(this.m_bd.canInsertData());
                this.jbtnDelete.setEnabled(false);
                this.jbtnSave.setEnabled(false);
                break;
            }
            case 1: {
                this.jbtnNew.setEnabled(this.m_bd.canInsertData());
                this.jbtnDelete.setEnabled(this.m_bd.canDeleteData());
                this.jbtnSave.setEnabled(this.m_bd.canUpdateData());
            }
        }
    }

    private void initComponents() {
        this.jbtnNew = new JButton();
        this.jbtnDelete = new JButton();
        this.jbtnSave = new JButton();
        Font btnFont = new Font("Segoe UI", 1, 12);
        Dimension btnSize = new Dimension(100, 45);
        this.jbtnNew.setText("NUEVO");
        this.jbtnNew.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/editnew.png")));
        ResourceBundle bundle = ResourceBundle.getBundle("pos_messages");
        this.jbtnNew.setToolTipText(bundle.getString("tooltip.addnew"));
        this.jbtnNew.setBackground(new Color(220, 220, 220));
        this.jbtnNew.setFont(btnFont);
        this.jbtnNew.setPreferredSize(btnSize);
        this.jbtnNew.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JSaver.this.jbtnNewActionPerformed(evt);
            }
        });
        this.jbtnDelete.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/sale_delete.png")));
        this.jbtnDelete.setToolTipText(bundle.getString("tooltip.delete"));
        this.jbtnDelete.setBackground(new Color(255, 100, 100));
        this.jbtnDelete.setForeground(Color.WHITE);
        this.jbtnDelete.setFont(btnFont);
        this.jbtnDelete.setPreferredSize(btnSize);
        this.jbtnDelete.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JSaver.this.jbtnDeleteActionPerformed(evt);
            }
        });
        this.jbtnSave.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/filesave.png")));
        this.jbtnSave.setToolTipText(bundle.getString("tooltip.save"));
        this.jbtnSave.setBackground(new Color(46, 204, 113));
        this.jbtnSave.setForeground(Color.WHITE);
        this.jbtnSave.setFont(btnFont);
        this.jbtnSave.setPreferredSize(btnSize);
        this.jbtnSave.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JSaver.this.jbtnSaveActionPerformed(evt);
            }
        });
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jbtnNew, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jbtnDelete, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jbtnSave, -2, 120, -2).addContainerGap());
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER).addComponent(this.jbtnNew, -2, -1, -2).addComponent(this.jbtnDelete, -2, -1, -2).addComponent(this.jbtnSave, -2, -1, -2));
    }

    private void jbtnSaveActionPerformed(ActionEvent evt) {
        try {
            this.m_bd.saveData();
        }
        catch (BasicException eD) {
            MessageInf msg = new MessageInf(-67108864, LocalRes.getIntString("message.nosave"), eD);
            msg.show(this);
        }
    }

    private void jbtnDeleteActionPerformed(ActionEvent evt) {
        try {
            this.m_bd.actionDelete();
        }
        catch (BasicException eD) {
            MessageInf msg = new MessageInf(-67108864, LocalRes.getIntString("message.nodelete"), eD);
            msg.show(this);
        }
    }

    private void jbtnNewActionPerformed(ActionEvent evt) {
        try {
            this.m_bd.actionInsert();
        }
        catch (BasicException eD) {
            MessageInf msg = new MessageInf(-67108864, LocalRes.getIntString("message.nonew"), eD);
            msg.show(this);
        }
    }
}

