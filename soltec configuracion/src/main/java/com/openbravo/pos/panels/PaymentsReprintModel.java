/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.panels;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.DataRead;
import com.openbravo.data.loader.Datas;
import com.openbravo.data.loader.SerializableRead;
import com.openbravo.data.loader.SerializerReadBasic;
import com.openbravo.data.loader.SerializerReadClass;
import com.openbravo.data.loader.SerializerWriteString;
import com.openbravo.data.loader.StaticSentence;
import com.openbravo.format.Formats;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.util.StringUtils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

public class PaymentsReprintModel {
    private String m_sHost;
    private String m_sUser;
    private int m_iSeq;
    private String m_dDateStart;
    private String m_dDateEnd;
    private Date rDate;
    private Date m_dPrintDate;
    private Integer m_iPayments;
    private Double m_dPaymentsTotal;
    private List<PaymentsLine> m_lpayments;
    private Integer m_iCategorySalesRows;
    private Double m_dCategorySalesTotalUnits;
    private Double m_dCategorySalesTotal;
    private List<CategorySalesLine> m_lcategorysales;
    private Integer m_iProductSalesRows;
    private Double m_dProductSalesTotalUnits;
    private Double m_dProductSalesTotal;
    private List<ProductSalesLine> m_lproductsales;
    private List<RemovedProductLines> m_lremovedlines;
    private List<DrawerOpenedLines> m_ldraweropenedlines;
    private static final String[] PAYMENTHEADERS = new String[]{"label.Payment", "label.Money"};
    private Integer m_iSales;
    private Double m_dSalesBase;
    private Double m_dSalesTaxes;
    private Double m_dSalesTaxNet;
    private List<SalesLine> m_lsales;
    private static final String[] SALEHEADERS = new String[]{"label.taxcategory", "label.totaltax", "label.totalnet"};

    private PaymentsReprintModel() {
    }

    public static PaymentsReprintModel emptyInstance() {
        PaymentsReprintModel p = new PaymentsReprintModel();
        p.m_iPayments = 0;
        p.m_dPaymentsTotal = 0.0;
        p.m_lpayments = new ArrayList<PaymentsLine>();
        p.m_iCategorySalesRows = 0;
        p.m_dCategorySalesTotalUnits = 0.0;
        p.m_dCategorySalesTotal = 0.0;
        p.m_lcategorysales = new ArrayList<CategorySalesLine>();
        p.m_iSales = 0;
        p.m_dSalesBase = 0.0;
        p.m_dSalesTaxes = 0.0;
        p.m_dSalesTaxNet = 0.0;
        p.m_iProductSalesRows = 0;
        p.m_dProductSalesTotalUnits = 0.0;
        p.m_dProductSalesTotal = 0.0;
        p.m_lproductsales = new ArrayList<ProductSalesLine>();
        p.m_lremovedlines = new ArrayList<RemovedProductLines>();
        p.m_lsales = new ArrayList<SalesLine>();
        return p;
    }

