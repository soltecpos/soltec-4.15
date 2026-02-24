/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.AbstractListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.ListModel;

public class JListData
extends JDialog {
    private Object m_selected;
    private JButton jButton1;
    private JButton jButton2;
    private JPanel jPanel1;
    private JScrollPane jScrollPane1;
    private JToolBar jToolBar1;
    private JButton m_jCancel;
    private JList<Object> m_jData;
    private JButton m_jOK;

    public JListData(Frame parent) {
        super(parent, true);
        this.initComponents();
        this.getRootPane().setDefaultButton(this.m_jOK);
    }

    public Object showList(List<?> data) {
        return this.showList(new MyListData(data));
    }

    public Object showList(ListModel<Object> model) {
        this.m_jData.setModel(model);
        this.m_selected = null;
        this.setVisible(true);
        return this.m_selected;
    }

    private void initComponents() {
        this.jPanel1 = new JPanel();
        this.m_jOK = new JButton();
        this.m_jCancel = new JButton();
        this.jScrollPane1 = new JScrollPane();
        this.m_jData = new JList();
        this.jToolBar1 = new JToolBar();
        this.jButton1 = new JButton();
        this.jButton2 = new JButton();
        this.setDefaultCloseOperation(2);
        this.jPanel1.setLayout(new FlowLayout(2));
        this.m_jOK.setText("Accept");
        this.m_jOK.setMaximumSize(new Dimension(65, 33));
        this.m_jOK.setMinimumSize(new Dimension(65, 33));
        this.m_jOK.setPreferredSize(new Dimension(65, 33));
        this.m_jOK.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JListData.this.m_jOKActionPerformed(evt);
            }
        });
        this.jPanel1.add(this.m_jOK);
        this.m_jCancel.setText("Cancel");
        this.m_jCancel.setMaximumSize(new Dimension(65, 33));
        this.m_jCancel.setMinimumSize(new Dimension(65, 33));
        this.m_jCancel.setPreferredSize(new Dimension(65, 33));
        this.m_jCancel.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JListData.this.m_jCancelActionPerformed(evt);
            }
        });
        this.jPanel1.add(this.m_jCancel);
        this.getContentPane().add((Component)this.jPanel1, "South");
        this.m_jData.setFont(new Font("Arial", 0, 14));
        this.m_jData.setSelectionMode(0);
        this.jScrollPane1.setViewportView(this.m_jData);
        this.getContentPane().add((Component)this.jScrollPane1, "Center");
        this.jToolBar1.setFloatable(false);
        this.jButton1.setText("jButton1");
        this.jToolBar1.add(this.jButton1);
        this.jButton2.setText("jButton2");
        this.jToolBar1.add(this.jButton2);
        this.getContentPane().add((Component)this.jToolBar1, "North");
        this.setSize(new Dimension(264, 337));
        this.setLocationRelativeTo(null);
    }

    private void m_jCancelActionPerformed(ActionEvent evt) {
        this.dispose();
    }

    private void m_jOKActionPerformed(ActionEvent evt) {
        this.m_selected = this.m_jData.getSelectedValue();
        this.dispose();
    }

    private static class MyListData
    extends AbstractListModel<Object> {
        private List<?> m_data;

        public MyListData(List<?> data) {
            this.m_data = data;
        }

        @Override
        public Object getElementAt(int index) {
            return this.m_data.get(index);
        }

        @Override
        public int getSize() {
            return this.m_data.size();
        }
    }
}

