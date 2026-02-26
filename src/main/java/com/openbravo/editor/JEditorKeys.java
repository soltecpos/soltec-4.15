/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.editor;

import com.openbravo.editor.EditorComponent;
import com.openbravo.editor.EditorKeys;
import com.openbravo.pos.customers.JCustomerFinder;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.EventListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.EventListenerList;

public class JEditorKeys
extends JPanel
implements EditorKeys {
    private static final long serialVersionUID = 1L;
    private static final char[] SET_DOUBLE = new char[]{'\u007f', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.', '-'};
    private static final char[] SET_DOUBLE_POSITIVE = new char[]{'\u007f', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.'};
    private static final char[] SET_INTEGER = new char[]{'\u007f', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-'};
    private static final char[] SET_INTEGER_POSITIVE = new char[]{'\u007f', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    protected EventListenerList listeners = new EventListenerList();
    private EditorComponent editorcurrent;
    private char[] keysavailable;
    private boolean m_bMinus;
    private boolean m_bKeyDot;
    private JCustomerFinder customerFinder;
    JButton m_jCE;
    JButton m_jKey0;
    JButton m_jKey1;
    JButton m_jKey2;
    JButton m_jKey3;
    JButton m_jKey4;
    JButton m_jKey5;
    JButton m_jKey6;
    JButton m_jKey7;
    JButton m_jKey8;
    JButton m_jKey9;
    JButton m_jKeyDot;
    JButton m_jMinus;
    JTextField m_txtKeys;

    public JCustomerFinder getCustomerFinder() {
        return this.customerFinder;
    }

    public void setCustomerFinder(JCustomerFinder customerFinder) {
        this.customerFinder = customerFinder;
    }

    public JEditorKeys() {
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
        this.m_jCE.addActionListener(new MyKeyNumberListener('\u007f'));
        this.m_jMinus.addActionListener(new MyKeyNumberListener('-'));
        this.editorcurrent = null;
        this.setMode(0);
        this.doEnabled(false);
    }

    @Override
    public void setComponentOrientation(ComponentOrientation o) {
    }

    public void addActionListener(ActionListener l) {
        this.listeners.add(ActionListener.class, l);
    }

    public void removeActionListener(ActionListener l) {
        this.listeners.remove(ActionListener.class, l);
    }

    protected void fireActionPerformed() {
        EventListener[] l = this.listeners.getListeners(ActionListener.class);
        ActionEvent e = null;
        for (int i = 0; i < l.length; ++i) {
            if (e == null) {
                e = new ActionEvent(this, 1001, null);
            }
            ((ActionListener)l[i]).actionPerformed(e);
        }
    }

    private void doEnabled(boolean b) {
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
        this.m_jKeyDot.setEnabled(b && this.m_bKeyDot);
        this.m_jCE.setEnabled(b);
        this.m_jMinus.setEnabled(b && this.m_bMinus);
    }

    public void setMode(int iMode) {
        switch (iMode) {
            case 1: {
                this.m_bMinus = true;
                this.m_bKeyDot = true;
                this.keysavailable = SET_DOUBLE;
                break;
            }
            case 2: {
                this.m_bMinus = false;
                this.m_bKeyDot = true;
                this.keysavailable = SET_DOUBLE_POSITIVE;
                break;
            }
            case 3: {
                this.m_bMinus = true;
                this.m_bKeyDot = false;
                this.keysavailable = SET_INTEGER;
                break;
            }
            case 4: {
                this.m_bMinus = false;
                this.m_bKeyDot = false;
                this.keysavailable = SET_INTEGER_POSITIVE;
                break;
            }
            default: {
                this.m_bMinus = true;
                this.m_bKeyDot = true;
                this.keysavailable = null;
            }
        }
    }

    @Override
    public void setActive(EditorComponent e, int iMode) {
        if (this.editorcurrent != null) {
            this.editorcurrent.deactivate();
        }
        this.editorcurrent = e;
        this.setMode(iMode);
        this.doEnabled(true);
        this.m_txtKeys.setText(null);
        EventQueue.invokeLater(new Runnable(){

            @Override
            public void run() {
                JEditorKeys.this.m_txtKeys.requestFocus();
            }
        });
    }

    @Override
    public void setInactive(EditorComponent e) {
        if (e == this.editorcurrent && this.editorcurrent != null) {
            this.editorcurrent.deactivate();
            this.editorcurrent = null;
            this.setMode(0);
            this.doEnabled(false);
        }
    }

    public void dotIs00(boolean enabled) {
        if (enabled) {
            this.m_jKeyDot.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/btn00.png")));
        }
    }

    private void initComponents() {
        this.m_jCE = new JButton();
        this.m_jMinus = new JButton();
        this.m_jKey1 = new JButton();
        this.m_jKey2 = new JButton();
        this.m_jKey3 = new JButton();
        this.m_jKey4 = new JButton();
        this.m_jKey5 = new JButton();
        this.m_jKey6 = new JButton();
        this.m_jKey7 = new JButton();
        this.m_jKey8 = new JButton();
        this.m_jKey9 = new JButton();
        this.m_jKey0 = new JButton();
        this.m_jKeyDot = new JButton();
        this.m_txtKeys = new JTextField();
        this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        this.setPreferredSize(new Dimension(300, 300));
        this.setLayout(new GridBagLayout());
        this.m_jCE.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/btnce.png")));
        this.m_jCE.setFocusPainted(false);
        this.m_jCE.setFocusable(false);
        this.m_jCE.setMargin(new Insets(8, 16, 8, 16));
        this.m_jCE.setRequestFocusEnabled(false);
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = 1;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new Insets(0, 0, 5, 0);
        this.add((Component)this.m_jCE, gridBagConstraints);
        this.m_jMinus.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/btnminus.png")));
        this.m_jMinus.setFocusPainted(false);
        this.m_jMinus.setFocusable(false);
        this.m_jMinus.setMargin(new Insets(8, 16, 8, 16));
        this.m_jMinus.setRequestFocusEnabled(false);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = 1;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new Insets(0, 0, 5, 0);
        this.add((Component)this.m_jMinus, gridBagConstraints);
        this.m_jKey1.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/btn1.png")));
        this.m_jKey1.setFocusPainted(false);
        this.m_jKey1.setFocusable(false);
        this.m_jKey1.setMargin(new Insets(8, 16, 8, 16));
        this.m_jKey1.setRequestFocusEnabled(false);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = 1;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        this.add((Component)this.m_jKey1, gridBagConstraints);
        this.m_jKey2.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/btn2a.png")));
        this.m_jKey2.setFocusPainted(false);
        this.m_jKey2.setFocusable(false);
        this.m_jKey2.setMargin(new Insets(8, 16, 8, 16));
        this.m_jKey2.setRequestFocusEnabled(false);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = 1;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        this.add((Component)this.m_jKey2, gridBagConstraints);
        this.m_jKey3.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/btn3a.png")));
        this.m_jKey3.setFocusPainted(false);
        this.m_jKey3.setFocusable(false);
        this.m_jKey3.setMargin(new Insets(8, 16, 8, 16));
        this.m_jKey3.setRequestFocusEnabled(false);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = 1;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        this.add((Component)this.m_jKey3, gridBagConstraints);
        this.m_jKey4.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/btn4a.png")));
        this.m_jKey4.setFocusPainted(false);
        this.m_jKey4.setFocusable(false);
        this.m_jKey4.setMargin(new Insets(8, 16, 8, 16));
        this.m_jKey4.setRequestFocusEnabled(false);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = 1;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        this.add((Component)this.m_jKey4, gridBagConstraints);
        this.m_jKey5.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/btn5a.png")));
        this.m_jKey5.setFocusPainted(false);
        this.m_jKey5.setFocusable(false);
        this.m_jKey5.setMargin(new Insets(8, 16, 8, 16));
        this.m_jKey5.setRequestFocusEnabled(false);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = 1;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        this.add((Component)this.m_jKey5, gridBagConstraints);
        this.m_jKey6.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/btn6a.png")));
        this.m_jKey6.setFocusPainted(false);
        this.m_jKey6.setFocusable(false);
        this.m_jKey6.setMargin(new Insets(8, 16, 8, 16));
        this.m_jKey6.setRequestFocusEnabled(false);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = 1;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        this.add((Component)this.m_jKey6, gridBagConstraints);
        this.m_jKey7.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/btn7a.png")));
        this.m_jKey7.setFocusPainted(false);
        this.m_jKey7.setFocusable(false);
        this.m_jKey7.setMargin(new Insets(8, 16, 8, 16));
        this.m_jKey7.setRequestFocusEnabled(false);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = 1;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        this.add((Component)this.m_jKey7, gridBagConstraints);
        this.m_jKey8.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/btn8a.png")));
        this.m_jKey8.setFocusPainted(false);
        this.m_jKey8.setFocusable(false);
        this.m_jKey8.setMargin(new Insets(8, 16, 8, 16));
        this.m_jKey8.setRequestFocusEnabled(false);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = 1;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        this.add((Component)this.m_jKey8, gridBagConstraints);
        this.m_jKey9.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/btn9a.png")));
        this.m_jKey9.setFocusPainted(false);
        this.m_jKey9.setFocusable(false);
        this.m_jKey9.setMargin(new Insets(8, 16, 8, 16));
        this.m_jKey9.setRequestFocusEnabled(false);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = 1;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        this.add((Component)this.m_jKey9, gridBagConstraints);
        this.m_jKey0.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/btn0.png")));
        this.m_jKey0.setFocusPainted(false);
        this.m_jKey0.setFocusable(false);
        this.m_jKey0.setMargin(new Insets(8, 16, 8, 16));
        this.m_jKey0.setRequestFocusEnabled(false);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = 1;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new Insets(5, 0, 0, 0);
        this.add((Component)this.m_jKey0, gridBagConstraints);
        this.m_jKeyDot.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/btndot.png")));
        this.m_jKeyDot.setFocusPainted(false);
        this.m_jKeyDot.setFocusable(false);
        this.m_jKeyDot.setMargin(new Insets(8, 16, 8, 16));
        this.m_jKeyDot.setRequestFocusEnabled(false);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = 1;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new Insets(5, 0, 0, 0);
        this.add((Component)this.m_jKeyDot, gridBagConstraints);
        this.m_txtKeys.setPreferredSize(new Dimension(0, 0));
        this.m_txtKeys.addFocusListener(new FocusAdapter(){

            @Override
            public void focusLost(FocusEvent evt) {
                JEditorKeys.this.m_txtKeysFocusLost(evt);
            }
        });
        this.m_txtKeys.addKeyListener(new KeyAdapter(){

            @Override
            public void keyTyped(KeyEvent evt) {
                JEditorKeys.this.m_txtKeysKeyTyped(evt);
            }
        });
    }

    private void m_txtKeysKeyTyped(KeyEvent evt) {
        char c = evt.getKeyChar();
        // Always fire action on Enter, even if no editor is active (for password confirm)
        if (c == '\n') {
            this.fireActionPerformed();
            return;
        }
        if (this.editorcurrent != null) {
            this.m_txtKeys.setText("0");
            if (this.keysavailable == null) {
                this.editorcurrent.typeChar(c);
            } else {
                for (int i = 0; i < this.keysavailable.length; ++i) {
                    if (c != this.keysavailable[i]) continue;
                    this.editorcurrent.typeChar(c);
                }
            }
        }
    }

    private void m_txtKeysFocusLost(FocusEvent evt) {
        this.setInactive(this.editorcurrent);
    }

    private class MyKeyNumberListener
    implements ActionListener {
        private char m_cCad;

        public MyKeyNumberListener(char cCad) {
            this.m_cCad = cCad;
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            if (JEditorKeys.this.editorcurrent != null) {
                JEditorKeys.this.editorcurrent.transChar(this.m_cCad);
            }
        }
    }
}

