/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.admin;

import com.openbravo.basic.BasicException;
import com.openbravo.data.gui.ComboBoxValModel;
import com.openbravo.data.gui.JImageEditor;
import com.openbravo.data.loader.ImageUtils;
import com.openbravo.data.user.DirtyManager;
import com.openbravo.data.user.EditorRecord;
import com.openbravo.format.Formats;
import com.openbravo.pos.admin.ResourceType;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.util.Base64Encoder;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.UUID;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;

public final class ResourcesView
extends JPanel
implements EditorRecord {
    private Object m_oId;
    private ComboBoxValModel<ResourceType> m_ResourceModel;
    private JLabel jLabel2;
    private JPanel jPanel1;
    private JPanel jPanel3;
    private JScrollPane jScrollPane1;
    private JPanel m_jContainer;
    private ButtonGroup m_jGroupType;
    private JImageEditor m_jImage;
    private JTextField m_jName;
    private JTextArea m_jText;
    private JComboBox<ResourceType> m_jType;

    public ResourcesView(DirtyManager dirty) {
        this.initComponents();
        this.m_ResourceModel = new ComboBoxValModel();
        this.m_ResourceModel.add(ResourceType.TEXT);
        this.m_ResourceModel.add(ResourceType.IMAGE);
        this.m_ResourceModel.add(ResourceType.BINARY);
        this.m_jType.setModel(this.m_ResourceModel);
        this.m_jName.getDocument().addDocumentListener(dirty);
        this.m_jType.addActionListener(dirty);
        this.m_jText.getDocument().addDocumentListener(dirty);
        this.m_jImage.addPropertyChangeListener("image", dirty);
        this.writeValueEOF();
    }

    @Override
    public void writeValueEOF() {
        this.m_oId = null;
        this.m_jName.setText(null);
        this.m_ResourceModel.setSelectedItem(null);
        this.m_jText.setText(null);
        this.m_jImage.setImage(null);
        this.m_jName.setEnabled(false);
        this.m_jType.setEnabled(false);
        this.m_jText.setEnabled(false);
        this.m_jImage.setEnabled(false);
    }

    @Override
    public void writeValueInsert() {
        this.m_oId = null;
        this.m_jName.setText(null);
        this.m_ResourceModel.setSelectedItem(ResourceType.TEXT);
        this.m_jText.setText(null);
        this.m_jImage.setImage(null);
        this.m_jName.setEnabled(true);
        this.m_jType.setEnabled(true);
        this.m_jText.setEnabled(true);
        this.m_jImage.setEnabled(true);
    }

    @Override
    public void writeValueDelete(Object value) {
        Object[] resource = (Object[])value;
        this.m_oId = resource[0];
        this.m_jName.setText((String)resource[1]);
        this.m_ResourceModel.setSelectedKey(resource[2]);
        ResourceType restype = (ResourceType)this.m_ResourceModel.getSelectedItem();
        if (restype == ResourceType.TEXT) {
            this.m_jText.setText(Formats.BYTEA.formatValue(resource[3]));
            this.m_jText.setCaretPosition(0);
            this.m_jImage.setImage(null);
        } else if (restype == ResourceType.IMAGE) {
            this.m_jText.setText(null);
            this.m_jImage.setImage(ImageUtils.readImage((byte[])resource[3]));
        } else if (restype == ResourceType.BINARY) {
            this.m_jText.setText(resource[3] == null ? null : Base64Encoder.encodeChunked((byte[])resource[3]));
            this.m_jText.setCaretPosition(0);
            this.m_jImage.setImage(null);
        } else {
            this.m_jText.setText(null);
            this.m_jImage.setImage(null);
        }
        this.m_jName.setEnabled(false);
        this.m_jType.setEnabled(false);
        this.m_jText.setEnabled(false);
        this.m_jImage.setEnabled(false);
    }

    @Override
    public void writeValueEdit(Object value) {
        Object[] resource = (Object[])value;
        this.m_oId = resource[0];
        this.m_jName.setText((String)resource[1]);
        this.m_ResourceModel.setSelectedKey(resource[2]);
        ResourceType restype = (ResourceType)this.m_ResourceModel.getSelectedItem();
        if (restype == ResourceType.TEXT) {
            this.m_jText.setText(Formats.BYTEA.formatValue(resource[3]));
            this.m_jText.setCaretPosition(0);
            this.m_jImage.setImage(null);
        } else if (restype == ResourceType.IMAGE) {
            this.m_jText.setText(null);
            this.m_jImage.setImage(ImageUtils.readImage((byte[])resource[3]));
        } else if (restype == ResourceType.BINARY) {
            this.m_jText.setText(resource[2] == null ? null : Base64Encoder.encodeChunked((byte[])resource[3]));
            this.m_jText.setCaretPosition(0);
            this.m_jImage.setImage(null);
        } else {
            this.m_jText.setText(null);
            this.m_jImage.setImage(null);
        }
        this.m_jName.setEnabled(true);
        this.m_jType.setEnabled(true);
        this.m_jText.setEnabled(true);
        this.m_jImage.setEnabled(true);
    }

    @Override
    public Object createValue() throws BasicException {
        Object[] resource = new Object[4];
        resource[0] = this.m_oId == null ? UUID.randomUUID().toString() : this.m_oId;
        resource[1] = this.m_jName.getText();
        ResourceType restype = (ResourceType)this.m_ResourceModel.getSelectedItem();
        resource[2] = restype.getKey();
        resource[3] = restype == ResourceType.TEXT ? Formats.BYTEA.parseValue(this.m_jText.getText()) : (restype == ResourceType.IMAGE ? (Object)ImageUtils.writeImage(this.m_jImage.getImage()) : (restype == ResourceType.BINARY ? (Object)Base64Encoder.decode(this.m_jText.getText()) : null));
        return resource;
    }

    @Override
    public Component getComponent() {
        return this;
    }

    @Override
    public void refresh() {
    }

    private void showView(String view) {
        CardLayout cl = (CardLayout)this.m_jContainer.getLayout();
        cl.show(this.m_jContainer, view);
    }

    private void initComponents() {
        this.m_jGroupType = new ButtonGroup();
        this.jPanel3 = new JPanel();
        this.m_jContainer = new JPanel();
        this.jScrollPane1 = new JScrollPane();
        this.m_jText = new JTextArea();
        this.jPanel1 = new JPanel();
        this.m_jImage = new JImageEditor();
        this.jLabel2 = new JLabel();
        this.m_jName = new JTextField();
        this.m_jType = new JComboBox();
        this.jPanel3.setLayout(new BorderLayout());
        this.m_jContainer.setLayout(new CardLayout());
        this.m_jText.setFont(new Font("Arial", 0, 14));
        this.jScrollPane1.setViewportView(this.m_jText);
        this.m_jContainer.add((Component)this.jScrollPane1, "text");
        this.m_jContainer.add((Component)this.jPanel1, "null");
        this.m_jContainer.add((Component)this.m_jImage, "image");
        this.jPanel3.add((Component)this.m_jContainer, "Center");
        this.jLabel2.setFont(new Font("Arial", 0, 14));
        this.jLabel2.setText(AppLocal.getIntString("label.resname"));
        this.jLabel2.setPreferredSize(new Dimension(0, 30));
        this.m_jName.setFont(new Font("Arial", 0, 14));
        this.m_jName.setPreferredSize(new Dimension(0, 30));
        this.m_jType.setFont(new Font("Arial", 0, 14));
        this.m_jType.setPreferredSize(new Dimension(150, 30));
        this.m_jType.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ResourcesView.this.m_jTypeActionPerformed(evt);
            }
        });
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jPanel3, -1, 549, Short.MAX_VALUE).addGroup(layout.createSequentialGroup().addComponent(this.jLabel2, -2, 90, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.m_jName, -2, 180, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.m_jType, -2, -1, -2).addGap(0, 0, Short.MAX_VALUE))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel2, -2, -1, -2).addComponent(this.m_jName, -2, -1, -2).addComponent(this.m_jType, -2, -1, -2)).addGap(18, 18, 18).addComponent(this.jPanel3, -1, 327, Short.MAX_VALUE).addContainerGap()));
    }

    private void m_jTypeActionPerformed(ActionEvent evt) {
        ResourceType restype = (ResourceType)this.m_ResourceModel.getSelectedItem();
        if (restype == ResourceType.TEXT) {
            this.showView("text");
        } else if (restype == ResourceType.IMAGE) {
            this.showView("image");
        } else if (restype == ResourceType.BINARY) {
            this.showView("text");
        } else {
            this.showView("null");
        }
    }
}

