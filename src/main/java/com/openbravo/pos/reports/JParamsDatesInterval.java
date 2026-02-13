/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.reports;

import com.openbravo.basic.BasicException;
import com.openbravo.beans.JCalendarDialog;
import com.openbravo.data.loader.Datas;
import com.openbravo.data.loader.QBFCompareEnum;
import com.openbravo.data.loader.SerializerWrite;
import com.openbravo.data.loader.SerializerWriteBasic;
import com.openbravo.format.Formats;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.reports.ReportEditorCreator;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;

public class JParamsDatesInterval
extends JPanel
implements ReportEditorCreator {
    private JButton btnDateEnd;
    private JButton btnDateStart;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JTextField jTxtEndDate;
    private JTextField jTxtStartDate;

    public JParamsDatesInterval() {
        this.initComponents();
    }

    public void addActionListener(ActionListener l) {
        this.btnDateStart.addActionListener(l);
        this.btnDateEnd.addActionListener(l);
    }

    public void removeActionListener(ActionListener l) {
        this.btnDateStart.removeActionListener(l);
        this.btnDateEnd.removeActionListener(l);
    }

    public void setStartDate(Date d) {
        this.jTxtStartDate.setText(Formats.TIMESTAMP.formatValue(d));
    }

    public void setEndDate(Date d) {
        this.jTxtEndDate.setText(Formats.TIMESTAMP.formatValue(d));
    }

    @Override
    public void init(AppView app) {
    }

    @Override
    public void activate() throws BasicException {
    }

    @Override
    public SerializerWrite getSerializerWrite() {
        return new SerializerWriteBasic(Datas.OBJECT, Datas.TIMESTAMP, Datas.OBJECT, Datas.TIMESTAMP);
    }

    @Override
    public Component getComponent() {
        return this;
    }

    @Override
    public Object createValue() throws BasicException {
        Object startdate = Formats.TIMESTAMP.parseValue(this.jTxtStartDate.getText());
        Object enddate = Formats.TIMESTAMP.parseValue(this.jTxtEndDate.getText());
        return new Object[]{startdate == null ? QBFCompareEnum.COMP_NONE : QBFCompareEnum.COMP_GREATEROREQUALS, startdate, enddate == null ? QBFCompareEnum.COMP_NONE : QBFCompareEnum.COMP_LESS, enddate};
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.jTxtStartDate = new JTextField();
        this.jLabel2 = new JLabel();
        this.jTxtEndDate = new JTextField();
        this.btnDateStart = new JButton();
        this.btnDateEnd = new JButton();
        this.setFont(new Font("Arial", 0, 14));
        this.setPreferredSize(new Dimension(750, 67));
        this.jLabel1.setFont(new Font("Arial", 0, 14));
        this.jLabel1.setText(AppLocal.getIntString("label.StartDate"));
        this.jLabel1.setPreferredSize(new Dimension(110, 30));
        this.jTxtStartDate.setFont(new Font("Arial", 0, 14));
        this.jTxtStartDate.setPreferredSize(new Dimension(160, 30));
        this.jLabel2.setFont(new Font("Arial", 0, 14));
        this.jLabel2.setText(AppLocal.getIntString("label.EndDate"));
        this.jLabel2.setPreferredSize(new Dimension(110, 30));
        this.jTxtEndDate.setFont(new Font("Arial", 0, 14));
        this.jTxtEndDate.setPreferredSize(new Dimension(160, 30));
        this.btnDateStart.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/date.png")));
        this.btnDateStart.setToolTipText("Open Calendar");
        this.btnDateStart.setMaximumSize(new Dimension(40, 33));
        this.btnDateStart.setMinimumSize(new Dimension(40, 33));
        this.btnDateStart.setPreferredSize(new Dimension(80, 45));
        this.btnDateStart.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JParamsDatesInterval.this.btnDateStartActionPerformed(evt);
            }
        });
        this.btnDateEnd.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/date.png")));
        this.btnDateEnd.setToolTipText("Open Calendar");
        this.btnDateEnd.setMaximumSize(new Dimension(40, 33));
        this.btnDateEnd.setMinimumSize(new Dimension(40, 33));
        this.btnDateEnd.setPreferredSize(new Dimension(80, 45));
        this.btnDateEnd.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JParamsDatesInterval.this.btnDateEndActionPerformed(evt);
            }
        });
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jLabel1, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jTxtStartDate, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.btnDateStart, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jLabel2, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jTxtEndDate, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.btnDateEnd, -2, -1, -2).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.btnDateEnd, -2, -1, -2).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1, -2, -1, -2).addComponent(this.jTxtStartDate, -2, -1, -2)).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel2, -2, -1, -2).addComponent(this.jTxtEndDate, -2, -1, -2)).addComponent(this.btnDateStart, -2, -1, -2)).addContainerGap()));
    }

    private void btnDateStartActionPerformed(ActionEvent evt) {
        Date date;
        try {
            date = (Date)Formats.TIMESTAMP.parseValue(this.jTxtStartDate.getText());
        }
        catch (BasicException e) {
            date = null;
        }
        date = JCalendarDialog.showCalendarTimeHours(this, date);
        if (date != null) {
            this.jTxtStartDate.setText(Formats.TIMESTAMP.formatValue(date));
        }
    }

    private void btnDateEndActionPerformed(ActionEvent evt) {
        Date date;
        try {
            date = (Date)Formats.TIMESTAMP.parseValue(this.jTxtEndDate.getText());
        }
        catch (BasicException e) {
            date = null;
        }
        date = JCalendarDialog.showCalendarTimeHours(this, date);
        if (date != null) {
            this.jTxtEndDate.setText(Formats.TIMESTAMP.formatValue(date));
        }
    }
}

