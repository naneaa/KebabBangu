package com.elaine.kebabbangu.base;

import java.util.LinkedList;

/**
 * Created by Elaine on 5/28/2017.
 */

public class Order {
    private LinkedList<Product> products;
    private LinkedList<Integer> quantity;
    private int id;
    private int number;
    private double price;
    private boolean isPaid;
    private String paymentMethod;

    public Order(int id){
        this.products = new LinkedList<>();
        this.quantity = new LinkedList<>();
        this.id = id;
        this.price = 0.0;
        this.setPaid(false);
    }

    public Order(){
        this.products = new LinkedList<>();
        this.quantity = new LinkedList<>();
        this.price = 0.0;
        this.setPaid(false);
    }

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double totalPrice) {
        this.price = totalPrice;
    }

    public void AddItem(Product product){
        if(products.contains(product)) {
            int index = products.indexOf(product);
            quantity.set(index, quantity.get(index) + 1);
        } else {
            products.add(product);
            quantity.add(1);
        }

        price += product.getPrice();
    }

    public String toString(){
        String list = "";
        for (int i = 0; i < products.size(); i++){
            list += quantity.get(i) + " - " + products.get(i).toString() + "\n";
        }

        return "Pedido " + id + "\n" + list + "Valor Total: " + price;
    }

    public String stringList() {
        String list = "";
        for (int i = 0; i < products.size(); i++){
            list += "(" + quantity.get(i) + ") " + products.get(i).getName();
            list += products.size() > 1 ? "\n" : "";
        }

        return list;
    }
}
