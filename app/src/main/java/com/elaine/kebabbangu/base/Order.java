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

    public Order() {
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

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public LinkedList<String> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(LinkedList<String> descriptions) {
        this.descriptions = descriptions;
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

    public void AddItem(Product product, String description) {
        products.add(product);

        descriptions.add(description);
        price += product.getPrice();
    }

    public void removeItem(int position) {
        products.remove(position);
        descriptions.remove(position);
    }

    public String toString() {
        String list = "";
        for (int i = 0; i < products.size(); i++) {
            list += products.get(i).getName() + ".\n\n" + descriptions.get(i) + "\n\n";
        }

        return "Pedido " + number + " de " +  clientName + ":\n" +
                (isPaid ? "Pago." : "Nao pago.") + " " +
                (paymentMethod == "Cash" ? "Dinheiro.\n" :
                        (paymentMethod == "Debit" ? "Debito.\n" : "Credito.\n") ) +
                "\n" + list + "Valor Total: " + price;
    }

    public String stringList() {
        String list = "";
        for (int i = 0; i < products.size(); i++) {
            list += products.get(i).getName();
            list += i != products.size() - 1 ? ", " : "";
        }

        return list;
    }



    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }


    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
