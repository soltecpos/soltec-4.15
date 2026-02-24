package com.openbravo.pos.inventory;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.DataRead;
import com.openbravo.data.loader.IKeyed;
import com.openbravo.data.loader.SerializableRead;
import java.util.Date;

public class InventoryTaskInfo implements SerializableRead, IKeyed {

    private String id;
    private String status;
    private Date createdAt;
    private String authorId;
    private String locationId;
    private String assigneeRole;

    public InventoryTaskInfo() {
    }

    public InventoryTaskInfo(String id, String status, Date createdAt, String authorId, String locationId, String assigneeRole) {
        this.id = id;
        this.status = status;
        this.createdAt = createdAt;
        this.authorId = authorId;
        this.locationId = locationId;
        this.assigneeRole = assigneeRole;
    }

    @Override
    public Object getKey() {
        return id;
    }

    @Override
    public void readValues(DataRead dr) throws BasicException {
        id = dr.getString(1);
        status = dr.getString(2);
        createdAt = dr.getTimestamp(3);
        authorId = dr.getString(4);
        locationId = dr.getString(5);
        assigneeRole = dr.getString(6);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getAssigneeRole() {
        return assigneeRole;
    }

    public void setAssigneeRole(String assigneeRole) {
        this.assigneeRole = assigneeRole;
    }
}
