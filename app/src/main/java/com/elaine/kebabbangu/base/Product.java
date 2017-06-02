package com.elaine.kebabbangu.base;

import java.io.Serializable;

/**
 * Created by Elaine on 5/28/2017.
 */

public class Product implements Serializable{
    private Integer id;
    private String name;
    private double price;

    public Product(String name, double price){
        this.name = name;
        this.price = price;
    }

    public Product(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String toString(){
        return "Produto: " + name + " Preco: " + price;
    }

}
