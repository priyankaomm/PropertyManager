/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo;




import com.Connection.MyConnection;
import demo.FXMLDocumentController;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.DoubleStream;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Pair;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
//import jdk.internal.util.xml.impl.Pair;
import org.openstreetmap.gui.jmapviewer.AbstractLayer;
import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.JMapViewerTree;
import org.openstreetmap.gui.jmapviewer.Layer;
import org.openstreetmap.gui.jmapviewer.LayerGroup;
import org.openstreetmap.gui.jmapviewer.MapMarkerCircle;
import static org.openstreetmap.gui.jmapviewer.MapMarkerCircle.getDefaultStyle;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.MapPolygonImpl;
import org.openstreetmap.gui.jmapviewer.MapRectangleImpl;
import org.openstreetmap.gui.jmapviewer.OsmTileLoader;
import org.openstreetmap.gui.jmapviewer.events.JMVCommandEvent;
import org.openstreetmap.gui.jmapviewer.interfaces.JMapViewerEventListener;
import org.openstreetmap.gui.jmapviewer.Style;
import org.openstreetmap.gui.jmapviewer.checkBoxTree.CheckBoxNodePanel;
import org.openstreetmap.gui.jmapviewer.checkBoxTree.CheckBoxTree;
import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;
import org.openstreetmap.gui.jmapviewer.interfaces.TileLoader;
import org.openstreetmap.gui.jmapviewer.interfaces.TileSource;
import org.openstreetmap.gui.jmapviewer.tilesources.BingAerialTileSource;
import org.openstreetmap.gui.jmapviewer.tilesources.MapQuestOpenAerialTileSource;
import org.openstreetmap.gui.jmapviewer.tilesources.MapQuestOsmTileSource;
import org.openstreetmap.gui.jmapviewer.tilesources.OsmTileSource;

/**
 *
 * @author comp3
 */
public class CreatingMap extends JFrame implements JMapViewerEventListener {
    
    private static final long serialVersionUID = 1L;

    private final JMapViewerTree treeMap;
    
    private final JLabel zoomLabel;
    private final JLabel zoomValue;
    private final JLabel mperpLabelName;
    private final JLabel mperpLabelValue;
    private final CheckBoxTree tree;
    private   AbstractLayer layer=null; 
    
