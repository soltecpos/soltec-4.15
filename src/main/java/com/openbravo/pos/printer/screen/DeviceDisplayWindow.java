/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.printer.screen;

import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.printer.DeviceDisplay;
import com.openbravo.pos.printer.screen.DeviceDisplayPanel;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class DeviceDisplayWindow
extends JFrame
implements DeviceDisplay {
    private String m_sName;
    private DeviceDisplayPanel m_display;
    private JPanel m_jContainer;

    public DeviceDisplayWindow() {
        this.initComponents();
        this.m_sName = AppLocal.getIntString("display.window");
        this.m_display = new DeviceDisplayPanel(3.0);
        this.m_jContainer.add(this.m_display.getDisplayComponent());
        this.setVisible(true);
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
        return null;
    }

    @Override
    public void writeVisor(int animation, String sLine1, String sLine2) {
        this.m_display.writeVisor(animation, sLine1, sLine2);
    }

    @Override
    public void writeVisor(String sLine1, String sLine2) {
        this.m_display.writeVisor(sLine1, sLine2);
    }

    @Override
    public void clearVisor() {
        this.m_display.clearVisor();
    }

    private void initComponents() {
        this.m_jContainer = new JPanel();
        this.setTitle(AppLocal.getIntString("display.window"));
        this.m_jContainer.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        this.m_jContainer.setFont(new Font("Arial", 0, 11));
        this.m_jContainer.setLayout(new BorderLayout());
        this.getContentPane().add((Component)this.m_jContainer, "Center");
        this.setSize(new Dimension(767, 245));
    }
}

