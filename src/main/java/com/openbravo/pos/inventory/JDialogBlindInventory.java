package com.openbravo.pos.inventory;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import com.openbravo.pos.forms.DataLogicSales;
import com.openbravo.pos.forms.AppUser;

public class JDialogBlindInventory extends JDialog {
    private List<InventoryTaskLineInfo> lines;
    private DataLogicSales dlSales;
    private InventoryTaskInfo task;
    private boolean completed = false;
    private JTable table;

    private JDialogBlindInventory(Frame parent, DataLogicSales dlSales, InventoryTaskInfo task) {
        super(parent, "Conteo Físico Obligatorio", true);
        this.dlSales = dlSales;
        this.task = task;
        initComponents();
    }

    public static boolean showMessage(Component parent, DataLogicSales dlSales, InventoryTaskInfo task, AppUser user) {
        Frame frame = JOptionPane.getFrameForComponent(parent);
        JDialogBlindInventory dialog = new JDialogBlindInventory(frame, dlSales, task);
        dialog.setVisible(true);
        return dialog.completed;
    }

    private void initComponents() {
        try {
            lines = dlSales.getInventoryTaskLines(task.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setLayout(new BorderLayout());
        setSize(650, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

        JLabel lblTitle = new JLabel("<html><h2 style='color:red;'>¡TAREA DE INVENTARIO FÍSICO!</h2><p><b>Estimado usuario:</b> El administrador ha programado un conteo físico en esta bodega.<br>Por favor, cuente los siguientes productos e ingrese la <b>cantidad física real</b> encontrada.<br>No podrá utilizar el sistema de ventas hasta completar y enviar este registro.</p></html>");
        lblTitle.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(lblTitle, BorderLayout.NORTH);

        table = new JTable(new BlindTableModel());
        table.setRowHeight(35);
        table.setFont(new Font("Arial", Font.BOLD, 14));
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel pnlBottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnSubmit = new JButton("Enviar Conteo Definitivo");
        btnSubmit.setFont(new Font("Arial", Font.BOLD, 14));
        btnSubmit.setBackground(new Color(50, 150, 50));
        btnSubmit.setForeground(Color.WHITE);
        btnSubmit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                submitCount();
            }
        });
        
        JButton btnLogout = new JButton("Cancelar y Salir");
        btnLogout.setFont(new Font("Arial", Font.PLAIN, 14));
        btnLogout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                completed = false;
                dispose();
            }
        });
        
        pnlBottom.add(btnLogout);
        pnlBottom.add(btnSubmit);
        add(pnlBottom, BorderLayout.SOUTH);
    }

    private void submitCount() {
        if (table.isEditing()) {
            table.getCellEditor().stopCellEditing();
        }

        for (InventoryTaskLineInfo line : lines) {
            if (line.getCountedQty() == null) {
                JOptionPane.showMessageDialog(this, "Debe ingresar el conteo para el producto:\n- " + line.getProductName(), "Falta Información", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de enviar este conteo al administrador?\nNo podrá modificarlo después de enviado.", "Confirmar Envío", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        try {
            for (InventoryTaskLineInfo line : lines) {
                double sysQty = line.getSystemQty() == null ? 0.0 : line.getSystemQty();
                double diff = line.getCountedQty() - sysQty;
                dlSales.updateInventoryTaskLineCounted(task.getId(), line.getProductId(), line.getCountedQty(), diff);
            }
            dlSales.updateInventoryTaskStatus(task.getId(), "WAITING_APPROVAL");
            JOptionPane.showMessageDialog(this, "¡Conteo enviado exitosamente!\nHa quedado pendiente de aprobación por el administrador.");
            completed = true;
            dispose();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error guardando el conteo: " + ex.getMessage(), "Error Interno", JOptionPane.ERROR_MESSAGE);
        }
    }

    class BlindTableModel extends AbstractTableModel {
        private String[] columns = {"Producto Físico a Contar", "Cantidad Física (Digite Aquí)"};

        public int getRowCount() { return lines.size(); }
        public int getColumnCount() { return columns.length; }
        public String getColumnName(int col) { return columns[col]; }
        public boolean isCellEditable(int row, int col) { return col == 1; }

        public Object getValueAt(int row, int col) {
            InventoryTaskLineInfo line = lines.get(row);
            return col == 0 ? line.getProductName() : line.getCountedQty();
        }

        public void setValueAt(Object val, int row, int col) {
            if (col == 1) {
                try {
                    Double qty = Double.parseDouble(val.toString());
                    lines.get(row).setCountedQty(qty);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(JDialogBlindInventory.this, "Por favor ingrese un número válido.");
                }
            }
        }
    }
}
