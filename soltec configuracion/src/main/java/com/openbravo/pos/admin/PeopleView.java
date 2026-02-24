/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.admin;

import com.openbravo.basic.BasicException;
import com.openbravo.data.gui.ComboBoxValModel;
import com.openbravo.data.gui.JImageEditor;
import com.openbravo.data.loader.SentenceList;
import com.openbravo.data.user.DirtyManager;
import com.openbravo.data.user.EditorRecord;
import com.openbravo.format.Formats;
import com.openbravo.pos.admin.DataLogicAdmin;
import com.openbravo.pos.admin.RoleInfo;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.util.Hashcypher;
import com.openbravo.pos.util.StringUtils;
import com.openbravo.pos.util.uOWWatch;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.UUID;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;

public class PeopleView
extends JPanel
implements EditorRecord {
    private Object m_oId;
    private String m_sPassword;
    private final DirtyManager m_Dirty;
    private final SentenceList<RoleInfo> m_sentrole;
    private ComboBoxValModel<RoleInfo> m_RoleModel;
    private final ComboBoxValModel<String> m_ReasonModel;
    private JButton jButton1;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel6;
    private JLabel jLblCardID;
    private JImageEditor m_jImage;
    private JTextField m_jName;
    private JComboBox<RoleInfo> m_jRole;
    private JCheckBox m_jVisible;
    private JTextField m_jcard;
    private JComboBox<String> webCBSecurity;

    public PeopleView(DataLogicAdmin dlAdmin, DirtyManager dirty) {
        this.initComponents();
        this.m_sentrole = dlAdmin.getRolesList();
        this.m_RoleModel = new ComboBoxValModel();
        this.m_Dirty = dirty;
        this.m_jName.getDocument().addDocumentListener(dirty);
        this.m_jRole.addActionListener(dirty);
        this.m_jVisible.addActionListener(dirty);
        this.m_jImage.addPropertyChangeListener("image", dirty);
        this.m_jcard.getDocument().addDocumentListener(dirty);
        this.m_ReasonModel = new ComboBoxValModel();
        this.m_ReasonModel.add(AppLocal.getIntString("cboption.generate"));
        this.m_ReasonModel.add(AppLocal.getIntString("cboption.clear"));
        this.m_ReasonModel.add(AppLocal.getIntString("cboption.iButton"));
        this.webCBSecurity.setModel(this.m_ReasonModel);
        this.writeValueEOF();
    }

    @Override
    public void writeValueEOF() {
        this.m_oId = null;
        this.m_jName.setText(null);
        this.m_sPassword = null;
        this.m_RoleModel.setSelectedKey(null);
        this.m_jVisible.setSelected(false);
        this.m_jcard.setText(null);
        this.m_jImage.setImage(null);
        this.m_jName.setEnabled(false);
        this.m_jRole.setEnabled(false);
        this.m_jVisible.setEnabled(false);
        this.m_jcard.setEnabled(false);
        this.m_jImage.setEnabled(false);
        this.jButton1.setEnabled(false);
        this.webCBSecurity.setEnabled(false);
    }

    @Override
    public void writeValueInsert() {
        this.m_oId = null;
        this.m_jName.setText(null);
        this.m_sPassword = null;
        this.m_RoleModel.setSelectedKey(null);
        this.m_jVisible.setSelected(true);
        this.m_jcard.setText(null);
        this.m_jImage.setImage(null);
        this.m_jName.setEnabled(true);
        this.m_jRole.setEnabled(true);
        this.m_jVisible.setEnabled(true);
        this.m_jcard.setEnabled(true);
        this.m_jImage.setEnabled(true);
        this.jButton1.setEnabled(true);
        this.webCBSecurity.setEnabled(true);
    }

    @Override
    public void writeValueDelete(Object value) {
        Object[] people = (Object[])value;
        this.m_oId = people[0];
        this.m_jName.setText(Formats.STRING.formatValue(people[1]));
        this.m_sPassword = Formats.STRING.formatValue(people[2]);
        this.m_RoleModel.setSelectedKey(people[3]);
        this.m_jVisible.setSelected((Boolean)people[4]);
        this.m_jcard.setText(Formats.STRING.formatValue(people[5]));
        this.m_jImage.setImage((BufferedImage)people[6]);
        this.m_jName.setEnabled(false);
        this.m_jRole.setEnabled(false);
        this.m_jVisible.setEnabled(false);
        this.m_jcard.setEnabled(false);
        this.m_jImage.setEnabled(false);
        this.jButton1.setEnabled(false);
        this.webCBSecurity.setEnabled(false);
    }

    @Override
    public void writeValueEdit(Object value) {
        Object[] people = (Object[])value;
        this.m_oId = people[0];
        this.m_jName.setText(Formats.STRING.formatValue(people[1]));
        this.m_sPassword = Formats.STRING.formatValue(people[2]);
        this.m_RoleModel.setSelectedKey(people[3]);
        this.m_jVisible.setSelected((Boolean)people[4]);
        this.m_jcard.setText(Formats.STRING.formatValue(people[5]));
        this.m_jImage.setImage((BufferedImage)people[6]);
        if (this.m_jcard.getText().length() == 16) {
            this.jLblCardID.setText(AppLocal.getIntString("label.ibutton"));
        } else {
            this.jLblCardID.setText(AppLocal.getIntString("label.card"));
        }
        System.out.println(this.m_jcard.getText().length());
        this.m_jName.setEnabled(true);
        this.m_jRole.setEnabled(true);
        this.m_jVisible.setEnabled(true);
        this.m_jcard.setEnabled(true);
        this.m_jImage.setEnabled(true);
        this.jButton1.setEnabled(true);
        this.webCBSecurity.setEnabled(true);
    }

    @Override
    public Object createValue() throws BasicException {
        Object[] people = new Object[]{this.m_oId == null ? UUID.randomUUID().toString() : this.m_oId, Formats.STRING.parseValue(this.m_jName.getText()), Formats.STRING.parseValue(this.m_sPassword), this.m_RoleModel.getSelectedKey(), this.m_jVisible.isSelected(), Formats.STRING.parseValue(this.m_jcard.getText()), this.m_jImage.getImage()};
        return people;
    }

    @Override
    public Component getComponent() {
        return this;
    }

    public void activate() throws BasicException {
        this.m_RoleModel = new ComboBoxValModel<RoleInfo>(this.m_sentrole.list());
        this.m_jRole.setModel(this.m_RoleModel);
    }

    @Override
    public void refresh() {
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.m_jName = new JTextField();
        this.m_jVisible = new JCheckBox();
        this.jLabel3 = new JLabel();
        this.jLabel4 = new JLabel();
        this.m_jImage = new JImageEditor();
        this.jButton1 = new JButton();
        this.m_jRole = new JComboBox();
        this.jLabel2 = new JLabel();
        this.m_jcard = new JTextField();
        this.jLblCardID = new JLabel();
        this.webCBSecurity = new JComboBox();
        this.jLabel6 = new JLabel();
        this.setFont(new Font("Arial", 0, 12));
        this.setPreferredSize(new Dimension(500, 500));
        this.jLabel1.setFont(new Font("Arial", 0, 14));
        this.jLabel1.setText(AppLocal.getIntString("label.peoplenamem"));
        this.jLabel1.setPreferredSize(new Dimension(110, 30));
        this.m_jName.setFont(new Font("Arial", 0, 14));
        this.m_jName.setPreferredSize(new Dimension(0, 30));
        this.m_jVisible.setFont(new Font("Arial", 0, 12));
        this.m_jVisible.setPreferredSize(new Dimension(0, 30));
        this.jLabel3.setFont(new Font("Arial", 0, 14));
        this.jLabel3.setText(AppLocal.getIntString("label.peoplevisible"));
        this.jLabel3.setPreferredSize(new Dimension(110, 30));
        this.jLabel4.setFont(new Font("Arial", 0, 14));
        this.jLabel4.setText(AppLocal.getIntString("label.peopleimage"));
        this.jLabel4.setPreferredSize(new Dimension(110, 30));
        this.m_jImage.setFont(new Font("Arial", 0, 12));
        this.m_jImage.setPreferredSize(new Dimension(300, 250));
        this.jButton1.setFont(new Font("Arial", 0, 14));
        this.jButton1.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/password.png")));
        this.jButton1.setText(AppLocal.getIntString("button.peoplepassword"));
        this.jButton1.setToolTipText("");
        this.jButton1.setPreferredSize(new Dimension(80, 45));
        this.jButton1.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                PeopleView.this.jButton1ActionPerformed(evt);
            }
        });
        this.m_jRole.setFont(new Font("Arial", 0, 14));
        this.m_jRole.setPreferredSize(new Dimension(0, 30));
        this.jLabel2.setFont(new Font("Arial", 0, 14));
        this.jLabel2.setText(AppLocal.getIntString("label.rolem"));
        this.jLabel2.setPreferredSize(new Dimension(110, 30));
        this.m_jcard.setFont(new Font("Arial", 0, 14));
        this.m_jcard.setPreferredSize(new Dimension(0, 30));
        this.jLblCardID.setFont(new Font("Arial", 0, 14));
        this.jLblCardID.setText(AppLocal.getIntString("label.card"));
        this.jLblCardID.setPreferredSize(new Dimension(110, 30));
        this.webCBSecurity.setModel(new DefaultComboBoxModel<String>(new String[]{"Generate new key", "Delete exisitng key", "Use iButton ID"}));
        this.webCBSecurity.setSelectedIndex(0);
        this.webCBSecurity.setToolTipText(AppLocal.getIntString("tooltip.peoplesecurity"));
        this.webCBSecurity.setFont(new Font("Arial", 0, 14));
        this.webCBSecurity.setPreferredSize(new Dimension(140, 45));
        this.webCBSecurity.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                PeopleView.this.webCBSecurityActionPerformed(evt);
            }
        });
        this.jLabel6.setFont(new Font("Arial", 0, 14));
        this.jLabel6.setText(AppLocal.getIntString("label.Password"));
        this.jLabel6.setPreferredSize(new Dimension(110, 30));
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.jLabel3, -2, -1, -2).addGap(18, 18, 18).addComponent(this.m_jVisible, -2, 207, -2)).addGroup(layout.createSequentialGroup().addComponent(this.jLabel2, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.m_jRole, -2, 180, -2)).addGroup(layout.createSequentialGroup().addComponent(this.jLabel4, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.m_jImage, -2, -1, -2)).addGroup(layout.createSequentialGroup().addComponent(this.jLblCardID, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.m_jcard, -2, 180, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.webCBSecurity, -1, -1, -2)).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel1, -2, -1, -2).addComponent(this.jLabel6, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jButton1, -2, -1, -2).addComponent(this.m_jName, -2, 180, -2)))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addGap(18, 18, 18).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1, -2, -1, -2).addComponent(this.m_jName, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER).addComponent(this.jLabel6, -2, -1, -2).addComponent(this.jButton1, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER).addComponent(this.jLblCardID, -2, -1, -2).addComponent(this.m_jcard, -2, -1, -2).addComponent(this.webCBSecurity, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel2, -2, -1, -2).addComponent(this.m_jRole, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel3, -2, 25, -2).addComponent(this.m_jVisible, -2, 25, -2)).addGap(15, 15, 15).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.m_jImage, -2, -1, -2).addComponent(this.jLabel4, -2, -1, -2)).addContainerGap(-1, Short.MAX_VALUE)));
    }

    private void jButton1ActionPerformed(ActionEvent evt) {
        String sNewPassword = Hashcypher.changePassword(this);
        if (sNewPassword != null) {
            this.m_sPassword = sNewPassword;
            this.m_Dirty.setDirty(true);
        }
    }

    private void webCBSecurityActionPerformed(ActionEvent evt) {
        if (this.webCBSecurity.getSelectedIndex() == 0 && JOptionPane.showConfirmDialog(this, AppLocal.getIntString("message.cardnew"), AppLocal.getIntString("title.editor"), 0, 3) == 0) {
            this.m_jcard.setText("C" + StringUtils.getCardNumber());
            this.m_Dirty.setDirty(true);
        }
        if (this.webCBSecurity.getSelectedIndex() == 1 && JOptionPane.showConfirmDialog(this, AppLocal.getIntString("message.cardremove"), AppLocal.getIntString("title.editor"), 0, 3) == 0) {
            this.m_jcard.setText(null);
            this.m_Dirty.setDirty(true);
        }
        if (this.webCBSecurity.getSelectedIndex() == 2 && JOptionPane.showConfirmDialog(this, AppLocal.getIntString("message.ibuttonassign"), AppLocal.getIntString("title.editor"), 2, 1) == 0) {
            this.m_jcard.setText(uOWWatch.getibuttonid());
            this.jLblCardID.setText(AppLocal.getIntString("label.ibutton"));
            this.m_Dirty.setDirty(true);
        }
    }
}

