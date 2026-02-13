/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.sales;

import com.openbravo.pos.ticket.TaxInfo;
import java.util.ArrayList;
import java.util.List;

public class TaxesLogicElement {
    private TaxInfo tax;
    private List<TaxesLogicElement> taxsons;

    public TaxesLogicElement(TaxInfo tax) {
        this.tax = tax;
        this.taxsons = new ArrayList<TaxesLogicElement>();
    }

    public TaxInfo getTax() {
        return this.tax;
    }

    public List<TaxesLogicElement> getSons() {
        return this.taxsons;
    }
}

