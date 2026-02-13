/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.ticket;

import com.openbravo.data.loader.IKeyed;
import java.io.Serializable;

public class TaxInfo
implements Serializable,
IKeyed {
    private static final long serialVersionUID = -2705212098856473043L;
    private String id;
    private String name;
    private String taxcategoryid;
    private String taxcustcategoryid;
    private String parentid;
    private double rate;
    private boolean cascade;
    private Integer order;

    public TaxInfo(String id, String name, String taxcategoryid, String taxcustcategoryid, String parentid, double rate, boolean cascade, Integer order) {
        this.id = id;
        this.name = name;
        this.taxcategoryid = taxcategoryid;
        this.taxcustcategoryid = taxcustcategoryid;
        this.parentid = parentid;
        this.rate = rate;
        this.cascade = cascade;
        this.order = order;
    }

    @Override
    public Object getKey() {
        return this.id;
    }

    public void setID(String value) {
        this.id = value;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public String getTaxCategoryID() {
        return this.taxcategoryid;
    }

    public void setTaxCategoryID(String value) {
        this.taxcategoryid = value;
    }

    public String getTaxCustCategoryID() {
        return this.taxcustcategoryid;
    }

    public void setTaxCustCategoryID(String value) {
        this.taxcustcategoryid = value;
    }

    public String getParentID() {
        return this.parentid;
    }

    public void setParentID(String value) {
        this.parentid = value;
    }

    public double getRate() {
        return this.rate;
    }

    public void setRate(double value) {
        this.rate = value;
    }

    public boolean isCascade() {
        return this.cascade;
    }

    public void setCascade(boolean value) {
        this.cascade = value;
    }

    public Integer getOrder() {
        return this.order;
    }

    public Integer getApplicationOrder() {
        return this.order == null ? Integer.MAX_VALUE : this.order;
    }

    public void setOrder(Integer value) {
        this.order = value;
    }

    public String toString() {
        return this.name;
    }
}

