/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.ticket;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.DataRead;
import com.openbravo.data.loader.IKeyed;
import com.openbravo.data.loader.SerializerRead;

public class CardInfo
implements IKeyed {
    private static final long serialVersionUID = 1612339444103L;
    private String m_sID;
    private String m_sCardName;
    private double m_Charges;

    public CardInfo(String id, String name, double charges) {
        this.m_sID = id;
        this.m_sCardName = name;
        this.m_Charges = charges;
    }

    @Override
    public Object getKey() {
        return this.m_sID;
    }

    public void setID(String sID) {
        this.m_sID = sID;
    }

    public String getID() {
        return this.m_sID;
    }

    public String getName() {
        return this.m_sCardName;
    }

    public void setName(String sName) {
        this.m_sCardName = sName;
    }

    public String toString() {
        return this.m_sCardName;
    }

    public static SerializerRead<CardInfo> getSerializerRead() {
        return new SerializerRead<CardInfo>(){

            @Override
            public CardInfo readValues(DataRead dr) throws BasicException {
                return new CardInfo(dr.getString(1), dr.getString(2), dr.getDouble(3));
            }
        };
    }

    public double getM_Charges() {
        return this.m_Charges;
    }

    public void setM_Charges(double m_Charges) {
        this.m_Charges = m_Charges;
    }
}

