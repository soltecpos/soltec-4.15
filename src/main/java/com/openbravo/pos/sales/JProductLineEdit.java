/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.sales;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.Session;
import com.openbravo.editor.JEditorCurrency;
import com.openbravo.editor.JEditorDouble;
import com.openbravo.editor.JEditorKeys;
import com.openbravo.editor.JEditorString;
import com.openbravo.pos.forms.AppConfig;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.ticket.TicketLineInfo;
import com.openbravo.pos.util.AltEncrypter;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;
import javax.swing.UIManager;

public class JProductLineEdit
extends JDialog {
    private TicketLineInfo returnLine;
    private TicketLineInfo m_oLine;
    private boolean m_bunitsok;
    private boolean m_bpriceok;
    private String productID;
    private Session s;
    private Connection con;
    private String SQL;
    private PreparedStatement pstmt;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JPanel jPanel3;
    private JPanel jPanel4;
    private JPanel jPanel5;
    private JButton m_jBtnPriceUpdate;
    private JButton m_jButtonCancel;
    private JButton m_jButtonOK;
    private JEditorKeys m_jKeys;
    private JEditorString m_jName;
    private JEditorCurrency m_jPrice;
    private JEditorCurrency m_jPriceTax;
    private JLabel m_jSubtotal;
    private JLabel m_jTaxrate;
    private JLabel m_jTotal;
    private JEditorDouble m_jUnits;

    private JProductLineEdit(Frame parent, boolean modal) {
        super(parent, modal);
    }

    private JProductLineEdit(Dialog parent, boolean modal) {
        super(parent, modal);
    }

    private TicketLineInfo init(AppView app, TicketLineInfo oLine) throws BasicException {
        this.initComponents();
        this.productID = oLine.getProductID();
        if (oLine.getTaxInfo() == null) {
            throw new BasicException(AppLocal.getIntString("message.cannotcalculatetaxes"));
        }
        if (!this.productID.equals("xxx999_999xxx_x9x9x9")) {
            this.m_jBtnPriceUpdate.setVisible(AppConfig.getInstance().getBoolean("db.prodpriceupdate"));
        } else {
            this.m_jBtnPriceUpdate.setVisible(false);
        }
        this.m_jBtnPriceUpdate.setEnabled(false);
        this.m_oLine = new TicketLineInfo(oLine);
        this.m_bunitsok = true;
        this.m_bpriceok = true;
        this.m_jName.setEnabled(app.getAppUserView().getUser().hasPermission("com.openbravo.pos.sales.JPanelTicketEdits"));
        this.m_jPrice.setEnabled(app.getAppUserView().getUser().hasPermission("com.openbravo.pos.sales.JPanelTicketEdits"));
        this.m_jPriceTax.setEnabled(app.getAppUserView().getUser().hasPermission("com.openbravo.pos.sales.JPanelTicketEdits"));
        this.m_jName.setText(oLine.getProductName());
        this.m_jUnits.setDoubleValue(oLine.getMultiply());
        this.m_jPrice.setDoubleValue(oLine.getPrice());
        this.m_jPriceTax.setDoubleValue(oLine.getPriceTax());
        this.m_jTaxrate.setText(oLine.getTaxInfo().getName());
        this.m_jName.addPropertyChangeListener("Edition", new RecalculateName());
        this.m_jUnits.addPropertyChangeListener("Edition", new RecalculateUnits());
        this.m_jPrice.addPropertyChangeListener("Edition", new RecalculatePrice());
        this.m_jPriceTax.addPropertyChangeListener("Edition", new RecalculatePriceTax());
        this.m_jName.addEditorKeys(this.m_jKeys);
        this.m_jUnits.addEditorKeys(this.m_jKeys);
        this.m_jPrice.addEditorKeys(this.m_jKeys);
        this.m_jPriceTax.addEditorKeys(this.m_jKeys);
        if (this.m_jName.isEnabled()) {
            this.m_jName.activate();
        } else {
            this.m_jUnits.activate();
        }
        this.printTotals();
        this.getRootPane().setDefaultButton(this.m_jButtonOK);
        this.returnLine = null;
        this.setVisible(true);
        return this.returnLine;
    }

    private void printTotals() {
        if (this.m_bunitsok && this.m_bpriceok) {
            this.m_jSubtotal.setText(this.m_oLine.printSubValue());
            this.m_jTotal.setText(this.m_oLine.printValue());
            this.m_jButtonOK.setEnabled(true);
        } else {
            this.m_jSubtotal.setText(null);
            this.m_jTotal.setText(null);
            this.m_jButtonOK.setEnabled(false);
        }
    }

    private static Window getWindow(Component parent) {
        if (parent == null) {
            return new JFrame();
        }
        if (parent instanceof Frame || parent instanceof Dialog) {
            return (Window)parent;
        }
        return JProductLineEdit.getWindow(parent.getParent());
    }

    public static TicketLineInfo showMessage(Component parent, AppView app, TicketLineInfo oLine) throws BasicException {
        Window window = JProductLineEdit.getWindow(parent);
        JProductLineEdit myMsg = window instanceof Frame ? new JProductLineEdit((Frame)window, true) : new JProductLineEdit((Dialog)window, true);
        return myMsg.init(app, oLine);
    }

    private void initComponents() {
        this.jPanel5 = new JPanel();
        this.jPanel2 = new JPanel();
        this.jLabel1 = new JLabel();
        this.jLabel2 = new JLabel();
        this.jLabel3 = new JLabel();
        this.jLabel4 = new JLabel();
        this.m_jName = new JEditorString();
        this.m_jUnits = new JEditorDouble();
        this.m_jPrice = new JEditorCurrency();
        this.m_jPriceTax = new JEditorCurrency();
        this.m_jTaxrate = new JLabel();
        this.jLabel5 = new JLabel();
        this.jLabel6 = new JLabel();
        this.m_jTotal = new JLabel();
        this.jLabel7 = new JLabel();
        this.m_jSubtotal = new JLabel();
        this.m_jBtnPriceUpdate = new JButton();
        this.jPanel3 = new JPanel();
        this.jPanel4 = new JPanel();
        this.m_jKeys = new JEditorKeys();
        this.jPanel1 = new JPanel();
        this.m_jButtonCancel = new JButton();
        this.m_jButtonOK = new JButton();
        this.setDefaultCloseOperation(2);
        this.setTitle(AppLocal.getIntString("label.editline"));
        this.setPreferredSize(new Dimension(650, 350));
        this.jPanel5.setLayout(new BorderLayout());
        this.jPanel2.setPreferredSize(new Dimension(400, 230));
        this.jLabel1.setFont(new Font("Arial", 0, 14));
        this.jLabel1.setText(AppLocal.getIntString("label.price"));
        this.jLabel1.setPreferredSize(new Dimension(110, 30));
        this.jLabel2.setFont(new Font("Arial", 0, 14));
        this.jLabel2.setText(AppLocal.getIntString("label.units"));
        this.jLabel2.setPreferredSize(new Dimension(110, 30));
        this.jLabel3.setFont(new Font("Arial", 0, 14));
        this.jLabel3.setText(AppLocal.getIntString("label.pricetax"));
        this.jLabel3.setPreferredSize(new Dimension(110, 30));
        this.jLabel4.setFont(new Font("Arial", 0, 14));
        this.jLabel4.setText(AppLocal.getIntString("label.item"));
        this.jLabel4.setPreferredSize(new Dimension(110, 30));
        this.m_jName.setFont(new Font("Arial", 0, 14));
        this.m_jName.setPreferredSize(new Dimension(132, 30));
        this.m_jUnits.setFont(new Font("Arial", 0, 14));
        this.m_jUnits.setPreferredSize(new Dimension(132, 30));
        this.m_jPrice.setFont(new Font("Arial", 0, 14));
        this.m_jPrice.setPreferredSize(new Dimension(132, 30));
        this.m_jPriceTax.setFont(new Font("Arial", 0, 14));
        this.m_jPriceTax.setPreferredSize(new Dimension(132, 30));
        this.m_jTaxrate.setBackground(UIManager.getDefaults().getColor("TextField.disabledBackground"));
        this.m_jTaxrate.setFont(new Font("Arial", 0, 12));
        this.m_jTaxrate.setHorizontalAlignment(4);
        this.m_jTaxrate.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(UIManager.getDefaults().getColor("Button.darkShadow")), BorderFactory.createEmptyBorder(1, 4, 1, 4)));
        this.m_jTaxrate.setOpaque(true);
        this.m_jTaxrate.setPreferredSize(new Dimension(150, 25));
        this.m_jTaxrate.setRequestFocusEnabled(false);
        this.jLabel5.setFont(new Font("Arial", 0, 14));
        this.jLabel5.setText(AppLocal.getIntString("label.tax"));
        this.jLabel5.setPreferredSize(new Dimension(110, 30));
        this.jLabel6.setFont(new Font("Arial", 0, 14));
        this.jLabel6.setText(AppLocal.getIntString("label.totalcash"));
        this.jLabel6.setPreferredSize(new Dimension(110, 30));
        this.m_jTotal.setBackground(UIManager.getDefaults().getColor("TextField.disabledBackground"));
        this.m_jTotal.setFont(new Font("Arial", 0, 12));
        this.m_jTotal.setHorizontalAlignment(4);
        this.m_jTotal.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(UIManager.getDefaults().getColor("Button.darkShadow")), BorderFactory.createEmptyBorder(1, 4, 1, 4)));
        this.m_jTotal.setOpaque(true);
        this.m_jTotal.setPreferredSize(new Dimension(150, 25));
        this.m_jTotal.setRequestFocusEnabled(false);
        this.jLabel7.setFont(new Font("Arial", 0, 14));
        this.jLabel7.setText(AppLocal.getIntString("label.subtotalcash"));
        this.jLabel7.setPreferredSize(new Dimension(110, 30));
        this.m_jSubtotal.setBackground(UIManager.getDefaults().getColor("TextField.disabledBackground"));
        this.m_jSubtotal.setFont(new Font("Arial", 0, 12));
        this.m_jSubtotal.setHorizontalAlignment(4);
        this.m_jSubtotal.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(UIManager.getDefaults().getColor("Button.darkShadow")), BorderFactory.createEmptyBorder(1, 4, 1, 4)));
        this.m_jSubtotal.setOpaque(true);
        this.m_jSubtotal.setPreferredSize(new Dimension(150, 25));
        this.m_jSubtotal.setRequestFocusEnabled(false);
        this.m_jBtnPriceUpdate.setFont(new Font("Arial", 0, 12));
        this.m_jBtnPriceUpdate.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/filesave.png")));
        ResourceBundle bundle = ResourceBundle.getBundle("pos_messages");
        this.m_jBtnPriceUpdate.setText(bundle.getString("button.priceupdate"));
        this.m_jBtnPriceUpdate.setFocusPainted(false);
        this.m_jBtnPriceUpdate.setFocusable(false);
        this.m_jBtnPriceUpdate.setMargin(new Insets(8, 16, 8, 16));
        this.m_jBtnPriceUpdate.setPreferredSize(new Dimension(110, 45));
        this.m_jBtnPriceUpdate.setRequestFocusEnabled(false);
        this.m_jBtnPriceUpdate.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JProductLineEdit.this.m_jBtnPriceUpdateActionPerformed(evt);
            }
        });
        GroupLayout jPanel2Layout = new GroupLayout(this.jPanel2);
        this.jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel2Layout.createSequentialGroup().addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel2Layout.createSequentialGroup().addGap(10, 10, 10).addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel2Layout.createSequentialGroup().addComponent(this.jLabel2, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.m_jUnits, -2, 100, -2)).addGroup(jPanel2Layout.createSequentialGroup().addComponent(this.jLabel3, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.m_jPriceTax, -2, 150, -2)).addGroup(jPanel2Layout.createSequentialGroup().addComponent(this.jLabel1, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.m_jPrice, -2, 150, -2)).addGroup(jPanel2Layout.createSequentialGroup().addComponent(this.jLabel4, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.m_jName, -2, 240, -2)))).addGroup(jPanel2Layout.createSequentialGroup().addContainerGap().addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel5, -2, -1, -2).addGroup(jPanel2Layout.createSequentialGroup().addComponent(this.jLabel7, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.m_jSubtotal, -2, 120, -2)).addGroup(jPanel2Layout.createSequentialGroup().addGap(116, 116, 116).addComponent(this.m_jTaxrate, -2, 120, -2)).addGroup(jPanel2Layout.createSequentialGroup().addComponent(this.jLabel6, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.m_jBtnPriceUpdate, -2, -1, -2).addComponent(this.m_jTotal, -2, 120, -2)))))).addGap(60, 60, 60)));
        jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel2Layout.createSequentialGroup().addContainerGap().addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.m_jName, -2, -1, -2).addComponent(this.jLabel4, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.jLabel2, -2, -1, -2).addComponent(this.m_jUnits, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.m_jPrice, -2, -1, -2).addComponent(this.jLabel1, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel3, -2, -1, -2).addComponent(this.m_jPriceTax, -2, -1, -2)).addGap(9, 9, 9).addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel2Layout.createSequentialGroup().addGap(1, 1, 1).addComponent(this.jLabel5, -2, -1, -2)).addGroup(GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup().addComponent(this.m_jTaxrate, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED))).addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel7, -2, -1, -2).addComponent(this.m_jSubtotal, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.m_jTotal, -2, -1, -2).addComponent(this.jLabel6, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE).addComponent(this.m_jBtnPriceUpdate, -2, -1, -2).addContainerGap()));
        this.jPanel5.add((Component)this.jPanel2, "Center");
        this.getContentPane().add((Component)this.jPanel5, "Center");
        this.jPanel3.setLayout(new BorderLayout());
        this.jPanel4.setLayout(new BoxLayout(this.jPanel4, 1));
        this.m_jKeys.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JProductLineEdit.this.m_jKeysActionPerformed(evt);
            }
        });
        this.jPanel4.add(this.m_jKeys);
        this.jPanel3.add((Component)this.jPanel4, "North");
        this.jPanel1.setLayout(new FlowLayout(2));
        this.m_jButtonCancel.setFont(new Font("Arial", 0, 12));
        this.m_jButtonCancel.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/cancel.png")));
        this.m_jButtonCancel.setText(AppLocal.getIntString("button.cancel"));
        this.m_jButtonCancel.setFocusPainted(false);
        this.m_jButtonCancel.setFocusable(false);
        this.m_jButtonCancel.setMargin(new Insets(8, 16, 8, 16));
        this.m_jButtonCancel.setPreferredSize(new Dimension(110, 45));
        this.m_jButtonCancel.setRequestFocusEnabled(false);
        this.m_jButtonCancel.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JProductLineEdit.this.m_jButtonCancelActionPerformed(evt);
            }
        });
        this.jPanel1.add(this.m_jButtonCancel);
        this.m_jButtonOK.setFont(new Font("Arial", 0, 12));
        this.m_jButtonOK.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/ok.png")));
        this.m_jButtonOK.setText(AppLocal.getIntString("button.OK"));
        this.m_jButtonOK.setFocusPainted(false);
        this.m_jButtonOK.setFocusable(false);
        this.m_jButtonOK.setMargin(new Insets(8, 16, 8, 16));
        this.m_jButtonOK.setPreferredSize(new Dimension(110, 45));
        this.m_jButtonOK.setRequestFocusEnabled(false);
        this.m_jButtonOK.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JProductLineEdit.this.m_jButtonOKActionPerformed(evt);
            }
        });
        this.jPanel1.add(this.m_jButtonOK);
        this.jPanel3.add((Component)this.jPanel1, "South");
        this.getContentPane().add((Component)this.jPanel3, "East");
        this.setSize(new Dimension(708, 394));
        this.setLocationRelativeTo(null);
    }

    private void m_jButtonCancelActionPerformed(ActionEvent evt) {
        this.dispose();
    }

    private void m_jButtonOKActionPerformed(ActionEvent evt) {
        this.returnLine = this.m_oLine;
        this.dispose();
    }

    private void m_jKeysActionPerformed(ActionEvent evt) {
    }

    private void m_jBtnPriceUpdateActionPerformed(ActionEvent evt) {
        String db_password = AppConfig.getInstance().getProperty("db.password");
        if (AppConfig.getInstance().getProperty("db.user") != null && db_password != null && db_password.startsWith("crypt:")) {
            AltEncrypter cypher = new AltEncrypter("cypherkey" + AppConfig.getInstance().getProperty("db.user"));
            db_password = cypher.decrypt(db_password.substring(6));
        }
        try {
            this.con = DriverManager.getConnection(AppConfig.getInstance().getProperty("db.URL"), AppConfig.getInstance().getProperty("db.user"), db_password);
            this.pstmt = this.con.prepareStatement("UPDATE PRODUCTS SET PRICESELL = ? WHERE ID = ?");
            this.pstmt.setDouble(1, this.m_jPrice.getDoubleValue());
            this.pstmt.setString(2, this.productID);
            this.pstmt.executeUpdate();
            this.m_jBtnPriceUpdate.setEnabled(false);
            this.con.close();
        }
        catch (SQLException e) {
            return;
        }
        this.m_oLine.setUpdated(true);
    }

    private class RecalculateName
    implements PropertyChangeListener {
        private RecalculateName() {
        }

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            JProductLineEdit.this.m_oLine.setProperty("product.name", JProductLineEdit.this.m_jName.getText());
        }
    }

    private class RecalculateUnits
    implements PropertyChangeListener {
        private RecalculateUnits() {
        }

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            Double value = JProductLineEdit.this.m_jUnits.getDoubleValue();
            if (value == null || value == 0.0) {
                JProductLineEdit.this.m_bunitsok = false;
            } else {
                JProductLineEdit.this.m_oLine.setMultiply(value);
                JProductLineEdit.this.m_bunitsok = true;
            }
            JProductLineEdit.this.printTotals();
        }
    }

    private class RecalculatePrice
    implements PropertyChangeListener {
        private RecalculatePrice() {
        }

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            Double value = JProductLineEdit.this.m_jPrice.getDoubleValue();
            if (value == null || value == 0.0) {
                JProductLineEdit.this.m_bpriceok = false;
            } else {
                JProductLineEdit.this.m_oLine.setPrice(value);
                JProductLineEdit.this.m_jPriceTax.setDoubleValue(JProductLineEdit.this.m_oLine.getPriceTax());
                JProductLineEdit.this.m_bpriceok = true;
                JProductLineEdit.this.m_jBtnPriceUpdate.setEnabled(AppConfig.getInstance().getBoolean("db.prodpriceupdate"));
            }
            JProductLineEdit.this.printTotals();
        }
    }

    private class RecalculatePriceTax
    implements PropertyChangeListener {
        private RecalculatePriceTax() {
        }

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            Double value = JProductLineEdit.this.m_jPriceTax.getDoubleValue();
            if (value == null || value == 0.0) {
                JProductLineEdit.this.m_bpriceok = false;
            } else {
                JProductLineEdit.this.m_oLine.setPriceTax(value);
                JProductLineEdit.this.m_jPrice.setDoubleValue(JProductLineEdit.this.m_oLine.getPrice());
                JProductLineEdit.this.m_bpriceok = true;
                JProductLineEdit.this.m_jBtnPriceUpdate.setEnabled(AppConfig.getInstance().getBoolean("db.prodpriceupdate"));
            }
            JProductLineEdit.this.printTotals();
        }
    }
}

