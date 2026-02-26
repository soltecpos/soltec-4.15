package com.soltec.web.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "stockcurrent")
@IdClass(StockCurrentId.class)
public class StockCurrent {

    @Id
    @Column(name = "location", length = 255)
    private String location;

    @Id
    @Column(name = "product", length = 255)
    private String productId;

    @Column(name = "attributesetinstance_id", length = 255)
    private String attributesetinstanceId;

    @Column(name = "units", nullable = false)
    private double units;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product", insertable = false, updatable = false)
    private Product product;

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }

    public String getAttributesetinstanceId() { return attributesetinstanceId; }
    public void setAttributesetinstanceId(String id) { this.attributesetinstanceId = id; }

    public double getUnits() { return units; }
    public void setUnits(double units) { this.units = units; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
}
