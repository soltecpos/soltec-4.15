/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.config;

import com.openbravo.basic.BasicException;
import com.openbravo.data.gui.JMessageDialog;
import com.openbravo.data.gui.MessageInf;
import com.openbravo.pos.config.JPanelConfigCompany;
import com.openbravo.pos.config.JPanelConfigDatabase;
import com.openbravo.pos.config.JPanelConfigFE;
import com.openbravo.pos.config.JPanelConfigGeneral;
import com.openbravo.pos.config.JPanelConfigLocale;
import com.openbravo.pos.config.JPanelConfigPayment;
import com.openbravo.pos.config.JPanelConfigPeripheral;
import com.openbravo.pos.config.JPanelConfigSystem;
import com.openbravo.pos.config.JPanelTicketSetup;
import com.openbravo.pos.config.PanelConfig;
import com.openbravo.pos.forms.AppConfig;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.AppProperties;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.forms.JPanelView;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.LayoutStyle;

public class JPanelConfiguration
extends JPanel
implements JPanelView {
    private List<PanelConfig> m_panelconfig;
    private AppConfig config;
    private JPanel jPanel1;
    private JPanel jPanelCompany;
    private JPanel jPanelDatabase;
    private JPanel jPanelFE;
    private JPanel jPanelGeneral;
    private JPanel jPanelLocale;
    private JPanel jPanelPayment;
    private JPanel jPanelPeripheral;
    private JPanel jPanelSystem;
    private JPanel jPanelTicketSetup;
    private JTabbedPane jTabbedPane1;
    private JButton jbtnExit;
    private JButton jbtnRestore;
    private JButton jbtnSave;

    public JPanelConfiguration(AppView oApp) {
        this(oApp.getProperties());
        if (oApp != null) {
            this.jbtnExit.setVisible(false);
        }
    }

    public JPanelConfiguration(AppProperties props) {
        this.initComponents();
        this.config = new AppConfig(props.getConfigFile());
        this.m_panelconfig = new ArrayList<PanelConfig>();
        JPanel panel = new JPanelConfigDatabase();
        this.m_panelconfig.add((PanelConfig)((Object)panel));
        this.jPanelDatabase.add(((PanelConfig)panel).getConfigComponent());
        panel = new JPanelConfigGeneral();
        this.m_panelconfig.add((PanelConfig)((Object)panel));
        this.jPanelGeneral.add(((PanelConfig)panel).getConfigComponent());
        panel = new JPanelConfigLocale();
        this.m_panelconfig.add((PanelConfig)((Object)panel));
        this.jPanelLocale.add(((PanelConfig)panel).getConfigComponent());
        panel = new JPanelConfigPayment();
        this.m_panelconfig.add((PanelConfig)((Object)panel));
        this.jPanelPayment.add(((PanelConfig)panel).getConfigComponent());
        panel = new JPanelConfigPeripheral();
        this.m_panelconfig.add((PanelConfig)((Object)panel));
        this.jPanelPeripheral.add(((PanelConfig)panel).getConfigComponent());
        panel = new JPanelConfigSystem();
        this.m_panelconfig.add((PanelConfig)((Object)panel));
        this.jPanelSystem.add(((PanelConfig)panel).getConfigComponent());
        panel = new JPanelTicketSetup();
        this.m_panelconfig.add((PanelConfig)((Object)panel));
        this.jPanelTicketSetup.add(((PanelConfig)panel).getConfigComponent());
        panel = new JPanelConfigCompany();
        this.m_panelconfig.add((PanelConfig)((Object)panel));
        this.jPanelCompany.add(((PanelConfig)panel).getConfigComponent());
        panel = new JPanelConfigFE();
        this.m_panelconfig.add((PanelConfig)((Object)panel));
        JScrollPane scrollFE = new JScrollPane(((PanelConfig)panel).getConfigComponent());
        scrollFE.getVerticalScrollBar().setUnitIncrement(16);
        this.jPanelFE.add(scrollFE);
    }

    private void restoreProperties() {
        if (this.config.delete()) {
            this.loadProperties();
        } else {
            JMessageDialog.showMessage(this, new MessageInf(-33554432, AppLocal.getIntString("message.cannotdeleteconfig")));
        }
    }

    private void loadProperties() {
        this.config.load();
        for (PanelConfig c : this.m_panelconfig) {
            c.loadProperties(this.config);
        }
    }

    private void saveProperties() {
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
    }

    @Override
    public JComponent getComponent() {
        return this;
    }

    @Override
    public String getTitle() {
        return AppLocal.getIntString("Menu.Configuration");
    }

    @Override
    public void activate() throws BasicException {
        this.loadProperties();
    }

    @Override
    public boolean deactivate() {
        boolean haschanged = false;
        for (PanelConfig c : this.m_panelconfig) {
            if (!c.hasChanged()) continue;
            haschanged = true;
        }
        if (haschanged) {
            int res = JOptionPane.showConfirmDialog(this, AppLocal.getIntString("message.wannasave"), AppLocal.getIntString("title.editor"), 1, 3);
            if (res == 0) {
                this.saveProperties();
                return true;
            }
            return res == 1;
        }
        return true;
    }

    private void initComponents() {
        this.jTabbedPane1 = new JTabbedPane();
        this.jPanelGeneral = new JPanel();
        this.jPanelLocale = new JPanel();
        this.jPanelPayment = new JPanel();
        this.jPanelPeripheral = new JPanel();
        this.jPanelSystem = new JPanel();
        this.jPanelTicketSetup = new JPanel();
        this.jPanelCompany = new JPanel();
        this.jPanelFE = new JPanel();
        this.jPanelDatabase = new JPanel();
        this.jPanel1 = new JPanel();
        this.jbtnRestore = new JButton();
        this.jbtnExit = new JButton();
        this.jbtnSave = new JButton();
        this.setBackground(new Color(255, 255, 255));
        this.setFont(new Font("Arial", 0, 12));
        this.setMinimumSize(new Dimension(0, 0));
        this.setPreferredSize(new Dimension(950, 600));
        this.jTabbedPane1.setBackground(new Color(255, 255, 255));
        this.jTabbedPane1.setFont(new Font("Arial", 0, 14));
        this.jTabbedPane1.setPreferredSize(new Dimension(930, 550));
        this.jPanelGeneral.setBackground(new Color(255, 255, 255));
        this.jPanelGeneral.setFont(new Font("Arial", 0, 14));
        this.jPanelGeneral.setPreferredSize(new Dimension(0, 400));
        this.jPanelGeneral.setLayout(new BoxLayout(this.jPanelGeneral, 2));
        this.jTabbedPane1.addTab("General", this.jPanelGeneral);
        this.jPanelLocale.setBackground(new Color(255, 255, 255));
        this.jPanelLocale.setFont(new Font("Arial", 0, 12));
        this.jPanelLocale.setPreferredSize(new Dimension(0, 400));
        this.jPanelLocale.setLayout(new BoxLayout(this.jPanelLocale, 2));
        this.jTabbedPane1.addTab("Locale", this.jPanelLocale);
        this.jPanelPayment.setBackground(new Color(255, 255, 255));
        this.jPanelPayment.setFont(new Font("Arial", 0, 12));
        this.jPanelPayment.setPreferredSize(new Dimension(0, 400));
        this.jPanelPayment.setLayout(new BoxLayout(this.jPanelPayment, 2));
        this.jTabbedPane1.addTab("Payment Method", this.jPanelPayment);
        this.jPanelPeripheral.setBackground(new Color(255, 255, 255));
        this.jPanelPeripheral.setFont(new Font("Arial", 0, 12));
        this.jPanelPeripheral.setPreferredSize(new Dimension(0, 400));
        this.jPanelPeripheral.setLayout(new BoxLayout(this.jPanelPeripheral, 2));
        this.jTabbedPane1.addTab("Peripherals", this.jPanelPeripheral);
        this.jPanelSystem.setBackground(new Color(255, 255, 255));
        this.jPanelSystem.setFont(new Font("Arial", 0, 12));
        this.jPanelSystem.setPreferredSize(new Dimension(0, 400));
        this.jPanelSystem.setLayout(new BoxLayout(this.jPanelSystem, 2));
        this.jTabbedPane1.addTab("System Options", this.jPanelSystem);
        this.jPanelTicketSetup.setBackground(new Color(255, 255, 255));
        this.jPanelTicketSetup.setFont(new Font("Arial", 0, 12));
        this.jPanelTicketSetup.setPreferredSize(new Dimension(0, 400));
        this.jPanelTicketSetup.setLayout(new BoxLayout(this.jPanelTicketSetup, 2));
        this.jTabbedPane1.addTab("Ticket Setup", this.jPanelTicketSetup);
        this.jPanelCompany.setBackground(new Color(255, 255, 255));
        this.jPanelCompany.setFont(new Font("Arial", 0, 14));
        this.jPanelCompany.setLayout(new BoxLayout(this.jPanelCompany, 2));
        this.jTabbedPane1.addTab("Company", this.jPanelCompany);
        this.jPanelFE.setBackground(new Color(255, 255, 255));
        this.jPanelFE.setFont(new Font("Arial", 0, 14));
        this.jPanelFE.setPreferredSize(new Dimension(0, 550));
        this.jPanelFE.setLayout(new BoxLayout(this.jPanelFE, 2));
        this.jTabbedPane1.addTab("Facturaci\u00f3n Electr\u00f3nica", this.jPanelFE);
        this.jPanelDatabase.setBackground(new Color(255, 255, 255));
        this.jPanelDatabase.setFont(new Font("Arial", 0, 12));
        this.jPanelDatabase.setPreferredSize(new Dimension(0, 400));
        this.jPanelDatabase.setLayout(new BoxLayout(this.jPanelDatabase, 2));
        this.jTabbedPane1.addTab("Database Setup", this.jPanelDatabase);
        this.jPanel1.setBackground(new Color(255, 255, 255));
        this.jbtnRestore.setFont(new Font("Arial", 0, 12));
        this.jbtnRestore.setText(AppLocal.getIntString("button.factory"));
        this.jbtnRestore.setMaximumSize(new Dimension(103, 33));
        this.jbtnRestore.setMinimumSize(new Dimension(103, 33));
        this.jbtnRestore.setPreferredSize(new Dimension(110, 45));
        this.jbtnRestore.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPanelConfiguration.this.jbtnRestoreActionPerformed(evt);
            }
        });
        this.jbtnExit.setFont(new Font("Arial", 0, 12));
        this.jbtnExit.setText(AppLocal.getIntString("Button.Exit"));
        this.jbtnExit.setMaximumSize(new Dimension(70, 33));
        this.jbtnExit.setMinimumSize(new Dimension(70, 33));
        this.jbtnExit.setPreferredSize(new Dimension(110, 45));
        this.jbtnExit.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPanelConfiguration.this.jbtnExitActionPerformed(evt);
            }
        });
        this.jbtnSave.setFont(new Font("Arial", 0, 12));
        this.jbtnSave.setText(AppLocal.getIntString("button.save"));
        this.jbtnSave.setMaximumSize(new Dimension(70, 33));
        this.jbtnSave.setMinimumSize(new Dimension(70, 33));
        this.jbtnSave.setPreferredSize(new Dimension(110, 45));
        this.jbtnSave.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPanelConfiguration.this.jbtnSaveActionPerformed(evt);
            }
        });
        GroupLayout jPanel1Layout = new GroupLayout(this.jPanel1);
        this.jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jbtnSave, -2, -1, -2).addGap(18, 18, 18).addComponent(this.jbtnRestore, -2, -1, -2).addGap(18, 18, 18).addComponent(this.jbtnExit, -2, -1, -2).addContainerGap(31, Short.MAX_VALUE)));
        jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jbtnRestore, -2, -1, -2).addComponent(this.jbtnExit, -2, -1, -2).addComponent(this.jbtnSave, -2, -1, -2)));
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jPanel1, -2, -1, -2).addComponent(this.jTabbedPane1, -2, -1, -2)).addContainerGap(-1, Short.MAX_VALUE)));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addContainerGap().addComponent(this.jTabbedPane1, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addComponent(this.jPanel1, -2, -1, -2).addContainerGap()));
    }

    private void jbtnRestoreActionPerformed(ActionEvent evt) {
        if (JOptionPane.showConfirmDialog(this, AppLocal.getIntString("message.configfactory"), AppLocal.getIntString("message.title"), 0, 3) == 0) {
            this.restoreProperties();
        }
    }

    private void jbtnSaveActionPerformed(ActionEvent evt) {
        this.saveProperties();
    }

    private void jbtnExitActionPerformed(ActionEvent evt) {
        this.deactivate();
        System.exit(0);
    }
}

