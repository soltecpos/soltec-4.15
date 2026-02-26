/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.forms;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.DataParams;
import com.openbravo.data.loader.DataRead;
import com.openbravo.data.loader.Datas;
import com.openbravo.data.loader.ImageUtils;
import com.openbravo.data.loader.PreparedSentence;
import com.openbravo.data.loader.QBFBuilder;
import com.openbravo.data.loader.SentenceExec;
import com.openbravo.data.loader.SentenceExecTransaction;
import com.openbravo.data.loader.SentenceFind;
import com.openbravo.data.loader.SentenceList;
import com.openbravo.data.loader.SerializerRead;
import com.openbravo.data.loader.SerializerReadBasic;
import com.openbravo.data.loader.SerializerReadClass;
import com.openbravo.data.loader.SerializerReadDouble;
import com.openbravo.data.loader.SerializerReadString;
import com.openbravo.data.loader.SerializerWriteBasic;
import com.openbravo.data.loader.SerializerWriteBasicExt;
import com.openbravo.data.loader.SerializerWriteBuilder;
import com.openbravo.data.loader.SerializerWriteParams;
import com.openbravo.data.loader.SerializerWriteString;
import com.openbravo.data.loader.Session;
import com.openbravo.data.loader.StaticSentence;
import com.openbravo.data.loader.TableDefinition;
import com.openbravo.data.loader.Transaction;
import com.openbravo.data.model.Field;
import com.openbravo.data.model.Row;
import com.openbravo.format.Formats;
import com.openbravo.pos.customers.CustomerInfoExt;
import com.openbravo.pos.customers.CustomerTransaction;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.BeanFactoryDataSingle;
import com.openbravo.pos.forms.Payments;
import com.openbravo.pos.inventory.AttributeSetInfo;
import com.openbravo.pos.inventory.LocationInfo;
import com.openbravo.pos.inventory.MovementReason;
import com.openbravo.pos.inventory.ProductStock;
import com.openbravo.pos.inventory.ProductsBundleInfo;
import com.openbravo.pos.inventory.TaxCategoryInfo;
import com.openbravo.pos.inventory.TaxCustCategoryInfo;
import com.openbravo.pos.inventory.UomInfo;
import com.openbravo.pos.mant.FloorsInfo;
import com.openbravo.pos.payment.PaymentInfo;
import com.openbravo.pos.payment.PaymentInfoTicket;
import com.openbravo.pos.sales.ReprintTicketInfo;
import com.openbravo.pos.suppliers.SupplierInfo;
import com.openbravo.pos.suppliers.SupplierInfoExt;
import com.openbravo.pos.ticket.CategoryInfo;
import com.openbravo.pos.ticket.FindTicketsInfo;
import com.openbravo.pos.ticket.ProductInfo;
import com.openbravo.pos.ticket.ProductInfoExt;
import com.openbravo.pos.ticket.TaxInfo;
import com.openbravo.pos.ticket.TicketInfo;
import com.openbravo.pos.ticket.TicketLineInfo;
import com.openbravo.pos.ticket.TicketTaxInfo;
import com.openbravo.pos.voucher.VoucherInfo;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