    public static PaymentsReprintModel loadInstance(AppView app) throws BasicException {
        PaymentsReprintModel p = new PaymentsReprintModel();
        p.m_sUser = app.getAppUserView().getUser().getName();
        p.m_sHost = app.getProperties().getHost();
        JFrame frame = new JFrame("Sequence");
        String sequence = JOptionPane.showInputDialog(frame, AppLocal.getIntString("message.ccentersequence"), 1);
        if (sequence != null) {
            int isequence;
            p.m_iSeq = isequence = Integer.parseInt(sequence);
        } else {
            app.getAppUserView().showTask("com.openbravo.pos.panels.JPanelCloseMoneyReprint");
        }
        Object[] ccash = (Object[])new StaticSentence<String, Object[]>(app.getSession(), "SELECT money, host, hostsequence, datestart, dateend, nosales FROM closedcash where hostsequence = ? and dateend is not null AND host = '" + app.getProperties().getHost() + "'", SerializerWriteString.INSTANCE, new SerializerReadBasic(new Datas[]{Datas.STRING, Datas.STRING, Datas.INT, Datas.STRING, Datas.STRING, Datas.INT})).find((Object)sequence);
        if (ccash != null) {
            p.m_dDateStart = ccash[3].toString();
            p.m_dDateEnd = ccash[4].toString();
            Object[] valcategorysales = (Object[])new StaticSentence<String, Object[]>(app.getSession(), "SELECT COUNT(*), SUM(ticketlines.UNITS), SUM((ticketlines.PRICE + ticketlines.PRICE * taxes.RATE ) * ticketlines.UNITS) FROM ticketlines, tickets, receipts, taxes WHERE ticketlines.TICKET = tickets.ID AND tickets.ID = receipts.ID AND ticketlines.TAXID = taxes.ID AND ticketlines.PRODUCT IS NOT NULL AND receipts.MONEY = ? GROUP BY receipts.MONEY", SerializerWriteString.INSTANCE, new SerializerReadBasic(new Datas[]{Datas.INT, Datas.DOUBLE, Datas.DOUBLE})).find(ccash[0]);
            if (valcategorysales == null) {
                p.m_iCategorySalesRows = 0;
                p.m_dCategorySalesTotalUnits = 0.0;
                p.m_dCategorySalesTotal = 0.0;
            } else {
                p.m_iCategorySalesRows = (Integer)valcategorysales[0];
                p.m_dCategorySalesTotalUnits = (Double)valcategorysales[1];
                p.m_dCategorySalesTotal = (Double)valcategorysales[2];
            }
            List categorys = new StaticSentence<String, CategorySalesLine>(app.getSession(), "SELECT a.NAME, sum(c.UNITS), sum(c.UNITS * (c.PRICE + (c.PRICE * d.RATE))) FROM categories as a LEFT JOIN products as b on a.id = b.CATEGORY LEFT JOIN ticketlines as c on b.id = c.PRODUCT LEFT JOIN taxes as d on c.TAXID = d.ID LEFT JOIN receipts as e on c.TICKET = e.ID WHERE e.MONEY = ? GROUP BY a.NAME", SerializerWriteString.INSTANCE, new SerializerReadClass<CategorySalesLine>(CategorySalesLine.class)).list(ccash[0]);
            p.m_lcategorysales = categorys == null ? new ArrayList<CategorySalesLine>() : categorys;
            Object[] valtickets = (Object[])new StaticSentence<String, Object[]>(app.getSession(), "SELECT COUNT(*), SUM(payments.TOTAL) FROM payments, receipts WHERE payments.RECEIPT = receipts.ID AND receipts.MONEY = ?", SerializerWriteString.INSTANCE, new SerializerReadBasic(new Datas[]{Datas.INT, Datas.DOUBLE})).find(ccash[0]);
            if (valtickets == null) {
                p.m_iPayments = 0;
                p.m_dPaymentsTotal = 0.0;
            } else {
                p.m_iPayments = (Integer)valtickets[0];
                p.m_dPaymentsTotal = (Double)valtickets[1];
            }
            List l = new StaticSentence<String, PaymentsLine>(app.getSession(), "SELECT payments.PAYMENT, SUM(payments.TOTAL), payments.NOTES FROM payments, receipts WHERE payments.RECEIPT = receipts.ID AND receipts.MONEY = ? GROUP BY payments.PAYMENT, payments.NOTES", SerializerWriteString.INSTANCE, new SerializerReadClass<PaymentsLine>(PaymentsLine.class)).list(ccash[0]);
            p.m_lpayments = l == null ? new ArrayList<PaymentsLine>() : l;
            Object[] recsales = (Object[])new StaticSentence<String, Object[]>(app.getSession(), "SELECT COUNT(DISTINCT receipts.ID), SUM(ticketlines.UNITS * ticketlines.PRICE) FROM receipts, ticketlines WHERE receipts.ID = ticketlines.TICKET AND receipts.MONEY = ?", SerializerWriteString.INSTANCE, new SerializerReadBasic(new Datas[]{Datas.INT, Datas.DOUBLE})).find(ccash[0]);
            if (recsales == null) {
                p.m_iSales = null;
                p.m_dSalesBase = null;
            } else {
                p.m_iSales = (Integer)recsales[0];
                p.m_dSalesBase = (Double)recsales[1];
            }
            Object[] rectaxes = (Object[])new StaticSentence<String, Object[]>(app.getSession(), "SELECT SUM(taxlines.AMOUNT), SUM(taxlines.BASE) FROM receipts, taxlines WHERE receipts.ID = taxlines.RECEIPT AND receipts.MONEY = ?", SerializerWriteString.INSTANCE, new SerializerReadBasic(new Datas[]{Datas.DOUBLE, Datas.DOUBLE})).find(ccash[0]);
            if (rectaxes == null) {
                p.m_dSalesTaxes = null;
                p.m_dSalesTaxNet = null;
            } else {
                p.m_dSalesTaxes = (Double)rectaxes[0];
                p.m_dSalesTaxNet = (Double)rectaxes[1];
            }
            List asales = new StaticSentence<String, SalesLine>(app.getSession(), "SELECT taxcategories.NAME, SUM(taxlines.AMOUNT), SUM(taxlines.BASE), SUM(taxlines.BASE + taxlines.AMOUNT) FROM receipts, taxlines, taxes, taxcategories WHERE receipts.ID = taxlines.RECEIPT AND taxlines.TAXID = taxes.ID AND taxes.CATEGORY = taxcategories.ID AND receipts.MONEY = ? GROUP BY taxcategories.NAME", SerializerWriteString.INSTANCE, new SerializerReadClass<SalesLine>(SalesLine.class)).list(ccash[0]);
            p.m_lsales = asales == null ? new ArrayList<SalesLine>() : asales;
            List removedLines = new StaticSentence<String, RemovedProductLines>(app.getSession(), "SELECT lineremoved.NAME, lineremoved.TICKETID, lineremoved.PRODUCTNAME, SUM(lineremoved.UNITS) AS TOTAL_UNITS  FROM lineremoved WHERE lineremoved.REMOVEDDATE > '" + p.m_dDateStart + "' GROUP BY lineremoved.NAME, lineremoved.TICKETID, lineremoved.PRODUCTNAME", SerializerWriteString.INSTANCE, new SerializerReadClass<RemovedProductLines>(RemovedProductLines.class)).list((Object)p.m_dDateStart);
            p.m_lremovedlines = removedLines == null ? new ArrayList<RemovedProductLines>() : removedLines;
            List drawerOpenedLines = new StaticSentence<String, DrawerOpenedLines>(app.getSession(), "SELECT OPENDATE, NAME, TICKETID  FROM draweropened WHERE TICKETID = 'No Sale' AND OPENDATE > ? GROUP BY NAME, OPENDATE, TICKETID", SerializerWriteString.INSTANCE, new SerializerReadClass<DrawerOpenedLines>(DrawerOpenedLines.class)).list((Object)p.m_dDateStart);
            p.m_ldraweropenedlines = drawerOpenedLines == null ? new ArrayList<DrawerOpenedLines>() : drawerOpenedLines;
            Object[] valproductsales = (Object[])new StaticSentence<String, Object[]>(app.getSession(), "SELECT COUNT(*), SUM(ticketlines.UNITS), SUM((ticketlines.PRICE + ticketlines.PRICE * taxes.RATE ) * ticketlines.UNITS) FROM ticketlines, tickets, receipts, taxes WHERE ticketlines.TICKET = tickets.ID AND tickets.ID = receipts.ID AND ticketlines.TAXID = taxes.ID AND ticketlines.PRODUCT IS NOT NULL AND receipts.MONEY = ? GROUP BY receipts.MONEY", SerializerWriteString.INSTANCE, new SerializerReadBasic(new Datas[]{Datas.INT, Datas.DOUBLE, Datas.DOUBLE})).find(ccash[0]);
            if (valproductsales == null) {
                p.m_iProductSalesRows = 0;
                p.m_dProductSalesTotalUnits = 0.0;
                p.m_dProductSalesTotal = 0.0;
            } else {
                p.m_iProductSalesRows = (Integer)valproductsales[0];
                p.m_dProductSalesTotalUnits = (Double)valproductsales[1];
                p.m_dProductSalesTotal = (Double)valproductsales[2];
            }
            List products = new StaticSentence<String, ProductSalesLine>(app.getSession(), "SELECT products.NAME, SUM(ticketlines.UNITS), ticketlines.PRICE, taxes.RATE FROM ticketlines, tickets, receipts, products, taxes WHERE ticketlines.PRODUCT = products.ID AND ticketlines.TICKET = tickets.ID AND tickets.ID = receipts.ID AND ticketlines.TAXID = taxes.ID AND receipts.MONEY = ? GROUP BY products.NAME, ticketlines.PRICE, taxes.RATE", SerializerWriteString.INSTANCE, new SerializerReadClass<ProductSalesLine>(ProductSalesLine.class)).list(ccash[0]);
            p.m_lproductsales = products == null ? new ArrayList<ProductSalesLine>() : products;
            return p;
        }
        JOptionPane.showMessageDialog(null, AppLocal.getIntString("message.ccsequencenotfound"), "", 2);
        return null;
    }

