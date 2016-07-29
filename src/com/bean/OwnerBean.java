/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bean;

import java.sql.Date;

/**
 *
 * @author comp2
 */
public class OwnerBean {
    private int owner_id;
    private String owner_name;
    private Date from_date;
    private Date to_date;
    private String mutation_no;
    private String saledeed_no;
    private int mutation_id;
    private int saledeed_id;
    private int hissa_id;
    private String hissa_survey;
    private String purchaser_name;

    public String getPurchaser_name() {
        return purchaser_name;
    }

    public void setPurchaser_name(String purchaser_name) {
        this.purchaser_name = purchaser_name;
    }

    
    public String getHissa_survey() {
        return hissa_survey;
    }

    public void setHissa_survey(String hissa_survey) {
        this.hissa_survey = hissa_survey;
    }
    
    public void setOwner_name(String owner_name) {
        this.owner_name = owner_name;
    }

    public String getOwner_name() {
        return owner_name;
    }

    public int getHissa_id() {
        return hissa_id;
    }

    public void setHissa_id(int hissa_id) {
        this.hissa_id = hissa_id;
    }
    

    public void setOwner_id(int owner_id) {
        this.owner_id = owner_id;
    }

    public void setFrom_date(Date from_date) {
        this.from_date = from_date;
    }

    public void setTo_date(Date to_date) {
        this.to_date = to_date;
    }

    public void setMutation_no(String mutation_no) {
        this.mutation_no = mutation_no;
    }

    public void setSaledeed_no(String saledeed_no) {
        this.saledeed_no = saledeed_no;
    }

    public void setMutation_id(int mutation_id) {
        this.mutation_id = mutation_id;
    }

    public void setSaledeed_id(int saledeed_id) {
        this.saledeed_id = saledeed_id;
    }

    public int getOwner_id() {
        return owner_id;
    }

    public Date getFrom_date() {
        return from_date;
    }

    public Date getTo_date() {
        return to_date;
    }

    public String getMutation_no() {
        return mutation_no;
    }

    public String getSaledeed_no() {
        return saledeed_no;
    }

    public int getMutation_id() {
        return mutation_id;
    }

    public int getSaledeed_id() {
        return saledeed_id;
    }
     
    
}
