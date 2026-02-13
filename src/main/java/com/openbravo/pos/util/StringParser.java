/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.util;

public class StringParser {
    private int currentPosition;
    private int maxPosition;
    private String str;

    public StringParser(String str) {
        this.str = str;
        this.currentPosition = 0;
        this.maxPosition = str == null ? 0 : str.length();
    }

    public String nextToken(char c) {
        if (this.currentPosition < this.maxPosition) {
            int start = this.currentPosition;
            while (this.currentPosition < this.maxPosition && c != this.str.charAt(this.currentPosition)) {
                ++this.currentPosition;
            }
            if (this.currentPosition < this.maxPosition) {
                return this.str.substring(start, this.currentPosition++);
            }
            return this.str.substring(start);
        }
        return "";
    }
}

