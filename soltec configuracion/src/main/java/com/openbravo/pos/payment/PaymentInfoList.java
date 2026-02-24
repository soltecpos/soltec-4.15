/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.payment;

import com.openbravo.pos.payment.PaymentInfo;
import java.util.LinkedList;

public class PaymentInfoList {
    private LinkedList<PaymentInfo> m_apayment = new LinkedList();

    public double getTotal() {
        double dTotal = 0.0;
        for (PaymentInfo p : this.m_apayment) {
            dTotal += p.getTotal();
        }
        return dTotal;
    }

    public boolean isEmpty() {
        return this.m_apayment.isEmpty();
    }

    public void add(PaymentInfo p) {
        this.m_apayment.addLast(p);
    }

    public void removeLast() {
        this.m_apayment.removeLast();
    }

    public LinkedList<PaymentInfo> getPayments() {
        return this.m_apayment;
    }
}

