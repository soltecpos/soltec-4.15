/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.forms;

import com.openbravo.basic.BasicException;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.forms.JPanelView;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class JPanelNull
extends JPanel
implements JPanelView {
    private JScrollPane jscrException;
    private JTextArea jtxtException;
    private JLabel m_jLabelError;

    public JPanelNull(AppView oApp, Object o) {
        this.initComponents();
        if (o instanceof Exception) {
            // empty if block
        }
        this.jtxtException.setText(o.toString());
    }

    @Override
    public JComponent getComponent() {
        return this;
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public void activate() throws BasicException {
    }

    @Override
    public boolean deactivate() {
        return true;
    }

    private void initComponents() {
        this.m_jLabelError = new JLabel();
        this.jscrException = new JScrollPane();
        this.jtxtException = new JTextArea();
        this.setLayout(null);
        this.m_jLabelError.setFont(new Font("Arial", 0, 14));
        this.m_jLabelError.setText(AppLocal.getIntString("label.LoadError"));
        this.m_jLabelError.setPreferredSize(new Dimension(110, 30));
        this.add(this.m_jLabelError);
        this.m_jLabelError.setBounds(30, 30, 490, 30);
        this.jtxtException.setEditable(false);
        this.jtxtException.setFont(new Font("Arial", 0, 14));
        this.jtxtException.setLineWrap(true);
        this.jtxtException.setWrapStyleWord(true);
        this.jscrException.setViewportView(this.jtxtException);
        this.add(this.jscrException);
        this.jscrException.setBounds(30, 70, 550, 180);
    }
}

