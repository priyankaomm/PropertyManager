/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.query;

import com.Connection.MyConnection;
import static com.aspose.imaging.internal.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.data;
import com.bean.UnitBean;
import com.query.Query;
import demo.FXMLDocumentController;
import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author comp2
 */
public class EditProject implements Initializable {
Query query =new Query();
    FXMLDocumentController fm=new FXMLDocumentController();
    /**
     * Initializes the controller class.
     */
  @FXML
  private ComboBox owner_list;
  @FXML
  private TextField owner;
  @FXML
  private TextField total_area;
  @FXML
  private TextField project_name;
  @FXML
  private Label project_id;
  @FXML
  private ImageView logo;
  @FXML
  private ComboBox city;
  @FXML
  private ComboBox district;
  @FXML
  private ComboBox location;
/*@FXML
public void handleOwnerAction(ActionEvent e)
      
{
   owner.setText(owner_list.getSelectionModel().getSelectedItem().toString());
}*/
 @FXML
public void editProjectName(ActionEvent e)
      
{
   String name=project_name.getText();
   String field="project_name";
   if(query.updateProjectName(field,name,project_id.getText()))
   {
       Alert alert = new Alert(Alert.AlertType.INFORMATION);
       alert.setTitle("Message");
       alert.setContentText("Updated Successfully!!");
       alert.showAndWait(); 
       project_name.setText(name);
   }
}
@FXML
public void editLogo(ActionEvent e)
      
{
    FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files",
                        "*.bmp", "*.png", "*.jpg", "*.gif"));
        chooser.setTitle("Open File");
        File file = chooser.showOpenDialog(new Stage());
        System.out.println("File name="+file.getPath());
        //file_path.setText(file.getPath());
        //FileOutputStream fio=new FileOutputStream(file);
        //fio.
        Image icon=new Image(file.toURI().toString());
        
   String path=file.getPath();
   String field="project_logo";
   if(query.updateLogo(field,path,project_id.getText()))
   {
       Alert alert = new Alert(Alert.AlertType.INFORMATION);
       alert.setTitle("Message");
       alert.setContentText("Updated Successfully!!");
       alert.showAndWait(); 
       logo.setImage(icon);
   }
}
@FXML
public void editCity(ActionEvent e)
      
{
   String name=city.getSelectionModel().getSelectedItem().toString();
   String field="city";
   if(query.updateProjectName(field,name,project_id.getText()))
   {
       Alert alert = new Alert(Alert.AlertType.INFORMATION);
       alert.setTitle("Message");
       alert.setContentText("Updated Successfully!!");
       alert.showAndWait(); 
       
   }
}
@FXML
public void editDistrict(ActionEvent e)
      
{
   String name=district.getSelectionModel().getSelectedItem().toString();
   String field="district";
   if(query.updateProjectName(field,name,project_id.getText()))
   {
       Alert alert = new Alert(Alert.AlertType.INFORMATION);
       alert.setTitle("Message");
       alert.setContentText("Updated Successfully!!");
       alert.showAndWait(); 
       
   }
}
@FXML
public void editLocation(ActionEvent e)
      
{
   String name=location.getSelectionModel().getSelectedItem().toString();
   String field="location";
   if(query.updateProjectName(field,name,project_id.getText()))
   {
       Alert alert = new Alert(Alert.AlertType.INFORMATION);
       alert.setTitle("Message");
       alert.setContentText("Updated Successfully!!");
       alert.showAndWait(); 
       
   }
}
@FXML
public void editOwner(ActionEvent e)
      
{
   String name=owner.getText();
   String field="ownership";
   if(query.updateOwner(field,name,project_id.getText()))
   {
       Alert alert = new Alert(Alert.AlertType.INFORMATION);
       alert.setTitle("Message");
       alert.setContentText("Updated Successfully!!");
       alert.showAndWait(); 
       
   }
}
@FXML
public void editArea(ActionEvent e)
      
{
   String name=total_area.getText();
   String field="total_area";
   if(query.updateProjectName(field,name,project_id.getText()))
   {
       Alert alert = new Alert(Alert.AlertType.INFORMATION);
       alert.setTitle("Message");
       alert.setContentText("Updated Successfully!!");
       alert.showAndWait(); 
       
   }
}
@FXML
public void deleteOwner(ActionEvent e)
      
{
   String name=owner_list.getSelectionModel().getSelectedItem().toString();
   String field="ownership";
   if(query.deleteOwner(field,name,project_id.getText()))
   {
       Alert alert = new Alert(Alert.AlertType.INFORMATION);
       alert.setTitle("Message");
       alert.setContentText("Updated Successfully!!");
       alert.showAndWait(); 
       
   }
}
   @FXML
    private void close_window(ActionEvent event)  {
          final Node source = (Node) event.getSource();
          final Stage stage = (Stage) source.getScene().getWindow();
          stage.close();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        System.out.println("in Edit Project controller");
        
        
    }    
   
}
