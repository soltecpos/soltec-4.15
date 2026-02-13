/*
 * Decompiled with CFR 0.152.
 */
package com.unicenta.pos.transfer;

import com.openbravo.basic.BasicException;
import com.openbravo.data.gui.JMessageDialog;
import com.openbravo.data.gui.MessageInf;
import com.openbravo.data.loader.BatchSentenceResource;
import com.openbravo.data.loader.Session;
import com.openbravo.data.user.DirtyManager;
import com.openbravo.pos.config.PanelConfig;
import com.openbravo.pos.forms.AppConfig;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.AppProperties;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.forms.AppViewConnection;
import com.openbravo.pos.forms.DriverWrapper;
import com.openbravo.pos.forms.JPanelView;
import com.openbravo.pos.util.AltEncrypter;
import com.openbravo.pos.util.DirectoryEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;

public class JPanelTransfer
extends JPanel
implements JPanelView {
    private DirtyManager dirty = new DirtyManager();
    private AppConfig config;
    private AppProperties m_props;
    private List<PanelConfig> m_panelconfig;
    private Connection con_source;
    private Connection con_target;
    private String sDB_source;
    private String sDB_target;
    private Session session_source;
    private Session session_target;
    private ResultSet rs;
    private Statement stmt_source;
    private Statement stmt_target;
    private String SQL;
    private PreparedStatement pstmt;
    private String ticketsnum;
    private String ticketsnumRefund;
    private String ticketsnumPayment;
    private String eScript = "";
    private String eScript1 = "";
    private String eScript2 = "";
    private String eScript3 = "";
    private JComboBox cbTarget;
    private JButton jButtonTest;
    private JButton jbtnExit;
    private JButton jbtnMigrate;
    private JButton jbtnDbDriverLib;
    private JLabel jLabel1;
    private JLabel jLabel11;
    private JLabel jLabel12;
    private JLabel jLabel18;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel8;
    private JLabel jLabel9;
    private JPanel jPanel1;
    private JTextField txtDBDriver;
    private JTextField txtDBDriverLib;
    private JPasswordField txtDBPass;
    private JTextField txtDBURL;
    private JTextField txtDBUser;
    private JPanel webPanel1;
    private JPanel webPanel2;

    public JPanelTransfer(AppView oApp) {
        this(oApp.getProperties());
    }

    public JPanelTransfer(AppProperties props) {
        this.initComponents();
        this.config = new AppConfig(props.getConfigFile());
        this.m_props = props;
        this.m_panelconfig = new ArrayList<PanelConfig>();
        this.config.load();
        for (PanelConfig c : this.m_panelconfig) {
            c.loadProperties(this.config);
        }
        this.txtDBDriverLib.getDocument().addDocumentListener(this.dirty);
        this.txtDBDriver.getDocument().addDocumentListener(this.dirty);
        this.txtDBURL.getDocument().addDocumentListener(this.dirty);
        this.txtDBPass.getDocument().addDocumentListener(this.dirty);
        this.txtDBUser.getDocument().addDocumentListener(this.dirty);
        this.jbtnDbDriverLib.addActionListener(new DirectoryEvent(this.txtDBDriverLib));
        this.cbTarget.addActionListener(this.dirty);
        this.cbTarget.addItem("MySQL");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Boolean createTargetDB() {
        List l;
        BatchSentenceResource bsentence;
        if (!"MySQL".equals(this.sDB_target) && !"PostgreSQL".equals(this.sDB_target)) {
            return false;
        }
        this.eScript = "/com/openbravo/pos/scripts/" + this.sDB_target + "-create-transfer.sql";
        if ("".equals(this.eScript)) {
            return false;
        }
        try {
            bsentence = new BatchSentenceResource(this.session_target, this.eScript);
            bsentence.putParameter("APP_ID", Matcher.quoteReplacement("soltecpos"));
            bsentence.putParameter("APP_NAME", Matcher.quoteReplacement("SOLTEC POS"));
            bsentence.putParameter("APP_VERSION", Matcher.quoteReplacement("4.15"));
            l = bsentence.list();
            if (l.size() > 0) {
                JMessageDialog.showMessage(this, new MessageInf(-33554432, AppLocal.getIntString("migration.warning"), l.toArray(new Throwable[l.size()])));
            }
        }
        catch (BasicException e) {
            JMessageDialog.showMessage(this, new MessageInf(-16777216, AppLocal.getIntString("migration.warningnodefault"), e));
            this.session_source.close();
        }
        try {
            bsentence = new BatchSentenceResource(this.session_target, this.eScript2);
            l = bsentence.list();
            if (l.size() > 0) {
                JMessageDialog.showMessage(this, new MessageInf(-33554432, AppLocal.getIntString("migration.warning"), l.toArray(new Throwable[l.size()])));
            }
        }
        catch (BasicException e) {
            JMessageDialog.showMessage(this, new MessageInf(-16777216, AppLocal.getIntString("migration.warningnofk"), e));
            this.session_source.close();
        }
        return true;
    }

    public Boolean addFKeys() {
        if ("".equals(this.eScript3)) {
            return false;
        }
        try {
            BatchSentenceResource bsentence = new BatchSentenceResource(this.session_target, this.eScript3);
            List l = bsentence.list();
            if (l.size() > 0) {
                JMessageDialog.showMessage(this, new MessageInf(-33554432, AppLocal.getIntString("migration.warning"), l.toArray(new Throwable[l.size()])));
            }
        }
        catch (BasicException e) {
            JMessageDialog.showMessage(this, new MessageInf(-16777216, AppLocal.getIntString("database.ScriptNotFound"), e));
            this.session_source.close();
        }
        return true;
    }

    @Override
    public JComponent getComponent() {
        return this;
    }

    @Override
    public String getTitle() {
        return AppLocal.getIntString("Menu.Configuration");
    }

    public Boolean getTargetDetail() {
        String db_user2 = this.txtDBUser.getText();
        String db_url2 = this.txtDBURL.getText();
        char[] pass = this.txtDBPass.getPassword();
        String db_password2 = new String(pass);
        Properties connectionProps = new Properties();
        connectionProps.put("user", db_user2);
        connectionProps.put("password", db_password2);
        try {
            Class.forName(this.txtDBDriver.getText());
            URLClassLoader cloader = new URLClassLoader(new URL[]{new File(this.txtDBDriverLib.getText()).toURI().toURL()});
            DriverManager.registerDriver(new DriverWrapper((Driver)Class.forName(this.txtDBDriver.getText(), true, cloader).newInstance()));
            this.con_target = DriverManager.getConnection(db_url2, db_user2, db_password2);
            this.session_target = new Session(db_url2, db_user2, db_password2);
            this.sDB_target = this.con_target.getMetaData().getDatabaseProductName();
            return true;
        }
        catch (ClassNotFoundException | IllegalAccessException | InstantiationException | MalformedURLException | SQLException e) {
            JMessageDialog.showMessage(this, new MessageInf(-16777216, AppLocal.getIntString("database.UnableToConnect"), e));
            return false;
        }
    }

    @Override
    public void activate() throws BasicException {
        String db_user = this.m_props.getProperty("db.user");
        String db_url = this.m_props.getProperty("db.URL");
        String db_password = this.m_props.getProperty("db.password");
        if (db_user != null && db_password != null && db_password.startsWith("crypt:")) {
            AltEncrypter cypher = new AltEncrypter("cypherkey" + db_user);
            db_password = cypher.decrypt(db_password.substring(6));
        }
        try {
            this.session_source = AppViewConnection.createSession(this.m_props);
            this.con_source = DriverManager.getConnection(db_url, db_user, db_password);
            this.sDB_source = this.con_source.getMetaData().getDatabaseProductName();
        }
        catch (BasicException | SQLException e) {
            JMessageDialog.showMessage(this, new MessageInf(-16777216, AppLocal.getIntString("database.UnableToConnect"), e));
            System.exit(0);
        }
    }

    @Override
    public boolean deactivate() {
        return true;
    }

    private void initComponents() {
        this.jPanel1 = new JPanel();
        this.webPanel1 = new JPanel();
        this.cbTarget = new JComboBox();
        this.txtDBDriverLib = new JTextField();
        this.txtDBDriver = new JTextField();
        this.txtDBUser = new JTextField();
        this.txtDBURL = new JTextField();
        this.txtDBPass = new JPasswordField();
        this.jbtnDbDriverLib = new JButton();
        this.jButtonTest = new JButton();
        this.jbtnMigrate = new JButton();
        this.webPanel2 = new JPanel();
        this.jLabel5 = new JLabel();
        this.jLabel18 = new JLabel();
        this.jLabel1 = new JLabel();
        this.jLabel2 = new JLabel();
        this.jLabel3 = new JLabel();
        this.jLabel4 = new JLabel();
        this.jbtnExit = new JButton();
        this.setBackground(new Color(255, 255, 255));
        this.setFont(new Font("Arial", 0, 14));
        this.setPreferredSize(new Dimension(650, 400));
        this.jPanel1.setLayout(null);
        this.webPanel1.setBackground(new Color(255, 255, 255));
        this.cbTarget.setFont(new Font("Arial", 0, 14));
        this.cbTarget.setPreferredSize(new Dimension(200, 30));
        this.txtDBDriverLib.setText("webTextField1");
        this.txtDBDriverLib.setFont(new Font("Arial", 0, 14));
        this.txtDBDriverLib.setPreferredSize(new Dimension(300, 30));
        this.txtDBDriver.setText("webTextField2");
        this.txtDBDriver.setFont(new Font("Arial", 0, 14));
        this.txtDBDriver.setPreferredSize(new Dimension(300, 30));
        this.txtDBUser.setText("webTextField2");
        this.txtDBUser.setFont(new Font("Arial", 0, 14));
        this.txtDBUser.setPreferredSize(new Dimension(300, 30));
        this.txtDBURL.setText("webTextField1");
        this.txtDBURL.setFont(new Font("Arial", 0, 14));
        this.txtDBURL.setPreferredSize(new Dimension(300, 30));
        this.txtDBPass.setText("webPasswordField1");
        this.txtDBPass.setFont(new Font("Arial", 0, 14));
        this.txtDBPass.setPreferredSize(new Dimension(300, 30));
        this.jbtnDbDriverLib.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/fileopen.png")));
        this.jbtnDbDriverLib.setMaximumSize(new Dimension(64, 32));
        this.jbtnDbDriverLib.setMinimumSize(new Dimension(64, 32));
        this.jbtnDbDriverLib.setPreferredSize(new Dimension(64, 32));
        this.jButtonTest.setFont(new Font("Arial", 0, 12));
        this.jButtonTest.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/database.png")));
        ResourceBundle bundle = ResourceBundle.getBundle("pos_messages");
        this.jButtonTest.setText(bundle.getString("Button.Test"));
        this.jButtonTest.setActionCommand(bundle.getString("Button.Test"));
        this.jButtonTest.setPreferredSize(new Dimension(80, 45));
        this.jButtonTest.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPanelTransfer.this.jButtonTestjButtonTestConnectionActionPerformed(evt);
            }
        });
        this.jbtnMigrate.setFont(new Font("Arial", 0, 12));
        this.jbtnMigrate.setText(AppLocal.getIntString("button.migrate"));
        this.jbtnMigrate.setMaximumSize(new Dimension(70, 33));
        this.jbtnMigrate.setMinimumSize(new Dimension(70, 33));
        this.jbtnMigrate.setPreferredSize(new Dimension(80, 45));
        this.jbtnMigrate.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPanelTransfer.this.jbtnMigrateActionPerformed(evt);
            }
        });
        GroupLayout webPanel1Layout = new GroupLayout(this.webPanel1);
        this.webPanel1.setLayout(webPanel1Layout);
        webPanel1Layout.setHorizontalGroup(webPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(webPanel1Layout.createSequentialGroup().addContainerGap().addGroup(webPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(webPanel1Layout.createSequentialGroup().addGroup(webPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.txtDBUser, -1, -1, -2).addComponent(this.cbTarget, -2, 300, -2).addGroup(webPanel1Layout.createSequentialGroup().addComponent(this.txtDBDriverLib, -1, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jbtnDbDriverLib, -2, -1, -2)).addComponent(this.txtDBDriver, -1, -1, -2).addComponent(this.txtDBURL, -1, -1, -2)).addContainerGap()).addGroup(webPanel1Layout.createSequentialGroup().addGroup(webPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.txtDBPass, -2, 300, -2).addGroup(webPanel1Layout.createSequentialGroup().addComponent(this.jButtonTest, -2, -1, -2).addGap(18, 18, 18).addComponent(this.jbtnMigrate, -2, -1, -2))).addContainerGap()))));
        webPanel1Layout.setVerticalGroup(webPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(webPanel1Layout.createSequentialGroup().addGap(40, 40, 40).addComponent(this.cbTarget, -2, -1, -2).addGap(18, 18, 18).addGroup(webPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.txtDBDriverLib, -2, -1, -2).addComponent(this.jbtnDbDriverLib, -1, -1, Short.MAX_VALUE)).addGap(18, 18, 18).addComponent(this.txtDBDriver, -2, 30, -2).addGap(18, 18, 18).addComponent(this.txtDBURL, -2, 30, -2).addGap(18, 18, 18).addComponent(this.txtDBUser, -2, 30, -2).addGap(18, 18, 18).addComponent(this.txtDBPass, -2, -1, -2).addGap(18, 18, 18).addGroup(webPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jButtonTest, -2, -1, -2).addComponent(this.jbtnMigrate, -2, -1, -2)).addContainerGap(-1, Short.MAX_VALUE)));
        this.jLabel5.setFont(new Font("Arial", 0, 14));
        this.jLabel5.setText("New  Database ");
        this.jLabel5.setMaximumSize(new Dimension(150, 30));
        this.jLabel5.setMinimumSize(new Dimension(150, 30));
        this.jLabel5.setPreferredSize(new Dimension(200, 30));
        this.jLabel18.setFont(new Font("Arial", 0, 14));
        this.jLabel18.setText(AppLocal.getIntString("label.dbdriverlib"));
        this.jLabel18.setMaximumSize(new Dimension(150, 30));
        this.jLabel18.setMinimumSize(new Dimension(150, 30));
        this.jLabel18.setPreferredSize(new Dimension(200, 30));
        this.jLabel1.setFont(new Font("Arial", 0, 14));
        this.jLabel1.setText(AppLocal.getIntString("Label.DbDriver"));
        this.jLabel1.setMaximumSize(new Dimension(150, 30));
        this.jLabel1.setMinimumSize(new Dimension(150, 30));
        this.jLabel1.setPreferredSize(new Dimension(200, 30));
        this.jLabel2.setFont(new Font("Arial", 0, 14));
        this.jLabel2.setText(AppLocal.getIntString("Label.DbURL"));
        this.jLabel2.setMaximumSize(new Dimension(150, 30));
        this.jLabel2.setMinimumSize(new Dimension(150, 30));
        this.jLabel2.setPreferredSize(new Dimension(200, 30));
        this.jLabel3.setFont(new Font("Arial", 0, 14));
        this.jLabel3.setText(AppLocal.getIntString("Label.DbUser"));
        this.jLabel3.setMaximumSize(new Dimension(150, 30));
        this.jLabel3.setMinimumSize(new Dimension(150, 30));
        this.jLabel3.setPreferredSize(new Dimension(200, 30));
        this.jLabel4.setFont(new Font("Arial", 0, 14));
        this.jLabel4.setText(AppLocal.getIntString("Label.DbPassword"));
        this.jLabel4.setMaximumSize(new Dimension(150, 30));
        this.jLabel4.setMinimumSize(new Dimension(150, 30));
        this.jLabel4.setPreferredSize(new Dimension(200, 30));
        this.jbtnExit.setFont(new Font("Arial", 0, 12));
        this.jbtnExit.setText(AppLocal.getIntString("Button.Exit"));
        this.jbtnExit.setMaximumSize(new Dimension(70, 33));
        this.jbtnExit.setMinimumSize(new Dimension(70, 33));
        this.jbtnExit.setPreferredSize(new Dimension(80, 45));
        this.jbtnExit.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPanelTransfer.this.jbtnExitActionPerformed(evt);
            }
        });
        GroupLayout webPanel2Layout = new GroupLayout(this.webPanel2);
        this.webPanel2.setLayout(webPanel2Layout);
        webPanel2Layout.setHorizontalGroup(webPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(webPanel2Layout.createSequentialGroup().addGroup(webPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel4, -2, -1, -2).addComponent(this.jLabel2, -2, -1, -2).addComponent(this.jLabel3, -2, -1, -2).addComponent(this.jLabel1, -2, -1, -2).addComponent(this.jLabel18, -2, -1, -2).addComponent(this.jLabel5, -2, -1, -2).addGroup(webPanel2Layout.createSequentialGroup().addContainerGap().addComponent(this.jbtnExit, -2, -1, -2))).addContainerGap()));
        webPanel2Layout.setVerticalGroup(webPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(webPanel2Layout.createSequentialGroup().addGap(40, 40, 40).addComponent(this.jLabel5, -2, -1, -2).addGap(18, 18, 18).addComponent(this.jLabel18, -2, -1, -2).addGap(18, 18, 18).addComponent(this.jLabel1, -2, -1, -2).addGap(18, 18, 18).addComponent(this.jLabel2, -2, -1, -2).addGap(18, 18, 18).addComponent(this.jLabel3, -2, -1, -2).addGap(18, 18, 18).addComponent(this.jLabel4, -2, -1, -2).addGap(18, 18, 18).addComponent(this.jbtnExit, -2, -1, -2).addContainerGap(48, Short.MAX_VALUE)));
        this.jLabel1.getAccessibleContext().setAccessibleName("DBDriver");
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.jPanel1, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.webPanel2, -2, -1, -2).addGap(18, 18, 18).addComponent(this.webPanel1, -2, -1, -2).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(11, 11, 11).addComponent(this.jPanel1, -2, -1, -2)).addComponent(this.webPanel1, -2, -1, -2).addComponent(this.webPanel2, -2, -1, -2)).addContainerGap(-1, Short.MAX_VALUE)));
    }

    private void jbtnMigrateActionPerformed(ActionEvent evt) {
        if (this.getTargetDetail().booleanValue()) {
            if (this.createTargetDB().booleanValue()) {
                try {
                    this.stmt_source = this.con_source.createStatement();
                    this.stmt_target = this.con_target.createStatement();
                    this.SQL = "SELECT * FROM attribute";
                    this.rs = this.stmt_source.executeQuery(this.SQL);
                    while (this.rs.next()) {
                        this.SQL = "INSERT INTO attribute (ID, NAME) VALUES (?, ?)";
                        this.pstmt = this.con_target.prepareStatement(this.SQL);
                        this.pstmt.setString(1, this.rs.getString("ID"));
                        this.pstmt.setString(2, this.rs.getString("NAME"));
                        this.pstmt.executeUpdate();
                    }
                    this.SQL = "SELECT * FROM attributeinstance";
                    while (this.rs.next()) {
                        this.SQL = "INSERT INTO attributeinstance (ID, ATTRIBUTEINSTANCE_ID, ATTRIBUTE_ID, VALUE) VALUES (?, ?, ?, ?)";
                        this.pstmt = this.con_target.prepareStatement(this.SQL);
                        this.pstmt.setString(1, this.rs.getString("ID"));
                        this.pstmt.setString(2, this.rs.getString("ATTRIBUTEINSTANCE_ID"));
                        this.pstmt.setString(3, this.rs.getString("ATTRIBUTE_ID"));
                        this.pstmt.setString(4, this.rs.getString("VALUE"));
                        this.pstmt.executeUpdate();
                    }
                    this.SQL = "SELECT * FROM attributeset";
                    this.rs = this.stmt_source.executeQuery(this.SQL);
                    while (this.rs.next()) {
                        this.SQL = "INSERT INTO attributeset (ID, NAME) VALUES (?, ?)";
                        this.pstmt = this.con_target.prepareStatement(this.SQL);
                        this.pstmt.setString(1, this.rs.getString("ID"));
                        this.pstmt.setString(2, this.rs.getString("NAME"));
                        this.pstmt.executeUpdate();
                    }
                    this.SQL = "SELECT * FROM attributesetinstance";
                    this.rs = this.stmt_source.executeQuery(this.SQL);
                    while (this.rs.next()) {
                        this.SQL = "INSERT INTO attributesetinstance (ID, ATTRIBUTESET_ID, DESCRIPTION) VALUES (?, ?, ?)";
                        this.pstmt = this.con_target.prepareStatement(this.SQL);
                        this.pstmt.setString(1, this.rs.getString("ID"));
                        this.pstmt.setString(2, this.rs.getString("ATTRIBUTESET_ID"));
                        this.pstmt.setString(3, this.rs.getString("DESCRIPTION"));
                        this.pstmt.executeUpdate();
                    }
                    this.SQL = "SELECT * FROM attributeuse";
                    this.rs = this.stmt_source.executeQuery(this.SQL);
                    while (this.rs.next()) {
                        this.SQL = "INSERT INTO attributeuse(ID, ATTRIBUTESET_ID, ATTRIBUTE_ID, LINENO) VALUES (?, ?, ?, ?)";
                        this.pstmt = this.con_target.prepareStatement(this.SQL);
                        this.pstmt.setString(1, this.rs.getString("ID"));
                        this.pstmt.setString(2, this.rs.getString("ATTRIBUTESET_ID"));
                        this.pstmt.setString(3, this.rs.getString("ATTRIBUTE_ID"));
                        this.pstmt.setInt(4, this.rs.getInt("LINENO"));
                        this.pstmt.executeUpdate();
                    }
                    this.SQL = "SELECT * FROM attributevalue";
                    this.rs = this.stmt_source.executeQuery(this.SQL);
                    while (this.rs.next()) {
                        this.SQL = "INSERT INTO attributevalue (ID, ATTRIBUTE_ID, VALUE) VALUES (?, ?, ?)";
                        this.pstmt = this.con_target.prepareStatement(this.SQL);
                        this.pstmt.setString(1, this.rs.getString("ID"));
                        this.pstmt.setString(2, this.rs.getString("ATTRIBUTE_ID"));
                        this.pstmt.setString(3, this.rs.getString("VALUE"));
                        this.pstmt.executeUpdate();
                    }
                    this.SQL = "SELECT * FROM breaks";
                    this.rs = this.stmt_source.executeQuery(this.SQL);
                    while (this.rs.next()) {
                        this.SQL = "INSERT INTO breaks(ID, NAME, NOTES, VISIBLE) VALUES (?, ?, ?, ?)";
                        this.pstmt = this.con_target.prepareStatement(this.SQL);
                        this.pstmt.setString(1, this.rs.getString("ID"));
                        this.pstmt.setString(2, this.rs.getString("NAME"));
                        this.pstmt.setString(3, this.rs.getString("NOTES"));
                        this.pstmt.setBoolean(4, this.rs.getBoolean("VISIBLE"));
                        this.pstmt.executeUpdate();
                    }
                    this.SQL = "SELECT * FROM categories";
                    this.rs = this.stmt_source.executeQuery(this.SQL);
                    while (this.rs.next()) {
                        this.SQL = "INSERT INTO categories(ID, NAME, PARENTID, IMAGE, TEXTTIP, CATSHOWNAME ) VALUES (?, ?, ?, ?, ?, ?)";
                        this.pstmt = this.con_target.prepareStatement(this.SQL);
                        this.pstmt.setString(1, this.rs.getString("ID"));
                        this.pstmt.setString(2, this.rs.getString("NAME"));
                        this.pstmt.setString(3, this.rs.getString("PARENTID"));
                        this.pstmt.setBytes(4, this.rs.getBytes("IMAGE"));
                        this.pstmt.setString(5, this.rs.getString("TEXTTIP"));
                        this.pstmt.setBoolean(6, this.rs.getBoolean("CATSHOWNAME"));
                        this.pstmt.executeUpdate();
                    }
                    this.SQL = "SELECT * FROM closedcash";
                    this.rs = this.stmt_source.executeQuery(this.SQL);
                    while (this.rs.next()) {
                        this.SQL = "INSERT INTO closedcash(MONEY, HOST, HOSTSEQUENCE, DATESTART, DATEEND, NOSALES ) VALUES (?, ?, ?, ?, ?, ?)";
                        this.pstmt = this.con_target.prepareStatement(this.SQL);
                        this.pstmt.setString(1, this.rs.getString("MONEY"));
                        this.pstmt.setString(2, this.rs.getString("HOST"));
                        this.pstmt.setInt(3, this.rs.getInt("HOSTSEQUENCE"));
                        System.out.println(this.rs.getTimestamp("DATESTART"));
                        this.pstmt.setTimestamp(4, this.rs.getTimestamp("DATESTART"));
                        System.out.println(this.rs.getTimestamp("DATEEND"));
                        this.pstmt.setTimestamp(5, this.rs.getTimestamp("DATEEND"));
                        this.pstmt.setInt(6, this.rs.getInt("NOSALES"));
                        this.pstmt.executeUpdate();
                    }
                    this.SQL = "SELECT * FROM csvimport";
                    this.rs = this.stmt_source.executeQuery(this.SQL);
                    while (this.rs.next()) {
                        this.SQL = "INSERT INTO csvimport (ID, ROWNUMBER, CSVERROR, REFERENCE, CODE, NAME, PRICEBUY, PRICESELL, PREVIOUSBUY, PREVIOUSSELL, CATEGORY  ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                        this.pstmt = this.con_target.prepareStatement(this.SQL);
                        this.pstmt.setString(1, this.rs.getString("ID"));
                        this.pstmt.setString(2, this.rs.getString("ROWNUMBER"));
                        this.pstmt.setString(3, this.rs.getString("CSVERROR"));
                        this.pstmt.setString(4, this.rs.getString("REFERENCE"));
                        this.pstmt.setString(5, this.rs.getString("CODE"));
                        this.pstmt.setString(6, this.rs.getString("NAME"));
                        this.pstmt.setDouble(7, this.rs.getDouble("PRICEBUY"));
                        this.pstmt.setDouble(8, this.rs.getDouble("PRICESELL"));
                        this.pstmt.setDouble(9, this.rs.getDouble("PREVIOUSBUY"));
                        this.pstmt.setDouble(10, this.rs.getDouble("PREVIOUSSELL"));
                        this.pstmt.setString(11, this.rs.getString("CATEGORY"));
                        this.pstmt.executeUpdate();
                    }
                    this.SQL = "SELECT * FROM customers";
                    this.rs = this.stmt_source.executeQuery(this.SQL);
                    while (this.rs.next()) {
                        this.SQL = "INSERT INTO customers( ID, SEARCHKEY, TAXID, NAME, TAXCATEGORY, CARD, MAXDEBT, ADDRESS, ADDRESS2, POSTAL, CITY, REGION, COUNTRY, FIRSTNAME, LASTNAME, EMAIL, PHONE, PHONE2, FAX, NOTES, VISIBLE, CURDATE, CURDEBT ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                        this.pstmt = this.con_target.prepareStatement(this.SQL);
                        this.pstmt.setString(1, this.rs.getString("ID"));
                        this.pstmt.setString(2, this.rs.getString("SEARCHKEY"));
                        this.pstmt.setString(3, this.rs.getString("TAXID"));
                        this.pstmt.setString(4, this.rs.getString("NAME"));
                        this.pstmt.setString(5, this.rs.getString("TAXCATEGORY"));
                        this.pstmt.setString(6, this.rs.getString("CARD"));
                        this.pstmt.setDouble(7, this.rs.getDouble("MAXDEBT"));
                        this.pstmt.setString(8, this.rs.getString("ADDRESS"));
                        this.pstmt.setString(9, this.rs.getString("ADDRESS2"));
                        this.pstmt.setString(10, this.rs.getString("POSTAL"));
                        this.pstmt.setString(11, this.rs.getString("CITY"));
                        this.pstmt.setString(12, this.rs.getString("REGION"));
                        this.pstmt.setString(13, this.rs.getString("COUNTRY"));
                        this.pstmt.setString(14, this.rs.getString("FIRSTNAME"));
                        this.pstmt.setString(15, this.rs.getString("LASTNAME"));
                        this.pstmt.setString(16, this.rs.getString("EMAIL"));
                        this.pstmt.setString(17, this.rs.getString("PHONE"));
                        this.pstmt.setString(18, this.rs.getString("PHONE2"));
                        this.pstmt.setString(19, this.rs.getString("FAX"));
                        this.pstmt.setString(20, this.rs.getString("NOTES"));
                        this.pstmt.setBoolean(21, this.rs.getBoolean("VISIBLE"));
                        this.pstmt.setTimestamp(22, this.rs.getTimestamp("CURDATE"));
                        this.pstmt.setDouble(23, this.rs.getDouble("CURDEBT"));
                        this.pstmt.setBytes(24, this.rs.getBytes("IMAGE"));
                        this.pstmt.setBytes(25, this.rs.getBytes("isvip"));
                        this.pstmt.setBytes(26, this.rs.getBytes("discount"));
                        this.pstmt.executeUpdate();
                    }
                    this.SQL = "SELECT * FROM draweropened";
                    this.rs = this.stmt_source.executeQuery(this.SQL);
                    while (this.rs.next()) {
                        this.SQL = "INSERT INTO draweropened(OPENDATE, NAME, TICKETID) VALUES (?, ?, ?)";
                        this.pstmt = this.con_target.prepareStatement(this.SQL);
                        this.pstmt.setString(1, this.rs.getString("OPENDATE"));
                        this.pstmt.setString(2, this.rs.getString("NAME"));
                        this.pstmt.setString(3, this.rs.getString("TICKETID"));
                        this.pstmt.executeUpdate();
                    }
                    this.SQL = "SELECT * FROM floors";
                    this.rs = this.stmt_source.executeQuery(this.SQL);
                    while (this.rs.next()) {
                        this.SQL = "INSERT INTO floors (ID, NAME, IMAGE) VALUES (?, ?, ?)";
                        this.pstmt = this.con_target.prepareStatement(this.SQL);
                        this.pstmt.setString(1, this.rs.getString("ID"));
                        this.pstmt.setString(2, this.rs.getString("NAME"));
                        this.pstmt.setBytes(3, this.rs.getBytes("IMAGE"));
                        this.pstmt.executeUpdate();
                    }
                    this.SQL = "SELECT * FROM leaves";
                    this.rs = this.stmt_source.executeQuery(this.SQL);
                    while (this.rs.next()) {
                        this.SQL = "INSERT INTO leaves (ID, PPLID, NAME, STARTDATE, ENDDATE, NOTES) VALUES (?, ?, ?, ?, ?, ?)";
                        this.pstmt = this.con_target.prepareStatement(this.SQL);
                        this.pstmt.setString(1, this.rs.getString("ID"));
                        this.pstmt.setString(2, this.rs.getString("PPLID"));
                        this.pstmt.setString(3, this.rs.getString("NAME"));
                        this.pstmt.setTimestamp(4, this.rs.getTimestamp("STARTDATE"));
                        this.pstmt.setTimestamp(5, this.rs.getTimestamp("ENDDATE"));
                        this.pstmt.setString(6, this.rs.getString("NOTES"));
                        this.pstmt.executeUpdate();
                    }
                    this.SQL = "SELECT * FROM locations";
                    this.rs = this.stmt_source.executeQuery(this.SQL);
                    while (this.rs.next()) {
                        this.SQL = "INSERT INTO locations(ID, NAME, ADDRESS) VALUES (?, ?, ?)";
                        this.pstmt = this.con_target.prepareStatement(this.SQL);
                        this.pstmt.setString(1, this.rs.getString("ID"));
                        this.pstmt.setString(2, this.rs.getString("NAME"));
                        this.pstmt.setString(3, this.rs.getString("ADDRESS"));
                        this.pstmt.executeUpdate();
                    }
                    this.SQL = "SELECT * FROM lineremoved";
                    this.rs = this.stmt_source.executeQuery(this.SQL);
                    while (this.rs.next()) {
                        this.SQL = "INSERT INTO lineremoved (REMOVEDATE, NAME, TICKETID, PRODUCTID, PRODUCTNAME, UNITS) VALUES (?, ?, ?, ?, ?, ?)";
                        this.pstmt = this.con_target.prepareStatement(this.SQL);
                        this.pstmt.setTimestamp(1, this.rs.getTimestamp("REMOVEDATE"));
                        this.pstmt.setString(2, this.rs.getString("NAME"));
                        this.pstmt.setString(3, this.rs.getString("TICKETID"));
                        this.pstmt.setString(4, this.rs.getString("PRODUCTID"));
                        this.pstmt.setString(5, this.rs.getString("PRODUCTNAME"));
                        this.pstmt.setDouble(6, this.rs.getDouble("UNITS"));
                        this.pstmt.executeUpdate();
                    }
                    this.SQL = "SELECT * FROM moorers";
                    this.rs = this.stmt_source.executeQuery(this.SQL);
                    while (this.rs.next()) {
                        this.SQL = "INSERT INTO moorers(VESSELNAME, SIZE, DAYS, POWER) VALUES (?, ?, ?, ?)";
                        this.pstmt = this.con_target.prepareStatement(this.SQL);
                        this.pstmt.setString(1, this.rs.getString("VESSELNAME"));
                        this.pstmt.setInt(2, this.rs.getInt("SIZE"));
                        this.pstmt.setInt(3, this.rs.getInt("DAYS"));
                        this.pstmt.setBoolean(4, this.rs.getBoolean("POWER"));
                        this.pstmt.executeUpdate();
                    }
                    this.SQL = "SELECT * FROM payments";
                    this.rs = this.stmt_source.executeQuery(this.SQL);
                    while (this.rs.next()) {
                        this.SQL = "INSERT INTO payments(ID, RECEIPT, PAYMENT, TOTAL, TRANSID, RETURNMSG, NOTES, CARDNAME) VALUES (?, ?, ?, ?, ?, ?, ?)";
                        this.pstmt = this.con_target.prepareStatement(this.SQL);
                        this.pstmt.setString(1, this.rs.getString("ID"));
                        this.pstmt.setString(2, this.rs.getString("RECEIPT"));
                        this.pstmt.setString(3, this.rs.getString("PAYMENT"));
                        this.pstmt.setDouble(4, this.rs.getDouble("TOTAL"));
                        this.pstmt.setString(5, this.rs.getString("TRANSID"));
                        this.pstmt.setBytes(6, this.rs.getBytes("RETURNMSG"));
                        this.pstmt.setString(7, this.rs.getString("NOTES"));
                        this.pstmt.setDouble(8, this.rs.getDouble("TENDERED"));
                        this.pstmt.setString(9, this.rs.getString("CARDNAME"));
                        this.pstmt.executeUpdate();
                    }
                    this.SQL = "SELECT * FROM people";
                    this.rs = this.stmt_source.executeQuery(this.SQL);
                    while (this.rs.next()) {
                        this.SQL = "INSERT INTO people(ID, NAME, APPPASSWORD, CARD, ROLE, VISIBLE, IMAGE) VALUES (?, ?, ?, ?, ?, ?, ?)";
                        this.pstmt = this.con_target.prepareStatement(this.SQL);
                        this.pstmt.setString(1, this.rs.getString("ID"));
                        this.pstmt.setString(2, this.rs.getString("NAME"));
                        this.pstmt.setString(3, this.rs.getString("APPPASSWORD"));
                        this.pstmt.setString(4, this.rs.getString("CARD"));
                        this.pstmt.setString(5, this.rs.getString("ROLE"));
                        this.pstmt.setBoolean(6, this.rs.getBoolean("VISIBLE"));
                        this.pstmt.setBytes(7, this.rs.getBytes("IMAGE"));
                        this.pstmt.executeUpdate();
                    }
                    this.SQL = "SELECT * FROM pickup_number";
                    this.rs = this.stmt_source.executeQuery(this.SQL);
                    while (this.rs.next()) {
                        this.SQL = "INSERT INTO pickup_number(ID) VALUES (?)";
                        this.pstmt = this.con_target.prepareStatement(this.SQL);
                        this.pstmt.setString(1, this.rs.getString("ID"));
                        this.pstmt.executeUpdate();
                    }
                    this.SQL = "SELECT * FROM places";
                    this.rs = this.stmt_source.executeQuery(this.SQL);
                    while (this.rs.next()) {
                        this.SQL = "INSERT INTO places (ID, NAME, X, Y, FLOOR, CUSTOMER, WAITER, TICKETID, TABLEMOVED) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                        this.pstmt = this.con_target.prepareStatement(this.SQL);
                        this.pstmt.setString(1, this.rs.getString("ID"));
                        this.pstmt.setString(2, this.rs.getString("NAME"));
                        this.pstmt.setInt(3, this.rs.getInt("X"));
                        this.pstmt.setInt(4, this.rs.getInt("Y"));
                        this.pstmt.setString(5, this.rs.getString("FLOOR"));
                        this.pstmt.setString(6, this.rs.getString("CUSTOMER"));
                        this.pstmt.setString(7, this.rs.getString("WAITER"));
                        this.pstmt.setString(8, this.rs.getString("TICKETID"));
                        this.pstmt.setBoolean(9, this.rs.getBoolean("TABLEMOVED"));
                        this.pstmt.executeUpdate();
                    }
                    this.SQL = "SELECT * FROM products";
                    this.rs = this.stmt_source.executeQuery(this.SQL);
                    while (this.rs.next()) {
                        this.SQL = "INSERT INTO products(ID, REFERENCE, CODE, CODETYPE, NAME, PRICEBUY, PRICESELL, CATEGORY, TAXCAT, ATTRIBUTESET_ID, STOCKCOST, STOCKVOLUME, IMAGE, ISCOM, ISSCALE, ISKITCHEN, PRINTKB, SENDSTATUS, ISSERVICE, ATTRIBUTES, DISPLAY, ISVPRICE, ISVERPATRIB, TEXTTIP, WARRANTY, STOCKUNITS) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                        this.pstmt = this.con_target.prepareStatement(this.SQL);
                        this.pstmt.setString(1, this.rs.getString("ID"));
                        this.pstmt.setString(2, this.rs.getString("REFERENCE"));
                        this.pstmt.setString(3, this.rs.getString("CODE"));
                        this.pstmt.setString(4, this.rs.getString("CODETYPE"));
                        this.pstmt.setString(5, this.rs.getString("NAME"));
                        this.pstmt.setDouble(6, this.rs.getDouble("PRICEBUY"));
                        this.pstmt.setDouble(7, this.rs.getDouble("PRICESELL"));
                        this.pstmt.setString(8, this.rs.getString("CATEGORY"));
                        this.pstmt.setString(9, this.rs.getString("TAXCAT"));
                        this.pstmt.setString(10, this.rs.getString("ATTRIBUTESET_ID"));
                        this.pstmt.setDouble(11, this.rs.getDouble("STOCKCOST"));
                        this.pstmt.setDouble(12, this.rs.getDouble("STOCKVOLUME"));
                        this.pstmt.setBytes(13, this.rs.getBytes("IMAGE"));
                        this.pstmt.setBoolean(14, this.rs.getBoolean("ISCOM"));
                        this.pstmt.setBoolean(15, this.rs.getBoolean("ISSCALE"));
                        this.pstmt.setBoolean(16, this.rs.getBoolean("ISKITCHEN"));
                        this.pstmt.setBoolean(17, this.rs.getBoolean("PRINTKB"));
                        this.pstmt.setBoolean(18, this.rs.getBoolean("SENDSTATUS"));
                        this.pstmt.setBoolean(19, this.rs.getBoolean("ISSERVICE"));
                        this.pstmt.setBytes(21, this.rs.getBytes("ATTRIBUTES"));
                        this.pstmt.setString(20, this.rs.getString("DISPLAY"));
                        this.pstmt.setBoolean(22, this.rs.getBoolean("ISVPRICE"));
                        this.pstmt.setBoolean(23, this.rs.getBoolean("ISVERPATRIB"));
                        this.pstmt.setString(24, this.rs.getString("TEXTTIP"));
                        this.pstmt.setBoolean(25, this.rs.getBoolean("WARRANTY"));
                        this.pstmt.setDouble(26, this.rs.getDouble("STOCKUNITS"));
                        if ("xxx999_999xxx_x9x9x9".equals(this.rs.getString(1))) continue;
                        this.pstmt.executeUpdate();
                    }
                    this.SQL = "SELECT * FROM products_cat";
                    this.rs = this.stmt_source.executeQuery(this.SQL);
                    while (this.rs.next()) {
                        this.SQL = "INSERT INTO products_cat(PRODUCT, CATORDER) VALUES (?, ?)";
                        this.pstmt = this.con_target.prepareStatement(this.SQL);
                        this.pstmt.setString(1, this.rs.getString("PRODUCT"));
                        this.pstmt.setInt(2, this.rs.getInt("CATORDER"));
                        if ("xxx999_999xxx_x9x9x9".equals(this.rs.getString(1))) continue;
                        this.pstmt.executeUpdate();
                    }
                    this.SQL = "SELECT * FROM products_com";
                    this.rs = this.stmt_source.executeQuery(this.SQL);
                    while (this.rs.next()) {
                        this.SQL = "INSERT INTO products_com(ID, PRODUCT, PRODUCT2 ) VALUES (?, ?, ?)";
                        this.pstmt = this.con_target.prepareStatement(this.SQL);
                        this.pstmt.setString(1, this.rs.getString("ID"));
                        this.pstmt.setString(2, this.rs.getString("PRODUCT"));
                        this.pstmt.setString(3, this.rs.getString("PRODUCT2"));
                        this.pstmt.executeUpdate();
                    }
                    this.SQL = "SELECT * FROM receipts";
                    this.rs = this.stmt_source.executeQuery(this.SQL);
                    while (this.rs.next()) {
                        this.SQL = "INSERT INTO receipts(ID, MONEY, DATENEW, ATTRIBUTES, PERSON ) VALUES (?, ?, ?, ?, ?)";
                        this.pstmt = this.con_target.prepareStatement(this.SQL);
                        this.pstmt.setString(1, this.rs.getString("ID"));
                        this.pstmt.setString(2, this.rs.getString("MONEY"));
                        this.pstmt.setTimestamp(3, this.rs.getTimestamp("DATENEW"));
                        this.pstmt.setBytes(4, this.rs.getBytes("ATTRIBUTES"));
                        this.pstmt.setString(5, this.rs.getString("PERSON"));
                        this.pstmt.executeUpdate();
                    }
                    this.SQL = "SELECT * FROM reservation_customers";
                    this.rs = this.stmt_source.executeQuery(this.SQL);
                    while (this.rs.next()) {
                        this.SQL = "INSERT INTO reservation_customers(ID, CUSTOMER) VALUES (?, ?)";
                        this.pstmt = this.con_target.prepareStatement(this.SQL);
                        this.pstmt.setString(1, this.rs.getString("ID"));
                        this.pstmt.setString(2, this.rs.getString("CUSTOMER"));
                        this.pstmt.executeUpdate();
                    }
                    this.SQL = "SELECT * FROM reservations";
                    this.rs = this.stmt_source.executeQuery(this.SQL);
                    while (this.rs.next()) {
                        this.SQL = "INSERT INTO reservations(ID, CREATED, DATENEW, TITLE, CHAIRS, ISDONE, DESCRIPTION ) VALUES (?, ?, ?, ?, ?, ?, ?)";
                        this.pstmt = this.con_target.prepareStatement(this.SQL);
                        this.pstmt.setString(1, this.rs.getString("ID"));
                        this.pstmt.setTimestamp(2, this.rs.getTimestamp("CREATED"));
                        this.pstmt.setTimestamp(3, this.rs.getTimestamp("DATENEW"));
                        this.pstmt.setString(4, this.rs.getString("TITLE"));
                        this.pstmt.setInt(5, this.rs.getInt("CHAIRS"));
                        this.pstmt.setBoolean(6, this.rs.getBoolean("ISDONE"));
                        this.pstmt.setString(7, this.rs.getString("DESCRIPTION"));
                        this.pstmt.executeUpdate();
                    }
                    this.SQL = "SELECT * FROM resources";
                    this.rs = this.stmt_source.executeQuery(this.SQL);
                    while (this.rs.next()) {
                        this.SQL = "INSERT INTO resources(ID, NAME, RESTYPE, CONTENT) VALUES (?, ?, ?, ?)";
                        this.pstmt = this.con_target.prepareStatement(this.SQL);
                        this.pstmt.setString(1, this.rs.getString("ID"));
                        this.pstmt.setString(2, this.rs.getString("NAME"));
                        this.pstmt.setInt(3, this.rs.getInt("RESTYPE"));
                        this.pstmt.setBytes(4, this.rs.getBytes("CONTENT"));
                        this.pstmt.executeUpdate();
                    }
                    this.SQL = "SELECT * FROM roles";
                    this.rs = this.stmt_source.executeQuery(this.SQL);
                    while (this.rs.next()) {
                        this.SQL = "INSERT INTO roles(ID, NAME, PERMISSIONS ) VALUES (?, ?, ?)";
                        this.pstmt = this.con_target.prepareStatement(this.SQL);
                        this.pstmt.setString(1, this.rs.getString("ID"));
                        this.pstmt.setString(2, this.rs.getString("NAME"));
                        this.pstmt.setBytes(3, this.rs.getBytes("PERMISSIONS"));
                        this.pstmt.executeUpdate();
                    }
                    this.SQL = "SELECT * FROM sharedtickets";
                    this.rs = this.stmt_source.executeQuery(this.SQL);
                    while (this.rs.next()) {
                        this.SQL = "INSERT INTO sharedtickets(ID, NAME, CONTENT, APPUSER, PICKUPID ) VALUES (?, ?, ?, ?, ?)";
                        this.pstmt = this.con_target.prepareStatement(this.SQL);
                        this.pstmt.setString(1, this.rs.getString("ID"));
                        this.pstmt.setString(2, this.rs.getString("NAME"));
                        this.pstmt.setBytes(3, this.rs.getBytes("CONTENT"));
                        this.pstmt.setBytes(4, this.rs.getBytes("APPUSER"));
                        this.pstmt.setInt(5, this.rs.getInt("PICKUPID"));
                        this.pstmt.executeUpdate();
                    }
                    this.SQL = "SELECT * FROM shift_breaks";
                    this.rs = this.stmt_source.executeQuery(this.SQL);
                    while (this.rs.next()) {
                        this.SQL = "INSERT INTO shift_breaks(ID, SHIFTID, BREAKID, STARTTIME, ENDTIME ) VALUES (?, ?, ?, ?, ?)";
                        this.pstmt = this.con_target.prepareStatement(this.SQL);
                        this.pstmt.setString(1, this.rs.getString("ID"));
                        this.pstmt.setString(2, this.rs.getString("SHIFTID"));
                        this.pstmt.setString(3, this.rs.getString("BREAKID"));
                        this.pstmt.setTimestamp(4, this.rs.getTimestamp("STARTTIME"));
                        this.pstmt.setTimestamp(5, this.rs.getTimestamp("ENDTIME"));
                        this.pstmt.executeUpdate();
                    }
                    this.SQL = "SELECT * FROM shifts";
                    this.rs = this.stmt_source.executeQuery(this.SQL);
                    while (this.rs.next()) {
                        this.SQL = "INSERT INTO shifts(ID, STARTSHIFT, ENDSHIFT, PPLID ) VALUES (?, ?, ?, ?)";
                        this.pstmt = this.con_target.prepareStatement(this.SQL);
                        this.pstmt.setString(1, this.rs.getString("ID"));
                        this.pstmt.setTimestamp(2, this.rs.getTimestamp("STARTSHIFT"));
                        this.pstmt.setTimestamp(3, this.rs.getTimestamp("ENDSHIFT"));
                        this.pstmt.setString(4, this.rs.getString("PPLID"));
                        this.pstmt.executeUpdate();
                    }
                    this.SQL = "SELECT * FROM stockcurrent";
                    this.rs = this.stmt_source.executeQuery(this.SQL);
                    while (this.rs.next()) {
                        this.SQL = "INSERT INTO stockcurrent(LOCATION, PRODUCT, ATTRIBUTESETINSTANCE_ID, UNITS ) VALUES (?, ?, ?, ?)";
                        this.pstmt = this.con_target.prepareStatement(this.SQL);
                        this.pstmt.setString(1, this.rs.getString("LOCATION"));
                        this.pstmt.setString(2, this.rs.getString("PRODUCT"));
                        this.pstmt.setString(3, this.rs.getString("ATTRIBUTESETINSTANCE_ID"));
                        this.pstmt.setDouble(4, this.rs.getDouble("UNITS"));
                        this.pstmt.executeUpdate();
                    }
                    this.SQL = "SELECT * FROM stockdiary";
                    this.rs = this.stmt_source.executeQuery(this.SQL);
                    while (this.rs.next()) {
                        this.SQL = "INSERT INTO stockdiary(ID, DATENEW, REASON, LOCATION,  PRODUCT, ATTRIBUTESETINSTANCE_ID, UNITS, PRICE, APPUSER ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                        this.pstmt = this.con_target.prepareStatement(this.SQL);
                        this.pstmt.setString(1, this.rs.getString("ID"));
                        this.pstmt.setTimestamp(2, this.rs.getTimestamp("DATENEW"));
                        this.pstmt.setInt(3, this.rs.getInt("REASON"));
                        this.pstmt.setString(4, this.rs.getString("LOCATION"));
                        this.pstmt.setString(5, this.rs.getString("PRODUCT"));
                        this.pstmt.setString(6, this.rs.getString("ATTRIBUTESETINSTANCE_ID"));
                        this.pstmt.setDouble(7, this.rs.getDouble("UNITS"));
                        this.pstmt.setDouble(8, this.rs.getDouble("PRICE"));
                        this.pstmt.setString(9, this.rs.getString("APPUSER"));
                        this.pstmt.executeUpdate();
                    }
                    this.SQL = "SELECT * FROM stocklevel";
                    this.rs = this.stmt_source.executeQuery(this.SQL);
                    while (this.rs.next()) {
                        this.SQL = "INSERT INTO stocklevel(ID, LOCATION, PRODUCT, STOCKSECURITY, STOCKMAXIMUM ) VALUES (?, ?, ?, ?, ?)";
                        this.pstmt = this.con_target.prepareStatement(this.SQL);
                        this.pstmt.setString(1, this.rs.getString("ID"));
                        this.pstmt.setString(2, this.rs.getString("LOCATION"));
                        this.pstmt.setString(3, this.rs.getString("PRODUCT"));
                        this.pstmt.setDouble(4, this.rs.getDouble("STOCKSECURITY"));
                        this.pstmt.setDouble(5, this.rs.getDouble("STOCKMAXIMUM"));
                        this.pstmt.executeUpdate();
                    }
                    this.SQL = "SELECT * FROM taxcategories";
                    this.rs = this.stmt_source.executeQuery(this.SQL);
                    while (this.rs.next()) {
                        this.SQL = "INSERT INTO taxcategories(ID, NAME) VALUES (?, ?)";
                        this.pstmt = this.con_target.prepareStatement(this.SQL);
                        this.pstmt.setString(1, this.rs.getString("ID"));
                        this.pstmt.setString(2, this.rs.getString("NAME"));
                        this.pstmt.executeUpdate();
                    }
                    this.SQL = "SELECT * FROM taxcustcategories";
                    this.rs = this.stmt_source.executeQuery(this.SQL);
                    while (this.rs.next()) {
                        this.SQL = "INSERT INTO taxcustcategories(ID, NAME) VALUES (?, ?)";
                        this.pstmt = this.con_target.prepareStatement(this.SQL);
                        this.pstmt.setString(1, this.rs.getString("ID"));
                        this.pstmt.setString(2, this.rs.getString("NAME"));
                        this.pstmt.executeUpdate();
                    }
                    this.SQL = "SELECT * FROM taxes";
                    this.rs = this.stmt_source.executeQuery(this.SQL);
                    while (this.rs.next()) {
                        this.SQL = "INSERT INTO taxes(ID, NAME, CATEGORY, CUSTCATEGORY, PARENTID, RATE, RATECASCADE, RATEORDER ) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                        this.pstmt = this.con_target.prepareStatement(this.SQL);
                        this.pstmt.setString(1, this.rs.getString("ID"));
                        this.pstmt.setString(2, this.rs.getString("NAME"));
                        this.pstmt.setString(3, this.rs.getString("CATEGORY"));
                        this.pstmt.setString(4, this.rs.getString("CUSTCATEGORY"));
                        this.pstmt.setString(5, this.rs.getString("PARENTID"));
                        this.pstmt.setDouble(6, this.rs.getDouble("RATE"));
                        this.pstmt.setBoolean(7, this.rs.getBoolean("RATECASCADE"));
                        this.pstmt.setInt(8, this.rs.getInt("RATEORDER"));
                        this.pstmt.executeUpdate();
                    }
                    this.SQL = "SELECT * FROM taxlines";
                    this.rs = this.stmt_source.executeQuery(this.SQL);
                    while (this.rs.next()) {
                        this.SQL = "INSERT INTO taxlines(ID, RECEIPT, TAXID, BASE, AMOUNT ) VALUES (?, ?, ?, ?, ?)";
                        this.pstmt = this.con_target.prepareStatement(this.SQL);
                        this.pstmt.setString(1, this.rs.getString("ID"));
                        this.pstmt.setString(2, this.rs.getString("RECEIPT"));
                        this.pstmt.setString(3, this.rs.getString("TAXID"));
                        this.pstmt.setDouble(4, this.rs.getDouble("BASE"));
                        this.pstmt.setDouble(5, this.rs.getDouble("AMOUNT"));
                        this.pstmt.executeUpdate();
                    }
                    this.SQL = "SELECT * FROM thirdparties";
                    this.rs = this.stmt_source.executeQuery(this.SQL);
                    while (this.rs.next()) {
                        this.SQL = "INSERT INTO thirdparties(ID, CIF, NAME, ADDRESS, CONTACTCOMM, CONTACTFACT, PAYRULE, FAXNUMBER, PHONENUMBER, MOBILENUMBER, EMAIL, WEBPAGE, NOTES  ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                        this.pstmt = this.con_target.prepareStatement(this.SQL);
                        this.pstmt.setString(1, this.rs.getString("ID"));
                        this.pstmt.setString(2, this.rs.getString("CIF"));
                        this.pstmt.setString(3, this.rs.getString("NAME"));
                        this.pstmt.setString(4, this.rs.getString("ADDRESS"));
                        this.pstmt.setString(5, this.rs.getString("CONTACTCOMM"));
                        this.pstmt.setString(6, this.rs.getString("CONTACTFACT"));
                        this.pstmt.setString(7, this.rs.getString("PAYRULE"));
                        this.pstmt.setString(8, this.rs.getString("FAXNUMBER"));
                        this.pstmt.setString(9, this.rs.getString("PHONENUMBER"));
                        this.pstmt.setString(10, this.rs.getString("MOBILENUMBER"));
                        this.pstmt.setString(11, this.rs.getString("EMAIL"));
                        this.pstmt.setString(12, this.rs.getString("WEBPAGE"));
                        this.pstmt.setString(13, this.rs.getString("NOTES"));
                        this.pstmt.executeUpdate();
                    }
                    this.SQL = "SELECT * FROM ticketlines";
                    this.rs = this.stmt_source.executeQuery(this.SQL);
                    while (this.rs.next()) {
                        this.SQL = "INSERT INTO ticketlines(TICKET, LINE, PRODUCT, ATTRIBUTESETINSTANCE_ID, UNITS, PRICE, TAXID, ATTRIBUTES ) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                        this.pstmt = this.con_target.prepareStatement(this.SQL);
                        this.pstmt.setString(1, this.rs.getString("TICKET"));
                        this.pstmt.setInt(2, this.rs.getInt("LINE"));
                        this.pstmt.setString(3, this.rs.getString("PRODUCT"));
                        this.pstmt.setString(4, this.rs.getString("ATTRIBUTESETINSTANCE_ID"));
                        this.pstmt.setDouble(5, this.rs.getDouble("UNITS"));
                        this.pstmt.setDouble(6, this.rs.getDouble("PRICE"));
                        this.pstmt.setString(7, this.rs.getString("TAXID"));
                        this.pstmt.setBytes(8, this.rs.getBytes("ATTRIBUTES"));
                        this.pstmt.executeUpdate();
                    }
                    this.SQL = "SELECT * FROM tickets";
                    this.rs = this.stmt_source.executeQuery(this.SQL);
                    while (this.rs.next()) {
                        this.SQL = "INSERT INTO tickets(ID, TICKETTYPE, TICKETID, PERSON, CUSTOMER, STATUS ) VALUES (?, ?, ?, ?, ?, ?)";
                        this.pstmt = this.con_target.prepareStatement(this.SQL);
                        this.pstmt.setString(1, this.rs.getString("ID"));
                        this.pstmt.setInt(2, this.rs.getInt("TICKETTYPE"));
                        this.pstmt.setInt(3, this.rs.getInt("TICKETID"));
                        this.pstmt.setString(4, this.rs.getString("PERSON"));
                        this.pstmt.setString(5, this.rs.getString("CUSTOMER"));
                        this.pstmt.setInt(6, this.rs.getInt("STATUS"));
                        this.pstmt.executeUpdate();
                    }
                    this.SQL = "SELECT * FROM ticketsnum";
                    this.rs = this.stmt_source.executeQuery(this.SQL);
                    while (this.rs.next()) {
                        this.ticketsnum = this.rs.getString("ID");
                    }
                    this.SQL = "SELECT * FROM ticketsnum_payment";
                    this.rs = this.stmt_source.executeQuery(this.SQL);
                    while (this.rs.next()) {
                        this.ticketsnumPayment = this.rs.getString("ID");
                    }
                    this.SQL = "SELECT * FROM ticketsnum_refund";
                    this.rs = this.stmt_source.executeQuery(this.SQL);
                    while (this.rs.next()) {
                        this.ticketsnumRefund = this.rs.getString("ID");
                    }
                    this.SQL = "UPDATE ticketsnum SET ID=" + this.ticketsnum;
                    this.stmt_target.executeUpdate(this.SQL);
                    this.SQL = "UPDATE ticketsnum__payment SET ID=" + this.ticketsnumPayment;
                    this.stmt_target.executeUpdate(this.SQL);
                    this.SQL = "UPDATE ticketsnum_refund SET ID=" + this.ticketsnumRefund;
                    this.stmt_target.executeUpdate(this.SQL);
                    this.addFKeys();
                    if ("MySQL".equals(this.sDB_target)) {
                        this.config.setProperty("db.engine", "MySQL");
                    } else {
                        this.config.setProperty("db.engine", "PostgreSQL");
                    }
                    this.config.setProperty("db.driverlib", this.txtDBDriverLib.getText());
                    this.config.setProperty("db.driver", this.txtDBDriver.getText());
                    this.config.setProperty("db.URL", this.txtDBURL.getText());
                    this.config.setProperty("db.user", this.txtDBUser.getText());
                    AltEncrypter cypher = new AltEncrypter("cypherkey" + this.txtDBUser.getText());
                    this.config.setProperty("db.password", "crypt:" + cypher.encrypt(new String(this.txtDBPass.getPassword())));
                    this.dirty.setDirty(false);
                    for (PanelConfig c : this.m_panelconfig) {
                        c.saveProperties(this.config);
                    }
                    try {
                        this.config.save();
                        JOptionPane.showMessageDialog(this, AppLocal.getIntString("message.restartchanges"), AppLocal.getIntString("message.title"), 1);
                    }
                    catch (IOException e) {
                        JMessageDialog.showMessage(this, new MessageInf(-33554432, AppLocal.getIntString("message.cannotsaveconfig"), e));
                    }
                    JOptionPane.showMessageDialog(this, "Migration complete.");
                    this.jbtnMigrate.setEnabled(false);
                }
                catch (HeadlessException | SQLException e) {
                    JMessageDialog.showMessage(this, new MessageInf(-33554432, this.SQL, e));
                }
            } else {
                JFrame frame = new JFrame();
                JOptionPane.showMessageDialog(frame, AppLocal.getIntString("message.migratenotsupported"), AppLocal.getIntString("message.migratemessage"), 2);
            }
        }
    }

    private void jbtnExitActionPerformed(ActionEvent evt) {
        this.deactivate();
        System.exit(0);
    }

    private void jButtonTestjButtonTestConnectionActionPerformed(ActionEvent evt) {
        try {
            boolean isValid;
            String driverlib = this.txtDBDriverLib.getText();
            String driver = this.txtDBDriver.getText();
            String url = this.txtDBURL.getText();
            String user = this.txtDBUser.getText();
            String password = new String(this.txtDBPass.getPassword());
            URLClassLoader cloader = new URLClassLoader(new URL[]{new File(driverlib).toURI().toURL()});
            DriverManager.registerDriver(new DriverWrapper((Driver)Class.forName(driver, true, cloader).newInstance()));
            Session session_source = new Session(url, user, password);
            Connection connection = session_source.getConnection();
            boolean bl = isValid = connection == null ? false : connection.isValid(1000);
            if (isValid) {
                JOptionPane.showMessageDialog(this, AppLocal.getIntString("message.databaseconnectsuccess"), "Connection Test", 1);
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
}

