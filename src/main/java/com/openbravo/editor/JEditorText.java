/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.editor;

import com.openbravo.basic.BasicException;
import com.openbravo.editor.JEditorAbstract;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public abstract class JEditorText
extends JEditorAbstract {
    private static final long serialVersionUID = 1L;
    protected String m_svalue = null;
    public static final int MODE_Abc1 = 0;
    public static final int MODE_abc1 = 1;
    public static final int MODE_ABC1 = 2;
    public static final int MODE_123 = 3;
    public int m_iMode;
    protected int m_iTicks = 0;
    protected char m_cLastChar = '\u0000';
    protected long m_lcount = 0L;
    private Timer m_jtimer = new Timer(1000, new TimerAction());
    private static final char[] CHAR_abc1_1 = new char[]{'.', '?', '!', ',', '1', ';', ':'};
    private static final char[] CHAR_abc1_2 = new char[]{'a', 'b', 'c', '2', '\u00a0'};
    private static final char[] CHAR_abc1_3 = new char[]{'d', 'e', 'f', '3', '\u201a'};
    private static final char[] CHAR_abc1_4 = new char[]{'g', 'h', 'i', '4', '\u00a1'};
    private static final char[] CHAR_abc1_5 = new char[]{'j', 'k', 'l', '5'};
    private static final char[] CHAR_abc1_6 = new char[]{'m', 'n', 'o', '6', '\u00a4', '\u00a2'};
    private static final char[] CHAR_abc1_7 = new char[]{'p', 'q', 'r', 's', '7'};
    private static final char[] CHAR_abc1_8 = new char[]{'t', 'u', 'v', '8', '\u00a3', '\ufffd'};
    private static final char[] CHAR_abc1_9 = new char[]{'w', 'x', 'y', 'z', '9'};
    private static final char[] CHAR_abc1_0 = new char[]{' ', '0'};
    private static final char[] CHAR_ABC1_1 = new char[]{'.', '?', '!', ',', '1', ';', ':'};
    private static final char[] CHAR_ABC1_2 = new char[]{'A', 'B', 'C', '2', '\u00b5'};
    private static final char[] CHAR_ABC1_3 = new char[]{'D', 'E', 'F', '3', '\u00c9'};
    private static final char[] CHAR_ABC1_4 = new char[]{'G', 'H', 'I', '4', '\u00cd'};
    private static final char[] CHAR_ABC1_5 = new char[]{'J', 'K', 'L', '5'};
    private static final char[] CHAR_ABC1_6 = new char[]{'M', 'N', 'O', '6', '\u00d1', '\u00d3'};
    private static final char[] CHAR_ABC1_7 = new char[]{'P', 'Q', 'R', 'S', '7'};
    private static final char[] CHAR_ABC1_8 = new char[]{'T', 'U', 'V', '8', '\u00da', '\u00dc'};
    private static final char[] CHAR_ABC1_9 = new char[]{'W', 'X', 'Y', 'Z', '9'};
    private static final char[] CHAR_ABC1_0 = new char[]{' ', '0'};

    public JEditorText() {
        this.m_iMode = this.getStartMode();
        this.m_jtimer.start();
    }

    protected abstract int getStartMode();

    public final void reset() {
        String sOldText = this.getText();
        this.m_iMode = this.getStartMode();
        this.m_svalue = null;
        this.m_iTicks = 0;
        this.m_cLastChar = '\u0000';
        this.reprintText();
        this.firePropertyChange("Text", sOldText, this.getText());
    }

    public final void setText(String sText) {
        String sOldText = this.getText();
        this.m_svalue = sText;
        this.m_iTicks = 0;
        this.m_cLastChar = '\u0000';
        this.reprintText();
        this.firePropertyChange("Text", sOldText, this.getText());
    }

    public final void setEditModeEnum(int iMode) {
        this.m_iMode = iMode;
        this.m_iTicks = 0;
        this.m_cLastChar = '\u0000';
        this.reprintText();
    }

    public final String getText() {
        if (this.m_cLastChar == '\u0000') {
            return this.m_svalue;
        }
        return this.appendChar2Value(this.getKeyChar());
    }

    @Override
    protected final int getAlignment() {
        return 2;
    }

    @Override
    protected final String getEditMode() {
        switch (this.m_iMode) {
            case 0: {
                return "Abc1";
            }
            case 1: {
                return "abc1";
            }
            case 2: {
                return "ABC1";
            }
            case 3: {
                return "123";
            }
        }
        return null;
    }

    @Override
    protected String getTextEdit() {
        StringBuilder s = new StringBuilder();
        s.append("<html>");
        if (this.m_svalue != null) {
            s.append(this.m_svalue);
        }
        if (this.m_cLastChar != '\u0000') {
            s.append("<font size=+1><font color=\"#a0a0a0\">");
            s.append(this.getKeyChar());
            s.append("</font>");
        }
        s.append("<font size=+1><font color=\"#a0a0a0\">_</font>");
        return s.toString();
    }

    @Override
    protected String getTextFormat() throws BasicException {
        return this.m_svalue == null ? "<html>" : "<html>" + this.m_svalue;
    }

    @Override
    protected void typeCharInternal(char c) {
        String sOldText = this.getText();
        if (c == '\b') {
            if (this.m_cLastChar == '\u0000') {
                if (this.m_svalue != null && this.m_svalue.length() > 0) {
                    this.m_svalue = this.m_svalue.substring(0, this.m_svalue.length() - 1);
                }
            } else {
                this.m_iTicks = 0;
                this.m_cLastChar = '\u0000';
            }
        } else if (c == '\u007f') {
            this.m_iMode = this.getStartMode();
            this.m_svalue = null;
            this.m_iTicks = 0;
            this.m_cLastChar = '\u0000';
        } else if (c >= ' ') {
            if (this.m_cLastChar != '\u0000') {
                char ckey = this.getKeyChar();
                this.m_svalue = this.appendChar2Value(ckey);
                this.acceptKeyChar(ckey);
            }
            this.m_iTicks = 0;
            this.m_cLastChar = '\u0000';
            this.m_svalue = this.appendChar2Value(c);
        }
        this.m_jtimer.restart();
        this.firePropertyChange("Text", sOldText, this.getText());
    }

    @Override
    protected void transCharInternal(char c) {
        String sOldText = this.getText();
        if (c == '-') {
            if (this.m_cLastChar == '\u0000') {
                if (this.m_svalue != null && this.m_svalue.length() > 0) {
                    this.m_svalue = this.m_svalue.substring(0, this.m_svalue.length() - 1);
                }
            } else {
                this.m_iTicks = 0;
                this.m_cLastChar = '\u0000';
            }
        } else if (c == '\u007f') {
            this.m_iMode = this.getStartMode();
            this.m_svalue = null;
            this.m_iTicks = 0;
            this.m_cLastChar = '\u0000';
        } else if (c == '.') {
            if (this.m_cLastChar != '\u0000') {
                this.m_svalue = this.appendChar2Value(this.getKeyChar());
            }
            this.m_iTicks = 0;
            this.m_cLastChar = '\u0000';
            this.m_iMode = (this.m_iMode + 1) % 4;
        } else if (c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9' || c == '0') {
            if (this.m_iMode == 3) {
                this.m_svalue = this.appendChar2Value(c);
            } else if (c == this.m_cLastChar) {
                ++this.m_iTicks;
            } else {
                if (this.m_cLastChar != '\u0000') {
                    char ckey = this.getKeyChar();
                    this.m_svalue = this.appendChar2Value(ckey);
                    this.acceptKeyChar(ckey);
                }
                this.m_iTicks = 0;
                this.m_cLastChar = c;
            }
        }
        this.m_jtimer.restart();
        this.firePropertyChange("Text", sOldText, this.getText());
    }

    private void acceptKeyChar(char c) {
        if (this.m_iMode == 0 && c != ' ') {
            this.m_iMode = 1;
        } else if (this.m_iMode == 1 && c == '.') {
            this.m_iMode = 0;
        }
    }

    protected char getKeyChar() {
        char[] clist = null;
        block0 : switch (this.m_iMode) {
            case 1: {
                switch (this.m_cLastChar) {
                    case '1': {
                        clist = CHAR_abc1_1;
                        break;
                    }
                    case '2': {
                        clist = CHAR_abc1_2;
                        break;
                    }
                    case '3': {
                        clist = CHAR_abc1_3;
                        break;
                    }
                    case '4': {
                        clist = CHAR_abc1_4;
                        break;
                    }
                    case '5': {
                        clist = CHAR_abc1_5;
                        break;
                    }
                    case '6': {
                        clist = CHAR_abc1_6;
                        break;
                    }
                    case '7': {
                        clist = CHAR_abc1_7;
                        break;
                    }
                    case '8': {
                        clist = CHAR_abc1_8;
                        break;
                    }
                    case '9': {
                        clist = CHAR_abc1_9;
                        break;
                    }
                    case '0': {
                        clist = CHAR_abc1_0;
                    }
                }
                break;
            }
            case 0: 
            case 2: {
                switch (this.m_cLastChar) {
                    case '1': {
                        clist = CHAR_ABC1_1;
                        break block0;
                    }
                    case '2': {
                        clist = CHAR_ABC1_2;
                        break block0;
                    }
                    case '3': {
                        clist = CHAR_ABC1_3;
                        break block0;
                    }
                    case '4': {
                        clist = CHAR_ABC1_4;
                        break block0;
                    }
                    case '5': {
                        clist = CHAR_ABC1_5;
                        break block0;
                    }
                    case '6': {
                        clist = CHAR_ABC1_6;
                        break block0;
                    }
                    case '7': {
                        clist = CHAR_ABC1_7;
                        break block0;
                    }
                    case '8': {
                        clist = CHAR_ABC1_8;
                        break block0;
                    }
                    case '9': {
                        clist = CHAR_ABC1_9;
                        break block0;
                    }
                    case '0': {
                        clist = CHAR_ABC1_0;
                    }
                }
            }
        }
        if (clist == null) {
            return this.m_cLastChar;
        }
        return clist[this.m_iTicks % clist.length];
    }

    private String appendChar2Value(char c) {
        StringBuilder s = new StringBuilder();
        if (this.m_svalue != null) {
            s.append(this.m_svalue);
        }
        s.append(c);
        return s.toString();
    }

    private class TimerAction
    implements ActionListener {
        private TimerAction() {
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            if (JEditorText.this.m_cLastChar != '\u0000') {
                char ckey = JEditorText.this.getKeyChar();
                JEditorText.this.m_svalue = JEditorText.this.appendChar2Value(ckey);
                JEditorText.this.acceptKeyChar(ckey);
                JEditorText.this.m_iTicks = 0;
                JEditorText.this.m_cLastChar = '\u0000';
                JEditorText.this.m_jtimer.restart();
                JEditorText.this.reprintText();
            }
        }
    }
}

