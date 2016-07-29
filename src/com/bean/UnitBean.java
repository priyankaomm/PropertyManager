/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bean;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author comp2
 */
public class UnitBean {
 

    private final StringProperty id;
    private final StringProperty unit_name;
    public UnitBean() {
        this(null, null);
    }

    /**
     * Constructor with some initial data.
     * 
     * @param firstName
     * @param lastName
     */
    public UnitBean(String id, String unit_name) {
        this.id = new SimpleStringProperty(id);
        this.unit_name = new SimpleStringProperty(unit_name);

    }

    public String getId() {
        return id.get();
    }

    public String getUnit_name() {
        return unit_name.get();
    }
    public void setId(String id) {
        this.id.set(id);
    }
    public void setUnit_name(String unitName) {
        this.unit_name.set(unitName);
    }
}
