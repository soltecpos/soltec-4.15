package com.soltec.web.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @Column(name = "id", length = 255)
    private String id;

    @Column(name = "receipt", length = 255, nullable = false)
    private String receiptId;

    @Column(name = "payment", length = 255, nullable = false)
    private String payment;

    @Column(name = "total", nullable = false)
    private double total;

    @Column(name = "tip")
    private Double tip;

    @Column(name = "transid", length = 255)
    private String transid;

    @Column(name = "notes", length = 255)
    private String notes;

    @Column(name = "tendered")
    private Double tendered;

    @Column(name = "cardname", length = 255)
    private String cardname;

    @Column(name = "voucher", length = 255)
    private String voucher;

    @Column(name = "category", length = 255)
    private String category;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getReceiptId() { return receiptId; }
    public void setReceiptId(String receiptId) { this.receiptId = receiptId; }

    public String getPayment() { return payment; }
    public void setPayment(String payment) { this.payment = payment; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public Double getTip() { return tip; }
    public void setTip(Double tip) { this.tip = tip; }

    public String getTransid() { return transid; }
    public void setTransid(String transid) { this.transid = transid; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public Double getTendered() { return tendered; }
    public void setTendered(Double tendered) { this.tendered = tendered; }

    public String getCardname() { return cardname; }
    public void setCardname(String cardname) { this.cardname = cardname; }

    public String getVoucher() { return voucher; }
    public void setVoucher(String voucher) { this.voucher = voucher; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
}
