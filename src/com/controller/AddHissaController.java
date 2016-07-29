/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import com.bean.HissaBean;
import com.query.Query;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author comp2
 */
public class AddHissaController implements Initializable {
Query query =new Query();
    HissaBean hbean=new HissaBean();
    @FXML
    private Label plot_no;
    @FXML
    private Label survey_no;
    @FXML
    private Label total_area;
    @FXML
    private Label tunit;
    @FXML
    private TextField area_purchased;
    @FXML
    private TextField title_owner;
    @FXML
    private TextField hissa_no;
    @FXML
    private ComboBox punit;
    /**
     * Initializes the controller class.
     */
    @FXML
    private void close_window(MouseEvent event)  {
          final Node source = (Node) event.getSource();
          final Stage stage = (Stage) source.getScene().getWindow();
          stage.close();
    }
    @FXML
    private void addHissa(ActionEvent event) throws SQLException  {
        //hbean.setProject_id(query.getProjectId(survey_no.getText(),plot_no.getText()));
       // hbean.setPlot_id(query.getPlotId(survey_no.getText(),plot_no.getText()));
        hbean.setSurvey_no(survey_no.getText());
        hbean.setTotal_area(total_area.getText());
        hbean.setArea_purchased(area_purchased.getText());
        hbean.setTitle_owner(title_owner.getText());
        hbean.setUnit_id(query.getUnitId(punit.getSelectionModel().getSelectedItem().toString()));
        hbean.setHissa_survey(survey_no.getText()+"/"+hissa_no.getText());
        hbean.setHissa_no(hissa_no.getText());
        try{
        if(query.addHissa(hbean))
        {
            
           Alert alert = new Alert(Alert.AlertType.INFORMATION);
           alert.setTitle("Information");
           //alert.setHeaderText("Look, an Information Dialog");
           alert.setContentText("Plotting done!!");
           alert.showAndWait();
           final Node source = (Node) event.getSource();
           final Stage stage1 = (Stage) source.getScene().getWindow();
           stage1.close();
           //loadUnit();
           //refresh(event);
           
        }else{
           Alert alert = new Alert(Alert.AlertType.INFORMATION);
           alert.setTitle("Error");
           alert.setHeaderText("Look, an Error Dialog");
           alert.setContentText("Error has occured!!");
           alert.showAndWait();
        }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
           alert.setTitle("Error");
           alert.setHeaderText("Look, an Information Dialog");
           alert.setContentText(e.getMessage());
           alert.showAndWait();
        }
    }
    
            
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
