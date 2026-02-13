/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.util;

import com.openbravo.beans.JPasswordDialog;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.util.StringUtils;
import java.awt.Component;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class Hashcypher {
    public static boolean authenticate(String sPassword, String sHashPassword) {
        if (sHashPassword == null || sHashPassword.equals("") || sHashPassword.startsWith("empty:")) {
            return sPassword == null || sPassword.equals("");
        }
        if (sHashPassword.startsWith("sha1:")) {
            return sHashPassword.equals(Hashcypher.hashString(sPassword));
        }
        if (sHashPassword.startsWith("plain:")) {
            return sHashPassword.equals("plain:" + sPassword);
        }
        return sHashPassword.equals(sPassword);
    }

    public static String hashString(String sPassword) {
        if (sPassword == null || sPassword.equals("")) {
            return "empty:";
        }
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(sPassword.getBytes("UTF-8"));
            byte[] res = md.digest();
            return "sha1:" + StringUtils.byte2hex(res);
        }
        catch (NoSuchAlgorithmException e) {
            return "plain:" + sPassword;
        }
        catch (UnsupportedEncodingException e) {
            return "plain:" + sPassword;
        }
    }

    public static String changePassword(Component parent) {
        String sPassword2;
        String sPassword = JPasswordDialog.showEditPassword(parent, AppLocal.getIntString("label.Password"), AppLocal.getIntString("label.passwordnew"), new ImageIcon(Hashcypher.class.getResource("/com/openbravo/images/password.png")));
        if (sPassword != null && (sPassword2 = JPasswordDialog.showEditPassword(parent, AppLocal.getIntString("label.Password"), AppLocal.getIntString("label.passwordrepeat"), new ImageIcon(Hashcypher.class.getResource("/com/openbravo/images/password.png")))) != null) {
            if (sPassword.equals(sPassword2)) {
                return Hashcypher.hashString(sPassword);
            }
            JOptionPane.showMessageDialog(parent, AppLocal.getIntString("message.changepassworddistinct"), AppLocal.getIntString("message.title"), 2);
        }
        return null;
    }

    public static String changePassword(Component parent, String sOldPassword) {
        String sPassword = JPasswordDialog.showEditPassword(parent, AppLocal.getIntString("label.Password"), AppLocal.getIntString("label.passwordold"), new ImageIcon(Hashcypher.class.getResource("/com/openbravo/images/password.png")));
        if (sPassword != null) {
            if (Hashcypher.authenticate(sPassword, sOldPassword)) {
                return Hashcypher.changePassword(parent);
            }
            JOptionPane.showMessageDialog(parent, AppLocal.getIntString("message.BadPassword"), AppLocal.getIntString("message.title"), 2);
        }
        return null;
    }
}

