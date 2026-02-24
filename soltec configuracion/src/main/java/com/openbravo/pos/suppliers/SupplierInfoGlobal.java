/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.suppliers;

import com.openbravo.data.user.BrowsableEditableData;
import com.openbravo.pos.suppliers.SupplierInfoExt;

public class SupplierInfoGlobal {
    private static SupplierInfoGlobal INSTANCE;
    private SupplierInfoExt supplierInfoExt;
    private BrowsableEditableData editableData;

    private SupplierInfoGlobal() {
    }

    public static SupplierInfoGlobal getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SupplierInfoGlobal();
        }
        return INSTANCE;
    }

    public SupplierInfoExt getSupplierInfoExt() {
        return this.supplierInfoExt;
    }

    public void setSupplierInfoExt(SupplierInfoExt supplierInfoExt) {
        this.supplierInfoExt = supplierInfoExt;
    }

    public BrowsableEditableData getEditableData() {
        return this.editableData;
    }

    public void setEditableData(BrowsableEditableData editableData) {
        this.editableData = editableData;
    }
}

