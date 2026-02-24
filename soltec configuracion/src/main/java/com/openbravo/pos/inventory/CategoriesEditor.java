/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.inventory;

import com.openbravo.basic.BasicException;
import com.openbravo.data.gui.ComboBoxValModel;
import com.openbravo.data.gui.JImageEditor;
import com.openbravo.data.gui.JMessageDialog;
import com.openbravo.data.gui.MessageInf;
import com.openbravo.data.loader.SentenceExec;
import com.openbravo.data.loader.SentenceList;
import com.openbravo.data.user.DirtyManager;
import com.openbravo.data.user.EditorRecord;
import com.openbravo.format.Formats;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.forms.DataLogicSales;
import com.openbravo.pos.ticket.CategoryInfo;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;

public final class CategoriesEditor
extends JPanel
implements EditorRecord {
    private static final long serialVersionUID = 1L;
    private final SentenceList<CategoryInfo> m_sentcat;
    private ComboBoxValModel<CategoryInfo> m_CategoryModel;
    private final SentenceExec<String> m_sentadd;
    private final SentenceExec<String> m_sentdel;
    private Object m_id;
    private JInternalFrame jInternalFrame1;
    private JLabel jLblCatOrder;
    private JLabel jLblCatShowName;
    private JLabel jLblCategory;
    private JLabel jLblImage;
    private JLabel jLblInCat;
    private JLabel jLblName;
    private JLabel jLblTextTip;
    private JCheckBox m_jCatNameShow;
    private JTextField m_jCatOrder;
    private JComboBox<CategoryInfo> m_jCategory;
    private JImageEditor m_jImage;
    private JTextField m_jName;
    private JTextField m_jTextTip;
    private JCheckBox webSwtch_InCatalog;

    public CategoriesEditor(AppView app, DirtyManager dirty) {
        DataLogicSales dlSales2 = (DataLogicSales)app.getBean("com.openbravo.pos.forms.DataLogicSales");
        this.initComponents();
        this.m_sentcat = dlSales2.getCategoriesList();
        this.m_CategoryModel = new ComboBoxValModel();
        this.m_sentadd = dlSales2.getCatalogCategoryAdd();
        this.m_sentdel = dlSales2.getCatalogCategoryDel();
        this.m_jName.getDocument().addDocumentListener(dirty);
        this.m_jCategory.addActionListener(dirty);
        this.m_jImage.addPropertyChangeListener("image", dirty);
        this.m_jCatNameShow.addActionListener(dirty);
        this.m_jTextTip.getDocument().addDocumentListener(dirty);
        this.m_jCatOrder.getDocument().addDocumentListener(dirty);
        this.webSwtch_InCatalog.addActionListener(dirty);
        this.writeValueEOF();
    }

    @Override
    public void refresh() {
        List<CategoryInfo> a;
        try {
            a = this.m_sentcat.list();
        }
        catch (BasicException eD) {
            MessageInf msg = new MessageInf(-67108864, AppLocal.getIntString("message.cannotloadlists"), eD);
            msg.show(this);
            a = new ArrayList<CategoryInfo>();
        }
        a.add(0, null);
        this.m_CategoryModel = new ComboBoxValModel<CategoryInfo>(a);
        this.m_jCategory.setModel(this.m_CategoryModel);
    }

    @Override
    public void writeValueEOF() {
        this.m_id = null;
        this.m_jName.setText(null);
        this.m_CategoryModel.setSelectedKey(null);
        this.m_jImage.setImage(null);
        this.m_jName.setEnabled(false);
        this.m_jCategory.setEnabled(false);
        this.m_jImage.setEnabled(false);
        this.webSwtch_InCatalog.isSelected();
        this.m_jTextTip.setText(null);
        this.m_jTextTip.setEnabled(false);
        this.m_jCatNameShow.setSelected(false);
        this.m_jCatNameShow.setEnabled(false);
        this.m_jCatOrder.setEnabled(false);
    }

    @Override
    public void writeValueInsert() {
        this.m_id = UUID.randomUUID().toString();
        this.m_jName.setText(null);
        this.m_CategoryModel.setSelectedKey(null);
        this.m_jImage.setImage(null);
        this.m_jName.setEnabled(true);
        this.m_jCategory.setEnabled(true);
        this.m_jImage.setEnabled(true);
        this.webSwtch_InCatalog.setEnabled(false);
        this.m_jTextTip.setText(null);
        this.m_jTextTip.setEnabled(true);
        this.m_jCatNameShow.setSelected(true);
        this.m_jCatNameShow.setEnabled(true);
        this.m_jCatOrder.setEnabled(true);
    }

    @Override
    public void writeValueDelete(Object value) {
        Object[] cat = (Object[])value;
        this.m_id = cat[0];
        this.m_jName.setText(Formats.STRING.formatValue(cat[1]));
        this.m_CategoryModel.setSelectedKey(cat[2]);
        this.m_jImage.setImage((BufferedImage)cat[3]);
        this.m_jTextTip.setText(Formats.STRING.formatValue(cat[4]));
        this.m_jCatNameShow.setSelected((Boolean)cat[5]);
        this.m_jCatOrder.setText(Formats.STRING.formatValue(cat[6]));
        this.m_jName.setEnabled(false);
        this.m_jCategory.setEnabled(false);
        this.m_jImage.setEnabled(false);
        this.webSwtch_InCatalog.setEnabled(false);
        this.m_jTextTip.setEnabled(false);
        this.m_jCatNameShow.setEnabled(false);
        this.m_jCatOrder.setEnabled(false);
    }

    @Override
    public void writeValueEdit(Object value) {
        Object[] cat = (Object[])value;
        this.m_id = cat[0];
        this.m_jName.setText(Formats.STRING.formatValue(cat[1]));
        this.m_CategoryModel.setSelectedKey(cat[2]);
        this.m_jImage.setImage((BufferedImage)cat[3]);
        this.m_jTextTip.setText(Formats.STRING.formatValue(cat[4]));
        this.m_jCatNameShow.setSelected((Boolean)cat[5]);
        if (this.m_jCatOrder.getText().length() == 0) {
            this.m_jCatOrder.setText(null);
        }
        this.m_jCatOrder.setText(Formats.STRING.formatValue(cat[6]));
        this.m_jName.setEnabled(true);
        this.m_jCategory.setEnabled(true);
        this.m_jImage.setEnabled(true);
        this.webSwtch_InCatalog.setEnabled(true);
        this.m_jTextTip.setEnabled(true);
        this.m_jCatNameShow.setEnabled(true);
        this.m_jCatOrder.setEnabled(true);
    }

    @Override
    public Object createValue() throws BasicException {
        Object[] cat = new Object[8];
        cat[0] = this.m_id;
        cat[1] = this.m_jName.getText();
        cat[2] = this.m_CategoryModel.getSelectedKey();
        cat[3] = this.m_jImage.getImage();
        cat[4] = this.m_jTextTip.getText();
        cat[5] = this.m_jCatNameShow.isSelected();
        if (this.m_jCatOrder.getText().length() == 0) {
            this.m_jCatOrder.setText(null);
        }
        cat[6] = this.m_jCatOrder.getText();
        return cat;
    }

    @Override
    public Component getComponent() {
        return this;
    }

    public void Notify(String msg) {
        Logger.getLogger(CategoriesEditor.class.getName()).log(Level.INFO, msg);
    }

    private void initComponents() {
        this.jInternalFrame1 = new JInternalFrame();
        this.jLblName = new JLabel();
        this.m_jName = new JTextField();
        this.jLblCategory = new JLabel();
        this.m_jCategory = new JComboBox();
        this.jLblTextTip = new JLabel();
        this.m_jTextTip = new JTextField();
        this.jLblCatShowName = new JLabel();
        this.m_jCatNameShow = new JCheckBox();
        this.jLblCatOrder = new JLabel();
        this.m_jCatOrder = new JTextField();
        this.jLblImage = new JLabel();
        this.m_jImage = new JImageEditor();
        this.jLblInCat = new JLabel();
        this.webSwtch_InCatalog = new JCheckBox();
        this.jInternalFrame1.setVisible(true);
        this.setPreferredSize(new Dimension(500, 500));
        this.jLblName.setFont(new Font("Arial", 0, 14));
        this.jLblName.setText(AppLocal.getIntString("label.namem"));
        this.jLblName.setPreferredSize(new Dimension(150, 30));
        this.m_jName.setFont(new Font("Arial", 0, 14));
        this.m_jName.setPreferredSize(new Dimension(250, 30));
        this.jLblCategory.setFont(new Font("Arial", 0, 14));
        this.jLblCategory.setText(AppLocal.getIntString("label.prodcategory"));
        this.jLblCategory.setPreferredSize(new Dimension(150, 30));
        this.m_jCategory.setFont(new Font("Arial", 0, 14));
        this.m_jCategory.setPreferredSize(new Dimension(250, 30));
        this.jLblTextTip.setFont(new Font("Arial", 0, 14));
        ResourceBundle bundle = ResourceBundle.getBundle("pos_messages");
        this.jLblTextTip.setText(bundle.getString("label.texttip"));
        this.jLblTextTip.setPreferredSize(new Dimension(150, 30));
        this.m_jTextTip.setFont(new Font("Arial", 0, 14));
        this.m_jTextTip.setPreferredSize(new Dimension(250, 30));
        this.jLblCatShowName.setFont(new Font("Arial", 0, 14));
        this.jLblCatShowName.setText(bundle.getString("label.subcategorytitle"));
        this.jLblCatShowName.setPreferredSize(new Dimension(150, 30));
        this.m_jCatNameShow.setFont(new Font("Arial", 0, 12));
        this.m_jCatNameShow.setSelected(true);
        this.m_jCatNameShow.setPreferredSize(new Dimension(30, 30));
        this.jLblCatOrder.setFont(new Font("Arial", 0, 14));
        this.jLblCatOrder.setHorizontalAlignment(4);
        this.jLblCatOrder.setText(bundle.getString("label.ccatorder"));
        this.jLblCatOrder.setPreferredSize(new Dimension(60, 30));
        this.m_jCatOrder.setFont(new Font("Arial", 0, 12));
        this.m_jCatOrder.setPreferredSize(new Dimension(60, 30));
        this.jLblImage.setFont(new Font("Arial", 0, 14));
        this.jLblImage.setText(AppLocal.getIntString("label.image"));
        this.jLblImage.setPreferredSize(new Dimension(150, 30));
        this.m_jImage.setPreferredSize(new Dimension(300, 250));
        this.jLblInCat.setFont(new Font("Arial", 0, 14));
        this.jLblInCat.setHorizontalAlignment(2);
        this.jLblInCat.setText(bundle.getString("label.CatalogueStatusYes"));
        this.jLblInCat.setHorizontalTextPosition(2);
        this.jLblInCat.setPreferredSize(new Dimension(150, 30));
        this.webSwtch_InCatalog.setFont(new Font("Arial", 0, 14));
        this.webSwtch_InCatalog.setPreferredSize(new Dimension(80, 30));
        this.webSwtch_InCatalog.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                CategoriesEditor.this.webSwtch_InCatalogActionPerformed(evt);
            }
        });
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(10, 10, 10).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLblTextTip, -2, -1, -2).addComponent(this.jLblCategory, -2, -1, -2))).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jLblInCat, -2, -1, -2)).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jLblName, -2, -1, -2)).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jLblCatShowName, -2, -1, -2))).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.m_jName, -2, -1, -2).addComponent(this.m_jTextTip, -2, -1, -2).addComponent(this.m_jCategory, -2, -1, -2).addGroup(layout.createSequentialGroup().addComponent(this.m_jCatNameShow, -2, 30, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jLblCatOrder, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.m_jCatOrder, -2, -1, -2)).addComponent(this.webSwtch_InCatalog, -2, -1, -2)).addGap(31, 31, 31)).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addContainerGap().addComponent(this.jLblImage, -2, -1, -2).addGap(18, 18, 18).addComponent(this.m_jImage, -2, 310, -2))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.jLblCategory, -2, -1, -2).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.m_jName, -2, -1, -2).addComponent(this.jLblName, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.m_jCategory, -2, -1, -2))).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLblTextTip, -2, -1, -2).addComponent(this.m_jTextTip, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.jLblInCat, -2, -1, -2).addComponent(this.webSwtch_InCatalog, -1, -1, -2)).addGap(18, 18, 18).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLblCatShowName, -2, -1, -2).addComponent(this.m_jCatNameShow, -2, -1, -2).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLblCatOrder, -2, -1, -2).addComponent(this.m_jCatOrder, -2, -1, -2))).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.m_jImage, -2, -1, -2).addGroup(layout.createSequentialGroup().addGap(94, 94, 94).addComponent(this.jLblImage, -2, -1, -2))).addContainerGap()));
    }

    private void webSwtch_InCatalogActionPerformed(ActionEvent evt) {
        if (this.webSwtch_InCatalog.isSelected()) {
            try {
                Object param = this.m_id;
                this.m_sentdel.exec((String)param);
                this.m_sentadd.exec((String)param);
                this.jLblInCat.setText(AppLocal.getIntString("label.CatalogueStatusYes"));
                this.Notify(AppLocal.getIntString("notify.added"));
            }
            catch (BasicException e) {
                JMessageDialog.showMessage(this, new MessageInf(-33554432, AppLocal.getIntString("message.cannotexecute"), e));
            }
        } else {
            try {
                this.m_sentdel.exec((String)this.m_id);
                this.jLblInCat.setText(AppLocal.getIntString("label.CatalogueStatusNo"));
                this.Notify(AppLocal.getIntString("notify.removed"));
            }
            catch (BasicException e) {
                JMessageDialog.showMessage(this, new MessageInf(-33554432, AppLocal.getIntString("message.cannotexecute"), e));
            }
        }
    }
}

