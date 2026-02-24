/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.beans;

import com.openbravo.beans.LocaleResources;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

public class JCalendarPanel
extends JPanel {
    private static LocaleResources m_resources;
    private static GregorianCalendar m_CalendarHelper;
    private Date m_date;
    private JButtonDate[] m_ListDates;
    private JLabel[] m_jDays;
    private JButtonDate m_jCurrent;
    private JButtonDate m_jBtnMonthInc;
    private JButtonDate m_jBtnMonthDec;
    private JButtonDate m_jBtnYearInc;
    private JButtonDate m_jBtnYearDec;
    private JButtonDate m_jBtnToday;
    private DateFormat fmtMonthYear = new SimpleDateFormat("MMMMM yyyy");
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JPanel jPanel3;
    private JPanel m_jActions;
    private JPanel m_jDates;
    private JLabel m_jLblMonth;
    private JPanel m_jMonth;
    private JPanel m_jWeekDays;

    public JCalendarPanel() {
        this(new Date());
    }

    public JCalendarPanel(Date dDate) {
        if (m_resources == null) {
            m_resources = new LocaleResources();
            m_resources.addBundleName("beans_messages");
        }
        this.initComponents();
        this.initComponents2();
        this.m_date = dDate;
        this.renderMonth();
        this.renderDay();
    }

    public void setDate(Date dNewDate) {
        Date dOldDate = this.m_date;
        this.m_date = dNewDate;
        this.renderMonth();
        this.renderDay();
        this.firePropertyChange("Date", dOldDate, dNewDate);
    }

    public Date getDate() {
        return this.m_date;
    }

    @Override
    public void setEnabled(boolean bValue) {
        super.setEnabled(bValue);
        this.renderMonth();
        this.renderDay();
    }

    private void renderMonth() {
        JButtonDate jAux;
        for (int j = 0; j < 7; ++j) {
            this.m_jDays[j].setEnabled(this.isEnabled());
        }
        for (int i = 0; i < 42; ++i) {
            jAux = this.m_ListDates[i];
            jAux.DateInf = null;
            jAux.setEnabled(false);
            jAux.setText(null);
            jAux.setForeground((Color)UIManager.getDefaults().get("TextPane.foreground"));
            jAux.setBackground((Color)UIManager.getDefaults().get("TextPane.background"));
            jAux.setBorder(null);
        }
        if (this.m_date == null) {
            this.m_jLblMonth.setEnabled(this.isEnabled());
            this.m_jLblMonth.setText(null);
        } else {
            m_CalendarHelper.setTime(this.m_date);
            this.m_jLblMonth.setEnabled(this.isEnabled());
            this.m_jLblMonth.setText(this.fmtMonthYear.format(m_CalendarHelper.getTime()));
            int iCurrentMonth = m_CalendarHelper.get(2);
            m_CalendarHelper.set(5, 1);
            while (m_CalendarHelper.get(2) == iCurrentMonth) {
                jAux = this.getLabelByDate(m_CalendarHelper.getTime());
                jAux.DateInf = m_CalendarHelper.getTime();
                jAux.setEnabled(this.isEnabled());
                jAux.setText(String.valueOf(m_CalendarHelper.get(5)));
                m_CalendarHelper.add(5, 1);
            }
        }
        this.m_jCurrent = null;
    }

    private void renderDay() {
        this.m_jBtnToday.setEnabled(this.isEnabled());
        if (this.m_date == null) {
            this.m_jBtnMonthDec.setEnabled(false);
            this.m_jBtnMonthInc.setEnabled(this.isEnabled());
            this.m_jBtnYearDec.setEnabled(this.isEnabled());
            this.m_jBtnYearInc.setEnabled(this.isEnabled());
        } else {
            m_CalendarHelper.setTime(this.m_date);
            m_CalendarHelper.add(2, -1);
            this.m_jBtnMonthDec.DateInf = m_CalendarHelper.getTime();
            this.m_jBtnMonthDec.setEnabled(this.isEnabled());
            m_CalendarHelper.add(2, 2);
            this.m_jBtnMonthInc.DateInf = m_CalendarHelper.getTime();
            this.m_jBtnMonthInc.setEnabled(this.isEnabled());
            m_CalendarHelper.setTime(this.m_date);
            m_CalendarHelper.add(1, -1);
            this.m_jBtnYearDec.DateInf = m_CalendarHelper.getTime();
            this.m_jBtnYearDec.setEnabled(this.isEnabled());
            m_CalendarHelper.add(1, 2);
            this.m_jBtnYearInc.DateInf = m_CalendarHelper.getTime();
            this.m_jBtnYearInc.setEnabled(this.isEnabled());
            if (this.m_jCurrent != null) {
                this.m_jCurrent.setForeground((Color)UIManager.getDefaults().get("TextPane.foreground"));
                this.m_jCurrent.setBackground((Color)UIManager.getDefaults().get("TextPane.background"));
                this.m_jCurrent.setBorder(null);
            }
            JButtonDate jAux = this.getLabelByDate(this.m_date);
            jAux.setBackground((Color)UIManager.getDefaults().get("TextPane.selectionBackground"));
            jAux.setForeground((Color)UIManager.getDefaults().get("TextPane.selectionForeground"));
            jAux.setBorder(new LineBorder((Color)UIManager.getDefaults().get("TitledBorder.titleColor")));
            this.m_jCurrent = jAux;
        }
    }

    private JButtonDate getLabelByDate(Date d) {
        GregorianCalendar oCalRender = new GregorianCalendar();
        oCalRender.setTime(d);
        int iDayOfMonth = oCalRender.get(5);
        oCalRender.set(5, 1);
        int iCol = oCalRender.get(7) - oCalRender.getFirstDayOfWeek();
        if (iCol < 0) {
            iCol += 7;
        }
        return this.m_ListDates[iCol + iDayOfMonth - 1];
    }

    private void initComponents2() {
        DateClick dateclick = new DateClick();
        this.m_jBtnYearDec = new JButtonDate(new ImageIcon(this.getClass().getResource("/com/openbravo/images/2leftarrow.png")), (ActionListener)dateclick);
        this.m_jBtnMonthDec = new JButtonDate(new ImageIcon(this.getClass().getResource("/com/openbravo/images/1leftarrow.png")), (ActionListener)dateclick);
        this.m_jBtnToday = new JButtonDate(m_resources.getString("button.Today"), (ActionListener)dateclick);
        this.m_jBtnMonthInc = new JButtonDate(new ImageIcon(this.getClass().getResource("/com/openbravo/images/1rightarrow.png")), (ActionListener)dateclick);
        this.m_jBtnYearInc = new JButtonDate(new ImageIcon(this.getClass().getResource("/com/openbravo/images/2rightarrow.png")), (ActionListener)dateclick);
        this.m_jBtnToday.DateInf = new Date();
        this.m_jActions.add(this.m_jBtnYearDec);
        this.m_jActions.add(this.m_jBtnMonthDec);
        this.m_jActions.add(this.m_jBtnToday);
        this.m_jActions.add(this.m_jBtnMonthInc);
        this.m_jActions.add(this.m_jBtnYearInc);
        this.m_ListDates = new JButtonDate[42];
        for (int i = 0; i < 42; ++i) {
            JButtonDate jAux = new JButtonDate(dateclick);
            jAux.setHorizontalAlignment(0);
            jAux.setText(null);
            jAux.setOpaque(true);
            jAux.setForeground((Color)UIManager.getDefaults().get("TextPane.foreground"));
            jAux.setBackground((Color)UIManager.getDefaults().get("TextPane.background"));
            jAux.setBorder(null);
            this.m_ListDates[i] = jAux;
            this.m_jDates.add(jAux);
        }
        this.m_jDays = new JLabel[7];
        for (int iHead = 0; iHead < 7; ++iHead) {
            JLabel JAuxHeader = new JLabel();
            JAuxHeader.setHorizontalAlignment(0);
            this.m_jDays[iHead] = JAuxHeader;
            this.m_jWeekDays.add(JAuxHeader);
        }
        SimpleDateFormat fmtWeekDay = new SimpleDateFormat("E");
        GregorianCalendar oCalRender = new GregorianCalendar();
        for (int j = 0; j < 7; ++j) {
            ((Calendar)oCalRender).add(5, 1);
            int iCol = oCalRender.get(7) - oCalRender.getFirstDayOfWeek();
            if (iCol < 0) {
                iCol += 7;
            }
            this.m_jDays[iCol].setText(fmtWeekDay.format(oCalRender.getTime()));
        }
    }

    private void initComponents() {
        this.jPanel1 = new JPanel();
        this.m_jMonth = new JPanel();
        this.m_jWeekDays = new JPanel();
        this.m_jDates = new JPanel();
        this.jPanel2 = new JPanel();
        this.m_jLblMonth = new JLabel();
        this.jPanel3 = new JPanel();
        this.m_jActions = new JPanel();
        this.setFont(new Font("Tahoma", 0, 12));
        this.setLayout(new BorderLayout());
        this.jPanel1.setBorder(BorderFactory.createEtchedBorder());
        this.jPanel1.setLayout(new BorderLayout());
        this.m_jMonth.setLayout(new BorderLayout());
        this.m_jWeekDays.setLayout(new GridLayout(1, 7));
        this.m_jMonth.add((Component)this.m_jWeekDays, "North");
        this.m_jDates.setBackground(UIManager.getDefaults().getColor("TextPane.background"));
        this.m_jDates.setFont(new Font("Arial", 0, 12));
        this.m_jDates.setLayout(new GridLayout(6, 7));
        this.m_jMonth.add((Component)this.m_jDates, "Center");
        this.jPanel1.add((Component)this.m_jMonth, "Center");
        this.m_jLblMonth.setFont(new Font("Dialog", 1, 14));
        this.jPanel2.add(this.m_jLblMonth);
        this.jPanel1.add((Component)this.jPanel2, "North");
        this.add((Component)this.jPanel1, "Center");
        this.jPanel3.setLayout(new BorderLayout());
        this.m_jActions.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        this.m_jActions.setLayout(new GridLayout(0, 1, 0, 5));
        this.jPanel3.add((Component)this.m_jActions, "North");
        this.add((Component)this.jPanel3, "After");
    }

    static {
        m_CalendarHelper = new GregorianCalendar();
    }

    private static class JButtonDate
    extends JButton {
        public Date DateInf;

        public JButtonDate(ActionListener datehandler) {
            this.initComponent();
            this.addActionListener(datehandler);
        }

        public JButtonDate(String sText, ActionListener datehandler) {
            super(sText);
            this.initComponent();
            this.addActionListener(datehandler);
        }

        public JButtonDate(Icon icon, ActionListener datehandler) {
            super(icon);
            this.initComponent();
            this.addActionListener(datehandler);
        }

        private void initComponent() {
            this.DateInf = null;
            this.setRequestFocusEnabled(false);
            this.setFocusPainted(false);
            this.setFocusable(false);
        }
    }

    private class DateClick
    implements ActionListener {
        private DateClick() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JButtonDate oLbl = (JButtonDate)e.getSource();
            if (oLbl.DateInf != null) {
                JCalendarPanel.this.setDate(oLbl.DateInf);
            }
        }
    }
}

