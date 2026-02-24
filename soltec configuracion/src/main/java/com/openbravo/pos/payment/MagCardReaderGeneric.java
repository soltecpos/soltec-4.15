/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.payment;

import com.openbravo.pos.payment.MagCardReader;
import com.openbravo.pos.util.LuhnAlgorithm;
import com.openbravo.pos.util.StringUtils;
import java.util.ArrayList;
import java.util.List;

public class MagCardReaderGeneric
implements MagCardReader {
    private String m_sHolderName;
    private String m_sCardNumber;
    private String m_sExpirationDate;
    private StringBuffer track1;
    private StringBuffer track2;
    private StringBuffer track3;
    private static final int READING_STARTSENTINEL1 = 0;
    private static final int READING_STARTSENTINEL2 = 1;
    private static final int READING_STARTSENTINEL3 = 2;
    private static final int READING_CARDTYPE = 3;
    private static final int READING_TRACK1 = 4;
    private static final int READING_TRACK2 = 5;
    private static final int READING_TRACK3 = 6;
    private static final int READING_END = 7;
    private int m_iAutomState;
    private List m_aTrack1;
    private List m_aTrack2;
    private List m_aTrack3;
    private StringBuffer m_sField;
    private char m_cCardType;
    private String m_encryptedCardData;
    private String m_encryptionKey;

    public MagCardReaderGeneric() {
        this.reset();
    }

    @Override
    public String getReaderName() {
        return "Generic magnetic card reader";
    }

    @Override
    public void reset() {
        this.m_aTrack1 = null;
        this.m_aTrack2 = null;
        this.m_aTrack3 = null;
        this.m_sField = null;
        this.m_cCardType = (char)32;
        this.m_sHolderName = null;
        this.m_sCardNumber = null;
        this.m_sExpirationDate = null;
        this.m_encryptedCardData = null;
        this.m_encryptionKey = null;
        this.m_iAutomState = 0;
    }

    @Override
    public void appendChar(char c) {
        if (c == '%') {
            this.track1 = new StringBuffer();
            this.track2 = new StringBuffer();
            this.track3 = new StringBuffer();
            this.m_aTrack1 = new ArrayList();
            this.m_aTrack2 = null;
            this.m_aTrack3 = null;
            this.m_sField = new StringBuffer();
            this.m_cCardType = (char)32;
            this.m_sHolderName = null;
            this.m_sCardNumber = null;
            this.m_sExpirationDate = null;
            this.m_iAutomState = 3;
        } else if (this.m_iAutomState == 3) {
            this.m_cCardType = c;
            this.m_iAutomState = 4;
        } else if (c == ';' && this.m_iAutomState == 1) {
            this.m_aTrack2 = new ArrayList();
            this.m_sField = new StringBuffer();
            this.m_iAutomState = 5;
        } else if (c == ';' && this.m_iAutomState == 2) {
            this.m_aTrack3 = new ArrayList();
            this.m_sField = new StringBuffer();
            this.m_iAutomState = 6;
        } else if (c == '^' && this.m_iAutomState == 4) {
            this.m_aTrack1.add(this.m_sField.toString());
            this.m_sField = new StringBuffer();
        } else if (c == '=' && this.m_iAutomState == 5) {
            this.m_aTrack2.add(this.m_sField.toString());
            this.m_sField = new StringBuffer();
        } else if (c == '=' && this.m_iAutomState == 6) {
            this.m_aTrack3.add(this.m_sField.toString());
            this.m_sField = new StringBuffer();
        } else if (c == '?' && this.m_iAutomState == 4) {
            this.m_aTrack1.add(this.m_sField.toString());
            this.m_sField = null;
            this.m_iAutomState = 1;
        } else if (c == '?' && this.m_iAutomState == 5) {
            this.m_aTrack2.add(this.m_sField.toString());
            this.m_sField = null;
            this.m_iAutomState = 2;
            this.checkTracks();
        } else if (c == '?' && this.m_iAutomState == 6) {
            this.m_aTrack3.add(this.m_sField.toString());
            this.m_sField = null;
            this.m_iAutomState = 7;
        } else if (this.m_iAutomState == 4 || this.m_iAutomState == 5 || this.m_iAutomState == 6) {
            this.m_sField.append(c);
        }
        if (this.m_iAutomState == 3 || this.m_iAutomState == 4 || this.m_iAutomState == 1) {
            this.track1.append(c);
        } else if (this.m_iAutomState == 5 || this.m_iAutomState == 2) {
            this.track2.append(c);
        } else if (this.m_iAutomState == 6 || this.m_iAutomState == 7) {
            this.track3.append(c);
        }
    }

    private void checkTracks() {
        String sExpDate2;
        if (this.m_cCardType != 'B') {
            return;
        }
        String sCardNumber1 = this.m_aTrack1 == null || this.m_aTrack1.size() < 1 ? null : (String)this.m_aTrack1.get(0);
        String sCardNumber2 = this.m_aTrack2 == null || this.m_aTrack2.size() < 1 ? null : (String)this.m_aTrack2.get(0);
        String sHolderName = this.m_aTrack1 == null || this.m_aTrack1.size() < 2 ? null : (String)this.m_aTrack1.get(1);
        String sExpDate1 = this.m_aTrack1 == null || this.m_aTrack1.size() < 3 ? null : ((String)this.m_aTrack1.get(2)).substring(0, 4);
        String string = sExpDate2 = this.m_aTrack2 == null || this.m_aTrack2.size() < 2 ? null : ((String)this.m_aTrack2.get(1)).substring(0, 4);
        if (!this.checkCardNumber(sCardNumber1) || sCardNumber2 != null && !sCardNumber1.equals(sCardNumber2)) {
            return;
        }
        if (sHolderName == null) {
            return;
        }
        if (!(sExpDate1 == null && this.checkExpDate(sExpDate2) || this.checkExpDate(sExpDate1) && sExpDate1.equals(sExpDate2))) {
            return;
        }
        this.m_sCardNumber = sCardNumber1;
        this.m_sHolderName = this.formatHolderName(sHolderName);
        String yymm = sExpDate1 == null ? sExpDate2 : sExpDate1;
        this.m_sExpirationDate = yymm.substring(2, 4) + yymm.substring(0, 2);
    }

    private boolean checkCardNumber(String sNumber) {
        return LuhnAlgorithm.checkCC(sNumber);
    }

    private boolean checkExpDate(String sDate) {
        return sDate.length() == 4 && StringUtils.isNumber(sDate.trim());
    }

    private String formatHolderName(String sName) {
        int iPos = sName.indexOf(47);
        if (iPos >= 0) {
            return sName.substring(iPos + 1).trim() + ' ' + sName.substring(0, iPos);
        }
        return sName.trim();
    }

    @Override
    public boolean isComplete() {
        return this.m_sCardNumber != null;
    }

    @Override
    public String getHolderName() {
        return this.m_sHolderName;
    }

    @Override
    public String getCardNumber() {
        return this.m_sCardNumber;
    }

    @Override
    public String getExpirationDate() {
        return this.m_sExpirationDate;
    }

    @Override
    public String getTrack1() {
        return this.track1 == null ? null : this.track1.toString();
    }

    @Override
    public String getTrack2() {
        return this.track2 == null ? null : this.track2.toString();
    }

    @Override
    public String getTrack3() {
        return this.track3 == null ? null : this.track3.toString();
    }

    @Override
    public String getEncryptedCardData() {
        return "".equals(this.m_encryptedCardData) ? null : this.m_encryptedCardData;
    }

    @Override
    public String getEncryptionKey() {
        return "".equals(this.m_encryptionKey) ? null : this.m_encryptionKey;
    }
}