    public CreatingMap(int bid)
    {
        super("Property Manager");
        setUndecorated(true);
        //setSize(1020, 550);
        Coordinate india = new Coordinate(18.521283325496277, 73.85696411132812);
        
        treeMap = new JMapViewerTree("Zones");
        createMarker(bid);
        map().setDisplayPosition(india, 10);
        map().addJMVListener(this);
        setLayout(new BorderLayout());
        //setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        //setLocation(260, 205);
        
        JPanel panel = new JPanel(new BorderLayout());
        JPanel panelTop = new JPanel();
        JPanel panelBottom = new JPanel();
        JPanel helpPanel = new JPanel();
        //org.openstreetmap.gui.jmapviewer.JMapViewer map = new org.openstreetmap.gui.jmapviewer.JMapViewer();
        mperpLabelName = new JLabel("Meters/Pixels: ");
        mperpLabelValue = new JLabel(String.format("%s", map().getMeterPerPixel()));

        zoomLabel = new JLabel("Zoom: ");
        zoomValue = new JLabel(String.format("%s", map().getZoom()));

        add(panel, BorderLayout.NORTH);
        add(helpPanel, BorderLayout.SOUTH);
        panel.add(panelTop, BorderLayout.NORTH);
        panel.add(panelBottom, BorderLayout.SOUTH);
        JLabel helpLabel = new JLabel("Use right mouse button to move,\n "
                + "left double click or mouse wheel to zoom.");
        helpPanel.add(helpLabel);
        helpPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        JLabel builder_id = new JLabel(""+bid);
        JButton back = new JButton("Back To Dashboard");
        back.setBackground(Color.LIGHT_GRAY);
        JButton button = new JButton("setDisplayToFitMapMarkers");
        back.setBounds(10,10,100,200);
        //panelTop.add(builder_id);
        panelTop.add(back);

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println("in dashboard:"+bid);
                dispose();
                createScene(bid);
                
            }
        });
        JComboBox<TileSource> tileSourceSelector = new JComboBox<>(new TileSource[] {
                new OsmTileSource.Mapnik() });
        tileSourceSelector.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                map().setTileSource((TileSource) e.getItem());
            }
        });
        JComboBox<TileLoader> tileLoaderSelector;
        tileLoaderSelector = new JComboBox<>(new TileLoader[] {new OsmTileLoader(map())});
        tileLoaderSelector.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                map().setTileLoader((TileLoader) e.getItem());
            }
        });
        map().setTileLoader((TileLoader) tileLoaderSelector.getSelectedItem());
        panelTop.add(tileSourceSelector);
        panelTop.add(tileLoaderSelector);
        
        ///
        final JCheckBox showTreeLayers = new JCheckBox("Tree Structure of Sites");
        showTreeLayers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                treeMap.setTreeVisible(true);
                showTreeLayers.isSelected();
                //treeMap.setT
            }
        });
        panelBottom.add(showTreeLayers);
        ///
        final JCheckBox showToolTip = new JCheckBox("Coordinates of Locations");
        showToolTip.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                map().setToolTipText(null);
            }
        });
        panelBottom.add(showToolTip);
        final JCheckBox showZoomControls = new JCheckBox("Show zoom controls");
        //showZoomControls.setSelected(map().getZoomControlsVisible());
        showZoomControls.addActionListener((ActionEvent e) -> {
            map().setZoomContolsVisible(showZoomControls.isSelected());
        });
        panelBottom.add(showZoomControls);
        
        
        //panelBottom.add(button);
        //panelBottom.add(create);
        
        panelTop.add(zoomLabel);
        panelTop.add(zoomValue);
        panelTop.add(mperpLabelName);
        panelTop.add(mperpLabelValue);
        
        
        add(treeMap, BorderLayout.CENTER);
        
        tree = treeMap.getTree();
        
        tree.addNodeListener(new MouseAdapter() {
            @Override
           public void mousePressed(MouseEvent e) {
                maybeShowPopup(e);
            }

           @Override
           public void mouseReleased(MouseEvent e) {
               maybeShowPopup(e);
          }

            private void maybeShowPopup(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    layer = ((CheckBoxNodePanel) e.getComponent()).getData().getAbstractLayer();
                    System.out.println("layer details is : "+layer.toString());
                    if (layer != null)
                       CreatingMap.this.createPopupMenu(layer).show(e.getComponent(), e.getX(), e.getY());
                }
            }
       });
        

            
               

        map().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
//                    JOptionPane.showMessageDialog(null,"here i am");
//                    map().getAttribution().handleAttribution(e.getPoint(), true);
                      
                      Point p = e.getPoint();
                      map().setToolTipText(map().getPosition(p).toString());
                      List<MapMarker> marker = map().getMapMarkerList();
                      Iterator itr= marker.iterator();
                      while(itr.hasNext())
                      {
                          System.out.println("coordinates are : "+itr.next());  
                      }
                      String point=map().getToolTipText();
                      String space="";
