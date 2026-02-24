/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.inventory;

import com.openbravo.basic.BasicException;
import com.openbravo.data.gui.ComboBoxValModel;
import com.openbravo.data.gui.MessageInf;
import com.openbravo.data.loader.SentenceList;
import com.openbravo.data.user.DirtyManager;
import com.openbravo.data.user.EditorRecord;
import com.openbravo.format.Formats;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.forms.DataLogicSales;
import com.openbravo.pos.inventory.TaxCategoryInfo;
import com.openbravo.pos.inventory.TaxCustCategoryInfo;
import com.openbravo.pos.ticket.TaxInfo;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.swing.GroupLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;

public class TaxEditor
extends JPanel
implements EditorRecord {
    private static final long serialVersionUID = 1L;
    private Object m_oId;
    private SentenceList<TaxCategoryInfo> taxcatsent;
    private ComboBoxValModel<TaxCategoryInfo> taxcatmodel;
    private SentenceList<TaxCustCategoryInfo> taxcustcatsent;
    private ComboBoxValModel<TaxCustCategoryInfo> taxcustcatmodel;
    private SentenceList<TaxInfo> taxparentsent;
    private ComboBoxValModel<TaxInfo> taxparentmodel;
    private JCheckBox jCascade;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JTextField jOrder;
    private JComboBox<TaxCustCategoryInfo> m_jCustTaxCategory;
    private JTextField m_jName;
    private JTextField m_jRate;
    private JComboBox<TaxCategoryInfo> m_jTaxCategory;
    private JComboBox<TaxInfo> m_jTaxParent;

    public TaxEditor(AppView app, DirtyManager dirty) {
        DataLogicSales dlSales2 = (DataLogicSales)app.getBean("com.openbravo.pos.forms.DataLogicSales");
        this.initComponents();
        this.taxcatsent = dlSales2.getTaxCategoriesList();
        this.taxcatmodel = new ComboBoxValModel();
        this.taxcustcatsent = dlSales2.getTaxCustCategoriesList();
        this.taxcustcatmodel = new ComboBoxValModel();
        this.taxparentsent = dlSales2.getTaxList();
        this.taxparentmodel = new ComboBoxValModel();
        this.m_jName.getDocument().addDocumentListener(dirty);
        this.m_jTaxCategory.addActionListener(dirty);
        this.m_jCustTaxCategory.addActionListener(dirty);
        this.m_jTaxParent.addActionListener(dirty);
        this.m_jRate.getDocument().addDocumentListener(dirty);
        this.jCascade.addActionListener(dirty);
        this.jOrder.getDocument().addDocumentListener(dirty);
        this.writeValueEOF();
    }

    public void activate() throws BasicException {
        List<TaxCategoryInfo> a = this.taxcatsent.list();
        this.taxcatmodel = new ComboBoxValModel<TaxCategoryInfo>(a);
        this.m_jTaxCategory.setModel(this.taxcatmodel);
        List<TaxCustCategoryInfo> b = this.taxcustcatsent.list();
        b.add(0, null);
        this.taxcustcatmodel = new ComboBoxValModel<TaxCustCategoryInfo>(b);
        this.m_jCustTaxCategory.setModel(this.taxcustcatmodel);
    }

    @Override
    public void refresh() {
        List<TaxInfo> a;
        try {
            a = this.taxparentsent.list();
        }
        catch (BasicException eD) {
            MessageInf msg = new MessageInf(-67108864, AppLocal.getIntString("message.cannotloadlists"), eD);
            msg.show(this);
            a = new ArrayList<TaxInfo>();
        }
        a.add(0, null);
        this.taxparentmodel = new ComboBoxValModel<TaxInfo>(a);
        this.m_jTaxParent.setModel(this.taxparentmodel);
    }

    @Override
    public void writeValueEOF() {
        this.m_oId = null;
        this.m_jName.setText(null);
        this.taxcatmodel.setSelectedKey(null);
        this.taxcustcatmodel.setSelectedKey(null);
        this.taxparentmodel.setSelectedKey(null);
        this.m_jRate.setText(null);
        this.jCascade.setSelected(false);
        this.jOrder.setText(null);
        this.m_jName.setEnabled(false);
        this.m_jTaxCategory.setEnabled(false);
        this.m_jCustTaxCategory.setEnabled(false);
        this.m_jTaxParent.setEnabled(false);
        this.m_jRate.setEnabled(false);
        this.jCascade.setEnabled(false);
        this.jOrder.setEnabled(false);
    }

    @Override
    public void writeValueInsert() {
        this.m_oId = UUID.randomUUID().toString();
        this.m_jName.setText(null);
        this.taxcatmodel.setSelectedKey(null);
        this.taxcustcatmodel.setSelectedKey(null);
        this.taxparentmodel.setSelectedKey(null);
        this.m_jRate.setText(null);
        this.jCascade.setSelected(false);
        this.jOrder.setText(null);
        this.m_jName.setEnabled(true);
        this.m_jTaxCategory.setEnabled(true);
        this.m_jCustTaxCategory.setEnabled(true);
        this.m_jTaxParent.setEnabled(true);
        this.m_jRate.setEnabled(true);
        this.jCascade.setEnabled(true);
        this.jOrder.setEnabled(true);
    }

    @Override
    public void writeValueDelete(Object value) {
        Object[] tax = (Object[])value;
        this.m_oId = tax[0];
        this.m_jName.setText(Formats.STRING.formatValue(tax[1]));
        this.taxcatmodel.setSelectedKey(tax[2]);
        this.taxcustcatmodel.setSelectedKey(tax[3]);
        this.taxparentmodel.setSelectedKey(tax[4]);
        this.m_jRate.setText(Formats.PERCENT.formatValue(tax[5]));
        this.jCascade.setSelected((Boolean)tax[6]);
        this.jOrder.setText(Formats.INT.formatValue(tax[7]));
        this.m_jName.setEnabled(false);
        this.m_jTaxCategory.setEnabled(false);
        this.m_jCustTaxCategory.setEnabled(false);
        this.m_jTaxParent.setEnabled(false);
        this.m_jRate.setEnabled(false);
        this.jCascade.setEnabled(false);
        this.jOrder.setEnabled(false);
    }

    @Override
    public void writeValueEdit(Object value) {
        Object[] tax = (Object[])value;
        this.m_oId = tax[0];
        this.m_jName.setText(Formats.STRING.formatValue(tax[1]));
        this.taxcatmodel.setSelectedKey(tax[2]);
        this.taxcustcatmodel.setSelectedKey(tax[3]);
        this.taxparentmodel.setSelectedKey(tax[4]);
        this.m_jRate.setText(Formats.PERCENT.formatValue(tax[5]));
        this.jCascade.setSelected((Boolean)tax[6]);
        this.jOrder.setText(Formats.INT.formatValue(tax[7]));
        this.m_jName.setEnabled(true);
        this.m_jTaxCategory.setEnabled(true);
        this.m_jCustTaxCategory.setEnabled(true);
        this.m_jTaxParent.setEnabled(true);
        this.m_jRate.setEnabled(true);
        this.jCascade.setEnabled(true);
        this.jOrder.setEnabled(true);
    }

    @Override
    public Object createValue() throws BasicException {
        Object[] tax = new Object[]{this.m_oId, this.m_jName.getText(), this.taxcatmodel.getSelectedKey(), this.taxcustcatmodel.getSelectedKey(), this.taxparentmodel.getSelectedKey(), Formats.PERCENT.parseValue(this.m_jRate.getText()), this.jCascade.isSelected(), Formats.INT.parseValue(this.jOrder.getText())};
        return tax;
    }

    @Override
    public Component getComponent() {
        return this;
    }

    private void initComponents() {
        this.m_jName = new JTextField();
        this.jLabel2 = new JLabel();
        this.jLabel3 = new JLabel();
        this.m_jRate = new JTextField();
        this.jLabel1 = new JLabel();
        this.jLabel4 = new JLabel();
        this.jLabel5 = new JLabel();
        this.jCascade = new JCheckBox();
        this.m_jTaxCategory = new JComboBox();
        this.m_jTaxParent = new JComboBox();
        this.m_jCustTaxCategory = new JComboBox();
        this.jLabel6 = new JLabel();
        this.jOrder = new JTextField();
        this.m_jName.setFont(new Font("Arial", 0, 14));
        this.m_jName.setPreferredSize(new Dimension(0, 30));
        this.jLabel2.setFont(new Font("Arial", 0, 14));
        this.jLabel2.setText(AppLocal.getIntString("label.name"));
        this.jLabel2.setPreferredSize(new Dimension(170, 30));
        this.jLabel3.setFont(new Font("Arial", 0, 14));
        this.jLabel3.setText(AppLocal.getIntString("label.dutyrate"));
        this.jLabel3.setPreferredSize(new Dimension(170, 30));
        this.m_jRate.setFont(new Font("Arial", 0, 14));
        this.m_jRate.setPreferredSize(new Dimension(0, 30));
        this.jLabel1.setFont(new Font("Arial", 0, 14));
        this.jLabel1.setText(AppLocal.getIntString("label.taxcategory"));
        this.jLabel1.setPreferredSize(new Dimension(170, 30));
        this.jLabel4.setFont(new Font("Arial", 0, 14));
        this.jLabel4.setText(AppLocal.getIntString("label.custtaxcategory"));
        this.jLabel4.setPreferredSize(new Dimension(170, 30));
        this.jLabel5.setFont(new Font("Arial", 0, 14));
        this.jLabel5.setText(AppLocal.getIntString("label.taxparent"));
        this.jLabel5.setPreferredSize(new Dimension(170, 30));
        this.jCascade.setFont(new Font("Arial", 0, 14));
        this.jCascade.setText(AppLocal.getIntString("label.cascade"));
        this.jCascade.setPreferredSize(new Dimension(0, 30));
        this.m_jTaxCategory.setFont(new Font("Arial", 0, 14));
        this.m_jTaxCategory.setPreferredSize(new Dimension(0, 30));
        this.m_jTaxParent.setFont(new Font("Arial", 0, 14));
        this.m_jTaxParent.setPreferredSize(new Dimension(0, 30));
        this.m_jCustTaxCategory.setFont(new Font("Arial", 0, 14));
        this.m_jCustTaxCategory.setPreferredSize(new Dimension(0, 30));
        this.jLabel6.setFont(new Font("Arial", 0, 14));
        this.jLabel6.setText(AppLocal.getIntString("label.order"));
        this.jLabel6.setPreferredSize(new Dimension(170, 30));
        this.jOrder.setFont(new Font("Arial", 0, 14));
        this.jOrder.setPreferredSize(new Dimension(0, 30));
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.jLabel1, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.m_jTaxCategory, -2, 200, -2)).addGroup(layout.createSequentialGroup().addComponent(this.jLabel4, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.m_jCustTaxCategory, -2, 200, -2)).addGroup(layout.createSequentialGroup().addComponent(this.jLabel5, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.m_jTaxParent, -2, 200, -2)).addGroup(layout.createSequentialGroup().addComponent(this.jLabel3, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.m_jRate, -2, 60, -2).addGap(16, 16, 16).addComponent(this.jCascade, -2, 110, -2)).addGroup(layout.createSequentialGroup().addComponent(this.jLabel6, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jOrder, -2, 60, -2)).addGroup(layout.createSequentialGroup().addComponent(this.jLabel2, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.m_jName, -2, 200, -2))).addGap(46, 46, 46)));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel2, -2, -1, -2).addComponent(this.m_jName, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1, -2, -1, -2).addComponent(this.m_jTaxCategory, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel4, -2, -1, -2).addComponent(this.m_jCustTaxCategory, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel5, -2, -1, -2).addComponent(this.m_jTaxParent, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel3, -2, -1, -2).addComponent(this.m_jRate, -2, -1, -2)).addComponent(this.jCascade, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel6, -2, -1, -2).addComponent(this.jOrder, -2, -1, -2))));
    }
}

