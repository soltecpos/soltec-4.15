package com.openbravo.pos.scripts;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class TestKardexRelations {
    public static void main(String[] args) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/soltecdb?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC", "root", "admin");
            Statement stmt = conn.createStatement();
            
            // Checking if DATENEW matches between stockdiary and receipts
            ResultSet rs = stmt.executeQuery(
                "SELECT SD.DATENEW, SD.PRODUCT, R.ID AS RECEIPT_ID, TL.PRODUCT AS TICKET_PRODUCT " +
                "FROM stockdiary SD " +
                "JOIN receipts R ON SD.DATENEW = R.DATENEW " +
                "JOIN ticketlines TL ON R.ID = TL.TICKET " +
                "WHERE SD.REASON = -1 LIMIT 10"
            );
            
            System.out.println("Matches found:");
            while(rs.next()) {
                System.out.println("Date: " + rs.getTimestamp("DATENEW") + 
                    " | Stock Product: " + rs.getString("PRODUCT") + 
                    " | Receipt: " + rs.getString("RECEIPT_ID") + 
                    " | TicketLine Product: " + rs.getString("TICKET_PRODUCT"));
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
