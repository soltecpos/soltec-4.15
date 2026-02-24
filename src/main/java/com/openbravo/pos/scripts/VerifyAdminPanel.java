package com.openbravo.pos.scripts;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class VerifyAdminPanel {
    public static void main(String[] args) {
        try {
            java.util.Properties config = new java.util.Properties();
            config.load(new java.io.FileInputStream(new File(System.getProperty("user.home"), "soltec.properties")));

            String url = config.getProperty("db.URL");
            String user = config.getProperty("db.user");
            String password = config.getProperty("db.password");
            
            if (password != null && password.startsWith("crypt:")) {
                com.openbravo.pos.util.AltEncrypter cryp = new com.openbravo.pos.util.AltEncrypter("cypherkey" + user);
                password = cryp.decrypt(password.substring(6));
            }
            
            Connection conn = DriverManager.getConnection(url, user, password);
            
            System.out.println("--- CHECKING ROLES ---");
            PreparedStatement getRole = conn.prepareStatement("SELECT ID, NAME, PERMISSIONS FROM ROLES");
            ResultSet rs = getRole.executeQuery();
            while (rs.next()) {
                String name = rs.getString("NAME");
                String permissions = rs.getString("PERMISSIONS");
                if (permissions != null && permissions.contains("JPanelBlindInventoryAdmin")) {
                    System.out.println("Role '" + name + "' (ID: " + rs.getString("ID") + ") HAS the permission.");
                } else {
                    System.out.println("Role '" + name + "' (ID: " + rs.getString("ID") + ") does NOT have the permission.");
                }
            }
            
            System.out.println("\n--- CHECKING MENU.ROOT ---");
            PreparedStatement getResource = conn.prepareStatement("SELECT CONTENT FROM RESOURCES WHERE NAME = 'Menu.Root'");
            ResultSet resRS = getResource.executeQuery();
            if (resRS.next()) {
                byte[] contentBytes = resRS.getBytes("CONTENT");
                if (contentBytes != null) {
                    String content = new String(contentBytes, "UTF-8");
                    if (content.contains("JPanelBlindInventoryAdmin")) {
                        System.out.println("Menu.Root DOES contain the inventory class.");
                    } else {
                        System.out.println("Menu.Root does NOT contain the inventory class.");
                        System.out.println("====== CONTENT DUMP ======");
                        System.out.println(content);
                        System.out.println("==========================");
                    }
                }
            } else {
                System.out.println("Menu.Root not found in database.");
            }
            
            conn.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
