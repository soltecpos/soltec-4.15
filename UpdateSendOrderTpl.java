import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.nio.file.Files;
import java.nio.file.Paths;

public class UpdateSendOrderTpl {
    public static void main(String[] args) {
        String url = "jdbc:mysql://100.112.128.16:3306/soltec?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        String user = "admin";
        String pass = "root";

        try {
            String content = new String(Files.readAllBytes(Paths.get("C:\\Users\\aux1\\Documents\\loquedejolatormenta\\soltec configuracion\\src\\main\\resources\\com\\openbravo\\pos\\templates\\script.SendOrder.txt")));
            byte[] bytes = content.getBytes("UTF-8");

            try (Connection conn = DriverManager.getConnection(url, user, pass);
                 PreparedStatement pstmt = conn.prepareStatement("UPDATE resources SET content = ? WHERE name = 'script.SendOrder'")) {
                
                pstmt.setBytes(1, bytes);
                int updated = pstmt.executeUpdate();
                System.out.println("Resource script.SendOrder updated: " + updated + " rows.");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
