package com.openbravo.pos.forms;

import javax.swing.*;
import java.awt.*;

public class TestMenuButton {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Menu Button Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        // Simulate the action
        Action act = new AbstractAction("Cierres de caja (Largo)") {
            public void actionPerformed(java.awt.event.ActionEvent e) {}
        };

        // Simulate MenuItemDefinition logic
        JButton btn = new JButton(act);
        String text = (String) act.getValue(Action.NAME);
        if (text != null) {
            btn.setText("<html><center>" + text + "</center></html>");
        }
        
        btn.setPreferredSize(new Dimension(100, 100));
        
        frame.add(btn);
        frame.pack();
        frame.setVisible(true);
    }
}
