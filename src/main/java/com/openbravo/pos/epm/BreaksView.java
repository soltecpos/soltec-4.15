/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.epm;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.SentenceList;
import com.openbravo.data.user.DirtyManager;
import com.openbravo.data.user.EditorRecord;
import com.openbravo.pos.epm.BreaksInfo;
import com.openbravo.pos.epm.DataLogicPresenceManagement;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.AppView;
import java.awt.Component;
import java.awt.Font;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;
import javax.swing.GroupLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;

public final class BreaksView
extends JPanel
implements EditorRecord {
    private Object m_oId;
    private SentenceList<BreaksInfo> m_sentcat;
    private DirtyManager m_Dirty;
    private JLabel jLabel1;
    private JScrollPane jScrollPane1;
    private JLabel m_Name3;
    private JLabel m_Ncx;
    private JTextArea m_jBreakDescription;
    private JTextField m_jBreakName;
    private JCheckBox m_jVisible;

    public BreaksView(AppView app, DirtyManager dirty) {
        DataLogicPresenceManagement dlPresenceManagement = (DataLogicPresenceManagement)app.getBean("com.openbravo.pos.epm.DataLogicPresenceManagement");
        this.initComponents();
        this.m_sentcat = dlPresenceManagement.getBreaksList();
        this.m_Dirty = dirty;
        this.m_jBreakName.getDocument().addDocumentListener(dirty);
        this.m_jVisible.addActionListener(dirty);
        this.m_jBreakDescription.getDocument().addDocumentListener(dirty);
        this.writeValueEOF();
    }

    void activate() throws BasicException {
        List<BreaksInfo> a = this.m_sentcat.list();
        a.add(0, null);
    }

    @Override
    public void writeValueEOF() {
        this.m_oId = null;
        this.m_jBreakName.setText(null);
        this.m_jBreakDescription.setText(null);
        this.m_jVisible.setSelected(false);
        this.m_jBreakName.setEditable(false);
        this.m_jBreakDescription.setEnabled(false);
        this.m_jVisible.setEnabled(false);
    }

    @Override
    public void writeValueInsert() {
        this.m_oId = null;
        this.m_jBreakName.setText(null);
        this.m_jBreakDescription.setText(null);
        this.m_jVisible.setSelected(true);
        this.m_jBreakName.setEditable(true);
        this.m_jBreakDescription.setEnabled(true);
        this.m_jVisible.setEnabled(true);
    }

    @Override
    public void writeValueEdit(Object value) {
        Object[] breaks = (Object[])value;
        this.m_oId = breaks[0];
        this.m_jBreakName.setText((String)breaks[1]);
        this.m_jBreakDescription.setText((String)breaks[2]);
        this.m_jVisible.setSelected((Boolean)breaks[3]);
        this.m_jBreakName.setEditable(true);
        this.m_jBreakDescription.setEnabled(true);
        this.m_jVisible.setEnabled(true);
    }

    @Override
    public void writeValueDelete(Object value) {
        Object[] breaks = (Object[])value;
        this.m_oId = breaks[0];
        this.m_jBreakName.setText((String)breaks[1]);
        this.m_jBreakDescription.setText((String)breaks[2]);
        this.m_jVisible.setSelected((Boolean)breaks[3]);
        this.m_jBreakName.setEditable(false);
        this.m_jBreakDescription.setEnabled(false);
        this.m_jVisible.setEnabled(false);
    }

    @Override
    public void refresh() {
    }

    @Override
    public Component getComponent() {
        return this;
    }

    @Override
    public Object createValue() throws BasicException {
        Object[] breaks = new Object[]{this.m_oId == null ? UUID.randomUUID().toString() : this.m_oId, this.m_jBreakName.getText(), this.m_jBreakDescription.getText(), this.m_jVisible.isSelected()};
        return breaks;
    }

    private void initComponents() {
        this.m_jBreakName = new JTextField();
        this.jScrollPane1 = new JScrollPane();
        this.m_jBreakDescription = new JTextArea();
        this.m_jVisible = new JCheckBox();
        this.m_Ncx = new JLabel();
        this.m_Name3 = new JLabel();
        this.jLabel1 = new JLabel();
        this.m_jBreakName.setFont(new Font("Arial", 0, 12));
        this.m_jBreakDescription.setColumns(20);
        this.m_jBreakDescription.setFont(new Font("Arial", 0, 12));
        this.m_jBreakDescription.setLineWrap(true);
        this.m_jBreakDescription.setRows(5);
        this.jScrollPane1.setViewportView(this.m_jBreakDescription);
        this.m_jVisible.setFont(new Font("Arial", 0, 12));
        this.m_Ncx.setFont(new Font("Arial", 0, 12));
        this.m_Ncx.setText(AppLocal.getIntString("label.epm.visible"));
        this.m_Name3.setFont(new Font("Arial", 0, 12));
        this.m_Name3.setText(AppLocal.getIntString("label.epm.notes"));
        this.jLabel1.setFont(new Font("Arial", 0, 12));
        ResourceBundle bundle = ResourceBundle.getBundle("pos_messages");
        this.jLabel1.setText(bundle.getString("label.epm.employee"));
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.jLabel1, -1, 126, Short.MAX_VALUE).addComponent(this.m_Name3, -1, 126, Short.MAX_VALUE).addComponent(this.m_Ncx, -1, 126, Short.MAX_VALUE)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.m_jVisible).addComponent(this.m_jBreakName).addComponent(this.jScrollPane1, -2, 223, -2)).addGap(214, 214, 214)));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.m_jBreakName, -2, 25, -2).addComponent(this.jLabel1, -2, 25, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.m_Ncx, -2, 25, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.m_Name3, -2, 25, -2)).addGroup(layout.createSequentialGroup().addComponent(this.m_jVisible, -2, 25, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jScrollPane1, -2, 138, -2))).addContainerGap(35, Short.MAX_VALUE)));
    }
}

