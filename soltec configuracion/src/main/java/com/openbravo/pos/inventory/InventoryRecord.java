/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.inventory;

import com.openbravo.format.Formats;
import com.openbravo.pos.inventory.InventoryLine;
import com.openbravo.pos.inventory.LocationInfo;
import com.openbravo.pos.inventory.MovementReason;
import com.openbravo.pos.suppliers.SupplierInfo;
import com.openbravo.pos.util.StringUtils;
import java.util.Date;
import java.util.List;

public class InventoryRecord {
    private final Date m_dDate;
    private final MovementReason m_reason;
    private final LocationInfo m_locationOri;
    private final List<InventoryLine> m_invlines;
    private final String user;
    private final SupplierInfo m_Supplier;
    private final String m_SupplierDoc;

    public InventoryRecord(Date d, MovementReason reason, LocationInfo location, String currentUser, SupplierInfo supplier, List<InventoryLine> invlines, String supplierdoc) {
        this.m_dDate = d;
        this.m_reason = reason;
        this.m_locationOri = location;
        this.m_invlines = invlines;
        this.user = currentUser;
        this.m_Supplier = supplier;
        this.m_SupplierDoc = supplierdoc;
    }

    public Date getDate() {
        return this.m_dDate;
    }

    public String printDate() {
        return Formats.TIMESTAMP.formatValue(this.m_dDate);
    }

    public MovementReason getReason() {
        return this.m_reason;
    }

    public String printReason() {
        return StringUtils.encodeXML(this.m_reason.toString());
    }

    public String getUser() {
        return this.user;
    }

    public String printUser() {
        return StringUtils.encodeXML(this.user.toString());
    }

    public LocationInfo getLocation() {
        return this.m_locationOri;
    }

    public String printLocation() {
        return StringUtils.encodeXML(this.m_locationOri.toString());
    }

    public SupplierInfo getSupplier() {
        return this.m_Supplier;
    }

    public String printSupplier() {
        return StringUtils.encodeXML(this.m_Supplier.toString());
    }

    public String getSupplierDoc() {
        return this.m_SupplierDoc;
    }

    public String printSupplierDoc() {
        return StringUtils.encodeXML(this.m_SupplierDoc.toString());
    }

    public List<InventoryLine> getLines() {
        return this.m_invlines;
    }

    public boolean isInput() {
        return this.m_reason.isInput();
    }

    public double getSubTotal() {
        double dSuma = 0.0;
        for (InventoryLine oLine : this.m_invlines) {
            dSuma += oLine.getSubValue();
        }
        return dSuma;
    }

    public String printSubTotal() {
        return Formats.CURRENCY.formatValue(this.getSubTotal());
    }

    public double getTotalArticles() {
        double dSum = 0.0;
        for (InventoryLine oLine : this.m_invlines) {
            dSum += oLine.getMultiply();
        }
        return dSum;
    }

    public String printTotalArticles() {
        return Formats.DOUBLE.formatValue(this.getTotalArticles());
    }
}

