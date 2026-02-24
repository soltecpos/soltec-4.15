package test;

import com.openbravo.pos.util.AltEncrypter;

public class DecryptPass {
    public static void main(String[] args) {
        AltEncrypter cypher = new AltEncrypter("cypherkey");
        String encrypted = "56FC6C93B442722A";
        String decrypted = cypher.decrypt(encrypted);
        System.out.println("DECRYPTED PASSWORD IS: " + decrypted);
    }
}
