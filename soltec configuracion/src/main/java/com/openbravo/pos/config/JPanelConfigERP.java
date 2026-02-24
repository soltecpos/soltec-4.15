/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.config;

import com.openbravo.data.user.DirtyManager;
import com.openbravo.pos.config.PanelConfig;
import com.openbravo.pos.forms.AppConfig;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.util.AltEncrypter;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;

public class JPanelConfigERP
extends JPanel
implements PanelConfig {
    private DirtyManager dirty = new DirtyManager();
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabelId;
    private JLabel jLabelName;
    private JLabel jLabelProperties;
    private JTextField jTextField1;
    private JTextField jTextField2;
    private JLabel jlabelUrl;
    private JTextField jtxtId;
    private JTextField jtxtName;
    private JPasswordField jtxtPassword;
    private JTextField jtxtUrl;

    public JPanelConfigERP() {
        this.initComponents();
        this.jTextField1.getDocument().addDocumentListener(this.dirty);
        this.jTextField2.getDocument().addDocumentListener(this.dirty);
        this.jtxtId.getDocument().addDocumentListener(this.dirty);
        this.jtxtName.getDocument().addDocumentListener(this.dirty);
        this.jtxtPassword.getDocument().addDocumentListener(this.dirty);
        this.jtxtUrl.getDocument().addDocumentListener(this.dirty);
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
        this.jtxtUrl.setText(config.getProperty("erp.URL"));
        this.jtxtId.setText(config.getProperty("erp.id"));
        this.jTextField2.setText(config.getProperty("erp.pos"));
        this.jTextField1.setText(config.getProperty("erp.org"));
        String sERPUser = config.getProperty("erp.user");
        String sERPPassword = config.getProperty("erp.password");
        if (sERPUser != null && sERPPassword != null && sERPPassword.startsWith("crypt:")) {
            AltEncrypter cypher = new AltEncrypter("cypherkey" + sERPUser);
            sERPPassword = cypher.decrypt(sERPPassword.substring(6));
        }
        this.jtxtName.setText(sERPUser);
        this.jtxtPassword.setText(sERPPassword);
        this.dirty.setDirty(false);
    }

    @Override
    public void saveProperties(AppConfig config) {
        config.setProperty("erp.URL", this.jtxtUrl.getText());
        config.setProperty("erp.id", this.jtxtId.getText());
        config.setProperty("erp.pos", this.jTextField2.getText());
        config.setProperty("erp.org", this.jTextField1.getText());
        config.setProperty("erp.user", this.jtxtName.getText());
        AltEncrypter cypher = new AltEncrypter("cypherkey" + this.jtxtName.getText());
        config.setProperty("erp.password", "crypt:" + cypher.encrypt(new String(this.jtxtPassword.getPassword())));
        this.dirty.setDirty(false);
    }

    private void initComponents() {
        this.jtxtUrl = new JTextField();
        this.jtxtId = new JTextField();
        this.jTextField1 = new JTextField();
        this.jTextField2 = new JTextField();
        this.jtxtName = new JTextField();
        this.jtxtPassword = new JPasswordField();
        this.jlabelUrl = new JLabel();
        this.jLabelId = new JLabel();
        this.jLabel1 = new JLabel();
        this.jLabel2 = new JLabel();
        this.jLabelName = new JLabel();
        this.jLabelProperties = new JLabel();
        this.setBackground(new Color(255, 255, 255));
        this.setPreferredSize(new Dimension(700, 500));
        this.jtxtUrl.setFont(new Font("Arial", 0, 14));
        this.jtxtUrl.setPreferredSize(new Dimension(350, 30));
        this.jtxtId.setFont(new Font("Arial", 0, 14));
        this.jtxtId.setPreferredSize(new Dimension(250, 30));
        this.jTextField1.setFont(new Font("Arial", 0, 14));
        this.jTextField1.setPreferredSize(new Dimension(250, 30));
        this.jTextField2.setFont(new Font("Arial", 0, 14));
        this.jTextField2.setPreferredSize(new Dimension(250, 30));
        this.jtxtName.setFont(new Font("Arial", 0, 14));
        this.jtxtName.setPreferredSize(new Dimension(250, 30));
        this.jtxtPassword.setFont(new Font("Arial", 0, 14));
        this.jtxtPassword.setPreferredSize(new Dimension(250, 30));
        this.jlabelUrl.setFont(new Font("Arial", 0, 14));
        this.jlabelUrl.setText(AppLocal.getIntString("label.erpurl"));
        this.jlabelUrl.setPreferredSize(new Dimension(150, 30));
        this.jLabelId.setFont(new Font("Arial", 0, 14));
        this.jLabelId.setText(AppLocal.getIntString("label.erpid"));
        this.jLabelId.setPreferredSize(new Dimension(150, 30));
        this.jLabel1.setFont(new Font("Arial", 0, 14));
        this.jLabel1.setText(AppLocal.getIntString("label.erporg"));
        this.jLabel1.setPreferredSize(new Dimension(150, 30));
        this.jLabel2.setFont(new Font("Arial", 0, 14));
        this.jLabel2.setText(AppLocal.getIntString("label.erppos"));
        this.jLabel2.setPreferredSize(new Dimension(150, 30));
        this.jLabelName.setFont(new Font("Arial", 0, 14));
        this.jLabelName.setText(AppLocal.getIntString("label.erpuser"));
        this.jLabelName.setPreferredSize(new Dimension(150, 30));
        this.jLabelProperties.setFont(new Font("Arial", 0, 14));
        this.jLabelProperties.setText(AppLocal.getIntString("label.erppassword"));
        this.jLabelProperties.setPreferredSize(new Dimension(150, 30));
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jlabelUrl, -2, -1, -2).addComponent(this.jLabelId, -2, -1, -2).addComponent(this.jLabel1, -2, -1, -2).addComponent(this.jLabel2, -2, -1, -2).addComponent(this.jLabelName, -2, -1, -2).addComponent(this.jLabelProperties, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jtxtUrl, -2, -1, -2).addComponent(this.jtxtId, -2, -1, -2).addComponent(this.jTextField1, -2, -1, -2).addComponent(this.jTextField2, -2, -1, -2).addComponent(this.jtxtName, -2, -1, -2).addComponent(this.jtxtPassword, -2, -1, -2)).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addComponent(this.jtxtUrl, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jtxtId, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jTextField1, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jTextField2, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jtxtName, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jtxtPassword, -2, -1, -2)).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addComponent(this.jlabelUrl, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jLabelId, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jLabel1, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jLabel2, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jLabelName, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jLabelProperties, -2, -1, -2)))));
    }
}

