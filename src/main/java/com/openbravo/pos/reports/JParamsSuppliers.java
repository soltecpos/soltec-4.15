/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.reports;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.Datas;
import com.openbravo.data.loader.QBFCompareEnum;
import com.openbravo.data.loader.SerializerWrite;
import com.openbravo.data.loader.SerializerWriteBasic;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.reports.ReportEditorCreator;
import com.openbravo.pos.suppliers.DataLogicSuppliers;
import com.openbravo.pos.suppliers.JSupplierFinder;
import com.openbravo.pos.suppliers.SupplierInfo;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class JParamsSuppliers
extends JPanel
implements ReportEditorCreator {
    private DataLogicSuppliers dlSuppliers;
    private SupplierInfo currentsupplier;
    private JButton btnSupplier;
    private JLabel jLabel2;
    private JTextField jTextField1;

    public JParamsSuppliers() {
        this.initComponents();
        this.jTextField1.getDocument().addDocumentListener(new DocumentListener(){

            @Override
            public void insertUpdate(DocumentEvent e) {
                JParamsSuppliers.this.currentsupplier = null;
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                JParamsSuppliers.this.currentsupplier = null;
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                JParamsSuppliers.this.currentsupplier = null;
            }
        });
    }

    @Override
    public void init(AppView app) {
        this.dlSuppliers = (DataLogicSuppliers)app.getBean("com.openbravo.pos.suppliers.DataLogicSuppliers");
    }

    @Override
    public void activate() throws BasicException {
        this.currentsupplier = null;
        this.jTextField1.setText(null);
    }

    @Override
    public SerializerWrite getSerializerWrite() {
        return new SerializerWriteBasic(Datas.OBJECT, Datas.STRING, Datas.OBJECT, Datas.STRING);
    }

    @Override
    public Component getComponent() {
        return this;
    }

    @Override
    public Object createValue() throws BasicException {
        if (this.currentsupplier == null) {
            if (this.jTextField1.getText() == null || this.jTextField1.getText().equals("")) {
                return new Object[]{QBFCompareEnum.COMP_NONE, null, QBFCompareEnum.COMP_NONE, null};
            }
            return new Object[]{QBFCompareEnum.COMP_NONE, null, QBFCompareEnum.COMP_RE, this.jTextField1.getText()};
        }
        return new Object[]{QBFCompareEnum.COMP_EQUALS, this.currentsupplier.getID(), QBFCompareEnum.COMP_NONE, null};
    }

    private void initComponents() {
        this.jTextField1 = new JTextField();
        this.btnSupplier = new JButton();
        this.jLabel2 = new JLabel();
        this.setFont(new Font("Arial", 0, 14));
        this.setPreferredSize(new Dimension(400, 50));
        this.jTextField1.setFont(new Font("Arial", 0, 14));
        this.jTextField1.setPreferredSize(new Dimension(300, 30));
        this.btnSupplier.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/customer_sml.png")));
        this.btnSupplier.setToolTipText("Get Suppliers");
        this.btnSupplier.setPreferredSize(new Dimension(80, 45));
        this.btnSupplier.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JParamsSuppliers.this.btnSupplierActionPerformed(evt);
            }
        });
        this.jLabel2.setFont(new Font("Arial", 0, 14));
        this.jLabel2.setText(AppLocal.getIntString("label.supplier"));
        this.jLabel2.setPreferredSize(new Dimension(100, 30));
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jLabel2, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jTextField1, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.btnSupplier, -2, -1, -2).addContainerGap(-1, Short.MAX_VALUE)));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jTextField1, -2, -1, -2).addComponent(this.jLabel2, -2, -1, -2))).addComponent(this.btnSupplier, -2, -1, -2)).addContainerGap()));
        ResourceBundle bundle = ResourceBundle.getBundle("pos_messages");
        this.getAccessibleContext().setAccessibleName(bundle.getString("label.bycustomer"));
    }

    private void btnSupplierActionPerformed(ActionEvent evt) {
        JSupplierFinder finder = JSupplierFinder.getSupplierFinder(this, this.dlSuppliers);
        finder.search(this.currentsupplier);
        finder.setVisible(true);
        this.currentsupplier = finder.getSelectedSupplier();
        if (this.currentsupplier == null) {
            this.jTextField1.setText(null);
        } else {
            this.jTextField1.setText(this.currentsupplier.getName());
        }
    }
}

