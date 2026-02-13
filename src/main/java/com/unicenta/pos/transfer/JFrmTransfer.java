/*
 * Decompiled with CFR 0.152.
 */
package com.unicenta.pos.transfer;

import com.openbravo.basic.BasicException;
import com.openbravo.pos.forms.AppConfig;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.AppProperties;
import com.openbravo.pos.forms.JRootFrame;
import com.unicenta.pos.transfer.JPanelTransfer;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class JFrmTransfer
extends JFrame {
    private JPanelTransfer config;

    public JFrmTransfer(AppProperties props) {
        this.initComponents();
        try {
            this.setIconImage(ImageIO.read(JRootFrame.class.getResourceAsStream("/com/openbravo/images/favicon.png")));
        }
        catch (IOException iOException) {
            // empty catch block
        }
        this.setTitle("SOLTEC POS - 4.15 - " + AppLocal.getIntString("Menu.Configuration"));
        this.addWindowListener(new MyFrameListener());
        this.config = new JPanelTransfer(props);
        this.getContentPane().add((Component)this.config, "Center");
        try {
            this.config.activate();
        }
        catch (BasicException basicException) {
            // empty catch block
        }
    }

    private void initComponents() {
        this.setDefaultCloseOperation(0);
        this.setSize(new Dimension(702, 325));
        this.setLocationRelativeTo(null);
    }

    public static void main(final String[] args) {
        EventQueue.invokeLater(new Runnable(){

            @Override
            public void run() {
                AppConfig config = new AppConfig(args);
                config.load();
                new JFrmTransfer(config).setVisible(true);
            }
        });
    }

    private class MyFrameListener
    extends WindowAdapter {
        private MyFrameListener() {
        }

        @Override
        public void windowClosing(WindowEvent evt) {
            if (JFrmTransfer.this.config.deactivate()) {
                JFrmTransfer.this.dispose();
            }
        }

        @Override
        public void windowClosed(WindowEvent evt) {
            System.exit(0);
        }
    }
}

