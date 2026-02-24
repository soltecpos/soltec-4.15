/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.sales;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.Datas;
import com.openbravo.data.loader.PreparedSentence;
import com.openbravo.data.loader.SerializerReadBasic;
import com.openbravo.data.loader.SerializerReadClass;
import com.openbravo.data.loader.SerializerWriteBasicExt;
import com.openbravo.data.loader.SerializerWriteString;
import com.openbravo.data.loader.Session;
import com.openbravo.data.loader.StaticSentence;
import com.openbravo.pos.forms.BeanFactoryDataSingle;
import com.openbravo.pos.sales.SharedTicketInfo;
import com.openbravo.pos.ticket.TicketInfo;
import java.util.List;

public class DataLogicReceipts
extends BeanFactoryDataSingle {
    private Session s;

    @Override
    public void init(Session s) {
        this.s = s;
    }

    public final TicketInfo getSharedTicket(String Id2) throws BasicException {
        if (Id2 == null) {
            return null;
        }
        Object[] record = (Object[])new StaticSentence<String, Object[]>(this.s, "SELECT CONTENT, LOCKED FROM sharedtickets WHERE ID = ?", SerializerWriteString.INSTANCE, new SerializerReadBasic(new Datas[]{Datas.SERIALIZABLE})).find((Object)Id2);
        return record == null ? null : (TicketInfo)record[0];
    }

    public final List<SharedTicketInfo> getSharedTicketList() throws BasicException {
        return new StaticSentence(this.s, "SELECT ID, NAME, CONTENT, APPUSER, PICKUPID, LOCKED FROM sharedtickets ORDER BY ID", null, new SerializerReadClass<SharedTicketInfo>(SharedTicketInfo.class)).list();
    }

    public final List<SharedTicketInfo> getUserSharedTicketList(String appuser) throws BasicException {
        String sql = "SELECT ID, NAME, CONTENT, APPUSER, PICKUPID, LOCKED FROM sharedtickets WHERE APPUSER =\"" + appuser + "\" ORDER BY ID";
        return new StaticSentence(this.s, sql, null, new SerializerReadClass<SharedTicketInfo>(SharedTicketInfo.class)).list();
    }

    public final void insertSharedTicket(String id, TicketInfo ticket, int pickupid) throws BasicException {
        Object[] values = new Object[]{id, ticket.getName(), ticket, ticket.getUser().getId(), pickupid};
        Datas[] datas = new Datas[]{Datas.STRING, Datas.STRING, Datas.SERIALIZABLE, Datas.STRING, Datas.INT};
        new PreparedSentence(this.s, "INSERT INTO sharedtickets (ID, NAME, CONTENT, APPUSER, PICKUPID) VALUES (?, ?, ?, ?, ?)", new SerializerWriteBasicExt(datas, new int[]{0, 1, 2, 3, 4})).exec(values);
    }

    public final void updateSharedTicket(String id, TicketInfo ticket, int pickupid) throws BasicException {
        Object[] values = new Object[]{id, ticket.getName(), ticket, ticket.getUser().getId(), pickupid};
        Datas[] datas = new Datas[]{Datas.STRING, Datas.STRING, Datas.SERIALIZABLE, Datas.STRING, Datas.INT};
        new PreparedSentence(this.s, "UPDATE sharedtickets SET NAME = ?, CONTENT = ?, APPUSER = ?, PICKUPID = ? WHERE ID = ?", new SerializerWriteBasicExt(datas, new int[]{1, 2, 3, 4, 0})).exec(values);
    }

    public final void updateRSharedTicket(String id, TicketInfo ticket, int pickupid) throws BasicException {
        Object[] values = new Object[]{id, ticket.getName(), ticket, pickupid};
        Datas[] datas = new Datas[]{Datas.STRING, Datas.STRING, Datas.SERIALIZABLE, Datas.INT};
        new PreparedSentence(this.s, "UPDATE sharedtickets SET NAME = ?, CONTENT = ?, PICKUPID = ? WHERE ID = ?", new SerializerWriteBasicExt(datas, new int[]{1, 2, 3, 0})).exec(values);
    }

    public final void lockSharedTicket(String id, String locked) throws BasicException {
        Object[] values = new Object[]{locked, System.currentTimeMillis(), id};
        Datas[] datas = new Datas[]{Datas.STRING, Datas.LONG, Datas.STRING};
        new PreparedSentence(this.s, "UPDATE sharedtickets SET LOCKED = ?, LOCKTIME = ? WHERE ID = ?", new SerializerWriteBasicExt(datas, new int[]{0, 1, 2})).exec(values);
    }

    public final void unlockSharedTicket(String id, String unlocked) throws BasicException {
        Object[] values = new Object[]{id, unlocked};
        Datas[] datas = new Datas[]{Datas.STRING, Datas.STRING};
        new PreparedSentence(this.s, "UPDATE sharedtickets SET LOCKED = ?, LOCKTIME = NULL WHERE ID = ?", new SerializerWriteBasicExt(datas, new int[]{1, 0})).exec(values);
    }

    public final void insertRSharedTicket(String id, TicketInfo ticket, int pickupid) throws BasicException {
        Object[] values = new Object[]{id, ticket.getName(), ticket, ticket.getUser(), ticket.getPickupId(), ticket.getHost()};
        Datas[] datas = new Datas[]{Datas.STRING, Datas.STRING, Datas.SERIALIZABLE, Datas.STRING, Datas.INT};
        new PreparedSentence(this.s, "INSERT INTO sharedtickets (ID, NAME, CONTENT, APPUSER, PICKUPID) VALUES (?, ?, ?, ?, ?)", new SerializerWriteBasicExt(datas, new int[]{0, 1, 2, 3, 4})).exec(values);
    }

    public final void deleteSharedTicket(String id) throws BasicException {
        new StaticSentence(this.s, "DELETE FROM sharedtickets WHERE ID = ?", SerializerWriteString.INSTANCE).exec(id);
    }

    public final void clearStaleLocks(long timeoutMillis) throws BasicException {
        Object[] values = new Object[]{System.currentTimeMillis() - timeoutMillis};
        Datas[] datas = new Datas[]{Datas.LONG};
        new PreparedSentence(this.s, "UPDATE sharedtickets SET LOCKED = NULL, LOCKTIME = NULL WHERE LOCKED IS NOT NULL AND LOCKTIME IS NOT NULL AND LOCKTIME < ?", new SerializerWriteBasicExt(datas, new int[]{0})).exec(values);
    }

    public final Integer getPickupId(String Id2) throws BasicException {
        if (Id2 == null) {
            return null;
        }
        Object[] record = (Object[])new StaticSentence<String, Object[]>(this.s, "SELECT PICKUPID FROM sharedtickets WHERE ID = ?", SerializerWriteString.INSTANCE, new SerializerReadBasic(new Datas[]{Datas.INT})).find((Object)Id2);
        return record == null ? 0 : (Integer)record[0];
    }

    public final String getUserId(String id) throws BasicException {
        Object[] userID = (Object[])new StaticSentence<String, Object[]>(this.s, "SELECT APPUSER FROM sharedtickets WHERE ID = ?", SerializerWriteString.INSTANCE, new SerializerReadBasic(new Datas[]{Datas.STRING})).find((Object)id);
        if (userID == null) {
            return null;
        }
        return (String)userID[0];
    }

    public final String getLockState(String id, String lockState) throws BasicException {
        Object[] state = (Object[])new StaticSentence<String, Object[]>(this.s, "SELECT LOCKED FROM sharedtickets WHERE ID = ?", SerializerWriteString.INSTANCE, new SerializerReadBasic(new Datas[]{Datas.STRING})).find((Object)id);
        return state == null ? null : (String)state[0];
    }
}

