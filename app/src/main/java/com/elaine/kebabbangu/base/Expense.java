package com.elaine.kebabbangu.base;

import java.io.Serializable;

/**
 * Created by Elaine on 10/26/2017.
 */

public class Expense implements Serializable {
    private Integer id;
    private String description;
    private String date;
    private double value;

    public Expense(){}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
