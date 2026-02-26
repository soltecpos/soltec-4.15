/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jdesktop.swingx.JXTaskPane
 *  org.jdesktop.swingx.JXTaskPaneContainer
 */
package com.openbravo.pos.forms;

import com.openbravo.basic.BasicException;
import com.openbravo.data.gui.JMessageDialog;
import com.openbravo.data.gui.MessageInf;
import com.openbravo.data.loader.Datas;
import com.openbravo.data.loader.PreparedSentence;
import com.openbravo.data.loader.SerializerReadBasic;
import com.openbravo.data.loader.SerializerWriteBasic;
import com.openbravo.data.loader.SerializerWriteString;
import com.openbravo.pos.customers.CustomerInfo;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.AppUser;
import com.openbravo.pos.forms.AppUserView;
import com.openbravo.pos.forms.BeanFactoryException;
import com.openbravo.pos.forms.DataLogicSystem;
import com.openbravo.pos.forms.JPanelMenu;
import com.openbravo.pos.forms.JPanelNull;
import com.openbravo.pos.forms.JPanelView;
import com.openbravo.pos.forms.JRootApp;
import com.openbravo.pos.forms.MenuDefinition;
import com.openbravo.pos.forms.MenuExecAction;
import com.openbravo.pos.forms.MenuPanelAction;
import com.openbravo.pos.forms.ProcessAction;
import com.openbravo.pos.scripting.ScriptEngine;
import com.openbravo.pos.scripting.ScriptException;
import com.openbravo.pos.scripting.ScriptFactory;
import com.openbravo.pos.util.Hashcypher;
import com.openbravo.pos.util.StringUtils;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.GroupLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.jdesktop.swingx.JXTaskPane;
import org.jdesktop.swingx.JXTaskPaneContainer;

