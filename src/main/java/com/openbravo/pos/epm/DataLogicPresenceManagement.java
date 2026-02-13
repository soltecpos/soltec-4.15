/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.epm;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.DataRead;
import com.openbravo.data.loader.Datas;
import com.openbravo.data.loader.PreparedSentence;
import com.openbravo.data.loader.QBFBuilder;
import com.openbravo.data.loader.SentenceExec;
import com.openbravo.data.loader.SentenceFind;
import com.openbravo.data.loader.SentenceList;
import com.openbravo.data.loader.SerializerRead;
import com.openbravo.data.loader.SerializerReadDate;
import com.openbravo.data.loader.SerializerReadString;
import com.openbravo.data.loader.SerializerWriteBasic;
import com.openbravo.data.loader.SerializerWriteString;
import com.openbravo.data.loader.Session;
import com.openbravo.data.loader.StaticSentence;
import com.openbravo.data.loader.TableDefinition;
import com.openbravo.format.Formats;
import com.openbravo.pos.epm.Break;
import com.openbravo.pos.epm.BreaksInfo;
import com.openbravo.pos.epm.EmployeeInfo;
import com.openbravo.pos.epm.EmployeeInfoExt;
import com.openbravo.pos.epm.LeavesInfo;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.BeanFactoryDataSingle;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class DataLogicPresenceManagement
extends BeanFactoryDataSingle {
    protected Session s;
    private SentenceExec<Object[]> m_checkin;
    private SentenceExec<Object[]> m_checkout;
    private SentenceFind m_checkdate;
    private SentenceList<Object> m_breaksvisible;
    private SentenceExec<Object[]> m_startbreak;
    private SentenceExec<Object[]> m_endbreak;
    private SentenceFind m_isonbreak;
    private SentenceFind m_isonleave;
    private SentenceFind m_shiftid;
    private SentenceFind m_lastcheckin;
    private SentenceFind m_lastcheckout;
    private SentenceFind m_startbreaktime;
    private SentenceFind m_lastbreakid;
    private SentenceFind m_breakname;
    private SerializerRead<Object> breakread;
    private TableDefinition tbreaks;
    private TableDefinition tleaves;

    @Override
    public void init(Session s) {
        this.s = s;
        this.breakread = new SerializerRead<Object>(){

            @Override
            public Object readValues(DataRead dr) throws BasicException {
                return new Break(dr.getString(1), dr.getString(2), dr.getString(3), dr.getBoolean(4));
            }
        };
        this.tbreaks = new TableDefinition(s, "breaks", new String[]{"ID", "NAME", "NOTES", "VISIBLE"}, new String[]{"ID", AppLocal.getIntString("label.epm.employee"), AppLocal.getIntString("label.epm.notes"), "VISIBLE"}, new Datas[]{Datas.STRING, Datas.STRING, Datas.STRING, Datas.BOOLEAN}, new Formats[]{Formats.STRING, Formats.STRING, Formats.STRING, Formats.BOOLEAN}, new int[]{0});
        this.tleaves = new TableDefinition(s, "leaves", new String[]{"ID", "PPLID", "NAME", "STARTDATE", "ENDDATE", "NOTES"}, new String[]{"ID", AppLocal.getIntString("label.epm.employee.id"), AppLocal.getIntString("label.epm.employee"), AppLocal.getIntString("label.StartDate"), AppLocal.getIntString("label.EndDate"), AppLocal.getIntString("label.notes")}, new Datas[]{Datas.STRING, Datas.STRING, Datas.STRING, Datas.TIMESTAMP, Datas.TIMESTAMP, Datas.STRING}, new Formats[]{Formats.STRING, Formats.STRING, Formats.STRING, Formats.TIMESTAMP, Formats.TIMESTAMP, Formats.STRING}, new int[]{0});
        this.m_breaksvisible = new StaticSentence(s, "SELECT ID, NAME, NOTES, VISIBLE FROM breaks WHERE VISIBLE = " + s.DB.TRUE(), null, this.breakread);
        this.m_checkin = new PreparedSentence(s, "INSERT INTO shifts(ID, STARTSHIFT, PPLID) VALUES (?, ?, ?)", new SerializerWriteBasic(Datas.STRING, Datas.TIMESTAMP, Datas.STRING));
        this.m_checkout = new StaticSentence(s, "UPDATE shifts SET ENDSHIFT = ? WHERE ENDSHIFT IS NULL AND PPLID = ?", new SerializerWriteBasic(Datas.TIMESTAMP, Datas.STRING));
        this.m_checkdate = new StaticSentence<String, String>(s, "SELECT COUNT(*) FROM shifts WHERE ENDSHIFT IS NULL AND PPLID = ?", SerializerWriteString.INSTANCE, SerializerReadString.INSTANCE);
        this.m_startbreak = new PreparedSentence(s, "INSERT INTO shift_breaks(ID, SHIFTID, BREAKID, STARTTIME) VALUES (?, ?, ?, ?)", new SerializerWriteBasic(Datas.STRING, Datas.STRING, Datas.STRING, Datas.TIMESTAMP));
        this.m_endbreak = new StaticSentence(s, "UPDATE shift_breaks SET ENDTIME = ? WHERE ENDTIME IS NULL AND SHIFTID = ?", new SerializerWriteBasic(Datas.TIMESTAMP, Datas.STRING));
        this.m_isonbreak = new StaticSentence<String, String>(s, "SELECT COUNT(*) FROM shift_breaks WHERE ENDTIME IS NULL", SerializerWriteString.INSTANCE, SerializerReadString.INSTANCE);
        this.m_shiftid = new StaticSentence<String, String>(s, "SELECT ID FROM shifts WHERE ENDSHIFT IS NULL AND PPLID = ?", SerializerWriteString.INSTANCE, SerializerReadString.INSTANCE);
        this.m_isonleave = new StaticSentence<Object[], String>(s, "SELECT COUNT(*) FROM leaves WHERE STARTDATE < ? AND ENDDATE > ? AND PPLID = ?", new SerializerWriteBasic(Datas.TIMESTAMP, Datas.TIMESTAMP, Datas.STRING), SerializerReadString.INSTANCE);
        this.m_lastcheckin = new StaticSentence<String, Date>(s, "SELECT STARTSHIFT FROM shifts WHERE ENDSHIFT IS NULL AND PPLID = ?", SerializerWriteString.INSTANCE, SerializerReadDate.INSTANCE);
        this.m_lastcheckout = new StaticSentence<String, Date>(s, "SELECT MAX(ENDSHIFT) FROM shifts WHERE PPLID = ?", SerializerWriteString.INSTANCE, SerializerReadDate.INSTANCE);
        this.m_startbreaktime = new StaticSentence<String, Date>(s, "SELECT STARTTIME FROM shift_breaks WHERE ENDTIME IS NULL AND SHIFTID = ?", SerializerWriteString.INSTANCE, SerializerReadDate.INSTANCE);
        this.m_lastbreakid = new StaticSentence<String, String>(s, "SELECT BREAKID FROM shift_breaks WHERE ENDTIME IS NULL AND SHIFTID = ?", SerializerWriteString.INSTANCE, SerializerReadString.INSTANCE);
        this.m_breakname = new StaticSentence<String, String>(s, "SELECT NAME FROM breaks WHERE ID = ?", SerializerWriteString.INSTANCE, SerializerReadString.INSTANCE);
    }

    public final SentenceList<BreaksInfo> getBreaksList() {
        return new StaticSentence(this.s, "SELECT ID, NAME FROM breaks ORDER BY NAME", null, new SerializerRead<BreaksInfo>(){

            @Override
            public BreaksInfo readValues(DataRead dr) throws BasicException {
                return new BreaksInfo(dr.getString(1), dr.getString(2));
            }
        });
    }

    public final SentenceList<LeavesInfo> getLeavesList() {
        return new StaticSentence(this.s, "SELECT ID, PPLID, NAME, STARTDATE, ENDDATE, NOTES FROM leaves ORDER BY NAME", null, new SerializerRead<LeavesInfo>(){

            @Override
            public LeavesInfo readValues(DataRead dr) throws BasicException {
                return new LeavesInfo(dr.getString(1), dr.getString(2), dr.getString(3), dr.getString(4), dr.getString(5), dr.getString(6));
            }
        });
    }

    public final List listBreaksVisible() throws BasicException {
        return this.m_breaksvisible.list();
    }

    public final void CheckIn(String user) throws BasicException {
        Object[] value = new Object[]{UUID.randomUUID().toString(), new Date(), user};
        this.m_checkin.exec(value);
    }

    public final void CheckOut(String user) throws BasicException {
        Object[] value = new Object[]{new Date(), user};
        this.m_checkout.exec(value);
    }

    public final boolean IsCheckedIn(String user) throws BasicException {
        String Data = (String)this.m_checkdate.find((Object)user);
        return !Data.equals("0");
    }

    public final void StartBreak(String UserID, String BreakID) throws BasicException {
        String ShiftID = this.GetShiftID(UserID);
        Object[] value = new Object[]{UUID.randomUUID().toString(), ShiftID, BreakID, new Date()};
        this.m_startbreak.exec(value);
    }

    public final void EndBreak(String UserID) throws BasicException {
        String ShiftID = this.GetShiftID(UserID);
        Object[] value = new Object[]{new Date(), ShiftID};
        this.m_endbreak.exec(value);
    }

    public final boolean IsOnBreak(String user) throws BasicException {
        String ShiftID = this.GetShiftID(user);
        String Data = (String)this.m_isonbreak.find((Object)ShiftID);
        return !Data.equals("0");
    }

    public final String GetShiftID(String user) throws BasicException {
        return (String)this.m_shiftid.find((Object)user);
    }

    public final Date GetLastCheckIn(String user) throws BasicException {
        return (Date)this.m_lastcheckin.find((Object)user);
    }

    public final Date GetLastCheckOut(String user) throws BasicException {
        return (Date)this.m_lastcheckout.find((Object)user);
    }

    public final Date GetStartBreakTime(String ShiftID) throws BasicException {
        return (Date)this.m_startbreaktime.find((Object)ShiftID);
    }

    public final String GetLastBreakID(String ShiftID) throws BasicException {
        return (String)this.m_lastbreakid.find((Object)ShiftID);
    }

    public final String GetLastBreakName(String ShiftID) throws BasicException {
        String BreakID = this.GetLastBreakID(ShiftID);
        return (String)this.m_breakname.find((Object)BreakID);
    }

    public final Object[] GetLastBreak(String user) throws BasicException {
        String ShiftID = this.GetShiftID(user);
        Date StartBreakTime = this.GetStartBreakTime(ShiftID);
        String BreakName = this.GetLastBreakName(ShiftID);
        return new Object[]{BreakName, StartBreakTime};
    }

    public final boolean IsOnLeave(String user) throws BasicException {
        Object[] value = new Object[]{new Date(), new Date(), user};
        String Data = (String)this.m_isonleave.find(value);
        return !Data.equals("0");
    }

    public SentenceList<EmployeeInfo> getEmployeeList() {
        return new StaticSentence<Object[], EmployeeInfo>(this.s, new QBFBuilder("SELECT ID, NAME FROM people WHERE ROLE != '0' AND VISIBLE = " + this.s.DB.TRUE() + " AND ?(QBF_FILTER) ORDER BY NAME", new String[]{"NAME"}), new SerializerWriteBasic(Datas.OBJECT, Datas.STRING), new SerializerRead<EmployeeInfo>(){

            @Override
            public EmployeeInfo readValues(DataRead dr) throws BasicException {
                EmployeeInfo c = new EmployeeInfo(dr.getString(1));
                c.setName(dr.getString(2));
                return c;
            }
        });
    }

    public void BlockEmployee(String user) throws BasicException {
        boolean isOnBreak = this.IsOnBreak(user);
        if (isOnBreak) {
            this.EndBreak(user);
        }
        this.CheckOut(user);
    }

    TableDefinition getTableBreaks() {
        return this.tbreaks;
    }

    TableDefinition getTableLeaves() {
        return this.tleaves;
    }

    public EmployeeInfoExt loadEmployeeExt(String id) throws BasicException {
        return (EmployeeInfoExt)new PreparedSentence<String, EmployeeInfoExt>(this.s, "SELECT ID, NAME FROM people WHERE ID = ?", SerializerWriteString.INSTANCE, new EmployeeExtRead()).find((Object)id);
    }

    protected static class EmployeeExtRead
    implements SerializerRead<EmployeeInfoExt> {
        protected EmployeeExtRead() {
        }

        @Override
        public EmployeeInfoExt readValues(DataRead dr) throws BasicException {
            EmployeeInfoExt c = new EmployeeInfoExt(dr.getString(1));
            c.setName(dr.getString(2));
            return c;
        }
    }
}

