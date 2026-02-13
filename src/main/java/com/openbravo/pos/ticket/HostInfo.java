/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.ticket;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.DataRead;
import com.openbravo.data.loader.IKeyed;
import com.openbravo.data.loader.SerializerRead;

public class HostInfo
implements IKeyed {
    private String m_sMoney;
    private String m_sHost;
    private String m_Hostsequence;

    public HostInfo(String money, String host, String hostsequence) {
        this.m_sMoney = host;
        this.m_sHost = host;
        this.m_Hostsequence = hostsequence;
    }

    @Override
    public Object getKey() {
        return this.m_sMoney;
    }

    public String getHostsequence() {
        return this.m_Hostsequence;
    }

    public void setHostsequence(String m_Hostsequence) {
        this.m_Hostsequence = m_Hostsequence;
    }

    public String getHost() {
        return this.m_sHost;
    }

    public void setHost(String m_sHost) {
        this.m_sHost = m_sHost;
    }

    public String getMoney() {
        return this.m_sMoney;
    }

    public void setMoney(String m_sMoney) {
        this.m_sMoney = m_sMoney;
    }

    public String toString() {
        return this.m_sHost;
    }

    public static SerializerRead<HostInfo> getSerializerRead() {
        return new SerializerRead<HostInfo>(){

            @Override
            public HostInfo readValues(DataRead dr) throws BasicException {
                return new HostInfo(dr.getString(1), dr.getString(2), dr.getString(3));
            }
        };
    }
}

