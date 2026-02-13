/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.inventory;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.Datas;
import com.openbravo.data.loader.PreparedSentence;
import com.openbravo.data.loader.SerializerWriteBasic;
import com.openbravo.data.loader.Session;
import java.util.Date;

public class AuditLogger {
    public static void logEvent(Session s, String type, String user, String product, Double qty, String oldValue, String newValue, String info) {
        try {
            Object[] values = new Object[]{new Date(), type, user, product, qty, oldValue, newValue, info};
            new PreparedSentence(s, "INSERT INTO AUDIT_LINES (DATE, TYPE, USER, PRODUCT, QTY, OLD_VALUE, NEW_VALUE, INFO) VALUES (?, ?, ?, ?, ?, ?, ?, ?)", new SerializerWriteBasic(Datas.TIMESTAMP, Datas.STRING, Datas.STRING, Datas.STRING, Datas.DOUBLE, Datas.STRING, Datas.STRING, Datas.STRING)).exec(values);
        }
        catch (BasicException e) {
            e.printStackTrace();
        }
    }
}

