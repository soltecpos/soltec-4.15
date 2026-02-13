/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.licensing;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.security.MessageDigest;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class LicenseManager {
    private static final Logger logger = Logger.getLogger("com.openbravo.pos.licensing.LicenseManager");
    private static final String MASTER_SECRET = "SOLTEC_POS_SECURE_LICENSE_KEY_2025_XYZ";

    public static String getMachineID() {
        try {
            StringBuilder sb = new StringBuilder();
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface ni = interfaces.nextElement();
                byte[] hardwareAddress = ni.getHardwareAddress();
                if (hardwareAddress == null || hardwareAddress.length <= 0 || ni.isLoopback()) continue;
                for (byte b : hardwareAddress) {
                    sb.append(String.format("%02X", b));
                }
            }
            try {
                sb.append(InetAddress.getLocalHost().getHostName());
            }
            catch (Exception exception) {
                // empty catch block
            }
            if (sb.length() == 0) {
                return "NO-NET-ID-" + System.getProperty("user.name");
            }
            return LicenseManager.hashSHA256(sb.toString()).substring(0, 16).toUpperCase();
        }
        catch (Exception e) {
            logger.log(Level.SEVERE, "Error generating Machine ID", e);
            return "ERROR-ID";
        }
    }

    public static String generateLicenseKey(String machineID) {
        try {
            return LicenseManager.hmacSHA256(machineID, MASTER_SECRET).toUpperCase();
        }
        catch (Exception e) {
            logger.log(Level.SEVERE, "Error generating license", e);
            return null;
        }
    }

    public static boolean isValid(String enteredKey) {
        String machineID = LicenseManager.getMachineID();
        String expectedKey = LicenseManager.generateLicenseKey(machineID);
        return expectedKey != null && expectedKey.equals(enteredKey);
    }

    private static String hashSHA256(String data) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(data.getBytes("UTF-8"));
        return LicenseManager.bytesToHex(hash);
    }

    private static String hmacSHA256(String data, String key) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(secretKeySpec);
        byte[] hmacData = mac.doFinal(data.getBytes("UTF-8"));
        return LicenseManager.bytesToHex(hmacData);
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02X", b));
        }
        return result.toString();
    }
}

