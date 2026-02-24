/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.forms;

import java.util.HashMap;

public class Payments {
    private Double amount;
    private Double tendered;
    private final HashMap<String, Double> paymentPaid = new HashMap();
    private final HashMap<String, Double> paymentTendered = new HashMap();
    private final HashMap<String, String> rtnMessage = new HashMap();
    private String name;
    private final HashMap<String, String> paymentVoucher = new HashMap();

    public void addPayment(String pName, Double pAmountPaid, Double pTendered, String rtnMsg) {
        if (this.paymentPaid.containsKey(pName)) {
            this.paymentPaid.put(pName, this.paymentPaid.get(pName) + pAmountPaid);
            this.paymentTendered.put(pName, this.paymentTendered.get(pName) + pTendered);
            this.rtnMessage.put(pName, rtnMsg);
        } else {
            this.paymentPaid.put(pName, pAmountPaid);
            this.paymentTendered.put(pName, pTendered);
            this.rtnMessage.put(pName, rtnMsg);
        }
    }

    public Double getTendered(String pName) {
        return this.paymentTendered.get(pName);
    }

    public Double getPaidAmount(String pName) {
        return this.paymentPaid.get(pName);
    }

    public Integer getSize() {
        return this.paymentPaid.size();
    }

    public String getRtnMessage(String pName) {
        return this.rtnMessage.get(pName);
    }

    public String getFirstElement() {
        String rtnKey = this.paymentPaid.keySet().iterator().next();
        return rtnKey;
    }

    public void removeFirst(String pName) {
        this.paymentPaid.remove(pName);
        this.paymentTendered.remove(pName);
        this.rtnMessage.remove(pName);
    }
}

