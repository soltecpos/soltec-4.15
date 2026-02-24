/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.sales;

import com.openbravo.data.loader.Session;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;

public class JMooringDetails
extends JDialog {
    private Connection con;
    private ResultSet rs;
    private Statement stmt;
    private String ID;
    private String SQL;
    private String vesselName = "";
    private Integer vesselSize;
    private Integer vesselDays;
    private Boolean vesselPower;
    private boolean create = false;
    private JLabel jLabel1;
    private JScrollPane jScrollPane2;
    private JTable jTableSelector;
    private JTextField jText;
    private JButton jbtnCreateTicket;

    private JMooringDetails(Frame parent, boolean modal) {
        super(parent, modal);
    }

    private JMooringDetails(Dialog parent, boolean modal) {
        super(parent, modal);
    }

    private void init(Session s) {
        this.initComponents();
        this.setTitle("Select Vessel details");
        try {
            this.con = s.getConnection();
            this.stmt = this.con.createStatement();
            this.SQL = "SELECT * FROM moorers";
            this.rs = this.stmt.executeQuery(this.SQL);
            this.jTableSelector.setModel(DbUtils.resultSetToTableModel(this.rs));
            this.jTableSelector.getColumnModel().getColumn(0).setPreferredWidth(200);
            this.jTableSelector.getColumnModel().getColumn(1).setPreferredWidth(40);
            this.jTableSelector.getColumnModel().getColumn(2).setPreferredWidth(40);
            this.jTableSelector.getColumnModel().getColumn(3).setPreferredWidth(40);
            this.jTableSelector.setRowSelectionAllowed(true);
            this.jTableSelector.getTableHeader().setReorderingAllowed(true);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public static JMooringDetails getMooringDetails(Component parent, Session s) {
        Window window = SwingUtilities.getWindowAncestor(parent);
        JMooringDetails myMsg = window instanceof Frame ? new JMooringDetails((Frame)window, true) : new JMooringDetails((Dialog)window, true);
        myMsg.init(s);
        myMsg.applyComponentOrientation(parent.getComponentOrientation());
        return myMsg;
    }

    public boolean isCreate() {
        return this.create;
    }

    public String getVesselName() {
        return this.vesselName;
    }

    public Integer getVesselSize() {
        return this.vesselSize;
    }

    public Integer getVesselDays() {
        return this.vesselDays;
    }

    public Boolean getVesselPower() {
        return this.vesselPower;
    }

    private void initComponents() {
        this.jbtnCreateTicket = new JButton();
        this.jScrollPane2 = new JScrollPane();
        this.jTableSelector = new JTable();
        this.jText = new JTextField();
        this.jLabel1 = new JLabel();
        this.setDefaultCloseOperation(2);
        this.jbtnCreateTicket.setFont(new Font("Arial", 0, 12));
        this.jbtnCreateTicket.setIcon(new ImageIcon(this.getClass().getResource("/com/openbravo/images/ok.png")));
        ResourceBundle bundle = ResourceBundle.getBundle("pos_messages");
        this.jbtnCreateTicket.setText(bundle.getString("label.mooringcreatebtn"));
        this.jbtnCreateTicket.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JMooringDetails.this.jbtnCreateTicketActionPerformed(evt);
            }
        });
        this.jTableSelector.setFont(new Font("Arial", 0, 12));
        this.jTableSelector.setModel(new DefaultTableModel(new Object[][]{{null, null, null, null}, {null, null, null, null}, {null, null, null, null}, {null, null, null, null}, {null, null, null, null}, {null, null, null, null}}, new String[]{"Vessel Name", "Size", "Days", "Power"}){
            boolean[] canEdit;
            {
                this.canEdit = new boolean[]{false, false, false, false};
            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return this.canEdit[columnIndex];
            }
        });
        this.jTableSelector.setRowHeight(25);
        this.jTableSelector.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent evt) {
                JMooringDetails.this.jTableSelectorMouseClicked(evt);
            }
        });
        this.jScrollPane2.setViewportView(this.jTableSelector);
        this.jTableSelector.getColumnModel().getColumn(1).setResizable(false);
        this.jTableSelector.getColumnModel().getColumn(1).setPreferredWidth(50);
        this.jTableSelector.getColumnModel().getColumn(2).setResizable(false);
        this.jTableSelector.getColumnModel().getColumn(2).setPreferredWidth(50);
        this.jTableSelector.getColumnModel().getColumn(3).setResizable(false);
        this.jTableSelector.getColumnModel().getColumn(3).setPreferredWidth(70);
        this.jText.setFont(new Font("Arial", 0, 12));
        this.jText.setDisabledTextColor(new Color(0, 0, 0));
        this.jText.setEnabled(false);
        this.jText.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JMooringDetails.this.jTextActionPerformed(evt);
            }
        });
        this.jLabel1.setFont(new Font("Arial", 0, 12));
        this.jLabel1.setText(bundle.getString("label.mooringscreatefor"));
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jScrollPane2, -1, 508, Short.MAX_VALUE).addGroup(layout.createSequentialGroup().addGap(10, 10, 10).addComponent(this.jLabel1, -2, 107, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jText, -2, 222, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jbtnCreateTicket, -2, 150, -2).addGap(0, 0, Short.MAX_VALUE))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jScrollPane2, -2, 179, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jText, -2, -1, -2).addComponent(this.jLabel1).addComponent(this.jbtnCreateTicket)).addContainerGap(-1, Short.MAX_VALUE)));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setBounds((screenSize.width - 536) / 2, (screenSize.height - 274) / 2, 536, 274);
    }

    private void jbtnCreateTicketActionPerformed(ActionEvent evt) {
        if (this.vesselName.equals("")) {
            this.create = false;
            this.dispose();
        } else {
            this.create = true;
            this.dispose();
        }
    }

    private void jTableSelectorMouseClicked(MouseEvent evt) {
        try {
            int row = this.jTableSelector.getSelectedRow();
            this.vesselName = this.jTableSelector.getModel().getValueAt(row, 0).toString();
            this.SQL = "SELECT * FROM moorers WHERE VESSELNAME ='" + this.vesselName + "';";
            this.rs = this.stmt.executeQuery(this.SQL);
            if (this.rs.next()) {
                this.vesselDays = this.rs.getInt("DAYS");
                this.vesselSize = this.rs.getInt("SIZE");
                this.vesselPower = this.rs.getBoolean("POWER");
                this.jText.setText(this.vesselName);
            }
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void jTextActionPerformed(ActionEvent evt) {
    }
}

