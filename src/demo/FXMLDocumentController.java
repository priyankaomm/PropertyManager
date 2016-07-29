/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo;


import com.Connection.MyConnection;
import com.bean.HissaBean;
import com.bean.HistoryBean;
import com.bean.OwnerBean;
import com.bean.ProjectBean;
import com.bean.UnitBean;
import demo.CreatingMap;
import demo.NewMarker;
import com.qoppa.pdfViewerFX.PDFViewer;
import com.query.Mapconnection;
import com.query.Modal;
import com.query.Query;
import static com.sun.deploy.uitoolkit.ToolkitStore.dispose;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import static javafx.scene.layout.AnchorPane.setLeftAnchor;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.Duration;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import org.apache.commons.io.FileUtils;

import org.imgscalr.Scalr;
import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;
import java.awt.Checkbox;
import java.awt.Component;
import java.awt.Point;
import java.awt.Rectangle;
import java.sql.Date;
import java.util.Optional;
import javafx.embed.swing.JFXPanel;
import javafx.embed.swing.SwingNode;
import javafx.geometry.Bounds;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;

/**
 *
 * @author ADMIN
 */
public class FXMLDocumentController implements Initializable {
    ProjectBean pbean =new ProjectBean();
    HissaBean hbean=new HissaBean();
    HistoryBean hsbean=new HistoryBean();
    OwnerBean obean=new OwnerBean();
    Modal modal=new Modal();
    final int N_SECS = 1;
    Boolean issaved=true;
    Boolean isviewed=false;
    int previous_oid;
    Boolean isSame=new Boolean(false);
    Query query =new Query();
    List<Integer> node=new ArrayList<Integer>();
    //Dashboard
     @FXML
    private AnchorPane add_pane;
    @FXML
    private Pane map_panel;
    @FXML
    private Pane hpane;
    @FXML
    private ScrollPane scroll;
    @FXML
    private Button refreshButton;
    @FXML
    private TextField search;
    @FXML
    private ComboBox view_by;
    @FXML
    private Label builder_id;
    @FXML
    private ImageView photo;
    //Add Project
    @FXML
    private ProgressBar progress;
    @FXML
    private Label file_path;
    @FXML
    private Label firm_list;
    @FXML
    private ImageView logo;
    @FXML
    private ImageView map;
    @FXML
    private TextField project_name;
    @FXML
    private ComboBox city;
    @FXML
    private ComboBox district;
    @FXML
    private ComboBox location;
    @FXML
    private ComboBox unitList;
    @FXML
    private TextField total_area;
    @FXML
    private TextField survey_no;   
    @FXML
    private Label map_path;
    @FXML
    private Label pbuilder_id;
    @FXML
    private TextField owner;
    @FXML
    private Label tunit;
    //plot Info
    @FXML
    private TextField plot_area;
    @FXML
    private ComboBox plot_unit;
    @FXML
    private Label plot_survey;
    @FXML
    private TableView hissa_table;
    
    private ObservableList data;
    @FXML
    private Button soldarea;
    @FXML
    private Button nonsold;
    @FXML
    private Button plot_area_save;
    @FXML
    private Label plot_no;
    @FXML
    private TextField area_purchased;
    @FXML
    private TextField title_owner;
    @FXML
    private TextField hissa_no;
    @FXML
    private ComboBox punit;
    @FXML
    private Label plotinfo_id;
    @FXML
    private Label plot_info_plotid;
    @FXML
    private Pane table_pane;
    @FXML
    private Pane main_pane;
    @FXML
    private Button show_btn;
    @FXML
    private Button save_btn;
    //Add History
    @FXML
    private ScrollPane hscroll;
    boolean isHidden=false;
    @FXML 
    private Label addhistory_id;
    private PDFViewer m_PDFViewer;
    //UploadDocument
    @FXML
    private ComboBox doc_type;
    @FXML
    private Label upload_hissa_id;
    @FXML 
    private Pane upload_pane;
    @FXML 
    private Pane file_pane;
    @FXML 
    private Pane filepane;
    @FXML
    private Label isClicked;
    @FXML
    private ScrollPane fscroll;
    @FXML
    private ScrollPane sale_scroll;
    @FXML
    private Label upload_owner_id;
    //View History
    @FXML
    private Label view_hissa_id;
    @FXML
    private Label view_plot_id;
    @FXML
    private ScrollPane vscroll;
    @FXML
    private TextField search_history;
    @FXML
    private Pane search_pane;
    ListView list=null;
    @FXML
    private AnchorPane history_main;
    //search history
    @FXML
    private ComboBox plots;
      @FXML
    private ComboBox hissa;
      @FXML
    private ListView viewlist;
    @FXML
    private Label splot_id;
    @FXML
    private Label shissa_id;
    @FXML
    private Label sproject_id;
    //Purchaser
    @FXML
    private ComboBox owner_id_list;
    @FXML
    private Label pc_hissa_id;
    @FXML
    private Label pc_owner_id;
    @FXML
    private Label pc_hissa_survey;
    @FXML
    private Label edit_hissa_id;
    @FXML
    private Label edit_owner_id;
    @FXML
    private DatePicker edit_from_date;
    @FXML
    private DatePicker edit_to_date;
    @FXML
    private TextField edit_mno;
    @FXML
    private TextField edit_sno;
    @FXML
    private TextField edit_owner_name;
    @FXML
    private Label edit_hs;
    @FXML
    private Label addhistory_hissaid;
    public Pane loadMain(int bid)
    {
        Pane spane=new Pane();
        //spane.setStyle("-fx-background-color:  white;");
        //Pane[] p=new Pane[100];
        
        //scroll.setFitToWidth(true);
        try{
        Connection con=MyConnection.createConnection();
            PreparedStatement psmt=con.prepareStatement("select * from project where builder_id=?");
            psmt.setInt(1, bid);
            ResultSet rs=psmt.executeQuery();
            String ls[]=new String[100];
            Blob img[]=new Blob[100];
            String location[]=new String[100];
            String owner[]=new String[100];
            String area[]=new String[100];
            Integer pid[]=new Integer[100];
            int j=0;
            boolean flag=false;
            while(rs.next())
            {
                ls[j]=rs.getString("project_name");
                img[j]=rs.getBlob("project_logo");
                location[j]=rs.getString("location");
                owner[j]=rs.getString("ownership");
                area[j]=rs.getString("total_area");
                pid[j]=rs.getInt("Id");
                j++;
                flag=true;
            }
            //rs.last();
            //int last=rs.getRow();
            final Pane[] p=new Pane[j];
            int x=50,y=100;
            //int i=0;
            for(int i=0;i<j;i++)
            {
                p[i]= new Pane();
                Label id=new Label("Id: "+pid[i]);
                final int proj=pid[i];
                id.setFont(new Font("Times new Roman",18));
                id.setLayoutX(20);
                id.setLayoutY(80);
                
                Label l=new Label("Project Name : "+ls[i]);
                l.setFont(new Font("Times new Roman",18));
                l.setLayoutX(40);
                l.setLayoutY(id.getLayoutY()+30);
                l.setStyle("-fx-font-weight:bold;");
                Label loc=new Label("Location : "+location[i]);
                loc.setFont(new Font("Times new Roman",14));
                loc.setLayoutX(60);
                loc.setLayoutY(l.getLayoutY()+30);
                loc.setStyle("-fx-font-weight:bold;");
                Label oner=new Label("OwnerShip : "+owner[i]);
                oner.setFont(new Font("Times new Roman",14));
                oner.setLayoutX(40);
                oner.setLayoutY(loc.getLayoutY()+30);
                oner.setWrapText(true);
                oner.setMaxWidth(200);
                oner.setStyle("-fx-font-weight:bold;");
                Label ara=new Label("Total area : "+area[i]);
                ara.setFont(new Font("Times new Roman",14));
                ara.setLayoutX(60);
                ara.setLayoutY(oner.getLayoutY()+oner.getHeight()+30);
                ara.setStyle("-fx-font-weight:bold;");
                byte[] imageByte = img[i].getBytes(1, (int) img[i].length());
            
  	    InputStream is=new ByteArrayInputStream(imageByte);
  	    
            Image ig=new Image(is, 70, 70, true, true);
            
                ImageView iv=new ImageView();
                iv.setImage(ig);
                iv.setLayoutX(80);
                iv.setLayoutY(30);
                p[i].getChildren().add(iv);
                p[i].setLayoutX(x);
                p[i].setLayoutY(y);
                p[i].setMinSize(230, 250);
                p[i].getChildren().add(l);
                p[i].getChildren().add(loc);
                //p[i].getChildren().add(oner);
                p[i].getChildren().add(ara);
                p[i].getChildren().add(id);
                id.setVisible(false);
                p[i].setId(""+pid[i]);
                p[i].setStyle("-fx-padding: 10;" + 
                      "-fx-border-style: solid inside;" + 
                      "-fx-border-width: 2;" +
                      
                      "-fx-border-radius: 5;" +
                      "-fx-background-color: rgba(66,74,93,0.5);" + 
                      "-fx-border-color:black;");
                 p[i].styleProperty().bind(
      Bindings
        .when(p[i].hoverProperty())
          .then(
            new SimpleStringProperty("-fx-padding: 10;" + 
                      "-fx-border-style: solid inside;" + 
                      "-fx-border-width: 2;" +
                      
                      "-fx-border-radius: 5;" +
                      "-fx-background-color:  rgba(66,74,93,0.8);" +
                      "-fx-border-color:black;")
          )
          .otherwise(
            new SimpleStringProperty("-fx-padding: 10;" + 
                      "-fx-border-style: solid inside;" + 
                      "-fx-border-width: 2;" + 
                      "-fx-border-radius: 5;" +
                      "-fx-background-color: rgba(66,74,93,0.5);" + 
                      "-fx-border-color:black;")
          )
    );
                 Pane test=p[i];
                 int count = 0;
                 int proj_id=pid[i];
                 p[i].setOnMouseClicked(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            //System.out.println("mouse click detected! "+event.getSource());
            
            if (event.getButton() == MouseButton.SECONDARY) {
               
                ContextMenu cm=modal.addMenu(test,event,proj_id);
               
            }
            else
            {
                openProjectMap(proj);
            }
        }
    });
             

                spane.getChildren().add(p[i]);
               
                x=x+250;
                if(x>900)
                {
                    y=y+270;
                    x=50;
                }
                //sPane.setBackground(new Background(black));
            }
            if(!flag)
            {
                Label l=new Label("No Projects Available!!");
                
                l.setFont(new Font("Arial black",18));
                //l.setTextFill(new Color(255,255,255,0));
                //l.setStyle("-fx-background-color:white;");
                l.setLayoutX(400);
                l.setLayoutY(300);
                spane.getChildren().add(l);
            }
            //scroll.setStyle("-fx-background-color:white;");
            scroll.setContent(spane);
            
            scroll.setPannable(true);
            //scroll.setV
            
            //scroll.setPrefSize(1100, 750);
            
