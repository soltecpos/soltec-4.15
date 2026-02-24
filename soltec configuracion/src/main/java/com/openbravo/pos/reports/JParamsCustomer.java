/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.reports;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.Datas;
import com.openbravo.data.loader.QBFCompareEnum;
import com.openbravo.data.loader.SerializerWrite;
import com.openbravo.data.loader.SerializerWriteBasic;
import com.openbravo.pos.customers.CustomerInfo;
import com.openbravo.pos.customers.DataLogicCustomers;
import com.openbravo.pos.customers.JCustomerFinder;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.reports.ReportEditorCreator;
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

public class JParamsCustomer
extends JPanel
implements ReportEditorCreator {
    private DataLogicCustomers dlCustomers;
    private CustomerInfo currentcustomer;
    private JButton btnCustomer;
    private JLabel jLabel2;
    private JTextField jTextField1;

    public JParamsCustomer() {
        this.initComponents();
        this.jTextField1.getDocument().addDocumentListener(new DocumentListener(){

            @Override
            public void insertUpdate(DocumentEvent e) {
                JParamsCustomer.this.currentcustomer = null;
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                JParamsCustomer.this.currentcustomer = null;
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                JParamsCustomer.this.currentcustomer = null;
            }
        });
    }

    @Override
    public void init(AppView app) {
        this.dlCustomers = (DataLogicCustomers)app.getBean("com.openbravo.pos.customers.DataLogicCustomers");
    }

    @Override
    public void activate() throws BasicException {
        this.currentcustomer = null;
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
        if (this.currentcustomer == null) {
            if (this.jTextField1.getText() == null || this.jTextField1.getText().equals("")) {
                return new Object[]{QBFCompareEnum.COMP_NONE, null, QBFCompareEnum.COMP_NONE, null};
            }
            return new Object[]{QBFCompareEnum.COMP_NONE, null, QBFCompareEnum.COMP_RE, this.jTextField1.getText()};
        }
        return new Object[]{QBFCompareEnum.COMP_EQUALS, this.currentcustomer.getId(), QBFCompareEnum.COMP_NONE, null};
    }

    private void initComponents() {
        this.jTextField1 = new JTextField();
        this.btnCustomer = new JButton();
        this.jLabel2 = new JLabel();
        this.setFont(new Font("Arial", 0, 14));
        this.setPreferredSize(new Dimension(400, 60));
        this.jTextField1.setFont(new Font("Arial", 0, 14));
        this.jTextField1.setPreferredSize(new Dimension(250, 30));
        this.btnCustomer.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/customer_sml.png")));
        this.btnCustomer.setToolTipText("Get Customers");
        this.btnCustomer.setPreferredSize(new Dimension(80, 45));
        this.btnCustomer.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JParamsCustomer.this.btnCustomerActionPerformed(evt);
            }
        });
        this.jLabel2.setFont(new Font("Arial", 0, 14));
        this.jLabel2.setText(AppLocal.getIntString("label.customer"));
        this.jLabel2.setPreferredSize(new Dimension(110, 30));
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jLabel2, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jTextField1, -2, 300, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.btnCustomer, -2, -1, -2).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.btnCustomer, -2, -1, -2).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jTextField1, -2, -1, -2).addComponent(this.jLabel2, -2, -1, -2))).addGap(32, 32, 32)));
        ResourceBundle bundle = ResourceBundle.getBundle("pos_messages");
        this.getAccessibleContext().setAccessibleName(bundle.getString("label.bycustomer"));
    }

    private void btnCustomerActionPerformed(ActionEvent evt) {
        JCustomerFinder finder = JCustomerFinder.getCustomerFinder(this, this.dlCustomers);
        finder.search(this.currentcustomer);
        finder.setVisible(true);
        this.currentcustomer = finder.getSelectedCustomer();
        if (this.currentcustomer == null) {
            this.jTextField1.setText(null);
        } else {
            this.jTextField1.setText(this.currentcustomer.getName());
        }
    }
}

