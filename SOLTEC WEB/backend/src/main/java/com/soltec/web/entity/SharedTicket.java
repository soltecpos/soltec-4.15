package com.soltec.web.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "sharedtickets")
public class SharedTicket {

    @Id
    @Column(name = "id", length = 255)
    private String id;

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Lob
    @Column(name = "content")
    private byte[] content;

    @Column(name = "appuser", length = 255)
    private String appuser;

    @Column(name = "pickupid")
    private int pickupid;

    @Column(name = "locked", length = 20)
    private String locked;

    @Column(name = "locktime")
    private Long locktime;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public byte[] getContent() { return content; }
    public void setContent(byte[] content) { this.content = content; }

    public String getAppuser() { return appuser; }
    public void setAppuser(String appuser) { this.appuser = appuser; }

    public int getPickupid() { return pickupid; }
    public void setPickupid(int pickupid) { this.pickupid = pickupid; }

    public String getLocked() { return locked; }
    public void setLocked(String locked) { this.locked = locked; }

    public Long getLocktime() { return locktime; }
    public void setLocktime(Long locktime) { this.locktime = locktime; }
}
