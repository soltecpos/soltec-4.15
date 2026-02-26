package com.soltec.web.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "taxes")
public class Tax {

    @Id
    @Column(name = "id", length = 255)
    private String id;

    @Column(name = "name", length = 255, nullable = false, unique = true)
    private String name;

    @Column(name = "category", length = 255, nullable = false)
    private String categoryId;

    @Column(name = "custcategory", length = 255)
    private String custcategory;

    @Column(name = "parentid", length = 255)
    private String parentid;

    @Column(name = "rate", nullable = false)
    private double rate;

    @Column(name = "ratecascade", nullable = false)
    private boolean ratecascade;

    @Column(name = "rateorder")
    private Integer rateorder;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCategoryId() { return categoryId; }
    public void setCategoryId(String categoryId) { this.categoryId = categoryId; }

    public String getCustcategory() { return custcategory; }
    public void setCustcategory(String custcategory) { this.custcategory = custcategory; }

    public String getParentid() { return parentid; }
    public void setParentid(String parentid) { this.parentid = parentid; }

    public double getRate() { return rate; }
    public void setRate(double rate) { this.rate = rate; }

    public boolean isRatecascade() { return ratecascade; }
    public void setRatecascade(boolean ratecascade) { this.ratecascade = ratecascade; }

    public Integer getRateorder() { return rateorder; }
    public void setRateorder(Integer rateorder) { this.rateorder = rateorder; }
}
