package com.elaine.kebabbangu.base;

import java.io.Serializable;

/**
 * Created by Elaine on 6/2/2017.
 */

public class StockItem implements Serializable {
    private Product product;
    private Integer quantity;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
