/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.basic;

public class BasicException
extends Exception {
    private static final long serialVersionUID = 1L;

    public BasicException() {
    }

    public BasicException(String msg) {
        super(msg);
    }

    public BasicException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public BasicException(Throwable cause) {
        super(cause);
    }
}

