/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.format;

import com.openbravo.basic.BasicException;
import com.openbravo.format.DoubleUtils;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Formats {
    public static final Formats NULL = new FormatsNULL();
    public static final Formats INT = new FormatsINT();
    public static final Formats STRING = new FormatsSTRING();
    public static final Formats DOUBLE = new FormatsDOUBLE();
    public static final Formats CURRENCY = new FormatsCURRENCY();
    public static final Formats PERCENT = new FormatsPERCENT();
    public static final Formats BOOLEAN = new FormatsBOOLEAN();
    public static final Formats TIMESTAMP = new FormatsTIMESTAMP();
    public static final Formats DATE = new FormatsDATE();
    public static final Formats TIME = new FormatsTIME();
    public static final Formats BYTEA = new FormatsBYTEA();
    public static final Formats HOURMIN = new FormatsHOURMIN();
    public static final Formats SIMPLEDATE = new FormatsSIMPLEDATE();
    private static NumberFormat m_integerformat = NumberFormat.getIntegerInstance();
    private static NumberFormat m_doubleformat = NumberFormat.getNumberInstance();
    private static NumberFormat m_currencyformat = NumberFormat.getCurrencyInstance();
    private static NumberFormat m_percentformat = new DecimalFormat("#,##0.##%");
    private static DateFormat m_dateformat = DateFormat.getDateInstance();
    private static DateFormat m_timeformat = DateFormat.getTimeInstance();
    private static DateFormat m_datetimeformat = DateFormat.getDateTimeInstance();
    private static final DateFormat m_hourminformat = new SimpleDateFormat("H:mm:ss");
    private static final DateFormat m_simpledate = new SimpleDateFormat("dd-MM-yyyy");

    protected Formats() {
    }

    public static int getCurrencyDecimals() {
        return m_currencyformat.getMaximumFractionDigits();
    }

    public String formatValue(Object value) {
        if (value == null) {
            return "";
        }
        return this.formatValueInt(value);
    }

    public Object parseValue(String value, Object defvalue) throws BasicException {
        if (value == null || "".equals(value)) {
            return defvalue;
        }
        try {
            return this.parseValueInt(value);
        }
        catch (ParseException e) {
            throw new BasicException(e.getMessage(), e);
        }
    }

    public Object parseValue(String value) throws BasicException {
        return this.parseValue(value, null);
    }

    public static void setIntegerPattern(String pattern) {
        m_integerformat = pattern == null || pattern.equals("") ? NumberFormat.getIntegerInstance() : new DecimalFormat(pattern);
    }

    public static void setDoublePattern(String pattern) {
        m_doubleformat = pattern == null || pattern.equals("") ? NumberFormat.getNumberInstance() : new DecimalFormat(pattern);
    }

    public static void setCurrencyPattern(String pattern) {
        m_currencyformat = pattern == null || pattern.equals("") ? NumberFormat.getCurrencyInstance() : new DecimalFormat(pattern);
    }

    public static void setPercentPattern(String pattern) {
        m_percentformat = pattern == null || pattern.equals("") ? new DecimalFormat("#,##0.##%") : new DecimalFormat(pattern);
    }

    public static void setDatePattern(String pattern) {
        m_dateformat = pattern == null || pattern.equals("") ? DateFormat.getDateInstance() : new SimpleDateFormat(pattern);
    }

    public static void setTimePattern(String pattern) {
        m_timeformat = pattern == null || pattern.equals("") ? DateFormat.getTimeInstance() : new SimpleDateFormat(pattern);
    }

    public static void setDateTimePattern(String pattern) {
        m_datetimeformat = pattern == null || pattern.equals("") ? DateFormat.getDateTimeInstance() : new SimpleDateFormat(pattern);
    }

    protected abstract String formatValueInt(Object var1);

    protected abstract Object parseValueInt(String var1) throws ParseException;

    public abstract int getAlignment();

    private static final class FormatsNULL
    extends Formats {
        private FormatsNULL() {
        }

        @Override
        protected String formatValueInt(Object value) {
            return null;
        }

        @Override
        protected Object parseValueInt(String value) throws ParseException {
            return null;
        }

        @Override
        public int getAlignment() {
            return 2;
        }
    }

    private static final class FormatsINT
    extends Formats {
        private FormatsINT() {
        }

        @Override
        protected String formatValueInt(Object value) {
            return m_integerformat.format(((Number)value).longValue());
        }

        @Override
        protected Object parseValueInt(String value) throws ParseException {
            return m_integerformat.parse(value).intValue();
        }

        @Override
        public int getAlignment() {
            return 4;
        }
    }

    private static final class FormatsSTRING
    extends Formats {
        private FormatsSTRING() {
        }

        @Override
        protected String formatValueInt(Object value) {
            return (String)value;
        }

        @Override
        protected Object parseValueInt(String value) throws ParseException {
            return value;
        }

        @Override
        public int getAlignment() {
            return 2;
        }
    }

    private static final class FormatsDOUBLE
    extends Formats {
        private FormatsDOUBLE() {
        }

        @Override
        protected String formatValueInt(Object value) {
            return m_doubleformat.format(DoubleUtils.fixDecimals((Number)value));
        }

        @Override
        protected Object parseValueInt(String value) throws ParseException {
            return m_doubleformat.parse(value).doubleValue();
        }

        @Override
        public int getAlignment() {
            return 4;
        }
    }

    private static final class FormatsCURRENCY
    extends Formats {
        private FormatsCURRENCY() {
        }

        @Override
        protected String formatValueInt(Object value) {
            return m_currencyformat.format(DoubleUtils.fixDecimals((Number)value));
        }

        @Override
        protected Object parseValueInt(String value) throws ParseException {
            try {
                return m_currencyformat.parse(value).doubleValue();
            }
            catch (ParseException e) {
                return m_doubleformat.parse(value).doubleValue();
            }
        }

        @Override
        public int getAlignment() {
            return 4;
        }
    }

    private static final class FormatsPERCENT
    extends Formats {
        private FormatsPERCENT() {
        }

        @Override
        protected String formatValueInt(Object value) {
            return m_percentformat.format(DoubleUtils.fixDecimals((Number)value));
        }

        @Override
        protected Object parseValueInt(String value) throws ParseException {
            try {
                return m_percentformat.parse(value).doubleValue();
            }
            catch (ParseException e) {
                return m_doubleformat.parse(value).doubleValue() / 100.0;
            }
        }

        @Override
        public int getAlignment() {
            return 4;
        }
    }

    private static final class FormatsBOOLEAN
    extends Formats {
        private FormatsBOOLEAN() {
        }

        @Override
        protected String formatValueInt(Object value) {
            return ((Boolean)value).toString();
        }

        @Override
        protected Object parseValueInt(String value) throws ParseException {
            return Boolean.valueOf(value);
        }

        @Override
        public int getAlignment() {
            return 0;
        }
    }

    private static final class FormatsTIMESTAMP
    extends Formats {
        private FormatsTIMESTAMP() {
        }

        @Override
        protected String formatValueInt(Object value) {
            return m_datetimeformat.format((Date)value);
        }

        @Override
        protected Object parseValueInt(String value) throws ParseException {
            try {
                return m_datetimeformat.parse(value);
            }
            catch (ParseException e) {
                return m_dateformat.parse(value);
            }
        }

        @Override
        public int getAlignment() {
            return 0;
        }
    }

    private static final class FormatsDATE
    extends Formats {
        private FormatsDATE() {
        }

        @Override
        protected String formatValueInt(Object value) {
            return m_dateformat.format((Date)value);
        }

        @Override
        protected Object parseValueInt(String value) throws ParseException {
            return m_dateformat.parse(value);
        }

        @Override
        public int getAlignment() {
            return 0;
        }
    }

    private static final class FormatsTIME
    extends Formats {
        private FormatsTIME() {
        }

        @Override
        protected String formatValueInt(Object value) {
            return m_timeformat.format((Date)value);
        }

        @Override
        protected Object parseValueInt(String value) throws ParseException {
            return m_timeformat.parse(value);
        }

        @Override
        public int getAlignment() {
            return 0;
        }
    }

    private static final class FormatsBYTEA
    extends Formats {
        private FormatsBYTEA() {
        }

        @Override
        protected String formatValueInt(Object value) {
            try {
                return new String((byte[])value, "UTF-8");
            }
            catch (UnsupportedEncodingException eu) {
                return "";
            }
        }

        @Override
        protected Object parseValueInt(String value) throws ParseException {
            try {
                return value.getBytes("UTF-8");
            }
            catch (UnsupportedEncodingException eu) {
                return new byte[0];
            }
        }

        @Override
        public int getAlignment() {
            return 10;
        }
    }

    private static final class FormatsHOURMIN
    extends Formats {
        private FormatsHOURMIN() {
        }

        @Override
        protected String formatValueInt(Object value) {
            return m_hourminformat.format(value);
        }

        @Override
        protected Date parseValueInt(String value) throws ParseException {
            return m_hourminformat.parse(value);
        }

        @Override
        public int getAlignment() {
            return 0;
        }
    }

    private static final class FormatsSIMPLEDATE
    extends Formats {
        private FormatsSIMPLEDATE() {
        }

        @Override
        protected String formatValueInt(Object value) {
            return m_simpledate.format(value);
        }

        @Override
        protected Date parseValueInt(String value) throws ParseException {
            return m_simpledate.parse(value);
        }

        @Override
        public int getAlignment() {
            return 0;
        }
    }
}

