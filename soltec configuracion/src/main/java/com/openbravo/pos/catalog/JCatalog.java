/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.catalog;

import com.openbravo.basic.BasicException;
import com.openbravo.data.gui.JMessageDialog;
import com.openbravo.data.gui.MessageInf;
import com.openbravo.pos.catalog.CatalogSelector;
import com.openbravo.pos.catalog.JCatalogTab;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.DataLogicSales;
import com.openbravo.pos.sales.TaxesLogic;
import com.openbravo.pos.ticket.CategoryInfo;
import com.openbravo.pos.ticket.ProductInfoExt;
import com.openbravo.pos.ticket.TaxInfo;
import com.openbravo.pos.util.ThumbNailBuilder;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.EventListenerList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class JCatalog
extends JPanel
implements ListSelectionListener,
CatalogSelector {
    protected EventListenerList listeners = new EventListenerList();
    private DataLogicSales m_dlSales;
    private TaxesLogic taxeslogic;
    private boolean pricevisible;
    private boolean taxesincluded;
    private int m_width;
    private int m_height;
    private final Map<String, ProductInfoExt> m_productsset = new HashMap<String, ProductInfoExt>();
    private final Set<String> m_categoriesset = new HashSet<String>();
    private ThumbNailBuilder tnbbutton;
    private ThumbNailBuilder tnbcat;
    private ThumbNailBuilder tnbsubcat;
    private CategoryInfo showingcategory = null;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JPanel jPanel3;
    private JPanel jPanel4;
    private JPanel jPanel5;
    private JButton m_btnBack1;
    private JPanel m_jCategories;
    private JList<CategoryInfo> m_jListCategories;
    private JPanel m_jProducts;
    private JPanel m_jRootCategories;
    private JPanel m_jSubCategories;
    private JScrollPane m_jscrollcat;
    private JLabel m_lblIndicator;

    public JCatalog(DataLogicSales dlSales2) {
        this(dlSales2, false, false, 90, 60);
    }

    public JCatalog(DataLogicSales dlSales2, boolean pricevisible, boolean taxesincluded, int width, int height) {
        this.m_dlSales = dlSales2;
        this.pricevisible = pricevisible;
        this.taxesincluded = taxesincluded;
        this.m_width = width;
        this.m_height = height;
        this.initComponents();
        this.m_jListCategories.addListSelectionListener(this);
        this.m_jscrollcat.getVerticalScrollBar().setPreferredSize(new Dimension(35, 35));
        this.tnbcat = new ThumbNailBuilder(48, 48, "com/openbravo/images/category.png");
        this.tnbsubcat = new ThumbNailBuilder(width, height, "com/openbravo/images/subcategory.png");
        this.tnbbutton = new ThumbNailBuilder(width, height, "com/openbravo/images/null.png");
    }

    @Override
    public Component getComponent() {
        return this;
    }

    @Override
    public void showCatalogPanel(String id) {
        if (id == null) {
            this.showRootCategoriesPanel();
        } else {
            this.showProductPanel(id);
        }
    }

    @Override
    public void loadCatalog() throws BasicException {
        this.m_jProducts.removeAll();
        this.m_productsset.clear();
        this.m_categoriesset.clear();
        this.showingcategory = null;
        this.taxeslogic = new TaxesLogic(this.m_dlSales.getTaxList().list());
        List<CategoryInfo> categories = this.m_dlSales.getRootCategories();
        this.m_jListCategories.setCellRenderer(new SmallCategoryRenderer());
        this.m_jListCategories.setModel(new CategoriesListModel(categories));
        if (this.m_jListCategories.getModel().getSize() == 0) {
            this.m_jscrollcat.setVisible(false);
            this.jPanel2.setVisible(false);
        } else {
            this.m_jscrollcat.setVisible(true);
            this.jPanel2.setVisible(true);
            this.m_jListCategories.setSelectedIndex(0);
        }
        this.showRootCategoriesPanel();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void setComponentEnabled(boolean value) {
        this.m_jListCategories.setEnabled(value);
        this.m_jscrollcat.setEnabled(value);
        this.m_lblIndicator.setEnabled(value);
        this.m_btnBack1.setEnabled(value);
        this.m_jProducts.setEnabled(value);
        Object object = this.m_jProducts.getTreeLock();
        synchronized (object) {
            int compCount = this.m_jProducts.getComponentCount();
            for (int i = 0; i < compCount; ++i) {
                this.m_jProducts.getComponent(i).setEnabled(value);
            }
        }
        this.setEnabled(value);
    }

    @Override
    public void addActionListener(ActionListener l) {
        this.listeners.add(ActionListener.class, l);
    }

    @Override
    public void removeActionListener(ActionListener l) {
        this.listeners.remove(ActionListener.class, l);
    }

    @Override
    public void valueChanged(ListSelectionEvent evt) {
        int i;
        if (!evt.getValueIsAdjusting() && (i = this.m_jListCategories.getSelectedIndex()) >= 0) {
            Rectangle oRect = this.m_jListCategories.getCellBounds(i, i);
            this.m_jListCategories.scrollRectToVisible(oRect);
        }
    }

    protected void fireSelectedProduct(ProductInfoExt prod) {
        EventListener[] l = this.listeners.getListeners(ActionListener.class);
        ActionEvent e = null;
        for (EventListener l1 : l) {
            if (e == null) {
                e = new ActionEvent(prod, 1001, prod.getID());
            }
            ((ActionListener)l1).actionPerformed(e);
        }
    }

    private void selectCategoryPanel(String catid) {
        try {
            if (!this.m_categoriesset.contains(catid)) {
                JCatalogTab jcurrTab = new JCatalogTab();
                jcurrTab.applyComponentOrientation(this.getComponentOrientation());
                jcurrTab.setButtonDimesions(this.m_width, this.m_height);
                this.m_jProducts.add((Component)jcurrTab, catid);
                this.m_categoriesset.add(catid);
                List<CategoryInfo> categories = this.m_dlSales.getSubcategories(catid);
                for (CategoryInfo categoryInfo : categories) {
                    if (categoryInfo.getCatShowName().booleanValue()) {
                        jcurrTab.addButton(new ImageIcon(this.tnbsubcat.getThumbNail(categoryInfo.getImage())), new SelectedCategory(categoryInfo), categoryInfo.getTextTip(), categoryInfo.getName());
                        continue;
                    }
                    jcurrTab.addButton(new ImageIcon(this.tnbsubcat.getThumbNail(categoryInfo.getImage())), new SelectedCategory(categoryInfo), categoryInfo.getTextTip(), "");
                }
                List<ProductInfoExt> prods = this.m_dlSales.getProductConstant();
                for (ProductInfoExt prod : prods) {
                    jcurrTab.addButton(new ImageIcon(this.tnbbutton.getThumbNail(prod.getImage())), new SelectedAction(prod), prod.getTextTip(), this.getProductLabel(prod));
                }
                List<ProductInfoExt> list = this.m_dlSales.getProductCatalog(catid);
                for (ProductInfoExt prod : list) {
                    jcurrTab.addButton(new ImageIcon(this.tnbbutton.getThumbNail(prod.getImage())), new SelectedAction(prod), prod.getTextTip(), this.getProductLabel(prod));
                }
            }
            CardLayout cl = (CardLayout)this.m_jProducts.getLayout();
            cl.show(this.m_jProducts, catid);
        }
        catch (BasicException e) {
            JMessageDialog.showMessage(this, new MessageInf(-33554432, AppLocal.getIntString("message.notactive"), e));
        }
    }

    private String getProductLabel(ProductInfoExt product) {
        if (this.pricevisible) {
            if (this.taxesincluded) {
                TaxInfo tax = this.taxeslogic.getTaxInfo(product.getTaxCategoryID());
                if (!"".equals(product.getDisplay())) {
                    return "<html><center>" + product.getDisplay() + "</center><br><center><font color=\"#003366\"><b>" + product.printPriceSellTax(tax) + "</b></font></center></html>";
                }
                return "<html><center>" + product.getName() + "</center><br><center><font color=\"#003366\"><b>" + product.printPriceSellTax(tax) + "</b></font></center></html>";
            }
            if (!"".equals(product.getDisplay())) {
                return "<html><center>" + product.getDisplay() + "</center><br><center><font color=\"#003366\"><b>" + product.printPriceSell() + "</b></font></center></html>";
            }
            return "<html><center>" + product.getName() + "</center><br><center><font color=\"#003366\"><b>" + product.printPriceSell() + "</b></font></center></html>";
        }
        if (!"".equals(product.getDisplay())) {
            return product.getDisplay();
        }
        return product.getName();
    }

    private void selectIndicatorPanel(Icon icon, String label, String texttip) {
        this.m_lblIndicator.setText(label);
        this.m_lblIndicator.setIcon(icon);
        CardLayout cl = (CardLayout)this.m_jCategories.getLayout();
        cl.show(this.m_jCategories, "subcategories");
    }

    private void selectIndicatorCategories() {
        CardLayout cl = (CardLayout)this.m_jCategories.getLayout();
        cl.show(this.m_jCategories, "rootcategories");
    }

    private void showRootCategoriesPanel() {
        this.selectIndicatorCategories();
        CategoryInfo cat = this.m_jListCategories.getSelectedValue();
        if (cat != null) {
            this.selectCategoryPanel(cat.getID());
        }
        this.showingcategory = null;
    }

    private void showSubcategoryPanel(CategoryInfo category) {
        this.selectIndicatorPanel(new ImageIcon(this.tnbsubcat.getThumbNail(category.getImage())), category.getName(), category.getTextTip());
        this.selectCategoryPanel(category.getID());
        this.showingcategory = category;
    }

    private void showProductPanel(String id) {
        block13: {
            ProductInfoExt product = this.m_productsset.get(id);
            if (product == null) {
                if (this.m_productsset.containsKey(id)) {
                    if (this.showingcategory == null) {
                        this.showRootCategoriesPanel();
                    } else {
                        this.showSubcategoryPanel(this.showingcategory);
                    }
                } else {
                    try {
                        List<ProductInfoExt> products = this.m_dlSales.getProductComments(id);
                        if (products.isEmpty()) {
                            this.m_productsset.put(id, null);
                            if (this.showingcategory == null) {
                                this.showRootCategoriesPanel();
                            } else {
                                this.showSubcategoryPanel(this.showingcategory);
                            }
                            break block13;
                        }
                        product = this.m_dlSales.getProductInfo(id);
                        this.m_productsset.put(id, product);
                        JCatalogTab jcurrTab = new JCatalogTab();
                        jcurrTab.applyComponentOrientation(this.getComponentOrientation());
                        jcurrTab.setButtonDimesions(this.m_width, this.m_height);
                        this.m_jProducts.add((Component)jcurrTab, "PRODUCT." + id);
                        for (ProductInfoExt prod : products) {
                            jcurrTab.addButton(new ImageIcon(this.tnbbutton.getThumbNail(prod.getImage())), new SelectedAction(prod), prod.getTextTip(), this.getProductLabel(prod));
                        }
                        this.selectIndicatorPanel(new ImageIcon(this.tnbbutton.getThumbNail(product.getImage())), product.getDisplay(), product.getTextTip());
                        CardLayout cl = (CardLayout)this.m_jProducts.getLayout();
                        cl.show(this.m_jProducts, "PRODUCT." + id);
                    }
                    catch (BasicException eb) {
                        this.m_productsset.put(id, null);
                        if (this.showingcategory == null) {
                            this.showRootCategoriesPanel();
                            break block13;
                        }
                        this.showSubcategoryPanel(this.showingcategory);
                    }
                }
            } else {
                this.selectIndicatorPanel(new ImageIcon(this.tnbbutton.getThumbNail(product.getImage())), product.getName(), product.getTextTip());
                CardLayout cl = (CardLayout)this.m_jProducts.getLayout();
                cl.show(this.m_jProducts, "PRODUCT." + id);
            }
        }
    }

    private void initComponents() {
        this.m_jCategories = new JPanel();
        this.m_jRootCategories = new JPanel();
        this.m_jscrollcat = new JScrollPane();
        this.m_jListCategories = new JList();
        this.jPanel2 = new JPanel();
        this.jPanel3 = new JPanel();
        this.m_jSubCategories = new JPanel();
        this.jPanel4 = new JPanel();
        this.m_lblIndicator = new JLabel();
        this.jPanel1 = new JPanel();
        this.jPanel5 = new JPanel();
        this.m_btnBack1 = new JButton();
        this.m_jProducts = new JPanel();
        this.setLayout(new BorderLayout());
        this.m_jCategories.setFont(new Font("Arial", 0, 12));
        this.m_jCategories.setMaximumSize(new Dimension(275, 600));
        this.m_jCategories.setPreferredSize(new Dimension(265, 0));
        this.m_jCategories.setLayout(new CardLayout());
        this.m_jRootCategories.setFont(new Font("Arial", 0, 11));
        this.m_jRootCategories.setMinimumSize(new Dimension(200, 100));
        this.m_jRootCategories.setPreferredSize(new Dimension(275, 130));
        this.m_jRootCategories.setLayout(new BorderLayout());
        this.m_jscrollcat.setHorizontalScrollBarPolicy(31);
        this.m_jscrollcat.setVerticalScrollBarPolicy(22);
        this.m_jscrollcat.setFont(new Font("Arial", 0, 12));
        this.m_jscrollcat.setPreferredSize(new Dimension(265, 130));
        this.m_jListCategories.setFont(new Font("Arial", 0, 12));
        this.m_jListCategories.setSelectionMode(0);
        this.m_jListCategories.setFocusable(false);
        this.m_jListCategories.addListSelectionListener(new ListSelectionListener(){

            @Override
            public void valueChanged(ListSelectionEvent evt) {
                JCatalog.this.m_jListCategoriesValueChanged(evt);
            }
        });
        this.m_jscrollcat.setViewportView(this.m_jListCategories);
        this.m_jRootCategories.add((Component)this.m_jscrollcat, "West");
        this.jPanel2.setLayout(new BorderLayout());
        this.jPanel3.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        this.jPanel3.setLayout(new GridLayout(0, 1, 0, 5));
        this.jPanel2.add((Component)this.jPanel3, "North");
        this.m_jRootCategories.add((Component)this.jPanel2, "After");
        this.m_jCategories.add((Component)this.m_jRootCategories, "rootcategories");
        this.m_jSubCategories.setLayout(new BorderLayout());
        this.jPanel4.setLayout(new BorderLayout());
        this.m_lblIndicator.setFont(new Font("Arial", 0, 14));
        this.m_lblIndicator.setText("jLabel1");
        this.jPanel4.add((Component)this.m_lblIndicator, "North");
        this.m_jSubCategories.add((Component)this.jPanel4, "West");
        this.jPanel1.setLayout(new BorderLayout());
        this.jPanel5.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        this.jPanel5.setLayout(new GridLayout(0, 1, 0, 5));
        this.m_btnBack1.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/2uparrow.png")));
        this.m_btnBack1.setFocusPainted(false);
        this.m_btnBack1.setFocusable(false);
        this.m_btnBack1.setMargin(new Insets(8, 14, 8, 14));
        this.m_btnBack1.setPreferredSize(new Dimension(60, 45));
        this.m_btnBack1.setRequestFocusEnabled(false);
        this.m_btnBack1.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JCatalog.this.m_btnBack1ActionPerformed(evt);
            }
        });
        this.jPanel5.add(this.m_btnBack1);
        this.jPanel1.add((Component)this.jPanel5, "North");
        this.m_jSubCategories.add((Component)this.jPanel1, "After");
        this.m_jCategories.add((Component)this.m_jSubCategories, "subcategories");
        this.add((Component)this.m_jCategories, "Before");
        this.m_jProducts.setFont(new Font("Arial", 0, 14));
        this.m_jProducts.setLayout(new CardLayout());
        this.add((Component)this.m_jProducts, "Center");
    }

    private void m_jListCategoriesValueChanged(ListSelectionEvent evt) {
        CategoryInfo cat;
        if (!evt.getValueIsAdjusting() && (cat = this.m_jListCategories.getSelectedValue()) != null) {
            this.selectCategoryPanel(cat.getID());
        }
    }

    private void m_btnBack1ActionPerformed(ActionEvent evt) {
        this.showRootCategoriesPanel();
    }

    private class SmallCategoryRenderer
    extends DefaultListCellRenderer {
        private SmallCategoryRenderer() {
        }

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent((JList<?>)list, (Object)null, index, isSelected, cellHasFocus);
            CategoryInfo cat = (CategoryInfo)value;
            this.setText(cat.getName());
            this.setIcon(new ImageIcon(JCatalog.this.tnbcat.getThumbNail(cat.getImage())));
            return this;
        }
    }

    private class CategoriesListModel
    extends AbstractListModel<CategoryInfo> {
        private final List<CategoryInfo> m_aCategories;

        public CategoriesListModel(List<CategoryInfo> aCategories) {
            this.m_aCategories = aCategories;
        }

        @Override
        public int getSize() {
            return this.m_aCategories.size();
        }

        @Override
        public CategoryInfo getElementAt(int i) {
            return this.m_aCategories.get(i);
        }
    }

    private class SelectedCategory
    implements ActionListener {
        private final CategoryInfo category;

        public SelectedCategory(CategoryInfo category) {
            this.category = category;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JCatalog.this.showSubcategoryPanel(this.category);
        }
    }

    private class SelectedAction
    implements ActionListener {
        private final ProductInfoExt prod;

        public SelectedAction(ProductInfoExt prod) {
            this.prod = prod;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JCatalog.this.fireSelectedProduct(this.prod);
        }
    }
}

