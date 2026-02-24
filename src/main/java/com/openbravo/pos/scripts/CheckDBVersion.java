package com.openbravo.pos.scripts;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class CheckDBVersion {
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
            Statement stmt = conn.createStatement();
            
            System.out.println("Checking DB Version in APPLICATIONS table...");
            ResultSet rs = stmt.executeQuery("SELECT ID, NAME, VERSION FROM APPLICATIONS");
            while (rs.next()) {
                System.out.println("ID: " + rs.getString("ID") + ", NAME: " + rs.getString("NAME") + ", VERSION: " + rs.getString("VERSION"));
            }
            
            conn.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
