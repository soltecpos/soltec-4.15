/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.persistence.Basic
 *  javax.persistence.Column
 *  javax.persistence.Entity
 *  javax.persistence.Id
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="applications", catalog="unicentaopos", schema="")
@NamedQueries(value={@NamedQuery(name="Applications.findAll", query="SELECT a FROM Applications a"), @NamedQuery(name="Applications.findById", query="SELECT a FROM Applications a WHERE a.id = :id"), @NamedQuery(name="Applications.findByName", query="SELECT a FROM Applications a WHERE a.name = :name"), @NamedQuery(name="Applications.findByVersion", query="SELECT a FROM Applications a WHERE a.version = :version")})
public class Applications
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
    @Basic(optional=false)
    @Column(name="VERSION")
    private String version;

    public Applications() {
    }

    public Applications(String id) {
        this.id = id;
    }

    public Applications(String id, String name, String version) {
        this.id = id;
        this.name = name;
        this.version = version;
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

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        String oldVersion = this.version;
        this.version = version;
        this.changeSupport.firePropertyChange("version", oldVersion, version);
    }

    public int hashCode() {
        int hash = 0;
        return hash += this.id != null ? this.id.hashCode() : 0;
    }

    public boolean equals(Object object) {
        if (!(object instanceof Applications)) {
            return false;
        }
        Applications other = (Applications)object;
        return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
    }

    public String toString() {
        return "com.openbravo.pos.mant.Applications[ id=" + this.id + " ]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.changeSupport.removePropertyChangeListener(listener);
    }
}

