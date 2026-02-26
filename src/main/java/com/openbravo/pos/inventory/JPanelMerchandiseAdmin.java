package com.openbravo.pos.inventory;

import com.openbravo.basic.BasicException;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.forms.BeanFactoryApp;
import com.openbravo.pos.forms.BeanFactoryException;
import com.openbravo.pos.forms.DataLogicSales;
import com.openbravo.pos.forms.JPanelView;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class JPanelMerchandiseAdmin extends JPanel implements JPanelView, BeanFactoryApp {

    private AppView m_App;
    private DataLogicSales m_dlSales;
    
    private JTable tableReceipts;
    private DefaultTableModel modelReceipts;
    private JTable tableLines;
    private DefaultTableModel modelLines;
    
    private String selectedReceiptId = null;

    @Override
    public void init(AppView app) throws BeanFactoryException {
        this.m_App = app;
        this.m_dlSales = (DataLogicSales) m_App.getBean("com.openbravo.pos.forms.DataLogicSales");
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(230, 126, 34)); // Orange for Admin
        header.setPreferredSize(new Dimension(100, 60));
        JLabel lblTitle = new JLabel("  APROBACIÓN DE INGRESOS");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setForeground(Color.WHITE);
        header.add(lblTitle, BorderLayout.WEST);
        add(header, BorderLayout.NORTH);

        // Split Pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setDividerLocation(250);

        // Top: List of Receipts
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createTitledBorder("Ingresos Pendientes"));
        modelReceipts = new DefaultTableModel(new Object[]{"ID", "Fecha", "Usuario", "Notas"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tableReceipts = new JTable(modelReceipts);
        tableReceipts.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableReceipts.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) loadReceiptDetails();
        });
        topPanel.add(new JScrollPane(tableReceipts), BorderLayout.CENTER);

        // Bottom: Details of Selected Receipt
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(BorderFactory.createTitledBorder("Detalle del Ingreso"));
        modelLines = new DefaultTableModel(new Object[]{"Producto", "Cantidad", "Notas"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tableLines = new JTable(modelLines);
        bottomPanel.add(new JScrollPane(tableLines), BorderLayout.CENTER);

        // Administrative Actions
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actions.setBackground(Color.WHITE);
        
        JButton btnReject = createPremiumButton("RECHAZAR", new Color(231, 76, 60), Color.WHITE);
        btnReject.addActionListener(e -> rejectReceipt());
        actions.add(btnReject);

        JButton btnApprove = createPremiumButton("APROBAR Y ACTUALIZAR STOCK", new Color(46, 204, 113), Color.WHITE);
        btnApprove.addActionListener(e -> approveReceipt());
        actions.add(btnApprove);
        
        bottomPanel.add(actions, BorderLayout.SOUTH);

        splitPane.setTopComponent(topPanel);
        splitPane.setBottomComponent(bottomPanel);
        add(splitPane, BorderLayout.CENTER);
    }

    private void loadReceipts() {
        try {
            modelReceipts.setRowCount(0);
            selectedReceiptId = null;
            modelLines.setRowCount(0);
            
            List<Object[]> receipts = m_dlSales.getPendingMerchandiseReceipts();
            for (Object[] r : receipts) {
                modelReceipts.addRow(r);
            }
        } catch (BasicException e) {
            e.printStackTrace();
        }
    }

    private void loadReceiptDetails() {
        int row = tableReceipts.getSelectedRow();
        if (row >= 0) {
            selectedReceiptId = (String) modelReceipts.getValueAt(row, 0);
            try {
                modelLines.setRowCount(0);
                List<Object[]> lines = m_dlSales.getMerchandiseReceiptLines(selectedReceiptId);
                for (Object[] l : lines) {
                    // Lines: ID, RECEIPT_ID, PRODUCT_ID, UNITS, NOTES, PRODUCT_NAME
                    modelLines.addRow(new Object[]{l[5], l[3], l[4]});
                }
            } catch (BasicException e) {
                e.printStackTrace();
            }
        }
    }

    private void approveReceipt() {
        if (selectedReceiptId == null) return;
        
        int res = JOptionPane.showConfirmDialog(this, "¿Está seguro de que desea APROBAR este ingreso? El inventario se actualizará inmediatamente.", "Confirmar Aprobación", JOptionPane.YES_NO_OPTION);
        if (res == JOptionPane.YES_OPTION) {
            try {
                // We need a location. For now default to the first location if we can't get current.
                // Or better, let the user choose or use current.
                String location = m_App.getInventoryLocation();
                String user = m_App.getAppUserView().getUser().getName();
                
                m_dlSales.approveMerchandiseReceipt(selectedReceiptId, location, user);
                JOptionPane.showMessageDialog(this, "Ingreso aprobado y stock actualizado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                loadReceipts();
            } catch (BasicException e) {
                JOptionPane.showMessageDialog(this, "Error al aprobar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void rejectReceipt() {
        if (selectedReceiptId == null) return;
        
        int res = JOptionPane.showConfirmDialog(this, "¿Está seguro de que desea RECHAZAR este ingreso?", "Confirmar Rechazo", JOptionPane.YES_NO_OPTION);
        if (res == JOptionPane.YES_OPTION) {
            try {
                m_dlSales.rejectMerchandiseReceipt(selectedReceiptId);
                JOptionPane.showMessageDialog(this, "Ingreso rechazado.", "Información", JOptionPane.INFORMATION_MESSAGE);
                loadReceipts();
            } catch (BasicException e) {
                JOptionPane.showMessageDialog(this, "Error al rechazar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private JButton createPremiumButton(String text, Color bg, Color fg) {
        JButton btn = new JButton(text);
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(220, 40));
        return btn;
    }

    @Override
    public Object getBean() { return this; }
    @Override
    public JComponent getComponent() { return this; }
    @Override
    public String getTitle() { return "Aprobación de Ingresos"; }
    
    @Override
    public void activate() throws BasicException {
        loadReceipts();
    }
    
    @Override
    public boolean deactivate() { return true; }
}
