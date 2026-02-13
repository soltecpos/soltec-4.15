/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.mant;

import com.openbravo.basic.BasicException;
import com.openbravo.data.gui.ComboBoxValModel;
import com.openbravo.data.loader.SentenceList;
import com.openbravo.data.user.DirtyManager;
import com.openbravo.data.user.EditorRecord;
import com.openbravo.format.Formats;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.DataLogicSales;
import com.openbravo.pos.mant.FloorsInfo;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.UUID;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.border.MatteBorder;

public final class PlacesEditor
extends JPanel
implements EditorRecord {
    private SentenceList<FloorsInfo> m_sentfloor;
    private ComboBoxValModel<FloorsInfo> m_FloorModel;
    private String m_sID;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JComboBox<FloorsInfo> m_jFloor;
    private JTextField m_jName;
    private JTextField m_jX;
    private JTextField m_jY;

    public PlacesEditor(DataLogicSales dlSales2, DirtyManager dirty) {
        this.initComponents();
        this.m_sentfloor = dlSales2.getFloorsList();
        this.m_FloorModel = new ComboBoxValModel();
        this.m_jName.getDocument().addDocumentListener(dirty);
        this.m_jFloor.addActionListener(dirty);
        this.m_jX.getDocument().addDocumentListener(dirty);
        this.m_jY.getDocument().addDocumentListener(dirty);
        this.writeValueEOF();
    }

    public void activate() throws BasicException {
        this.m_FloorModel = new ComboBoxValModel<FloorsInfo>(this.m_sentfloor.list());
        this.m_jFloor.setModel(this.m_FloorModel);
    }

    @Override
    public void refresh() {
    }

    @Override
    public void writeValueEOF() {
        this.m_sID = null;
        this.m_jName.setText(null);
        this.m_FloorModel.setSelectedKey(null);
        this.m_jX.setText(null);
        this.m_jY.setText(null);
        this.m_jName.setEnabled(false);
        this.m_jFloor.setEnabled(false);
        this.m_jX.setEnabled(false);
        this.m_jY.setEnabled(false);
    }

    @Override
    public void writeValueInsert() {
        this.m_sID = UUID.randomUUID().toString();
        this.m_jName.setText(null);
        this.m_FloorModel.setSelectedKey(null);
        this.m_jX.setText(null);
        this.m_jY.setText(null);
        this.m_jName.setEnabled(true);
        this.m_jFloor.setEnabled(true);
        this.m_jX.setEnabled(true);
        this.m_jY.setEnabled(true);
    }

    @Override
    public void writeValueDelete(Object value) {
        Object[] place = (Object[])value;
        this.m_sID = Formats.STRING.formatValue(place[0]);
        this.m_jName.setText(Formats.STRING.formatValue(place[1]));
        this.m_jX.setText(Formats.INT.formatValue(place[2]));
        this.m_jY.setText(Formats.INT.formatValue(place[3]));
        this.m_FloorModel.setSelectedKey(place[4]);
        this.m_jName.setEnabled(false);
        this.m_jFloor.setEnabled(false);
        this.m_jX.setEnabled(false);
        this.m_jY.setEnabled(false);
    }

    @Override
    public void writeValueEdit(Object value) {
        Object[] place = (Object[])value;
        this.m_sID = Formats.STRING.formatValue(place[0]);
        this.m_jName.setText(Formats.STRING.formatValue(place[1]));
        this.m_jX.setText(Formats.INT.formatValue(place[2]));
        this.m_jY.setText(Formats.INT.formatValue(place[3]));
        this.m_FloorModel.setSelectedKey(place[4]);
        this.m_jName.setEnabled(true);
        this.m_jFloor.setEnabled(true);
        this.m_jX.setEnabled(true);
        this.m_jY.setEnabled(true);
    }

    @Override
    public Object createValue() throws BasicException {
        Object[] place = new Object[]{this.m_sID, this.m_jName.getText(), Formats.INT.parseValue(this.m_jX.getText()), Formats.INT.parseValue(this.m_jY.getText()), this.m_FloorModel.getSelectedKey(), "qwerty"};
        return place;
    }

    @Override
    public Component getComponent() {
        return this;
    }

    private void initComponents() {
        this.jLabel2 = new JLabel();
        this.m_jName = new JTextField();
        this.jLabel1 = new JLabel();
        this.m_jFloor = new JComboBox();
        this.jLabel6 = new JLabel();
        this.jLabel5 = new JLabel();
        this.jLabel3 = new JLabel();
        this.m_jX = new JTextField();
        this.m_jY = new JTextField();
        this.jLabel7 = new JLabel();
        this.jLabel4 = new JLabel();
        this.jLabel2.setFont(new Font("Arial", 0, 14));
        this.jLabel2.setText(AppLocal.getIntString("label.name"));
        this.jLabel2.setPreferredSize(new Dimension(110, 30));
        this.m_jName.setFont(new Font("Arial", 0, 14));
        this.m_jName.setPreferredSize(new Dimension(0, 30));
        this.jLabel1.setFont(new Font("Arial", 0, 14));
        this.jLabel1.setText(AppLocal.getIntString("label.placefloor"));
        this.jLabel1.setPreferredSize(new Dimension(110, 30));
        this.m_jFloor.setFont(new Font("Arial", 0, 14));
        this.m_jFloor.setPreferredSize(new Dimension(0, 30));
        this.jLabel6.setFont(new Font("Arial", 0, 14));
        this.jLabel6.setText(AppLocal.getIntString("label.placeposition"));
        this.jLabel6.setPreferredSize(new Dimension(0, 30));
        this.jLabel5.setFont(new Font("Arial", 0, 14));
        this.jLabel5.setText("Across");
        this.jLabel5.setPreferredSize(new Dimension(0, 30));
        this.jLabel3.setFont(new Font("Arial", 0, 14));
        this.jLabel3.setText("Down");
        this.jLabel3.setPreferredSize(new Dimension(0, 30));
        this.m_jX.setFont(new Font("Arial", 0, 14));
        this.m_jX.setPreferredSize(new Dimension(0, 30));
        this.m_jY.setFont(new Font("Arial", 0, 14));
        this.m_jY.setPreferredSize(new Dimension(0, 30));
        this.jLabel7.setFont(new Font("Arial", 0, 14));
        this.jLabel7.setText("<html>Position buttons in your Table plan graphic set in the Floor option <br><br> Start Position 0, 0 is Top Left");
        this.jLabel7.setVerticalAlignment(1);
        this.jLabel7.setMinimumSize(new Dimension(50, 40));
        this.jLabel7.setPreferredSize(new Dimension(489, 40));
        this.jLabel4.setFont(new Font("Tahoma", 1, 11));
        this.jLabel4.setForeground(new Color(0, 102, 255));
        this.jLabel4.setHorizontalAlignment(0);
        this.jLabel4.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/restaurant_floor_sml.png")));
        this.jLabel4.setText(" ");
        this.jLabel4.setBorder(new MatteBorder(new ImageIcon(this.getClass().getResource("/com/openbravo/images/restaurant_floor_sml.png"))));
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addComponent(this.jLabel7, GroupLayout.Alignment.LEADING, -1, -1, Short.MAX_VALUE).addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup().addComponent(this.jLabel2, -2, -1, -2).addGap(0, 0, 0).addComponent(this.m_jName, -2, 200, -2).addGap(18, 18, 18).addComponent(this.jLabel1, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.m_jFloor, -2, 200, -2))).addGroup(layout.createSequentialGroup().addComponent(this.jLabel6, -2, 60, -2).addGap(18, 18, 18).addComponent(this.jLabel5, -2, 50, -2).addGap(18, 18, 18).addComponent(this.m_jX, -2, 50, -2).addGap(18, 18, 18).addComponent(this.jLabel4, -2, 310, -2).addGap(18, 18, 18).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel3, -2, 40, -2).addComponent(this.m_jY, -2, 50, -2)))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel2, -2, -1, -2).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.m_jName, -2, -1, -2).addComponent(this.jLabel1, -2, -1, -2).addComponent(this.m_jFloor, -2, -1, -2))).addGap(18, 18, 18).addComponent(this.jLabel7, -2, 83, -2).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel6, -2, -1, -2).addComponent(this.jLabel5, -2, -1, -2).addComponent(this.m_jX, -2, -1, -2)).addComponent(this.jLabel4, -2, 180, -2))).addGroup(layout.createSequentialGroup().addGap(23, 23, 23).addComponent(this.jLabel3, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.m_jY, -2, -1, -2))).addContainerGap()));
    }
}

