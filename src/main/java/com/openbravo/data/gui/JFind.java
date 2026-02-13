/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.gui;

import com.openbravo.basic.BasicException;
import com.openbravo.data.gui.FindInfo;
import com.openbravo.data.loader.LocalRes;
import com.openbravo.data.loader.Vectorer;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;

public class JFind
extends JDialog {
    private FindInfo m_FindInfo;
    private Vectorer m_vec;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JButton jcmdCancel;
    private JButton jcmdOK;
    private JTextField m_jFind;
    private JComboBox<String> m_jMatch;
    private JCheckBox m_jMatchCase;
    private JComboBox<String> m_jWhere;

    private JFind(Frame parent, boolean modal) {
        super(parent, modal);
    }

    private JFind(Dialog parent, boolean modal) {
        super(parent, modal);
    }

    private FindInfo init(FindInfo lastFindInfo) throws BasicException {
        this.initComponents();
        this.getRootPane().setDefaultButton(this.jcmdOK);
        this.m_jFind.setText(lastFindInfo.getText());
        this.m_jWhere.removeAllItems();
        for (int i = 0; i < lastFindInfo.getVectorer().getHeaders().length; ++i) {
            this.m_jWhere.addItem(lastFindInfo.getVectorer().getHeaders()[i]);
        }
        this.m_jWhere.setSelectedIndex(lastFindInfo.getField());
        this.m_jMatch.removeAllItems();
        this.m_jMatch.addItem(LocalRes.getIntString("list.startfield"));
        this.m_jMatch.addItem(LocalRes.getIntString("list.wholefield"));
        this.m_jMatch.addItem(LocalRes.getIntString("list.anypart"));
        this.m_jMatch.addItem(LocalRes.getIntString("list.re"));
        this.m_jMatch.setSelectedIndex(lastFindInfo.getMatch());
        this.m_jMatchCase.setSelected(lastFindInfo.isMatchCase());
        this.m_vec = lastFindInfo.getVectorer();
        this.m_FindInfo = null;
        this.setVisible(true);
        return this.m_FindInfo;
    }

    private static Window getWindow(Component parent) {
        if (parent == null) {
            return new JFrame();
        }
        if (parent instanceof Frame || parent instanceof Dialog) {
            return (Window)parent;
        }
        return JFind.getWindow(parent.getParent());
    }

    public static FindInfo showMessage(Component parent, FindInfo lastFindInfo) throws BasicException {
        Window window = JFind.getWindow(parent);
        JFind myMsg = window instanceof Frame ? new JFind((Frame)window, true) : new JFind((Dialog)window, true);
        return myMsg.init(lastFindInfo);
    }

    private void initComponents() {
        this.jPanel1 = new JPanel();
        this.jLabel1 = new JLabel();
        this.m_jFind = new JTextField();
        this.jLabel2 = new JLabel();
        this.m_jWhere = new JComboBox();
        this.jLabel3 = new JLabel();
        this.m_jMatch = new JComboBox();
        this.m_jMatchCase = new JCheckBox();
        this.jPanel2 = new JPanel();
        this.jcmdCancel = new JButton();
        this.jcmdOK = new JButton();
        this.setDefaultCloseOperation(2);
        this.setTitle(LocalRes.getIntString("title.find"));
        this.setResizable(false);
        this.jLabel1.setFont(new Font("Arial", 0, 14));
        this.jLabel1.setText(LocalRes.getIntString("label.findwhat"));
        this.jLabel1.setPreferredSize(new Dimension(150, 30));
        this.m_jFind.setFont(new Font("Arial", 0, 14));
        this.m_jFind.setPreferredSize(new Dimension(250, 30));
        this.jLabel2.setFont(new Font("Arial", 0, 14));
        this.jLabel2.setText(LocalRes.getIntString("label.where"));
        this.jLabel2.setPreferredSize(new Dimension(150, 30));
        this.m_jWhere.setFont(new Font("Arial", 0, 14));
        this.m_jWhere.setPreferredSize(new Dimension(250, 30));
        this.jLabel3.setFont(new Font("Arial", 0, 14));
        this.jLabel3.setText(LocalRes.getIntString("label.match"));
        this.jLabel3.setPreferredSize(new Dimension(150, 30));
        this.m_jMatch.setFont(new Font("Arial", 0, 14));
        this.m_jMatch.setPreferredSize(new Dimension(250, 30));
        this.m_jMatchCase.setFont(new Font("Arial", 0, 14));
        this.m_jMatchCase.setText(LocalRes.getIntString("label.casesensitive"));
        GroupLayout jPanel1Layout = new GroupLayout(this.jPanel1);
        this.jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jLabel1, -2, -1, -2).addGap(0, 0, 0).addComponent(this.m_jFind, -2, -1, -2)).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jLabel2, -2, -1, -2).addGap(0, 0, 0).addComponent(this.m_jWhere, -2, -1, -2)).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jLabel3, -2, -1, -2).addGap(0, 0, 0).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.m_jMatchCase, -2, 230, -2).addComponent(this.m_jMatch, -2, -1, -2)))).addContainerGap()));
        jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel1, -2, -1, -2).addComponent(this.m_jFind, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel2, -2, -1, -2).addComponent(this.m_jWhere, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel3, -2, -1, -2).addComponent(this.m_jMatch, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.m_jMatchCase).addContainerGap()));
        this.getContentPane().add((Component)this.jPanel1, "Center");
        this.jPanel2.setLayout(new FlowLayout(2));
        this.jcmdCancel.setFont(new Font("Arial", 0, 12));
        this.jcmdCancel.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/cancel.png")));
        this.jcmdCancel.setText(LocalRes.getIntString("button.cancel"));
        this.jcmdCancel.setPreferredSize(new Dimension(110, 45));
        this.jcmdCancel.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JFind.this.jcmdCancelActionPerformed(evt);
            }
        });
        this.jPanel2.add(this.jcmdCancel);
        this.jcmdOK.setFont(new Font("Arial", 0, 12));
        this.jcmdOK.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/ok.png")));
        this.jcmdOK.setText(LocalRes.getIntString("button.OK"));
        this.jcmdOK.setMaximumSize(new Dimension(65, 33));
        this.jcmdOK.setMinimumSize(new Dimension(65, 33));
        this.jcmdOK.setPreferredSize(new Dimension(110, 45));
        this.jcmdOK.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JFind.this.jcmdOKActionPerformed(evt);
            }
        });
        this.jPanel2.add(this.jcmdOK);
        this.getContentPane().add((Component)this.jPanel2, "South");
        this.setSize(new Dimension(434, 251));
        this.setLocationRelativeTo(null);
    }

    private void jcmdCancelActionPerformed(ActionEvent evt) {
        this.dispose();
    }

    private void jcmdOKActionPerformed(ActionEvent evt) {
        this.m_FindInfo = new FindInfo(this.m_vec, this.m_jFind.getText(), this.m_jWhere.getSelectedIndex(), this.m_jMatchCase.isSelected(), this.m_jMatch.getSelectedIndex());
        this.dispose();
    }
}

