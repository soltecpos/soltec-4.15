/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.forms;

import com.openbravo.data.gui.JMessageDialog;
import com.openbravo.data.gui.MessageInf;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.util.Hashcypher;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.LayoutStyle;

public class JDlgChangePassword
extends JDialog {
    private String m_sOldPassword;
    private String m_sNewPassword;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JButton jcmdCancel;
    private JButton jcmdOK;
    private JPasswordField jtxtPasswordNew;
    private JPasswordField jtxtPasswordOld;
    private JPasswordField jtxtPasswordRepeat;

    private JDlgChangePassword(Frame parent, boolean modal) {
        super(parent, modal);
    }

    private JDlgChangePassword(Dialog parent, boolean modal) {
        super(parent, modal);
    }

    private String init(String sOldPassword) {
        this.initComponents();
        this.getRootPane().setDefaultButton(this.jcmdOK);
        this.m_sOldPassword = sOldPassword;
        this.m_sNewPassword = null;
        this.setVisible(true);
        return this.m_sNewPassword;
    }

    private static Window getWindow(Component parent) {
        if (parent == null) {
            return new JFrame();
        }
        if (parent instanceof Frame || parent instanceof Dialog) {
            return (Window)parent;
        }
        return JDlgChangePassword.getWindow(parent.getParent());
    }

    public static String showMessage(Component parent, String sOldPassword) {
        Window window = JDlgChangePassword.getWindow(parent);
        JDlgChangePassword myMsg = window instanceof Frame ? new JDlgChangePassword((Frame)window, true) : new JDlgChangePassword((Dialog)window, true);
        return myMsg.init(sOldPassword);
    }

    private void initComponents() {
        this.jPanel2 = new JPanel();
        this.jcmdCancel = new JButton();
        this.jcmdOK = new JButton();
        this.jPanel1 = new JPanel();
        this.jLabel1 = new JLabel();
        this.jtxtPasswordOld = new JPasswordField();
        this.jLabel2 = new JLabel();
        this.jtxtPasswordNew = new JPasswordField();
        this.jtxtPasswordRepeat = new JPasswordField();
        this.jLabel3 = new JLabel();
        this.setDefaultCloseOperation(2);
        this.setTitle(AppLocal.getIntString("title.changepassword"));
        this.setResizable(false);
        this.jPanel2.setLayout(new FlowLayout(2));
        this.jcmdCancel.setFont(new Font("Arial", 0, 12));
        this.jcmdCancel.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/cancel.png")));
        this.jcmdCancel.setText(AppLocal.getIntString("button.cancel"));
        this.jcmdCancel.setPreferredSize(new Dimension(110, 45));
        this.jcmdCancel.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JDlgChangePassword.this.jcmdCancelActionPerformed(evt);
            }
        });
        this.jPanel2.add(this.jcmdCancel);
        this.jcmdOK.setFont(new Font("Arial", 0, 12));
        this.jcmdOK.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/ok.png")));
        this.jcmdOK.setText(AppLocal.getIntString("button.OK"));
        this.jcmdOK.setPreferredSize(new Dimension(110, 45));
        this.jcmdOK.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JDlgChangePassword.this.jcmdOKActionPerformed(evt);
            }
        });
        this.jPanel2.add(this.jcmdOK);
        this.getContentPane().add((Component)this.jPanel2, "South");
        this.jPanel1.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        this.jPanel1.setFont(new Font("Arial", 0, 11));
        this.jLabel1.setFont(new Font("Arial", 0, 14));
        this.jLabel1.setText(AppLocal.getIntString("label.passwordold"));
        this.jLabel1.setPreferredSize(new Dimension(150, 30));
        this.jtxtPasswordOld.setFont(new Font("Arial", 0, 14));
        this.jtxtPasswordOld.setPreferredSize(new Dimension(0, 30));
        this.jLabel2.setFont(new Font("Arial", 0, 14));
        this.jLabel2.setText(AppLocal.getIntString("label.passwordnew"));
        this.jLabel2.setPreferredSize(new Dimension(150, 30));
        this.jtxtPasswordNew.setFont(new Font("Arial", 0, 12));
        this.jtxtPasswordNew.setPreferredSize(new Dimension(0, 30));
        this.jtxtPasswordRepeat.setFont(new Font("Arial", 0, 14));
        this.jtxtPasswordRepeat.setPreferredSize(new Dimension(0, 30));
        this.jLabel3.setFont(new Font("Arial", 0, 14));
        this.jLabel3.setText(AppLocal.getIntString("label.passwordrepeat"));
        this.jLabel3.setPreferredSize(new Dimension(150, 30));
        GroupLayout jPanel1Layout = new GroupLayout(this.jPanel1);
        this.jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jLabel1, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jtxtPasswordOld, -2, 180, -2)).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jLabel2, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jtxtPasswordNew, -2, 180, -2)).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jLabel3, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jtxtPasswordRepeat, -2, 180, -2))).addGap(88, 88, 88)));
        jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1, -2, -1, -2).addComponent(this.jtxtPasswordOld, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jtxtPasswordNew, -2, -1, -2).addComponent(this.jLabel2, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jtxtPasswordRepeat, -2, -1, -2).addComponent(this.jLabel3, -2, -1, -2))));
        this.getContentPane().add((Component)this.jPanel1, "Center");
        this.setSize(new Dimension(416, 234));
        this.setLocationRelativeTo(null);
    }

    private void jcmdCancelActionPerformed(ActionEvent evt) {
        this.dispose();
    }

    private void jcmdOKActionPerformed(ActionEvent evt) {
        if (new String(this.jtxtPasswordNew.getPassword()).equals(new String(this.jtxtPasswordRepeat.getPassword()))) {
            if (Hashcypher.authenticate(new String(this.jtxtPasswordOld.getPassword()), this.m_sOldPassword)) {
                this.m_sNewPassword = Hashcypher.hashString(new String(this.jtxtPasswordNew.getPassword()));
                this.dispose();
            } else {
                JMessageDialog.showMessage(this, new MessageInf(-33554432, AppLocal.getIntString("message.BadPassword")));
            }
        } else {
            JMessageDialog.showMessage(this, new MessageInf(-33554432, AppLocal.getIntString("message.changepassworddistinct")));
        }
    }
}

