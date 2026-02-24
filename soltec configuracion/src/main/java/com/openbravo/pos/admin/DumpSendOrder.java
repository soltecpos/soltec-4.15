package com.openbravo.pos.admin;

import com.openbravo.data.loader.*;
import com.openbravo.pos.forms.AppConfig;
import java.io.File;
import java.io.FileWriter;

public class DumpSendOrder {
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
            
            String script = (String) new PreparedSentence(session, 
                "SELECT CONTENT FROM RESOURCES WHERE NAME = ?", 
                SerializerWriteString.INSTANCE, 
                SerializerReadString.INSTANCE)
                .find("script.SendOrder");
                
            if (script != null) {
                System.out.println("Script found. Writing to script_SendOrder.txt");
                try (FileWriter fw = new FileWriter("script_SendOrder.txt")) {
                    fw.write(script);
                }
            } else {
                System.out.println("Script script.SendOrder NOT FOUND in DB.");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
