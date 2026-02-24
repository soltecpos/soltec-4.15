/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.ticket;

import com.openbravo.basic.BasicException;
import com.openbravo.data.gui.ComboBoxValModel;
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
import javax.swing.LayoutStyle;

public class CategoryFilter_1
extends JPanel
implements ReportEditorCreator {
    private SentenceList<CategoryInfo> m_sentcat;
    private ComboBoxValModel<CategoryInfo> m_CategoryModel;
    private JLabel jLabel1;
    private JComboBox m_jCategory;

    public CategoryFilter_1() {
        this.initComponents();
    }

    @Override
    public void init(AppView app) {
        DataLogicSales dlSales2 = (DataLogicSales)app.getBean("com.openbravo.pos.forms.DataLogicSales");
        this.m_sentcat = dlSales2.getCategoriesList_1();
        this.m_CategoryModel = new ComboBoxValModel();
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
        return new SerializerWriteBasic(Datas.OBJECT, Datas.STRING);
    }

    @Override
    public Component getComponent() {
        return this;
    }

    @Override
    public Object createValue() throws BasicException {
        return new Object[]{this.m_CategoryModel.getSelectedKey() == null ? QBFCompareEnum.COMP_NONE : QBFCompareEnum.COMP_EQUALS, this.m_CategoryModel.getSelectedKey(), QBFCompareEnum.COMP_NONE, null};
    }

    private void initComponents() {
        this.m_jCategory = new JComboBox();
        this.jLabel1 = new JLabel();
        this.m_jCategory.setFont(new Font("Arial", 0, 14));
        this.m_jCategory.setPreferredSize(new Dimension(200, 30));
        this.jLabel1.setFont(new Font("Arial", 0, 14));
        this.jLabel1.setText(AppLocal.getIntString("label.prodcategory"));
        this.jLabel1.setPreferredSize(new Dimension(150, 30));
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jLabel1, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.m_jCategory, -2, -1, -2).addGap(36, 36, 36)));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1, -2, -1, -2).addComponent(this.m_jCategory, -2, -1, -2)).addContainerGap(29, Short.MAX_VALUE)));
    }
}

