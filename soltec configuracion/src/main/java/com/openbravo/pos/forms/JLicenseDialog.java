/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.forms;

import com.openbravo.pos.forms.AppConfig;
import com.openbravo.pos.licensing.LicenseManager;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class JLicenseDialog
extends JDialog {
    private boolean authenticated = false;
    private JTextField txtLicenseKey;

    public JLicenseDialog(Frame parent, boolean modal) {
        super(parent, modal);
        this.initComponents();
    }

    private void initComponents() {
        this.setTitle("SOLTEC POS - Activation Required");
        this.setDefaultCloseOperation(2);
        this.setResizable(false);
        this.setSize(500, 250);
        this.setLocationRelativeTo(this.getParent());
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JLabel lblHeader = new JLabel("<html><b>SOLTEC POS is not activated.</b><br>Please send the ID below to your provider to receive an activation key.</html>");
        lblHeader.setFont(new Font("SansSerif", 0, 14));
        mainPanel.add((Component)lblHeader, "North");
        JPanel formPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        formPanel.add(new JLabel("Your System ID:"));
        JTextField txtMachineID = new JTextField(LicenseManager.getMachineID());
        txtMachineID.setEditable(false);
        txtMachineID.setFont(new Font("Monospaced", 1, 14));
        txtMachineID.setHorizontalAlignment(0);
        formPanel.add(txtMachineID);
        formPanel.add(new JLabel("Enter Activation Key:"));
        this.txtLicenseKey = new JTextField();
        this.txtLicenseKey.setFont(new Font("Monospaced", 0, 12));
        formPanel.add(this.txtLicenseKey);
        mainPanel.add((Component)formPanel, "Center");
        JPanel buttonPanel = new JPanel(new FlowLayout(2));
        JButton btnExit = new JButton("Exit");
        JButton btnActivate = new JButton("ACTIVATE");
        btnExit.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        btnActivate.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                JLicenseDialog.this.checkLicense();
            }
        });
        buttonPanel.add(btnExit);
        buttonPanel.add(btnActivate);
        mainPanel.add((Component)buttonPanel, "South");
        this.add(mainPanel);
        this.addWindowListener(new WindowAdapter(){

            @Override
            public void windowClosing(WindowEvent windowEvent) {
                if (!JLicenseDialog.this.authenticated) {
                    System.exit(0);
                }
            }
        });
    }

    private void checkLicense() {
        String key = this.txtLicenseKey.getText().trim();
        if (LicenseManager.isValid(key)) {
            this.authenticated = true;
            AppConfig config = AppConfig.getInstance();
            config.load();
            config.setProperty("activation.key", key);
            try {
                config.save();
                JOptionPane.showMessageDialog(this, "Activation Successful! Please restart the application.", "Success", 1);
                this.dispose();
            }
            catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error saving license: " + e.getMessage(), "Error", 0);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Invalid Activation Key.", "Error", 0);
        }
    }

    public boolean isAuthenticated() {
        return this.authenticated;
    }
}

