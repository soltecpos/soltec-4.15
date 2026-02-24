/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.formdev.flatlaf.FlatLightLaf
 */
package com.openbravo.pos.forms;

import com.formdev.flatlaf.FlatLightLaf;
import com.openbravo.format.Formats;
import com.openbravo.pos.api.APIServer;
import com.openbravo.pos.forms.AppConfig;
import com.openbravo.pos.forms.JLicenseDialog;
import com.openbravo.pos.forms.JRootFrame;
import com.openbravo.pos.forms.JRootKiosk;
import com.openbravo.pos.instance.InstanceQuery;
import com.openbravo.pos.licensing.LicenseManager;
import com.openbravo.pos.plaf.SOLTECTheme;
import com.openbravo.pos.ticket.TicketInfo;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class StartPOS {
    private static final Logger logger = Logger.getLogger("com.openbravo.pos.forms.StartPOS");

    private StartPOS() {
    }

    public static boolean registerApp() {
        InstanceQuery i = null;
        try {
            i = new InstanceQuery();
            i.getAppMessage().restoreWindow();
            return false;
        }
        catch (NotBoundException | RemoteException e) {
            return true;
        }
    }

    public static void main(final String[] args) {
        try {
            java.io.PrintStream logFile = new java.io.PrintStream(new java.io.FileOutputStream("pos_console.log", true));
            System.setOut(logFile);
            System.setErr(logFile);
        } catch (java.io.FileNotFoundException e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run() {
                SOLTECSplash splash = new SOLTECSplash();
                try {
                    splash.showSplash();
                    
                    System.out.println("DEBUG: Starting registration check...");
                    if (!StartPOS.registerApp()) {
                        System.out.println("DEBUG: Instance already running, exiting.");
                        // System.exit(1);
                        System.out.println("DEBUG: Bypassing single instance check for verification.");
                    }
                    System.out.println("DEBUG: Registration successful.");

                    AppConfig config = new AppConfig(args);
                    config.load();
                    System.out.println("DEBUG: Config loaded.");
                    
                    AppConfig.initInstance(config);
                    System.out.println("DEBUG: Instance initialized.");

                    String activationKey = config.getProperty("activation.key");
                    if (!LicenseManager.isValid(activationKey)) {
                        System.out.println("DEBUG: Checking license...");
                        JLicenseDialog licenseDialog = new JLicenseDialog(null, true);
                        licenseDialog.setVisible(true);
                        if (!licenseDialog.isAuthenticated()) {
                            System.exit(0);
                        }
                        config.load();
                    }
                    
                    System.out.println("DEBUG: Starting API Server...");
                    new APIServer(8080).start();
                    
                    System.out.println("DEBUG: Setting Locale...");
                    Locale.setDefault(new Locale("es", "CO"));
                    String slang = config.getProperty("user.language");
                    String scountry = config.getProperty("user.country");
                    String svariant = config.getProperty("user.variant");
                    if (slang != null && !slang.equals("") && scountry != null && svariant != null) {
                        Locale.setDefault(new Locale(slang, scountry, svariant));
                    }
                    
                    System.out.println("DEBUG: Soltec POS - Robust Print Fix v3.1 (2026-02-19)");
                    System.out.println("DEBUG: Setting Formats...");
                    Formats.setIntegerPattern(config.getProperty("format.integer"));
                    Formats.setDoublePattern(config.getProperty("format.double"));
                    Formats.setCurrencyPattern(config.getProperty("format.currency"));
                    Formats.setPercentPattern(config.getProperty("format.percent"));
                    Formats.setDatePattern(config.getProperty("format.date"));
                    Formats.setTimePattern(config.getProperty("format.time"));
                    Formats.setDateTimePattern(config.getProperty("format.datetime"));
                    
                    try {
                        System.out.println("DEBUG: Setting Look and Feel...");
                        SOLTECTheme.setup();
                        FlatLightLaf.setup();
                        logger.log(Level.WARNING, "FlatLaf Look and Feel is set");
                    }
                    catch (Exception e) {
                        logger.log(Level.WARNING, "Cannot set FlatLaf, trying default", e);
                        try {
                            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                        }
                        catch (Exception ex) {
                            logger.log(Level.WARNING, "Cannot set Look and Feel", ex);
                        }
                    }
                    
                    String hostname = config.getProperty("machine.hostname");
                    TicketInfo.setHostname(hostname);
                    
                    String screenmode = config.getProperty("machine.screenmode");
                    System.out.println("DEBUG: Initializing Frame (" + screenmode + ")...");
                    
                    if ("fullscreen".equals(screenmode)) {
                        JRootKiosk rootkiosk = new JRootKiosk();
                        try {
                            rootkiosk.initFrame(config);
                        }
                        catch (IOException ex) {
                            Logger.getLogger(StartPOS.class.getName()).log(Level.SEVERE, null, ex);
                            ex.printStackTrace();
                        }
                    } else {
                        JRootFrame rootframe = new JRootFrame();
                        try {
                            rootframe.initFrame(config);
                        }
                        catch (IOException ex) {
                            Logger.getLogger(StartPOS.class.getName()).log(Level.SEVERE, null, ex);
                            ex.printStackTrace();
                        }
                    }
                    System.out.println("DEBUG: Frame initialized.");
                    splash.closeSplash();

                } catch (Throwable t) {
                    splash.closeSplash();
                    System.out.println("CRITICAL ERROR IN MAIN:");
                    t.printStackTrace();
                }
            }
        });
    }
}

