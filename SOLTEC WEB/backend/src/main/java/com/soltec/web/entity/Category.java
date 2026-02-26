package com.soltec.web.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @Column(name = "id", length = 255)
    private String id;

    @Column(name = "name", length = 255, nullable = false, unique = true)
    private String name;

    @Column(name = "parentid", length = 255)
    private String parentId;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "texttip", length = 255)
    private String texttip;

    @Column(name = "catshowname")
    private int catshowname;

    @Column(name = "catorder", length = 255)
    private String catorder;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getParentId() { return parentId; }
    public void setParentId(String parentId) { this.parentId = parentId; }

    public byte[] getImage() { return image; }
    public void setImage(byte[] image) { this.image = image; }

    public String getTexttip() { return texttip; }
    public void setTexttip(String texttip) { this.texttip = texttip; }

    public int getCatshowname() { return catshowname; }
    public void setCatshowname(int catshowname) { this.catshowname = catshowname; }

    public String getCatorder() { return catorder; }
    public void setCatorder(String catorder) { this.catorder = catorder; }
}
