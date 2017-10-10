package com.elaine.kebabbangu.base;

import java.io.Serializable;

/**
 * Created by Elaine on 5/28/2017.
 */

public class Product implements Serializable {
    private Integer id;
    private String name;
    private double price;
    private boolean hasSauce;
    private boolean hasSalad;
    private boolean hasCheese;
    private boolean onMenu;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public Product() {
    }

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


    public boolean hasSauce() {
        return hasSauce;
    }

    public void setHasSauce(boolean hasSauce) {
        this.hasSauce = hasSauce;
    }

    public boolean hasSalad() {
        return hasSalad;
    }

    public void setHasSalad(boolean hasSalad) {
        this.hasSalad = hasSalad;
    }

    public boolean hasCheese() {
        return hasCheese;
    }

    public void setHasCheese(boolean hasCheese) {
        this.hasCheese = hasCheese;
    }

    public boolean isOnMenu() {
        return onMenu;
    }

    public void setOnMenu(boolean onMenu) {
        this.onMenu = onMenu;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String toString() {
        return "Produto: " + name + " Preco: " + price;
    }

}
