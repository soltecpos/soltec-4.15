/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.persistence.Basic
 *  javax.persistence.Column
 *  javax.persistence.Entity
 *  javax.persistence.Id
 *  javax.persistence.Lob
 *  javax.persistence.NamedQueries
 *  javax.persistence.NamedQuery
 *  javax.persistence.Table
 *  javax.persistence.Transient
 */
package com.openbravo.pos.mant;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="floors", catalog="unicentaopos", schema="")
@NamedQueries(value={@NamedQuery(name="Floors.findAll", query="SELECT f FROM Floors f"), @NamedQuery(name="Floors.findById", query="SELECT f FROM Floors f WHERE f.id = :id"), @NamedQuery(name="Floors.findByName", query="SELECT f FROM Floors f WHERE f.name = :name")})
public class Floors
implements Serializable {
    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional=false)
    @Column(name="ID")
    private String id;
    @Basic(optional=false)
    @Column(name="NAME")
    private String name;
    @Lob
    @Column(name="IMAGE")
    private byte[] image;

    public Floors() {
    }

    public Floors(String id) {
        this.id = id;
    }

    public Floors(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        String oldId = this.id;
        this.id = id;
        this.changeSupport.firePropertyChange("id", oldId, id);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        String oldName = this.name;
        this.name = name;
        this.changeSupport.firePropertyChange("name", oldName, name);
    }

    public byte[] getImage() {
        return this.image;
    }

    public void setImage(byte[] image) {
        byte[] oldImage = this.image;
        this.image = image;
        this.changeSupport.firePropertyChange("image", oldImage, image);
    }

    public int hashCode() {
        int hash = 0;
        return hash += this.id != null ? this.id.hashCode() : 0;
    }

    public boolean equals(Object object) {
        if (!(object instanceof Floors)) {
            return false;
        }
        Floors other = (Floors)object;
        return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
    }

    public String toString() {
        return "com.openbravo.pos.mant.Floors[ id=" + this.id + " ]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.changeSupport.removePropertyChangeListener(listener);
    }
}

