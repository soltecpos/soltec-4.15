/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.beans;

import com.openbravo.beans.JNumberEvent;
import com.openbravo.beans.JNumberEventListener;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class JNumberKeys
extends JPanel {
    private Vector<JNumberEventListener> m_Listeners = new Vector();
    private boolean minusenabled = true;
    private boolean equalsenabled = true;
    private JButton m_jCE;
    private JButton m_jEquals;
    private JButton m_jKey0;
    private JButton m_jKey1;
    private JButton m_jKey2;
    private JButton m_jKey3;
    private JButton m_jKey4;
    private JButton m_jKey5;
    private JButton m_jKey6;
    private JButton m_jKey7;
    private JButton m_jKey8;
    private JButton m_jKey9;
    private JButton m_jKeyDot;
    private JButton m_jMinus;
    private JButton m_jMultiply;
    private JButton m_jPlus;

    public JNumberKeys() {
        this.initComponents();
        this.m_jKey0.addActionListener(new MyKeyNumberListener('0'));
        this.m_jKey1.addActionListener(new MyKeyNumberListener('1'));
        this.m_jKey2.addActionListener(new MyKeyNumberListener('2'));
        this.m_jKey3.addActionListener(new MyKeyNumberListener('3'));
        this.m_jKey4.addActionListener(new MyKeyNumberListener('4'));
        this.m_jKey5.addActionListener(new MyKeyNumberListener('5'));
        this.m_jKey6.addActionListener(new MyKeyNumberListener('6'));
        this.m_jKey7.addActionListener(new MyKeyNumberListener('7'));
        this.m_jKey8.addActionListener(new MyKeyNumberListener('8'));
        this.m_jKey9.addActionListener(new MyKeyNumberListener('9'));
        this.m_jKeyDot.addActionListener(new MyKeyNumberListener('.'));
        this.m_jMultiply.addActionListener(new MyKeyNumberListener('*'));
        this.m_jCE.addActionListener(new MyKeyNumberListener('\u007f'));
        this.m_jPlus.addActionListener(new MyKeyNumberListener('+'));
        this.m_jMinus.addActionListener(new MyKeyNumberListener('-'));
        this.m_jEquals.addActionListener(new MyKeyNumberListener('='));
    }

    public void setNumbersOnly(boolean value) {
        this.m_jEquals.setVisible(value);
        this.m_jMinus.setVisible(value);
        this.m_jPlus.setVisible(value);
        this.m_jMultiply.setVisible(value);
    }

    @Override
    public void setEnabled(boolean b) {
        super.setEnabled(b);
        this.m_jKey0.setEnabled(b);
        this.m_jKey1.setEnabled(b);
        this.m_jKey2.setEnabled(b);
        this.m_jKey3.setEnabled(b);
        this.m_jKey4.setEnabled(b);
        this.m_jKey5.setEnabled(b);
        this.m_jKey6.setEnabled(b);
        this.m_jKey7.setEnabled(b);
        this.m_jKey8.setEnabled(b);
        this.m_jKey9.setEnabled(b);
        this.m_jKeyDot.setEnabled(b);
        this.m_jMultiply.setEnabled(b);
        this.m_jCE.setEnabled(b);
        this.m_jPlus.setEnabled(b);
        this.m_jMinus.setEnabled(this.minusenabled && b);
        this.m_jEquals.setEnabled(this.equalsenabled && b);
    }

    @Override
    public void setComponentOrientation(ComponentOrientation o) {
    }

    public void setMinusEnabled(boolean b) {
        this.minusenabled = b;
        this.m_jMinus.setEnabled(this.minusenabled && this.isEnabled());
    }

    public boolean isMinusEnabled() {
        return this.minusenabled;
    }

    public void setEqualsEnabled(boolean b) {
        this.equalsenabled = b;
        this.m_jEquals.setEnabled(this.equalsenabled && this.isEnabled());
    }

    public boolean isEqualsEnabled() {
        return this.equalsenabled;
    }

    public void dotIs00(boolean enabled) {
        if (enabled) {
            this.m_jKeyDot.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/btn00.png")));
        }
    }

    public boolean isNumbersOnly() {
        return this.m_jEquals.isVisible();
    }

    public void addJNumberEventListener(JNumberEventListener listener) {
        this.m_Listeners.add(listener);
    }

    public void removeJNumberEventListener(JNumberEventListener listener) {
        this.m_Listeners.remove(listener);
    }

    private void initComponents() {
        int i;
        this.m_jCE = new JButton();
        this.m_jMultiply = new JButton();
        this.m_jMinus = new JButton();
        this.m_jPlus = new JButton();
        this.m_jKey9 = new JButton();
        this.m_jKey8 = new JButton();
        this.m_jKey7 = new JButton();
        this.m_jKey4 = new JButton();
        this.m_jKey5 = new JButton();
        this.m_jKey6 = new JButton();
        this.m_jKey3 = new JButton();
        this.m_jKey2 = new JButton();
        this.m_jKey1 = new JButton();
        this.m_jKey0 = new JButton();
        this.m_jKeyDot = new JButton();
        this.m_jEquals = new JButton();
        this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        this.setMinimumSize(new Dimension(150, 150));
        this.setPreferredSize(new Dimension(150, 180));
        this.setLayout(new GridBagLayout());
        Font fontKeys = new Font("Arial", 1, 18);
        Color colorText = new Color(0, 51, 102);
        Color colorBg = new Color(245, 245, 245);
        Border borderKey = BorderFactory.createLineBorder(new Color(160, 160, 160), 1);
        this.m_jCE.setText("CE");
        this.m_jCE.setFont(new Font("Arial", 1, 16));
        this.m_jCE.setForeground(Color.RED);
        this.m_jCE.setBackground(colorBg);
        this.m_jCE.setBorder(borderKey);
        this.m_jCE.setFocusPainted(false);
        this.m_jCE.setFocusable(false);
        this.m_jCE.setPreferredSize(new Dimension(66, 35));
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = 1;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new Insets(1, 1, 1, 1);
        this.add((Component)this.m_jCE, gridBagConstraints);
        this.m_jMultiply.setText("*");
        this.m_jMultiply.setFont(fontKeys);
        this.m_jMultiply.setForeground(colorText);
        this.m_jMultiply.setBackground(colorBg);
        this.m_jMultiply.setBorder(borderKey);
        this.m_jMultiply.setFocusPainted(false);
        this.m_jMultiply.setFocusable(false);
        this.m_jMultiply.setPreferredSize(new Dimension(42, 35));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = 1;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new Insets(1, 1, 1, 1);
        this.add((Component)this.m_jMultiply, gridBagConstraints);
        this.m_jMinus.setText("-");
        this.m_jMinus.setFont(fontKeys);
        this.m_jMinus.setForeground(colorText);
        this.m_jMinus.setBackground(colorBg);
        this.m_jMinus.setBorder(borderKey);
        this.m_jMinus.setFocusPainted(false);
        this.m_jMinus.setFocusable(false);
        this.m_jMinus.setPreferredSize(new Dimension(42, 35));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = 1;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new Insets(1, 1, 1, 1);
        this.add((Component)this.m_jMinus, gridBagConstraints);
        this.m_jPlus.setText("+");
        this.m_jPlus.setFont(fontKeys);
        this.m_jPlus.setForeground(colorText);
        this.m_jPlus.setBackground(colorBg);
        this.m_jPlus.setBorder(borderKey);
        this.m_jPlus.setFocusPainted(false);
        this.m_jPlus.setFocusable(false);
        this.m_jPlus.setPreferredSize(new Dimension(42, 35));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = 1;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new Insets(1, 1, 1, 1);
        this.add((Component)this.m_jPlus, gridBagConstraints);
        JButton[] keys = new JButton[]{this.m_jKey7, this.m_jKey8, this.m_jKey9};
        String[] labels = new String[]{"7", "8", "9"};
        for (i = 0; i < 3; ++i) {
            keys[i].setText(labels[i]);
            keys[i].setFont(fontKeys);
            keys[i].setForeground(colorText);
            keys[i].setBackground(colorBg);
            keys[i].setBorder(borderKey);
            keys[i].setFocusPainted(false);
            keys[i].setFocusable(false);
            keys[i].setPreferredSize(new Dimension(42, 35));
            gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.gridx = i;
            gridBagConstraints.gridy = 3;
            gridBagConstraints.fill = 1;
            gridBagConstraints.weightx = 1.0;
            gridBagConstraints.weighty = 1.0;
            gridBagConstraints.insets = new Insets(1, 1, 1, 1);
            this.add((Component)keys[i], gridBagConstraints);
        }
        keys = new JButton[]{this.m_jKey4, this.m_jKey5, this.m_jKey6};
        labels = new String[]{"4", "5", "6"};
        for (i = 0; i < 3; ++i) {
            keys[i].setText(labels[i]);
            keys[i].setFont(fontKeys);
            keys[i].setForeground(colorText);
            keys[i].setBackground(colorBg);
            keys[i].setBorder(borderKey);
            keys[i].setFocusPainted(false);
            keys[i].setFocusable(false);
            gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.gridx = i;
            gridBagConstraints.gridy = 2;
            gridBagConstraints.fill = 1;
            gridBagConstraints.weightx = 1.0;
            gridBagConstraints.weighty = 1.0;
            gridBagConstraints.insets = new Insets(1, 1, 1, 1);
            this.add((Component)keys[i], gridBagConstraints);
        }
        keys = new JButton[]{this.m_jKey1, this.m_jKey2, this.m_jKey3};
        labels = new String[]{"1", "2", "3"};
        for (i = 0; i < 3; ++i) {
            keys[i].setText(labels[i]);
            keys[i].setFont(fontKeys);
            keys[i].setForeground(colorText);
            keys[i].setBackground(colorBg);
            keys[i].setBorder(borderKey);
            keys[i].setFocusPainted(false);
            keys[i].setFocusable(false);
            gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.gridx = i;
            gridBagConstraints.gridy = 1;
            gridBagConstraints.fill = 1;
            gridBagConstraints.weightx = 1.0;
            gridBagConstraints.weighty = 1.0;
            gridBagConstraints.insets = new Insets(1, 1, 1, 1);
            this.add((Component)keys[i], gridBagConstraints);
        }
        ((GridBagLayout)this.getLayout()).setConstraints(this.m_jKey7, this.newConstraints(0, 1, 1, 1));
        ((GridBagLayout)this.getLayout()).setConstraints(this.m_jKey8, this.newConstraints(1, 1, 1, 1));
        ((GridBagLayout)this.getLayout()).setConstraints(this.m_jKey9, this.newConstraints(2, 1, 1, 1));
        ((GridBagLayout)this.getLayout()).setConstraints(this.m_jKey4, this.newConstraints(0, 2, 1, 1));
        ((GridBagLayout)this.getLayout()).setConstraints(this.m_jKey5, this.newConstraints(1, 2, 1, 1));
        ((GridBagLayout)this.getLayout()).setConstraints(this.m_jKey6, this.newConstraints(2, 2, 1, 1));
        ((GridBagLayout)this.getLayout()).setConstraints(this.m_jKey1, this.newConstraints(0, 3, 1, 1));
        ((GridBagLayout)this.getLayout()).setConstraints(this.m_jKey2, this.newConstraints(1, 3, 1, 1));
        ((GridBagLayout)this.getLayout()).setConstraints(this.m_jKey3, this.newConstraints(2, 3, 1, 1));
        this.m_jKey0.setText("0");
        this.m_jKey0.setFont(fontKeys);
        this.m_jKey0.setForeground(colorText);
        this.m_jKey0.setBackground(colorBg);
        this.m_jKey0.setBorder(borderKey);
        this.m_jKey0.setFocusPainted(false);
        this.m_jKey0.setFocusable(false);
        gridBagConstraints = this.newConstraints(0, 4, 2, 1);
        this.add((Component)this.m_jKey0, gridBagConstraints);
        this.m_jKeyDot.setText(".");
        this.m_jKeyDot.setFont(fontKeys);
        this.m_jKeyDot.setForeground(colorText);
        this.m_jKeyDot.setBackground(colorBg);
        this.m_jKeyDot.setBorder(borderKey);
        this.m_jKeyDot.setFocusPainted(false);
        this.m_jKeyDot.setFocusable(false);
        gridBagConstraints = this.newConstraints(2, 4, 1, 1);
        this.add((Component)this.m_jKeyDot, gridBagConstraints);
        this.m_jEquals.setText("=");
        this.m_jEquals.setFont(fontKeys);
        this.m_jEquals.setForeground(colorText);
        this.m_jEquals.setBackground(new Color(220, 230, 240));
        this.m_jEquals.setBorder(borderKey);
        this.m_jEquals.setFocusPainted(false);
        this.m_jEquals.setFocusable(false);
        gridBagConstraints = this.newConstraints(3, 3, 1, 2);
        this.add((Component)this.m_jEquals, gridBagConstraints);
        ((GridBagLayout)this.getLayout()).setConstraints(this.m_jPlus, this.newConstraints(3, 1, 1, 2));
    }

    private GridBagConstraints newConstraints(int x, int y, int w, int h) {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = x;
        c.gridy = y;
        c.gridwidth = w;
        c.gridheight = h;
        c.fill = 1;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.insets = new Insets(1, 1, 1, 1);
        return c;
    }

    private class MyKeyNumberListener
    implements ActionListener {
        private final char m_cCad;

        public MyKeyNumberListener(char cCad) {
            this.m_cCad = cCad;
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            JNumberEvent oEv = new JNumberEvent(JNumberKeys.this, this.m_cCad);
            for (JNumberEventListener oListener : JNumberKeys.this.m_Listeners) {
                oListener.keyPerformed(oEv);
            }
        }
    }
}

