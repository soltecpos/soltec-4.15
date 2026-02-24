package com.openbravo.pos.admin;

import com.openbravo.data.loader.*;
import com.openbravo.pos.forms.AppConfig;
import java.io.FileWriter;
import java.io.File;

public class DumpButtons {
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
                .find("Ticket.Buttons");
                
            if (content != null) {
                System.out.println("Found Ticket.Buttons");
                try (FileWriter fw = new FileWriter("Ticket_Buttons.xml")) {
                    fw.write(content);
                }
            } else {
                System.out.println("Ticket.Buttons NOT FOUND in DB.");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
