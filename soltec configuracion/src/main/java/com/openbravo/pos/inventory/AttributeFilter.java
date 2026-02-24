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
import com.openbravo.pos.inventory.AttributeInfo;
import com.openbravo.pos.reports.ReportEditorCreator;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;

public class AttributeFilter
extends JPanel
implements ReportEditorCreator {
    private static final long serialVersionUID = 1L;
    private SentenceList<AttributeInfo> attsent;
    private ComboBoxValModel<AttributeInfo> attmodel;
    private JComboBox<AttributeInfo> jAttr;
    private JLabel jLabel8;

    public AttributeFilter() {
        this.initComponents();
    }

    @Override
    public void init(AppView app) {
        this.attsent = new StaticSentence(app.getSession(), "SELECT ID, NAME FROM attribute ORDER BY NAME", null, new SerializerRead<AttributeInfo>(){

            @Override
            public AttributeInfo readValues(DataRead dr) throws BasicException {
                return new AttributeInfo(dr.getString(1), dr.getString(2));
            }
        });
        this.attmodel = new ComboBoxValModel();
    }

    @Override
    public void activate() throws BasicException {
        List<AttributeInfo> a = this.attsent.list();
        this.attmodel = new ComboBoxValModel<AttributeInfo>(a);
        this.attmodel.setSelectedFirst();
        this.jAttr.setModel(this.attmodel);
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
        this.jAttr.addActionListener(l);
    }

    public void removeActionListener(ActionListener l) {
        this.jAttr.removeActionListener(l);
    }

    @Override
    public Object createValue() throws BasicException {
        AttributeInfo att = (AttributeInfo)this.attmodel.getSelectedItem();
        return att == null ? null : att.getId();
    }

    private void initComponents() {
        this.jLabel8 = new JLabel();
        this.jAttr = new JComboBox();
        this.jLabel8.setFont(new Font("Arial", 0, 14));
        this.jLabel8.setText(AppLocal.getIntString("label.attribute"));
        this.jLabel8.setPreferredSize(new Dimension(110, 30));
        this.jAttr.setFont(new Font("Arial", 0, 14));
        this.jAttr.setPreferredSize(new Dimension(220, 30));
        this.jAttr.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                AttributeFilter.this.jAttrActionPerformed(evt);
            }
        });
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jLabel8, -2, 110, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jAttr, -2, 220, -2).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.jAttr, -1, -1, Short.MAX_VALUE).addComponent(this.jLabel8, -1, -1, Short.MAX_VALUE)).addContainerGap(17, Short.MAX_VALUE)));
    }

    private void jAttrActionPerformed(ActionEvent evt) {
    }
}

