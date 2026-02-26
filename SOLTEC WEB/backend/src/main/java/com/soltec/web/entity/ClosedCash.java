package com.soltec.web.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "closedcash")
public class ClosedCash {

    @Id
    @Column(name = "money", length = 255)
    private String money;

    @Column(name = "host", length = 255, nullable = false)
    private String host;

    @Column(name = "hostsequence", nullable = false)
    private int hostsequence;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "datestart", nullable = false)
    private Date datestart;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dateend")
    private Date dateend;

    @Column(name = "nosales")
    private int nosales;

    public String getMoney() { return money; }
    public void setMoney(String money) { this.money = money; }

    public String getHost() { return host; }
    public void setHost(String host) { this.host = host; }

    public int getHostsequence() { return hostsequence; }
    public void setHostsequence(int hostsequence) { this.hostsequence = hostsequence; }

    public Date getDatestart() { return datestart; }
    public void setDatestart(Date datestart) { this.datestart = datestart; }

    public Date getDateend() { return dateend; }
    public void setDateend(Date dateend) { this.dateend = dateend; }

    public int getNosales() { return nosales; }
    public void setNosales(int nosales) { this.nosales = nosales; }
}
