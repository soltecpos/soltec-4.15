/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.epm;

public class Break {
    private String m_sId;
    private String m_sName;
    private String m_sNotes;
    private boolean m_sVisible;

    public Break(String id, String name, String notes, boolean visible) {
        this.m_sId = id;
        this.m_sName = name;
        this.m_sNotes = notes;
        this.m_sVisible = visible;
    }

    public String getId() {
        return this.m_sId;
    }

    public void setId(String Id) {
        this.m_sId = Id;
    }

    public String getName() {
        return this.m_sName;
    }

    public void setName(String Name2) {
        this.m_sName = Name2;
    }

    public String getNotes() {
        return this.m_sNotes;
    }

    public void setNotes(String Notes) {
        this.m_sNotes = Notes;
    }

    public boolean isVisible() {
        return this.m_sVisible;
    }

    public void setVisible(boolean Visible) {
        this.m_sVisible = Visible;
    }
}

