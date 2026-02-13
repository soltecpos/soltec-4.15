/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.panels;

import com.openbravo.basic.BasicException;
import com.openbravo.data.gui.MessageInf;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.DataLogicSales;
import com.openbravo.pos.ticket.ProductInfoExt;
import com.openbravo.pos.ticket.ProductRenderer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class JProductQuickSearch
extends JDialog {
    private ProductInfoExt m_ReturnProduct;
    private DataLogicSales dlSales;
    private JTextField txtSearch;
    private JList<ProductInfoExt> listProducts;
    private DefaultListModel<ProductInfoExt> listModel;

    private JProductQuickSearch(Frame parent, boolean modal) {
        super(parent, modal);
    }

    private JProductQuickSearch(Dialog parent, boolean modal) {
        super(parent, modal);
    }

    private ProductInfoExt init(DataLogicSales dlSales2) {
        this.dlSales = dlSales2;
        this.initComponents();
        this.addWindowListener(new WindowAdapter(){

            @Override
            public void windowOpened(WindowEvent e) {
                JProductQuickSearch.this.txtSearch.requestFocus();
            }
        });
        this.setVisible(true);
        return this.m_ReturnProduct;
    }

    private void initComponents() {
        this.setTitle(AppLocal.getIntString("form.productslist"));
        this.setLayout(new BorderLayout());
        this.txtSearch = new JTextField();
        this.txtSearch.setFont(new Font("Arial", 0, 18));
        this.txtSearch.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10), BorderFactory.createLineBorder(Color.GRAY)));
        this.txtSearch.getDocument().addDocumentListener(new DocumentListener(){

            @Override
            public void insertUpdate(DocumentEvent e) {
                JProductQuickSearch.this.performSearch();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                JProductQuickSearch.this.performSearch();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                JProductQuickSearch.this.performSearch();
            }
        });
        this.txtSearch.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                if (evt.getKeyCode() == 10) {
                    if (JProductQuickSearch.this.listModel.getSize() > 0) {
                        JProductQuickSearch.this.listProducts.setSelectedIndex(0);
                        JProductQuickSearch.this.selectAndClose();
                    }
                } else if (evt.getKeyCode() == 40) {
                    if (JProductQuickSearch.this.listModel.getSize() > 0) {
                        JProductQuickSearch.this.listProducts.setSelectedIndex(0);
                        JProductQuickSearch.this.listProducts.requestFocusInWindow();
                    }
                } else if (evt.getKeyCode() == 27) {
                    JProductQuickSearch.this.dispose();
                }
            }
        });
        this.listModel = new DefaultListModel();
        this.listProducts = new JList<ProductInfoExt>(this.listModel);
        this.listProducts.setCellRenderer(new ProductRenderer());
        this.listProducts.setSelectionMode(0);
        this.listProducts.setFont(new Font("Arial", 0, 14));
        this.listProducts.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                if (evt.getKeyCode() == 10) {
                    JProductQuickSearch.this.selectAndClose();
                } else if (evt.getKeyCode() == 38) {
                    if (JProductQuickSearch.this.listProducts.getSelectedIndex() <= 0) {
                        JProductQuickSearch.this.txtSearch.requestFocusInWindow();
                    }
                } else if (evt.getKeyCode() == 27) {
                    JProductQuickSearch.this.txtSearch.requestFocusInWindow();
                }
            }
        });
        this.listProducts.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    JProductQuickSearch.this.selectAndClose();
                }
            }
        });
        JScrollPane scrollPane = new JScrollPane(this.listProducts);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        this.add((Component)this.txtSearch, "North");
        this.add((Component)scrollPane, "Center");
        this.setSize(550, 400);
        this.setLocationRelativeTo(this.getParent());
        this.setLocationRelativeTo(this.getParent());
    }

    private void performSearch() {
        String query = this.txtSearch.getText();
        if ((query == null || query.trim().length() < 2) && query.isEmpty()) {
            this.listModel.clear();
            return;
        }
        try {
            List<ProductInfoExt> results = this.dlSales.getProductListQuick("%" + query + "%");
            this.listModel.clear();
            for (ProductInfoExt prod : results) {
                this.listModel.addElement(prod);
            }
        }
        catch (BasicException e) {
            new MessageInf(e).show(this);
        }
    }

    private void selectAndClose() {
        ProductInfoExt selected = this.listProducts.getSelectedValue();
        if (selected != null) {
            this.m_ReturnProduct = selected;
            this.dispose();
        }
    }

    private static Window getWindow(Component parent) {
        if (parent == null) {
            return new JFrame();
        }
        if (parent instanceof Frame || parent instanceof Dialog) {
            return (Window)parent;
        }
        return JProductQuickSearch.getWindow(parent.getParent());
    }

    public static ProductInfoExt showMessage(Component parent, DataLogicSales dlSales2) {
        Window window = JProductQuickSearch.getWindow(parent);
        JProductQuickSearch myMsg = window instanceof Frame ? new JProductQuickSearch((Frame)window, true) : new JProductQuickSearch((Dialog)window, true);
        return myMsg.init(dlSales2);
    }
}

