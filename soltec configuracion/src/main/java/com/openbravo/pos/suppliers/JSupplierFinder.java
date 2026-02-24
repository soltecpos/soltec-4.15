/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.suppliers;

import com.openbravo.basic.BasicException;
import com.openbravo.data.gui.JImageViewerCustomer;
import com.openbravo.data.loader.QBFCompareEnum;
import com.openbravo.data.user.EditorCreator;
import com.openbravo.data.user.ListProvider;
import com.openbravo.data.user.ListProviderCreator;
import com.openbravo.editor.JEditorKeys;
import com.openbravo.editor.JEditorString;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.suppliers.DataLogicSuppliers;
import com.openbravo.pos.suppliers.SupplierInfo;
import com.openbravo.pos.suppliers.SupplierInfoExt;
import com.openbravo.pos.suppliers.SupplierInfoGlobal;
import com.openbravo.pos.suppliers.SupplierRenderer;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class JSupplierFinder
extends JDialog
implements EditorCreator {
    private SupplierInfo m_ReturnSupplier;
    private ListProvider<SupplierInfo> lpr;
    private AppView appView;
    private JImageViewerCustomer jImageViewerSupplier;
    private JLabel jLblEmail;
    private JLabel jLblName;
    private JLabel jLblPhone;
    private JLabel jLblPostal;
    private JLabel jLblSearchKey;
    private JLabel jLblTaxID;
    private JList jListSuppliers;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JPanel jPanel3;
    private JPanel jPanel4;
    private JPanel jPanel5;
    private JPanel jPanel6;
    private JPanel jPanel7;
    private JPanel jPanel8;
    private JScrollPane jScrollPane1;
    private JButton jbtnExecute;
    private JButton jbtnReset;
    private JButton jcmdCancel;
    private JButton jcmdOK;
    private JEditorKeys m_jKeys;
    private JEditorString m_jtxtName;
    private JEditorString m_jtxtName2;
    private JEditorString m_jtxtPhone;
    private JEditorString m_jtxtPostal;
    private JEditorString m_jtxtSearchKey;
    private JEditorString m_jtxtTaxID;

    public void searchKey() {
        this.jbtnExecute.setMnemonic(69);
        this.executeSearch();
    }

    public void resetKey() {
        this.jbtnReset.setMnemonic(82);
        this.m_jtxtTaxID.reset();
        this.m_jtxtSearchKey.reset();
        this.m_jtxtName.reset();
        this.m_jtxtPostal.reset();
        this.m_jtxtPhone.reset();
        this.m_jtxtName2.reset();
        this.m_jtxtTaxID.activate();
        this.cleanSearch();
    }

    public void setAppView(AppView appView) {
        this.appView = appView;
    }

    private JSupplierFinder(Frame parent, boolean modal) {
        super(parent, modal);
    }

    private JSupplierFinder(Dialog parent, boolean modal) {
        super(parent, modal);
    }

    public static JSupplierFinder getSupplierFinder(Component parent, DataLogicSuppliers dlSuppliers) {
        Window window = JSupplierFinder.getWindow(parent);
        JSupplierFinder myMsg = window instanceof Frame ? new JSupplierFinder((Frame)window, true) : new JSupplierFinder((Dialog)window, true);
        myMsg.init(dlSuppliers);
        myMsg.applyComponentOrientation(parent.getComponentOrientation());
        return myMsg;
    }

    public SupplierInfo getSelectedSupplier() {
        return this.m_ReturnSupplier;
    }

    private void init(DataLogicSuppliers dlSuppliers) {
        this.initComponents();
        this.jImageViewerSupplier.setVisible(false);
        this.jScrollPane1.getVerticalScrollBar().setPreferredSize(new Dimension(35, 35));
        this.m_jtxtTaxID.addEditorKeys(this.m_jKeys);
        this.m_jtxtSearchKey.addEditorKeys(this.m_jKeys);
        this.m_jtxtName.addEditorKeys(this.m_jKeys);
        this.m_jtxtPostal.addEditorKeys(this.m_jKeys);
        this.m_jtxtPhone.addEditorKeys(this.m_jKeys);
        this.m_jtxtName2.addEditorKeys(this.m_jKeys);
        this.m_jtxtTaxID.reset();
        this.m_jtxtSearchKey.reset();
        this.m_jtxtName.reset();
        this.m_jtxtPostal.reset();
        this.m_jtxtPhone.reset();
        this.m_jtxtName2.reset();
        this.m_jtxtTaxID.activate();
        this.lpr = new ListProviderCreator<SupplierInfo>(dlSuppliers.getSupplierList(), this);
        this.jListSuppliers.setCellRenderer(new SupplierRenderer());
        this.getRootPane().setDefaultButton(this.jcmdOK);
        this.m_ReturnSupplier = null;
    }

    public void search(SupplierInfo supplier) {
        if (supplier == null || supplier.getName() == null || supplier.getName().equals("")) {
            this.m_jtxtTaxID.reset();
            this.m_jtxtSearchKey.reset();
            this.m_jtxtName.reset();
            this.m_jtxtPostal.reset();
            this.m_jtxtPhone.reset();
            this.m_jtxtName2.reset();
            this.m_jtxtTaxID.activate();
            this.cleanSearch();
        } else {
            this.m_jtxtTaxID.setText(supplier.getTaxid());
            this.m_jtxtSearchKey.setText(supplier.getSearchkey());
            this.m_jtxtName.setText(supplier.getName());
            this.m_jtxtPostal.setText(supplier.getPostal());
            this.m_jtxtPhone.setText(supplier.getPhone());
            this.m_jtxtName2.setText(supplier.getEmail());
            this.m_jtxtTaxID.activate();
            this.executeSearch();
        }
    }

    private void cleanSearch() {
        this.m_jtxtTaxID.setText("");
        this.m_jtxtSearchKey.setText("");
        this.m_jtxtName.setText("");
        this.m_jtxtPostal.setText("");
        this.m_jtxtPhone.setText("");
        this.m_jtxtName2.setText("");
        this.jListSuppliers.setModel(new MyListData(new ArrayList()));
    }

    public void executeSearch() {
        try {
            int n;
            this.jListSuppliers.setModel(new MyListData(this.lpr.loadData()));
            if (this.jListSuppliers.getModel().getSize() > 0) {
                this.jListSuppliers.setSelectedIndex(0);
            } else if (!this.m_jtxtName.getText().equals("") && (n = JOptionPane.showConfirmDialog(null, AppLocal.getIntString("message.suppliernotfound"), AppLocal.getIntString("title.editor"), 0)) != 1) {
                SupplierInfoGlobal supplierInfoGlobal = SupplierInfoGlobal.getInstance();
                SupplierInfoExt supplierInfoExt = supplierInfoGlobal.getSupplierInfoExt();
                this.setVisible(false);
                this.appView.getAppUserView().showTask("com.openbravo.pos.suppliers.SuppliersPanel");
                JOptionPane.showMessageDialog(null, "You must complete Account and Search Key Then Save to add to Ticket", "Create Supplier", 0);
            }
        }
        catch (BasicException basicException) {
            // empty catch block
        }
    }

    @Override
    public Object createValue() throws BasicException {
        Object[] afilter = new Object[12];
        if (this.m_jtxtTaxID.getText() == null || this.m_jtxtTaxID.getText().equals("")) {
            afilter[0] = QBFCompareEnum.COMP_NONE;
            afilter[1] = null;
        } else {
            afilter[0] = QBFCompareEnum.COMP_RE;
            afilter[1] = "%" + this.m_jtxtTaxID.getText() + "%";
        }
        if (this.m_jtxtSearchKey.getText() == null || this.m_jtxtSearchKey.getText().equals("")) {
            afilter[2] = QBFCompareEnum.COMP_NONE;
            afilter[3] = null;
        } else {
            afilter[2] = QBFCompareEnum.COMP_RE;
            afilter[3] = "%" + this.m_jtxtSearchKey.getText() + "%";
        }
        if (this.m_jtxtName.getText() == null || this.m_jtxtName.getText().equals("")) {
            afilter[4] = QBFCompareEnum.COMP_NONE;
            afilter[5] = null;
        } else {
            afilter[4] = QBFCompareEnum.COMP_RE;
            afilter[5] = "%" + this.m_jtxtName.getText() + "%";
        }
        if (this.m_jtxtPostal.getText() == null || this.m_jtxtPostal.getText().equals("")) {
            afilter[6] = QBFCompareEnum.COMP_NONE;
            afilter[7] = null;
        } else {
            afilter[6] = QBFCompareEnum.COMP_RE;
            afilter[7] = "%" + this.m_jtxtPostal.getText() + "%";
        }
        if (this.m_jtxtPhone.getText() == null || this.m_jtxtPhone.getText().equals("")) {
            afilter[8] = QBFCompareEnum.COMP_NONE;
            afilter[9] = null;
        } else {
            afilter[8] = QBFCompareEnum.COMP_RE;
            afilter[9] = "%" + this.m_jtxtPhone.getText() + "%";
        }
        if (this.m_jtxtName2.getText() == null || this.m_jtxtName2.getText().equals("")) {
            afilter[10] = QBFCompareEnum.COMP_NONE;
            afilter[11] = null;
        } else {
            afilter[10] = QBFCompareEnum.COMP_RE;
            afilter[11] = "%" + this.m_jtxtName2.getText() + "%";
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
        return JSupplierFinder.getWindow(parent.getParent());
    }

    private void initComponents() {
        this.jPanel2 = new JPanel();
        this.m_jKeys = new JEditorKeys();
        this.jPanel8 = new JPanel();
        this.jPanel1 = new JPanel();
        this.jcmdCancel = new JButton();
        this.jcmdOK = new JButton();
        this.jImageViewerSupplier = new JImageViewerCustomer();
        this.jPanel3 = new JPanel();
        this.jPanel5 = new JPanel();
        this.jPanel7 = new JPanel();
        this.jLblTaxID = new JLabel();
        this.m_jtxtTaxID = new JEditorString();
        this.jLblSearchKey = new JLabel();
        this.m_jtxtSearchKey = new JEditorString();
        this.jLblPostal = new JLabel();
        this.m_jtxtPostal = new JEditorString();
        this.jLblName = new JLabel();
        this.m_jtxtName = new JEditorString();
        this.jLblPhone = new JLabel();
        this.jLblEmail = new JLabel();
        this.m_jtxtPhone = new JEditorString();
        this.m_jtxtName2 = new JEditorString();
        this.jPanel4 = new JPanel();
        this.jScrollPane1 = new JScrollPane();
        this.jListSuppliers = new JList();
        this.jPanel6 = new JPanel();
        this.jbtnReset = new JButton();
        this.jbtnExecute = new JButton();
        this.setDefaultCloseOperation(2);
        this.setTitle(AppLocal.getIntString("form.customertitle"));
        this.setCursor(new Cursor(0));
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
                JSupplierFinder.this.jcmdCancelActionPerformed(evt);
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
                JSupplierFinder.this.jcmdOKActionPerformed(evt);
            }
        });
        this.jPanel1.add(this.jcmdOK);
        this.jPanel8.add((Component)this.jPanel1, "After");
        this.jPanel2.add((Component)this.jPanel8, "Last");
        this.jPanel2.add((Component)this.jImageViewerSupplier, "Center");
        this.getContentPane().add((Component)this.jPanel2, "After");
        this.jPanel3.setPreferredSize(new Dimension(450, 0));
        this.jPanel3.setLayout(new BorderLayout());
        this.jPanel5.setLayout(new BorderLayout());
        this.jLblTaxID.setFont(new Font("Arial", 0, 14));
        this.jLblTaxID.setText(AppLocal.getIntString("label.taxid"));
        this.jLblTaxID.setMaximumSize(new Dimension(60, 15));
        this.jLblTaxID.setMinimumSize(new Dimension(60, 15));
        this.jLblTaxID.setPreferredSize(new Dimension(110, 30));
        this.m_jtxtTaxID.setFont(new Font("Arial", 0, 14));
        this.m_jtxtTaxID.setPreferredSize(new Dimension(200, 30));
        this.jLblSearchKey.setFont(new Font("Arial", 0, 14));
        this.jLblSearchKey.setText(AppLocal.getIntString("label.searchkey"));
        this.jLblSearchKey.setMaximumSize(new Dimension(60, 15));
        this.jLblSearchKey.setMinimumSize(new Dimension(60, 15));
        this.jLblSearchKey.setPreferredSize(new Dimension(110, 30));
        this.m_jtxtSearchKey.setFont(new Font("Arial", 0, 14));
        this.m_jtxtSearchKey.setPreferredSize(new Dimension(250, 30));
        this.jLblPostal.setFont(new Font("Arial", 0, 14));
        this.jLblPostal.setText("Postal");
        this.jLblPostal.setMaximumSize(new Dimension(60, 15));
        this.jLblPostal.setMinimumSize(new Dimension(60, 15));
        this.jLblPostal.setPreferredSize(new Dimension(110, 30));
        this.m_jtxtPostal.setFont(new Font("Arial", 0, 14));
        this.m_jtxtPostal.setPreferredSize(new Dimension(250, 30));
        this.jLblName.setFont(new Font("Arial", 0, 14));
        this.jLblName.setText(AppLocal.getIntString("label.prodname"));
        this.jLblName.setMaximumSize(new Dimension(60, 15));
        this.jLblName.setMinimumSize(new Dimension(60, 15));
        this.jLblName.setPreferredSize(new Dimension(110, 30));
        this.m_jtxtName.setFont(new Font("Arial", 0, 14));
        this.m_jtxtName.setPreferredSize(new Dimension(250, 30));
        this.jLblPhone.setFont(new Font("Arial", 0, 14));
        ResourceBundle bundle = ResourceBundle.getBundle("pos_messages");
        this.jLblPhone.setText(bundle.getString("label.companytelephone"));
        this.jLblPhone.setPreferredSize(new Dimension(110, 30));
        this.jLblEmail.setFont(new Font("Arial", 0, 14));
        this.jLblEmail.setText(bundle.getString("label.companyemail"));
        this.jLblEmail.setPreferredSize(new Dimension(110, 30));
        this.m_jtxtPhone.setFont(new Font("Arial", 0, 14));
        this.m_jtxtPhone.setMinimumSize(new Dimension(150, 30));
        this.m_jtxtPhone.setPreferredSize(new Dimension(250, 30));
        this.m_jtxtName2.setFont(new Font("Arial", 0, 14));
        this.m_jtxtName2.setMinimumSize(new Dimension(150, 30));
        this.m_jtxtName2.setPreferredSize(new Dimension(250, 30));
        GroupLayout jPanel7Layout = new GroupLayout(this.jPanel7);
        this.jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(jPanel7Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel7Layout.createSequentialGroup().addContainerGap().addGroup(jPanel7Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel7Layout.createSequentialGroup().addGroup(jPanel7Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLblName, -2, -1, -2).addComponent(this.jLblSearchKey, -2, -1, -2).addComponent(this.jLblPostal, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel7Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.m_jtxtSearchKey, -2, -1, -2).addComponent(this.m_jtxtPostal, -2, -1, -2).addComponent(this.m_jtxtName, -2, -1, -2))).addGroup(jPanel7Layout.createSequentialGroup().addComponent(this.jLblTaxID, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.m_jtxtTaxID, -2, -1, -2)).addGroup(jPanel7Layout.createSequentialGroup().addGroup(jPanel7Layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addComponent(this.jLblEmail, -1, -1, -2).addComponent(this.jLblPhone, GroupLayout.Alignment.LEADING, -1, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel7Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.m_jtxtPhone, -2, -1, -2).addComponent(this.m_jtxtName2, -2, -1, -2)))).addContainerGap()));
        jPanel7Layout.setVerticalGroup(jPanel7Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel7Layout.createSequentialGroup().addContainerGap().addGroup(jPanel7Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.m_jtxtTaxID, -2, -1, -2).addComponent(this.jLblTaxID, GroupLayout.Alignment.TRAILING, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel7Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.m_jtxtSearchKey, -2, -1, -2).addComponent(this.jLblSearchKey, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel7Layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(jPanel7Layout.createSequentialGroup().addGroup(jPanel7Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLblPostal, -2, -1, -2).addComponent(this.m_jtxtPostal, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.m_jtxtName, -2, -1, -2)).addComponent(this.jLblName, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel7Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.m_jtxtPhone, -2, -1, -2).addComponent(this.jLblPhone, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel7Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLblEmail, -2, -1, -2).addComponent(this.m_jtxtName2, -2, -1, -2)).addContainerGap()));
        this.m_jtxtName.getAccessibleContext().setAccessibleName("");
        this.jPanel5.add((Component)this.jPanel7, "Center");
        this.jPanel3.add((Component)this.jPanel5, "First");
        this.jPanel4.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        this.jPanel4.setPreferredSize(new Dimension(450, 140));
        this.jPanel4.setLayout(new BorderLayout());
        this.jScrollPane1.setPreferredSize(new Dimension(400, 147));
        this.jListSuppliers.setFont(new Font("Arial", 0, 14));
        this.jListSuppliers.setFocusable(false);
        this.jListSuppliers.setRequestFocusEnabled(false);
        this.jListSuppliers.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent evt) {
                JSupplierFinder.this.jListSuppliersMouseClicked(evt);
            }
        });
        this.jListSuppliers.addListSelectionListener(new ListSelectionListener(){

            @Override
            public void valueChanged(ListSelectionEvent evt) {
                JSupplierFinder.this.jListSuppliersValueChanged(evt);
            }
        });
        this.jScrollPane1.setViewportView(this.jListSuppliers);
        this.jPanel4.add((Component)this.jScrollPane1, "Center");
        this.jbtnReset.setFont(new Font("Arial", 0, 12));
        this.jbtnReset.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/reload.png")));
        this.jbtnReset.setText(bundle.getString("button.reset"));
        this.jbtnReset.setToolTipText("Clear Filter");
        this.jbtnReset.setActionCommand("Reset ");
        this.jbtnReset.setFocusable(false);
        this.jbtnReset.setPreferredSize(new Dimension(110, 45));
        this.jbtnReset.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JSupplierFinder.this.jbtnResetActionPerformed(evt);
            }
        });
        this.jPanel6.add(this.jbtnReset);
        this.jbtnReset.getAccessibleContext().setAccessibleDescription("");
        this.jbtnExecute.setFont(new Font("Arial", 0, 12));
        this.jbtnExecute.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/ok.png")));
        this.jbtnExecute.setText(AppLocal.getIntString("button.executefilter"));
        this.jbtnExecute.setToolTipText("Execute Filter");
        this.jbtnExecute.setFocusPainted(false);
        this.jbtnExecute.setPreferredSize(new Dimension(110, 45));
        this.jbtnExecute.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JSupplierFinder.this.jbtnExecuteActionPerformed(evt);
            }
        });
        this.jPanel6.add(this.jbtnExecute);
        this.jbtnExecute.getAccessibleContext().setAccessibleDescription("");
        this.jPanel4.add((Component)this.jPanel6, "First");
        this.jPanel3.add((Component)this.jPanel4, "Center");
        this.getContentPane().add((Component)this.jPanel3, "Center");
        this.setSize(new Dimension(758, 634));
        this.setLocationRelativeTo(null);
    }

    private void jcmdOKActionPerformed(ActionEvent evt) {
        this.m_ReturnSupplier = (SupplierInfo)this.jListSuppliers.getSelectedValue();
        this.dispose();
    }

    private void jcmdCancelActionPerformed(ActionEvent evt) {
        this.dispose();
    }

    private void jbtnExecuteActionPerformed(ActionEvent evt) {
        this.m_ReturnSupplier = null;
        this.executeSearch();
    }

    private void jListSuppliersValueChanged(ListSelectionEvent evt) {
        this.m_ReturnSupplier = (SupplierInfo)this.jListSuppliers.getSelectedValue();
        if (this.m_ReturnSupplier != null) {
            this.m_ReturnSupplier = (SupplierInfo)this.jListSuppliers.getSelectedValue();
        }
        this.jcmdOK.setEnabled(this.jListSuppliers.getSelectedValue() != null);
    }

    private void jListSuppliersMouseClicked(MouseEvent evt) {
        this.m_ReturnSupplier = (SupplierInfo)this.jListSuppliers.getSelectedValue();
        if (this.m_ReturnSupplier != null) {
            this.m_ReturnSupplier = (SupplierInfo)this.jListSuppliers.getSelectedValue();
        }
    }

    private void jbtnResetActionPerformed(ActionEvent evt) {
        this.m_jtxtTaxID.reset();
        this.m_jtxtSearchKey.reset();
        this.m_jtxtName.reset();
        this.m_jtxtPostal.reset();
        this.m_jtxtPhone.reset();
        this.m_jtxtName2.reset();
        this.m_jtxtTaxID.activate();
        this.cleanSearch();
    }

    private static class MyListData
    extends AbstractListModel {
        private final List m_data;

        public MyListData(List data) {
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

    public class Global {
    }
}

