/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.inventory;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.DataRead;
import com.openbravo.data.loader.SerializerRead;

public class ProductStock {
    String pId;
    String location;
    Double units;
    Double minimum;
    Double maximum;
    Double pricebuy;
    Double pricesell;

    public ProductStock() {
    }

    public ProductStock(String pId, String location, Double units, Double minimum, Double maximum, Double pricebuy, Double pricesell) {
        this.pId = pId;
        this.location = location;
        this.units = units;
        this.minimum = minimum;
        this.maximum = maximum;
        this.pricebuy = pricebuy;
        this.pricesell = pricesell;
    }

    public String getProductId() {
        return this.pId;
    }

    public void setProductId(String pId) {
        this.pId = pId;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getUnits() {
        return this.units;
    }

    public void setUnits(Double units) {
        this.units = units;
    }

    public Double getMinimum() {
        return this.minimum;
    }

    public void setMinimum(Double minimum) {
        this.minimum = minimum;
    }

    public Double getMaximum() {
        return this.maximum;
    }

    public void setMaximum(Double maximum) {
        this.maximum = maximum;
    }

    public Double getPriceBuy() {
        return this.pricebuy;
    }

    public void setPriceBuy(Double pricebuy) {
        this.pricebuy = pricebuy;
    }

    public Double getPriceSell() {
        return this.pricesell;
    }

    public void setPriceSell(Double pricesell) {
        this.pricebuy = pricesell;
    }

    public static SerializerRead<ProductStock> getSerializerRead() {
        return new SerializerRead<ProductStock>(){

            @Override
            public ProductStock readValues(DataRead dr) throws BasicException {
                String pId = dr.getString(1);
                String location = dr.getString(2);
                Double units = dr.getDouble(3);
                Double minimum = dr.getDouble(4);
                Double maximum = dr.getDouble(5);
                Double pricebuy = dr.getDouble(6);
                Double pricesell = dr.getDouble(7);
                return new ProductStock(pId, location, units, minimum, maximum, pricebuy, pricesell);
            }
        };
    }
}

