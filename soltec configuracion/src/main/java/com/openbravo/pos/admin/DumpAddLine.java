package com.openbravo.pos.admin;

import com.openbravo.data.loader.*;
import com.openbravo.pos.forms.AppConfig;
import java.io.FileWriter;
import java.io.File;

public class DumpAddLine {
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
            
            String content = (String) new PreparedSentence(session, 
                "SELECT CONTENT FROM RESOURCES WHERE NAME = ?", 
                SerializerWriteString.INSTANCE, 
                SerializerReadString.INSTANCE)
                .find("event.addline"); // Also check Ticket.AddLine
                
            if (content != null) {
                System.out.println("Found event.addline");
                try (FileWriter fw = new FileWriter("event_addline.txt")) {
                    fw.write(content);
                }
            } else {
                 // Try ticket.addline if event.addline is missing (though typically event.addline)
                System.out.println("event.addline NOT FOUND. Checking Ticket.AddLine...");
                 content = (String) new PreparedSentence(session, 
                    "SELECT CONTENT FROM RESOURCES WHERE NAME = ?", 
                    SerializerWriteString.INSTANCE, 
                    SerializerReadString.INSTANCE)
                    .find("Ticket.AddLine");
                 if (content != null) {
                     System.out.println("Found Ticket.AddLine");
                     try (FileWriter fw = new FileWriter("event_addline.txt")) {
                        fw.write(content);
                    }
                 } else {
                     System.out.println("Ticket.AddLine NOT FOUND.");
                 }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
