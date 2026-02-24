/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.beans;

import com.openbravo.beans.LocaleResources;
import com.openbravo.beans.RoundedBorder;
import com.openbravo.editor.JEditorDoublePositive;
import com.openbravo.editor.JEditorKeys;
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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class JNumberDialog
extends JDialog {
    private static LocaleResources m_resources;
    private Double m_value;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JPanel jPanel3;
    private JPanel jPanel4;
    private JPanel jPanelGrid;
    private JButton jcmdCancel;
    private JButton jcmdOK;
    private JEditorKeys m_jKeys;
    private JPanel m_jPanelTitle;
    private JEditorDoublePositive m_jnumber;
    private JLabel m_lblMessage;

    public JNumberDialog(Frame parent, boolean modal) {
        super(parent, modal);
        this.init();
    }

    public JNumberDialog(Dialog parent, boolean modal) {
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
        this.m_jnumber.addEditorKeys(this.m_jKeys);
        this.m_jnumber.reset();
        this.m_jnumber.setDoubleValue(0.0);
        this.m_jnumber.activate();
        this.m_jPanelTitle.setBorder(RoundedBorder.createGradientBorder());
        this.m_value = null;
    }

    private void setTitle(String title, String message, Icon icon) {
        this.setTitle(title);
        this.m_lblMessage.setText(message);
        this.m_lblMessage.setIcon(icon);
    }

    public static Double showEditNumber(Component parent, String title) {
        return JNumberDialog.showEditNumber(parent, title, null, null);
    }

    public static Double showEditNumber(Component parent, String title, String message) {
        return JNumberDialog.showEditNumber(parent, title, message, null);
    }

    public static Double showEditNumber(Component parent, String title, String message, Icon icon) {
        Window window = SwingUtilities.windowForComponent(parent);
        JNumberDialog myMsg = window instanceof Frame ? new JNumberDialog((Frame)window, true) : new JNumberDialog((Dialog)window, true);
        myMsg.setTitle(title, message, icon);
        myMsg.setVisible(true);
        return myMsg.m_value;
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
        this.m_jnumber = new JEditorDoublePositive();
        this.m_jPanelTitle = new JPanel();
        this.m_lblMessage = new JLabel();
        this.setResizable(false);
        this.addWindowListener(new WindowAdapter(){

            @Override
            public void windowClosing(WindowEvent evt) {
                JNumberDialog.this.formWindowClosing(evt);
            }
        });
        this.jPanel1.setLayout(new FlowLayout(2));
        this.jcmdCancel.setFont(new Font("Arial", 0, 12));
        this.jcmdCancel.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/cancel.png")));
        this.jcmdCancel.setText(m_resources.getString("button.cancel"));
        this.jcmdCancel.setMargin(new Insets(8, 16, 8, 16));
        this.jcmdCancel.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JNumberDialog.this.jcmdCancelActionPerformed(evt);
            }
        });
        this.jPanel1.add(this.jcmdCancel);
        this.jcmdOK.setFont(new Font("Arial", 0, 12));
        this.jcmdOK.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/ok.png")));
        this.jcmdOK.setText(m_resources.getString("button.ok"));
        this.jcmdOK.setMargin(new Insets(8, 16, 8, 16));
        this.jcmdOK.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JNumberDialog.this.jcmdOKActionPerformed(evt);
            }
        });
        this.jPanel1.add(this.jcmdOK);
        this.getContentPane().add((Component)this.jPanel1, "South");
        this.jPanel2.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        this.jPanel2.setLayout(new BorderLayout());
        this.jPanelGrid.setFont(new Font("Arial", 0, 14));
        this.jPanelGrid.setPreferredSize(new Dimension(300, 300));
        this.jPanel3.setLayout(new BoxLayout(this.jPanel3, 1));
        this.m_jKeys.setFont(new Font("Arial", 0, 14));
        this.m_jKeys.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JNumberDialog.this.m_jKeysActionPerformed(evt);
            }
        });
        this.jPanel3.add(this.m_jKeys);
        this.jPanel4.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        this.jPanel4.setLayout(new BorderLayout());
        this.m_jnumber.setFont(new Font("Arial", 0, 14));
        this.jPanel4.add((Component)this.m_jnumber, "Center");
        this.jPanel3.add(this.jPanel4);
        this.jPanelGrid.add(this.jPanel3);
        this.jPanel2.add((Component)this.jPanelGrid, "Center");
        this.getContentPane().add((Component)this.jPanel2, "Center");
        this.m_jPanelTitle.setLayout(new BorderLayout());
        this.m_lblMessage.setFont(new Font("Arial", 0, 14));
        this.m_lblMessage.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.darkGray), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        this.m_jPanelTitle.add((Component)this.m_lblMessage, "Center");
        this.getContentPane().add((Component)this.m_jPanelTitle, "North");
        this.setSize(new Dimension(358, 452));
        this.setLocationRelativeTo(null);
    }

    private void jcmdOKActionPerformed(ActionEvent evt) {
        this.m_value = this.m_jnumber.getDoubleValue();
        this.setVisible(false);
        this.dispose();
    }

    private void jcmdCancelActionPerformed(ActionEvent evt) {
        this.setVisible(false);
        this.dispose();
    }

    private void formWindowClosing(WindowEvent evt) {
        this.setVisible(false);
        this.dispose();
    }

    private void m_jKeysActionPerformed(ActionEvent evt) {
    }
}

