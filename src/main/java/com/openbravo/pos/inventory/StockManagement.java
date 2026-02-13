/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.inventory;

import com.openbravo.basic.BasicException;
import com.openbravo.beans.DateUtils;
import com.openbravo.beans.JCalendarDialog;
import com.openbravo.beans.JNumberEvent;
import com.openbravo.beans.JNumberEventListener;
import com.openbravo.beans.JNumberKeys;
import com.openbravo.data.gui.ComboBoxValModel;
import com.openbravo.data.gui.MessageInf;
import com.openbravo.data.loader.LocalRes;
import com.openbravo.data.loader.SentenceExec;
import com.openbravo.data.loader.SentenceList;
import com.openbravo.format.Formats;
import com.openbravo.pos.catalog.CatalogSelector;
import com.openbravo.pos.catalog.JCatalog;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.forms.DataLogicSales;
import com.openbravo.pos.forms.DataLogicSystem;
import com.openbravo.pos.forms.JPanelView;
import com.openbravo.pos.inventory.InventoryLine;
import com.openbravo.pos.inventory.InventoryRecord;
import com.openbravo.pos.inventory.JInventoryLines;
import com.openbravo.pos.inventory.LocationInfo;
import com.openbravo.pos.inventory.MovementReason;
import com.openbravo.pos.inventory.ProductStock;
import com.openbravo.pos.inventory.ProductsEditor;
import com.openbravo.pos.panels.JProductFinder;
import com.openbravo.pos.printer.TicketParser;
import com.openbravo.pos.printer.TicketPrinterException;
import com.openbravo.pos.sales.JProductAttEdit2;
import com.openbravo.pos.scripting.ScriptEngine;
import com.openbravo.pos.scripting.ScriptException;
import com.openbravo.pos.scripting.ScriptFactory;
import com.openbravo.pos.suppliers.DataLogicSuppliers;
import com.openbravo.pos.suppliers.SupplierInfo;
import com.openbravo.pos.ticket.ProductInfoExt;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;

