/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.epm;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.QBFCompareEnum;
import com.openbravo.data.user.EditorCreator;
import com.openbravo.data.user.ListProvider;
import com.openbravo.data.user.ListProviderCreator;
import com.openbravo.editor.JEditorKeys;
import com.openbravo.editor.JEditorString;
import com.openbravo.pos.epm.DataLogicPresenceManagement;
import com.openbravo.pos.epm.EmployeeInfo;
import com.openbravo.pos.epm.EmployeeRenderer;
import com.openbravo.pos.forms.AppLocal;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class JEmployeeFinder
extends JDialog
implements EditorCreator {
    private EmployeeInfo selectedEmployee;
    private ListProvider<EmployeeInfo> lpr;
    private JButton jButton1;
    private JButton jButton3;
    private JLabel jLabel5;
    private JList<EmployeeInfo> jListEmployees;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JPanel jPanel3;
    private JPanel jPanel4;
    private JPanel jPanel5;
    private JPanel jPanel6;
    private JPanel jPanel7;
    private JPanel jPanel8;
    private JScrollPane jScrollPane1;
    private JButton jcmdCancel;
    private JButton jcmdOK;
    private JEditorKeys m_jKeys;
    private JEditorString m_jtxtName;

    private JEmployeeFinder(Frame parent, boolean modal) {
        super(parent, modal);
    }

    private JEmployeeFinder(Dialog parent, boolean modal) {
        super(parent, modal);
    }

    public static JEmployeeFinder getEmployeeFinder(Component parent, DataLogicPresenceManagement dlPresenceManagement) {
        Window window = JEmployeeFinder.getWindow(parent);
        JEmployeeFinder myMsg = window instanceof Frame ? new JEmployeeFinder((Frame)window, true) : new JEmployeeFinder((Dialog)window, true);
        myMsg.init(dlPresenceManagement);
        myMsg.applyComponentOrientation(parent.getComponentOrientation());
        return myMsg;
    }

    public EmployeeInfo getSelectedEmployee() {
        return this.selectedEmployee;
    }

    private void init(DataLogicPresenceManagement dlPresenceManagement) {
        this.initComponents();
        this.jScrollPane1.getVerticalScrollBar().setPreferredSize(new Dimension(35, 35));
        this.m_jtxtName.addEditorKeys(this.m_jKeys);
        this.m_jtxtName.reset();
        this.lpr = new ListProviderCreator<EmployeeInfo>(dlPresenceManagement.getEmployeeList(), this);
        this.jListEmployees.setCellRenderer(new EmployeeRenderer());
        this.getRootPane().setDefaultButton(this.jcmdOK);
        this.selectedEmployee = null;
    }

    public void search(EmployeeInfo employee) {
        if (employee == null || employee.getName() == null || employee.getName().equals("")) {
            this.m_jtxtName.reset();
            this.cleanSearch();
        } else {
            this.m_jtxtName.setText(employee.getName());
            this.executeSearch();
        }
    }

    private void cleanSearch() {
        this.jListEmployees.setModel(new MyListData(new ArrayList<EmployeeInfo>()));
    }

    public void executeSearch() {
        try {
            this.jListEmployees.setModel(new MyListData(this.lpr.loadData()));
            if (this.jListEmployees.getModel().getSize() > 0) {
                this.jListEmployees.setSelectedIndex(0);
            }
        }
        catch (BasicException basicException) {
            // empty catch block
        }
    }

    @Override
    public Object createValue() throws BasicException {
        Object[] afilter = new Object[2];
        if (this.m_jtxtName.getText() == null || this.m_jtxtName.getText().equals("")) {
            afilter[0] = QBFCompareEnum.COMP_NONE;
            afilter[1] = null;
        } else {
            afilter[0] = QBFCompareEnum.COMP_RE;
            afilter[1] = "%" + this.m_jtxtName.getText() + "%";
        }
        return afilter;
    }

    private static Window getWindow(Component parent) {
        if (parent == null) {
            return new JFrame();
        }
        if (parent instanceof Frame || parent instanceof Dialog) {
            return (Window)parent;
        }
        return JEmployeeFinder.getWindow(parent.getParent());
    }

    private void initComponents() {
        this.jPanel2 = new JPanel();
        this.m_jKeys = new JEditorKeys();
        this.jPanel1 = new JPanel();
        this.jcmdCancel = new JButton();
        this.jcmdOK = new JButton();
        this.jPanel3 = new JPanel();
        this.jPanel5 = new JPanel();
        this.jPanel7 = new JPanel();
        this.jLabel5 = new JLabel();
        this.m_jtxtName = new JEditorString();
        this.jPanel6 = new JPanel();
        this.jButton1 = new JButton();
        this.jButton3 = new JButton();
        this.jPanel4 = new JPanel();
        this.jScrollPane1 = new JScrollPane();
        this.jListEmployees = new JList();
        this.jPanel8 = new JPanel();
        this.setDefaultCloseOperation(2);
        this.setTitle(AppLocal.getIntString("form.customertitle"));
        this.jPanel2.setLayout(new BorderLayout());
        this.jPanel2.add((Component)this.m_jKeys, "North");
        this.jcmdCancel.setFont(new Font("Arial", 0, 12));
        this.jcmdCancel.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/cancel.png")));
        this.jcmdCancel.setText(AppLocal.getIntString("button.cancel"));
        this.jcmdCancel.setFocusPainted(false);
        this.jcmdCancel.setFocusable(false);
        this.jcmdCancel.setMargin(new Insets(8, 16, 8, 16));
        this.jcmdCancel.setPreferredSize(new Dimension(110, 45));
        this.jcmdCancel.setRequestFocusEnabled(false);
        this.jcmdCancel.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JEmployeeFinder.this.jcmdCancelActionPerformed(evt);
            }
        });
        this.jPanel1.add(this.jcmdCancel);
        this.jcmdOK.setFont(new Font("Arial", 0, 12));
        this.jcmdOK.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/ok.png")));
        this.jcmdOK.setText(AppLocal.getIntString("button.OK"));
        this.jcmdOK.setEnabled(false);
        this.jcmdOK.setFocusPainted(false);
        this.jcmdOK.setFocusable(false);
        this.jcmdOK.setMargin(new Insets(8, 16, 8, 16));
        this.jcmdOK.setPreferredSize(new Dimension(110, 45));
        this.jcmdOK.setRequestFocusEnabled(false);
        this.jcmdOK.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JEmployeeFinder.this.jcmdOKActionPerformed(evt);
            }
        });
        this.jPanel1.add(this.jcmdOK);
        this.jPanel2.add((Component)this.jPanel1, "Last");
        this.getContentPane().add((Component)this.jPanel2, "After");
        this.jPanel3.setLayout(new BorderLayout());
        this.jPanel5.setLayout(new BorderLayout());
        this.jLabel5.setFont(new Font("Arial", 0, 12));
        this.jLabel5.setText(AppLocal.getIntString("label.epm.employee"));
        this.m_jtxtName.setFont(new Font("Arial", 0, 12));
        GroupLayout jPanel7Layout = new GroupLayout(this.jPanel7);
        this.jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(jPanel7Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel7Layout.createSequentialGroup().addContainerGap().addComponent(this.jLabel5, -2, 126, -2).addGap(18, 18, 18).addComponent(this.m_jtxtName, -2, 220, -2).addContainerGap(-1, Short.MAX_VALUE)));
        jPanel7Layout.setVerticalGroup(jPanel7Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel7Layout.createSequentialGroup().addContainerGap().addGroup(jPanel7Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel5, -2, 25, -2).addComponent(this.m_jtxtName, -2, 25, -2)).addContainerGap(-1, Short.MAX_VALUE)));
        this.jPanel5.add((Component)this.jPanel7, "Center");
        this.jButton1.setFont(new Font("Arial", 0, 12));
        this.jButton1.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/reload.png")));
        this.jButton1.setText(AppLocal.getIntString("button.clean"));
        this.jButton1.setPreferredSize(new Dimension(110, 45));
        this.jButton1.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JEmployeeFinder.this.jButton1ActionPerformed(evt);
            }
        });
        this.jPanel6.add(this.jButton1);
        this.jButton3.setFont(new Font("Arial", 0, 12));
        this.jButton3.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/ok.png")));
        this.jButton3.setText(AppLocal.getIntString("button.executefilter"));
        this.jButton3.setFocusPainted(false);
        this.jButton3.setFocusable(false);
        this.jButton3.setPreferredSize(new Dimension(110, 45));
        this.jButton3.setRequestFocusEnabled(false);
        this.jButton3.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JEmployeeFinder.this.jButton3ActionPerformed(evt);
            }
        });
        this.jPanel6.add(this.jButton3);
        this.jPanel5.add((Component)this.jPanel6, "South");
        this.jPanel3.add((Component)this.jPanel5, "First");
        this.jPanel4.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        this.jPanel4.setLayout(new BorderLayout());
        this.jListEmployees.setFont(new Font("Arial", 0, 12));
        this.jListEmployees.setFocusable(false);
        this.jListEmployees.setRequestFocusEnabled(false);
        this.jListEmployees.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent evt) {
                JEmployeeFinder.this.jListEmployeesMouseClicked(evt);
            }
        });
        this.jListEmployees.addListSelectionListener(new ListSelectionListener(){

            @Override
            public void valueChanged(ListSelectionEvent evt) {
                JEmployeeFinder.this.jListEmployeesValueChanged(evt);
            }
        });
        this.jScrollPane1.setViewportView(this.jListEmployees);
        this.jPanel4.add((Component)this.jScrollPane1, "Center");
        this.jPanel3.add((Component)this.jPanel4, "Center");
        this.jPanel8.setLayout(new BorderLayout());
        this.jPanel3.add((Component)this.jPanel8, "South");
        this.getContentPane().add((Component)this.jPanel3, "Center");
        this.setSize(new Dimension(683, 495));
        this.setLocationRelativeTo(null);
    }

    private void jcmdOKActionPerformed(ActionEvent evt) {
        this.selectedEmployee = this.jListEmployees.getSelectedValue();
        this.dispose();
    }

    private void jcmdCancelActionPerformed(ActionEvent evt) {
        this.dispose();
    }

    private void jButton3ActionPerformed(ActionEvent evt) {
        this.executeSearch();
    }

    private void jListEmployeesValueChanged(ListSelectionEvent evt) {
        this.jcmdOK.setEnabled(this.jListEmployees.getSelectedValue() != null);
    }

    private void jListEmployeesMouseClicked(MouseEvent evt) {
        if (evt.getClickCount() == 2) {
            this.selectedEmployee = this.jListEmployees.getSelectedValue();
            this.dispose();
        }
    }

    private void jButton1ActionPerformed(ActionEvent evt) {
        this.m_jtxtName.reset();
        this.cleanSearch();
    }

    private static class MyListData
    extends AbstractListModel<EmployeeInfo> {
        private List<EmployeeInfo> m_data;

        public MyListData(List<EmployeeInfo> data) {
            this.m_data = data;
        }

        @Override
        public EmployeeInfo getElementAt(int index) {
            return this.m_data.get(index);
        }

        @Override
        public int getSize() {
            return this.m_data.size();
        }
    }
}

