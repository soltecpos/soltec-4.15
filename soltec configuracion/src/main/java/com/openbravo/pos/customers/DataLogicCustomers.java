/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.customers;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.DataParams;
import com.openbravo.data.loader.DataRead;
import com.openbravo.data.loader.Datas;
import com.openbravo.data.loader.ImageUtils;
import com.openbravo.data.loader.PreparedSentence;
import com.openbravo.data.loader.QBFBuilder;
import com.openbravo.data.loader.SentenceExec;
import com.openbravo.data.loader.SentenceExecTransaction;
import com.openbravo.data.loader.SentenceList;
import com.openbravo.data.loader.SerializerRead;
import com.openbravo.data.loader.SerializerReadBasic;
import com.openbravo.data.loader.SerializerWriteBasic;
import com.openbravo.data.loader.SerializerWriteBasicExt;
import com.openbravo.data.loader.SerializerWriteParams;
import com.openbravo.data.loader.SerializerWriteString;
import com.openbravo.data.loader.Session;
import com.openbravo.data.loader.StaticSentence;
import com.openbravo.data.loader.TableDefinition;
import com.openbravo.format.Formats;
import com.openbravo.pos.customers.CustomerInfo;
import com.openbravo.pos.customers.CustomerInfoExt;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.BeanFactoryDataSingle;
import com.openbravo.pos.voucher.VoucherInfo;
import java.util.List;

