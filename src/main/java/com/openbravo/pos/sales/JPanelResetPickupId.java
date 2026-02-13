/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.sales;

import com.openbravo.basic.BasicException;
import com.openbravo.data.gui.JMessageDialog;
import com.openbravo.data.gui.MessageInf;
import com.openbravo.data.loader.Session;
import com.openbravo.pos.forms.AppConfig;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.AppProperties;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.forms.AppViewConnection;
import com.openbravo.pos.forms.JPanelView;
import com.openbravo.pos.util.AltEncrypter;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class JPanelResetPickupId
extends JPanel
implements JPanelView {
    private AppConfig config;
    private Connection con;
    private String sdbmanager;
    private Session session;
    private AppProperties m_props;
    private String SQL;
    private Statement stmt;
    private JPanel jPanel1;
    private JButton jbtnExit;
    private JButton jbtnUpdate;

    public JPanelResetPickupId(AppView oApp) {
        this(oApp.getProperties());
    }

    public JPanelResetPickupId(AppProperties props) {
        this.initComponents();
        this.config = new AppConfig(props.getConfigFile());
        this.m_props = props;
    }

    public void performReset() {
        if ("HSQL Database Engine".equals(this.sdbmanager)) {
            this.SQL = "ALTER SEQUENCE pickup_number RESTART WITH 1";
            try {
                this.stmt.executeUpdate(this.SQL);
            }
            catch (SQLException sQLException) {}
        } else if ("MySQL".equals(this.sdbmanager)) {
            this.SQL = "UPDATE pickup_number SET ID=0";
            try {
                this.stmt.executeUpdate(this.SQL);
            }
            catch (SQLException sQLException) {}
        } else if ("PostgreSQL".equals(this.sdbmanager)) {
            this.SQL = "ALTER SEQUENCE pickup_number RESTART WITH 1";
            try {
                this.stmt.executeUpdate(this.SQL);
            }
            catch (SQLException sQLException) {}
        } else if ("Oracle".equals(this.sdbmanager)) {
            this.SQL = "ALTER SEQUENCE pickup_number RESTART WITH 1";
            try {
                this.stmt.executeUpdate(this.SQL);
            }
            catch (SQLException sQLException) {}
        } else if ("Apache Derby".equals(this.sdbmanager)) {
            this.SQL = "ALTER TABLE pickup_number ALTER COLUMN ID RESTART WITH 1";
            try {
                this.stmt.executeUpdate(this.SQL);
            }
            catch (SQLException sQLException) {}
        } else if ("Derby".equals(this.sdbmanager)) {
            this.SQL = "UPDATE pickup_number SET ID=0";
            try {
                this.stmt.executeUpdate(this.SQL);
            }
            catch (SQLException sQLException) {}
        } else {
            this.SQL = "ALTER SEQUENCE pickup_number RESTART WITH 1";
            try {
                this.stmt.executeUpdate(this.SQL);
            }
            catch (SQLException sQLException) {
                // empty catch block
            }
        }
        JOptionPane.showMessageDialog(this, "Reset complete.");
    }

    @Override
    public JComponent getComponent() {
        return this;
    }

    @Override
    public String getTitle() {
        return AppLocal.getIntString("Menu.Resetpickup");
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
            this.session = AppViewConnection.createSession(this.m_props);
            this.con = DriverManager.getConnection(db_url, db_user, db_password);
            this.sdbmanager = this.con.getMetaData().getDatabaseProductName();
            this.stmt = this.con.createStatement();
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
        this.jbtnUpdate = new JButton();
        this.jbtnExit = new JButton();
        this.setPreferredSize(new Dimension(200, 100));
        this.jPanel1.setPreferredSize(new Dimension(342, 80));
        this.jbtnUpdate.setFont(new Font("Arial", 0, 12));
        this.jbtnUpdate.setText(AppLocal.getIntString("label.resetpickup"));
        this.jbtnUpdate.setMaximumSize(new Dimension(70, 33));
        this.jbtnUpdate.setMinimumSize(new Dimension(70, 33));
        this.jbtnUpdate.setPreferredSize(new Dimension(70, 33));
        this.jbtnUpdate.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPanelResetPickupId.this.jbtnUpdateActionPerformed(evt);
            }
        });
        this.jbtnExit.setFont(new Font("Arial", 0, 12));
        this.jbtnExit.setText(AppLocal.getIntString("button.exit"));
        this.jbtnExit.setMaximumSize(new Dimension(70, 33));
        this.jbtnExit.setMinimumSize(new Dimension(70, 33));
        this.jbtnExit.setPreferredSize(new Dimension(70, 33));
        this.jbtnExit.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPanelResetPickupId.this.jbtnExitActionPerformed(evt);
            }
        });
        GroupLayout jPanel1Layout = new GroupLayout(this.jPanel1);
        this.jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addGap(20, 20, 20).addComponent(this.jbtnUpdate, -2, 155, -2).addGap(56, 56, 56).addComponent(this.jbtnExit, -2, 101, -2).addContainerGap(-1, Short.MAX_VALUE)));
        jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addGap(22, 22, 22).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jbtnUpdate, -2, -1, -2).addComponent(this.jbtnExit, -2, -1, -2)).addContainerGap(25, Short.MAX_VALUE)));
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jPanel1, GroupLayout.Alignment.TRAILING, -1, -1, Short.MAX_VALUE));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jPanel1, -2, -1, -2));
    }

    private void jbtnUpdateActionPerformed(ActionEvent evt) {
        this.performReset();
    }

    private void jbtnExitActionPerformed(ActionEvent evt) {
        this.deactivate();
        System.exit(0);
    }
}

