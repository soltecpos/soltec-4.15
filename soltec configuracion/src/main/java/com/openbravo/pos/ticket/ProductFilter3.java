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
import com.openbravo.format.Formats;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.forms.DataLogicSales;
import com.openbravo.pos.reports.ReportEditorCreator;
import com.openbravo.pos.suppliers.DataLogicSuppliers;
import com.openbravo.pos.suppliers.SupplierInfo;
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

public class ProductFilter3
extends JPanel
implements ReportEditorCreator {
    private SentenceList<CategoryInfo> m_sentcat;
    private ComboBoxValModel<CategoryInfo> m_CategoryModel;
    private SentenceList<SupplierInfo> m_sentsup;
    private ComboBoxValModel<SupplierInfo> m_SupplierModel;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JTextField m_jBarcode;
    private JComboBox m_jCategory;
    private JComboBox m_jCboName;
    private JComboBox m_jCboPriceBuy;
    private JComboBox m_jCboPriceSell;
    private JTextField m_jName;
    private JTextField m_jPriceBuy;
    private JTextField m_jPriceSell;
    private JComboBox m_jSupplier;

    public ProductFilter3() {
        this.initComponents();
    }

    @Override
    public void init(AppView app) {
        DataLogicSales dlSales2 = (DataLogicSales)app.getBean("com.openbravo.pos.forms.DataLogicSales");
        DataLogicSuppliers dlSuppliers = (DataLogicSuppliers)app.getBean("com.openbravo.pos.suppliers.DataLogicSuppliers");
        this.m_sentcat = dlSales2.getCategoriesList();
        this.m_CategoryModel = new ComboBoxValModel();
        this.m_sentsup = dlSuppliers.getSupplierList();
        this.m_SupplierModel = new ComboBoxValModel();
        this.m_jCboName.setModel(ListQBFModelNumber.getMandatoryString());
        this.m_jCboPriceBuy.setModel(ListQBFModelNumber.getMandatoryNumber());
        this.m_jCboPriceSell.setModel(ListQBFModelNumber.getMandatoryNumber());
    }

    @Override
    public void activate() throws BasicException {
        List<CategoryInfo> catlist = this.m_sentcat.list();
        catlist.add(0, null);
        this.m_CategoryModel = new ComboBoxValModel<CategoryInfo>(catlist);
        this.m_jCategory.setModel(this.m_CategoryModel);
        List<SupplierInfo> suplist = this.m_sentsup.list();
        suplist.add(0, null);
        this.m_SupplierModel = new ComboBoxValModel<SupplierInfo>(suplist);
        this.m_jSupplier.setModel(this.m_SupplierModel);
    }

    @Override
    public SerializerWrite getSerializerWrite() {
        return new SerializerWriteBasic(Datas.OBJECT, Datas.STRING, Datas.OBJECT, Datas.DOUBLE, Datas.OBJECT, Datas.DOUBLE, Datas.OBJECT, Datas.STRING, Datas.OBJECT, Datas.STRING, Datas.OBJECT, Datas.STRING);
    }

    @Override
    public Component getComponent() {
        return this;
    }

    @Override
    public Object createValue() throws BasicException {
        if (this.m_jBarcode.getText() == null || this.m_jBarcode.getText().equals("")) {
            return new Object[]{this.m_jCboName.getSelectedItem(), this.m_jName.getText(), this.m_jCboPriceBuy.getSelectedItem(), Formats.CURRENCY.parseValue(this.m_jPriceBuy.getText()), this.m_jCboPriceSell.getSelectedItem(), Formats.CURRENCY.parseValue(this.m_jPriceSell.getText()), this.m_CategoryModel.getSelectedKey() == null ? QBFCompareEnum.COMP_NONE : QBFCompareEnum.COMP_EQUALS, this.m_CategoryModel.getSelectedKey(), this.m_SupplierModel.getSelectedKey() == null ? QBFCompareEnum.COMP_NONE : QBFCompareEnum.COMP_EQUALS, this.m_SupplierModel.getSelectedKey(), QBFCompareEnum.COMP_NONE, null};
        }
        return new Object[]{QBFCompareEnum.COMP_NONE, null, QBFCompareEnum.COMP_NONE, null, QBFCompareEnum.COMP_NONE, null, QBFCompareEnum.COMP_NONE, null, QBFCompareEnum.COMP_NONE, null, QBFCompareEnum.COMP_EQUALS, this.m_jBarcode.getText()};
    }

    private void initComponents() {
        this.m_jBarcode = new JTextField();
        this.jLabel5 = new JLabel();
        this.jLabel1 = new JLabel();
        this.jLabel2 = new JLabel();
        this.m_jCategory = new JComboBox();
        this.m_jCboName = new JComboBox();
        this.m_jName = new JTextField();
        this.jLabel4 = new JLabel();
        this.jLabel3 = new JLabel();
        this.m_jCboPriceBuy = new JComboBox();
        this.m_jCboPriceSell = new JComboBox();
        this.m_jPriceBuy = new JTextField();
        this.m_jPriceSell = new JTextField();
        this.jLabel6 = new JLabel();
        this.m_jSupplier = new JComboBox();
        this.setPreferredSize(new Dimension(976, 100));
        this.m_jBarcode.setFont(new Font("Arial", 0, 14));
        this.m_jBarcode.setPreferredSize(new Dimension(150, 30));
        this.jLabel5.setFont(new Font("Arial", 0, 14));
        this.jLabel5.setText(AppLocal.getIntString("label.prodbarcode"));
        this.jLabel5.setPreferredSize(new Dimension(100, 30));
        this.jLabel1.setFont(new Font("Arial", 0, 14));
        this.jLabel1.setText(AppLocal.getIntString("label.prodcategory"));
        this.jLabel1.setPreferredSize(new Dimension(100, 30));
        this.jLabel2.setFont(new Font("Arial", 0, 14));
        this.jLabel2.setText(AppLocal.getIntString("label.prodname"));
        this.jLabel2.setPreferredSize(new Dimension(100, 30));
        this.m_jCategory.setFont(new Font("Arial", 0, 14));
        this.m_jCategory.setPreferredSize(new Dimension(150, 30));
        this.m_jCboName.setFont(new Font("Arial", 0, 14));
        this.m_jCboName.setPreferredSize(new Dimension(150, 30));
        this.m_jName.setFont(new Font("Arial", 0, 14));
        this.m_jName.setPreferredSize(new Dimension(150, 30));
        this.jLabel4.setFont(new Font("Arial", 0, 14));
        this.jLabel4.setText(AppLocal.getIntString("label.prodpricebuy"));
        this.jLabel4.setPreferredSize(new Dimension(100, 30));
        this.jLabel3.setFont(new Font("Arial", 0, 14));
        this.jLabel3.setText(AppLocal.getIntString("label.prodpricesell"));
        this.jLabel3.setPreferredSize(new Dimension(100, 30));
        this.m_jCboPriceBuy.setFont(new Font("Arial", 0, 14));
        this.m_jCboPriceBuy.setPreferredSize(new Dimension(150, 30));
        this.m_jCboPriceSell.setFont(new Font("Arial", 0, 14));
        this.m_jCboPriceSell.setPreferredSize(new Dimension(150, 30));
        this.m_jPriceBuy.setFont(new Font("Arial", 0, 14));
        this.m_jPriceBuy.setPreferredSize(new Dimension(60, 30));
        this.m_jPriceSell.setFont(new Font("Arial", 0, 14));
        this.m_jPriceSell.setPreferredSize(new Dimension(60, 30));
        this.jLabel6.setFont(new Font("Arial", 0, 14));
        this.jLabel6.setText(AppLocal.getIntString("label.prodsupplier"));
        this.jLabel6.setPreferredSize(new Dimension(100, 30));
        this.m_jSupplier.setFont(new Font("Arial", 0, 14));
        this.m_jSupplier.setPreferredSize(new Dimension(150, 30));
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addGroup(layout.createSequentialGroup().addComponent(this.jLabel2, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.m_jCboName, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.m_jName, -1, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jLabel1, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.m_jCategory, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jLabel6, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.m_jSupplier, -2, -1, -2)).addGroup(layout.createSequentialGroup().addComponent(this.jLabel5, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.m_jBarcode, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addComponent(this.jLabel4, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.m_jCboPriceBuy, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.m_jPriceBuy, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jLabel3, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.m_jCboPriceSell, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.m_jPriceSell, -2, -1, -2))).addContainerGap(40, Short.MAX_VALUE)));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel6, -2, -1, -2).addComponent(this.m_jSupplier, -2, -1, -2)).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel2, -2, -1, -2).addComponent(this.m_jCboName, -2, -1, -2).addComponent(this.m_jName, -2, -1, -2).addComponent(this.jLabel1, -2, -1, -2).addComponent(this.m_jCategory, -2, -1, -2))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel5, -2, -1, -2).addComponent(this.m_jBarcode, -2, -1, -2)).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel4, -2, -1, -2).addComponent(this.m_jCboPriceBuy, -2, -1, -2).addComponent(this.m_jPriceBuy, -2, -1, -2).addComponent(this.jLabel3, -2, -1, -2).addComponent(this.m_jCboPriceSell, -2, -1, -2).addComponent(this.m_jPriceSell, -2, -1, -2))).addContainerGap()));
    }
}

