/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.ticket;

import com.openbravo.basic.BasicException;
import com.openbravo.data.gui.ComboBoxValModel;
import com.openbravo.data.gui.ListQBFModelNumber;
import com.openbravo.data.loader.QBFCompareEnum;
import com.openbravo.data.loader.SentenceList;
import com.openbravo.data.user.EditorCreator;
import com.openbravo.editor.JEditorCurrency;
import com.openbravo.editor.JEditorKeys;
import com.openbravo.editor.JEditorString;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.DataLogicSales;
import com.openbravo.pos.ticket.CategoryInfo;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LocationFilterSales
extends JPanel
implements EditorCreator {
    private SentenceList<CategoryInfo> m_sentcat;
    private ComboBoxValModel<CategoryInfo> m_CategoryModel;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JComboBox m_jCategory;
    private JComboBox m_jCboPriceBuy;
    private JComboBox m_jCboPriceSell;
    private JEditorCurrency m_jPriceBuy;
    private JEditorCurrency m_jPriceSell;
    private JEditorString m_jtxtBarCode;
    private JEditorString m_jtxtName;

    public LocationFilterSales(DataLogicSales dlSales2, JEditorKeys jKeys) {
        this.initComponents();
        this.m_sentcat = dlSales2.getCategoriesList();
        this.m_CategoryModel = new ComboBoxValModel();
        this.m_jCboPriceBuy.setModel(ListQBFModelNumber.getMandatoryNumber());
        this.m_jPriceBuy.addEditorKeys(jKeys);
        this.m_jCboPriceSell.setModel(ListQBFModelNumber.getMandatoryNumber());
        this.m_jPriceSell.addEditorKeys(jKeys);
        this.m_jtxtName.addEditorKeys(jKeys);
        this.m_jtxtBarCode.addEditorKeys(jKeys);
    }

    public void activate() {
        this.m_jtxtBarCode.reset();
        this.m_jtxtBarCode.setEditModeEnum(3);
        this.m_jtxtName.reset();
        this.m_jPriceBuy.reset();
        this.m_jPriceSell.reset();
        this.m_jtxtName.activate();
        try {
            List<CategoryInfo> catlist = this.m_sentcat.list();
            catlist.add(0, null);
            this.m_CategoryModel = new ComboBoxValModel<CategoryInfo>(catlist);
            this.m_jCategory.setModel(this.m_CategoryModel);
        }
        catch (BasicException basicException) {
            // empty catch block
        }
    }

    @Override
    public Object createValue() throws BasicException {
        Object[] afilter = new Object[10];
        if (this.m_jtxtName.getText() == null || this.m_jtxtName.getText().equals("")) {
            afilter[0] = QBFCompareEnum.COMP_NONE;
            afilter[1] = null;
        } else {
            afilter[0] = QBFCompareEnum.COMP_RE;
            afilter[1] = "%" + this.m_jtxtName.getText() + "%";
        }
        afilter[3] = this.m_jPriceBuy.getDoubleValue();
        afilter[2] = afilter[3] == null ? QBFCompareEnum.COMP_NONE : this.m_jCboPriceBuy.getSelectedItem();
        afilter[5] = this.m_jPriceSell.getDoubleValue();
        Object object = afilter[4] = afilter[5] == null ? QBFCompareEnum.COMP_NONE : this.m_jCboPriceSell.getSelectedItem();
        if (this.m_CategoryModel.getSelectedKey() == null) {
            afilter[6] = QBFCompareEnum.COMP_NONE;
            afilter[7] = null;
        } else {
            afilter[6] = QBFCompareEnum.COMP_EQUALS;
            afilter[7] = this.m_CategoryModel.getSelectedKey();
        }
        if (this.m_jtxtBarCode.getText() == null || this.m_jtxtBarCode.getText().equals("")) {
            afilter[8] = QBFCompareEnum.COMP_NONE;
            afilter[9] = null;
        } else {
            afilter[8] = QBFCompareEnum.COMP_RE;
            afilter[9] = "%" + this.m_jtxtBarCode.getText() + "%";
        }
        return afilter;
    }

    private void initComponents() {
        this.jLabel5 = new JLabel();
        this.m_jtxtName = new JEditorString();
        this.jLabel2 = new JLabel();
        this.m_jCategory = new JComboBox();
        this.jLabel4 = new JLabel();
        this.m_jCboPriceBuy = new JComboBox();
        this.m_jPriceBuy = new JEditorCurrency();
        this.jLabel3 = new JLabel();
        this.m_jCboPriceSell = new JComboBox();
        this.m_jPriceSell = new JEditorCurrency();
        this.m_jtxtBarCode = new JEditorString();
        this.jLabel1 = new JLabel();
        this.setPreferredSize(new Dimension(370, 170));
        this.jLabel5.setFont(new Font("Arial", 0, 14));
        this.jLabel5.setText(AppLocal.getIntString("label.prodname"));
        this.jLabel5.setPreferredSize(new Dimension(0, 30));
        this.m_jtxtName.setFont(new Font("Arial", 0, 14));
        this.m_jtxtName.setPreferredSize(new Dimension(0, 30));
        this.jLabel2.setFont(new Font("Arial", 0, 14));
        this.jLabel2.setText(AppLocal.getIntString("label.prodcategory"));
        this.jLabel2.setPreferredSize(new Dimension(0, 30));
        this.m_jCategory.setFont(new Font("Arial", 0, 14));
        this.m_jCategory.setPreferredSize(new Dimension(0, 30));
        this.jLabel4.setFont(new Font("Arial", 0, 14));
        this.jLabel4.setText(AppLocal.getIntString("label.prodpricebuy"));
        this.jLabel4.setPreferredSize(new Dimension(0, 30));
        this.m_jCboPriceBuy.setFont(new Font("Arial", 0, 14));
        this.m_jCboPriceBuy.setPreferredSize(new Dimension(0, 30));
        this.m_jCboPriceBuy.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                LocationFilterSales.this.m_jCboPriceBuyActionPerformed(evt);
            }
        });
        this.m_jPriceBuy.setFont(new Font("Arial", 0, 14));
        this.m_jPriceBuy.setPreferredSize(new Dimension(0, 30));
        this.jLabel3.setFont(new Font("Arial", 0, 14));
        this.jLabel3.setText(AppLocal.getIntString("label.prodpricesell"));
        this.jLabel3.setPreferredSize(new Dimension(0, 30));
        this.m_jCboPriceSell.setFont(new Font("Arial", 0, 14));
        this.m_jCboPriceSell.setPreferredSize(new Dimension(0, 30));
        this.m_jPriceSell.setFont(new Font("Arial", 0, 14));
        this.m_jPriceSell.setPreferredSize(new Dimension(0, 30));
        this.m_jtxtBarCode.setFont(new Font("Arial", 0, 14));
        this.m_jtxtBarCode.setMaximumSize(new Dimension(100, 25));
        this.m_jtxtBarCode.setPreferredSize(new Dimension(0, 30));
        this.jLabel1.setFont(new Font("Arial", 0, 14));
        this.jLabel1.setText(AppLocal.getIntString("label.prodbarcode"));
        this.jLabel1.setPreferredSize(new Dimension(0, 30));
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.jLabel3, -2, 110, -2).addGap(10, 10, 10).addComponent(this.m_jCboPriceSell, -2, 150, -2).addGap(10, 10, 10).addComponent(this.m_jPriceSell, -2, 130, -2)).addGroup(layout.createSequentialGroup().addComponent(this.jLabel1, -2, 110, -2).addGap(10, 10, 10).addComponent(this.m_jtxtBarCode, -2, 290, -2)).addGroup(layout.createSequentialGroup().addComponent(this.jLabel5, -2, 110, -2).addGap(10, 10, 10).addComponent(this.m_jtxtName, -2, 290, -2)).addGroup(layout.createSequentialGroup().addComponent(this.jLabel2, -2, 110, -2).addGap(10, 10, 10).addComponent(this.m_jCategory, -2, 260, -2)).addGroup(layout.createSequentialGroup().addComponent(this.jLabel4, -2, 110, -2).addGap(10, 10, 10).addComponent(this.m_jCboPriceBuy, -2, 150, -2).addGap(10, 10, 10).addComponent(this.m_jPriceBuy, -2, 130, -2))).addContainerGap(-1, Short.MAX_VALUE)));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap(-1, Short.MAX_VALUE).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel1, -2, -1, -2).addComponent(this.m_jtxtBarCode, -2, -1, -2)).addGap(5, 5, 5).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel5, -2, -1, -2).addComponent(this.m_jtxtName, -2, -1, -2)).addGap(5, 5, 5).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.m_jCategory, -2, -1, -2).addComponent(this.jLabel2, -2, -1, -2)).addGap(5, 5, 5).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.m_jCboPriceBuy, -2, -1, -2).addComponent(this.jLabel4, -2, -1, -2)).addComponent(this.m_jPriceBuy, -2, -1, -2)).addGap(5, 5, 5).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.m_jCboPriceSell, -2, -1, -2).addComponent(this.jLabel3, -2, -1, -2)).addComponent(this.m_jPriceSell, -2, -1, -2))));
    }

    private void m_jCboPriceBuyActionPerformed(ActionEvent evt) {
    }
}

