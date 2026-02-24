/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.config;

import com.openbravo.data.gui.JMessageDialog;
import com.openbravo.data.gui.MessageInf;
import com.openbravo.data.loader.Session;
import com.openbravo.data.user.DirtyManager;
import com.openbravo.pos.config.PanelConfig;
import com.openbravo.pos.forms.AppConfig;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.DriverWrapper;
import com.openbravo.pos.util.AltEncrypter;
import com.openbravo.pos.util.DirectoryEvent;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;

public class JPanelConfigDatabase
extends JPanel
implements PanelConfig {
    private final DirtyManager dirty = new DirtyManager();
    private JButton jbtnListDB;
    private JLabel LblMultiDB;
    private JButton jButtonTest;
    private JButton jButtonTest1;
    private JLabel jLabel1;
    private JLabel jLabel18;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLblDBIP;
    private JLabel jLblDBName;
    private JLabel jLblDBPort;
    private JLabel jLblDBPort1;
    private JLabel jLblDbIP1;
    private JLabel jLblDbName1;
    private JLabel jLblDbPassword1;
    private JLabel jLblDbURL1;
    private JLabel jLblDbUser1;
    private JSeparator jSeparator1;
    private JButton jbtnDbDriverLib;
    private JComboBox<String> jcboDBDriver;
    private JTextField jtxtDbDriver;
    private JTextField jtxtDbDriverLib;
    private JTextField jtxtDbIP;
    private JTextField jtxtDbIP1;
    private JTextField jtxtDbName;
    private JTextField jtxtDbName1;
    private JPasswordField jtxtDbPassword;
    private JPasswordField jtxtDbPassword1;
    private JTextField jtxtDbPort;
    private JTextField jtxtDbPort1;
    private JTextField jtxtDbURL;
    private JTextField jtxtDbURL1;
    private JTextField jtxtDbUser;
    private JTextField jtxtDbUser1;
    private JCheckBox multiDB;

    public JPanelConfigDatabase() {
        this.initComponents();
        this.jtxtDbDriverLib.getDocument().addDocumentListener(this.dirty);
        this.jtxtDbDriver.getDocument().addDocumentListener(this.dirty);
        this.jbtnDbDriverLib.addActionListener(new DirectoryEvent(this.jtxtDbDriverLib));
        this.jcboDBDriver.addActionListener(this.dirty);
        this.jcboDBDriver.addItem("MySQL");
        this.jcboDBDriver.setSelectedIndex(0);
        this.multiDB.addActionListener(this.dirty);
        this.jtxtDbName.getDocument().addDocumentListener(this.dirty);
        this.jtxtDbURL.getDocument().addDocumentListener(this.dirty);
        this.jtxtDbIP.getDocument().addDocumentListener(this.dirty);
        this.jtxtDbPort.getDocument().addDocumentListener(this.dirty);
        this.jtxtDbPassword.getDocument().addDocumentListener(this.dirty);
        this.jtxtDbUser.getDocument().addDocumentListener(this.dirty);
        this.jtxtDbName1.getDocument().addDocumentListener(this.dirty);
        this.jtxtDbURL1.getDocument().addDocumentListener(this.dirty);
        this.jtxtDbIP1.getDocument().addDocumentListener(this.dirty);
        this.jtxtDbPort1.getDocument().addDocumentListener(this.dirty);
        this.jtxtDbPassword1.getDocument().addDocumentListener(this.dirty);
        this.jtxtDbUser1.getDocument().addDocumentListener(this.dirty);
    }

    @Override
    public boolean hasChanged() {
        return this.dirty.isDirty();
    }

    @Override
    public Component getConfigComponent() {
        return this;
    }

    @Override
    public void loadProperties(AppConfig config) {
        this.multiDB.setSelected(Boolean.parseBoolean(config.getProperty("db.multi")));
        this.jcboDBDriver.setSelectedItem(config.getProperty("db.engine"));
        this.jtxtDbDriverLib.setText(config.getProperty("db.driverlib"));
        this.jtxtDbDriver.setText(config.getProperty("db.driver"));
        this.jtxtDbName.setText(config.getProperty("db.name"));
        this.jtxtDbIP.setText(config.getProperty("db.ip"));
        this.jtxtDbPort.setText(config.getProperty("db.port"));
        this.jtxtDbURL.setText(config.getProperty("db.URL"));
        String sDBUser = config.getProperty("db.user");
        String sDBPassword = config.getProperty("db.password");
        if (sDBUser != null && sDBPassword != null && sDBPassword.startsWith("crypt:")) {
            AltEncrypter cypher = new AltEncrypter("cypherkey" + sDBUser);
            sDBPassword = cypher.decrypt(sDBPassword.substring(6));
        }
        this.jtxtDbUser.setText(sDBUser);
        this.jtxtDbPassword.setText(sDBPassword);
        this.jtxtDbName1.setText(config.getProperty("db1.name"));
        this.jtxtDbIP1.setText(config.getProperty("db1.ip"));
        this.jtxtDbPort1.setText(config.getProperty("db1.port"));
        this.jtxtDbURL1.setText(config.getProperty("db1.URL"));
        String sDBUser1 = config.getProperty("db1.user");
        String sDBPassword1 = config.getProperty("db1.password");
        if (sDBUser1 != null && sDBPassword1 != null && sDBPassword1.startsWith("crypt:")) {
            AltEncrypter cypher = new AltEncrypter("cypherkey" + sDBUser1);
            sDBPassword1 = cypher.decrypt(sDBPassword1.substring(6));
        }
        this.jtxtDbUser1.setText(sDBUser1);
        this.jtxtDbPassword1.setText(sDBPassword1);
        this.dirty.setDirty(false);
    }

    @Override
    public void saveProperties(AppConfig config) {
        config.setProperty("db.multi", Boolean.toString(this.multiDB.isSelected()));
        config.setProperty("db.engine", this.comboValue(this.jcboDBDriver.getSelectedItem()));
        config.setProperty("db.driverlib", this.jtxtDbDriverLib.getText());
        config.setProperty("db.driver", this.jtxtDbDriver.getText());
        config.setProperty("db.name", this.jtxtDbName.getText());
        config.setProperty("db.ip", this.jtxtDbIP.getText());
        config.setProperty("db.port", this.jtxtDbPort.getText());
        config.setProperty("db.URL", this.jtxtDbURL.getText());
        config.setProperty("db.user", this.jtxtDbUser.getText());
        AltEncrypter cypher = new AltEncrypter("cypherkey" + this.jtxtDbUser.getText());
        config.setProperty("db.password", "crypt:" + cypher.encrypt(new String(this.jtxtDbPassword.getPassword())));
        config.setProperty("db1.name", this.jtxtDbName1.getText());
        config.setProperty("db1.ip", this.jtxtDbIP1.getText());
        config.setProperty("db1.port", this.jtxtDbPort1.getText());
        config.setProperty("db1.URL", this.jtxtDbURL1.getText());
        config.setProperty("db1.user", this.jtxtDbUser1.getText());
        cypher = new AltEncrypter("cypherkey" + this.jtxtDbUser1.getText());
        config.setProperty("db1.password", "crypt:" + cypher.encrypt(new String(this.jtxtDbPassword1.getPassword())));
        this.dirty.setDirty(false);
    }

    private String comboValue(Object value) {
        return value == null ? "" : value.toString();
    }

    private ImageIcon getSoltecLogoIcon() {
        int width = 880;
        int height = 120;
        BufferedImage image = new BufferedImage(width, height, 2);
        Graphics2D g2d = image.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        Color color1 = new Color(30, 30, 40);
        Color color2 = new Color(10, 10, 15);
        GradientPaint gp = new GradientPaint(0.0f, 0.0f, color1, 0.0f, height, color2);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, width, height);
        g2d.setColor(Color.WHITE);
        Font font = new Font("Arial", 1, 48);
        g2d.setFont(font);
        String text = "SOLTEC";
        FontMetrics fm = g2d.getFontMetrics();
        int textX = (width - fm.stringWidth(text)) / 2;
        int textY = (height - fm.getHeight()) / 2 + fm.getAscent() - 10;
        g2d.drawString(text, textX, textY);
        g2d.setColor(new Color(200, 200, 200));
        g2d.setFont(new Font("Arial", 0, 16));
        String sub = "Database Configuration";
        fm = g2d.getFontMetrics();
        g2d.drawString(sub, (width - fm.stringWidth(sub)) / 2, textY + 30);
        g2d.dispose();
        return new ImageIcon(image);
    }

    private ImageIcon safeIcon(String path) {
        java.net.URL imgUrl = this.getClass().getResource(path);
        if (imgUrl != null) {
            return new ImageIcon(imgUrl);
        } else {
            System.err.println("WARNING: Image resource not found: " + path);
            return null;
        }
    }

    private void initComponents() {
        this.jLabel6 = new JLabel();
        this.jcboDBDriver = new JComboBox();
        this.jLabel18 = new JLabel();
        this.jtxtDbDriverLib = new JTextField();
        this.jbtnDbDriverLib = new JButton();
        this.jLabel1 = new JLabel();
        this.jtxtDbDriver = new JTextField();
        this.jLabel2 = new JLabel();
        this.jtxtDbURL = new JTextField();
        this.jLabel3 = new JLabel();
        this.jtxtDbUser = new JTextField();
        this.jLabel4 = new JLabel();
        this.jtxtDbPassword = new JPasswordField();
        this.jButtonTest = new JButton();
        this.jSeparator1 = new JSeparator();
        this.jLabel5 = new JLabel();
        this.jButtonTest1 = new JButton();
        this.jtxtDbPassword1 = new JPasswordField();
        this.jtxtDbUser1 = new JTextField();
        this.jtxtDbURL1 = new JTextField();
        this.jLblDbURL1 = new JLabel();
        this.jLblDbUser1 = new JLabel();
        this.jLblDbPassword1 = new JLabel();
        this.jLblDBName = new JLabel();
        this.jtxtDbName = new JTextField();
        this.jLblDbName1 = new JLabel();
        this.jtxtDbName1 = new JTextField();
        this.LblMultiDB = new JLabel();
        this.multiDB = new JCheckBox();
        this.jLblDBIP = new JLabel();
        this.jtxtDbIP = new JTextField();
        this.jLblDbIP1 = new JLabel();
        this.jtxtDbIP1 = new JTextField();
        this.jLblDBPort = new JLabel();
        this.jtxtDbPort = new JTextField();
        this.jLblDBPort1 = new JLabel();
        this.jtxtDbPort1 = new JTextField();
        this.setBackground(new Color(255, 255, 255));
        this.setFont(new Font("Arial", 0, 12));
        this.setPreferredSize(new Dimension(900, 500));
        this.setLayout(new AbsoluteLayout());
        this.jLabel6.setFont(new Font("Arial", 0, 14));
        ResourceBundle bundle = ResourceBundle.getBundle("pos_messages");
        this.jLabel6.setText(bundle.getString("label.Database"));
        this.jLabel6.setPreferredSize(new Dimension(125, 30));
        this.add((Component)this.jLabel6, new AbsoluteConstraints(10, 196, -1, -1));
        this.jcboDBDriver.setFont(new Font("Arial", 0, 14));
        this.jcboDBDriver.setPreferredSize(new Dimension(150, 30));
        this.jcboDBDriver.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPanelConfigDatabase.this.jcboDBDriverActionPerformed(evt);
            }
        });
        this.add(this.jcboDBDriver, new AbsoluteConstraints(139, 196, -1, -1));
        this.jLabel18.setFont(new Font("Arial", 0, 14));
        this.jLabel18.setText(AppLocal.getIntString("label.dbdriverlib"));
        this.jLabel18.setPreferredSize(new Dimension(125, 30));
        this.add((Component)this.jLabel18, new AbsoluteConstraints(10, 154, -1, 25));
        this.jtxtDbDriverLib.setFont(new Font("Arial", 0, 14));
        this.jtxtDbDriverLib.setPreferredSize(new Dimension(500, 30));
        this.add((Component)this.jtxtDbDriverLib, new AbsoluteConstraints(139, 152, -1, -1));
        this.jbtnDbDriverLib.setIcon(safeIcon("/com/openbravo/images/fileopen.png"));
        this.jbtnDbDriverLib.setText("  ");
        this.jbtnDbDriverLib.setToolTipText("");
        this.jbtnDbDriverLib.setMaximumSize(new Dimension(64, 32));
        this.jbtnDbDriverLib.setMinimumSize(new Dimension(64, 32));
        this.jbtnDbDriverLib.setPreferredSize(new Dimension(80, 45));
        this.add((Component)this.jbtnDbDriverLib, new AbsoluteConstraints(649, 145, -1, -1));
        this.jLabel1.setFont(new Font("Arial", 0, 14));
        this.jLabel1.setText(AppLocal.getIntString("label.DbDriver"));
        this.jLabel1.setPreferredSize(new Dimension(125, 30));
        this.add((Component)this.jLabel1, new AbsoluteConstraints(324, 196, -1, -1));
        this.jtxtDbDriver.setFont(new Font("Arial", 0, 14));
        this.jtxtDbDriver.setPreferredSize(new Dimension(150, 30));
        this.jtxtDbDriver.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPanelConfigDatabase.this.jtxtDbDriverActionPerformed(evt);
            }
        });
        this.add((Component)this.jtxtDbDriver, new AbsoluteConstraints(459, 197, -1, -1));
        this.jLabel2.setFont(new Font("Arial", 0, 14));
        this.jLabel2.setText(AppLocal.getIntString("label.DbURL"));
        this.jLabel2.setPreferredSize(new Dimension(125, 30));
        this.add((Component)this.jLabel2, new AbsoluteConstraints(10, 343, -1, -1));
        this.jtxtDbURL.setFont(new Font("Arial", 0, 14));
        this.jtxtDbURL.setPreferredSize(new Dimension(275, 30));
        this.add((Component)this.jtxtDbURL, new AbsoluteConstraints(139, 343, -1, -1));
        this.jLabel3.setFont(new Font("Arial", 0, 14));
        this.jLabel3.setText(AppLocal.getIntString("label.DbUser"));
        this.jLabel3.setPreferredSize(new Dimension(125, 30));
        this.add((Component)this.jLabel3, new AbsoluteConstraints(10, 380, -1, -1));
        this.jtxtDbUser.setFont(new Font("Arial", 0, 14));
        this.jtxtDbUser.setPreferredSize(new Dimension(150, 30));
        this.add((Component)this.jtxtDbUser, new AbsoluteConstraints(139, 380, -1, -1));
        this.jLabel4.setFont(new Font("Arial", 0, 14));
        this.jLabel4.setText(AppLocal.getIntString("label.DbPassword"));
        this.jLabel4.setPreferredSize(new Dimension(125, 30));
        this.add((Component)this.jLabel4, new AbsoluteConstraints(10, 417, -1, -1));
        this.jtxtDbPassword.setFont(new Font("Arial", 0, 14));
        this.jtxtDbPassword.setPreferredSize(new Dimension(150, 30));
        this.add((Component)this.jtxtDbPassword, new AbsoluteConstraints(139, 417, -1, -1));
        this.jButtonTest.setFont(new Font("Arial", 0, 14));
        this.jButtonTest.setIcon(safeIcon("/com/openbravo/images/database.png"));
        this.jButtonTest.setText(bundle.getString("button.test"));
        this.jButtonTest.setActionCommand(bundle.getString("Button.Test"));
        this.jButtonTest.setPreferredSize(new Dimension(110, 45));
        this.jButtonTest.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPanelConfigDatabase.this.jButtonTestActionPerformed(evt);
            }
        });
        this.add((Component)this.jButtonTest, new AbsoluteConstraints(139, 455, -1, -1));
        this.jbtnListDB = new JButton();
        this.jbtnListDB.setFont(new Font("Arial", 0, 14));
        this.jbtnListDB.setIcon(safeIcon("/com/openbravo/images/database.png"));
        this.jbtnListDB.setText("Listen");
        this.jbtnListDB.setToolTipText("List available databases");
        this.jbtnListDB.setPreferredSize(new Dimension(110, 45));
        this.jbtnListDB.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPanelConfigDatabase.this.jbtnListDBActionPerformed(evt);
            }
        });
        this.add((Component)this.jbtnListDB, new AbsoluteConstraints(260, 455, -1, -1));
        this.add((Component)this.jSeparator1, new AbsoluteConstraints(10, 137, 880, -1));
        this.jLabel5.setBackground(new Color(255, 255, 255));
        this.jLabel5.setFont(new Font("Arial", 0, 12));
        this.jLabel5.setHorizontalAlignment(0);
        this.jLabel5.setIcon(this.getSoltecLogoIcon());
        this.jLabel5.setText("");
        this.jLabel5.setToolTipText("");
        this.jLabel5.setVerticalAlignment(1);
        this.jLabel5.setPreferredSize(new Dimension(889, 120));
        this.add((Component)this.jLabel5, new AbsoluteConstraints(10, 6, 880, -1));
        this.jButtonTest1.setFont(new Font("Arial", 0, 14));
        this.jButtonTest1.setIcon(safeIcon("/com/openbravo/images/database.png"));
        this.jButtonTest1.setText(bundle.getString("button.test"));
        this.jButtonTest1.setActionCommand(bundle.getString("Button.Test"));
        this.jButtonTest1.setEnabled(false);
        this.jButtonTest1.setPreferredSize(new Dimension(110, 45));
        this.jButtonTest1.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPanelConfigDatabase.this.jButtonTest1ActionPerformed(evt);
            }
        });
        this.add((Component)this.jButtonTest1, new AbsoluteConstraints(570, 455, -1, -1));
        this.jtxtDbPassword1.setFont(new Font("Arial", 0, 14));
        this.jtxtDbPassword1.setEnabled(false);
        this.jtxtDbPassword1.setPreferredSize(new Dimension(150, 30));
        this.add((Component)this.jtxtDbPassword1, new AbsoluteConstraints(570, 417, -1, -1));
        this.jtxtDbUser1.setFont(new Font("Arial", 0, 14));
        this.jtxtDbUser1.setEnabled(false);
        this.jtxtDbUser1.setPreferredSize(new Dimension(150, 30));
        this.add((Component)this.jtxtDbUser1, new AbsoluteConstraints(570, 380, -1, -1));
        this.jtxtDbURL1.setFont(new Font("Arial", 0, 14));
        this.jtxtDbURL1.setEnabled(false);
        this.jtxtDbURL1.setPreferredSize(new Dimension(275, 30));
        this.add((Component)this.jtxtDbURL1, new AbsoluteConstraints(570, 343, -1, -1));
        this.jLblDbURL1.setFont(new Font("Arial", 0, 14));
        this.jLblDbURL1.setText(AppLocal.getIntString("label.DbURL1"));
        this.jLblDbURL1.setEnabled(false);
        this.jLblDbURL1.setPreferredSize(new Dimension(125, 30));
        this.add((Component)this.jLblDbURL1, new AbsoluteConstraints(440, 343, -1, -1));
        this.jLblDbUser1.setFont(new Font("Arial", 0, 14));
        this.jLblDbUser1.setText(AppLocal.getIntString("label.DbUser"));
        this.jLblDbUser1.setEnabled(false);
        this.jLblDbUser1.setPreferredSize(new Dimension(125, 30));
        this.add((Component)this.jLblDbUser1, new AbsoluteConstraints(440, 380, -1, -1));
        this.jLblDbPassword1.setFont(new Font("Arial", 0, 14));
        this.jLblDbPassword1.setText(AppLocal.getIntString("label.DbPassword"));
        this.jLblDbPassword1.setEnabled(false);
        this.jLblDbPassword1.setPreferredSize(new Dimension(125, 30));
        this.add((Component)this.jLblDbPassword1, new AbsoluteConstraints(440, 417, -1, -1));
        this.jLblDBName.setFont(new Font("Arial", 0, 14));
        this.jLblDBName.setText(AppLocal.getIntString("label.DbName"));
        this.jLblDBName.setPreferredSize(new Dimension(125, 30));
        this.add((Component)this.jLblDBName, new AbsoluteConstraints(10, 269, -1, -1));
        this.jtxtDbName.setFont(new Font("Arial", 0, 14));
        this.jtxtDbName.setPreferredSize(new Dimension(275, 30));
        this.add((Component)this.jtxtDbName, new AbsoluteConstraints(139, 269, -1, -1));
        this.jLblDbName1.setFont(new Font("Arial", 0, 14));
        this.jLblDbName1.setText(AppLocal.getIntString("label.DbName1"));
        this.jLblDbName1.setEnabled(false);
        this.jLblDbName1.setPreferredSize(new Dimension(125, 30));
        this.add((Component)this.jLblDbName1, new AbsoluteConstraints(440, 269, -1, -1));
        this.jtxtDbName1.setFont(new Font("Arial", 0, 14));
        this.jtxtDbName1.setEnabled(false);
        this.jtxtDbName1.setPreferredSize(new Dimension(275, 30));
        this.add((Component)this.jtxtDbName1, new AbsoluteConstraints(570, 269, -1, -1));
        this.LblMultiDB.setText(AppLocal.getIntString("label.multidb"));
        this.LblMultiDB.setFont(new Font("Arial", 0, 14));
        this.LblMultiDB.setPreferredSize(new Dimension(125, 30));
        this.add((Component)this.LblMultiDB, new AbsoluteConstraints(10, 233, -1, -1));
        this.multiDB.setFont(new Font("Arial", 0, 14));
        this.multiDB.setPreferredSize(new Dimension(80, 30));
        this.multiDB.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPanelConfigDatabase.this.multiDBActionPerformed(evt);
            }
        });
        this.add((Component)this.multiDB, new AbsoluteConstraints(139, 233, -1, -1));
        this.jLblDBIP.setFont(new Font("Arial", 0, 14));
        this.jLblDBIP.setText(AppLocal.getIntString("label.DbIP"));
        this.jLblDBIP.setPreferredSize(new Dimension(125, 30));
        this.add((Component)this.jLblDBIP, new AbsoluteConstraints(10, 306, -1, -1));
        this.jtxtDbIP.setFont(new Font("Arial", 0, 14));
        this.jtxtDbIP.setPreferredSize(new Dimension(135, 30));
        this.add((Component)this.jtxtDbIP, new AbsoluteConstraints(139, 306, -1, -1));
        this.jLblDbIP1.setFont(new Font("Arial", 0, 14));
        this.jLblDbIP1.setText(AppLocal.getIntString("label.IP1"));
        this.jLblDbIP1.setEnabled(false);
        this.jLblDbIP1.setPreferredSize(new Dimension(125, 30));
        this.add((Component)this.jLblDbIP1, new AbsoluteConstraints(440, 306, -1, -1));
        this.jtxtDbIP1.setFont(new Font("Arial", 0, 14));
        this.jtxtDbIP1.setEnabled(false);
        this.jtxtDbIP1.setPreferredSize(new Dimension(135, 30));
        this.add((Component)this.jtxtDbIP1, new AbsoluteConstraints(570, 306, -1, -1));
        this.jLblDBPort.setFont(new Font("Arial", 0, 14));
        this.jLblDBPort.setText(AppLocal.getIntString("label.DbPort"));
        this.jLblDBPort.setPreferredSize(new Dimension(50, 30));
        this.add((Component)this.jLblDBPort, new AbsoluteConstraints(288, 306, -1, -1));
        this.jtxtDbPort.setFont(new Font("Arial", 0, 14));
        this.jtxtDbPort.setPreferredSize(new Dimension(70, 30));
        this.add((Component)this.jtxtDbPort, new AbsoluteConstraints(344, 306, 70, -1));
        this.jLblDBPort1.setFont(new Font("Arial", 0, 14));
        this.jLblDBPort1.setText(AppLocal.getIntString("label.DbPort1"));
        this.jLblDBPort1.setEnabled(false);
        this.jLblDBPort1.setPreferredSize(new Dimension(50, 30));
        this.add((Component)this.jLblDBPort1, new AbsoluteConstraints(720, 306, -1, -1));
        this.jtxtDbPort1.setFont(new Font("Arial", 0, 14));
        this.jtxtDbPort1.setEnabled(false);
        this.jtxtDbPort1.setPreferredSize(new Dimension(70, 30));
        this.add((Component)this.jtxtDbPort1, new AbsoluteConstraints(774, 306, -1, -1));
    }

    private void jtxtDbDriverActionPerformed(ActionEvent evt) {
    }

    private void jcboDBDriverActionPerformed(ActionEvent evt) {
        String dirname = System.getProperty("dirname.path");
        String string = dirname = dirname == null ? "./" : dirname;
        if ("PostgreSQL".equals(this.jcboDBDriver.getSelectedItem())) {
            this.jtxtDbDriverLib.setText(new File(new File(dirname), "lib/postgresql-9.4-1208.jdbc4.jar").getAbsolutePath());
            this.jtxtDbDriver.setText("org.postgresql.Driver");
            this.jtxtDbURL.setText("jdbc:postgresql://localhost:5432/unicentaopos");
        } else {
            this.jtxtDbDriverLib.setText(new File(new File(dirname), "lib/mysql-connector-java-5.1.34-bin.jar").getAbsolutePath());
            this.jtxtDbDriver.setText("com.mysql.jdbc.Driver");
            this.jtxtDbURL.setText("jdbc:mysql://localhost:3306/unicentaopos");
        }
    }

    private void jButtonTestActionPerformed(ActionEvent evt) {
        try {
            boolean isValid;
            String driverlib = this.jtxtDbDriverLib.getText();
            String driver = this.jtxtDbDriver.getText();
            String url = this.jtxtDbURL.getText();
            String user = this.jtxtDbUser.getText();
            String password = new String(this.jtxtDbPassword.getPassword());
            URLClassLoader cloader = new URLClassLoader(new URL[]{new File(driverlib).toURI().toURL()});
            DriverManager.registerDriver(new DriverWrapper((Driver)Class.forName(driver, true, cloader).newInstance()));
            Session session = new Session(url, user, password);
            Connection connection = session.getConnection();
            boolean bl = isValid = connection == null ? false : connection.isValid(1000);
            if (isValid) {
                JOptionPane.showMessageDialog(this, AppLocal.getIntString("message.databasesuccess"), "Connection Test", 1);
            } else {
                JMessageDialog.showMessage(this, new MessageInf(-33554432, "Connection Error"));
            }
        }
        catch (ClassNotFoundException | IllegalAccessException | InstantiationException | MalformedURLException e) {
            JMessageDialog.showMessage(this, new MessageInf(-33554432, AppLocal.getIntString("message.databasedrivererror"), e));
        }
        catch (SQLException e) {
            JMessageDialog.showMessage(this, new MessageInf(-33554432, AppLocal.getIntString("message.databaseconnectionerror"), e));
        }
        catch (Exception e) {
            JMessageDialog.showMessage(this, new MessageInf(-33554432, "Unknown exception", e));
        }
    }

    private void jbtnListDBActionPerformed(ActionEvent evt) {
        try {
            String driverlib = this.jtxtDbDriverLib.getText();
            String driver = this.jtxtDbDriver.getText();
            String ip = this.jtxtDbIP.getText();
            String port = this.jtxtDbPort.getText();
            String user = this.jtxtDbUser.getText();
            String password = new String(this.jtxtDbPassword.getPassword());
            String urlBase = "jdbc:mysql://" + ip + ":" + port + "/";
            URLClassLoader cloader = new URLClassLoader(new URL[]{new File(driverlib).toURI().toURL()});
            DriverManager.registerDriver(new DriverWrapper((Driver)Class.forName(driver, true, cloader).newInstance()));
            Connection connection = DriverManager.getConnection(urlBase + "?useSSL=false", user, password);
            ArrayList<String> databases = new ArrayList<String>();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SHOW DATABASES");
            while (rs.next()) {
                databases.add(rs.getString(1));
            }
            rs.close();
            stmt.close();
            connection.close();
            if (databases.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No databases found.", "Database List", 1);
                return;
            }
            String selectedDB = (String)JOptionPane.showInputDialog(this, "Select a database to use:", "Select Database", 3, null, databases.toArray(), databases.get(0));
            if (selectedDB != null) {
                this.jtxtDbName.setText(selectedDB);
                this.jtxtDbURL.setText(urlBase + selectedDB);
                this.dirty.setDirty(true);
            }
        }
        catch (Exception e) {
            JMessageDialog.showMessage(this, new MessageInf(-33554432, "Error listing databases", e));
        }
    }

    private void jButtonTest1ActionPerformed(ActionEvent evt) {
        try {
            boolean isValid;
            String driverlib = this.jtxtDbDriverLib.getText();
            String driver = this.jtxtDbDriver.getText();
            String url = this.jtxtDbURL1.getText();
            String user = this.jtxtDbUser1.getText();
            String password = new String(this.jtxtDbPassword1.getPassword());
            URLClassLoader cloader = new URLClassLoader(new URL[]{new File(driverlib).toURI().toURL()});
            DriverManager.registerDriver(new DriverWrapper((Driver)Class.forName(driver, true, cloader).newInstance()));
            Session session = new Session(url, user, password);
            Connection connection = session.getConnection();
            boolean bl = isValid = connection == null ? false : connection.isValid(1000);
            if (isValid) {
                JOptionPane.showMessageDialog(this, AppLocal.getIntString("message.databasesuccess"), "Connection Test", 1);
            } else {
                JMessageDialog.showMessage(this, new MessageInf(-33554432, "Connection Error"));
            }
        }
        catch (ClassNotFoundException | IllegalAccessException | InstantiationException | MalformedURLException e) {
            JMessageDialog.showMessage(this, new MessageInf(-33554432, AppLocal.getIntString("message.databasedrivererror"), e));
        }
        catch (SQLException e) {
            JMessageDialog.showMessage(this, new MessageInf(-33554432, AppLocal.getIntString("message.databaseconnectionerror"), e));
        }
        catch (Exception e) {
            JMessageDialog.showMessage(this, new MessageInf(-33554432, "Unknown exception", e));
        }
    }

    private void multiDBActionPerformed(ActionEvent evt) {
        if (this.multiDB.isSelected()) {
            this.jLblDbName1.setEnabled(true);
            this.jtxtDbName1.setEnabled(true);
            this.jLblDbIP1.setEnabled(true);
            this.jtxtDbIP1.setEnabled(true);
            this.jLblDBPort1.setEnabled(true);
            this.jtxtDbPort1.setEnabled(true);
            this.jLblDbURL1.setEnabled(true);
            this.jtxtDbURL1.setEnabled(true);
            this.jLblDbUser1.setEnabled(true);
            this.jtxtDbUser1.setEnabled(true);
            this.jLblDbPassword1.setEnabled(true);
            this.jtxtDbPassword1.setEnabled(true);
            this.jButtonTest1.setEnabled(true);
        } else {
            this.jLblDbName1.setEnabled(false);
            this.jtxtDbName1.setEnabled(false);
            this.jLblDbIP1.setEnabled(false);
            this.jtxtDbIP1.setEnabled(false);
            this.jLblDBPort1.setEnabled(false);
            this.jtxtDbPort1.setEnabled(false);
            this.jLblDbURL1.setEnabled(false);
            this.jtxtDbURL1.setEnabled(false);
            this.jLblDbUser1.setEnabled(false);
            this.jtxtDbUser1.setEnabled(false);
            this.jLblDbPassword1.setEnabled(false);
            this.jtxtDbPassword1.setEnabled(false);
            this.jButtonTest1.setEnabled(false);
        }
    }
}

