/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.config;

import com.openbravo.data.user.DirtyManager;
import com.openbravo.pos.config.PanelConfig;
import com.openbravo.pos.forms.AppConfig;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import javax.swing.GroupLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;

public class JPanelConfigSystem
extends JPanel
implements PanelConfig {
    private DirtyManager dirty = new DirtyManager();
    private JCheckBox jCheckPrice00;
    private JCheckBox jCloseCashbtn;
    private JTextField jCustomerColour1;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabelInactiveTime;
    private JLabel jLabelInactiveTime1;
    private JLabel jLabelSCRate;
    private JLabel jLabelSCRatePerCent;
    private JLabel jLabelTableNameTextColour;
    private JLabel jLabelTimedMessage;
    private JLabel jLblautoRefresh;
    private JCheckBox jMoveAMountBoxToTop;
    private JTextField jTableNameColour1;
    private JCheckBox jTaxIncluded;
    private JTextField jTextAutoLogoffTime;
    private JTextField jTextSCRate;
    private JTextField jTxtautoRefreshTimer;
    private JTextField jWaiterColour1;
    private JCheckBox jchkAutoLogoff;
    private JCheckBox jchkAutoLogoffToTables;
    private JCheckBox jchkBarcodetype;
    private JCheckBox jchkInstance;
    private JCheckBox jchkPriceUpdate;
    private JCheckBox jchkSCOnOff;
    private JCheckBox jchkSCRestaurant;
    private JCheckBox jchkShowCustomerDetails;
    private JCheckBox jchkShowWaiterDetails;
    private JCheckBox jchkTextOverlay;
    private JCheckBox jchkTransBtn;
    private JCheckBox jchkautoRefreshTableMap;

    public JPanelConfigSystem() {
        this.initComponents();
        this.jTextAutoLogoffTime.getDocument().addDocumentListener(this.dirty);
        this.jchkInstance.addActionListener(this.dirty);
        this.jchkTextOverlay.addActionListener(this.dirty);
        this.jchkAutoLogoff.addActionListener(this.dirty);
        this.jchkAutoLogoffToTables.addActionListener(this.dirty);
        this.jchkShowCustomerDetails.addActionListener(this.dirty);
        this.jchkShowWaiterDetails.addActionListener(this.dirty);
        this.jCustomerColour1.addActionListener(this.dirty);
        this.jWaiterColour1.addActionListener(this.dirty);
        this.jTableNameColour1.addActionListener(this.dirty);
        this.jTaxIncluded.addActionListener(this.dirty);
        this.jCheckPrice00.addActionListener(this.dirty);
        this.jMoveAMountBoxToTop.addActionListener(this.dirty);
        this.jCloseCashbtn.addActionListener(this.dirty);
        this.jchkautoRefreshTableMap.addActionListener(this.dirty);
        this.jTxtautoRefreshTimer.getDocument().addDocumentListener(this.dirty);
        this.jchkSCOnOff.addActionListener(this.dirty);
        this.jchkSCRestaurant.addActionListener(this.dirty);
        this.jTextSCRate.getDocument().addDocumentListener(this.dirty);
        this.jchkPriceUpdate.addActionListener(this.dirty);
        this.jchkBarcodetype.addActionListener(this.dirty);
        this.jchkTransBtn.addActionListener(this.dirty);
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
        String timerCheck = config.getProperty("till.autotimer");
        if (timerCheck == null) {
            config.setProperty("till.autotimer", "100");
        }
        this.jTextAutoLogoffTime.setText(config.getProperty("till.autotimer"));
        String autoRefreshtimerCheck = config.getProperty("till.autoRefreshTimer");
        if (autoRefreshtimerCheck == null) {
            config.setProperty("till.autoRefreshTimer", "5");
        }
        this.jTxtautoRefreshTimer.setText(config.getProperty("till.autoRefreshTimer"));
        this.jchkInstance.setSelected(Boolean.parseBoolean(config.getProperty("machine.uniqueinstance")));
        this.jchkShowCustomerDetails.setSelected(Boolean.parseBoolean(config.getProperty("table.showcustomerdetails")));
        this.jchkShowWaiterDetails.setSelected(Boolean.parseBoolean(config.getProperty("table.showwaiterdetails")));
        this.jchkTextOverlay.setSelected(Boolean.parseBoolean(config.getProperty("payments.textoverlay")));
        this.jchkAutoLogoff.setSelected(Boolean.parseBoolean(config.getProperty("till.autoLogoff")));
        this.jchkAutoLogoffToTables.setSelected(Boolean.parseBoolean(config.getProperty("till.autoLogoffrestaurant")));
        this.jTaxIncluded.setSelected(Boolean.parseBoolean(config.getProperty("till.taxincluded")));
        this.jCheckPrice00.setSelected(Boolean.parseBoolean(config.getProperty("till.pricewith00")));
        this.jMoveAMountBoxToTop.setSelected(Boolean.parseBoolean(config.getProperty("till.amountattop")));
        this.jCloseCashbtn.setSelected(Boolean.parseBoolean(config.getProperty("screen.600800")));
        this.jchkautoRefreshTableMap.setSelected(Boolean.parseBoolean(config.getProperty("till.autoRefreshTableMap")));
        this.jchkPriceUpdate.setSelected(AppConfig.getInstance().getBoolean("db.prodpriceupdate"));
        this.jchkBarcodetype.setSelected(Boolean.parseBoolean(config.getProperty("machine.barcodetype")));
        String SCCheck = config.getProperty("till.SCRate");
        if (SCCheck == null) {
            config.setProperty("till.SCRate", "0");
        }
        this.jTextSCRate.setText(config.getProperty("till.SCRate"));
        this.jchkSCOnOff.setSelected(Boolean.parseBoolean(config.getProperty("till.SCOnOff")));
        this.jchkSCRestaurant.setSelected(Boolean.parseBoolean(config.getProperty("till.SCRestaurant")));
        if (this.jchkSCOnOff.isSelected()) {
            this.jchkSCRestaurant.setVisible(true);
            this.jLabelSCRate.setVisible(true);
            this.jTextSCRate.setVisible(true);
            this.jLabelSCRatePerCent.setVisible(true);
        } else {
            this.jchkSCRestaurant.setVisible(false);
            this.jLabelSCRate.setVisible(false);
            this.jTextSCRate.setVisible(false);
            this.jLabelSCRatePerCent.setVisible(false);
        }
        if (this.jchkAutoLogoff.isSelected()) {
            this.jchkAutoLogoffToTables.setVisible(true);
            this.jLabelInactiveTime.setVisible(true);
            this.jLabelTimedMessage.setVisible(true);
            this.jTextAutoLogoffTime.setVisible(true);
        } else {
            this.jchkAutoLogoffToTables.setVisible(false);
            this.jLabelInactiveTime.setVisible(false);
            this.jLabelTimedMessage.setVisible(false);
            this.jTextAutoLogoffTime.setVisible(false);
        }
        if (this.jchkautoRefreshTableMap.isSelected()) {
            this.jLblautoRefresh.setVisible(true);
            this.jLabelInactiveTime1.setVisible(true);
            this.jTxtautoRefreshTimer.setVisible(true);
        } else {
            this.jLblautoRefresh.setVisible(false);
            this.jLabelInactiveTime1.setVisible(false);
            this.jTxtautoRefreshTimer.setVisible(false);
        }
        if (config.getProperty("table.customercolour") == null) {
            this.jCustomerColour1.setText("blue");
        } else {
            this.jCustomerColour1.setText(config.getProperty("table.customercolour"));
        }
        if (config.getProperty("table.waitercolour") == null) {
            this.jWaiterColour1.setText("red");
        } else {
            this.jWaiterColour1.setText(config.getProperty("table.waitercolour"));
        }
        if (config.getProperty("table.tablecolour") == null) {
            this.jTableNameColour1.setText("black");
        } else {
            this.jTableNameColour1.setText(config.getProperty("table.tablecolour"));
        }
        this.jchkTransBtn.setSelected(Boolean.parseBoolean(config.getProperty("table.transbtn")));
        this.dirty.setDirty(false);
    }

    @Override
    public void saveProperties(AppConfig config) {
        config.setProperty("till.autotimer", this.jTextAutoLogoffTime.getText());
        config.setProperty("machine.uniqueinstance", Boolean.toString(this.jchkInstance.isSelected()));
        config.setProperty("table.showcustomerdetails", Boolean.toString(this.jchkShowCustomerDetails.isSelected()));
        config.setProperty("table.showwaiterdetails", Boolean.toString(this.jchkShowWaiterDetails.isSelected()));
        config.setProperty("payments.textoverlay", Boolean.toString(this.jchkTextOverlay.isSelected()));
        config.setProperty("till.autoLogoff", Boolean.toString(this.jchkAutoLogoff.isSelected()));
        config.setProperty("till.autoLogoffrestaurant", Boolean.toString(this.jchkAutoLogoffToTables.isSelected()));
        config.setProperty("table.customercolour", this.jCustomerColour1.getText());
        config.setProperty("table.waitercolour", this.jWaiterColour1.getText());
        config.setProperty("table.tablecolour", this.jTableNameColour1.getText());
        config.setProperty("till.taxincluded", Boolean.toString(this.jTaxIncluded.isSelected()));
        config.setProperty("till.pricewith00", Boolean.toString(this.jCheckPrice00.isSelected()));
        config.setProperty("till.amountattop", Boolean.toString(this.jMoveAMountBoxToTop.isSelected()));
        config.setProperty("screen.600800", Boolean.toString(this.jCloseCashbtn.isSelected()));
        config.setProperty("till.autoRefreshTableMap", Boolean.toString(this.jchkautoRefreshTableMap.isSelected()));
        config.setProperty("till.autoRefreshTimer", this.jTxtautoRefreshTimer.getText());
        config.setProperty("till.SCOnOff", Boolean.toString(this.jchkSCOnOff.isSelected()));
        config.setProperty("till.SCRate", this.jTextSCRate.getText());
        config.setProperty("till.SCRestaurant", Boolean.toString(this.jchkSCRestaurant.isSelected()));
        config.setProperty("db.prodpriceupdate", Boolean.toString(this.jchkPriceUpdate.isSelected()));
        config.setProperty("machine.barcodetype", Boolean.toString(this.jchkBarcodetype.isSelected()));
        config.setProperty("table.transbtn", Boolean.toString(this.jchkTransBtn.isSelected()));
        this.dirty.setDirty(false);
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.jLabel2 = new JLabel();
        this.jLabel3 = new JLabel();
        this.jLabel4 = new JLabel();
        this.jchkInstance = new JCheckBox();
        this.jLabelInactiveTime = new JLabel();
        this.jTextAutoLogoffTime = new JTextField();
        this.jLabelTimedMessage = new JLabel();
        this.jchkAutoLogoff = new JCheckBox();
        this.jchkAutoLogoffToTables = new JCheckBox();
        this.jchkShowCustomerDetails = new JCheckBox();
        this.jchkShowWaiterDetails = new JCheckBox();
        this.jLabelTableNameTextColour = new JLabel();
        this.jCheckPrice00 = new JCheckBox();
        this.jTaxIncluded = new JCheckBox();
        this.jCloseCashbtn = new JCheckBox();
        this.jMoveAMountBoxToTop = new JCheckBox();
        this.jchkTextOverlay = new JCheckBox();
        this.jchkautoRefreshTableMap = new JCheckBox();
        this.jLabelInactiveTime1 = new JLabel();
        this.jTxtautoRefreshTimer = new JTextField();
        this.jLblautoRefresh = new JLabel();
        this.jchkSCOnOff = new JCheckBox();
        this.jLabelSCRate = new JLabel();
        this.jTextSCRate = new JTextField();
        this.jLabelSCRatePerCent = new JLabel();
        this.jchkSCRestaurant = new JCheckBox();
        this.jCustomerColour1 = new JTextField();
        this.jWaiterColour1 = new JTextField();
        this.jTableNameColour1 = new JTextField();
        this.jchkPriceUpdate = new JCheckBox();
        this.jchkBarcodetype = new JCheckBox();
        this.jchkTransBtn = new JCheckBox();
        this.setBackground(new Color(255, 255, 255));
        this.setFont(new Font("Arial", 0, 14));
        this.setPreferredSize(new Dimension(700, 500));
        this.jLabel1.setFont(new Font("Arial", 1, 14));
        ResourceBundle bundle = ResourceBundle.getBundle("pos_messages");
        this.jLabel1.setText(bundle.getString("label.configOptionStartup"));
        this.jLabel1.setPreferredSize(new Dimension(250, 30));
        this.jLabel2.setFont(new Font("Arial", 1, 14));
        this.jLabel2.setText(bundle.getString("label.configOptionKeypad"));
        this.jLabel2.setPreferredSize(new Dimension(250, 30));
        this.jLabel3.setFont(new Font("Arial", 1, 14));
        this.jLabel3.setText(bundle.getString("label.configOptionLogOff"));
        this.jLabel3.setPreferredSize(new Dimension(100, 30));
        this.jLabel4.setFont(new Font("Arial", 1, 14));
        this.jLabel4.setText(bundle.getString("label.configOptionRestaurant"));
        this.jLabel4.setPreferredSize(new Dimension(250, 30));
        this.jchkInstance.setBackground(new Color(255, 255, 255));
        this.jchkInstance.setFont(new Font("Arial", 0, 14));
        this.jchkInstance.setText(bundle.getString("label.instance"));
        this.jchkInstance.setMaximumSize(new Dimension(0, 25));
        this.jchkInstance.setMinimumSize(new Dimension(0, 0));
        this.jchkInstance.setPreferredSize(new Dimension(250, 25));
        this.jLabelInactiveTime.setBackground(new Color(255, 255, 255));
        this.jLabelInactiveTime.setFont(new Font("Arial", 0, 14));
        this.jLabelInactiveTime.setHorizontalAlignment(11);
        this.jLabelInactiveTime.setText(bundle.getString("label.autolofftime"));
        this.jLabelInactiveTime.setMaximumSize(new Dimension(0, 25));
        this.jLabelInactiveTime.setMinimumSize(new Dimension(0, 0));
        this.jLabelInactiveTime.setPreferredSize(new Dimension(100, 30));
        this.jTextAutoLogoffTime.setFont(new Font("Arial", 0, 14));
        this.jTextAutoLogoffTime.setText("0");
        this.jTextAutoLogoffTime.setMaximumSize(new Dimension(0, 25));
        this.jTextAutoLogoffTime.setMinimumSize(new Dimension(0, 0));
        this.jTextAutoLogoffTime.setPreferredSize(new Dimension(0, 30));
        this.jLabelTimedMessage.setFont(new Font("Arial", 0, 14));
        this.jLabelTimedMessage.setText(bundle.getString("label.autologoffzero"));
        this.jLabelTimedMessage.setMaximumSize(new Dimension(0, 25));
        this.jLabelTimedMessage.setMinimumSize(new Dimension(0, 0));
        this.jLabelTimedMessage.setPreferredSize(new Dimension(200, 30));
        this.jchkAutoLogoff.setBackground(new Color(255, 255, 255));
        this.jchkAutoLogoff.setFont(new Font("Arial", 0, 14));
        this.jchkAutoLogoff.setText(bundle.getString("label.autologonoff"));
        this.jchkAutoLogoff.setMaximumSize(new Dimension(0, 25));
        this.jchkAutoLogoff.setMinimumSize(new Dimension(0, 0));
        this.jchkAutoLogoff.setPreferredSize(new Dimension(200, 30));
        this.jchkAutoLogoff.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPanelConfigSystem.this.jchkAutoLogoffActionPerformed(evt);
            }
        });
        this.jchkAutoLogoffToTables.setBackground(new Color(255, 255, 255));
        this.jchkAutoLogoffToTables.setFont(new Font("Arial", 0, 14));
        this.jchkAutoLogoffToTables.setText(bundle.getString("label.autoloffrestaurant"));
        this.jchkAutoLogoffToTables.setMaximumSize(new Dimension(0, 25));
        this.jchkAutoLogoffToTables.setMinimumSize(new Dimension(0, 0));
        this.jchkAutoLogoffToTables.setPreferredSize(new Dimension(0, 30));
        this.jchkAutoLogoffToTables.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPanelConfigSystem.this.jchkAutoLogoffToTablesActionPerformed(evt);
            }
        });
        this.jchkShowCustomerDetails.setBackground(new Color(255, 255, 255));
        this.jchkShowCustomerDetails.setFont(new Font("Arial", 0, 14));
        this.jchkShowCustomerDetails.setText(bundle.getString("label.tableshowcustomerdetails"));
        this.jchkShowCustomerDetails.setMaximumSize(new Dimension(0, 25));
        this.jchkShowCustomerDetails.setMinimumSize(new Dimension(0, 0));
        this.jchkShowCustomerDetails.setPreferredSize(new Dimension(350, 30));
        this.jchkShowCustomerDetails.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPanelConfigSystem.this.jchkShowCustomerDetailsActionPerformed(evt);
            }
        });
        this.jchkShowWaiterDetails.setBackground(new Color(255, 255, 255));
        this.jchkShowWaiterDetails.setFont(new Font("Arial", 0, 14));
        this.jchkShowWaiterDetails.setText(bundle.getString("label.tableshowwaiterdetails"));
        this.jchkShowWaiterDetails.setMaximumSize(new Dimension(0, 25));
        this.jchkShowWaiterDetails.setMinimumSize(new Dimension(0, 0));
        this.jchkShowWaiterDetails.setPreferredSize(new Dimension(350, 30));
        this.jLabelTableNameTextColour.setBackground(new Color(255, 255, 255));
        this.jLabelTableNameTextColour.setFont(new Font("Arial", 0, 14));
        this.jLabelTableNameTextColour.setText(bundle.getString("label.textclourtablename"));
        this.jLabelTableNameTextColour.setMaximumSize(new Dimension(0, 25));
        this.jLabelTableNameTextColour.setMinimumSize(new Dimension(0, 0));
        this.jLabelTableNameTextColour.setPreferredSize(new Dimension(350, 30));
        this.jCheckPrice00.setBackground(new Color(255, 255, 255));
        this.jCheckPrice00.setFont(new Font("Arial", 0, 14));
        this.jCheckPrice00.setText(bundle.getString("label.pricewith00"));
        this.jCheckPrice00.setToolTipText("");
        this.jCheckPrice00.setMaximumSize(new Dimension(0, 25));
        this.jCheckPrice00.setMinimumSize(new Dimension(0, 0));
        this.jCheckPrice00.setPreferredSize(new Dimension(250, 25));
        this.jCheckPrice00.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPanelConfigSystem.this.jCheckPrice00ActionPerformed(evt);
            }
        });
        this.jTaxIncluded.setBackground(new Color(255, 255, 255));
        this.jTaxIncluded.setFont(new Font("Arial", 0, 14));
        this.jTaxIncluded.setText(bundle.getString("label.taxincluded"));
        this.jTaxIncluded.setMaximumSize(new Dimension(0, 25));
        this.jTaxIncluded.setMinimumSize(new Dimension(0, 0));
        this.jTaxIncluded.setPreferredSize(new Dimension(250, 25));
        this.jCloseCashbtn.setBackground(new Color(255, 255, 255));
        this.jCloseCashbtn.setFont(new Font("Arial", 0, 14));
        this.jCloseCashbtn.setText(bundle.getString("message.systemclosecash"));
        this.jCloseCashbtn.setHorizontalAlignment(2);
        this.jCloseCashbtn.setMaximumSize(new Dimension(0, 25));
        this.jCloseCashbtn.setMinimumSize(new Dimension(0, 0));
        this.jCloseCashbtn.setPreferredSize(new Dimension(250, 25));
        this.jMoveAMountBoxToTop.setBackground(new Color(255, 255, 255));
        this.jMoveAMountBoxToTop.setFont(new Font("Arial", 0, 14));
        this.jMoveAMountBoxToTop.setText(bundle.getString("label.inputamount"));
        this.jMoveAMountBoxToTop.setMaximumSize(new Dimension(0, 25));
        this.jMoveAMountBoxToTop.setMinimumSize(new Dimension(0, 0));
        this.jMoveAMountBoxToTop.setPreferredSize(new Dimension(250, 25));
        this.jchkTextOverlay.setBackground(new Color(255, 255, 255));
        this.jchkTextOverlay.setFont(new Font("Arial", 0, 14));
        this.jchkTextOverlay.setText(bundle.getString("label.currencybutton"));
        this.jchkTextOverlay.setHorizontalAlignment(2);
        this.jchkTextOverlay.setMaximumSize(new Dimension(0, 25));
        this.jchkTextOverlay.setMinimumSize(new Dimension(0, 0));
        this.jchkTextOverlay.setPreferredSize(new Dimension(250, 25));
        this.jchkautoRefreshTableMap.setBackground(new Color(255, 255, 255));
        this.jchkautoRefreshTableMap.setFont(new Font("Arial", 0, 14));
        this.jchkautoRefreshTableMap.setText(bundle.getString("label.autoRefreshTableMap"));
        this.jchkautoRefreshTableMap.setMaximumSize(new Dimension(0, 25));
        this.jchkautoRefreshTableMap.setMinimumSize(new Dimension(0, 0));
        this.jchkautoRefreshTableMap.setPreferredSize(new Dimension(200, 30));
        this.jchkautoRefreshTableMap.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPanelConfigSystem.this.jchkautoRefreshTableMapActionPerformed(evt);
            }
        });
        this.jLabelInactiveTime1.setBackground(new Color(255, 255, 255));
        this.jLabelInactiveTime1.setFont(new Font("Arial", 0, 14));
        this.jLabelInactiveTime1.setHorizontalAlignment(11);
        this.jLabelInactiveTime1.setText(bundle.getString("label.autolofftime"));
        this.jLabelInactiveTime1.setMaximumSize(new Dimension(0, 25));
        this.jLabelInactiveTime1.setMinimumSize(new Dimension(0, 0));
        this.jLabelInactiveTime1.setPreferredSize(new Dimension(100, 30));
        this.jTxtautoRefreshTimer.setFont(new Font("Arial", 0, 14));
        this.jTxtautoRefreshTimer.setText("0");
        this.jTxtautoRefreshTimer.setMaximumSize(new Dimension(0, 25));
        this.jTxtautoRefreshTimer.setMinimumSize(new Dimension(0, 0));
        this.jTxtautoRefreshTimer.setPreferredSize(new Dimension(0, 30));
        this.jLblautoRefresh.setFont(new Font("Arial", 0, 14));
        this.jLblautoRefresh.setText(bundle.getString("label.autoRefreshTableMapTimer"));
        this.jLblautoRefresh.setMaximumSize(new Dimension(0, 25));
        this.jLblautoRefresh.setMinimumSize(new Dimension(0, 0));
        this.jLblautoRefresh.setPreferredSize(new Dimension(200, 30));
        this.jchkSCOnOff.setBackground(new Color(255, 255, 255));
        this.jchkSCOnOff.setFont(new Font("Arial", 0, 14));
        this.jchkSCOnOff.setText(bundle.getString("label.SCOnOff"));
        this.jchkSCOnOff.setMaximumSize(new Dimension(0, 25));
        this.jchkSCOnOff.setMinimumSize(new Dimension(0, 0));
        this.jchkSCOnOff.setPreferredSize(new Dimension(0, 25));
        this.jchkSCOnOff.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPanelConfigSystem.this.jchkSCOnOffActionPerformed(evt);
            }
        });
        this.jLabelSCRate.setFont(new Font("Arial", 0, 14));
        this.jLabelSCRate.setText(bundle.getString("label.SCRate"));
        this.jLabelSCRate.setMaximumSize(new Dimension(0, 25));
        this.jLabelSCRate.setMinimumSize(new Dimension(0, 0));
        this.jLabelSCRate.setPreferredSize(new Dimension(190, 30));
        this.jTextSCRate.setFont(new Font("Arial", 0, 14));
        this.jTextSCRate.setText("0");
        this.jTextSCRate.setMaximumSize(new Dimension(0, 25));
        this.jTextSCRate.setMinimumSize(new Dimension(0, 0));
        this.jTextSCRate.setPreferredSize(new Dimension(0, 30));
        this.jTextSCRate.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPanelConfigSystem.this.jTextSCRateActionPerformed(evt);
            }
        });
        this.jLabelSCRatePerCent.setFont(new Font("Arial", 0, 14));
        this.jLabelSCRatePerCent.setHorizontalAlignment(4);
        this.jLabelSCRatePerCent.setText(bundle.getString("label.SCZero"));
        this.jLabelSCRatePerCent.setMaximumSize(new Dimension(0, 25));
        this.jLabelSCRatePerCent.setMinimumSize(new Dimension(0, 0));
        this.jLabelSCRatePerCent.setPreferredSize(new Dimension(0, 30));
        this.jchkSCRestaurant.setFont(new Font("Arial", 0, 14));
        this.jchkSCRestaurant.setText(bundle.getString("label.SCRestaurant"));
        this.jchkSCRestaurant.setMaximumSize(new Dimension(0, 25));
        this.jchkSCRestaurant.setMinimumSize(new Dimension(0, 0));
        this.jchkSCRestaurant.setPreferredSize(new Dimension(0, 25));
        this.jCustomerColour1.setForeground(new Color(0, 153, 255));
        this.jCustomerColour1.setToolTipText(bundle.getString("tooltip.prodhtmldisplayColourChooser"));
        this.jCustomerColour1.setFont(new Font("Arial", 0, 14));
        this.jCustomerColour1.setPreferredSize(new Dimension(200, 30));
        this.jWaiterColour1.setForeground(new Color(0, 153, 255));
        this.jWaiterColour1.setToolTipText(bundle.getString("tooltip.prodhtmldisplayColourChooser"));
        this.jWaiterColour1.setFont(new Font("Arial", 0, 14));
        this.jWaiterColour1.setPreferredSize(new Dimension(200, 30));
        this.jTableNameColour1.setForeground(new Color(0, 153, 255));
        this.jTableNameColour1.setToolTipText(bundle.getString("tooltip.prodhtmldisplayColourChooser"));
        this.jTableNameColour1.setFont(new Font("Arial", 0, 14));
        this.jTableNameColour1.setPreferredSize(new Dimension(200, 30));
        this.jchkPriceUpdate.setBackground(new Color(255, 255, 255));
        this.jchkPriceUpdate.setFont(new Font("Arial", 0, 14));
        this.jchkPriceUpdate.setText(bundle.getString("label.priceupdate"));
        this.jchkPriceUpdate.setToolTipText(bundle.getString("tooltip.priceupdate"));
        this.jchkPriceUpdate.setHorizontalAlignment(2);
        this.jchkPriceUpdate.setMaximumSize(new Dimension(0, 25));
        this.jchkPriceUpdate.setMinimumSize(new Dimension(0, 0));
        this.jchkPriceUpdate.setPreferredSize(new Dimension(250, 25));
        this.jchkBarcodetype.setBackground(new Color(255, 255, 255));
        this.jchkBarcodetype.setFont(new Font("Arial", 0, 14));
        this.jchkBarcodetype.setText(bundle.getString("label.barcodetype"));
        this.jchkBarcodetype.setToolTipText(bundle.getString("tooltip.barcodetype"));
        this.jchkBarcodetype.setHorizontalAlignment(2);
        this.jchkBarcodetype.setMaximumSize(new Dimension(0, 25));
        this.jchkBarcodetype.setMinimumSize(new Dimension(0, 0));
        this.jchkBarcodetype.setPreferredSize(new Dimension(250, 25));
        this.jchkTransBtn.setBackground(new Color(255, 255, 255));
        this.jchkTransBtn.setFont(new Font("Arial", 0, 14));
        this.jchkTransBtn.setText(bundle.getString("label.tabletransbutton"));
        this.jchkTransBtn.setMaximumSize(new Dimension(0, 25));
        this.jchkTransBtn.setMinimumSize(new Dimension(0, 0));
        this.jchkTransBtn.setPreferredSize(new Dimension(350, 30));
        this.jchkTransBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPanelConfigSystem.this.jchkTransBtnActionPerformed(evt);
            }
        });
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(10, 10, 10).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addComponent(this.jchkSCOnOff, GroupLayout.Alignment.LEADING, -1, -1, Short.MAX_VALUE).addComponent(this.jchkautoRefreshTableMap, GroupLayout.Alignment.LEADING, -1, -1, Short.MAX_VALUE)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jLabelInactiveTime1, -2, -1, -2)).addComponent(this.jchkShowWaiterDetails, -2, -1, -2).addComponent(this.jLabelTableNameTextColour, -2, -1, -2).addComponent(this.jchkShowCustomerDetails, -2, -1, -2).addComponent(this.jchkTransBtn, -2, -1, -2)).addGap(50, 50, 50)).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addComponent(this.jLabelSCRate, -2, 333, -2).addGap(12, 12, 12).addComponent(this.jLabelSCRatePerCent, -2, 32, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED))).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.jTextSCRate, -2, 50, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jchkSCRestaurant, -2, 240, -2)).addGroup(layout.createSequentialGroup().addComponent(this.jTxtautoRefreshTimer, -2, 50, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jLblautoRefresh, -2, -1, -2)).addComponent(this.jTableNameColour1, -1, -1, -2).addComponent(this.jWaiterColour1, -1, -1, -2).addComponent(this.jCustomerColour1, -1, -1, -2)).addContainerGap()).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(15, 15, 15).addComponent(this.jchkAutoLogoffToTables, -2, 340, -2)).addGroup(layout.createSequentialGroup().addComponent(this.jchkAutoLogoff, -1, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jLabelInactiveTime, -2, -1, -2).addGap(100, 100, 100).addComponent(this.jTextAutoLogoffTime, -2, 50, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jLabelTimedMessage, -2, -1, -2))).addGap(0, 0, Short.MAX_VALUE)))).addComponent(this.jLabel4, -2, -1, -2).addComponent(this.jLabel3, -2, 654, -2).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel1, -2, 260, -2).addGroup(layout.createSequentialGroup().addGap(10, 10, 10).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jchkInstance, -1, -1, -2).addComponent(this.jTaxIncluded, -1, -1, -2).addComponent(this.jchkTextOverlay, -1, -1, -2).addComponent(this.jchkPriceUpdate, -1, -1, -2)))).addGap(150, 150, 150).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel2, GroupLayout.Alignment.TRAILING, -2, 250, -2).addComponent(this.jCheckPrice00, -1, -1, -2).addComponent(this.jMoveAMountBoxToTop, -1, -1, -2).addComponent(this.jCloseCashbtn, -1, -1, -2).addComponent(this.jchkBarcodetype, -1, -1, -2))))));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1, -2, -1, -2).addComponent(this.jLabel2, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jTaxIncluded, -2, -1, -2).addComponent(this.jCheckPrice00, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jchkInstance, -2, -1, -2).addComponent(this.jMoveAMountBoxToTop, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jchkTextOverlay, -2, -1, -2).addComponent(this.jCloseCashbtn, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jchkPriceUpdate, -2, -1, -2).addComponent(this.jchkBarcodetype, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jLabel3, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jchkAutoLogoff, -2, -1, -2).addComponent(this.jLabelInactiveTime, -2, -1, -2).addComponent(this.jTextAutoLogoffTime, -2, -1, -2).addComponent(this.jLabelTimedMessage, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jchkAutoLogoffToTables, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jchkautoRefreshTableMap, -2, -1, -2).addComponent(this.jLabelInactiveTime1, -2, -1, -2).addComponent(this.jTxtautoRefreshTimer, -2, -1, -2).addComponent(this.jLblautoRefresh, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jchkSCOnOff, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabelSCRate, -2, -1, -2).addComponent(this.jTextSCRate, -2, -1, -2).addComponent(this.jLabelSCRatePerCent, -2, -1, -2).addComponent(this.jchkSCRestaurant, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jLabel4, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jchkShowCustomerDetails, -2, -1, -2).addComponent(this.jCustomerColour1, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jchkShowWaiterDetails, -2, -1, -2).addComponent(this.jWaiterColour1, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabelTableNameTextColour, -2, -1, -2).addComponent(this.jTableNameColour1, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jchkTransBtn, -2, -1, -2).addContainerGap(25, Short.MAX_VALUE)));
        this.jCustomerColour1.getAccessibleContext().setAccessibleName("colourChooser");
        this.jWaiterColour1.getAccessibleContext().setAccessibleName("colourChooser1");
        this.jTableNameColour1.getAccessibleContext().setAccessibleName("colourChooser2");
    }

    private void jchkAutoLogoffActionPerformed(ActionEvent evt) {
        if (this.jchkAutoLogoff.isSelected()) {
            this.jchkAutoLogoffToTables.setVisible(true);
            this.jLabelInactiveTime.setVisible(true);
            this.jLabelTimedMessage.setVisible(true);
            this.jTextAutoLogoffTime.setVisible(true);
        } else {
            this.jchkAutoLogoffToTables.setVisible(false);
            this.jLabelInactiveTime.setVisible(false);
            this.jLabelTimedMessage.setVisible(false);
            this.jTextAutoLogoffTime.setVisible(false);
        }
    }

    private void jCheckPrice00ActionPerformed(ActionEvent evt) {
    }

    private void jchkAutoLogoffToTablesActionPerformed(ActionEvent evt) {
    }

    private void jchkShowCustomerDetailsActionPerformed(ActionEvent evt) {
    }

    private void jchkautoRefreshTableMapActionPerformed(ActionEvent evt) {
        if (this.jchkautoRefreshTableMap.isSelected()) {
            this.jLblautoRefresh.setVisible(true);
            this.jLabelInactiveTime1.setVisible(true);
            this.jTxtautoRefreshTimer.setVisible(true);
        } else {
            this.jLblautoRefresh.setVisible(false);
            this.jLabelInactiveTime1.setVisible(false);
            this.jTxtautoRefreshTimer.setVisible(false);
        }
    }

    private void jchkSCOnOffActionPerformed(ActionEvent evt) {
        if (this.jchkSCOnOff.isSelected()) {
            this.jchkSCRestaurant.setVisible(true);
            this.jLabelSCRate.setVisible(true);
            this.jTextSCRate.setVisible(true);
            this.jLabelSCRatePerCent.setVisible(true);
        } else {
            this.jchkSCRestaurant.setVisible(false);
            this.jLabelSCRate.setVisible(false);
            this.jTextSCRate.setVisible(false);
            this.jLabelSCRatePerCent.setVisible(false);
        }
    }

    private void jTextSCRateActionPerformed(ActionEvent evt) {
    }

    private void jchkTransBtnActionPerformed(ActionEvent evt) {
    }
}