public class StockManagement
extends JPanel
implements JPanelView {
    private static final long serialVersionUID = 1L;
    private final AppView m_App;
    private final String user;
    private final DataLogicSystem m_dlSystem;
    private final DataLogicSales m_dlSales;
    private final DataLogicSuppliers m_dlSuppliers;
    private final TicketParser m_TTP;
    private final CatalogSelector m_cat;
    private final ComboBoxValModel<MovementReason> m_ReasonModel;
    private final SentenceList<LocationInfo> m_sentlocations;
    private ComboBoxValModel<LocationInfo> m_LocationsModel;
    private ComboBoxValModel<LocationInfo> m_LocationsModelDes;
    private final SentenceList<SupplierInfo> m_sentsuppliers;
    private ComboBoxValModel<SupplierInfo> m_SuppliersModel;
    private final JInventoryLines m_invlines;
    private int NUMBER_STATE = 0;
    private int MULTIPLY = 0;
    private static final int DEFAULT = 0;
    private static final int ACTIVE = 1;
    private static final int DECIMAL = 2;
    private List<ProductStock> productStockList;
    private aStockTableModel stockModel;
    private static final int NUMBERZERO = 0;
    private static final int NUMBERVALID = 1;
    private static final int NUMBER_INPUTZERO = 0;
    private static final int NUMBER_INPUTZERODEC = 1;
    private static final int NUMBER_INPUTINT = 2;
    private static final int NUMBER_INPUTDEC = 3;
    private static final int NUMBER_PORZERO = 4;
    private static final int NUMBER_PORZERODEC = 5;
    private static final int NUMBER_PORINT = 6;
    private static final int NUMBER_PORDEC = 7;
    private int m_iNumberStatus;
    private int m_iNumberStatusInput;
    private int m_iNumberStatusPor;
    private StringBuffer m_sBarcode;
    private JPanel catcontainer;
    private JLabel jLabel1;
    private JLabel jLabel10;
    private JLabel jLabel2;
    private JLabel jLabel8;
    private JLabel jLabel9;
    private JNumberKeys jNumberKeys;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JPanel jPanel5;
    private JPanel jPanel8;
    private JScrollPane jScrollPane2;
    private JTable jTableProductStock;
    private JTextField jTextField1;
    private JLabel lbTotalValue;
    private JLabel lblTotalQtyValue;
    private JButton m_jBtnDelete;
    private JButton m_jBtnShowStock;
    private JButton m_jDelete;
    private JButton m_jEditAttributes;
    private JButton m_jEditLine;
    private JButton m_jEnter;
    private JButton m_jList;
    private JComboBox<LocationInfo> m_jLocation;
    private JComboBox<LocationInfo> m_jLocationDes;
    private JComboBox<SupplierInfo> m_jSupplier;
    private JTextField m_jSupplierDoc;
    private JButton m_jbtndate;
    private JLabel m_jcodebar;
    private JTextField m_jdate;
    private JComboBox<MovementReason> m_jreason;
    private JLabel webLblQty;
    private JLabel webLblValue;

    public StockManagement(AppView app) {
        this.m_App = app;
        this.m_dlSystem = (DataLogicSystem)this.m_App.getBean("com.openbravo.pos.forms.DataLogicSystem");
        this.m_dlSales = (DataLogicSales)this.m_App.getBean("com.openbravo.pos.forms.DataLogicSales");
        this.m_dlSuppliers = (DataLogicSuppliers)this.m_App.getBean("com.openbravo.pos.suppliers.DataLogicSuppliers");
        this.m_TTP = new TicketParser(this.m_App.getDeviceTicket(), this.m_dlSystem);
        this.initComponents();
        this.user = this.m_App.getAppUserView().getUser().getName();
        this.jNumberKeys.setEnabled(true);
        this.lblTotalQtyValue.setText(null);
        this.lbTotalValue.setText(null);
        this.m_sentlocations = this.m_dlSales.getLocationsList();
        this.m_LocationsModel = new ComboBoxValModel();
        this.m_LocationsModelDes = new ComboBoxValModel();
        this.m_ReasonModel = new ComboBoxValModel();
        this.m_ReasonModel.add(MovementReason.IN_PURCHASE);
        this.m_ReasonModel.add(MovementReason.OUT_SALE);
        this.m_ReasonModel.add(MovementReason.IN_REFUND);
        this.m_ReasonModel.add(MovementReason.OUT_REFUND);
        this.m_ReasonModel.add(MovementReason.IN_MOVEMENT);
        this.m_ReasonModel.add(MovementReason.OUT_MOVEMENT);
        this.m_ReasonModel.add(MovementReason.OUT_SUBTRACT);
        this.m_ReasonModel.add(MovementReason.OUT_BREAK);
        this.m_ReasonModel.add(MovementReason.OUT_FREE);
        this.m_ReasonModel.add(MovementReason.OUT_SAMPLE);
        this.m_ReasonModel.add(MovementReason.OUT_USED);
        this.m_ReasonModel.add(MovementReason.OUT_CROSSING);
        this.m_jreason.setModel(this.m_ReasonModel);
        this.m_sentsuppliers = this.m_dlSuppliers.getSupplierList();
        this.m_SuppliersModel = new ComboBoxValModel();
        this.m_cat = new JCatalog(this.m_dlSales);
        this.m_cat.addActionListener(new CatalogListener());
        this.catcontainer.add(this.m_cat.getComponent(), "Center");
        this.m_invlines = new JInventoryLines();
        this.jPanel5.add((Component)this.m_invlines, "Center");
        this.jTableProductStock.setVisible(false);
    }

    @Override
    public String getTitle() {
        return AppLocal.getIntString("Menu.StockMovement");
    }

    @Override
    public JComponent getComponent() {
        return this;
    }

    @Override
    public void activate() throws BasicException {
        this.m_cat.loadCatalog();
        List<LocationInfo> l = this.m_sentlocations.list();
        this.m_LocationsModel = new ComboBoxValModel<LocationInfo>(l);
        this.m_jLocation.setModel(this.m_LocationsModel);
        this.m_LocationsModelDes = new ComboBoxValModel<LocationInfo>(l);
        this.m_jLocationDes.setModel(this.m_LocationsModelDes);
        List<SupplierInfo> sl = this.m_sentsuppliers.list();
        this.m_SuppliersModel = new ComboBoxValModel<SupplierInfo>(sl);
        this.m_jSupplier.setModel(this.m_SuppliersModel);
        this.stateToInsert();
        EventQueue.invokeLater(() -> this.jTextField1.requestFocus());
    }

    public void stateToInsert() {
        this.m_jdate.setText(Formats.TIMESTAMP.formatValue(DateUtils.getTodayMinutes()));
        this.m_ReasonModel.setSelectedItem(MovementReason.IN_PURCHASE);
        this.m_LocationsModel.setSelectedKey(this.m_App.getInventoryLocation());
        this.m_LocationsModel.setSelectedFirst();
        this.m_LocationsModelDes.setSelectedKey(this.m_App.getInventoryLocation());
        this.m_jcodebar.setText(null);
        this.m_SuppliersModel.setSelectedFirst();
        this.m_jSupplierDoc.setText(null);
        this.m_invlines.clear();
        this.resetTranxTable();
    }

    @Override
    public boolean deactivate() {
        if (this.m_invlines.getCount() > 0) {
            int res = JOptionPane.showConfirmDialog(this, LocalRes.getIntString("message.wannasave"), LocalRes.getIntString("title.editor"), 1, 3);
            if (res == 0) {
                this.saveData();
                return true;
            }
            return res == 1;
        }
        return true;
    }

    private void addLine(ProductInfoExt oProduct, double dpor, double dprice) {
        this.m_invlines.addLine(new InventoryLine(oProduct, dpor, dprice));
        this.showStockTable();
    }

    private void deleteLine(int index) {
        if (index < 0) {
            Toolkit.getDefaultToolkit().beep();
        } else {
            this.m_invlines.deleteLine(index);
            this.clearStockTable();
            this.showStockTable();
            this.lblTotalQtyValue.setText(null);
            this.lbTotalValue.setText(null);
        }
    }

    private void incProduct(ProductInfoExt product, double units) {
        MovementReason reason = (MovementReason)this.m_ReasonModel.getSelectedItem();
        this.addLine(product, units, reason.isInput() ? product.getPriceBuy() : product.getPriceSell());
    }

    private void incProductByCode(String sCode) {
        this.incProductByCode(sCode, 1.0);
    }

    private void incProductByCode(String sCode, double dQuantity) {
        try {
            ProductInfoExt oProduct = this.m_dlSales.getProductInfoByCode(sCode);
            if (oProduct == null) {
                Toolkit.getDefaultToolkit().beep();
            } else {
                this.incProduct(oProduct, dQuantity);
            }
        }
        catch (BasicException eData) {
            MessageInf msg = new MessageInf(eData);
            msg.show(this);
        }
    }

    private List<ProductStock> getProductOfName(String pId) {
        try {
            this.productStockList = this.m_dlSales.getProductStockList(pId);
        }
        catch (BasicException ex) {
            Logger.getLogger(ProductsEditor.class.getName()).log(Level.SEVERE, null, ex);
        }
        ArrayList<ProductStock> productList = new ArrayList<ProductStock>();
        this.productStockList.stream().forEach(productStock -> {
            String productId = productStock.getProductId();
            if (productId.equals(pId)) {
                productList.add((ProductStock)productStock);
            }
        });
        this.repaint();
        return productList;
    }

    public void resetTranxTable() {
        this.jTableProductStock.getColumnModel().getColumn(0).setPreferredWidth(50);
        this.jTableProductStock.getColumnModel().getColumn(1).setPreferredWidth(50);
        this.jTableProductStock.getColumnModel().getColumn(2).setPreferredWidth(50);
        this.jTableProductStock.getColumnModel().getColumn(3).setPreferredWidth(50);
        this.jTableProductStock.getColumnModel().getColumn(4).setPreferredWidth(50);
        this.jTableProductStock.getColumnModel().getColumn(5).setPreferredWidth(50);
        this.jTableProductStock.repaint();
    }

    public void clearStockTable() {
        aStockTableModel model = (aStockTableModel)this.jTableProductStock.getModel();
        while (model.getRowCount() > 0) {
            for (int i = 0; i < model.getRowCount(); ++i) {
                model.stockList.removeAll(this.productStockList);
            }
            this.lblTotalQtyValue.setText(null);
            this.lbTotalValue.setText(null);
        }
        this.jTableProductStock.repaint();
    }

    public void showStockTable() {
        String pId = null;
        int i = this.m_invlines.getSelectedRow();
        if (i < 0) {
            Toolkit.getDefaultToolkit().beep();
        } else {
            InventoryLine line = this.m_invlines.getLine(i);
            pId = line.getProductID();
        }
        if (pId != null) {
            this.stockModel = new aStockTableModel(this.getProductOfName(pId));
            this.jTableProductStock.setModel(this.stockModel);
            if (this.stockModel.getRowCount() > 0) {
                this.jTableProductStock.setVisible(true);
            } else {
                this.jTableProductStock.setVisible(false);
                JOptionPane.showMessageDialog(null, AppLocal.getIntString("message.nostocklocation"), AppLocal.getIntString("message.title.nostocklocation"), 1);
            }
            this.sumStockTable();
            this.resetTranxTable();
        }
    }

    public void sumStockTable() {
        int i;
        double totalQty = 0.0;
        double totalVal = 0.0;
        double lQty = 0.0;
        double lVal = 0.0;
        for (i = 0; i < this.stockModel.getRowCount(); ++i) {
            totalQty += Double.parseDouble(this.stockModel.getValueAt(i, 1).toString());
            totalVal += Double.parseDouble(this.stockModel.getValueAt(i, 5).toString());
        }
        i = this.m_invlines.getSelectedRow();
        lQty = this.m_invlines.getLine(i).getMultiply();
        lVal = this.m_invlines.getLine(i).getPrice() * lQty;
        MovementReason reason = (MovementReason)this.m_ReasonModel.getSelectedItem();
        if (reason == MovementReason.OUT_BREAK || reason == MovementReason.OUT_FREE || reason == MovementReason.OUT_REFUND || reason == MovementReason.OUT_SALE || reason == MovementReason.OUT_SAMPLE || reason == MovementReason.OUT_SAMPLE || reason == MovementReason.OUT_SUBTRACT || reason == MovementReason.OUT_USED) {
            this.lblTotalQtyValue.setText(Double.toString(totalQty -= lQty));
            this.lbTotalValue.setText(Double.toString(totalVal -= lVal));
        } else {
            this.lblTotalQtyValue.setText(Double.toString(lQty += totalQty));
            this.lbTotalValue.setText(Double.toString(lVal += totalVal));
        }
    }

    private void addUnits(double dUnits) {
        int i = this.m_invlines.getSelectedRow();
        if (i >= 0) {
            InventoryLine inv = this.m_invlines.getLine(i);
            double dunits = inv.getMultiply() + dUnits;
            if (dunits <= 0.0) {
                this.deleteLine(i);
            } else {
                inv.setMultiply(inv.getMultiply() + dUnits);
                this.m_invlines.setLine(i, inv);
            }
            this.sumStockTable();
        }
    }

    private void setUnits(double dUnits) {
        int i = this.m_invlines.getSelectedRow();
        if (i >= 0) {
            InventoryLine inv = this.m_invlines.getLine(i);
            inv.setMultiply(dUnits);
            this.m_invlines.setLine(i, inv);
        }
    }

    private void stateTransition(char cTrans) {
        if (cTrans == '\n') {
            this.m_jEnter.doClick();
        }
        if (cTrans == '\u007f') {
            this.m_jcodebar.setText(null);
            this.NUMBER_STATE = 0;
        } else if (cTrans == '*') {
            this.MULTIPLY = 1;
        } else if (cTrans == '+') {
            if (this.MULTIPLY != 0 && this.NUMBER_STATE != 0) {
                this.setUnits(Double.parseDouble(this.m_jcodebar.getText()));
                this.m_jcodebar.setText(null);
            } else if (this.m_jcodebar.getText() == null || this.m_jcodebar.getText().equals("")) {
                this.addUnits(1.0);
            } else {
                this.addUnits(Double.parseDouble(this.m_jcodebar.getText()));
                this.m_jcodebar.setText(null);
            }
            this.NUMBER_STATE = 0;
            this.MULTIPLY = 0;
        } else if (cTrans == '-') {
            if (this.m_jcodebar.getText() == null || this.m_jcodebar.getText().equals("")) {
                this.addUnits(-1.0);
            } else {
                this.addUnits(-Double.parseDouble(this.m_jcodebar.getText()));
                this.m_jcodebar.setText(null);
            }
            this.NUMBER_STATE = 0;
            this.MULTIPLY = 0;
        } else if (cTrans == '.') {
            if (this.m_jcodebar.getText() == null || this.m_jcodebar.getText().equals("")) {
                this.m_jcodebar.setText("0.");
            } else if (this.NUMBER_STATE != 2) {
                this.m_jcodebar.setText(this.m_jcodebar.getText() + cTrans);
            }
            this.NUMBER_STATE = 2;
        } else if (cTrans == ' ' || cTrans == '=') {
            if (this.m_invlines.getCount() == 0) {
                Toolkit.getDefaultToolkit().beep();
            } else {
                this.saveData();
                this.jNumberKeys.setEnabled(true);
            }
        } else if (Character.isDigit(cTrans)) {
            if (this.m_jcodebar.getText() == null) {
                this.m_jcodebar.setText("" + cTrans);
            } else {
                this.m_jcodebar.setText(this.m_jcodebar.getText() + cTrans);
            }
            if (this.NUMBER_STATE != 2) {
                this.NUMBER_STATE = 1;
            }
        } else if (Character.isAlphabetic(cTrans)) {
            if (this.m_jcodebar.getText() == null) {
                this.m_jcodebar.setText("" + cTrans);
            } else {
                this.m_jcodebar.setText(this.m_jcodebar.getText() + cTrans);
            }
            if (this.NUMBER_STATE != 2) {
                this.NUMBER_STATE = 1;
            }
        } else {
            Toolkit.getDefaultToolkit().beep();
        }
    }

    protected void buttonTransition(ProductInfoExt prod) {
        this.incProduct(prod);
    }

    private void saveData() {
        try {
            Date d = (Date)Formats.TIMESTAMP.parseValue(this.m_jdate.getText());
            MovementReason reason = (MovementReason)this.m_ReasonModel.getSelectedItem();
            if (reason == MovementReason.OUT_CROSSING) {
                this.saveData(new InventoryRecord(d, MovementReason.OUT_MOVEMENT, (LocationInfo)this.m_LocationsModel.getSelectedItem(), this.m_App.getAppUserView().getUser().getName(), (SupplierInfo)this.m_SuppliersModel.getSelectedItem(), this.m_invlines.getLines(), this.m_jSupplierDoc.getText()));
                this.saveData(new InventoryRecord(d, MovementReason.IN_MOVEMENT, (LocationInfo)this.m_LocationsModelDes.getSelectedItem(), this.m_App.getAppUserView().getUser().getName(), (SupplierInfo)this.m_SuppliersModel.getSelectedItem(), this.m_invlines.getLines(), this.m_jSupplierDoc.getText()));
            } else {
                this.saveData(new InventoryRecord(d, reason, (LocationInfo)this.m_LocationsModel.getSelectedItem(), this.m_App.getAppUserView().getUser().getName(), (SupplierInfo)this.m_SuppliersModel.getSelectedItem(), this.m_invlines.getLines(), this.m_jSupplierDoc.getText()));
            }
            this.stateToInsert();
        }
        catch (BasicException eData) {
            MessageInf msg = new MessageInf(-67108864, AppLocal.getIntString("message.cannotsaveinventorydata"), eData);
            msg.show(this);
        }
    }

    private void saveData(InventoryRecord rec) throws BasicException {
        SentenceExec<Object[]> sent = this.m_dlSales.getStockDiaryInsert1();
        for (int i = 0; i < this.m_invlines.getCount(); ++i) {
            InventoryLine inv = rec.getLines().get(i);
            sent.exec(new Object[]{UUID.randomUUID().toString(), rec.getDate(), rec.getReason().getKey(), rec.getLocation().getID(), inv.getProductID(), inv.getProductAttSetInstId(), rec.getReason().samesignum(inv.getMultiply()), inv.getPrice(), rec.getUser(), rec.getSupplier().getID(), rec.getSupplierDoc()});
        }
        this.clearStockTable();
        this.printTicket(rec);
    }

    private void printTicket(InventoryRecord invrec) {
        String sresource = this.m_dlSystem.getResourceAsXML("Printer.Inventory");
        if (sresource == null) {
            MessageInf msg = new MessageInf(-33554432, AppLocal.getIntString("message.cannotprintticket"));
            msg.show(this);
        } else {
            try {
                ScriptEngine script = ScriptFactory.getScriptEngine("velocity");
                script.put("inventoryrecord", invrec);
                this.m_TTP.printTicket(script.eval(sresource).toString());
            }
            catch (TicketPrinterException | ScriptException e) {
                MessageInf msg = new MessageInf(-33554432, AppLocal.getIntString("message.cannotprintticket"), e);
                msg.show(this);
            }
        }
    }

    private void removeInvLine(int index) {
        if (index < 0) {
            Toolkit.getDefaultToolkit().beep();
        } else {
            this.m_invlines.deleteLine(index);
            this.clearStockTable();
            this.showStockTable();
            this.lblTotalQtyValue.setText(null);
            this.lbTotalValue.setText(null);
        }
    }

    public void deleteTicket(int index) {
        while (index < this.m_invlines.getCount()) {
            this.m_invlines.deleteLine(index);
        }
    }

    private void incProduct(ProductInfoExt prod) {
        this.incProduct(1.0, prod);
    }

    private void incProduct(double dPor, ProductInfoExt prod) {
        this.addLine(prod, dPor, prod.getPriceBuy());
    }

    private void stateToZero() {
        this.m_sBarcode = new StringBuffer();
        this.m_iNumberStatus = 0;
        this.m_iNumberStatusInput = 0;
        this.m_iNumberStatusPor = 0;
        this.repaint();
    }

    private void initComponents() {
        this.jPanel8 = new JPanel();
        this.jLabel1 = new JLabel();
        this.m_jdate = new JTextField();
        this.m_jbtndate = new JButton();
        this.jLabel2 = new JLabel();
        this.m_jreason = new JComboBox();
        this.jLabel8 = new JLabel();
        this.m_jLocation = new JComboBox();
        this.m_jLocationDes = new JComboBox();
        this.jLabel10 = new JLabel();
        this.m_jSupplier = new JComboBox();
        this.jLabel9 = new JLabel();
        this.m_jSupplierDoc = new JTextField();
        this.jPanel5 = new JPanel();
        this.m_jcodebar = new JLabel();
        this.m_jEnter = new JButton();
        this.jTextField1 = new JTextField();
        this.jPanel2 = new JPanel();
        this.m_jDelete = new JButton();
        this.m_jList = new JButton();
        this.m_jEditLine = new JButton();
        this.m_jEditAttributes = new JButton();
        this.m_jBtnDelete = new JButton();
        this.jPanel1 = new JPanel();
        this.jNumberKeys = new JNumberKeys();
        this.jScrollPane2 = new JScrollPane();
        this.jTableProductStock = new JTable();
        this.m_jBtnShowStock = new JButton();
        this.lblTotalQtyValue = new JLabel();
        this.lbTotalValue = new JLabel();
        this.webLblQty = new JLabel();
        this.webLblValue = new JLabel();
        this.catcontainer = new JPanel();
        this.setFont(new Font("Arial", 0, 12));
        this.setMinimumSize(new Dimension(550, 250));
        this.setPreferredSize(new Dimension(1000, 350));
        this.setLayout(new BorderLayout());
        this.jPanel8.setFont(new Font("Arial", 0, 12));
        this.jPanel8.setPreferredSize(new Dimension(1020, 320));
        this.jPanel8.setLayout(new AbsoluteLayout());
        this.jLabel1.setFont(new Font("Arial", 0, 14));
        this.jLabel1.setText(AppLocal.getIntString("label.stockdate"));
        this.jLabel1.setMaximumSize(new Dimension(40, 25));
        this.jLabel1.setMinimumSize(new Dimension(40, 25));
        this.jLabel1.setPreferredSize(new Dimension(70, 30));
        this.jPanel8.add((Component)this.jLabel1, new AbsoluteConstraints(5, 5, -1, -1));
        this.m_jdate.setFont(new Font("Arial", 0, 14));
        this.m_jdate.setPreferredSize(new Dimension(160, 30));
        this.jPanel8.add((Component)this.m_jdate, new AbsoluteConstraints(90, 5, -1, -1));
        this.m_jbtndate.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/date.png")));
        this.m_jbtndate.setToolTipText("Open Calendar");
        this.m_jbtndate.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                StockManagement.this.m_jbtndateActionPerformed(evt);
            }
        });
        this.jPanel8.add((Component)this.m_jbtndate, new AbsoluteConstraints(50, 5, 40, 30));
        this.jLabel2.setFont(new Font("Arial", 0, 14));
        this.jLabel2.setText(AppLocal.getIntString("label.stockreason"));
        this.jLabel2.setMaximumSize(new Dimension(40, 25));
        this.jLabel2.setMinimumSize(new Dimension(40, 25));
        this.jLabel2.setPreferredSize(new Dimension(70, 30));
        this.jPanel8.add((Component)this.jLabel2, new AbsoluteConstraints(5, 40, -1, -1));
        this.m_jreason.setFont(new Font("Arial", 0, 14));
        this.m_jreason.setMaximumRowCount(13);
        this.m_jreason.setPreferredSize(new Dimension(160, 30));
        this.m_jreason.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                StockManagement.this.m_jreasonActionPerformed(evt);
            }
        });
        this.jPanel8.add(this.m_jreason, new AbsoluteConstraints(90, 40, -1, -1));
        this.jLabel8.setFont(new Font("Arial", 0, 14));
        this.jLabel8.setText(AppLocal.getIntString("label.locationplace"));
        this.jLabel8.setMaximumSize(new Dimension(40, 25));
        this.jLabel8.setMinimumSize(new Dimension(40, 25));
        this.jLabel8.setPreferredSize(new Dimension(70, 30));
        this.jPanel8.add((Component)this.jLabel8, new AbsoluteConstraints(5, 75, -1, -1));
        this.m_jLocation.setFont(new Font("Arial", 0, 14));
        this.m_jLocation.setPreferredSize(new Dimension(160, 30));
        this.jPanel8.add(this.m_jLocation, new AbsoluteConstraints(90, 75, -1, -1));
        this.m_jLocationDes.setFont(new Font("Arial", 0, 14));
        this.m_jLocationDes.setPreferredSize(new Dimension(160, 30));
        this.jPanel8.add(this.m_jLocationDes, new AbsoluteConstraints(90, 110, -1, -1));
        this.jLabel10.setFont(new Font("Arial", 0, 14));
        this.jLabel10.setText(AppLocal.getIntString("label.supplier"));
        this.jLabel10.setMaximumSize(new Dimension(40, 25));
        this.jLabel10.setMinimumSize(new Dimension(40, 25));
        this.jLabel10.setPreferredSize(new Dimension(70, 30));
        this.jPanel8.add((Component)this.jLabel10, new AbsoluteConstraints(5, 145, -1, -1));
        this.m_jSupplier.setFont(new Font("Arial", 0, 14));
        this.m_jSupplier.setPreferredSize(new Dimension(160, 30));
        this.m_jSupplier.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                StockManagement.this.m_jSupplierActionPerformed(evt);
            }
        });
        this.jPanel8.add(this.m_jSupplier, new AbsoluteConstraints(90, 145, -1, -1));
        this.jLabel9.setFont(new Font("Arial", 0, 14));
        this.jLabel9.setText(AppLocal.getIntString("label.supplierdocment"));
        this.jLabel9.setToolTipText("null");
        this.jLabel9.setMaximumSize(new Dimension(40, 25));
        this.jLabel9.setMinimumSize(new Dimension(40, 25));
        this.jLabel9.setPreferredSize(new Dimension(70, 30));
        this.jPanel8.add((Component)this.jLabel9, new AbsoluteConstraints(5, 180, -1, -1));
        this.m_jSupplierDoc.setFont(new Font("Arial", 0, 14));
        this.m_jSupplierDoc.setToolTipText(AppLocal.getIntString("button.exit"));
        this.m_jSupplierDoc.setPreferredSize(new Dimension(160, 30));
        this.jPanel8.add((Component)this.m_jSupplierDoc, new AbsoluteConstraints(90, 180, -1, -1));
        this.jPanel5.setBorder(BorderFactory.createLineBorder(new Color(204, 204, 204)));
        this.jPanel5.setFont(new Font("Arial", 0, 12));
        this.jPanel5.setPreferredSize(new Dimension(455, 245));
        this.jPanel5.setLayout(new BorderLayout());
        this.jPanel8.add((Component)this.jPanel5, new AbsoluteConstraints(250, 5, -1, 190));
        this.m_jcodebar.setBackground(Color.white);
        this.m_jcodebar.setFont(new Font("Arial", 1, 14));
        this.m_jcodebar.setForeground(new Color(76, 197, 237));
        this.m_jcodebar.setHorizontalAlignment(4);
        this.m_jcodebar.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)), BorderFactory.createEmptyBorder(1, 1, 1, 1)));
        this.m_jcodebar.setOpaque(true);
        this.m_jcodebar.setPreferredSize(new Dimension(130, 25));
        this.m_jcodebar.setRequestFocusEnabled(false);
        this.m_jcodebar.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent evt) {
                StockManagement.this.m_jcodebarMouseClicked(evt);
            }
        });
        this.jPanel8.add((Component)this.m_jcodebar, new AbsoluteConstraints(780, 270, -1, -1));
        this.m_jEnter.setFont(new Font("Arial", 0, 14));
        this.m_jEnter.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/barcode.png")));
        this.m_jEnter.setFocusPainted(false);
        this.m_jEnter.setFocusable(false);
        this.m_jEnter.setPreferredSize(new Dimension(54, 45));
        this.m_jEnter.setRequestFocusEnabled(false);
        this.m_jEnter.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                StockManagement.this.m_jEnterActionPerformed(evt);
            }
        });
        this.jPanel8.add((Component)this.m_jEnter, new AbsoluteConstraints(920, 260, -1, -1));
        this.jTextField1.setBackground(UIManager.getDefaults().getColor("Panel.background"));
        this.jTextField1.setFont(new Font("Arial", 0, 14));
        this.jTextField1.setForeground(new Color(255, 255, 255));
        this.jTextField1.setCaretColor(UIManager.getDefaults().getColor("Panel.background"));
        this.jTextField1.setPreferredSize(new Dimension(1, 1));
        this.jTextField1.addKeyListener(new KeyAdapter(){

            @Override
            public void keyTyped(KeyEvent evt) {
                StockManagement.this.jTextField1KeyTyped(evt);
            }
        });
        this.jPanel8.add((Component)this.jTextField1, new AbsoluteConstraints(1, 1, -1, 0));
        this.jPanel2.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        this.jPanel2.setPreferredSize(new Dimension(70, 250));
        this.jPanel2.setLayout(new GridLayout(0, 1, 5, 5));
        this.m_jDelete.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/editdelete.png")));
        ResourceBundle bundle = ResourceBundle.getBundle("pos_messages");
        this.m_jDelete.setToolTipText(bundle.getString("tooltip.saleremoveline"));
        this.m_jDelete.setFocusPainted(false);
        this.m_jDelete.setFocusable(false);
        this.m_jDelete.setMargin(new Insets(8, 14, 8, 14));
        this.m_jDelete.setMaximumSize(new Dimension(42, 36));
        this.m_jDelete.setMinimumSize(new Dimension(42, 36));
        this.m_jDelete.setPreferredSize(new Dimension(50, 45));
        this.m_jDelete.setRequestFocusEnabled(false);
        this.m_jDelete.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                StockManagement.this.m_jDeleteActionPerformed(evt);
            }
        });
        this.jPanel2.add(this.m_jDelete);
        this.m_jList.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/search32.png")));
        this.m_jList.setToolTipText(bundle.getString("tooltip.saleproductfind"));
        this.m_jList.setFocusPainted(false);
        this.m_jList.setFocusable(false);
        this.m_jList.setMargin(new Insets(8, 14, 8, 14));
        this.m_jList.setMaximumSize(new Dimension(42, 36));
        this.m_jList.setMinimumSize(new Dimension(42, 36));
        this.m_jList.setPreferredSize(new Dimension(50, 45));
        this.m_jList.setRequestFocusEnabled(false);
        this.m_jList.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                StockManagement.this.m_jListActionPerformed(evt);
            }
        });
        this.jPanel2.add(this.m_jList);
        this.m_jEditLine.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/sale_editline.png")));
        this.m_jEditLine.setToolTipText(bundle.getString("tooltip.saleeditline"));
        this.m_jEditLine.setFocusPainted(false);
        this.m_jEditLine.setFocusable(false);
        this.m_jEditLine.setMargin(new Insets(8, 14, 8, 14));
        this.m_jEditLine.setMaximumSize(new Dimension(42, 36));
        this.m_jEditLine.setMinimumSize(new Dimension(42, 36));
        this.m_jEditLine.setPreferredSize(new Dimension(50, 45));
        this.m_jEditLine.setRequestFocusEnabled(false);
        this.m_jEditLine.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                StockManagement.this.m_jEditLineActionPerformed(evt);
            }
        });
        this.jPanel2.add(this.m_jEditLine);
        this.m_jEditAttributes.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/attributes.png")));
        this.m_jEditAttributes.setToolTipText(bundle.getString("tooltip.saleattributes"));
        this.m_jEditAttributes.setFocusPainted(false);
        this.m_jEditAttributes.setFocusable(false);
        this.m_jEditAttributes.setMargin(new Insets(8, 14, 8, 14));
        this.m_jEditAttributes.setMaximumSize(new Dimension(42, 36));
        this.m_jEditAttributes.setMinimumSize(new Dimension(42, 36));
        this.m_jEditAttributes.setPreferredSize(new Dimension(50, 45));
        this.m_jEditAttributes.setRequestFocusEnabled(false);
        this.m_jEditAttributes.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                StockManagement.this.m_jEditAttributesActionPerformed(evt);
            }
        });
        this.jPanel2.add(this.m_jEditAttributes);
        this.m_jBtnDelete.setFont(new Font("Arial", 0, 12));
        this.m_jBtnDelete.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/sale_delete.png")));
        this.m_jBtnDelete.setText(AppLocal.getIntString("button.deleteticket"));
        this.m_jBtnDelete.setToolTipText("Delete current Ticket");
        this.m_jBtnDelete.setFocusPainted(false);
        this.m_jBtnDelete.setFocusable(false);
        this.m_jBtnDelete.setMargin(new Insets(0, 4, 0, 4));
        this.m_jBtnDelete.setMaximumSize(new Dimension(50, 40));
        this.m_jBtnDelete.setMinimumSize(new Dimension(50, 40));
        this.m_jBtnDelete.setPreferredSize(new Dimension(80, 45));
        this.m_jBtnDelete.setRequestFocusEnabled(false);
        this.m_jBtnDelete.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                StockManagement.this.m_jBtnDeleteActionPerformed(evt);
            }
        });
        this.jPanel2.add(this.m_jBtnDelete);
        this.jPanel8.add((Component)this.jPanel2, new AbsoluteConstraints(705, 0, -1, -1));
        this.jPanel1.setMinimumSize(new Dimension(150, 250));
        this.jPanel1.setPreferredSize(new Dimension(200, 250));
        this.jNumberKeys.setPreferredSize(new Dimension(210, 240));
        this.jNumberKeys.addJNumberEventListener(new JNumberEventListener(){

            @Override
            public void keyPerformed(JNumberEvent evt) {
                StockManagement.this.jNumberKeysKeyPerformed(evt);
            }
        });
        GroupLayout jPanel1Layout = new GroupLayout(this.jPanel1);
        this.jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 230, Short.MAX_VALUE).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addGap(0, 10, Short.MAX_VALUE).addComponent(this.jNumberKeys, -2, -1, -2).addGap(0, 10, Short.MAX_VALUE))));
        jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 260, Short.MAX_VALUE).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE).addComponent(this.jNumberKeys, -2, -1, -2).addGap(0, 0, Short.MAX_VALUE))));
        this.jPanel8.add((Component)this.jPanel1, new AbsoluteConstraints(760, 0, 230, 260));
        this.jScrollPane2.setFont(new Font("Arial", 0, 14));
        this.jTableProductStock.setModel(new DefaultTableModel(new Object[][]{{null, null, null, null, null, null}, {null, null, null, null, null, null}, {null, null, null, null, null, null}, {null, null, null, null, null, null}, {null, null, null, null, null, null}, {null, null, null, null, null, null}, {null, null, null, null, null, null}, {null, null, null, null, null, null}}, new String[]{"Location", "Current", "Maximum", "Minimum", "PriceSell", "PriceValue"}));
        this.jTableProductStock.setFont(new Font("Arial", 0, 14));
        this.jTableProductStock.setRowHeight(25);
        this.jScrollPane2.setViewportView(this.jTableProductStock);
        this.jPanel8.add((Component)this.jScrollPane2, new AbsoluteConstraints(5, 220, 650, 70));
        this.m_jBtnShowStock.setFont(new Font("Arial", 0, 12));
        this.m_jBtnShowStock.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/pay.png")));
        this.m_jBtnShowStock.setToolTipText(AppLocal.getIntString("tooltip.salecheckstock"));
        this.m_jBtnShowStock.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                StockManagement.this.m_jBtnShowStockActionPerformed(evt);
            }
        });
        this.jPanel8.add((Component)this.m_jBtnShowStock, new AbsoluteConstraints(660, 250, 40, 40));
        this.lblTotalQtyValue.setFont(new Font("Arial", 0, 14));
        this.lblTotalQtyValue.setHorizontalAlignment(2);
        this.lblTotalQtyValue.setHorizontalTextPosition(2);
        this.lblTotalQtyValue.setPreferredSize(new Dimension(100, 30));
        this.jPanel8.add((Component)this.lblTotalQtyValue, new AbsoluteConstraints(118, 290, -1, -1));
        this.lbTotalValue.setFont(new Font("Arial", 0, 14));
        this.lbTotalValue.setHorizontalAlignment(2);
        this.lbTotalValue.setHorizontalTextPosition(2);
        this.lbTotalValue.setPreferredSize(new Dimension(100, 30));
        this.jPanel8.add((Component)this.lbTotalValue, new AbsoluteConstraints(547, 290, -1, -1));
        this.webLblQty.setHorizontalAlignment(2);
        this.webLblQty.setText(AppLocal.getIntString("label.stock.quantity"));
        this.webLblQty.setFont(new Font("Arial", 1, 12));
        this.webLblQty.setPreferredSize(new Dimension(90, 30));
        this.jPanel8.add((Component)this.webLblQty, new AbsoluteConstraints(5, 290, 100, -1));
        this.webLblValue.setHorizontalAlignment(4);
        this.webLblValue.setText(AppLocal.getIntString("label.stock.value"));
        this.webLblValue.setFont(new Font("Arial", 1, 12));
        this.webLblValue.setPreferredSize(new Dimension(180, 30));
        this.jPanel8.add((Component)this.webLblValue, new AbsoluteConstraints(355, 290, 170, -1));
        this.add((Component)this.jPanel8, "First");
        this.catcontainer.setFont(new Font("Arial", 0, 12));
        this.catcontainer.setMinimumSize(new Dimension(0, 250));
        this.catcontainer.setPreferredSize(new Dimension(0, 250));
        this.catcontainer.setRequestFocusEnabled(false);
        this.catcontainer.setLayout(new BorderLayout());
        this.add((Component)this.catcontainer, "Center");
        this.catcontainer.getAccessibleContext().setAccessibleParent(this.jPanel8);
    }

    private void jTextField1KeyTyped(KeyEvent evt) {
        this.jTextField1.setText(null);
        this.stateTransition(evt.getKeyChar());
    }

    private void jNumberKeysKeyPerformed(JNumberEvent evt) {
        this.stateTransition(evt.getKey());
    }

    private void m_jreasonActionPerformed(ActionEvent evt) {
        this.m_jLocationDes.setEnabled(this.m_ReasonModel.getSelectedItem() == MovementReason.OUT_CROSSING);
    }

    private void m_jbtndateActionPerformed(ActionEvent evt) {
        Date date;
        try {
            date = (Date)Formats.TIMESTAMP.parseValue(this.m_jdate.getText());
        }
        catch (BasicException e) {
            date = null;
        }
        date = JCalendarDialog.showCalendarTime(this, date);
        if (date != null) {
            this.m_jdate.setText(Formats.TIMESTAMP.formatValue(date));
        }
    }

    private void m_jEnterActionPerformed(ActionEvent evt) {
        this.incProductByCode(this.m_jcodebar.getText());
        this.m_jcodebar.setText(null);
        if (this.m_jSupplier.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(null, AppLocal.getIntString("message.supplierinvalid"), AppLocal.getIntString("message.title.supplierinvalid"), 2);
        }
    }

    private void m_jSupplierActionPerformed(ActionEvent evt) {
    }

    private void m_jBtnShowStockActionPerformed(ActionEvent evt) {
        this.showStockTable();
    }

    private void m_jDeleteActionPerformed(ActionEvent evt) {
        int i = this.m_invlines.getSelectedRow();
        if (i < 0) {
            Toolkit.getDefaultToolkit().beep();
        } else {
            this.removeInvLine(i);
        }
    }

    private void m_jListActionPerformed(ActionEvent evt) {
        ProductInfoExt prod = JProductFinder.showMessage(this, this.m_dlSales);
        if (prod != null) {
            this.buttonTransition(prod);
        }
    }

    private void m_jEditLineActionPerformed(ActionEvent evt) {
        int i = this.m_invlines.getSelectedRow();
        if (i < 0) {
            Toolkit.getDefaultToolkit().beep();
        } else {
            InventoryLine line = this.m_invlines.getLine(i);
            JFrame frame = new JFrame("New Price Buy");
            String spricebuy = JOptionPane.showInputDialog(frame, AppLocal.getIntString("message.enterbuyprice"), 1);
            if (spricebuy != null) {
                double dpricebuy = Double.parseDouble(spricebuy);
                line.setPrice(dpricebuy);
                this.m_invlines.setLine(i, line);
            }
        }
    }

    private void m_jEditAttributesActionPerformed(ActionEvent evt) {
        int i = this.m_invlines.getSelectedRow();
        if (i < 0) {
            Toolkit.getDefaultToolkit().beep();
        } else {
            try {
                InventoryLine line = this.m_invlines.getLine(i);
                JProductAttEdit2 attedit = JProductAttEdit2.getAttributesEditor(this, this.m_App.getSession());
                attedit.editAttributes(line.getProductAttSetId(), line.getProductAttSetInstId());
                attedit.setVisible(true);
                if (attedit.isOK()) {
                    line.setProductAttSetInstId(attedit.getAttributeSetInst());
                    line.setProductAttSetInstDesc(attedit.getAttributeSetInstDescription());
                    this.m_invlines.setLine(i, line);
                }
            }
            catch (BasicException ex) {
                MessageInf msg = new MessageInf(-33554432, AppLocal.getIntString("message.cannotfindattributes"), ex);
                msg.show(this);
            }
        }
    }

    private void m_jBtnDeleteActionPerformed(ActionEvent evt) {
        int res = JOptionPane.showConfirmDialog(this, AppLocal.getIntString("message.wannadelete"), AppLocal.getIntString("title.editor"), 0, 3);
        if (res == 0) {
            int i = 0;
            while (i < this.m_invlines.getCount()) {
                this.m_invlines.deleteLine(i);
            }
            this.clearStockTable();
            this.showStockTable();
            this.lblTotalQtyValue.setText(null);
            this.lbTotalValue.setText(null);
            this.jTableProductStock.repaint();
        }
    }

    private void m_jcodebarMouseClicked(MouseEvent evt) {
        this.m_jcodebar.requestFocusInWindow();
        this.jTextField1.requestFocus();
        this.m_jcodebar.setEnabled(true);
        this.m_jcodebar.setText(null);
    }

    private class CatalogListener
    implements ActionListener {
        private CatalogListener() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String sQty = StockManagement.this.m_jcodebar.getText();
            if (sQty != null) {
                Double dQty = Double.valueOf(sQty) == 0.0 ? 1.0 : Double.valueOf(sQty);
                StockManagement.this.incProduct((ProductInfoExt)e.getSource(), dQty);
                StockManagement.this.m_jcodebar.setText(null);
            } else {
                StockManagement.this.incProduct((ProductInfoExt)e.getSource(), 1.0);
            }
        }
    }

    class aStockTableModel
    extends AbstractTableModel {
        String loc = AppLocal.getIntString("label.tblProdHeaderCol1");
        String qty = AppLocal.getIntString("label.tblProdHeaderCol2");
        String max = AppLocal.getIntString("label.tblProdHeaderCol3");
        String min = AppLocal.getIntString("label.tblProdHeaderCol4");
        String buy = AppLocal.getIntString("label.tblProdHeaderCol5");
        String val = AppLocal.getIntString("label.tblProdHeaderCol6");
        List<ProductStock> stockList;
        String[] columnNames = new String[]{this.loc, this.qty, this.max, this.min, this.buy, this.val};

        public aStockTableModel(List<ProductStock> list) {
            this.stockList = list;
        }

        @Override
        public int getColumnCount() {
            return 6;
        }

        @Override
        public int getRowCount() {
            return this.stockList.size();
        }

        @Override
        public Object getValueAt(int row, int column) {
            ProductStock productStock = this.stockList.get(row);
            switch (column) {
                case 0: {
                    return productStock.getLocation();
                }
                case 1: {
                    return productStock.getUnits();
                }
                case 2: {
                    return productStock.getMinimum();
                }
                case 3: {
                    return productStock.getMaximum();
                }
                case 4: {
                    return productStock.getPriceSell();
                }
                case 5: {
                    return productStock.getUnits() * productStock.getPriceSell();
                }
                case 6: {
                    return productStock.getProductId();
                }
            }
            return "";
        }

        public Object setValueAt(int row, int column) {
            ProductStock productStock = this.stockList.get(row);
            switch (column) {
                case 0: {
                    return productStock.getLocation();
                }
                case 1: {
                    return productStock.getUnits();
                }
                case 2: {
                    return productStock.getMinimum();
                }
                case 3: {
                    return productStock.getMaximum();
                }
                case 4: {
                    return productStock.getPriceSell();
                }
                case 5: {
                    return productStock.getUnits() * productStock.getPriceSell();
                }
                case 6: {
                    return productStock.getProductId();
                }
            }
            return "";
        }

        @Override
        public String getColumnName(int col) {
            return this.columnNames[col];
        }
    }
}

