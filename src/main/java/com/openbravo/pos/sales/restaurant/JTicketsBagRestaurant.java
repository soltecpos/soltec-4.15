/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bsh.EvalError
 *  bsh.Interpreter
 */
package com.openbravo.pos.sales.restaurant;

import bsh.EvalError;
import bsh.Interpreter;
import com.openbravo.basic.BasicException;
import com.openbravo.data.gui.JMessageDialog;
import com.openbravo.data.gui.MessageInf;
import com.openbravo.format.Formats;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.forms.DataLogicSales;
import com.openbravo.pos.forms.DataLogicSystem;
import com.openbravo.pos.forms.JRootApp;
import com.openbravo.pos.plaf.SOLTECTheme;
import com.openbravo.pos.printer.DeviceTicket;
import com.openbravo.pos.printer.TicketParser;
import com.openbravo.pos.printer.TicketPrinterException;
import com.openbravo.pos.sales.DataLogicReceipts;
import com.openbravo.pos.sales.JPanelTicket;
import com.openbravo.pos.sales.restaurant.JTicketsBagRestaurantMap;
import com.openbravo.pos.sales.restaurant.RestaurantDBUtils;
import com.openbravo.pos.scripting.ScriptEngine;
import com.openbravo.pos.scripting.ScriptException;
import com.openbravo.pos.scripting.ScriptFactory;
import com.openbravo.pos.ticket.TicketInfo;
import com.openbravo.pos.ticket.TicketLineInfo;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class JTicketsBagRestaurant
extends JPanel {
    private final AppView m_App;
    private final JTicketsBagRestaurantMap m_restaurant;
    private List<TicketLineInfo> m_aLines;
    private TicketLineInfo line;
    private TicketInfo ticket;
    private DataLogicSystem m_dlSystem = null;
    private final DeviceTicket m_TP;
    private final TicketParser m_TTP2;
    private final RestaurantDBUtils restDB;
    private DataLogicSales dlSales = null;
    private JButton j_btnKitchen;
    private JButton m_DelTicket;
    private JButton m_MoveTable;
    private JButton m_TablePlan;

    public JTicketsBagRestaurant(AppView app, JTicketsBagRestaurantMap restaurant) {
        this.m_App = app;
        this.m_restaurant = restaurant;
        this.initComponents();
        this.restDB = new RestaurantDBUtils(this.m_App);
        this.m_dlSystem = (DataLogicSystem)this.m_App.getBean("com.openbravo.pos.forms.DataLogicSystem");
        this.dlSales = (DataLogicSales)this.m_App.getBean("com.openbravo.pos.forms.DataLogicSales");
        DataLogicReceipts m_dlReceipts = (DataLogicReceipts)this.m_App.getBean("com.openbravo.pos.sales.DataLogicReceipts");
        this.m_TP = new DeviceTicket();
        this.m_TTP2 = new TicketParser(this.m_App.getDeviceTicket(), this.m_dlSystem);
        this.j_btnKitchen.setVisible(false);
        this.m_TablePlan.setVisible(this.m_App.getAppUserView().getUser().hasPermission("sales.TablePlan"));
    }

    public void activate() {
        this.m_DelTicket.setEnabled(this.m_App.getAppUserView().getUser().hasPermission("com.openbravo.pos.sales.JPanelTicketEdits"));
        this.m_TablePlan.setEnabled(this.m_App.getAppUserView().getUser().hasPermission("sales.TablePlan"));
        this.m_TablePlan.setVisible(true);
    }

    public String getPickupString(TicketInfo pTicket) {
        if (pTicket == null) {
            return "0";
        }
        String tmpPickupId = Integer.toString(pTicket.getPickupId());
        String pickupSize = this.m_App.getProperties().getProperty("till.pickupsize");
        if (pickupSize != null && Integer.parseInt(pickupSize) >= tmpPickupId.length()) {
            while (tmpPickupId.length() < Integer.parseInt(pickupSize)) {
                tmpPickupId = "0" + tmpPickupId;
            }
        }
        return tmpPickupId;
    }

    public void printTicket(String resource) {
        this.printTicket(resource, this.ticket, this.m_restaurant.getTable());
        this.printNotify();
        this.j_btnKitchen.setEnabled(false);
    }

    private void printTicket(String sresourcename, TicketInfo ticket, String table) {
        if (ticket != null) {
            if (ticket.getPickupId() == 0) {
                try {
                    ticket.setPickupId(this.dlSales.getNextPickupIndex());
                }
                catch (BasicException e) {
                    ticket.setPickupId(0);
                }
            }
            try {
                ScriptEngine script = ScriptFactory.getScriptEngine("velocity");
                script.put("ticket", ticket);
                script.put("place", this.m_restaurant.getTableName());
                script.put("pickupid", this.getPickupString(ticket));
                this.m_TTP2.printTicket(script.eval(this.m_dlSystem.getResourceAsXML(sresourcename)).toString());
            }
            catch (TicketPrinterException | ScriptException e) {
                JMessageDialog.showMessage(this, new MessageInf(-67108864, AppLocal.getIntString("message.cannotprint"), e));
            }
        }
    }

    public void printNotify() {
        Logger.getLogger(JTicketsBagRestaurant.class.getName()).log(Level.INFO, "Printed successfully");
    }

    private void initComponents() {
        this.m_TablePlan = new JButton();
        this.m_MoveTable = new JButton();
        this.m_DelTicket = new JButton();
        this.j_btnKitchen = new JButton();
        this.setFont(new Font("Arial", 0, 12));
        // Aumentar dimensiones del panel para caber botones de 90x85
        this.setMinimumSize(new Dimension(300, 95));
        this.setPreferredSize(new Dimension(300, 95));
        this.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        
        // BOTÓN ELIMINAR PEDIDO (CANECA) - MODO RESTAURANTE
        this.m_DelTicket.setFont(new Font("Arial", Font.BOLD, 10));
        this.m_DelTicket.setText("Eliminar");
        this.m_DelTicket.setIcon(SOLTECTheme.getScaledIcon(this.getClass().getResource("/com/openbravo/images/eliminarpedidoactual.png"), 55, 55));
        this.m_DelTicket.setToolTipText("Eliminar Pedido Actual");
        SOLTECTheme.applyIconButtonStyle(this.m_DelTicket);
        this.m_DelTicket.setVerticalTextPosition(JButton.BOTTOM);
        this.m_DelTicket.setHorizontalTextPosition(JButton.CENTER);
        this.m_DelTicket.setPreferredSize(new Dimension(90, 85));
        this.m_DelTicket.setMinimumSize(new Dimension(90, 85));
        this.m_DelTicket.setMaximumSize(new Dimension(90, 85));
        this.m_DelTicket.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JTicketsBagRestaurant.this.m_DelTicketActionPerformed(evt);
            }
        });
        this.add(this.m_DelTicket);
        
        // BOTÓN PLANO DE MESAS (Ajustado a 55px para uniformidad)
        // CAMBIO DE ICONO: botonmesas.png (Solicitado por usuario)
        this.m_TablePlan.setFont(new Font("Arial", Font.BOLD, 10));
        this.m_TablePlan.setText("Mesas");
        this.m_TablePlan.setIcon(SOLTECTheme.getScaledIcon(this.getClass().getResource("/com/openbravo/images/botonmesas.png"), 55, 55));
        this.m_TablePlan.setToolTipText("Volver al Plano de Mesas");
        SOLTECTheme.applyIconButtonStyle(this.m_TablePlan);
        this.m_TablePlan.setVerticalTextPosition(JButton.BOTTOM);
        this.m_TablePlan.setHorizontalTextPosition(JButton.CENTER);
        this.m_TablePlan.setPreferredSize(new Dimension(90, 85));
        this.m_TablePlan.setMinimumSize(new Dimension(90, 85));
        this.m_TablePlan.setMaximumSize(new Dimension(90, 85));
        this.m_TablePlan.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JTicketsBagRestaurant.this.m_TablePlanActionPerformed(evt);
            }
        });
        this.add(this.m_TablePlan);
        
        // BOTÓN MOVER MESA (Ajustado a 55px para uniformidad)
        this.m_MoveTable.setFont(new Font("Arial", Font.BOLD, 10));
        this.m_MoveTable.setText("Mover");
        this.m_MoveTable.setIcon(SOLTECTheme.getScaledIcon(this.getClass().getResource("/com/openbravo/images/movermesa.png"), 55, 55));
        this.m_MoveTable.setToolTipText("Mover Mesa / Combinar");
        SOLTECTheme.applyIconButtonStyle(this.m_MoveTable);
        this.m_MoveTable.setVerticalTextPosition(JButton.BOTTOM);
        this.m_MoveTable.setHorizontalTextPosition(JButton.CENTER);
        this.m_MoveTable.setPreferredSize(new Dimension(90, 85));
        this.m_MoveTable.setMinimumSize(new Dimension(90, 85));
        this.m_MoveTable.setMaximumSize(new Dimension(90, 85));
        this.m_MoveTable.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JTicketsBagRestaurant.this.m_MoveTableActionPerformed(evt);
            }
        });
        this.add(this.m_MoveTable);
    }

    private void m_MoveTableActionPerformed(ActionEvent evt) {
        this.restDB.clearCustomerNameInTableById(this.m_restaurant.getTable());
        this.restDB.clearWaiterNameInTableById(this.m_restaurant.getTable());
        this.restDB.setTableMovedFlag(this.m_restaurant.getTable());
        this.m_restaurant.moveTicket();
    }

    private void m_DelTicketActionPerformed(ActionEvent evt) {
        int res = JOptionPane.showConfirmDialog(this, AppLocal.getIntString("message.wannadelete"), AppLocal.getIntString("title.editor"), 0, 3);
        if (res == 0) {
            try {
                TicketInfo t = this.m_restaurant.getActiveTicket();
                if (t != null) {
                    String user = this.m_App.getAppUserView().getUser().getName();
                    String tableName = this.m_restaurant.getTableName();
                    if (tableName == null) {
                        tableName = "Unknown Table";
                    }
                    for (int i = 0; i < t.getLinesCount(); ++i) {
                        TicketLineInfo line = t.getLine(i);
                        this.m_dlSystem.execAuditEntry(new Object[]{UUID.randomUUID().toString(), new Date(), "DELETE_TICKET", user, line.getProductName(), line.getMultiply(), Formats.CURRENCY.formatValue(line.getPrice()), "Deleted Ticket", "Table Ticket Delete: " + tableName, Integer.toString(t.getTicketId())});
                    }
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
            this.restDB.clearCustomerNameInTableById(this.m_restaurant.getTable());
            this.restDB.clearWaiterNameInTableById(this.m_restaurant.getTable());
            this.restDB.clearTicketIdInTableById(this.m_restaurant.getTable());
            this.m_restaurant.deleteTicket();
        }
    }

    private void m_TablePlanActionPerformed(ActionEvent evt) {
        this.m_restaurant.newTicket();
    }

    private void j_btnKitchenActionPerformed(ActionEvent evt) {
        this.ticket = this.m_restaurant.getActiveTicket();
        String rScript = this.m_dlSystem.getResourceAsText("script.SendOrder");
        Interpreter i = new Interpreter();
        try {
            i.set("ticket", (Object)this.ticket);
            i.set("place", (Object)this.m_restaurant.getTableName());
            i.set("user", (Object)this.m_App.getAppUserView().getUser());
            i.set("sales", (Object)this);
            i.set("pickupid", this.ticket.getPickupId());
            Object object = i.eval(rScript);
        }
        catch (EvalError ex) {
            Logger.getLogger(JPanelTicket.class.getName()).log(Level.SEVERE, null, ex);
        }
        String autoLogoff = this.m_App.getProperties().getProperty("till.autoLogoff");
        String autoLogoffRestaurant = this.m_App.getProperties().getProperty("till.autoLogoffrestaurant");
        if (autoLogoff != null && autoLogoff.equals("true")) {
            if (autoLogoffRestaurant == null) {
                ((JRootApp)this.m_App).closeAppView();
            } else if (autoLogoffRestaurant.equals("true")) {
                this.m_restaurant.newTicket();
            } else {
                ((JRootApp)this.m_App).closeAppView();
            }
        }
    }
}

