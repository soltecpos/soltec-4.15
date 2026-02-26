package com.openbravo.pos.inventory;

import com.openbravo.basic.BasicException;
import com.openbravo.pos.catalog.CatalogSelector;
import com.openbravo.pos.catalog.JCatalog;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.forms.BeanFactoryApp;
import com.openbravo.pos.forms.BeanFactoryException;
import com.openbravo.pos.forms.DataLogicSales;
import com.openbravo.pos.forms.JPanelView;
import com.openbravo.pos.ticket.ProductInfoExt;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Touch-friendly Merchandise Entry panel.
 * Uses JCatalog for category/product browsing and a numeric keypad for quantity input.
 */
public class JPanelMerchandiseEntry extends JPanel implements JPanelView, BeanFactoryApp {

    private AppView m_App;
    private DataLogicSales m_dlSales;
    
    // Product selection
    private CatalogSelector m_catalog;
    private ProductInfoExt selectedProduct;
    private JLabel lblSelectedProduct;
    
    // Quantity input
    private JTextField txtQuantity;
    private JTextField txtNotes;
    
    // Summary table
    private DefaultTableModel tableModel;
    private JTable table;
    private List<RecipeLine> receiptLines = new ArrayList<>();

    // Tracks whether the user entered quantity before selecting a product
    private boolean quantityEnteredFirst = false;

    // Keyboard product search
    private JTextField txtSearch;
    private JPopupMenu searchPopup;
    private JList<String> searchList;
    private DefaultListModel<String> searchModel;
    private List<ProductInfoExt> searchResults = new ArrayList<>();
    private Timer searchTimer;

    @Override
    public void init(AppView app) throws BeanFactoryException {
        this.m_App = app;
        this.m_dlSales = (DataLogicSales) m_App.getBean("com.openbravo.pos.forms.DataLogicSales");
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(5, 5));
        setBackground(new Color(240, 240, 240));

