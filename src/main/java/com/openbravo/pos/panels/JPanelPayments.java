/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.panels;

import com.openbravo.basic.BasicException;
import com.openbravo.data.gui.JMessageDialog;
import com.openbravo.data.gui.JSaver;
import com.openbravo.data.gui.MessageInf;
import com.openbravo.data.gui.TableRendererBasic;
import com.openbravo.data.loader.ComparatorCreator;
import com.openbravo.data.loader.Vectorer;
import com.openbravo.data.user.BrowsableEditableData;
import com.openbravo.data.user.DirtyManager;
import com.openbravo.data.user.EditorRecord;
import com.openbravo.data.user.ListProvider;
import com.openbravo.data.user.SaveProvider;
import com.openbravo.format.Formats;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.DataLogicSales;
import com.openbravo.pos.panels.JPanelTable;
import com.openbravo.pos.panels.JTableNavigator;
import com.openbravo.pos.panels.PaymentsEditor;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.text.ParseException;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;
import javax.swing.table.TableColumnModel;

public class JPanelPayments
extends JPanelTable {
    private PaymentsEditor jeditor;
    private DataLogicSales m_dlSales = null;

    @Override
    protected void init() {
        try {
            this.m_dlSales = (DataLogicSales)this.app.getBean("com.openbravo.pos.forms.DataLogicSales");
            this.jeditor = new PaymentsEditor(this.app, this.dirty);
        }
        catch (Throwable t) {
            JMessageDialog.showMessage(this, new MessageInf(-33554432, "Error in JPanelPayments.init: " + t.getMessage(), t));
            t.printStackTrace();
        }
    }

    @Override
    protected void startNavigation() {
        if (this.bd == null) {
            this.bd = new BrowsableEditableData<Object[]>(this.getListProvider(), this.getSaveProvider(), this.getEditor(), this.dirty);
            Component c = this.getEditor().getComponent();
            if (c != null) {
                c.applyComponentOrientation(this.getComponentOrientation());
                this.container.add(c, "Center");
            }
            this.toolbar.add(new JSeparator(1));
            JButton btnSave = new JButton("GUARDAR");
            URL iconSave = this.getClass().getResource("/com/openbravo/images/filesave.png");
            if (iconSave != null) {
                btnSave.setIcon(new ImageIcon(iconSave));
            }
            btnSave.setToolTipText(AppLocal.getIntString("tooltip.save"));
            btnSave.setFocusPainted(false);
            btnSave.setBackground(new Color(46, 204, 113));
            btnSave.setForeground(Color.WHITE);
            btnSave.setFont(new Font("Arial", 1, 12));
            btnSave.setPreferredSize(new Dimension(120, 45));
            btnSave.setMargin(new Insets(5, 5, 5, 5));
            final PaymentsEditor theEditor = this.jeditor;
            btnSave.addActionListener(new ActionListener(){

                @Override
                public void actionPerformed(ActionEvent evt) {
                    try {
                        if (theEditor != null) {
                            theEditor.setIgnoreDirty(true);
                        }
                        JPanelPayments.this.bd.actionInsert();
                        if (theEditor != null) {
                            theEditor.enableFields(true);
                        }
                        JPanelPayments.this.dirty.setDirty(false);
                    }
                    catch (BasicException e) {
                        try {
                            e.printStackTrace();
                            JMessageDialog.showMessage(JPanelPayments.this, new MessageInf(-33554432, "Error al guardar el movimiento: " + e.getMessage(), e));
                        }
                        catch (Throwable throwable) {
                            SwingUtilities.invokeLater(new Runnable(){

                                @Override
                                public void run() {
                                    if (theEditor != null) {
                                        theEditor.setIgnoreDirty(false);
                                    }
                                    JPanelPayments.this.dirty.setDirty(false);
                                }
                            });
                            throw throwable;
                        }
                        SwingUtilities.invokeLater(new Runnable(){

                                @Override
                                public void run() {
                                    if (theEditor != null) {
                                        theEditor.setIgnoreDirty(false);
                                    }
                                    JPanelPayments.this.dirty.setDirty(false);
                                }
                            });
                    }
                    SwingUtilities.invokeLater(new Runnable(){

                                @Override
                                public void run() {
                                    if (theEditor != null) {
                                        theEditor.setIgnoreDirty(false);
                                    }
                                    JPanelPayments.this.dirty.setDirty(false);
                                }
                            });
                }
            });
            this.toolbar.add(btnSave);
            JButton btnNew = new JButton("NUEVO");
            URL iconNew = this.getClass().getResource("/com/openbravo/images/editnew.png");
            if (iconNew != null) {
                btnNew.setIcon(new ImageIcon(iconNew));
            }
            btnNew.setToolTipText(AppLocal.getIntString("tooltip.addnew"));
            btnNew.setFocusPainted(false);
            btnNew.setBackground(new Color(52, 152, 219));
            btnNew.setForeground(Color.WHITE);
            btnNew.setFont(new Font("Arial", 1, 12));
            btnNew.setPreferredSize(new Dimension(120, 45));
            btnNew.setMargin(new Insets(5, 5, 5, 5));
            btnNew.addActionListener(new ActionListener(){

                @Override
                public void actionPerformed(ActionEvent evt) {
                    try {
                        if (theEditor != null) {
                            theEditor.setIgnoreDirty(true);
                        }
                        JPanelPayments.this.bd.actionInsert();
                        if (theEditor != null) {
                            theEditor.enableFields(true);
                        }
                        JPanelPayments.this.dirty.setDirty(false);
                    }
                    catch (BasicException e) {
                        try {
                            e.printStackTrace();
                        }
                        catch (Throwable throwable) {
                            SwingUtilities.invokeLater(new Runnable(){

                                @Override
                                public void run() {
                                    if (theEditor != null) {
                                        theEditor.setIgnoreDirty(false);
                                    }
                                    JPanelPayments.this.dirty.setDirty(false);
                                }
                            });
                            throw throwable;
                        }
                        SwingUtilities.invokeLater(new Runnable(){

                                @Override
                                public void run() {
                                    if (theEditor != null) {
                                        theEditor.setIgnoreDirty(false);
                                    }
                                    JPanelPayments.this.dirty.setDirty(false);
                                }
                            });
                    }
                    SwingUtilities.invokeLater(new Runnable(){

                                @Override
                                public void run() {
                                    if (theEditor != null) {
                                        theEditor.setIgnoreDirty(false);
                                    }
                                    JPanelPayments.this.dirty.setDirty(false);
                                }
                            });
                }
            });
            this.toolbar.add(btnNew);
            this.toolbar.add(new JSeparator(1));
            JButton btnViewAll = new JButton("HISTORIAL");
            URL iconSearch = this.getClass().getResource("/com/openbravo/images/search.png");
            if (iconSearch != null) {
                btnViewAll.setIcon(new ImageIcon(iconSearch));
            }
            btnViewAll.setToolTipText(AppLocal.getIntString("label.viewhistory", "Ver Historial Completo"));
            btnViewAll.setFocusPainted(false);
            btnViewAll.setBackground(Color.WHITE);
            btnViewAll.setFont(new Font("Arial", 1, 12));
            btnViewAll.setPreferredSize(new Dimension(150, 45));
            btnViewAll.setMargin(new Insets(5, 5, 5, 5));
            btnViewAll.addActionListener(new ActionListener(){

                @Override
                public void actionPerformed(ActionEvent evt) {
                    JPanelPayments.this.showHistoryDialog();
                }
            });
            this.toolbar.add(btnViewAll);
            this.toolbar.revalidate();
            this.toolbar.repaint();
        }
    }

    private void showHistoryDialog() {
        final JDialog dialog = new JDialog(SwingUtilities.getWindowAncestor(this), "Historial de Movimientos - Modo Edici\u00c3\u00b3n", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setLayout(new BorderLayout());
        DirtyManager popupDirty = new DirtyManager();
        PaymentsEditor popupEditor = new PaymentsEditor(this.app, popupDirty);
        BrowsableEditableData<Object[]> popupBd = new BrowsableEditableData<Object[]>(this.getListProvider(), this.getSaveProvider(), popupEditor, popupDirty);
        String[] colNames = new String[]{AppLocal.getIntString("label.date"), AppLocal.getIntString("label.paymentreason"), AppLocal.getIntString("label.paymentcategory"), AppLocal.getIntString("label.paymenttotal"), AppLocal.getIntString("label.notes"), AppLocal.getIntString("label.user")};
        int[] colIndices = new int[]{2, 4, 7, 5, 6, 8};
        JTableNavigator<Object[]> popupNavigator = new JTableNavigator<Object[]>(popupBd, colNames, colIndices);
        popupNavigator.getTable().setDefaultRenderer(Object.class, new TableRendererBasic(new Formats[]{Formats.TIMESTAMP, new FormatsPayment(), Formats.STRING, Formats.CURRENCY, Formats.STRING, Formats.STRING}));
        TableColumnModel jColumns = popupNavigator.getTable().getColumnModel();
        jColumns.getColumn(0).setPreferredWidth(140);
        jColumns.getColumn(1).setPreferredWidth(160);
        jColumns.getColumn(2).setPreferredWidth(160);
        jColumns.getColumn(3).setPreferredWidth(100);
        jColumns.getColumn(4).setPreferredWidth(200);
        jColumns.getColumn(5).setPreferredWidth(100);
        popupNavigator.getTable().setRowHeight(35);
        JComponent editorComp = (JComponent)popupEditor.getComponent();
        if (editorComp != null) {
            editorComp.applyComponentOrientation(this.getComponentOrientation());
        }
        JPanel pnlEditorContainer = new JPanel(new BorderLayout());
        pnlEditorContainer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        pnlEditorContainer.add((Component)editorComp, "North");
        JSplitPane splitPane = new JSplitPane(1, popupNavigator, pnlEditorContainer);
        splitPane.setDividerLocation(600);
        splitPane.setResizeWeight(0.6);
        dialog.add((Component)splitPane, "Center");
        JPanel pnlBottom = new JPanel(new FlowLayout(2));
        JSaver dialogSaver = new JSaver(popupBd);
        pnlBottom.add(dialogSaver);
        JButton btnClose = new JButton("Cerrar");
        btnClose.setFont(new Font("Segoe UI", 1, 14));
        btnClose.setPreferredSize(new Dimension(100, 40));
        btnClose.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
        pnlBottom.add(btnClose);
        dialog.add((Component)pnlBottom, "South");
        try {
            popupBd.actionLoad();
        }
        catch (BasicException e) {
            e.printStackTrace();
        }
        dialog.setSize(1000, 700);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    @Override
    public ListProvider<Object[]> getListProvider() {
        return new ListProvider<Object[]>(){

            @Override
            public List<Object[]> loadData() throws BasicException {
                return JPanelPayments.this.m_dlSales.getPaymentMovementsList(JPanelPayments.this.app.getActiveCashIndex(), false);
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
    public Vectorer getVectorer() {
        return null;
    }

    @Override
    public ComparatorCreator getComparatorCreator() {
        return null;
    }

    @Override
    public EditorRecord getEditor() {
        return this.jeditor;
    }

    @Override
    public void activate() throws BasicException {
        try {
            this.startNavigation();
            this.bd.actionLoad();
            this.bd.actionInsert();
        }
        catch (Throwable t) {
            JMessageDialog.showMessage(this, new MessageInf(-33554432, "Error: " + t.toString(), t));
            t.printStackTrace();
            if (t instanceof BasicException) {
                throw (BasicException)t;
            }
            throw new BasicException(t);
        }
    }

    @Override
    public String getTitle() {
        return AppLocal.getIntString("Menu.Payments");
    }

    private class FormatsPayment
    extends Formats {
        private FormatsPayment() {
        }

        @Override
        protected String formatValueInt(Object value) {
            return AppLocal.getIntString("transpayment." + (String)value);
        }

        @Override
        protected Object parseValueInt(String value) throws ParseException {
            return value;
        }

        @Override
        public int getAlignment() {
            return 2;
        }
    }
}

