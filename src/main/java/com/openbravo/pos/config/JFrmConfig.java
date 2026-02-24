/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.config;

import com.openbravo.basic.BasicException;
import com.openbravo.pos.config.JPanelConfiguration;
import com.openbravo.pos.forms.AppConfig;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.AppProperties;
import com.openbravo.pos.forms.JRootFrame;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class JFrmConfig
extends JFrame {
    private final JPanelConfiguration config;

    public JFrmConfig(AppProperties props) {
        this.initComponents();
        try {
            java.io.InputStream imgStream = JRootFrame.class.getResourceAsStream("/com/openbravo/images/iconoprincipal.png");
            if (imgStream != null) {
                this.setIconImage(ImageIO.read(imgStream));
            } else {
                System.err.println("WARNING: Icon resource not found: /com/openbravo/images/iconoprincipal.png");
            }
        }
        catch (IOException iOException) {
            // empty catch block
        }
        this.setTitle("SOLTEC POS - 4.15 - " + AppLocal.getIntString("Menu.Configuration"));
        this.addWindowListener(new MyFrameListener());
        this.config = new JPanelConfiguration(props);
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
        this.setBackground(new Color(255, 255, 255));
        this.setMinimumSize(new Dimension(950, 450));
        this.setPreferredSize(new Dimension(950, 500));
        this.setSize(new Dimension(808, 794));
        this.setLocationRelativeTo(null);
    }

    public static void main(final String[] args) {
        EventQueue.invokeLater(new Runnable(){

            @Override
            public void run() {
                AppConfig config = new AppConfig(args);
                config.load();
                new JFrmConfig(config).setVisible(true);
            }
        });
    }

    private class MyFrameListener
    extends WindowAdapter {
        private MyFrameListener() {
        }

        @Override
        public void windowClosing(WindowEvent evt) {
            if (JFrmConfig.this.config.deactivate()) {
                JFrmConfig.this.dispose();
            }
        }

        @Override
        public void windowClosed(WindowEvent evt) {
            System.exit(0);
        }
    }
}

