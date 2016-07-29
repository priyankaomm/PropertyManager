/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bean;

/**
 *
 * @author comp2
 */
public class HistoryBean {
    private int id;
    private int hissa_id;
    private String hissa_survey;
    private String owner_id;
    private String child_id;

    public void setId(int id) {
        this.id = id;
    }

    public void setHissa_id(int hissa_id) {
        this.hissa_id = hissa_id;
    }

    public void setHissa_survey(String hissa_survey) {
        this.hissa_survey = hissa_survey;
    }

    public void setOwner_id(String owner_id) {
        this.owner_id = owner_id;
    }

    public void setChild_id(String child_id) {
        this.child_id = child_id;
    }

    public int getId() {
        return id;
    }

    public int getHissa_id() {
        return hissa_id;
    }

    public String getHissa_survey() {
        return hissa_survey;
    }

    public String getOwner_id() {
        return owner_id;
    }

    public String getChild_id() {
        return child_id;
    }
    
    
}
