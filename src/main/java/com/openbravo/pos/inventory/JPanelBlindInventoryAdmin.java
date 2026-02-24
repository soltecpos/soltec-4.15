package com.openbravo.pos.inventory;

import java.awt.*;
import java.awt.event.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import com.openbravo.pos.forms.AppUser;
import com.openbravo.pos.ticket.CategoryInfo;
import com.openbravo.basic.BasicException;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.forms.BeanFactoryApp;
import com.openbravo.pos.forms.BeanFactoryException;
import com.openbravo.pos.forms.DataLogicSales;
import com.openbravo.pos.forms.JPanelView;
import com.openbravo.pos.panels.JPanelTable;
import com.openbravo.pos.ticket.ProductInfoExt;
import com.openbravo.pos.inventory.InventoryHistoryRecord;
import com.openbravo.pos.forms.BeanFactoryApp;
import com.openbravo.pos.forms.BeanFactoryException;
import com.openbravo.pos.forms.DataLogicSales;
import com.openbravo.pos.forms.JPanelView;
import com.openbravo.pos.panels.JPanelTable;
import com.openbravo.pos.ticket.ProductInfoExt;

public class JPanelBlindInventoryAdmin extends JPanel implements JPanelView, BeanFactoryApp {

    private AppView m_App;
    private DataLogicSales m_dlSales;
    
    private JTable tasksTable;
    private TasksTableModel tasksModel;
    private List<InventoryTaskInfo> taskList;

    public JPanelBlindInventoryAdmin() {
    }

    @Override
    public void init(AppView app) throws BeanFactoryException {
        m_App = app;
        m_dlSales = (DataLogicSales) m_App.getBean("com.openbravo.pos.forms.DataLogicSales");
        initComponents();
    }

