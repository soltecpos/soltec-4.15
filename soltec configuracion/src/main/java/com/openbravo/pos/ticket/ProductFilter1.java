/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.ticket;

import com.openbravo.basic.BasicException;
import com.openbravo.data.gui.ComboBoxValModel;
import com.openbravo.data.gui.ListQBFModelNumber;
import com.openbravo.data.loader.Datas;
import com.openbravo.data.loader.QBFCompareEnum;
import com.openbravo.data.loader.SentenceList;
import com.openbravo.data.loader.SerializerWrite;
import com.openbravo.data.loader.SerializerWriteBasic;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.forms.DataLogicSales;
import com.openbravo.pos.reports.ReportEditorCreator;
import com.openbravo.pos.ticket.CategoryInfo;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;
import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;

public class ProductFilter1
extends JPanel
implements ReportEditorCreator {
    private SentenceList<CategoryInfo> m_sentcat;
    private ComboBoxValModel<CategoryInfo> m_CategoryModel;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel5;
    private JTextField m_jBarcode;
    private JComboBox m_jCategory;
    private JComboBox m_jCboName;
    private JComboBox m_jCboUnits;
    private JTextField m_jName;
    private JTextField m_jUnits;

    public ProductFilter1() {
        this.initComponents();
    }

    @Override
    public void init(AppView app) {
        DataLogicSales dlSales2 = (DataLogicSales)app.getBean("com.openbravo.pos.forms.DataLogicSales");
        this.m_sentcat = dlSales2.getCategoriesList();
        this.m_CategoryModel = new ComboBoxValModel();
        this.m_jCboName.setModel(ListQBFModelNumber.getMandatoryString());
        this.m_jCboUnits.setModel(ListQBFModelNumber.getMandatoryString());
    }

    @Override
    public void activate() throws BasicException {
        List<CategoryInfo> catlist = this.m_sentcat.list();
        catlist.add(0, null);
        this.m_CategoryModel = new ComboBoxValModel<CategoryInfo>(catlist);
        this.m_jCategory.setModel(this.m_CategoryModel);
    }

    @Override
    public SerializerWrite getSerializerWrite() {
        return new SerializerWriteBasic(Datas.OBJECT, Datas.STRING, Datas.OBJECT, Datas.STRING, Datas.OBJECT, Datas.STRING, Datas.OBJECT, Datas.STRING);
    }

    @Override
    public Component getComponent() {
        return this;
    }

    @Override
    public Object createValue() throws BasicException {
        if (this.m_jBarcode.getText() == null || this.m_jBarcode.getText().equals("")) {
            return new Object[]{this.m_jCboName.getSelectedItem(), this.m_jName.getText(), this.m_CategoryModel.getSelectedKey() == null ? QBFCompareEnum.COMP_NONE : QBFCompareEnum.COMP_EQUALS, this.m_CategoryModel.getSelectedKey(), QBFCompareEnum.COMP_NONE, null, this.m_jCboUnits.getSelectedItem(), this.m_jUnits.getText()};
        }
        return new Object[]{QBFCompareEnum.COMP_NONE, null, QBFCompareEnum.COMP_NONE, null, QBFCompareEnum.COMP_RE, "%" + this.m_jBarcode.getText() + "%", QBFCompareEnum.COMP_NONE, null};
    }

    private void initComponents() {
        this.m_jBarcode = new JTextField();
        this.jLabel5 = new JLabel();
        this.jLabel1 = new JLabel();
        this.jLabel2 = new JLabel();
        this.m_jCategory = new JComboBox();
        this.m_jCboName = new JComboBox();
        this.m_jName = new JTextField();
        this.jLabel3 = new JLabel();
        this.m_jCboUnits = new JComboBox();
        this.m_jUnits = new JTextField();
        this.setPreferredSize(new Dimension(946, 50));
        this.m_jBarcode.setFont(new Font("Arial", 0, 14));
        this.m_jBarcode.setPreferredSize(new Dimension(150, 30));
        this.jLabel5.setFont(new Font("Arial", 0, 14));
        this.jLabel5.setText(AppLocal.getIntString("label.prodbarcode"));
        this.jLabel5.setPreferredSize(new Dimension(70, 30));
        this.jLabel1.setFont(new Font("Arial", 0, 14));
        this.jLabel1.setText(AppLocal.getIntString("label.prodcategory"));
        this.jLabel1.setPreferredSize(new Dimension(70, 30));
        this.jLabel2.setFont(new Font("Arial", 0, 14));
        this.jLabel2.setText(AppLocal.getIntString("label.prodname"));
        this.jLabel2.setPreferredSize(new Dimension(70, 30));
        this.m_jCategory.setFont(new Font("Arial", 0, 14));
        this.m_jCategory.setPreferredSize(new Dimension(150, 30));
        this.m_jCboName.setFont(new Font("Arial", 0, 14));
        this.m_jCboName.setPreferredSize(new Dimension(150, 30));
        this.m_jName.setFont(new Font("Arial", 0, 14));
        this.m_jName.setPreferredSize(new Dimension(150, 30));
        this.jLabel3.setFont(new Font("Arial", 0, 14));
        this.jLabel3.setText(AppLocal.getIntString("label.produnits"));
        this.jLabel3.setPreferredSize(new Dimension(50, 30));
        this.m_jCboUnits.setFont(new Font("Arial", 0, 14));
        this.m_jCboUnits.setPreferredSize(new Dimension(150, 30));
        this.m_jUnits.setFont(new Font("Arial", 0, 14));
        this.m_jUnits.setPreferredSize(new Dimension(50, 30));
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jLabel2, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.m_jCboName, 0, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.m_jName, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jLabel1, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.m_jCategory, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jLabel5, -1, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.m_jBarcode, -1, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jLabel3, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.m_jCboUnits, 0, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.m_jUnits, -2, -1, -2).addContainerGap(-1, Short.MAX_VALUE)));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel2, -2, -1, -2).addComponent(this.m_jCboName, -2, -1, -2).addComponent(this.m_jName, -2, -1, -2).addComponent(this.jLabel1, -2, -1, -2).addComponent(this.m_jCategory, -2, -1, -2).addComponent(this.jLabel5, -2, -1, -2).addComponent(this.m_jBarcode, -2, -1, -2).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel3, -2, -1, -2).addComponent(this.m_jCboUnits, -2, -1, -2).addComponent(this.m_jUnits, -2, -1, -2))).addContainerGap()));
    }
}

