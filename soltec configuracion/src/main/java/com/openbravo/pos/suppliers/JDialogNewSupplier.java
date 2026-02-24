/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.suppliers;

import com.openbravo.basic.BasicException;
import com.openbravo.data.gui.MessageInf;
import com.openbravo.data.loader.LocalRes;
import com.openbravo.data.loader.TableDefinition;
import com.openbravo.format.Formats;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.forms.DataLogicSales;
import com.openbravo.pos.suppliers.DataLogicSuppliers;
import com.openbravo.pos.suppliers.SupplierInfoExt;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.UUID;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;

public class JDialogNewSupplier
extends JDialog {
    private DataLogicSuppliers dlSupplier;
    private DataLogicSales dlSales;
    private TableDefinition tsuppliers;
    private SupplierInfoExt selectedSupplier;
    private Object m_oId;
    private JLabel jLblEmail;
    private JLabel jLblFirstName;
    private JLabel jLblLastName;
    private JLabel jLblName;
    private JLabel jLblSearchkey;
    private JLabel jLblTaxID;
    private JLabel jLblTelephone1;
    private JLabel jLblTelephone2;
    private JPanel jPanel2;
    private JPanel jPanel3;
    private JButton m_jBtnCancel;
    private JButton m_jBtnOK;
    private JTextField m_jName;
    private JTextField m_jSearchkey;
    private JTextField m_jTaxID;
    private JTextField txtEmail;
    private JTextField txtFirstName;
    private JTextField txtLastName;
    private JTextField txtPhone;
    private JTextField txtPhone2;

    protected JDialogNewSupplier(Frame parent) {
        super(parent, true);
    }

    protected JDialogNewSupplier(Dialog parent) {
        super(parent, true);
    }

    private void init(AppView app) {
        this.dlSales = (DataLogicSales)app.getBean("com.openbravo.pos.forms.DataLogicSales");
        this.dlSupplier = (DataLogicSuppliers)app.getBean("com.openbravo.pos.suppliers.DataLogicSuppliers");
        this.tsuppliers = this.dlSupplier.getTableSuppliers();
        this.initComponents();
        this.getRootPane().setDefaultButton(this.m_jBtnOK);
    }

    public Object createValue() throws BasicException {
        Object[] supplier = new Object[]{this.m_oId, this.m_jSearchkey.getText(), this.m_jTaxID.getText(), this.m_jName.getText(), 0.0, null, null, null, null, null, null, Formats.STRING.parseValue(this.txtFirstName.getText()), Formats.STRING.parseValue(this.txtLastName.getText()), Formats.STRING.parseValue(this.txtEmail.getText()), Formats.STRING.parseValue(this.txtPhone.getText()), Formats.STRING.parseValue(this.txtPhone2.getText()), null, null, true, null, 0.0, null};
        return supplier;
    }

    public static JDialogNewSupplier getDialog(Component parent, AppView app) {
        Window window = JDialogNewSupplier.getWindow(parent);
        JDialogNewSupplier quicknewsupplier = window instanceof Frame ? new JDialogNewSupplier((Frame)window) : new JDialogNewSupplier((Dialog)window);
        quicknewsupplier.init(app);
        return quicknewsupplier;
    }

    protected static Window getWindow(Component parent) {
        if (parent == null) {
            return new JFrame();
        }
        if (parent instanceof Frame || parent instanceof Dialog) {
            return (Window)parent;
        }
        return JDialogNewSupplier.getWindow(parent.getParent());
    }

    public SupplierInfoExt getSelectedSupplier() {
        return this.selectedSupplier;
    }

    private void initComponents() {
        this.jPanel3 = new JPanel();
        this.jLblTaxID = new JLabel();
        this.m_jTaxID = new JTextField();
        this.jLblSearchkey = new JLabel();
        this.m_jSearchkey = new JTextField();
        this.jLblName = new JLabel();
        this.m_jName = new JTextField();
        this.jLblFirstName = new JLabel();
        this.txtFirstName = new JTextField();
        this.jLblLastName = new JLabel();
        this.txtLastName = new JTextField();
        this.jLblEmail = new JLabel();
        this.txtEmail = new JTextField();
        this.jLblTelephone1 = new JLabel();
        this.txtPhone = new JTextField();
        this.jLblTelephone2 = new JLabel();
        this.txtPhone2 = new JTextField();
        this.jPanel2 = new JPanel();
        this.m_jBtnOK = new JButton();
        this.m_jBtnCancel = new JButton();
        this.setDefaultCloseOperation(2);
        this.setTitle(AppLocal.getIntString("label.supplier"));
        this.setFont(new Font("Arial", 0, 14));
        this.setResizable(false);
        this.jPanel3.setFont(new Font("Arial", 0, 14));
        this.jLblTaxID.setFont(new Font("Arial", 0, 14));
        this.jLblTaxID.setText(AppLocal.getIntString("label.taxid"));
        this.jLblTaxID.setMaximumSize(new Dimension(150, 30));
        this.jLblTaxID.setMinimumSize(new Dimension(140, 25));
        this.jLblTaxID.setPreferredSize(new Dimension(150, 30));
        this.m_jTaxID.setFont(new Font("Arial", 0, 14));
        this.m_jTaxID.setPreferredSize(new Dimension(150, 30));
        this.jLblSearchkey.setFont(new Font("Arial", 0, 14));
        this.jLblSearchkey.setText(AppLocal.getIntString("label.searchkeym"));
        this.jLblSearchkey.setPreferredSize(new Dimension(82, 30));
        this.m_jSearchkey.setFont(new Font("Arial", 0, 14));
        this.m_jSearchkey.setHorizontalAlignment(2);
        this.m_jSearchkey.setCursor(new Cursor(2));
        this.m_jSearchkey.setPreferredSize(new Dimension(150, 30));
        this.jLblName.setFont(new Font("Arial", 0, 14));
        this.jLblName.setText(AppLocal.getIntString("label.supplier"));
        this.jLblName.setMaximumSize(new Dimension(140, 25));
        this.jLblName.setMinimumSize(new Dimension(140, 25));
        this.jLblName.setPreferredSize(new Dimension(150, 30));
        this.m_jName.setFont(new Font("Arial", 0, 14));
        this.m_jName.setPreferredSize(new Dimension(400, 30));
        this.jLblFirstName.setFont(new Font("Arial", 0, 14));
        this.jLblFirstName.setText(AppLocal.getIntString("label.firstname"));
        this.jLblFirstName.setAlignmentX(0.5f);
        this.jLblFirstName.setPreferredSize(new Dimension(150, 30));
        this.txtFirstName.setFont(new Font("Arial", 0, 14));
        this.txtFirstName.setPreferredSize(new Dimension(200, 30));
        this.jLblLastName.setFont(new Font("Arial", 0, 14));
        this.jLblLastName.setText(AppLocal.getIntString("label.lastname"));
        this.jLblLastName.setPreferredSize(new Dimension(150, 30));
        this.txtLastName.setFont(new Font("Arial", 0, 14));
        this.txtLastName.setPreferredSize(new Dimension(200, 30));
        this.jLblEmail.setFont(new Font("Arial", 0, 14));
        this.jLblEmail.setText(AppLocal.getIntString("label.email"));
        this.jLblEmail.setPreferredSize(new Dimension(150, 30));
        this.txtEmail.setFont(new Font("Arial", 0, 14));
        this.txtEmail.setPreferredSize(new Dimension(200, 30));
        this.jLblTelephone1.setFont(new Font("Arial", 0, 14));
        this.jLblTelephone1.setText(AppLocal.getIntString("label.phone"));
        this.jLblTelephone1.setPreferredSize(new Dimension(150, 30));
        this.txtPhone.setFont(new Font("Arial", 0, 14));
        this.txtPhone.setPreferredSize(new Dimension(200, 30));
        this.jLblTelephone2.setFont(new Font("Arial", 0, 14));
        this.jLblTelephone2.setText(AppLocal.getIntString("label.phone2"));
        this.jLblTelephone2.setPreferredSize(new Dimension(150, 30));
        this.txtPhone2.setFont(new Font("Arial", 0, 14));
        this.txtPhone2.setPreferredSize(new Dimension(200, 30));
        GroupLayout jPanel3Layout = new GroupLayout(this.jPanel3);
        this.jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel3Layout.createSequentialGroup().addContainerGap().addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addGroup(jPanel3Layout.createSequentialGroup().addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLblEmail, -1, -1, -2).addComponent(this.jLblTelephone1, -1, -1, -2).addComponent(this.jLblTelephone2, GroupLayout.Alignment.TRAILING, -1, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.txtPhone, -1, -1, -2).addComponent(this.txtEmail, -1, -1, -2).addComponent(this.txtPhone2, -1, -1, -2))).addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel3Layout.createSequentialGroup().addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLblFirstName, -1, -1, -2).addComponent(this.jLblLastName, GroupLayout.Alignment.TRAILING, -1, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.txtLastName, -1, -1, -2).addComponent(this.txtFirstName, -1, -1, -2))).addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel3Layout.createSequentialGroup().addComponent(this.jLblTaxID, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.m_jTaxID, -2, -1, -2).addGap(24, 24, 24).addComponent(this.jLblSearchkey, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.m_jSearchkey, -2, -1, -2)).addGroup(jPanel3Layout.createSequentialGroup().addComponent(this.jLblName, -1, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.m_jName, -1, -1, Short.MAX_VALUE))))).addContainerGap(34, Short.MAX_VALUE)));
        jPanel3Layout.setVerticalGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup().addContainerGap(-1, Short.MAX_VALUE).addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLblSearchkey, -2, -1, -2).addComponent(this.m_jSearchkey, -2, -1, -2)).addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.m_jTaxID, -2, -1, -2).addComponent(this.jLblTaxID, -2, -1, -2))).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLblName, -2, -1, -2).addComponent(this.m_jName, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.txtFirstName, -2, -1, -2).addComponent(this.jLblFirstName, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLblLastName, -2, -1, -2).addComponent(this.txtLastName, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLblEmail, -2, -1, -2).addComponent(this.txtEmail, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLblTelephone1, -2, -1, -2).addComponent(this.txtPhone, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLblTelephone2, -2, -1, -2).addComponent(this.txtPhone2, -2, -1, -2))));
        this.getContentPane().add((Component)this.jPanel3, "North");
        this.jPanel3.getAccessibleContext().setAccessibleName("");
        this.jPanel2.setLayout(new FlowLayout(2));
        this.m_jBtnOK.setFont(new Font("Arial", 0, 14));
        this.m_jBtnOK.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/ok.png")));
        this.m_jBtnOK.setText(AppLocal.getIntString("Button.OK"));
        this.m_jBtnOK.setFocusPainted(false);
        this.m_jBtnOK.setFocusable(false);
        this.m_jBtnOK.setMargin(new Insets(8, 16, 8, 16));
        this.m_jBtnOK.setPreferredSize(new Dimension(80, 45));
        this.m_jBtnOK.setRequestFocusEnabled(false);
        this.m_jBtnOK.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JDialogNewSupplier.this.m_jBtnOKActionPerformed(evt);
            }
        });
        this.jPanel2.add(this.m_jBtnOK);
        this.m_jBtnCancel.setFont(new Font("Arial", 0, 14));
        this.m_jBtnCancel.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/cancel.png")));
        this.m_jBtnCancel.setText(AppLocal.getIntString("Button.Cancel"));
        this.m_jBtnCancel.setFocusPainted(false);
        this.m_jBtnCancel.setFocusable(false);
        this.m_jBtnCancel.setMargin(new Insets(8, 16, 8, 16));
        this.m_jBtnCancel.setPreferredSize(new Dimension(80, 45));
        this.m_jBtnCancel.setRequestFocusEnabled(false);
        this.m_jBtnCancel.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JDialogNewSupplier.this.m_jBtnCancelActionPerformed(evt);
            }
        });
        this.jPanel2.add(this.m_jBtnCancel);
        this.getContentPane().add((Component)this.jPanel2, "South");
        this.setSize(new Dimension(628, 455));
        this.setLocationRelativeTo(null);
    }

    private void m_jBtnOKActionPerformed(ActionEvent evt) {
        try {
            this.m_oId = UUID.randomUUID().toString();
            Object supplier = this.createValue();
            int status = this.tsuppliers.getInsertSentence().exec((Object[])supplier);
            if (status > 0) {
                this.selectedSupplier = this.dlSales.loadSupplierExt(this.m_oId.toString());
                this.dispose();
            } else {
                MessageInf msg = new MessageInf(-67108864, LocalRes.getIntString("message.nosave"), "Error save");
                msg.show(this);
            }
        }
        catch (BasicException ex) {
            MessageInf msg = new MessageInf(-67108864, LocalRes.getIntString("message.nosave"), ex);
            msg.show(this);
        }
    }

    private void m_jBtnCancelActionPerformed(ActionEvent evt) {
        this.dispose();
    }
}

