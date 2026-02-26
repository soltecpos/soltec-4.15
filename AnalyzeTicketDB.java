import com.openbravo.pos.ticket.TicketInfo;
import com.openbravo.pos.ticket.TicketLineInfo;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class AnalyzeTicketDB {
    public static void main(String[] args) {
        String url = "jdbc:mysql://100.112.128.16:3306/soltec?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        String user = "admin";
        String pass = "root";

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             Statement stmt = conn.createStatement()) {

            System.out.println("====== ANALYZING SHAREDTICKETS ====== ");
            ResultSet rs = stmt.executeQuery("SELECT id, name, content FROM sharedtickets LIMIT 10");
            boolean found = false;
            while (rs.next()) {
                found = true;
                String id = rs.getString("id");
                String name = rs.getString("name");
                System.out.println("ID: " + id + " | Name: " + name);
                
                byte[] blob = rs.getBytes("content");
                if (blob != null) {
                    try (ByteArrayInputStream bis = new ByteArrayInputStream(blob);
                         ObjectInputStream ois = new ObjectInputStream(bis)) {
                        TicketInfo ti = (TicketInfo) ois.readObject();
                        for (int i=0; i < ti.getLinesCount(); i++) {
                            TicketLineInfo line = ti.getLine(i);
                            String print = line.getProperty("product.printer");
                            String send = line.getProperty("sendstatus");
                            System.out.println("  Line " + i + ": " + line.getProductName() + " | printer=" + print + " | sendstatus=" + send);
                        }
                    } catch (Exception ex) {
                        System.out.println("  [Error deserializing: " + ex.getMessage() + "]");
                    }
                }
            }
            if (!found) System.out.println("No open tickets found.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
