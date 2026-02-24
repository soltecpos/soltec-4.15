/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.gui;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.Vectorer;
import com.openbravo.data.user.Finder;
import java.util.regex.Pattern;

public class FindInfo
implements Finder {
    public static final int MATCH_STARTFIELD = 0;
    public static final int MATCH_WHOLEFIELD = 1;
    public static final int MATCH_ANYPARTFIELD = 2;
    public static final int MATCH_REGEXP = 3;
    private String m_sTextCompare;
    private Pattern m_TextPattern;
    private String m_sText;
    private int m_iField;
    private int m_iMatch;
    private boolean m_bMatchCase;
    private Vectorer m_vec;

    public FindInfo(Vectorer vec, String sText, int iField, boolean bMatchCase, int iMatch) {
        this.m_vec = vec;
        this.m_sText = sText;
        this.m_iField = iField;
        this.m_bMatchCase = bMatchCase;
        this.m_iMatch = iMatch;
        if (iMatch == 3) {
            this.m_TextPattern = this.m_bMatchCase ? Pattern.compile(this.m_sText) : Pattern.compile(this.m_sText, 2);
        } else {
            this.m_sTextCompare = this.m_bMatchCase ? this.m_sText : this.m_sText.toUpperCase();
        }
    }

    public FindInfo(Vectorer vec) {
        this(vec, "", 0, true, 2);
    }

    public Vectorer getVectorer() {
        return this.m_vec;
    }

    public String getText() {
        return this.m_sText;
    }

    public int getField() {
        return this.m_iField;
    }

    public boolean isMatchCase() {
        return this.m_bMatchCase;
    }

    public int getMatch() {
        return this.m_iMatch;
    }

    @Override
    public boolean match(Object obj) throws BasicException {
        String[] v = this.m_vec.getValues(obj);
        String sField = this.m_bMatchCase ? v[this.m_iField] : v[this.m_iField].toUpperCase();
        switch (this.m_iMatch) {
            case 0: {
                return sField.startsWith(this.m_sTextCompare);
            }
            case 1: {
                return sField.equals(this.m_sTextCompare);
            }
            case 2: {
                return sField.contains(this.m_sTextCompare);
            }
            case 3: {
                return this.m_TextPattern.matcher(sField).matches();
            }
        }
        return false;
    }
}

