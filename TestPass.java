import java.sql.Connection;
import java.sql.DriverManager;

public class TestPass {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/soltec?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        String[] users = {"admin", "root"};
        String[] passes = {"admin", "root", "soltec", "123456", "password", "", "1234"};

        for (String user : users) {
            for (String pass : passes) {
                try {
                    System.out.println("Trying " + user + " / " + pass);
                    Connection conn = DriverManager.getConnection(url, user, pass);
                    System.out.println("SUCCESS! User: " + user + " Pass: " + pass);
                    conn.close();
                    return;
                } catch (Exception e) {
                    // System.out.println("Failed: " + e.getMessage());
                }
            }
        }
        System.out.println("No matching password found.");
    }
}
