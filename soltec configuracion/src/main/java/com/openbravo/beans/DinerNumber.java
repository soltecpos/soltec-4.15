/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.beans;

import com.openbravo.beans.LocaleResources;
import com.openbravo.beans.RoundedBorder;
import com.openbravo.editor.JEditorDoublePositive;
import com.openbravo.editor.JEditorKeys;
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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class DinerNumber
extends JDialog {
    private static final long serialVersionUID = 1L;
    private static LocaleResources m_resources;
    private Double m_value;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JPanel jPanel3;
    private JPanel jPanel4;
    private JPanel jPanelGrid;
    private JButton jcmdOK;
    private JEditorKeys m_jKeys;
    private JPanel m_jPanelTitle;
    private JEditorDoublePositive m_jnumber;
    private JLabel m_lblMessage;

    public DinerNumber(Frame parent, boolean modal) {
        super(parent, modal);
        this.init();
    }

    public DinerNumber(Dialog parent, boolean modal) {
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
        this.m_jnumber.setDoubleValue(1.0);
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
        return DinerNumber.showEditNumber(parent, title, null, null);
    }

    public static Double showEditNumber(Component parent, String title, String message) {
        return DinerNumber.showEditNumber(parent, title, message, null);
    }

    public static Double showEditNumber(Component parent, String title, String message, Icon icon) {
        Window window = SwingUtilities.windowForComponent(parent);
        DinerNumber myMsg = window instanceof Frame ? new DinerNumber((Frame)window, true) : new DinerNumber((Dialog)window, true);
        myMsg.setTitle(title, message, icon);
        myMsg.setVisible(true);
        return myMsg.m_value;
    }

    private void initComponents() {
        this.jPanel2 = new JPanel();
        this.jPanelGrid = new JPanel();
        this.jPanel3 = new JPanel();
        this.m_jKeys = new JEditorKeys();
        this.jPanel4 = new JPanel();
        this.jPanel1 = new JPanel();
        this.m_jnumber = new JEditorDoublePositive();
        this.jcmdOK = new JButton();
        this.m_jPanelTitle = new JPanel();
        this.m_lblMessage = new JLabel();
        this.setResizable(false);
        this.addWindowListener(new WindowAdapter(){

            @Override
            public void windowClosing(WindowEvent evt) {
                DinerNumber.this.formWindowClosing(evt);
            }
        });
        this.jPanel2.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        this.jPanel2.setLayout(new BorderLayout());
        this.jPanel3.setLayout(new BoxLayout(this.jPanel3, 1));
        this.m_jKeys.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                DinerNumber.this.m_jKeysActionPerformed(evt);
            }
        });
        this.jPanel3.add(this.m_jKeys);
        this.jPanel4.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        this.jPanel4.setLayout(new BorderLayout());
        this.jPanel1.setFont(new Font("Arial", 0, 14));
        this.m_jnumber.setFont(new Font("Arial", 0, 14));
        GroupLayout jPanel1Layout = new GroupLayout(this.jPanel1);
        this.jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup().addContainerGap().addComponent(this.m_jnumber, -2, 115, -2).addGap(95, 95, 95)));
        jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addComponent(this.m_jnumber, -2, -1, -2).addContainerGap(20, Short.MAX_VALUE)));
        this.jPanel4.add((Component)this.jPanel1, "Before");
        this.jcmdOK.setFont(new Font("Arial", 0, 12));
        this.jcmdOK.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/ok.png")));
        this.jcmdOK.setMargin(new Insets(8, 16, 8, 16));
        this.jcmdOK.setPreferredSize(new Dimension(80, 45));
        this.jcmdOK.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                DinerNumber.this.jcmdOKActionPerformed(evt);
            }
        });
        this.jPanel4.add((Component)this.jcmdOK, "Center");
        this.jPanel3.add(this.jPanel4);
        this.jPanelGrid.add(this.jPanel3);
        this.jPanel2.add((Component)this.jPanelGrid, "Center");
        this.getContentPane().add((Component)this.jPanel2, "Center");
        this.m_jPanelTitle.setLayout(new BorderLayout());
        this.m_lblMessage.setFont(new Font("Arial", 0, 14));
        this.m_jPanelTitle.add((Component)this.m_lblMessage, "Center");
        this.getContentPane().add((Component)this.m_jPanelTitle, "North");
        this.setSize(new Dimension(328, 409));
        this.setLocationRelativeTo(null);
    }

    private void jcmdOKActionPerformed(ActionEvent evt) {
        this.m_value = this.m_jnumber.getDoubleValue();
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

