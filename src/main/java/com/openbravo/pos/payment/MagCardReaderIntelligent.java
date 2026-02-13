/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.payment;

import com.openbravo.pos.payment.MagCardReader;

public final class MagCardReaderIntelligent
implements MagCardReader {
    private String m_sHolderName;
    private String m_sCardNumber;
    private String m_sExpirationDate;
    private StringBuffer m_sField;
    private static final int READING_HOLDER = 0;
    private static final int READING_NUMBER = 1;
    private static final int READING_DATE = 2;
    private static final int READING_FINISHED = 3;
    private int m_iAutomState;

    public MagCardReaderIntelligent() {
        this.reset();
    }

    @Override
    public String getReaderName() {
        return "Basic magnetic card reader";
    }

    @Override
    public void reset() {
        this.m_sHolderName = null;
        this.m_sCardNumber = null;
        this.m_sExpirationDate = null;
        this.m_sField = new StringBuffer();
        this.m_iAutomState = 0;
    }

    @Override
    public void appendChar(char c) {
        switch (this.m_iAutomState) {
            case 0: 
            case 3: {
                if (c == '\t') {
                    this.m_sHolderName = this.m_sField.toString();
                    this.m_sField = new StringBuffer();
                    this.m_iAutomState = 1;
                    break;
                }
                if (c == '\n') {
                    this.m_sHolderName = null;
                    this.m_sCardNumber = null;
                    this.m_sExpirationDate = null;
                    this.m_sField = new StringBuffer();
                    this.m_iAutomState = 0;
                    break;
                }
                this.m_sField.append(c);
                this.m_iAutomState = 0;
                break;
            }
            case 1: {
                if (c == '\t') {
                    this.m_sCardNumber = this.m_sField.toString();
                    this.m_sField = new StringBuffer();
                    this.m_iAutomState = 2;
                    break;
                }
                if (c == '\n') {
                    this.m_sHolderName = null;
                    this.m_sCardNumber = null;
                    this.m_sExpirationDate = null;
                    this.m_sField = new StringBuffer();
                    this.m_iAutomState = 0;
                    break;
                }
                this.m_sField.append(c);
                break;
            }
            case 2: {
                if (c == '\t') {
                    this.m_sHolderName = this.m_sCardNumber;
                    this.m_sCardNumber = this.m_sExpirationDate;
                    this.m_sExpirationDate = null;
                    this.m_sField = new StringBuffer();
                    break;
                }
                if (c == '\n') {
                    this.m_sExpirationDate = this.m_sField.toString();
                    this.m_sField = new StringBuffer();
                    this.m_iAutomState = 3;
                    break;
                }
                this.m_sField.append(c);
            }
        }
    }

    @Override
    public boolean isComplete() {
        return this.m_iAutomState == 3;
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
        return null;
    }

    @Override
    public String getTrack2() {
        return null;
    }

    @Override
    public String getTrack3() {
        return null;
    }

    @Override
    public String getEncryptedCardData() {
        return null;
    }

    @Override
    public String getEncryptionKey() {
        return null;
    }
}

