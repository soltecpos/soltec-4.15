/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.beans;

import com.openbravo.beans.LocaleResources;
import com.openbravo.beans.RoundedBorder;
import com.openbravo.editor.JEditorKeys;
import com.openbravo.editor.JEditorPassword;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class JPasswordDialog
extends JDialog {
    private static LocaleResources m_resources;
    private String m_sPassword;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JPanel jPanel3;
    private JPanel jPanel4;
    private JPanel jPanelGrid;
    private JButton jcmdCancel;
    private JButton jcmdOK;
    private JEditorKeys m_jKeys;
    private JPanel m_jPanelTitle;
    private JEditorPassword m_jpassword;
    private JLabel m_lblMessage;

    public JPasswordDialog(Frame parent, boolean modal) {
        super(parent, modal);
        this.init();
    }

    public JPasswordDialog(Dialog parent, boolean modal) {
        super(parent, modal);
        this.init();
    }

    private void init() {
        if (m_resources == null) {
            m_resources = new LocaleResources();
            m_resources.addBundleName("beans_messages");
        }
        this.initComponents();
        this.getRootPane().setDefaultButton(this.jcmdOK);
        // Ensure Enter key always triggers OK, even when JEditorKeys has focus
        this.getRootPane().getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW)
            .put(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ENTER, 0), "confirmPassword");
        this.getRootPane().getActionMap().put("confirmPassword", new javax.swing.AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                jcmdOK.doClick();
            }
        });
        this.m_jpassword.addEditorKeys(this.m_jKeys);
        this.m_jpassword.reset();
        this.m_jpassword.activate();
        this.m_jPanelTitle.setBorder(RoundedBorder.createGradientBorder());
        this.m_sPassword = null;
    }

    private void setTitle(String title, String message, Icon icon) {
        this.setTitle(title);
        this.m_lblMessage.setText(message);
        this.m_lblMessage.setIcon(icon);
    }

    private static Window getWindow(Component parent) {
        if (parent == null) {
            return new JFrame();
        }
        if (parent instanceof Frame || parent instanceof Dialog) {
            return (Window)parent;
        }
        return JPasswordDialog.getWindow(parent.getParent());
    }

    public static String showEditPassword(Component parent, String title) {
        return JPasswordDialog.showEditPassword(parent, title, null, null);
    }

    public static String showEditPassword(Component parent, String title, String message) {
        return JPasswordDialog.showEditPassword(parent, title, message, null);
    }

    public static String showEditPassword(Component parent, String title, String message, Icon icon) {
        Window window = JPasswordDialog.getWindow(parent);
        JPasswordDialog myMsg = window instanceof Frame ? new JPasswordDialog((Frame)window, true) : new JPasswordDialog((Dialog)window, true);
        myMsg.setTitle(title, message, icon);
        myMsg.setVisible(true);
        return myMsg.m_sPassword;
    }

    private void initComponents() {
        this.jPanel1 = new JPanel();
        this.jcmdCancel = new JButton();
        this.jcmdOK = new JButton();
        this.jPanel2 = new JPanel();
        this.jPanelGrid = new JPanel();
        this.jPanel3 = new JPanel();
        this.m_jKeys = new JEditorKeys();
        this.jPanel4 = new JPanel();
        this.m_jpassword = new JEditorPassword();
        this.m_jPanelTitle = new JPanel();
        this.m_lblMessage = new JLabel();
        this.setPreferredSize(new Dimension(320, 450));
        this.setResizable(false);
        this.addWindowListener(new WindowAdapter(){

            @Override
            public void windowClosing(WindowEvent evt) {
                JPasswordDialog.this.closeWindow(evt);
            }
        });
        this.jPanel1.setLayout(new FlowLayout(2));
        this.jcmdCancel.setFont(new Font("Arial", 0, 12));
        this.jcmdCancel.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/cancel.png")));
        this.jcmdCancel.setMargin(new Insets(8, 16, 8, 16));
        this.jcmdCancel.setPreferredSize(new Dimension(80, 45));
        this.jcmdCancel.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPasswordDialog.this.jcmdCancelActionPerformed(evt);
            }
        });
        this.jPanel1.add(this.jcmdCancel);
        this.jcmdOK.setFont(new Font("Arial", 0, 12));
        this.jcmdOK.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/ok.png")));
        this.jcmdOK.setMargin(new Insets(8, 16, 8, 16));
        this.jcmdOK.setPreferredSize(new Dimension(80, 45));
        this.jcmdOK.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPasswordDialog.this.jcmdOKActionPerformed(evt);
            }
        });
        this.jPanel1.add(this.jcmdOK);
        this.getContentPane().add((Component)this.jPanel1, "South");
        this.jPanel2.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        this.jPanel2.setPreferredSize(new Dimension(320, 390));
        this.jPanel2.setLayout(new BorderLayout());
        this.jPanelGrid.setPreferredSize(new Dimension(310, 380));
        this.jPanel3.setPreferredSize(new Dimension(300, 350));
        this.jPanel3.setLayout(new BoxLayout(this.jPanel3, 1));
        this.m_jKeys.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPasswordDialog.this.m_jKeysActionPerformed(evt);
            }
        });
        this.jPanel3.add(this.m_jKeys);
        this.jPanel4.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        this.jPanel4.setPreferredSize(new Dimension(120, 30));
        this.jPanel4.setLayout(new BorderLayout());
        this.m_jpassword.setFont(new Font("Arial", 0, 14));
        this.m_jpassword.setPreferredSize(new Dimension(110, 30));
        this.jPanel4.add((Component)this.m_jpassword, "Center");
        this.jPanel3.add(this.jPanel4);
        this.jPanelGrid.add(this.jPanel3);
        this.jPanel2.add((Component)this.jPanelGrid, "Center");
        this.getContentPane().add((Component)this.jPanel2, "Center");
        this.m_jPanelTitle.setLayout(new BorderLayout());
        this.m_lblMessage.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.darkGray), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        this.m_jPanelTitle.add((Component)this.m_lblMessage, "Center");
        this.getContentPane().add((Component)this.m_jPanelTitle, "North");
        this.setSize(new Dimension(308, 489));
        this.setLocationRelativeTo(null);
    }

    private void jcmdOKActionPerformed(ActionEvent evt) {
        this.m_sPassword = this.m_jpassword.getPassword();
        this.setVisible(false);
        this.dispose();
    }

    private void jcmdCancelActionPerformed(ActionEvent evt) {
        this.setVisible(false);
        this.dispose();
    }

    private void closeWindow(WindowEvent evt) {
        this.setVisible(false);
        this.dispose();
    }

    private void m_jKeysActionPerformed(ActionEvent evt) {
        this.jcmdOK.doClick();
    }
}

