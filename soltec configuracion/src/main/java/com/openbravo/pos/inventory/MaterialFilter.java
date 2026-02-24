/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.inventory;

import com.openbravo.basic.BasicException;
import com.openbravo.data.gui.ComboBoxValModel;
import com.openbravo.data.gui.ListQBFModelNumber;
import com.openbravo.data.loader.QBFCompareEnum;
import com.openbravo.data.loader.SentenceList;
import com.openbravo.data.user.EditorCreator;
import com.openbravo.format.Formats;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.DataLogicSales;
import com.openbravo.pos.ticket.ProductInfoExt;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MaterialFilter
extends JPanel
implements EditorCreator {
    private SentenceList<ProductInfoExt> m_sentprods;
    private ComboBoxValModel<ProductInfoExt> m_ProdsModel;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JPanel jPanel1;
    private JComboBox<Object> m_jCboName;
    private JComboBox<Object> m_jCboPriceBuy;
    private JComboBox<ProductInfoExt> m_jCboProduct;
    private JTextField m_jName;
    private JTextField m_jPriceBuy;

    public MaterialFilter(DataLogicSales dlSales2) {
        this.initComponents();
        this.m_sentprods = dlSales2.getProductList();
        this.m_ProdsModel = new ComboBoxValModel();
        this.m_jCboName.setModel(new ListQBFModelNumber(new Object[0]));
        this.m_jCboPriceBuy.setModel(new ListQBFModelNumber(new Object[0]));
    }

    public void activate() throws BasicException {
        List<ProductInfoExt> prodlist = this.m_sentprods.list();
        prodlist.add(0, null);
        this.m_ProdsModel.refresh(prodlist);
        this.m_jCboProduct.setModel(this.m_ProdsModel);
    }

    @Override
    public Object createValue() throws BasicException {
        return new Object[]{this.m_jCboName.getSelectedItem(), this.m_jName.getText(), this.m_jCboPriceBuy.getSelectedItem(), Formats.CURRENCY.parseValue(this.m_jPriceBuy.getText()), this.m_ProdsModel.getSelectedKey() == null ? QBFCompareEnum.COMP_NONE : QBFCompareEnum.COMP_EQUALS, this.m_ProdsModel.getSelectedKey()};
    }

    private void initComponents() {
        this.jPanel1 = new JPanel();
        this.jLabel4 = new JLabel();
        this.m_jCboName = new JComboBox();
        this.m_jName = new JTextField();
        this.m_jPriceBuy = new JTextField();
        this.m_jCboPriceBuy = new JComboBox();
        this.jLabel2 = new JLabel();
        this.jLabel3 = new JLabel();
        this.m_jCboProduct = new JComboBox();
        this.setMaximumSize(new Dimension(Short.MAX_VALUE, 160));
        this.setOpaque(false);
        this.setPreferredSize(new Dimension(500, 160));
        this.setRequestFocusEnabled(false);
        this.setLayout(new BoxLayout(this, 1));
        this.jPanel1.setBorder(BorderFactory.createTitledBorder(AppLocal.getIntString("label.byform")));
        this.jPanel1.setMaximumSize(new Dimension(Short.MAX_VALUE, 140));
        this.jPanel1.setPreferredSize(new Dimension(500, 140));
        this.jPanel1.setRequestFocusEnabled(false);
        this.jPanel1.setLayout(null);
        this.jLabel4.setFont(new Font("Arial", 0, 12));
        this.jLabel4.setText(AppLocal.getIntString("label.prodpricebuy"));
        this.jPanel1.add(this.jLabel4);
        this.jLabel4.setBounds(20, 50, 130, 25);
        this.m_jCboName.setFont(new Font("Arial", 0, 14));
        this.jPanel1.add(this.m_jCboName);
        this.m_jCboName.setBounds(150, 20, 150, 25);
        this.m_jName.setFont(new Font("Arial", 0, 12));
        this.jPanel1.add(this.m_jName);
        this.m_jName.setBounds(310, 20, 180, 25);
        this.m_jPriceBuy.setFont(new Font("Arial", 0, 12));
        this.jPanel1.add(this.m_jPriceBuy);
        this.m_jPriceBuy.setBounds(310, 50, 60, 25);
        this.m_jCboPriceBuy.setFont(new Font("Arial", 0, 14));
        this.jPanel1.add(this.m_jCboPriceBuy);
        this.m_jCboPriceBuy.setBounds(150, 50, 150, 25);
        this.jLabel2.setFont(new Font("Arial", 0, 12));
        this.jLabel2.setText(AppLocal.getIntString("label.prodname"));
        this.jPanel1.add(this.jLabel2);
        this.jLabel2.setBounds(20, 20, 130, 25);
        this.jLabel3.setFont(new Font("Arial", 0, 12));
        this.jLabel3.setText(AppLocal.getIntString("label.stockproduct"));
        this.jPanel1.add(this.jLabel3);
        this.jLabel3.setBounds(20, 80, 130, 25);
        this.m_jCboProduct.setFont(new Font("Arial", 0, 14));
        this.jPanel1.add(this.m_jCboProduct);
        this.m_jCboProduct.setBounds(150, 80, 220, 25);
        this.add(this.jPanel1);
    }
}

