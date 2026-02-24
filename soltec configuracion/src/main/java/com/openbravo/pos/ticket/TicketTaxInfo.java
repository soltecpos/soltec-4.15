/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.ticket;

import com.openbravo.format.Formats;
import com.openbravo.pos.ticket.TaxInfo;

public class TicketTaxInfo {
    private TaxInfo tax;
    private double subtotal;
    private double taxtotal;

    public TicketTaxInfo(TaxInfo tax) {
        this.tax = tax;
        this.subtotal = 0.0;
        this.taxtotal = 0.0;
    }

    public TaxInfo getTaxInfo() {
        return this.tax;
    }

    public void add(double dValue) {
        this.subtotal += dValue;
        this.taxtotal = this.subtotal * this.tax.getRate();
    }

    public double getSubTotal() {
        return this.subtotal;
    }

    public double getTax() {
        return this.taxtotal;
    }

    public double getTotal() {
        return this.subtotal + this.taxtotal;
    }

    public String printSubTotal() {
        return Formats.CURRENCY.formatValue(this.getSubTotal());
    }

    public String printTax() {
        return Formats.CURRENCY.formatValue(this.getTax());
    }

    public String printTotal() {
        return Formats.CURRENCY.formatValue(this.getTotal());
    }
}

