package com.openbravo.pos.inventory;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.DataRead;
import com.openbravo.data.loader.SerializableRead;

public class InventoryTaskLineInfo implements SerializableRead {

    private String taskId;
    private String productId;
    private String productName;
    private Double systemQty;
    private Double countedQty;
    private Double difference;

    public InventoryTaskLineInfo() {
    }

    public InventoryTaskLineInfo(String taskId, String productId, String productName, Double systemQty, Double countedQty, Double difference) {
        this.taskId = taskId;
        this.productId = productId;
        this.productName = productName;
        this.systemQty = systemQty;
        this.countedQty = countedQty;
        this.difference = difference;
    }

    @Override
    public void readValues(DataRead dr) throws BasicException {
        taskId = dr.getString(1);
        productId = dr.getString(2);
        systemQty = dr.getDouble(3);
        countedQty = dr.getDouble(4);
        difference = dr.getDouble(5);
        productName = dr.getString(6);
    }

    public String getTaskId() { return taskId; }
    public void setTaskId(String taskId) { this.taskId = taskId; }

    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public Double getSystemQty() { return systemQty; }
    public void setSystemQty(Double systemQty) { this.systemQty = systemQty; }

    public Double getCountedQty() { return countedQty; }
    public void setCountedQty(Double countedQty) { this.countedQty = countedQty; }

    public Double getDifference() { return difference; }
    public void setDifference(Double difference) { this.difference = difference; }
}
