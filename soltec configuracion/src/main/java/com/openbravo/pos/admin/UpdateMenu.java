package com.openbravo.pos.admin;

import java.io.*;
import java.sql.*;
import java.util.Properties;
import com.openbravo.pos.util.AltEncrypter;

public class UpdateMenu {

    public static void main(String[] args) {
        Connection conn = null;
        try {
            // 1. Read Properties
            Properties props = new Properties();
            File propertiesFile = new File(System.getProperty("user.home"), "soltec.properties");
            if (!propertiesFile.exists()) {
                 propertiesFile = new File("soltec.properties");
            }
            props.load(new FileInputStream(propertiesFile));

            String dbUrl = props.getProperty("db.URL");
            String dbUser = props.getProperty("db.user");
            String dbPass = props.getProperty("db.password");

            if (dbPass.startsWith("crypt:")) {
                AltEncrypter cypher = new AltEncrypter("cypherkey" + dbUser);
                dbPass = cypher.decrypt(dbPass.substring(6));
            }

            // 2. Connect
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
            System.out.println("Connected to database.");

            // 3. Read File Content
            File scriptFile = new File("menu_root_new.txt");
            if (!scriptFile.exists()) {
                System.out.println("Error: menu_root_new.txt not found.");
                return;
            }
            
            StringBuilder contentBuilder = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new FileReader(scriptFile))) {
                String sCurrentLine;
                while ((sCurrentLine = br.readLine()) != null) {
                    contentBuilder.append(sCurrentLine).append("\n");
                }
            }
            String scriptContent = contentBuilder.toString();

            // 4. Update Database
            String updateSql = "UPDATE RESOURCES SET CONTENT = ? WHERE NAME = 'Menu.Root'";
            PreparedStatement pstmt = conn.prepareStatement(updateSql);
            pstmt.setBytes(1, scriptContent.getBytes("UTF-8"));
            
            int rows = pstmt.executeUpdate();
            
            if (rows > 0) {
                System.out.println("Successfully updated Menu.Root.");
            } else {
                System.out.println("Warning: Menu.Root not found or not updated.");
            }
            
            pstmt.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (conn != null) conn.close(); } catch (SQLException e) {}
        }
    }
}
