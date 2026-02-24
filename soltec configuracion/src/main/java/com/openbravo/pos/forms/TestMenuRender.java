package com.openbravo.pos.forms;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TestMenuRender {
    public static void main(String[] args) {
        System.out.println("Starting TestMenuRender...");
        JFrame frame = new JFrame("Test Menu Render");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Mock MenuDefinition
        JPanelMenu menu = new JPanelMenu(new MenuDefinition("Test Menu Title") {
            // public String getTitle() { return "Test Menu"; } // Override might not be needed if constructor sets it
            public int countMenuElements() { return 1; }
            public MenuElement getMenuElement(int i) {
                return new MenuItemDefinition(new AbstractAction("Very Long Button Name That Should Wrap") {
                    public void actionPerformed(ActionEvent e) {}
                });
            }
        });
        
        try {
            menu.activate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        frame.add(menu);
        frame.pack();
        frame.setVisible(true);
        System.out.println("Menu visible.");
    }
}
