/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.beans;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils {
    private DateUtils() {
    }

    public static Date getToday() {
        return DateUtils.getToday(new Date());
    }

    public static Date getToday(Date d) {
        GregorianCalendar ddate = new GregorianCalendar();
        ddate.setTime(d);
        GregorianCalendar ddateday = new GregorianCalendar(ddate.get(1), ddate.get(2), ddate.get(5));
        return ddateday.getTime();
    }

    public static Date getTodayMinutes() {
        return DateUtils.getTodayMinutes(new Date());
    }

    public static Date getTodayMinutes(Date d) {
        GregorianCalendar ddate = new GregorianCalendar();
        ddate.setTime(d);
        GregorianCalendar ddateday = new GregorianCalendar(ddate.get(1), ddate.get(2), ddate.get(5), ddate.get(11), ddate.get(12));
        return ddateday.getTime();
    }

    public static Date getTodayHours(Date d) {
        Calendar ddate = Calendar.getInstance();
        ddate.setTime(d);
        Calendar dNow = Calendar.getInstance();
        dNow.clear();
        dNow.set(ddate.get(1), ddate.get(2), ddate.get(5), ddate.get(11), 0, 0);
        return dNow.getTime();
    }

    public static Date getDate(Date day, Date hour) {
        Calendar dDay = Calendar.getInstance();
        dDay.setTime(day);
        Calendar dHour = Calendar.getInstance();
        dHour.setTime(hour);
        Calendar dNow = Calendar.getInstance();
        dNow.clear();
        dNow.set(dDay.get(1), dDay.get(2), dDay.get(5), dHour.get(11), dHour.get(12), dHour.get(13));
        return dNow.getTime();
    }
}

