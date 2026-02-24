/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.gui;

import com.openbravo.data.user.BrowsableEditableData;
import com.openbravo.data.user.BrowseListener;
import com.openbravo.data.user.StateListener;
import com.openbravo.format.Formats;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class JCounter
extends JPanel
implements BrowseListener,
StateListener {
    private JLabel jLabel2;
    private JLabel jlblCounter;
    private JLabel jlblIndex;

    public JCounter(BrowsableEditableData bd) {
        this.initComponents();
        bd.addBrowseListener(this);
        bd.addStateListener(this);
    }

    @Override
    public void updateState(int iState) {
        if (iState == 3) {
            this.jlblIndex.setText("*");
        }
    }

    @Override
    public void updateIndex(int iIndex, int iCounter) {
        if (iIndex >= 0 && iIndex < iCounter) {
            this.jlblIndex.setText(Formats.INT.formatValue(iIndex + 1));
        } else {
            this.jlblIndex.setText("-");
        }
        this.jlblCounter.setText(Formats.INT.formatValue(iCounter));
    }

    private void initComponents() {
        this.jlblIndex = new JLabel();
        this.jLabel2 = new JLabel();
        this.jlblCounter = new JLabel();
        this.setFont(new Font("Arial", 0, 12));
        this.setMaximumSize(new Dimension(80, 20));
        this.setMinimumSize(new Dimension(80, 20));
        this.setPreferredSize(new Dimension(80, 20));
        this.jlblIndex.setFont(new Font("Arial", 0, 14));
        this.jlblIndex.setText("XX");
        this.add(this.jlblIndex);
        this.jLabel2.setFont(new Font("Arial", 0, 14));
        this.jLabel2.setText("/");
        this.add(this.jLabel2);
        this.jlblCounter.setFont(new Font("Arial", 0, 14));
        this.jlblCounter.setText("XX");
        this.add(this.jlblCounter);
    }
}

