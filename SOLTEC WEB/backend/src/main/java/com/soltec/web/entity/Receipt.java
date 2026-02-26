package com.soltec.web.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "receipts")
public class Receipt {

    @Id
    @Column(name = "id", length = 255)
    private String id;

    @Column(name = "money", length = 255, nullable = false)
    private String money;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "datenew", nullable = false)
    private Date datenew;

    @Lob
    @Column(name = "attributes")
    private byte[] attributes;

    @Column(name = "person", length = 255)
    private String person;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getMoney() { return money; }
    public void setMoney(String money) { this.money = money; }

    public Date getDatenew() { return datenew; }
    public void setDatenew(Date datenew) { this.datenew = datenew; }

    public byte[] getAttributes() { return attributes; }
    public void setAttributes(byte[] attributes) { this.attributes = attributes; }

    public String getPerson() { return person; }
    public void setPerson(String person) { this.person = person; }
}
