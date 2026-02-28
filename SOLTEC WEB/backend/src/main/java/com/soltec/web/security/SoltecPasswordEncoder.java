package com.soltec.web.security;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class SoltecPasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        if (rawPassword == null || rawPassword.length() == 0) {
            return "empty:";
        }
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(rawPassword.toString().getBytes("UTF-8"));
            byte[] res = md.digest();
            return "sha1:" + byte2hex(res);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            return "plain:" + rawPassword;
        }
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        if (encodedPassword == null || encodedPassword.isEmpty() || encodedPassword.startsWith("empty:")) {
            return rawPassword == null || rawPassword.length() == 0;
        }
        if (encodedPassword.startsWith("sha1:")) {
            return encodedPassword.equals(encode(rawPassword));
        }
        if (encodedPassword.startsWith("plain:")) {
            return encodedPassword.equals("plain:" + rawPassword);
        }
        return encodedPassword.equals(rawPassword.toString());
    }

    private String byte2hex(byte[] binput) {
        char[] hexchars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        StringBuilder sb = new StringBuilder(binput.length * 2);
        for (byte b : binput) {
            int high = (b & 0xF0) >> 4;
            int low = b & 0xF;
            sb.append(hexchars[high]);
            sb.append(hexchars[low]);
        }
        return sb.toString();
    }
}
