import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.UUID;

public class TestInsertKitchen {
    public static void main(String[] args) {
        String url = "jdbc:mysql://100.112.128.16:3306/soltec?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        String user = "admin";
        String pass = "root";

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             Statement stmt = conn.createStatement()) {

            String uuid = UUID.randomUUID().toString();
            String ticketId = "test-ticket";
            String place = "Mesa 1";
            String userName = "Admin";
            String productName = "Burger 'Doble'";
            double multiplier = 1.0;

            String sql = "INSERT INTO web_kitchen_orders (id, ticket_id, table_name, waiter_name, product_name, multiplier) " +
                         "VALUES ('" + uuid + "', '" + ticketId + "', '" + place + "', '" + userName + "', '" + productName.replace("'", "\\'") + "', " + multiplier + ")";
            
            System.out.println("Executing: " + sql);
            stmt.executeUpdate(sql);
            System.out.println("Insert successful.");

        } catch (Exception e) {
            System.err.println("Database Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
