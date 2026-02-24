/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.mant;

import com.openbravo.basic.BasicException;
import com.openbravo.data.gui.JImageEditor;
import com.openbravo.data.user.DirtyManager;
import com.openbravo.data.user.EditorRecord;
import com.openbravo.format.Formats;
import com.openbravo.pos.forms.AppLocal;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.util.UUID;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public final class FloorsEditor
extends JPanel
implements EditorRecord {
    private String m_sID;
    private JLabel jLabel3;
    private JPanel jPanel1;
    private JPanel jPanel3;
    private JImageEditor m_jImage;
    private JTextField m_jName;

    public FloorsEditor(DirtyManager dirty) {
        this.initComponents();
        this.m_jName.getDocument().addDocumentListener(dirty);
        this.m_jImage.addPropertyChangeListener("image", dirty);
        this.writeValueEOF();
    }

    @Override
    public void writeValueEOF() {
        this.m_sID = null;
        this.m_jName.setText(null);
        this.m_jImage.setImage(null);
        this.m_jName.setEnabled(false);
        this.m_jImage.setEnabled(false);
    }

    @Override
    public void writeValueInsert() {
        this.m_sID = UUID.randomUUID().toString();
        this.m_jName.setText(null);
        this.m_jImage.setImage(null);
        this.m_jName.setEnabled(true);
        this.m_jImage.setEnabled(true);
    }

    @Override
    public void writeValueDelete(Object value) {
        Object[] floor = (Object[])value;
        this.m_sID = Formats.STRING.formatValue(floor[0]);
        this.m_jName.setText(Formats.STRING.formatValue(floor[1]));
        this.m_jImage.setImage((BufferedImage)floor[2]);
        this.m_jName.setEnabled(false);
        this.m_jImage.setEnabled(false);
    }

    @Override
    public void writeValueEdit(Object value) {
        Object[] floor = (Object[])value;
        this.m_sID = Formats.STRING.formatValue(floor[0]);
        this.m_jName.setText(Formats.STRING.formatValue(floor[1]));
        this.m_jImage.setImage((BufferedImage)floor[2]);
        this.m_jName.setEnabled(true);
        this.m_jImage.setEnabled(true);
    }

    @Override
    public Object createValue() throws BasicException {
        Object[] floor = new Object[]{this.m_sID, this.m_jName.getText(), this.m_jImage.getImage()};
        return floor;
    }

    @Override
    public Component getComponent() {
        return this;
    }

    @Override
    public void refresh() {
    }

    private void initComponents() {
        this.jPanel1 = new JPanel();
        this.jLabel3 = new JLabel();
        this.m_jName = new JTextField();
        this.jPanel3 = new JPanel();
        this.m_jImage = new JImageEditor();
        this.setMinimumSize(new Dimension(91, 125));
        this.setLayout(new BorderLayout());
        this.jPanel1.setPreferredSize(new Dimension(150, 100));
        this.jPanel1.setLayout(null);
        this.jLabel3.setFont(new Font("Arial", 0, 14));
        this.jLabel3.setText(AppLocal.getIntString("label.name"));
        this.jLabel3.setPreferredSize(new Dimension(110, 30));
        this.jPanel1.add(this.jLabel3);
        this.jLabel3.setBounds(20, 20, 110, 30);
        this.m_jName.setFont(new Font("Arial", 0, 14));
        this.m_jName.setPreferredSize(new Dimension(250, 30));
        this.jPanel1.add(this.m_jName);
        this.m_jName.setBounds(130, 20, 250, 30);
        this.add((Component)this.jPanel1, "North");
        this.jPanel3.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
        this.jPanel3.setLayout(new BorderLayout());
        this.m_jImage.setFont(new Font("Arial", 0, 12));
        this.jPanel3.add((Component)this.m_jImage, "Center");
        this.add((Component)this.jPanel3, "Center");
    }
}

