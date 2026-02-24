/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.admin;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.QBFCompareEnum;
import com.openbravo.data.user.EditorCreator;
import com.openbravo.data.user.ListProvider;
import com.openbravo.data.user.ListProviderCreator;
import com.openbravo.editor.JEditorKeys;
import com.openbravo.editor.JEditorString;
import com.openbravo.pos.admin.DataLogicAdmin;
import com.openbravo.pos.admin.PeopleInfo;
import com.openbravo.pos.admin.PeopleRenderer;
import com.openbravo.pos.forms.AppLocal;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
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
import java.util.ResourceBundle;
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
import javax.swing.LayoutStyle;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class JPeopleFinder
extends JDialog
implements EditorCreator {
    private PeopleInfo selectedPeople;
    private ListProvider<PeopleInfo> lpr;
    private JButton jButton1;
    private JButton jButton3;
    private JLabel jLblName;
    private JList<PeopleInfo> jListPeople;
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

    private JPeopleFinder(Frame parent, boolean modal) {
        super(parent, modal);
    }

    private JPeopleFinder(Dialog parent, boolean modal) {
        super(parent, modal);
    }

    public static JPeopleFinder getPeopleFinder(Component parent, DataLogicAdmin dlPeople) {
        Window window = JPeopleFinder.getWindow(parent);
        JPeopleFinder myMsg = window instanceof Frame ? new JPeopleFinder((Frame)window, true) : new JPeopleFinder((Dialog)window, true);
        myMsg.init(dlPeople);
        myMsg.applyComponentOrientation(parent.getComponentOrientation());
        return myMsg;
    }

    public PeopleInfo getSelectedPeople() {
        return this.selectedPeople;
    }

    private void init(DataLogicAdmin dlPeople) {
        this.initComponents();
        this.jScrollPane1.getVerticalScrollBar().setPreferredSize(new Dimension(35, 35));
        this.m_jtxtName.addEditorKeys(this.m_jKeys);
        this.m_jtxtName.reset();
        this.lpr = new ListProviderCreator<PeopleInfo>(dlPeople.getPeopleList(), this);
        this.jListPeople.setCellRenderer(new PeopleRenderer());
        this.getRootPane().setDefaultButton(this.jcmdOK);
        this.selectedPeople = null;
    }

    public void search(PeopleInfo people) {
        if (people == null || people.getName() == null || people.getName().equals("")) {
            this.m_jtxtName.reset();
            this.cleanSearch();
        } else {
            this.m_jtxtName.setText(people.getName());
            this.executeSearch();
        }
    }

    private void cleanSearch() {
        this.jListPeople.setModel(new MyListData(new ArrayList<PeopleInfo>()));
    }

    public void executeSearch() {
        try {
            this.jListPeople.setModel(new MyListData(this.lpr.loadData()));
            if (this.jListPeople.getModel().getSize() > 0) {
                this.jListPeople.setSelectedIndex(0);
            }
        }
        catch (BasicException basicException) {
            // empty catch block
        }
    }

    @Override
    public Object createValue() throws BasicException {
        Object[] afilter = new Object[12];
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
        return JPeopleFinder.getWindow(parent.getParent());
    }

    private void initComponents() {
        this.jPanel3 = new JPanel();
        this.jPanel5 = new JPanel();
        this.jPanel7 = new JPanel();
        this.jLblName = new JLabel();
        this.m_jtxtName = new JEditorString();
        this.jPanel4 = new JPanel();
        this.jScrollPane1 = new JScrollPane();
        this.jListPeople = new JList();
        this.jPanel6 = new JPanel();
        this.jButton1 = new JButton();
        this.jButton3 = new JButton();
        this.jPanel2 = new JPanel();
        this.m_jKeys = new JEditorKeys();
        this.jPanel8 = new JPanel();
        this.jPanel1 = new JPanel();
        this.jcmdCancel = new JButton();
        this.jcmdOK = new JButton();
        this.setDefaultCloseOperation(2);
        this.setTitle(AppLocal.getIntString("form.usertitle"));
        this.setCursor(new Cursor(0));
        this.setPreferredSize(new Dimension(650, 300));
        this.jPanel3.setPreferredSize(new Dimension(450, 231));
        this.jPanel3.setLayout(new BorderLayout());
        this.jPanel5.setLayout(new BorderLayout());
        this.jPanel7.setFont(new Font("Arial", 0, 12));
        this.jLblName.setFont(new Font("Arial", 0, 14));
        this.jLblName.setText(AppLocal.getIntString("label.prodname"));
        this.jLblName.setMaximumSize(new Dimension(60, 15));
        this.jLblName.setMinimumSize(new Dimension(60, 15));
        this.jLblName.setPreferredSize(new Dimension(110, 30));
        this.m_jtxtName.setFont(new Font("Arial", 0, 14));
        this.m_jtxtName.setPreferredSize(new Dimension(250, 30));
        GroupLayout jPanel7Layout = new GroupLayout(this.jPanel7);
        this.jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(jPanel7Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel7Layout.createSequentialGroup().addContainerGap().addComponent(this.jLblName, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.m_jtxtName, -2, -1, -2).addContainerGap()));
        jPanel7Layout.setVerticalGroup(jPanel7Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel7Layout.createSequentialGroup().addContainerGap().addGroup(jPanel7Layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.m_jtxtName, -2, -1, -2).addComponent(this.jLblName, -2, -1, -2))));
        this.m_jtxtName.getAccessibleContext().setAccessibleName("");
        this.jPanel5.add((Component)this.jPanel7, "Center");
        this.jPanel3.add((Component)this.jPanel5, "First");
        this.jPanel4.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        this.jPanel4.setLayout(new BorderLayout());
        this.jListPeople.setFont(new Font("Arial", 0, 12));
        this.jListPeople.setFocusable(false);
        this.jListPeople.setRequestFocusEnabled(false);
        this.jListPeople.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent evt) {
                JPeopleFinder.this.jListPeopleMouseClicked(evt);
            }
        });
        this.jListPeople.addListSelectionListener(new ListSelectionListener(){

            @Override
            public void valueChanged(ListSelectionEvent evt) {
                JPeopleFinder.this.jListPeopleValueChanged(evt);
            }
        });
        this.jScrollPane1.setViewportView(this.jListPeople);
        this.jPanel4.add((Component)this.jScrollPane1, "Center");
        this.jButton1.setFont(new Font("Arial", 0, 12));
        this.jButton1.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/reload.png")));
        ResourceBundle bundle = ResourceBundle.getBundle("pos_messages");
        this.jButton1.setText(bundle.getString("button.reset"));
        this.jButton1.setToolTipText("Clear Filter");
        this.jButton1.setActionCommand("Reset ");
        this.jButton1.setFocusable(false);
        this.jButton1.setPreferredSize(new Dimension(110, 45));
        this.jButton1.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPeopleFinder.this.jButton1ActionPerformed(evt);
            }
        });
        this.jPanel6.add(this.jButton1);
        this.jButton1.getAccessibleContext().setAccessibleDescription("");
        this.jButton3.setFont(new Font("Arial", 0, 12));
        this.jButton3.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/ok.png")));
        this.jButton3.setText(AppLocal.getIntString("button.executefilter"));
        this.jButton3.setToolTipText("Execute Filter");
        this.jButton3.setFocusPainted(false);
        this.jButton3.setPreferredSize(new Dimension(110, 45));
        this.jButton3.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPeopleFinder.this.jButton3ActionPerformed(evt);
            }
        });
        this.jPanel6.add(this.jButton3);
        this.jButton3.getAccessibleContext().setAccessibleDescription("");
        this.jPanel4.add((Component)this.jPanel6, "First");
        this.jPanel3.add((Component)this.jPanel4, "Center");
        this.getContentPane().add((Component)this.jPanel3, "Center");
        this.jPanel2.setCursor(new Cursor(0));
        this.jPanel2.setLayout(new BorderLayout());
        this.jPanel2.add((Component)this.m_jKeys, "North");
        this.jPanel8.setLayout(new BorderLayout());
        this.jcmdCancel.setFont(new Font("Arial", 0, 12));
        this.jcmdCancel.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/cancel.png")));
        this.jcmdCancel.setText(AppLocal.getIntString("button.Cancel"));
        this.jcmdCancel.setFocusPainted(false);
        this.jcmdCancel.setFocusable(false);
        this.jcmdCancel.setMargin(new Insets(8, 16, 8, 16));
        this.jcmdCancel.setPreferredSize(new Dimension(110, 45));
        this.jcmdCancel.setRequestFocusEnabled(false);
        this.jcmdCancel.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPeopleFinder.this.jcmdCancelActionPerformed(evt);
            }
        });
        this.jPanel1.add(this.jcmdCancel);
        this.jcmdOK.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/ok.png")));
        this.jcmdOK.setText(AppLocal.getIntString("button.OK"));
        this.jcmdOK.setEnabled(false);
        this.jcmdOK.setFocusPainted(false);
        this.jcmdOK.setFocusable(false);
        this.jcmdOK.setMargin(new Insets(8, 16, 8, 16));
        this.jcmdOK.setMaximumSize(new Dimension(103, 44));
        this.jcmdOK.setMinimumSize(new Dimension(103, 44));
        this.jcmdOK.setPreferredSize(new Dimension(110, 45));
        this.jcmdOK.setRequestFocusEnabled(false);
        this.jcmdOK.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JPeopleFinder.this.jcmdOKActionPerformed(evt);
            }
        });
        this.jPanel1.add(this.jcmdOK);
        this.jPanel8.add((Component)this.jPanel1, "After");
        this.jPanel2.add((Component)this.jPanel8, "South");
        this.getContentPane().add((Component)this.jPanel2, "After");
        this.setSize(new Dimension(758, 497));
        this.setLocationRelativeTo(null);
    }

    private void jcmdOKActionPerformed(ActionEvent evt) {
        this.selectedPeople = this.jListPeople.getSelectedValue();
        this.dispose();
    }

    private void jcmdCancelActionPerformed(ActionEvent evt) {
        this.dispose();
    }

    private void jButton3ActionPerformed(ActionEvent evt) {
        this.executeSearch();
    }

    private void jListPeopleValueChanged(ListSelectionEvent evt) {
        this.jcmdOK.setEnabled(this.jListPeople.getSelectedValue() != null);
    }

    private void jListPeopleMouseClicked(MouseEvent evt) {
        if (evt.getClickCount() == 2) {
            this.selectedPeople = this.jListPeople.getSelectedValue();
            this.dispose();
        }
    }

    private void jButton1ActionPerformed(ActionEvent evt) {
        this.m_jtxtName.reset();
        this.cleanSearch();
    }

    private static class MyListData
    extends AbstractListModel<PeopleInfo> {
        private final List<PeopleInfo> m_data;

        public MyListData(List<PeopleInfo> data) {
            this.m_data = data;
        }

        @Override
        public PeopleInfo getElementAt(int index) {
            return this.m_data.get(index);
        }

        @Override
        public int getSize() {
            return this.m_data.size();
        }
    }
}

