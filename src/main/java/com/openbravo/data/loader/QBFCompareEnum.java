/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.loader;

import com.openbravo.data.loader.LocalRes;

public abstract class QBFCompareEnum {
    public static final QBFCompareEnum COMP_NONE = new QBFCompareEnum(0, "qbf.none"){

        @Override
        public String getExpression(String sField, String sSQLValue) {
            return null;
        }
    };
    public static final QBFCompareEnum COMP_ISNULL = new QBFCompareEnum(1, "qbf.null"){

        @Override
        public String getExpression(String sField, String sSQLValue) {
            return sField + " IS NULL";
        }
    };
    public static final QBFCompareEnum COMP_ISNOTNULL = new QBFCompareEnum(2, "qbf.notnull"){

        @Override
        public String getExpression(String sField, String sSQLValue) {
            return sField + " IS NOT NULL";
        }
    };
    public static final QBFCompareEnum COMP_RE = new QBFCompareEnum(3, "qbf.re"){

        @Override
        public String getExpression(String sField, String sSQLValue) {
            return sField + " LIKE " + sSQLValue;
        }
    };
    public static final QBFCompareEnum COMP_EQUALS = new QBFCompareEnum(3, "qbf.equals"){

        @Override
        public String getExpression(String sField, String sSQLValue) {
            return sField + " = " + sSQLValue;
        }
    };
    public static final QBFCompareEnum COMP_DISTINCT = new QBFCompareEnum(4, "qbf.distinct"){

        @Override
        public String getExpression(String sField, String sSQLValue) {
            return sField + " <> " + sSQLValue;
        }
    };
    public static final QBFCompareEnum COMP_GREATER = new QBFCompareEnum(5, "qbf.greater"){

        @Override
        public String getExpression(String sField, String sSQLValue) {
            return sField + " > " + sSQLValue;
        }
    };
    public static final QBFCompareEnum COMP_LESS = new QBFCompareEnum(6, "qbf.less"){

        @Override
        public String getExpression(String sField, String sSQLValue) {
            return sField + " < " + sSQLValue;
        }
    };
    public static final QBFCompareEnum COMP_GREATEROREQUALS = new QBFCompareEnum(7, "qbf.greaterequals"){

        @Override
        public String getExpression(String sField, String sSQLValue) {
            return sField + " >= " + sSQLValue;
        }
    };
    public static final QBFCompareEnum COMP_LESSOREQUALS = new QBFCompareEnum(8, "qbf.lessequals"){

        @Override
        public String getExpression(String sField, String sSQLValue) {
            return sField + " <= " + sSQLValue;
        }
    };
    private final int m_iValue;
    private final String m_sKey;

    private QBFCompareEnum(int iValue, String sKey) {
        this.m_iValue = iValue;
        this.m_sKey = sKey;
    }

    public int getCompareInt() {
        return this.m_iValue;
    }

    public String toString() {
        return LocalRes.getIntString(this.m_sKey);
    }

    public abstract String getExpression(String var1, String var2);
}

