import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class CheckResourceDB {
    public static void main(String[] args) {
        String url = "jdbc:mysql://100.112.128.16:3306/soltec?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        String user = "admin";
        String pass = "root";

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             Statement stmt = conn.createStatement()) {

            System.out.println("====== DB SCRIPT.SENDORDER ====== ");
            ResultSet rs = stmt.executeQuery("SELECT content FROM resources WHERE name = 'script.SendOrder'");
            if (rs.next()) {
                byte[] bytes = rs.getBytes("content");
                if (bytes != null) {
                    String content = new String(bytes, "UTF-8");
                    System.out.println(content);
                } else {
                    System.out.println("Content is null");
                }
            } else {
                System.out.println("Resource not found");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
