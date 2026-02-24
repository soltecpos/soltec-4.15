/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.panels;

import com.openbravo.basic.BasicException;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.forms.JPanelView;
import com.openbravo.pos.printer.DeviceFiscalPrinter;
import com.openbravo.pos.printer.DevicePrinter;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class JPanelPrinter
extends JPanel
implements JPanelView {
    private JPanel jPanel1;
    private JPanel m_jDisplay;
    private JTabbedPane m_jPrinters;

    public JPanelPrinter(AppView oApp) {
        this.initComponents();
        if (oApp.getDeviceTicket().getDeviceDisplay().getDisplayComponent() != null) {
            this.m_jDisplay.add(oApp.getDeviceTicket().getDeviceDisplay().getDisplayComponent());
        }
        List<DevicePrinter> aprinters = oApp.getDeviceTicket().getDevicePrinterAll();
        for (int i = 0; i < aprinters.size(); ++i) {
            DevicePrinter printer = aprinters.get(i);
            if (printer.getPrinterComponent() == null) continue;
            this.m_jPrinters.add(printer.getPrinterName(), printer.getPrinterComponent());
        }
        DeviceFiscalPrinter fp = oApp.getDeviceTicket().getFiscalPrinter();
        if (fp.getFiscalComponent() != null) {
            this.m_jPrinters.add(fp.getFiscalName(), fp.getFiscalComponent());
        }
    }

    @Override
    public JComponent getComponent() {
        return this;
    }

    @Override
    public String getTitle() {
        return AppLocal.getIntString("Menu.Printer");
    }

    @Override
    public void activate() throws BasicException {
    }

    @Override
    public boolean deactivate() {
        return true;
    }

    private void initComponents() {
        this.m_jDisplay = new JPanel();
        this.jPanel1 = new JPanel();
        this.m_jPrinters = new JTabbedPane();
        this.setLayout(new BorderLayout());
        this.add((Component)this.m_jDisplay, "North");
        this.jPanel1.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        this.jPanel1.setLayout(new BorderLayout());
        this.m_jPrinters.setFont(new Font("Arial", 0, 12));
        this.jPanel1.add((Component)this.m_jPrinters, "Center");
        this.add((Component)this.jPanel1, "Center");
    }
}

