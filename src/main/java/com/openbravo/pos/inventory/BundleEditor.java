/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.inventory;

import com.openbravo.basic.BasicException;
import com.openbravo.data.gui.MessageInf;
import com.openbravo.data.user.DirtyManager;
import com.openbravo.data.user.EditorRecord;
import com.openbravo.format.Formats;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.forms.DataLogicSales;
import com.openbravo.pos.panels.JProductFinder;
import com.openbravo.pos.ticket.ProductInfoExt;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.UUID;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;

public class BundleEditor
extends JPanel
implements EditorRecord {
    private DataLogicSales m_dlSales;
    private Object id;
    private Object product;
    private Object productBundle;
    private Object name;
    private Object quantity;
    private Object insertproduct;
    private JLabel jLabel1;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JTextField m_jBarcode;
    private JButton m_jEnter1;
    private JButton m_jEnter2;
    private JTextField m_jProduct;
    private JTextField m_jQuantity;
    private JTextField m_jReference;
    private JButton m_jSearch;

    public BundleEditor(AppView app, DirtyManager dirty) {
        this.m_dlSales = (DataLogicSales)app.getBean("com.openbravo.pos.forms.DataLogicSales");
        this.initComponents();
        this.m_jProduct.getDocument().addDocumentListener(dirty);
        this.m_jQuantity.getDocument().addDocumentListener(dirty);
    }

    public void setInsertProduct(ProductInfoExt prod) {
        this.insertproduct = prod == null ? null : prod.getID();
    }

    @Override
    public void refresh() {
    }

    @Override
    public void writeValueEOF() {
        this.id = null;
        this.product = null;
        this.productBundle = null;
        this.quantity = null;
        this.name = null;
        this.m_jReference.setText(null);
        this.m_jBarcode.setText(null);
        this.m_jProduct.setText(null);
        this.m_jQuantity.setText(null);
        this.m_jReference.setEnabled(false);
        this.m_jBarcode.setEnabled(false);
        this.m_jProduct.setEnabled(false);
        this.m_jQuantity.setEnabled(false);
        this.m_jEnter1.setEnabled(false);
        this.m_jEnter2.setEnabled(false);
        this.m_jSearch.setEnabled(false);
    }

    @Override
    public void writeValueInsert() {
        this.id = UUID.randomUUID().toString();
        this.product = this.insertproduct;
        this.productBundle = null;
        this.name = null;
        this.quantity = null;
        this.m_jReference.setText(null);
        this.m_jBarcode.setText(null);
        this.m_jProduct.setText(null);
        this.m_jQuantity.setText(null);
        this.m_jReference.setEnabled(true);
        this.m_jBarcode.setEnabled(true);
        this.m_jProduct.setEnabled(true);
        this.m_jQuantity.setEnabled(true);
        this.m_jEnter1.setEnabled(true);
        this.m_jEnter2.setEnabled(true);
        this.m_jSearch.setEnabled(true);
    }

    @Override
    public void writeValueEdit(Object value) {
        Object[] obj = (Object[])value;
        this.id = obj[0];
        this.product = obj[1];
        this.productBundle = obj[2];
        this.quantity = obj[3];
        this.name = obj[6];
        this.m_jReference.setText(Formats.STRING.formatValue(obj[4]));
        this.m_jBarcode.setText(Formats.STRING.formatValue(obj[5]));
        this.m_jProduct.setText(Formats.STRING.formatValue(obj[4]) + " - " + Formats.STRING.formatValue(obj[6]));
        this.m_jQuantity.setText(Formats.DOUBLE.formatValue(obj[3]));
        this.m_jReference.setEnabled(true);
        this.m_jBarcode.setEnabled(true);
        this.m_jProduct.setEnabled(true);
        this.m_jQuantity.setEnabled(true);
        this.m_jEnter1.setEnabled(true);
        this.m_jEnter2.setEnabled(true);
        this.m_jSearch.setEnabled(true);
    }

    @Override
    public void writeValueDelete(Object value) {
        Object[] obj = (Object[])value;
        this.id = obj[0];
        this.product = obj[1];
        this.productBundle = obj[2];
        this.quantity = obj[3];
        this.name = obj[6];
        this.m_jReference.setText(Formats.STRING.formatValue(obj[4]));
        this.m_jBarcode.setText(Formats.STRING.formatValue(obj[5]));
        this.m_jProduct.setText(Formats.STRING.formatValue(obj[4]) + " - " + Formats.STRING.formatValue(obj[6]));
        this.m_jQuantity.setText(Formats.DOUBLE.formatValue(obj[3]));
        this.m_jReference.setEnabled(false);
        this.m_jBarcode.setEnabled(false);
        this.m_jProduct.setEnabled(false);
        this.m_jEnter1.setEnabled(false);
        this.m_jEnter2.setEnabled(false);
        this.m_jSearch.setEnabled(false);
    }

    @Override
    public Object createValue() throws BasicException {
        return new Object[]{this.id, this.product, this.productBundle, Formats.DOUBLE.parseValue(this.m_jQuantity.getText()), this.m_jReference.getText(), this.m_jBarcode.getText(), this.name};
    }

    @Override
    public Component getComponent() {
        return this;
    }

    private void assignProduct(ProductInfoExt prod) {
        if (this.m_jSearch.isEnabled()) {
            if (prod == null) {
                this.productBundle = null;
                this.quantity = null;
                this.m_jReference.setText(null);
                this.m_jBarcode.setText(null);
                this.m_jProduct.setText(null);
                this.name = null;
            } else {
                this.productBundle = prod.getID();
                this.quantity = null;
                this.m_jReference.setText(prod.getReference());
                this.m_jBarcode.setText(prod.getCode());
                this.m_jProduct.setText(prod.getReference() + " - " + prod.getName());
                this.name = prod.getName();
            }
        }
    }

    private void assignProductByCode() {
        try {
            ProductInfoExt prod = this.m_dlSales.getProductInfoByCode(this.m_jBarcode.getText());
            this.assignProduct(prod);
            if (prod == null) {
                Toolkit.getDefaultToolkit().beep();
            }
        }
        catch (BasicException eData) {
            this.assignProduct(null);
            MessageInf msg = new MessageInf(eData);
            msg.show(this);
        }
    }

    private void assignProductByReference() {
        try {
            ProductInfoExt prod = this.m_dlSales.getProductInfoByReference(this.m_jReference.getText());
            this.assignProduct(prod);
            if (prod == null) {
                Toolkit.getDefaultToolkit().beep();
            }
        }
        catch (BasicException eData) {
            this.assignProduct(null);
            MessageInf msg = new MessageInf(eData);
            msg.show(this);
        }
    }

    private void initComponents() {
        this.jLabel3 = new JLabel();
        this.m_jReference = new JTextField();
        this.m_jEnter1 = new JButton();
        this.m_jEnter2 = new JButton();
        this.m_jSearch = new JButton();
        this.m_jProduct = new JTextField();
        this.m_jBarcode = new JTextField();
        this.jLabel4 = new JLabel();
        this.jLabel1 = new JLabel();
        this.m_jQuantity = new JTextField();
        this.setPreferredSize(new Dimension(700, 120));
        this.jLabel3.setFont(new Font("Arial", 0, 14));
        this.jLabel3.setText(AppLocal.getIntString("label.prodref"));
        this.jLabel3.setPreferredSize(new Dimension(100, 30));
        this.m_jReference.setFont(new Font("Arial", 0, 14));
        this.m_jReference.setPreferredSize(new Dimension(150, 30));
        this.m_jReference.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                BundleEditor.this.m_jReferenceActionPerformed(evt);
            }
        });
        this.m_jEnter1.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/ok.png")));
        this.m_jEnter1.setPreferredSize(new Dimension(80, 45));
        this.m_jEnter1.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                BundleEditor.this.m_jEnter1ActionPerformed(evt);
            }
        });
        this.m_jEnter2.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/barcode.png")));
        this.m_jEnter2.setPreferredSize(new Dimension(80, 45));
        this.m_jEnter2.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                BundleEditor.this.m_jEnter2ActionPerformed(evt);
            }
        });
        this.m_jSearch.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/search24.png")));
        this.m_jSearch.setPreferredSize(new Dimension(80, 45));
        this.m_jSearch.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                BundleEditor.this.m_jSearchActionPerformed(evt);
            }
        });
        this.m_jProduct.setEditable(false);
        this.m_jProduct.setPreferredSize(new Dimension(200, 30));
        this.m_jProduct.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                BundleEditor.this.m_jProductActionPerformed(evt);
            }
        });
        this.m_jBarcode.setFont(new Font("Arial", 0, 14));
        this.m_jBarcode.setPreferredSize(new Dimension(150, 30));
        this.m_jBarcode.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                BundleEditor.this.m_jBarcodeActionPerformed(evt);
            }
        });
        this.jLabel4.setFont(new Font("Arial", 0, 14));
        this.jLabel4.setText(AppLocal.getIntString("label.prodbarcode"));
        this.jLabel4.setPreferredSize(new Dimension(100, 30));
        this.jLabel1.setFont(new Font("Arial", 0, 14));
        this.jLabel1.setText("Quantity");
        this.jLabel1.setPreferredSize(new Dimension(110, 30));
        this.m_jQuantity.setFont(new Font("Arial", 0, 14));
        this.m_jQuantity.setPreferredSize(new Dimension(100, 30));
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.jLabel4, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.m_jBarcode, -2, -1, -2)).addGroup(layout.createSequentialGroup().addComponent(this.jLabel3, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.m_jReference, -2, -1, -2))).addGap(10, 10, 10).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.m_jEnter1, -1, -1, Short.MAX_VALUE).addComponent(this.m_jEnter2, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addGroup(layout.createSequentialGroup().addComponent(this.m_jQuantity, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jLabel1, -2, 0, Short.MAX_VALUE)).addComponent(this.m_jProduct, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.m_jSearch, -2, -1, -2).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER).addComponent(this.jLabel3, -2, -1, -2).addComponent(this.m_jReference, -2, -1, -2).addComponent(this.m_jEnter1, -2, -1, -2).addComponent(this.m_jProduct, -2, -1, -2).addComponent(this.m_jSearch, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER).addComponent(this.m_jBarcode, -2, -1, -2).addComponent(this.jLabel4, -2, -1, -2).addComponent(this.m_jEnter2, -2, -1, -2).addComponent(this.m_jQuantity, -2, -1, -2).addComponent(this.jLabel1, -2, -1, -2)).addContainerGap()));
    }

    private void m_jSearchActionPerformed(ActionEvent evt) {
        this.assignProduct(JProductFinder.showMessage(this, this.m_dlSales, 3));
    }

    private void m_jReferenceActionPerformed(ActionEvent evt) {
        this.assignProductByReference();
    }

    private void m_jEnter2ActionPerformed(ActionEvent evt) {
        this.assignProductByCode();
    }

    private void m_jEnter1ActionPerformed(ActionEvent evt) {
        this.assignProductByReference();
    }

    private void m_jBarcodeActionPerformed(ActionEvent evt) {
        this.assignProductByCode();
    }

    private void m_jProductActionPerformed(ActionEvent evt) {
    }
}

