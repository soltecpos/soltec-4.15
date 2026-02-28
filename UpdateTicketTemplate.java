import java.sql.*;
import java.io.*;
import java.util.Properties;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class UpdateTicketTemplate {
    
    public static String decrypt(String str, String passPhrase) throws Exception {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        sr.setSeed(passPhrase.getBytes("UTF8"));
        KeyGenerator kGen = KeyGenerator.getInstance("DESEDE");
        kGen.init(168, sr);
        SecretKey key = kGen.generateKey();
        Cipher cipherDecrypt = Cipher.getInstance("DESEDE/ECB/PKCS5Padding");
        cipherDecrypt.init(Cipher.DECRYPT_MODE, key);
        return new String(cipherDecrypt.doFinal(hex2byte(str)), "UTF8");
    }
    
    public static byte[] hex2byte(String string) {
        if (string == null) return null;
        int n = string.length();
        if (n % 2 == 1) return null;
        byte[] byArray = new byte[n / 2];
        for (int i = 0; i < n; i += 2) {
            byArray[i / 2] = (byte) Integer.parseInt(string.substring(i, i + 2), 16);
        }
        return byArray;
    }
    
    public static void main(String[] args) throws Exception {
        String password = "admin";
        String url = "jdbc:mysql://localhost:3306/soltec";
        String user = "root";
        
        File propsFile = new File(System.getProperty("user.home"), "soltec.properties");
        if (propsFile.exists()) {
            Properties props = new Properties();
            props.load(new FileInputStream(propsFile));
            String dbUrl = props.getProperty("db.URL");
            if (dbUrl != null) url = dbUrl;
            String dbUser = props.getProperty("db.user");
            if (dbUser != null) user = dbUser;
            String dbPass = props.getProperty("db.password");
            if (dbPass != null) {
                if (dbPass.startsWith("crypt:")) {
                    String encKey = "cypherkey" + user;
                    password = decrypt(dbPass.substring(6), encKey);
                } else {
                    password = dbPass;
                }
            }
            System.out.println("DB URL: " + url);
            System.out.println("DB User: " + user);
        }

        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection(url, user, password);
        System.out.println("Connected OK!");
        
        PreparedStatement ps = conn.prepareStatement("SELECT CONTENT FROM resources WHERE NAME = 'Printer.Ticket'");
        ResultSet rs = ps.executeQuery();
        
        if (!rs.next()) {
            System.out.println("ERROR: Printer.Ticket not found!");
            conn.close();
            return;
        }
        
        byte[] contentBytes = rs.getBytes("CONTENT");
        String content = new String(contentBytes, "UTF-8");
        rs.close();
        ps.close();
        
        if (content.contains("printTicketHeaderLine1")) {
            System.out.println("Template already uses dynamic headers.");
            conn.close();
            return;
        }

        String dynamicHeaders = 
            "\t#if (${ticket.printTicketHeaderLine1()} != \"\")\n" +
            "\t<line>\n\t\t<text align=\"center\" length=\"42\">${ticket.printTicketHeaderLine1()}</text>\n\t</line>\n" +
            "\t#end\n" +
            "\t#if (${ticket.printTicketHeaderLine2()} != \"\")\n" +
            "\t<line>\n\t\t<text align=\"center\" length=\"42\">${ticket.printTicketHeaderLine2()}</text>\n\t</line>\n" +
            "\t#end\n" +
            "\t#if (${ticket.printTicketHeaderLine3()} != \"\")\n" +
            "\t<line>\n\t\t<text align=\"center\" length=\"42\">${ticket.printTicketHeaderLine3()}</text>\n\t</line>\n" +
            "\t#end\n" +
            "\t#if (${ticket.printTicketHeaderLine4()} != \"\")\n" +
            "\t<line>\n\t\t<text align=\"center\" length=\"42\">${ticket.printTicketHeaderLine4()}</text>\n\t</line>\n" +
            "\t#end\n" +
            "\t#if (${ticket.printTicketHeaderLine5()} != \"\")\n" +
            "\t<line>\n\t\t<text align=\"center\" length=\"42\">${ticket.printTicketHeaderLine5()}</text>\n\t</line>\n" +
            "\t#end\n" +
            "\t#if (${ticket.printTicketHeaderLine6()} != \"\")\n" +
            "\t<line>\n\t\t<text align=\"center\" length=\"42\">${ticket.printTicketHeaderLine6()}</text>\n\t</line>\n" +
            "\t#end\n";

        // Find the header block between <image>...</image> and ===
        int imgEnd = content.indexOf("</image>");
        int sepIdx = content.indexOf("==========================================");
        
        if (imgEnd < 0 || sepIdx < 0) {
            System.out.println("ERROR: Cannot find image/separator markers in template");
            conn.close();
            return;
        }
        
        // Find the empty <line></line> after image
        int emptyLine = content.indexOf("<line></line>", imgEnd);
        int afterEmptyLine = content.indexOf("\n", emptyLine) + 1;
        
        // Find the <line> that starts the === separator
        // Go backwards from sepIdx to find the start of that <line> tag
        int sepLineStart = content.lastIndexOf("<line>", sepIdx);
        // Also check for whitespace-prefixed version
        int sepLineWithTab = content.lastIndexOf("\t<line>", sepIdx);
        if (sepLineWithTab > sepLineStart) sepLineStart = sepLineWithTab;
        
        // Show what will be replaced
        String oldSection = content.substring(afterEmptyLine, sepLineStart);
        System.out.println("=== REPLACING THIS ===");
        System.out.println(oldSection.trim());
        System.out.println("=== WITH DYNAMIC HEADERS ===");
        
        content = content.substring(0, afterEmptyLine) + dynamicHeaders + content.substring(sepLineStart);
        
        byte[] newBytes = content.getBytes("UTF-8");
        PreparedStatement updatePs = conn.prepareStatement("UPDATE resources SET CONTENT = ? WHERE NAME = 'Printer.Ticket'");
        updatePs.setBytes(1, newBytes);
        int updated = updatePs.executeUpdate();
        updatePs.close();
        
        System.out.println("Updated " + updated + " row(s). Restart Soltec POS!");
        conn.close();
    }
}
