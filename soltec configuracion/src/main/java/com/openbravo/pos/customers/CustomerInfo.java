/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.customers;

import com.openbravo.pos.util.StringUtils;
import java.awt.image.BufferedImage;
import java.io.Serializable;

public class CustomerInfo
implements Serializable {
    private static final long serialVersionUID = 9083257536541L;
    protected String id;
    protected String searchkey;
    protected String taxid;
    protected String name;
    protected String postal;
    protected String phone;
    protected String email;
    protected BufferedImage image;
    protected Double curdebt;

    public CustomerInfo(String id) {
        this.id = id;
        this.searchkey = null;
        this.taxid = null;
        this.name = null;
        this.postal = null;
        this.phone = null;
        this.email = null;
        this.image = null;
        this.curdebt = null;
    }

    public String getId() {
        return this.id;
    }

    public String getSearchkey() {
        return this.searchkey;
    }

    public void setSearchkey(String searchkey) {
        this.searchkey = searchkey;
    }

    public String getTaxid() {
        return this.taxid;
    }

    public void setTaxid(String taxid) {
        this.taxid = taxid;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPostal() {
        return this.postal;
    }

    public void setPostal(String postal) {
        this.postal = postal;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String printName() {
        return StringUtils.encodeXML(this.name);
    }

    public String toString() {
        return this.getName();
    }

    public BufferedImage getImage() {
        return this.image;
    }

    public void setImage(BufferedImage img) {
        this.image = img;
    }

    public Double getCurDebt() {
        return this.curdebt;
    }

    public void setCurDebt(Double curdebt) {
        this.curdebt = curdebt;
    }
}

