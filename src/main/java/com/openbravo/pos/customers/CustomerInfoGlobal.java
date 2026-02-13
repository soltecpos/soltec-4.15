/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.customers;

import com.openbravo.data.user.BrowsableEditableData;
import com.openbravo.pos.customers.CustomerInfoExt;

public class CustomerInfoGlobal {
    private static CustomerInfoGlobal INSTANCE;
    private CustomerInfoExt customerInfoExt;
    private BrowsableEditableData editableData;

    private CustomerInfoGlobal() {
    }

    public static CustomerInfoGlobal getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CustomerInfoGlobal();
        }
        return INSTANCE;
    }

    public CustomerInfoExt getCustomerInfoExt() {
        return this.customerInfoExt;
    }

    public void setCustomerInfoExt(CustomerInfoExt customerInfoExt) {
        this.customerInfoExt = customerInfoExt;
    }

    public BrowsableEditableData getEditableData() {
        return this.editableData;
    }

    public void setEditableData(BrowsableEditableData editableData) {
        this.editableData = editableData;
    }
}

