/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.gui;

import com.openbravo.data.gui.JMessageDialog;
import com.openbravo.data.loader.LocalRes;
import java.awt.Component;
import javax.swing.Icon;
import javax.swing.UIManager;

public class MessageInf {
    public static final int SGN_DANGER = -16777216;
    public static final int SGN_WARNING = -33554432;
    public static final int SGN_CAUTION = -50331648;
    public static final int SGN_NOTICE = -67108864;
    public static final int SGN_IMPORTANT = -100663296;
    public static final int SGN_SUCCESS = -83886080;
    public static final int CLS_GENERIC = 0;
    private int m_iMsgNumber;
    private String m_sHazard;
    private String m_sConsequences;
    private String m_sAvoiding;
    private Object m_eCause;

    public MessageInf(int iSignalWord, String sHazard, Object e) {
        this.m_iMsgNumber = iSignalWord;
        this.m_sHazard = sHazard;
        this.m_sConsequences = "";
        this.m_sAvoiding = "";
        this.m_eCause = e;
    }

    public MessageInf(int iSignalWord, String sHazard) {
        this(iSignalWord, sHazard, null);
    }

    public MessageInf(Throwable e) {
        this(-33554432, e.getLocalizedMessage(), e);
    }

    public void show(Component parent) {
        JMessageDialog.showMessage(parent, this);
    }

    public Object getCause() {
        return this.m_eCause;
    }

    public int getSignalWord() {
        return this.m_iMsgNumber & 0xFF000000;
    }

    public Icon getSignalWordIcon() {
        int iSignalWord = this.getSignalWord();
        if (iSignalWord == -16777216) {
            return UIManager.getIcon("OptionPane.errorIcon");
        }
        if (iSignalWord == -33554432) {
            return UIManager.getIcon("OptionPane.warningIcon");
        }
        if (iSignalWord == -50331648) {
            return UIManager.getIcon("OptionPane.warningIcon");
        }
        if (iSignalWord == -67108864) {
            return UIManager.getIcon("OptionPane.informationIcon");
        }
        if (iSignalWord == -100663296) {
            return UIManager.getIcon("OptionPane.informationIcon");
        }
        if (iSignalWord == -83886080) {
            return UIManager.getIcon("OptionPane.informationIcon");
        }
        return UIManager.getIcon("OptionPane.questionIcon");
    }

    public String getErrorCodeMsg() {
        StringBuilder sb = new StringBuilder();
        int iSignalWord = this.getSignalWord();
        if (iSignalWord == -16777216) {
            sb.append("DNG_");
        } else if (iSignalWord == -33554432) {
            sb.append("WRN_");
        } else if (iSignalWord == -50331648) {
            sb.append("CAU_");
        } else if (iSignalWord == -67108864) {
            sb.append("NOT_");
        } else if (iSignalWord == -100663296) {
            sb.append("IMP_");
        } else if (iSignalWord == -83886080) {
            sb.append("INF_");
        } else {
            sb.append("UNK_");
        }
        sb.append(this.toHex((this.m_iMsgNumber & 0xFF0000) >> 16, 2));
        sb.append('_');
        sb.append(this.toHex(this.m_iMsgNumber & 0xFFFF, 4));
        return sb.toString();
    }

    private String toHex(int i, int iChars) {
        String s = Integer.toHexString(i);
        return s.length() >= iChars ? s : this.fillString(iChars - s.length()) + s;
    }

    private String fillString(int iChars) {
        char[] aStr = new char[iChars];
        for (int i = 0; i < aStr.length; ++i) {
            aStr[i] = 48;
        }
        return new String(aStr);
    }

    public String getMessageMsg() {
        StringBuilder sb = new StringBuilder();
        int iSignalWord = this.getSignalWord();
        if (iSignalWord == -16777216) {
            sb.append(LocalRes.getIntString("sgn.danger"));
        } else if (iSignalWord == -33554432) {
            sb.append(LocalRes.getIntString("sgn.warning"));
        } else if (iSignalWord == -50331648) {
            sb.append(LocalRes.getIntString("sgn.caution"));
        } else if (iSignalWord == -67108864) {
            sb.append(LocalRes.getIntString("sgn.notice"));
        } else if (iSignalWord == -100663296) {
            sb.append(LocalRes.getIntString("sgn.important"));
        } else if (iSignalWord == -83886080) {
            sb.append(LocalRes.getIntString("sgn.success"));
        } else {
            sb.append(LocalRes.getIntString("sgn.unknown"));
        }
        sb.append(this.m_sHazard);
        sb.append(this.m_sConsequences);
        sb.append(this.m_sAvoiding);
        return sb.toString();
    }
}

