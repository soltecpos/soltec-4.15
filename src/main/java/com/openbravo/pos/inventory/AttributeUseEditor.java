/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.inventory;

import com.openbravo.basic.BasicException;
import com.openbravo.data.gui.ComboBoxValModel;
import com.openbravo.data.loader.DataRead;
import com.openbravo.data.loader.SentenceList;
import com.openbravo.data.loader.SerializerRead;
import com.openbravo.data.loader.StaticSentence;
import com.openbravo.data.user.DirtyManager;
import com.openbravo.data.user.EditorRecord;
import com.openbravo.format.Formats;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.inventory.AttributeInfo;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.UUID;
import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;

public class AttributeUseEditor
extends JPanel
implements EditorRecord {
    private static final long serialVersionUID = 1L;
    private SentenceList<AttributeInfo> attributesent;
    private ComboBoxValModel<AttributeInfo> attributemodel;
    private Object id;
    private Object attuseid;
    private Object insertid;
    private JComboBox<AttributeInfo> jAttribute;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JTextField jLineno;

    public AttributeUseEditor(AppView app, DirtyManager dirty) {
        this.attributesent = new StaticSentence(app.getSession(), "SELECT ID, NAME FROM attribute ORDER BY NAME", null, new SerializerRead<AttributeInfo>(){

            @Override
            public AttributeInfo readValues(DataRead dr) throws BasicException {
                return new AttributeInfo(dr.getString(1), dr.getString(2));
            }
        });
        this.attributemodel = new ComboBoxValModel();
        this.initComponents();
        this.jLineno.getDocument().addDocumentListener(dirty);
        this.jAttribute.addActionListener(dirty);
    }

    public void setInsertId(String insertid) {
        this.insertid = insertid;
    }

    public void activate() throws BasicException {
        this.attributemodel = new ComboBoxValModel<AttributeInfo>(this.attributesent.list());
        this.jAttribute.setModel(this.attributemodel);
    }

    @Override
    public void refresh() {
    }

    @Override
    public void writeValueEOF() {
        this.id = null;
        this.attuseid = null;
        this.attributemodel.setSelectedKey(null);
        this.jLineno.setText(null);
        this.jAttribute.setEnabled(false);
        this.jLineno.setEnabled(false);
    }

    @Override
    public void writeValueInsert() {
        this.id = UUID.randomUUID().toString();
        this.attuseid = this.insertid;
        this.attributemodel.setSelectedKey(null);
        this.jLineno.setText(null);
        this.jAttribute.setEnabled(true);
        this.jLineno.setEnabled(true);
    }

    @Override
    public void writeValueEdit(Object value) {
        Object[] obj = (Object[])value;
        this.id = obj[0];
        this.attuseid = obj[1];
        this.attributemodel.setSelectedKey(obj[2]);
        this.jLineno.setText(Formats.INT.formatValue(obj[3]));
        this.jAttribute.setEnabled(true);
        this.jLineno.setEnabled(true);
    }

    @Override
    public void writeValueDelete(Object value) {
        Object[] obj = (Object[])value;
        this.id = obj[0];
        this.attuseid = obj[1];
        this.attributemodel.setSelectedKey(obj[2]);
        this.jLineno.setText(Formats.INT.formatValue(obj[3]));
        this.jAttribute.setEnabled(false);
        this.jLineno.setEnabled(false);
    }

    @Override
    public Component getComponent() {
        return this;
    }

    @Override
    public Object createValue() throws BasicException {
        Object[] value = new Object[]{this.id, this.attuseid, this.attributemodel.getSelectedKey(), Formats.INT.parseValue(this.jLineno.getText()), this.attributemodel.getSelectedText()};
        return value;
    }

    private void initComponents() {
        this.jLabel3 = new JLabel();
        this.jLineno = new JTextField();
        this.jLabel4 = new JLabel();
        this.jAttribute = new JComboBox();
        this.setFont(new Font("Tahoma", 0, 12));
        this.jLabel3.setFont(new Font("Arial", 0, 14));
        this.jLabel3.setText(AppLocal.getIntString("label.order"));
        this.jLabel3.setPreferredSize(new Dimension(110, 30));
        this.jLineno.setFont(new Font("Arial", 0, 14));
        this.jLineno.setPreferredSize(new Dimension(110, 30));
        this.jLabel4.setFont(new Font("Arial", 0, 14));
        this.jLabel4.setText(AppLocal.getIntString("label.attribute"));
        this.jLabel4.setPreferredSize(new Dimension(110, 30));
        this.jAttribute.setFont(new Font("Arial", 0, 14));
        this.jAttribute.setPreferredSize(new Dimension(0, 30));
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.jLabel3, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jLineno, -2, -1, -2)).addGroup(layout.createSequentialGroup().addComponent(this.jLabel4, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jAttribute, -2, 170, -2))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel3, -2, 25, -2).addComponent(this.jLineno, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel4, -2, -1, -2).addComponent(this.jAttribute, -1, -1, -2)).addContainerGap()));
    }
}

