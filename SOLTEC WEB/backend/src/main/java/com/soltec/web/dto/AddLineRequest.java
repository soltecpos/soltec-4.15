package com.soltec.web.dto;

public class AddLineRequest {
    private String ticketId;
    private String productId;
    private double quantity;

    public AddLineRequest() {}

    public String getTicketId() { return ticketId; }
    public void setTicketId(String ticketId) { this.ticketId = ticketId; }

    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }

    public double getQuantity() { return quantity; }
    public void setQuantity(double quantity) { this.quantity = quantity; }
}
