package com.openbravo.pos.admin;

import java.io.*;
import java.sql.*;
import java.util.Properties;
import com.openbravo.pos.util.AltEncrypter;

public class UpdateSendOrder {

    public static void main(String[] args) {
        Connection conn = null;
        try {
            // 1. Read Properties
            Properties props = new Properties();
            File propertiesFile = new File(System.getProperty("user.home"), "soltec.properties");
            if (!propertiesFile.exists()) {
                 // Fallback to current dir
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
            File scriptFile = new File("script_SendOrder_debug.txt");
            if (!scriptFile.exists()) {
                System.out.println("Error: script_SendOrder_debug.txt not found.");
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
            // RESOURCES table has NAME (varchar) and CONTENT (blob/longvarbinary)
            // We use PreparedStatement to handle the blob/string safely.
            String updateSql = "UPDATE RESOURCES SET CONTENT = ? WHERE NAME = 'script.SendOrder'";
            PreparedStatement pstmt = conn.prepareStatement(updateSql);
            pstmt.setBytes(1, scriptContent.getBytes("UTF-8")); // Resources are often stored as raw bytes
            
            int rows = pstmt.executeUpdate();
            
            if (rows > 0) {
                System.out.println("Successfully updated script.SendOrder with debug content.");
            } else {
                System.out.println("Warning: script.SendOrder not found or not updated. Trying INSERT...");
                // Optional: Insert if not exists, but usually it exists.
            }
            
            pstmt.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (conn != null) conn.close(); } catch (SQLException e) {}
        }
    }
}
