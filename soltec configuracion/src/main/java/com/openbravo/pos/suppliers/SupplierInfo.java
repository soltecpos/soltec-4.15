/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.suppliers;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.DataRead;
import com.openbravo.data.loader.IKeyed;
import com.openbravo.pos.util.StringUtils;
import java.io.Serializable;

public class SupplierInfo
implements Serializable,
IKeyed {
    private static final long serialVersionUID = 9093257536541L;
    protected String m_sID;
    protected String m_sSearchkey;
    protected String m_sTaxid;
    protected String m_sName;
    protected String m_sPostal;
    protected String m_sPhone;
    protected String m_sEmail;

    public SupplierInfo(String id) {
        this.m_sID = id;
        this.m_sSearchkey = null;
        this.m_sTaxid = null;
        this.m_sName = null;
        this.m_sPostal = null;
        this.m_sPhone = null;
        this.m_sEmail = null;
    }

    public SupplierInfo(String id, String searchkey, String name) {
        this.m_sID = id;
        this.m_sSearchkey = searchkey;
        this.m_sName = name;
    }

    @Override
    public Object getKey() {
        return this.m_sID;
    }

    public void readValues(DataRead dr) throws BasicException {
        this.m_sID = dr.getString(1);
        this.m_sName = dr.getString(2);
    }

    public String getID() {
        return this.m_sID;
    }

    public void setID(String sID) {
        this.m_sID = sID;
    }

    public String getTaxid() {
        return this.m_sTaxid;
    }

    public void setTaxid(String sTaxid) {
        this.m_sTaxid = sTaxid;
    }

    public String printTaxid() {
        return StringUtils.encodeXML(this.m_sTaxid);
    }

    public String getSearchkey() {
        return this.m_sSearchkey;
    }

    public void setSearchkey(String sSearchkey) {
        this.m_sSearchkey = sSearchkey;
    }

    public String getName() {
        return this.m_sName;
    }

    public void setName(String sName) {
        this.m_sName = sName;
    }

    public String getPostal() {
        return this.m_sPostal;
    }

    public void setPostal(String sPostal) {
        this.m_sPostal = sPostal;
    }

    public String getPhone() {
        return this.m_sPhone;
    }

    public void setPhone(String sPhone) {
        this.m_sPhone = sPhone;
    }

    public String getEmail() {
        return this.m_sEmail;
    }

    public void setEmail(String sEmail) {
        this.m_sEmail = sEmail;
    }

    public String printName() {
        return StringUtils.encodeXML(this.m_sName);
    }

    public String toString() {
        return this.getName();
    }
}

