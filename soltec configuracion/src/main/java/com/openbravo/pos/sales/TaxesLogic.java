/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.sales;

import com.openbravo.pos.customers.CustomerInfoExt;
import com.openbravo.pos.inventory.TaxCategoryInfo;
import com.openbravo.pos.sales.TaxesException;
import com.openbravo.pos.sales.TaxesLogicElement;
import com.openbravo.pos.ticket.TaxInfo;
import com.openbravo.pos.ticket.TicketInfo;
import com.openbravo.pos.ticket.TicketLineInfo;
import com.openbravo.pos.ticket.TicketTaxInfo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaxesLogic {
    private List<TaxInfo> taxlist;
    private Map<String, TaxesLogicElement> taxtrees;

    public TaxesLogic(List<TaxInfo> taxlist) {
        this.taxlist = taxlist;
        this.taxtrees = new HashMap<String, TaxesLogicElement>();
        ArrayList<TaxInfo> taxlistordered = new ArrayList<TaxInfo>();
        taxlistordered.addAll(taxlist);
        Collections.sort(taxlistordered, new Comparator<TaxInfo>(){

            @Override
            public int compare(TaxInfo o1, TaxInfo o2) {
                if (o1.getApplicationOrder() < o2.getApplicationOrder()) {
                    return -1;
                }
                if (o1.getApplicationOrder() == o2.getApplicationOrder()) {
                    return 0;
                }
                return 1;
            }
        });
        HashMap<String, TaxesLogicElement> taxorphans = new HashMap<String, TaxesLogicElement>();
        for (TaxInfo t : taxlistordered) {
            TaxesLogicElement te = new TaxesLogicElement(t);
            TaxesLogicElement teparent = this.taxtrees.get(t.getParentID());
            if (teparent == null && (teparent = (TaxesLogicElement)taxorphans.get(t.getParentID())) == null) {
                teparent = new TaxesLogicElement(null);
                taxorphans.put(t.getParentID(), teparent);
            }
            teparent.getSons().add(te);
            teparent = (TaxesLogicElement)taxorphans.get(t.getId());
            if (teparent != null) {
                te.getSons().addAll(teparent.getSons());
                taxorphans.remove(t.getId());
            }
            this.taxtrees.put(t.getId(), te);
        }
    }

    public void calculateTaxes(TicketInfo ticket) throws TaxesException {
        List<TicketTaxInfo> tickettaxes = new ArrayList<TicketTaxInfo>();
        for (TicketLineInfo line : ticket.getLines()) {
            tickettaxes = this.sumLineTaxes(tickettaxes, this.calculateTaxes(line));
        }
        ticket.setTaxes(tickettaxes);
    }

    public List<TicketTaxInfo> calculateTaxes(TicketLineInfo line) throws TaxesException {
        TaxesLogicElement taxesapplied = this.getTaxesApplied(line.getTaxInfo());
        return this.calculateLineTaxes(line.getSubValue(), taxesapplied);
    }

    private List<TicketTaxInfo> calculateLineTaxes(double base, TaxesLogicElement taxesapplied) {
        ArrayList<TicketTaxInfo> linetaxes = new ArrayList<TicketTaxInfo>();
        if (taxesapplied.getSons().isEmpty()) {
            TicketTaxInfo tickettax = new TicketTaxInfo(taxesapplied.getTax());
            tickettax.add(base);
            linetaxes.add(tickettax);
        } else {
            double acum = base;
            for (TaxesLogicElement te : taxesapplied.getSons()) {
                List<TicketTaxInfo> sublinetaxes = this.calculateLineTaxes(te.getTax().isCascade() ? acum : base, te);
                linetaxes.addAll(sublinetaxes);
                acum += this.sumTaxes(sublinetaxes);
            }
        }
        return linetaxes;
    }

    private TaxesLogicElement getTaxesApplied(TaxInfo t) throws TaxesException {
        if (t == null) {
            throw new TaxesException(new NullPointerException());
        }
        return this.taxtrees.get(t.getId());
    }

    private double sumTaxes(List<TicketTaxInfo> linetaxes) {
        double taxtotal = 0.0;
        for (TicketTaxInfo tickettax : linetaxes) {
            taxtotal += tickettax.getTax();
        }
        return taxtotal;
    }

    private List<TicketTaxInfo> sumLineTaxes(List<TicketTaxInfo> list1, List<TicketTaxInfo> list2) {
        for (TicketTaxInfo tickettax : list2) {
            TicketTaxInfo i = this.searchTicketTax(list1, tickettax.getTaxInfo().getId());
            if (i == null) {
                list1.add(tickettax);
                continue;
            }
            i.add(tickettax.getSubTotal());
        }
        return list1;
    }

    private TicketTaxInfo searchTicketTax(List<TicketTaxInfo> l, String id) {
        for (TicketTaxInfo tickettax : l) {
            if (!id.equals(tickettax.getTaxInfo().getId())) continue;
            return tickettax;
        }
        return null;
    }

    public double getTaxRate(String tcid) {
        return this.getTaxRate(tcid, null);
    }

    public double getTaxRate(TaxCategoryInfo tc) {
        return this.getTaxRate(tc, null);
    }

    public double getTaxRate(TaxCategoryInfo tc, CustomerInfoExt customer) {
        if (tc == null) {
            return 0.0;
        }
        return this.getTaxRate(tc.getID(), customer);
    }

    public double getTaxRate(String tcid, CustomerInfoExt customer) {
        if (tcid == null) {
            return 0.0;
        }
        TaxInfo tax = this.getTaxInfo(tcid, customer);
        if (tax == null) {
            return 0.0;
        }
        return tax.getRate();
    }

    public TaxInfo getTaxInfo(String tcid) {
        return this.getTaxInfo(tcid, null);
    }

    public TaxInfo getTaxInfo(TaxCategoryInfo tc) {
        return this.getTaxInfo(tc.getID(), null);
    }

    public TaxInfo getTaxInfo(TaxCategoryInfo tc, CustomerInfoExt customer) {
        return this.getTaxInfo(tc.getID(), customer);
    }

    public TaxInfo getTaxInfo(String tcid, CustomerInfoExt customer) {
        TaxInfo defaulttax = null;
        for (TaxInfo tax : this.taxlist) {
            if (tax.getParentID() != null || !tax.getTaxCategoryID().equals(tcid)) continue;
            if ((customer == null || customer.getTaxCustCategoryID() == null) && tax.getTaxCustCategoryID() == null) {
                return tax;
            }
            if (customer != null && customer.getTaxCustCategoryID() != null && customer.getTaxCustCategoryID().equals(tax.getTaxCustCategoryID())) {
                return tax;
            }
            if (tax.getTaxCustCategoryID() != null) continue;
            defaulttax = tax;
        }
        return defaulttax;
    }
}

