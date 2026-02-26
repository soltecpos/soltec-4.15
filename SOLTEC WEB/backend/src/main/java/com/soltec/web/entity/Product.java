package com.soltec.web.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @Column(name = "id", length = 255)
    private String id;

    @Column(name = "reference", length = 255, nullable = false, unique = true)
    private String reference;

    @Column(name = "code", length = 255, nullable = false, unique = true)
    private String code;

    @Column(name = "codetype", length = 255)
    private String codetype;

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Column(name = "pricebuy", nullable = false)
    private double pricebuy;

    @Column(name = "pricesell", nullable = false)
    private double pricesell;

    @Column(name = "category", length = 255, nullable = false)
    private String categoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category", insertable = false, updatable = false)
    private Category category;

    @Column(name = "taxcat", length = 255, nullable = false)
    private String taxcatId;

    @Column(name = "stockcost")
    private double stockcost;

    @Column(name = "stockvolume")
    private double stockvolume;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "iscom")
    private boolean iscom;

    @Column(name = "isscale")
    private boolean isscale;

    @Column(name = "isconstant")
    private boolean isconstant;

    @Column(name = "printkb")
    private boolean printkb;

    @Column(name = "sendstatus")
    private boolean sendstatus;

    @Column(name = "isservice")
    private boolean isservice;

    @Column(name = "display", length = 255)
    private String display;

    @Column(name = "isvprice")
    private int isvprice;

    @Column(name = "isverpatrib")
    private int isverpatrib;

    @Column(name = "texttip", length = 255)
    private String texttip;

    @Column(name = "warranty")
    private int warranty;

    @Column(name = "stockunits")
    private double stockunits;

    @Column(name = "printto", length = 255)
    private String printto;

    @Column(name = "supplier", length = 255)
    private String supplier;

    @Column(name = "uom", length = 255)
    private String uom;

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getReference() { return reference; }
    public void setReference(String reference) { this.reference = reference; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getCodetype() { return codetype; }
    public void setCodetype(String codetype) { this.codetype = codetype; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getPricebuy() { return pricebuy; }
    public void setPricebuy(double pricebuy) { this.pricebuy = pricebuy; }

    public double getPricesell() { return pricesell; }
    public void setPricesell(double pricesell) { this.pricesell = pricesell; }

    public String getCategoryId() { return categoryId; }
    public void setCategoryId(String categoryId) { this.categoryId = categoryId; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }

    public String getTaxcatId() { return taxcatId; }
    public void setTaxcatId(String taxcatId) { this.taxcatId = taxcatId; }

    public double getStockcost() { return stockcost; }
    public void setStockcost(double stockcost) { this.stockcost = stockcost; }

    public double getStockvolume() { return stockvolume; }
    public void setStockvolume(double stockvolume) { this.stockvolume = stockvolume; }

    public byte[] getImage() { return image; }
    public void setImage(byte[] image) { this.image = image; }

    public boolean isIscom() { return iscom; }
    public void setIscom(boolean iscom) { this.iscom = iscom; }

    public boolean isIsscale() { return isscale; }
    public void setIsscale(boolean isscale) { this.isscale = isscale; }

    public boolean isIsconstant() { return isconstant; }
    public void setIsconstant(boolean isconstant) { this.isconstant = isconstant; }

    public boolean isPrintkb() { return printkb; }
    public void setPrintkb(boolean printkb) { this.printkb = printkb; }

    public boolean isSendstatus() { return sendstatus; }
    public void setSendstatus(boolean sendstatus) { this.sendstatus = sendstatus; }

    public boolean isIsservice() { return isservice; }
    public void setIsservice(boolean isservice) { this.isservice = isservice; }

    public String getDisplay() { return display; }
    public void setDisplay(String display) { this.display = display; }

    public int getIsvprice() { return isvprice; }
    public void setIsvprice(int isvprice) { this.isvprice = isvprice; }

    public int getIsverpatrib() { return isverpatrib; }
    public void setIsverpatrib(int isverpatrib) { this.isverpatrib = isverpatrib; }

    public String getTexttip() { return texttip; }
    public void setTexttip(String texttip) { this.texttip = texttip; }

    public int getWarranty() { return warranty; }
    public void setWarranty(int warranty) { this.warranty = warranty; }

    public double getStockunits() { return stockunits; }
    public void setStockunits(double stockunits) { this.stockunits = stockunits; }

    public String getPrintto() { return printto; }
    public void setPrintto(String printto) { this.printto = printto; }

    public String getSupplier() { return supplier; }
    public void setSupplier(String supplier) { this.supplier = supplier; }

    public String getUom() { return uom; }
    public void setUom(String uom) { this.uom = uom; }
}
