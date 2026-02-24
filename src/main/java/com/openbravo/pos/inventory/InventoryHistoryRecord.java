package com.openbravo.pos.inventory;

import java.util.Date;

public class InventoryHistoryRecord {
    private Date transactionDate;
    private int reasonCode;
    private double quantity;
    private double newBalance;
    private String user;
    private String parentProductName;
    
    public InventoryHistoryRecord(Date transactionDate, int reasonCode, double quantity, double newBalance, String user, String parentProductName) {
        this.transactionDate = transactionDate;
        this.reasonCode = reasonCode;
        this.quantity = quantity;
        this.newBalance = newBalance;
        this.user = user;
        this.parentProductName = parentProductName;
    }

    public Date getTransactionDate() { return transactionDate; }
    public int getReasonCode() { return reasonCode; }
    public double getQuantity() { return quantity; }
    public double getNewBalance() { return newBalance; }
    public void setNewBalance(double newBalance) { this.newBalance = newBalance; }
    public String getUser() { return user; }
    public String getParentProductName() { return parentProductName; }
    
    public String getReasonString() {
        String baseReason = "";
        switch(reasonCode) {
            case 1: baseReason = "Entrada (Compra)"; break;
            case -1: baseReason = "Salida (Venta)"; break;
            case 2: baseReason = "Entrada (Traslado)"; break;
            case -2: baseReason = "Salida (Traslado)"; break;
            case 4: baseReason = "Ajuste de Salida"; break;
            case 5: baseReason = "Cuadre / Ajuste Faltante"; break;
            case 10: baseReason = "Sobrante Arqueo Físico"; break;
            case -11: baseReason = "Faltante Físico"; break;
            default: baseReason = "Movimiento " + reasonCode; break;
        }
        
        if (reasonCode == -1 && parentProductName != null && !parentProductName.isEmpty()) {
            return baseReason + " de " + parentProductName;
        }
        return baseReason;
    }
}
