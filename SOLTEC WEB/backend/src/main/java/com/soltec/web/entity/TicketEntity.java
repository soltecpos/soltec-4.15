package com.soltec.web.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tickets")
public class TicketEntity {

    @Id
    @Column(name = "id", length = 255)
    private String id;

    @Column(name = "tickettype", nullable = false)
    private int tickettype;

    @Column(name = "ticketid", nullable = false)
    private int ticketid;

    @Column(name = "person", length = 255, nullable = false)
    private String personId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person", insertable = false, updatable = false)
    private Person person;

    @Column(name = "customer", length = 255)
    private String customerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer", insertable = false, updatable = false)
    private Customer customer;

    @Column(name = "status", nullable = false)
    private int status;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", referencedColumnName = "id", insertable = false, updatable = false)
    private Receipt receipt;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public int getTickettype() { return tickettype; }
    public void setTickettype(int tickettype) { this.tickettype = tickettype; }

    public int getTicketid() { return ticketid; }
    public void setTicketid(int ticketid) { this.ticketid = ticketid; }

    public String getPersonId() { return personId; }
    public void setPersonId(String personId) { this.personId = personId; }

    public Person getPerson() { return person; }
    public void setPerson(Person person) { this.person = person; }

    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }

    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }

    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }

    public Receipt getReceipt() { return receipt; }
    public void setReceipt(Receipt receipt) { this.receipt = receipt; }
}
