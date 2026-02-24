/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.customers;

import com.openbravo.format.Formats;
import com.openbravo.pos.customers.CustomerInfo;
import com.openbravo.pos.util.RoundUtils;
import java.awt.image.BufferedImage;
import java.util.Date;

public class CustomerInfoExt
extends CustomerInfo {
    private static final long serialVersionUID = 1L;
    protected String taxcustomerid;
    protected String taxcustcategoryid;
    protected String card;
    protected Double maxdebt;
    protected String address;
    protected String address2;
    protected String postal;
    protected String city;
    protected String region;
    protected String country;
    protected String firstname;
    protected String lastname;
    protected String email;
    protected String phone;
    protected String phone2;
    protected String fax;
    protected String notes;
    protected boolean visible;
    protected Date curdate;
    protected Double curdebt;
    protected BufferedImage m_Image;
    protected boolean isvip;
    protected Double discount;
    protected String prepay;
    protected String dv;
    protected String docType;
    protected String personType;
    protected String liability;
    protected String municipalityCode;
    protected String regimeCode;

    public CustomerInfoExt(String id) {
        super(id);
    }

    public String getTaxCustCategoryID() {
        return this.taxcustcategoryid;
    }

    public void setTaxCustCategoryID(String taxcustcategoryid) {
        this.taxcustcategoryid = taxcustcategoryid;
    }

    public String getTaxCustomerID() {
        return this.taxcustomerid;
    }

    public void setTaxCustomerID(String taxcustomerid) {
        this.taxcustomerid = taxcustomerid;
    }

    public String printTaxCustomerID() {
        return Formats.STRING.formatValue(this.taxcustomerid);
    }

    @Override
    public String getTaxid() {
        return this.taxid;
    }

    @Override
    public void setTaxid(String taxid) {
        this.taxid = taxid;
    }

    public String printTaxid() {
        return Formats.STRING.formatValue(this.taxid);
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

    public String getCard() {
        return this.card;
    }

    public void setCard(String card) {
        this.card = card;
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

    public String getPrePay() {
        return this.prepay;
    }

    public void setPrePay(String prepay) {
        this.prepay = prepay;
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

    public String printAddress2() {
        return Formats.STRING.formatValue(this.address2);
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

    @Override
    public BufferedImage getImage() {
        return this.m_Image;
    }

    @Override
    public void setImage(BufferedImage img) {
        this.m_Image = img;
    }

    public boolean isVIP() {
        return this.isvip;
    }

    public void setisVIP(boolean isvip) {
        this.isvip = isvip;
    }

    public Double getDiscount() {
        return this.discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public String printDiscount() {
        return Formats.CURRENCY.formatValue(RoundUtils.getValue(this.getDiscount()));
    }

    public String getDv() {
        return this.dv;
    }

    public void setDv(String dv) {
        this.dv = dv;
    }

    public String getDocType() {
        return this.docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getPersonType() {
        return this.personType;
    }

    public void setPersonType(String personType) {
        this.personType = personType;
    }

    public String getLiability() {
        return this.liability;
    }

    public void setLiability(String liability) {
        this.liability = liability;
    }

    public String getMunicipalityCode() {
        return this.municipalityCode;
    }

    public void setMunicipalityCode(String municipalityCode) {
        this.municipalityCode = municipalityCode;
    }

    public String getRegimeCode() {
        return this.regimeCode;
    }

    public void setRegimeCode(String regimeCode) {
        this.regimeCode = regimeCode;
    }
}