public class DataLogicSales
extends BeanFactoryDataSingle {
    protected Session s;
    protected Datas[] auxiliarDatas;
    protected Datas[] stockdiaryDatas = new Datas[]{Datas.STRING, Datas.TIMESTAMP, Datas.INT, Datas.STRING, Datas.STRING, Datas.STRING, Datas.DOUBLE, Datas.DOUBLE, Datas.STRING, Datas.STRING, Datas.STRING};
    protected Datas[] paymenttabledatas = new Datas[]{Datas.STRING, Datas.STRING, Datas.TIMESTAMP, Datas.STRING, Datas.STRING, Datas.DOUBLE, Datas.STRING, Datas.STRING, Datas.STRING, Datas.STRING};
    protected Datas[] stockdatas = new Datas[]{Datas.STRING, Datas.STRING, Datas.STRING, Datas.DOUBLE, Datas.DOUBLE, Datas.DOUBLE};
    protected Datas[] stockAdjustDatas = new Datas[]{Datas.STRING, Datas.STRING, Datas.STRING, Datas.DOUBLE};
    protected Row productsRow;
    protected Row customersRow;
    private String pName;
    private Double getTotal;
    private Double getTendered;
    private String getRetMsg;
    private String getVoucher;
    public static final String DEBT = "debt";
    public static final String DEBT_PAID = "debtpaid";
    protected static final String PREPAY = "prepay";
    private static final Logger logger = Logger.getLogger("com.openbravo.pos.forms.DataLogicSales");
    private String getCardName;
    protected SentenceExec<Object[]> m_createCat;
    protected SentenceExec<Object[]> m_createSupp;

    public DataLogicSales() {
        this.auxiliarDatas = new Datas[]{Datas.STRING, Datas.STRING, Datas.STRING, Datas.STRING, Datas.STRING, Datas.STRING};
        this.productsRow = new Row(new Field("ID", Datas.STRING, Formats.STRING), new Field(AppLocal.getIntString("label.prodref"), Datas.STRING, Formats.STRING, true, true, true), new Field(AppLocal.getIntString("label.prodbarcode"), Datas.STRING, Formats.STRING, false, true, true), new Field(AppLocal.getIntString("label.prodbarcodetype"), Datas.STRING, Formats.STRING, false, true, true), new Field(AppLocal.getIntString("label.prodname"), Datas.STRING, Formats.STRING, true, true, true), new Field(AppLocal.getIntString("label.prodpricebuy"), Datas.DOUBLE, Formats.CURRENCY, false, true, true), new Field(AppLocal.getIntString("label.prodpricesell"), Datas.DOUBLE, Formats.CURRENCY, false, true, true), new Field(AppLocal.getIntString("label.prodcategory"), Datas.STRING, Formats.STRING, false, false, true), new Field(AppLocal.getIntString("label.taxcategory"), Datas.STRING, Formats.STRING, false, false, true), new Field(AppLocal.getIntString("label.attributeset"), Datas.STRING, Formats.STRING, false, false, true), new Field("STOCKCOST", Datas.DOUBLE, Formats.CURRENCY), new Field("STOCKVOLUME", Datas.DOUBLE, Formats.DOUBLE), new Field("IMAGE", Datas.IMAGE, Formats.NULL), new Field("ISCOM", Datas.BOOLEAN, Formats.BOOLEAN), new Field("ISSCALE", Datas.BOOLEAN, Formats.BOOLEAN), new Field("ISCONSTANT", Datas.BOOLEAN, Formats.BOOLEAN), new Field("PRINTKB", Datas.BOOLEAN, Formats.BOOLEAN), new Field("SENDSTATUS", Datas.BOOLEAN, Formats.BOOLEAN), new Field("ISSERVICE", Datas.BOOLEAN, Formats.BOOLEAN), new Field("PROPERTIES", Datas.BYTES, Formats.NULL), new Field(AppLocal.getIntString("label.display"), Datas.STRING, Formats.STRING, false, true, true), new Field("ISVPRICE", Datas.BOOLEAN, Formats.BOOLEAN), new Field("ISVERPATRIB", Datas.BOOLEAN, Formats.BOOLEAN), new Field("TEXTTIP", Datas.STRING, Formats.STRING), new Field("WARRANTY", Datas.BOOLEAN, Formats.BOOLEAN), new Field(AppLocal.getIntString("label.stockunits"), Datas.DOUBLE, Formats.DOUBLE), new Field("PRINTTO", Datas.STRING, Formats.STRING), new Field(AppLocal.getIntString("label.prodsupplier"), Datas.STRING, Formats.STRING, false, false, true), new Field(AppLocal.getIntString("label.UOM"), Datas.STRING, Formats.STRING), new Field("ISCATALOG", Datas.BOOLEAN, Formats.BOOLEAN), new Field("CATORDER", Datas.INT, Formats.INT));
        this.customersRow = new Row(new Field("ID", Datas.STRING, Formats.STRING), new Field("SEARCHKEY", Datas.STRING, Formats.STRING), new Field("TAXID", Datas.STRING, Formats.STRING), new Field("NAME", Datas.STRING, Formats.STRING), new Field("TAXCATEGORY", Datas.STRING, Formats.STRING), new Field("CARD", Datas.STRING, Formats.STRING), new Field("MAXDEBT", Datas.DOUBLE, Formats.CURRENCY), new Field("ADDRESS", Datas.STRING, Formats.STRING), new Field("ADDRESS2", Datas.STRING, Formats.STRING), new Field("POSTAL", Datas.STRING, Formats.STRING), new Field("CITY", Datas.STRING, Formats.STRING), new Field("REGION", Datas.STRING, Formats.STRING), new Field("COUNTRY", Datas.STRING, Formats.STRING), new Field("FIRSTNAME", Datas.STRING, Formats.STRING), new Field("LASTNAME", Datas.STRING, Formats.STRING), new Field("EMAIL", Datas.STRING, Formats.STRING), new Field("PHONE", Datas.STRING, Formats.STRING), new Field("PHONE2", Datas.STRING, Formats.STRING), new Field("FAX", Datas.STRING, Formats.STRING), new Field("NOTES", Datas.STRING, Formats.STRING), new Field("VISIBLE", Datas.BOOLEAN, Formats.BOOLEAN), new Field("CURDATE", Datas.STRING, Formats.TIMESTAMP), new Field("CURDEBT", Datas.DOUBLE, Formats.CURRENCY), new Field("IMAGE", Datas.BYTES, Formats.NULL), new Field("ISVIP", Datas.BOOLEAN, Formats.BOOLEAN), new Field("DISCOUNT", Datas.DOUBLE, Formats.CURRENCY));
    }

    @Override
    public void init(Session s) {
        this.s = s;
        this.m_createCat = new StaticSentence(s, "INSERT INTO CATEGORIES ( ID, NAME, CATSHOWNAME ) VALUES (?, ?, ?)", new SerializerWriteBasic(Datas.STRING, Datas.STRING, Datas.BOOLEAN));
        this.m_createSupp = new StaticSentence(s, "INSERT INTO suppliers ( ID, NAME, SEARCHKEY, VISIBLE ) VALUES (?, ?, ?, ?)", new SerializerWriteBasic(Datas.STRING, Datas.STRING, Datas.STRING, Datas.BOOLEAN));
    }

    public final void createCategory(Object[] category) throws BasicException {
        this.m_createCat.exec(category);
    }

    public final void createSupplier(Object[] supplier) throws BasicException {
        this.m_createSupp.exec(supplier);
    }

    public final Row getProductsRow() {
        return this.productsRow;
    }

    public final Row getCustomersRow() {
        return this.customersRow;
    }

    public final ProductInfoExt getProductInfo(String id) throws BasicException {
        return (ProductInfoExt)new PreparedSentence<String, ProductInfoExt>(this.s, "SELECT ID, REFERENCE, CODE, CODETYPE, NAME, PRICEBUY, PRICESELL, CATEGORY, TAXCAT, ATTRIBUTESET_ID, STOCKCOST, STOCKVOLUME, IMAGE, ISCOM, ISSCALE, ISCONSTANT, PRINTKB, SENDSTATUS, ISSERVICE, ATTRIBUTES, DISPLAY, ISVPRICE, ISVERPATRIB, TEXTTIP, WARRANTY, STOCKUNITS, PRINTTO, SUPPLIER, UOM FROM products WHERE ID = ?", SerializerWriteString.INSTANCE, ProductInfoExt.getSerializerRead()).find((Object)id);
    }

    public final ProductInfoExt getProductInfoByCode(String sCode) throws BasicException {
        return (ProductInfoExt)new PreparedSentence<String, ProductInfoExt>(this.s, "SELECT ID, REFERENCE, CODE, CODETYPE, NAME, PRICEBUY, PRICESELL, CATEGORY, TAXCAT, ATTRIBUTESET_ID, STOCKCOST, STOCKVOLUME, IMAGE, ISCOM, ISSCALE, ISCONSTANT, PRINTKB, SENDSTATUS, ISSERVICE, ATTRIBUTES, DISPLAY, ISVPRICE, ISVERPATRIB, TEXTTIP, WARRANTY, STOCKUNITS, PRINTTO, SUPPLIER, UOM FROM products WHERE CODE = ?", SerializerWriteString.INSTANCE, ProductInfoExt.getSerializerRead()).find((Object)sCode);
    }

    public final ProductInfoExt getProductInfoByShortCode(String sCode) throws BasicException {
        return (ProductInfoExt)new PreparedSentence<String, ProductInfoExt>(this.s, "SELECT ID, REFERENCE, CODE, CODETYPE, NAME, PRICEBUY, PRICESELL, CATEGORY, TAXCAT, ATTRIBUTESET_ID, STOCKCOST, STOCKVOLUME, IMAGE, ISCOM, ISSCALE, ISCONSTANT, PRINTKB, SENDSTATUS, ISSERVICE, ATTRIBUTES, DISPLAY, ISVPRICE, ISVERPATRIB, TEXTTIP, WARRANTY, STOCKUNITS, PRINTTO, SUPPLIER, UOM FROM products WHERE SUBSTRING( CODE, 3, 6 ) = ?", SerializerWriteString.INSTANCE, ProductInfoExt.getSerializerRead()).find((Object)sCode.substring(2, 8));
    }

    public final ProductInfoExt getProductInfoByUShortCode(String sCode) throws BasicException {
        return (ProductInfoExt)new PreparedSentence<String, ProductInfoExt>(this.s, "SELECT ID, REFERENCE, CODE, CODETYPE, NAME, PRICEBUY, PRICESELL, CATEGORY, TAXCAT, ATTRIBUTESET_ID, STOCKCOST, STOCKVOLUME, IMAGE, ISCOM, ISSCALE, ISCONSTANT, PRINTKB, SENDSTATUS, ISSERVICE, ATTRIBUTES, DISPLAY, ISVPRICE, ISVERPATRIB, TEXTTIP, WARRANTY, STOCKUNITS, PRINTTO, SUPPLIER, UOM FROM products WHERE LEFT( CODE, 7 ) = ? AND CODETYPE = 'UPC-A' ", SerializerWriteString.INSTANCE, ProductInfoExt.getSerializerRead()).find((Object)sCode.substring(0, 7));
    }

    public final ProductInfoExt getProductInfoByReference(String sReference) throws BasicException {
        return (ProductInfoExt)new PreparedSentence<String, ProductInfoExt>(this.s, "SELECT ID, REFERENCE, CODE, CODETYPE, NAME, PRICEBUY, PRICESELL, CATEGORY, TAXCAT, ATTRIBUTESET_ID, STOCKCOST, STOCKVOLUME, IMAGE, ISCOM, ISSCALE, ISCONSTANT, PRINTKB, SENDSTATUS, ISSERVICE, ATTRIBUTES, DISPLAY, ISVPRICE, ISVERPATRIB, TEXTTIP, WARRANTY, STOCKUNITS, PRINTTO, SUPPLIER, UOM FROM products WHERE REFERENCE = ?", SerializerWriteString.INSTANCE, ProductInfoExt.getSerializerRead()).find((Object)sReference);
    }

    public final List<ProductInfoExt> getProductsByKeyword(String keyword) throws BasicException {
        return new PreparedSentence<Object[], ProductInfoExt>(this.s, "SELECT ID, REFERENCE, CODE, CODETYPE, NAME, PRICEBUY, PRICESELL, CATEGORY, TAXCAT, ATTRIBUTESET_ID, STOCKCOST, STOCKVOLUME, IMAGE, ISCOM, ISSCALE, ISCONSTANT, PRINTKB, SENDSTATUS, ISSERVICE, ATTRIBUTES, DISPLAY, ISVPRICE, ISVERPATRIB, TEXTTIP, WARRANTY, STOCKUNITS, PRINTTO, SUPPLIER, UOM FROM products WHERE NAME LIKE ? OR CODE LIKE ? ORDER BY NAME", new SerializerWriteBasic(Datas.STRING, Datas.STRING), ProductInfoExt.getSerializerRead()).list(keyword, keyword);
    }

    public final List<CategoryInfo> getRootCategories() throws BasicException {
        return new PreparedSentence(this.s, "SELECT ID, NAME, IMAGE, TEXTTIP, CATSHOWNAME, CATORDER FROM categories WHERE PARENTID IS NULL AND CATSHOWNAME = " + this.s.DB.TRUE() + " ORDER BY CATORDER, NAME", null, CategoryInfo.getSerializerRead()).list();
    }

    public final List<CategoryInfo> getSubcategories(String category) throws BasicException {
        return new PreparedSentence<String, CategoryInfo>(this.s, "SELECT ID, NAME, IMAGE, TEXTTIP, CATSHOWNAME, CATORDER FROM categories WHERE PARENTID = ? ORDER BY CATORDER, NAME", SerializerWriteString.INSTANCE, CategoryInfo.getSerializerRead()).list((Object)category);
    }

    public List<ProductInfoExt> getProductCatalog(String category) throws BasicException {
        return new PreparedSentence<String, ProductInfoExt>(this.s, "SELECT P.ID, P.REFERENCE, P.CODE, P.CODETYPE, P.NAME, P.PRICEBUY, P.PRICESELL, P.CATEGORY, P.TAXCAT, P.ATTRIBUTESET_ID, P.STOCKCOST, P.STOCKVOLUME, P.IMAGE, P.ISCOM, P.ISSCALE, P.ISCONSTANT, P.PRINTKB, P.SENDSTATUS, P.ISSERVICE, P.ATTRIBUTES, P.DISPLAY, P.ISVPRICE, P.ISVERPATRIB, P.TEXTTIP, P.WARRANTY, P.STOCKUNITS, P.PRINTTO, P.SUPPLIER, P.UOM FROM products P, products_cat O WHERE P.ID = O.PRODUCT AND P.CATEGORY = ? ORDER BY O.CATORDER, P.NAME ", SerializerWriteString.INSTANCE, ProductInfoExt.getSerializerRead()).list((Object)category);
    }

    public List<ProductInfoExt> getProductComments(String id) throws BasicException {
        return new PreparedSentence<String, ProductInfoExt>(this.s, "SELECT P.ID, P.REFERENCE, P.CODE, P.CODETYPE, P.NAME, P.PRICEBUY, P.PRICESELL, P.CATEGORY, P.TAXCAT, P.ATTRIBUTESET_ID, P.STOCKCOST, P.STOCKVOLUME, P.IMAGE, P.ISCOM, P.ISSCALE, P.ISCONSTANT, P.PRINTKB, P.SENDSTATUS, P.ISSERVICE, P.ATTRIBUTES, P.DISPLAY, P.ISVPRICE, P.ISVERPATRIB, P.TEXTTIP, P.WARRANTY, P.STOCKUNITS, P.PRINTTO, P.SUPPLIER, P.UOM FROM products P, products_cat O, products_com M WHERE P.ID = O.PRODUCT AND P.ID = M.PRODUCT2 AND M.PRODUCT = ? AND P.ISCOM = " + this.s.DB.TRUE() + " ORDER BY O.CATORDER, P.NAME", SerializerWriteString.INSTANCE, ProductInfoExt.getSerializerRead()).list((Object)id);
    }

    public List<ProductInfoExt> getProductConstant() throws BasicException {
        return new PreparedSentence(this.s, "SELECT products.ID, products.REFERENCE, products.CODE, products.CODETYPE, products.NAME, products.PRICEBUY, products.PRICESELL, products.CATEGORY, products.TAXCAT, products.ATTRIBUTESET_ID, products.STOCKCOST, products.STOCKVOLUME, products.IMAGE, products.ISCOM, products.ISSCALE, products.ISCONSTANT, products.PRINTKB, products.SENDSTATUS, products.ISSERVICE, products.ATTRIBUTES, products.DISPLAY, products.ISVPRICE, products.ISVERPATRIB, products.TEXTTIP, products.WARRANTY, products.STOCKUNITS, products.PRINTTO, products.SUPPLIER, products.UOM FROM categories INNER JOIN products ON (products.CATEGORY = categories.ID) WHERE products.ISCONSTANT = " + this.s.DB.TRUE() + " ORDER BY categories.NAME, products.NAME", null, ProductInfoExt.getSerializerRead()).list();
    }

    public final CategoryInfo getCategoryInfo(String id) throws BasicException {
        return (CategoryInfo)new PreparedSentence<String, CategoryInfo>(this.s, "SELECT ID, NAME, IMAGE, TEXTTIP, CATSHOWNAME, CATORDER FROM categories WHERE ID = ? ORDER BY CATORDER, NAME", SerializerWriteString.INSTANCE, CategoryInfo.getSerializerRead()).find((Object)id);
    }

    public final SentenceList<ProductInfoExt> getProductList() {
        return new StaticSentence<Object[], ProductInfoExt>(this.s, new QBFBuilder("SELECT ID, REFERENCE, CODE, CODETYPE, NAME, PRICEBUY, PRICESELL, CATEGORY, TAXCAT, ATTRIBUTESET_ID, STOCKCOST, STOCKVOLUME, IMAGE, ISCOM, ISSCALE, ISCONSTANT, PRINTKB, SENDSTATUS, ISSERVICE, ATTRIBUTES, DISPLAY, ISVPRICE, ISVERPATRIB, TEXTTIP, WARRANTY, STOCKUNITS, PRINTTO, SUPPLIER, UOM FROM products WHERE ?(QBF_FILTER) ORDER BY REFERENCE", new String[]{"NAME", "PRICEBUY", "PRICESELL", "CATEGORY", "CODE"}), new SerializerWriteBasic(Datas.OBJECT, Datas.STRING, Datas.OBJECT, Datas.DOUBLE, Datas.OBJECT, Datas.DOUBLE, Datas.OBJECT, Datas.STRING, Datas.OBJECT, Datas.STRING), ProductInfoExt.getSerializerRead());
    }

    public List<ProductInfoExt> getProductListQuick(String text) throws BasicException {
        return new PreparedSentence<Object[], ProductInfoExt>(this.s, "SELECT ID, REFERENCE, CODE, CODETYPE, NAME, PRICEBUY, PRICESELL, CATEGORY, TAXCAT, ATTRIBUTESET_ID, STOCKCOST, STOCKVOLUME, IMAGE, ISCOM, ISSCALE, ISCONSTANT, PRINTKB, SENDSTATUS, ISSERVICE, ATTRIBUTES, DISPLAY, ISVPRICE, ISVERPATRIB, TEXTTIP, WARRANTY, STOCKUNITS, PRINTTO, SUPPLIER, UOM FROM products WHERE (NAME LIKE ? OR CODE LIKE ? OR REFERENCE LIKE ?) AND ISCOM = " + this.s.DB.FALSE() + " ORDER BY NAME", new SerializerWriteBasic(Datas.STRING, Datas.STRING, Datas.STRING), ProductInfoExt.getSerializerRead()).list(text, text, text);
    }

    public SentenceList<ProductInfoExt> getProductListNormal() {
        return new StaticSentence<Object[], ProductInfoExt>(this.s, new QBFBuilder("SELECT ID, REFERENCE, CODE, CODETYPE, NAME, PRICEBUY, PRICESELL, CATEGORY, TAXCAT, ATTRIBUTESET_ID, STOCKCOST, STOCKVOLUME, IMAGE, ISCOM, ISSCALE, ISCONSTANT, PRINTKB, SENDSTATUS, ISSERVICE, ATTRIBUTES, DISPLAY, ISVPRICE, ISVERPATRIB, TEXTTIP, WARRANTY, STOCKUNITS, PRINTTO, SUPPLIER, UOM FROM products WHERE ISCOM = " + this.s.DB.FALSE() + " AND ?(QBF_FILTER) ORDER BY REFERENCE", new String[]{"NAME", "PRICEBUY", "PRICESELL", "CATEGORY", "CODE"}), new SerializerWriteBasic(Datas.OBJECT, Datas.STRING, Datas.OBJECT, Datas.DOUBLE, Datas.OBJECT, Datas.DOUBLE, Datas.OBJECT, Datas.STRING, Datas.OBJECT, Datas.STRING), ProductInfoExt.getSerializerRead());
    }

    public SentenceList<ProductInfo> getProductsList() {
        return new StaticSentence(this.s, "SELECT ID, REFERENCE, CODE, CODETYPE, NAME, PRICEBUY, PRICESELL, CATEGORY, TAXCAT, ATTRIBUTESET_ID, STOCKCOST, STOCKVOLUME, IMAGE, ISCOM, ISSCALE, ISCONSTANT, PRINTKB, SENDSTATUS, ISSERVICE, ATTRIBUTES, DISPLAY, ISVPRICE, ISVERPATRIB, TEXTTIP, WARRANTY, STOCKUNITS, PRINTTO, SUPPLIER, UOM FROM products ORDER BY NAME", null, ProductInfo.getSerializerRead());
    }

    public SentenceList<ProductInfoExt> getProductListAuxiliar() {
        return new StaticSentence<Object[], ProductInfoExt>(this.s, new QBFBuilder("SELECT ID, REFERENCE, CODE, CODETYPE, NAME, PRICEBUY, PRICESELL, CATEGORY, TAXCAT, ATTRIBUTESET_ID, STOCKCOST, STOCKVOLUME, IMAGE, ISCOM, ISSCALE, ISCONSTANT, PRINTKB, SENDSTATUS, ISSERVICE, ATTRIBUTES, DISPLAY, ISVPRICE, ISVERPATRIB, TEXTTIP, WARRANTY, STOCKUNITS, PRINTTO, SUPPLIER, UOM FROM products WHERE ISCOM = " + this.s.DB.TRUE() + " AND ?(QBF_FILTER) ORDER BY REFERENCE", new String[]{"NAME", "PRICEBUY", "PRICESELL", "CATEGORY", "CODE"}), new SerializerWriteBasic(Datas.OBJECT, Datas.STRING, Datas.OBJECT, Datas.DOUBLE, Datas.OBJECT, Datas.DOUBLE, Datas.OBJECT, Datas.STRING, Datas.OBJECT, Datas.STRING), ProductInfoExt.getSerializerRead());
    }

    public final List<ProductsBundleInfo> getProductsBundle(String productId) throws BasicException {
        return new PreparedSentence<String, ProductsBundleInfo>(this.s, "SELECT ID, PRODUCT, PRODUCT_BUNDLE, QUANTITY FROM products_bundle WHERE PRODUCT = ?", SerializerWriteString.INSTANCE, ProductsBundleInfo.getSerializerRead()).list((Object)productId);
    }

    public final ProductStock getProductStockState(String pId) throws BasicException {
        return (ProductStock)new PreparedSentence<String, ProductStock>(this.s, "SELECT products.id, locations.name as Location, stockcurrent.units AS Current, stocklevel.stocksecurity AS Minimum, stocklevel.stockmaximum AS Maximum, products.pricebuy, products.pricesell FROM locations INNER JOIN ((products INNER JOIN stockcurrent ON products.id = stockcurrent.product) LEFT JOIN stocklevel ON products.id = stocklevel.product) ON locations.id = stockcurrent.location WHERE products.id= ? ORDER BY locations.name", SerializerWriteString.INSTANCE, ProductStock.getSerializerRead()).find((Object)pId);
    }

    public final List<ProductStock> getProductStockList(String pId) throws BasicException {
        return new PreparedSentence<String, ProductStock>(this.s, "SELECT products.id, locations.name AS Location, stockcurrent.units AS Current, stocklevel.stocksecurity AS Minimum, stocklevel.stockmaximum AS Maximum, Round(products.pricebuy) AS PriceBuy, Round((products.pricesell * COALESCE(taxes.rate, 0)) + products.pricesell) AS PriceSell FROM products products LEFT OUTER JOIN stockcurrent stockcurrent ON (products.ID = stockcurrent.product) INNER JOIN locations locations ON (stockcurrent.location = locations.id) LEFT OUTER JOIN stocklevel stocklevel ON (stocklevel.product = products.ID AND stocklevel.location = stockcurrent.location) LEFT OUTER JOIN (SELECT category, MAX(rate) AS rate FROM taxes GROUP BY category) taxes ON (products.TAXCAT = taxes.category) WHERE products.id= ? ORDER BY locations.name", SerializerWriteString.INSTANCE, ProductStock.getSerializerRead()).list((Object)pId);
    }

    public final List<ReprintTicketInfo> getReprintTicketList() throws BasicException {
        return new StaticSentence(this.s, "SELECT T.TICKETID, T.TICKETTYPE, R.DATENEW, P.NAME, C.NAME, SUM(PM.TOTAL), T.STATUS FROM receipts R JOIN tickets T ON R.ID = T.ID LEFT OUTER JOIN payments PM ON R.ID = PM.RECEIPT LEFT OUTER JOIN customers C ON C.ID = T.CUSTOMER LEFT OUTER JOIN people P ON T.PERSON = P.ID GROUP BY T.ID, T.TICKETID, T.TICKETTYPE, R.DATENEW, P.NAME, C.NAME ORDER BY R.DATENEW DESC, T.TICKETID LIMIT 10 ", null, new SerializerReadClass<ReprintTicketInfo>(ReprintTicketInfo.class)).list();
    }

    public final TicketInfo getReprintTicket(String Id) throws BasicException {
        if (Id == null) {
            return null;
        }
        Object[] record = (Object[])new StaticSentence<String, Object[]>(this.s, "SELECT T.TICKETID, SUM(PM.TOTAL), R.DATENEW, P.NAME, T.TICKETTYPE, C.NAME, T.STATUS FROM receipts R JOIN tickets T ON R.ID = T.ID LEFT OUTER JOIN payments PM ON R.ID = PM.RECEIPT LEFT OUTER JOIN customers C ON C.ID = T.CUSTOMER LEFT OUTER JOIN people P ON T.PERSON = P.ID WHERE T.TICKETID = ?", SerializerWriteString.INSTANCE, new SerializerReadBasic(new Datas[]{Datas.SERIALIZABLE})).find((Object)Id);
        return record == null ? null : (TicketInfo)record[0];
    }

    public SentenceList<FindTicketsInfo> getTicketsList() {
        return new StaticSentence<Object[], FindTicketsInfo>(this.s, new QBFBuilder("SELECT T.TICKETID, T.TICKETTYPE, R.DATENEW, P.NAME, C.NAME, SUM(PM.TOTAL), T.STATUS FROM receipts R JOIN tickets T ON R.ID = T.ID LEFT OUTER JOIN payments PM ON R.ID = PM.RECEIPT LEFT OUTER JOIN customers C ON C.ID = T.CUSTOMER LEFT OUTER JOIN people P ON T.PERSON = P.ID WHERE ?(QBF_FILTER) GROUP BY T.ID, T.TICKETID, T.TICKETTYPE, R.DATENEW, P.NAME, C.NAME ORDER BY R.DATENEW DESC, T.TICKETID", new String[]{"T.TICKETID", "T.TICKETTYPE", "PM.TOTAL", "R.DATENEW", "R.DATENEW", "P.NAME", "C.NAME"}), new SerializerWriteBasic(Datas.OBJECT, Datas.INT, Datas.OBJECT, Datas.INT, Datas.OBJECT, Datas.DOUBLE, Datas.OBJECT, Datas.TIMESTAMP, Datas.OBJECT, Datas.TIMESTAMP, Datas.OBJECT, Datas.STRING, Datas.OBJECT, Datas.STRING), new SerializerReadClass<FindTicketsInfo>(FindTicketsInfo.class));
    }

    public final SentenceList<TaxCategoryInfo> getUserList() {
        return new StaticSentence(this.s, "SELECT ID, NAME FROM people ORDER BY NAME", null, dr -> new TaxCategoryInfo(dr.getString(1), dr.getString(2)));
    }

    public final SentenceList<TaxInfo> getTaxList() {
        return new StaticSentence(this.s, "SELECT ID, NAME, CATEGORY, CUSTCATEGORY, PARENTID, RATE, RATECASCADE, RATEORDER FROM taxes ORDER BY NAME", null, dr -> new TaxInfo(dr.getString(1), dr.getString(2), dr.getString(3), dr.getString(4), dr.getString(5), dr.getDouble(6), dr.getBoolean(7), dr.getInt(8)));
    }

    public final SentenceList<CategoryInfo> getCategoriesList() {
        return new StaticSentence(this.s, "SELECT ID, NAME, IMAGE, TEXTTIP, CATSHOWNAME, CATORDER FROM categories ORDER BY NAME", null, CategoryInfo.getSerializerRead());
    }

    public final SentenceList<CategoryInfo> getCategoriesList_1() {
        return new StaticSentence(this.s, "SELECT ID, NAME, IMAGE, TEXTTIP, CATSHOWNAME, CATORDER FROM categories WHERE PARENTID IS NULL ORDER BY NAME", null, CategoryInfo.getSerializerRead());
    }

    public final SentenceList<SupplierInfo> getSuppList() {
        return new StaticSentence(this.s, "SELECT ID, SEARCHKEY, NAME FROM suppliers ORDER BY NAME", null, dr -> new SupplierInfo(dr.getString(1), dr.getString(2), dr.getString(3)));
    }

    public final SentenceList<TaxCustCategoryInfo> getTaxCustCategoriesList() {
        return new StaticSentence(this.s, "SELECT ID, NAME FROM taxcustcategories ORDER BY NAME", null, dr -> new TaxCustCategoryInfo(dr.getString(1), dr.getString(2)));
    }

    public final CustomerInfoExt getCustomerInfo(String id) throws BasicException {
        return (CustomerInfoExt)new PreparedSentence<String, CustomerInfoExt>(this.s, "SELECT ID, SEARCHKEY, TAXID, NAME, TAXCATEGORY, CARD, MAXDEBT, ADDRESS, ADDRESS2, POSTAL, CITY, REGION, COUNTRY, FIRSTNAME, LASTNAME, EMAIL, PHONE, PHONE2, FAX, NOTES, VISIBLE, CURDATE, CURDEBT, IMAGE, ISVIP, DISCOUNT, DV, DOC_TYPE, PERSON_TYPE, LIABILITY, MUNICIPALITY_CODE, REGIME_CODE FROM customers WHERE ID = ?", SerializerWriteString.INSTANCE, new CustomerExtRead()).find((Object)id);
    }

    public final List<CustomerTransaction> getCustomersTransactionList(String cId) throws BasicException {
        return new PreparedSentence<String, CustomerTransaction>(this.s, "SELECT tickets.TICKETID, products.NAME AS PNAME, SUM(ticketlines.UNITS) AS UNITS, SUM(ticketlines.UNITS * ticketlines.PRICE) AS AMOUNT, SUM(ticketlines.UNITS * ticketlines.PRICE * (1.0 + taxes.RATE)) AS TOTAL, receipts.DATENEW, customers.ID AS CID FROM ((((ticketlines ticketlines CROSS JOIN taxes taxes ON (ticketlines.TAXID = taxes.ID)) INNER JOIN tickets tickets ON (tickets.ID = ticketlines.TICKET)) INNER JOIN customers customers ON (customers.ID = tickets.CUSTOMER)) INNER JOIN receipts receipts ON (tickets.ID = receipts.ID)) LEFT OUTER JOIN products products ON (ticketlines.PRODUCT = products.ID) WHERE tickets.CUSTOMER = ? GROUP BY customers.ID, receipts.DATENEW, tickets.TICKETID, products.NAME, tickets.TICKETTYPE ORDER BY receipts.DATENEW DESC", SerializerWriteString.INSTANCE, CustomerTransaction.getSerializerRead()).list((Object)cId);
    }

    public final SentenceList<TaxCategoryInfo> getTaxCategoriesList() {
        return new StaticSentence(this.s, "SELECT ID, NAME FROM taxcategories ORDER BY NAME", null, dr -> new TaxCategoryInfo(dr.getString(1), dr.getString(2)));
    }

    public final SentenceList<AttributeSetInfo> getAttributeSetList() {
        return new StaticSentence(this.s, "SELECT ID, NAME FROM attributeset ORDER BY NAME", null, dr -> new AttributeSetInfo(dr.getString(1), dr.getString(2)));
    }

    public final SentenceList<LocationInfo> getLocationsList() {
        return new StaticSentence(this.s, "SELECT ID, NAME, ADDRESS FROM locations ORDER BY NAME", null, new SerializerReadClass<LocationInfo>(LocationInfo.class));
    }

    public final SentenceList<FloorsInfo> getFloorsList() {
        return new StaticSentence(this.s, "SELECT ID, NAME FROM floors ORDER BY NAME", null, new SerializerReadClass<FloorsInfo>(FloorsInfo.class));
    }

    public final SentenceList<FloorsInfo> getFloorTablesList() {
        return new StaticSentence(this.s, "SELECT ID, NAME FROM places ORDER BY NAME", null, new SerializerReadClass<FloorsInfo>(FloorsInfo.class));
    }

    public CustomerInfoExt findCustomerExt(String card) throws BasicException {
        return (CustomerInfoExt)new PreparedSentence<String, CustomerInfoExt>(this.s, "SELECT ID, TAXID, SEARCHKEY, NAME, TAXCATEGORY, CARD, MAXDEBT, ADDRESS, ADDRESS2, POSTAL, CITY, REGION, COUNTRY, FIRSTNAME, LASTNAME, EMAIL, PHONE, PHONE2, FAX, NOTES, VISIBLE, CURDATE, CURDEBT, IMAGE, ISVIP, DISCOUNT, DV, DOC_TYPE, PERSON_TYPE, LIABILITY, MUNICIPALITY_CODE, REGIME_CODE FROM customers WHERE CARD = ? AND VISIBLE = " + this.s.DB.TRUE() + " ORDER BY NAME", SerializerWriteString.INSTANCE, new CustomerExtRead()).find((Object)card);
    }

    public CustomerInfoExt findCustomerName(String name) throws BasicException {
        return (CustomerInfoExt)new PreparedSentence<String, CustomerInfoExt>(this.s, "SELECT ID, SEARCHKEY, TAXID, NAME, TAXCATEGORY, CARD, MAXDEBT, ADDRESS, ADDRESS2, POSTAL, CITY, REGION, COUNTRY, FIRSTNAME, LASTNAME, EMAIL, PHONE, PHONE2, FAX, NOTES, VISIBLE, CURDATE, CURDEBT, IMAGE, ISVIP, DISCOUNT, DV, DOC_TYPE, PERSON_TYPE, LIABILITY, MUNICIPALITY_CODE, REGIME_CODE FROM customers WHERE NAME = ? AND VISIBLE = " + this.s.DB.TRUE() + " ORDER BY NAME", SerializerWriteString.INSTANCE, new CustomerExtRead()).find((Object)name);
    }

    public CustomerInfoExt loadCustomerExt(String id) throws BasicException {
        return (CustomerInfoExt)new PreparedSentence<String, CustomerInfoExt>(this.s, "SELECT ID, SEARCHKEY, TAXID, NAME, TAXCATEGORY, CARD, MAXDEBT, ADDRESS, ADDRESS2, POSTAL, CITY, REGION, COUNTRY, FIRSTNAME, LASTNAME, EMAIL, PHONE, PHONE2, FAX, NOTES, VISIBLE, CURDATE, CURDEBT, IMAGE, ISVIP, DISCOUNT, DV, DOC_TYPE, PERSON_TYPE, LIABILITY, MUNICIPALITY_CODE, REGIME_CODE FROM customers WHERE ID = ?", SerializerWriteString.INSTANCE, new CustomerExtRead()).find((Object)id);
    }

    public CustomerInfoExt qCustomerExt(String id) throws BasicException {
        return (CustomerInfoExt)new PreparedSentence<String, CustomerInfoExt>(this.s, "SELECT ID, SEARCHKEY, TAXID, NAME, TAXCATEGORY, MAXDEBT, FIRSTNAME, LASTNAME, EMAIL, PHONE, PHONE2, ISVIP, DISCOUNT FROM customers WHERE ID = ?", SerializerWriteString.INSTANCE, new CustomerExtRead()).find((Object)id);
    }

    public final boolean isCashActive(String id) throws BasicException {
        return new PreparedSentence<String, String>(this.s, "SELECT MONEY FROM closedcash WHERE DATEEND IS NULL AND MONEY = ?", SerializerWriteString.INSTANCE, SerializerReadString.INSTANCE).find((Object)id) != null;
    }

    public final TicketInfo loadTicket(final int tickettype, final int ticketid) throws BasicException {
        TicketInfo ticket = (TicketInfo)new PreparedSentence<DataParams, TicketInfo>(this.s, "SELECT T.ID, T.TICKETTYPE, T.TICKETID, R.DATENEW, R.MONEY, R.ATTRIBUTES, P.ID, P.NAME, T.CUSTOMER, T.STATUS FROM receipts R JOIN tickets T ON R.ID = T.ID LEFT OUTER JOIN people P ON T.PERSON = P.ID WHERE T.TICKETTYPE = ? AND T.TICKETID = ? ORDER BY R.DATENEW DESC", SerializerWriteParams.INSTANCE, new SerializerReadClass<TicketInfo>(TicketInfo.class)).find((Object)new DataParams(){

            @Override
            public void writeValues() throws BasicException {
                this.setInt(1, tickettype);
                this.setInt(2, ticketid);
            }
        });
        if (ticket != null) {
            String customerid = ticket.getCustomerId();
            ticket.setCustomer(customerid == null ? null : this.loadCustomerExt(customerid));
            ticket.setLines(new PreparedSentence<String, TicketLineInfo>(this.s, "SELECT L.TICKET, L.LINE, L.PRODUCT, L.ATTRIBUTESETINSTANCE_ID, L.UNITS, L.PRICE, T.ID, T.NAME, T.CATEGORY, T.CUSTCATEGORY, T.PARENTID, T.RATE, T.RATECASCADE, T.RATEORDER, L.ATTRIBUTES FROM ticketlines L, taxes T WHERE L.TAXID = T.ID AND L.TICKET = ? ORDER BY L.LINE", SerializerWriteString.INSTANCE, new SerializerReadClass<TicketLineInfo>(TicketLineInfo.class)).list((Object)ticket.getId()));
            ticket.setPayments(new PreparedSentence<String, PaymentInfoTicket>(this.s, "SELECT PAYMENT, TOTAL, TRANSID, TENDERED, CARDNAME FROM payments WHERE RECEIPT = ?", SerializerWriteString.INSTANCE, new SerializerReadClass<PaymentInfoTicket>(PaymentInfoTicket.class)).list((Object)ticket.getId()));
        }
        return ticket;
    }

    public final void saveTicket(final TicketInfo ticket, final String location) throws BasicException {
        Transaction<Object> t = new Transaction<Object>(this.s){

            @Override
            public Object transact() throws BasicException {
                if (ticket.getTicketId() == 0) {
                    switch (ticket.getTicketType()) {
                        case 0: {
                            ticket.setTicketId(DataLogicSales.this.getNextTicketIndex());
                            break;
                        }
                        case 1: {
                            ticket.setTicketId(DataLogicSales.this.getNextTicketRefundIndex());
                            break;
                        }
                        case 2: {
                            ticket.setTicketId(DataLogicSales.this.getNextTicketPaymentIndex());
                            break;
                        }
                        case 3: {
                            ticket.setTicketId(DataLogicSales.this.getNextTicketPaymentIndex());
                            break;
                        }
                        default: {
                            throw new BasicException();
                        }
                    }
                }
                new PreparedSentence(DataLogicSales.this.s, "INSERT INTO receipts (ID, MONEY, DATENEW, ATTRIBUTES, PERSON) VALUES (?, ?, ?, ?, ?)", SerializerWriteParams.INSTANCE).exec(new DataParams(){

                    @Override
                    public void writeValues() throws BasicException {
                        this.setString(1, ticket.getId());
                        this.setString(2, ticket.getActiveCash());
                        this.setTimestamp(3, ticket.getDate());
                        try {
                            ByteArrayOutputStream o = new ByteArrayOutputStream();
                            ticket.getProperties().storeToXML(o, "SOLTEC POS", "UTF-8");
                            this.setBytes(4, o.toByteArray());
                        }
                        catch (IOException e) {
                            this.setBytes(4, null);
                        }
                        this.setString(5, ticket.getProperty("person"));
                    }
                });
                new PreparedSentence(DataLogicSales.this.s, "INSERT INTO tickets (ID, TICKETTYPE, TICKETID, PERSON, CUSTOMER, STATUS) VALUES (?, ?, ?, ?, ?, ?)", SerializerWriteParams.INSTANCE).exec(new DataParams(){

                    @Override
                    public void writeValues() throws BasicException {
                        this.setString(1, ticket.getId());
                        this.setInt(2, ticket.getTicketType());
                        this.setInt(3, ticket.getTicketId());
                        this.setString(4, ticket.getUser().getId());
                        this.setString(5, ticket.getCustomerId());
                        this.setInt(6, ticket.getTicketStatus());
                    }
                });
                PreparedSentence ticketstatusupdate = new PreparedSentence(DataLogicSales.this.s, "UPDATE tickets SET STATUS =" + ticket.getTicketId() + " WHERE TICKETID = " + ticket.getTicketStatus() + " ", SerializerWriteParams.INSTANCE);
                PreparedSentence ticketlineinsert = new PreparedSentence(DataLogicSales.this.s, "INSERT INTO ticketlines (TICKET, LINE, PRODUCT, ATTRIBUTESETINSTANCE_ID, UNITS, PRICE, TAXID, ATTRIBUTES) VALUES (?, ?, ?, ?, ?, ?, ?, ?)", SerializerWriteBuilder.INSTANCE);
                for (TicketLineInfo l : ticket.getLines()) {
                    ticketlineinsert.exec(l);
                    if (l.getProductID() == null || l.isProductService()) continue;
                    DataLogicSales.this.getStockDiaryInsert().exec(new Object[]{UUID.randomUUID().toString(), ticket.getDate(), l.getMultiply() < 0.0 ? MovementReason.IN_REFUND.getKey() : MovementReason.OUT_SALE.getKey(), location, l.getProductID(), l.getProductAttSetInstId(), -l.getMultiply(), l.getPrice(), ticket.getUser().getName()});
                }
                final Payments payments = new Payments();
                PreparedSentence paymentinsert = new PreparedSentence(DataLogicSales.this.s, "INSERT INTO payments (ID, RECEIPT, PAYMENT, TOTAL, TRANSID, RETURNMSG, TENDERED, CARDNAME, VOUCHER) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)", SerializerWriteParams.INSTANCE);
                for (PaymentInfo p : ticket.getPayments()) {
                    payments.addPayment(p.getName(), p.getTotal(), p.getPaid(), ticket.getReturnMessage());
                }
                while (payments.getSize() >= 1) {
                    paymentinsert.exec(new DataParams(){

                        @Override
                        public void writeValues() throws BasicException {
                            DataLogicSales.this.pName = payments.getFirstElement();
                            DataLogicSales.this.getTotal = payments.getPaidAmount(DataLogicSales.this.pName);
                            DataLogicSales.this.getTendered = payments.getTendered(DataLogicSales.this.pName);
                            DataLogicSales.this.getRetMsg = payments.getRtnMessage(DataLogicSales.this.pName);
                            payments.removeFirst(DataLogicSales.this.pName);
                            this.setString(1, UUID.randomUUID().toString());
                            this.setString(2, ticket.getId());
                            this.setString(3, DataLogicSales.this.pName);
                            this.setDouble(4, DataLogicSales.this.getTotal);
                            this.setString(5, ticket.getTransactionID());
                            this.setBytes(6, (byte[])Formats.BYTEA.parseValue(DataLogicSales.this.getRetMsg));
                            this.setDouble(7, DataLogicSales.this.getTendered);
                            this.setString(8, DataLogicSales.this.getCardName);
                            this.setString(9, DataLogicSales.this.getVoucher);
                            payments.removeFirst(DataLogicSales.this.pName);
                        }
                    });
                    if (!DataLogicSales.DEBT.equals(DataLogicSales.this.pName) && !DataLogicSales.DEBT_PAID.equals(DataLogicSales.this.pName)) continue;
                    ticket.getCustomer().updateCurDebt(DataLogicSales.this.getTotal, ticket.getDate());
                    DataLogicSales.this.getDebtUpdate().exec(new DataParams(){

                        @Override
                        public void writeValues() throws BasicException {
                            this.setDouble(1, ticket.getCustomer().getCurdebt());
                            this.setTimestamp(2, ticket.getCustomer().getCurdate());
                            this.setString(3, ticket.getCustomer().getId());
                        }
                    });
                }
                PreparedSentence taxlinesinsert = new PreparedSentence(DataLogicSales.this.s, "INSERT INTO taxlines (ID, RECEIPT, TAXID, BASE, AMOUNT)  VALUES (?, ?, ?, ?, ?)", SerializerWriteParams.INSTANCE);
                if (ticket.getTaxes() != null) {
                    for (final TicketTaxInfo tickettax : ticket.getTaxes()) {
                        taxlinesinsert.exec(new DataParams(){

                            @Override
                            public void writeValues() throws BasicException {
                                this.setString(1, UUID.randomUUID().toString());
                                this.setString(2, ticket.getId());
                                this.setString(3, tickettax.getTaxInfo().getId());
                                this.setDouble(4, tickettax.getSubTotal());
                                this.setDouble(5, tickettax.getTax());
                            }
                        });
                    }
                }
                return null;
            }
        };
        t.execute();
    }

    public final void deleteTicket(final TicketInfo ticket, final String location) throws BasicException {
        Transaction<Object> t = new Transaction<Object>(this.s){

            @Override
            public Object transact() throws BasicException {
                Date d = new Date();
                for (int i = 0; i < ticket.getLinesCount(); ++i) {
                    List<ProductsBundleInfo> bundle;
                    if (ticket.getLine(i).getProductID() != null) {
                        DataLogicSales.this.getStockDiaryInsert().exec(new Object[]{UUID.randomUUID().toString(), d, ticket.getLine(i).getMultiply() >= 0.0 ? MovementReason.IN_REFUND.getKey() : MovementReason.OUT_SALE.getKey(), location, ticket.getLine(i).getProductID(), ticket.getLine(i).getProductAttSetInstId(), ticket.getLine(i).getMultiply(), ticket.getLine(i).getPrice(), ticket.getUser().getName()});
                    }
                    if ((bundle = DataLogicSales.this.getProductsBundle(ticket.getLine(i).getProductID())).size() <= 0) continue;
                    for (ProductsBundleInfo bundleComponent : bundle) {
                        ProductInfoExt bundleProduct = DataLogicSales.this.getProductInfo(bundleComponent.getProductBundleId());
                        DataLogicSales.this.getStockDiaryInsert().exec(new Object[]{UUID.randomUUID().toString(), d, ticket.getLine(i).getMultiply() * bundleComponent.getQuantity() >= 0.0 ? MovementReason.IN_REFUND.getKey() : MovementReason.OUT_SALE.getKey(), location, bundleComponent.getProductBundleId(), null, ticket.getLine(i).getMultiply() * bundleComponent.getQuantity(), bundleProduct.getPriceSell(), ticket.getUser().getName()});
                    }
                }
                for (PaymentInfo p : ticket.getPayments()) {
                    if (!DataLogicSales.DEBT.equals(p.getName()) && !DataLogicSales.DEBT_PAID.equals(p.getName())) continue;
                    ticket.getCustomer().updateCurDebt(-p.getTotal(), ticket.getDate());
                    DataLogicSales.this.getDebtUpdate().exec(new DataParams(){

                        @Override
                        public void writeValues() throws BasicException {
                            this.setDouble(1, ticket.getCustomer().getCurdebt());
                            this.setTimestamp(2, ticket.getCustomer().getCurdate());
                            this.setString(3, ticket.getCustomer().getId());
                        }
                    });
                }
                new StaticSentence(DataLogicSales.this.s, "DELETE FROM taxlines WHERE RECEIPT = ?", SerializerWriteString.INSTANCE).exec(ticket.getId());
                new StaticSentence(DataLogicSales.this.s, "DELETE FROM payments WHERE RECEIPT = ?", SerializerWriteString.INSTANCE).exec(ticket.getId());
                new StaticSentence(DataLogicSales.this.s, "DELETE FROM ticketlines WHERE TICKET = ?", SerializerWriteString.INSTANCE).exec(ticket.getId());
                new StaticSentence(DataLogicSales.this.s, "DELETE FROM tickets WHERE ID = ?", SerializerWriteString.INSTANCE).exec(ticket.getId());
                new StaticSentence(DataLogicSales.this.s, "DELETE FROM receipts WHERE ID = ?", SerializerWriteString.INSTANCE).exec(ticket.getId());
                return null;
            }
        };
        t.execute();
    }

    public final Integer getNextPickupIndex() throws BasicException {
        return (Integer)this.s.DB.getSequenceSentence(this.s, "pickup_number").find();
    }

    public final Integer getNextTicketIndex() throws BasicException {
        return (Integer)this.s.DB.getSequenceSentence(this.s, "ticketsnum").find();
    }

    public final Integer getNextTicketRefundIndex() throws BasicException {
        return (Integer)this.s.DB.getSequenceSentence(this.s, "ticketsnum_refund").find();
    }

    public final Integer getNextTicketPaymentIndex() throws BasicException {
        return (Integer)this.s.DB.getSequenceSentence(this.s, "ticketsnum_payment").find();
    }

    public final SentenceFind<BufferedImage> getProductImage() {
        return new PreparedSentence<String, BufferedImage>(this.s, "SELECT IMAGE FROM products WHERE ID = ?", SerializerWriteString.INSTANCE, dr -> ImageUtils.readImage(dr.getBytes(1)));
    }

    public final SentenceList<Object[]> getProductCatQBF() {
        return new StaticSentence<Object[], Object[]>(this.s, new QBFBuilder("SELECT P.ID, P.REFERENCE, P.CODE, P.CODETYPE, P.NAME, P.PRICEBUY, P.PRICESELL, P.CATEGORY, P.TAXCAT, P.ATTRIBUTESET_ID, P.STOCKCOST, P.STOCKVOLUME, " + this.s.DB.CHAR_NULL() + ",P.ISCOM, P.ISSCALE, P.ISCONSTANT, P.PRINTKB, P.SENDSTATUS, P.ISSERVICE, P.ATTRIBUTES, P.DISPLAY, P.ISVPRICE, P.ISVERPATRIB, P.TEXTTIP, P.WARRANTY, P.STOCKUNITS, P.PRINTTO, P.SUPPLIER, P.UOM, CASE WHEN C.PRODUCT IS NULL THEN " + this.s.DB.FALSE() + " ELSE " + this.s.DB.TRUE() + " END, C.CATORDER FROM products P LEFT OUTER JOIN products_cat C ON P.ID = C.PRODUCT WHERE ?(QBF_FILTER) ORDER BY P.REFERENCE", new String[]{"P.NAME", "P.PRICEBUY", "P.PRICESELL", "P.CATEGORY", "P.CODE"}), new SerializerWriteBasic(Datas.OBJECT, Datas.STRING, Datas.OBJECT, Datas.DOUBLE, Datas.OBJECT, Datas.DOUBLE, Datas.OBJECT, Datas.STRING, Datas.OBJECT, Datas.STRING), this.productsRow.getSerializerRead());
    }

    public final SentenceExec<Object[]> getProductCatInsert() {
        return new SentenceExecTransaction<Object[]>(this.s){

            @Override
            public int execInTransaction(Object[] params) throws BasicException {
                Object[] values = params;
                int i = new PreparedSentence(DataLogicSales.this.s, "INSERT INTO products (ID, REFERENCE, CODE, CODETYPE, NAME, PRICEBUY, PRICESELL, CATEGORY, TAXCAT, ATTRIBUTESET_ID, STOCKCOST, STOCKVOLUME, IMAGE, ISCOM, ISSCALE, ISCONSTANT, PRINTKB, SENDSTATUS, ISSERVICE, ATTRIBUTES, DISPLAY, ISVPRICE, ISVERPATRIB, TEXTTIP, WARRANTY, STOCKUNITS, PRINTTO, SUPPLIER, UOM ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", new SerializerWriteBasicExt(DataLogicSales.this.productsRow.getDatas(), new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28})).exec(params);
                if (i > 0 && ((Boolean)values[29]).booleanValue()) {
                    return new PreparedSentence(DataLogicSales.this.s, "INSERT INTO products_cat (PRODUCT, CATORDER) VALUES (?, ?)", new SerializerWriteBasicExt(DataLogicSales.this.productsRow.getDatas(), new int[]{0, 30})).exec(params);
                }
                return i;
            }
        };
    }

    public final SentenceExec<Object[]> getProductCatUpdate() {
        return new SentenceExecTransaction<Object[]>(this.s){

            @Override
            public int execInTransaction(Object[] params) throws BasicException {
                Object[] values = params;
                int i = new PreparedSentence(DataLogicSales.this.s, "UPDATE products SET ID = ?, REFERENCE = ?, CODE = ?, CODETYPE = ?, NAME = ?, PRICEBUY = ?, PRICESELL = ?, CATEGORY = ?, TAXCAT = ?, ATTRIBUTESET_ID = ?, STOCKCOST = ?, STOCKVOLUME = ?, IMAGE = ?, ISCOM = ?, ISSCALE = ?, ISCONSTANT = ?, PRINTKB = ?, SENDSTATUS = ?, ISSERVICE = ?,  ATTRIBUTES = ?,DISPLAY = ?, ISVPRICE = ?, ISVERPATRIB = ?, TEXTTIP = ?, WARRANTY = ?, STOCKUNITS = ?, PRINTTO = ?, SUPPLIER = ?, UOM = ? WHERE ID = ?", new SerializerWriteBasicExt(DataLogicSales.this.productsRow.getDatas(), new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 0})).exec(params);
                if (i > 0) {
                    if (((Boolean)values[29]).booleanValue()) {
                        if (new PreparedSentence(DataLogicSales.this.s, "UPDATE products_cat SET CATORDER = ? WHERE PRODUCT = ?", new SerializerWriteBasicExt(DataLogicSales.this.productsRow.getDatas(), new int[]{30, 0})).exec(params) == 0) {
                            new PreparedSentence(DataLogicSales.this.s, "INSERT INTO products_cat (PRODUCT, CATORDER) VALUES (?, ?)", new SerializerWriteBasicExt(DataLogicSales.this.productsRow.getDatas(), new int[]{0, 30})).exec(params);
                        }
                    } else {
                        new PreparedSentence(DataLogicSales.this.s, "DELETE FROM products_cat WHERE PRODUCT = ?", new SerializerWriteBasicExt(DataLogicSales.this.productsRow.getDatas(), new int[]{0})).exec(params);
                    }
                }
                return i;
            }
        };
    }

    public final SentenceExec<Object[]> getProductCatDelete() {
        return new SentenceExecTransaction<Object[]>(this.s){

            @Override
            public int execInTransaction(Object[] params) throws BasicException {
                new PreparedSentence(DataLogicSales.this.s, "DELETE FROM products_cat WHERE PRODUCT = ?", new SerializerWriteBasicExt(DataLogicSales.this.productsRow.getDatas(), new int[]{0})).exec(params);
                return new PreparedSentence(DataLogicSales.this.s, "DELETE FROM products where ID = ?", new SerializerWriteBasicExt(DataLogicSales.this.productsRow.getDatas(), new int[]{0})).exec(params);
            }
        };
    }

    public final SentenceExec<DataParams> getDebtUpdate() {
        return new PreparedSentence(this.s, "UPDATE customers SET CURDEBT = ?, CURDATE = ? WHERE ID = ?", SerializerWriteParams.INSTANCE);
    }

    public final SentenceExec<Object[]> getStockDiaryInsert() {
        return new SentenceExecTransaction<Object[]>(this.s){

            @Override
            public int execInTransaction(Object[] params) throws BasicException {
                Object[] adjustParams = new Object[4];
                Object[] paramsArray = params;
                adjustParams[0] = paramsArray[4];
                adjustParams[1] = paramsArray[3];
                adjustParams[2] = paramsArray[5];
                adjustParams[3] = paramsArray[6];
                DataLogicSales.this.adjustStock(adjustParams);
                return new PreparedSentence(DataLogicSales.this.s, "INSERT INTO stockdiary (ID, DATENEW, REASON, LOCATION, PRODUCT, ATTRIBUTESETINSTANCE_ID, UNITS, PRICE, AppUser) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)", new SerializerWriteBasicExt(DataLogicSales.this.stockdiaryDatas, new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8})).exec(params);
            }
        };
    }

    public final SentenceExec<Object[]> getStockDiaryInsert1() {
        return new SentenceExecTransaction<Object[]>(this.s){

            @Override
            public int execInTransaction(Object[] params) throws BasicException {
                int updateresult;
                int n = updateresult = params[5] == null ? new PreparedSentence(DataLogicSales.this.s, "UPDATE stockcurrent SET UNITS = (UNITS + ?) WHERE LOCATION = ? AND PRODUCT = ? AND ATTRIBUTESETINSTANCE_ID IS NULL", new SerializerWriteBasicExt(DataLogicSales.this.stockdiaryDatas, new int[]{6, 3, 4})).exec(params) : new PreparedSentence(DataLogicSales.this.s, "UPDATE stockcurrent SET UNITS = (UNITS + ?) WHERE LOCATION = ? AND PRODUCT = ? AND ATTRIBUTESETINSTANCE_ID = ?", new SerializerWriteBasicExt(DataLogicSales.this.stockdiaryDatas, new int[]{6, 3, 4, 5})).exec(params);
                if (updateresult == 0) {
                    new PreparedSentence(DataLogicSales.this.s, "INSERT INTO stockcurrent (LOCATION, PRODUCT, ATTRIBUTESETINSTANCE_ID, UNITS) VALUES (?, ?, ?, ?)", new SerializerWriteBasicExt(DataLogicSales.this.stockdiaryDatas, new int[]{3, 4, 5, 6})).exec(params);
                }
                return new PreparedSentence(DataLogicSales.this.s, "INSERT INTO stockdiary (ID, DATENEW, REASON, LOCATION, PRODUCT, ATTRIBUTESETINSTANCE_ID, UNITS, PRICE, AppUser, SUPPLIER, SUPPLIERDOC) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", new SerializerWriteBasicExt(DataLogicSales.this.stockdiaryDatas, new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10})).exec(params);
            }
        };
    }

    public final SentenceExec<Object[]> getStockDiaryDelete() {
        return new SentenceExecTransaction<Object[]>(this.s){

            @Override
            public int execInTransaction(Object[] params) throws BasicException {
                int updateresult;
                int n = updateresult = params[5] == null ? new PreparedSentence(DataLogicSales.this.s, "UPDATE stockcurrent SET UNITS = (UNITS - ?) WHERE LOCATION = ? AND PRODUCT = ? AND ATTRIBUTESETINSTANCE_ID IS NULL", new SerializerWriteBasicExt(DataLogicSales.this.stockdiaryDatas, new int[]{6, 3, 4})).exec(params) : new PreparedSentence(DataLogicSales.this.s, "UPDATE stockcurrent SET UNITS = (UNITS - ?) WHERE LOCATION = ? AND PRODUCT = ? AND ATTRIBUTESETINSTANCE_ID = ?", new SerializerWriteBasicExt(DataLogicSales.this.stockdiaryDatas, new int[]{6, 3, 4, 5})).exec(params);
                if (updateresult == 0) {
                    new PreparedSentence(DataLogicSales.this.s, "INSERT INTO stockcurrent (LOCATION, PRODUCT, ATTRIBUTESETINSTANCE_ID, UNITS) VALUES (?, ?, ?, -(?))", new SerializerWriteBasicExt(DataLogicSales.this.stockdiaryDatas, new int[]{3, 4, 5, 6})).exec(params);
                }
                return new PreparedSentence(DataLogicSales.this.s, "DELETE FROM stockdiary WHERE ID = ?", new SerializerWriteBasicExt(DataLogicSales.this.stockdiaryDatas, new int[]{0})).exec(params);
            }
        };
    }

    private void adjustStock(Object[] params) throws BasicException {
        List<ProductsBundleInfo> bundle = this.getProductsBundle((String)params[0]);
        if (bundle.size() > 0) {
            for (ProductsBundleInfo component : bundle) {
                Object[] adjustParams = new Object[]{component.getProductBundleId(), params[1], params[2], (Double)params[3] * component.getQuantity()};
                this.adjustStock(adjustParams);
            }
        } else {
            int updateresult;
            int n = updateresult = params[2] == null ? new PreparedSentence(this.s, "UPDATE stockcurrent SET UNITS = (UNITS + ?) WHERE LOCATION = ? AND PRODUCT = ? AND ATTRIBUTESETINSTANCE_ID IS NULL", new SerializerWriteBasicExt(this.stockAdjustDatas, new int[]{3, 1, 0})).exec(params) : new PreparedSentence(this.s, "UPDATE stockcurrent SET UNITS = (UNITS + ?) WHERE LOCATION = ? AND PRODUCT = ? AND ATTRIBUTESETINSTANCE_ID = ?", new SerializerWriteBasicExt(this.stockAdjustDatas, new int[]{3, 1, 0, 2})).exec(params);
            if (updateresult == 0) {
                new PreparedSentence(this.s, "INSERT INTO stockcurrent (LOCATION, PRODUCT, ATTRIBUTESETINSTANCE_ID, UNITS) VALUES (?, ?, ?, ?)", new SerializerWriteBasicExt(this.stockAdjustDatas, new int[]{1, 0, 2, 3})).exec(params);
            }
        }
    }

    public final SentenceExec<Object[]> getPaymentMovementInsert() {
        return new SentenceExecTransaction<Object[]>(this.s){

            @Override
            public int execInTransaction(Object[] params) throws BasicException {
                new PreparedSentence(DataLogicSales.this.s, "INSERT INTO receipts (ID, MONEY, DATENEW, PERSON) VALUES (?, ?, ?, ?)", new SerializerWriteBasicExt(DataLogicSales.this.paymenttabledatas, new int[]{0, 1, 2, 8})).exec(params);
                return new PreparedSentence(DataLogicSales.this.s, "INSERT INTO payments (ID, RECEIPT, PAYMENT, TOTAL, NOTES, CATEGORY) VALUES (?, ?, ?, ?, ?, ?)", new SerializerWriteBasicExt(DataLogicSales.this.paymenttabledatas, new int[]{3, 0, 4, 5, 6, 9})).exec(params);
            }
        };
    }

    public final SentenceExec<Object[]> getPaymentMovementUpdate() {
        return new SentenceExecTransaction<Object[]>(this.s){

            @Override
            public int execInTransaction(Object[] params) throws BasicException {
                new PreparedSentence(DataLogicSales.this.s, "UPDATE receipts SET PERSON = ? WHERE ID = ?", new SerializerWriteBasicExt(DataLogicSales.this.paymenttabledatas, new int[]{8, 0})).exec(params);
                int count = new PreparedSentence(DataLogicSales.this.s, "UPDATE payments SET PAYMENT = ?, TOTAL = ?, NOTES = ?, CATEGORY = ? WHERE ID = ?", new SerializerWriteBasicExt(DataLogicSales.this.paymenttabledatas, new int[]{4, 5, 6, 9, 3})).exec(params);
                if (count > 0) {
                    new PreparedSentence(DataLogicSales.this.s, "INSERT INTO audit_lines (ID, DATE, TYPE, USER, PRODUCT, QTY, OLD_VALUE, NEW_VALUE, INFO, TICKET_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", new SerializerWriteBasic(Datas.STRING, Datas.TIMESTAMP, Datas.STRING, Datas.STRING, Datas.STRING, Datas.DOUBLE, Datas.STRING, Datas.STRING, Datas.STRING, Datas.STRING)).exec(new Object[]{UUID.randomUUID().toString(), new Date(), "PAYMENT UPDATE", params[8], "PAYMENT", 0.0, null, Formats.DOUBLE.formatValue(params[5]), (String)params[6], params[3]});
                }
                return count;
            }
        };
    }

    public final SentenceList<Object[]> getPaymentCategoriesList() {
        return new StaticSentence(this.s, "SELECT ID, NAME FROM payment_categories ORDER BY NAME", null, new SerializerReadBasic(new Datas[]{Datas.STRING, Datas.STRING}));
    }

    public final SentenceList<Object[]> getPaymentCategoriesGlobal(boolean bGlobal) {
        String bitValue = bGlobal ? "b'1'" : "b'0'";
        return new StaticSentence(this.s, "SELECT ID, NAME FROM payment_categories WHERE IS_GLOBAL = " + bitValue + " ORDER BY NAME", null, new SerializerReadBasic(new Datas[]{Datas.STRING, Datas.STRING}));
    }

    public final List<Object[]> getGlobalPaymentsList() throws BasicException {
        return new PreparedSentence(this.s, "SELECT R.ID, R.MONEY, R.DATENEW, P.ID, P.PAYMENT, P.TOTAL, P.NOTES, PC.NAME, PP.NAME, P.CATEGORY FROM payments P JOIN receipts R ON P.RECEIPT = R.ID LEFT JOIN people PP ON R.PERSON = pp.ID LEFT JOIN payment_categories PC ON P.CATEGORY = PC.ID WHERE PC.IS_GLOBAL = b'1' ORDER BY R.DATENEW DESC", null, new SerializerReadBasic(new Datas[]{Datas.STRING, Datas.STRING, Datas.TIMESTAMP, Datas.STRING, Datas.STRING, Datas.DOUBLE, Datas.STRING, Datas.STRING, Datas.STRING, Datas.STRING})).list();
    }

    public final List<Object[]> getPaymentMovementsList(String money, boolean bGlobal) throws BasicException {
        logger.info("getPaymentMovementsList called with money ID: " + money + ", global: " + bGlobal);
        List<Object[]> result = new PreparedSentence<Object[], Object[]>(this.s, "SELECT R.ID, R.MONEY, R.DATENEW, P.ID, P.PAYMENT, P.TOTAL, P.NOTES, PC.NAME, PP.NAME, P.CATEGORY FROM payments P JOIN receipts R ON P.RECEIPT = R.ID LEFT JOIN people PP ON R.PERSON = pp.ID LEFT JOIN payment_categories PC ON P.CATEGORY = PC.ID WHERE R.MONEY = ? AND PC.IS_GLOBAL = ? AND NOT EXISTS (SELECT * FROM tickets T WHERE T.ID = R.ID) ORDER BY R.DATENEW DESC", new SerializerWriteBasic(Datas.STRING, Datas.BOOLEAN), new SerializerReadBasic(new Datas[]{Datas.STRING, Datas.STRING, Datas.TIMESTAMP, Datas.STRING, Datas.STRING, Datas.DOUBLE, Datas.STRING, Datas.STRING, Datas.STRING, Datas.STRING})).list(money, bGlobal);
        logger.info("getPaymentMovementsList returned " + (result != null ? Integer.valueOf(result.size()) : "null") + " rows.");
        return result;
    }

    public final SentenceExec<Object[]> getPaymentMovementDelete() {
        return new SentenceExecTransaction<Object[]>(this.s){

            @Override
            public int execInTransaction(Object[] params) throws BasicException {
                new PreparedSentence(DataLogicSales.this.s, "DELETE FROM payments WHERE ID = ?", new SerializerWriteBasicExt(DataLogicSales.this.paymenttabledatas, new int[]{3})).exec(params);
                return new PreparedSentence(DataLogicSales.this.s, "DELETE FROM receipts WHERE ID = ?", new SerializerWriteBasicExt(DataLogicSales.this.paymenttabledatas, new int[]{0})).exec(params);
            }
        };
    }

    public final double findProductStock(String warehouse, String id, String attsetinstid) throws BasicException {
        PreparedSentence<Object[], Double> p = attsetinstid == null ? new PreparedSentence<Object[], Double>(this.s, "SELECT UNITS FROM stockcurrent WHERE LOCATION = ? AND PRODUCT = ? AND ATTRIBUTESETINSTANCE_ID IS NULL", new SerializerWriteBasic(Datas.STRING, Datas.STRING), SerializerReadDouble.INSTANCE) : new PreparedSentence<Object[], Double>(this.s, "SELECT UNITS FROM stockcurrent WHERE LOCATION = ? AND PRODUCT = ? AND ATTRIBUTESETINSTANCE_ID = ?", new SerializerWriteBasic(Datas.STRING, Datas.STRING, Datas.STRING), SerializerReadDouble.INSTANCE);
        Double d = (Double)p.find(warehouse, id, attsetinstid);
        return d == null ? 0.0 : d;
    }

    public final Object[] getProductStockState(String warehouse, String id) throws BasicException {
        return (Object[])new PreparedSentence<Object[], Object[]>(this.s, "SELECT COALESCE(SUM(S.UNITS), 0.0), MAX(L.STOCKSECURITY), MAX(L.STOCKMAXIMUM) FROM PRODUCTS P LEFT JOIN STOCKCURRENT S ON P.ID = S.PRODUCT AND S.LOCATION = ? LEFT JOIN STOCKLEVEL L ON P.ID = L.PRODUCT AND L.LOCATION = ? WHERE P.ID = ? GROUP BY P.ID", new SerializerWriteBasic(Datas.STRING, Datas.STRING, Datas.STRING), new SerializerReadBasic(new Datas[]{Datas.DOUBLE, Datas.DOUBLE, Datas.DOUBLE})).find(warehouse, warehouse, id);
    }

    public final SentenceExec<String> getCatalogCategoryAdd() {
        return new StaticSentence(this.s, "INSERT INTO products_cat(PRODUCT, CATORDER) SELECT ID, " + this.s.DB.INTEGER_NULL() + " FROM products WHERE CATEGORY = ?", SerializerWriteString.INSTANCE);
    }

    public final SentenceExec<String> getCatalogCategoryDel() {
        return new StaticSentence(this.s, "DELETE FROM products_cat WHERE PRODUCT = ANY (SELECT ID FROM products WHERE CATEGORY = ?)", SerializerWriteString.INSTANCE);
    }

    public final TableDefinition getTableCategories() {
        return new TableDefinition(this.s, "categories", new String[]{"ID", "NAME", "PARENTID", "IMAGE", "TEXTTIP", "CATSHOWNAME", "CATORDER"}, new String[]{"ID", AppLocal.getIntString("label.name"), "", AppLocal.getIntString("label.image")}, new Datas[]{Datas.STRING, Datas.STRING, Datas.STRING, Datas.IMAGE, Datas.STRING, Datas.BOOLEAN, Datas.STRING}, new Formats[]{Formats.STRING, Formats.STRING, Formats.STRING, Formats.NULL, Formats.STRING, Formats.BOOLEAN, Formats.STRING}, new int[]{0});
    }

    public final TableDefinition getTableTaxes() {
        return new TableDefinition(this.s, "taxes", new String[]{"ID", "NAME", "CATEGORY", "CUSTCATEGORY", "PARENTID", "RATE", "RATECASCADE", "RATEORDER"}, new String[]{"ID", AppLocal.getIntString("label.name"), AppLocal.getIntString("label.taxcategory"), AppLocal.getIntString("label.custtaxcategory"), AppLocal.getIntString("label.taxparent"), AppLocal.getIntString("label.dutyrate"), AppLocal.getIntString("label.cascade"), AppLocal.getIntString("label.order")}, new Datas[]{Datas.STRING, Datas.STRING, Datas.STRING, Datas.STRING, Datas.STRING, Datas.DOUBLE, Datas.BOOLEAN, Datas.INT}, new Formats[]{Formats.STRING, Formats.STRING, Formats.STRING, Formats.STRING, Formats.STRING, Formats.PERCENT, Formats.BOOLEAN, Formats.INT}, new int[]{0});
    }

    public final TableDefinition getTableTaxCustCategories() {
        return new TableDefinition(this.s, "taxcustcategories", new String[]{"ID", "NAME"}, new String[]{"ID", AppLocal.getIntString("label.name")}, new Datas[]{Datas.STRING, Datas.STRING}, new Formats[]{Formats.STRING, Formats.STRING}, new int[]{0});
    }

    public final TableDefinition getTableTaxCategories() {
        return new TableDefinition(this.s, "taxcategories", new String[]{"ID", "NAME"}, new String[]{"ID", AppLocal.getIntString("label.name")}, new Datas[]{Datas.STRING, Datas.STRING}, new Formats[]{Formats.STRING, Formats.STRING}, new int[]{0});
    }

    public final TableDefinition getTableLocations() {
        return new TableDefinition(this.s, "locations", new String[]{"ID", "NAME", "ADDRESS"}, new String[]{"ID", AppLocal.getIntString("label.locationname"), AppLocal.getIntString("label.locationaddress")}, new Datas[]{Datas.STRING, Datas.STRING, Datas.STRING}, new Formats[]{Formats.STRING, Formats.STRING, Formats.STRING}, new int[]{0});
    }

    public final UomInfo getUomInfoById(String id) throws BasicException {
        return (UomInfo)new PreparedSentence<String, UomInfo>(this.s, "SELECT id, name FROM uom WHERE id = ?", SerializerWriteString.INSTANCE, UomInfo.getSerializerRead()).find((Object)id);
    }

    public final TableDefinition getTableUom() {
        return new TableDefinition(this.s, "uom", new String[]{"id", "name"}, new String[]{"id", AppLocal.getIntString("Label.Name")}, new Datas[]{Datas.STRING, Datas.STRING}, new Formats[]{Formats.STRING, Formats.STRING}, new int[]{0});
    }

    public final SentenceList<UomInfo> getUomList() {
        return new StaticSentence(this.s, "SELECT ID, NAME  FROM uom ORDER BY NAME", null, UomInfo.getSerializerRead());
    }

    public final SentenceList<VoucherInfo> getVoucherList() {
        return new StaticSentence(this.s, "SELECT vouchers.ID,vouchers.VOUCHER_NUMBER,vouchers.CUSTOMER, customers.NAME,AMOUNT FROM vouchers   JOIN customers ON customers.id = vouchers.CUSTOMER  WHERE STATUS='A'", null, VoucherInfo.getSerializerRead());
    }

    public final SentenceExec<String> getVoucherNonActive() {
        return new PreparedSentence(this.s, "UPDATE vouchers SET STATUS = 'D' WHERE VOUCHER_NUMBER = ?", SerializerWriteString.INSTANCE);
    }

    public final SentenceExec<String> resetPickupId() {
        return new PreparedSentence(this.s, "UPDATE pickup_number SET ID=1 ", SerializerWriteString.INSTANCE);
    }

    public SupplierInfoExt loadSupplierExt(String id) throws BasicException {
        return (SupplierInfoExt)new PreparedSentence<String, SupplierInfoExt>(this.s, "SELECT ID, SEARCHKEY, TAXID, NAME, MAXDEBT, ADDRESS, ADDRESS2, POSTAL, CITY, REGION, COUNTRY, FIRSTNAME, LASTNAME, EMAIL, PHONE, PHONE2, FAX, NOTES, VISIBLE, CURDATE, CURDEBT, VATID FROM suppliers WHERE ID = ?", SerializerWriteString.INSTANCE, new SupplierExtRead()).find((Object)id);
    }

    public final SentenceExec<Object[]> getCustomerInsert() {
        return new SentenceExecTransaction<Object[]>(this.s){

            @Override
            public int execInTransaction(Object[] params) throws BasicException {
                int i = new PreparedSentence(DataLogicSales.this.s, "INSERT INTO customers (ID, SEARCHKEY, TAXID, NAME, TAXCATEGORY, CARD, MAXDEBT, ADDRESS, ADDRESS2, POSTAL, CITY, REGION, COUNTRY, FIRSTNAME, LASTNAME, EMAIL, PHONE, PHONE2, FAX, NOTES, VISIBLE, CURDATE, CURDEBT, IMAGE, ISVIP, DISCOUNT ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", new SerializerWriteBasicExt(DataLogicSales.this.customersRow.getDatas(), new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25})).exec(params);
                return i;
            }
        };
    }

    public final SentenceExec<Object[]> getCustomerUpdate() {
        return new SentenceExecTransaction<Object[]>(this.s){

            @Override
            public int execInTransaction(Object[] params) throws BasicException {
                int i = new PreparedSentence(DataLogicSales.this.s, "UPDATE customers SET ID = ?, SEARCHKEY = ?, TAXID = ?, NAME = ?, TAXCATEGORY = ?, CARD = ?, MAXDEBT = ?, ADDRESS = ?, ADDRESS2 = ?, POSTAL = ?, CITY = ?, REGION = ?, COUNTRY = ?, FIRSTNAME = ?, LASTNAME = ?, EMAIL = ?, PHONE = ?, PHONE2 = ?, FAX = ?,  NOTES = ?,VISIBLE = ?, CURDATE = ?, CURDEBT = ?, IMAGE = ?, ISVIP = ?, DISCOUNT = ? WHERE ID = ?", new SerializerWriteBasicExt(DataLogicSales.this.customersRow.getDatas(), new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 0})).exec(params);
                return i;
            }
        };
    }

    public final SentenceExec<Object[]> getCustomerDelete() {
        return new SentenceExecTransaction<Object[]>(this.s){

            @Override
            public int execInTransaction(Object[] params) throws BasicException {
                return new PreparedSentence(DataLogicSales.this.s, "DELETE FROM customers WHERE ID = ?", new SerializerWriteBasicExt(DataLogicSales.this.customersRow.getDatas(), new int[]{0})).exec(params);
            }
        };
    }

    public TableDefinition getTablePaymentCategories() {
        return new TableDefinition(this.s, "PAYMENT_CATEGORIES", new String[]{"ID", "NAME"}, new String[]{"ID", AppLocal.getIntString("label.name")}, new Datas[]{Datas.STRING, Datas.STRING}, new Formats[]{Formats.STRING, Formats.STRING}, new int[]{0});
    }

    public final Integer getNextTicketFeIndex() throws BasicException {
        return (Integer)this.s.DB.getSequenceSentence(this.s, "ticketsnum_fe").find();
    }

    public final Integer getNextTicketPoseIndex() throws BasicException {
        return (Integer)this.s.DB.getSequenceSentence(this.s, "ticketsnum_pose").find();
    }

    public final Integer getNextTicketNocIndex() throws BasicException {
        return (Integer)this.s.DB.getSequenceSentence(this.s, "ticketsnum_noc").find();
    }

    public final Integer getNextTicketDocsIndex() throws BasicException {
        return (Integer)this.s.DB.getSequenceSentence(this.s, "ticketsnum_docs").find();
    }

    public final java.util.List<com.openbravo.pos.inventory.InventoryTaskInfo> getPendingInventoryTasks(String assigneeRole, String locationId) throws com.openbravo.basic.BasicException {
        return new com.openbravo.data.loader.PreparedSentence<Object[], com.openbravo.pos.inventory.InventoryTaskInfo>(this.s, "SELECT ID, STATUS, CREATED_AT, AUTHOR_ID, LOCATION_ID, ASSIGNEE_ROLE FROM inventory_tasks WHERE STATUS = 'PENDING' AND ASSIGNEE_ROLE = ? AND LOCATION_ID = ?", new com.openbravo.data.loader.SerializerWriteBasic(com.openbravo.data.loader.Datas.STRING, com.openbravo.data.loader.Datas.STRING), new com.openbravo.data.loader.SerializerReadClass<com.openbravo.pos.inventory.InventoryTaskInfo>(com.openbravo.pos.inventory.InventoryTaskInfo.class)).list(new Object[]{assigneeRole, locationId});
    }

    public final java.util.List<com.openbravo.pos.inventory.InventoryHistoryRecord> getInventoryHistory(String productId) throws com.openbravo.basic.BasicException {
        return new com.openbravo.data.loader.PreparedSentence<String, com.openbravo.pos.inventory.InventoryHistoryRecord>(this.s, 
            "SELECT SD.DATENEW, SD.REASON, SD.UNITS, SD.PRICE, SD.AppUser, " +
            "(SELECT P.NAME FROM receipts R JOIN ticketlines TL ON R.ID = TL.TICKET JOIN products P ON TL.PRODUCT = P.ID WHERE R.DATENEW = SD.DATENEW AND (TL.PRODUCT = SD.PRODUCT OR EXISTS (SELECT 1 FROM products_com PC WHERE PC.PRODUCT = TL.PRODUCT AND PC.PRODUCT2 = SD.PRODUCT)) LIMIT 1) AS PARENT_PRODUCT_NAME " +
            "FROM stockdiary SD WHERE SD.PRODUCT = ? ORDER BY SD.DATENEW DESC LIMIT 100", 
            com.openbravo.data.loader.SerializerWriteString.INSTANCE, 
            new com.openbravo.data.loader.SerializerRead() {
                public Object readValues(com.openbravo.data.loader.DataRead dr) throws com.openbravo.basic.BasicException {
                    return new com.openbravo.pos.inventory.InventoryHistoryRecord(
                        dr.getTimestamp(1), 
                        dr.getInt(2), 
                        dr.getDouble(3), 
                        0.0, // Running balance to be calculated in UI 
                        dr.getString(5),
                        dr.getString(6)
                    );
                }
            }
        ).list(productId);
    }

    public final java.util.List<com.openbravo.pos.inventory.InventoryTaskLineInfo> getInventoryTaskLines(String taskId) throws com.openbravo.basic.BasicException {
        return new com.openbravo.data.loader.PreparedSentence<String, com.openbravo.pos.inventory.InventoryTaskLineInfo>(this.s, "SELECT L.TASK_ID, L.PRODUCT_ID, L.SYSTEM_QTY, L.COUNTED_QTY, L.DIFFERENCE, P.NAME FROM inventory_task_lines L JOIN products P ON L.PRODUCT_ID = P.ID WHERE L.TASK_ID = ?", com.openbravo.data.loader.SerializerWriteString.INSTANCE, new com.openbravo.data.loader.SerializerReadClass<com.openbravo.pos.inventory.InventoryTaskLineInfo>(com.openbravo.pos.inventory.InventoryTaskLineInfo.class)).list(taskId);
    }

    public final void insertInventoryTask(com.openbravo.pos.inventory.InventoryTaskInfo task) throws com.openbravo.basic.BasicException {
        new com.openbravo.data.loader.PreparedSentence(this.s, "INSERT INTO inventory_tasks (ID, STATUS, CREATED_AT, AUTHOR_ID, LOCATION_ID, ASSIGNEE_ROLE) VALUES (?, ?, ?, ?, ?, ?)", new com.openbravo.data.loader.SerializerWriteBasic(com.openbravo.data.loader.Datas.STRING, com.openbravo.data.loader.Datas.STRING, com.openbravo.data.loader.Datas.TIMESTAMP, com.openbravo.data.loader.Datas.STRING, com.openbravo.data.loader.Datas.STRING, com.openbravo.data.loader.Datas.STRING)).exec(new Object[]{task.getId(), task.getStatus(), task.getCreatedAt(), task.getAuthorId(), task.getLocationId(), task.getAssigneeRole()});
    }

    public final void insertInventoryTaskLine(com.openbravo.pos.inventory.InventoryTaskLineInfo line) throws com.openbravo.basic.BasicException {
        new com.openbravo.data.loader.PreparedSentence(this.s, "INSERT INTO inventory_task_lines (TASK_ID, PRODUCT_ID, SYSTEM_QTY, COUNTED_QTY, DIFFERENCE) VALUES (?, ?, ?, ?, ?)", new com.openbravo.data.loader.SerializerWriteBasic(com.openbravo.data.loader.Datas.STRING, com.openbravo.data.loader.Datas.STRING, com.openbravo.data.loader.Datas.DOUBLE, com.openbravo.data.loader.Datas.DOUBLE, com.openbravo.data.loader.Datas.DOUBLE)).exec(new Object[]{line.getTaskId(), line.getProductId(), line.getSystemQty(), line.getCountedQty(), line.getDifference()});
    }

    public final void updateInventoryTaskStatus(String taskId, String status) throws com.openbravo.basic.BasicException {
        new com.openbravo.data.loader.PreparedSentence(this.s, "UPDATE inventory_tasks SET STATUS = ? WHERE ID = ?", new com.openbravo.data.loader.SerializerWriteBasic(com.openbravo.data.loader.Datas.STRING, com.openbravo.data.loader.Datas.STRING)).exec(new Object[]{status, taskId});
    }

    public final void updateInventoryTaskLineCounted(String taskId, String productId, Double countedQty, Double difference) throws com.openbravo.basic.BasicException {
        new com.openbravo.data.loader.PreparedSentence(this.s, "UPDATE inventory_task_lines SET COUNTED_QTY = ?, DIFFERENCE = ? WHERE TASK_ID = ? AND PRODUCT_ID = ?", new com.openbravo.data.loader.SerializerWriteBasic(com.openbravo.data.loader.Datas.DOUBLE, com.openbravo.data.loader.Datas.DOUBLE, com.openbravo.data.loader.Datas.STRING, com.openbravo.data.loader.Datas.STRING)).exec(new Object[]{countedQty, difference, taskId, productId});
    }

    public final List<ProductInfoExt> searchProductsByName(String partialName) throws BasicException {
        return new PreparedSentence<String, ProductInfoExt>(this.s, "SELECT ID, REFERENCE, CODE, CODETYPE, NAME, PRICEBUY, PRICESELL, CATEGORY, TAXCAT, ATTRIBUTESET_ID, STOCKCOST, STOCKVOLUME, IMAGE, ISCOM, ISSCALE, ISCONSTANT, PRINTKB, SENDSTATUS, ISSERVICE, ATTRIBUTES, DISPLAY, ISVPRICE, ISVERPATRIB, TEXTTIP, WARRANTY, STOCKUNITS, PRINTTO, SUPPLIER, UOM FROM products WHERE UPPER(NAME) LIKE ? ORDER BY NAME", SerializerWriteString.INSTANCE, ProductInfoExt.getSerializerRead()).list("%" + partialName.toUpperCase() + "%");
    }

    public final void insertMerchandiseReceipt(String id, String person, String notes) throws BasicException {
        new PreparedSentence(this.s, "INSERT INTO merchandise_receipts (ID, PERSON, STATUS, NOTES) VALUES (?, ?, 'PENDING', ?)", new SerializerWriteBasic(Datas.STRING, Datas.STRING, Datas.STRING)).exec(new Object[]{id, person, notes});
    }

    public final void insertMerchandiseReceiptLine(String id, String receiptId, String productId, double units, String notes) throws BasicException {
        new PreparedSentence(this.s, "INSERT INTO merchandise_receipt_lines (ID, RECEIPT_ID, PRODUCT_ID, UNITS, NOTES) VALUES (?, ?, ?, ?, ?)", new SerializerWriteBasic(Datas.STRING, Datas.STRING, Datas.STRING, Datas.DOUBLE, Datas.STRING)).exec(new Object[]{id, receiptId, productId, units, notes});
    }

    public final List<Object[]> getPendingMerchandiseReceipts() throws BasicException {
        return new PreparedSentence(this.s, "SELECT ID, DATENEW, PERSON, STATUS, NOTES FROM merchandise_receipts WHERE STATUS = 'PENDING' ORDER BY DATENEW DESC", null, new SerializerReadBasic(new Datas[]{Datas.STRING, Datas.TIMESTAMP, Datas.STRING, Datas.STRING, Datas.STRING})).list();
    }

    public final List<Object[]> getMerchandiseReceiptLines(String receiptId) throws BasicException {
        return new PreparedSentence(this.s, "SELECT L.ID, L.RECEIPT_ID, L.PRODUCT_ID, L.UNITS, L.NOTES, P.NAME FROM merchandise_receipt_lines L JOIN products P ON L.PRODUCT_ID = P.ID WHERE L.RECEIPT_ID = ?", SerializerWriteString.INSTANCE, new SerializerReadBasic(new Datas[]{Datas.STRING, Datas.STRING, Datas.STRING, Datas.DOUBLE, Datas.STRING, Datas.STRING})).list(receiptId);
    }

    public final void approveMerchandiseReceipt(final String receiptId, final String location, final String user) throws BasicException {
        new SentenceExecTransaction<Object>(this.s) {
            @Override
            public int execInTransaction(Object params) throws BasicException {
                List<Object[]> lines = getMerchandiseReceiptLines(receiptId);
                for (Object[] line : lines) {
                    Object[] sdRecord = new Object[9];
                    sdRecord[0] = UUID.randomUUID().toString();
                    sdRecord[1] = new Date();
                    sdRecord[2] = 1; // IN_PURCHASE
                    sdRecord[3] = location;
                    sdRecord[4] = (String) line[2];
                    sdRecord[5] = null;
                    sdRecord[6] = (Double) line[3];
                    sdRecord[7] = 0.0;
                    sdRecord[8] = user;
                    DataLogicSales.this.getStockDiaryInsert().exec(sdRecord);
                }
                new PreparedSentence(DataLogicSales.this.s, "UPDATE merchandise_receipts SET STATUS = 'APPROVED' WHERE ID = ?", SerializerWriteString.INSTANCE).exec(receiptId);
                return 1;
            }
        }.exec(null);
    }

    public final void rejectMerchandiseReceipt(String receiptId) throws BasicException {
        new PreparedSentence(this.s, "UPDATE merchandise_receipts SET STATUS = 'REJECTED' WHERE ID = ?", SerializerWriteString.INSTANCE).exec(receiptId);
    }

    public void updatePlaceCoordinates(String id, int x, int y) throws BasicException {
        new SentenceExecTransaction<Object[]>(this.s){

            @Override
            public int execInTransaction(Object[] params) throws BasicException {
                return new PreparedSentence(DataLogicSales.this.s, "UPDATE PLACES SET X = ?, Y = ? WHERE ID = ?", new SerializerWriteBasic(Datas.INT, Datas.INT, Datas.STRING)).exec(params);
            }
        }.exec(new Object[]{x, y, id});
    }

    protected static class CustomerExtRead
    implements SerializerRead<CustomerInfoExt> {
        protected CustomerExtRead() {
        }

        @Override
        public CustomerInfoExt readValues(DataRead dr) throws BasicException {
            CustomerInfoExt c = new CustomerInfoExt(dr.getString(1));
            c.setSearchkey(dr.getString(2));
            c.setTaxid(dr.getString(3));
            c.setTaxCustomerID(dr.getString(3));
            c.setName(dr.getString(4));
            c.setTaxCustCategoryID(dr.getString(5));
            c.setCard(dr.getString(6));
            c.setMaxdebt(dr.getDouble(7));
            c.setAddress(dr.getString(8));
            c.setAddress2(dr.getString(9));
            c.setPostal(dr.getString(10));
            c.setCity(dr.getString(11));
            c.setRegion(dr.getString(12));
            c.setCountry(dr.getString(13));
            c.setFirstname(dr.getString(14));
            c.setLastname(dr.getString(15));
            c.setEmail(dr.getString(16));
            c.setPhone(dr.getString(17));
            c.setPhone2(dr.getString(18));
            c.setFax(dr.getString(19));
            c.setNotes(dr.getString(20));
            c.setVisible(dr.getBoolean(21));
            c.setCurdate(dr.getTimestamp(22));
            c.setCurdebt(dr.getDouble(23));
            c.setImage(ImageUtils.readImage(dr.getString(24)));
            c.setisVIP(dr.getBoolean(25));
            c.setDiscount(dr.getDouble(26));
            c.setDv(dr.getString(27));
            c.setDocType(dr.getString(28));
            c.setPersonType(dr.getString(29));
            c.setLiability(dr.getString(30));
            c.setMunicipalityCode(dr.getString(31));
            c.setRegimeCode(dr.getString(32));
            return c;
        }
    }

    protected static class SupplierExtRead
    implements SerializerRead<SupplierInfoExt> {
        protected SupplierExtRead() {
        }

        @Override
        public SupplierInfoExt readValues(DataRead dr) throws BasicException {
            SupplierInfoExt s = new SupplierInfoExt(dr.getString(1));
            s.setSearchkey(dr.getString(2));
            s.setTaxid(dr.getString(3));
            s.setName(dr.getString(4));
            s.setMaxdebt(dr.getDouble(5));
            s.setAddress(dr.getString(6));
            s.setAddress2(dr.getString(7));
            s.setPostal(dr.getString(8));
            s.setCity(dr.getString(9));
            s.setRegion(dr.getString(10));
            s.setCountry(dr.getString(11));
            s.setFirstname(dr.getString(12));
            s.setLastname(dr.getString(13));
            s.setEmail(dr.getString(14));
            s.setPhone(dr.getString(15));
            s.setPhone2(dr.getString(16));
            s.setFax(dr.getString(17));
            s.setNotes(dr.getString(18));
            s.setVisible(dr.getBoolean(19));
            s.setCurdate(dr.getTimestamp(20));
            s.setCurdebt(dr.getDouble(21));
            s.setSupplierVATID(dr.getString(22));
            return s;
        }
    }
}

