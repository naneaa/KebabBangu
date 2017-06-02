package com.elaine.kebabbangu;

import java.util.LinkedList;

/**
 * Created by Elaine on 6/2/2017.
 */

public class Stock {
    private LinkedList<Product> products;
    private LinkedList<Integer> quantity;

    public LinkedList<Product> getProducts() {
        return products;
    }

    public void setProducts(LinkedList<Product> products) {
        this.products = products;
    }

    public LinkedList<Integer> getQuantity() {
        return quantity;
    }

    public void setQuantity(LinkedList<Integer> quantity) {
        this.quantity = quantity;
    }
}
