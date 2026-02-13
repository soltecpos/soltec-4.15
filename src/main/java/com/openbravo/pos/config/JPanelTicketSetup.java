/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.config;

import com.openbravo.data.user.DirtyManager;
import com.openbravo.pos.config.PanelConfig;
import com.openbravo.pos.forms.AppConfig;
import com.openbravo.pos.forms.AppLocal;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class JPanelTicketSetup
extends JPanel
implements PanelConfig {
    private final DirtyManager dirty = new DirtyManager();
    private String receipt = "1";
    private Integer x = 0;
    private String receiptSize;
    private String pickupSize;
    private final Integer ps = 0;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JSpinner jPickupSize;
    private JSpinner jReceiptSize;
    private JTextField jTextField2;
    private JTextField jTextReceiptPrefix;
    private JTextField jTicketExample;
    private JButton jbtnReset;
    private JCheckBox m_jReceiptPrintOff;

    public JPanelTicketSetup() {
        this.initComponents();
        this.jReceiptSize.addChangeListener(this.dirty);
        this.jPickupSize.addChangeListener(this.dirty);
        this.jTextReceiptPrefix.getDocument().addDocumentListener(this.dirty);
        this.m_jReceiptPrintOff.addActionListener(this.dirty);
        this.jbtnReset.setVisible(false);
    }

    @Override
    public boolean hasChanged() {
        return this.dirty.isDirty();
    }

    @Override
    public Component getConfigComponent() {
        return this;
    }

    @Override
    public void loadProperties(AppConfig config) {
        this.receiptSize = config.getProperty("till.receiptsize");
        if (this.receiptSize == null || "".equals(this.receiptSize)) {
            this.jReceiptSize.setModel(new SpinnerNumberModel(1, 1, 20, 1));
        } else {
            this.jReceiptSize.setModel(new SpinnerNumberModel(Integer.parseInt(this.receiptSize), 1, 20, 1));
        }
        this.pickupSize = config.getProperty("till.pickupsize");
        if (this.pickupSize == null || "".equals(this.pickupSize)) {
            this.jPickupSize.setModel(new SpinnerNumberModel(1, 1, 20, 1));
        } else {
            this.jPickupSize.setModel(new SpinnerNumberModel(Integer.parseInt(this.pickupSize), 1, 20, 1));
        }
        this.jTextReceiptPrefix.setText(config.getProperty("till.receiptprefix"));
        this.receipt = "";
        this.x = 1;
        while (this.x < (Integer)this.jReceiptSize.getValue()) {
            this.receipt = this.receipt + "0";
            Integer n = this.x;
            this.x = this.x + 1;
        }
        this.receipt = this.receipt + "1";
        this.jTicketExample.setText(this.jTextReceiptPrefix.getText() + this.receipt);
        this.m_jReceiptPrintOff.setSelected(Boolean.parseBoolean(config.getProperty("till.receiptprintoff")));
        this.dirty.setDirty(false);
    }

    public void loadUp() throws ClassNotFoundException, SQLException {
    }

    @Override
    public void saveProperties(AppConfig config) {
        config.setProperty("till.receiptprefix", this.jTextReceiptPrefix.getText());
        config.setProperty("till.receiptsize", this.jReceiptSize.getValue().toString());
        config.setProperty("till.pickupsize", this.jPickupSize.getValue().toString());
        config.setProperty("till.receiptprintoff", Boolean.toString(this.m_jReceiptPrintOff.isSelected()));
        this.dirty.setDirty(false);
    }

    private void initComponents() {
        this.jTextField2 = new JTextField();
        this.jLabel1 = new JLabel();
        this.jReceiptSize = new JSpinner();
        this.jLabel3 = new JLabel();
        this.jTextReceiptPrefix = new JTextField();
        this.jTicketExample = new JTextField();
        this.jLabel2 = new JLabel();
        this.jPickupSize = new JSpinner();
        this.m_jReceiptPrintOff = new JCheckBox();
        this.jbtnReset = new JButton();
        this.jTextField2.setText("jTextField2");
        this.setBackground(new Color(255, 255, 255));
        this.setPreferredSize(new Dimension(700, 500));
        this.jLabel1.setFont(new Font("Arial", 0, 14));
        this.jLabel1.setHorizontalAlignment(2);
        ResourceBundle bundle = ResourceBundle.getBundle("pos_messages");
        this.jLabel1.setText(bundle.getString("label.ticketsetupnumber"));
        this.jLabel1.setPreferredSize(new Dimension(190, 30));
        this.jReceiptSize.setFont(new Font("Arial", 0, 18));
        this.jReceiptSize.setModel(new SpinnerNumberModel((Number)0, Integer.valueOf(0), null, (Number)1));
        this.jReceiptSize.setPreferredSize(new Dimension(50, 30));
        this.jReceiptSize.addChangeListener(new ChangeListener(){

            @Override
            public void stateChanged(ChangeEvent evt) {
                JPanelTicketSetup.this.jReceiptSizeStateChanged(evt);
            }
        });
        this.jLabel3.setFont(new Font("Arial", 0, 14));
        this.jLabel3.setText(bundle.getString("label.ticketsetupprefix"));
        this.jTextReceiptPrefix.setFont(new Font("Arial", 0, 18));
        this.jTextReceiptPrefix.setHorizontalAlignment(0);
        this.jTextReceiptPrefix.setPreferredSize(new Dimension(100, 30));
        this.jTextReceiptPrefix.addKeyListener(new KeyAdapter(){

            @Override
            public void keyReleased(KeyEvent evt) {
                JPanelTicketSetup.this.jTextReceiptPrefixKeyReleased(evt);
            }
        });
        this.jTicketExample.setFont(new Font("Arial", 0, 18));
        this.jTicketExample.setText("1");
        this.jTicketExample.setDisabledTextColor(new Color(0, 0, 0));
        this.jTicketExample.setEnabled(false);
        this.jTicketExample.setPreferredSize(new Dimension(100, 30));
        this.jLabel2.setFont(new Font("Arial", 0, 14));
        this.jLabel2.setText(bundle.getString("label.pickupcodesize"));
        this.jLabel2.setPreferredSize(new Dimension(190, 30));
        this.jPickupSize.setFont(new Font("Arial", 0, 18));
        this.jPickupSize.setModel(new SpinnerNumberModel((Number)0, Integer.valueOf(0), null, (Number)1));
        this.jPickupSize.setToolTipText("");
        this.jPickupSize.setPreferredSize(new Dimension(50, 30));
        this.jPickupSize.addChangeListener(new ChangeListener(){

            @Override
            public void stateChanged(ChangeEvent evt) {
                JPanelTicketSetup.this.jPickupSizeStateChanged(evt);
            }
        });
        this.m_jReceiptPrintOff.setBackground(new Color(255, 255, 255));
        this.m_jReceiptPrintOff.setFont(new Font("Arial", 0, 14));
        this.m_jReceiptPrintOff.setText(bundle.getString("label.receiptprint"));
        this.m_jReceiptPrintOff.setPreferredSize(new Dimension(180, 30));
        this.m_jReceiptPrintOff.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPanelTicketSetup.this.m_jReceiptPrintOffActionPerformed(evt);
            }
        });
        this.jbtnReset.setFont(new Font("Arial", 0, 12));
        this.jbtnReset.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/reload.png")));
        this.jbtnReset.setText(AppLocal.getIntString("label.resetpickup"));
        this.jbtnReset.setMaximumSize(new Dimension(70, 33));
        this.jbtnReset.setMinimumSize(new Dimension(70, 33));
        this.jbtnReset.setPreferredSize(new Dimension(100, 45));
        this.jbtnReset.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPanelTicketSetup.this.jbtnResetActionPerformed(evt);
            }
        });
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.m_jReceiptPrintOff, -2, -1, -2).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup().addComponent(this.jLabel2, -2, -1, -2).addGap(18, 18, 18).addComponent(this.jPickupSize, -2, -1, -2).addGap(18, 18, 18).addComponent(this.jbtnReset, -2, -1, -2)).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel1, -2, -1, -2).addComponent(this.jLabel3, -2, 160, -2)).addGap(18, 18, 18).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jReceiptSize, -2, -1, -2).addGroup(layout.createSequentialGroup().addComponent(this.jTextReceiptPrefix, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jTicketExample, -2, -1, -2)))))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1, -2, -1, -2).addComponent(this.jReceiptSize, -2, -1, -2)).addGap(18, 18, 18).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel3, -2, 40, -2).addComponent(this.jTextReceiptPrefix, -2, -1, -2).addComponent(this.jTicketExample, -2, -1, -2)).addGap(18, 18, 18).addComponent(this.m_jReceiptPrintOff, -2, -1, -2).addGap(18, 18, 18).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jPickupSize, -2, -1, -2).addComponent(this.jLabel2, -2, -1, -2).addComponent(this.jbtnReset, -2, -1, -2)).addGap(295, 295, 295)));
    }

    private void jTextReceiptPrefixKeyReleased(KeyEvent evt) {
        this.jTicketExample.setText(this.jTextReceiptPrefix.getText() + this.receipt);
    }

    private void jReceiptSizeStateChanged(ChangeEvent evt) {
        this.receipt = "";
        this.x = 1;
        while (this.x < (Integer)this.jReceiptSize.getValue()) {
            this.receipt = this.receipt + "0";
            Integer n = this.x;
            this.x = this.x + 1;
        }
        this.receipt = this.receipt + "1";
        this.jTicketExample.setText(this.jTextReceiptPrefix.getText() + this.receipt);
    }

    private void jPickupSizeStateChanged(ChangeEvent evt) {
    }

    private void m_jReceiptPrintOffActionPerformed(ActionEvent evt) {
    }

    private void jbtnResetActionPerformed(ActionEvent evt) {
        try {
            this.loadUp();
        }
        catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(JPanelTicketSetup.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

