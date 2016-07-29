/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo;

import com.Connection.MyConnection;
import static com.aspose.imaging.internal.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.data;
import com.bean.UnitBean;
import com.query.Query;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author comp2
 */
public class EditProject implements Initializable {
Query query =new Query();
    /**
     * Initializes the controller class.
     */

    @FXML
    private TextField unit_name;
    @FXML
    private TextField short_name;
    @FXML
    private TableView unit_table;
    private ObservableList data;
     @FXML
    private void close_window(MouseEvent event)  {
          final Node source = (Node) event.getSource();
          final Stage stage = (Stage) source.getScene().getWindow();
          stage.close();
    }
     @FXML
    private void addUnit(ActionEvent event)  {
    try{
        if(query.addUnit(unit_name.getText(),short_name.getText()))
        {
            
            //crateFolderById(project_id.getText());
            //System.out.println("in insert"+project_id.getText());
                       
           Alert alert = new Alert(Alert.AlertType.INFORMATION);
           alert.setTitle("Information");
           //alert.setHeaderText("Look, an Information Dialog");
           alert.setContentText("Unit Added Successfully!!");
           alert.showAndWait();
           unit_table.setItems(buildData());
           unit_name.setText("");
           short_name.setText("");
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
    public ObservableList<UnitBean> loadUnit()
    {
         ObservableList<UnitBean> personData = FXCollections.observableArrayList();
         try{
        
         Connection con=MyConnection.createConnection();
         PreparedStatement pmt=con.prepareStatement("select * from unit");
        // Add some sample data
         ResultSet rs=pmt.executeQuery();
         while(rs.next())
         {
             personData.add(new UnitBean(rs.getString("Id"),rs.getString("unit_name")));
         }
         }catch(Exception e){}
    return personData;
    }
    
    public ObservableList buildData(){

	          Connection c ;

	          data = FXCollections.observableArrayList();

	          try{

	            c = MyConnection.createConnection();

	            //SQL FOR SELECTING ALL OF CUSTOMER

	            String SQL = "SELECT * from unit";

	            //ResultSet

	            ResultSet rs = c.createStatement().executeQuery(SQL);

	 

	            /**********************************

	             * TABLE COLUMN ADDED DYNAMICALLY *

	             **********************************/

	            for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++){

	                //We are using non property style for making dynamic table

	                final int j = i;               

	                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
                        
                        col.prefWidthProperty().bind(unit_table.widthProperty().multiply(0.33));
                        col.setResizable(false);
	                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){                   
                        
	                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {                                                                                             

	                        return new SimpleStringProperty(param.getValue().get(j).toString());                       

	                    }                   

	                });

	                

	                unit_table.getColumns().addAll(col);
                        
	                System.out.println("Column ["+i+"] ");

	            }

	 

	            /********************************

	             * Data added to ObservableList *

	             ********************************/

	            while(rs.next()){

	                //Iterate Row

	                ObservableList<String> row = FXCollections.observableArrayList();

	                for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){

	                    //Iterate Column

	                    row.add(rs.getString(i));
                            //row.add(rs.getString("unit_name"));

	                }

	                System.out.println("Row [1] added "+row );

	                data.add(row);

	           System.out.println("data [1] added "+data );

	            }

	 

	            //FINALLY ADDED TO TableView

	            unit_table.setItems(data);

	          }catch(Exception e){

	              e.printStackTrace();

	              System.out.println("Error on Building Data");            

	          }
                return data;
	      }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        System.out.println("in unit controller");
        buildData();
        
    }    
   
}
