/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.panels;

import com.openbravo.basic.BasicException;
import com.openbravo.data.user.BrowsableEditableData;
import com.openbravo.data.user.EditorRecord;
import com.openbravo.data.user.ListProvider;
import com.openbravo.data.user.SaveProvider;
import com.openbravo.format.Formats;
import com.openbravo.pos.forms.DataLogicSales;
import com.openbravo.pos.forms.DataLogicSystem;
import com.openbravo.pos.panels.GlobalExpensesEditor;
import com.openbravo.pos.panels.JPanelTable;
import com.openbravo.pos.printer.TicketParser;
import com.openbravo.pos.scripting.ScriptEngine;
import com.openbravo.pos.scripting.ScriptFactory;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class JPanelGlobalExpenses
extends JPanelTable {
    private GlobalExpensesEditor jeditor;
    private DataLogicSales m_dlSales = null;
    private DataLogicSystem m_dlSystem = null;
    private CardLayout m_cardLayout;
    private JPanel m_mainContainer;
    private TicketParser m_TTP;
    private static final String CARD_LANDING = "LANDING";
    private static final String CARD_EDITOR = "EDITOR";
    private static final String CARD_HISTORY = "HISTORY";
    private ActionListener btnHistoryListener;

    @Override
    protected void init() {
        try {
            this.m_dlSales = (DataLogicSales)this.app.getBean("com.openbravo.pos.forms.DataLogicSales");
            this.m_dlSystem = (DataLogicSystem)this.app.getBean("com.openbravo.pos.forms.DataLogicSystem");
            this.m_TTP = new TicketParser(this.app.getDeviceTicket(), this.m_dlSystem);
            this.jeditor = new GlobalExpensesEditor(this.app, this.dirty);
            this.removeAll();
            this.setLayout(new BorderLayout());
            this.m_cardLayout = new CardLayout();
            this.m_mainContainer = new JPanel(this.m_cardLayout);
            this.m_mainContainer.setBackground(Color.WHITE);
            this.m_mainContainer.add((Component)this.createLandingPanel(), CARD_LANDING);
            JPanel editorPanel = new JPanel(new BorderLayout());
            editorPanel.setBackground(Color.WHITE);
            JPanel editorHeader = new JPanel(new GridBagLayout());
            editorHeader.setBackground(new Color(245, 245, 245));
            editorHeader.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 220, 220)));
            editorHeader.setPreferredSize(new Dimension(100, 80));
            JButton btnSave = this.createPremiumButton("GUARDAR REGISTRO", new Color(39, 174, 96), Color.WHITE, 250);
            btnSave.addActionListener(e -> {
                block11: {
                    try {
                        if (this.dirty.isDirty()) {
                            Object[] record = null;
                            try {
                                record = (Object[])this.jeditor.createValue();
                            }
                            catch (NullPointerException npe) {
                                JOptionPane.showMessageDialog(this, "Error creando registro (NPE). Revise campos.", "Error Debug", 0);
                                npe.printStackTrace();
                                return;
                            }
                            catch (Exception ex) {
                                JOptionPane.showMessageDialog(this, "Error creando registro: " + ex.getMessage(), "Error Debug", 0);
                                ex.printStackTrace();
                                return;
                            }
                            if (this.bd == null) {
                                JOptionPane.showMessageDialog(this, "Error: Controlador de datos (bd) es nulo.", "Error Debug", 0);
                                return;
                            }
                            try {
                                this.bd.saveData();
                            }
                            catch (NullPointerException npe) {
                                JOptionPane.showMessageDialog(this, "Error interno al guardar (NPE).", "Error Debug", 0);
                                npe.printStackTrace();
                                return;
                            }
                            Object[] options = new Object[]{"Imprimir Recibo", "Solo Guardar"};
                            int n = JOptionPane.showOptionDialog(this, "Registro guardado correctamente.\n\u00bfDesea imprimir un comprobante?", "\u00c9xito", 0, 3, null, options, options[0]);
                            if (n == 0) {
                                this.printTicket(record);
                            }
                            this.showCard(CARD_LANDING);
                            break block11;
                        }
                        JOptionPane.showMessageDialog(this, "No hay cambios para guardar.", "Aviso", 2);
                    }
                    catch (BasicException ex) {
                        JOptionPane.showMessageDialog(this, "Error al guardar (BasicException): " + ex.getMessage(), "Error", 0);
                        ex.printStackTrace();
                    }
                    catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Error inesperado General: " + ex.toString(), "Error Cr\u00edtico", 0);
                        ex.printStackTrace();
                    }
                }
            });
            JButton btnCancel = this.createPremiumButton("VOLVER / CANCELAR", new Color(149, 165, 166), Color.WHITE, 200);
            btnCancel.addActionListener(e -> this.showCard(CARD_LANDING));
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(0, 10, 0, 10);
            editorHeader.add((Component)btnCancel, gbc);
            editorHeader.add((Component)btnSave, gbc);
            editorPanel.add((Component)editorHeader, "North");
            JScrollPane scrollEditor = new JScrollPane(this.jeditor);
            scrollEditor.setBorder(null);
            editorPanel.add((Component)scrollEditor, "Center");
            this.m_mainContainer.add((Component)editorPanel, CARD_EDITOR);
            this.m_mainContainer.add((Component)this.createHistoryPanel(), CARD_HISTORY);
            this.add((Component)this.m_mainContainer, "Center");
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private void printTicket(Object[] record) {
        try {
            ScriptEngine script = ScriptFactory.getScriptEngine("velocity");
            script.put("date", Formats.TIMESTAMP.formatValue(record[2]));
            script.put("payment", record[4]);
            script.put("total", Formats.CURRENCY.formatValue(Math.abs((Double)record[5])));
            script.put("notes", record[6]);
            script.put("user", this.app.getAppUserView().getUser().getName());
            String catName = "General";
            if (record[7] != null) {
                List<Object[]> cats = this.m_dlSales.getPaymentCategoriesGlobal(true).list();
                for (Object[] c : cats) {
                    if (!record[7].equals(c[0])) continue;
                    catName = (String)c[1];
                    break;
                }
            }
            script.put("category", catName);
            String template = "<ticket>\n    <image>Printer.Ticket.Logo</image>\n    <line></line>\n    <line size=\"1\">GASTO GERENCIAL</line>\n    <line></line>\n    <line><text align=\"left\" length=\"15\">Fecha:</text><text>${date}</text></line>\n    <line><text align=\"left\" length=\"15\">Usuario:</text><text>${user}</text></line>\n    <line></line>\n    <line><text align=\"left\" length=\"15\">Categor\u00eda:</text><text>${category}</text></line>\n    <line><text align=\"left\" length=\"15\">M\u00e9todo:</text><text>${payment}</text></line>\n    <line><text align=\"left\" length=\"15\">Notas:</text><text>${notes}</text></line>\n    <line></line>\n    <line size=\"1\"><text align=\"left\" length=\"15\" bold=\"true\">TOTAL:</text><text bold=\"true\">${total}</text></line>\n    <line></line>\n    <line><text align=\"center\">Firma Autorizada</text></line>\n</ticket>";
            this.m_TTP.printTicket(script.eval(template).toString());
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error imprimiendo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private JPanel createLandingPanel() {
        JPanel landing = new JPanel(new GridBagLayout());
        landing.setBackground(Color.WHITE);
        JLabel lblHeader = new JLabel("Gastos Gerenciales SOLTEC");
        lblHeader.setFont(new Font("Segoe UI", 1, 28));
        lblHeader.setForeground(new Color(44, 62, 80));
        JButton btnNew = this.createPremiumButton("NUEVO REGISTRO", new Color(46, 204, 113), Color.WHITE, 300);
        btnNew.setFont(new Font("Segoe UI", 1, 18));
        btnNew.addActionListener(e -> {
            try {
                this.bd.actionInsert();
                this.showCard(CARD_EDITOR);
            }
            catch (BasicException ex) {
                ex.printStackTrace();
            }
        });
        JButton btnHistory = this.createPremiumButton("VER HISTORIAL / EDITAR", new Color(52, 152, 219), Color.WHITE, 300);
        btnHistory.setFont(new Font("Segoe UI", 1, 18));
        btnHistory.addActionListener(e -> {
            if (this.btnHistoryListener != null) {
                this.btnHistoryListener.actionPerformed(e);
            }
            this.showCard(CARD_HISTORY);
        });
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 50, 0);
        landing.add((Component)lblHeader, gbc);
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 0, 10, 0);
        landing.add((Component)btnNew, gbc);
        gbc.gridy = 2;
        landing.add((Component)btnHistory, gbc);
        return landing;
    }

    private JPanel createHistoryPanel() {
        JPanel history = new JPanel(new BorderLayout());
        history.setBackground(Color.WHITE);
        history.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JLabel lblTitle = new JLabel("Historial de Gastos Gerenciales");
        lblTitle.setFont(new Font("Segoe UI", 1, 22));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        history.add((Component)lblTitle, "North");
        DefaultTableModel tableModel = new DefaultTableModel(new Object[]{"Fecha", "Categor\u00eda", "Medio Pago", "Total", "Notas", "Usuario"}, 0){

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(tableModel);
        table.setRowHeight(35);
        table.getTableHeader().setFont(new Font("Segoe UI", 1, 14));
        table.setFont(new Font("Segoe UI", 0, 13));
        JScrollPane scroll = new JScrollPane(table);
        history.add((Component)scroll, "Center");
        JButton btnBack = this.createPremiumButton("VOLVER AL INICIO", new Color(44, 62, 80), Color.WHITE, 200);
        btnBack.addActionListener(e -> this.showCard(CARD_LANDING));
        JPanel footer = new JPanel(new FlowLayout(0));
        footer.setBackground(Color.WHITE);
        footer.add(btnBack);
        history.add((Component)footer, "South");
        this.btnHistoryListener = e -> {
            try {
                List<Object[]> data = this.m_dlSales.getGlobalPaymentsList();
                tableModel.setRowCount(0);
                for (Object[] row : data) {
                    tableModel.addRow(new Object[]{Formats.TIMESTAMP.formatValue(row[2]), row[7] == null ? "Sin Categor\u00eda" : row[7], row[4], Formats.CURRENCY.formatValue(row[5]), row[6], row[8]});
                }
            }
            catch (BasicException ex) {
                ex.printStackTrace();
            }
        };
        return history;
    }

    private JButton createPremiumButton(String text, Color bg, Color fg, int width) {
        JButton btn = new JButton(text);
        btn.setPreferredSize(new Dimension(width, 60));
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFont(new Font("Segoe UI", 1, 14));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        return btn;
    }

    private void showCard(String cardId) {
        this.m_cardLayout.show(this.m_mainContainer, cardId);
    }

    @Override
    protected void startNavigation() {
        if (this.bd == null) {
            this.bd = new BrowsableEditableData<Object[]>(this.getListProvider(), this.getSaveProvider(), this.getEditor(), this.dirty);
            this.toolbar.setVisible(false);
        }
    }

    @Override
    public ListProvider<Object[]> getListProvider() {
        return new ListProvider<Object[]>(){

            @Override
            public List<Object[]> loadData() throws BasicException {
                return JPanelGlobalExpenses.this.m_dlSales.getGlobalPaymentsList();
            }

            @Override
            public List<Object[]> refreshData() throws BasicException {
                return this.loadData();
            }
        };
    }

    @Override
    public SaveProvider<Object[]> getSaveProvider() {
        return new SaveProvider<Object[]>(this.m_dlSales.getPaymentMovementUpdate(), this.m_dlSales.getPaymentMovementInsert(), this.m_dlSales.getPaymentMovementDelete());
    }

    @Override
    public EditorRecord getEditor() {
        return this.jeditor;
    }

    @Override
    public void activate() throws BasicException {
        this.startNavigation();
        this.showCard(CARD_LANDING);
    }

    @Override
    public String getTitle() {
        return "Gastos Gerenciales";
    }
}

