/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.editor;

import com.openbravo.basic.BasicException;
import com.openbravo.editor.JEditorText;

public class JEditorPassword
extends JEditorText {
    private static final char ECHO_CHAR = '*';

    @Override
    protected final int getMode() {
        return 0;
    }

    @Override
    protected int getStartMode() {
        return 0;
    }

    @Override
    protected String getTextEdit() {
        StringBuilder s = new StringBuilder();
        s.append("<html>");
        s.append(this.getEcho(this.m_svalue));
        if (this.m_cLastChar != '\u0000') {
            s.append("<font color=\"#a0a0a0\">");
            s.append('*');
            s.append("</font>");
        }
        s.append("<font color=\"#a0a0a0\">_</font>");
        return s.toString();
    }

    public final String getPassword() {
        String sPassword = this.getText();
        return sPassword == null ? "" : sPassword;
    }

    @Override
    protected String getTextFormat() throws BasicException {
        return "<html>" + this.getEcho(this.m_svalue);
    }

    private String getEcho(String sValue) {
        if (sValue == null) {
            return "";
        }
        char[] c = new char[sValue.length()];
        for (int i = 0; i < sValue.length(); ++i) {
            c[i] = 42;
        }
        return new String(c);
    }
}