//                    Pattern q = Pattern.compile("(\\d+(?:\\.\\d+))");
//                    String grup=null;
//                    Matcher m = q.matcher(point);
//                    while(m.find()) {
//                    grup=m.group();}
                        String result =point.replaceAll("[^0-9. ]", space);
                      
                        String[] grp=result.split("\\s+");
                        double d1=Double.parseDouble(grp[0]);
                        double d2=Double.parseDouble(grp[1]);
                        NewMarker lp = new NewMarker(d1,d2,bid);
                        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
                        lp.setLocation(dim.width/2-lp.getSize().width/2, dim.height/2-lp.getSize().height/2);
                        lp.setVisible(true);
                    
                }
                
            }
           
        });

        map().addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                Point p = e.getPoint();
                boolean cursorHand = map().getAttribution().handleAttributionCursor(p);
                if (cursorHand) {
                    map().setCursor(new Cursor(Cursor.HAND_CURSOR));
                } else {
                    map().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
                if (showToolTip.isSelected()) map().setToolTipText(map().getPosition(p).toString());
            }
        });
        
        
    }
    
    
    public void createMarker(int bid)
    {
      try
      {
          Connection con =MyConnection.createConnection();
          PreparedStatement pmt = con.prepareStatement("Select Name,langtitute,longtitute from newcoordinate where builder_id=?");
          pmt.setInt(1, bid);
          ResultSet rs = pmt.executeQuery();
          LayerGroup IndiaGroup = new LayerGroup("India");
          while(rs.next())
          {
           Layer layer1 = IndiaGroup.addLayer(rs.getString("Name"));   
           Coordinate coor= new Coordinate(Double.parseDouble(rs.getString("langtitute")), Double.parseDouble(rs.getString("longtitute")));
           //MapMarkerDot anonymous = new MapMarkerDot(Double.parseDouble(rs.getString("langtitute")), Double.parseDouble(rs.getString("longtitute")));
           treeMap.addLayer(layer1);
           map().addMapMarker(new MapMarkerDot(layer1,rs.getString("Name"),coor));
           

          }
           
      }
      catch(Exception ex)
      {
          JOptionPane.showMessageDialog(null, ex);
      }
       
    }
    

    @Override
    public void processCommand(JMVCommandEvent jmvce) {
    if (jmvce.getCommand().equals(JMVCommandEvent.COMMAND.ZOOM) ||
                jmvce.getCommand().equals(JMVCommandEvent.COMMAND.MOVE)) {
            updateZoomParameters();
        }
    }
    
    private void updateZoomParameters() {
        if (mperpLabelValue != null)
            mperpLabelValue.setText(String.format("%s", map().getMeterPerPixel()));
        if (zoomValue != null)
            zoomValue.setText(String.format("%s", map().getZoom()));
    }
    private JMapViewer map() {
        return treeMap.getViewer();
    }
    /*public static void main(String []q)
    {
        //int i=0;
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
            
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CreatingMap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CreatingMap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CreatingMap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CreatingMap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        new CreatingMap().setVisible(true);
    }*/
