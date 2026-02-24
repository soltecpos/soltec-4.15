package com.openbravo.pos.admin;

import java.io.*;
import java.sql.*;
import java.util.Properties;
import com.openbravo.pos.util.AltEncrypter;

public class DumpMenu {
    public static void main(String[] args) {
        Connection conn = null;
        try {
            Properties props = new Properties();
            File propertiesFile = new File(System.getProperty("user.home"), "soltec.properties");
            if (!propertiesFile.exists()) propertiesFile = new File("soltec.properties");
            props.load(new FileInputStream(propertiesFile));

            String dbUrl = props.getProperty("db.URL");
            String dbUser = props.getProperty("db.user");
            String dbPass = props.getProperty("db.password");

            if (dbPass.startsWith("crypt:")) {
                AltEncrypter cypher = new AltEncrypter("cypherkey" + dbUser);
                dbPass = cypher.decrypt(dbPass.substring(6));
            }

            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);

            String query = "SELECT CONTENT FROM RESOURCES WHERE NAME = 'Menu.Root'";
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                byte[] content = rs.getBytes("CONTENT");
                if (content != null) {
                    try (FileOutputStream fos = new FileOutputStream("menu_dump.txt")) {
                        fos.write(content);
                        System.out.println("Menu.Root dumped to menu_dump.txt");
                    }
                } else {
                    System.out.println("Menu.Root is empty.");
                }
            } else {
                System.out.println("Menu.Root not found in database.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (conn != null) conn.close(); } catch (SQLException e) {}
        }
    }
}
