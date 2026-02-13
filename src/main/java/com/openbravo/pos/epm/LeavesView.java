/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.epm;

import com.openbravo.basic.BasicException;
import com.openbravo.beans.JCalendarDialog;
import com.openbravo.data.gui.MessageInf;
import com.openbravo.data.loader.SentenceList;
import com.openbravo.data.user.DirtyManager;
import com.openbravo.data.user.EditorRecord;
import com.openbravo.format.Formats;
import com.openbravo.pos.epm.DataLogicPresenceManagement;
import com.openbravo.pos.epm.JEmployeeFinder;
import com.openbravo.pos.epm.LeavesInfo;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.AppView;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;

public final class LeavesView
extends JPanel
implements EditorRecord {
    private Object m_oId;
    private Object m_employeeid;
    private SentenceList<LeavesInfo> m_sentcat;
    private DirtyManager m_Dirty;
    private DataLogicPresenceManagement dlPresenceManagement;
    private JButton btnEmployee;
    private JButton btnEndDate;
    private JButton btnStartDate;
    private JScrollPane jScrollPane1;
    private JLabel m_EndDate;
    private JLabel m_Name;
    private JLabel m_Notes;
    private JLabel m_StartDate;
    private JTextField m_jEmployeeName;
    private JTextField m_jEndDate;
    private JTextArea m_jLeaveNote;
    private JTextField m_jStartDate;

    public LeavesView(AppView app, DirtyManager dirty) {
        this.dlPresenceManagement = (DataLogicPresenceManagement)app.getBean("com.openbravo.pos.epm.DataLogicPresenceManagement");
        this.initComponents();
        this.m_sentcat = this.dlPresenceManagement.getLeavesList();
        this.m_Dirty = dirty;
        this.m_jEmployeeName.getDocument().addDocumentListener(dirty);
        this.m_jStartDate.getDocument().addDocumentListener(dirty);
        this.m_jEndDate.getDocument().addDocumentListener(dirty);
        this.m_jLeaveNote.getDocument().addDocumentListener(dirty);
        this.writeValueEOF();
    }

    void activate() throws BasicException {
        List<LeavesInfo> a = this.m_sentcat.list();
        a.add(0, null);
    }

    @Override
    public void writeValueEOF() {
        this.m_oId = null;
        this.m_jEmployeeName.setText(null);
        this.m_jStartDate.setText(null);
        this.m_jEndDate.setText(null);
        this.m_jLeaveNote.setText(null);
        this.m_jEmployeeName.setEditable(false);
        this.m_jStartDate.setEnabled(false);
        this.m_jEndDate.setEnabled(false);
        this.m_jLeaveNote.setEnabled(false);
    }

    @Override
    public void writeValueInsert() {
        this.m_oId = null;
        this.m_jEmployeeName.setText(null);
        this.m_jStartDate.setText(null);
        this.m_jEndDate.setText(null);
        this.m_jLeaveNote.setText(null);
        this.m_jEmployeeName.setEditable(true);
        this.m_jStartDate.setEnabled(true);
        this.m_jEndDate.setEnabled(true);
        this.m_jLeaveNote.setEnabled(true);
    }

    @Override
    public void writeValueEdit(Object value) {
        Object[] leaves = (Object[])value;
        this.m_oId = leaves[0];
        this.m_employeeid = leaves[1];
        this.m_jEmployeeName.setText((String)leaves[2]);
        this.m_jStartDate.setText(Formats.TIMESTAMP.formatValue((Date)leaves[3]));
        this.m_jEndDate.setText(Formats.TIMESTAMP.formatValue((Date)leaves[4]));
        this.m_jLeaveNote.setText((String)leaves[5]);
        this.m_jEmployeeName.setEditable(true);
        this.m_jStartDate.setEnabled(true);
        this.m_jEndDate.setEnabled(true);
        this.m_jLeaveNote.setEnabled(true);
    }

    @Override
    public void writeValueDelete(Object value) {
        Object[] leaves = (Object[])value;
        this.m_oId = leaves[0];
        this.m_employeeid = leaves[1];
        this.m_jEmployeeName.setText((String)leaves[2]);
        this.m_jStartDate.setText(Formats.TIMESTAMP.formatValue((Date)leaves[3]));
        this.m_jEndDate.setText(Formats.TIMESTAMP.formatValue((Date)leaves[4]));
        this.m_jLeaveNote.setText((String)leaves[5]);
        this.m_jEmployeeName.setEditable(false);
        this.m_jStartDate.setEnabled(false);
        this.m_jEndDate.setEnabled(false);
        this.m_jLeaveNote.setEnabled(false);
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
        Object[] leaves = new Object[]{this.m_oId == null ? UUID.randomUUID().toString() : this.m_oId, this.m_employeeid, this.m_jEmployeeName.getText(), (Date)Formats.TIMESTAMP.parseValue(this.m_jStartDate.getText()), (Date)Formats.TIMESTAMP.parseValue(this.m_jEndDate.getText()), this.m_jLeaveNote.getText()};
        boolean isCheckedIn = this.dlPresenceManagement.IsCheckedIn((String)this.m_employeeid);
        Date startDate = (Date)Formats.TIMESTAMP.parseValue(this.m_jStartDate.getText());
        Date endDate = (Date)Formats.TIMESTAMP.parseValue(this.m_jEndDate.getText());
        Date systemDate = new Date();
        if (isCheckedIn && startDate.before(systemDate) && endDate.after(systemDate)) {
            this.dlPresenceManagement.BlockEmployee((String)this.m_employeeid);
        }
        return leaves;
    }

    private boolean IsValidEndDate(Date date) {
        Date systemDate = new Date();
        if (!this.m_jStartDate.getText().equals("")) {
            try {
                Date startdate = (Date)Formats.TIMESTAMP.parseValue(this.m_jStartDate.getText());
                return startdate.before(date) || startdate.getDate() == date.getDate() && startdate.getMonth() == date.getMonth() && startdate.getYear() == date.getYear();
            }
            catch (BasicException basicException) {
                // empty catch block
            }
        }
        return systemDate.before(date) || systemDate.getDate() == date.getDate() && systemDate.getMonth() == date.getMonth() && systemDate.getYear() == date.getYear();
    }

    private boolean IsValidStartDate(Date date) {
        Date systemDate = new Date();
        boolean validEndDate = true;
        if (!this.m_jEndDate.getText().equals("")) {
            try {
                Date enddate = (Date)Formats.TIMESTAMP.parseValue(this.m_jEndDate.getText());
                validEndDate = enddate.after(date) || enddate.getDate() == date.getDate() && enddate.getMonth() == date.getMonth() && enddate.getYear() == date.getYear();
            }
            catch (BasicException basicException) {
                // empty catch block
            }
        }
        return validEndDate && (systemDate.before(date) || systemDate.getDate() == date.getDate() && systemDate.getMonth() == date.getMonth() && systemDate.getYear() == date.getYear());
    }

    private void initComponents() {
        this.m_jEmployeeName = new JTextField();
        this.jScrollPane1 = new JScrollPane();
        this.m_jLeaveNote = new JTextArea();
        this.m_Name = new JLabel();
        this.m_StartDate = new JLabel();
        this.m_EndDate = new JLabel();
        this.m_jStartDate = new JTextField();
        this.m_Notes = new JLabel();
        this.btnEmployee = new JButton();
        this.btnEndDate = new JButton();
        this.btnStartDate = new JButton();
        this.m_jEndDate = new JTextField();
        this.m_jEmployeeName.setEditable(false);
        this.m_jEmployeeName.setFont(new Font("Arial", 0, 12));
        this.m_jEmployeeName.setPreferredSize(new Dimension(0, 30));
        this.m_jLeaveNote.setColumns(20);
        this.m_jLeaveNote.setFont(new Font("Monospaced", 0, 14));
        this.m_jLeaveNote.setLineWrap(true);
        this.m_jLeaveNote.setRows(5);
        this.jScrollPane1.setViewportView(this.m_jLeaveNote);
        this.m_Name.setFont(new Font("Arial", 0, 12));
        ResourceBundle bundle = ResourceBundle.getBundle("pos_messages");
        this.m_Name.setText(bundle.getString("label.epm.employee"));
        this.m_Name.setPreferredSize(new Dimension(0, 30));
        this.m_StartDate.setFont(new Font("Arial", 0, 12));
        this.m_StartDate.setText(AppLocal.getIntString("label.epm.startdate"));
        this.m_StartDate.setPreferredSize(new Dimension(0, 30));
        this.m_EndDate.setFont(new Font("Arial", 0, 12));
        this.m_EndDate.setText(AppLocal.getIntString("label.epm.enddate"));
        this.m_EndDate.setPreferredSize(new Dimension(0, 30));
        this.m_jStartDate.setFont(new Font("Arial", 0, 12));
        this.m_jStartDate.setPreferredSize(new Dimension(0, 30));
        this.m_Notes.setFont(new Font("Arial", 0, 12));
        this.m_Notes.setText(AppLocal.getIntString("label.epm.notes"));
        this.m_Notes.setPreferredSize(new Dimension(0, 30));
        this.btnEmployee.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/user_sml.png")));
        this.btnEmployee.setFocusPainted(false);
        this.btnEmployee.setFocusable(false);
        this.btnEmployee.setMaximumSize(new Dimension(57, 33));
        this.btnEmployee.setMinimumSize(new Dimension(57, 33));
        this.btnEmployee.setPreferredSize(new Dimension(80, 45));
        this.btnEmployee.setRequestFocusEnabled(false);
        this.btnEmployee.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                LeavesView.this.btnEmployeeActionPerformed(evt);
            }
        });
        this.btnEndDate.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/date.png")));
        this.btnEndDate.setPreferredSize(new Dimension(80, 45));
        this.btnEndDate.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                LeavesView.this.btnEndDateActionPerformed(evt);
            }
        });
        this.btnStartDate.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/date.png")));
        this.btnStartDate.setPreferredSize(new Dimension(80, 45));
        this.btnStartDate.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                LeavesView.this.btnStartDateActionPerformed(evt);
            }
        });
        this.m_jEndDate.setFont(new Font("Arial", 0, 12));
        this.m_jEndDate.setPreferredSize(new Dimension(0, 30));
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.m_Notes, GroupLayout.Alignment.TRAILING, -1, 126, Short.MAX_VALUE).addComponent(this.m_EndDate, -1, 126, Short.MAX_VALUE).addComponent(this.m_StartDate, -1, 126, Short.MAX_VALUE).addComponent(this.m_Name, -1, 126, Short.MAX_VALUE)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.m_jEndDate, -1, -1, Short.MAX_VALUE).addComponent(this.m_jStartDate, -1, -1, Short.MAX_VALUE).addComponent(this.m_jEmployeeName, -1, 172, Short.MAX_VALUE)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.btnStartDate, -1, -1, Short.MAX_VALUE).addComponent(this.btnEmployee, -1, -1, Short.MAX_VALUE).addComponent(this.btnEndDate, -1, -1, Short.MAX_VALUE))).addComponent(this.jScrollPane1, -1, 258, Short.MAX_VALUE)).addGap(77, 77, 77)));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.m_Name, -2, -1, -2).addComponent(this.m_jEmployeeName, -2, -1, -2)).addComponent(this.btnEmployee, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.m_StartDate, -2, -1, -2).addComponent(this.m_jStartDate, -2, -1, -2)).addComponent(this.btnStartDate, -2, -1, -2)).addGap(18, 18, 18).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.btnEndDate, -2, -1, -2).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.m_EndDate, -2, -1, -2).addComponent(this.m_jEndDate, -2, -1, -2))).addGap(18, 18, Short.MAX_VALUE).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.m_Notes, -2, 25, -2).addComponent(this.jScrollPane1, -2, 141, -2)).addContainerGap(-1, Short.MAX_VALUE)));
    }

    private void btnEmployeeActionPerformed(ActionEvent evt) {
        JEmployeeFinder finder = JEmployeeFinder.getEmployeeFinder(this, this.dlPresenceManagement);
        finder.search(null);
        finder.setVisible(true);
        try {
            this.m_jEmployeeName.setText(finder.getSelectedEmployee() == null ? null : this.dlPresenceManagement.loadEmployeeExt(finder.getSelectedEmployee().getId()).toString());
            this.m_employeeid = finder.getSelectedEmployee() == null ? null : finder.getSelectedEmployee().getId();
        }
        catch (BasicException e) {
            MessageInf msg = new MessageInf(-33554432, AppLocal.getIntString("message.cannotfindemployee"), e);
            msg.show(this);
        }
    }

    private void btnEndDateActionPerformed(ActionEvent evt) {
        Date date;
        try {
            date = (Date)Formats.TIMESTAMP.parseValue(this.m_jEndDate.getText());
        }
        catch (BasicException e) {
            date = null;
        }
        date = JCalendarDialog.showCalendarTimeHours(this, date);
        if (date != null) {
            if (this.IsValidEndDate(date)) {
                this.m_jEndDate.setText(Formats.TIMESTAMP.formatValue(date));
            } else {
                MessageInf msg = new MessageInf(-33554432, AppLocal.getIntString("message.invalidenddate"));
                msg.show(this);
            }
        }
    }

    private void btnStartDateActionPerformed(ActionEvent evt) {
        Date date;
        try {
            date = (Date)Formats.TIMESTAMP.parseValue(this.m_jStartDate.getText());
        }
        catch (BasicException e) {
            date = null;
        }
        date = JCalendarDialog.showCalendarTimeHours(this, date);
        if (date != null) {
            if (this.IsValidStartDate(date)) {
                this.m_jStartDate.setText(Formats.TIMESTAMP.formatValue(date));
            } else {
                MessageInf msg = new MessageInf(-33554432, AppLocal.getIntString("message.invalidstartdate"));
                msg.show(this);
            }
        }
    }
}

