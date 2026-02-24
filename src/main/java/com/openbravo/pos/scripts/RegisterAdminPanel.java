package com.openbravo.pos.scripts;

import com.openbravo.pos.forms.AppConfig;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RegisterAdminPanel {
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
            
            // Get Admin Role (usually Administrator or role 0, we search by name "Administrator" or "Administrador")
            PreparedStatement getRole = conn.prepareStatement("SELECT ID, PERMISSIONS FROM ROLES WHERE NAME LIKE '%Admin%'");
            ResultSet rs = getRole.executeQuery();
            
            if (rs.next()) {
                String id = rs.getString("ID");
                String permissions = rs.getString("PERMISSIONS");
                
                String newClassXML = "<class name=\"com.openbravo.pos.inventory.JPanelBlindInventoryAdmin\"/>";
                
                if (!permissions.contains(newClassXML)) {
                    // Inject before the closing </permissions> tag
                    String updated = permissions.replace("</permissions>", "    " + newClassXML + "\n</permissions>");
                    
                    PreparedStatement updateRole = conn.prepareStatement("UPDATE ROLES SET PERMISSIONS = ? WHERE ID = ?");
                    updateRole.setString(1, updated);
                    updateRole.setString(2, id);
                    updateRole.executeUpdate();
                    System.out.println("Role " + id + " updated successfully to include the Blind Inventory Panel.");
                } else {
                    System.out.println("Role already has permission for the Blind Inventory Panel.");
                }
            } else {
                System.out.println("Admin role not found.");
            }
            
            // Also update Menu.Root as a database resource
            PreparedStatement getResource = conn.prepareStatement("SELECT CONTENT FROM RESOURCES WHERE NAME = 'Menu.Root'");
            ResultSet resRS = getResource.executeQuery();
            if (resRS.next()) {
                byte[] contentBytes = resRS.getBytes("CONTENT");
                if (contentBytes != null) {
                    String content = new String(contentBytes, "UTF-8");
                    String newMenuXML = "        submenu.addPanel(\"/com/openbravo/images/bookmark.png\", \"Menu.BlindInventory\", \"com.openbravo.pos.inventory.JPanelBlindInventoryAdmin\");";
                    
                    if (!content.contains("JPanelBlindInventoryAdmin")) {
                        String updated = content.replace("submenu.addPanel(\"/com/openbravo/images/location.png\", \"Menu.Locations\", \"com.openbravo.pos.inventory.LocationsPanel\");", "submenu.addPanel(\"/com/openbravo/images/location.png\", \"Menu.Locations\", \"com.openbravo.pos.inventory.LocationsPanel\");\n" + newMenuXML);
                        
                        PreparedStatement updateRes = conn.prepareStatement("UPDATE RESOURCES SET CONTENT = ? WHERE NAME = 'Menu.Root'");
                        updateRes.setBytes(1, updated.getBytes("UTF-8"));
                        updateRes.executeUpdate();
                        System.out.println("Menu.Root resource updated to inject the menu item.");
                    } else {
                        System.out.println("Menu.Root already contains the menu item.");
                    }
                }
            }
            
            conn.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
