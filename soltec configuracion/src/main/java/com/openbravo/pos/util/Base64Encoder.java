/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.codec.binary.Base64
 */
package com.openbravo.pos.util;

import java.io.UnsupportedEncodingException;
import org.apache.commons.codec.binary.Base64;

public class Base64Encoder {
    public static byte[] decode(String base64) {
        try {
            return Base64.decodeBase64((byte[])base64.getBytes("ASCII"));
        }
        catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    public static String encode(byte[] raw) {
        try {
            return new String(Base64.encodeBase64((byte[])raw), "ASCII");
        }
        catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    public static String encodeChunked(byte[] raw) {
        try {
            return new String(Base64.encodeBase64Chunked((byte[])raw), "ASCII");
        }
        catch (UnsupportedEncodingException e) {
            return null;
        }
    }
}

