/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.reports;

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
import com.openbravo.pos.inventory.LocationInfo;
import com.openbravo.pos.reports.ReportEditorCreator;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;

public class JParamsLocation
extends JPanel
implements ReportEditorCreator {
    private SentenceList<LocationInfo> m_sentlocations;
    private ComboBoxValModel<LocationInfo> m_LocationsModel;
    private JLabel jLabel8;
    private JComboBox<LocationInfo> m_jLocation;

    public JParamsLocation() {
        this.initComponents();
    }

    @Override
    public void init(AppView app) {
        DataLogicSales dlSales2 = (DataLogicSales)app.getBean("com.openbravo.pos.forms.DataLogicSales");
        this.m_sentlocations = dlSales2.getLocationsList();
        this.m_LocationsModel = new ComboBoxValModel();
    }

    @Override
    public void activate() throws BasicException {
        List<LocationInfo> a = this.m_sentlocations.list();
        this.addFirst(a);
        this.m_LocationsModel = new ComboBoxValModel<LocationInfo>(a);
        this.m_LocationsModel.setSelectedFirst();
        this.m_jLocation.setModel(this.m_LocationsModel);
    }

    @Override
    public SerializerWrite getSerializerWrite() {
        return new SerializerWriteBasic(Datas.OBJECT, Datas.STRING);
    }

    @Override
    public Component getComponent() {
        return this;
    }

    protected void addFirst(List<LocationInfo> a) {
        a.add(0, null);
    }

    public void addActionListener(ActionListener l) {
        this.m_jLocation.addActionListener(l);
    }

    public void removeActionListener(ActionListener l) {
        this.m_jLocation.removeActionListener(l);
    }

    @Override
    public Object createValue() throws BasicException {
        return new Object[]{this.m_LocationsModel.getSelectedKey() == null ? QBFCompareEnum.COMP_NONE : QBFCompareEnum.COMP_EQUALS, this.m_LocationsModel.getSelectedKey()};
    }

    private void initComponents() {
        this.m_jLocation = new JComboBox();
        this.jLabel8 = new JLabel();
        this.setFont(new Font("Arial", 0, 12));
        this.setPreferredSize(new Dimension(384, 40));
        this.m_jLocation.setFont(new Font("Arial", 0, 14));
        this.m_jLocation.setPreferredSize(new Dimension(250, 30));
        this.jLabel8.setFont(new Font("Arial", 0, 14));
        this.jLabel8.setText(AppLocal.getIntString("label.warehouse"));
        this.jLabel8.setPreferredSize(new Dimension(100, 30));
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jLabel8, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.m_jLocation, -2, -1, -2).addContainerGap(-1, Short.MAX_VALUE)));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.m_jLocation, -2, -1, -2).addComponent(this.jLabel8, -2, -1, -2)).addContainerGap()));
    }
}

