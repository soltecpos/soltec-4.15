package com.soltec.web.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "people")
public class Person {

    @Id
    @Column(name = "id", length = 255)
    private String id;

    @Column(name = "name", length = 255, nullable = false, unique = true)
    private String name;

    @Column(name = "apppassword", length = 255)
    private String apppassword;

    @Column(name = "card", length = 255)
    private String card;

    @Column(name = "role", length = 255, nullable = false)
    private String roleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role", insertable = false, updatable = false)
    private Role role;

    @Column(name = "visible", nullable = false)
    private boolean visible;

    @Lob
    @Column(name = "image")
    private byte[] image;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getApppassword() { return apppassword; }
    public void setApppassword(String apppassword) { this.apppassword = apppassword; }

    public String getCard() { return card; }
    public void setCard(String card) { this.card = card; }

    public String getRoleId() { return roleId; }
    public void setRoleId(String roleId) { this.roleId = roleId; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public boolean isVisible() { return visible; }
    public void setVisible(boolean visible) { this.visible = visible; }

    public byte[] getImage() { return image; }
    public void setImage(byte[] image) { this.image = image; }
}
