/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.beans;

import com.openbravo.beans.JClockPanel;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class JTimePanel
extends JPanel {
    public static final int BUTTONS_ALL = 3;
    public static final int BUTTONS_HOUR = 1;
    public static final int BUTTONS_MINUTE = 2;
    private DateFormat fmtTime = DateFormat.getTimeInstance(3);
    private JClockPanel m_jclock;
    private Date m_dMinDate;
    private Date m_dMaxDate;
    private JButtonDate m_jbtnplushour = null;
    private JButtonDate m_jbtnminushour = null;
    private JButtonDate m_jbtnplusfifteen = null;
    private JButtonDate m_jbtnminusfifteen = null;
    private JButtonDate m_jbtnplusminute = null;
    private JButtonDate m_jbtnminusminute = null;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JPanel m_jactions;
    private JLabel m_jlblSeparator;
    private JLabel m_jlblTime;
    private JLabel m_jlblTime2;
    private JPanel m_jtime;

    public JTimePanel() {
        this(null, 3);
    }

    public JTimePanel(Date dDate) {
        this(dDate, 3);
    }

    public JTimePanel(Date dDate, int iButtons) {
        GregorianCalendar c;
        this.initComponents();
        this.m_jclock = new JClockPanel(false);
        this.jPanel2.add((Component)this.m_jclock, "Center");
        SimpleDateFormat f = new SimpleDateFormat("H:mm");
        DateClick dateclick = new DateClick();
        if ((iButtons & 1) > 0) {
            c = new GregorianCalendar(1900, 0, 0, 1, 0);
            this.m_jbtnplushour = new JButtonDate(f.format(c.getTime()), new ImageIcon(this.getClass().getResource("/com/openbravo/images/2rightarrow.png")), dateclick);
            this.m_jactions.add(this.m_jbtnplushour);
        }
        if ((iButtons & 2) > 0) {
            c = new GregorianCalendar(1900, 0, 0, 0, 15);
            this.m_jbtnplusfifteen = new JButtonDate(f.format(c.getTime()), new ImageIcon(this.getClass().getResource("/com/openbravo/images/1rightarrow.png")), dateclick);
            this.m_jactions.add(this.m_jbtnplusfifteen);
        }
        if ((iButtons & 2) > 0) {
            c = new GregorianCalendar(1900, 0, 0, 0, 1);
            this.m_jbtnplusminute = new JButtonDate(f.format(c.getTime()), new ImageIcon(this.getClass().getResource("/com/openbravo/images/1rightarrow.png")), dateclick);
            this.m_jactions.add(this.m_jbtnplusminute);
        }
        if ((iButtons & 2) > 0) {
            c = new GregorianCalendar(1900, 0, 0, 0, 1);
            this.m_jbtnminusminute = new JButtonDate(f.format(c.getTime()), new ImageIcon(this.getClass().getResource("/com/openbravo/images/1leftarrow.png")), dateclick);
            this.m_jactions.add(this.m_jbtnminusminute);
        }
        if ((iButtons & 2) > 0) {
            c = new GregorianCalendar(1900, 0, 0, 0, 15);
            this.m_jbtnminusfifteen = new JButtonDate(f.format(c.getTime()), new ImageIcon(this.getClass().getResource("/com/openbravo/images/1leftarrow.png")), dateclick);
            this.m_jactions.add(this.m_jbtnminusfifteen);
        }
        if ((iButtons & 1) > 0) {
            c = new GregorianCalendar(1900, 0, 0, 1, 0);
            this.m_jbtnminushour = new JButtonDate(f.format(c.getTime()), new ImageIcon(this.getClass().getResource("/com/openbravo/images/2leftarrow.png")), dateclick);
            this.m_jactions.add(this.m_jbtnminushour);
        }
        this.m_dMinDate = null;
        this.m_dMaxDate = null;
        this.m_jclock.setTime(dDate);
        this.renderTime();
    }

    public void setDateMidNight() {
        this.setDate(new GregorianCalendar(1900, 0, 0, 0, 0).getTime());
    }

    public void setDate(Date dNewDate) {
        Date dOldDate = this.m_jclock.getTime();
        if ((dNewDate == null && dOldDate != null || dNewDate != null && !dNewDate.equals(dOldDate)) && this.checkDates(dNewDate)) {
            this.m_jclock.setTime(dNewDate);
            this.renderTime();
            this.firePropertyChange("Date", dOldDate, dNewDate);
        }
    }

    private boolean checkDates(Date dDate) {
        return dDate == null || (this.m_dMaxDate == null || this.m_dMaxDate.compareTo(dDate) > 0) && (this.m_dMinDate == null || this.m_dMinDate.compareTo(dDate) <= 0);
    }

    public Date getDate() {
        return this.m_jclock.getTime();
    }

    public void setCheckDates(Date dMinDate, Date dMaxDate) {
        this.m_dMinDate = dMinDate;
        this.m_dMaxDate = dMaxDate;
        this.setDate(null);
        this.renderTime();
    }

    @Override
    public void setEnabled(boolean bValue) {
        super.setEnabled(bValue);
        this.renderTime();
    }

    public void setPeriod(long period) {
        this.m_jclock.setPeriod(period);
        this.renderTime();
    }

    private void renderTime() {
        Date dDate = this.m_jclock.getTime();
        if (dDate == null) {
            if (this.m_jbtnplushour != null) {
                this.m_jbtnplushour.setEnabled(false);
            }
            if (this.m_jbtnminushour != null) {
                this.m_jbtnminushour.setEnabled(false);
            }
            if (this.m_jbtnplusfifteen != null) {
                this.m_jbtnplusfifteen.setEnabled(false);
            }
            if (this.m_jbtnminusfifteen != null) {
                this.m_jbtnminusfifteen.setEnabled(false);
            }
            if (this.m_jbtnplusminute != null) {
                this.m_jbtnplusminute.setEnabled(false);
            }
            if (this.m_jbtnminusminute != null) {
                this.m_jbtnminusminute.setEnabled(false);
            }
            this.m_jlblTime.setText("  ");
            this.m_jlblSeparator.setVisible(false);
            this.m_jlblTime2.setVisible(false);
            this.m_jtime.revalidate();
        } else {
            GregorianCalendar oCalRender = new GregorianCalendar();
            oCalRender.setTime(dDate);
            oCalRender.add(11, 1);
            if (this.m_jbtnplushour != null) {
                this.m_jbtnplushour.DateInf = oCalRender.getTime();
                this.m_jbtnplushour.setEnabled(this.isEnabled() && this.checkDates(oCalRender.getTime()));
            }
            oCalRender.add(11, -2);
            if (this.m_jbtnminushour != null) {
                this.m_jbtnminushour.DateInf = oCalRender.getTime();
                this.m_jbtnminushour.setEnabled(this.isEnabled() && this.checkDates(oCalRender.getTime()));
            }
            oCalRender.setTime(dDate);
            oCalRender.add(12, 15);
            if (this.m_jbtnplusfifteen != null) {
                this.m_jbtnplusfifteen.DateInf = oCalRender.getTime();
                this.m_jbtnplusfifteen.setEnabled(this.isEnabled() && this.checkDates(oCalRender.getTime()));
            }
            oCalRender.add(12, -30);
            if (this.m_jbtnminusfifteen != null) {
                this.m_jbtnminusfifteen.DateInf = oCalRender.getTime();
                this.m_jbtnminusfifteen.setEnabled(this.isEnabled() && this.checkDates(oCalRender.getTime()));
            }
            oCalRender.setTime(dDate);
            oCalRender.add(12, 1);
            if (this.m_jbtnplusminute != null) {
                this.m_jbtnplusminute.DateInf = oCalRender.getTime();
                this.m_jbtnplusminute.setEnabled(this.isEnabled() && this.checkDates(oCalRender.getTime()));
            }
            oCalRender.add(12, -2);
            if (this.m_jbtnminusminute != null) {
                this.m_jbtnminusminute.DateInf = oCalRender.getTime();
                this.m_jbtnminusminute.setEnabled(this.isEnabled() && this.checkDates(oCalRender.getTime()));
            }
            if (this.m_jclock.getPeriod() > 0L) {
                this.m_jlblTime.setText(this.fmtTime.format(dDate));
                this.m_jlblTime2.setText(this.fmtTime.format(new Date(dDate.getTime() + this.m_jclock.getPeriod())));
                this.m_jlblSeparator.setVisible(true);
                this.m_jlblTime2.setVisible(true);
                this.m_jtime.revalidate();
            } else {
                this.m_jlblTime.setText(this.fmtTime.format(dDate));
                this.m_jlblSeparator.setVisible(false);
                this.m_jlblTime2.setVisible(false);
                this.m_jtime.revalidate();
            }
        }
        this.m_jclock.setEnabled(this.isEnabled());
    }

    private void initComponents() {
        this.jPanel1 = new JPanel();
        this.m_jactions = new JPanel();
        this.jPanel2 = new JPanel();
        this.m_jtime = new JPanel();
        this.m_jlblTime = new JLabel();
        this.m_jlblSeparator = new JLabel();
        this.m_jlblTime2 = new JLabel();
        this.setLayout(new BorderLayout());
        this.jPanel1.setLayout(new BorderLayout());
        this.m_jactions.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        this.m_jactions.setLayout(new GridLayout(0, 1, 0, 5));
        this.jPanel1.add((Component)this.m_jactions, "North");
        this.add((Component)this.jPanel1, "After");
        this.jPanel2.setBorder(BorderFactory.createEtchedBorder());
        this.jPanel2.setFont(new Font("Arial", 0, 12));
        this.jPanel2.setLayout(new BorderLayout());
        this.m_jtime.add(this.m_jlblTime);
        this.m_jlblSeparator.setText(" - ");
        this.m_jtime.add(this.m_jlblSeparator);
        this.m_jtime.add(this.m_jlblTime2);
        this.jPanel2.add((Component)this.m_jtime, "North");
        this.add((Component)this.jPanel2, "Center");
    }

    private static class JButtonDate
    extends JButton {
        public Date DateInf;

        public JButtonDate(String sText, Icon ico, ActionListener datehandler) {
            super(sText, ico);
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
                JTimePanel.this.setDate(oLbl.DateInf);
            }
        }
    }
}

