/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.sales.restaurant;

import com.openbravo.format.Formats;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.io.Serializable;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class JCalendarItemRenderer
extends JPanel
implements ListCellRenderer,
Serializable {
    protected static Border noFocusBorder;
    private boolean m_bDone = false;
    JPanel jPanel1;
    JLabel m_jChairs;
    JLabel m_jDescription;
    JLabel m_jTime;
    JLabel m_jTitle;

    public JCalendarItemRenderer() {
        if (noFocusBorder == null) {
            noFocusBorder = new EmptyBorder(1, 1, 1, 1);
        }
        this.initComponents();
        this.m_jTime.setFont(new Font("SansSerif", 1, 14));
        this.m_jTitle.setFont(new Font("SansSerif", 1, 14));
        this.m_jDescription.setFont(new Font("SansSerif", 2, 14));
        this.setOpaque(true);
        this.setBorder(noFocusBorder);
    }

    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        this.applyComponentOrientation(list.getComponentOrientation());
        if (isSelected) {
            this.setBackground(list.getSelectionBackground());
            this.m_jTime.setForeground(list.getSelectionForeground());
            this.m_jTitle.setForeground(list.getSelectionForeground());
            this.m_jDescription.setForeground(list.getSelectionForeground());
        } else {
            this.setBackground(list.getBackground());
            this.m_jTime.setForeground(Color.BLUE);
            this.m_jTitle.setForeground(list.getForeground());
            this.m_jDescription.setForeground(list.getForeground());
        }
        if (value == null) {
            this.m_jTime.setText("");
            this.m_jTitle.setText("");
            this.m_jChairs.setText("");
            this.m_bDone = false;
            this.m_jDescription.setText("");
        } else {
            Object[] avalue = (Object[])value;
            this.m_jTime.setText(Formats.TIME.formatValue(avalue[2]));
            this.m_jTitle.setText(Formats.STRING.formatValue(avalue[6]));
            this.m_jChairs.setText(Formats.INT.formatValue(avalue[7]));
            this.m_bDone = (Boolean)avalue[8];
            this.m_jDescription.setText(Formats.STRING.formatValue(avalue[9]));
        }
        this.setEnabled(list.isEnabled());
        this.setFont(list.getFont());
        this.setBorder(cellHasFocus ? UIManager.getBorder("List.focusCellHighlightBorder") : noFocusBorder);
        return this;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (this.m_bDone) {
            Insets in = this.getInsets();
            g.drawLine(in.left, 10, this.getWidth() - in.right, 10);
        }
    }

    @Override
    protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
    }

    @Override
    public void firePropertyChange(String propertyName, byte oldValue, byte newValue) {
    }

    @Override
    public void firePropertyChange(String propertyName, char oldValue, char newValue) {
    }

    @Override
    public void firePropertyChange(String propertyName, short oldValue, short newValue) {
    }

    @Override
    public void firePropertyChange(String propertyName, int oldValue, int newValue) {
    }

    @Override
    public void firePropertyChange(String propertyName, long oldValue, long newValue) {
    }

    @Override
    public void firePropertyChange(String propertyName, float oldValue, float newValue) {
    }

    @Override
    public void firePropertyChange(String propertyName, double oldValue, double newValue) {
    }

    @Override
    public void firePropertyChange(String propertyName, boolean oldValue, boolean newValue) {
    }

    private void initComponents() {
        this.m_jDescription = new JLabel();
        this.jPanel1 = new JPanel();
        this.m_jTime = new JLabel();
        this.m_jTitle = new JLabel();
        this.m_jChairs = new JLabel();
        this.setLayout(new BorderLayout());
        this.m_jDescription.setFont(new Font("Arial", 0, 12));
        this.m_jDescription.setText("<html>This is a test comment that shows how a long line is printed with this renderer.");
        this.m_jDescription.setVerticalAlignment(1);
        this.m_jDescription.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));
        this.add((Component)this.m_jDescription, "Center");
        this.jPanel1.setOpaque(false);
        this.jPanel1.setLayout(new BorderLayout());
        this.m_jTime.setFont(new Font("Arial", 0, 12));
        this.m_jTime.setForeground(new Color(0, 0, 255));
        this.m_jTime.setText("10:20");
        this.jPanel1.add((Component)this.m_jTime, "West");
        this.m_jTitle.setFont(new Font("Arial", 0, 12));
        this.m_jTitle.setText(" This is a test");
        this.m_jTitle.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
        this.jPanel1.add((Component)this.m_jTitle, "Center");
        this.m_jChairs.setText("5");
        this.jPanel1.add((Component)this.m_jChairs, "East");
        this.add((Component)this.jPanel1, "North");
    }
}

