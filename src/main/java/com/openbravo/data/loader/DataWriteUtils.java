/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.loader;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataWriteUtils {
    private static DateFormat tsf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    public static String getSQLValue(Object obj) {
        if (obj == null) {
            return "NULL";
        }
        if (obj instanceof Double) {
            return DataWriteUtils.getSQLValue((Double)obj);
        }
        if (obj instanceof Integer) {
            return DataWriteUtils.getSQLValue((Integer)obj);
        }
        if (obj instanceof Boolean) {
            return DataWriteUtils.getSQLValue((Boolean)obj);
        }
        if (obj instanceof String) {
            return DataWriteUtils.getSQLValue((String)obj);
        }
        if (obj instanceof Date) {
            return DataWriteUtils.getSQLValue((Date)obj);
        }
        return DataWriteUtils.getSQLValue(obj.toString());
    }

    public static String getSQLValue(Integer iValue) {
        if (iValue == null) {
            return "NULL";
        }
        return iValue.toString();
    }

    public static String getSQLValue(Double dValue) {
        if (dValue == null) {
            return "NULL";
        }
        return dValue.toString();
    }

    public static String getSQLValue(Boolean bValue) {
        if (bValue == null) {
            return "NULL";
        }
        return bValue != false ? "TRUE" : "FALSE";
    }

    public static String getSQLValue(String sValue) {
        if (sValue == null) {
            return "NULL";
        }
        return '\'' + DataWriteUtils.getEscaped(sValue) + '\'';
    }

    public static String getSQLValue(Date dValue) {
        if (dValue == null) {
            return "NULL";
        }
        return "{ts '" + tsf.format(dValue) + "'}";
    }

    public static String getEscaped(String sValue) {
        StringBuilder sb = new StringBuilder();
        block6: for (int i = 0; i < sValue.length(); ++i) {
            switch (sValue.charAt(i)) {
                case '\\': {
                    sb.append("\\\\");
                    continue block6;
                }
                case '\'': {
                    sb.append("\\'");
                    continue block6;
                }
                case '\n': {
                    sb.append("\\n");
                    continue block6;
                }
                case '\r': {
                    sb.append("\\r");
                    continue block6;
                }
                default: {
                    sb.append(sValue.charAt(i));
                }
            }
        }
        return sb.toString();
    }
}

