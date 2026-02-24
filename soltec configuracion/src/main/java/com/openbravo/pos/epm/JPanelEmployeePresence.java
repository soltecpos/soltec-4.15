/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.epm;

import com.openbravo.basic.BasicException;
import com.openbravo.beans.JFlowPanel;
import com.openbravo.data.gui.MessageInf;
import com.openbravo.format.Formats;
import com.openbravo.pos.epm.Break;
import com.openbravo.pos.epm.DataLogicPresenceManagement;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.forms.BeanFactoryApp;
import com.openbravo.pos.forms.BeanFactoryException;
import com.openbravo.pos.forms.JPanelView;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle;
import javax.swing.UIManager;

public class JPanelEmployeePresence
extends JPanel
implements JPanelView,
BeanFactoryApp {
    private AppView app;
    private DataLogicPresenceManagement dlpresencemanagement;
    private JFlowPanel jBreaks;
    private JButton btnCheckIn;
    private JButton btnCheckOut;
    private JScrollPane jScrollPane1;
    private JLabel message;

    public JPanelEmployeePresence() {
        this.initComponents();
        this.setVisible(true);
    }

    @Override
    public void init(AppView app) throws BeanFactoryException {
        this.app = app;
        this.dlpresencemanagement = (DataLogicPresenceManagement)app.getBean("com.openbravo.pos.epm.DataLogicPresenceManagement");
    }

    private void listBreaks() {
        try {
            this.jScrollPane1.getViewport().setView(null);
            this.jBreaks = new JFlowPanel();
            this.jBreaks.applyComponentOrientation(this.getComponentOrientation());
            List breaks = this.dlpresencemanagement.listBreaksVisible();
            for (int i = 0; i < breaks.size(); ++i) {
                Break m_break = (Break)breaks.get(i);
                JButton btn = new JButton(new BreakAction(m_break));
                btn.applyComponentOrientation(this.getComponentOrientation());
                btn.setFocusPainted(false);
                btn.setFocusable(false);
                btn.setRequestFocusEnabled(false);
                btn.setHorizontalAlignment(10);
                btn.setMaximumSize(new Dimension(190, 50));
                btn.setPreferredSize(new Dimension(190, 50));
                btn.setMinimumSize(new Dimension(190, 50));
                this.jBreaks.add(btn);
            }
            this.jScrollPane1.getViewport().setView(this.jBreaks);
        }
        catch (BasicException basicException) {
            // empty catch block
        }
    }

    @Override
    public String getTitle() {
        return AppLocal.getIntString("Menu.CheckInCheckOut");
    }

    @Override
    public void activate() throws BasicException {
        boolean isOnLeave = this.dlpresencemanagement.IsOnLeave(this.app.getAppUserView().getUser().getId());
        this.listBreaks();
        if (isOnLeave) {
            this.message.setText(this.app.getAppUserView().getUser().getName() + " " + AppLocal.getIntString("message.leavecontrol"));
            this.LeaveAction();
        } else {
            boolean isCheckedIn = this.dlpresencemanagement.IsCheckedIn(this.app.getAppUserView().getUser().getId());
            if (isCheckedIn) {
                Date lastCheckIn = this.dlpresencemanagement.GetLastCheckIn(this.app.getAppUserView().getUser().getId());
                this.message.setText(this.app.getAppUserView().getUser().getName() + " " + AppLocal.getIntString("message.checkedin") + " " + Formats.TIMESTAMP.formatValue(lastCheckIn));
                this.CheckInAction();
            } else {
                Date lastCheckOut = this.dlpresencemanagement.GetLastCheckOut(this.app.getAppUserView().getUser().getId());
                if (lastCheckOut != null) {
                    this.message.setText(this.app.getAppUserView().getUser().getName() + " " + AppLocal.getIntString("message.checkedout") + " " + Formats.TIMESTAMP.formatValue(lastCheckOut));
                } else {
                    this.message.setText(this.app.getAppUserView().getUser().getName() + " " + AppLocal.getIntString("message.noshift"));
                }
                this.CheckOutAction();
            }
            boolean isOnBreak = this.dlpresencemanagement.IsOnBreak(this.app.getAppUserView().getUser().getId());
            if (isOnBreak) {
                Object[] LastBreak = this.dlpresencemanagement.GetLastBreak(this.app.getAppUserView().getUser().getId());
                this.message.setText(this.app.getAppUserView().getUser().getName() + " " + AppLocal.getIntString("message.leavefor") + " " + (String)LastBreak[0] + " " + AppLocal.getIntString("message.at") + " " + Formats.TIMESTAMP.formatValue((Date)LastBreak[1]));
                this.BreakAction();
            }
        }
    }

    @Override
    public boolean deactivate() {
        return true;
    }

    @Override
    public JComponent getComponent() {
        return this;
    }

    @Override
    public Object getBean() {
        return this;
    }

    private void CheckInAction() {
        this.btnCheckIn.setEnabled(false);
        this.btnCheckOut.setEnabled(true);
        this.jBreaks.setEnabled(true);
    }

    private void CheckOutAction() {
        this.btnCheckIn.setEnabled(true);
        this.btnCheckOut.setEnabled(false);
        this.jBreaks.setEnabled(false);
    }

    private void BreakAction() {
        this.btnCheckIn.setEnabled(true);
        this.btnCheckOut.setEnabled(true);
        this.jBreaks.setEnabled(false);
    }

    private void LeaveAction() {
        this.btnCheckIn.setEnabled(false);
        this.btnCheckOut.setEnabled(false);
        this.jBreaks.setEnabled(false);
    }

    private void initComponents() {
        this.btnCheckIn = new JButton();
        this.btnCheckOut = new JButton();
        this.jScrollPane1 = new JScrollPane();
        this.message = new JLabel();
        this.setPreferredSize(new Dimension(0, 45));
        this.btnCheckIn.setFont(new Font("Arial", 0, 12));
        this.btnCheckIn.setText("Check In");
        this.btnCheckIn.setMaximumSize(new Dimension(85, 23));
        this.btnCheckIn.setPreferredSize(new Dimension(0, 45));
        this.btnCheckIn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPanelEmployeePresence.this.btnCheckInActionPerformed(evt);
            }
        });
        this.btnCheckOut.setFont(new Font("Arial", 0, 12));
        this.btnCheckOut.setText("Check Out");
        this.btnCheckOut.setPreferredSize(new Dimension(0, 45));
        this.btnCheckOut.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPanelEmployeePresence.this.btnCheckOutActionPerformed(evt);
            }
        });
        this.jScrollPane1.setHorizontalScrollBarPolicy(31);
        this.jScrollPane1.setVerticalScrollBarPolicy(22);
        this.jScrollPane1.setFont(new Font("Arial", 0, 12));
        this.jScrollPane1.setPreferredSize(new Dimension(570, 120));
        this.message.setFont(new Font("Arial", 0, 14));
        this.message.setHorizontalAlignment(0);
        this.message.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(UIManager.getDefaults().getColor("Button.darkShadow")), BorderFactory.createEmptyBorder(1, 4, 1, 4)));
        this.message.setOpaque(true);
        this.message.setPreferredSize(new Dimension(160, 25));
        this.message.setRequestFocusEnabled(false);
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.btnCheckIn, -2, 256, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addComponent(this.btnCheckOut, -2, 248, -2)).addComponent(this.jScrollPane1, -1, -1, Short.MAX_VALUE).addComponent(this.message, GroupLayout.Alignment.TRAILING, -1, -1, Short.MAX_VALUE)).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.message, -2, 50, -2).addGap(18, 18, 18).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.btnCheckIn, -2, 49, -2).addComponent(this.btnCheckOut, -2, 49, -2)).addGap(18, 18, 18).addComponent(this.jScrollPane1, -2, -1, -2).addContainerGap(80, Short.MAX_VALUE)));
    }

    private void btnCheckInActionPerformed(ActionEvent evt) {
        try {
            boolean isOnBreak = this.dlpresencemanagement.IsOnBreak(this.app.getAppUserView().getUser().getId());
            if (isOnBreak) {
                this.dlpresencemanagement.EndBreak(this.app.getAppUserView().getUser().getId());
                this.message.setText(this.app.getAppUserView().getUser().getName() + AppLocal.getIntString("message.breakoverandcheckedin") + " " + Formats.TIMESTAMP.formatValue(new Date()));
            } else {
                this.dlpresencemanagement.CheckIn(this.app.getAppUserView().getUser().getId());
                this.message.setText(this.app.getAppUserView().getUser().getName() + " " + AppLocal.getIntString("message.checkedin") + " " + Formats.TIMESTAMP.formatValue(new Date()));
            }
        }
        catch (BasicException ex) {
            MessageInf msg = new MessageInf(-33554432, AppLocal.getIntString("message.cannotcheckin"));
            msg.show(this);
        }
        this.CheckInAction();
    }

    private void btnCheckOutActionPerformed(ActionEvent evt) {
        try {
            boolean isOnBreak = this.dlpresencemanagement.IsOnBreak(this.app.getAppUserView().getUser().getId());
            if (isOnBreak) {
                this.dlpresencemanagement.EndBreak(this.app.getAppUserView().getUser().getId());
                this.message.setText(this.app.getAppUserView().getUser().getName() + " " + AppLocal.getIntString("message.breakoverandcheckedout") + " " + Formats.TIMESTAMP.formatValue(new Date()));
            } else {
                this.message.setText(this.app.getAppUserView().getUser().getName() + " " + AppLocal.getIntString("message.checkedout") + " " + Formats.TIMESTAMP.formatValue(new Date()));
            }
            this.dlpresencemanagement.CheckOut(this.app.getAppUserView().getUser().getId());
        }
        catch (BasicException ex) {
            MessageInf msg = new MessageInf(-33554432, AppLocal.getIntString("message.cannotcheckout"));
            msg.show(this);
        }
        this.CheckOutAction();
    }

    private class BreakAction
    extends AbstractAction {
        private Break m_break;

        public BreakAction(Break aBreak) {
            this.m_break = aBreak;
            this.putValue("Name", this.m_break.getName());
        }

        public Break getBreak() {
            return this.m_break;
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            try {
                JPanelEmployeePresence.this.dlpresencemanagement.StartBreak(JPanelEmployeePresence.this.app.getAppUserView().getUser().getId(), this.m_break.getId());
                JPanelEmployeePresence.this.message.setText(JPanelEmployeePresence.this.app.getAppUserView().getUser().getName() + " " + AppLocal.getIntString("message.leavefor") + " " + this.m_break.getName() + " " + AppLocal.getIntString("message.at") + " " + Formats.TIMESTAMP.formatValue(new Date()));
                JPanelEmployeePresence.this.BreakAction();
            }
            catch (BasicException ex) {
                JPanelEmployeePresence.this.message.setText(AppLocal.getIntString("message.probleminbreak"));
            }
        }
    }
}

