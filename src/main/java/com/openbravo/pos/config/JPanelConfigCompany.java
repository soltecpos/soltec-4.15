/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.config;

import com.openbravo.data.user.DirtyManager;
import com.openbravo.pos.config.PanelConfig;
import com.openbravo.pos.forms.AppConfig;
import com.openbravo.pos.forms.AppLocal;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;

public class JPanelConfigCompany
extends JPanel
implements PanelConfig {
    private final DirtyManager dirty = new DirtyManager();
    private JTextField jtxtTktFooter1;
    private JTextField jtxtTktFooter2;
    private JTextField jtxtTktFooter3;
    private JTextField jtxtTktFooter4;
    private JTextField jtxtTktFooter5;
    private JTextField jtxtTktFooter6;
    private JTextField jtxtTktHeader1;
    private JTextField jtxtTktHeader2;
    private JTextField jtxtTktHeader3;
    private JTextField jtxtTktHeader4;
    private JTextField jtxtTktHeader5;
    private JTextField jtxtTktHeader6;
    private JLabel lblTktFooter1;
    private JLabel lblTktFooter2;
    private JLabel lblTktFooter3;
    private JLabel lblTktFooter4;
    private JLabel lblTktFooter5;
    private JLabel lblTktFooter6;
    private JLabel lblTktHeader1;
    private JLabel lblTktHeader2;
    private JLabel lblTktHeader3;
    private JLabel lblTktHeader4;
    private JLabel lblTktHeader5;
    private JLabel lblTktHeader6;

    public JPanelConfigCompany() {
        this.initComponents();
        this.jtxtTktHeader1.getDocument().addDocumentListener(this.dirty);
        this.jtxtTktHeader2.getDocument().addDocumentListener(this.dirty);
        this.jtxtTktHeader3.getDocument().addDocumentListener(this.dirty);
        this.jtxtTktHeader4.getDocument().addDocumentListener(this.dirty);
        this.jtxtTktHeader5.getDocument().addDocumentListener(this.dirty);
        this.jtxtTktHeader6.getDocument().addDocumentListener(this.dirty);
        this.jtxtTktFooter1.getDocument().addDocumentListener(this.dirty);
        this.jtxtTktFooter2.getDocument().addDocumentListener(this.dirty);
        this.jtxtTktFooter3.getDocument().addDocumentListener(this.dirty);
        this.jtxtTktFooter4.getDocument().addDocumentListener(this.dirty);
        this.jtxtTktFooter5.getDocument().addDocumentListener(this.dirty);
        this.jtxtTktFooter6.getDocument().addDocumentListener(this.dirty);
    }

    @Override
    public boolean hasChanged() {
        return this.dirty.isDirty();
    }

    @Override
    public Component getConfigComponent() {
        return this;
    }

    @Override
    public void loadProperties(AppConfig config) {
        this.jtxtTktHeader1.setText(config.getProperty("tkt.header1"));
        this.jtxtTktHeader2.setText(config.getProperty("tkt.header2"));
        this.jtxtTktHeader3.setText(config.getProperty("tkt.header3"));
        this.jtxtTktHeader4.setText(config.getProperty("tkt.header4"));
        this.jtxtTktHeader5.setText(config.getProperty("tkt.header5"));
        this.jtxtTktHeader6.setText(config.getProperty("tkt.header6"));
        this.jtxtTktFooter1.setText(config.getProperty("tkt.footer1"));
        this.jtxtTktFooter2.setText(config.getProperty("tkt.footer2"));
        this.jtxtTktFooter3.setText(config.getProperty("tkt.footer3"));
        this.jtxtTktFooter4.setText(config.getProperty("tkt.footer4"));
        this.jtxtTktFooter5.setText(config.getProperty("tkt.footer5"));
        this.jtxtTktFooter6.setText(config.getProperty("tkt.footer6"));
        this.dirty.setDirty(false);
    }

    @Override
    public void saveProperties(AppConfig config) {
        config.setProperty("tkt.header1", this.jtxtTktHeader1.getText());
        config.setProperty("tkt.header2", this.jtxtTktHeader2.getText());
        config.setProperty("tkt.header3", this.jtxtTktHeader3.getText());
        config.setProperty("tkt.header4", this.jtxtTktHeader4.getText());
        config.setProperty("tkt.header5", this.jtxtTktHeader5.getText());
        config.setProperty("tkt.header6", this.jtxtTktHeader6.getText());
        config.setProperty("tkt.footer1", this.jtxtTktFooter1.getText());
        config.setProperty("tkt.footer2", this.jtxtTktFooter2.getText());
        config.setProperty("tkt.footer3", this.jtxtTktFooter3.getText());
        config.setProperty("tkt.footer4", this.jtxtTktFooter4.getText());
        config.setProperty("tkt.footer5", this.jtxtTktFooter5.getText());
        config.setProperty("tkt.footer6", this.jtxtTktFooter6.getText());
        this.dirty.setDirty(false);
    }

    private void initComponents() {
        this.lblTktHeader1 = new JLabel();
        this.jtxtTktHeader1 = new JTextField();
        this.lblTktHeader2 = new JLabel();
        this.jtxtTktHeader2 = new JTextField();
        this.lblTktHeader3 = new JLabel();
        this.jtxtTktHeader3 = new JTextField();
        this.lblTktHeader4 = new JLabel();
        this.jtxtTktHeader4 = new JTextField();
        this.lblTktHeader5 = new JLabel();
        this.jtxtTktHeader5 = new JTextField();
        this.lblTktHeader6 = new JLabel();
        this.jtxtTktHeader6 = new JTextField();
        this.lblTktFooter1 = new JLabel();
        this.jtxtTktFooter1 = new JTextField();
        this.lblTktFooter2 = new JLabel();
        this.jtxtTktFooter2 = new JTextField();
        this.lblTktFooter3 = new JLabel();
        this.jtxtTktFooter3 = new JTextField();
        this.lblTktFooter4 = new JLabel();
        this.jtxtTktFooter4 = new JTextField();
        this.lblTktFooter5 = new JLabel();
        this.jtxtTktFooter5 = new JTextField();
        this.lblTktFooter6 = new JLabel();
        this.jtxtTktFooter6 = new JTextField();
        this.setBackground(new Color(255, 255, 255));
        this.setFont(new Font("Arial", 0, 14));
        this.setPreferredSize(new Dimension(700, 500));
        this.lblTktHeader1.setFont(new Font("Arial", 1, 14));
        this.lblTktHeader1.setText(AppLocal.getIntString("label.tktheader1"));
        this.lblTktHeader1.setMaximumSize(new Dimension(0, 25));
        this.lblTktHeader1.setMinimumSize(new Dimension(0, 0));
        this.lblTktHeader1.setPreferredSize(new Dimension(150, 30));
        this.jtxtTktHeader1.setFont(new Font("Arial", 0, 14));
        this.jtxtTktHeader1.setMaximumSize(new Dimension(0, 25));
        this.jtxtTktHeader1.setMinimumSize(new Dimension(0, 0));
        this.jtxtTktHeader1.setPreferredSize(new Dimension(300, 30));
        this.lblTktHeader2.setFont(new Font("Arial", 0, 14));
        this.lblTktHeader2.setText(AppLocal.getIntString("label.tktheader2"));
        this.lblTktHeader2.setMaximumSize(new Dimension(0, 25));
        this.lblTktHeader2.setMinimumSize(new Dimension(0, 0));
        this.lblTktHeader2.setPreferredSize(new Dimension(150, 30));
        this.jtxtTktHeader2.setFont(new Font("Arial", 0, 14));
        this.jtxtTktHeader2.setMaximumSize(new Dimension(0, 25));
        this.jtxtTktHeader2.setMinimumSize(new Dimension(0, 0));
        this.jtxtTktHeader2.setPreferredSize(new Dimension(300, 30));
        this.lblTktHeader3.setFont(new Font("Arial", 0, 14));
        this.lblTktHeader3.setText(AppLocal.getIntString("label.tktheader3"));
        this.lblTktHeader3.setMaximumSize(new Dimension(0, 25));
        this.lblTktHeader3.setMinimumSize(new Dimension(0, 0));
        this.lblTktHeader3.setPreferredSize(new Dimension(150, 30));
        this.jtxtTktHeader3.setFont(new Font("Arial", 0, 14));
        this.jtxtTktHeader3.setMaximumSize(new Dimension(0, 25));
        this.jtxtTktHeader3.setMinimumSize(new Dimension(0, 0));
        this.jtxtTktHeader3.setPreferredSize(new Dimension(300, 30));
        this.lblTktHeader4.setFont(new Font("Arial", 0, 14));
        this.lblTktHeader4.setText(AppLocal.getIntString("label.tktheader4"));
        this.lblTktHeader4.setMaximumSize(new Dimension(0, 25));
        this.lblTktHeader4.setMinimumSize(new Dimension(0, 0));
        this.lblTktHeader4.setPreferredSize(new Dimension(150, 30));
        this.jtxtTktHeader4.setFont(new Font("Arial", 0, 14));
        this.jtxtTktHeader4.setMaximumSize(new Dimension(0, 25));
        this.jtxtTktHeader4.setMinimumSize(new Dimension(0, 0));
        this.jtxtTktHeader4.setPreferredSize(new Dimension(300, 30));
        this.lblTktHeader5.setFont(new Font("Arial", 0, 14));
        this.lblTktHeader5.setText(AppLocal.getIntString("label.tktheader5"));
        this.lblTktHeader5.setMaximumSize(new Dimension(0, 25));
        this.lblTktHeader5.setMinimumSize(new Dimension(0, 0));
        this.lblTktHeader5.setPreferredSize(new Dimension(150, 30));
        this.jtxtTktHeader5.setFont(new Font("Arial", 0, 14));
        this.jtxtTktHeader5.setMaximumSize(new Dimension(0, 25));
        this.jtxtTktHeader5.setMinimumSize(new Dimension(0, 0));
        this.jtxtTktHeader5.setPreferredSize(new Dimension(300, 30));
        this.lblTktHeader6.setFont(new Font("Arial", 0, 14));
        this.lblTktHeader6.setText(AppLocal.getIntString("label.tktheader6"));
        this.lblTktHeader6.setMaximumSize(new Dimension(0, 25));
        this.lblTktHeader6.setMinimumSize(new Dimension(0, 0));
        this.lblTktHeader6.setPreferredSize(new Dimension(150, 30));
        this.jtxtTktHeader6.setFont(new Font("Arial", 0, 14));
        this.jtxtTktHeader6.setMaximumSize(new Dimension(0, 25));
        this.jtxtTktHeader6.setMinimumSize(new Dimension(0, 0));
        this.jtxtTktHeader6.setPreferredSize(new Dimension(300, 30));
        this.lblTktFooter1.setFont(new Font("Arial", 1, 14));
        this.lblTktFooter1.setText(AppLocal.getIntString("label.tktfooter1"));
        this.lblTktFooter1.setMaximumSize(new Dimension(0, 25));
        this.lblTktFooter1.setMinimumSize(new Dimension(0, 0));
        this.lblTktFooter1.setPreferredSize(new Dimension(150, 30));
        this.jtxtTktFooter1.setFont(new Font("Arial", 0, 14));
        this.jtxtTktFooter1.setMaximumSize(new Dimension(0, 25));
        this.jtxtTktFooter1.setMinimumSize(new Dimension(0, 0));
        this.jtxtTktFooter1.setPreferredSize(new Dimension(300, 30));
        this.lblTktFooter2.setFont(new Font("Arial", 0, 14));
        this.lblTktFooter2.setText(AppLocal.getIntString("label.tktfooter2"));
        this.lblTktFooter2.setMaximumSize(new Dimension(0, 25));
        this.lblTktFooter2.setMinimumSize(new Dimension(0, 0));
        this.lblTktFooter2.setPreferredSize(new Dimension(150, 30));
        this.jtxtTktFooter2.setFont(new Font("Arial", 0, 14));
        this.jtxtTktFooter2.setMaximumSize(new Dimension(0, 25));
        this.jtxtTktFooter2.setMinimumSize(new Dimension(0, 0));
        this.jtxtTktFooter2.setPreferredSize(new Dimension(300, 30));
        this.lblTktFooter3.setFont(new Font("Arial", 0, 14));
        this.lblTktFooter3.setText(AppLocal.getIntString("label.tktfooter3"));
        this.lblTktFooter3.setMaximumSize(new Dimension(0, 25));
        this.lblTktFooter3.setMinimumSize(new Dimension(0, 0));
        this.lblTktFooter3.setPreferredSize(new Dimension(150, 30));
        this.jtxtTktFooter3.setFont(new Font("Arial", 0, 14));
        this.jtxtTktFooter3.setMaximumSize(new Dimension(0, 25));
        this.jtxtTktFooter3.setMinimumSize(new Dimension(0, 0));
        this.jtxtTktFooter3.setPreferredSize(new Dimension(300, 30));
        this.lblTktFooter4.setFont(new Font("Arial", 0, 14));
        this.lblTktFooter4.setText(AppLocal.getIntString("label.tktfooter4"));
        this.lblTktFooter4.setMaximumSize(new Dimension(0, 25));
        this.lblTktFooter4.setMinimumSize(new Dimension(0, 0));
        this.lblTktFooter4.setPreferredSize(new Dimension(150, 30));
        this.jtxtTktFooter4.setFont(new Font("Arial", 0, 14));
        this.jtxtTktFooter4.setMaximumSize(new Dimension(0, 25));
        this.jtxtTktFooter4.setMinimumSize(new Dimension(0, 0));
        this.jtxtTktFooter4.setPreferredSize(new Dimension(300, 30));
        this.lblTktFooter5.setFont(new Font("Arial", 0, 14));
        this.lblTktFooter5.setText(AppLocal.getIntString("label.tktfooter5"));
        this.lblTktFooter5.setMaximumSize(new Dimension(0, 25));
        this.lblTktFooter5.setMinimumSize(new Dimension(0, 0));
        this.lblTktFooter5.setPreferredSize(new Dimension(150, 30));
        this.jtxtTktFooter5.setFont(new Font("Arial", 0, 14));
        this.jtxtTktFooter5.setMaximumSize(new Dimension(0, 25));
        this.jtxtTktFooter5.setMinimumSize(new Dimension(0, 0));
        this.jtxtTktFooter5.setPreferredSize(new Dimension(300, 30));
        this.lblTktFooter6.setFont(new Font("Arial", 0, 14));
        this.lblTktFooter6.setText(AppLocal.getIntString("label.tktfooter6"));
        this.lblTktFooter6.setToolTipText("");
        this.lblTktFooter6.setMaximumSize(new Dimension(0, 25));
        this.lblTktFooter6.setMinimumSize(new Dimension(0, 0));
        this.lblTktFooter6.setPreferredSize(new Dimension(150, 30));
        this.jtxtTktFooter6.setFont(new Font("Arial", 0, 14));
        this.jtxtTktFooter6.setMaximumSize(new Dimension(0, 25));
        this.jtxtTktFooter6.setMinimumSize(new Dimension(0, 0));
        this.jtxtTktFooter6.setPreferredSize(new Dimension(300, 30));
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.lblTktHeader1, -2, -1, -2).addComponent(this.lblTktFooter1, -2, -1, -2).addGroup(layout.createSequentialGroup().addGap(10, 10, 10).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.lblTktHeader2, -1, -1, -2).addComponent(this.lblTktFooter2, -1, -1, -2).addComponent(this.lblTktFooter4, -2, -1, -2).addComponent(this.lblTktFooter3, -2, -1, -2).addComponent(this.lblTktFooter5, -2, -1, -2).addComponent(this.lblTktFooter6, -2, -1, -2).addComponent(this.lblTktHeader3, -1, -1, -2).addComponent(this.lblTktHeader4, -1, -1, -2).addComponent(this.lblTktHeader5, -1, -1, -2))).addComponent(this.lblTktHeader6, GroupLayout.Alignment.TRAILING, -1, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addComponent(this.jtxtTktFooter5, GroupLayout.Alignment.LEADING, -1, -1, Short.MAX_VALUE).addComponent(this.jtxtTktFooter4, GroupLayout.Alignment.LEADING, -1, -1, Short.MAX_VALUE).addComponent(this.jtxtTktFooter3, GroupLayout.Alignment.LEADING, -1, -1, Short.MAX_VALUE).addComponent(this.jtxtTktFooter2, GroupLayout.Alignment.LEADING, -1, -1, Short.MAX_VALUE).addComponent(this.jtxtTktFooter1, GroupLayout.Alignment.LEADING, -1, -1, Short.MAX_VALUE).addComponent(this.jtxtTktHeader6, GroupLayout.Alignment.LEADING, -2, -1, -2).addComponent(this.jtxtTktHeader5, GroupLayout.Alignment.LEADING, -2, -1, -2).addComponent(this.jtxtTktHeader4, GroupLayout.Alignment.LEADING, -1, -1, -2).addComponent(this.jtxtTktHeader3, GroupLayout.Alignment.LEADING, -1, -1, -2).addComponent(this.jtxtTktHeader2, GroupLayout.Alignment.LEADING, -1, -1, -2).addComponent(this.jtxtTktHeader1, GroupLayout.Alignment.LEADING, -1, -1, -2).addComponent(this.jtxtTktFooter6, -1, -1, Short.MAX_VALUE)).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.lblTktHeader1, -2, -1, -2).addComponent(this.jtxtTktHeader1, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.lblTktHeader2, -2, -1, -2).addComponent(this.jtxtTktHeader2, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jtxtTktHeader3, -2, -1, -2).addComponent(this.lblTktHeader3, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jtxtTktHeader4, -2, -1, -2).addComponent(this.lblTktHeader4, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jtxtTktHeader5, -2, -1, -2).addComponent(this.lblTktHeader5, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jtxtTktHeader6, -2, -1, -2).addComponent(this.lblTktHeader6, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.lblTktFooter1, -2, -1, -2).addComponent(this.jtxtTktFooter1, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.lblTktFooter2, -2, -1, -2).addComponent(this.jtxtTktFooter2, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.lblTktFooter3, -2, -1, -2).addComponent(this.jtxtTktFooter3, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.lblTktFooter4, -2, -1, -2).addComponent(this.jtxtTktFooter4, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.lblTktFooter5, -2, -1, -2).addComponent(this.jtxtTktFooter5, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.lblTktFooter6, -2, -1, -2).addComponent(this.jtxtTktFooter6, -2, -1, -2)).addContainerGap()));
    }
}

