import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class StandaloneDecrypt {
    public static void main(String[] args) {
        try {
            String encrypted = "56FC6C93B442722A";
            System.out.println("DECRYPTED: " + decrypt(encrypted, "cypherkey"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
            byArray[i / 2] = (byte)Integer.parseInt(string.substring(i, i + 2), 16);
        }
        return byArray;
    }
}
