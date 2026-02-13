/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.payment;

public interface MagCardReader {
    public String getReaderName();

    public void reset();

    public void appendChar(char var1);

    public boolean isComplete();

    public String getHolderName();

    public String getCardNumber();

    public String getExpirationDate();

    public String getTrack1();

    public String getTrack2();

    public String getTrack3();

    public String getEncryptedCardData();

    public String getEncryptionKey();
}

