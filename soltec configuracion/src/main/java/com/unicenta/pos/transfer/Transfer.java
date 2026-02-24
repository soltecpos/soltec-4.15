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
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
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
import javax.swing.BorderFactory;
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
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.SwingWorker;

public final class Transfer
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
    private String source_version;
    private String ticketsnum;
    private String ticketsnumRefund;
    private String ticketsnumPayment;
    private String targetCreate = "";
    private String targetFKadd = "";
    private String targetFKdrop = "";
    ArrayList<String> stringList = new ArrayList();
    public String strOut = "";
    private JComboBox<String> cbSource;
    private JButton jButton2;
    private JButton jButton3;
    private JLabel jLabel1;
    private JLabel jLabel10;
    private JLabel jLabel11;
    private JLabel jLabel18;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JLabel jLabel8;
    private JLabel jLabel9;
    private JPanel jPanel1;
    private JScrollPane jScrollPane1;
    private JButton jbtnDbDriverLib;
    private JButton jbtnExit;
    private JButton jbtnTest;
    private JButton jbtnTransfer;
    private JLabel jlblSource;
    private JLabel jlblVersion;
    private JTextField jtxtDbDriver;
    private JTextField jtxtDbDriverLib;
    private JTextField jtxtDbName;
    private JTextField jtxtDbURL;
    private JPasswordField txtDbPass;
    private JTextField txtDbUser;
    private JTextArea txtOut;
    private JProgressBar webPBar;
    private JPanel webPanel2;

    public Transfer(AppView oApp) {
        this(oApp.getProperties());
    }

    public Transfer(AppProperties props) {
        this.initComponents();
        this.config = new AppConfig(props.getConfigFile());
        this.m_props = props;
        this.m_panelconfig = new ArrayList<PanelConfig>();
        this.config.load();
        this.m_panelconfig.stream().forEach(c -> c.loadProperties(this.config));
        this.jtxtDbDriverLib.getDocument().addDocumentListener(this.dirty);
        this.jtxtDbDriver.getDocument().addDocumentListener(this.dirty);
        this.jtxtDbURL.getDocument().addDocumentListener(this.dirty);
        this.txtDbPass.getDocument().addDocumentListener(this.dirty);
        this.txtDbUser.getDocument().addDocumentListener(this.dirty);
        this.txtOut.getDocument().addDocumentListener(this.dirty);
        this.jbtnDbDriverLib.addActionListener(new DirectoryEvent(this.jtxtDbDriverLib));
        this.cbSource.addActionListener(this.dirty);
        this.cbSource.addItem("Derby");
        this.cbSource.addItem("MySQL");
        this.cbSource.addItem("PostgreSQL");
        this.stringList.add("Transfer Ready...\n");
        this.txtOut.setText(this.stringList.get(0));
        this.webPBar.setIndeterminate(true);
        this.webPBar.setStringPainted(true);
        this.webPBar.setString("Waiting...");
        this.webPBar.setVisible(false);
        this.jbtnTransfer.setEnabled(false);
        this.jbtnExit.setVisible(false);
    }

    @Override
    public JComponent getComponent() {
        return this;
    }

    @Override
    public String getTitle() {
        return AppLocal.getIntString("Menu.Transfer");
    }

    public Boolean getSource() {
        String db_user2 = this.txtDbUser.getText();
        String db_url2 = this.jtxtDbURL.getText();
        char[] pass = this.txtDbPass.getPassword();
        String db_password2 = new String(pass);
        Properties connectionProps = new Properties();
        connectionProps.put("user", db_user2);
        connectionProps.put("password", db_password2);
        try {
            Class.forName(this.jtxtDbDriver.getText());
            URLClassLoader cloader = new URLClassLoader(new URL[]{new File(this.jtxtDbDriverLib.getText()).toURI().toURL()});
            DriverManager.registerDriver(new DriverWrapper((Driver)Class.forName(this.jtxtDbDriver.getText(), true, cloader).newInstance()));
            this.con_source = DriverManager.getConnection(db_url2, db_user2, db_password2);
            this.session_source = new Session(db_url2, db_user2, db_password2);
            this.sDB_source = this.con_source.getMetaData().getDatabaseProductName();
            this.txtOut.append("Connected to Source OK\n");
            this.jbtnTransfer.setEnabled(true);
            return true;
        }
        catch (ClassNotFoundException | IllegalAccessException | InstantiationException | MalformedURLException | SQLException e) {
            JMessageDialog.showMessage(this, new MessageInf(-16777216, AppLocal.getIntString("database.UnableToConnect"), e));
            return false;
        }
    }

    public Boolean createTargetDB() {
        block6: {
            this.targetCreate = "/com/openbravo/pos/scripts/" + this.sDB_target + "-create-transfer.sql";
            this.targetFKadd = "/com/openbravo/pos/scripts/MySQL-FKeys.sql";
            this.targetFKdrop = "/com/openbravo/pos/scripts/MySQL-dropFKeys.sql";
            if ("".equals(this.targetCreate)) {
                return false;
            }
            try {
                BatchSentenceResource bsentence = new BatchSentenceResource(this.session_target, this.targetCreate);
                bsentence = new BatchSentenceResource(this.session_target, this.targetFKdrop);
                bsentence.putParameter("APP_ID", Matcher.quoteReplacement("soltecpos"));
                bsentence.putParameter("APP_NAME", Matcher.quoteReplacement("SOLTEC POS"));
                bsentence.putParameter("APP_VERSION", Matcher.quoteReplacement("4.15"));
                List l = bsentence.list();
                if (l.size() > 0) {
                    JMessageDialog.showMessage(this, new MessageInf(-33554432, AppLocal.getIntString("transfer.warning"), l.toArray(new Throwable[l.size()])));
                    break block6;
                }
                this.txtOut.append("Connected to Target OK\n");
                this.txtOut.revalidate();
                this.txtOut.repaint();
            }
            catch (BasicException e) {
                JMessageDialog.showMessage(this, new MessageInf(-16777216, AppLocal.getIntString("transfer.warningnodefault"), e));
                this.session_source.close();
            }
        }
        return true;
    }

    public Boolean FKeys() {
        block6: {
            if ("".equals(this.targetFKadd)) {
                return false;
            }
            try {
                this.txtOut.append("Adding Foreign Keys\n");
                this.webPBar.setString("Adding Keys...");
                BatchSentenceResource bsentence = new BatchSentenceResource(this.session_target, this.targetFKadd);
                List l = bsentence.list();
                if (l.size() > 0) {
                    JMessageDialog.showMessage(this, new MessageInf(-33554432, AppLocal.getIntString("transfer.warning"), l.toArray(new Throwable[l.size()])));
                    this.txtOut.append("Foreign Key error\n");
                    break block6;
                }
                this.txtOut.append("Foreign Keys completed\n");
            }
            catch (BasicException e) {
                JMessageDialog.showMessage(this, new MessageInf(-16777216, AppLocal.getIntString("database.ScriptNotFound"), e));
                this.session_source.close();
            }
        }
        this.txtOut.revalidate();
        this.txtOut.repaint();
        return true;
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
            this.session_target = AppViewConnection.createSession(this.m_props);
            this.con_target = DriverManager.getConnection(db_url, db_user, db_password);
            this.sDB_target = this.con_target.getMetaData().getDatabaseProductName();
            this.jlblSource.setText(this.con_target.getCatalog());
        }
        catch (BasicException | SQLException e) {
            JMessageDialog.showMessage(this, new MessageInf(-16777216, AppLocal.getIntString("database.UnableToConnect"), e));
        }
    }

    @Override
    public boolean deactivate() {
        return true;
    }

    public void doTransfer() {
        this.webPBar.setString("Starting...");
        this.webPBar.setVisible(true);
        String Dbtname = "";
        if (this.getSource().booleanValue()) {
            this.txtOut.setVisible(true);
            this.txtOut.append("Transfer Started...\n");
            if (this.createTargetDB().booleanValue()) {
                this.jbtnTransfer.setEnabled(false);
                try {
                    this.stmt_source = this.con_source.createStatement();
                    this.stmt_target = this.con_target.createStatement();
                    this.webPBar.setString("Running...");
                    Dbtname = "attribute";
                    ResultSet rss = this.con_source.getMetaData().getTables(null, null, Dbtname, null);
                    this.txtOut.append("Attribute\n");
                    this.SQL = "SELECT * FROM attribute";
                    this.rs = this.stmt_source.executeQuery(this.SQL);
                    while (this.rs.next()) {
                        this.SQL = "INSERT INTO attribute (ID, NAME) VALUES (?, ?)";
                        this.pstmt = this.con_target.prepareStatement(this.SQL);
                        this.pstmt.setString(1, this.rs.getString("ID"));
                        this.pstmt.setString(2, this.rs.getString("NAME"));
                        this.pstmt.executeUpdate();
                    }
                    Dbtname = "attributeinstance";
                    rss = this.con_source.getMetaData().getTables(null, null, Dbtname, null);
                    this.txtOut.append("Attributeinstance\n");
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
                    Dbtname = "attributeset";
                    rss = this.con_source.getMetaData().getTables(null, null, Dbtname, null);
                    this.txtOut.append("Attributeset\n");
                    this.SQL = "SELECT * FROM attributeset";
                    this.rs = this.stmt_source.executeQuery(this.SQL);
                    while (this.rs.next()) {
                        this.SQL = "INSERT INTO attributeset (ID, NAME) VALUES (?, ?)";
                        this.pstmt = this.con_target.prepareStatement(this.SQL);
                        this.pstmt.setString(1, this.rs.getString("ID"));
                        this.pstmt.setString(2, this.rs.getString("NAME"));
                        this.pstmt.executeUpdate();
                    }
                    Dbtname = "attributesetinstance";
                    rss = this.con_source.getMetaData().getTables(null, null, Dbtname, null);
                    this.txtOut.append("Attributesetinstance\n");
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
                    Dbtname = "attributeuse";
                    rss = this.con_source.getMetaData().getTables(null, null, Dbtname, null);
                    this.txtOut.append("Attributeuse\n");
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
                    Dbtname = "attributevalue";
                    rss = this.con_source.getMetaData().getTables(null, null, Dbtname, null);
                    this.txtOut.append("Attributevalue\n");
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
                    Dbtname = "breaks";
                    rss = this.con_source.getMetaData().getTables(null, null, Dbtname, null);
                    this.txtOut.append("Breaks\n");
                    this.SQL = "SELECT * FROM breaks";
                    this.rs = this.stmt_source.executeQuery(this.SQL);
                    while (this.rs.next()) {
                        this.SQL = "INSERT INTO breaks(ID, NAME, VISIBLE, NOTES) VALUES (?, ?, ?, ?)";
                        this.pstmt = this.con_target.prepareStatement(this.SQL);
                        this.pstmt.setString(1, this.rs.getString("ID"));
                        this.pstmt.setString(2, this.rs.getString("NAME"));
                        this.pstmt.setString(3, this.rs.getString("VISIBLE"));
                        this.pstmt.setBoolean(4, this.rs.getBoolean("NOTES"));
                        this.pstmt.executeUpdate();
                    }
                    Dbtname = "categories";
                    rss = this.con_source.getMetaData().getTables(null, null, Dbtname, null);
                    this.txtOut.append("Categories\n");
                    this.SQL = "SELECT * FROM categories";
                    this.rs = this.stmt_source.executeQuery(this.SQL);
                    while (this.rs.next()) {
                        this.SQL = "INSERT INTO categories(ID, NAME, PARENTID, IMAGE, TEXTTIP, CATSHOWNAME) VALUES (?, ?, ?, ?, ?, ?)";
                        this.pstmt = this.con_target.prepareStatement(this.SQL);
                        this.pstmt.setString(1, this.rs.getString("ID"));
                        this.pstmt.setString(2, this.rs.getString("NAME"));
                        this.pstmt.setString(3, this.rs.getString("PARENTID"));
                        this.pstmt.setBytes(4, this.rs.getBytes("IMAGE"));
                        this.pstmt.setString(5, this.rs.getString("TEXTTIP"));
                        this.pstmt.setString(6, this.rs.getString("CATSHOWNAME"));
                        this.pstmt.executeUpdate();
                    }
                    Dbtname = "closedcash";
                    rss = this.con_source.getMetaData().getTables(null, null, Dbtname, null);
                    this.txtOut.append("ClosedCash\n");
                    this.SQL = "DELETE FROM closedcash";
                    this.pstmt = this.con_target.prepareStatement(this.SQL);
                    this.pstmt.executeUpdate();
                    this.SQL = "SELECT * FROM closedcash";
                    this.rs = this.stmt_source.executeQuery(this.SQL);
                    while (this.rs.next()) {
                        this.SQL = "INSERT INTO closedcash(MONEY, HOST, HOSTSEQUENCE, DATESTART, DATEEND, NOSALES) VALUES (?, ?, ?, ?, ?, ?)";
                        this.pstmt = this.con_target.prepareStatement(this.SQL);
                        this.pstmt.setString(1, this.rs.getString("MONEY"));
                        this.pstmt.setString(2, this.rs.getString("HOST"));
                        this.pstmt.setInt(3, this.rs.getInt("HOSTSEQUENCE"));
                        this.pstmt.setTimestamp(4, this.rs.getTimestamp("DATESTART"));
                        this.pstmt.setTimestamp(5, this.rs.getTimestamp("DATEEND"));
                        this.pstmt.setInt(6, this.rs.getInt("NOSALES"));
                        this.pstmt.executeUpdate();
                    }
                    Dbtname = "customers";
                    rss = this.con_source.getMetaData().getTables(null, null, Dbtname, null);
                    this.txtOut.append("Customers\n");
                    this.SQL = "SELECT * FROM customers";
                    this.rs = this.stmt_source.executeQuery(this.SQL);
                    while (this.rs.next()) {
                        this.SQL = "INSERT INTO customers(ID, SEARCHKEY, TAXID, NAME, TAXCATEGORY, CARD, MAXDEBT,ADDRESS, ADDRESS2, POSTAL, CITY, REGION, COUNTRY, FIRSTNAME, LASTNAME, EMAIL, PHONE, PHONE2, FAX, NOTES, VISIBLE, CURDATE, CURDEBT, IMAGE ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
                        this.pstmt.executeUpdate();
                    }
                    Dbtname = "draweropened";
                    rss = this.con_source.getMetaData().getTables(null, null, Dbtname, null);
                    this.txtOut.append("DrawerOpened\n");
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
                    Dbtname = "floors";
                    rss = this.con_source.getMetaData().getTables(null, null, Dbtname, null);
                    this.txtOut.append("Floors\n");
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
                    Dbtname = "leaves";
                    rss = this.con_source.getMetaData().getTables(null, null, Dbtname, null);
                    this.txtOut.append("Leaves\n");
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
                    Dbtname = "lineremoved";
                    rss = this.con_source.getMetaData().getTables(null, null, Dbtname, null);
                    this.txtOut.append("LineRemoved\n");
                    this.SQL = "SELECT * FROM lineremoved";
                    this.rs = this.stmt_source.executeQuery(this.SQL);
                    while (this.rs.next()) {
                        this.SQL = "INSERT INTO lineremoved (REMOVEDDATE, NAME, TICKETID, PRODUCTID, PRODUCTNAME, UNITS) VALUES (?, ?, ?, ?, ?, ?)";
                        this.pstmt = this.con_target.prepareStatement(this.SQL);
                        this.pstmt.setTimestamp(1, this.rs.getTimestamp("REMOVEDDATE"));
                        this.pstmt.setString(2, this.rs.getString("NAME"));
                        this.pstmt.setString(3, this.rs.getString("TICKETID"));
                        this.pstmt.setString(4, this.rs.getString("PRODUCTID"));
                        this.pstmt.setString(5, this.rs.getString("PRODUCTNAME"));
                        this.pstmt.setDouble(6, this.rs.getDouble("UNITS"));
                        this.pstmt.executeUpdate();
                    }
                    Dbtname = "locations";
                    rss = this.con_source.getMetaData().getTables(null, null, Dbtname, null);
                    this.txtOut.append("Locations\n");
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
                    Dbtname = "moorers";
                    rss = this.con_source.getMetaData().getTables(null, null, Dbtname, null);
                    this.txtOut.append("Moorers\n");
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
                    Dbtname = "payments";
                    rss = this.con_source.getMetaData().getTables(null, null, Dbtname, null);
                    this.txtOut.append("Payments\n");
                    this.SQL = "SELECT * FROM payments";
                    this.rs = this.stmt_source.executeQuery(this.SQL);
                    while (this.rs.next()) {
                        this.SQL = "INSERT INTO payments(ID, RECEIPT, PAYMENT, TOTAL, TRANSID, RETURNMSG, NOTES, TENDERED) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                        this.pstmt = this.con_target.prepareStatement(this.SQL);
                        this.pstmt.setString(1, this.rs.getString("ID"));
                        this.pstmt.setString(2, this.rs.getString("RECEIPT"));
                        this.pstmt.setString(3, this.rs.getString("PAYMENT"));
                        this.pstmt.setDouble(4, this.rs.getDouble("TOTAL"));
                        this.pstmt.setString(5, this.rs.getString("TRANSID"));
                        this.pstmt.setBytes(6, this.rs.getBytes("RETURNMSG"));
                        this.pstmt.setString(7, this.rs.getString("NOTES"));
                        this.pstmt.setDouble(8, this.rs.getDouble("TENDERED"));
                        this.pstmt.executeUpdate();
                    }
                    Dbtname = "people";
                    rss = this.con_source.getMetaData().getTables(null, null, Dbtname, null);
                    this.txtOut.append("People\n");
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
                    Dbtname = "pickup_number";
                    rss = this.con_source.getMetaData().getTables(null, null, Dbtname, null);
                    this.txtOut.append("Pickup Number\n");
                    this.SQL = "SELECT * FROM pickup_number";
                    this.rs = this.stmt_source.executeQuery(this.SQL);
                    while (this.rs.next()) {
                        this.SQL = "INSERT INTO pickup_number(ID) VALUES (?)";
                        this.pstmt = this.con_target.prepareStatement(this.SQL);
                        this.pstmt.setString(1, this.rs.getString("ID"));
                        this.pstmt.executeUpdate();
                    }
                    Dbtname = "places";
                    rss = this.con_source.getMetaData().getTables(null, null, Dbtname, null);
                    this.txtOut.append("Places\n");
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
                    Dbtname = "products";
                    rss = this.con_source.getMetaData().getTables(null, null, Dbtname, null);
                    this.txtOut.append("Products\n");
                    this.SQL = "SELECT ID, REFERENCE, CODE, CODETYPE, NAME, PRICEBUY, PRICESELL, CATEGORY, TAXCAT, ATTRIBUTESET_ID, STOCKCOST, STOCKVOLUME, IMAGE, ISCOM, ISSCALE, PRINTKB, SENDSTATUS, ISSERVICE, ATTRIBUTES, DISPLAY, ISVPRICE, ISVERPATRIB, TEXTTIP, WARRANTY, STOCKUNITS FROM products ";
                    this.rs = this.stmt_source.executeQuery(this.SQL);
                    while (this.rs.next()) {
                        this.SQL = "INSERT INTO products(ID, REFERENCE, CODE, CODETYPE, NAME, PRICEBUY, PRICESELL, CATEGORY, TAXCAT, ATTRIBUTESET_ID, STOCKCOST, STOCKVOLUME, IMAGE, ISCOM, ISSCALE, PRINTKB, SENDSTATUS, ISSERVICE, ATTRIBUTES, DISPLAY, ISVPRICE, ISVERPATRIB, TEXTTIP, WARRANTY, STOCKUNITS) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
                        this.pstmt.setBoolean(16, this.rs.getBoolean("PRINTKB"));
                        this.pstmt.setBoolean(17, this.rs.getBoolean("SENDSTATUS"));
                        this.pstmt.setBoolean(18, this.rs.getBoolean("ISSERVICE"));
                        this.pstmt.setBytes(19, this.rs.getBytes("ATTRIBUTES"));
                        this.pstmt.setString(20, this.rs.getString("DISPLAY"));
                        this.pstmt.setBoolean(21, this.rs.getBoolean("ISVPRICE"));
                        this.pstmt.setBoolean(22, this.rs.getBoolean("ISVERPATRIB"));
                        this.pstmt.setString(23, this.rs.getString("TEXTTIP"));
                        this.pstmt.setBoolean(24, this.rs.getBoolean("WARRANTY"));
                        this.pstmt.setDouble(25, this.rs.getDouble("STOCKUNITS"));
                        if (this.rs.getString(1).startsWith("xxx")) {
                            this.SQL = "DELETE FROM products where left(ID,3)='xxx'";
                            this.pstmt = this.con_target.prepareStatement(this.SQL);
                            this.pstmt.executeUpdate();
                            continue;
                        }
                        this.pstmt.executeUpdate();
                    }
                    Dbtname = "products_cat";
                    rss = this.con_source.getMetaData().getTables(null, null, Dbtname, null);
                    this.txtOut.append("Products_Cat\n");
                    this.SQL = "SELECT * FROM products_cat";
                    this.rs = this.stmt_source.executeQuery(this.SQL);
                    while (this.rs.next()) {
                        this.SQL = "INSERT INTO products_cat(PRODUCT, CATORDER) VALUES (?, ?)";
                        this.pstmt = this.con_target.prepareStatement(this.SQL);
                        this.pstmt.setString(1, this.rs.getString("PRODUCT"));
                        this.pstmt.setInt(2, this.rs.getInt("CATORDER"));
                        if (this.rs.getString(1).startsWith("xxx99")) {
                            this.SQL = "DELETE FROM products where left(ID,3)='xxx'";
                            this.pstmt = this.con_target.prepareStatement(this.SQL);
                            this.pstmt.executeUpdate();
                            continue;
                        }
                        this.pstmt.executeUpdate();
                    }
                    Dbtname = "products_com";
                    rss = this.con_source.getMetaData().getTables(null, null, Dbtname, null);
                    this.txtOut.append("Products_Com\n");
                    this.SQL = "SELECT * FROM products_com";
                    this.rs = this.stmt_source.executeQuery(this.SQL);
                    while (this.rs.next()) {
                        this.SQL = "INSERT INTO products_com(ID, PRODUCT, PRODUCT2) VALUES (?, ?, ?)";
                        this.pstmt = this.con_target.prepareStatement(this.SQL);
                        this.pstmt.setString(1, this.rs.getString("ID"));
                        this.pstmt.setString(2, this.rs.getString("PRODUCT"));
                        this.pstmt.setString(3, this.rs.getString("PRODUCT2"));
                        this.pstmt.executeUpdate();
                    }
                    Dbtname = "receipts";
                    rss = this.con_source.getMetaData().getTables(null, null, Dbtname, null);
                    this.txtOut.append("Receipts\n");
                    this.SQL = "SELECT * FROM receipts";
                    this.rs = this.stmt_source.executeQuery(this.SQL);
                    while (this.rs.next()) {
                        this.SQL = "INSERT INTO receipts(ID, MONEY, DATENEW, ATTRIBUTES, PERSON) VALUES (?, ?, ?, ?, ?)";
                        this.pstmt = this.con_target.prepareStatement(this.SQL);
                        this.pstmt.setString(1, this.rs.getString("ID"));
                        this.pstmt.setString(2, this.rs.getString("MONEY"));
                        this.pstmt.setTimestamp(3, this.rs.getTimestamp("DATENEW"));
                        this.pstmt.setBytes(4, this.rs.getBytes("ATTRIBUTES"));
                        this.pstmt.setString(5, this.rs.getString("PERSON"));
                        this.pstmt.executeUpdate();
                    }
                    Dbtname = "reservation_customers";
                    rss = this.con_source.getMetaData().getTables(null, null, Dbtname, null);
                    this.txtOut.append("Reservation_Customers\n");
                    this.SQL = "SELECT * FROM reservation_customers";
                    this.rs = this.stmt_source.executeQuery(this.SQL);
                    while (this.rs.next()) {
                        this.SQL = "INSERT INTO reservation_customers(ID, CUSTOMER) VALUES (?, ?)";
                        this.pstmt = this.con_target.prepareStatement(this.SQL);
                        this.pstmt.setString(1, this.rs.getString("ID"));
                        this.pstmt.setString(2, this.rs.getString("CUSTOMER"));
                        this.pstmt.executeUpdate();
                    }
                    Dbtname = "reservations";
                    rss = this.con_source.getMetaData().getTables(null, null, Dbtname, null);
                    this.txtOut.append("Reservations\n");
                    this.SQL = "SELECT * FROM reservations";
                    this.rs = this.stmt_source.executeQuery(this.SQL);
                    while (this.rs.next()) {
                        this.SQL = "INSERT INTO reservations(ID, CREATED, DATENEW, TITLE, CHAIRS, ISDONE, DESCRIPTION) VALUES (?, ?, ?, ?, ?, ?, ?)";
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
                    Dbtname = "shift_breaks";
                    rss = this.con_source.getMetaData().getTables(null, null, Dbtname, null);
                    this.txtOut.append("Shift_breaks\n");
                    this.SQL = "SELECT * FROM shift_breaks";
                    this.rs = this.stmt_source.executeQuery(this.SQL);
                    while (this.rs.next()) {
                        this.SQL = "INSERT INTO shift_breaks(ID, SHIFTID, BREAKID, STARTTIME, ENDTIME) VALUES (?, ?, ?, ?, ?)";
                        this.pstmt = this.con_target.prepareStatement(this.SQL);
                        this.pstmt.setString(1, this.rs.getString("ID"));
                        this.pstmt.setString(2, this.rs.getString("SHIFTID"));
                        this.pstmt.setString(3, this.rs.getString("BREAKID"));
                        this.pstmt.setTimestamp(4, this.rs.getTimestamp("STARTTIME"));
                        this.pstmt.setTimestamp(5, this.rs.getTimestamp("ENDTIME"));
                        this.pstmt.executeUpdate();
                    }
                    Dbtname = "shifts";
                    rss = this.con_source.getMetaData().getTables(null, null, Dbtname, null);
                    this.txtOut.append("Shifts\n");
                    this.SQL = "SELECT * FROM shifts";
                    this.rs = this.stmt_source.executeQuery(this.SQL);
                    while (this.rs.next()) {
                        this.SQL = "INSERT INTO shifts(ID, STARTSHIFT, ENDSHIFT, PPLID) VALUES (?, ?, ?, ?)";
                        this.pstmt = this.con_target.prepareStatement(this.SQL);
                        this.pstmt.setString(1, this.rs.getString("ID"));
                        this.pstmt.setTimestamp(2, this.rs.getTimestamp("STARTSHIFT"));
                        this.pstmt.setTimestamp(3, this.rs.getTimestamp("ENDSHIFT"));
                        this.pstmt.setString(4, this.rs.getString("PPLID"));
                        this.pstmt.executeUpdate();
                    }
                    Dbtname = "stockcurrent";
                    rss = this.con_source.getMetaData().getTables(null, null, Dbtname, null);
                    this.txtOut.append("Stockcurrent\n");
                    this.SQL = "SELECT * FROM stockcurrent";
                    this.rs = this.stmt_source.executeQuery(this.SQL);
                    while (this.rs.next()) {
                        this.SQL = "INSERT INTO stockcurrent(LOCATION, PRODUCT, ATTRIBUTESETINSTANCE_ID, UNITS) VALUES (?, ?, ?, ?)";
                        this.pstmt = this.con_target.prepareStatement(this.SQL);
                        this.pstmt.setString(1, this.rs.getString("LOCATION"));
                        this.pstmt.setString(2, this.rs.getString("PRODUCT"));
                        this.pstmt.setString(3, this.rs.getString("ATTRIBUTESETINSTANCE_ID"));
                        this.pstmt.setDouble(4, this.rs.getDouble("UNITS"));
                        this.pstmt.executeUpdate();
                    }
                    Dbtname = "stockdiary";
                    rss = this.con_source.getMetaData().getTables(null, null, Dbtname, null);
                    this.txtOut.append("Stockdiary\n");
                    this.SQL = "SELECT * FROM stockdiary";
                    this.rs = this.stmt_source.executeQuery(this.SQL);
                    while (this.rs.next()) {
                        this.SQL = "INSERT INTO stockdiary(ID, DATENEW, REASON, LOCATION, PRODUCT, ATTRIBUTESETINSTANCE_ID, UNITS, PRICE, APPUSER) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
                    Dbtname = "stocklevel";
                    rss = this.con_source.getMetaData().getTables(null, null, Dbtname, null);
                    this.txtOut.append("Stocklevel\n");
                    this.SQL = "SELECT * FROM stocklevel";
                    this.rs = this.stmt_source.executeQuery(this.SQL);
                    while (this.rs.next()) {
                        this.SQL = "INSERT INTO stocklevel(ID, LOCATION, PRODUCT, STOCKSECURITY, STOCKMAXIMUM) VALUES (?, ?, ?, ?, ?)";
                        this.pstmt = this.con_target.prepareStatement(this.SQL);
                        this.pstmt.setString(1, this.rs.getString("ID"));
                        this.pstmt.setString(2, this.rs.getString("LOCATION"));
                        this.pstmt.setString(3, this.rs.getString("PRODUCT"));
                        this.pstmt.setDouble(4, this.rs.getDouble("STOCKSECURITY"));
                        this.pstmt.setDouble(5, this.rs.getDouble("STOCKMAXIMUM"));
                        this.pstmt.executeUpdate();
                    }
                    Dbtname = "taxcategories";
                    rss = this.con_source.getMetaData().getTables(null, null, Dbtname, null);
                    this.txtOut.append("TaxCategories\n");
                    this.SQL = "SELECT * FROM taxcategories";
                    this.rs = this.stmt_source.executeQuery(this.SQL);
                    while (this.rs.next()) {
                        this.SQL = "INSERT INTO taxcategories(ID, NAME) VALUES (?, ?)";
                        this.pstmt = this.con_target.prepareStatement(this.SQL);
                        this.pstmt.setString(1, this.rs.getString("ID"));
                        this.pstmt.setString(2, this.rs.getString("NAME"));
                        this.pstmt.executeUpdate();
                    }
                    Dbtname = "taxcustcategories";
                    rss = this.con_source.getMetaData().getTables(null, null, Dbtname, null);
                    this.txtOut.append("Tax Customer Categories\n");
                    this.SQL = "SELECT * FROM taxcustcategories";
                    this.rs = this.stmt_source.executeQuery(this.SQL);
                    while (this.rs.next()) {
                        this.SQL = "INSERT INTO taxcustcategories(ID, NAME) VALUES (?, ?)";
                        this.pstmt = this.con_target.prepareStatement(this.SQL);
                        this.pstmt.setString(1, this.rs.getString("ID"));
                        this.pstmt.setString(2, this.rs.getString("NAME"));
                        this.pstmt.executeUpdate();
                    }
                    Dbtname = "taxes";
                    rss = this.con_source.getMetaData().getTables(null, null, Dbtname, null);
                    this.txtOut.append("Taxes\n");
                    this.SQL = "SELECT * FROM taxes";
                    this.rs = this.stmt_source.executeQuery(this.SQL);
                    while (this.rs.next()) {
                        this.SQL = "INSERT INTO taxes(ID, NAME, CATEGORY, CUSTCATEGORY, PARENTID, RATE, RATECASCADE, RATEORDER) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
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
                    Dbtname = "taxlines";
                    rss = this.con_source.getMetaData().getTables(null, null, Dbtname, null);
                    this.txtOut.append("TaxLines\n");
                    this.SQL = "SELECT * FROM taxlines";
                    this.rs = this.stmt_source.executeQuery(this.SQL);
                    while (this.rs.next()) {
                        this.SQL = "INSERT INTO taxlines(ID, RECEIPT, TAXID, BASE, AMOUNT) VALUES (?, ?, ?, ?, ?)";
                        this.pstmt = this.con_target.prepareStatement(this.SQL);
                        this.pstmt.setString(1, this.rs.getString("ID"));
                        this.pstmt.setString(2, this.rs.getString("RECEIPT"));
                        this.pstmt.setString(3, this.rs.getString("TAXID"));
                        this.pstmt.setDouble(4, this.rs.getDouble("BASE"));
                        this.pstmt.setDouble(5, this.rs.getDouble("AMOUNT"));
                        this.pstmt.executeUpdate();
                    }
                    Dbtname = "thirdparties";
                    rss = this.con_source.getMetaData().getTables(null, null, Dbtname, null);
                    this.txtOut.append("ThirdParties\n");
                    this.SQL = "SELECT * FROM thirdparties";
                    this.rs = this.stmt_source.executeQuery(this.SQL);
                    while (this.rs.next()) {
                        this.SQL = "INSERT INTO thirdparties(ID, CIF, NAME, ADDRESS, CONTACTCOMM, CONTACTFACT, PAYRULE, FAXNUMBER, PHONENUMBER, MOBILENUMBER, EMAIL, WEBPAGE, NOTES) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
                    Dbtname = "ticketlines";
                    rss = this.con_source.getMetaData().getTables(null, null, Dbtname, null);
                    this.txtOut.append("TicketLines\n");
                    this.SQL = "SELECT * FROM ticketlines";
                    this.rs = this.stmt_source.executeQuery(this.SQL);
                    while (this.rs.next()) {
                        this.SQL = "INSERT INTO ticketlines(TICKET, LINE, PRODUCT, ATTRIBUTESETINSTANCE_ID, UNITS, PRICE, TAXID, ATTRIBUTES) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
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
                    Dbtname = "tickets";
                    rss = this.con_source.getMetaData().getTables(null, null, Dbtname, null);
                    this.txtOut.append("Tickets\n");
                    this.SQL = "SELECT * FROM tickets";
                    this.rs = this.stmt_source.executeQuery(this.SQL);
                    while (this.rs.next()) {
                        this.SQL = "INSERT INTO tickets(ID, TICKETTYPE, TICKETID, PERSON, CUSTOMER, STATUS) VALUES (?, ?, ?, ?, ?, ?)";
                        this.pstmt = this.con_target.prepareStatement(this.SQL);
                        this.pstmt.setString(1, this.rs.getString("ID"));
                        this.pstmt.setInt(2, this.rs.getInt("TICKETTYPE"));
                        this.pstmt.setInt(3, this.rs.getInt("TICKETID"));
                        this.pstmt.setString(4, this.rs.getString("PERSON"));
                        this.pstmt.setString(5, this.rs.getString("CUSTOMER"));
                        this.pstmt.setInt(6, this.rs.getInt("STATUS"));
                        this.pstmt.executeUpdate();
                    }
                    Dbtname = "ticketsnum";
                    rss = this.con_source.getMetaData().getTables(null, null, Dbtname, null);
                    this.txtOut.append("Tickets Number\n");
                    this.SQL = "SELECT * FROM ticketsnum";
                    this.rs = this.stmt_source.executeQuery(this.SQL);
                    while (this.rs.next()) {
                        this.ticketsnum = this.rs.getString("ID");
                    }
                    this.SQL = "UPDATE ticketsnum SET ID=" + this.ticketsnum;
                    this.stmt_target.executeUpdate(this.SQL);
                    Dbtname = "ticketsnum_payment";
                    rss = this.con_source.getMetaData().getTables(null, null, Dbtname, null);
                    this.txtOut.append("Tickets Number Payments\n");
                    this.SQL = "SELECT * FROM ticketsnum_payment";
                    this.rs = this.stmt_source.executeQuery(this.SQL);
                    while (this.rs.next()) {
                        this.ticketsnumPayment = this.rs.getString("ID");
                    }
                    this.SQL = "UPDATE ticketsnum_payment SET ID=" + this.ticketsnumPayment;
                    this.stmt_target.executeUpdate(this.SQL);
                    Dbtname = "ticketsnum_refund";
                    rss = this.con_source.getMetaData().getTables(null, null, Dbtname, null);
                    this.txtOut.append("Tickets Number Refunds\n");
                    this.SQL = "SELECT * FROM ticketsnum_refund";
                    this.rs = this.stmt_source.executeQuery(this.SQL);
                    while (this.rs.next()) {
                        this.ticketsnumRefund = this.rs.getString("ID");
                    }
                    this.SQL = "UPDATE ticketsnum_refund SET ID=" + this.ticketsnumRefund;
                    this.stmt_target.executeUpdate(this.SQL);
                    JOptionPane.showMessageDialog(this, AppLocal.getIntString("message.transfercomplete"), AppLocal.getIntString("message.transfermessage"), 2);
                    this.FKeys();
                    this.txtOut.append("Data Transfer Complete\n");
                    this.webPBar.setString("Finished!");
                    this.jbtnTransfer.setEnabled(true);
                    JOptionPane.showMessageDialog(this, AppLocal.getIntString("message.indexcomplete"), AppLocal.getIntString("message.transfermessage"), 2);
                }
                catch (HeadlessException | SQLException e) {
                    JMessageDialog.showMessage(this, new MessageInf(-33554432, this.SQL, e));
                }
            } else {
                JFrame frame = new JFrame();
                JOptionPane.showMessageDialog(frame, AppLocal.getIntString("message.transfernotsupported"), AppLocal.getIntString("message.transfermessage"), 2);
            }
            this.session_source.close();
        }
    }

    private void initComponents() {
        this.jLabel5 = new JLabel();
        this.jLabel18 = new JLabel();
        this.jLabel1 = new JLabel();
        this.jLabel2 = new JLabel();
        this.jLabel7 = new JLabel();
        this.jLabel3 = new JLabel();
        this.jLabel4 = new JLabel();
        this.jLabel6 = new JLabel();
        this.webPanel2 = new JPanel();
        this.cbSource = new JComboBox();
        this.jtxtDbDriverLib = new JTextField();
        this.jButton3 = new JButton();
        this.jtxtDbDriver = new JTextField();
        this.jtxtDbURL = new JTextField();
        this.jLabel4 = new JLabel();
        this.txtDbUser = new JTextField();
        this.txtDbPass = new JPasswordField();
        this.jPanel1 = new JPanel();
        this.jButton2 = new JButton();
        this.jLabel11 = new JLabel();
        this.jLabel5 = new JLabel();
        this.jLabel6 = new JLabel();
        this.jtxtDbName = new JTextField();
        this.jLabel7 = new JLabel();
        this.jLabel8 = new JLabel();
        this.jLabel9 = new JLabel();
        this.jLabel1 = new JLabel();
        this.webPBar = new JProgressBar();
        this.setBackground(new Color(255, 255, 255));
        this.setFont(new Font("Arial", 0, 14));
        this.setPreferredSize(new Dimension(900, 425));
        this.jLabel5.setFont(new Font("Arial", 0, 14));
        this.jLabel5.setForeground(new Color(102, 102, 102));
        ResourceBundle bundle = ResourceBundle.getBundle("pos_messages");
        this.jLabel5.setText(bundle.getString("label.DbType"));
        this.jLabel5.setMaximumSize(new Dimension(150, 30));
        this.jLabel5.setMinimumSize(new Dimension(150, 30));
        this.jLabel5.setPreferredSize(new Dimension(160, 30));
        this.jLabel18.setFont(new Font("Arial", 0, 14));
        this.jLabel18.setForeground(new Color(102, 102, 102));
        this.jLabel18.setText(AppLocal.getIntString("label.dbdriverlib"));
        this.jLabel18.setMaximumSize(new Dimension(150, 30));
        this.jLabel18.setMinimumSize(new Dimension(150, 30));
        this.jLabel18.setPreferredSize(new Dimension(160, 30));
        this.jLabel1.setFont(new Font("Arial", 0, 14));
        this.jLabel1.setForeground(new Color(102, 102, 102));
        this.jLabel1.setText(AppLocal.getIntString("label.DbDriver"));
        this.jLabel1.setMaximumSize(new Dimension(150, 30));
        this.jLabel1.setMinimumSize(new Dimension(150, 30));
        this.jLabel1.setPreferredSize(new Dimension(160, 30));
        this.jLabel2.setFont(new Font("Arial", 0, 14));
        this.jLabel2.setForeground(new Color(102, 102, 102));
        this.jLabel2.setText("URL");
        this.jLabel2.setMaximumSize(new Dimension(150, 30));
        this.jLabel2.setMinimumSize(new Dimension(150, 30));
        this.jLabel2.setPreferredSize(new Dimension(160, 30));
        this.jLabel7.setFont(new Font("Arial", 0, 14));
        this.jLabel7.setForeground(new Color(102, 102, 102));
        this.jLabel7.setText("DB Version");
        this.jLabel7.setMaximumSize(new Dimension(150, 30));
        this.jLabel7.setMinimumSize(new Dimension(150, 30));
        this.jLabel7.setPreferredSize(new Dimension(160, 30));
        this.jLabel3.setFont(new Font("Arial", 0, 14));
        this.jLabel3.setForeground(new Color(102, 102, 102));
        this.jLabel3.setText(AppLocal.getIntString("label.DbUser"));
        this.jLabel3.setMaximumSize(new Dimension(150, 30));
        this.jLabel3.setMinimumSize(new Dimension(150, 30));
        this.jLabel3.setPreferredSize(new Dimension(160, 30));
        this.jLabel4.setFont(new Font("Arial", 0, 14));
        this.jLabel4.setForeground(new Color(102, 102, 102));
        this.jLabel4.setText(AppLocal.getIntString("label.DbPassword"));
        this.jLabel4.setMaximumSize(new Dimension(150, 30));
        this.jLabel4.setMinimumSize(new Dimension(150, 30));
        this.jLabel4.setPreferredSize(new Dimension(160, 30));
        this.jLabel6.setFont(new Font("Arial", 0, 18));
        this.jLabel6.setForeground(new Color(51, 51, 51));
        this.jLabel6.setText("TRANSFER TO :");
        this.jLabel6.setPreferredSize(new Dimension(150, 30));
        this.jLabel6.setSize(new Dimension(150, 30));
        this.webPanel2.setBackground(new Color(255, 255, 255));
        GroupLayout webPanel2Layout = new GroupLayout(this.webPanel2);
        this.webPanel2.setLayout(webPanel2Layout);
        webPanel2Layout.setHorizontalGroup(webPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 270, Short.MAX_VALUE));
        webPanel2Layout.setVerticalGroup(webPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 51, Short.MAX_VALUE));
        this.cbSource.setForeground(new Color(51, 51, 51));
        this.cbSource.setToolTipText(bundle.getString("tooltip.transferfromdb"));
        this.cbSource.setFont(new Font("Arial", 0, 14));
        this.cbSource.setPreferredSize(new Dimension(150, 30));
        this.cbSource.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                Transfer.this.cbSourceActionPerformed(evt);
            }
        });
        this.jtxtDbDriverLib.setForeground(new Color(51, 51, 51));
        this.jtxtDbDriverLib.setToolTipText(bundle.getString("tootltip.transferlib"));
        this.jtxtDbDriverLib.setFont(new Font("Arial", 0, 14));
        this.jtxtDbDriverLib.setPreferredSize(new Dimension(300, 30));
        this.jbtnDbDriverLib.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/fileopen.png")));
        this.jbtnDbDriverLib.setToolTipText(bundle.getString("tooltip.openfile"));
        this.jbtnDbDriverLib.setMaximumSize(new Dimension(64, 32));
        this.jbtnDbDriverLib.setMinimumSize(new Dimension(64, 32));
        this.jbtnDbDriverLib.setPreferredSize(new Dimension(60, 30));
        this.jtxtDbDriver.setForeground(new Color(51, 51, 51));
        this.jtxtDbDriver.setToolTipText(bundle.getString("tootltip.transferclass"));
        this.jtxtDbDriver.setFont(new Font("Arial", 0, 14));
        this.jtxtDbDriver.setPreferredSize(new Dimension(300, 30));
        this.jtxtDbURL.setForeground(new Color(51, 51, 51));
        this.jtxtDbURL.setToolTipText(bundle.getString("tootltip.transferdbname"));
        this.jtxtDbURL.setFont(new Font("Arial", 0, 14));
        this.jtxtDbURL.setPreferredSize(new Dimension(300, 30));
        this.jlblVersion.setFont(new Font("Arial", 1, 16));
        this.jlblVersion.setForeground(new Color(0, 204, 255));
        this.jlblVersion.setToolTipText(bundle.getString("tooltip.transferdbversion"));
        this.jlblVersion.setBorder(BorderFactory.createLineBorder(new Color(0, 204, 255)));
        this.jlblVersion.setMaximumSize(new Dimension(150, 30));
        this.jlblVersion.setMinimumSize(new Dimension(150, 30));
        this.jlblVersion.setPreferredSize(new Dimension(100, 30));
        this.txtDbUser.setForeground(new Color(51, 51, 51));
        this.txtDbUser.setToolTipText(bundle.getString("tooltip.dbuser"));
        this.txtDbUser.setFont(new Font("Arial", 0, 14));
        this.txtDbUser.setPreferredSize(new Dimension(300, 30));
        this.txtDbPass.setForeground(new Color(51, 51, 51));
        this.txtDbPass.setToolTipText(bundle.getString("tooltip.dbpassword"));
        this.txtDbPass.setFont(new Font("Arial", 0, 14));
        this.txtDbPass.setPreferredSize(new Dimension(300, 30));
        this.jScrollPane1.setBorder(null);
        this.jScrollPane1.setVerticalScrollBarPolicy(22);
        this.jScrollPane1.setViewportBorder(BorderFactory.createLineBorder(new Color(204, 204, 204)));
        this.jScrollPane1.setFont(new Font("Arial", 0, 13));
        this.jScrollPane1.setHorizontalScrollBar(null);
        this.jScrollPane1.setPreferredSize(new Dimension(200, 350));
        this.txtOut.setColumns(20);
        this.txtOut.setFont(new Font("Arial", 0, 12));
        this.txtOut.setForeground(new Color(0, 153, 255));
        this.txtOut.setRows(5);
        this.txtOut.setToolTipText(bundle.getString("tooltip.transfertxtout"));
        this.txtOut.setBorder(null);
        this.jScrollPane1.setViewportView(this.txtOut);
        this.jLabel8.setFont(new Font("Arial", 0, 18));
        this.jLabel8.setForeground(new Color(0, 153, 255));
        this.jLabel8.setText("PROGRESS");
        this.jLabel8.setPreferredSize(new Dimension(150, 30));
        this.jLabel8.setSize(new Dimension(150, 30));
        this.jlblSource.setFont(new Font("Arial", 0, 18));
        this.jlblSource.setForeground(new Color(0, 153, 255));
        this.jlblSource.setHorizontalAlignment(2);
        this.jlblSource.setPreferredSize(new Dimension(150, 30));
        this.jlblSource.setSize(new Dimension(150, 30));
        this.jbtnExit.setFont(new Font("Arial", 0, 12));
        this.jbtnExit.setText(AppLocal.getIntString("Button.Exit"));
        this.jbtnExit.setToolTipText(bundle.getString("tooltip.exit"));
        this.jbtnExit.setMaximumSize(new Dimension(70, 33));
        this.jbtnExit.setMinimumSize(new Dimension(70, 33));
        this.jbtnExit.setPreferredSize(new Dimension(80, 45));
        this.jbtnExit.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                Transfer.this.jbtnExitActionPerformed(evt);
            }
        });
        this.jtxtDbName.setForeground(new Color(51, 51, 51));
        this.jtxtDbName.setToolTipText(bundle.getString("tooltip.dbname"));
        this.jtxtDbName.setFont(new Font("Arial", 0, 14));
        this.jtxtDbName.setPreferredSize(new Dimension(300, 30));
        this.jtxtDbName.addFocusListener(new FocusAdapter(){

            @Override
            public void focusLost(FocusEvent evt) {
                Transfer.this.jtxtDbNameFocusLost(evt);
            }
        });
        this.jLabel9.setFont(new Font("Arial", 0, 14));
        this.jLabel9.setForeground(new Color(102, 102, 102));
        this.jLabel9.setText(AppLocal.getIntString("label.DbUser"));
        this.jLabel9.setMaximumSize(new Dimension(150, 30));
        this.jLabel9.setMinimumSize(new Dimension(150, 30));
        this.jLabel9.setPreferredSize(new Dimension(160, 30));
        this.jLabel10.setFont(new Font("Arial", 0, 14));
        this.jLabel10.setForeground(new Color(102, 102, 102));
        this.jLabel10.setText(AppLocal.getIntString("label.DbName"));
        this.jLabel10.setMaximumSize(new Dimension(150, 30));
        this.jLabel10.setMinimumSize(new Dimension(150, 30));
        this.jLabel10.setPreferredSize(new Dimension(160, 30));
        this.jbtnTest.setFont(new Font("Arial", 0, 14));
        this.jbtnTest.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/database.png")));
        this.jbtnTest.setText(bundle.getString("Button.Test"));
        this.jbtnTest.setToolTipText(bundle.getString("tooltip.dbtest"));
        this.jbtnTest.setActionCommand(bundle.getString("Button.Test"));
        this.jbtnTest.setPreferredSize(new Dimension(160, 45));
        this.jbtnTest.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                Transfer.this.jbtnTestjButtonTestConnectionActionPerformed(evt);
            }
        });
        this.jbtnTransfer.setFont(new Font("Arial", 0, 14));
        this.jbtnTransfer.setText(AppLocal.getIntString("button.transfer"));
        this.jbtnTransfer.setToolTipText(bundle.getString("tooltip.transferdb"));
        this.jbtnTransfer.setMaximumSize(new Dimension(70, 33));
        this.jbtnTransfer.setMinimumSize(new Dimension(70, 33));
        this.jbtnTransfer.setPreferredSize(new Dimension(160, 45));
        this.jbtnTransfer.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                Transfer.this.jbtnTransferActionPerformed(evt);
            }
        });
        this.webPBar.setFont(new Font("Arial", 0, 13));
        this.webPBar.setPreferredSize(new Dimension(240, 30));
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.jLabel6, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jlblSource, -2, 470, -2)).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel5, -2, -1, -2).addComponent(this.jLabel9, 0, 0, -2).addComponent(this.jLabel10, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jtxtDbName, -1, -1, -2).addComponent(this.cbSource, -2, -1, -2))).addGroup(layout.createSequentialGroup().addGap(160, 160, 160).addComponent(this.jbtnTest, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jbtnTransfer, -2, -1, -2).addGap(18, 18, 18).addComponent(this.jbtnExit, -2, -1, -2)).addGroup(layout.createSequentialGroup().addComponent(this.jLabel4, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.txtDbPass, -2, -1, -2)).addGroup(layout.createSequentialGroup().addComponent(this.jLabel3, -1, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.txtDbUser, -1, -1, -2)).addGroup(layout.createSequentialGroup().addComponent(this.jLabel7, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jlblVersion, -2, -1, -2))).addGap(18, 18, 18).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addGroup(layout.createSequentialGroup().addComponent(this.jLabel8, -2, 125, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addComponent(this.webPBar, -2, 109, -2)).addComponent(this.jScrollPane1, -2, 240, -2))).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.jLabel2, GroupLayout.Alignment.TRAILING, -1, -1, -2).addComponent(this.jLabel1, GroupLayout.Alignment.TRAILING, -2, -1, -2).addComponent(this.jLabel18, GroupLayout.Alignment.TRAILING, -1, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.jtxtDbDriverLib, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jbtnDbDriverLib, -2, -1, -2)).addComponent(this.jtxtDbDriver, -1, -1, -2).addComponent(this.jtxtDbURL, -1, -1, -2)).addComponent(this.webPanel2, -2, -1, -2))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel6, -2, -1, -2).addComponent(this.jLabel8, -2, -1, -2).addComponent(this.jlblSource, -2, -1, -2)).addComponent(this.webPBar, -2, 32, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.jLabel5, -2, -1, -2).addComponent(this.cbSource, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel9, -2, -1, -2).addComponent(this.jtxtDbName, -2, -1, -2).addComponent(this.jLabel10, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel18, -2, -1, -2).addComponent(this.jtxtDbDriverLib, -2, -1, -2)).addComponent(this.jbtnDbDriverLib, -2, 39, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1, -2, -1, -2).addComponent(this.jtxtDbDriver, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel2, -2, -1, -2).addComponent(this.jtxtDbURL, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel3, -2, -1, -2).addComponent(this.txtDbUser, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel4, -2, -1, -2).addComponent(this.txtDbPass, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.jLabel7, -2, -1, -2).addComponent(this.jlblVersion, -2, -1, -2)).addGap(12, 12, 12).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jbtnTest, -2, -1, -2).addComponent(this.jbtnTransfer, -2, -1, -2).addComponent(this.jbtnExit, -2, -1, -2))).addComponent(this.jScrollPane1, -1, 361, Short.MAX_VALUE)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.webPanel2, -2, -1, -2)));
        this.jLabel1.getAccessibleContext().setAccessibleName("DBDriver");
    }

    private void jbtnTransferActionPerformed(ActionEvent evt) {
        if (JOptionPane.showConfirmDialog(this, AppLocal.getIntString("message.transfer"), AppLocal.getIntString("message.transfertitle"), 0, 3) == 0) {
            SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>(){

                @Override
                protected Void doInBackground() throws Exception {
                    Transfer.this.webPBar.setString("Starting...");
                    Transfer.this.doTransfer();
                    return null;
                }
            };
            worker.execute();
        }
    }

    private void jbtnExitActionPerformed(ActionEvent evt) {
        this.session_source.close();
        this.deactivate();
    }

    private void jbtnTestjButtonTestConnectionActionPerformed(ActionEvent evt) {
        try {
            boolean isValid;
            String driverlib = this.jtxtDbDriverLib.getText();
            String driver = this.jtxtDbDriver.getText();
            String url = this.jtxtDbURL.getText();
            String user = this.txtDbUser.getText();
            String password = new String(this.txtDbPass.getPassword());
            URLClassLoader cloader = new URLClassLoader(new URL[]{new File(driverlib).toURI().toURL()});
            DriverManager.registerDriver(new DriverWrapper((Driver)Class.forName(driver, true, cloader).newInstance()));
            Session session_source = new Session(url, user, password);
            Connection connection = session_source.getConnection();
            boolean bl = isValid = connection == null ? false : connection.isValid(1000);
            if (isValid) {
                this.SQL = "SELECT * FROM applications";
                Statement stmt = connection.createStatement();
                this.rs = stmt.executeQuery(this.SQL);
                this.rs.next();
                this.jlblVersion.setText(this.rs.getString(3));
                this.stringList.add("Version check... " + this.rs.getString(3) + "\n");
                this.txtOut.setText(this.stringList.get(1));
                JOptionPane.showMessageDialog(this, AppLocal.getIntString("message.databaseconnectsuccess"), "Connection Test", 1);
                this.jbtnTransfer.setEnabled(true);
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

    private void cbSourceActionPerformed(ActionEvent evt) {
        String dirname = System.getProperty("dirname.path");
        String string = dirname = dirname == null ? "./" : dirname;
        if ("Derby".equals(this.cbSource.getSelectedItem())) {
            this.jtxtDbName.setText("unicentaopos-database");
            this.jtxtDbDriverLib.setText(new File(new File(dirname), "/lib/derby-10.10.2.0.jar").getAbsolutePath());
            this.jtxtDbDriver.setText("org.apache.derby.jdbc.EmbeddedDriver");
            this.jtxtDbURL.setText("jdbc:derby:" + new File(new File(System.getProperty("user.home")), "unicentaopos-database"));
            this.txtDbUser.setText("");
            this.txtDbPass.setText("");
        } else if ("PostgreSQL".equals(this.cbSource.getSelectedItem())) {
            this.jtxtDbName.setText("unicentaopos");
            this.jtxtDbDriverLib.setText(new File(new File(dirname), "/lib/postgresql-9.4-1208.jdbc4.jar").getAbsolutePath());
            this.jtxtDbDriver.setText("org.postgresql.Driver");
            this.jtxtDbURL.setText("jdbc:postgresql://localhost:5432/unicentaopos");
        } else {
            this.jtxtDbName.setText("unicentaopos");
            this.jtxtDbDriverLib.setText(new File(new File(dirname), "/lib/mysql-connector-java-5.1.34-bin.jar").getAbsolutePath());
            this.jtxtDbDriver.setText("com.mysql.jdbc.Driver");
            this.jtxtDbURL.setText("jdbc:mysql://localhost:3306/unicentaopos");
        }
    }

    private void jtxtDbNameFocusLost(FocusEvent evt) {
        this.jtxtDbURL.setText(this.jtxtDbName.getText());
        String dirname = System.getProperty("dirname.path");
        if ("Derby".equals(this.cbSource.getSelectedItem())) {
            this.jtxtDbURL.setText("jdbc:derby:" + new File(new File(System.getProperty("user.home")), this.jtxtDbName.getText()));
        } else if ("PostgreSQL".equals(this.cbSource.getSelectedItem())) {
            this.jtxtDbURL.setText("jdbc:postgresql://localhost:5432/" + this.jtxtDbName.getText());
        } else {
            this.jtxtDbURL.setText("jdbc:mysql://localhost:3306/" + this.jtxtDbName.getText());
        }
    }
}

