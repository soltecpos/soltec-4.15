/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.printer.screen;

import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.printer.DevicePrinter;
import com.openbravo.pos.printer.screen.JTicket;
import com.openbravo.pos.printer.screen.JTicketContainer;
import com.openbravo.pos.printer.ticket.BasicTicket;
import com.openbravo.pos.printer.ticket.BasicTicketForScreen;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class DevicePrinterPanel
extends JPanel
implements DevicePrinter {
    private final String m_sName;
    private final JTicketContainer m_jTicketContainer;
    private BasicTicket m_ticketcurrent;
    private JScrollPane m_jScrollView;

    public DevicePrinterPanel() {
        this.initComponents();
        this.m_sName = AppLocal.getIntString("printer.screen");
        this.m_ticketcurrent = null;
        this.m_jTicketContainer = new JTicketContainer();
        this.m_jScrollView.setViewportView(this.m_jTicketContainer);
    }

    @Override
    public String getPrinterName() {
        return this.m_sName;
    }

    @Override
    public void printLogo() {
    }

    @Override
    public String getPrinterDescription() {
        return null;
    }

    @Override
    public JComponent getPrinterComponent() {
        return this;
    }

    @Override
    public void reset() {
        this.m_ticketcurrent = null;
        this.m_jTicketContainer.removeAllTickets();
        this.m_jTicketContainer.repaint();
    }

    @Override
    public void beginReceipt() {
        this.m_ticketcurrent = new BasicTicketForScreen();
    }

    @Override
    public void printImage(BufferedImage image) {
        this.m_ticketcurrent.printImage(image);
    }

    @Override
    public void printBarCode(String type, String position, String code) {
        this.m_ticketcurrent.printBarCode(type, position, code);
    }

    @Override
    public void beginLine(int iTextSize) {
        this.m_ticketcurrent.beginLine(iTextSize);
    }

    @Override
    public void printText(int iStyle, String sText) {
        this.m_ticketcurrent.printText(iStyle, sText);
    }

    @Override
    public void endLine() {
        this.m_ticketcurrent.endLine();
    }

    @Override
    public void endReceipt() {
        this.m_jTicketContainer.addTicket(new JTicket(this.m_ticketcurrent));
        this.m_ticketcurrent = null;
    }

    @Override
    public void openDrawer() {
        Toolkit.getDefaultToolkit().beep();
    }

    private void initComponents() {
        this.m_jScrollView = new JScrollPane();
        this.setLayout(new BorderLayout());
        this.m_jScrollView.setFont(new Font("Arial", 0, 12));
        this.add((Component)this.m_jScrollView, "Center");
    }
}

