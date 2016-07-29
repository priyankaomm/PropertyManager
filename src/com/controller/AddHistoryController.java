/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import com.query.Query;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author comp2
 */
public class AddHistoryController implements Initializable {
Query query =new Query();
    /**
     * Initializes the controller class.
     */
    @FXML
    private ScrollPane hscroll;
    @FXML
    private Label hissa_id;
    @FXML
    private Label addhistory_hissaid;
    private void loadOwner()
    {
        System.out.println("hissa id in loader:"+addhistory_hissaid.getText());
        try{
        ResultSet rs=query.getOwner(hissa_id.getText());
                     int x=100,y=50;
                     Pane spane=new Pane();
                     while(rs.next())
                     {
                         Button b=new Button();
                         b.setText(rs.getString("owner_name"));
                         b.setLayoutX(x);
                         b.setLayoutY(y);
                         spane.getChildren().add(b);
                     }
                     hscroll.setContent(spane);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    @FXML
    private void close_window(MouseEvent event)  {
          final Node source = (Node) event.getSource();
          final Stage stage = (Stage) source.getScene().getWindow();
          stage.close();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //loadOwner();
    }    
    
}
