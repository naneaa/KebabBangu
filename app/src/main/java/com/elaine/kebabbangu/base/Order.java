package com.elaine.kebabbangu.base;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Created by Elaine on 5/28/2017.
 */

public class Order implements Serializable {
    private LinkedList<Product> products;
    private LinkedList<String> descriptions;
    private int id;
    private int number;
    private double price;
    private boolean isPaid;
    private String paymentMethod;
    private String clientName;
    private String date;

    public Order(int id){
        this.products = new LinkedList<>();
        this.descriptions = new LinkedList<>();
        this.id = id;
        this.price = 0.0;
        this.setPaid(false);
    }

    public Order(){
        this.products = new LinkedList<>();
        this.descriptions = new LinkedList<>();
        this.price = 0.0;
        this.setPaid(false);
    }

    public LinkedList<Product> getProducts() {
        return products;
    }

    public void setProducts(LinkedList<Product> products) {
        this.products = products;
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

    public LinkedList<String> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(LinkedList<String> descriptions) {
        this.descriptions = descriptions;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void AddItem(Product product, String description){
            products.add(product);

            descriptions.add(description);
        price += product.getPrice();
    }

    public void removeItem(int position){
        products.remove(position);
        descriptions.remove(position);
    }

    public String toString(){
        String list = "";
        for (int i = 0; i < products.size(); i++){
            list += products.get(i).toString() + "\n";
        }

        return "Pedido " + id + "\n" + list + "Valor Total: " + price;
    }

    public String stringList() {
        String list = "";
        for (int i = 0; i < products.size(); i++){
            list += products.get(i).getName() + descriptions.get(i);
            list += products.size() > 1 ? "\n" : "";
        }

        return list;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

}
