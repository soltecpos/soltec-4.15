package com.openbravo.pos.admin;

import java.io.*;
import java.sql.*;
import java.util.Properties;
import com.openbravo.pos.util.AltEncrypter;

/**
 * Updates Printer.Ticket.P1 through P6 templates in the database
 * from the source XML files to ensure sendstatus filtering is active.
 */
public class UpdatePrinterTemplates {
    public static void main(String[] args) {
        Connection conn = null;
        try {
            // 1. Read Properties (same logic as UpdateSendOrder)
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

            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
            System.out.println("Connected to database.");

            // 2. Dump current P1 template for diagnostics
            System.out.println("\n=== CURRENT DB TEMPLATE (Printer.Ticket.P1) ===");
            PreparedStatement dumpPs = conn.prepareStatement("SELECT CONTENT FROM resources WHERE NAME = ?");
            dumpPs.setString(1, "Printer.Ticket.P1");
            ResultSet dumpRs = dumpPs.executeQuery();
            if (dumpRs.next()) {
                byte[] content = dumpRs.getBytes(1);
                if (content != null) {
                    System.out.println(new String(content, "UTF-8"));
                } else {
                    System.out.println("CONTENT IS NULL");
                }
            } else {
                System.out.println("NOT FOUND IN DB");
            }
            dumpRs.close();
            dumpPs.close();
            System.out.println("=== END CURRENT DB TEMPLATE ===\n");

            // 3. Update P1-P6 from source files
            String[] templates = {"Printer.Ticket.P1", "Printer.Ticket.P2", "Printer.Ticket.P3",
                                  "Printer.Ticket.P4", "Printer.Ticket.P5", "Printer.Ticket.P6"};

            for (String templateName : templates) {
                String sourceFile = "src/main/resources/com/openbravo/pos/templates/" + templateName + ".xml";
                File f = new File(sourceFile);
                if (!f.exists()) {
                    System.out.println("Source file not found: " + sourceFile + " - SKIPPING");
                    continue;
                }

                byte[] fileContent = new byte[(int) f.length()];
                FileInputStream fis = new FileInputStream(f);
                fis.read(fileContent);
                fis.close();

                String updateSql = "UPDATE resources SET CONTENT = ? WHERE NAME = ?";
                PreparedStatement ps = conn.prepareStatement(updateSql);
                ps.setBytes(1, fileContent);
                ps.setString(2, templateName);
                int rows = ps.executeUpdate();
                ps.close();

                if (rows > 0) {
                    System.out.println("Updated " + templateName + " (" + fileContent.length + " bytes)");
                } else {
                    System.out.println("WARNING: " + templateName + " not found in DB");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) try { conn.close(); } catch (Exception e) {}
        }
        System.out.println("\nDone.");
    }
}
