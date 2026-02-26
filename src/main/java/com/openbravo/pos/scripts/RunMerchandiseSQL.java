package com.openbravo.pos.scripts;

import com.openbravo.pos.forms.AppConfig;
import com.openbravo.data.loader.Session;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;

public class RunMerchandiseSQL {
    public static void main(String[] args) {
        try {
            AppConfig config = new AppConfig(new File(System.getProperty("user.home"), "soltec.properties"));
            config.load();
            
            String url = config.getProperty("db.URL");
            String user = config.getProperty("db.user");
            String password = config.getProperty("db.password");
            
            if (password != null && password.startsWith("crypt:")) {
                com.openbravo.pos.util.AltEncrypter cryp = new com.openbravo.pos.util.AltEncrypter("cypherkey" + user);
                password = cryp.decrypt(password.substring(6));
            }
            
            System.out.println("Connecting to " + url);
            Connection conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();
            
            File sqlFile = new File("C:/Users/aux1/Documents/loquedejolatormenta/src/main/resources/com/openbravo/pos/scripts/merchandise_receipts.sql");
            BufferedReader br = new BufferedReader(new FileReader(sqlFile));
            String line;
            StringBuilder sb = new StringBuilder();
            
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("--")) continue;
                
                sb.append(line).append(" ");
                if (line.endsWith(";")) {
                    String sql = sb.toString();
                    System.out.println("Executing: " + sql);
                    try {
                        stmt.execute(sql);
                    } catch (Exception e) {
                        System.out.println("Error executing statement: " + e.getMessage());
                    }
                    sb.setLength(0);
                }
            }
            br.close();
            stmt.close();
            conn.close();
            System.out.println("Finished running script.");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