    public int getPayments() {
        return this.m_iPayments;
    }

    public double getTotal() {
        return this.m_dPaymentsTotal;
    }

    public String getHost() {
        return this.m_sHost;
    }

    public String getUser() {
        return this.m_sUser;
    }

    public int getSequence() {
        return this.m_iSeq;
    }

    public String getPrintDate() {
        Date m_dPrintDate = new Date();
        return Formats.TIMESTAMP.formatValue(m_dPrintDate);
    }

    public String getDateStart() {
        return this.m_dDateStart;
    }

    public void setDateEnd(String dValue) {
        this.m_dDateEnd = dValue;
    }

    public String getDateEnd() {
        return this.m_dDateEnd;
    }

    public String getDateStartDerby() {
        SimpleDateFormat ndf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return ndf.format(this.m_dDateStart);
    }

    public String printHost() {
        return StringUtils.encodeXML(this.m_sHost);
    }

    public String printUser() {
        return StringUtils.encodeXML(this.m_sUser);
    }

    public String printSequence() {
        return Formats.INT.formatValue(this.m_iSeq);
    }

    public String printDate() {
        Date m_dPrintDate = new Date();
        return Formats.TIMESTAMP.formatValue(m_dPrintDate);
    }

    public String printDateStart() {
        return this.m_dDateStart;
    }

