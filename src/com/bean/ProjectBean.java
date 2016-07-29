package com.bean;


import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ADMIN
 */
public class ProjectBean {
    private int proj_id;
    private int builder_id;
    private String project_name;
    private String logo_path;
    private String city;
    private String district;
    private String location;
    private String total_area;
    private String owner;
    private String map_path;
    private String survey_no;
    private int unitId;
    private String unit_name;

    public int getBuilder_id() {
        return builder_id;
    }

    public void setBuilder_id(int builder_id) {
        this.builder_id = builder_id;
    }

    
    public int getUnitId() {
        return unitId;
    }

    public String getUnit_name() {
        return unit_name;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }

    public void setUnit_name(String unit_name) {
        this.unit_name = unit_name;
    }
    
    public String getSurvey_no() {
        return survey_no;
    }

    public void setSurvey_no(String survey_no) {
        this.survey_no = survey_no;
    }
    
    public void setMap_path(String map_path) {
        this.map_path = map_path;
    }
    
    public String getOwner() {
        return owner;
    }

    public String getMap_path() {
        return map_path;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
    
    public void setProj_id(int proj_id) {
        this.proj_id = proj_id;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public void setLogo_path(String logo_path) {
        this.logo_path = logo_path;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setTotal_area(String total_area) {
        this.total_area = total_area;
    }
    
    public int getProj_id() {
        return proj_id;
    }

    public String getProject_name() {
        return project_name;
    }

    public String getLogo_path() {
        return logo_path;
    }

    public String getCity() {
        return city;
    }

    public String getDistrict() {
        return district;
    }

    public String getLocation() {
        return location;
    }

    public String getTotal_area() {
        return total_area;
    }
    
   
    
}
