/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.licensing;

import com.openbravo.pos.licensing.LicenseManager;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class KeyGen
extends JFrame {
    private JTextField txtMachineID;
    private JTextField txtLicenseKey;
    private JButton btnGenerate;

    public KeyGen() {
        this.setTitle("SOLTEC POS Key Generator");
        this.setSize(500, 200);
        this.setDefaultCloseOperation(3);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout(10, 10));
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(new JLabel("Machine ID (Customer's System ID):"));
        this.txtMachineID = new JTextField();
        panel.add(this.txtMachineID);
        panel.add(new JLabel("License Key (Send this to Customer):"));
        this.txtLicenseKey = new JTextField();
        this.txtLicenseKey.setEditable(false);
        this.txtLicenseKey.setFont(new Font("Monospaced", 1, 12));
        panel.add(this.txtLicenseKey);
        this.btnGenerate = new JButton("GENERATE LICENSE");
        panel.add(new JLabel(""));
        panel.add(this.btnGenerate);
        this.add((Component)panel, "Center");
        this.btnGenerate.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                String id = KeyGen.this.txtMachineID.getText().trim();
                if (id.isEmpty()) {
                    JOptionPane.showMessageDialog(KeyGen.this, "Please enter a Machine ID.", "Error", 0);
                    return;
                }
                String key = LicenseManager.generateLicenseKey(id);
                KeyGen.this.txtLicenseKey.setText(key);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable(){

            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                }
                catch (Exception exception) {
                    // empty catch block
                }
                new KeyGen().setVisible(true);
            }
        });
    }
}

