/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.query;

import java.io.File;
import java.sql.ResultSet;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author comp2
 */
public class Modal {
    Query query=new Query();
    //boolean flag=false;
    public ContextMenu addMenu(Pane test,MouseEvent event,int pid)
    {
        ContextMenu cm=new ContextMenu();
        MenuItem edit = new MenuItem("Edit");
        MenuItem delete = new MenuItem("Delete");
        cm.getItems().addAll(edit,delete);
        
        cm.show(test, Side.TOP, event.getX() ,event.getY());
        edit.setOnAction(new EventHandler<ActionEvent>() {
       @Override
         public void handle(ActionEvent event) {
           System.out.println("edit...");
           try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EditProject.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            
            ResultSet rs=query.getUnitList();
            while(rs.next())
            {
                ((ComboBox)root1.lookup("#unit_list")).getItems().addAll(
                    rs.getString("unit_name"));
            }
            ((Label)root1.lookup("#project_id")).setText(""+pid);
            rs=query.getProjectInfo(pid);
            while(rs.next())
            {
                ((TextField)root1.lookup("#project_name")).setText(rs.getString("project_name"));
                ((TextField)root1.lookup("#survey_no")).setText(rs.getString("survey_no"));
                ((ImageView)root1.lookup("#logo")).setImage(new Image(rs.getBinaryStream("project_logo")));
                ((ComboBox)root1.lookup("#city")).getSelectionModel().select(rs.getString("city"));
                ((ComboBox)root1.lookup("#district")).getSelectionModel().select(rs.getString("district"));
                ((ComboBox)root1.lookup("#location")).getSelectionModel().select(rs.getString("location"));
                ((TextField)root1.lookup("#total_area")).setText(rs.getString("total_area"));
                ((ComboBox)root1.lookup("#unit_list")).getSelectionModel().select(rs.getString("unit_name"));
                File dpath=new File(rs.getString("map"));
                Image icon=new Image(dpath.toURI().toString());
                ((ImageView)root1.lookup("#map")).setImage(icon);
                String names=rs.getString("ownership");
                
                try{
                    String arr[]=names.split(",");
                    for(int j=0;j<arr.length;j++)
                {
                  ((ComboBox)root1.lookup("#owner_list")).getItems().add(arr[j]);
                }}catch(Exception e){
                   ((ComboBox)root1.lookup("#owner_list")).getItems().add(names);
                }
                
            }
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Edit Project");
            stage.setScene(new Scene(root1));  
            stage.show();
            
          }
         catch (Exception e) {
             e.printStackTrace();
         
         }
        }
    });
        return cm;
    }
    
    
}
