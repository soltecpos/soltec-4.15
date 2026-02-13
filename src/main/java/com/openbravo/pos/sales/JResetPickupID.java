/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.sales;

import com.openbravo.basic.BasicException;
import com.openbravo.pos.forms.AppConfig;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.AppProperties;
import com.openbravo.pos.forms.JRootFrame;
import com.openbravo.pos.sales.JPanelResetPickupId;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class JResetPickupID
extends JFrame {
    private JPanelResetPickupId config;

    public JResetPickupID(AppProperties props) {
        this.initComponents();
        try {
            this.setIconImage(ImageIO.read(JRootFrame.class.getResourceAsStream("/com/openbravo/images/favicon.png")));
        }
        catch (IOException iOException) {
            // empty catch block
        }
        this.setTitle("SOLTEC POS - 4.15 - " + AppLocal.getIntString("Menu.Resetpickup"));
        this.addWindowListener(new MyFrameListener());
        this.config = new JPanelResetPickupId(props);
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
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setBounds((screenSize.width - 507) / 2, (screenSize.height - 304) / 2, 507, 304);
    }

    public static void main(final String[] args) {
        EventQueue.invokeLater(new Runnable(){

            @Override
            public void run() {
                AppConfig config = new AppConfig(args);
                config.load();
                try {
                    Object laf = Class.forName(config.getProperty("swing.defaultlaf")).newInstance();
                    if (laf instanceof LookAndFeel) {
                        UIManager.setLookAndFeel((LookAndFeel)laf);
                    }
                }
                catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException laf) {
                    // empty catch block
                }
                JResetPickupID resetFrame = new JResetPickupID(config);
                resetFrame.setSize(360, 120);
                resetFrame.setVisible(true);
            }
        });
    }

    private class MyFrameListener
    extends WindowAdapter {
        private MyFrameListener() {
        }

        @Override
        public void windowClosing(WindowEvent evt) {
            if (JResetPickupID.this.config.deactivate()) {
                JResetPickupID.this.dispose();
            }
        }

        @Override
        public void windowClosed(WindowEvent evt) {
            System.exit(0);
        }
    }
}

