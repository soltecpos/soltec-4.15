/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.epm;

import com.openbravo.data.loader.IKeyed;

public class LeavesInfo
implements IKeyed {
    private static final long serialVersionUID = 8936482715929L;
    private String m_sID;
    private String m_sName;
    private String m_sEmployeeID;
    private String m_sStartDate;
    private String m_sEndDate;
    private String m_sNotes;

    public LeavesInfo(String id, String name, String employeeid, String startdate, String enddate, String notes) {
        this.m_sID = id;
        this.m_sName = name;
        this.m_sEmployeeID = employeeid;
        this.m_sStartDate = startdate;
        this.m_sEndDate = enddate;
        this.m_sNotes = notes;
    }

    @Override
    public Object getKey() {
        return this.m_sID;
    }

    public void setID(String sID) {
        this.m_sID = sID;
    }

    public String getID() {
        return this.m_sID;
    }

    public String getName() {
        return this.m_sName;
    }

    public void setName(String sName) {
        this.m_sName = sName;
    }

    public String toString() {
        return this.m_sName;
    }

    public String getEmployeeID() {
        return this.m_sEmployeeID;
    }

    public void setEmployeeID(String EmployeeID) {
        this.m_sEmployeeID = EmployeeID;
    }

    public String getStartDate() {
        return this.m_sStartDate;
    }

    public void setStartDate(String StartDate) {
        this.m_sStartDate = StartDate;
    }

    public String getEndDate() {
        return this.m_sEndDate;
    }

    public void setEndDate(String EndDate) {
        this.m_sEndDate = EndDate;
    }

    public String getNotes() {
        return this.m_sNotes;
    }

    public void setNotes(String Notes) {
        this.m_sNotes = Notes;
    }
}

