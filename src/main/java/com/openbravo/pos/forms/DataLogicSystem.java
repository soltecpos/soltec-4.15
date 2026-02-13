/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.forms;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.DataRead;
import com.openbravo.data.loader.Datas;
import com.openbravo.data.loader.ImageUtils;
import com.openbravo.data.loader.PreparedSentence;
import com.openbravo.data.loader.SentenceExec;
import com.openbravo.data.loader.SentenceFind;
import com.openbravo.data.loader.SentenceList;
import com.openbravo.data.loader.SerializerRead;
import com.openbravo.data.loader.SerializerReadBasic;
import com.openbravo.data.loader.SerializerReadBytes;
import com.openbravo.data.loader.SerializerReadInteger;
import com.openbravo.data.loader.SerializerReadString;
import com.openbravo.data.loader.SerializerWriteBasic;
import com.openbravo.data.loader.SerializerWriteBasicExt;
import com.openbravo.data.loader.SerializerWriteString;
import com.openbravo.data.loader.Session;
import com.openbravo.data.loader.StaticSentence;
import com.openbravo.format.Formats;
import com.openbravo.pos.forms.AppUser;
import com.openbravo.pos.forms.BeanFactoryDataSingle;
import com.openbravo.pos.util.ThumbNailBuilder;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class DataLogicSystem
extends BeanFactoryDataSingle {
    protected String m_sInitScript;
    private SentenceFind<String> m_version;
    private SentenceExec<Object> m_dummy;
    private String m_dbVersion;
    protected SentenceList<AppUser> m_peoplevisible;
    protected SentenceFind<AppUser> m_peoplebycard;
    protected SerializerRead<AppUser> peopleread;
    protected SentenceList<String> m_permissionlist;
    protected SerializerRead<String> productIdRead;
    protected SerializerRead<String> customerIdRead;
    private SentenceFind<byte[]> m_rolepermissions;
    private SentenceExec<Object[]> m_changepassword;
    private SentenceFind<String> m_locationfind;
    private SentenceExec<Object[]> m_insertCSVEntry;
    private SentenceFind<String> m_getProductAllFields;
    private SentenceFind<String> m_getProductRefAndCode;
    private SentenceFind<String> m_getProductRefAndName;
    private SentenceFind<String> m_getProductCodeAndName;
    private SentenceFind<String> m_getProductByReference;
    private SentenceFind<String> m_getProductByCode;
    private SentenceFind<String> m_getProductByName;
    private SentenceExec<Object[]> m_insertCustomerCSVEntry;
    private SentenceFind<String> m_getCustomerAllFields;
    private SentenceFind<String> m_getCustomerSearchKeyAndName;
    private SentenceFind<String> m_getCustomerBySearchKey;
    private SentenceFind<String> m_getCustomerByName;
    private SentenceFind<byte[]> m_resourcebytes;
    private SentenceExec<Object[]> m_resourcebytesinsert;
    private SentenceExec<Object[]> m_resourcebytesupdate;
    protected SentenceFind<Integer> m_sequencecash;
    protected SentenceFind<Object[]> m_activecash;
    protected SentenceFind<Object[]> m_closedcash;
    protected SentenceExec<Object[]> m_insertcash;
    protected SentenceExec<Object[]> m_draweropened;
    protected SentenceExec<Object[]> m_updatepermissions;
    protected SentenceExec<Object[]> m_lineremoved;
    protected SentenceExec<Object[]> m_insertAuditEntry;
    private String SQL;
    private Map<String, byte[]> resourcescache;
    private SentenceList<Object> m_voucherlist;

    @Override
    public void init(Session s) {
        this.m_sInitScript = "/com/openbravo/pos/scripts/" + s.DB.getName();
        this.m_dbVersion = s.DB.getName();
        this.m_version = new PreparedSentence<String, String>(s, "SELECT VERSION FROM applications WHERE ID = ?", SerializerWriteString.INSTANCE, SerializerReadString.INSTANCE);
        this.m_dummy = new StaticSentence(s, "SELECT * FROM people WHERE 1 = 0");
        ThumbNailBuilder tnb = new ThumbNailBuilder(32, 32, "com/openbravo/images/user.png");
        this.peopleread = dr -> {
            java.awt.Image img = ImageUtils.readImage(dr.getBytes(6));
             ImageIcon icon = (img != null) ? new ImageIcon(tnb.getThumbNail(img)) : null;
             return new AppUser(dr.getString(1), dr.getString(2), dr.getString(3), dr.getString(4), dr.getString(5), icon);
        };
        this.productIdRead = new SerializerRead<String>(){

            @Override
            public String readValues(DataRead dr) throws BasicException {
                return dr.getString(1);
            }
        };
        this.m_getProductAllFields = new PreparedSentence<Object[], String>(s, "SELECT ID FROM products WHERE REFERENCE=? AND CODE=? AND NAME=? ", new SerializerWriteBasic(Datas.STRING, Datas.STRING, Datas.STRING), this.productIdRead);
        this.m_getProductRefAndCode = new PreparedSentence<Object[], String>(s, "SELECT ID FROM products WHERE REFERENCE=? AND CODE=?", new SerializerWriteBasic(Datas.STRING, Datas.STRING), this.productIdRead);
        this.m_getProductRefAndName = new PreparedSentence<Object[], String>(s, "SELECT ID FROM products WHERE REFERENCE=? AND NAME=? ", new SerializerWriteBasic(Datas.STRING, Datas.STRING), this.productIdRead);
        this.m_getProductCodeAndName = new PreparedSentence<Object[], String>(s, "SELECT ID FROM products WHERE CODE=? AND NAME=? ", new SerializerWriteBasic(Datas.STRING, Datas.STRING), this.productIdRead);
        this.m_getProductByReference = new PreparedSentence<String, String>(s, "SELECT ID FROM products WHERE REFERENCE=? ", SerializerWriteString.INSTANCE, this.productIdRead);
        this.m_getProductByCode = new PreparedSentence<String, String>(s, "SELECT ID FROM products WHERE CODE=? ", SerializerWriteString.INSTANCE, this.productIdRead);
        this.m_getProductByName = new PreparedSentence<String, String>(s, "SELECT ID FROM products WHERE NAME=? ", SerializerWriteString.INSTANCE, this.productIdRead);
        this.customerIdRead = new SerializerRead<String>(){

            @Override
            public String readValues(DataRead dr) throws BasicException {
                return dr.getString(1);
            }
        };
        this.m_getCustomerAllFields = new PreparedSentence<Object[], String>(s, "SELECT ID FROM customers WHERE SEARCHKEY=? AND NAME=? ", new SerializerWriteBasic(Datas.STRING, Datas.STRING), this.customerIdRead);
        this.m_getCustomerSearchKeyAndName = new PreparedSentence<Object[], String>(s, "SELECT ID FROM customers WHERE SEARCHKEY=? AND NAME=? ", new SerializerWriteBasic(Datas.STRING, Datas.STRING), this.customerIdRead);
        this.m_getCustomerBySearchKey = new PreparedSentence<String, String>(s, "SELECT ID FROM customers WHERE SEARCHKEY=? ", SerializerWriteString.INSTANCE, this.customerIdRead);
        this.m_getCustomerByName = new PreparedSentence<String, String>(s, "SELECT ID FROM customers WHERE NAME=? ", SerializerWriteString.INSTANCE, this.customerIdRead);
        this.m_peoplevisible = new StaticSentence(s, "SELECT ID, NAME, APPPASSWORD, CARD, ROLE, IMAGE FROM people WHERE VISIBLE = " + s.DB.TRUE() + " ORDER BY NAME", null, this.peopleread);
        this.m_peoplebycard = new PreparedSentence<String, AppUser>(s, "SELECT ID, NAME, APPPASSWORD, CARD, ROLE, IMAGE FROM people WHERE CARD = ? AND VISIBLE = " + s.DB.TRUE(), SerializerWriteString.INSTANCE, this.peopleread);
        this.m_resourcebytes = new PreparedSentence<String, byte[]>(s, "SELECT CONTENT FROM resources WHERE NAME = ?", SerializerWriteString.INSTANCE, SerializerReadBytes.INSTANCE);
        Datas[] resourcedata = new Datas[]{Datas.STRING, Datas.STRING, Datas.INT, Datas.BYTES};
        this.m_resourcebytesinsert = new PreparedSentence(s, "INSERT INTO resources(ID, NAME, RESTYPE, CONTENT) VALUES (?, ?, ?, ?)", new SerializerWriteBasic(resourcedata));
        this.m_resourcebytesupdate = new PreparedSentence(s, "UPDATE resources SET NAME = ?, RESTYPE = ?, CONTENT = ? WHERE NAME = ?", new SerializerWriteBasicExt(resourcedata, new int[]{1, 2, 3, 1}));
        this.m_rolepermissions = new PreparedSentence<String, byte[]>(s, "SELECT PERMISSIONS FROM roles WHERE ID = ?", SerializerWriteString.INSTANCE, SerializerReadBytes.INSTANCE);
        this.m_changepassword = new StaticSentence(s, "UPDATE people SET APPPASSWORD = ? WHERE ID = ?", new SerializerWriteBasic(Datas.STRING, Datas.STRING));
        this.m_sequencecash = new StaticSentence<String, Integer>(s, "SELECT MAX(HOSTSEQUENCE) FROM closedcash WHERE HOST = ?", SerializerWriteString.INSTANCE, SerializerReadInteger.INSTANCE);
        this.m_activecash = new StaticSentence<String, Object[]>(s, "SELECT HOST, HOSTSEQUENCE, DATESTART, DATEEND, NOSALES FROM closedcash WHERE MONEY = ?", SerializerWriteString.INSTANCE, new SerializerReadBasic(new Datas[]{Datas.STRING, Datas.INT, Datas.TIMESTAMP, Datas.TIMESTAMP, Datas.INT}));
        this.m_closedcash = new StaticSentence<String, Object[]>(s, "SELECT HOST, HOSTSEQUENCE, DATESTART, DATEEND, NOSALES FROM closedcash WHERE HOSTSEQUENCE = ?", SerializerWriteString.INSTANCE, new SerializerReadBasic(new Datas[]{Datas.STRING, Datas.INT, Datas.TIMESTAMP, Datas.TIMESTAMP, Datas.INT}));
        this.m_insertcash = new StaticSentence(s, "INSERT INTO closedcash(MONEY, HOST, HOSTSEQUENCE, DATESTART, DATEEND) VALUES (?, ?, ?, ?, ?)", new SerializerWriteBasic(Datas.STRING, Datas.STRING, Datas.INT, Datas.TIMESTAMP, Datas.TIMESTAMP));
        this.m_draweropened = new StaticSentence(s, "INSERT INTO draweropened ( NAME, TICKETID) VALUES (?, ?)", new SerializerWriteBasic(Datas.STRING, Datas.STRING));
        this.m_lineremoved = new StaticSentence(s, "INSERT INTO lineremoved (NAME, TICKETID, PRODUCTID, PRODUCTNAME, UNITS) VALUES (?, ?, ?, ?, ?)", new SerializerWriteBasic(Datas.STRING, Datas.STRING, Datas.STRING, Datas.STRING, Datas.DOUBLE));
        this.m_insertAuditEntry = new StaticSentence(s, "INSERT INTO audit_lines (ID, DATE, TYPE, USER, PRODUCT, QTY, OLD_VALUE, NEW_VALUE, INFO, TICKET_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", new SerializerWriteBasic(Datas.STRING, Datas.TIMESTAMP, Datas.STRING, Datas.STRING, Datas.STRING, Datas.DOUBLE, Datas.STRING, Datas.STRING, Datas.STRING, Datas.STRING));
        this.m_locationfind = new StaticSentence<String, String>(s, "SELECT NAME FROM locations WHERE ID = ?", SerializerWriteString.INSTANCE, SerializerReadString.INSTANCE);
        this.m_permissionlist = new StaticSentence<String, String>(s, "SELECT PERMISSIONS FROM PERMISSIONS WHERE ID = ?", SerializerWriteString.INSTANCE, SerializerReadString.INSTANCE);
        this.m_updatepermissions = new StaticSentence(s, "INSERT INTO PERMISSIONS (ID, PERMISSIONS) VALUES (?, ?)", new SerializerWriteBasic(Datas.STRING, Datas.STRING));
        this.m_insertCSVEntry = new StaticSentence(s, "INSERT INTO csvimport ( ID, ROWNUMBER, CSVERROR, REFERENCE, CODE, NAME, PRICEBUY, PRICESELL, PREVIOUSBUY, PREVIOUSSELL, CATEGORY, TAX) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", new SerializerWriteBasic(Datas.STRING, Datas.STRING, Datas.STRING, Datas.STRING, Datas.STRING, Datas.STRING, Datas.DOUBLE, Datas.DOUBLE, Datas.DOUBLE, Datas.DOUBLE, Datas.STRING, Datas.STRING));
        this.m_insertCustomerCSVEntry = new StaticSentence(s, "INSERT INTO csvimport ( ID, ROWNUMBER, CSVERROR, SEARCHKEY, NAME) VALUES (?, ?, ?, ?, ?)", new SerializerWriteBasic(Datas.STRING, Datas.STRING, Datas.STRING, Datas.STRING, Datas.STRING));
        this.resetResourcesCache();
    }

    public String getInitScript() {
        return this.m_sInitScript;
    }

    public String getDBVersion() {
        return this.m_dbVersion;
    }

    public final String findVersion() throws BasicException {
        return this.m_version.find((Object)"soltecpos");
    }

    public final String getUser() throws BasicException {
        return "";
    }

    public final void execDummy() throws BasicException {
        this.m_dummy.exec();
    }

    public final List<AppUser> listPeopleVisible() throws BasicException {
        return this.m_peoplevisible.list();
    }

    public final List<String> getPermissions(String role) throws BasicException {
        return this.m_permissionlist.list((Object)role);
    }

    public final AppUser findPeopleByCard(String card) throws BasicException {
        return this.m_peoplebycard.find((Object)card);
    }

    public final String findRolePermissions(String sRole) {
        try {
            return Formats.BYTEA.formatValue(this.m_rolepermissions.find((Object)sRole));
        }
        catch (BasicException e) {
            return null;
        }
    }

    public final void execChangePassword(Object[] userdata) throws BasicException {
        this.m_changepassword.exec(userdata);
    }

    public final void resetResourcesCache() {
        this.resourcescache = new HashMap<String, byte[]>();
    }

    private byte[] getResource(String name) {
        byte[] resource = this.resourcescache.get(name);
        if (resource == null) {
            try {
                resource = this.m_resourcebytes.find((Object)name);
                this.resourcescache.put(name, resource);
            }
            catch (BasicException e) {
                resource = null;
            }
        }
        return resource;
    }

    public final void setResource(String name, int type, byte[] data) {
        Object[] value = new Object[]{UUID.randomUUID().toString(), name, type, data};
        try {
            if (this.m_resourcebytesupdate.exec(value) == 0) {
                this.m_resourcebytesinsert.exec(value);
            }
            this.resourcescache.put(name, data);
        }
        catch (BasicException basicException) {
            // empty catch block
        }
    }

    public final void setResourceAsBinary(String sName, byte[] data) {
        this.setResource(sName, 2, data);
    }

    public final byte[] getResourceAsBinary(String sName) {
        return this.getResource(sName);
    }

    public final String getResourceAsText(String sName) {
        return Formats.BYTEA.formatValue(this.getResource(sName));
    }

    public final String getResourceAsXML(String sName) {
        return Formats.BYTEA.formatValue(this.getResource(sName));
    }

    public final BufferedImage getResourceAsImage(String sName) {
        try {
            byte[] img = this.getResource(sName);
            return img == null ? null : ImageIO.read(new ByteArrayInputStream(img));
        }
        catch (IOException e) {
            return null;
        }
    }

    public final void setResourceAsProperties(String sName, Properties p) {
        if (p == null) {
            this.setResource(sName, 0, null);
        } else {
            try {
                ByteArrayOutputStream o = new ByteArrayOutputStream();
                p.storeToXML(o, "SOLTEC POS", "UTF8");
                this.setResource(sName, 0, o.toByteArray());
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
    }

    public final Properties getResourceAsProperties(String sName) {
        Properties p = new Properties();
        try {
            byte[] img = this.getResourceAsBinary(sName);
            if (img != null) {
                p.loadFromXML(new ByteArrayInputStream(img));
            }
        }
        catch (IOException iOException) {
            // empty catch block
        }
        return p;
    }

    public final int getSequenceCash(String host) throws BasicException {
        Integer i = this.m_sequencecash.find((Object)host);
        return i == null ? 1 : i;
    }

    public final Object[] findActiveCash(String sActiveCashIndex) throws BasicException {
        return this.m_activecash.find((Object)sActiveCashIndex);
    }

    public final Object[] findClosedCash(String sClosedCashIndex) throws BasicException {
        return this.m_activecash.find((Object)sClosedCashIndex);
    }

    public final void execInsertCash(Object[] cash) throws BasicException {
        this.m_insertcash.exec(cash);
    }

    public final void execDrawerOpened(Object[] drawer) throws BasicException {
        this.m_draweropened.exec(drawer);
    }

    public final void execUpdatePermissions(Object[] permissions) throws BasicException {
        this.m_updatepermissions.exec(permissions);
    }

    public final void execLineRemoved(Object[] line) {
        try {
            this.m_lineremoved.exec(line);
        }
        catch (BasicException basicException) {
            // empty catch block
        }
    }

    public final void execAuditEntry(Object[] auditEntry) {
        try {
            this.m_insertAuditEntry.exec(auditEntry);
        }
        catch (BasicException basicException) {
            // empty catch block
        }
    }

    public final String findLocationName(String iLocation) throws BasicException {
        return this.m_locationfind.find((Object)iLocation);
    }

    public final void execAddCSVEntry(Object[] csv) throws BasicException {
        this.m_insertCSVEntry.exec(csv);
    }

    public final void execCustomerAddCSVEntry(Object[] csv) throws BasicException {
        this.m_insertCustomerCSVEntry.exec(csv);
    }

    public final String getProductRecordType(Object[] myProduct) throws BasicException {
        if (this.m_getProductAllFields.find(myProduct) != null) {
            return this.m_getProductAllFields.find(myProduct).toString();
        }
        if (this.m_getProductRefAndCode.find(myProduct[0], myProduct[1]) != null) {
            return "name error";
        }
        if (this.m_getProductRefAndName.find(myProduct[0], myProduct[2]) != null) {
            return "barcode error";
        }
        if (this.m_getProductCodeAndName.find(myProduct[1], myProduct[2]) != null) {
            return "reference error";
        }
        if (this.m_getProductByReference.find(myProduct[0]) != null) {
            return "Duplicate Reference found.";
        }
        if (this.m_getProductByCode.find(myProduct[1]) != null) {
            return "Duplicate Barcode found.";
        }
        if (this.m_getProductByName.find(myProduct[2]) != null) {
            return "Duplicate Description found.";
        }
        return "new";
    }

    public final String getCustomerRecordType(Object[] myCustomer) throws BasicException {
        if (this.m_getCustomerAllFields.find(myCustomer) != null) {
            return this.m_getCustomerAllFields.find(myCustomer).toString();
        }
        if (this.m_getCustomerSearchKeyAndName.find(myCustomer[0], myCustomer[1]) != null) {
            return "reference error";
        }
        if (this.m_getCustomerBySearchKey.find(myCustomer[0]) != null) {
            return "Duplicate Search Key found.";
        }
        if (this.m_getCustomerByName.find(myCustomer[1]) != null) {
            return "Duplicate Name found.";
        }
        return "new";
    }

    public final SentenceList<Object> getVouchersActiveList() {
        return this.m_voucherlist;
    }
}

