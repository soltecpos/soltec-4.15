/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.data.loader;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.DataRead;
import com.openbravo.data.loader.DataWrite;
import com.openbravo.data.loader.DataWriteUtils;
import com.openbravo.data.loader.ImageUtils;
import java.awt.image.BufferedImage;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Datas {
    public static final Datas INT = new DatasINT();
    public static final Datas STRING = new DatasSTRING();
    public static final Datas DOUBLE = new DatasDOUBLE();
    public static final Datas BOOLEAN = new DatasBOOLEAN();
    public static final Datas LONG = new DatasLONG();
    public static final Datas TIMESTAMP = new DatasTIMESTAMP();
    public static final Datas BYTES = new DatasBYTES();
    public static final Datas IMAGE = new DatasIMAGE();
    public static final Datas OBJECT = new DatasOBJECT();
    public static final Datas SERIALIZABLE = new DatasSERIALIZABLE();
    public static final Datas NULL = new DatasNULL();
    private static DateFormat tsf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    private Datas() {
    }

    public abstract Object getValue(DataRead var1, int var2) throws BasicException;

    public abstract void setValue(DataWrite var1, int var2, Object var3) throws BasicException;

    public abstract Class<?> getClassValue();

    protected abstract String toStringAbstract(Object var1);

    protected abstract int compareAbstract(Object var1, Object var2);

    public String toString(Object value) {
        if (value == null) {
            return "null";
        }
        return this.toStringAbstract(value);
    }

    public int compare(Object o1, Object o2) {
        if (o1 == null) {
            if (o2 == null) {
                return 0;
            }
            return -1;
        }
        if (o2 == null) {
            return 1;
        }
        return this.compareAbstract(o1, o2);
    }

    private static final class DatasINT
    extends Datas {
        private DatasINT() {
        }

        @Override
        public Object getValue(DataRead dr, int i) throws BasicException {
            return dr.getInt(i);
        }

        @Override
        public void setValue(DataWrite dw, int i, Object value) throws BasicException {
            dw.setInt(i, (Integer)value);
        }

        @Override
        public Class<?> getClassValue() {
            return Integer.class;
        }

        @Override
        protected String toStringAbstract(Object value) {
            return ((Integer)value).toString();
        }

        @Override
        protected int compareAbstract(Object o1, Object o2) {
            return ((Integer)o1).compareTo((Integer)o2);
        }
    }

    private static final class DatasSTRING
    extends Datas {
        private DatasSTRING() {
        }

        @Override
        public Object getValue(DataRead dr, int i) throws BasicException {
            return dr.getString(i);
        }

        @Override
        public void setValue(DataWrite dw, int i, Object value) throws BasicException {
            dw.setString(i, (String)value);
        }

        @Override
        public Class<?> getClassValue() {
            return String.class;
        }

        @Override
        protected String toStringAbstract(Object value) {
            return "'" + DataWriteUtils.getEscaped((String)value) + "'";
        }

        @Override
        protected int compareAbstract(Object o1, Object o2) {
            return ((String)o1).compareTo((String)o2);
        }
    }

    private static final class DatasDOUBLE
    extends Datas {
        private DatasDOUBLE() {
        }

        @Override
        public Object getValue(DataRead dr, int i) throws BasicException {
            return dr.getDouble(i);
        }

        @Override
        public void setValue(DataWrite dw, int i, Object value) throws BasicException {
            dw.setDouble(i, (Double)value);
        }

        @Override
        public Class<?> getClassValue() {
            return Double.class;
        }

        @Override
        protected String toStringAbstract(Object value) {
            return ((Double)value).toString();
        }

        @Override
        protected int compareAbstract(Object o1, Object o2) {
            return ((Double)o1).compareTo((Double)o2);
        }
    }

    private static final class DatasBOOLEAN
    extends Datas {
        private DatasBOOLEAN() {
        }

        @Override
        public Object getValue(DataRead dr, int i) throws BasicException {
            return dr.getBoolean(i);
        }

        @Override
        public void setValue(DataWrite dw, int i, Object value) throws BasicException {
            dw.setBoolean(i, (Boolean)value);
        }

        @Override
        public Class<?> getClassValue() {
            return Boolean.class;
        }

        @Override
        protected String toStringAbstract(Object value) {
            return ((Boolean)value).toString();
        }

        @Override
        protected int compareAbstract(Object o1, Object o2) {
            return ((Boolean)o1).compareTo((Boolean)o2);
        }
    }

    private static final class DatasLONG
    extends Datas {
        private DatasLONG() {
        }

        @Override
        public Object getValue(DataRead dr, int i) throws BasicException {
            Object o = dr.getObject(i);
            if (o == null) {
                return null;
            }
            if (o instanceof Long) {
                return o;
            }
            if (o instanceof Integer) {
                return ((Integer)o).longValue();
            }
            if (o instanceof Double) {
                return ((Double)o).longValue();
            }
            try {
                return Long.valueOf(o.toString());
            }
            catch (NumberFormatException e) {
                return null;
            }
        }

        @Override
        public void setValue(DataWrite dw, int i, Object value) throws BasicException {
            dw.setObject(i, value);
        }

        @Override
        public Class<?> getClassValue() {
            return Long.class;
        }

        @Override
        protected String toStringAbstract(Object value) {
            return ((Long)value).toString();
        }

        @Override
        protected int compareAbstract(Object o1, Object o2) {
            return ((Long)o1).compareTo((Long)o2);
        }
    }

    private static final class DatasTIMESTAMP
    extends Datas {
        private DatasTIMESTAMP() {
        }

        @Override
        public Object getValue(DataRead dr, int i) throws BasicException {
            return dr.getTimestamp(i);
        }

        @Override
        public void setValue(DataWrite dw, int i, Object value) throws BasicException {
            dw.setTimestamp(i, (Date)value);
        }

        @Override
        public Class<?> getClassValue() {
            return Date.class;
        }

        @Override
        protected String toStringAbstract(Object value) {
            return tsf.format(value);
        }

        @Override
        protected int compareAbstract(Object o1, Object o2) {
            return ((Date)o1).compareTo((Date)o2);
        }
    }

    private static final class DatasBYTES
    extends Datas {
        private DatasBYTES() {
        }

        @Override
        public Object getValue(DataRead dr, int i) throws BasicException {
            return dr.getBytes(i);
        }

        @Override
        public void setValue(DataWrite dw, int i, Object value) throws BasicException {
            dw.setBytes(i, (byte[])value);
        }

        @Override
        public Class<?> getClassValue() {
            return byte[].class;
        }

        @Override
        protected String toStringAbstract(Object value) {
            return "0x" + ImageUtils.bytes2hex((byte[])value);
        }

        @Override
        protected int compareAbstract(Object o1, Object o2) {
            throw new UnsupportedOperationException();
        }
    }

    private static final class DatasIMAGE
    extends Datas {
        private DatasIMAGE() {
        }

        @Override
        public Object getValue(DataRead dr, int i) throws BasicException {
            return ImageUtils.readImage(dr.getBytes(i));
        }

        @Override
        public void setValue(DataWrite dw, int i, Object value) throws BasicException {
            dw.setBytes(i, ImageUtils.writeImage((BufferedImage)value));
        }

        @Override
        public Class<?> getClassValue() {
            return BufferedImage.class;
        }

        @Override
        protected String toStringAbstract(Object value) {
            return "0x" + ImageUtils.bytes2hex(ImageUtils.writeImage((BufferedImage)value));
        }

        @Override
        protected int compareAbstract(Object o1, Object o2) {
            throw new UnsupportedOperationException();
        }
    }

    private static final class DatasOBJECT
    extends Datas {
        private DatasOBJECT() {
        }

        @Override
        public Object getValue(DataRead dr, int i) throws BasicException {
            return dr.getObject(i);
        }

        @Override
        public void setValue(DataWrite dw, int i, Object value) throws BasicException {
            dw.setObject(i, value);
        }

        @Override
        public Class<?> getClassValue() {
            return Object.class;
        }

        @Override
        protected String toStringAbstract(Object value) {
            return "0x" + ImageUtils.bytes2hex(ImageUtils.writeSerializable(value));
        }

        @Override
        protected int compareAbstract(Object o1, Object o2) {
            throw new UnsupportedOperationException();
        }
    }

    private static final class DatasSERIALIZABLE
    extends Datas {
        private DatasSERIALIZABLE() {
        }

        @Override
        public Object getValue(DataRead dr, int i) throws BasicException {
            return ImageUtils.readSerializable(dr.getBytes(i));
        }

        @Override
        public void setValue(DataWrite dw, int i, Object value) throws BasicException {
            dw.setBytes(i, ImageUtils.writeSerializable(value));
        }

        @Override
        public Class<?> getClassValue() {
            return Object.class;
        }

        @Override
        protected String toStringAbstract(Object value) {
            return "0x" + ImageUtils.bytes2hex(ImageUtils.writeSerializable(value));
        }

        @Override
        protected int compareAbstract(Object o1, Object o2) {
            throw new UnsupportedOperationException();
        }
    }

    private static final class DatasNULL
    extends Datas {
        private DatasNULL() {
        }

        @Override
        public Object getValue(DataRead dr, int i) throws BasicException {
            return null;
        }

        @Override
        public void setValue(DataWrite dw, int i, Object value) throws BasicException {
        }

        @Override
        public Class<?> getClassValue() {
            return Object.class;
        }

        @Override
        protected String toStringAbstract(Object value) {
            return "null";
        }

        @Override
        protected int compareAbstract(Object o1, Object o2) {
            throw new UnsupportedOperationException();
        }
    }
}