            //scroll.setContent(sPane);
            
            
        }catch(Exception e)
        {
           //e.printStackTrace();
        }
        return spane;
    }
    @FXML
    public void handleViewByAction(ActionEvent evt)
    {
        if(view_by.getSelectionModel().getSelectedIndex()==0)
        {
          
        }
        else
        {
            int bid=Integer.parseInt(builder_id.getText());
            final Node source = (Node) evt.getSource();
            final Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
            //stage.initModality(Modality.WINDOW_MODAL);
            //stage.initOwner();
            
            openJMapViewer(bid);
            //loadMap();
        }
    }
    @FXML
    public void handleViewMap(MouseEvent evt)
    {
        int bid=Integer.parseInt(builder_id.getText());
        openJMapViewer(bid);
    }
    @FXML
    private void searchProject()
    {
        Pane spane=new Pane();
        
        try{
        Connection con=MyConnection.createConnection();
            PreparedStatement psmt=con.prepareStatement("select * from project where (project_name like '"+search.getText()+"%' or "
                    + "district like '"+search.getText()+"%' or location like '"+search.getText()+"%' or ownership like '"+search.getText()+"%') and"
                    + "builder_id=?");
            psmt.setString(1,builder_id.getText());
            ResultSet rs=psmt.executeQuery();
            String ls[]=new String[100];
            Blob img[]=new Blob[100];
            String location[]=new String[100];
            String owner[]=new String[100];
            String area[]=new String[100];
            Integer pid[]=new Integer[100];
            int j=0;
            boolean flag=false;
            while(rs.next())
            {
                ls[j]=rs.getString("project_name");
                img[j]=rs.getBlob("project_logo");
                location[j]=rs.getString("location");
                owner[j]=rs.getString("ownership");
                area[j]=rs.getString("total_area");
                pid[j]=rs.getInt("Id");
                j++;
                flag=true;
            }
            //rs.last();
            //int last=rs.getRow();
            final Pane[] p=new Pane[j];
            int x=50,y=100;
            //int i=0;
            for(int i=0;i<j;i++)
            {
                p[i]= new Pane();
                Label id=new Label("Id: "+pid[i]);
                final int proj=pid[i];
                id.setFont(new Font("Times new Roman",18));
                id.setLayoutX(20);
                id.setLayoutY(80);
                
                Label l=new Label("Project Name : "+ls[i]);
                l.setFont(new Font("Times new Roman",18));
                l.setLayoutX(40);
                l.setLayoutY(100);
                l.setStyle("-fx-font-weight:bold;");
                Label loc=new Label("Location : "+location[i]);
                loc.setFont(new Font("Times new Roman",14));
                loc.setLayoutX(60);
                loc.setLayoutY(130);
                loc.setStyle("-fx-font-weight:bold;");
                Label oner=new Label("OwnerShip : "+owner[i]);
                oner.setFont(new Font("Times new Roman",14));
                oner.setLayoutX(40);
                oner.setLayoutY(160);
                oner.setStyle("-fx-font-weight:bold;");
                Label ara=new Label("Total area : "+area[i]);
                ara.setFont(new Font("Times new Roman",14));
                ara.setLayoutX(60);
                ara.setLayoutY(190);
                ara.setStyle("-fx-font-weight:bold;");
                byte[] imageByte = img[i].getBytes(1, (int) img[i].length());
            
  	    InputStream is=new ByteArrayInputStream(imageByte);
  	    
            Image ig=new Image(is, 70, 70, true, true);
            
                ImageView iv=new ImageView();
                iv.setImage(ig);
                iv.setLayoutX(80);
                iv.setLayoutY(30);
                p[i].getChildren().add(iv);
                p[i].setLayoutX(x);
                p[i].setLayoutY(y);
                p[i].setMinSize(230, 250);
                p[i].getChildren().add(l);
                p[i].getChildren().add(loc);
                p[i].getChildren().add(oner);
                p[i].getChildren().add(ara);
                p[i].getChildren().add(id);
                id.setVisible(false);
                p[i].setId(""+pid[i]);
                p[i].setStyle("-fx-padding: 10;" + 
                      "-fx-border-style: solid inside;" + 
                      "-fx-border-width: 2;" +
                      
                      "-fx-border-radius: 5;" +
                      "-fx-background-color: rgba(66,74,93,0.5);" + 
                      "-fx-border-color:black;");
                 p[i].styleProperty().bind(
      Bindings
        .when(p[i].hoverProperty())
          .then(
            new SimpleStringProperty("-fx-padding: 10;" + 
                      "-fx-border-style: solid inside;" + 
                      "-fx-border-width: 2;" +
                      
                      "-fx-border-radius: 5;" +
                      "-fx-background-color:  rgba(66,74,93,0.8);" +
                      "-fx-border-color:black;")
          )
          .otherwise(
            new SimpleStringProperty("-fx-padding: 10;" + 
                      "-fx-border-style: solid inside;" + 
                      "-fx-border-width: 2;" + 
                      "-fx-border-radius: 5;" +
                      "-fx-background-color: rgba(66,74,93,0.5);" + 
                      "-fx-border-color:black;")
          )
    );
                 p[i].setOnMouseClicked(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            System.out.println("mouse click detected! "+event.getSource());
            openProjectMap(proj);
        }
    });

                spane.getChildren().add(p[i]);
               
                x=x+250;
                if(x>900)
                {
                    y=y+270;
                    x=50;
                }
                //sPane.setBackground(new Background(black));
            }
            if(!flag)
            {
                Label l=new Label("No Projects Available!!");
                
                l.setFont(new Font("Arial black",18));
                //l.setTextFill(new Color(255,255,255,0));
                //l.setStyle("-fx-background-color:white;");
                l.setLayoutX(400);
                l.setLayoutY(300);
                spane.getChildren().add(l);
            }
            scroll.setStyle("-fx-background-color:white;");
            scroll.setContent(spane);
            
            scroll.setPannable(true);
            //scroll.setV
            
            //scroll.setPrefSize(1100, 750);
            
            //scroll.setContent(sPane);
            
            
        }catch(Exception e)
        {
           e.printStackTrace();
        }
    }
    @FXML
    private void addProjectAction(MouseEvent event) {
       
            
            try{
                  
            //final Node source = (Node) event.getSource();
            //final Stage stage1 = (Stage) source.getScene().getWindow();
            //stage1.close();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddProject.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            //int bid= Integer.parseInt(builder_id.getText());
            ((Label)root1.lookup("#pbuilder_id")).setText(builder_id.getText());
            ResultSet rs=query.getUnitList();
            while(rs.next())
            {
                ((ComboBox)root1.lookup("#unit_list")).getItems().addAll(
                    rs.getString("unit_name"));
            }
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Add Project");
            stage.setScene(new Scene(root1));  
            stage.show();
            
          }
         catch (Exception e) {
             /*e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
           alert.setTitle("Error");
           //alert.setHeaderText("Look, an Information Dialog");
           alert.setContentText(e.getMessage());
           alert.showAndWait();*/
        }
    }
   @FXML
    private void addUnitAction(MouseEvent event) {
       
            
            try{
                  
            EditProject c=new EditProject();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddUnit.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            //((TableView)root1.lookup("#unit_table")).setItems(c.buildData());
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Add Unit");
            stage.setScene(new Scene(root1));  
            stage.show();
            
          }
         catch (Exception e) {
             e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
           alert.setTitle("Error");
           //alert.setHeaderText("Look, an Information Dialog");
           alert.setContentText(e.getMessage());
           alert.showAndWait();
        }
    }
    
    
    public void openProjectMap(int proj_id) {
       
            
            try{
                 FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ProjectMap.fxml"));
            //Parent root1 = (Parent) fxmlLoader.load();
            //Stage stage = new Stage();
                 //Fade transition
                     Parent root1 = (Parent) fxmlLoader.load();
                     Stage stage = new Stage();
                     FadeTransition ft = new FadeTransition(Duration.millis(2000), root1);
                     ft.setFromValue(0.0);
                     ft.setToValue(1.0);
                     ft.play();
                     
                     Scene scene = new Scene(root1);
                     ((Label)root1.lookup("#pid")).setText(""+proj_id);
                     List<Label> l=stichImage(proj_id);
                    for(int i=0;i<l.size();i++)
                    {
                      ((Pane)root1.lookup("#map_pane")).getChildren().add(l.get(i));
                    }
            List<Label> b=map_button(proj_id);
            for(int i=0;i<b.size();i++)
            {
                ((Pane)root1.lookup("#map_pane")).getChildren().add(b.get(i));
            }
            ResultSet rs=query.getProjectDetails(proj_id);
            while(rs.next())
            {
                ((Label)root1.lookup("#sno")).setText("Survey No "+rs.getString("survey_no"));
                ((Label)root1.lookup("#area")).setText(rs.getString("total_area"));
                ((Label)root1.lookup("#area_unit")).setText(rs.getString("short_name"));
            }
            rs=query.getTotalPlotArea(proj_id);
             while(rs.next())
             {
                ((Label)root1.lookup("#plot_area")).setText(""+rs.getDouble("total"));
                ((Label)root1.lookup("#plot_sold")).setText(""+rs.getDouble("Sold"));
                ((Label)root1.lookup("#plot_rem")).setText(""+rs.getDouble("Nonsold"));
                
             }
             rs=query.getPlotDetails(proj_id);
             while(rs.next())
             {
                 
             }
            //stichImage(proj_id);
            //File file=getMap(proj_id);
            //Image i=new Image(file.toURI().toString());
            //((ImageView)root1.lookup("#map")).setImage(i);
             TranslateTransition slideOut = new TranslateTransition(Duration.seconds(1), ((Pane)root1.lookup("#side-bar")));
             slideOut.setByX(-300);
             slideOut.play();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Map");
            stage.setScene(scene);  
            stage.show(); 
            
            
          }
         catch (Exception e) {
             e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
           alert.setTitle("Error");
           //alert.setHeaderText("Look, an Information Dialog");
           alert.setContentText(e.getMessage());
           alert.showAndWait();
        }
        
    
    }
    private File getMap(int proj_id) {
       File f=null;
            
            try{
                 Connection con=MyConnection.createConnection();
                 PreparedStatement pmt=con.prepareStatement("select * from project where Id=?");
                 pmt.setInt(1, proj_id);
                 ResultSet rs=pmt.executeQuery();
                 String path="";
                 while(rs.next())
                         {
                             path=rs.getString("map");
                         }
            f=new File(path);
          }
         catch (Exception e) {
             e.printStackTrace();
            
        }
        
    return f;
    }
    @FXML
    private void openFileChooser(ActionEvent event) throws FileNotFoundException {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files",
                        "*.bmp", "*.png", "*.jpg", "*.gif"));
        chooser.setTitle("Open File");
        File file = chooser.showOpenDialog(new Stage());
        System.out.println("File name="+file.getPath());
        file_path.setText(file.getPath());
        //FileOutputStream fio=new FileOutputStream(file);
        //fio.
        Image icon=new Image(file.toURI().toString());
        logo.setImage(icon);
    }
    @FXML
    private void openFileChooserForMap(ActionEvent event) throws FileNotFoundException {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files",
                        "*.psd"));
        chooser.setTitle("Open File");
        File file = chooser.showOpenDialog(new Stage());
        map_path.setText(file.getPath());
        final Node source = (Node) event.getSource();
        final Scene stage = (Scene) source.getScene();
       //stage.close();
        //if(map_path.getText().equals(""))
        createProgressBar(stage,file);
        
        
         //progress.setProgress(-1.0);
         //progress.toFront();

        //threadBudgetShow().start();
        
    }
    private void createProgressBar(Scene stage,File file)
    {
        
        Task task = createTask(file);
       Label l=createCounter(task);
       ProgressIndicator pi=createProgressIndicator(task);
        add_pane.getChildren().addAll(
            pi,
            l
        );
        pi.setLayoutX(300);
        pi.setLayoutY(500);
       // pi.setVisible(false);
        l.setVisible(false);
        new Thread(task).start();
        if(task.isDone())
        {
            System.out.println("completed.....");
            pi.setVisible(false);
        }
       // pi.setVisible(false);
    }
private Task<Void> createTask(File file) {
        return new Task<Void>() {
            @Override public Void call() {
                for (int i=0; i < N_SECS; i++) {
                    if (isCancelled()) {
                        break;
                    }
                           System.out.println("File name="+file.getPath());
                           map_path.setText(file.getPath());
                            pbean.setProj_id(query.getMaxProjectId());
                            File dpath=createDirectory(pbean.getProj_id());
                            //query.ExportPsdLayersToImages(path+"/psd files"+, path);
                             Image icon=new Image(dpath.toURI().toString());
                             map.setImage(icon);
                             
                             pbean.setMap_path(dpath.getPath());
                    // uncomment updateProgress call if you want to show progress
                    // rather than let progress remain indeterminate.
                     updateProgress(i, N_SECS);
                    updateMessage((N_SECS - i) + "");
                    
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        return null;
                    }
                }

                updateMessage(0 + "");
                updateProgress(N_SECS, N_SECS);

                return null;
            }
        };
    }
private ProgressIndicator createProgressIndicator(Task task) {
        ProgressIndicator progress = new ProgressIndicator();

        progress.progressProperty().bind(task.progressProperty());

        return progress;
    }
