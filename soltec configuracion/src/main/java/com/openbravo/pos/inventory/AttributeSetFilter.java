/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.inventory;

import com.openbravo.basic.BasicException;
import com.openbravo.data.gui.ComboBoxValModel;
import com.openbravo.data.loader.DataRead;
import com.openbravo.data.loader.SentenceList;
import com.openbravo.data.loader.SerializerRead;
import com.openbravo.data.loader.SerializerWrite;
import com.openbravo.data.loader.SerializerWriteString;
import com.openbravo.data.loader.StaticSentence;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.inventory.AttributeSetInfo;
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

public class AttributeSetFilter
extends JPanel
implements ReportEditorCreator {
    private static final long serialVersionUID = 1L;
    private SentenceList<AttributeSetInfo> attusesent;
    private ComboBoxValModel<AttributeSetInfo> attusemodel;
    private JComboBox<AttributeSetInfo> jAttrSet;
    private JLabel jLabel8;

    public AttributeSetFilter() {
        this.initComponents();
    }

    @Override
    public void init(AppView app) {
        this.attusesent = new StaticSentence(app.getSession(), "SELECT ID, NAME FROM attributeset ORDER BY NAME", null, new SerializerRead<AttributeSetInfo>(){

            @Override
            public AttributeSetInfo readValues(DataRead dr) throws BasicException {
                return new AttributeSetInfo(dr.getString(1), dr.getString(2));
            }
        });
        this.attusemodel = new ComboBoxValModel();
    }

    @Override
    public void activate() throws BasicException {
        List<AttributeSetInfo> a = this.attusesent.list();
        this.attusemodel = new ComboBoxValModel<AttributeSetInfo>(a);
        this.attusemodel.setSelectedFirst();
        this.jAttrSet.setModel(this.attusemodel);
    }

    @Override
    public SerializerWrite getSerializerWrite() {
        return SerializerWriteString.INSTANCE;
    }

    @Override
    public Component getComponent() {
        return this;
    }

    public void addActionListener(ActionListener l) {
        this.jAttrSet.addActionListener(l);
    }

    public void removeActionListener(ActionListener l) {
        this.jAttrSet.removeActionListener(l);
    }

    @Override
    public Object createValue() throws BasicException {
        AttributeSetInfo attset = (AttributeSetInfo)this.attusemodel.getSelectedItem();
        return attset == null ? null : attset.getId();
    }

    private void initComponents() {
        this.jLabel8 = new JLabel();
        this.jAttrSet = new JComboBox();
        this.setPreferredSize(new Dimension(354, 61));
        this.jLabel8.setFont(new Font("Arial", 0, 14));
        this.jLabel8.setText(AppLocal.getIntString("label.attributeset"));
        this.jLabel8.setPreferredSize(new Dimension(110, 30));
        this.jAttrSet.setFont(new Font("Arial", 0, 14));
        this.jAttrSet.setPreferredSize(new Dimension(220, 30));
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jLabel8, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jAttrSet, -2, -1, -2).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel8, -2, -1, -2).addComponent(this.jAttrSet, -1, -1, Short.MAX_VALUE)).addContainerGap(20, Short.MAX_VALUE)));
    }
}

