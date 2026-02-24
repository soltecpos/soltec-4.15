/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.editor;

import com.openbravo.basic.BasicException;
import com.openbravo.editor.EditorComponent;
import com.openbravo.editor.EditorKeys;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public abstract class JEditorAbstract
extends JPanel
implements EditorComponent {
    private static final long serialVersionUID = 1L;
    private EditorKeys editorkeys;
    private boolean m_bActive;
    private final Border m_borderactive = new CompoundBorder(new LineBorder(UIManager.getDefaults().getColor("TextField.selectionBackground")), new EmptyBorder(new Insets(1, 4, 1, 4)));
    private final Border m_borderinactive = new CompoundBorder(new LineBorder(UIManager.getDefaults().getColor("Button.darkShadow")), new EmptyBorder(new Insets(1, 4, 1, 4)));
    private JLabel m_jMode;
    private JButton m_jText;
    private JPanel panBackground;

    public JEditorAbstract() {
        this.initComponents();
        this.editorkeys = null;
        this.m_bActive = false;
        this.m_jText.setBorder(this.m_borderinactive);
    }

    protected abstract int getMode();

    protected abstract int getAlignment();

    protected abstract String getEditMode();

    protected abstract String getTextEdit();

    protected abstract String getTextFormat() throws BasicException;

    protected abstract void typeCharInternal(char var1);

    protected abstract void transCharInternal(char var1);

    @Override
    public void typeChar(char c) {
        this.typeCharInternal(c);
        this.reprintText();
        this.firePropertyChange("Edition", null, null);
    }

    @Override
    public void transChar(char c) {
        this.transCharInternal(c);
        this.reprintText();
        this.firePropertyChange("Edition", null, null);
    }

    @Override
    public void addEditorKeys(EditorKeys ed) {
        this.editorkeys = ed;
    }

    @Override
    public void deactivate() {
        this.setActive(false);
    }

    @Override
    public Component getComponent() {
        return this;
    }

    public void activate() {
        if (this.isEnabled()) {
            this.editorkeys.setActive(this, this.getMode());
            this.setActive(true);
        }
    }

    private void setActive(boolean bValue) {
        this.m_bActive = bValue;
        this.m_jText.setBorder(this.m_bActive ? this.m_borderactive : this.m_borderinactive);
        this.reprintText();
    }

    protected void reprintText() {
        this.m_jText.setHorizontalAlignment(this.getAlignment());
        if (this.m_bActive) {
            this.m_jMode.setText(this.getEditMode());
            this.m_jText.setText(this.getTextEdit());
            this.m_jText.setForeground(UIManager.getDefaults().getColor("label.foreground"));
        } else {
            this.m_jMode.setText(null);
            try {
                this.m_jText.setText(this.getTextFormat());
                this.m_jText.setForeground(UIManager.getDefaults().getColor("label.foreground"));
            }
            catch (BasicException e) {
                this.m_jText.setText(this.getTextEdit());
                this.m_jText.setForeground(Color.RED);
            }
        }
    }

    @Override
    public void setEnabled(boolean b) {
        if (this.editorkeys != null) {
            this.editorkeys.setInactive(this);
        }
        this.panBackground.setBackground(b ? UIManager.getDefaults().getColor("TextField.background") : UIManager.getDefaults().getColor("TextField.disabledBackground"));
        super.setEnabled(b);
    }

    private void initComponents() {
        this.panBackground = new JPanel();
        this.m_jText = new JButton();
        this.m_jMode = new JLabel();
        this.setLayout(new BorderLayout());
        this.panBackground.setBackground(UIManager.getDefaults().getColor("TextField.background"));
        this.panBackground.setLayout(new BorderLayout());
        this.m_jText.setBackground(UIManager.getDefaults().getColor("TextField.background"));
        this.m_jText.setFont(new Font("Arial", 0, 12));
        this.m_jText.setContentAreaFilled(false);
        this.m_jText.setFocusPainted(false);
        this.m_jText.setFocusable(true);
        this.m_jText.setMinimumSize(new Dimension(100, 25));
        this.m_jText.setPreferredSize(new Dimension(100, 25));
        this.m_jText.setRequestFocusEnabled(true);
        this.m_jText.setVerticalAlignment(1);
        this.m_jText.addKeyListener(new KeyAdapter(){

            @Override
            public void keyTyped(KeyEvent evt) {
                JEditorAbstract.this.typeChar(evt.getKeyChar());
            }
        });
        this.m_jText.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JEditorAbstract.this.m_jTextActionPerformed(evt);
            }
        });
        this.panBackground.add((Component)this.m_jText, "Center");
        this.add((Component)this.panBackground, "Center");
        this.m_jMode.setFont(new Font("Dialog", 0, 9));
        this.m_jMode.setHorizontalAlignment(0);
        this.m_jMode.setVerticalAlignment(1);
        this.m_jMode.setPreferredSize(new Dimension(32, 0));
        this.add((Component)this.m_jMode, "After");
        
        // Evitar que el botón interno consuma la tecla Enter para que el diálogo principal pueda usarla
        this.m_jText.getInputMap(javax.swing.JComponent.WHEN_FOCUSED).put(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ENTER, 0), "none");
    }

    private void m_jTextActionPerformed(ActionEvent evt) {
        this.activate();
    }
}

