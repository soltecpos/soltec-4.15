package com.soltec.web.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @Column(name = "id", length = 255)
    private String id;

    @Column(name = "searchkey", length = 255, nullable = false, unique = true)
    private String searchkey;

    @Column(name = "taxid", length = 255)
    private String taxid;

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Column(name = "card", length = 255)
    private String card;

    @Column(name = "maxdebt")
    private double maxdebt;

    @Column(name = "address", length = 255)
    private String address;

    @Column(name = "firstname", length = 255)
    private String firstname;

    @Column(name = "lastname", length = 255)
    private String lastname;

    @Column(name = "email", length = 255)
    private String email;

    @Column(name = "phone", length = 255)
    private String phone;

    @Column(name = "phone2", length = 255)
    private String phone2;

    @Column(name = "notes", length = 255)
    private String notes;

    @Column(name = "visible", nullable = false)
    private boolean visible;

    @Column(name = "curdebt")
    private Double curdebt;

    @Column(name = "discount")
    private Double discount;

    @Column(name = "isvip", nullable = false)
    private boolean isvip;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getSearchkey() { return searchkey; }
    public void setSearchkey(String searchkey) { this.searchkey = searchkey; }

    public String getTaxid() { return taxid; }
    public void setTaxid(String taxid) { this.taxid = taxid; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCard() { return card; }
    public void setCard(String card) { this.card = card; }

    public double getMaxdebt() { return maxdebt; }
    public void setMaxdebt(double maxdebt) { this.maxdebt = maxdebt; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getFirstname() { return firstname; }
    public void setFirstname(String firstname) { this.firstname = firstname; }

    public String getLastname() { return lastname; }
    public void setLastname(String lastname) { this.lastname = lastname; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getPhone2() { return phone2; }
    public void setPhone2(String phone2) { this.phone2 = phone2; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public boolean isVisible() { return visible; }
    public void setVisible(boolean visible) { this.visible = visible; }

    public Double getCurdebt() { return curdebt; }
    public void setCurdebt(Double curdebt) { this.curdebt = curdebt; }

    public Double getDiscount() { return discount; }
    public void setDiscount(Double discount) { this.discount = discount; }

    public boolean isIsvip() { return isvip; }
    public void setIsvip(boolean isvip) { this.isvip = isvip; }
}
