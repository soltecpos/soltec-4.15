/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.customers;

import com.openbravo.basic.BasicException;
import com.openbravo.data.gui.MessageInf;
import com.openbravo.pos.catalog.JCatalogTab;
import com.openbravo.pos.customers.CustomerInfoExt;
import com.openbravo.pos.customers.DataLogicCustomers;
import com.openbravo.pos.customers.TicketSelector;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.sales.DataLogicReceipts;
import com.openbravo.pos.sales.SharedTicketInfo;
import com.openbravo.pos.sales.TicketsEditor;
import com.openbravo.pos.ticket.TicketInfo;
import com.openbravo.pos.util.ThumbNailBuilder;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Comparator;
import java.util.EventListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.EventListenerList;

public class OrderCustomerList
extends JPanel
implements TicketSelector {
    protected AppView application;
    private String currentTicket;
    protected TicketsEditor panelticket;
    protected EventListenerList listeners = new EventListenerList();
    private final DataLogicCustomers dataLogicCustomers;
    private final DataLogicReceipts dataLogicReceipts;
    private final ThumbNailBuilder tnbbutton;
    protected static final Logger LOGGER = Logger.getLogger("com.openbravo.pos.customers.CustomersList");
    private JPanel jPanelCustomers;

    public OrderCustomerList(DataLogicCustomers dlCustomers, AppView app, TicketsEditor panelticket) {
        this.application = app;
        this.panelticket = panelticket;
        this.dataLogicCustomers = dlCustomers;
        this.dataLogicReceipts = (DataLogicReceipts)this.application.getBean("com.openbravo.pos.sales.DataLogicReceipts");
        this.tnbbutton = new ThumbNailBuilder(90, 98);
        this.initComponents();
    }

    @Override
    public Component getComponent() {
        return this;
    }

    public void reloadCustomers() throws BasicException {
        this.loadCustomers();
    }

    @Override
    public void loadCustomers() throws BasicException {
        SwingUtilities.invokeLater(new Runnable(){

            @Override
            public void run() {
                long time = System.currentTimeMillis();
                OrderCustomerList.this.jPanelCustomers.removeAll();
                JCatalogTab flowTab = new JCatalogTab();
                OrderCustomerList.this.jPanelCustomers.add(flowTab);
                List customers = null;
                List<SharedTicketInfo> ticketList = null;
                try {
                    LOGGER.log(Level.INFO, "Time of getCustomersWithOutImage {0}", System.currentTimeMillis() - time);
                    time = System.currentTimeMillis();
                    ticketList = OrderCustomerList.this.dataLogicReceipts.getSharedTicketList();
                    LOGGER.log(Level.INFO, "Time of getSharedTicketList {0}", System.currentTimeMillis() - time);
                    time = System.currentTimeMillis();
                }
                catch (BasicException ex) {
                    Logger.getLogger(OrderCustomerList.class.getName()).log(Level.SEVERE, null, ex);
                }
                HashMap<SharedTicketInfo, CustomerInfoExt> orderMap = new HashMap<SharedTicketInfo, CustomerInfoExt>();
                block4: for (SharedTicketInfo sharedTicketInfo : ticketList) {
                    String ticketName = sharedTicketInfo.getName().trim();
                    if (!ticketName.contains("[") || !ticketName.contains("]")) continue;
                    if (ticketName.startsWith("[")) {
                        orderMap.put(sharedTicketInfo, null);
                        continue;
                    }
                    if (customers.isEmpty()) continue;
                    for (Object obj : customers) {
                        CustomerInfoExt customer = (CustomerInfoExt) obj;
                        String name;
                        if (customer == null || !ticketName.startsWith(name = customer.getName().trim())) continue;
                        orderMap.put(sharedTicketInfo, customer);
                        continue block4;
                    }
                }
                CustomerComparator bvc = new CustomerComparator(orderMap);
                TreeMap<SharedTicketInfo, CustomerInfoExt> sortedMap = new TreeMap<SharedTicketInfo, CustomerInfoExt>(bvc);
                sortedMap.putAll(orderMap);
                LOGGER.log(Level.INFO, "Time of orderMap {0}", System.currentTimeMillis() - time);
                time = System.currentTimeMillis();
                for (Map.Entry entry : sortedMap.entrySet()) {
                    String username;
                    SharedTicketInfo ticket = (SharedTicketInfo) entry.getKey();
                    CustomerInfoExt customer = (CustomerInfoExt)entry.getValue();
                    String name = ticket.getName();
                    BufferedImage image = null;
                    if (image == null) {
                        try {
                            InputStream is = this.getClass().getResourceAsStream("/com/openbravo/images/no_image.png");
                            if (is != null) {
                                image = ImageIO.read(is);
                            }
                        }
                        catch (IOException ex) {
                            Logger.getLogger(OrderCustomerList.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    if (name.indexOf("[") != 0) {
                        username = name.substring(0, name.indexOf("[") - 1);
                        username = username.replace("-", "");
                    } else {
                        username = "unknown";
                    }
                    String orderId = name.substring(name.indexOf("["), name.indexOf("]") + 1);
                    String text = "<html><center>" + username.trim() + "<br/>" + orderId.trim() + "</center></html>";
                    ImageIcon imageIcon = new ImageIcon(OrderCustomerList.this.tnbbutton.getThumbNailText(image, text));
                }
                LOGGER.log(Level.INFO, "Time of finished loadCustomerOrders {0}", System.currentTimeMillis() - time);
            }
        });
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void setComponentEnabled(boolean value) {
        this.jPanelCustomers.setEnabled(value);
        Object object = this.jPanelCustomers.getTreeLock();
        synchronized (object) {
            int compCount = this.jPanelCustomers.getComponentCount();
            for (int i = 0; i < compCount; ++i) {
                this.jPanelCustomers.getComponent(i).setEnabled(value);
            }
        }
        this.setEnabled(value);
    }

    @Override
    public void addActionListener(ActionListener l) {
        this.listeners.add(ActionListener.class, l);
    }

    @Override
    public void removeActionListener(ActionListener l) {
        this.listeners.remove(ActionListener.class, l);
    }

    private void setActiveTicket(String id) throws BasicException {
        this.currentTicket = this.panelticket.getActiveTicket().getId();
        TicketInfo ticket = this.dataLogicReceipts.getSharedTicket(id);
        if (ticket == null) {
            throw new BasicException(AppLocal.getIntString("message.noticket"));
        }
        this.dataLogicReceipts.deleteSharedTicket(id);
        this.currentTicket = id;
        this.panelticket.setActiveTicket(ticket, null);
        this.fireTicketSelectionChanged(ticket.getId());
    }

    private void fireTicketSelectionChanged(String ticketId) {
        EventListener[] l = this.listeners.getListeners(ActionListener.class);
        ActionEvent e = null;
        for (EventListener l1 : l) {
            if (e == null) {
                e = new ActionEvent(this, 1001, ticketId);
            }
            ((ActionListener)l1).actionPerformed(e);
        }
    }

    private void initComponents() {
        this.jPanelCustomers = new JPanel();
        this.setMinimumSize(new Dimension(256, 560));
        this.setPreferredSize(new Dimension(256, 560));
        this.setLayout(new BorderLayout());
        this.jPanelCustomers.setFont(new Font("Arial", 0, 14));
        this.jPanelCustomers.setLayout(new CardLayout());
        this.add((Component)this.jPanelCustomers, "Center");
    }

    class CustomerComparator
    implements Comparator<SharedTicketInfo> {
        Map<SharedTicketInfo, CustomerInfoExt> base;

        public CustomerComparator(Map<SharedTicketInfo, CustomerInfoExt> base) {
            this.base = base;
        }

        @Override
        public int compare(SharedTicketInfo a, SharedTicketInfo b) {
            String nameB;
            String nameA = this.base.get(a).getName();
            if (nameA.compareToIgnoreCase(nameB = this.base.get(b).getName()) < 0) {
                return -1;
            }
            return 1;
        }
    }

    private class SelectedCustomerAction
    implements ActionListener {
        private final String ticketId;

        public SelectedCustomerAction(String ticketId) {
            this.ticketId = ticketId;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (this.ticketId != null) {
                    OrderCustomerList.this.setActiveTicket(this.ticketId);
                }
            }
            catch (BasicException ex) {
                new MessageInf(ex).show(OrderCustomerList.this);
            }
        }
    }
}

