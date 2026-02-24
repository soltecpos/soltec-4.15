/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.reports;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.Datas;
import com.openbravo.data.loader.QBFCompareEnum;
import com.openbravo.data.loader.SerializerWrite;
import com.openbravo.data.loader.SerializerWriteBasic;
import com.openbravo.pos.admin.DataLogicAdmin;
import com.openbravo.pos.admin.JPeopleFinder;
import com.openbravo.pos.admin.PeopleInfo;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.reports.ReportEditorCreator;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class JParamsUser
extends JPanel
implements ReportEditorCreator {
    private DataLogicAdmin dlPeople;
    private PeopleInfo currentpeople;
    private JButton btnUser;
    private JLabel jLabel1;
    private JTextField jTextField1;

    public JParamsUser() {
        this.initComponents();
        this.jTextField1.getDocument().addDocumentListener(new DocumentListener(){

            @Override
            public void insertUpdate(DocumentEvent e) {
                JParamsUser.this.currentpeople = null;
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                JParamsUser.this.currentpeople = null;
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                JParamsUser.this.currentpeople = null;
            }
        });
    }

    @Override
    public void init(AppView app) {
        this.dlPeople = (DataLogicAdmin)app.getBean("com.openbravo.pos.admin.DataLogicAdmin");
    }

    @Override
    public void activate() throws BasicException {
        this.currentpeople = null;
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
        if (this.currentpeople == null) {
            if (this.jTextField1.getText() == null || this.jTextField1.getText().equals("")) {
                return new Object[]{QBFCompareEnum.COMP_NONE, null, QBFCompareEnum.COMP_NONE, null};
            }
            return new Object[]{QBFCompareEnum.COMP_NONE, null, QBFCompareEnum.COMP_RE, this.jTextField1.getText()};
        }
        return new Object[]{QBFCompareEnum.COMP_EQUALS, this.currentpeople.getID(), QBFCompareEnum.COMP_NONE, null};
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.jTextField1 = new JTextField();
        this.btnUser = new JButton();
        this.setFont(new Font("Arial", 0, 12));
        this.setPreferredSize(new Dimension(400, 60));
        this.jLabel1.setFont(new Font("Arial", 0, 14));
        this.jLabel1.setText(AppLocal.getIntString("label.user"));
        this.jLabel1.setPreferredSize(new Dimension(110, 30));
        this.jTextField1.setFont(new Font("Arial", 0, 14));
        this.jTextField1.setPreferredSize(new Dimension(200, 30));
        this.btnUser.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/customer_sml.png")));
        this.btnUser.setToolTipText("Get Customers");
        this.btnUser.setPreferredSize(new Dimension(80, 45));
        this.btnUser.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JParamsUser.this.btnUserActionPerformed(evt);
            }
        });
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jLabel1, -2, -1, -2).addGap(6, 6, 6).addComponent(this.jTextField1, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.btnUser, -2, -1, -2).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.btnUser, -2, -1, -2).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1, -2, -1, -2).addComponent(this.jTextField1, -2, -1, -2))).addGap(12, 12, 12)));
    }

    private void btnUserActionPerformed(ActionEvent evt) {
        JPeopleFinder finder = JPeopleFinder.getPeopleFinder(this, this.dlPeople);
        finder.search(this.currentpeople);
        finder.setVisible(true);
        this.currentpeople = finder.getSelectedPeople();
        if (this.currentpeople == null) {
            this.jTextField1.setText(null);
        } else {
            this.jTextField1.setText(this.currentpeople.getName());
        }
    }
}

