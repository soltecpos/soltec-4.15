package com.soltec.web.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "places")
public class Place {

    @Id
    @Column(name = "id", length = 255)
    private String id;

    @Column(name = "name", length = 255, nullable = false, unique = true)
    private String name;

    @Column(name = "x", nullable = false)
    private int x;

    @Column(name = "y", nullable = false)
    private int y;

    @Column(name = "floor", length = 255, nullable = false)
    private String floorId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "floor", insertable = false, updatable = false)
    private Floor floor;

    @Column(name = "customer", length = 255)
    private String customer;

    @Column(name = "waiter", length = 255)
    private String waiter;

    @Column(name = "ticketid", length = 255)
    private String ticketid;

    @Column(name = "tablemoved")
    private int tablemoved;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getX() { return x; }
    public void setX(int x) { this.x = x; }

    public int getY() { return y; }
    public void setY(int y) { this.y = y; }

    public String getFloorId() { return floorId; }
    public void setFloorId(String floorId) { this.floorId = floorId; }

    public Floor getFloor() { return floor; }
    public void setFloor(Floor floor) { this.floor = floor; }

    public String getCustomer() { return customer; }
    public void setCustomer(String customer) { this.customer = customer; }

    public String getWaiter() { return waiter; }
    public void setWaiter(String waiter) { this.waiter = waiter; }

    public String getTicketid() { return ticketid; }
    public void setTicketid(String ticketid) { this.ticketid = ticketid; }

    public int getTablemoved() { return tablemoved; }
    public void setTablemoved(int tablemoved) { this.tablemoved = tablemoved; }
}
