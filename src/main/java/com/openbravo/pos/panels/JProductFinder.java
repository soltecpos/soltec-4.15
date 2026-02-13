/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.panels;

import com.openbravo.basic.BasicException;
import com.openbravo.data.gui.JImageViewerProduct;
import com.openbravo.data.user.ListProvider;
import com.openbravo.data.user.ListProviderCreator;
import com.openbravo.editor.JEditorKeys;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.DataLogicSales;
import com.openbravo.pos.ticket.ProductFilterSales;
import com.openbravo.pos.ticket.ProductInfoExt;
import com.openbravo.pos.ticket.ProductRenderer;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class JProductFinder
extends JDialog {
    private ProductInfoExt m_ReturnProduct;
    private ListProvider<ProductInfoExt> lpr;
    public static final int PRODUCT_ALL = 0;
    public static final int PRODUCT_NORMAL = 1;
    public static final int PRODUCT_AUXILIAR = 2;
    public static final int PRODUCT_BUNDLE = 3;
    private Object dlSales;
    private JButton jButton3;
    private JImageViewerProduct jImageViewerProduct;
    private JList jListProducts;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JPanel jPanel3;
    private JPanel jPanel4;
    private JPanel jPanel5;
    private JPanel jPanel6;
    private JScrollPane jScrollPane1;
    private JButton jcmdCancel;
    private JButton jcmdOK;
    private JEditorKeys m_jKeys;
    private JPanel m_jProductSelect;

    private JProductFinder(Frame parent, boolean modal) {
        super(parent, modal);
    }

    private JProductFinder(Dialog parent, boolean modal) {
        super(parent, modal);
    }

    private ProductInfoExt init(DataLogicSales dlSales2, int productsType) {
        this.initComponents();
        this.jScrollPane1.getVerticalScrollBar().setPreferredSize(new Dimension(35, 35));
        this.jScrollPane1.getHorizontalScrollBar().setPreferredSize(new Dimension(35, 35));
        ProductFilterSales jproductfilter = new ProductFilterSales(dlSales2, this.m_jKeys);
        jproductfilter.activate();
        this.m_jProductSelect.add((Component)jproductfilter, "Center");
        switch (productsType) {
            case 1: {
                this.lpr = new ListProviderCreator<ProductInfoExt>(dlSales2.getProductListNormal(), jproductfilter);
                break;
            }
            case 2: {
                this.lpr = new ListProviderCreator<ProductInfoExt>(dlSales2.getProductListAuxiliar(), jproductfilter);
                break;
            }
            default: {
                this.lpr = new ListProviderCreator<ProductInfoExt>(dlSales2.getProductList(), jproductfilter);
            }
        }
        this.jListProducts.setCellRenderer(new ProductRenderer());
        this.jListProducts.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                if (evt.getKeyCode() == 10) {
                    JProductFinder.this.jcmdOK.doClick();
                }
            }
        });
        this.getRootPane().setDefaultButton(this.jcmdOK);
        this.m_ReturnProduct = null;
        this.setVisible(true);
        return this.m_ReturnProduct;
    }

    private static Window getWindow(Component parent) {
        if (parent == null) {
            return new JFrame();
        }
        if (parent instanceof Frame || parent instanceof Dialog) {
            return (Window)parent;
        }
        return JProductFinder.getWindow(parent.getParent());
    }

    public static ProductInfoExt showMessage(Component parent, DataLogicSales dlSales2) {
        return JProductFinder.showMessage(parent, dlSales2, 0);
    }

    public static ProductInfoExt showMessage(Component parent, DataLogicSales dlSales2, int productsType) {
        Window window = JProductFinder.getWindow(parent);
        JProductFinder myMsg = window instanceof Frame ? new JProductFinder((Frame)window, true) : new JProductFinder((Dialog)window, true);
        return myMsg.init(dlSales2, productsType);
    }

    private void initComponents() {
        this.jPanel2 = new JPanel();
        this.m_jProductSelect = new JPanel();
        this.jPanel3 = new JPanel();
        this.jButton3 = new JButton();
        this.jPanel5 = new JPanel();
        this.jScrollPane1 = new JScrollPane();
        this.jListProducts = new JList();
        this.jPanel4 = new JPanel();
        this.m_jKeys = new JEditorKeys();
        this.jPanel1 = new JPanel();
        this.jcmdCancel = new JButton();
        this.jcmdOK = new JButton();
        this.jPanel6 = new JPanel();
        this.jImageViewerProduct = new JImageViewerProduct();
        this.setDefaultCloseOperation(2);
        this.setTitle(AppLocal.getIntString("form.productslist"));
        this.setPreferredSize(new Dimension(750, 600));
        this.jPanel2.setPreferredSize(new Dimension(450, 0));
        this.jPanel2.setLayout(new BorderLayout());
        this.m_jProductSelect.setLayout(new BorderLayout());
        this.jButton3.setFont(new Font("Arial", 0, 12));
        this.jButton3.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/ok.png")));
        this.jButton3.setText(AppLocal.getIntString("button.executefilter"));
        this.jButton3.setToolTipText("Execute Filter");
        this.jButton3.setPreferredSize(new Dimension(110, 45));
        this.jButton3.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JProductFinder.this.jButton3ActionPerformed(evt);
            }
        });
        this.jPanel3.add(this.jButton3);
        this.m_jProductSelect.add((Component)this.jPanel3, "South");
        this.jPanel2.add((Component)this.m_jProductSelect, "North");
        this.jPanel5.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        this.jPanel5.setFont(new Font("Arial", 0, 12));
        this.jPanel5.setPreferredSize(new Dimension(450, 140));
        this.jPanel5.setLayout(new BorderLayout());
        this.jScrollPane1.setFont(new Font("Arial", 0, 12));
        this.jScrollPane1.setPreferredSize(new Dimension(400, 147));
        this.jListProducts.setFont(new Font("Arial", 0, 12));
        this.jListProducts.setSelectionMode(0);
        this.jListProducts.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent evt) {
                JProductFinder.this.jListProductsMouseClicked(evt);
            }
        });
        this.jListProducts.addListSelectionListener(new ListSelectionListener(){

            @Override
            public void valueChanged(ListSelectionEvent evt) {
                JProductFinder.this.jListProductsValueChanged(evt);
            }
        });
        this.jScrollPane1.setViewportView(this.jListProducts);
        this.jPanel5.add((Component)this.jScrollPane1, "Center");
        this.jPanel2.add((Component)this.jPanel5, "West");
        this.getContentPane().add((Component)this.jPanel2, "West");
        this.jPanel4.setPreferredSize(new Dimension(300, 0));
        this.jPanel4.setLayout(new BorderLayout());
        this.m_jKeys.setPreferredSize(new Dimension(290, 300));
        this.m_jKeys.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JProductFinder.this.m_jKeysActionPerformed(evt);
            }
        });
        this.jPanel4.add((Component)this.m_jKeys, "North");
        this.jPanel1.setLayout(new FlowLayout(2));
        this.jcmdCancel.setFont(new Font("Arial", 0, 12));
        this.jcmdCancel.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/cancel.png")));
        this.jcmdCancel.setText(AppLocal.getIntString("button.cancel"));
        this.jcmdCancel.setMargin(new Insets(8, 16, 8, 16));
        this.jcmdCancel.setMaximumSize(new Dimension(103, 44));
        this.jcmdCancel.setMinimumSize(new Dimension(103, 44));
        this.jcmdCancel.setPreferredSize(new Dimension(110, 45));
        this.jcmdCancel.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JProductFinder.this.jcmdCancelActionPerformed(evt);
            }
        });
        this.jPanel1.add(this.jcmdCancel);
        this.jcmdOK.setFont(new Font("Arial", 0, 12));
        this.jcmdOK.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/ok.png")));
        this.jcmdOK.setText(AppLocal.getIntString("button.OK"));
        this.jcmdOK.setEnabled(false);
        this.jcmdOK.setMargin(new Insets(8, 16, 8, 16));
        this.jcmdOK.setMaximumSize(new Dimension(103, 44));
        this.jcmdOK.setMinimumSize(new Dimension(103, 44));
        this.jcmdOK.setPreferredSize(new Dimension(110, 45));
        this.jcmdOK.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JProductFinder.this.jcmdOKActionPerformed(evt);
            }
        });
        this.jPanel1.add(this.jcmdOK);
        this.jPanel4.add((Component)this.jPanel1, "Last");
        GroupLayout jPanel6Layout = new GroupLayout(this.jPanel6);
        this.jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(jPanel6Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup().addContainerGap().addComponent(this.jImageViewerProduct, -1, 280, Short.MAX_VALUE).addContainerGap()));
        jPanel6Layout.setVerticalGroup(jPanel6Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup().addContainerGap(12, Short.MAX_VALUE).addComponent(this.jImageViewerProduct, -2, 222, -2).addContainerGap()));
        this.jPanel4.add((Component)this.jPanel6, "Center");
        this.getContentPane().add((Component)this.jPanel4, "East");
        this.setSize(new Dimension(758, 634));
        this.setLocationRelativeTo(null);
    }

    private void jListProductsMouseClicked(MouseEvent evt) {
        this.m_ReturnProduct = (ProductInfoExt)this.jListProducts.getSelectedValue();
        if (this.m_ReturnProduct != null) {
            this.m_ReturnProduct = (ProductInfoExt)this.jListProducts.getSelectedValue();
            if (this.m_ReturnProduct != null) {
                this.jImageViewerProduct.setImage(this.m_ReturnProduct.getImage());
            }
        }
    }

    private void jcmdOKActionPerformed(ActionEvent evt) {
        this.m_ReturnProduct = (ProductInfoExt)this.jListProducts.getSelectedValue();
        this.dispose();
    }

    private void jcmdCancelActionPerformed(ActionEvent evt) {
        this.m_ReturnProduct = null;
        this.dispose();
    }

    private void jListProductsValueChanged(ListSelectionEvent evt) {
        this.m_ReturnProduct = (ProductInfoExt)this.jListProducts.getSelectedValue();
        if (this.m_ReturnProduct != null) {
            this.m_ReturnProduct = (ProductInfoExt)this.jListProducts.getSelectedValue();
            if (this.m_ReturnProduct != null) {
                this.jImageViewerProduct.setImage(this.m_ReturnProduct.getImage());
            }
        }
        this.jcmdOK.setEnabled(this.jListProducts.getSelectedValue() != null);
    }

    private void jButton3ActionPerformed(ActionEvent evt) {
        try {
            this.jListProducts.setModel(new MyListData(this.lpr.loadData()));
            if (this.jListProducts.getModel().getSize() > 0) {
                this.jListProducts.setSelectedIndex(0);
            }
        }
        catch (BasicException basicException) {
            // empty catch block
        }
    }

    private void m_jKeysActionPerformed(ActionEvent evt) {
        this.jButton3ActionPerformed(evt);
    }

    private static class MyListData
    extends AbstractListModel<ProductInfoExt> {
        private final List<ProductInfoExt> m_data;

        public MyListData(List<ProductInfoExt> data) {
            this.m_data = data;
        }

        @Override
        public ProductInfoExt getElementAt(int index) {
            return this.m_data.get(index);
        }

        @Override
        public int getSize() {
            return this.m_data.size();
        }
    }
}

