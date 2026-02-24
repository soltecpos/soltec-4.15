package com.openbravo.pos.admin;

import com.openbravo.data.loader.*;
import com.openbravo.pos.forms.AppConfig;
import java.io.File;
import java.io.FileWriter;

public class DumpPrinterTemplates {
    public static void main(String[] args) {
        try {
            File configFile = new File(System.getProperty("user.home"), "soltec.properties");
            AppConfig config = new AppConfig(configFile);
            config.load();
            
            String driver = config.getProperty("db.driver");
            String url = config.getProperty("db.URL");
            String user = config.getProperty("db.user");
            String password = config.getProperty("db.password");
            
            if (password != null && password.startsWith("crypt:")) {
                com.openbravo.pos.util.AltEncrypter cypher = new com.openbravo.pos.util.AltEncrypter("cypherkey" + user);
                password = cypher.decrypt(password.substring(6));
            }
            
            Class.forName(driver);
            Session session = new Session(url, user, password);
            
            String[] templates = {"Printer.Ticket.Kitchen", "Printer.Ticket.P1", "Printer.Ticket.P2"};
            
            for (String t : templates) {
                String content = (String) new PreparedSentence(session, 
                    "SELECT CONTENT FROM RESOURCES WHERE NAME = ?", 
                    SerializerWriteString.INSTANCE, 
                    SerializerReadString.INSTANCE)
                    .find(t);
                    
                if (content != null) {
                    System.out.println("Found " + t);
                    try (FileWriter fw = new FileWriter(t + ".xml")) {
                        fw.write(content);
                    }
                } else {
                    System.out.println(t + " NOT FOUND in DB.");
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
