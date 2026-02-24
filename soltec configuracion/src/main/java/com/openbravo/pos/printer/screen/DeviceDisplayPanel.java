/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.printer.screen;

import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.printer.DeviceDisplay;
import com.openbravo.pos.printer.DeviceDisplayBase;
import com.openbravo.pos.printer.DeviceDisplayImpl;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class DeviceDisplayPanel
extends JPanel
implements DeviceDisplay,
DeviceDisplayImpl {
    private String m_sName;
    private DeviceDisplayBase m_displaylines;
    private JPanel jPanel1;
    private JLabel jline1;
    private JLabel jline2;

    public DeviceDisplayPanel() {
        this(1.0);
    }

    public DeviceDisplayPanel(double dZoom) {
        this.initComponents();
        this.m_sName = AppLocal.getIntString("display.screen");
        this.jline1.setFont(new Font("Monospaced", 1, (int)(16.0 * dZoom)));
        this.jline2.setFont(new Font("Monospaced", 1, (int)(16.0 * dZoom)));
        this.m_displaylines = new DeviceDisplayBase(this);
    }

    @Override
    public String getDisplayName() {
        return this.m_sName;
    }

    @Override
    public String getDisplayDescription() {
        return null;
    }

    @Override
    public JComponent getDisplayComponent() {
        return this;
    }

    @Override
    public void writeVisor(int animation, String sLine1, String sLine2) {
        this.m_displaylines.writeVisor(animation, sLine1, sLine2);
    }

    @Override
    public void writeVisor(String sLine1, String sLine2) {
        this.m_displaylines.writeVisor(sLine1, sLine2);
    }

    @Override
    public void clearVisor() {
        this.m_displaylines.clearVisor();
    }

    @Override
    public void repaintLines() {
        this.jline1.setText(this.m_displaylines.getLine1());
        this.jline2.setText(this.m_displaylines.getLine2());
    }

    @Override
    protected void paintComponent(Graphics g) {
        this.paintBorder(g);
        Graphics2D g2d = (Graphics2D)g;
        Insets i = this.getInsets();
        g2d.setPaint(new GradientPaint(this.getWidth() - i.left - i.right - 50, this.getHeight() - i.top - i.bottom - 50, this.getBackground(), this.getWidth() - i.left - i.right, this.getHeight() - i.top - i.bottom, new Color(0xF0F0F0), true));
        g2d.fillRect(i.left, i.top, this.getWidth() - i.left - i.right, this.getHeight() - i.top - i.bottom);
    }

    private void initComponents() {
        this.jPanel1 = new JPanel();
        this.jline1 = new JLabel();
        this.jline2 = new JLabel();
        this.setBackground(UIManager.getDefaults().getColor("window"));
        this.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        this.setLayout(new BorderLayout());
        this.jPanel1.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        this.jPanel1.setFont(new Font("Arial", 0, 14));
        this.jPanel1.setOpaque(false);
        this.jPanel1.setLayout(new GridBagLayout());
        this.jline1.setFont(new Font("Arial", 0, 14));
        this.jline1.setText("jline1");
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        this.jPanel1.add((Component)this.jline1, gridBagConstraints);
        this.jline2.setFont(new Font("Arial", 0, 14));
        this.jline2.setText("jline2");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        this.jPanel1.add((Component)this.jline2, gridBagConstraints);
        this.add((Component)this.jPanel1, "Center");
    }
}

