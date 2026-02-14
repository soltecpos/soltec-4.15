/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.panels;

import com.openbravo.basic.BasicException;
import com.openbravo.data.gui.MessageInf;
import com.openbravo.data.loader.SerializerWrite;
import com.openbravo.data.loader.SerializerWriteString;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.forms.DataLogicSales;
import com.openbravo.pos.panels.JProductFinder;
import com.openbravo.pos.reports.ReportEditorCreator;
import com.openbravo.pos.ticket.ProductInfoExt;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventListener;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.event.EventListenerList;

public class BundleFilter
extends JPanel
implements ReportEditorCreator {
    private ProductInfoExt product;
    private DataLogicSales m_dlSales;
    protected EventListenerList listeners = new EventListenerList();
    private JButton Enter1;
    private JButton Enter2;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JTextField m_jBarcode1;
    private JTextField m_jReference1;
    private JTextField m_jSearch;
    private JButton search;

    public BundleFilter() {
        this.initComponents();
    }

    @Override
    public void init(AppView app) {
        this.m_dlSales = (DataLogicSales)app.getBean("com.openbravo.pos.forms.DataLogicSales");
    }

    @Override
    public void activate() throws BasicException {
        this.product = null;
        this.m_jSearch.setText(null);
        this.m_jBarcode1.setText(null);
        this.m_jReference1.setText(null);
    }

    @Override
    public SerializerWrite getSerializerWrite() {
        return SerializerWriteString.INSTANCE;
    }

    public void addActionListener(ActionListener l) {
        this.listeners.add(ActionListener.class, l);
    }

    public void removeActionListener(ActionListener l) {
        this.listeners.remove(ActionListener.class, l);
    }

    @Override
    public Component getComponent() {
        return this;
    }

    @Override
    public Object createValue() throws BasicException {
        return this.product == null ? null : this.product.getID();
    }

    public ProductInfoExt getProductInfoExt() {
        return this.product;
    }

    private void assignProduct(ProductInfoExt prod) {
        this.product = prod;
        if (this.product == null) {
            this.m_jSearch.setText(null);
            this.m_jBarcode1.setText(null);
            this.m_jReference1.setText(null);
        } else {
            this.m_jSearch.setText(this.product.getReference() + " - " + this.product.getName());
            this.m_jBarcode1.setText(this.product.getCode());
            this.m_jReference1.setText(this.product.getReference());
        }
        this.fireSelectedProduct();
    }

    protected void fireSelectedProduct() {
        EventListener[] l = this.listeners.getListeners(ActionListener.class);
        ActionEvent e = null;
        for (int i = 0; i < l.length; ++i) {
            if (e == null) {
                e = new ActionEvent(this, 1001, "SELECTED");
            }
            ((ActionListener)l[i]).actionPerformed(e);
        }
    }

    private void assignProductByCode() {
        try {
            ProductInfoExt prod = this.m_dlSales.getProductInfoByCode(this.m_jBarcode1.getText());
            if (prod == null) {
                Toolkit.getDefaultToolkit().beep();
            }
            this.assignProduct(prod);
        }
        catch (BasicException eData) {
            MessageInf msg = new MessageInf(eData);
            msg.show(this);
            this.assignProduct(null);
        }
    }

    private void assignProductByReference() {
        try {
            ProductInfoExt prod = this.m_dlSales.getProductInfoByReference(this.m_jReference1.getText());
            if (prod == null) {
                Toolkit.getDefaultToolkit().beep();
            }
            this.assignProduct(prod);
        }
        catch (BasicException eData) {
            MessageInf msg = new MessageInf(eData);
            msg.show(this);
            this.assignProduct(null);
        }
    }

    private void initComponents() {
        this.jLabel6 = new JLabel();
        this.m_jReference1 = new JTextField();
        this.Enter1 = new JButton();
        this.jLabel7 = new JLabel();
        this.m_jBarcode1 = new JTextField();
        this.Enter2 = new JButton();
        this.m_jSearch = new JTextField();
        this.search = new JButton();
        this.setFont(new Font("Arial", 0, 12));
        this.jLabel6.setFont(new Font("Arial", 0, 14));
        this.jLabel6.setText(AppLocal.getIntString("label.prodref"));
        this.jLabel6.setMaximumSize(new Dimension(50, 20));
        this.jLabel6.setMinimumSize(new Dimension(50, 20));
        this.jLabel6.setPreferredSize(new Dimension(100, 30));
        this.m_jReference1.setFont(new Font("Arial", 0, 14));
        this.m_jReference1.setPreferredSize(new Dimension(150, 30));
        this.m_jReference1.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                BundleFilter.this.m_jReference1ActionPerformed(evt);
            }
        });
        this.Enter1.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/products24.png")));
        this.Enter1.setToolTipText("Enter Product ID");
        this.Enter1.setPreferredSize(new Dimension(80, 45));
        this.Enter1.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                BundleFilter.this.Enter1ActionPerformed(evt);
            }
        });
        this.jLabel7.setFont(new Font("Arial", 0, 14));
        this.jLabel7.setText(AppLocal.getIntString("label.prodbarcode"));
        this.jLabel7.setPreferredSize(new Dimension(100, 30));
        this.m_jBarcode1.setFont(new Font("Arial", 0, 14));
        this.m_jBarcode1.setPreferredSize(new Dimension(150, 30));
        this.m_jBarcode1.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                BundleFilter.this.m_jBarcode1ActionPerformed(evt);
            }
        });
        this.Enter2.setIcon(com.openbravo.pos.plaf.SOLTECTheme.getScaledIcon(this.getClass().getResource("/com/openbravo/images/barcode.png"), 24, 24));
        this.Enter2.setToolTipText("Get Barcode");
        this.Enter2.setPreferredSize(new Dimension(80, 45));
        this.Enter2.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                BundleFilter.this.Enter2ActionPerformed(evt);
            }
        });
        this.m_jSearch.setEditable(false);
        this.m_jSearch.setFont(new Font("Arial", 0, 14));
        this.m_jSearch.setCursor(new Cursor(0));
        this.m_jSearch.setFocusable(false);
        this.m_jSearch.setPreferredSize(new Dimension(200, 30));
        this.m_jSearch.setRequestFocusEnabled(false);
        this.search.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/search24.png")));
        this.search.setToolTipText("Search Products");
        this.search.setPreferredSize(new Dimension(80, 45));
        this.search.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                BundleFilter.this.searchActionPerformed(evt);
            }
        });
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel6, -2, -1, -2).addComponent(this.jLabel7, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup().addComponent(this.m_jBarcode1, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.Enter2, -2, -1, -2)).addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup().addComponent(this.m_jReference1, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.Enter1, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.m_jSearch, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.search, -2, -1, -2))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER).addComponent(this.jLabel6, -2, -1, -2).addComponent(this.m_jReference1, -2, -1, -2).addComponent(this.Enter1, -2, -1, -2).addComponent(this.m_jSearch, -2, -1, -2).addComponent(this.search, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER).addComponent(this.jLabel7, -2, -1, -2).addComponent(this.m_jBarcode1, -2, -1, -2).addComponent(this.Enter2, -2, -1, -2)).addContainerGap()));
        this.getAccessibleContext().setAccessibleName("By product");
    }

    private void m_jReference1ActionPerformed(ActionEvent evt) {
        this.assignProductByReference();
    }

    private void searchActionPerformed(ActionEvent evt) {
        this.assignProduct(JProductFinder.showMessage(this, this.m_dlSales, 1));
    }

    private void Enter2ActionPerformed(ActionEvent evt) {
        this.assignProductByCode();
    }

    private void Enter1ActionPerformed(ActionEvent evt) {
        this.assignProductByReference();
    }

    private void m_jBarcode1ActionPerformed(ActionEvent evt) {
        this.assignProductByCode();
    }
}

