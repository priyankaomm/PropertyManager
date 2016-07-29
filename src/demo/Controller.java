/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;





/**
 *
 * @author ADMIN
 */

public class Controller implements Initializable {
    private IntegerProperty proj_id;
   /* public void setLabel(int pid)
    {
        System.out.println("In Controller.."+pid);
        project_id.setText(""+pid);
    }
    public Label getLabel()
    {
        //System.out.println("In Controller.."+pid);
        return project_id;
    }*/
    public Controller()
    {
        System.out.println("in controller");
        proj_id = new SimpleIntegerProperty(15);
        proj_id.set(10);
    }
   
      

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
    
}
