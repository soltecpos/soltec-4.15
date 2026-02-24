/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.suppliers;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.DataParams;
import com.openbravo.data.loader.Datas;
import com.openbravo.data.loader.PreparedSentence;
import com.openbravo.data.loader.QBFBuilder;
import com.openbravo.data.loader.SentenceList;
import com.openbravo.data.loader.SerializerWriteBasic;
import com.openbravo.data.loader.SerializerWriteParams;
import com.openbravo.data.loader.SerializerWriteString;
import com.openbravo.data.loader.Session;
import com.openbravo.data.loader.StaticSentence;
import com.openbravo.data.loader.TableDefinition;
import com.openbravo.format.Formats;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.BeanFactoryDataSingle;
import com.openbravo.pos.suppliers.SupplierInfo;
import com.openbravo.pos.suppliers.SupplierInfoExt;
import com.openbravo.pos.suppliers.SupplierTransaction;
import java.util.List;

public class DataLogicSuppliers
extends BeanFactoryDataSingle {
    protected Session s;
    private TableDefinition tsuppliers;
    private static final Datas[] supplierdatas = new Datas[]{Datas.STRING, Datas.STRING, Datas.STRING, Datas.STRING, Datas.STRING, Datas.STRING, Datas.STRING};

    @Override
    public void init(Session s) {
        this.s = s;
        this.tsuppliers = new TableDefinition(s, "suppliers", new String[]{"ID", "SEARCHKEY", "TAXID", "NAME", "MAXDEBT", "ADDRESS", "ADDRESS2", "POSTAL", "CITY", "REGION", "COUNTRY", "FIRSTNAME", "LASTNAME", "EMAIL", "PHONE", "PHONE2", "FAX", "NOTES", "VISIBLE", "CURDATE", "CURDEBT", "VATID"}, new String[]{"ID", AppLocal.getIntString("label.searchkey"), AppLocal.getIntString("label.suppliertaxid"), AppLocal.getIntString("label.name"), AppLocal.getIntString("label.maxdebt"), AppLocal.getIntString("label.address"), AppLocal.getIntString("label.address2"), AppLocal.getIntString("label.postal"), AppLocal.getIntString("label.city"), AppLocal.getIntString("label.region"), AppLocal.getIntString("label.country"), AppLocal.getIntString("label.firstname"), AppLocal.getIntString("label.lastname"), AppLocal.getIntString("label.email"), AppLocal.getIntString("label.phone"), AppLocal.getIntString("label.phone2"), AppLocal.getIntString("label.fax"), AppLocal.getIntString("label.notes"), "VISIBLE", AppLocal.getIntString("label.curdate"), AppLocal.getIntString("label.curdebt"), AppLocal.getIntString("label.suppliervatid")}, new Datas[]{Datas.STRING, Datas.STRING, Datas.STRING, Datas.STRING, Datas.DOUBLE, Datas.STRING, Datas.STRING, Datas.STRING, Datas.STRING, Datas.STRING, Datas.STRING, Datas.STRING, Datas.STRING, Datas.STRING, Datas.STRING, Datas.STRING, Datas.STRING, Datas.STRING, Datas.BOOLEAN, Datas.TIMESTAMP, Datas.DOUBLE, Datas.STRING}, new Formats[]{Formats.STRING, Formats.STRING, Formats.STRING, Formats.STRING, Formats.CURRENCY, Formats.STRING, Formats.STRING, Formats.STRING, Formats.STRING, Formats.STRING, Formats.STRING, Formats.STRING, Formats.STRING, Formats.STRING, Formats.STRING, Formats.STRING, Formats.STRING, Formats.STRING, Formats.BOOLEAN, Formats.TIMESTAMP, Formats.CURRENCY, Formats.STRING}, new int[]{0});
    }

    public SentenceList<SupplierInfo> getSupplierList() {
        return new StaticSentence<Object[], SupplierInfo>(this.s, new QBFBuilder("SELECT ID, SEARCHKEY, TAXID, NAME, POSTAL, PHONE, EMAIL FROM suppliers WHERE VISIBLE = " + this.s.DB.TRUE() + " AND ?(QBF_FILTER) ORDER BY NAME", new String[]{"SEARCHKEY", "TAXID", "NAME", "POSTAL", "PHONE", "EMAIL"}), new SerializerWriteBasic(Datas.OBJECT, Datas.STRING, Datas.OBJECT, Datas.STRING, Datas.OBJECT, Datas.STRING, Datas.OBJECT, Datas.STRING, Datas.OBJECT, Datas.STRING, Datas.OBJECT, Datas.STRING), dr -> {
            SupplierInfo s1 = new SupplierInfo(dr.getString(1));
            s1.setSearchkey(dr.getString(2));
            s1.setTaxid(dr.getString(3));
            s1.setName(dr.getString(4));
            s1.setPostal(dr.getString(5));
            s1.setPhone(dr.getString(6));
            s1.setEmail(dr.getString(7));
            return s1;
        });
    }

    public SentenceList<SupplierInfo> getSuppList() {
        return new StaticSentence<Object[], SupplierInfo>(this.s, new QBFBuilder("SELECT ID, SEARCHKEY, TAXID, NAME, POSTAL, PHONE, EMAIL FROM suppliers WHERE VISIBLE = " + this.s.DB.TRUE() + " ORDER BY NAME", new String[]{"SEARCHKEY", "TAXID", "NAME", "POSTAL", "PHONE", "EMAIL"}), new SerializerWriteBasic(Datas.OBJECT, Datas.STRING, Datas.OBJECT, Datas.STRING, Datas.OBJECT, Datas.STRING, Datas.OBJECT, Datas.STRING, Datas.OBJECT, Datas.STRING, Datas.OBJECT, Datas.STRING), dr -> {
            SupplierInfo s1 = new SupplierInfo(dr.getString(1));
            s1.setSearchkey(dr.getString(2));
            s1.setTaxid(dr.getString(3));
            s1.setName(dr.getString(4));
            s1.setPostal(dr.getString(5));
            s1.setPhone(dr.getString(6));
            s1.setEmail(dr.getString(7));
            return s1;
        });
    }

    public int updateSupplierExt(final SupplierInfoExt supplier) throws BasicException {
        return new PreparedSentence(this.s, "UPDATE suppliers SET NOTES = ? WHERE ID = ?", SerializerWriteParams.INSTANCE).exec(new DataParams(){

            @Override
            public void writeValues() throws BasicException {
                this.setString(1, supplier.getNotes());
                this.setString(2, supplier.getID());
            }
        });
    }

    public final TableDefinition getTableSuppliers() {
        return this.tsuppliers;
    }

    public final List<SupplierTransaction> getSuppliersTransactionList(String sId) throws BasicException {
        return new PreparedSentence<String, SupplierTransaction>(this.s, "SELECT stockdiary.datenew, products.NAME, stockdiary.units, stockdiary.price, stockdiary.reason, suppliers.id FROM (stockdiary stockdiary INNER JOIN suppliers suppliers ON (stockdiary.supplier = suppliers.id)) INNER JOIN products products ON (stockdiary.product = products.ID) WHERE suppliers.id = ? GROUP BY stockdiary.datenew, products.NAME, stockdiary.reason ORDER BY stockdiary.datenew DESC", SerializerWriteString.INSTANCE, SupplierTransaction.getSerializerRead()).list((Object)sId);
    }
}