    @Override
    public Object getBean() {
        return this;
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        
        // --- TOP PANEL: ACTIONS ---
        JPanel pnlTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnCreateTask = new JButton("Crear Tarea de Conteo");
        btnCreateTask.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createTaskDialog();
            }
        });
        
        JButton btnRefresh = new JButton("Actualizar");
        btnRefresh.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                refreshTasks();
            }
        });
        
        pnlTop.add(btnCreateTask);
        pnlTop.add(btnRefresh);
        add(pnlTop, BorderLayout.NORTH);
        
        // --- CENTER PANEL: TABLE ---
        tasksModel = new TasksTableModel();
        tasksTable = new JTable(tasksModel);
        tasksTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tasksTable.setRowHeight(30);
        
        tasksTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                if (me.getClickCount() == 2) {
                    int row = tasksTable.getSelectedRow();
                    if (row >= 0) {
                        InventoryTaskInfo selectedTask = taskList.get(row);
                        if ("COMPLETED".equals(selectedTask.getStatus())) {
                            showHistoricalReport(selectedTask);
                        } else {
                            reviewSelectedTask();
                        }
                    }
                }
            }
        });
        
        add(new JScrollPane(tasksTable), BorderLayout.CENTER);
        
        // --- BOTTOM PANEL: APPROVAL ---
        JPanel pnlBottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnReview = new JButton("Revisar y Aprobar Conteo Seleccionado");
        btnReview.setFont(new Font("Arial", Font.BOLD, 14));
        btnReview.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                reviewSelectedTask();
            }
        });
        pnlBottom.add(btnReview);
        add(pnlBottom, BorderLayout.SOUTH);
    }
    
    @Override
    public void activate() throws BasicException {
        refreshTasks();
    }
    
    @Override
    public boolean deactivate() {
        return true;
    }
    
    @Override
    public JComponent getComponent() {
        return this;
    }
    
    @Override
    public String getTitle() {
        return "Admin. Inventario Ciego";
    }

    private void refreshTasks() {
        try {
            // Need a new query to fetch all tasks for admin
            // Re-using the pending tasks query for now but without constraints for simplicity, or we add a new one
            // We should add a new method in DataLogicSales: getAllInventoryTasks()
            // For now, let's use a Direct SQL call if possible, or we will add the method.
            taskList = new com.openbravo.data.loader.PreparedSentence<com.openbravo.data.loader.DataParams, InventoryTaskInfo>(
                m_App.getSession(),
                "SELECT T.ID, T.STATUS, T.CREATED_AT, T.AUTHOR_ID, T.LOCATION_ID, P.NAME AS ASSIGNEE_ROLE FROM inventory_tasks T LEFT JOIN people P ON T.ASSIGNEE_ROLE = P.ID ORDER BY T.CREATED_AT DESC",
                null,
                new com.openbravo.data.loader.SerializerReadClass<InventoryTaskInfo>(InventoryTaskInfo.class)
            ).list();
            
            tasksModel.fireTableDataChanged();
        } catch (BasicException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error recargando tareas: " + ex.getMessage());
        }
    }
    
    // Dialog to create a new task
    private void createTaskDialog() {
        JDialog dlg = new JDialog(SwingUtilities.getWindowAncestor(this), "Nueva Tarea de Inventario Ciego");
        dlg.setModal(true);
        dlg.setLayout(new BorderLayout(10, 10));
        dlg.setSize(550, 480);
        ((JPanel)dlg.getContentPane()).setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        dlg.setLocationRelativeTo(this);
        
        // Top Panel for User and Location
        JPanel pnlTop = new JPanel(new GridLayout(2, 2, 10, 10));
        pnlTop.add(new JLabel("Usuario Asignado a Contar:"));
        
        // Load users
        JComboBox<AppUser> cmbUsers = new JComboBox<>();
        try {
            com.openbravo.pos.forms.DataLogicSystem dlsys = (com.openbravo.pos.forms.DataLogicSystem) m_App.getBean("com.openbravo.pos.forms.DataLogicSystem");
            List<AppUser> users = dlsys.listPeopleVisible();
            for(AppUser u : users) {
                cmbUsers.addItem(u);
            }
        } catch (Exception ex) {}
        
        cmbUsers.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof AppUser) {
                    setText(((AppUser)value).getName());
                }
                return this;
            }
        });
        pnlTop.add(cmbUsers);
        
        pnlTop.add(new JLabel("ID Bodega o Locación:"));
        JTextField txtLoc = new JTextField("0"); // General Location
        pnlTop.add(txtLoc);
        
        dlg.add(pnlTop, BorderLayout.NORTH);
        
        // Center panel for categories
        JPanel pnlCenter = new JPanel(new BorderLayout(5, 5));
        pnlCenter.add(new JLabel("Seleccione Filtros de Categoría a Contar:"), BorderLayout.NORTH);
        
        List<CategoryInfo> categories = new java.util.ArrayList<>();
        try {
            categories = m_dlSales.getCategoriesList().list();
        } catch (BasicException ex) {}
        
        final List<CategoryInfo> fCategories = categories;
        final List<Boolean> selections = new java.util.ArrayList<>();
        for (int i=0; i<fCategories.size(); i++) selections.add(false); // Default unselected
        
        JTable catTable = new JTable(new AbstractTableModel() {
            public int getRowCount() { return fCategories.size(); }
            public int getColumnCount() { return 2; }
            public String getColumnName(int c) { return c == 0 ? "Incluir" : "Categoría de Producto"; }
            public Class<?> getColumnClass(int c) { return c == 0 ? Boolean.class : String.class; }
            public boolean isCellEditable(int r, int c) { return c == 0; }
            public Object getValueAt(int r, int c) {
                if (c == 0) return selections.get(r);
                return fCategories.get(r).getName();
            }
            public void setValueAt(Object val, int r, int c) {
                if (c == 0) {
                    selections.set(r, (Boolean)val);
                    fireTableCellUpdated(r, c);
                }
            }
        });
        
        catTable.getColumnModel().getColumn(0).setMaxWidth(60);
        catTable.setRowHeight(24);
        
        // Select All button
        JButton btnSelectAll = new JButton("Seleccionar Todo / Ninguno");
        btnSelectAll.addActionListener(e -> {
            boolean allTrue = !selections.contains(false);
            for (int i=0; i<selections.size(); i++) selections.set(i, !allTrue);
            ((AbstractTableModel)catTable.getModel()).fireTableDataChanged();
        });
        
        JPanel pnlCatActions = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlCatActions.add(btnSelectAll);
        pnlCenter.add(pnlCatActions, BorderLayout.SOUTH);
        pnlCenter.add(new JScrollPane(catTable), BorderLayout.CENTER);
        
        dlg.add(pnlCenter, BorderLayout.CENTER);
        
        // Bottom Panel
        JPanel pnlBottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnSave = new JButton("Generar Tarea de Conteo");
        btnSave.setBackground(new Color(0, 168, 223));
        btnSave.setForeground(Color.WHITE);
        btnSave.addActionListener(e -> {
            // Check if at least one selected
            if (!selections.contains(true)) {
                JOptionPane.showMessageDialog(dlg, "Debe seleccionar al menos una categoría de la tabla.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            try {
                // Get selected category IDs
                List<String> selectedCatIds = new java.util.ArrayList<>();
                for (int i=0; i<fCategories.size(); i++) {
                    if (selections.get(i)) selectedCatIds.add(fCategories.get(i).getID());
                }
                
                // Fetch all products and filter
                List<ProductInfoExt> products = m_dlSales.getProductListNormal().list();
                List<ProductInfoExt> productsToCount = new java.util.ArrayList<>();
                for (ProductInfoExt p : products) {
                    if (selectedCatIds.contains(p.getCategoryID())) {
                        productsToCount.add(p);
                    }
                }
                
                if (productsToCount.isEmpty()) {
                    JOptionPane.showMessageDialog(dlg, "No hay productos disponibles en las categorías seleccionadas.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                // Create Task
                String taskId = UUID.randomUUID().toString();
                String targetUserId = ((AppUser)cmbUsers.getSelectedItem()).getId();
                InventoryTaskInfo newTask = new InventoryTaskInfo(taskId, "PENDING", new Date(), m_App.getAppUserView().getUser().getId(), txtLoc.getText(), targetUserId);
                m_dlSales.insertInventoryTask(newTask);
                
                for (ProductInfoExt p : productsToCount) {
                    Double sysQty = m_dlSales.findProductStock(txtLoc.getText(), p.getID(), null);
                    if (sysQty == null) sysQty = 0.0;
                    InventoryTaskLineInfo line = new InventoryTaskLineInfo(taskId, p.getID(), p.getName(), sysQty, null, null);
                    m_dlSales.insertInventoryTaskLine(line);
                }
                
                JOptionPane.showMessageDialog(dlg, "¡Tarea generada exitosamente!\nSe designaron " + productsToCount.size() + " productos.\nEl sistema bloqueará al usuario " + ((AppUser)cmbUsers.getSelectedItem()).getName() + " hasta que acabe.");
                dlg.dispose();
                refreshTasks();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        
        pnlBottom.add(btnSave);
        dlg.add(pnlBottom, BorderLayout.SOUTH);
        
        dlg.setVisible(true);
    }
    
    // Dialog to review and approve
    private void reviewSelectedTask() {
        int sel = tasksTable.getSelectedRow();
        if (sel < 0) return;
        
        final InventoryTaskInfo task = taskList.get(sel);
        if (!"WAITING_APPROVAL".equals(task.getStatus())) {
            JOptionPane.showMessageDialog(this, "Esta tarea no está pendiente de aprobación.\nEstado actual: " + task.getStatus());
            return;
        }
        
        try {
            final List<InventoryTaskLineInfo> lines = m_dlSales.getInventoryTaskLines(task.getId());
            
            JDialog dlg = new JDialog(SwingUtilities.getWindowAncestor(this), "Aprobar Diferencias");
            dlg.setModal(true);
            dlg.setSize(800, 600);
            dlg.setLocationRelativeTo(this);
            dlg.setLayout(new BorderLayout());
            
            // Difference Table
            JTable diffTable = new JTable(new AbstractTableModel() {
                String[] cols = {"Producto", "Cant. Sistema", "Cant. Contada", "Diferencia"};
                public int getRowCount() { return lines.size(); }
                public int getColumnCount() { return cols.length; }
                public String getColumnName(int c) { return cols[c]; }
                public Object getValueAt(int r, int c) {
                    InventoryTaskLineInfo l = lines.get(r);
                    if (c == 0) return l.getProductName();
                    if (c == 1) return l.getSystemQty();
                    if (c == 2) return l.getCountedQty();
                    if (c == 3) return l.getDifference();
                    return null;
                }
            });
            dlg.add(new JScrollPane(diffTable), BorderLayout.CENTER);
            
            JButton btnApprove = new JButton("Aprobar y Ajustar Kardex Oficialmente");
            btnApprove.setBackground(Color.RED);
            btnApprove.setForeground(Color.WHITE);
            btnApprove.addActionListener(e -> {
                try {
                    // Update task status
                    m_dlSales.updateInventoryTaskStatus(task.getId(), "COMPLETED");
                    
                    // Actually alter the stock!
                    Date now = new Date();
                    for(InventoryTaskLineInfo l : lines) {
                        double diff = l.getDifference() == null ? 0.0 : l.getDifference();
                        if (diff != 0) {
                            int reasonId = diff > 0 ? 10 : -11; // 10 = Sobrante Arqueo Físico, -11 = Faltante Físico
                            Object[] params = new Object[] {
                                UUID.randomUUID().toString(),
                                now,
                                reasonId, 
                                task.getLocationId(),
                                l.getProductId(),
                                null, // attributes
                                diff, // Positive adds to stockcurrent, Negative subtracts 
                                0.0, // price
                                m_App.getAppUserView().getUser().getName(),
                                null, null // Supplier info
                            };
                            
                            m_dlSales.getStockDiaryInsert1().exec(params);
                        }
                    }
                    JOptionPane.showMessageDialog(dlg, "Inventario Ajustado!!");
                    dlg.dispose();
                    refreshTasks();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
            
            dlg.add(btnApprove, BorderLayout.SOUTH);
            dlg.setVisible(true);
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    // Dialog for Historical Report
    private void showHistoricalReport(InventoryTaskInfo task) {
        try {
            final List<InventoryTaskLineInfo> lines = m_dlSales.getInventoryTaskLines(task.getId());
            
            JDialog dlg = new JDialog(SwingUtilities.getWindowAncestor(this), "Arqueo Histórico de Conteo Físico");
            dlg.setModal(true);
            dlg.setSize(950, 700);
            dlg.setLocationRelativeTo(this);
            dlg.setLayout(new BorderLayout(10, 10));
            ((JPanel)dlg.getContentPane()).setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
            
            // Header
            JPanel pnlHeader = new JPanel(new GridLayout(2, 1));
            pnlHeader.add(new JLabel("<html><h3>Reporte de Arqueo - Tarea ID: " + task.getId() + "</h3></html>"));
            pnlHeader.add(new JLabel("Seleccione un producto para ver la trazabilidad de sus movimientos:"));
            dlg.add(pnlHeader, BorderLayout.NORTH);
            
            // Top Table - Products List
            JTable prodTable = new JTable(new AbstractTableModel() {
                String[] cols = {"Producto", "Cant. Sistema", "Cant. Física (Real)", "Diferencia Faltante/Sobrante"};
                public int getRowCount() { return lines.size(); }
                public int getColumnCount() { return cols.length; }
                public String getColumnName(int c) { return cols[c]; }
                public Object getValueAt(int r, int c) {
                    InventoryTaskLineInfo l = lines.get(r);
                    if (c == 0) return l.getProductName();
                    if (c == 1) return l.getSystemQty();
                    if (c == 2) return l.getCountedQty();
                    if (c == 3) return l.getDifference();
                    return null;
                }
            });
            prodTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            prodTable.setRowHeight(25);
            
            // Bottom Table - History (Kardex)
            final JTable histTable = new JTable();
            histTable.setRowHeight(22);
            JScrollPane scrollHist = new JScrollPane(histTable);
            scrollHist.setBorder(BorderFactory.createTitledBorder("Historial de Transacciones (Kardex)"));
            
            // Selection Listener on Top Table
            prodTable.getSelectionModel().addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting() && prodTable.getSelectedRow() >= 0) {
                    InventoryTaskLineInfo selectedLine = lines.get(prodTable.getSelectedRow());
                    try {
                        final List<InventoryHistoryRecord> history = m_dlSales.getInventoryHistory(selectedLine.getProductId());
                        // Calculate running balances (from bottom up)
                        Double dbStock = m_dlSales.findProductStock(task.getLocationId(), selectedLine.getProductId(), null);
                        double currentStock = dbStock != null ? dbStock : 0.0;
                        
                        if (!history.isEmpty()) {
                            // Reverse calculate
                            for (InventoryHistoryRecord rec : history) {
                                // For the current record, the balance AFTER it happened is currentStock
                                rec.setNewBalance(currentStock);
                                // The balance BEFORE it happened is currentStock minus whatever was added/removed
                                currentStock = currentStock - rec.getQuantity();
                            }
                        }
                        
                        histTable.setModel(new AbstractTableModel() {
                            String[] cols = {"Fecha de Transacción", "Tipo de Movimiento", "Usuario", "Unidades Afectadas", "Saldo Restante"};
                            public int getRowCount() { return history.size(); }
                            public int getColumnCount() { return cols.length; }
                            public String getColumnName(int c) { return cols[c]; }
                            public Object getValueAt(int r, int c) {
                                InventoryHistoryRecord rec = history.get(r);
                                if (c == 0) return rec.getTransactionDate();
                                if (c == 1) return rec.getReasonString();
                                if (c == 2) return rec.getUser();
                                if (c == 3) return rec.getQuantity();
                                if (c == 4) return rec.getNewBalance();
                                return null;
                            }
                        });
                        histTable.getColumnModel().getColumn(0).setPreferredWidth(170);
                        histTable.getColumnModel().getColumn(1).setPreferredWidth(210);
                        histTable.getColumnModel().getColumn(3).setPreferredWidth(100);
                        histTable.getColumnModel().getColumn(4).setPreferredWidth(100);
                        
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
            
            // Split Pane
            JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JScrollPane(prodTable), scrollHist);
            splitPane.setDividerLocation(200);
            dlg.add(splitPane, BorderLayout.CENTER);
            
            JButton btnClose = new JButton("Cerrar Historial");
            btnClose.addActionListener(e -> dlg.dispose());
            JPanel pnlBot = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            pnlBot.add(btnClose);
            dlg.add(pnlBot, BorderLayout.SOUTH);
            
            dlg.setVisible(true);
            
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "No se pudo cargar el historial.");
        }
    }

    class TasksTableModel extends AbstractTableModel {
        private String[] columns = {"Fecha Creación", "Rol Asignado", "Estado"};

        public int getRowCount() { return taskList == null ? 0 : taskList.size(); }
        public int getColumnCount() { return columns.length; }
        public String getColumnName(int col) { return columns[col]; }

        public Object getValueAt(int row, int col) {
            InventoryTaskInfo t = taskList.get(row);
            switch(col) {
                case 0: return t.getCreatedAt();
                case 1: return t.getAssigneeRole();
                case 2: return t.getStatus();
                default: return null;
            }
        }
    }
}