    public String reformDateStart() {
        SimpleDateFormat sdfIn = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdfOut = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
        String input = this.m_dDateStart;
        Date date = null;
        try {
            date = sdfIn.parse(input);
        }
        catch (ParseException ex) {
            Logger.getLogger(PaymentsReprintModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.m_dDateStart = sdfOut.format(date);
        return this.m_dDateStart;
    }

    public String reformDateEnd() {
        SimpleDateFormat sdfIn = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdfOut = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
        String input = this.m_dDateEnd;
        Date date = null;
        try {
            date = sdfIn.parse(input);
        }
        catch (ParseException ex) {
            Logger.getLogger(PaymentsReprintModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.m_dDateEnd = sdfOut.format(date);
        return this.m_dDateEnd;
    }

    public String printDateEnd() {
        return this.m_dDateEnd;
    }

    public String printPayments() {
        return Formats.INT.formatValue(this.m_iPayments);
    }

    public String printPaymentsTotal() {
        return Formats.CURRENCY.formatValue(this.m_dPaymentsTotal);
    }

    public List<PaymentsLine> getPaymentLines() {
        return this.m_lpayments;
    }

    public int getSales() {
        return this.m_iSales == null ? 0 : this.m_iSales;
    }

    public String printSales() {
        return Formats.INT.formatValue(this.m_iSales);
    }

    public String printSalesBase() {
        return Formats.CURRENCY.formatValue(this.m_dSalesBase);
    }

    public String printSalesTaxes() {
        return Formats.CURRENCY.formatValue(this.m_dSalesTaxes);
    }

    public String printSalesTotal() {
        return Formats.CURRENCY.formatValue(this.m_dSalesBase == null || this.m_dSalesTaxes == null ? null : Double.valueOf(this.m_dSalesBase + this.m_dSalesTaxes));
    }

    public List<SalesLine> getSaleLines() {
        return this.m_lsales;
    }

    public double getCategorySalesRows() {
        return this.m_iCategorySalesRows.intValue();
    }

    public String printCategorySalesRows() {
        return Formats.INT.formatValue(this.m_iCategorySalesRows);
    }

    public double getCategorySalesTotalUnits() {
        return this.m_dCategorySalesTotalUnits;
    }

    public String printCategorySalesTotalUnits() {
        return Formats.DOUBLE.formatValue(this.m_dCategorySalesTotalUnits);
    }

    public double getCategorySalesTotal() {
        return this.m_dCategorySalesTotal;
    }

    public String printCategorySalesTotal() {
        return Formats.CURRENCY.formatValue(this.m_dCategorySalesTotal);
    }

    public List<CategorySalesLine> getCategorySalesLines() {
        return this.m_lcategorysales;
    }

    public double getProductSalesRows() {
        return this.m_iProductSalesRows.intValue();
    }

    public String printProductSalesRows() {
        return Formats.INT.formatValue(this.m_iProductSalesRows);
    }

    public double getProductSalesTotalUnits() {
        return this.m_dProductSalesTotalUnits;
    }

    public String printProductSalesTotalUnits() {
        return Formats.DOUBLE.formatValue(this.m_dProductSalesTotalUnits);
    }

    public double getProductSalesTotal() {
        return this.m_dProductSalesTotal;
    }

    public String printProductSalesTotal() {
        return Formats.CURRENCY.formatValue(this.m_dProductSalesTotal);
    }

    public List<ProductSalesLine> getProductSalesLines() {
        return this.m_lproductsales;
    }

    public List<RemovedProductLines> getRemovedProductLines() {
        return this.m_lremovedlines;
    }

    public List<DrawerOpenedLines> getDrawerOpenedLines() {
        return this.m_ldraweropenedlines;
    }

    public AbstractTableModel getPaymentsReprintModel() {
        return new AbstractTableModel(){

            @Override
            public String getColumnName(int column) {
                return AppLocal.getIntString(PAYMENTHEADERS[column]);
            }

            @Override
            public int getRowCount() {
                return PaymentsReprintModel.this.m_lpayments.size();
            }

            @Override
            public int getColumnCount() {
                return PAYMENTHEADERS.length;
            }

            @Override
            public Object getValueAt(int row, int column) {
                PaymentsLine l = (PaymentsLine)PaymentsReprintModel.this.m_lpayments.get(row);
                switch (column) {
                    case 0: {
                        return l.getType();
                    }
                    case 1: {
                        return l.getValue();
                    }
                }
                return null;
            }
        };
    }

    public AbstractTableModel getSalesModel() {
        return new AbstractTableModel(){

            @Override
            public String getColumnName(int column) {
                return AppLocal.getIntString(SALEHEADERS[column]);
            }

            @Override
            public int getRowCount() {
                return PaymentsReprintModel.this.m_lsales.size();
            }

            @Override
            public int getColumnCount() {
                return SALEHEADERS.length;
            }

            @Override
            public Object getValueAt(int row, int column) {
                SalesLine l = (SalesLine)PaymentsReprintModel.this.m_lsales.get(row);
                switch (column) {
                    case 0: {
                        return l.getTaxName();
                    }
                    case 1: {
                        return l.getTaxes();
                    }
                    case 2: {
                        return l.getTaxNet();
                    }
                }
                return null;
            }
        };
    }

    public static class CategorySalesLine
    implements SerializableRead {
        private String m_CategoryName;
        private Double m_CategoryUnits;
        private Double m_CategorySum;

        @Override
        public void readValues(DataRead dr) throws BasicException {
            this.m_CategoryName = dr.getString(1);
            this.m_CategoryUnits = dr.getDouble(2);
            this.m_CategorySum = dr.getDouble(3);
        }

        public String printCategoryName() {
            return this.m_CategoryName;
        }

        public String printCategoryUnits() {
            return Formats.DOUBLE.formatValue(this.m_CategoryUnits);
        }

        public Double getCategoryUnits() {
            return this.m_CategoryUnits;
        }

        public String printCategorySum() {
            return Formats.CURRENCY.formatValue(this.m_CategorySum);
        }

        public Double getCategorySum() {
            return this.m_CategorySum;
        }
    }

    public static class PaymentsLine
    implements SerializableRead {
        private String m_PaymentType;
        private Double m_PaymentValue;
        private String s_PaymentReason;

        @Override
        public void readValues(DataRead dr) throws BasicException {
            this.m_PaymentType = dr.getString(1);
            this.m_PaymentValue = dr.getDouble(2);
            this.s_PaymentReason = dr.getString(3) == null ? "" : dr.getString(3);
        }

        public String printType() {
            return AppLocal.getIntString("transpayment." + this.m_PaymentType);
        }

        public String getType() {
            return this.m_PaymentType;
        }

        public String printValue() {
            return Formats.CURRENCY.formatValue(this.m_PaymentValue);
        }

        public Double getValue() {
            return this.m_PaymentValue;
        }

        public String printReason() {
            return this.s_PaymentReason;
        }

        public String getReason() {
            return this.s_PaymentReason;
        }
    }

    public static class SalesLine
    implements SerializableRead {
        private String m_SalesTaxName;
        private Double m_SalesTaxes;
        private Double m_SalesTaxNet;
        private Double m_SalesTaxGross;

        @Override
        public void readValues(DataRead dr) throws BasicException {
            this.m_SalesTaxName = dr.getString(1);
            this.m_SalesTaxes = dr.getDouble(2);
            this.m_SalesTaxNet = dr.getDouble(3);
            this.m_SalesTaxGross = dr.getDouble(4);
        }

        public String printTaxName() {
            return this.m_SalesTaxName;
        }

        public String printTaxes() {
            return Formats.CURRENCY.formatValue(this.m_SalesTaxes);
        }

        public String printTaxNet() {
            return Formats.CURRENCY.formatValue(this.m_SalesTaxNet);
        }

        public String printTaxGross() {
            return Formats.CURRENCY.formatValue(this.m_SalesTaxes + this.m_SalesTaxNet);
        }

        public String getTaxName() {
            return this.m_SalesTaxName;
        }

        public Double getTaxes() {
            return this.m_SalesTaxes;
        }

        public Double getTaxNet() {
            return this.m_SalesTaxNet;
        }

        public Double getTaxGross() {
            return this.m_SalesTaxGross;
        }
    }

    public static class RemovedProductLines
    implements SerializableRead {
        private String m_Name;
        private String m_TicketId;
        private String m_ProductName;
        private Double m_TotalUnits;

        @Override
        public void readValues(DataRead dr) throws BasicException {
            this.m_Name = dr.getString(1);
            this.m_TicketId = dr.getString(2);
            this.m_ProductName = dr.getString(3);
            this.m_TotalUnits = dr.getDouble(4);
        }

        public String printWorkerName() {
            return StringUtils.encodeXML(this.m_Name);
        }

        public String printTicketId() {
            return StringUtils.encodeXML(this.m_TicketId);
        }

        public String printProductName() {
            return StringUtils.encodeXML(this.m_ProductName);
        }

        public String printTotalUnits() {
            return Formats.DOUBLE.formatValue(this.m_TotalUnits);
        }
    }

    public static class DrawerOpenedLines
    implements SerializableRead {
        private String m_DrawerOpened;
        private String m_Name;
        private String m_TicketId;

        @Override
        public void readValues(DataRead dr) throws BasicException {
            this.m_DrawerOpened = dr.getString(1);
            this.m_Name = dr.getString(2);
            this.m_TicketId = dr.getString(3);
        }

        public String printDrawerOpened() {
            return StringUtils.encodeXML(this.m_DrawerOpened);
        }

        public String printUserName() {
            return StringUtils.encodeXML(this.m_Name);
        }

        public String printTicketId() {
            return StringUtils.encodeXML(this.m_TicketId);
        }
    }

    public static class ProductSalesLine
    implements SerializableRead {
        private String m_ProductName;
        private Double m_ProductUnits;
        private Double m_ProductPrice;
        private Double m_TaxRate;
        private Double m_ProductPriceTax;
        private Double m_ProductPriceNet;

        @Override
        public void readValues(DataRead dr) throws BasicException {
            this.m_ProductName = dr.getString(1);
            this.m_ProductUnits = dr.getDouble(2);
            this.m_ProductPrice = dr.getDouble(3);
            this.m_TaxRate = dr.getDouble(4);
            this.m_ProductPriceTax = this.m_ProductPrice + this.m_ProductPrice * this.m_TaxRate;
            this.m_ProductPriceNet = this.m_ProductPrice * this.m_TaxRate;
        }

        public String printProductName() {
            return StringUtils.encodeXML(this.m_ProductName);
        }

        public String printProductUnits() {
            return Formats.DOUBLE.formatValue(this.m_ProductUnits);
        }

        public Double getProductUnits() {
            return this.m_ProductUnits;
        }

        public String printProductPrice() {
            return Formats.CURRENCY.formatValue(this.m_ProductPrice);
        }

        public Double getProductPrice() {
            return this.m_ProductPrice;
        }

        public String printTaxRate() {
            return Formats.PERCENT.formatValue(this.m_TaxRate);
        }

        public Double getTaxRate() {
            return this.m_TaxRate;
        }

        public String printProductPriceTax() {
            return Formats.CURRENCY.formatValue(this.m_ProductPriceTax);
        }

        public String printProductSubValue() {
            return Formats.CURRENCY.formatValue(this.m_ProductPriceTax * this.m_ProductUnits);
        }

        public String printProductPriceNet() {
            return Formats.CURRENCY.formatValue(this.m_ProductPrice * this.m_ProductUnits);
        }
    }
}

