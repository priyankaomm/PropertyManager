/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;


/**
 * Load image, provide rectangle for rubberband selection. Press right mouse button for "crop" context menu which then crops the image at the selection rectangle and saves it as jpg.
 */
public class crop  {

    RubberBandSelection rubberBandSelection;
    ImageView imageView;

    Stage primaryStage;

    

    
    public void openMap(Stage primaryStage,Image image) {

        this.primaryStage = primaryStage;

        primaryStage.setTitle("Image Crop");

        BorderPane root = new BorderPane();

        // container for image layers
        ScrollPane scrollPane = new ScrollPane();

        // image layer: a group of images
        Group imageLayer = new Group(); 

        // load the image
//      Image image = new Image( getClass().getResource( "cat.jpg").toExternalForm());
        //Image image = new Image();

        // the container for the image as a javafx node
        imageView = new ImageView( image);

        // add image to layer
        imageLayer.getChildren().add( imageView);

        // use scrollpane for image view in case the image is large
        scrollPane.setContent(imageLayer);

        // put scrollpane in scene
        root.setCenter(scrollPane);

        // rubberband selection
        rubberBandSelection = new RubberBandSelection(imageLayer);

        // create context menu and menu items
        ContextMenu contextMenu = new ContextMenu();

        MenuItem cropMenuItem = new MenuItem("Crop");
        cropMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {

                // get bounds for image crop
                Bounds selectionBounds = rubberBandSelection.getBounds();

                // show bounds info
                System.out.println( "Selected area: " + selectionBounds);

                // crop the image
                crop( selectionBounds);

            }
        });
        contextMenu.getItems().add( cropMenuItem);

        // set context menu on image layer
        imageLayer.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isSecondaryButtonDown()) {
                    contextMenu.show(imageLayer, event.getScreenX(), event.getScreenY());
                }
            }
        });

        primaryStage.setScene(new Scene(root, 1024, 768));
        primaryStage.show();
    }

    private void crop( Bounds bounds) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Image");

        File file = fileChooser.showSaveDialog( primaryStage);
       // File file=new File("c:/Property/"+pid+"/temp files/");
        if (file == null)
            return;

        int width = (int) bounds.getWidth();
        int height = (int) bounds.getHeight();

        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);
        parameters.setViewport(new Rectangle2D( bounds.getMinX(), bounds.getMinY(), width, height));

        WritableImage wi = new WritableImage( width, height);
        imageView.snapshot(parameters, wi);

        // save image 
        // !!! has bug because of transparency (use approach below) !!!
        // --------------------------------
//        try {
//          ImageIO.write(SwingFXUtils.fromFXImage( wi, null), "jpg", file);
//      } catch (IOException e) {
//          e.printStackTrace();
//      }


        // save image (without alpha)
        // --------------------------------
        BufferedImage bufImageARGB = SwingFXUtils.fromFXImage(wi, null);
        BufferedImage bufImageRGB = new BufferedImage(bufImageARGB.getWidth(), bufImageARGB.getHeight(), BufferedImage.OPAQUE);

        Graphics2D graphics = bufImageRGB.createGraphics();
        graphics.drawImage(bufImageARGB, 0, 0, null);

        try {

            ImageIO.write(bufImageRGB, "png", file); 

            System.out.println( "Image saved to " + file.getAbsolutePath());

        } catch (IOException e) {
            e.printStackTrace();
        }

        graphics.dispose();

    }

    

    /**
     * Drag rectangle with mouse cursor in order to get selection bounds
     */
    public static class RubberBandSelection {

        final DragContext dragContext = new DragContext();
        Rectangle rect = new Rectangle();

        Group group;


        public Bounds getBounds() {
            return rect.getBoundsInParent();
        }

        public RubberBandSelection( Group group) {

            this.group = group;

            rect = new Rectangle( 0,0,0,0);
            rect.setStroke(Color.BLUE);
            rect.setStrokeWidth(1);
            rect.setStrokeLineCap(StrokeLineCap.ROUND);
            rect.setFill(Color.LIGHTBLUE.deriveColor(0, 1.2, 1, 0.6));

            group.addEventHandler(MouseEvent.MOUSE_PRESSED, onMousePressedEventHandler);
            group.addEventHandler(MouseEvent.MOUSE_DRAGGED, onMouseDraggedEventHandler);
            group.addEventHandler(MouseEvent.MOUSE_RELEASED, onMouseReleasedEventHandler);

        }

        EventHandler<MouseEvent> onMousePressedEventHandler = new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {

                if( event.isSecondaryButtonDown())
                    return;

                // remove old rect
                rect.setX(0);
                rect.setY(0);
                rect.setWidth(0);
                rect.setHeight(0);

                group.getChildren().remove( rect);


                // prepare new drag operation
                dragContext.mouseAnchorX = event.getX();
                dragContext.mouseAnchorY = event.getY();

                rect.setX(dragContext.mouseAnchorX);
                rect.setY(dragContext.mouseAnchorY);
                rect.setWidth(0);
                rect.setHeight(0);

                group.getChildren().add( rect);

            }
        };

        EventHandler<MouseEvent> onMouseDraggedEventHandler = new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {

                if( event.isSecondaryButtonDown())
                    return;

                double offsetX = event.getX() - dragContext.mouseAnchorX;
                double offsetY = event.getY() - dragContext.mouseAnchorY;

                if( offsetX > 0)
                    rect.setWidth( offsetX);
                else {
                    rect.setX(event.getX());
                    rect.setWidth(dragContext.mouseAnchorX - rect.getX());
                }

                if( offsetY > 0) {
                    rect.setHeight( offsetY);
                } else {
                    rect.setY(event.getY());
                    rect.setHeight(dragContext.mouseAnchorY - rect.getY());
                }
            }
        };


        EventHandler<MouseEvent> onMouseReleasedEventHandler = new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {

                if( event.isSecondaryButtonDown())
                    return;

                // remove rectangle
                // note: we want to keep the ruuberband selection for the cropping => code is just commented out
                /*
                rect.setX(0);
                rect.setY(0);
                rect.setWidth(0);
                rect.setHeight(0);

                group.getChildren().remove( rect);
                */

            }
        };
        private static final class DragContext {

            public double mouseAnchorX;
            public double mouseAnchorY;


        }
    }
}