private Label createCounter(Task task) {
        Label counter = new Label();

        counter.setMinWidth(20);
        counter.setAlignment(Pos.CENTER_RIGHT);
        //counter.setLayoutX(100);
        //counter.setLayoutY(100);
        counter.textProperty().bind(task.messageProperty());
        counter.setStyle("-fx-border-color: forestgreen;");

        return counter;
    }
    
    
    @FXML
    private void addMoreFirms(ActionEvent event) {
        
        String list=null;
        if(firm_list.getText().equals(""))
        {
        list=owner.getText();
        firm_list.setText(list);
        }
        else{
            list=firm_list.getText().concat(","+owner.getText());
            firm_list.setText(list);
         }
        //firm_list.setText();
        owner.setText("");
    }
     @FXML
    private void insertProject(ActionEvent event) throws SQLException {
        pbean.setBuilder_id(Integer.parseInt(pbuilder_id.getText()));
        pbean.setProject_name(project_name.getText());
        pbean.setCity(city.getSelectionModel().getSelectedItem().toString());
        pbean.setDistrict(district.getSelectionModel().getSelectedItem().toString());
        pbean.setLocation(location.getSelectionModel().getSelectedItem().toString());
        pbean.setLogo_path(file_path.getText());
        pbean.setTotal_area(total_area.getText());
        pbean.setOwner(firm_list.getText());
        pbean.setSurvey_no(survey_no.getText());
        pbean.setUnitId(query.getUnitId(unitList.getSelectionModel().getSelectedItem().toString()));
        //pbean.setMap_path(map_path.getText());
        
        try{
        if(query.addProject(pbean))
        {
            
            //crateFolderById(project_id.getText());
            //System.out.println("in insert"+project_id.getText());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
           alert.setTitle("Information");
           //alert.setHeaderText("Look, an Information Dialog");
           alert.setContentText("Project Added Successfully!!Please refresh page!!!");
           alert.showAndWait();
           int bid=Integer.parseInt(pbuilder_id.getText());            
           
           final Node source = (Node) event.getSource();
           final Stage stage = (Stage) source.getScene().getWindow();
           stage.close();
           
            openJMapViewer(bid);
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
    @FXML
    private void savePlotArea(ActionEvent event) {
        String parea=plot_area.getText();
        String psurvey=plot_survey.getText();
        try{
        if(plot_area_save.getText().equalsIgnoreCase("save"))
        {
            String punit=plot_unit.getSelectionModel().getSelectedItem().toString();
        
        if(query.savePlotArea(parea,punit,psurvey))
        {
                    
           Alert alert = new Alert(Alert.AlertType.INFORMATION);
           alert.setTitle("Information");
           //alert.setHeaderText("Look, an Information Dialog");
           alert.setContentText("Area saved!!!");
           alert.showAndWait();
           ResultSet rs=query.getPlotDetails(plot_survey.getText());
            while(rs.next())
            {
                plot_area.setText(rs.getString("total_area"));
                soldarea.setText("Total Sold Area:"+rs.getString("sold_area"));
                nonsold.setText("Remaining Area:"+rs.getString("nonsold_area"));
                tunit.setText(rs.getString("short_name"));
                //plot_unit.selectionModelProperty().setValue(rs.getString("unit_name"));
                plot_area_save.setText("Edit");
              
            }
           
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
           alert.setTitle("Error");
           alert.setHeaderText("Look, an Error Dialog");
           alert.setContentText("Error has occured!!");
           alert.showAndWait();
        }
        }
        else{
            if(query.editPlotArea(parea,psurvey))
        {
                    
           Alert alert = new Alert(Alert.AlertType.INFORMATION);
           alert.setTitle("Information");
           //alert.setHeaderText("Look, an Information Dialog");
           alert.setContentText("Area saved!!!");
           alert.showAndWait();
           ResultSet rs=query.getPlotDetails(plot_survey.getText());
            while(rs.next())
            {
                plot_area.setText(rs.getString("total_area"));
                soldarea.setText("Total Sold Area:"+rs.getString("sold_area"));
                nonsold.setText("Remaining Area:"+rs.getString("nonsold_area"));
                tunit.setText(rs.getString("short_name"));
                //plot_unit.selectionModelProperty().setValue(rs.getString("unit_name"));
                plot_area_save.setText("Edit");
              
            }
           
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("Error");
           //alert.setHeaderText("Look, an Error Dialog");
           alert.setContentText("Please Enter valid area!!");
           alert.showAndWait();
        }
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
        
    
    private File createDirectory(int pid)
    {
        

File theDir = new File("C:/Property");
System.out.println("in insert"+pid);
// if the directory does not exist, create it
if (!theDir.exists()) {
    System.out.println("creating directory: " + theDir.getName());
    boolean result = false;

    try{
        theDir.mkdir();
        result = true;
    } 
    catch(SecurityException se){
        //handle it
        se.printStackTrace();
    }        
    if(result) {    
        System.out.println("DIR created");  
    }
    
}
    System.out.println("path="+theDir.getPath());
    File path=crateFolderById(theDir.getPath(),pid);
    return path;
    }

 private File crateFolderById(String path,int pid) 
    {
        
        File dir = new File(path+"/"+pid);
        if(!dir.exists())
        {
        dir.mkdir();
        }
        File f1=new File(dir.getPath()+"/"+"psd files");
        if(!f1.exists())
        {
        f1.mkdir();
        }
         File f2=new File(dir.getPath()+"/"+"png files");
        if(!f2.exists())
        {
        f2.mkdir();
        }
//create temp files folder
        File f3=new File(dir.getPath()+"/"+"temp files");
        if(!f3.exists())
        {
        f3.mkdir();
        }
        File fp=null;
        try{
        String source = map_path.getText();
        //directory where file will be copied
        String target =f1.getPath()+"/";
     
        //name of source file
        File sourceFile = new File(source);
        String name = sourceFile.getName();
     
        File targetFile = new File(target+name);
        System.out.println("Copying file : " + sourceFile.getName() +" from Java Program");
     
        //copy file from one location to other
        FileUtils.copyFile(sourceFile, targetFile);
        fp=query.ExportPsdLayersToImages( targetFile,f2.getPath(),pid);
        System.out.println("copying of file from Java program is completed");
        
        }catch(Exception e)
        {
            e.printStackTrace();
        }

return fp;
}
    
 
    @FXML
    public void refresh(ActionEvent event)  {
        try{
            String bid=builder_id.getText();
            Image profile=photo.getImage();
        final Node source = (Node) event.getSource();
       final Stage stage = (Stage) source.getScene().getWindow();
       stage.close();
           
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Pane p=loadMain(Integer.parseInt(bid));
        ((Label)root.lookup("#builder_id")).setText(""+bid);
        ((ScrollPane)root.lookup("#scroll_pane")).setContent(p);
                     if(photo !=null)
                     {
                         ((ImageView)root.lookup("#profile_photo")).setImage(profile);
                     }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        }catch(Exception e)
        {
           Alert alert = new Alert(Alert.AlertType.INFORMATION);
           alert.setTitle("Error");
           //alert.setHeaderText("Look, an Information Dialog");
           alert.setContentText(e.getMessage());
           alert.showAndWait(); 
        }
    }
    @FXML
    private void close_window(MouseEvent event)  {
          final Node source = (Node) event.getSource();
          final Stage stage = (Stage) source.getScene().getWindow();
          stage.close();
    }
   
    @FXML
    private void close_Editwindow(MouseEvent event)  {
          close_window(event);
          refreshAddHistory(edit_hs.getText(), edit_hissa_id.getText());
    }
    private void close(ActionEvent evt)  {
          final Node source = (Node) evt.getSource();
          final Stage stage = (Stage) source.getScene().getWindow();
          stage.close();
    }
    @FXML
    private void close_PlotInfo(MouseEvent event)  {
        String plotinfo_pid=plotinfo_id.getText();
        openProjectMap(Integer.parseInt(plotinfo_pid));
          close_window(event);
    }
    @FXML
    private void close_history(MouseEvent event)  {
        String plotinfo_pid=addhistory_id.getText();
        openProjectMap(Integer.parseInt(plotinfo_pid));
          close_window(event);
    }
    @FXML
    private void close_All(MouseEvent event)  {
        
          Platform.exit();
    }
    @FXML
    private void close_view_history(MouseEvent event)  {
        String plotinfo_pid=view_plot_id.getText();
        openProjectMap(Integer.parseInt(plotinfo_pid));
          close_window(event);
    }
    @FXML
    private void close_Projectwindow(MouseEvent event) throws SQLException  {
        if(map_path.getText().equals(""))
        {
            
        }
        else{
            query.deleteLastPath();
        }
          final Node source = (Node) event.getSource();
          final Stage stage = (Stage) source.getScene().getWindow();
          stage.close();
    }
    private List<Label> stichImage(int pid) 
    {
       BufferedImage result = new BufferedImage(1200, 600,BufferedImage.TYPE_INT_RGB);
        Graphics g = result.getGraphics();
        List<Label> map_list=new ArrayList<Label>();
        
        try{
        Connection con=MyConnection.createConnection();
        PreparedStatement pmt=con.prepareStatement("select * from layer_info where project_id=?");
        pmt.setInt(1, pid);
        ResultSet rs=pmt.executeQuery();
        int i=0;
        String img[]=new String[100];
        BufferedImage bimg[]=new BufferedImage[100];
        
        while(rs.next())
        {
            final int j=i+1;
        img[i]="C:/Property/"+pid+"/png files/Layers/layer"+(i+1)+".png";
       final String  fp=img[i];
            //System.out.println("In stich image image path=="+img[i]);
        bimg[i]=ImageIO.read(new File(img[i]));
        
        //g.drawImage(bimg[i], rs.getInt("left"),rs.getInt("top") , null);
        //Button b=new Button();
        //b.setText("Plot"+(i+1));
        
        Label l=new Label();
        l.setLayoutX(rs.getInt("left"));
        l.setLayoutY(rs.getInt("top"));
        Image image = new Image("file:///C:/Property/"+pid+"/png files/Layers/layer"+(i+1)+".png");
       // Image i=new Image
        //Label label = new Label("Label");
        //l.setText(""+(i+1));
        l.setWrapText(true);
        l.setGraphic(new ImageView(image));
        //Hover effect
        l.setOnMouseEntered(new EventHandler<MouseEvent>() {
    @Override public void handle(MouseEvent e) {
        l.setScaleX(1.5);
        l.setScaleY(1.5);
    }
});

l.setOnMouseExited(new EventHandler<MouseEvent>() {
    @Override public void handle(MouseEvent e) {
        l.setScaleX(1);
        l.setScaleY(1);
    }
});
l.setOnMouseClicked(new EventHandler<MouseEvent>() {
    @Override public void handle(MouseEvent e) {
        close_window(e);
        openPlot(pid,j,image);
    }
});
        map_list.add(l);
        //map_panel.getChildren().add(l);
        i++;
        }
        //ImageIO.write(result,"png",new File("C:\\Property\\"+pid+"\\temp files\\resulting.png")); 
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return map_list;
    }
    private List<Label> map_button(int pid) 
    {
       BufferedImage result = new BufferedImage(1200, 600,BufferedImage.TYPE_INT_RGB);
        Graphics g = result.getGraphics();
        List<Label> map_list=new ArrayList<Label>();
        
        try{
        Connection con=MyConnection.createConnection();
        PreparedStatement pmt=con.prepareStatement("select l.Id,l.project_id,l.layer,l.left as 'left',l.top as 'top',p.plot_survey_no,p.total_area,p.sold_area,p.nonsold_area,u.short_name from layer_info l "
                + "inner join plot p on l.layer=p.plot inner join unit u on u.Id=p.unit_id where l.project_id=? and l.project_id=p.project_id"); 
        pmt.setInt(1, pid);
        ResultSet rs=pmt.executeQuery();
        int i=0;
        
        //String img[]=new String[100];
        //BufferedImage bimg[]=new BufferedImage[100];
        
        while(rs.next())
        {
        
        Label l=new Label();
        //l.setOpacity(10);
        //l.setBorder(Border.EMPTY);
        l.setLayoutX(rs.getInt("left"));
        l.setLayoutY(rs.getInt("top")+(rs.getInt("top")*2));
        
        //l.setText("Plot "+(i+1));
        l.setText(rs.getString("plot_survey_no"));
        l.setStyle("-fx-text-fill:black;"
                + "-fx-font-weight: bold;");
        l.setWrapText(true);
        l.setRotate(-90);
        Label l2=new Label();
        l2.setLayoutX(rs.getInt("left"));
        l2.setLayoutY(rs.getInt("top")+(rs.getInt("top")*2));
        l2.setText(rs.getString("total_area")+" "+rs.getString("short_name"));
        l2.setStyle("-fx-text-fill:black;"
                + "-fx-font-weight: bold;");
        l2.setWrapText(true);
        l2.setRotate(-90);
        map_list.add(l);
        map_list.add(l2);
        //map_panel.getChildren().add(l);
        i++;
        }
        //ImageIO.write(result,"png",new File("C:\\Property\\"+pid+"\\temp files\\resulting.png")); 
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return map_list;
    }
    public void openPlot(int pid,int j,Image image)
    {
        try{
            
            //Animation
            
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PlotInfo.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            //TranslateTransition slideOut = new TranslateTransition(Duration.seconds(2), root1);
            //slideOut.setByX(250);
            //root1.setLayoutX(-100);
            //TranslateTransition slideOut = new TranslateTransition(Duration.seconds(1), ((Pane)root1.lookup("#main_pane")));
            //slideOut.setByX(200);
            //slideOut.play();
            TranslateTransition slideOut = new TranslateTransition(Duration.seconds(1), ((Pane)root1.lookup("#table_pane")));
            slideOut.setByX(-400);
            slideOut.play();
            TranslateTransition slideOut1 = new TranslateTransition(Duration.seconds(1), ((Pane)root1.lookup("#main_pane")));
            slideOut1.setByX(-300);
            slideOut1.play();
            ((Label)root1.lookup("#title_label")).setText("Plot "+j);
            ((ImageView)root1.lookup("#plot_map")).setImage(image);
            ((Pane)root1.lookup("#hissa_pane")).setVisible(false);
            ((Label)root1.lookup("#plotinfo_id")).setText(""+pid);
            ((Label)root1.lookup("#plotinfo_plotid")).setText(""+j);
          
            ResultSet rs=query.getPlotDetails(j,pid);
            while(rs.next())
            {
                System.out.println("In Plot....");
                ((Label)root1.lookup("#survey_no")).setText(rs.getString("plot_survey_no"));
                ((Button)root1.lookup("#sold_area")).setText("Total Sold Area:"+rs.getString("sold_area"));
                ((Button)root1.lookup("#nonsold_area")).setText("Remaining Area:"+rs.getString("nonsold_area"));
                ((Button)root1.lookup("#sold_area")).setWrapText(true);
                ((Button)root1.lookup("#nonsold_area")).setWrapText(true);
                ((TextField)root1.lookup("#total_area")).setText(rs.getString("total_area"));
                
                if(rs.getString("total_area").equals("0"))
                {
                    ((Button)root1.lookup("#btn")).setText("Save");
                    ((ComboBox)root1.lookup("#plot_unit")).setVisible(true);
                }
                else
                {
                    ((Button)root1.lookup("#btn")).setText("Edit");
                    ((ComboBox)root1.lookup("#plot_unit")).setVisible(false);
                    ((Label)root1.lookup("#unit")).setText(rs.getString("short_name"));
                }
                
            }
            
             rs=query.getUnitList();
            while(rs.next())
            {
                ((ComboBox)root1.lookup("#plot_unit")).getItems().addAll(
                    rs.getString("unit_name"));
                ((ComboBox)root1.lookup("#punit")).getItems().addAll(
                    rs.getString("unit_name"));
            }
            TableView ht=((TableView)root1.lookup("#hissa_table"));
            int plot_id=0;
            ResultSet rs1=query.getPlotIdByNo(String.valueOf(pid), String.valueOf(j));
                while(rs1.next())
                 {
                     plot_id=rs1.getInt("Id");
                 }
            buildData(ht,pid,plot_id);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Plot");
            stage.setScene(new Scene(root1));  
            //stage.setScene(new Scene(g, 800, 600));
            stage.show();
            
          }
         catch (Exception ex) {
             ex.printStackTrace();
        }
    }
   @FXML
   public void openHissaWindow(ActionEvent evt) throws SQLException
   {
       showHissaTable(evt);
       hpane.setVisible(true);
       int k=query.getHissaNo(plot_survey.getText());
            
                hissa_no.setText(""+k);
       
   }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }    
   public Group plotting(String image,ImageView iv,Stage root) {

       
        BufferedImage[] imgs = getImages(image);
        Group grp = new Group();
        //set contentpane layout for grid
        
        final GridPane pane = new GridPane();
        for (int i = 0; i < 10; i++) {
            //final ImageView imageView = new ImageView(new Image("..."));
            //pane.getChildren().add(iv);
            
            GridPane.setConstraints(iv, i%5, i/5);
        Label[] labels = new Label[imgs.length];
         
        //create JLabels with split images and add to frame contentPane
        for (int k = 0; k < imgs.length; k++) {
            Label nw = labels[k];
            
                    
            pane.getChildren().add(labels[k]);
            System.out.println(imgs[k]);
                    

                    
        }
        grp.getChildren().add(pane);
    }
        return grp;
   }

    private BufferedImage[] getImages(String image) {
         final int rows = 3; 
         final int cols = 3;
         final int chunks = rows * cols;
         final int SPACING = 10;//spacing between split images

        File file = new File(image); // I have bear.jpg in my working directory
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException ex) {
            //Logger.getLogger(.class.getName()).log(Level.SEVERE, null, ex);
        }
        BufferedImage image1 = null;
        try {
            image1 = ImageIO.read(fis); //reading the image file
        } catch (IOException ex) {
            //Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
        int chunkWidth = image1.getWidth() / cols; // determines the chunk width and height
        int chunkHeight = image1.getHeight() / rows;
        int count = 0;
        BufferedImage imgs[] = new BufferedImage[chunks]; //Image array to hold image chunks
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {
                //Initialize the image array with image chunks
                imgs[count] = new BufferedImage(chunkWidth, chunkHeight, image1.getType());

                // draws the image chunk
                Graphics2D gr = imgs[count++].createGraphics();
                gr.drawImage(image1, 0, 0, chunkWidth, chunkHeight, chunkWidth * y, chunkHeight * x, chunkWidth * y + chunkWidth, chunkHeight * x + chunkHeight, null);
                gr.dispose();
            }
        }
        return imgs;
    } 
    @FXML
    private void addHissa(ActionEvent event) throws SQLException  {
        hbean.setProject_id(query.getProjectId(plot_survey.getText()));
        hbean.setPlot_id(query.getPlotId(plot_survey.getText()));
        hbean.setSurvey_no(plot_survey.getText());
        hbean.setTotal_area(plot_area.getText());
        hbean.setArea_purchased(area_purchased.getText());
        hbean.setTitle_owner(title_owner.getText());
        hbean.setUnit_id(query.getUnitId(punit.getSelectionModel().getSelectedItem().toString()));
        hbean.setHissa_survey(plot_survey.getText()+"/"+hissa_no.getText());
        hbean.setHissa_no(hissa_no.getText());
        try{
            
            Double nonsoldarea=0.00;
            ResultSet rs=query.getPlotDetails(plot_survey.getText());
            while(rs.next())
            {
               
                nonsoldarea=rs.getDouble("nonsold_area");
             
            }
     if(Double.parseDouble(hbean.getArea_purchased())<=nonsoldarea)
     { 
         if(save_btn.getText().equalsIgnoreCase("save"))
            {
     
        if(query.addHissa(hbean))
        {
           
           Alert alert = new Alert(Alert.AlertType.INFORMATION);
           alert.setTitle("Information");
           //alert.setHeaderText("Look, an Information Dialog");
           alert.setContentText("Plotting done!!");
           alert.showAndWait();
        }else{
           Alert alert = new Alert(Alert.AlertType.INFORMATION);
           alert.setTitle("Error");
           alert.setHeaderText("Look, an Error Dialog");
           alert.setContentText("Error has occured!!");
           alert.showAndWait();
              }
            }
            else if(save_btn.getText().equalsIgnoreCase("edit"))
            {
                
            }
           area_purchased.setText("");
           title_owner.setText("");
           hissa_no.setText("");
           punit.getSelectionModel().select(0);
           hpane.setVisible(false);
           rs=query.getPlotDetails(plot_survey.getText());
            while(rs.next())
            {
                //plot_area.setText(rs.getString("total_area"));
                soldarea.setText("Total Sold Area:"+rs.getString("sold_area"));
                nonsold.setText("Remaining Area:"+rs.getString("nonsold_area"));
                            
            }
            buildData(hissa_table,hbean.getProject_id() , hbean.getPlot_id());
            showHissaTable(event);
           
      }else{
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("Error");
           alert.setHeaderText("Message");
           alert.setContentText("Please Enter valid Area");
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
    @FXML
    public void closeHissa()
    {
           area_purchased.setText("");
           title_owner.setText("");
           hissa_no.setText("");
           punit.getSelectionModel().select(0);
           hpane.setVisible(false);
    }
    public TableView buildData(String table){

	          Connection c ;
                  TableView docTable=new TableView();
                  docTable.getColumns().clear();
                  //data.clear();
	          data = FXCollections.observableArrayList();

	          try{

	            c = MyConnection.createConnection();

	            //SQL FOR SELECTING ALL OF CUSTOMER

	            String SQL = "select Id,document from "+table ;
                
	            //ResultSet
                    PreparedStatement pmt=c.prepareStatement(SQL);
                    //pmt.setInt(1, pid);
                    //pmt.setInt(2, plot);
	            ResultSet rs = pmt.executeQuery();

	 

	            /**********************************

	             * TABLE COLUMN ADDED DYNAMICALLY *

	             **********************************/

	            for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++){

	                //We are using non property style for making dynamic table

	                final int j = i;               

	                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
                        
                        col.prefWidthProperty().bind(docTable.widthProperty().multiply(0.50));
                        col.setResizable(false);
	                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){                   
                        
	                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {                                                                                             

	                        return new SimpleStringProperty(param.getValue().get(j).toString());                       

	                    }                   

	                });
                         
	                
                    
	                docTable.getColumns().addAll(col);
                        
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
                                  
	            docTable.setItems(data);
                    
	          }catch(Exception e){

	              e.printStackTrace();

	              System.out.println("Error on Building Data");            

	          }
                return docTable;
	      }
    public ObservableList buildData(TableView hissa_table,int pid,int plot){

	          Connection c ;
                  hissa_table.getColumns().clear();
                  //data.clear();
	          data = FXCollections.observableArrayList();

	          try{

	            c = MyConnection.createConnection();

	            //SQL FOR SELECTING ALL OF CUSTOMER
                    
	            String SQL = "select plot_id,hissa_survey,total_area,area_purchased,title_owner from plot_hissa_details where project_id=? and plot_id=?";
                    //System.out.println("pid=="+pid+"  plot id=="+plot);
	            //ResultSet
                    PreparedStatement pmt=c.prepareStatement(SQL);
                    pmt.setInt(1, pid);
                    pmt.setInt(2, plot);
	            ResultSet rs = pmt.executeQuery();

	 

	            /**********************************

	             * TABLE COLUMN ADDED DYNAMICALLY *

	             **********************************/

	            for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++){

	                //We are using non property style for making dynamic table

	                final int j = i;               

	                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
                        
                        col.prefWidthProperty().bind(hissa_table.widthProperty().multiply(0.20));
                        col.setResizable(false);
	                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){                   
                        
	                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {                                                                                             

	                        return new SimpleStringProperty(param.getValue().get(j).toString());                       

	                    }                   

	                });
                         hissa_table.getColumns().addAll(col);
                        
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
                                  
	            hissa_table.setItems(data);
                    
	          }catch(Exception e){

	              e.printStackTrace();

	              System.out.println("Error on Building Data");            

	          }
                return data;
	      }
    @FXML
    public void detectRightClick(MouseEvent e)
    {
        if (e.getButton() == MouseButton.SECONDARY) {
            
            System.out.println("Right button clicked");
        }
    }
    @FXML
    public void openAddHistory(ActionEvent evt)
    {
       if(hissa_table.getSelectionModel().getSelectedItem() != null) 
            {
                loadHistory();
            }
        else{
             Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setTitle("Error");
             alert.setHeaderText("Error");
             alert.setContentText("No data Found!!");
             alert.showAndWait();
       }
    } 
    @FXML
    public void openViewHistory(ActionEvent evt)
    {
       if(hissa_table.getSelectionModel().getSelectedItem() != null) 
            {
                loadViewHistory();
            }
        else{
             Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setTitle("Error");
             alert.setHeaderText("Error");
             alert.setContentText("No data Found!!");
             alert.showAndWait();
       }
    } 
    /*public void handleUploadEvent(Pane pane,int x1,int y,String table,boolean flag,int hissa_id)
    {
        if(!flag)
        {
            flag=true;
            final boolean f=flag;
            Button add=new Button();
            add.setText("Add More");
            add.setLayoutX(x1+20);
            add.setLayoutY(y+220);
            add.setOnAction(new EventHandler<ActionEvent>() {
                         @Override public void handle(ActionEvent e) {
                            FileChooser chooser = new FileChooser();
            chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files",
                "*.bmp", "*.pdf", "*.jpg", "*.gif","*.png"));
            chooser.setTitle("Open File");
            File file = chooser.showOpenDialog(new Stage());
            System.out.println("File name="+file.getPath());
                             //String table="saledeed_temp";
            query.uploadTemp(table,file.getPath(),f,hissa_id);
            if(file.getName().contains(".pdf"))
            {
               m_PDFViewer = new PDFViewer();
               m_PDFViewer.setToolBarVisible(false);
               m_PDFViewer.setSplitVisible(false);
               try{
                   //query.getPDFData(table)
                   m_PDFViewer.loadPDF(file.getPath());
                  }catch(Exception exp)
               {
                                 
               }
              m_PDFViewer.setLayoutX(x1);
              m_PDFViewer.setLayoutY(y);
              m_PDFViewer.setMaxHeight(200);
              m_PDFViewer.setMaxWidth(150);
              pane.getChildren().add(m_PDFViewer);
            }
            else
            {
                Image icon=new Image(file.toURI().toString());
                ImageView iv=new ImageView(icon);
                iv.setLayoutX(x1);
                iv.setLayoutY(y);
                iv.setFitHeight(200);
                iv.setFitWidth(150);
                pane.getChildren().add(iv);
                //logo.setImage(icon); 
            }
                         }
                        });
            pane.getChildren().add(add);
        }
             flag=false;
            FileChooser chooser = new FileChooser();
            chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files",
                "*.bmp", "*.pdf", "*.jpg", "*.gif","*.png"));
            chooser.setTitle("Open File");
            File file = chooser.showOpenDialog(new Stage());
            System.out.println("File name="+file.getPath());
            query.uploadTemp(table,file.getPath(),flag,hissa_id);
            if(file.getName().contains(".pdf"))
            {
               m_PDFViewer = new PDFViewer();
               m_PDFViewer.setToolBarVisible(false);
               m_PDFViewer.setSplitVisible(false);
               try{
                   //query.getPDFData(table)
                   m_PDFViewer.loadPDF(file.getPath());
                  }catch(Exception exp)
               {
                                 
               }
              m_PDFViewer.setLayoutX(x1);
              m_PDFViewer.setLayoutY(y);
              m_PDFViewer.setMaxHeight(200);
              m_PDFViewer.setMaxWidth(150);
              pane.getChildren().add(m_PDFViewer);
            }
            else
            {
                Image icon=new Image(file.toURI().toString());
                ImageView iv=new ImageView(icon);
                iv.setLayoutX(x1);
                iv.setLayoutY(y);
                iv.setFitHeight(200);
                iv.setFitWidth(150);
                pane.getChildren().add(iv);
                //logo.setImage(icon); 
            }
        
    }
    public void handleAddMoreEvent(boolean flag)
    {
        
    }*/
    //Translate transition effect
   @FXML
   public void showHissaTable(ActionEvent evt)
   {
       if(isHidden)
       {
            TranslateTransition slideOut = new TranslateTransition(Duration.seconds(1), table_pane);
            slideOut.setByX(-500);
            slideOut.play();
            TranslateTransition slideOut1 = new TranslateTransition(Duration.seconds(1), main_pane);
            slideOut1.setByX(-500);
            slideOut1.play();
            isHidden=false;
            show_btn.setText("Hide");
       }
       else{
           TranslateTransition slideOut = new TranslateTransition(Duration.seconds(1), table_pane);
            slideOut.setByX(500);
            slideOut.play();
            TranslateTransition slideOut1 = new TranslateTransition(Duration.seconds(1), main_pane);
            slideOut1.setByX(500);
            slideOut1.play();
            isHidden=true;
            show_btn.setText("Show");
       }
   }
   public void loadHistory()
   {
       try{
                     String plot=plot_info_plotid.getText();
                     String project=plotinfo_id.getText();
                     int i=hissa_table.getSelectionModel().getSelectedIndex();
                     TableViewSelectionModel selectionModel = hissa_table.getSelectionModel();
                     ObservableList selectedCells = hissa_table.getColumns();
                     TableColumn tablePosition =   (TableColumn) selectedCells.get(1);//1 is 2nd column
                     Object val = tablePosition.getCellData(i);
                     System.out.println("Hissa Survey No is :" + val);
                     String h[]=val.toString().split("/");
                     String hissa=h[2];
                     Stage stage1 = (Stage) plot_area_save.getScene().getWindow();
                     stage1.close();
                     AddHistoryPage(project,plot,hissa,val.toString());
        }catch(Exception e)
        {
            e.printStackTrace();
        }
   }
   public void AddHistoryPage(String project,String plot,String hissa,String val)
   {
       try{
       FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddHistory.fxml"));
                     
                     Parent root1 = (Parent) fxmlLoader.load();
                     Stage stage = new Stage();
                     Scene scene = new Scene(root1);
                     ((Label)root1.lookup("#addhistory_id")).setText(project);
                     ((Label)root1.lookup("#addhistory_plotid")).setText(plot);
                     ((Label)root1.lookup("#addhistory_hissaid")).setText(hissa);
                     int hid=query.getHissaId(val);
                     ((Label)root1.lookup("#hissa_id")).setText(String.valueOf(query.getHissaId(val)));
                     ResultSet rs=query.getOwner(String.valueOf(query.getHissaId(val)));
                     Button edit[]=new Button[200];
                     Button delete[]=new Button[200];
                     int x=20,y=30;
                             double yc=0.00,yb=0.00;
                     Pane spane=new Pane();
                     Pane snew=new Pane();
                     Pane snew1=new Pane();
                     //spane.setStyle("-fx-background-color:rgba(66,74,93,1.0);");
                     Label title=new Label("Owner Name");
                     title.setLayoutX(x+50);
                     title.setLayoutY(y-10);
                     title.setStyle("-fx-font-weight: bold;"
                             + "-fx-font-size: 13pt;");
                     spane.getChildren().add(title);
                     title=new Label("Duration");
                     title.setLayoutX(x+220);
                     title.setLayoutY(y-10);
                     title.setStyle("-fx-font-weight: bold;"
                             + "-fx-font-size: 13pt;");
                     spane.getChildren().add(title);
                     title=new Label("Mutation No");
                     title.setLayoutX(x+360);
                     title.setLayoutY(y-10);
                     title.setStyle("-fx-font-weight: bold;"
                             + "-fx-font-size: 13pt;");
                     spane.getChildren().add(title);
                     title=new Label("Saleded No");
                     title.setLayoutX(x+480);
                     title.setLayoutY(y-10);
                     //title.setWrapText(true);
                     spane.getChildren().add(title);
                     title.setStyle("-fx-font-weight: bold;"
                             + "-fx-font-size: 13pt;");
                     int i=0;
                     while(rs.next())
                     {
                         int y1=y;
                           Label id=new Label();
                           id.setText(rs.getString("Id"));
                           id.setLayoutX(x);
                           id.setLayoutY(y1);
                           //x+=20;
                           Pane pane=createDynamicHistory(rs, x+20, y1, hid,id.getText(),val.toString());
                           
                           edit[i]=new Button();
                           edit[i].setId(rs.getString("Id"));
                           edit[i].setText("Edit");
                           edit[i].setLayoutX(x+750);
                           edit[i].setLayoutY(y);
                           delete[i]=new Button();
                           delete[i].setId(rs.getString("Id"));
                           delete[i].setText("Delete");
                           delete[i].setLayoutX(x+805);
                           delete[i].setLayoutY(y);
                          // bp.getChildren().addAll(edit);
                           //pane.getChildren().addAll(id);
                         snew.getChildren().add(pane);
                         y+=70;
                        yc=pane.getLayoutY()+50;
                        i++;
                     }
                     
                     rs=query.getParentList(String.valueOf(query.getHissaId(val.toString())));
                      while(rs.next())
                     {
                         int y1=y;
                           Label id=new Label();
                           id.setText(rs.getString("owner_id"));
                           id.setLayoutX(x);
                           id.setLayoutY(y1);
                           //x+=20;
                           Pane pane=createDynamicHistory(rs, x+20, y1, hid,id.getText(),val.toString());
                           pane.getChildren().add(id);
                         //if(rs.last()){
                           //pane.setStyle("-fx-border-color:white;");
                         //}
                         snew1.getChildren().add(pane);
                         y+=70;
                        yc=pane.getLayoutY()+50;
                     }
                      Pane bp=new Pane();
                     int by=60;
                     for(int j=0;j<edit.length;j++)
                     {
                         if(edit[j]!=null)
                         {
                             String oid=edit[j].getId();
                         System.out.println("edit["+j+"]:"+edit[j]);
                         edit[j].setLayoutY(by);
                         bp.getChildren().add(edit[j]);
                         edit[j].setOnAction(new EventHandler<ActionEvent>() {
                            @Override public void handle(ActionEvent e) {
                                close(e);
                                System.out.println("hid before edit  "+hid);
                                openEditHistory(oid,hid,hissa);
                            }
                         });
                         }
                         by+=140;
                     }
                     by=60;
                     for(int j=0;j<delete.length;j++)
                     {
                         if(delete[j]!=null)
                         {
                             String oid=delete[j].getId();
                             
                         System.out.println("edit["+j+"]:"+delete[j]);
                         delete[j].setLayoutY(by);
                         bp.getChildren().add(delete[j]);
                         delete[j].setOnAction(new EventHandler<ActionEvent>() {
                            @Override public void handle(ActionEvent e) {
                                System.out.println("oid=="+oid);
                                Alert alert = new Alert(AlertType.CONFIRMATION);
                                alert.setTitle("Confirmation Dialog");
                                alert.setHeaderText("Look, a Confirmation Dialog");
                                alert.setContentText("Are you sure want to delete?");

                            Optional<ButtonType> result = alert.showAndWait();
                                if (result.get() == ButtonType.OK){
                                    
                                    deleteOwner(oid,hid);
                             } else {
                                    System.out.println("cancel..");
                             }
                            }
                         });
                         }
                         by+=140;
                     }
                     spane.getChildren().add(bp);
                     double my=yc+y;
                     double yc1=yc;
                     System.out.println("my="+my);
                     System.out.println("y="+y);
                     Pane pnew=new Pane();
                      Button addMore=new Button("Add");
                           addMore.setLayoutX(x+800);
                           addMore.setLayoutY(5);
                           addMore.setOnAction(new EventHandler<ActionEvent>() {
                            @Override public void handle(ActionEvent e) {
                            //label.setText("Accepted");
                             try{
                                 
                                 Pane p=createOwnerList(x, my, val,hid,yc1);
                                 //p.setLayoutX(x);
                                 //p.setLayoutY(my);
                                 //p.setStyle("-fx-border-color:red;");
                             pnew.getChildren().addAll(p);
                             }catch(Exception ex){}
                         }
                        });
                           spane.getChildren().addAll(snew,snew1,pnew);
                        ((Pane)root1.lookup("#title_pane")).getChildren().add(addMore);    
                         
                     ((ScrollPane)root1.lookup("#hscroll")).setContent(spane);
                     //stage.setX(300);
                     //stage.setY(200);
                     
                     stage.initStyle(StageStyle.UNDECORATED);
                     stage.setScene(scene);
                     stage.show();
       }catch(Exception e){
       e.printStackTrace();}
   }
    /*public void saveOwnerDetails(Label name,DatePicker f,DatePicker t,TextField mno,TextField sno,
            boolean flag,int hissa_id,Label parent) throws SQLException
    {
        if(f.getValue()==null||t.getValue()==null)
        {
             Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setTitle("Error");
             alert.setHeaderText("Error");
             alert.setContentText("Please Select date first!!");
             alert.showAndWait();
        }
        else if(mno.getText().equals("")||sno.getText().equals(""))
        {
             Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setTitle("Error");
             alert.setHeaderText("Error");
             alert.setContentText("Registration No are complusory!!");
             alert.showAndWait();
        }
       
        
        else
        {
            Date date = java.sql.Date.valueOf(f.getValue());
            Date tdate = java.sql.Date.valueOf(t.getValue());
            obean.setOwner_name(name.getText());
            obean.setFrom_date(date);
            obean.setTo_date(tdate);
            obean.setMutation_no(mno.getText());
            obean.setSaledeed_no(sno.getText());
            obean.setHissa_id(hissa_id);
           
            if(query.saveOwnerDetails(obean))
            {
               Alert alert = new Alert(Alert.AlertType.INFORMATION);
               alert.setTitle("Message");
               //alert.setHeaderText("Error");
               alert.setContentText("Owner saved successfully!!");
               alert.showAndWait(); 
               
               openProjectMap(Integer.parseInt(addhistory_id.getText()));
            }
        }
    }*/
    public void saveOwnerDetails(TextField owner,DatePicker f,DatePicker t,TextField mno,TextField sno,
            boolean flag,int hissa_id,String hs) throws SQLException
    {
        if(owner.getText().equals(""))
        {
             Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setTitle("Error");
             alert.setHeaderText("Error");
             alert.setContentText("Owner name Should not be empty!!");
             alert.showAndWait();
        }
        else if(f.getValue()==null||t.getValue()==null)
        {
             Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setTitle("Error");
             alert.setHeaderText("Error");
             alert.setContentText("Please Select date first!!");
             alert.showAndWait();
        }
        else if(mno.getText().equals("")||sno.getText().equals(""))
        {
             Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setTitle("Error");
             alert.setHeaderText("Error");
             alert.setContentText("Registration No are complusory!!");
             alert.showAndWait();
        }
        else if(!issaved)
        {
             Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setTitle("Error");
             alert.setHeaderText("Error");
             alert.setContentText("Please select purchaser!!");
             alert.showAndWait();
        }
        /*else if(purchaser.getSelectionModel().getSelectedItem()==null)
        {
             Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setTitle("Error");
             alert.setHeaderText("Error");
             alert.setContentText("Please select purchaser!!");
             alert.showAndWait();
        }
        /*else if(!(uploadM.isDisable()||uploadS.isDisable()))
        {
             Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setTitle("Error");
             alert.setHeaderText("Error");
             alert.setContentText("Please upload documents!!");
             alert.showAndWait();
        }*/
        else
        {
            Date date = java.sql.Date.valueOf(f.getValue());
            Date tdate = java.sql.Date.valueOf(t.getValue());
            obean.setOwner_name(owner.getText());
            obean.setFrom_date(date);
            obean.setTo_date(tdate);
            obean.setMutation_no(mno.getText());
            obean.setSaledeed_no(sno.getText());
            obean.setHissa_id(hissa_id);
            obean.setHissa_survey(hs);
            /*obean.setPurchaser_name(purchaser.getSelectionModel().getSelectedItem().toString());*/
            if(query.saveOwner(obean))
            {
               Alert alert = new Alert(Alert.AlertType.INFORMATION);
               alert.setTitle("Message");
               //alert.setHeaderText("Error");
               alert.setContentText("Owner saved successfully!!");
               alert.showAndWait(); 
               final Node source = (Node) owner;
               final Stage stage = (Stage) source.getScene().getWindow();
               stage.close(); 
                                 System.out.println("after closing history"+source);
                                 refreshAddHistory(hs,String.valueOf(hissa_id));
               //openProjectMap(Integer.parseInt(addhistory_id.getText()));
            }
        }
    }
    @FXML
    public void openJMapViewer(int id) 
    {
        
        if(Mapconnection.checkConnection())
        {
           initAndShowGUI(id);
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Connection Lost!!");
            alert.showAndWait(); 
        }
        
    }
     private  void initAndShowGUI(int id) {
        // This method is invoked on the EDT thread
         //int i=Integer.parseInt(builder_id.getText());
        CreatingMap frame=new CreatingMap(id);
        final JFXPanel fxPanel = new JFXPanel();
        //frame.add(fxPanel);
        ///frame.setUndecorated(true);
        
        //frame.setSize(100, 100);
        frame.setVisible(true);
        
        //frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                initFX(fxPanel);
            }
       });
    }

    private static void initFX(JFXPanel fxPanel) {
        // This method is invoked on the JavaFX thread
        Scene scene = createScene();
        fxPanel.setScene(scene);
    }

    private static Scene createScene() {
        Group  root  =  new  Group();
        Scene  scene  =  new  Scene(root, Color.ALICEBLUE);
        Text  text  =  new  Text();
        
        text.setX(40);
        text.setY(20);
        text.setFont(new Font(25));
        text.setText("Welcome JavaFX!");

        root.getChildren().add(text);

        return (scene);
    }
    public Pane createDynamicHistory(ResultSet rs,int x,int y,int hid,String oid,String hs) throws SQLException
    {
                         Pane pane=new Pane();
                         int x1=x,y1=y;
                         
                         Label b=new Label();
                         b.setText(rs.getString("owner_name"));
                         b.setLayoutX(x1);
                         b.setLayoutY(y1);
                         b.setMaxWidth(100);
                         b.setWrapText(true);
                        
                         x1+=120;
                         Label l=new Label("From");
                         l.setLayoutX(x1);
                         l.setLayoutY(y1);
                         Label d=new Label();
                         d.setLayoutX(x1+50);
                         d.setLayoutY(y1);
                         d.setMaxWidth(100);
                        
                         pane.getChildren().addAll(b,l,d);
                         l=new Label("To");
                         l.setLayoutX(x1);
                         l.setLayoutY(y1+40);
                         x1+=50;
                         Label d1=new Label();
                         d1.setLayoutX(x1);
                         d1.setLayoutY(y1+40);
                         d1.setMaxWidth(100);
                         
                         pane.getChildren().addAll(l,d1);
                         //x1+=120;
                         /*l=new Label("Purchaser");
                         l.setLayoutX(x1+120);
                         l.setLayoutY(y1+40);
                         Button purchaser=new Button("Purchaser List");
                         purchaser.setLayoutX(x1+240);
                         purchaser.setLayoutY(y1+40);
                         purchaser.setOnAction(new EventHandler<ActionEvent>() {
                         @Override public void handle(ActionEvent e) {
                             //openPurchaser(hid,hs,oid);
                         }
                         });
                        */
                         /*String parent=query.getParentNameById(rs.getInt("Id"));
                         Label p=new Label(parent);
                         p.setLayoutX(x1+350);
                         p.setLayoutY(y1+40);
                         pane.getChildren().addAll(p);*/
                         x1+=150;
                         Label t=new Label();
                         t.setLayoutX(x1);
                         t.setLayoutY(y1);
                         t.setMaxWidth(100);
                         //t.setPromptText("Mutation No");
                         pane.getChildren().addAll(t);
                        
                         x1+=120;
                         Label t1=new Label();
                         t1.setLayoutX(x1);
                         t1.setLayoutY(y1);
                         t1.setMaxWidth(100);
                         //t1.setPromptText("Saledeed No");
                         pane.getChildren().addAll(t1);
                         x1+=120;
                         Button uploadM=new Button();
                         uploadM.setText("Documents");
                         uploadM.setLayoutX(x1);
                         uploadM.setLayoutY(y);
                        
                         if(rs.getDate("from_date")!=null&&rs.getDate("to_date")!=null)
                         {
                             d.setText(rs.getString("from_date"));
                             d1.setText(rs.getString("to_date"));
                             t.setText(rs.getString("mutation_reg_no"));
                             t1.setText(rs.getString("saledeed_reg_no"));
                          
                         }
                          Boolean flag=false;
                          int mx=x1;
                         uploadM.setOnAction(new EventHandler<ActionEvent>() {
                         @Override public void handle(ActionEvent e) {
                            
                             try{
                                 System.out.println("hid in upload"+hid);
                                 System.out.println("owner in upload"+oid);
                                 openUploadDocument(hid,oid);
                             }catch(Exception ex){
                             ex.printStackTrace();
                             }
                         }
                        });
                         x1+=120;
                         //pane.getChildren().add(uploadM);
                        
                         Button save=new Button();
                         save.setText("Save/Edit");
                         save.setLayoutX(x1);
                         save.setLayoutY(y1);
                         save.setOnAction(new EventHandler<ActionEvent>() {
                         @Override public void handle(ActionEvent e) {
                            //label.setText("Accepted");
                             try{
                            //saveOwnerDetails(b,d,d1,t,t1,flag,hid,p);
                             }catch(Exception ex){}
                          }
                        });
                         //pane.getChildren().add(save);
                         
                         pane.setLayoutX(x);
                         pane.setLayoutY(y);
                        
                         return pane;
}
    public Pane createOwnerList(int x,double y,String hs,int hid,double yc) throws SQLException
    {
        Pane pane=new Pane();
        Double y2=yc;
        int x1=x,y1=y2.intValue();
        Pane pane2=createDynamicHistory(x1,y1,hid,hs);
        pane2.setLayoutY(y1);
        pane.getChildren().add(pane2);
        
        CheckBox same=new CheckBox();
                                same.setText("Same as");
                                same.setLayoutX(x+50);
                                same.setLayoutY(y); 
                              //System.out.println("pane2.getLayoutY()="+pane2.getLayoutY());
                                //x+=100;
                         ComboBox hissa=new ComboBox();
                         hissa.setLayoutX(x+170);
                         hissa.setLayoutY(y);
                         String plot[]=hs.split("/");
                         String p=plot[0]+"/"+plot[1];
                         ResultSet hrs=query.getHissaListBySurveyNo(p,hs);
                         
                         while(hrs.next())
                         {
                             hissa.getItems().addAll(hrs.getString("Id"));
                         }
                         pane.getChildren().addAll(same,hissa);
                         //x+=120;
                         Label from =new Label("From");
                         from.setLayoutX(x+220);
                         from.setLayoutY(y);
                         ComboBox o=new ComboBox();
                          //x+=170;
                         o.setLayoutX(x+300);
                         o.setLayoutY(y);
                         o.setMaxWidth(100);
                          //x+=o.getLayoutX()+100;
                         Button s=new Button("Save");
                         s.setLayoutX(x+550);
                         s.setLayoutY(y);
                         
                         //String hs=hissa.getSelectionModel().getSelectedItem().toString();
                        
                         pane.getChildren().addAll(from,o,s);
                         hissa.setVisible(false);
                         from.setVisible(false);
                         o.setVisible(false);
                         s.setVisible(false);
                          //boolean flag=null;
                         
                         
                         hissa.setOnAction(new EventHandler<ActionEvent>() {
                         @Override public void handle(ActionEvent e) {
                             //handleOwnerAction(hissa,x,y,pane);
                             o.getItems().clear();
                             String hs=hissa.getSelectionModel().getSelectedItem().toString();
                        try{
                           ResultSet rs=query.getOwner(hs);
                           while(rs.next())
                          {
                             o.getItems().addAll(rs.getString("owner_name"));
                          }
                        }catch(Exception ex){}
                         }
                        });
                         
                         same.setOnAction(new EventHandler<ActionEvent>() {
                         @Override public void handle(ActionEvent e) {
                             boolean f=false;
                            if(same.isSelected())
                            {
                               hissa.setVisible(true);
                               from.setVisible(true);
                               o.setVisible(true);
                               s.setVisible(true);
                               isSame=new Boolean(true);
                               pane2.setVisible(false);
                               f=true;
                               //pane.getChildren().add(pane2);  
                            }
                            else
                            {
                               hissa.setVisible(false);
                               from.setVisible(false);
                               o.setVisible(false);
                               s.setVisible(false); 
                               o.getSelectionModel().clearSelection();
                               hissa.getSelectionModel().clearSelection();
                               
                               pane2.setVisible(true);
                               
                               isSame=new Boolean(false);
                               
                            } 
                          //flag=f; 
                         }
                        });
                         s.setOnAction(new EventHandler<ActionEvent>() {
                         @Override public void handle(ActionEvent e) {
                             try{
                             //handleOwnerAction(hissa,x,y,pane);
                             obean.setHissa_id(Integer.parseInt(hissa.getSelectionModel().getSelectedItem().toString()));
                             obean.setOwner_name(o.getSelectionModel().getSelectedItem().toString());
                             int oid=query.getOwnerIdByName(obean.getOwner_name());
                             query.saveFromOwner(oid,obean,hid,hs);
                             query.saveSameHistory(oid,obean,hid,hs);
                             }catch(Exception ex)
                             {}
                         }
                        });
                         
                         return pane;
                         //y1+=40;
    }
    public Pane createDynamicHistory(int x,int y,int hid,String hs) throws SQLException
    {
    Pane pane=new Pane();
    boolean f=false;
    int x1=x,y1=y;
                         //int i=40;
                         //Pane pane=new Pane();
                         
                         /*Pane p=createOwnerList(x1, y1, hs,hid);
                         pane.getChildren().add(p);
                         ObservableList<Node> node=p.getChildren();
                         for(int i=0;i<node.size();i++)
                         {
                             if(node instanceof CheckBox)
                             {
                                 CheckBox c=(CheckBox) node.get(i);
                                 if(c.isSelected())
                                     f=true;
                                 else 
                                     f=false;
                             }
                         }
                         System.out.println("is same history"+f);
                         if(!f){
                         y1+=60;*/
                         Label id=new Label();
                         id.setText(""+query.getMaxOwnerId());
                         id.setLayoutX(x1);
                         id.setLayoutY(y1);
                         x1+=5;
                         //id.setMaxWidth(250);
                         TextField b=new TextField();
                         //b.setText("");
                         b.setLayoutX(x1);
                         b.setLayoutY(y1);
                         b.setMaxWidth(250);
                         //b.setStyle("-fx-font-weight: bold;"
                                 //+ "-fx-font-size: 15pt;");
                         //y1+=40;
                         x1+=170;
                         Label l=new Label("From");
                         l.setLayoutX(x1);
                         l.setLayoutY(y1);
                          //x1+=50;
                         
                             
                         DatePicker d=new DatePicker();
                         d.setLayoutX(x1+50);
                         d.setLayoutY(y1);
                         d.setMaxWidth(100);
                         
                          //y1+=40;
                         //x1+=100;
                         pane.getChildren().addAll(id,b,l,d);
                         l=new Label("To");
                         l.setLayoutX(x1);
                         l.setLayoutY(y1+40);
                         x1+=50;
                         DatePicker d1=new DatePicker();
                         d1.setLayoutX(x1);
                         d1.setLayoutY(y1+40);
                         d1.setMaxWidth(100);
                         
                         pane.getChildren().addAll(l,d1);
                         l=new Label("Purchaser");
                         l.setLayoutX(x1+120);
                         l.setLayoutY(y1+40);
                         //boolean flag=false;
                         Button purchaser=new Button("Add Purchaser");
                         purchaser.setLayoutX(x1+240);
                         purchaser.setLayoutY(y1+40);
                         purchaser.setOnAction(new EventHandler<ActionEvent>() {
                         @Override public void handle(ActionEvent e) {
                             openAddPurchaser(hid,hs,id.getText());
                         }
                         });
                         /*ComboBox purchaser=new ComboBox();
                         purchaser.setLayoutX(x1+240);
                         purchaser.setLayoutY(y1+40);
                         purchaser.setMaxWidth(300);
                         ResultSet rs=query.getOwner(String.valueOf(hid));
                         while(rs.next())
                         {
                         purchaser.getItems().addAll(
                         rs.getString("owner_name"));
                         }*/
                         pane.getChildren().addAll(l,purchaser);
                          //y1+=40;
                         //l=new Label("Mutation No");
                         //l.setLayoutX(x1-20);
                         //l.setLayoutY(y1);
                         //l.setMaxWidth(72);
                         //l.setWrapText(true);
                         x1+=120;
                         TextField t=new TextField();
                         t.setLayoutX(x1);
                         t.setLayoutY(y1);
                         t.setMaxWidth(120);
                         t.setPromptText("Mutation No");
                         pane.getChildren().addAll(t);
                         //y1+=40;
                         //l=new Label("Sale deed No");
                         //l.setLayoutX(x1-20);
                         //l.setLayoutY(y1);
                         //l.setMaxWidth(72);
                         //l.setWrapText(true);
                         x1+=140;
                         TextField t1=new TextField();
                         t1.setLayoutX(x1);
                         t1.setLayoutY(y1);
                         t1.setMaxWidth(120);
                         t1.setPromptText("Saledeed No");
                         pane.getChildren().addAll(t1);
                         x1+=140;
                         Button uploadM=new Button();
                         uploadM.setText("Upload");
                         uploadM.setLayoutX(x1);
                         uploadM.setLayoutY(y);
                         //uploadM.setMaxWidth(100);
                         //uploadM.setWrapText(true);
                         
                         /*if(rs.getDate("from_date")!=null&&rs.getDate("to_date")!=null)
                         {
                             d.setValue(rs.getDate("from_date").toLocalDate());
                             d1.setValue(rs.getDate("to_date").toLocalDate());
                             t.setText(rs.getString("mutation_reg_no"));
                             t1.setText(rs.getString("saledeed_reg_no"));
                             
                         }*/
                          Boolean flag=false;
                          int mx=x1;
                         uploadM.setOnAction(new EventHandler<ActionEvent>() {
                         @Override public void handle(ActionEvent e) {
                             try{
                                 System.out.println("hid in upload"+hid);
                             openUploadDocument(hid,id.getText());
                             }catch(Exception ex){
                             ex.printStackTrace();
                             }
                           
                         }
                        });
                         x1+=120;
                         pane.getChildren().add(uploadM);
                         
                         Button save=new Button();
                         save.setText("Save");
                         save.setLayoutX(x1);
                         save.setLayoutY(y);
                         save.setOnAction(new EventHandler<ActionEvent>() {
                         @Override public void handle(ActionEvent e) {
                            //label.setText("Accepted");
                             try{
                            saveOwnerDetails(b,d,d1,t,t1,flag,hid,hs);
                            
                             }catch(Exception ex){}
                          }
                        });
                         
                         pane.getChildren().add(save);
                         Button cancel=new Button("Cancel");
                         cancel.setLayoutX(x1+70);
                         cancel.setLayoutY(y);
                         cancel.setOnAction(new EventHandler<ActionEvent>() {
                         @Override public void handle(ActionEvent e) {
                             try{
                                 close(e);
                             refreshAddHistory(hs,String.valueOf(hid));
                             }catch(Exception ex)
                             {
                             ex.printStackTrace();}
                         }
                        });
                          pane.getChildren().addAll(cancel);
                         //}
                         pane.setLayoutX(x);
                         pane.setLayoutY(y);
                         
                         //spane.getChildren().add(pane);
                         return pane;
}
    public void refreshAddHistory(String hs,String hid)
    {
        try{
        ResultSet rs=query.getPlot(hs,hid);
        String project="",plot="";
        while(rs.next())
        {
            project=rs.getString("project_id");
            plot=rs.getString("plot_id");
        }
        
        AddHistoryPage(project, plot, hs, hs);
        }catch(Exception ex){}
                          
    }
    public void handleOwnerAction(ComboBox hissa,int x,int y,Pane pane)
    {
        if(hissa.getSelectionModel().getSelectedItem()!=null)
            {
                
            }
    }
    public void openUploadDocument(int hid) throws IOException
    {
            System.out.println("hiii"+hid);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("UploadDocument.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            ((Label)root1.lookup("#isClicked")).setText(String.valueOf(0));
            ((Label)root1.lookup("#upload_hissa_id")).setText(String.valueOf(hid));
            ((ScrollPane)root1.lookup("#fscroll")).setContent(loadDocuments("mutation_temp",hid));
            ((ScrollPane)root1.lookup("#sale_scroll")).setContent(loadDocuments("saledeed_temp",hid));
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Plot");
            stage.setScene(new Scene(root1));  
            //stage.setScene(new Scene(g, 800, 600));
            stage.show();
    }
    public void openUploadDocument(int hid,String oid) throws IOException, SQLException
    {
            System.out.println("hiii"+hid);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("UploadDocument.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            ((Label)root1.lookup("#isClicked")).setText(String.valueOf(0));
            ((Label)root1.lookup("#upload_owner_id")).setText(String.valueOf(oid));
            ((Label)root1.lookup("#upload_hissa_id")).setText(String.valueOf(hid));
            String mt="mutation_temp",st="saledeed_temp";
            if(!query.isTempEmpty(mt,Integer.parseInt(oid)))
            {
               ((ScrollPane)root1.lookup("#fscroll")).setContent(loadDocuments(mt,hid));
               
            }
            else{
               ((ScrollPane)root1.lookup("#fscroll")).setContent(loadDocuments("mutation",oid,hid)); 
            }
            if(!query.isTempEmpty(st,Integer.parseInt(oid)))
            {
              ((ScrollPane)root1.lookup("#sale_scroll")).setContent(loadDocuments(st,hid));   
            }
            else
            {
              ((ScrollPane)root1.lookup("#sale_scroll")).setContent(loadDocuments("sale_deed",oid,hid));
            }
            
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Plot");
            stage.setScene(new Scene(root1));  
            //stage.setScene(new Scene(g, 800, 600));
            stage.show();
    }
    @FXML
    public void fileUploader(ActionEvent evt) throws SQLException
    {
        String table;
        boolean f=false;
        
        if(isClicked.getText().equalsIgnoreCase("0"))
        {
            f=false;
            isClicked.setText("1");
            
            System.out.println("in if");
        }
        else{
            f=true;
            
            System.out.println("in else");
            //isClicked.setText("1");
        }
        if(doc_type.getSelectionModel().getSelectedItem().toString().equalsIgnoreCase("mutation"))
        {
            table="mutation_temp";
        }
        else{
            table="saledeed_temp";
        }
        
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Image Files",
            "*.bmp", "*.pdf", "*.jpg", "*.gif","*.png"));
        chooser.setTitle("Open File");
        File file = chooser.showOpenDialog(new Stage());
        System.out.println("File name="+file.getPath());
                             //String table="saledeed_temp";
            
            int hid=Integer.parseInt(upload_hissa_id.getText());
            int oid=Integer.parseInt(upload_owner_id.getText());
            query.uploadTemp(table,file.getPath(),f,hid,oid);
            
            if(table.equals("mutation_temp"))
            {
               Pane main=loadDocuments(table,hid);
               fscroll.setContent(main);
               fscroll.setPannable(true); 
            }
            else{
               Pane main=loadDocuments(table,hid);
               sale_scroll.setContent(main);
               sale_scroll.setPannable(true); 
            }
            
              
    }
    public Pane loadDocuments(String table,int hid)
    {
        Pane main=new Pane();
        int x=30,y=50;
            try{
            Connection con=MyConnection.createConnection();
            PreparedStatement pst=con.prepareStatement("select * from "+table);
            //pst.setInt(1, oid);
            ResultSet rs=pst.executeQuery();
            while(rs.next())
            {
                Pane p=new Pane();
                Button remove=new Button("Remove");
                remove.setLayoutX(x+200);
                remove.setLayoutY(y);
                Label did=new Label(rs.getString("Id"));
                did.setLayoutX(x+200);
                did.setLayoutY(y+50);
                remove.setOnAction(new EventHandler<ActionEvent>() {
                         @Override public void handle(ActionEvent e) {
                             try{
                            if(query.deleteDocument(table,did.getText()))
                                try{
                                    final Node source = (Node) e.getSource();
                                    final Stage stage = (Stage) source.getScene().getWindow();
                                    stage.close();
                                    openUploadDocument(hid);
                                }catch(Exception ex){
                                    ex.printStackTrace();
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    //alert.setTitle("Error");
                                    alert.setContentText(ex.getMessage());
                                    alert.showAndWait(); 
                                }
                             
                          }catch(Exception ex){}
                         }
                        });
                
                p.getChildren().addAll(did,remove);
            if(rs.getString("path").contains(".pdf"))
            {
               m_PDFViewer = new PDFViewer();
               m_PDFViewer.setToolBarVisible(false);
               m_PDFViewer.setSplitVisible(false);
               try{
                   //query.getPDFData(table)
                   m_PDFViewer.loadPDF(rs.getBinaryStream("doc"));
                  }catch(Exception exp)
               {
                                 
               }
              m_PDFViewer.setLayoutX(x);
              m_PDFViewer.setLayoutY(y);
              m_PDFViewer.setMaxHeight(200);
              m_PDFViewer.setMaxWidth(150);
              p.getChildren().add(m_PDFViewer);
            }
            else
            {
                //File fnew=new File(rs.getString("path"));
                Image icon=new Image(rs.getBinaryStream("doc"));
                ImageView iv=new ImageView(icon);
                iv.setLayoutX(x);
                iv.setLayoutY(y);
                iv.setFitHeight(200);
                iv.setFitWidth(150);
                p.getChildren().add(iv);
                //logo.setImage(icon); 
            }
            main.getChildren().add(p);
            y+=220;
            }
            
            }catch(Exception e){}
           
            //main.getChildren().add(p);
            //fscroll.setContent(main);
            //fscroll.setPannable(true);
           return main;           
    }
    public Pane loadDocuments(String table,String oid,int hid)
    {
        Pane main=new Pane();
        int x=30,y=50;
            try{
            Connection con=MyConnection.createConnection();
            PreparedStatement pst=con.prepareStatement("select * from "+table+" where owner_id=?");
            pst.setString(1, oid);
            ResultSet rs=pst.executeQuery();
            while(rs.next())
            {
                Pane p=new Pane();
                Button remove=new Button("Remove");
                remove.setLayoutX(x+200);
                remove.setLayoutY(y);
                Label did=new Label(rs.getString("doc_id"));
                did.setLayoutX(x+200);
                did.setLayoutY(y+50);
                remove.setOnAction(new EventHandler<ActionEvent>() {
                         @Override public void handle(ActionEvent e) {
                             try{
                            if(query.deleteDocument(table,oid,did.getText()))
                            {
                                try{
                                    final Node source = (Node) e.getSource();
                                    final Stage stage = (Stage) source.getScene().getWindow();
                                    stage.close();
                                    openUploadDocument(hid);
                                }catch(Exception ex){
                                    ex.printStackTrace();
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    //alert.setTitle("Error");
                                    alert.setContentText(ex.getMessage());
                                    alert.showAndWait(); 
                                }
                            }
                             }catch(Exception ex){}
                             
                          }
                        });
                
                p.getChildren().addAll(did,remove);
                
            if(rs.getString("path").contains(".pdf"))
            {
               m_PDFViewer = new PDFViewer();
               m_PDFViewer.setToolBarVisible(false);
               m_PDFViewer.setSplitVisible(false);
               try{
                   //query.getPDFData(table)
                   m_PDFViewer.loadPDF(rs.getBinaryStream("document"));
                  }catch(Exception ex)
               {
                                 
               }
              m_PDFViewer.setLayoutX(x);
              m_PDFViewer.setLayoutY(y);
              m_PDFViewer.setMaxHeight(200);
              m_PDFViewer.setMaxWidth(150);
              p.getChildren().add(m_PDFViewer);
            }
            else
            {
                //File fnew=new File(rs.getString("path"));
                Image icon=new Image(rs.getBinaryStream("document"));
                ImageView iv=new ImageView(icon);
                iv.setLayoutX(x);
                iv.setLayoutY(y);
                iv.setFitHeight(200);
                iv.setFitWidth(150);
                p.getChildren().add(iv);
                //logo.setImage(icon); 
            }
            main.getChildren().add(p);
            y+=220;
            }
            
            }catch(Exception e){}
           
            //main.getChildren().add(p);
            //fscroll.setContent(main);
            //fscroll.setPannable(true);
           return main;           
    }
     public void loadViewHistory()
     {
         try{
                     String plot=plot_info_plotid.getText();
                     String project=plotinfo_id.getText();
                     int i=hissa_table.getSelectionModel().getSelectedIndex();
                     TableViewSelectionModel selectionModel = hissa_table.getSelectionModel();
                     ObservableList selectedCells = hissa_table.getColumns();
                     TableColumn tablePosition =   (TableColumn) selectedCells.get(1);//1 is 2nd column
                     Object val = tablePosition.getCellData(i);
                     //System.out.println("Selected Value" + tablePosition);
                     //System.out.println("selected cells :" + selectedCells.toString());
                     //System.out.println("cell no :" + i);
                     System.out.println("Hissa Survey No is :" + val);
                     
                     String h[]=val.toString().split("/");
                     String hissa=h[2];
                     Stage stage1 = (Stage) plot_area_save.getScene().getWindow();
                     stage1.close();
                     FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ViewHistory.fxml"));
                     
                     Parent root1 = (Parent) fxmlLoader.load();
                     Stage stage = new Stage();
                     Scene scene = new Scene(root1);
                     int hid=query.getHissaId(val.toString());
                     ((Label)root1.lookup("#view_hissa_id")).setText(String.valueOf(hid));
                     ((Label)root1.lookup("#view_plot_id")).setText(plot);
                     
                     int x=200,y=30;
                     Pane vpane =new Pane();
                     ResultSet rs=query.getTitleOwner(hid);
                     rs.next();
                     int oid=rs.getInt("Id");
                     vpane=viewAllHistory(hid, oid, x, y);
                     vpane.setMaxHeight(1000000000);
                     vpane.setMaxWidth(1000);
                     vpane.setMinHeight(1000000000);
                     vpane.setMinWidth(1000);
                     
                     ((ScrollPane)root1.lookup("#vscroll")).setContent(vpane);
                     ((ScrollPane)root1.lookup("#vscroll")).setPannable(true);
                     stage.initStyle(StageStyle.UNDECORATED);
                     stage.setScene(scene);
                     stage.show();
         }catch(Exception e)
         {
            e.printStackTrace();
         }
     }
     public Pane viewHistory(int hid,int oid,int x,int y)
     {
         Pane pane=new Pane();
         //System.out.println("in history"+oid);
         //Pane main=new Pane();
         //ScrollPane vs=new ScrollPane();
         //int x=200,y=30;
         try{
         ResultSet rs=query.getOwnerInformation(oid);
         while(rs.next())
         {
             //int oid=rs.getInt("Id");
             
             String m=rs.getString("mutation_reg_no");
             String s=rs.getString("saledeed_reg_no");
             int mid=rs.getInt("mutation_id");
             int sid=rs.getInt("saledeed_id");
             Button title=new Button(rs.getString("owner_name"));
             title.setLayoutX(x);
             title.setLayoutY(y);
             title.setMaxWidth(250);
             title.setMaxHeight(250);
             title.setMinWidth(180);
             title.setMinHeight(50);
             title.setWrapText(true);
             title.setOnAction(new EventHandler<ActionEvent>() {
                         @Override public void handle(ActionEvent e) {
                            ImageView arrow=new ImageView();
                            arrow.setLayoutX(x+65);
                            arrow.setLayoutY(y);
                            TranslateTransition slideOut = new TranslateTransition(Duration.seconds(1), arrow);
                            slideOut.setByY(100);
                            slideOut.play();
                            File file = new File("/d1.png");
                            Image i=new Image(file.toURI().toString());
                            arrow.setImage(i);
                            //Right arrow
                            ImageView rarrow=new ImageView();
                            rarrow.setLayoutX(x+65);
                            rarrow.setLayoutY(y+50);
                            slideOut = new TranslateTransition(Duration.seconds(1), rarrow);
                            slideOut.setByX(50);
                            slideOut.play();
                            file = new File("/r1.png");
                            i=new Image(file.toURI().toString());
                            rarrow.setImage(i);
                            
                            Hyperlink sno=new Hyperlink(s);
                            sno.setLayoutX(x+65);
                            sno.setLayoutY(y+50);
                            sno.setStyle("-fx-text-fill:white;"
                                    + "-fx-font-weight: bold;"
                                    + "-fx-font-size: 15pt;");
                            slideOut = new TranslateTransition(Duration.seconds(1), sno);
                            slideOut.setByX(130);
                            slideOut.play();
                            sno.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent e) {
                                String table="sale_deed";
                               viewDocument(table,oid);
                            }
                           });
                            //left arrow
                            ImageView larrow=new ImageView();
                            larrow.setLayoutX(x+65);
                            larrow.setLayoutY(y+50);
                            slideOut = new TranslateTransition(Duration.seconds(1), larrow);
                            slideOut.setByX(-70);
                            slideOut.play();
                            file = new File("/l1.png");
                            i=new Image(file.toURI().toString());
                            larrow.setImage(i);
                            
                            Hyperlink mno=new Hyperlink(m);
                            mno.setLayoutX(x+65);
                            mno.setLayoutY(y+50);
                            mno.setStyle("-fx-text-fill:white;"
                                    + "-fx-font-weight: bold;"
                                    + "-fx-font-size: 15pt;");
                            slideOut = new TranslateTransition(Duration.seconds(1), mno);
                            slideOut.setByX(-130);
                            slideOut.play();
                            mno.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent e) {
                                String table="mutation";
                               viewDocument(table,oid);
                            }
                           });
                            int x1=x,y1=y;
                            try{
                            ResultSet rs=query.getOwnerById(oid);
                            while(rs.next())
                            {
                               int owner=rs.getInt("owner_id");
                               //Pane p1=new Pane();
                               Pane p=
                                       viewHistory(hid, owner,x1,y1);
                               slideOut = new TranslateTransition(Duration.seconds(1), p);
                               slideOut.setByY(150);
                               slideOut.play();
                               pane.getChildren().addAll(p);
                               //vscroll.setContent(p);
                               //y1+=50;
                               x1+=200;
                            }
                            }catch(Exception ex)
                            {
                               ex.printStackTrace();
                            }
                            
                            pane.getChildren().addAll(arrow,rarrow,larrow,mno,sno);
                          }
                        });
                   //pane.setStyle("-fx-border-color:white;");
             pane.getChildren().addAll(title);
             //vscroll.setContent(pane);
             //vs.setContent(pane);
             
           }
         }catch(Exception e)
         {
            
         }
         //vscroll.setContent(pane);
        // main.getChildren().add(pane);
         return pane;
     }
     public void viewDocument(String table,int oid)
     {
         try{
         ResultSet rs=query.getDocumentPath(table, oid);
         while(rs.next())
         {
             String path=rs.getString("path");
             if(path.contains(".pdf"))
             {
                 FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Document.fxml"));
                 Parent root1 = (Parent) fxmlLoader.load();
                 Stage stage = new Stage();
                 m_PDFViewer = new PDFViewer();
                 m_PDFViewer.loadPDF(rs.getBinaryStream("document"));
                 m_PDFViewer.setLayoutX(10);
                 m_PDFViewer.setLayoutY(10);
                 m_PDFViewer.setMaxHeight(650);
                 m_PDFViewer.setMaxWidth(550);
                 m_PDFViewer.setMinHeight(650);
                 m_PDFViewer.setMinWidth(450);
                ((Pane)root1.lookup("#doc_pane")).getChildren().add(m_PDFViewer);     
                stage.initStyle(StageStyle.UNDECORATED);
                stage.setTitle("Add Project");
                stage.setScene(new Scene(root1));  
                stage.show();
             }
             else
             {
                 FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Document.fxml"));
                 Parent root1 = (Parent) fxmlLoader.load();
                 Stage stage = new Stage();
                 //File fnew=new File(path);
                 Image icon=new Image(rs.getBinaryStream("document"));
                 ImageView iv=new ImageView(icon);
                 iv.setFitHeight(650);
                 iv.setFitWidth(450);
                 
                 iv.setLayoutX(10);
                 iv.setLayoutY(10);
                ((Pane)root1.lookup("#doc_pane")).getChildren().add(iv);     
                stage.initStyle(StageStyle.UNDECORATED);
                stage.setTitle("Add Project");
                stage.setScene(new Scene(root1));  
                stage.show();
             }
         }
         }catch(Exception e){
         e.printStackTrace();}
     }
     
     public void searchFromHistory()
     {
         String s=search_history.getText();
         if(s.equals(""))
         {
             list.setVisible(false);
         }
         else
         {
         try
         {
             
             String hid=view_hissa_id.getText();
             ResultSet rs=query.getSearchHistory(s,hid);
             
             list=new ListView();
             while(rs.next())
             {
                 list.getItems().addAll(rs.getString("owner_name"));
                 
             }
             list.setLayoutX(search_history.getLayoutX()+20);
             list.setLayoutY(55);
             list.setMaxWidth(170);
             list.setMaxHeight(100);
             list.setVisible(true);
             search_pane.getChildren().add(list);
             list.setOnMouseClicked(new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {
            System.out.println("clicked on " + list.getSelectionModel().getSelectedItem());
            String txt=list.getSelectionModel().getSelectedItem().toString();
            
            
           //Pane content=  (Pane) vscroll.getContent();
            
            
               //Pane p=(Pane) content;
               ObservableList<Node> allContent=history_main.getChildren();
               for(int i=0;i<allContent.size();i++)
               {
               if(allContent.get(i) instanceof ScrollPane)
               {
                 ScrollPane sc=(ScrollPane)allContent.get(i);
                  
                   System.out.println("scrollPane content :::"+sc.getContent());
                   Pane p=(Pane)sc.getContent();
                   System.out.println("Panel content :::"+p.getChildren());
                   ObservableList<Node> content=p.getChildren();
                    for(int j=0;j<content.size();j++)
                    {
                      if(content.get(j) instanceof Button)
                      {
                         Button b=(Button)content.get(j);
                         if(b.getText().equals(txt))
                         {
                            System.out.println("Found "+txt);
                            Double x=b.getLayoutX();
                            Double y=b.getLayoutY()/1000000000;
                            
                            //b.setDefaultButton(true);
                            //b.requestFocus();
                            //System.out.println("x "+x);
                            //System.out.println("y "+y);
                            //Point p=new Point(x.intValue(),y.intValue());
                            vscroll.setVvalue(y);
                         }
                         else{
                            System.out.println("Not Found "+txt);  
                         }
                     
                      }
                    }
                    getPanel(p,txt);
               }
               }
            
            }
        
    });
             } 
         
         catch(Exception e){
         e.printStackTrace();}
         }
     }
     @FXML
     public void handlefocusLost(MouseEvent evt)
     {
         search_pane.getChildren().remove(list);
     }
     public void getPanel(Pane pane,String txt)
     
     {
        ObservableList<Node> content=pane.getChildren();
                    for(int j=0;j<content.size();j++)
                    {
                       if(content.get(j) instanceof Button)
                      {
                         Button b=(Button)content.get(j);
                         if(b.getText().equals(txt))
                         {
                            System.out.println("Found "+txt); 
                            
                            Double x=b.getLayoutX();
                            Double y=b.getLayoutY()/1000000000;
                            b.fire();
                            //System.out.println("x "+x);
                            
                            //System.out.println("y "+y);
                            //Point p=new Point(x.intValue(),y.intValue());
                            //vscroll.setVvalue(y);
                            //b.setDefaultButton(true);
                            //b.requestFocus();
    
                            
                         }
                         else{
                            System.out.println("Not Found "+txt);  
                         }
                     
                      } 
                      if(content.get(j) instanceof Pane)
                     {
                        Pane c=(Pane)content.get(j);
                         getPanel(c,txt);
                        System.out.println("Sub Panel content :::"+c.getChildren());
                     }
                      
                    } 
     }
     //view Static history
     public Pane viewAllHistory(int hid,int oid,int x,int y)
     {
         Pane pane=new Pane();
         if(node.contains(oid))
         {
             try{
             
                  ResultSet rs=query.getOwnerInformation(oid);
                  while(rs.next())
                 {
             
                  String m=rs.getString("mutation_reg_no");
                  String s=rs.getString("saledeed_reg_no");
                  int mid=rs.getInt("mutation_id");
                  int sid=rs.getInt("saledeed_id");
                  Button title=new Button(rs.getString("owner_name"));
                  title.setLayoutX(x);
                  title.setLayoutY(y);
                  title.setMaxWidth(250);
                  title.setMaxHeight(250);
                  title.setMinWidth(180);
                  title.setMinHeight(50);
                  title.setWrapText(true);
            
                            ImageView arrow=new ImageView();
                            arrow.setLayoutX(x+65);
                            arrow.setLayoutY(y);
                            TranslateTransition slideOut = new TranslateTransition(Duration.seconds(1), arrow);
                            slideOut.setByY(100);
                            slideOut.play();
                            File file = new File("d1.png");
                            Image i=new Image(file.toURI().toString());
                            arrow.setImage(i);
                            //Right arrow
                            ImageView rarrow=new ImageView();
                            rarrow.setLayoutX(x+65);
                            rarrow.setLayoutY(y+50);
                            slideOut = new TranslateTransition(Duration.seconds(1), rarrow);
                            slideOut.setByX(50);
                            slideOut.play();
                            file = new File("r1.png");
                            i=new Image(file.toURI().toString());
                            rarrow.setImage(i);
                            
                            Hyperlink sno=new Hyperlink(s);
                            sno.setLayoutX(x+65);
                            sno.setLayoutY(y+50);
                            sno.setStyle("-fx-text-fill:white;"
                                    + "-fx-font-weight: bold;"
                                    + "-fx-font-size: 15pt;");
                            slideOut = new TranslateTransition(Duration.seconds(1), sno);
                            slideOut.setByX(130);
                            slideOut.play();
                            sno.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent e) {
                                String table="sale_deed";
                               viewDocument(table,oid);
                            }
                           });
                            //left arrow
                            ImageView larrow=new ImageView();
                            larrow.setLayoutX(x+65);
                            larrow.setLayoutY(y+50);
                            slideOut = new TranslateTransition(Duration.seconds(1), larrow);
                            slideOut.setByX(-70);
                            slideOut.play();
                            file = new File("l1.png");
                            i=new Image(file.toURI().toString());
                            larrow.setImage(i);
                            
                            Hyperlink mno=new Hyperlink(m);
                            mno.setLayoutX(x+65);
                            mno.setLayoutY(y+50);
                            mno.setStyle("-fx-text-fill:white;"
                                    + "-fx-font-weight: bold;"
                                    + "-fx-font-size: 15pt;");
                            slideOut = new TranslateTransition(Duration.seconds(1), mno);
                            slideOut.setByX(-130);
                            slideOut.play();
                            mno.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent e) {
                                String table="mutation";
                               viewDocument(table,oid);
                            }
                           });
                            title.setDisable(true);
                             pane.getChildren().addAll(title,arrow,rarrow,larrow,mno,sno);
                 }
             }catch(Exception e){
             e.printStackTrace();}
         }
         else
         {
         node.add(oid);
         //previous_oid=oid;
         try{
             
         ResultSet rs=query.getOwnerInformation(oid);
         while(rs.next())
         {
             //int oid=rs.getInt("Id");
             
             String m=rs.getString("mutation_reg_no");
             String s=rs.getString("saledeed_reg_no");
             int mid=rs.getInt("mutation_id");
             int sid=rs.getInt("saledeed_id");
             Button title=new Button(rs.getString("owner_name"));
             title.setLayoutX(x);
             title.setLayoutY(y);
             title.setMaxWidth(250);
             title.setMaxHeight(250);
             title.setMinWidth(180);
             title.setMinHeight(50);
             title.setWrapText(true);
            
                            ImageView arrow=new ImageView();
                            arrow.setLayoutX(x+65);
                            arrow.setLayoutY(y);
                            TranslateTransition slideOut = new TranslateTransition(Duration.seconds(1), arrow);
                            slideOut.setByY(100);
                            slideOut.play();
                            File file = new File("d1.png");
                            Image i=new Image(file.toURI().toString());
                            arrow.setImage(i);
                            //Right arrow
                            ImageView rarrow=new ImageView();
                            rarrow.setLayoutX(x+65);
                            rarrow.setLayoutY(y+50);
                            slideOut = new TranslateTransition(Duration.seconds(1), rarrow);
                            slideOut.setByX(50);
                            slideOut.play();
                            file = new File("r1.png");
                            i=new Image(file.toURI().toString());
                            rarrow.setImage(i);
                            
                            Hyperlink sno=new Hyperlink(s);
                            sno.setLayoutX(x+65);
                            sno.setLayoutY(y+50);
                            sno.setStyle("-fx-text-fill:white;"
                                    + "-fx-font-weight: bold;"
                                    + "-fx-font-size: 15pt;");
                            slideOut = new TranslateTransition(Duration.seconds(1), sno);
                            slideOut.setByX(130);
                            slideOut.play();
                            sno.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent e) {
                                String table="sale_deed";
                               viewDocument(table,oid);
                            }
                           });
                            //left arrow
                            ImageView larrow=new ImageView();
                            larrow.setLayoutX(x+65);
                            larrow.setLayoutY(y+50);
                            slideOut = new TranslateTransition(Duration.seconds(1), larrow);
                            slideOut.setByX(-70);
                            slideOut.play();
                            file = new File("l1.png");
                            i=new Image(file.toURI().toString());
                            larrow.setImage(i);
                            
                            Hyperlink mno=new Hyperlink(m);
                            mno.setLayoutX(x+65);
                            mno.setLayoutY(y+50);
                            mno.setStyle("-fx-text-fill:white;"
                                    + "-fx-font-weight: bold;"
                                    + "-fx-font-size: 15pt;");
                            slideOut = new TranslateTransition(Duration.seconds(1), mno);
                            slideOut.setByX(-130);
                            slideOut.play();
                            mno.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent e) {
                                String table="mutation";
                               viewDocument(table,oid);
                            }
                           });
                            int x1=x,y1=y;
                            try{
                                
                             rs=query.getOwnerById(oid);
                                System.out.println("Previous owner:"+previous_oid);
                                System.out.println("current owner:"+oid);
                             if(previous_oid==oid)
                                   isviewed=true;
                               else
                                   isviewed=false;
                               if(!isviewed)
                               {
                            while(rs.next())
                            {
                               
                               int owner=rs.getInt("owner_id");
                               //Pane p1=new Pane();
                               previous_oid=oid;
                               Pane p=
                                       viewAllHistory(hid, owner,x1,y1);
                               slideOut = new TranslateTransition(Duration.seconds(1), p);
                               slideOut.setByY(150);
                               slideOut.play();
                               pane.getChildren().addAll(p);
                               //vscroll.setContent(p);
                               //y1+=50;
                               x1+=200;
                               
                               
                               }
                            }
                               
                            }catch(Exception ex)
                            {
                               ex.printStackTrace();
                            }
                            
                            pane.getChildren().addAll(arrow,rarrow,larrow,mno,sno);
                          
                   //pane.setStyle("-fx-border-color:white;");
             pane.getChildren().addAll(title);
             //vscroll.setContent(pane);
             //vs.setContent(pane);
             
           }
         }catch(Exception e)
         {
            
         }
         }
         //vscroll.setContent(pane);
        // main.getChildren().add(pane);
         return pane;
     }
     @FXML
     private void searchplothistry1()
    {
        try{
        Connection con=MyConnection.createConnection();
         //   PreparedStatement psmt=con.prepareStatement("select * from owner where owner_name like '"+search.getText()+"%' or "
            //        + "saledeed_reg_no like '"+search.getText()+"%' or mutation_reg_no like '"+search.getText()+"%' or to_date like '"+search.getText()+"%' ");
        PreparedStatement psmt=con.prepareStatement("select * from project where project_name like '"+search.getText()+"%' or "
                 + "city like '"+search.getText()+"%' or district like '"+search.getText()+"%' or location like '"+search.getText()+"%' or ownership like '"+search.getText()+"%' or survey_no like '"+search.getText()+"%' ");
        ResultSet rs=psmt.executeQuery();
         
         viewlist.getItems().clear();
         while(rs.next())
            viewlist.getItems().addAll(rs.getString("project_name"));
         
    }catch(Exception ex)
    {
        ex.printStackTrace();
        System.out.println("Error in search..");
    }
    }
     @FXML
    private void searchHistory(MouseEvent event) {
       
            
            try{
                  
            //SearchPlotHistoryController c=new SearchPlotHistoryController();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SearchPlotHistory.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            ((ComboBox)root1.lookup("#plots")).setVisible(false);
            
            ((ComboBox)root1.lookup("#hissssno")).setVisible(false);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Search History");
            stage.setScene(new Scene(root1));  
            stage.show();
            
          }
         catch (Exception e) {
             e.printStackTrace();
            /*Alert alert = new Alert(Alert.AlertType.INFORMATION);
           alert.setTitle("Error");
           //alert.setHeaderText("Look, an Information Dialog");
           alert.setContentText(e.getMessage());
           alert.showAndWait();*/
        }
    }
    @FXML
     private void plotnos(MouseEvent e)
    {
        try{
            String pname= viewlist.getSelectionModel().getSelectedItem().toString();
            ResultSet rs=query.getProjectIdByName(pname);
            while(rs.next())
            {
                sproject_id.setText(rs.getString("Id"));
            }
        Connection con=MyConnection.createConnection();
         //   PreparedStatement psmt=con.prepareStatement("select * from owner where owner_name like '"+search.getText()+"%' or "
            //        + "saledeed_reg_no like '"+search.getText()+"%' or mutation_reg_no like '"+search.getText()+"%' or to_date like '"+search.getText()+"%' ");
        PreparedStatement psmt=con.prepareStatement("select plot from plot p,project pj where p.project_id=pj.Id and pj.project_name= ?");
       psmt.setString(1,pname);
      //   psmt.setString(2, viewlist.getSelectionModel().getSelectedItem().toString());
         rs=psmt.executeQuery();
         plots.setVisible(true);
        // hissa.setVisible(true);
         
         plots.getItems().clear();
         while(rs.next())
            plots.getItems().addAll(rs.getString("plot"));
         
    }catch(Exception ex)
        
    {
        ex.printStackTrace();
        System.out.println("Error in search..");
    }
    }
    
      @FXML
     private void hiss(ActionEvent e)
    {
        try{
            String plotno=plots.getSelectionModel().getSelectedItem().toString();
            ResultSet rs=query.getPlotIdByNo(sproject_id.getText(),plotno);
            while(rs.next())
            {
                splot_id.setText(rs.getString("Id"));
            }
        Connection con=MyConnection.createConnection();
        
        PreparedStatement psmt=con.prepareStatement("select hissa_no from plot_hissa_details where plot_id=? and project_id=?");
        psmt.setString(1, splot_id.getText());
        psmt.setString(2,sproject_id.getText());
        rs=psmt.executeQuery();
         hissa.setVisible(true);
        // hissa.setVisible(true);
         System.out.println(plotno);
         System.out.println(viewlist.getSelectionModel().getSelectedItem().toString());
         hissa.getItems().clear();
         while(rs.next())
            hissa.getItems().addAll(rs.getString("hissa_no"));
         
    }catch(Exception ex)
        
    {
        ex.printStackTrace();
        System.out.println("Error in search..");
    }
    }
     @FXML
     public void openHistory(ActionEvent e)
     {
         try{
            String hno=hissa.getSelectionModel().getSelectedItem().toString();
            ResultSet rs=query.getHissaIdByNo(splot_id.getText(),sproject_id.getText(),hno);
            while(rs.next())
            {
                shissa_id.setText(rs.getString("Id"));
                
            }
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ViewHistory.fxml"));
                     
                     Parent root1 = (Parent) fxmlLoader.load();
                     Stage stage = new Stage();
                     Scene scene = new Scene(root1);
                     int hid=Integer.parseInt(shissa_id.getText());
                     ((Label)root1.lookup("#view_hissa_id")).setText(shissa_id.getText());
                     ((Label)root1.lookup("#view_plot_id")).setText(splot_id.getText());
                     
                     int x=200,y=30;
                     Pane vpane =new Pane();
                      rs=query.getTitleOwner(hid);
                     rs.next();
                     int oid=rs.getInt("Id");
                     vpane=viewAllHistory(hid, oid, x, y);
                     vpane.setMaxHeight(1000000000);
                     vpane.setMaxWidth(1000);
                     vpane.setMinHeight(1000000000);
                     vpane.setMinWidth(1000);
                     
                     ((ScrollPane)root1.lookup("#vscroll")).setContent(vpane);
                     ((ScrollPane)root1.lookup("#vscroll")).setPannable(true);
                     stage.initStyle(StageStyle.UNDECORATED);
                     stage.setScene(scene);
                     stage.show();
         }catch(Exception ex)
        
    {
        ex.printStackTrace();
        System.out.println("Error in search..");
    }
     }
     public boolean openAddPurchaser(int hid,String hs,String oid)
     {
         boolean flag=false;
         try{
               FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Purchaser.fxml"));         
               Parent root1 = (Parent) fxmlLoader.load();
               Stage stage = new Stage();
               Pane p=OwnerList(hid,((ComboBox)root1.lookup("#owner_id_list")));
               ((ScrollPane)root1.lookup("#pcscroll")).setContent(p);
               ((Label)root1.lookup("#pc_hissa_survey")).setText(hs);
               ((Label)root1.lookup("#pc_owner_id")).setText(oid);
               ((Label)root1.lookup("#pc_hissa_id")).setText(""+hid);
               Scene scene = new Scene(root1);
               stage.initStyle(StageStyle.UNDECORATED);
               stage.setScene(scene);
               stage.show();
               flag=true;
            }catch(Exception ex)
            {
               ex.printStackTrace();
               System.out.println("Error in search..");
            }
         return flag;
     }
     public boolean openPurchaser(int hid,String hs,String oid)
     {
         boolean flag=false;
         try{
               FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Purchaser.fxml"));         
               Parent root1 = (Parent) fxmlLoader.load();
               Stage stage = new Stage();
               Pane p=savedOwnerList(hid,((ComboBox)root1.lookup("#owner_id_list")),oid);
               ((ScrollPane)root1.lookup("#pcscroll")).setContent(p);
               ((Label)root1.lookup("#pc_hissa_survey")).setText(hs);
               ((Label)root1.lookup("#pc_owner_id")).setText(oid);
               ((Label)root1.lookup("#pc_hissa_id")).setText(""+hid);
               Scene scene = new Scene(root1);
               stage.initStyle(StageStyle.UNDECORATED);
               stage.setScene(scene);
               stage.show();
               flag=true;
            }catch(Exception ex)
            {
               ex.printStackTrace();
               System.out.println("Error in search..");
            }
         return flag;
     }
     public Pane OwnerList(int hid,ComboBox olist)
     {
         Pane p=new Pane();
         //ComboBox olist=new ComboBox();
         CheckBox owner[]=new CheckBox[100];
         try{
            int x=30,y=30; 
         ResultSet rs=query.getOwnerListByHissa(hid);
         int i=0;
         while(rs.next())
         {
             owner[i]=new CheckBox(rs.getString("owner_name"));
             
             owner[i].setLayoutX(x);
             owner[i].setLayoutY(y);
             System.out.println("x and y1 "+x+" "+y);
             if(x>300)
             {
                 x=30;
                 y+=50;
             }
             else
             {                 
                 x+=200;
             }
             
             String id=rs.getString("Id");
             owner[i].setOnAction(new EventHandler<ActionEvent>() {
                @Override
                    public void handle(ActionEvent e) {
                        CheckBox cb=(CheckBox)e.getSource();
                        if(cb.isSelected())
                        {
                          if(!(olist.getItems().equals(id)))
                          olist.getItems().add(id);    
                        }
                        else 
                        {
                            olist.getItems().remove(id); 
                        }
                       }
             });
             i++;
         }
         y+=50;
         rs=query.getHistoryListByHissa(hid);
         while(rs.next())
         {
             owner[i]=new CheckBox(rs.getString("owner_name"));
             //if(!(olist.getItems().equals(rs.getString("owner_id"))))
                //olist.getItems().add(rs.getString("owner_id"));
             
             owner[i].setLayoutX(x);
             owner[i].setLayoutY(y);
             System.out.println("x and y2"+x+" "+y);
             if(x>300)
             {
                 x=30;
                 y+=50;
             }
             else
             {                 
                 x+=200;
             }
             
             String id=rs.getString("owner_id");
             owner[i].setOnAction(new EventHandler<ActionEvent>() {
                @Override
                    public void handle(ActionEvent e) {
                        CheckBox cb=(CheckBox)e.getSource();
                        if(cb.isSelected())
                        {
                          if(!(olist.getItems().equals(id)))
                          olist.getItems().add(id);    
                        }
                        else 
                        {
                            olist.getItems().remove(id); 
                        }
                       }
             });
             i++;
         }
         for(int j=0;j<owner.length;j++)
         {
             p.getChildren().add(owner[j]);
         }
        // olist.setLayoutX(x);
         ///olist.setLayoutY(y);
         //p.getChildren().add(olist);
         }catch(Exception e)
         {
            e.printStackTrace();
         }
         return p;
     }
     public Pane savedOwnerList(int hid,ComboBox olist,String oid)
     {
         Pane p=new Pane();
         //ComboBox olist=new ComboBox();
         CheckBox owner[]=new CheckBox[100];
         try{
            int x=30,y=30; 
         ResultSet rs=query.getOwnerListByHissa(hid);
             System.out.println("resultSet1:"+rs.toString());
         int i=0;
         while(rs.next())
         {
             owner[i]=new CheckBox(rs.getString("owner_name"));
             owner[i].setLayoutX(x);
             owner[i].setLayoutY(y);
             System.out.println("x and y1 "+x+" "+y);
             if(x>300)
             {
                 x=30;
                 y+=50;
             }
             else
             {                 
                 x+=200;
             }
             
             String id=rs.getString("Id");
             owner[i].setOnAction(new EventHandler<ActionEvent>() {
                @Override
                    public void handle(ActionEvent e) {
                        CheckBox cb=(CheckBox)e.getSource();
                        if(cb.isSelected())
                        {
                          if(!(olist.getItems().equals(id)))
                          olist.getItems().add(id);    
                        }
                        else 
                        {
                            olist.getItems().remove(id); 
                        }
                       }
             });
             i++;
         }
         y+=50;
         rs=query.getHistoryListByHissa(hid);
         System.out.println("resultSet2:"+rs.toString());
         while(rs.next())
         {
             owner[i]=new CheckBox(rs.getString("owner_name"));
             //if(!(olist.getItems().equals(rs.getString("owner_id"))))
                //olist.getItems().add(rs.getString("owner_id"));
             
             owner[i].setLayoutX(x);
             owner[i].setLayoutY(y);
             System.out.println("x and y2"+x+" "+y);
             if(x>300)
             {
                 x=30;
                 y+=50;
             }
             else
             {                 
                 x+=200;
             }
             
             String id=rs.getString("owner_id");
             System.out.println("id is:"+id);
             owner[i].setOnAction(new EventHandler<ActionEvent>() {
                @Override
                    public void handle(ActionEvent e) {
                        CheckBox cb=(CheckBox)e.getSource();
                        if(cb.isSelected())
                        {
                          if(!(olist.getItems().equals(id)))
                          olist.getItems().add(id);    
                        }
                        else 
                        {
                            olist.getItems().remove(id); 
                        }
                       }
             });
             i++;
         }
         System.out.println("BeforPanel Addition");
         for(int j=0;j<i;j++)
         {
             p.getChildren().add(owner[j]);
         }
         System.out.println("Added to panel");
         rs=query.getOwnerNames(hid,oid);
         System.out.println("Resultset :"+rs.toString());
         while(rs.next())
         {
            System.out.println("in while");   
            for(int j=0;j<i;j++)
            {
                System.out.println("name is :"+owner[j]);
                if(owner[j].getText().equals(rs.getString("owner_name")))
                {
                    System.out.println("Match found");
                    owner[j].setSelected(true);
                }
                else
                {
                    System.out.println(" Not Match");
                   //owner[j].setSelected(false); 
                }
            }
         
         }
        // olist.setLayoutX(x);
         ///olist.setLayoutY(y);
         //p.getChildren().add(olist);
         }catch(Exception e)
         {
            e.printStackTrace();
         }
         return p;
     }
     @FXML
     public void savePurchaser(ActionEvent e)
     {
         try
         {
             hsbean.setHissa_id(Integer.parseInt(pc_hissa_id.getText()));
             hsbean.setHissa_survey(pc_hissa_survey.getText());
             hsbean.setOwner_id(pc_owner_id.getText());
             String[] list=new String[owner_id_list.getItems().size()];
             //System.out.println("combo item lenght:"+owner_id_list.getItems());
             for(int i=0;i<owner_id_list.getItems().size();i++)
                 list[i]=owner_id_list.getItems().get(i).toString();
             if(query.savePurchaser(hsbean,list))
             {
                 System.out.println("done");
                 issaved=true;
                 final Node source = (Node) e.getSource();
                 final Stage stage = (Stage) source.getScene().getWindow();
                 stage.close();
             }
             else
             {
                 issaved=false;
                 System.out.println("error");
             }
         }catch(Exception ex)
         {
             ex.printStackTrace();
         }
     }
    public void openEditHistory(String oid,int hid,String hs)
    {
        try{
               FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EditHistory.fxml"));         
               Parent root1 = (Parent) fxmlLoader.load();
               Stage stage = new Stage();
               /*Pane p=savedOwnerList(hid,((ComboBox)root1.lookup("#owner_id_list")),oid);
               ((ScrollPane)root1.lookup("#pcscroll")).setContent(p);
               ((Label)root1.lookup("#pc_hissa_survey")).setText(hs);
               ((Label)root1.lookup("#pc_owner_id")).setText(oid);*/
               ResultSet rs1=query.getHissaSurveyById(hid);
               while(rs1.next())
               {
                   hs=rs1.getString("hissa_survey");
               }
               ((Label)root1.lookup("#edit_hissa_id")).setText(""+hid);
               ((Label)root1.lookup("#edit_owner_id")).setText(oid);
               ((Label)root1.lookup("#edit_hs")).setText(hs);
               ResultSet rs=query.getOwnerInformation(Integer.parseInt(oid));
               while(rs.next())
               {
                   ((TextField)root1.lookup("#edit_owner_name")).setText(rs.getString("owner_name"));
                   if(rs.getDate("from_date")!=null)
                   {
                   ((DatePicker)root1.lookup("#edit_from_date")).setValue(rs.getDate("from_date").toLocalDate());
                   ((DatePicker)root1.lookup("#edit_to_date")).setValue(rs.getDate("to_date").toLocalDate());
                   ((TextField)root1.lookup("#edit_mno")).setText(rs.getString("mutation_reg_no"));
                   ((TextField)root1.lookup("#edit_sno")).setText(rs.getString("saledeed_reg_no"));
                   }
               }
               Scene scene = new Scene(root1);
               stage.initStyle(StageStyle.UNDECORATED);
               stage.setScene(scene);
               stage.show();
               ///flag=true;
            }catch(Exception ex)
            {
               ex.printStackTrace();
               System.out.println("Error in search..");
            }
    }
    @FXML
    public void openDocuments(ActionEvent e)
    {
        int hid=Integer.parseInt(edit_hissa_id.getText());
        String oid=edit_owner_id.getText();
        try{
        openUploadDocument(hid,oid);
        }catch(Exception ex){
        ex.printStackTrace();
        }
    }
    @FXML
    public void EditOwner(ActionEvent e)
    {
        int hid=Integer.parseInt(edit_hissa_id.getText());
        int oid=Integer.parseInt(edit_owner_id.getText());
        try{
        if(edit_from_date.getValue()==null||edit_from_date.getValue()==null)
        {
             Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setTitle("Error");
             alert.setHeaderText("Error");
             alert.setContentText("Please Select date first!!");
             alert.showAndWait();
        }
        else if(edit_mno.getText().equals("")||edit_mno.getText().equals(""))
        {
             Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setTitle("Error");
             alert.setHeaderText("Error");
             alert.setContentText("Registration No are complusory!!");
             alert.showAndWait();
        }
        else{
            Date date = java.sql.Date.valueOf(edit_from_date.getValue());
            Date tdate = java.sql.Date.valueOf(edit_to_date.getValue());
            obean.setOwner_name(edit_owner_name.getText());
            obean.setFrom_date(date);
            obean.setTo_date(tdate);
            obean.setMutation_no(edit_mno.getText());
            obean.setSaledeed_no(edit_sno.getText());
            obean.setHissa_id(hid);
            obean.setOwner_id(oid);
            if(query.saveOwnerDetails(obean))
            {
               Alert alert = new Alert(Alert.AlertType.INFORMATION);
               alert.setTitle("Message");
               //alert.setHeaderText("Error");
               alert.setContentText("Owner saved successfully!!");
               alert.showAndWait(); 
               final Node source = (Node) e.getSource();
               final Stage stage = (Stage) source.getScene().getWindow();
               stage.close();
                refreshAddHistory(edit_hs.getText(), String.valueOf(hid));
               //openProjectMap(Integer.parseInt(addhistory_id.getText()));
            }
        }
        }catch(Exception ex){
        ex.printStackTrace();
        
        }
    }
    @FXML
    public void openPurchaserList()
    {
        openPurchaser(Integer.parseInt(edit_hissa_id.getText()), edit_hs.getText(), edit_owner_id.getText());
    }
    public void deleteOwner(String oid,int hid)
    {
        try
        {
            ResultSet rs=query.getChild(oid);
            if(rs.next())
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Message");
               //alert.setHeaderText("Error");
               alert.setContentText("Please delete child first!!");
               alert.showAndWait();  
            }
            else{
              
               if(query.deleteOwner(oid))
                {
                   Alert alert = new Alert(Alert.AlertType.INFORMATION);
                   alert.setTitle("Message");
                   alert.setContentText("Deleted successfully!!");
                   alert.showAndWait(); 
                   refreshAddHistory(addhistory_hissaid.getText(),String.valueOf(hid));
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    @FXML
    public void openEditHissa(ActionEvent evt)
    {
        try{
        showHissaTable(evt);
        hpane.setVisible(true);
        String plot=plot_info_plotid.getText();
        String project=plotinfo_id.getText();
        int i=hissa_table.getSelectionModel().getSelectedIndex();
        TableViewSelectionModel selectionModel = hissa_table.getSelectionModel();
        ObservableList selectedCells = hissa_table.getColumns();
        TableColumn tablePosition =   (TableColumn) selectedCells.get(1);//1 is 2nd column
        Object val = tablePosition.getCellData(i);
        System.out.println("Hissa Survey No is :" + val);
        String h[]=val.toString().split("/");
        String hissa=h[2];
        hissa_no.setText(hissa);
        ResultSet rs=query.getPlotTitleOwner(val.toString());
        while(rs.next())
        {
           area_purchased.setText(rs.getString("area_purchased"));
           title_owner.setText(rs.getString("title_owner"));
           punit.getSelectionModel().select(rs.getString("unit_name"));
        }
        save_btn.setText("Edit");
        }catch(Exception e){}
    }
    @FXML
    public void handleLogout(ActionEvent evt)
    {
        close(evt);
        Demo d=new Demo();
        d.openJMapViewer();
    }
}

