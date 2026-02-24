/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.beans;

import com.openbravo.beans.DateUtils;
import com.openbravo.beans.JCalendarPanel;
import com.openbravo.beans.JTimePanel;
import com.openbravo.beans.LocaleResources;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class JCalendarDialog
extends JDialog {
    private static LocaleResources m_resources;
    private Date m_date;
    private JCalendarPanel myCalendar = null;
    private JTimePanel myTime = null;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JPanel jPanelGrid;
    private JButton jcmdCancel;
    private JButton jcmdOK;

    public JCalendarDialog(Frame parent, boolean modal) {
        super(parent, modal);
        if (m_resources == null) {
            m_resources = new LocaleResources();
            m_resources.addBundleName("beans_messages");
        }
    }

    public JCalendarDialog(Dialog parent, boolean modal) {
        super(parent, modal);
        if (m_resources == null) {
            m_resources = new LocaleResources();
            m_resources.addBundleName("beans_messages");
        }
    }

    private static Window getWindow(Component parent) {
        if (parent == null) {
            return new JFrame();
        }
        if (parent instanceof Frame || parent instanceof Dialog) {
            return (Window)parent;
        }
        return JCalendarDialog.getWindow(parent.getParent());
    }

    public static Date showCalendarTimeHours(Component parent, Date date) {
        return JCalendarDialog.internalCalendarTime(parent, date == null ? DateUtils.getToday() : date, true);
    }

    public static Date showCalendarTime(Component parent, Date date) {
        return JCalendarDialog.internalCalendarTime(parent, date == null ? DateUtils.getTodayMinutes() : date, true);
    }

    public static Date showCalendar(Component parent, Date date) {
        return JCalendarDialog.internalCalendarTime(parent, date == null ? DateUtils.getTodayMinutes() : date, false);
    }

    private static Date internalCalendarTime(Component parent, Date date, boolean bTimePanel) {
        Window window = JCalendarDialog.getWindow(parent);
        JCalendarDialog myMsg = window instanceof Frame ? new JCalendarDialog((Frame)window, true) : new JCalendarDialog((Dialog)window, true);
        myMsg.initComponents();
        Date d = date;
        int dialogwidth = 400;
        myMsg.myCalendar = new JCalendarPanel(d);
        myMsg.myCalendar.addPropertyChangeListener("Date", new JPanelCalendarChange(myMsg));
        myMsg.jPanelGrid.add(myMsg.myCalendar);
        if (bTimePanel) {
            myMsg.myTime = new JTimePanel(d);
            myMsg.myTime.addPropertyChangeListener("Date", new JPanelTimeChange(myMsg));
            myMsg.jPanelGrid.add(myMsg.myTime);
            dialogwidth += 400;
        }
        myMsg.getRootPane().setDefaultButton(myMsg.jcmdOK);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        myMsg.setBounds((screenSize.width - dialogwidth) / 2, (screenSize.height - 359) / 2, dialogwidth, 359);
        myMsg.m_date = null;
        myMsg.setVisible(true);
        return myMsg.m_date;
    }

    private void initComponents() {
        this.jPanel1 = new JPanel();
        this.jcmdOK = new JButton();
        this.jcmdCancel = new JButton();
        this.jPanel2 = new JPanel();
        this.jPanelGrid = new JPanel();
        this.setTitle(m_resources.getString("title.calendar"));
        this.setResizable(false);
        this.addWindowListener(new WindowAdapter(){

            @Override
            public void windowClosing(WindowEvent evt) {
                JCalendarDialog.this.closeWindow(evt);
            }
        });
        this.jPanel1.setLayout(new FlowLayout(2));
        this.jcmdOK.setFont(new Font("Arial", 0, 12));
        this.jcmdOK.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/ok.png")));
        this.jcmdOK.setText(m_resources.getString("button.ok"));
        this.jcmdOK.setMargin(new Insets(8, 16, 8, 16));
        this.jcmdOK.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JCalendarDialog.this.jcmdOKActionPerformed(evt);
            }
        });
        this.jPanel1.add(this.jcmdOK);
        this.jcmdCancel.setFont(new Font("Arial", 0, 12));
        this.jcmdCancel.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/cancel.png")));
        this.jcmdCancel.setText(m_resources.getString("button.cancel"));
        this.jcmdCancel.setMargin(new Insets(8, 16, 8, 16));
        this.jcmdCancel.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JCalendarDialog.this.jcmdCancelActionPerformed(evt);
            }
        });
        this.jPanel1.add(this.jcmdCancel);
        this.getContentPane().add((Component)this.jPanel1, "South");
        this.jPanel2.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        this.jPanel2.setLayout(new BorderLayout());
        this.jPanelGrid.setFont(new Font("Arial", 0, 12));
        this.jPanelGrid.setLayout(new GridLayout(1, 0, 5, 0));
        this.jPanel2.add((Component)this.jPanelGrid, "Center");
        this.getContentPane().add((Component)this.jPanel2, "Center");
    }

    private void jcmdOKActionPerformed(ActionEvent evt) {
        GregorianCalendar dateresult;
        GregorianCalendar date1 = new GregorianCalendar();
        date1.setTime(this.myCalendar.getDate());
        if (this.myTime == null) {
            dateresult = new GregorianCalendar(date1.get(1), date1.get(2), date1.get(5));
        } else {
            GregorianCalendar date2 = new GregorianCalendar();
            date2.setTime(this.myTime.getDate());
            dateresult = new GregorianCalendar(date1.get(1), date1.get(2), date1.get(5), date2.get(11), date2.get(12));
        }
        this.m_date = dateresult.getTime();
        this.setVisible(false);
        this.dispose();
    }

    private void jcmdCancelActionPerformed(ActionEvent evt) {
        this.setVisible(false);
        this.dispose();
    }

    private void closeWindow(WindowEvent evt) {
        this.setVisible(false);
        this.dispose();
    }

    private static class JPanelCalendarChange
    implements PropertyChangeListener {
        private JCalendarDialog m_me;

        public JPanelCalendarChange(JCalendarDialog me) {
            this.m_me = me;
        }

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            this.m_me.myTime.setDate(this.m_me.myCalendar.getDate());
        }
    }

    private static class JPanelTimeChange
    implements PropertyChangeListener {
        private JCalendarDialog m_me;

        public JPanelTimeChange(JCalendarDialog me) {
            this.m_me = me;
        }

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            this.m_me.myCalendar.setDate(this.m_me.myTime.getDate());
        }
    }
}