public class JPrincipalApp
extends JPanel
implements AppUserView {
    private static final Logger logger = Logger.getLogger("com.openbravo.pos.forms.JPrincipalApp");
    private final JRootApp m_appview;
    private final AppUser m_appuser;
    private DataLogicSystem m_dlSystem;
    private JLabel m_principalnotificator;
    private JPanelView m_jLastView;
    private Action m_actionfirst;
    private Map<String, JPanelView> m_aPreparedViews;
    private Map<String, JPanelView> m_aCreatedViews;
    private Icon menu_open;
    private Icon menu_close;
    private CustomerInfo customerInfo;
    private JButton jButton1;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JPanel m_jPanelContainer;
    private JScrollPane m_jPanelLeft;
    private JPanel m_jPanelRight;
    private JPanel m_jPanelTitle;
    private JLabel m_jTitle;

    private ImageIcon safeIcon(String path) {
        java.net.URL imgUrl = this.getClass().getResource(path);
        if (imgUrl != null) {
            return new ImageIcon(imgUrl);
        } else {
            System.err.println("WARNING: Image resource not found: " + path);
            return null;
        }
    }

    public JPrincipalApp(JRootApp appview, AppUser appuser) {
        this.m_appview = appview;
        this.m_appuser = appuser;
        this.m_dlSystem = (DataLogicSystem)this.m_appview.getBean("com.openbravo.pos.forms.DataLogicSystem");
        this.m_appuser.fillPermissions(this.m_dlSystem);
        this.autoUpdateMenu();
        this.m_actionfirst = null;
        this.m_jLastView = null;
        this.m_aPreparedViews = new HashMap<String, JPanelView>();
        this.m_aCreatedViews = new HashMap<String, JPanelView>();
        this.initComponents();
        this.jPanel2.add(Box.createVerticalStrut(50), 0);
        this.m_jPanelLeft.getVerticalScrollBar().setPreferredSize(new Dimension(35, 35));
        this.applyComponentOrientation(appview.getComponentOrientation());
        this.m_principalnotificator = new JLabel();
        this.m_principalnotificator.applyComponentOrientation(this.getComponentOrientation());
        this.m_principalnotificator.setText(this.m_appuser.getName());
        this.m_principalnotificator.setIcon(this.m_appuser.getIcon());
        if (this.jButton1.getComponentOrientation().isLeftToRight()) {
            this.menu_open = safeIcon("/com/openbravo/images/menu-right.png");
            this.menu_close = safeIcon("/com/openbravo/images/menu-left.png");
        } else {
            this.menu_open = safeIcon("/com/openbravo/images/menu-left.png");
            this.menu_close = safeIcon("/com/openbravo/images/menu-right.png");
        }
        this.assignMenuButtonIcon();
        this.m_jPanelTitle.setVisible(false);
        this.m_jPanelContainer.add((Component)new JPanel(), "<NULL>");
        this.showView("<NULL>");
        try {
            String menuText = null;
            try {
                menuText = this.m_dlSystem.getResourceAsText("Menu.Root");
            } catch (Exception e) {
            }

            if (menuText == null) {
                 try {
                     menuText = StringUtils.readResource("/com/openbravo/pos/templates/Menu.Root.txt");
                 } catch (Exception ex) {
                     logger.log(Level.SEVERE, "Cannot read default menu", ex);
                 }
            }

            if (menuText != null) {
                 // Parche para cambiar el icono de Impresoras a yast_printer.png
                 if (menuText.contains("printer.png")) {
                     menuText = menuText.replace("printer.png", "yast_printer.png");
                 }
                 // Additional safe replacement
                 menuText = menuText.replaceAll("(\"[^\"]+\")(\\s*,\\s*\"printer\"\\s*,\\s*AppLocal\\.getIntString\\(\"Menu\\.Printer\"\\))", 
                               "\"/com/openbravo/images/yast_printer.png\"$2");
            }

            this.m_jPanelLeft.setViewportView(this.getScriptMenu(menuText));
        }
        catch (ScriptException e) {
            logger.log(Level.SEVERE, "Cannot read Menu.Root resource.", e);
        }
    }

    private Component getScriptMenu(String menutext) throws ScriptException {
        ScriptMenu menu = new ScriptMenu();
        ScriptEngine eng = ScriptFactory.getScriptEngine("beanshell");
        eng.put("menu", menu);
        eng.eval(menutext);
        return menu.getTaskPane();
    }

    private void assignMenuButtonIcon() {
        this.jButton1.setIcon(this.m_jPanelLeft.isVisible() ? this.menu_close : this.menu_open);
    }

    private void setMenuVisible(boolean value) {
        this.m_jPanelLeft.setVisible(value);
        this.assignMenuButtonIcon();
        this.revalidate();
    }

    public JComponent getNotificator() {
        return this.m_principalnotificator;
    }

    public void activate() {
        this.setMenuVisible(this.getBounds().width > 800);
        if (this.m_actionfirst != null) {
            this.m_actionfirst.actionPerformed(null);
            this.m_actionfirst = null;
        }
    }

    public boolean deactivate() {
        if (this.m_jLastView == null) {
            return true;
        }
        if (this.m_jLastView.deactivate()) {
            this.m_jLastView = null;
            this.showView("<NULL>");
            return true;
        }
        return false;
    }

    public void exitToLogin() {
        this.m_appview.closeAppView();
    }

    private void showView(String sView) {
        CardLayout cl = (CardLayout)this.m_jPanelContainer.getLayout();
        cl.show(this.m_jPanelContainer, sView);
    }

    @Override
    public AppUser getUser() {
        return this.m_appuser;
    }

    @Override
    public void showTask(String sTaskClass) {
        this.customerInfo = new CustomerInfo("");
        this.customerInfo.setName("");
        this.m_appview.waitCursorBegin();
        if (this.m_appuser.hasPermission(sTaskClass)) {
            JPanelView m_jMyView = this.m_aCreatedViews.get(sTaskClass);
            if (this.m_jLastView == null || m_jMyView != this.m_jLastView && this.m_jLastView.deactivate()) {
                if (m_jMyView == null) {
                    m_jMyView = this.m_aPreparedViews.get(sTaskClass);
                    if (m_jMyView == null) {
                        try {
                            m_jMyView = (JPanelView)this.m_appview.getBean(sTaskClass);
                        }
                        catch (BeanFactoryException e) {
                            m_jMyView = new JPanelNull(this.m_appview, e);
                        }
                    }
                    m_jMyView.getComponent().applyComponentOrientation(this.getComponentOrientation());
                    this.m_jPanelContainer.add((Component)m_jMyView.getComponent(), sTaskClass);
                    this.m_aCreatedViews.put(sTaskClass, m_jMyView);
                }
                try {
                    m_jMyView.activate();
                }
                catch (BasicException e) {
                    JMessageDialog.showMessage(this, new MessageInf(-33554432, AppLocal.getIntString("message.notactive"), e));
                }
                this.m_jLastView = m_jMyView;
                this.setMenuVisible(this.getBounds().width > 800);
                this.setMenuVisible(false);
                this.showView(sTaskClass);
                String sTitle = m_jMyView.getTitle();
                this.m_jPanelTitle.setVisible(sTitle != null);
                this.m_jTitle.setText(sTitle);
            }
        } else {
            JMessageDialog.showMessage(this, new MessageInf(-33554432, AppLocal.getIntString("message.notpermissions")));
        }
        this.m_appview.waitCursorEnd();
    }

    @Override
    public void executeTask(String sTaskClass) {
        this.m_appview.waitCursorBegin();
        if (this.m_appuser.hasPermission(sTaskClass)) {
            try {
                ProcessAction myProcess = (ProcessAction)this.m_appview.getBean(sTaskClass);
                try {
                    MessageInf m = myProcess.execute();
                    if (m != null) {
                        JMessageDialog.showMessage(this, m);
                    }
                }
                catch (BasicException eb) {
                    JMessageDialog.showMessage(this, new MessageInf(eb));
                }
            }
            catch (BeanFactoryException e) {
                e.printStackTrace();
                JMessageDialog.showMessage(this, new MessageInf(-33554432, AppLocal.getIntString("label.LoadError"), e));
            }
        } else {
            JMessageDialog.showMessage(this, new MessageInf(-33554432, AppLocal.getIntString("message.notpermissions")));
        }
        this.m_appview.waitCursorEnd();
    }

    private void initComponents() {
        this.jPanel1 = new JPanel();
        this.m_jPanelLeft = new JScrollPane();
        this.jPanel2 = new JPanel();
        this.jButton1 = new JButton();
        this.m_jPanelRight = new JPanel();
        this.m_jPanelTitle = new JPanel();
        this.m_jTitle = new JLabel();
        this.m_jPanelContainer = new JPanel();
        this.setLayout(new BorderLayout());
        this.jPanel1.setLayout(new BorderLayout());
        this.m_jPanelLeft.setBackground(new Color(102, 102, 102));
        this.m_jPanelLeft.setBorder(null);
        this.m_jPanelLeft.setFont(new Font("Arial", 0, 14));
        this.m_jPanelLeft.setPreferredSize(new Dimension(250, 2));
        this.jPanel1.add((Component)this.m_jPanelLeft, "Before");
        this.jPanel2.setFont(new Font("Arial", 0, 12));
        this.jPanel2.setPreferredSize(new Dimension(45, 45));
        this.jButton1.setToolTipText(AppLocal.getIntString("tooltip.menu"));
        this.jButton1.setFocusPainted(false);
        this.jButton1.setFocusable(false);
        this.jButton1.setIconTextGap(0);
        this.jButton1.setMargin(new Insets(10, 2, 10, 2));
        this.jButton1.setMaximumSize(new Dimension(45, 45));
        this.jButton1.setMinimumSize(new Dimension(32, 32));
        this.jButton1.setPreferredSize(new Dimension(36, 45));
        this.jButton1.setRequestFocusEnabled(false);
        this.jButton1.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPrincipalApp.this.jButton1ActionPerformed(evt);
            }
        });
        GroupLayout jPanel2Layout = new GroupLayout(this.jPanel2);
        this.jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup().addGap(0, 0, 0).addComponent(this.jButton1, -2, -1, -2).addContainerGap()));
        jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel2Layout.createSequentialGroup().addContainerGap(88, Short.MAX_VALUE).addComponent(this.jButton1, -1, 33, Short.MAX_VALUE).addContainerGap(188, Short.MAX_VALUE)));
        this.jPanel1.add((Component)this.jPanel2, "After");
        this.add((Component)this.jPanel1, "Before");
        this.m_jPanelRight.setPreferredSize(new Dimension(200, 40));
        this.m_jPanelRight.setLayout(new BorderLayout());
        this.m_jPanelTitle.setLayout(new BorderLayout());
        this.m_jTitle.setFont(new Font("Arial", 1, 14));
        this.m_jTitle.setForeground(new Color(0, 168, 223));
        this.m_jTitle.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.darkGray), BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        this.m_jTitle.setMaximumSize(new Dimension(100, 35));
        this.m_jTitle.setMinimumSize(new Dimension(30, 25));
        this.m_jTitle.setPreferredSize(new Dimension(100, 35));
        this.m_jPanelTitle.add((Component)this.m_jTitle, "North");
        this.m_jPanelRight.add((Component)this.m_jPanelTitle, "North");
        this.m_jPanelContainer.setFont(new Font("Arial", 0, 14));
        this.m_jPanelContainer.setLayout(new CardLayout());
        this.m_jPanelRight.add((Component)this.m_jPanelContainer, "Center");
        this.m_jPanelRight.add((Component)this.m_jPanelContainer, "Center");
        this.add((Component)this.m_jPanelRight, "Center");
    }

    private void jButton1ActionPerformed(ActionEvent evt) {
        this.setMenuVisible(!this.m_jPanelLeft.isVisible());
    }

    private void autoUpdateMenu() {
        try {
            String content = this.m_dlSystem.getResourceAsText("Menu.Root");
            if (content != null && !content.contains("Menu.DBMaintenance")) {
                String search = "submenu.addPanel(\"/com/openbravo/images/card.png\", \"Menu.Vouchers\", \"com.openbravo.pos.voucher.VoucherPanel\");";
                String replace = search + "\n        submenu.addPanel(\"/com/openbravo/images/bookmark.png\", \"Menu.DBMaintenance\", \"com.openbravo.pos.maintenance.JPanelDBMaintenance\");";
                if (!content.contains(search)) {
                    search = "submenu.addPanel(\"/com/openbravo/images/tables.png\", \"Menu.Tables\", \"com.openbravo.pos.mant.JPanelPlaces\");";
                    replace = search + "\n        submenu.addPanel(\"/com/openbravo/images/bookmark.png\", \"Menu.DBMaintenance\", \"com.openbravo.pos.maintenance.JPanelDBMaintenance\");";
                }
                if (content.contains(search)) {
                    String newContent = content.replace(search, replace);
                    new PreparedSentence(this.m_appview.getSession(), "UPDATE RESOURCES SET CONTENT = ? WHERE NAME = ?", new SerializerWriteBasic(Datas.STRING, Datas.STRING)).exec(new Object[]{newContent, "Menu.Root"});
                    content = newContent;
                }
            }
            if (content != null && !content.contains("Menu.PaymentSummary")) {
                String regex2;
                String regex = "submenu\\.addPanel\\s*\\(.*\"Menu\\.PaymentReport\".*\\);";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(content);
                if (matcher.find()) {
                    String foundMatch = matcher.group();
                    String replacement = foundMatch + "\n        submenu.addPanel(\"/com/openbravo/images/reports.png\", \"Menu.PaymentSummary\", \"/com/openbravo/reports/sales_paymentsummary.bs\");";
                    String newContent = content.replace(foundMatch, replacement);
                    new PreparedSentence(this.m_appview.getSession(), "UPDATE RESOURCES SET CONTENT = ? WHERE NAME = ?", new SerializerWriteBasic(Datas.STRING, Datas.STRING)).exec(new Object[]{newContent, "Menu.Root"});
                }
            }
            
            // Auto-add "Gastos de Gerencia"
            if (content != null && !content.contains("com.openbravo.pos.panels.JPanelGlobalExpenses")) {
                Pattern p = Pattern.compile("(submenu|group)\\.addPanel\\s*\\([^{};]*\"com\\.openbravo\\.pos\\.panels\\.JPanelCloseMoney\"\\s*\\);");
                Matcher m = p.matcher(content);
                if (m.find()) {
                    String found = m.group();
                    String prefix = m.group(1);
                    String replace = found + "\n        " + prefix + ".addPanel(\"\", \"Gastos de Gerencia\", \"com.openbravo.pos.panels.JPanelGlobalExpenses\");";
                    content = content.replace(found, replace);
                    new PreparedSentence(this.m_appview.getSession(), "UPDATE RESOURCES SET CONTENT = ? WHERE NAME = ?", new SerializerWriteBasic(Datas.STRING, Datas.STRING)).exec(new Object[]{content, "Menu.Root"});
                }
            }

            // Auto-add "Ingreso de Mercancia"
            if (content != null && !content.contains("com.openbravo.pos.inventory.JPanelMerchandiseEntry")) {
                Pattern p = Pattern.compile("(submenu|group)\\.addPanel\\s*\\([^{};]*\"com\\.openbravo\\.pos\\.panels\\.JPanelCloseMoney\"\\s*\\);");
                Matcher m = p.matcher(content);
                if (m.find()) {
                    String found = m.group();
                    String prefix = m.group(1);
                    String replace = found + "\n        " + prefix + ".addPanel(\"\", \"Ingreso de Mercancia\", \"com.openbravo.pos.inventory.JPanelMerchandiseEntry\");";
                    replace += "\n        " + prefix + ".addPanel(\"\", \"Aprobacion de Ingresos\", \"com.openbravo.pos.inventory.JPanelMerchandiseAdmin\");";
                    content = content.replace(found, replace);
                    new PreparedSentence(this.m_appview.getSession(), "UPDATE RESOURCES SET CONTENT = ? WHERE NAME = ?", new SerializerWriteBasic(Datas.STRING, Datas.STRING)).exec(new Object[]{content, "Menu.Root"});
                }
            }
            this.autoUpdatePermissions();
        }
        catch (BasicException e) {
            e.printStackTrace();
        }
    }

    private void autoUpdatePermissions() throws BasicException {
        String permissionKey;
        String permissions;
        String roleId = this.m_appuser.getRole();
        Object[] val = (Object[])new PreparedSentence<String, Object[]>(this.m_appview.getSession(), "SELECT PERMISSIONS FROM ROLES WHERE ID = ?", SerializerWriteString.INSTANCE, new SerializerReadBasic(new Datas[]{Datas.BYTES})).find((Object)roleId);
        if (val != null && val[0] != null && (permissions = new String((byte[])val[0])).endsWith("</permissions>")) {
            boolean changed = false;
            String newPermissions = permissions;
            
            // Permiso para reporte de pagos
            if (!newPermissions.contains("/com/openbravo/reports/sales_paymentsummary.bs")) {
                newPermissions = newPermissions.substring(0, newPermissions.length() - 14) + "\n    <class name=\"/com/openbravo/reports/sales_paymentsummary.bs\"/>\n</permissions>";
                changed = true;
            }
            
            // Permiso para Gastos de Gerencia
            if (!newPermissions.contains("com.openbravo.pos.panels.JPanelGlobalExpenses")) {
                newPermissions = newPermissions.substring(0, newPermissions.length() - 14) + "\n    <class name=\"com.openbravo.pos.panels.JPanelGlobalExpenses\"/>\n</permissions>";
                changed = true;
            }
            
            // Permiso para Ingreso de Mercancía
            if (!newPermissions.contains("com.openbravo.pos.inventory.JPanelMerchandiseEntry")) {
                newPermissions = newPermissions.substring(0, newPermissions.length() - 14) + "\n    <class name=\"com.openbravo.pos.inventory.JPanelMerchandiseEntry\"/>\n</permissions>";
                changed = true;
            }
            
            // Permiso para Aprobación de Ingresos
            if (!newPermissions.contains("com.openbravo.pos.inventory.JPanelMerchandiseAdmin")) {
                newPermissions = newPermissions.substring(0, newPermissions.length() - 14) + "\n    <class name=\"com.openbravo.pos.inventory.JPanelMerchandiseAdmin\"/>\n</permissions>";
                changed = true;
            }
            
            if (changed) {
                new PreparedSentence(this.m_appview.getSession(), "UPDATE ROLES SET PERMISSIONS = ? WHERE ID = ?", new SerializerWriteBasic(Datas.BYTES, Datas.STRING)).exec(new Object[]{newPermissions.getBytes(), roleId});
                this.m_appuser.fillPermissions(this.m_dlSystem);
            }
        }
    }

    public class ScriptMenu {
        private final JXTaskPaneContainer taskPane = new JXTaskPaneContainer();

        private ScriptMenu() {
            this.taskPane.applyComponentOrientation(JPrincipalApp.this.getComponentOrientation());
        }

        public ScriptGroup addGroup(String key) {
            ScriptGroup group = new ScriptGroup(key);
            this.taskPane.add((Component)group.getTaskGroup());
            return group;
        }

        public JXTaskPaneContainer getTaskPane() {
            return this.taskPane;
        }
    }

    private class ChangePasswordAction
    extends AbstractAction {
        public ChangePasswordAction(String icon, String keytext) {
            this.putValue("SmallIcon", JPrincipalApp.this.safeIcon(icon));
            this.putValue("Name", AppLocal.getIntString(keytext));
            this.putValue("taskname", keytext);
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            String sNewPassword = Hashcypher.changePassword(JPrincipalApp.this, JPrincipalApp.this.m_appuser.getPassword());
            if (sNewPassword != null) {
                try {
                    JPrincipalApp.this.m_dlSystem.execChangePassword(new Object[]{sNewPassword, JPrincipalApp.this.m_appuser.getId()});
                    JPrincipalApp.this.m_appuser.setPassword(sNewPassword);
                }
                catch (BasicException e) {
                    JMessageDialog.showMessage(JPrincipalApp.this, new MessageInf(-33554432, AppLocal.getIntString("message.cannotchangepassword")));
                }
            }
        }
    }

    private class ExitAction
    extends AbstractAction {
        public ExitAction(String icon, String keytext) {
            this.putValue("SmallIcon", JPrincipalApp.this.safeIcon(icon));
            this.putValue("Name", AppLocal.getIntString(keytext));
            this.putValue("taskname", keytext);
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            JPrincipalApp.this.m_appview.closeAppView();
        }
    }



    public class ScriptSubmenu {
        private final MenuDefinition menudef;

        private ScriptSubmenu(String key) {
            this.menudef = new MenuDefinition(key);
        }

        public void addTitle(String key) {
            this.menudef.addMenuTitle(key);
        }

        public void addPanel(String icon, String key, String classname) {
            this.menudef.addMenuItem(new MenuPanelAction(JPrincipalApp.this.m_appview, icon, key, classname));
        }

        public void addExecution(String icon, String key, String classname) {
            this.menudef.addMenuItem(new MenuExecAction(JPrincipalApp.this.m_appview, icon, key, classname));
        }

        public ScriptSubmenu addSubmenu(String icon, String key, String classname) {
            ScriptSubmenu submenu = new ScriptSubmenu(key);
            JPrincipalApp.this.m_aPreparedViews.put(classname, new JPanelMenu(submenu.getMenuDefinition()));
            this.menudef.addMenuItem(new MenuPanelAction(JPrincipalApp.this.m_appview, icon, key, classname));
            return submenu;
        }

        public void addChangePasswordAction() {
            this.menudef.addMenuItem(new ChangePasswordAction("/com/openbravo/images/password.png", "Menu.ChangePassword"));
        }

        public void addExitAction() {
            this.menudef.addMenuItem(new ExitAction("/com/openbravo/images/logout.png", "Menu.Exit"));
        }

        public MenuDefinition getMenuDefinition() {
            return this.menudef;
        }
    }

    public class ScriptGroup {
        private final JXTaskPane taskGroup = new JXTaskPane();

        private ScriptGroup(String key) {
            this.taskGroup.applyComponentOrientation(JPrincipalApp.this.getComponentOrientation());
            this.taskGroup.setFocusable(false);
            this.taskGroup.setRequestFocusEnabled(false);
            this.taskGroup.setTitle(AppLocal.getIntString(key));
            this.taskGroup.setVisible(false);
        }

        public void addPanel(String icon, String key, String classname) {
            this.addAction(new MenuPanelAction(JPrincipalApp.this.m_appview, icon, key, classname));
        }

        public void addExecution(String icon, String key, String classname) {
            this.addAction(new MenuExecAction(JPrincipalApp.this.m_appview, icon, key, classname));
        }

        public ScriptSubmenu addSubmenu(String icon, String key, String classname) {
            ScriptSubmenu submenu = new ScriptSubmenu(key);
            JPrincipalApp.this.m_aPreparedViews.put(classname, new JPanelMenu(submenu.getMenuDefinition()));
            this.addAction(new MenuPanelAction(JPrincipalApp.this.m_appview, icon, key, classname));
            return submenu;
        }

        public void addChangePasswordAction() {
            this.addAction(new ChangePasswordAction("/com/openbravo/images/password.png", "Menu.ChangePassword"));
        }

        public void addExitAction() {
            this.addAction(new ExitAction("/com/openbravo/images/logout.png", "Menu.Exit"));
        }

        private void addAction(Action act) {
            if (JPrincipalApp.this.m_appuser.hasPermission((String)act.getValue("taskname"))) {
                Component c = this.taskGroup.add(act);
                c.applyComponentOrientation(JPrincipalApp.this.getComponentOrientation());
                c.setFocusable(false);
                this.taskGroup.setVisible(true);
                if (JPrincipalApp.this.m_actionfirst == null) {
                    JPrincipalApp.this.m_actionfirst = act;
                }
            }
        }

        public JXTaskPane getTaskGroup() {
            return this.taskGroup;
        }
    }
}