        // ==================== HEADER ====================
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(44, 62, 80));
        header.setPreferredSize(new Dimension(100, 50));
        JLabel lblTitle = new JLabel("  INGRESO DE MERCANCÍA");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitle.setForeground(Color.WHITE);
        header.add(lblTitle, BorderLayout.WEST);
        add(header, BorderLayout.NORTH);

        // ==================== MAIN CONTENT ====================
        // Left: Entry form (selected product + quantity + summary)
        // Right: This is where the catalog goes (bottom portion)
        
        JPanel mainPanel = new JPanel(new BorderLayout(5, 5));
        mainPanel.setBackground(new Color(240, 240, 240));
        
        // --- Top section: Selected product, quantity, notes, and summary table ---
        JPanel topPanel = new JPanel(new BorderLayout(10, 5));
        topPanel.setBackground(Color.WHITE);
        topPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        // Left side of top: Product info + Quantity + Numpad + Notes + Buttons
        JPanel entryPanel = new JPanel();
        entryPanel.setLayout(new javax.swing.BoxLayout(entryPanel, javax.swing.BoxLayout.Y_AXIS));
        entryPanel.setBackground(Color.WHITE);
        entryPanel.setPreferredSize(new Dimension(350, 0));
        
        // Selected product display
        JPanel productPanel = new JPanel(new BorderLayout(5, 0));
        productPanel.setBackground(Color.WHITE);
        productPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(52, 152, 219), 2), 
            "Producto Seleccionado",
            0, 0, new Font("Segoe UI", Font.BOLD, 12), new Color(52, 152, 219)
        ));
        lblSelectedProduct = new JLabel("Seleccione un producto ↓");
        lblSelectedProduct.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblSelectedProduct.setForeground(new Color(127, 140, 141));
        lblSelectedProduct.setHorizontalAlignment(JLabel.CENTER);
        productPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        productPanel.setPreferredSize(new Dimension(350, 55));
        productPanel.add(lblSelectedProduct, BorderLayout.CENTER);
        entryPanel.add(productPanel);
        
        // Quantity field
        JPanel qtyRow = new JPanel(new BorderLayout(5, 0));
        qtyRow.setBackground(Color.WHITE);
        qtyRow.setBorder(BorderFactory.createEmptyBorder(3, 5, 3, 5));
        qtyRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        JLabel lblQty = new JLabel(" Cant: ");
        lblQty.setFont(new Font("Segoe UI", Font.BOLD, 14));
        qtyRow.add(lblQty, BorderLayout.WEST);
        txtQuantity = new JTextField("");
        txtQuantity.setFont(new Font("Segoe UI", Font.BOLD, 28));
        txtQuantity.setHorizontalAlignment(JTextField.CENTER);
        txtQuantity.setEditable(true);
        txtQuantity.setBackground(new Color(253, 254, 254));
        txtQuantity.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(46, 204, 113), 2),
            BorderFactory.createEmptyBorder(3, 10, 3, 10)
        ));
        qtyRow.add(txtQuantity, BorderLayout.CENTER);
        // Track physical keyboard input for auto-add logic
        txtQuantity.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (selectedProduct == null && txtQuantity.getText().length() > 0) {
                    quantityEnteredFirst = true;
                }
            }
        });
        entryPanel.add(qtyRow);
        
        // Numeric keypad - half size
        JPanel numpad = createNumericKeypad();
        numpad.setMaximumSize(new Dimension(Integer.MAX_VALUE, 140));
        numpad.setPreferredSize(new Dimension(350, 120));
        entryPanel.add(numpad);
        
        // Notes field
        JPanel notesRow = new JPanel(new BorderLayout(5, 0));
        notesRow.setBackground(Color.WHITE);
        notesRow.setBorder(BorderFactory.createEmptyBorder(3, 5, 3, 5));
        notesRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        JLabel lblNotes = new JLabel(" Notas: ");
        lblNotes.setFont(new Font("Segoe UI", Font.BOLD, 12));
        notesRow.add(lblNotes, BorderLayout.WEST);
        txtNotes = new JTextField();
        txtNotes.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        notesRow.add(txtNotes, BorderLayout.CENTER);
        entryPanel.add(notesRow);
        
        // Action buttons
        JPanel actionsPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        actionsPanel.setBackground(Color.WHITE);
        actionsPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        actionsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        JButton btnAdd = createTouchButton("AGREGAR", new Color(46, 204, 113), Color.WHITE);
        btnAdd.addActionListener(e -> addLine());
        actionsPanel.add(btnAdd);
        JButton btnClear = createTouchButton("LIMPIAR", new Color(149, 165, 166), Color.WHITE);
        btnClear.addActionListener(e -> clearForm());
        actionsPanel.add(btnClear);
        entryPanel.add(actionsPanel);
        
        topPanel.add(entryPanel, BorderLayout.WEST);
        
        // Right side of top: Summary table
        JPanel summaryPanel = new JPanel(new BorderLayout(5, 5));
        summaryPanel.setBackground(Color.WHITE);
        summaryPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(52, 152, 219)),
            "Resumen de Ingreso",
            0, 0, new Font("Segoe UI", Font.BOLD, 12), new Color(52, 152, 219)
        ));
        
        tableModel = new DefaultTableModel(new Object[]{"Producto", "Cant.", "Notas"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(tableModel);
        table.setRowHeight(35);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.getColumnModel().getColumn(0).setPreferredWidth(200);
        table.getColumnModel().getColumn(1).setPreferredWidth(60);
        table.getColumnModel().getColumn(2).setPreferredWidth(120);
        summaryPanel.add(new JScrollPane(table), BorderLayout.CENTER);
        
        // Summary table buttons
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
        btnPanel.setBackground(Color.WHITE);
        JButton btnRemove = createTouchButton("ELIMINAR", new Color(231, 76, 60), Color.WHITE);
        btnRemove.setPreferredSize(new Dimension(150, 50));
        btnRemove.addActionListener(e -> removeLine());
        btnPanel.add(btnRemove);
        JButton btnSubmit = createTouchButton("ENVIAR A APROBACIÓN", new Color(52, 152, 219), Color.WHITE);
        btnSubmit.setPreferredSize(new Dimension(220, 50));
        btnSubmit.addActionListener(e -> submitReceipt());
        btnPanel.add(btnSubmit);
        summaryPanel.add(btnPanel, BorderLayout.SOUTH);
        
        topPanel.add(summaryPanel, BorderLayout.CENTER);
        
        mainPanel.add(topPanel, BorderLayout.CENTER);
        
        // ==================== BOTTOM: SEARCH + CATALOG ====================
        JPanel bottomPanel = new JPanel(new BorderLayout(5, 0));
        bottomPanel.setBackground(new Color(240, 240, 240));
        
        // Search bar for physical keyboard
        JPanel searchBarPanel = new JPanel(new BorderLayout(5, 0));
        searchBarPanel.setBackground(new Color(52, 73, 94));
        searchBarPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        JLabel lblSearch = new JLabel("  Buscar: ");
        lblSearch.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblSearch.setForeground(Color.WHITE);
        searchBarPanel.add(lblSearch, BorderLayout.WEST);
        txtSearch = new JTextField();
        txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtSearch.setPreferredSize(new Dimension(300, 35));
        txtSearch.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.WHITE, 1),
            BorderFactory.createEmptyBorder(3, 8, 3, 8)
        ));
        searchBarPanel.add(txtSearch, BorderLayout.CENTER);
        setupSearchAutocomplete();
        bottomPanel.add(searchBarPanel, BorderLayout.NORTH);
        
        // Catalog
        m_catalog = new JCatalog(m_dlSales, false, false, 80, 60);
        m_catalog.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductInfoExt prod = (ProductInfoExt) e.getSource();
                onProductSelected(prod);
            }
        });
        m_catalog.getComponent().setPreferredSize(new Dimension(0, 220));
        bottomPanel.add(m_catalog.getComponent(), BorderLayout.CENTER);
        
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        add(mainPanel, BorderLayout.CENTER);
    }

    private void setupSearchAutocomplete() {
        searchModel = new DefaultListModel<>();
        searchList = new JList<>(searchModel);
        searchList.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        searchList.setFixedCellHeight(35);
        
        searchPopup = new JPopupMenu();
        searchPopup.setLayout(new BorderLayout());
        JScrollPane sp = new JScrollPane(searchList);
        sp.setPreferredSize(new Dimension(400, 200));
        searchPopup.add(sp, BorderLayout.CENTER);
        searchPopup.setFocusable(false);
        
        searchTimer = new Timer(300, e -> performProductSearch());
        searchTimer.setRepeats(false);
        
        txtSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DOWN && searchPopup.isVisible() && searchModel.size() > 0) {
                    searchList.setSelectedIndex(0);
                    searchList.requestFocus();
                    return;
                }
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) { searchPopup.setVisible(false); return; }
                if (e.getKeyCode() == KeyEvent.VK_ENTER) { selectSearchResult(); return; }
                searchTimer.restart();
            }
        });
        
        searchList.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) selectSearchResult();
            }
        });
        searchList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) { selectSearchResult(); }
        });
    }

    private void performProductSearch() {
        String text = txtSearch.getText().trim();
        if (text.length() < 2) { searchPopup.setVisible(false); return; }
        try {
            searchResults = m_dlSales.searchProductsByName(text);
            searchModel.clear();
            for (ProductInfoExt p : searchResults) {
                searchModel.addElement(p.getReference() + " - " + p.getName());
            }
            if (searchResults.isEmpty()) {
                searchModel.addElement("(Sin resultados)");
            }
            searchPopup.show(txtSearch, 0, txtSearch.getHeight());
            SwingUtilities.invokeLater(() -> txtSearch.requestFocusInWindow());
        } catch (BasicException ex) {
            ex.printStackTrace();
        }
    }

    private void selectSearchResult() {
        int idx = searchList.getSelectedIndex();
        if (idx >= 0 && idx < searchResults.size()) {
            onProductSelected(searchResults.get(idx));
            searchPopup.setVisible(false);
            txtSearch.setText("");
        }
    }

    private JPanel createNumericKeypad() {
        JPanel pad = new JPanel(new GridLayout(4, 3, 4, 4));
        pad.setBackground(Color.WHITE);
        pad.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        String[] keys = {"7", "8", "9", "4", "5", "6", "1", "2", "3", "C", "0", "."};
        
        for (String key : keys) {
            JButton btn;
            if (key.equals("C")) {
                btn = createTouchButton(key, new Color(231, 76, 60), Color.WHITE);
            } else {
                btn = createTouchButton(key, new Color(236, 240, 241), new Color(44, 62, 80));
            }
            btn.setFont(new Font("Segoe UI", Font.BOLD, 18));
            btn.setMinimumSize(new Dimension(60, 35));
            final String val = key;
            btn.addActionListener(e -> onNumpadKey(val));
            pad.add(btn);
        }
        
        return pad;
    }

    private void onNumpadKey(String key) {
        if (key.equals("C")) {
            txtQuantity.setText("");
            quantityEnteredFirst = (selectedProduct == null);
        } else {
            String current = txtQuantity.getText();
            if (key.equals(".") && current.contains(".")) return;
            txtQuantity.setText(current + key);
            // If no product selected yet, user is entering quantity first
            if (selectedProduct == null) {
                quantityEnteredFirst = true;
            }
        }
    }

    private void onProductSelected(ProductInfoExt prod) {
        selectedProduct = prod;
        lblSelectedProduct.setText(prod.getName());
        lblSelectedProduct.setForeground(new Color(44, 62, 80));
        
        // If quantity was entered first, auto-add the line immediately
        if (quantityEnteredFirst) {
            String qtyText = txtQuantity.getText().trim();
            if (qtyText.isEmpty()) qtyText = "1";
            try {
                double qty = Double.parseDouble(qtyText);
                if (qty > 0) {
                    RecipeLine line = new RecipeLine(selectedProduct, qty, txtNotes.getText());
                    receiptLines.add(line);
                    tableModel.addRow(new Object[]{selectedProduct.getName(), qty, txtNotes.getText()});
                    clearForm();
                    return;
                }
            } catch (NumberFormatException ex) {
                // fall through to normal flow
            }
        }
        
        // Otherwise, set quantity to 1 if empty and wait for AGREGAR
        if (txtQuantity.getText().isEmpty()) {
            txtQuantity.setText("1");
        }
    }

    private void addLine() {
        if (selectedProduct == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto del catálogo.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        double qty;
        try {
            String qtyText = txtQuantity.getText().trim();
            if (qtyText.isEmpty()) qtyText = "1";
            qty = Double.parseDouble(qtyText);
            if (qty <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese una cantidad válida.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        RecipeLine line = new RecipeLine(selectedProduct, qty, txtNotes.getText());
        receiptLines.add(line);
        tableModel.addRow(new Object[]{selectedProduct.getName(), qty, txtNotes.getText()});
        clearForm();
    }

    private void removeLine() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            tableModel.removeRow(row);
            receiptLines.remove(row);
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione una línea para eliminar.", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void clearForm() {
        selectedProduct = null;
        quantityEnteredFirst = false;
        lblSelectedProduct.setText("Seleccione un producto ↓");
        lblSelectedProduct.setForeground(new Color(127, 140, 141));
        txtQuantity.setText("");
        txtNotes.setText("");
    }

    private void submitReceipt() {
        if (receiptLines.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay productos en la lista.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String receiptId = UUID.randomUUID().toString();
            String person = m_App.getAppUserView().getUser().getName();
            
            m_dlSales.insertMerchandiseReceipt(receiptId, person, "Ingreso registrado por " + person);
            
            for (RecipeLine line : receiptLines) {
                m_dlSales.insertMerchandiseReceiptLine(UUID.randomUUID().toString(), receiptId, line.product.getID(), line.units, line.notes);
            }
            
            JOptionPane.showMessageDialog(this, "Ingreso enviado para aprobación.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            receiptLines.clear();
            tableModel.setRowCount(0);
            clearForm();
        } catch (BasicException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JButton createTouchButton(String text, Color bg, Color fg) {
        JButton btn = new JButton(text);
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(150, 55));
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(bg.darker(), 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        return btn;
    }

    @Override
    public Object getBean() { return this; }
    @Override
    public JComponent getComponent() { return this; }
    @Override
    public String getTitle() { return "Ingreso de Mercancía"; }
    @Override
    public void activate() throws BasicException {
        m_catalog.loadCatalog();
    }
    @Override
    public boolean deactivate() { return true; }

    private static class RecipeLine {
        ProductInfoExt product;
        double units;
        String notes;
        public RecipeLine(ProductInfoExt product, double units, String notes) {
            this.product = product;
            this.units = units;
            this.notes = notes;
        }
    }
}
