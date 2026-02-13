/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.suppliers;

import com.openbravo.basic.BasicException;
import com.openbravo.data.gui.MessageInf;
import com.openbravo.pos.catalog.JCatalogTab;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.sales.DataLogicReceipts;
import com.openbravo.pos.sales.SharedTicketInfo;
import com.openbravo.pos.sales.TicketsEditor;
import com.openbravo.pos.suppliers.DataLogicSuppliers;
import com.openbravo.pos.suppliers.SupplierInfoExt;
import com.openbravo.pos.suppliers.SupplierTicketSelector;
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

public class OrderSupplierList
extends JPanel
implements SupplierTicketSelector {
    protected AppView application;
    private String currentTicket;
    protected TicketsEditor panelticket;
    protected EventListenerList listeners = new EventListenerList();
    private final DataLogicSuppliers dataLogicSuppliers;
    private final DataLogicReceipts dataLogicReceipts;
    private final ThumbNailBuilder tnbbutton;
    protected static final Logger LOGGER = Logger.getLogger("com.openbravo.pos.suppliers.SuppliersList");
    private JPanel jPanelSuppliers;

    public OrderSupplierList(DataLogicSuppliers dlSuppliers, AppView app, TicketsEditor panelticket) {
        this.application = app;
        this.panelticket = panelticket;
        this.dataLogicSuppliers = dlSuppliers;
        this.dataLogicReceipts = (DataLogicReceipts)this.application.getBean("com.openbravo.pos.sales.DataLogicReceipts");
        this.tnbbutton = new ThumbNailBuilder(90, 98);
        this.initComponents();
    }

    @Override
    public Component getComponent() {
        return this;
    }

    public void reloadSuppliers() throws BasicException {
        this.loadSuppliers();
    }

    public void loadSuppliers() throws BasicException {
        SwingUtilities.invokeLater(new Runnable(){

            @Override
            public void run() {
                long time = System.currentTimeMillis();
                OrderSupplierList.this.jPanelSuppliers.removeAll();
                JCatalogTab flowTab = new JCatalogTab();
                OrderSupplierList.this.jPanelSuppliers.add(flowTab);
                List suppliers = null;
                List<SharedTicketInfo> ticketList = null;
                try {
                    LOGGER.log(Level.INFO, "Time of getSuppliersWithOutImage {0}", System.currentTimeMillis() - time);
                    time = System.currentTimeMillis();
                    ticketList = OrderSupplierList.this.dataLogicReceipts.getSharedTicketList();
                    LOGGER.log(Level.INFO, "Time of getSharedTicketList {0}", System.currentTimeMillis() - time);
                    time = System.currentTimeMillis();
                }
                catch (BasicException ex) {
                    Logger.getLogger(OrderSupplierList.class.getName()).log(Level.SEVERE, null, ex);
                }
                HashMap<SharedTicketInfo, SupplierInfoExt> orderMap = new HashMap<SharedTicketInfo, SupplierInfoExt>();
                block4: for (SharedTicketInfo sharedTicketInfo : ticketList) {
                    String ticketName = sharedTicketInfo.getName().trim();
                    if (!ticketName.contains("[") || !ticketName.contains("]")) continue;
                    if (ticketName.startsWith("[")) {
                        orderMap.put(sharedTicketInfo, null);
                        continue;
                    }
                    if (suppliers.isEmpty()) continue;
                    for (Object obj : suppliers) {
                        SupplierInfoExt supplier = (SupplierInfoExt) obj;
                        String name;
                        if (supplier == null || !ticketName.startsWith(name = supplier.getName().trim())) continue;
                        orderMap.put(sharedTicketInfo, supplier);
                        continue block4;
                    }
                }
                SupplierComparator bvc = new SupplierComparator(orderMap);
                TreeMap<SharedTicketInfo, SupplierInfoExt> sortedMap = new TreeMap<SharedTicketInfo, SupplierInfoExt>(bvc);
                sortedMap.putAll(orderMap);
                LOGGER.log(Level.INFO, "Time of orderMap {0}", System.currentTimeMillis() - time);
                time = System.currentTimeMillis();
                for (Map.Entry entry : sortedMap.entrySet()) {
                    String username;
                    SharedTicketInfo ticket = (SharedTicketInfo) entry.getKey();
                    SupplierInfoExt supplier = (SupplierInfoExt)entry.getValue();
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
                            Logger.getLogger(OrderSupplierList.class.getName()).log(Level.SEVERE, null, ex);
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
                    ImageIcon imageIcon = new ImageIcon(OrderSupplierList.this.tnbbutton.getThumbNailText(image, text));
                }
                LOGGER.log(Level.INFO, "Time of finished loadSupplierOrders {0}", System.currentTimeMillis() - time);
            }
        });
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void setComponentEnabled(boolean value) {
        this.jPanelSuppliers.setEnabled(value);
        Object object = this.jPanelSuppliers.getTreeLock();
        synchronized (object) {
            int compCount = this.jPanelSuppliers.getComponentCount();
            for (int i = 0; i < compCount; ++i) {
                this.jPanelSuppliers.getComponent(i).setEnabled(value);
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

    @Override
    public void loadSupplierss() throws BasicException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void initComponents() {
        this.jPanelSuppliers = new JPanel();
        this.setMinimumSize(new Dimension(256, 560));
        this.setPreferredSize(new Dimension(256, 560));
        this.setLayout(new BorderLayout());
        this.jPanelSuppliers.setFont(new Font("Arial", 0, 14));
        this.jPanelSuppliers.setLayout(new CardLayout());
        this.add((Component)this.jPanelSuppliers, "Center");
    }

    class SupplierComparator
    implements Comparator<SharedTicketInfo> {
        Map<SharedTicketInfo, SupplierInfoExt> base;

        public SupplierComparator(Map<SharedTicketInfo, SupplierInfoExt> base) {
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

    private class SelectedSupplierAction
    implements ActionListener {
        private final String ticketId;

        public SelectedSupplierAction(String ticketId) {
            this.ticketId = ticketId;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (this.ticketId != null) {
                    OrderSupplierList.this.setActiveTicket(this.ticketId);
                }
            }
            catch (BasicException ex) {
                new MessageInf(ex).show(OrderSupplierList.this);
            }
        }
    }
}

