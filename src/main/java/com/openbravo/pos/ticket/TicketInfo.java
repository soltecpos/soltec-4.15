/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang.StringUtils
 */
package com.openbravo.pos.ticket;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.DataRead;
import com.openbravo.data.loader.LocalRes;
import com.openbravo.data.loader.SerializableRead;
import com.openbravo.format.Formats;
import com.openbravo.pos.customers.CustomerInfoExt;
import com.openbravo.pos.forms.AppConfig;
import com.openbravo.pos.payment.PaymentInfo;
import com.openbravo.pos.payment.PaymentInfoMagcard;
import com.openbravo.pos.payment.PaymentInfoTicket;
import com.openbravo.pos.ticket.TaxInfo;
import com.openbravo.pos.ticket.TicketLineInfo;
import com.openbravo.pos.ticket.TicketTaxInfo;
import com.openbravo.pos.ticket.UserInfo;
import com.openbravo.pos.util.StringUtils;
import java.io.ByteArrayInputStream;
import java.io.Externalizable;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

public final class TicketInfo
implements SerializableRead,
Externalizable {
    private static final long serialVersionUID = 2765650092387265178L;
    public static final int RECEIPT_NORMAL = 0;
    public static final int RECEIPT_REFUND = 1;
    public static final int RECEIPT_PAYMENT = 2;
    public static final int RECEIPT_NOSALE = 3;
    public static final int REFUND_NOT = 0;
    public static final int REFUND_PARTIAL = 1;
    public static final int REFUND_ALL = 2;
    private static final DateFormat m_dateformat = new SimpleDateFormat("hh:mm");
    private String m_sHost;
    private String m_sId;
    private int tickettype = 0;
    private int m_iTicketId = 0;
    private int m_iPickupId;
    private Date m_dDate;
    private Properties attributes;
    private UserInfo m_User = null;
    private Double multiply;
    private CustomerInfoExt m_Customer = null;
    private String m_sActiveCash = null;
    private List<TicketLineInfo> m_aLines;
    private List<PaymentInfo> payments;
    private List<TicketTaxInfo> taxes = null;
    private final String m_sResponse;
    private String loyaltyCardNumber;
    private Boolean oldTicket = false;
    private boolean tip;
    private PaymentInfoTicket m_paymentInfo;
    private boolean m_isProcessed;
    private final String m_locked;
    private Double nsum;
    private int ticketstatus;
    private boolean isFe = false;
    private boolean isPose = false;
    private boolean isDocs = false;
    private boolean isNoc = false;
    private String cufe = "";
    private List<String> cufeDiv = new ArrayList<String>();
    private String nocuuid = "";
    private String nocnumber = "";
    private String nocissue = "";
    private String nocConcepto = "";
    private static String Hostname;

    public static void setHostname(String name) {
        Hostname = name;
    }

    public static String getHostname() {
        return Hostname;
    }

    public TicketInfo() {
        this.m_sId = UUID.randomUUID().toString();
        this.m_dDate = new Date();
        this.attributes = new Properties();
        this.m_aLines = new ArrayList<TicketLineInfo>();
        this.payments = new ArrayList<PaymentInfo>();
        this.m_sResponse = null;
        AppConfig config = new AppConfig(new File(new File(System.getProperty("user.home")), "soltec.properties"));
        config.load();
        this.tip = Boolean.valueOf(config.getProperty("machine.showTip"));
        this.m_isProcessed = false;
        this.m_locked = null;
        this.ticketstatus = 0;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(this.m_sId);
        out.writeInt(this.tickettype);
        out.writeInt(this.m_iTicketId);
        out.writeObject(this.m_Customer);
        out.writeObject(this.m_dDate);
        out.writeObject(this.attributes);
        out.writeObject(this.m_aLines);
        out.writeInt(this.ticketstatus);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        List lines;
        this.m_sId = (String)in.readObject();
        this.tickettype = in.readInt();
        this.m_iTicketId = in.readInt();
        this.m_Customer = (CustomerInfoExt)in.readObject();
        this.m_dDate = (Date)in.readObject();
        this.attributes = (Properties)in.readObject();
        this.m_aLines = lines = (List)in.readObject();
        this.m_User = null;
        this.m_sActiveCash = null;
        this.payments = new ArrayList<PaymentInfo>();
        this.taxes = null;
        this.ticketstatus = in.readInt();
    }

    @Override
    public void readValues(DataRead dr) throws BasicException {
        this.m_sId = dr.getString(1);
        this.tickettype = dr.getInt(2);
        this.m_iTicketId = dr.getInt(3);
        this.m_dDate = dr.getTimestamp(4);
        this.m_sActiveCash = dr.getString(5);
        try {
            byte[] img = dr.getBytes(6);
            if (img != null) {
                this.attributes.loadFromXML(new ByteArrayInputStream(img));
            }
        }
        catch (IOException iOException) {
            // empty catch block
        }
        this.m_User = new UserInfo(dr.getString(7), dr.getString(8));
        this.m_Customer = new CustomerInfoExt(dr.getString(9));
        this.m_aLines = new ArrayList<TicketLineInfo>();
        this.payments = new ArrayList<PaymentInfo>();
        this.taxes = null;
        this.ticketstatus = dr.getInt(10);
    }

    public TicketInfo copyTicket() {
        TicketInfo t = new TicketInfo();
        t.tickettype = this.tickettype;
        t.m_iTicketId = this.m_iTicketId;
        t.m_dDate = this.m_dDate;
        t.m_sActiveCash = this.m_sActiveCash;
        t.attributes = (Properties)this.attributes.clone();
        t.m_User = this.m_User;
        t.m_Customer = this.m_Customer;
        t.m_aLines = new ArrayList<TicketLineInfo>();
        for (TicketLineInfo l : this.m_aLines) {
            t.m_aLines.add(l.copyTicketLine());
        }
        t.refreshLines();
        t.payments = new LinkedList<PaymentInfo>();
        for (PaymentInfo p : this.payments) {
            t.payments.add(p.copyPayment());
        }
        t.oldTicket = this.oldTicket;
        t.ticketstatus = this.ticketstatus;
        return t;
    }

    public String getId() {
        return this.m_sId;
    }

    public int getTicketType() {
        return this.tickettype;
    }

    public void setTicketType(int tickettype) {
        this.tickettype = tickettype;
    }

    public int getTicketId() {
        return this.m_iTicketId;
    }

    public void setTicketId(int iTicketId) {
        this.m_iTicketId = iTicketId;
    }

    public int getTicketStatus() {
        return this.ticketstatus;
    }

    public void setTicketStatus(int ticketstatus) {
        this.ticketstatus = this.m_iTicketId > 0 ? this.m_iTicketId : ticketstatus;
    }

    public void setPickupId(int iTicketId) {
        this.m_iPickupId = iTicketId;
    }

    public int getPickupId() {
        return this.m_iPickupId;
    }

    public String getName(Object info) {
        ArrayList<String> name = new ArrayList<String>();
        String nameprop = this.getProperty("name");
        if (nameprop != null) {
            name.add(nameprop);
        }
        if (this.m_User != null) {
            name.add(this.m_User.getName());
        }
        if (info == null) {
            if (this.m_iTicketId == 0) {
                name.add("(" + m_dateformat.format(this.m_dDate) + " " + Long.toString(this.m_dDate.getTime() % 1000L) + ")");
            } else {
                name.add(Integer.toString(this.m_iTicketId));
            }
        } else {
            name.add(info.toString());
        }
        if (this.m_Customer != null) {
            name.add(this.m_Customer.getName());
        }
        return org.apache.commons.lang.StringUtils.join(name, (String)" - ");
    }

    public String getName() {
        return this.getName(null);
    }

    public Date getDate() {
        return this.m_dDate;
    }

    public void setDate(Date dDate) {
        this.m_dDate = dDate;
    }

    public String getHost() {
        AppConfig m_config_host = new AppConfig(new File(System.getProperty("user.home"), "soltec.properties"));
        m_config_host.load();
        String machineHostname = m_config_host.getProperty("machine.hostname");
        m_config_host = null;
        return machineHostname;
    }

    public UserInfo getUser() {
        return this.m_User;
    }

    public void setUser(UserInfo value) {
        this.m_User = value;
    }

    public CustomerInfoExt getCustomer() {
        return this.m_Customer;
    }

    public void setCustomer(CustomerInfoExt value) {
        this.m_Customer = value;
    }

    public String getCustomerId() {
        if (this.m_Customer == null) {
            return null;
        }
        return this.m_Customer.getId();
    }

    public String getTransactionID() {
        return this.getPayments().size() > 0 ? this.getPayments().get(this.getPayments().size() - 1).getTransactionID() : StringUtils.getCardNumber();
    }

    public String getReturnMessage() {
        return this.getPayments().get(this.getPayments().size() - 1) instanceof PaymentInfoMagcard ? ((PaymentInfoMagcard)this.getPayments().get(this.getPayments().size() - 1)).getReturnMessage() : LocalRes.getIntString("button.OK");
    }

    public void setActiveCash(String value) {
        this.m_sActiveCash = value;
    }

    public String getActiveCash() {
        return this.m_sActiveCash;
    }

    public String getProperty(String key) {
        return this.attributes.getProperty(key);
    }

    public String getProperty(String key, String defaultvalue) {
        return this.attributes.getProperty(key, defaultvalue);
    }

    public void setProperty(String key, String value) {
        this.attributes.setProperty(key, value);
    }

    public Properties getProperties() {
        return this.attributes;
    }

    public TicketLineInfo getLine(int index) {
        return this.m_aLines.get(index);
    }

    public void addLine(TicketLineInfo oLine) {
        oLine.setTicket(this.m_sId, this.m_aLines.size());
        this.m_aLines.add(oLine);
    }

    public void insertLine(int index, TicketLineInfo oLine) {
        this.m_aLines.add(index, oLine);
        this.refreshLines();
    }

    public void setLine(int index, TicketLineInfo oLine) {
        oLine.setTicket(this.m_sId, index);
        this.m_aLines.set(index, oLine);
    }

    public void removeLine(int index) {
        this.m_aLines.remove(index);
        this.refreshLines();
    }

    private void refreshLines() {
        for (int i = 0; i < this.m_aLines.size(); ++i) {
            this.getLine(i).setTicket(this.m_sId, i);
        }
    }

    public int getLinesCount() {
        return this.m_aLines.size();
    }

    public double getArticlesCount() {
        double dArticles = 0.0;
        for (TicketLineInfo oLine : this.m_aLines) {
            dArticles += oLine.getMultiply();
        }
        return dArticles;
    }

    public double getSubTotal() {
        double sum = 0.0;
        for (TicketLineInfo line : this.m_aLines) {
            sum += line.getSubValue();
        }
        return sum;
    }

    public double getTax() {
        double sum = 0.0;
        if (this.hasTaxesCalculated()) {
            for (TicketLineInfo line : this.m_aLines) {
                sum += line.getTax();
            }
        } else {
            for (TicketLineInfo line : this.m_aLines) {
                sum += line.getTax();
            }
        }
        return sum;
    }

    public double getTotal() {
        return this.getSubTotal() + this.getTax();
    }

    public double getServiceCharge() {
        return this.getTotal() + this.getTax();
    }

    public double getTotalPaid() {
        double sum = 0.0;
        for (PaymentInfo p : this.payments) {
            if ("debtpaid".equals(p.getName())) continue;
            sum += p.getTotal();
        }
        return sum;
    }

    public double getTendered() {
        return this.getTotalPaid();
    }

    public List<TicketLineInfo> getLines() {
        return this.m_aLines;
    }

    public void setLines(List<TicketLineInfo> l) {
        this.m_aLines = l;
    }

    public List<PaymentInfo> getPayments() {
        return this.payments;
    }

    public void setPayments(List<? extends PaymentInfo> l) {
        this.payments = new ArrayList<PaymentInfo>(l);
    }

    public void resetPayments() {
        this.payments = new ArrayList<PaymentInfo>();
    }

    public List<TicketTaxInfo> getTaxes() {
        return this.taxes;
    }

    public boolean hasTaxesCalculated() {
        return this.taxes != null;
    }

    public void setTaxes(List<TicketTaxInfo> l) {
        this.taxes = l;
    }

    public void resetTaxes() {
        this.taxes = null;
    }

    public void setTip(boolean tips) {
        this.tip = tips;
    }

    public boolean hasTip() {
        return this.tip;
    }

    public void setIsProcessed(boolean isP) {
        this.m_isProcessed = isP;
    }

    public TicketTaxInfo getTaxLine(TaxInfo tax) {
        for (TicketTaxInfo taxline : this.taxes) {
            if (!tax.getId().equals(taxline.getTaxInfo().getId())) continue;
            return taxline;
        }
        return new TicketTaxInfo(tax);
    }

    public TicketTaxInfo[] getTaxLines() {
        HashMap<String, TicketTaxInfo> m = new HashMap<String, TicketTaxInfo>();
        for (TicketLineInfo oLine : this.m_aLines) {
            TicketTaxInfo t = (TicketTaxInfo)m.get(oLine.getTaxInfo().getId());
            if (t == null) {
                t = new TicketTaxInfo(oLine.getTaxInfo());
                m.put(t.getTaxInfo().getId(), t);
            }
            t.add(oLine.getSubValue());
        }
        Collection avalues = m.values();
        return (TicketTaxInfo[]) avalues.toArray(new TicketTaxInfo[avalues.size()]);
    }

    public String printId() {
        AppConfig m_config = new AppConfig(new File(System.getProperty("user.home"), "soltec.properties"));
        m_config.load();
        String receiptSize = m_config.getProperty("till.receiptsize");
        String receiptPrefix = m_config.getProperty("till.receiptprefix");
        m_config = null;
        if (this.m_iTicketId > 0) {
            String tmpTicketId = Integer.toString(this.m_iTicketId);
            if (receiptSize == null || Integer.parseInt(receiptSize) <= tmpTicketId.length()) {
                if (receiptPrefix != null) {
                    tmpTicketId = receiptPrefix + tmpTicketId;
                }
                return tmpTicketId;
            }
            while (tmpTicketId.length() < Integer.parseInt(receiptSize)) {
                tmpTicketId = "0" + tmpTicketId;
            }
            if (receiptPrefix != null) {
                tmpTicketId = receiptPrefix + tmpTicketId;
            }
            return tmpTicketId;
        }
        return "";
    }

    public String printDate() {
        return Formats.TIMESTAMP.formatValue(this.m_dDate);
    }

    public String printUser() {
        return this.m_User == null ? "" : this.m_User.getName();
    }

    public String printHost() {
        return this.m_sHost;
    }

    public void clearCardNumber() {
        this.loyaltyCardNumber = null;
    }

    public void setLoyaltyCardNumber(String cardNumber) {
        this.loyaltyCardNumber = cardNumber;
    }

    public String getLoyaltyCardNumber() {
        return this.loyaltyCardNumber;
    }

    public String printCustomer() {
        return this.m_Customer == null ? "" : this.m_Customer.getName();
    }

    public String printArticlesCount() {
        return Formats.DOUBLE.formatValue(this.getArticlesCount());
    }

    public String printSubTotal() {
        return Formats.CURRENCY.formatValue(this.getSubTotal());
    }

    public String printTax() {
        return Formats.CURRENCY.formatValue(this.getTax());
    }

    public String printTotal() {
        return Formats.CURRENCY.formatValue(this.getTotal());
    }

    public String printTotalPaid() {
        return Formats.CURRENCY.formatValue(this.getTotalPaid());
    }

    public String printTendered() {
        return Formats.CURRENCY.formatValue(this.getTendered());
    }

    public String VoucherReturned() {
        return Formats.CURRENCY.formatValue(this.getTotalPaid() - this.getTotal());
    }

    public boolean getOldTicket() {
        return this.oldTicket;
    }

    public void setOldTicket(Boolean otState) {
        this.oldTicket = otState;
    }

    public String getTicketHeaderFooterData(String data) {
        AppConfig m_config = new AppConfig(new File(System.getProperty("user.home"), "soltec.properties"));
        m_config.load();
        String row = m_config.getProperty("tkt." + data);
        return row;
    }

    public String printTicketHeaderLine1() {
        String lineData = this.getTicketHeaderFooterData("header1");
        if (lineData != null) {
            return lineData;
        }
        return "";
    }

    public String printTicketHeaderLine2() {
        String lineData = this.getTicketHeaderFooterData("header2");
        if (lineData != null) {
            return lineData;
        }
        return "";
    }

    public String printTicketHeaderLine3() {
        String lineData = this.getTicketHeaderFooterData("header3");
        if (lineData != null) {
            return lineData;
        }
        return "";
    }

    public String printTicketHeaderLine4() {
        String lineData = this.getTicketHeaderFooterData("header4");
        if (lineData != null) {
            return lineData;
        }
        return "";
    }

    public String printTicketHeaderLine5() {
        String lineData = this.getTicketHeaderFooterData("header5");
        if (lineData != null) {
            return lineData;
        }
        return "";
    }

    public String printTicketHeaderLine6() {
        String lineData = this.getTicketHeaderFooterData("header6");
        if (lineData != null) {
            return lineData;
        }
        return "";
    }

    public String printTicketFooterLine1() {
        String lineData = this.getTicketHeaderFooterData("footer1");
        if (lineData != null) {
            return lineData;
        }
        return "";
    }

    public String printTicketFooterLine2() {
        String lineData = this.getTicketHeaderFooterData("footer2");
        if (lineData != null) {
            return lineData;
        }
        return "";
    }

    public String printTicketFooterLine3() {
        String lineData = this.getTicketHeaderFooterData("footer3");
        if (lineData != null) {
            return lineData;
        }
        return "";
    }

    public String printTicketFooterLine4() {
        String lineData = this.getTicketHeaderFooterData("footer4");
        if (lineData != null) {
            return lineData;
        }
        return "";
    }

    public String printTicketFooterLine5() {
        String lineData = this.getTicketHeaderFooterData("footer5");
        if (lineData != null) {
            return lineData;
        }
        return "";
    }

    public String printTicketFooterLine6() {
        String lineData = this.getTicketHeaderFooterData("footer6");
        if (lineData != null) {
            return lineData;
        }
        return "";
    }

    public boolean getIsFe() {
        return "true".equals(this.getProperty("efe.isFe"));
    }

    public void setIsFe(boolean isFe) {
        this.setProperty("efe.isFe", isFe ? "true" : "false");
        this.isFe = isFe;
    }

    public boolean getIsPose() {
        return "true".equals(this.getProperty("efe.isPose"));
    }

    public void setIsPose(boolean isPose) {
        this.setProperty("efe.isPose", isPose ? "true" : "false");
        this.isPose = isPose;
    }

    public boolean getIsDocs() {
        return "true".equals(this.getProperty("efe.isDocs"));
    }

    public void setIsDocs(boolean isDocs) {
        this.setProperty("efe.isDocs", isDocs ? "true" : "false");
        this.isDocs = isDocs;
    }

    public boolean getIsNoc() {
        return "true".equals(this.getProperty("efe.isNoc"));
    }

    public void setIsNoc(boolean isNoc) {
        this.setProperty("efe.isNoc", isNoc ? "true" : "false");
        this.isNoc = isNoc;
    }

    public String getCufe() {
        return this.getProperty("efe.cufe");
    }

    public void setCufe(String cufe) {
        this.setProperty("efe.cufe", cufe);
        this.cufe = cufe;
    }

    public List<String> getcufeDiv() {
        return this.cufeDiv;
    }

    public void setcufeDiv(List<String> cufeDiv) {
        this.cufeDiv = cufeDiv;
    }

    public String getNocuuid() {
        return this.getProperty("efe.nocuuid");
    }

    public void setNocuuid(String nocuuid) {
        this.setProperty("efe.nocuuid", nocuuid);
        this.nocuuid = nocuuid;
    }

    public String getNocnumber() {
        return this.getProperty("efe.nocnumber");
    }

    public void setNocnumber(String nocnumber) {
        this.setProperty("efe.nocnumber", nocnumber);
        this.nocnumber = nocnumber;
    }

    public String getNocissue() {
        return this.getProperty("efe.nocissue");
    }

    public void setNocissue(String nocissue) {
        this.setProperty("efe.nocissue", nocissue);
        this.nocissue = nocissue;
    }

    public String getnocConcepto() {
        return this.getProperty("efe.nocConcepto");
    }

    public void setNocConcepto(String nocConcepto) {
        this.setProperty("efe.nocConcepto", nocConcepto);
        this.nocConcepto = nocConcepto;
    }
}

