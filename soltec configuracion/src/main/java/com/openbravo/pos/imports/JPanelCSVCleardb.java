/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.imports;

import com.openbravo.basic.BasicException;
import com.openbravo.pos.forms.AppConfig;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.AppProperties;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.forms.JPanelView;
import com.openbravo.pos.util.AltEncrypter;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ResourceBundle;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class JPanelCSVCleardb
extends JPanel
implements JPanelView {
    private Connection con;
    private Statement stmt;
    private String SQL;
    private AppConfig config;
    private JButton jBtnCleardb;
    private JCheckBox jEnableButton;
    private JLabel jLabel1;
    private JTextPane jMessageBox;
    private JScrollPane jScrollPane1;

    public JPanelCSVCleardb(AppView oApp) {
        this(oApp.getProperties());
    }

    public JPanelCSVCleardb(AppProperties props) {
        this.initComponents();
        this.config = new AppConfig(props.getConfigFile());
        this.config.load();
        this.jMessageBox.setText("Performing this action, will clear all data in the CSVImport table. \n\nIt is recommended that this is performed before running a new import. \n");
    }

    @Override
    public String getTitle() {
        return AppLocal.getIntString("Menu.CSVReset");
    }

    @Override
    public JComponent getComponent() {
        return this;
    }

    @Override
    public void activate() throws BasicException {
        String db_user = this.config.getProperty("db.user");
        String db_url = this.config.getProperty("db.URL");
        String db_password = this.config.getProperty("db.password");
        if (db_user != null && db_password != null && db_password.startsWith("crypt:")) {
            AltEncrypter cypher = new AltEncrypter("cypherkey" + db_user);
            db_password = cypher.decrypt(db_password.substring(6));
        }
        try {
            this.con = DriverManager.getConnection(db_url, db_user, db_password);
            this.stmt = this.con.createStatement();
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    @Override
    public boolean deactivate() {
        try {
            this.stmt.close();
            this.con.close();
        }
        catch (Exception exception) {
            // empty catch block
        }
        return true;
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.jScrollPane1 = new JScrollPane();
        this.jMessageBox = new JTextPane();
        this.jEnableButton = new JCheckBox();
        this.jBtnCleardb = new JButton();
        this.setPreferredSize(new Dimension(450, 240));
        this.jLabel1.setFont(new Font("Arial", 0, 14));
        ResourceBundle bundle = ResourceBundle.getBundle("pos_messages");
        this.jLabel1.setText(bundle.getString("label.csvresetimport"));
        this.jLabel1.setPreferredSize(new Dimension(250, 30));
        this.jMessageBox.setEditable(false);
        this.jMessageBox.setFont(new Font("Arial", 0, 14));
        this.jScrollPane1.setViewportView(this.jMessageBox);
        this.jEnableButton.setFont(new Font("Arial", 0, 14));
        this.jEnableButton.setText(bundle.getString("label.csvenableclear"));
        this.jEnableButton.setPreferredSize(new Dimension(250, 30));
        this.jEnableButton.addChangeListener(new ChangeListener(){

            @Override
            public void stateChanged(ChangeEvent evt) {
                JPanelCSVCleardb.this.jEnableButtonStateChanged(evt);
            }
        });
        this.jEnableButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPanelCSVCleardb.this.jEnableButtonActionPerformed(evt);
            }
        });
        this.jBtnCleardb.setFont(new Font("Arial", 0, 12));
        this.jBtnCleardb.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/sale_delete.png")));
        this.jBtnCleardb.setText(bundle.getString("label.clearimport"));
        this.jBtnCleardb.setActionCommand(AppLocal.getIntString("button.exit"));
        this.jBtnCleardb.setEnabled(false);
        this.jBtnCleardb.setPreferredSize(new Dimension(160, 45));
        this.jBtnCleardb.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPanelCSVCleardb.this.jBtnCleardbActionPerformed(evt);
            }
        });
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel1, -2, -1, -2).addGroup(layout.createSequentialGroup().addGap(16, 16, 16).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addGroup(layout.createSequentialGroup().addComponent(this.jEnableButton, -2, 0, Short.MAX_VALUE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jBtnCleardb, -2, 218, -2)).addComponent(this.jScrollPane1, -2, 400, -2)))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jLabel1, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jScrollPane1, -2, 127, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jBtnCleardb, -2, -1, -2).addComponent(this.jEnableButton, -2, -1, -2)).addContainerGap()));
    }

    private void jEnableButtonStateChanged(ChangeEvent evt) {
        if (this.jEnableButton.isSelected()) {
            this.jBtnCleardb.setEnabled(true);
        } else {
            this.jBtnCleardb.setEnabled(false);
        }
    }

    private void jBtnCleardbActionPerformed(ActionEvent evt) {
        this.SQL = "DELETE FROM csvimport";
        try {
            this.stmt.executeUpdate(this.SQL);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    private void jEnableButtonActionPerformed(ActionEvent evt) {
    }
}

