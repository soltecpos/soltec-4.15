import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class CreateTable {
    public static void main(String[] args) {
        String url = "jdbc:mysql://100.112.128.16:3306/soltec?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        String user = "admin";
        String pass = "root";

        String createTableSQL = "CREATE TABLE IF NOT EXISTS web_kitchen_orders (" +
                "id VARCHAR(255) PRIMARY KEY, " +
                "ticket_id VARCHAR(255) NOT NULL, " +
                "table_name VARCHAR(255), " +
                "waiter_name VARCHAR(255), " +
                "product_name VARCHAR(255) NOT NULL, " +
                "multiplier DOUBLE NOT NULL, " +
                "status VARCHAR(50) DEFAULT 'PENDING' NOT NULL, " +
                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ")";

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             Statement stmt = conn.createStatement()) {
            
            stmt.executeUpdate(createTableSQL);
            System.out.println("Table web_kitchen_orders created successfully.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
