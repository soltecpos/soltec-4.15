import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class CheckKitchenDB {
    public static void main(String[] args) {
        String url = "jdbc:mysql://100.112.128.16:3306/soltec?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        String user = "admin";
        String pass = "root";

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             Statement stmt = conn.createStatement()) {

            System.out.println("====== WEB KITCHEN ORDERS ====== ");
            ResultSet rs = stmt.executeQuery("SELECT * FROM web_kitchen_orders");
            int count = 0;
            while (rs.next()) {
                System.out.println("ID: " + rs.getString("id") + " | " + 
                                   "Product: " + rs.getString("product_name") + " | " +
                                   "Status: " + rs.getString("status"));
                count++;
            }
             System.out.println("Total rows: " + count);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
