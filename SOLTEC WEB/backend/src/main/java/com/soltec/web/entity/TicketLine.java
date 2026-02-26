package com.soltec.web.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ticketlines")
@IdClass(TicketLineId.class)
public class TicketLine {

    @Id
    @Column(name = "ticket", length = 255)
    private String ticket;

    @Id
    @Column(name = "line")
    private int line;

    @Column(name = "product", length = 255)
    private String productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product", insertable = false, updatable = false)
    private Product product;

    @Column(name = "attributesetinstance_id", length = 255)
    private String attributesetinstanceId;

    @Column(name = "units", nullable = false)
    private double units;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "taxid", length = 255, nullable = false)
    private String taxid;

    @Lob
    @Column(name = "attributes")
    private byte[] attributes;

    public String getTicket() { return ticket; }
    public void setTicket(String ticket) { this.ticket = ticket; }

    public int getLine() { return line; }
    public void setLine(int line) { this.line = line; }

    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    public String getAttributesetinstanceId() { return attributesetinstanceId; }
    public void setAttributesetinstanceId(String id) { this.attributesetinstanceId = id; }

    public double getUnits() { return units; }
    public void setUnits(double units) { this.units = units; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getTaxid() { return taxid; }
    public void setTaxid(String taxid) { this.taxid = taxid; }

    public byte[] getAttributes() { return attributes; }
    public void setAttributes(byte[] attributes) { this.attributes = attributes; }
}
