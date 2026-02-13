/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.gui;

import com.openbravo.basic.BasicException;
import com.openbravo.data.gui.MessageInf;
import com.openbravo.data.loader.ComparatorCreator;
import com.openbravo.data.loader.LocalRes;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Comparator;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;

public class JSort
extends JDialog {
    private ComparatorCreator m_cc;
    private Comparator<?> m_Comparator;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JButton jcmdCancel;
    private JButton jcmdOK;
    private JComboBox<String> m_jSort1;
    private JComboBox<String> m_jSort2;
    private JComboBox<String> m_jSort3;

    private JSort(Frame parent, boolean modal) {
        super(parent, modal);
    }

    private JSort(Dialog parent, boolean modal) {
        super(parent, modal);
    }

    private Comparator<?> init(ComparatorCreator cc) throws BasicException {
        int i;
        this.initComponents();
        this.getRootPane().setDefaultButton(this.jcmdOK);
        this.m_cc = cc;
        String[] sHeaders = this.m_cc.getHeaders();
        this.m_jSort1.removeAllItems();
        this.m_jSort1.addItem("");
        for (i = 0; i < sHeaders.length; ++i) {
            this.m_jSort1.addItem(sHeaders[i]);
        }
        this.m_jSort1.setSelectedItem(0);
        this.m_jSort2.removeAllItems();
        this.m_jSort2.addItem("");
        for (i = 0; i < sHeaders.length; ++i) {
            this.m_jSort2.addItem(sHeaders[i]);
        }
        this.m_jSort2.setSelectedItem(0);
        this.m_jSort3.removeAllItems();
        this.m_jSort3.addItem("");
        for (i = 0; i < sHeaders.length; ++i) {
            this.m_jSort3.addItem(sHeaders[i]);
        }
        this.m_jSort3.setSelectedItem(0);
        this.m_Comparator = null;
        this.setVisible(true);
        return this.m_Comparator;
    }

    private static Window getWindow(Component parent) {
        if (parent == null) {
            return new JFrame();
        }
        if (parent instanceof Frame || parent instanceof Dialog) {
            return (Window)parent;
        }
        return JSort.getWindow(parent.getParent());
    }

    public static Comparator<?> showMessage(Component parent, ComparatorCreator cc) throws BasicException {
        Window window = JSort.getWindow(parent);
        JSort myMsg = window instanceof Frame ? new JSort((Frame)window, true) : new JSort((Dialog)window, true);
        return myMsg.init(cc);
    }

    private void initComponents() {
        this.jPanel1 = new JPanel();
        this.jLabel2 = new JLabel();
        this.m_jSort1 = new JComboBox();
        this.jLabel3 = new JLabel();
        this.m_jSort2 = new JComboBox();
        this.jLabel4 = new JLabel();
        this.m_jSort3 = new JComboBox();
        this.jPanel2 = new JPanel();
        this.jcmdCancel = new JButton();
        this.jcmdOK = new JButton();
        this.setDefaultCloseOperation(2);
        this.setTitle(LocalRes.getIntString("caption.sort"));
        this.setResizable(false);
        this.jLabel2.setFont(new Font("Arial", 0, 14));
        this.jLabel2.setText(LocalRes.getIntString("label.sortby"));
        this.jLabel2.setPreferredSize(new Dimension(150, 30));
        this.m_jSort1.setFont(new Font("Arial", 0, 14));
        this.m_jSort1.setPreferredSize(new Dimension(200, 30));
        this.jLabel3.setFont(new Font("Arial", 0, 14));
        this.jLabel3.setText(LocalRes.getIntString("label.andby"));
        this.jLabel3.setPreferredSize(new Dimension(150, 30));
        this.m_jSort2.setFont(new Font("Arial", 0, 14));
        this.m_jSort2.setPreferredSize(new Dimension(200, 30));
        this.jLabel4.setFont(new Font("Arial", 0, 14));
        this.jLabel4.setText(LocalRes.getIntString("label.andby"));
        this.jLabel4.setPreferredSize(new Dimension(150, 30));
        this.m_jSort3.setFont(new Font("Arial", 0, 14));
        this.m_jSort3.setPreferredSize(new Dimension(200, 30));
        GroupLayout jPanel1Layout = new GroupLayout(this.jPanel1);
        this.jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jLabel2, -2, -1, -2).addGap(10, 10, 10).addComponent(this.m_jSort1, -2, -1, -2)).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jLabel3, -2, -1, -2).addGap(10, 10, 10).addComponent(this.m_jSort2, -2, -1, -2)).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jLabel4, -2, -1, -2).addGap(10, 10, 10).addComponent(this.m_jSort3, -2, -1, -2)))));
        jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel2, -2, -1, -2).addComponent(this.m_jSort1, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel3, -2, -1, -2).addComponent(this.m_jSort2, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.m_jSort3, -2, -1, -2).addComponent(this.jLabel4, -2, -1, -2))));
        this.getContentPane().add((Component)this.jPanel1, "Center");
        this.jPanel2.setLayout(new FlowLayout(2));
        this.jcmdCancel.setFont(new Font("Arial", 0, 12));
        this.jcmdCancel.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/cancel.png")));
        this.jcmdCancel.setText(LocalRes.getIntString("button.cancel"));
        this.jcmdCancel.setMaximumSize(new Dimension(65, 33));
        this.jcmdCancel.setMinimumSize(new Dimension(65, 33));
        this.jcmdCancel.setPreferredSize(new Dimension(110, 45));
        this.jcmdCancel.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JSort.this.jcmdCancelActionPerformed(evt);
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
                JSort.this.jcmdOKActionPerformed(evt);
            }
        });
        this.jPanel2.add(this.jcmdOK);
        this.getContentPane().add((Component)this.jPanel2, "South");
        this.setSize(new Dimension(396, 234));
        this.setLocationRelativeTo(null);
    }

    private void jcmdCancelActionPerformed(ActionEvent evt) {
        this.dispose();
    }

    private void jcmdOKActionPerformed(ActionEvent evt) {
        int iSort1 = this.m_jSort1.getSelectedIndex();
        int iSort2 = this.m_jSort2.getSelectedIndex();
        int iSort3 = this.m_jSort3.getSelectedIndex();
        if (iSort1 > 0 && iSort2 == 0 && iSort3 == 0) {
            this.m_Comparator = this.m_cc.createComparator(new int[]{iSort1 - 1});
            this.dispose();
        } else if (iSort1 > 0 && iSort2 > 0 && iSort3 == 0) {
            this.m_Comparator = this.m_cc.createComparator(new int[]{iSort1 - 1, iSort2 - 1});
            this.dispose();
        } else if (iSort1 > 0 && iSort2 > 0 && iSort3 > 0) {
            this.m_Comparator = this.m_cc.createComparator(new int[]{iSort1 - 1, iSort2 - 1, iSort3 - 1});
            this.dispose();
        } else {
            MessageInf msg = new MessageInf(-67108864, LocalRes.getIntString("message.nosort"));
            msg.show(this);
        }
    }
}

