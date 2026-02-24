package com.openbravo.pos.admin;

import java.io.*;
import java.sql.*;
import java.util.Properties;
import com.openbravo.pos.util.AltEncrypter;

public class DumpRoles {
    public static void main(String[] args) {
        Connection conn = null;
        try {
            Properties props = new Properties();
            File propertiesFile = new File("soltec.properties");
            if (!propertiesFile.exists()) {
                propertiesFile = new File(System.getProperty("user.home"), "soltec.properties");
            }
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

            String query = "SELECT NAME, PERMISSIONS FROM ROLES";
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String name = rs.getString("NAME");
                byte[] permissions = rs.getBytes("PERMISSIONS");
                System.out.println("Role: " + name);
                if (permissions != null) {
                    System.out.println(new String(permissions, "UTF-8"));
                } else {
                    System.out.println("Permissions: (null)");
                }
                System.out.println("--------------------------------------------------");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (conn != null) conn.close(); } catch (SQLException e) {}
        }
    }
}
