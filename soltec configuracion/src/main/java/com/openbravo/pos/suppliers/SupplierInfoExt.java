/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.suppliers;

import com.openbravo.format.Formats;
import com.openbravo.pos.suppliers.SupplierInfo;
import com.openbravo.pos.util.RoundUtils;
import java.util.Date;

public class SupplierInfoExt
extends SupplierInfo {
    protected String suppliertaxid;
    protected String suppliervatid;
    protected String notes;
    protected boolean visible;
    protected String card;
    protected Double maxdebt;
    protected Date curdate;
    protected Double curdebt;
    protected String firstname;
    protected String lastname;
    protected String email;
    protected String phone;
    protected String phone2;
    protected String fax;
    protected String address;
    protected String address2;
    protected String postal;
    protected String city;
    protected String region;
    protected String country;

    public SupplierInfoExt(String id) {
        super(id);
    }

    public String getSupplierTaxID() {
        return this.suppliertaxid;
    }

    public void setSupplierTAXID(String suppliertaxid) {
        this.suppliertaxid = suppliertaxid;
    }

    public String printSupplierTaxID() {
        return Formats.STRING.formatValue(this.suppliertaxid);
    }

    public String getSupplierVATID() {
        return this.suppliervatid;
    }

    public void setSupplierVATID(String suppliervatid) {
        this.suppliervatid = suppliervatid;
    }

    public String printSupplierVATID() {
        return Formats.STRING.formatValue(this.suppliervatid);
    }

    public String getNotes() {
        return this.notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public boolean isVisible() {
        return this.visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public Double getMaxdebt() {
        return this.maxdebt;
    }

    public void setMaxdebt(Double maxdebt) {
        this.maxdebt = maxdebt;
    }

    public String printMaxDebt() {
        return Formats.CURRENCY.formatValue(RoundUtils.getValue(this.getMaxdebt()));
    }

    public Date getCurdate() {
        return this.curdate;
    }

    public void setCurdate(Date curdate) {
        this.curdate = curdate;
    }

    public String printCurDate() {
        return Formats.DATE.formatValue(this.getCurdate());
    }

    public Double getCurdebt() {
        return this.curdebt;
    }

    public void setCurdebt(Double curdebt) {
        this.curdebt = curdebt;
    }

    public String printCurDebt() {
        return Formats.CURRENCY.formatValue(RoundUtils.getValue(this.getCurdebt()));
    }

    public void updateCurDebt(Double amount, Date d) {
        this.curdebt = this.curdebt == null ? amount : this.curdebt + amount;
        this.curdate = new Date();
        if (RoundUtils.compare(this.curdebt, 0.0) > 0) {
            if (this.curdate == null) {
                this.curdate = d;
            }
        } else if (RoundUtils.compare(this.curdebt, 0.0) == 0) {
            this.curdebt = null;
            this.curdate = null;
        } else {
            this.curdate = null;
        }
    }

    public String getFirstname() {
        return this.firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return this.lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override
    public String getEmail() {
        return this.email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getPhone() {
        return this.phone;
    }

    @Override
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String printPhone() {
        return Formats.STRING.formatValue(this.phone);
    }

    public String getPhone2() {
        return this.phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getFax() {
        return this.fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String printAddress() {
        return Formats.STRING.formatValue(this.address);
    }

    public String getAddress2() {
        return this.address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    @Override
    public String getPostal() {
        return this.postal;
    }

    @Override
    public void setPostal(String postal) {
        this.postal = postal;
    }

    public String printPostal() {
        return Formats.STRING.formatValue(this.postal);
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return this.region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}

