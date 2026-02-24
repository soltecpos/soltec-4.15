/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.util;

import com.openbravo.pos.util.StringUtils;
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

public class AltEncrypter {
    private Cipher cipherDecrypt;
    private Cipher cipherEncrypt;

    public AltEncrypter(String passPhrase) {
        try {
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
            sr.setSeed(passPhrase.getBytes("UTF8"));
            KeyGenerator kGen = KeyGenerator.getInstance("DESEDE");
            kGen.init(168, sr);
            SecretKey key = kGen.generateKey();
            this.cipherEncrypt = Cipher.getInstance("DESEDE/ECB/PKCS5Padding");
            this.cipherEncrypt.init(1, key);
            this.cipherDecrypt = Cipher.getInstance("DESEDE/ECB/PKCS5Padding");
            this.cipherDecrypt.init(2, key);
        }
        catch (UnsupportedEncodingException | InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException exception) {
            // empty catch block
        }
    }

    public String encrypt(String str) {
        try {
            return StringUtils.byte2hex(this.cipherEncrypt.doFinal(str.getBytes("UTF8")));
        }
        catch (UnsupportedEncodingException | BadPaddingException | IllegalBlockSizeException exception) {
            return null;
        }
    }

    public String decrypt(String str) {
        try {
            return new String(this.cipherDecrypt.doFinal(StringUtils.hex2byte(str)), "UTF8");
        }
        catch (UnsupportedEncodingException | BadPaddingException | IllegalBlockSizeException exception) {
            return null;
        }
    }
}

