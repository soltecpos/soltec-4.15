package com.soltec.web.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "web_kitchen_orders")
public class WebKitchenOrder {

    @Id
    @Column(name = "id", length = 255)
    private String id;

    @Column(name = "ticket_id", length = 255, nullable = false)
    private String ticketId;

    @Column(name = "table_name", length = 255)
    private String tableName;

    @Column(name = "waiter_name", length = 255)
    private String waiterName;

    @Column(name = "product_name", length = 255, nullable = false)
    private String productName;

    @Column(name = "multiplier", nullable = false)
    private Double multiplier;

    @Column(name = "status", length = 50, nullable = false)
    private String status = "PENDING";

    @Column(name = "created_at", insertable = false, updatable = false)
    private Timestamp createdAt;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTicketId() { return ticketId; }
    public void setTicketId(String ticketId) { this.ticketId = ticketId; }
    public String getTableName() { return tableName; }
    public void setTableName(String tableName) { this.tableName = tableName; }
    public String getWaiterName() { return waiterName; }
    public void setWaiterName(String waiterName) { this.waiterName = waiterName; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public Double getMultiplier() { return multiplier; }
    public void setMultiplier(Double multiplier) { this.multiplier = multiplier; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}
