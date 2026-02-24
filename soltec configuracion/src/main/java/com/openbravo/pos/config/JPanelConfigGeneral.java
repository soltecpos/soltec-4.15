/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.config;

import com.openbravo.data.user.DirtyManager;
import com.openbravo.pos.config.PanelConfig;
import com.openbravo.pos.forms.AppConfig;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.util.FileChooserEvent;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class JPanelConfigGeneral
extends JPanel
implements PanelConfig {
    private final DirtyManager dirty = new DirtyManager();
    private JLabel jLabel1;
    private JLabel jLabel18;
    private JLabel jLabel19;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel6;
    private JLabel jLblURL;
    private JPanel jPanel11;
    private JButton jbtnClearHTML;
    private JButton jbtnHTML;
    private JButton jbtnLogo;
    private JButton jbtnText;
    private JButton jbtnTextClear;
    private JComboBox<LAFInfo> jcboLAF;
    private JComboBox<String> jcboMachineScreenmode;
    private JComboBox<String> jcboTicketsBag;
    private JCheckBox jchkHideInfo;
    private JTextField jtxtMachineDepartment;
    private JTextField jtxtMachineHostname;
    private JTextField jtxtStartupHTML;
    private JTextField jtxtStartupLogo;
    private JTextField jtxtStartupText;
    private JLabel lblIP_Address;
    private JLabel webLabel1;

    public JPanelConfigGeneral() {
        UIManager.LookAndFeelInfo[] lafs;
        this.initComponents();
        InetAddress IP = null;
        try {
            IP = InetAddress.getLocalHost();
        }
        catch (UnknownHostException ex) {
            Logger.getLogger(JPanelConfigGeneral.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.jtxtMachineHostname.getDocument().addDocumentListener(this.dirty);
        this.jtxtMachineDepartment.getDocument().addDocumentListener(this.dirty);
        this.lblIP_Address.setText(IP.toString());
        this.jcboLAF.addActionListener(this.dirty);
        this.jcboMachineScreenmode.addActionListener(this.dirty);
        this.jcboTicketsBag.addActionListener(this.dirty);
        this.jchkHideInfo.addActionListener(this.dirty);
        this.jtxtStartupText.getDocument().addDocumentListener(this.dirty);
        this.jbtnText.addActionListener(new FileChooserEvent(this.jtxtStartupText));
        this.jtxtStartupLogo.getDocument().addDocumentListener(this.dirty);
        this.jbtnLogo.addActionListener(new FileChooserEvent(this.jtxtStartupLogo));
        this.jtxtStartupHTML.getDocument().addDocumentListener(this.dirty);
        this.jbtnHTML.addActionListener(new FileChooserEvent(this.jtxtStartupHTML));
        for (UIManager.LookAndFeelInfo laf : lafs = UIManager.getInstalledLookAndFeels()) {
            this.jcboLAF.addItem(new LAFInfo(laf.getName(), laf.getClassName()));
        }
        this.jcboLAF.addActionListener(evt -> this.changeLAF());
        this.jcboMachineScreenmode.addItem("window");
        this.jcboMachineScreenmode.addItem("fullscreen");
        this.jcboTicketsBag.addItem("simple");
        this.jcboTicketsBag.addItem("standard");
        this.jcboTicketsBag.addItem("restaurant");
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
        this.jtxtMachineHostname.setText(config.getProperty("machine.hostname"));
        this.jtxtMachineDepartment.setText(config.getProperty("machine.department"));
        String lafclass = config.getProperty("swing.defaultlaf");
        this.jcboLAF.setSelectedItem(null);
        for (int i = 0; i < this.jcboLAF.getItemCount(); ++i) {
            LAFInfo lafinfo = this.jcboLAF.getItemAt(i);
            if (!lafinfo.getClassName().equals(lafclass)) continue;
            this.jcboLAF.setSelectedIndex(i);
            break;
        }
        this.jcboMachineScreenmode.setSelectedItem(config.getProperty("machine.screenmode"));
        this.jcboTicketsBag.setSelectedItem(config.getProperty("machine.ticketsbag"));
        this.jchkHideInfo.setSelected(Boolean.parseBoolean(config.getProperty("till.hideinfo")));
        this.jtxtStartupLogo.setText(config.getProperty("start.logo"));
        this.jtxtStartupText.setText(config.getProperty("start.text"));
        this.jtxtStartupLogo.setText(config.getProperty("start.logo"));
        this.jtxtStartupHTML.setText(config.getProperty("start.html"));
        this.dirty.setDirty(false);
    }

    @Override
    public void saveProperties(AppConfig config) {
        config.setProperty("machine.hostname", this.jtxtMachineHostname.getText());
        config.setProperty("machine.department", this.jtxtMachineDepartment.getText());
        LAFInfo laf = (LAFInfo)this.jcboLAF.getSelectedItem();
        config.setProperty("swing.defaultlaf", laf == null ? System.getProperty("swing.defaultlaf", "javax.swing.plaf.metal.MetalLookAndFeel") : laf.getClassName());
        config.setProperty("machine.screenmode", this.comboValue(this.jcboMachineScreenmode.getSelectedItem()));
        config.setProperty("machine.ticketsbag", this.comboValue(this.jcboTicketsBag.getSelectedItem()));
        config.setProperty("till.hideinfo", Boolean.toString(this.jchkHideInfo.isSelected()));
        config.setProperty("start.logo", this.jtxtStartupLogo.getText());
        config.setProperty("start.text", this.jtxtStartupText.getText());
        config.setProperty("start.html", this.jtxtStartupHTML.getText());
        this.dirty.setDirty(false);
    }

    private String comboValue(Object value) {
        return value == null ? "" : value.toString();
    }

    private void changeLAF() {
        LAFInfo laf = (LAFInfo)this.jcboLAF.getSelectedItem();
        if (laf != null && !laf.getClassName().equals(UIManager.getLookAndFeel().getClass().getName())) {
            SwingUtilities.invokeLater(() -> {
                try {
                    String lafname = laf.getClassName();
                    Object laf1 = Class.forName(lafname).newInstance();
                    if (laf1 instanceof LookAndFeel) {
                        UIManager.setLookAndFeel((LookAndFeel)laf1);
                    }
                    SwingUtilities.updateComponentTreeUI(this.getTopLevelAncestor());
                }
                catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException exception) {
                    // empty catch block
                }
            });
        }
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
        this.jPanel11 = new JPanel();
        this.jLabel1 = new JLabel();
        this.jLabel2 = new JLabel();
        this.jLabel3 = new JLabel();
        this.jLabel4 = new JLabel();
        this.jtxtMachineHostname = new JTextField();
        this.jcboLAF = new JComboBox();
        this.jcboMachineScreenmode = new JComboBox();
        this.jcboTicketsBag = new JComboBox();
        this.jchkHideInfo = new JCheckBox();
        this.jLabel18 = new JLabel();
        this.jtxtStartupLogo = new JTextField();
        this.jLabel19 = new JLabel();
        this.jtxtStartupText = new JTextField();
        this.jbtnLogo = new JButton();
        this.jbtnText = new JButton();
        this.jbtnTextClear = new JButton();
        this.jLabel6 = new JLabel();
        this.jtxtMachineDepartment = new JTextField();
        this.lblIP_Address = new JLabel();
        this.webLabel1 = new JLabel();
        this.jLblURL = new JLabel();
        this.jtxtStartupHTML = new JTextField();
        this.jbtnHTML = new JButton();
        this.jbtnClearHTML = new JButton();
        this.setBackground(new Color(255, 255, 255));
        this.setFont(new Font("Arial", 0, 12));
        this.setPreferredSize(new Dimension(800, 450));
        this.jPanel11.setBackground(new Color(255, 255, 255));
        this.jPanel11.setOpaque(false);
        this.jPanel11.setPreferredSize(new Dimension(750, 450));
        this.jLabel1.setFont(new Font("Arial", 0, 14));
        this.jLabel1.setText(AppLocal.getIntString("label.MachineName"));
        this.jLabel1.setPreferredSize(new Dimension(150, 30));
        this.jLabel2.setFont(new Font("Arial", 0, 14));
        this.jLabel2.setText(AppLocal.getIntString("label.looknfeel"));
        this.jLabel2.setPreferredSize(new Dimension(150, 30));
        this.jLabel3.setFont(new Font("Arial", 0, 14));
        this.jLabel3.setText(AppLocal.getIntString("label.MachineScreen"));
        this.jLabel3.setPreferredSize(new Dimension(150, 30));
        this.jLabel4.setFont(new Font("Arial", 0, 14));
        this.jLabel4.setText(AppLocal.getIntString("label.Ticketsbag"));
        this.jLabel4.setPreferredSize(new Dimension(150, 30));
        this.jtxtMachineHostname.setFont(new Font("Arial", 0, 14));
        this.jtxtMachineHostname.setToolTipText(AppLocal.getIntString("tooltip.config.general.terminal"));
        this.jtxtMachineHostname.setCursor(new Cursor(0));
        this.jtxtMachineHostname.setMinimumSize(new Dimension(130, 25));
        this.jtxtMachineHostname.setPreferredSize(new Dimension(200, 30));
        this.jcboLAF.setFont(new Font("Arial", 0, 14));
        this.jcboLAF.setToolTipText(AppLocal.getIntString("tooltip.config.general.skin"));
        this.jcboLAF.setCursor(new Cursor(0));
        this.jcboLAF.setPreferredSize(new Dimension(200, 30));
        this.jcboLAF.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPanelConfigGeneral.this.jcboLAFActionPerformed(evt);
            }
        });
        this.jcboMachineScreenmode.setFont(new Font("Arial", 0, 14));
        this.jcboMachineScreenmode.setToolTipText(AppLocal.getIntString("tooltip.config.general.screen"));
        this.jcboMachineScreenmode.setCursor(new Cursor(0));
        this.jcboMachineScreenmode.setPreferredSize(new Dimension(200, 30));
        this.jcboTicketsBag.setFont(new Font("Arial", 0, 14));
        this.jcboTicketsBag.setToolTipText(AppLocal.getIntString("tooltip.config.general.tickets"));
        this.jcboTicketsBag.setCursor(new Cursor(0));
        this.jcboTicketsBag.setPreferredSize(new Dimension(200, 30));
        this.jchkHideInfo.setBackground(new Color(255, 255, 255));
        this.jchkHideInfo.setFont(new Font("Arial", 0, 14));
        ResourceBundle bundle = ResourceBundle.getBundle("pos_messages");
        this.jchkHideInfo.setText(bundle.getString("label.Infopanel"));
        this.jchkHideInfo.setToolTipText(AppLocal.getIntString("tooltip.config.general.footer"));
        this.jchkHideInfo.setHorizontalAlignment(2);
        this.jchkHideInfo.setHorizontalTextPosition(4);
        this.jchkHideInfo.setMaximumSize(new Dimension(0, 25));
        this.jchkHideInfo.setMinimumSize(new Dimension(0, 0));
        this.jchkHideInfo.setPreferredSize(new Dimension(150, 30));
        this.jLabel18.setFont(new Font("Arial", 0, 14));
        this.jLabel18.setText(bundle.getString("label.startuplogo"));
        this.jLabel18.setMaximumSize(new Dimension(0, 25));
        this.jLabel18.setMinimumSize(new Dimension(0, 0));
        this.jLabel18.setPreferredSize(new Dimension(150, 30));
        this.jtxtStartupLogo.setFont(new Font("Arial", 0, 14));
        this.jtxtStartupLogo.setToolTipText(AppLocal.getIntString("tooltip.config.general.logo"));
        this.jtxtStartupLogo.setMaximumSize(new Dimension(0, 25));
        this.jtxtStartupLogo.setMinimumSize(new Dimension(0, 0));
        this.jtxtStartupLogo.setPreferredSize(new Dimension(400, 30));
        this.jLabel19.setFont(new Font("Arial", 0, 14));
        this.jLabel19.setText(AppLocal.getIntString("label.startuptext"));
        this.jLabel19.setMaximumSize(new Dimension(0, 25));
        this.jLabel19.setMinimumSize(new Dimension(0, 0));
        this.jLabel19.setPreferredSize(new Dimension(150, 30));
        this.jtxtStartupText.setFont(new Font("Arial", 0, 14));
        this.jtxtStartupText.setToolTipText(AppLocal.getIntString("tooltip.config.general.text"));
        this.jtxtStartupText.setMaximumSize(new Dimension(0, 25));
        this.jtxtStartupText.setMinimumSize(new Dimension(0, 0));
        this.jtxtStartupText.setPreferredSize(new Dimension(400, 30));
        this.jtxtStartupText.addFocusListener(new FocusAdapter(){

            @Override
            public void focusGained(FocusEvent evt) {
                JPanelConfigGeneral.this.jtxtStartupTextFocusGained(evt);
            }
        });
        this.jtxtStartupText.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPanelConfigGeneral.this.jtxtStartupTextActionPerformed(evt);
            }
        });
        this.jbtnLogo.setIcon(safeIcon("/com/openbravo/images/fileopen.png"));
        this.jbtnLogo.setText("  ");
        this.jbtnLogo.setToolTipText(AppLocal.getIntString("tooltip.config.general.logo"));
        this.jbtnLogo.setMaximumSize(new Dimension(64, 32));
        this.jbtnLogo.setMinimumSize(new Dimension(64, 32));
        this.jbtnLogo.setPreferredSize(new Dimension(80, 45));
        this.jbtnLogo.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPanelConfigGeneral.this.jbtnLogoActionPerformed(evt);
            }
        });
        this.jbtnText.setIcon(safeIcon("/com/openbravo/images/fileopen.png"));
        this.jbtnText.setText("  ");
        this.jbtnText.setToolTipText(AppLocal.getIntString("tooltip.config.general.text"));
        this.jbtnText.setMaximumSize(new Dimension(64, 32));
        this.jbtnText.setMinimumSize(new Dimension(64, 32));
        this.jbtnText.setPreferredSize(new Dimension(80, 45));
        this.jbtnTextClear.setFont(new Font("Arial", 1, 12));
        this.jbtnTextClear.setForeground(new Color(255, 0, 153));
        this.jbtnTextClear.setText("X");
        this.jbtnTextClear.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPanelConfigGeneral.this.jbtnTextClearActionPerformed(evt);
            }
        });
        this.jLabel6.setFont(new Font("Arial", 0, 14));
        this.jLabel6.setText(AppLocal.getIntString("label.MachineDepartment"));
        this.jLabel6.setPreferredSize(new Dimension(150, 30));
        this.jtxtMachineDepartment.setFont(new Font("Arial", 0, 14));
        this.jtxtMachineDepartment.setToolTipText(AppLocal.getIntString("tooltip.config.general.dept"));
        this.jtxtMachineDepartment.setCursor(new Cursor(0));
        this.jtxtMachineDepartment.setMinimumSize(new Dimension(130, 25));
        this.jtxtMachineDepartment.setPreferredSize(new Dimension(200, 30));
        this.lblIP_Address.setToolTipText(AppLocal.getIntString("tooltip.config.general.compip"));
        this.lblIP_Address.setFont(new Font("Arial", 0, 14));
        this.lblIP_Address.setPreferredSize(new Dimension(200, 30));
        this.webLabel1.setText(bundle.getString("label.nameIP"));
        this.webLabel1.setFont(new Font("Arial", 0, 14));
        this.webLabel1.setPreferredSize(new Dimension(150, 30));
        this.jLblURL.setFont(new Font("Arial", 0, 14));
        this.jLblURL.setIcon(safeIcon("/com/openbravo/images/pay.png"));
        this.jLblURL.setText(AppLocal.getIntString("label.URL"));
        this.jLblURL.setToolTipText(bundle.getString("tooltip.config.general.URL"));
        this.jLblURL.setHorizontalTextPosition(2);
        this.jLblURL.setMaximumSize(new Dimension(0, 25));
        this.jLblURL.setMinimumSize(new Dimension(0, 0));
        this.jLblURL.setPreferredSize(new Dimension(150, 30));
        this.jLblURL.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent evt) {
                JPanelConfigGeneral.this.jLblURLMouseClicked(evt);
            }
        });
        this.jtxtStartupHTML.setFont(new Font("Arial", 0, 14));
        this.jtxtStartupHTML.setToolTipText(AppLocal.getIntString("tooltip.config.general.text"));
        this.jtxtStartupHTML.setMaximumSize(new Dimension(0, 25));
        this.jtxtStartupHTML.setMinimumSize(new Dimension(0, 0));
        this.jtxtStartupHTML.setPreferredSize(new Dimension(400, 30));
        this.jtxtStartupHTML.addFocusListener(new FocusAdapter(){

            @Override
            public void focusGained(FocusEvent evt) {
                JPanelConfigGeneral.this.jtxtStartupHTMLFocusGained(evt);
            }
        });
        this.jtxtStartupHTML.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPanelConfigGeneral.this.jtxtStartupHTMLActionPerformed(evt);
            }
        });
        this.jbtnHTML.setIcon(safeIcon("/com/openbravo/images/fileopen.png"));
        this.jbtnHTML.setText("  ");
        this.jbtnHTML.setToolTipText(AppLocal.getIntString("tooltip.config.general.text"));
        this.jbtnHTML.setMaximumSize(new Dimension(64, 32));
        this.jbtnHTML.setMinimumSize(new Dimension(64, 32));
        this.jbtnHTML.setPreferredSize(new Dimension(80, 45));
        this.jbtnHTML.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPanelConfigGeneral.this.jbtnHTMLActionPerformed(evt);
            }
        });
        this.jbtnClearHTML.setFont(new Font("Arial", 1, 12));
        this.jbtnClearHTML.setForeground(new Color(255, 0, 153));
        this.jbtnClearHTML.setText("X");
        this.jbtnClearHTML.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPanelConfigGeneral.this.jbtnClearHTMLActionPerformed(evt);
            }
        });
        GroupLayout jPanel11Layout = new GroupLayout(this.jPanel11);
        this.jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(jPanel11Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel11Layout.createSequentialGroup().addContainerGap().addGroup(jPanel11Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel11Layout.createSequentialGroup().addGroup(jPanel11Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel11Layout.createSequentialGroup().addGroup(jPanel11Layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addGroup(GroupLayout.Alignment.LEADING, jPanel11Layout.createSequentialGroup().addComponent(this.jLabel1, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jtxtMachineHostname, -2, -1, -2)).addGroup(jPanel11Layout.createSequentialGroup().addComponent(this.jLabel6, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jtxtMachineDepartment, -2, -1, -2))).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.webLabel1, -2, -1, -2)).addGroup(jPanel11Layout.createSequentialGroup().addComponent(this.jLabel2, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jcboLAF, -2, -1, -2)).addGroup(jPanel11Layout.createSequentialGroup().addComponent(this.jLabel3, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jcboMachineScreenmode, -2, -1, -2)).addGroup(jPanel11Layout.createSequentialGroup().addComponent(this.jLabel4, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jcboTicketsBag, -2, -1, -2))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addComponent(this.lblIP_Address, -2, -1, -2)).addGroup(jPanel11Layout.createSequentialGroup().addGroup(jPanel11Layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addGroup(GroupLayout.Alignment.LEADING, jPanel11Layout.createSequentialGroup().addComponent(this.jLabel18, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jtxtStartupLogo, -2, -1, -2)).addGroup(GroupLayout.Alignment.LEADING, jPanel11Layout.createSequentialGroup().addGroup(jPanel11Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel19, -2, -1, -2).addComponent(this.jLblURL, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel11Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jchkHideInfo, -2, 287, -2).addComponent(this.jtxtStartupText, -2, -1, -2).addComponent(this.jtxtStartupHTML, -2, -1, -2)))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel11Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jbtnLogo, -2, -1, -2).addGroup(jPanel11Layout.createSequentialGroup().addComponent(this.jbtnText, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jbtnTextClear, -2, 40, -2)).addGroup(jPanel11Layout.createSequentialGroup().addComponent(this.jbtnHTML, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jbtnClearHTML, -2, 40, -2))).addGap(0, 0, Short.MAX_VALUE))).addGap(136, 136, 136)));
        jPanel11Layout.setVerticalGroup(jPanel11Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel11Layout.createSequentialGroup().addContainerGap().addGroup(jPanel11Layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(jPanel11Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1, -2, -1, -2).addComponent(this.jtxtMachineHostname, -2, -1, -2).addComponent(this.webLabel1, -2, -1, -2)).addComponent(this.lblIP_Address, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel11Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel6, -2, -1, -2).addComponent(this.jtxtMachineDepartment, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel11Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel2, -2, -1, -2).addComponent(this.jcboLAF, -2, -1, -2)).addGap(11, 11, 11).addGroup(jPanel11Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel3, -2, -1, -2).addComponent(this.jcboMachineScreenmode, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel11Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel4, -2, -1, -2).addComponent(this.jcboTicketsBag, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel11Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel18, -2, -1, -2).addComponent(this.jtxtStartupLogo, -2, -1, -2).addComponent(this.jbtnLogo, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel11Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel19, -2, -1, -2).addComponent(this.jtxtStartupText, -2, -1, -2).addComponent(this.jbtnText, -2, -1, -2).addComponent(this.jbtnTextClear)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel11Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLblURL, -2, -1, -2).addComponent(this.jtxtStartupHTML, -2, -1, -2).addComponent(this.jbtnHTML, -2, -1, -2).addComponent(this.jbtnClearHTML)).addGap(18, 18, 18).addComponent(this.jchkHideInfo, -2, -1, -2).addContainerGap(41, Short.MAX_VALUE)));
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.jPanel11, -2, -1, -2).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jPanel11, -2, -1, -2));
        this.getAccessibleContext().setAccessibleName("");
    }

    private void jcboLAFActionPerformed(ActionEvent evt) {
    }

    private void jtxtStartupTextActionPerformed(ActionEvent evt) {
    }

    private void jtxtStartupTextFocusGained(FocusEvent evt) {
    }

    private void jbtnTextClearActionPerformed(ActionEvent evt) {
        this.jtxtStartupText.setText("");
    }

    private void jtxtStartupHTMLFocusGained(FocusEvent evt) {
    }

    private void jtxtStartupHTMLActionPerformed(ActionEvent evt) {
    }

    private void jbtnClearHTMLActionPerformed(ActionEvent evt) {
        this.jtxtStartupHTML.setText("");
    }

    private void jbtnHTMLActionPerformed(ActionEvent evt) {
    }

    private void jbtnLogoActionPerformed(ActionEvent evt) {
    }

    private void jLblURLMouseClicked(MouseEvent evt) {
        JOptionPane.showMessageDialog(this, AppLocal.getIntString("message.URL"), "URL", 1);
    }

    private static class LAFInfo {
        private final String name;
        private final String classname;

        public LAFInfo(String name, String classname) {
            this.name = name;
            this.classname = classname;
        }

        public String getName() {
            return this.name;
        }

        public String getClassName() {
            return this.classname;
        }

        public String toString() {
            return this.name;
        }
    }
}

