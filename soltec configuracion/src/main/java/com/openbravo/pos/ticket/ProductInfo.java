/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.ticket;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.DataRead;
import com.openbravo.data.loader.IKeyed;
import com.openbravo.data.loader.SerializerRead;

public class ProductInfo
implements IKeyed {
    private static final long serialVersionUID = 8712449444103L;
    private String m_sID;
    private String m_sRef;
    private String m_sCode;
    private String m_sName;

    public ProductInfo(String id, String ref, String code, String name) {
        this.m_sID = id;
        this.m_sRef = ref;
        this.m_sCode = code;
        this.m_sName = name;
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

    public String getRef() {
        return this.m_sRef;
    }

    public void setRef(String sRef) {
        this.m_sRef = sRef;
    }

    public String getCode() {
        return this.m_sCode;
    }

    public void setCode(String sCode) {
        this.m_sCode = sCode;
    }

    public String getName() {
        return this.m_sName;
    }

    public void setName(String sName) {
        this.m_sName = sName;
    }

    public String toString() {
        return this.m_sName;
    }

    public static SerializerRead<ProductInfo> getSerializerRead() {
        return new SerializerRead<ProductInfo>(){

            @Override
            public ProductInfo readValues(DataRead dr) throws BasicException {
                return new ProductInfo(dr.getString(1), dr.getString(2), dr.getString(3), dr.getString(4));
            }
        };
    }
}

