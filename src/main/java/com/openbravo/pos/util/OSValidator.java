/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.util;

public class OSValidator {
    private String OS = System.getProperty("os.name").toLowerCase();

    public String getOS() {
        if (this.isWindows()) {
            return "w";
        }
        if (this.isMac()) {
            return "m";
        }
        if (this.isUnix()) {
            return "l";
        }
        if (this.isSolaris()) {
            return "s";
        }
        return "x";
    }

    public boolean isWindows() {
        return this.OS.contains("win");
    }

    public boolean isMac() {
        return this.OS.contains("mac");
    }

    public boolean isUnix() {
        return this.OS.contains("nix") || this.OS.contains("nux") || this.OS.indexOf("aix") > 0;
    }

    public boolean isSolaris() {
        return this.OS.contains("sunos");
    }
}