public class DataLogicCustomers
extends BeanFactoryDataSingle {
    protected Session s;
    private TableDefinition tcustomers;
    private static final Datas[] customerdatas = new Datas[]{Datas.STRING, Datas.TIMESTAMP, Datas.TIMESTAMP, Datas.STRING, Datas.STRING, Datas.STRING, Datas.STRING, Datas.INT, Datas.BOOLEAN, Datas.STRING};

    @Override
    public void init(Session s) {
        System.out.println("DEBUG: Initializing DataLogicCustomers with FE fields...");
        this.s = s;
        try {
            new StaticSentence(s, "ALTER TABLE customers ADD COLUMN DV VARCHAR(255)").exec();
        }
        catch (Exception exception) {
            // empty catch block
        }
        try {
            new StaticSentence(s, "ALTER TABLE customers ADD COLUMN DOC_TYPE VARCHAR(255)").exec();
        }
        catch (Exception exception) {
            // empty catch block
        }
        try {
            new StaticSentence(s, "ALTER TABLE customers ADD COLUMN PERSON_TYPE VARCHAR(255)").exec();
        }
        catch (Exception exception) {
            // empty catch block
        }
        try {
            new StaticSentence(s, "ALTER TABLE customers ADD COLUMN LIABILITY VARCHAR(255)").exec();
        }
        catch (Exception exception) {
            // empty catch block
        }
        try {
            new StaticSentence(s, "ALTER TABLE customers ADD COLUMN MUNICIPALITY_CODE VARCHAR(255)").exec();
        }
        catch (Exception exception) {
            // empty catch block
        }
        try {
            new StaticSentence(s, "ALTER TABLE customers ADD COLUMN REGIME_CODE VARCHAR(255)").exec();
        }
        catch (Exception exception) {
            // empty catch block
        }
        this.tcustomers = new TableDefinition(s, "customers", new String[]{"ID", "SEARCHKEY", "TAXID", "NAME", "TAXCATEGORY", "CARD", "MAXDEBT", "ADDRESS", "ADDRESS2", "POSTAL", "CITY", "REGION", "COUNTRY", "FIRSTNAME", "LASTNAME", "EMAIL", "PHONE", "PHONE2", "FAX", "NOTES", "VISIBLE", "CURDATE", "CURDEBT", "IMAGE", "ISVIP", "DISCOUNT", "DV", "DOC_TYPE", "PERSON_TYPE", "LIABILITY", "MUNICIPALITY_CODE", "REGIME_CODE"}, new String[]{"ID", AppLocal.getIntString("label.searchkey"), AppLocal.getIntString("label.taxid"), AppLocal.getIntString("label.name"), "TAXCATEGORY", "CARD", AppLocal.getIntString("label.maxdebt"), AppLocal.getIntString("label.address"), AppLocal.getIntString("label.address2"), AppLocal.getIntString("label.postal"), AppLocal.getIntString("label.city"), AppLocal.getIntString("label.region"), AppLocal.getIntString("label.country"), AppLocal.getIntString("label.firstname"), AppLocal.getIntString("label.lastname"), AppLocal.getIntString("label.email"), AppLocal.getIntString("label.phone"), AppLocal.getIntString("label.phone2"), AppLocal.getIntString("label.fax"), AppLocal.getIntString("label.notes"), "VISIBLE", AppLocal.getIntString("label.curdate"), AppLocal.getIntString("label.curdebt"), "IMAGE", "ISVIP", "DISCOUNT", "DV", "DOC_TYPE", "PERSON_TYPE", "LIABILITY", "MUNICIPALITY_CODE", "REGIME_CODE"}, new Datas[]{Datas.STRING, Datas.STRING, Datas.STRING, Datas.STRING, Datas.STRING, Datas.STRING, Datas.DOUBLE, Datas.STRING, Datas.STRING, Datas.STRING, Datas.STRING, Datas.STRING, Datas.STRING, Datas.STRING, Datas.STRING, Datas.STRING, Datas.STRING, Datas.STRING, Datas.STRING, Datas.STRING, Datas.BOOLEAN, Datas.TIMESTAMP, Datas.DOUBLE, Datas.IMAGE, Datas.BOOLEAN, Datas.DOUBLE, Datas.STRING, Datas.STRING, Datas.STRING, Datas.STRING, Datas.STRING, Datas.STRING}, new Formats[]{Formats.STRING, Formats.STRING, Formats.STRING, Formats.STRING, Formats.STRING, Formats.STRING, Formats.CURRENCY, Formats.STRING, Formats.STRING, Formats.STRING, Formats.STRING, Formats.STRING, Formats.STRING, Formats.STRING, Formats.STRING, Formats.STRING, Formats.STRING, Formats.STRING, Formats.STRING, Formats.STRING, Formats.BOOLEAN, Formats.TIMESTAMP, Formats.CURRENCY, Formats.NULL, Formats.BOOLEAN, Formats.DOUBLE, Formats.STRING, Formats.STRING, Formats.STRING, Formats.STRING, Formats.STRING, Formats.STRING}, new int[]{0});
    }

    public SentenceList<CustomerInfo> getCustomerList() {
        return new StaticSentence<Object[], CustomerInfo>(this.s, new QBFBuilder("SELECT ID, TAXID, SEARCHKEY, NAME, POSTAL, EMAIL, PHONE, IMAGE FROM customers WHERE VISIBLE = " + this.s.DB.TRUE() + " AND ?(QBF_FILTER) ORDER BY NAME", new String[]{"TAXID", "SEARCHKEY", "NAME", "POSTAL", "PHONE", "EMAIL"}), new SerializerWriteBasic(Datas.OBJECT, Datas.STRING, Datas.OBJECT, Datas.STRING, Datas.OBJECT, Datas.STRING, Datas.OBJECT, Datas.STRING, Datas.OBJECT, Datas.STRING, Datas.OBJECT, Datas.STRING), new SerializerRead<CustomerInfo>(){

            @Override
            public CustomerInfo readValues(DataRead dr) throws BasicException {
                CustomerInfo c = new CustomerInfo(dr.getString(1));
                c.setTaxid(dr.getString(2));
                c.setSearchkey(dr.getString(3));
                c.setName(dr.getString(4));
                c.setPostal(dr.getString(5));
                c.setPhone(dr.getString(6));
                c.setEmail(dr.getString(7));
                c.setImage(ImageUtils.readImage(dr.getBytes(8)));
                return c;
            }
        });
    }

    public List<CustomerInfo> getCustomersByKeyword(String keyword) throws BasicException {
        return new PreparedSentence<Object[], CustomerInfo>(this.s, "SELECT ID, TAXID, SEARCHKEY, NAME, POSTAL, EMAIL, PHONE, IMAGE FROM customers WHERE VISIBLE = " + this.s.DB.TRUE() + " AND (UPPER(NAME) LIKE UPPER(?) OR PHONE LIKE ?) ORDER BY NAME", new SerializerWriteBasic(Datas.STRING, Datas.STRING), new SerializerRead<CustomerInfo>(){

            @Override
            public CustomerInfo readValues(DataRead dr) throws BasicException {
                CustomerInfo c = new CustomerInfo(dr.getString(1));
                c.setTaxid(dr.getString(2));
                c.setSearchkey(dr.getString(3));
                c.setName(dr.getString(4));
                c.setPostal(dr.getString(5));
                c.setPhone(dr.getString(6));
                c.setEmail(dr.getString(7));
                c.setImage(ImageUtils.readImage(dr.getBytes(8)));
                return c;
            }
        }).list(keyword, keyword);
    }

    public final CustomerInfo getCustomerInfo(String id) throws BasicException {
        return (CustomerInfo)new PreparedSentence<String, CustomerInfo>(this.s, "SELECT ID, TAXID, SEARCHKEY, NAME, POSTAL FROM customers WHERE VISIBLE = " + this.s.DB.TRUE() + " AND ID = ?", SerializerWriteString.INSTANCE, new SerializerRead<CustomerInfo>(){

            @Override
            public CustomerInfo readValues(DataRead dr) throws BasicException {
                CustomerInfo c = new CustomerInfo(dr.getString(1));
                c.setTaxid(dr.getString(2));
                c.setSearchkey(dr.getString(3));
                c.setName(dr.getString(4));
                c.setPostal(dr.getString(5));
                return c;
            }
        }).find((Object)id);
    }

    public int updateCustomerExt(final CustomerInfoExt customer) throws BasicException {
        return new PreparedSentence(this.s, "UPDATE customers SET NOTES = ? WHERE ID = ?", SerializerWriteParams.INSTANCE).exec(new DataParams(){

            @Override
            public void writeValues() throws BasicException {
                this.setString(1, customer.getNotes());
                this.setString(2, customer.getId());
            }
        });
    }

    public final SentenceList<Object[]> getReservationsList() {
        return new PreparedSentence<Object[], Object[]>(this.s, "SELECT R.ID, R.CREATED, R.DATENEW, C.CUSTOMER, customers.TAXID, customers.SEARCHKEY, COALESCE(customers.NAME, R.TITLE),  R.CHAIRS, R.ISDONE, R.DESCRIPTION FROM reservations R LEFT OUTER JOIN RESERVATION_customers C ON R.ID = C.ID LEFT OUTER JOIN customers ON C.CUSTOMER = customers.ID WHERE R.DATENEW >= ? AND R.DATENEW < ?", new SerializerWriteBasic(Datas.TIMESTAMP, Datas.TIMESTAMP), new SerializerReadBasic(customerdatas));
    }

    public final SentenceExec<Object[]> getReservationsUpdate() {
        return new SentenceExecTransaction<Object[]>(this.s){

            @Override
            public int execInTransaction(Object[] params) throws BasicException {
                new PreparedSentence(DataLogicCustomers.this.s, "DELETE FROM RESERVATION_customers WHERE ID = ?", new SerializerWriteBasicExt(customerdatas, new int[]{0})).exec(params);
                if (params[3] != null) {
                    new PreparedSentence(DataLogicCustomers.this.s, "INSERT INTO RESERVATION_customers (ID, CUSTOMER) VALUES (?, ?)", new SerializerWriteBasicExt(customerdatas, new int[]{0, 3})).exec(params);
                }
                return new PreparedSentence(DataLogicCustomers.this.s, "UPDATE reservations SET ID = ?, CREATED = ?, DATENEW = ?, TITLE = ?, CHAIRS = ?, ISDONE = ?, DESCRIPTION = ? WHERE ID = ?", new SerializerWriteBasicExt(customerdatas, new int[]{0, 1, 2, 6, 7, 8, 9, 0})).exec(params);
            }
        };
    }

    public final SentenceExec<Object[]> getReservationsDelete() {
        return new SentenceExecTransaction<Object[]>(this.s){

            @Override
            public int execInTransaction(Object[] params) throws BasicException {
                new PreparedSentence(DataLogicCustomers.this.s, "DELETE FROM RESERVATION_customers WHERE ID = ?", new SerializerWriteBasicExt(customerdatas, new int[]{0})).exec(params);
                return new PreparedSentence(DataLogicCustomers.this.s, "DELETE FROM reservations WHERE ID = ?", new SerializerWriteBasicExt(customerdatas, new int[]{0})).exec(params);
            }
        };
    }

    public final SentenceExec<Object[]> getReservationsInsert() {
        return new SentenceExecTransaction<Object[]>(this.s){

            @Override
            public int execInTransaction(Object[] params) throws BasicException {
                int i = new PreparedSentence(DataLogicCustomers.this.s, "INSERT INTO reservations (ID, CREATED, DATENEW, TITLE, CHAIRS, ISDONE, DESCRIPTION) VALUES (?, ?, ?, ?, ?, ?, ?)", new SerializerWriteBasicExt(customerdatas, new int[]{0, 1, 2, 6, 7, 8, 9})).exec(params);
                if (params[3] != null) {
                    new PreparedSentence(DataLogicCustomers.this.s, "INSERT INTO RESERVATION_customers (ID, CUSTOMER) VALUES (?, ?)", new SerializerWriteBasicExt(customerdatas, new int[]{0, 3})).exec(params);
                }
                return i;
            }
        };
    }

    public final TableDefinition getTableCustomers() {
        return this.tcustomers;
    }

    public final PreparedSentence<String, String> getVoucherNumber() {
        return new PreparedSentence<String, String>(this.s, "SELECT SUBSTRING(MAX(VOUCHER_NUMBER),10,3) AS LAST_NUMBER FROM vouchers WHERE SUBSTRING(VOUCHER_NUMBER,1,8) = ?", SerializerWriteString.INSTANCE, new SerializerRead<String>(){

            @Override
            public String readValues(DataRead dr) throws BasicException {
                return dr.getString(1);
            }
        });
    }

    public final VoucherInfo getVoucherInfo(String id) throws BasicException {
        return (VoucherInfo)new PreparedSentence<String, VoucherInfo>(this.s, "SELECT vouchers.ID,vouchers.VOUCHER_NUMBER,vouchers.CUSTOMER,customers.NAME,AMOUNT FROM vouchers JOIN customers ON customers.id = vouchers.CUSTOMER WHERE STATUS='A' AND vouchers.ID=?", SerializerWriteString.INSTANCE, VoucherInfo.getSerializerRead()).find((Object)id);
    }

    public final VoucherInfo getVoucherInfoAll(String id) throws BasicException {
        return (VoucherInfo)new PreparedSentence<String, VoucherInfo>(this.s, "SELECT vouchers.ID,vouchers.VOUCHER_NUMBER,vouchers.CUSTOMER, customers.NAME,AMOUNT FROM vouchers JOIN customers ON customers.id = vouchers.CUSTOMER  WHERE vouchers.ID=?", SerializerWriteString.INSTANCE, VoucherInfo.getSerializerRead()).find((Object)id);
    }

    public int insertCustomerFE(Object[] customer) throws BasicException {
        String sql = "INSERT INTO customers (ID, SEARCHKEY, TAXID, NAME, TAXCATEGORY, CARD, MAXDEBT, ADDRESS, ADDRESS2, POSTAL, CITY, REGION, COUNTRY, FIRSTNAME, LASTNAME, EMAIL, PHONE, PHONE2, FAX, NOTES, VISIBLE, CURDATE, CURDEBT, IMAGE, ISVIP, DISCOUNT, DV, DOC_TYPE, PERSON_TYPE, LIABILITY, MUNICIPALITY_CODE, REGIME_CODE) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return new PreparedSentence(this.s, sql, new SerializerWriteBasic(Datas.STRING, Datas.STRING, Datas.STRING, Datas.STRING, Datas.STRING, Datas.STRING, Datas.DOUBLE, Datas.STRING, Datas.STRING, Datas.STRING, Datas.STRING, Datas.STRING, Datas.STRING, Datas.STRING, Datas.STRING, Datas.STRING, Datas.STRING, Datas.STRING, Datas.STRING, Datas.STRING, Datas.BOOLEAN, Datas.TIMESTAMP, Datas.DOUBLE, Datas.IMAGE, Datas.BOOLEAN, Datas.DOUBLE, Datas.STRING, Datas.STRING, Datas.STRING, Datas.STRING, Datas.STRING, Datas.STRING)).exec(customer);
    }
}

