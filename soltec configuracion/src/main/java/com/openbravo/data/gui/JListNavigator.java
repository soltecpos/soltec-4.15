/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.gui;

import com.openbravo.basic.BasicException;
import com.openbravo.data.gui.MessageInf;
import com.openbravo.data.loader.LocalRes;
import com.openbravo.data.user.BrowsableEditableData;
import com.openbravo.data.user.BrowseListener;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class JListNavigator<T>
extends JPanel
implements BrowseListener,
ListSelectionListener {
    protected BrowsableEditableData<T> m_bd;
    private JScrollPane jScrollPane1;
    private JList<T> m_jlist;

    public JListNavigator(BrowsableEditableData<T> bd) {
        this(bd, false);
    }

    public JListNavigator(BrowsableEditableData<T> bd, boolean bTouchable) {
        this.m_bd = bd;
        this.initComponents();
        this.jScrollPane1.getVerticalScrollBar().setPreferredSize(new Dimension(35, 35));
        this.jScrollPane1.getHorizontalScrollBar().setPreferredSize(new Dimension(35, 35));
        this.jScrollPane1.setHorizontalScrollBarPolicy(30);
        this.jScrollPane1.setVerticalScrollBarPolicy(20);
        this.m_jlist.addListSelectionListener(this);
        this.m_jlist.setModel(this.m_bd.getListModel());
        this.m_bd.addBrowseListener(this);
    }

    public void setCellRenderer(ListCellRenderer<? super T> cellRenderer) {
        this.m_jlist.setCellRenderer(cellRenderer);
    }

    @Override
    public void updateIndex(int iIndex, int iCounter) {
        if (iIndex >= 0 && iIndex < iCounter) {
            this.m_jlist.setSelectedIndex(iIndex);
        } else {
            this.m_jlist.setSelectedIndex(-1);
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent evt) {
        int i;
        if (!evt.getValueIsAdjusting() && (i = this.m_jlist.getSelectedIndex()) >= 0) {
            if (!this.m_bd.isAdjusting()) {
                try {
                    this.m_bd.moveTo(i);
                }
                catch (BasicException eD) {
                    MessageInf msg = new MessageInf(-67108864, LocalRes.getIntString("message.nomove"), eD);
                    msg.show(this);
                }
            }
            Rectangle oRect = this.m_jlist.getCellBounds(i, i);
            this.m_jlist.scrollRectToVisible(oRect);
        }
    }

    private void initComponents() {
        this.jScrollPane1 = new JScrollPane();
        this.m_jlist = new JList();
        this.setFont(new Font("Arial", 0, 12));
        this.setPreferredSize(new Dimension(300, 2));
        this.setLayout(new BorderLayout());
        this.jScrollPane1.setAutoscrolls(true);
        this.jScrollPane1.setFont(new Font("Arial", 0, 12));
        this.m_jlist.setFont(new Font("Arial", 0, 14));
        this.m_jlist.setSelectionMode(0);
        this.m_jlist.setFocusable(false);
        this.m_jlist.setRequestFocusEnabled(false);
        this.jScrollPane1.setViewportView(this.m_jlist);
        this.add((Component)this.jScrollPane1, "Center");
    }
}

