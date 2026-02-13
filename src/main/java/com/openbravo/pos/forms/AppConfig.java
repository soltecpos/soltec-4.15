/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.forms;

import com.openbravo.pos.forms.AppProperties;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Locale;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AppConfig
implements AppProperties {
    private static final Logger logger = Logger.getLogger("com.openbravo.pos.forms.AppConfig");
    private static volatile AppConfig m_instance = null;
    private Properties m_propsconfig;
    private File configfile;

    public AppConfig(String[] args) {
        if (args.length == 0) {
            this.init(this.getDefaultConfig());
        } else {
            this.init(new File(args[0]));
        }
    }

    public AppConfig(File configfile) {
        this.init(configfile);
        this.configfile = configfile;
        this.m_propsconfig = new Properties();
        this.load();
        logger.log(Level.INFO, "Reading configuration file: {0}", configfile.getAbsolutePath());
    }

    private void init(File configfile) {
        this.configfile = configfile;
        this.m_propsconfig = new Properties();
        logger.log(Level.INFO, "Reading configuration file: {0}", configfile.getAbsolutePath());
    }

    private File getDefaultConfig() {
        return new File(new File(System.getProperty("user.home")), "soltec.properties");
    }

    @Override
    public String getProperty(String sKey) {
        return this.m_propsconfig.getProperty(sKey);
    }

    @Override
    public String getHost() {
        return this.getProperty("machine.hostname");
    }

    @Override
    public File getConfigFile() {
        return this.configfile;
    }

    public String getTicketHeaderLine1() {
        return this.getProperty("tkt.header1");
    }

    public String getTicketHeaderLine2() {
        return this.getProperty("tkt.header2");
    }

    public String getTicketHeaderLine3() {
        return this.getProperty("tkt.header3");
    }

    public String getTicketHeaderLine4() {
        return this.getProperty("tkt.header4");
    }

    public String getTicketHeaderLine5() {
        return this.getProperty("tkt.header5");
    }

    public String getTicketHeaderLine6() {
        return this.getProperty("tkt.header6");
    }

    public String getTicketFooterLine1() {
        return this.getProperty("tkt.footer1");
    }

    public String getTicketFooterLine2() {
        return this.getProperty("tkt.footer2");
    }

    public String getTicketFooterLine3() {
        return this.getProperty("tkt.footer3");
    }

    public String getTicketFooterLine4() {
        return this.getProperty("tkt.footer4");
    }

    public String getTicketFooterLine5() {
        return this.getProperty("tkt.footer5");
    }

    public String getTicketFooterLine6() {
        return this.getProperty("tkt.footer6");
    }

    public void setProperty(String sKey, String sValue) {
        if (sValue == null) {
            this.m_propsconfig.remove(sKey);
        } else {
            this.m_propsconfig.setProperty(sKey, sValue);
        }
    }

    private String getLocalHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        }
        catch (UnknownHostException eUH) {
            return "localhost";
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static AppConfig getInstance() {
        if (m_instance != null) return m_instance;
        Class<AppConfig> clazz = AppConfig.class;
        synchronized (AppConfig.class) {
            if (m_instance != null) return m_instance;
            m_instance = new AppConfig(new String[0]);
            // ** MonitorExit[var0] (shouldn't be in output)
            return m_instance;
        }
    }

    public static void initInstance(AppConfig config) {
        m_instance = config;
    }

    public Boolean getBoolean(String sKey) {
        return Boolean.valueOf(this.m_propsconfig.getProperty(sKey));
    }

    public void setBoolean(String sKey, Boolean sValue) {
        if (sValue == null) {
            this.m_propsconfig.remove(sKey);
        } else if (sValue.booleanValue()) {
            this.m_propsconfig.setProperty(sKey, "true");
        } else {
            this.m_propsconfig.setProperty(sKey, "false");
        }
    }

    public boolean delete() {
        this.loadDefault();
        return this.configfile.delete();
    }

    public void load() {
        this.loadDefault();
        try {
            FileInputStream in = new FileInputStream(this.configfile);
            if (in != null) {
                this.m_propsconfig.load(in);
                ((InputStream)in).close();
            }
        }
        catch (IOException e) {
            this.loadDefault();
        }
    }

    public Boolean isPriceWith00() {
        String prop = this.getProperty("pricewith00");
        if (prop == null) {
            return false;
        }
        return prop.equals("true");
    }

    public void save() throws IOException {
        FileOutputStream out = new FileOutputStream(this.configfile);
        if (out != null) {
            this.m_propsconfig.store(out, "SOLTEC POS. Configuration file.");
            ((OutputStream)out).close();
        }
    }

    private void loadDefault() {
        this.m_propsconfig = new Properties();
        String dirname = System.getProperty("dirname.path");
        dirname = dirname == null ? "./" : dirname;
        this.m_propsconfig.setProperty("db.multi", "false");
        this.m_propsconfig.setProperty("db.name", "soltec");
        this.m_propsconfig.setProperty("db.URL", "jdbc:mysql://localhost:3306/soltec");
        this.m_propsconfig.setProperty("db.user", "root");
        this.m_propsconfig.setProperty("db.password", "admin");
        this.m_propsconfig.setProperty("db.driverlib", new File(new File(dirname), "lib/mysql-connector-java-5.1.34.jar").getAbsolutePath());
        this.m_propsconfig.setProperty("db.driver", "com.mysql.jdbc.Driver");
        this.m_propsconfig.setProperty("db1.name", "");
        this.m_propsconfig.setProperty("db1.URL", "");
        this.m_propsconfig.setProperty("db1.user", "");
        this.m_propsconfig.setProperty("db1.password", "");
        this.m_propsconfig.setProperty("machine.hostname", this.getLocalHostName());
        Locale l = Locale.getDefault();
        this.m_propsconfig.setProperty("user.language", l.getLanguage());
        this.m_propsconfig.setProperty("user.country", l.getCountry());
        this.m_propsconfig.setProperty("user.variant", l.getVariant());
        this.m_propsconfig.setProperty("swing.defaultlaf", System.getProperty("swing.defaultlaf", "javax.swing.plaf.metal.MetalLookAndFeel"));
        this.m_propsconfig.setProperty("machine.printer", "screen");
        this.m_propsconfig.setProperty("machine.printer.2", "Not defined");
        this.m_propsconfig.setProperty("machine.printer.3", "Not defined");
        this.m_propsconfig.setProperty("machine.printer.4", "Not defined");
        this.m_propsconfig.setProperty("machine.printer.5", "Not defined");
        this.m_propsconfig.setProperty("machine.printer.6", "Not defined");
        this.m_propsconfig.setProperty("machine.display", "screen");
        this.m_propsconfig.setProperty("machine.scale", "Not defined");
        this.m_propsconfig.setProperty("machine.screenmode", "window");
        this.m_propsconfig.setProperty("machine.ticketsbag", "standard");
        this.m_propsconfig.setProperty("machine.scanner", "Not defined");
        this.m_propsconfig.setProperty("machine.iButton", "false");
        this.m_propsconfig.setProperty("machine.iButtonResponse", "5");
        this.m_propsconfig.setProperty("machine.uniqueinstance", "true");
        this.m_propsconfig.setProperty("payment.gateway", "external");
        this.m_propsconfig.setProperty("payment.magcardreader", "Not defined");
        this.m_propsconfig.setProperty("payment.testmode", "false");
        this.m_propsconfig.setProperty("payment.commerceid", "");
        this.m_propsconfig.setProperty("payment.commercepassword", "password");
        this.m_propsconfig.setProperty("machine.printername", "(Default)");
        this.m_propsconfig.setProperty("screen.receipt.columns", "42");
        this.m_propsconfig.setProperty("paper.receipt.x", "10");
        this.m_propsconfig.setProperty("paper.receipt.y", "10");
        this.m_propsconfig.setProperty("paper.receipt.width", "190");
        this.m_propsconfig.setProperty("paper.receipt.height", "546");
        this.m_propsconfig.setProperty("paper.receipt.mediasizename", "A4");
        this.m_propsconfig.setProperty("paper.standard.x", "72");
        this.m_propsconfig.setProperty("paper.standard.y", "72");
        this.m_propsconfig.setProperty("paper.standard.width", "451");
        this.m_propsconfig.setProperty("paper.standard.height", "698");
        this.m_propsconfig.setProperty("paper.standard.mediasizename", "A4");
        this.m_propsconfig.setProperty("tkt.header1", "SOLTEC POS");
        this.m_propsconfig.setProperty("tkt.header2", "Touch Friendly Point Of Sale");
        this.m_propsconfig.setProperty("tkt.header3", "Copyright (c) 2009-2016 uniCenta");
        this.m_propsconfig.setProperty("tkt.header4", "Change header text in Configuration");
        this.m_propsconfig.setProperty("tkt.footer1", "Change footer text in Configuration");
        this.m_propsconfig.setProperty("tkt.footer2", "Thank you for your custom");
        this.m_propsconfig.setProperty("tkt.footer3", "Please Call Again");
        this.m_propsconfig.setProperty("docs.prefix", "DS");
        this.m_propsconfig.setProperty("docs.resolucionfe", "XXXXXXXXXXXXXX");
        this.m_propsconfig.setProperty("docs.nit", "");
        this.m_propsconfig.setProperty("noc.prefix", "NC");
        this.m_propsconfig.setProperty("noc.resolucion", "XXXXXXXXXXXXXX");
        this.m_propsconfig.setProperty("noc.consecutivo", "1");
    }
}

