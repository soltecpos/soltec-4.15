/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.catalog;

import com.openbravo.beans.JFlowPanel;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionListener;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class JCatalogTab
extends JPanel {
    private JFlowPanel flowpanel;
    private int m_width = 80;
    private int m_height = 70;

    public JCatalogTab() {
        this.initComponents();
        this.flowpanel = new JFlowPanel();
        JScrollPane scroll = new JScrollPane(this.flowpanel);
        scroll.setHorizontalScrollBarPolicy(31);
        scroll.setVerticalScrollBarPolicy(22);
        scroll.getVerticalScrollBar().setPreferredSize(new Dimension(35, 35));
        this.add((Component)scroll, "Center");
    }

    public void setButtonDimesions(int width, int height) {
        this.m_width = width;
        this.m_height = height;
    }

    @Override
    public void setEnabled(boolean value) {
        this.flowpanel.setEnabled(value);
        super.setEnabled(value);
    }

    public void addButton(Icon ico, ActionListener al, String textTip, String label) {
        JButton btn = new JButton();
        btn.applyComponentOrientation(this.getComponentOrientation());
        btn.setIcon(ico);
        btn.setFocusPainted(false);
        btn.setFocusable(false);
        if (label.startsWith("<html>")) {
            btn.setText(label);
        } else {
            btn.setText("<html><center>" + label + "</center></html>");
        }
        btn.setFont(new Font("Arial", 1, 12));
        if (textTip != null) {
            btn.setToolTipText(textTip);
        }
        btn.setRequestFocusEnabled(false);
        btn.setPreferredSize(new Dimension(this.m_width, this.m_height));
        btn.setHorizontalTextPosition(0);
        btn.setVerticalTextPosition(0);
        btn.setMargin(new Insets(0, 0, 0, 0));
        btn.addActionListener(al);
        this.flowpanel.add(btn);
    }

    private void initComponents() {
        this.setFont(new Font("Arial", 0, 12));
        this.setLayout(new BorderLayout());
    }
}

