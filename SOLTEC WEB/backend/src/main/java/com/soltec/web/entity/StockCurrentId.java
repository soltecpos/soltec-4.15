package com.soltec.web.entity;

import java.io.Serializable;
import java.util.Objects;

public class StockCurrentId implements Serializable {
    private String location;
    private String productId;

    public StockCurrentId() {}

    public StockCurrentId(String location, String productId) {
        this.location = location;
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockCurrentId that = (StockCurrentId) o;
        return Objects.equals(location, that.location) && Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(location, productId);
    }
}
