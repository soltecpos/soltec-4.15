package com.soltec.web.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "taxcategories")
public class TaxCategory {

    @Id
    @Column(name = "id", length = 255)
    private String id;

    @Column(name = "name", length = 255, nullable = false, unique = true)
    private String name;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