//    private static Coordinate c(double lat, double lon) {
//        return new Coordinate(lat, lon);
//    }
    
        private JPopupMenu createPopupMenu(final AbstractLayer layer) {
        Random r = new Random();    
        final Double lat=getLayerDetails(layer.toString()).getKey();
        final Double longti=getLayerDetails(layer.toString()).getValue();
        final Coordinate location = new Coordinate(lat,longti);    
        MyMarker dot = new MyMarker("", location);
        map().addMapMarker(dot);
                     Style style = dot.getStyle();
                style.setBackColor(Color.getHSBColor(r.nextFloat(), 1f, 1f));
                style.setColor(Color.red);
                map().repaint();
        JMenuItem menuItemShow = new JMenuItem("show Details");
        JMenuItem menuItemHide = new JMenuItem("Delete");
        JMenuItem menuItemZoom = new JMenuItem("Zoom");
        //Create the popup menu.
        JPopupMenu popup = new JPopupMenu();

        // Create items
       // if (layer.isVisibleTexts() == null) {
            popup.add(menuItemShow);
            popup.add(menuItemHide);
            popup.add(menuItemZoom);
        //} //else if (layer.isVisibleTexts()) popup.add(menuItemHide);
        //else popup.add(menuItemShow);

        menuItemShow.addActionListener((ActionEvent arg0) -> {
             //Double lat=getLayerDetails(layer.toString()).getKey();
             //Double longti=getLayerDetails(layer.toString()).getValue();
             //location = new Coordinate(lat,longti);
             setVisibleTexts(layer, true,location);
            if (layer.getParent() != null) layer.getParent().calculateVisibleTexts();
             map().repaint();
       });
        menuItemHide.addActionListener((ActionEvent arg0) -> {
            
                int dialogButton = JOptionPane.YES_NO_OPTION;
                JOptionPane.showConfirmDialog (null, "Want To Delete this Location ?","Warning",dialogButton);

                if(dialogButton == 0){
                    
                try
                    {
                        this.dispose();
                        //Double lat=getLayerDetails(layer.toString()).getKey();
                        //Double longti=getLayerDetails(layer.toString()).getValue();
                        Connection con =MyConnection.createConnection();
                        PreparedStatement pmt = con.prepareStatement("Delete from newcoordinate where langtitute =? and longtitute=?");
                        pmt.setString(1, String.valueOf(lat));
                        pmt.setString(2, String.valueOf(longti));
                        pmt.executeUpdate();
                        //new CreatingMap(bid).setVisible(true);
        
           
                    }
                catch(Exception ex)
                    {
                        JOptionPane.showMessageDialog(null, ex);
                    }
                }
                else
                {
                    this.dispose();
                }
            //setVisibleTexts(layer, false);
            if (layer.getParent() != null) layer.getParent().calculateVisibleTexts();
            map().repaint();
       });
        
            menuItemZoom.addActionListener((ActionEvent arg0) -> {
             //Double lat=getLayerDetails(layer.toString()).getKey();
             //Double longti=getLayerDetails(layer.toString()).getValue();
            
            // Coordinate location = new Coordinate(lat,longti);
             map().setDisplayPosition(location, 16);
            //setVisibleTexts(layer, false);
            //JOptionPane.showMessageDialog(null, "Zooming tomorrow !!");
            if (layer.getParent() != null) layer.getParent().calculateVisibleTexts();
            map().repaint();
       });

       return popup;
    }
            private static void setVisibleTexts(AbstractLayer layer, boolean visible,Coordinate location) {
                
            NewMarker lp = new NewMarker(location.getLat(),location.getLon(),layer.toString());
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            lp.setLocation(dim.width/2-lp.getSize().width/2, dim.height/2-lp.getSize().height/2);
            lp.setVisible(true);    
            layer.setVisibleTexts(visible);
            if (layer instanceof LayerGroup) {
            LayerGroup group = (LayerGroup) layer;
            if (group.getLayers() != null)
                for (AbstractLayer al: group.getLayers()) {
                    setVisibleTexts(al, visible,location);
                }
        }
    }
            public Pair<Double,Double> getLayerDetails(String layer)
            {
                
                double val1=0.00,val2=0.00;
                 try
                    {
                        Connection con =MyConnection.createConnection();
                        PreparedStatement pmt = con.prepareStatement("Select langtitute,longtitute from newcoordinate where Name =?");
                        pmt.setString(1, layer);
                        ResultSet rs = pmt.executeQuery();
                        
                        while(rs.next())
                            {
                                   
                               val1=  Double.parseDouble(rs.getString("langtitute"));
                               val2=  Double.parseDouble(rs.getString("longtitute"));
                            }
           
      }
      catch(Exception ex)
      {
          JOptionPane.showMessageDialog(null, ex);
      }
                
               return new Pair<>(val1, val2);
            }
            
private static class MyMarker extends MapMarkerCircle {

        public MyMarker(String name, Coordinate coord) {
            super(null, name, coord, 12, MapMarker.STYLE.FIXED, getDefaultStyle());
        }
    }            
   private  void createScene(int id)  {
    
        Platform.runLater(new Runnable() {
        @Override
        public void run() {
          //javaFX operations should go here
        try{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLDocument.fxml"));
                     
                     Parent root1 = (Parent) fxmlLoader.load();
                     Stage stage = new Stage();
                     FXMLDocumentController fc=new FXMLDocumentController();
                     Pane p=fc.loadMain(id);
                     ((Label)root1.lookup("#builder_id")).setText(""+id);
                     ((ScrollPane)root1.lookup("#scroll_pane")).setContent(p);
                     Scene scene = new Scene(root1);
                     stage.initStyle(StageStyle.UNDECORATED);
                     stage.setScene(scene);
                     stage.show();
               
    }catch(Exception e)
    {
        e.printStackTrace();
    }
      }
   });        
    } 
}
