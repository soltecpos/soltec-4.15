/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.forms;

import com.openbravo.pos.config.JFrmConfig;
import com.openbravo.pos.forms.AppProperties;
import com.openbravo.pos.forms.JRootApp;
import com.openbravo.pos.instance.AppMessage;
import com.openbravo.pos.instance.InstanceManager;
import com.openbravo.pos.util.OSValidator;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import javax.swing.JFrame;

public class JRootKiosk
extends JFrame
implements AppMessage {
    private InstanceManager m_instmanager = null;
    private JRootApp m_rootapp;
    private AppProperties m_props;
    private OSValidator m_OS;

    public JRootKiosk() {
        this.setUndecorated(true);
        this.setResizable(false);
        this.initComponents();
    }

    public void initFrame(AppProperties props) throws IOException {
        this.m_OS = new OSValidator();
        this.m_props = props;
        this.m_rootapp = new JRootApp();
        if (this.m_rootapp.initApp(this.m_props)) {
            if ("true".equals(props.getProperty("machine.uniqueinstance"))) {
                try {
                    this.m_instmanager = new InstanceManager(this);
                }
                catch (AlreadyBoundException | RemoteException exception) {
                    // empty catch block
                }
            }
            this.add((Component)this.m_rootapp, "Center");
            this.setTitle("SOLTEC POS - 4.15");
            Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
            this.setBounds(0, 0, d.width, d.height);
            this.setVisible(true);
        } else {
            new JFrmConfig(props).setVisible(true);
        }
    }

    @Override
    public void restoreWindow() throws RemoteException {
        EventQueue.invokeLater(new Runnable(){

            @Override
            public void run() {
                if (JRootKiosk.this.getExtendedState() == 1) {
                    JRootKiosk.this.setExtendedState(0);
                }
                JRootKiosk.this.requestFocus();
            }
        });
    }

    private void initComponents() {
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter(){

            @Override
            public void windowClosed(WindowEvent evt) {
                JRootKiosk.this.formWindowClosed(evt);
            }

            @Override
            public void windowClosing(WindowEvent evt) {
                JRootKiosk.this.formWindowClosing(evt);
            }
        });
    }

    private void formWindowClosing(WindowEvent evt) {
        this.m_rootapp.tryToClose();
    }

    private void formWindowClosed(WindowEvent evt) {
        this.m_rootapp.releaseResources();
        System.exit(0);
    }
}

