package com.elaine.kebabbangu.base;

import java.io.Serializable;

/**
 * Created by Elaine on 6/3/2017.
 */

<<<<<<< HEAD
public class Register {
    private int id;

    public Register(){}
=======
public class Register  implements Serializable{
    private Integer id;
    private double startingValue;
    private double cashValue;
    private double debitValue;
    private double creditValue;
    private double totalValue;
    private String date;

    public Register(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getStarting() {
        return startingValue;
    }

    public void setStarting(double startingValue) {
        this.startingValue = startingValue;
    }

    public double getCash() {
        return cashValue;
    }

    public void setCash(double cashValue) {
        this.cashValue = cashValue;
    }

    public double getDebit() {
        return debitValue;
    }

    public void setDebit(double debitValue) {
        this.debitValue = debitValue;
    }

    public double getCredit() {
        return creditValue;
    }

    public void setCredit(double creditValue) {
        this.creditValue = creditValue;
    }
>>>>>>> parent of 9d4ced1... Revert "Oh well"

    public double getTotal() {
        return totalValue;
    }

    public void setTotal(double totalValue) {
        this.totalValue = totalValue;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
