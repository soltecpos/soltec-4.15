/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jdesktop.swingx.VerticalLayout
 */
package com.openbravo.pos.catalog;

import com.openbravo.beans.JFlowPanel;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import org.jdesktop.swingx.VerticalLayout;

public class JProductsSelector
extends JPanel {
    private final JFlowPanel flowpanel;

    public JProductsSelector() {
        this.initComponents();
        this.flowpanel = new JFlowPanel();
        this.add((Component)this.flowpanel, "Center");
    }

    public void addProduct(Image img, String display, ActionListener al, String textTip) {
        JButton btn = new JButton();
        btn.applyComponentOrientation(this.getComponentOrientation());
        btn.setText(display);
        btn.setFocusPainted(false);
        if (textTip != null) {
            btn.setToolTipText(textTip);
        }
        btn.setFocusable(false);
        btn.setRequestFocusEnabled(false);
        btn.setHorizontalTextPosition(0);
        btn.setVerticalTextPosition(1);
        btn.setMargin(new Insets(0, 0, 0, 0));
        btn.setMaximumSize(new Dimension(80, 70));
        btn.setMinimumSize(new Dimension(40, 30));
        btn.addActionListener(al);
        this.flowpanel.add(btn);
    }

    private void initComponents() {
        this.setFont(new Font("Arial", 0, 12));
        this.setLayout((LayoutManager)new VerticalLayout());
    }
}

